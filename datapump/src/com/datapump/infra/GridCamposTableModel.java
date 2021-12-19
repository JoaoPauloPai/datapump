package com.datapump.infra;

import com.datapump.java.CampoVO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * <p>
 * Title: Jumbo</p>
 * <p>
 * Description: Table Model gridCampos.</p>
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
 
* @author João Paulo
 * @version 1.0
 */
public class GridCamposTableModel extends AbstractTableModel {

    private List<CampoVO> campos;
    private final String[] colunas;
    private final int NOME_CAMPO = 0;

    public GridCamposTableModel() {
        this.campos = new ArrayList<>();
        this.colunas = new String[]{"Nome Campo"};
    }

    @Override
    public int getRowCount() {
        return campos.size();
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
        CampoVO campo = campos.get(rowIndex);
        switch (columnIndex) {
            case NOME_CAMPO:
                return campo.getNome();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        CampoVO campo = campos.get(rowIndex);

        switch (columnIndex) {
            case NOME_CAMPO:
                campo.setNome((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setValues(List<CampoVO> campos) {
        this.campos = campos;
        fireTableDataChanged();
    }

    public CampoVO getRow(int rowIndex) {
        return campos.get(rowIndex);
    }

    public void setRow(CampoVO campo, int rowIndex) {
        campos.set(rowIndex, campo);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void adicionaRegistro(CampoVO campo) {
        campos.add(campo);
        fireTableRowsInserted(campos.size() - 1, campos.size() - 1);
    }

    public void removeRegistro(int rowIndex) {
        campos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void limpaRegistros() {
        campos.clear();
        fireTableDataChanged();
    }
}
