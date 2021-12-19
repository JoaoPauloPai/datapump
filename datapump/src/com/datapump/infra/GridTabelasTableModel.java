package com.datapump.infra;

import com.datapump.java.TabelaVO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * <p>
 * Title: Jumbo</p>
 * <p>
 * Description: Table Model gridTabelas.</p>
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
public class GridTabelasTableModel extends AbstractTableModel {

    private List<TabelaVO> tabelas;
    private final String[] colunas;
    private static final int NOME_TABELA = 0;

    public GridTabelasTableModel() {
        this.tabelas = new ArrayList<>();
        this.colunas = new String[]{"Nome Tabela"};
    }

    @Override
    public int getRowCount() {
        return tabelas.size();
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
        TabelaVO tabela = tabelas.get(rowIndex);
        switch (columnIndex) {
            case NOME_TABELA:
                return tabela.getNome();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        TabelaVO tabela = tabelas.get(rowIndex);

        switch (columnIndex) {
            case NOME_TABELA:
                tabela.setNome((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setValues(List<TabelaVO> tabelas) {
        this.tabelas = tabelas;
        fireTableDataChanged();
    }

    public TabelaVO getRow(int rowIndex) {
        return tabelas.get(rowIndex);
    }

    public void setRow(TabelaVO tabela, int rowIndex) {
        tabelas.set(rowIndex, tabela);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void adicionaRegistro(TabelaVO tabela) {
        tabelas.add(tabela);
        fireTableRowsInserted(tabelas.size() - 1, tabelas.size() - 1);
    }

    public void removeRegistro(int rowIndex) {
        tabelas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void limpaRegistros() {
        tabelas.clear();
        fireTableDataChanged();
    }

}
