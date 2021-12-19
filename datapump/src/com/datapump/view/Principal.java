package com.datapump.view;

import com.datapump.controller.PrincipalController;
import com.datapump.infra.Constantes;
import com.datapump.infra.GridCamposTableModel;
import com.datapump.infra.GridTabelasTableModel;
import com.datapump.infra.GridVinculosTableModel;
import com.datapump.java.CampoVO;
import com.datapump.java.TabelaVO;
import com.datapump.java.VinculoVO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 * <p>
 * Title: Jumbo</p>
 * <p>
 * Description: Tela Principal.</p>
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
 
* @author Jo�o Paulo
 * @version 1.0
 */
public class Principal extends javax.swing.JFrame {

    private GridVinculosTableModel tableModelVinculos;
    private GridTabelasTableModel tableModelTabelasSh;
    private GridCamposTableModel tableModelCamposSh;
    private GridTabelasTableModel tableModelTabelasCliente;
    private GridCamposTableModel tableModelCamposCliente;
    private PrincipalController controller;
    private TableRowSorter sorterTabelasSoftwareHouse;
    private TableRowSorter sorterTabelasCliente;

    public Principal() {
        initComponents();

        tableModelVinculos = new GridVinculosTableModel();
        gridVinculos.setModel(tableModelVinculos);

        //SOFTWARE HOUSE
        tableModelTabelasSh = new GridTabelasTableModel();
        gridTabelasSoftwareHouse.setModel(tableModelTabelasSh);
        gridTabelasSoftwareHouse.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                atualizaCamposSoftwareHouse();
                totalRegistrosSoftwareHouse();
            }
        });
        sorterTabelasSoftwareHouse = new TableRowSorter(tableModelTabelasSh);
        gridTabelasSoftwareHouse.setRowSorter(sorterTabelasSoftwareHouse);
        gridTabelasSoftwareHouse.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    visualizaRegistrosTabela(Constantes.SOFTWARE_HOUSE);
                }
            }

        });

        tableModelCamposSh = new GridCamposTableModel();
        gridCamposSoftwareHouse.setModel(tableModelCamposSh);

        //CLIENTE
        tableModelTabelasCliente = new GridTabelasTableModel();
        gridTabelasCliente.setModel(tableModelTabelasCliente);
        gridTabelasCliente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                atualizaCamposCliente();
                totalRegistrosCliente();
            }
        });
        sorterTabelasCliente = new TableRowSorter(tableModelTabelasCliente);
        gridTabelasCliente.setRowSorter(sorterTabelasCliente);
        gridTabelasCliente.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    visualizaRegistrosTabela(Constantes.CLIENTE);
                }
            }

        });

        tableModelCamposCliente = new GridCamposTableModel();
        gridCamposCliente.setModel(tableModelCamposCliente);

        controller = new PrincipalController();
    }

    private void conectarSoftwareHouse() {
        try {
            controller.conectar(Constantes.SOFTWARE_HOUSE,
                    cbSgdbSh.getSelectedItem().toString(),
                    txtUrlSh.getText(),
                    txtUsuarioSh.getText(),
                    new String(txtSenhaSh.getPassword()));

            List<TabelaVO> tabelas = controller.getTabelas(Constantes.SOFTWARE_HOUSE);
            tableModelTabelasSh.setValues(tabelas);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao conectar ao banco!\n" + e.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conectarCliente() {
        try {
            controller.conectar(Constantes.CLIENTE,
                    cbSgdbCliente.getSelectedItem().toString(),
                    txtUrlCliente.getText(),
                    txtUsuarioCliente.getText(),
                    new String(txtSenhaCliente.getPassword()));

            List<TabelaVO> tabelas = controller.getTabelas(Constantes.CLIENTE);
            tableModelTabelasCliente.setValues(tabelas);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao conectar ao banco!\n" + e.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizaCamposSoftwareHouse() {
        if (gridTabelasSoftwareHouse.getSelectedRow() != -1) {
            TabelaVO tabela = tableModelTabelasSh.getRow(gridTabelasSoftwareHouse.convertRowIndexToModel(gridTabelasSoftwareHouse.getSelectedRow()));
            tableModelCamposSh.setValues(tabela.getCampos());
        } else {
            tableModelCamposSh.setValues(new ArrayList<CampoVO>());
        }
    }

    private void atualizaCamposCliente() {
        if (gridTabelasCliente.getSelectedRow() != -1) {
            TabelaVO tabela = tableModelTabelasCliente.getRow(gridTabelasCliente.convertRowIndexToModel(gridTabelasCliente.getSelectedRow()));
            tableModelCamposCliente.setValues(tabela.getCampos());
        } else {
            tableModelCamposCliente.setValues(new ArrayList<CampoVO>());
        }
    }

    private void totalRegistrosSoftwareHouse() {
        try {
            if (gridTabelasSoftwareHouse.getSelectedRow() != -1) {
                TabelaVO tabela = tableModelTabelasSh.getRow(gridTabelasSoftwareHouse.convertRowIndexToModel(gridTabelasSoftwareHouse.getSelectedRow()));
                labelTotalRegistrosSoftwareHouse.setText("Total Registros: "
                        + controller.totalRegistros(Constantes.SOFTWARE_HOUSE, tabela.getNome()));
            } else {
                labelTotalRegistrosSoftwareHouse.setText("Total Registros: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            labelTotalRegistrosSoftwareHouse.setText("Total Registros: " + e.getMessage());
        }
    }

    private void totalRegistrosCliente() {
        try {
            if (gridTabelasCliente.getSelectedRow() != -1) {
                TabelaVO tabela = tableModelTabelasCliente.getRow(gridTabelasCliente.convertRowIndexToModel(gridTabelasCliente.getSelectedRow()));
                labelTotalRegistrosCliente.setText("Total Registros: "
                        + controller.totalRegistros(Constantes.CLIENTE, tabela.getNome()));
            } else {
                labelTotalRegistrosCliente.setText("Total Registros: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            labelTotalRegistrosCliente.setText("Total Registros: " + e.getMessage());
        }
    }

    private void visualizaRegistrosTabela(int tipo) {
        try {
            TabelaVO tabela = null;
            if (tipo == Constantes.SOFTWARE_HOUSE) {
                tabela = tableModelTabelasSh.getRow(gridTabelasSoftwareHouse.convertRowIndexToModel(gridTabelasSoftwareHouse.getSelectedRow()));
            } else if (tipo == Constantes.CLIENTE) {
                tabela = tableModelTabelasCliente.getRow(gridTabelasCliente.convertRowIndexToModel(gridTabelasCliente.getSelectedRow()));
            }
            TabelaSelecionada tabelaSelecionada = new TabelaSelecionada(controller.registrosTabela(tipo, tabela.getNome()));
            tabelaSelecionada.setSize(800, 600);
            tabelaSelecionada.setLocationRelativeTo(null);
            tabelaSelecionada.setTitle("Registros da Tabela '" + tabela.getNome() + "'");
            tabelaSelecionada.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao visualizar os dados!\n" + ex.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtroTabelaSoftwareHouse() {
        RowFilter<GridTabelasTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(txtPesquisaSoftwareHouse.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            e.printStackTrace();
            return;
        }
        sorterTabelasSoftwareHouse.setRowFilter(rf);
    }

    private void filtroTabelaCliente() {
        RowFilter<GridTabelasTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(txtPesquisaCliente.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            e.printStackTrace();
            return;
        }
        sorterTabelasCliente.setRowFilter(rf);
    }

    private void inserirVinculoSoftwareHouse() {
        if (gridCamposSoftwareHouse.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um campo da lista.", "Informação do Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            TabelaVO tabela = tableModelTabelasSh.getRow(gridTabelasSoftwareHouse.convertRowIndexToModel(gridTabelasSoftwareHouse.getSelectedRow()));
            CampoVO campo = tableModelCamposSh.getRow(gridCamposSoftwareHouse.getSelectedRow());
            inserirVinculo(Constantes.SOFTWARE_HOUSE, tabela.getNome(), campo.getNome());
        }

    }

    private void inserirVinculoCliente() {
        if (gridCamposCliente.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um campo da lista.", "Informação do Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            TabelaVO tabela = tableModelTabelasCliente.getRow(gridTabelasCliente.convertRowIndexToModel(gridTabelasCliente.getSelectedRow()));
            CampoVO campo = tableModelCamposCliente.getRow(gridCamposCliente.getSelectedRow());
            inserirVinculo(Constantes.CLIENTE, tabela.getNome(), campo.getNome());
        }

    }

    private void editarVinculoSoftwareHouse() {
        if (gridCamposSoftwareHouse.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um campo da lista.", "Informação do Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            TabelaVO tabela = tableModelTabelasSh.getRow(gridTabelasSoftwareHouse.convertRowIndexToModel(gridTabelasSoftwareHouse.getSelectedRow()));
            CampoVO campo = tableModelCamposSh.getRow(gridCamposSoftwareHouse.getSelectedRow());
            editarVinculo(Constantes.SOFTWARE_HOUSE, tabela.getNome(), campo.getNome());
        }

    }

    private void editarVinculoCliente() {
        if (gridCamposCliente.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um campo da lista.", "Informação do Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            TabelaVO tabela = tableModelTabelasCliente.getRow(gridTabelasCliente.convertRowIndexToModel(gridTabelasCliente.getSelectedRow()));
            CampoVO campo = tableModelCamposCliente.getRow(gridCamposCliente.getSelectedRow());
            editarVinculo(Constantes.CLIENTE, tabela.getNome(), campo.getNome());
        }
    }

    private void inserirVinculo(int tipo, String tabela, String campo) {
        VinculoVO vinculo = new VinculoVO();
        if (tipo == Constantes.SOFTWARE_HOUSE) {
            vinculo.setTabelaSoftwareHouse(tabela);
            vinculo.setCampoSoftwareHouse(campo);
        } else if (tipo == Constantes.CLIENTE) {
            vinculo.setTabelaCliente(tabela);
            vinculo.setCampoCliente(campo);
        }
        tableModelVinculos.adicionaRegistro(vinculo);
        gridVinculos.setRowSelectionInterval(tableModelVinculos.getRowCount() - 1, tableModelVinculos.getRowCount() - 1);
    }

    private void editarVinculo(int tipo, String tabela, String campo) {
        if (gridVinculos.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um vínculo da lista.", "Informação do Sistema", JOptionPane.WARNING_MESSAGE);
        } else {
            VinculoVO vinculo = tableModelVinculos.getRow(gridVinculos.getSelectedRow());
            if (tipo == Constantes.SOFTWARE_HOUSE) {
                vinculo.setTabelaSoftwareHouse(tabela);
                vinculo.setCampoSoftwareHouse(campo);
            } else {
                vinculo.setTabelaCliente(tabela);
                vinculo.setCampoCliente(campo);
            }
            tableModelVinculos.setRow(vinculo, gridVinculos.getSelectedRow());
        }
    }

    private void excluirVinculo() {
        if (gridVinculos.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um vínculo da lista.", "Informação do Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableModelVinculos.removeRegistro(gridVinculos.getSelectedRow());
        }
    }

    private void excluirTodosVinculos() {
        int resposta = JOptionPane.showConfirmDialog(this, "Excluir todos os vínculos?.", "Pergunta do Sistema", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            tableModelVinculos.limpaRegistros();
        }
    }

    private void copiarDados() {
        if (tableModelVinculos.getAllRows().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há vínculos selecionados!", "Informação do Sistema", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja copiar os dados das tabelas selecionadas?.", "Pergunta do Sistema", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    List<VinculoVO> vinculos = tableModelVinculos.getAllRows();
                    controller.copiarDados(vinculos);
                    JOptionPane.showMessageDialog(this, "Dados copiados com sucesso!", "Informação do Sistema", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao copiar os dados!\n" + ex.getMessage(), "Erro do Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbSgdbSh = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUrlSh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsuarioSh = new javax.swing.JTextField();
        btnConectarSoftwareHouse = new javax.swing.JButton();
        txtSenhaSh = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbSgdbCliente = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtUrlCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsuarioCliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSenhaCliente = new javax.swing.JPasswordField();
        btnConectarCliente = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtPesquisaSoftwareHouse = new javax.swing.JTextField();
        btnInserirSoftwareHouse = new javax.swing.JButton();
        btnEditarSoftwareHouse = new javax.swing.JButton();
        labelTotalRegistrosSoftwareHouse = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        gridTabelasSoftwareHouse = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        gridCamposSoftwareHouse = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtPesquisaCliente = new javax.swing.JTextField();
        btnInserirCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        labelTotalRegistrosCliente = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        gridTabelasCliente = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        gridCamposCliente = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        gridVinculos = new javax.swing.JTable();
        btnCopiarDados = new javax.swing.JButton();
        btnLimparGrid = new javax.swing.JButton();
        btnExcluirLinha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jumbo Data Pump");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Conexão Software House"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Senha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(jLabel1, gridBagConstraints);

        cbSgdbSh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "Firebird", "PostGreSQL" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel2.add(cbSgdbSh, gridBagConstraints);

        jLabel2.setText("SGBD:");
        jLabel2.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel4.setText("URL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtUrlSh.setText("jdbc:mysql://localhost/");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel2.add(txtUrlSh, gridBagConstraints);

        jLabel5.setText("Usuário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel2.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel2.add(txtUsuarioSh, gridBagConstraints);

        btnConectarSoftwareHouse.setText("Conectar");
        btnConectarSoftwareHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarSoftwareHouseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnConectarSoftwareHouse, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel2.add(txtSenhaSh, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Conexão Cliente"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("SGBD:");
        jLabel3.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(jLabel3, gridBagConstraints);

        cbSgdbCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "Firebird", "PostGreSQL" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(cbSgdbCliente, gridBagConstraints);

        jLabel6.setText("URL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(jLabel6, gridBagConstraints);

        txtUrlCliente.setText("jdbc:mysql://localhost/");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtUrlCliente, gridBagConstraints);

        jLabel7.setText("Usuário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtUsuarioCliente, gridBagConstraints);

        jLabel8.setText("Senha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel3.add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel3.add(txtSenhaCliente, gridBagConstraints);

        btnConectarCliente.setText("Conectar");
        btnConectarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnConectarCliente, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel9.setText("Pesquisar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        txtPesquisaSoftwareHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaSoftwareHouseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel5.add(txtPesquisaSoftwareHouse, gridBagConstraints);

        btnInserirSoftwareHouse.setText("Inserir");
        btnInserirSoftwareHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirSoftwareHouseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnInserirSoftwareHouse, gridBagConstraints);

        btnEditarSoftwareHouse.setText("Editar");
        btnEditarSoftwareHouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSoftwareHouseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnEditarSoftwareHouse, gridBagConstraints);

        labelTotalRegistrosSoftwareHouse.setText("Total Registros: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(labelTotalRegistrosSoftwareHouse, gridBagConstraints);

        jScrollPane6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabelas"));

        gridTabelasSoftwareHouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(gridTabelasSoftwareHouse);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane6, gridBagConstraints);

        jScrollPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("Campos"));

        gridCamposSoftwareHouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(gridCamposSoftwareHouse);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jScrollPane7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel10.setText("Pesquisar:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        jPanel6.add(jLabel10, gridBagConstraints);

        txtPesquisaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel6.add(txtPesquisaCliente, gridBagConstraints);

        btnInserirCliente.setText("Inserir");
        btnInserirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnInserirCliente, gridBagConstraints);

        btnEditarCliente.setText("Editar");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnEditarCliente, gridBagConstraints);

        labelTotalRegistrosCliente.setText("Total Registros: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(labelTotalRegistrosCliente, gridBagConstraints);

        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabelas"));

        gridTabelasCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(gridTabelasCliente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jScrollPane8, gridBagConstraints);

        jScrollPane9.setBorder(javax.swing.BorderFactory.createTitledBorder("Campos"));

        gridCamposCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(gridCamposCliente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jScrollPane9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel6, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Vínculos"));

        gridVinculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(gridVinculos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        jPanel7.add(jScrollPane5, gridBagConstraints);

        btnCopiarDados.setText("Copiar Dados");
        btnCopiarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiarDadosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnCopiarDados, gridBagConstraints);

        btnLimparGrid.setText("Limpar Grid");
        btnLimparGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparGridActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnLimparGrid, gridBagConstraints);

        btnExcluirLinha.setText("Excluir Linha");
        btnExcluirLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirLinhaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnExcluirLinha, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConectarSoftwareHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarSoftwareHouseActionPerformed
        conectarSoftwareHouse();
    }//GEN-LAST:event_btnConectarSoftwareHouseActionPerformed

    private void txtPesquisaSoftwareHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaSoftwareHouseActionPerformed
        filtroTabelaSoftwareHouse();
    }//GEN-LAST:event_txtPesquisaSoftwareHouseActionPerformed

    private void btnInserirSoftwareHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirSoftwareHouseActionPerformed
        inserirVinculoSoftwareHouse();
    }//GEN-LAST:event_btnInserirSoftwareHouseActionPerformed

    private void btnEditarSoftwareHouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSoftwareHouseActionPerformed
        editarVinculoSoftwareHouse();
    }//GEN-LAST:event_btnEditarSoftwareHouseActionPerformed

    private void btnConectarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarClienteActionPerformed
        conectarCliente();
    }//GEN-LAST:event_btnConectarClienteActionPerformed

    private void txtPesquisaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaClienteActionPerformed
        filtroTabelaCliente();
    }//GEN-LAST:event_txtPesquisaClienteActionPerformed

    private void btnInserirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirClienteActionPerformed
        inserirVinculoCliente();
    }//GEN-LAST:event_btnInserirClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        editarVinculoCliente();
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnExcluirLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirLinhaActionPerformed
        excluirVinculo();
    }//GEN-LAST:event_btnExcluirLinhaActionPerformed

    private void btnLimparGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparGridActionPerformed
        excluirTodosVinculos();
    }//GEN-LAST:event_btnLimparGridActionPerformed

    private void btnCopiarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiarDadosActionPerformed
        copiarDados();
    }//GEN-LAST:event_btnCopiarDadosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectarCliente;
    private javax.swing.JButton btnConectarSoftwareHouse;
    private javax.swing.JButton btnCopiarDados;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarSoftwareHouse;
    private javax.swing.JButton btnExcluirLinha;
    private javax.swing.JButton btnInserirCliente;
    private javax.swing.JButton btnInserirSoftwareHouse;
    private javax.swing.JButton btnLimparGrid;
    private javax.swing.JComboBox cbSgdbCliente;
    private javax.swing.JComboBox cbSgdbSh;
    private javax.swing.JTable gridCamposCliente;
    private javax.swing.JTable gridCamposSoftwareHouse;
    private javax.swing.JTable gridTabelasCliente;
    private javax.swing.JTable gridTabelasSoftwareHouse;
    private javax.swing.JTable gridVinculos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel labelTotalRegistrosCliente;
    private javax.swing.JLabel labelTotalRegistrosSoftwareHouse;
    private javax.swing.JTextField txtPesquisaCliente;
    private javax.swing.JTextField txtPesquisaSoftwareHouse;
    private javax.swing.JPasswordField txtSenhaCliente;
    private javax.swing.JPasswordField txtSenhaSh;
    private javax.swing.JTextField txtUrlCliente;
    private javax.swing.JTextField txtUrlSh;
    private javax.swing.JTextField txtUsuarioCliente;
    private javax.swing.JTextField txtUsuarioSh;
    // End of variables declaration//GEN-END:variables
}
