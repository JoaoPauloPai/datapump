package com.datapump.infra;

import com.datapump.java.VinculoVO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
* <p>Title: Jumbo</p>
* <p>Description: Table Model gridVinculos.</p>
*
* <p>The MIT License</p>
*
* <p>Copyright: Copyright (C) 2010 JUMBO.COM
*
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
*

* @author João Paulo
* @version 1.0
*/
public class GridVinculosTableModel extends AbstractTableModel {

    private List<VinculoVO> vinculos;
    private final String[] colunas;
    private static final int TABELA_SH = 0;
    private static final int CAMPO_SH = 1;
    private static final int TABELA_CLIENTE = 2;
    private static final int CAMPO_CLIENTE = 3;
    private static final int VALOR = 4;

    public GridVinculosTableModel() {
        this.vinculos = new ArrayList<>();
        this.colunas = new String[]{"Tabela SH", "Campo SH", "Tabela Cliente", "Campo Cliente", "Valor"};
    }
    
    @Override
    public int getRowCount() {
        return vinculos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VinculoVO vinculo = vinculos.get(rowIndex);
        switch (columnIndex) {
            case TABELA_SH:
                return vinculo.getTabelaSoftwareHouse();
            case CAMPO_SH:
                return vinculo.getCampoSoftwareHouse();
            case TABELA_CLIENTE:
                return vinculo.getTabelaCliente();
            case CAMPO_CLIENTE:
                return vinculo.getCampoCliente();
            case VALOR:
                return vinculo.getValor();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        VinculoVO vinculo = vinculos.get(rowIndex);
        
        switch (columnIndex) {
            case TABELA_SH:
                vinculo.setTabelaSoftwareHouse((String) aValue);
            case CAMPO_SH:
                vinculo.setCampoSoftwareHouse((String) aValue);
            case TABELA_CLIENTE:
                vinculo.setTabelaCliente((String) aValue);
            case CAMPO_CLIENTE:
                vinculo.setCampoCliente((String) aValue);
            case VALOR:
                vinculo.setValor((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == VALOR) {
            return true;
        }
        return false;
    }
    
    public VinculoVO getRow(int rowIndex) {
        return vinculos.get(rowIndex);
    }

    public List<VinculoVO> getAllRows() {
        return vinculos;
    }
    
    public void setRow(VinculoVO vinculo, int rowIndex) {
        vinculos.set(rowIndex, vinculo);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public void adicionaRegistro(VinculoVO vinculo) {
        vinculos.add(vinculo);
        fireTableRowsInserted(vinculos.size() - 1, vinculos.size() - 1);
    }
    
    public void removeRegistro(int rowIndex) {
        vinculos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void limpaRegistros() {
        vinculos.clear();
        fireTableDataChanged();
    }
    
}