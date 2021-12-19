package com.datapump.controller;

import com.datapump.bd.AcessoBD;
import com.datapump.infra.Constantes;
import com.datapump.java.CampoVO;
import com.datapump.java.TabelaVO;
import com.datapump.java.VinculoVO;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: Jumbo</p>
 * <p>
 * Description: Controller Principal.</p>
 *
 * <p>
 * The MIT License</p>
 *
 * <p>
 * Copyright: Copyright (C) 2010 JUMBO.COM
 * 
* Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
* The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 

 * 
* @author João Paulo
 * @version 1.0
 */
public class PrincipalController {

    private Connection conSoftwareHouse;
    private Connection conCliente;

    public void conectar(int tipo, String bancoDados, String url, String usuario, String senha) throws Exception {
        String driver = null;
        switch (bancoDados) {
            case "MySQL":
                driver = "com.mysql.jdbc.Driver";
                break;
            case "Firebird":
                driver = "org.firebirdsql.jdbc.FBDriver";
                break;
            case "PostgreSql":
                driver = "org.postgresql.Driver";
                break;
        }
        AcessoBD acessoBD = new AcessoBD();
        if (tipo == Constantes.SOFTWARE_HOUSE) {
            conSoftwareHouse = acessoBD.getConnection(driver, url, usuario, senha);
        } else if (tipo == Constantes.CLIENTE) {
            conCliente = acessoBD.getConnection(driver, url, usuario, senha);
        }
    }

    public List<TabelaVO> getTabelas(int tipo) throws Exception {
        DatabaseMetaData databaseMetaData = null;
        if (tipo == Constantes.SOFTWARE_HOUSE) {
            databaseMetaData = conSoftwareHouse.getMetaData();
        } else if (tipo == Constantes.CLIENTE) {
            databaseMetaData = conCliente.getMetaData();
        }

        ResultSet rsTabelas = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
        ResultSet rsCampos;

        List<TabelaVO> tabelas = new ArrayList<>();
        TabelaVO t;
        CampoVO c;
        String nomeTabela;

        while (rsTabelas.next()) {
            nomeTabela = rsTabelas.getString("TABLE_NAME");
            t = new TabelaVO();
            t.setNome(nomeTabela);

            rsCampos = databaseMetaData.getColumns(null, null, nomeTabela, null);
            t.setCampos(new ArrayList<CampoVO>());
            while (rsCampos.next()) {
                c = new CampoVO();
                c.setNome(rsCampos.getString("COLUMN_NAME"));

                t.getCampos().add(c);
            }

            tabelas.add(t);
        }

        return tabelas;
    }

    public int totalRegistros(int tipo, String nomeTabela) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM " + nomeTabela;
        PreparedStatement ps = null;
        if (tipo == Constantes.SOFTWARE_HOUSE) {
            ps = conSoftwareHouse.prepareStatement(sql);
        } else if (tipo == Constantes.CLIENTE) {
            ps = conCliente.prepareStatement(sql);
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public void copiarDados(List<VinculoVO> vinculos) throws Exception {
        List<CampoVO[]> registros = registrosTabela(Constantes.CLIENTE, vinculos.get(0).getTabelaCliente());
        List<String> scripts = new ArrayList<>();
        String linha;
        String campos = "";
        String valores = "";
        //gera os scripts
        for (CampoVO[] campo : registros) {
            linha = "INSERT INTO " + vinculos.get(0).getTabelaSoftwareHouse() + " ";
            campos = "";
            valores = "";
            for (VinculoVO vinculo : vinculos) {
                campos += vinculo.getCampoSoftwareHouse() + ",";
                if (vinculo.getTabelaCliente() != null) {
                    for (int i = 0; i < campo.length; i++) {
                        if (campo[i].getNome().equals(vinculo.getCampoCliente())) {
                            valores += "'" + campo[i].getValor().toString() + "',";
                            break;
                        }
                    }
                } else {
                    valores += "'" + vinculo.getValor() + "',";
                }
            }
            campos = campos.substring(0, campos.length() - 1);
            valores = valores.substring(0, valores.length() - 1);
            linha += "(" + campos + ") VALUES (" + valores + ");";

            scripts.add(linha);
        }

        //executa os scripts
        PreparedStatement ps = null;
        for (String script : scripts) {
            ps = conSoftwareHouse.prepareStatement(script);
            ps.executeUpdate();
        }
    }

    public List<CampoVO[]> registrosTabela(int tipo, String nomeTabela) throws Exception {
        List<CampoVO[]> listaDados = new ArrayList<>();

        String sql = "SELECT * FROM " + nomeTabela;
        PreparedStatement ps = null;
        if (tipo == Constantes.SOFTWARE_HOUSE) {
            ps = conSoftwareHouse.prepareStatement(sql);
        } else if (tipo == Constantes.CLIENTE) {
            ps = conCliente.prepareStatement(sql);
        }
        ResultSet rs = ps.executeQuery();
        CampoVO[] campos;
        while (rs.next()) {
            campos = new CampoVO[ps.getMetaData().getColumnCount()];
            for (int i = 0; i < ps.getMetaData().getColumnCount(); i++) {
                campos[i] = new CampoVO();
                campos[i].setNome(ps.getMetaData().getColumnName(i + 1));
                campos[i].setValor(rs.getObject(i + 1));
            }
            listaDados.add(campos);
        }
        return listaDados;
    }
}
