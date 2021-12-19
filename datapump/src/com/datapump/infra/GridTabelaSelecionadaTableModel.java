package com.datapump.infra;

import com.datapump.java.CampoVO;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
* <p>Title: Jumbo</p>
* <p>Description: Table Model gridTabelaSelecionada.</p>
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
public class GridTabelaSelecionadaTableModel extends AbstractTableModel {

    private List<CampoVO[]> campos;
    private final String[] colunas;

    public GridTabelaSelecionadaTableModel(List<CampoVO[]> campos) {
        this.campos = campos;
        if (!campos.isEmpty()) {
            colunas = new String[campos.get(0).length];
            for (int i = 0; i < campos.get(0).length; i++) {
                colunas[i] = campos.get(0)[i].getNome();
            }
        } else {
            colunas = new String[0];
        }
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
        return campos.get(rowIndex)[columnIndex].getValor();
    }
}