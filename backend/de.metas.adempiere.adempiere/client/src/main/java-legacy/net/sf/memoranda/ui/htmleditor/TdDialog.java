package net.sf.memoranda.ui.htmleditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import net.sf.memoranda.ui.htmleditor.util.Local;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TdDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel buttonsPanel = new JPanel();
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  Border border1;

  String[] aligns = {"", "left", "center", "right"};
  String[] valigns = {"", "top", "center", "bottom"};
  String[] tdvaligns = {"", "top", "middle", "bottom", "baseline"};
  Border border2;
  JPanel headerPanel = new JPanel();
  JLabel header = new JLabel();
  FlowLayout flowLayout7 = new FlowLayout();

  public boolean CANCELLED = false;
  Border border3;
  Border border4;
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JLabel jLabel4 = new JLabel();
  FlowLayout flowLayout5 = new FlowLayout();
  JTextField tdHeightField = new JTextField();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel jLabel7 = new JLabel();
  FlowLayout flowLayout6 = new FlowLayout();
  Component component1;
  JPanel jPanel7 = new JPanel();
  JPanel tdPanel = new JPanel();
  JTextField tdWidthField = new JTextField();
  Component component2;
  JPanel jPanel6 = new JPanel();
  JComboBox tdAlignCB = new JComboBox(aligns);
  JLabel jLabel6 = new JLabel();
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanel5 = new JPanel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JPanel trPanel = new JPanel();
  GridLayout gridLayout4 = new GridLayout();
  JLabel jLabel20 = new JLabel();
  JComboBox tdValignCB = new JComboBox(tdvaligns);
  FlowLayout flowLayout12 = new FlowLayout();
  JTextField tdBgcolorField = new JTextField();
  JPanel jPanel14 = new JPanel();
  JLabel jLabel21 = new JLabel();
  JButton tdBgcolorB = new JButton();
  Component component11;
  JCheckBox tdNowrapChB = new JCheckBox();
  JComboBox trAlignCB = new JComboBox(aligns);
  JComboBox trValignCB = new JComboBox(tdvaligns);
  Component component12;
  JLabel jLabel22 = new JLabel();
  Component component7;
  FlowLayout flowLayout10 = new FlowLayout();
  JLabel jLabel16 = new JLabel();
  JPanel jPanel11 = new JPanel();
  JTextField trBgcolorField = new JTextField();
  JLabel jLabel23 = new JLabel();
  JPanel jPanel15 = new JPanel();
  FlowLayout flowLayout13 = new FlowLayout();
  JButton trBgcolorB = new JButton();
  JSpinner tdColspan = new JSpinner(new SpinnerNumberModel(0,0,999,1));
  JSpinner tdRowspan = new JSpinner(new SpinnerNumberModel(0,0,999,1));
  JLabel jLabel8 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JSpinner cellpadding = new JSpinner(new SpinnerNumberModel(0,0,999,1));
  FlowLayout flowLayout8 = new FlowLayout();
  FlowLayout flowLayout4 = new FlowLayout();
  Component component3;
  JLabel jLabel24 = new JLabel();
  JPanel tablePanel = new JPanel();
  GridLayout gridLayout3 = new GridLayout();
  JSpinner border = new JSpinner(new SpinnerNumberModel(1,0,999,1));
  JPanel jPanel10 = new JPanel();
  JPanel jPanel12 = new JPanel();
  JComboBox vAlignCB = new JComboBox(valigns);
  JTextField bgcolorField = new JTextField();
  JTextField heightField = new JTextField();
  JLabel jLabel13 = new JLabel();
  JSpinner cellspacing = new JSpinner(new SpinnerNumberModel(0,0,999,1));
  JLabel jLabel17 = new JLabel();
  JLabel jLabel25 = new JLabel();
  JButton bgColorB = new JButton();
  Component component5;
  JTextField widthField = new JTextField();
  FlowLayout flowLayout11 = new FlowLayout();
  JPanel jPanel13 = new JPanel();
  JComboBox alignCB = new JComboBox(aligns);
  JLabel jLabel14 = new JLabel();
  Component component6;
  JPanel jPanel9 = new JPanel();
  FlowLayout flowLayout9 = new FlowLayout();
  JLabel jLabel15 = new JLabel();
  Border border5;

  public TdDialog(Frame frame) {
    super(frame, Local.getString("Table properties"), true);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public TdDialog() {
    this(null);
  }


  void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder(5,5,5,5);
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    border3 = BorderFactory.createEmptyBorder(0,0,10,0);
    border4 = BorderFactory.createEmptyBorder(0,5,0,5);
    component1 = Box.createHorizontalStrut(8);
    component2 = Box.createHorizontalStrut(8);
    component11 = Box.createHorizontalStrut(8);
    component12 = Box.createHorizontalStrut(8);
    component7 = Box.createHorizontalStrut(8);
    component3 = Box.createHorizontalStrut(8);
    component5 = Box.createHorizontalStrut(8);
    component6 = Box.createHorizontalStrut(8);
    border5 = BorderFactory.createEmptyBorder();
    panel1.setLayout(borderLayout1);
    cancelB.setMaximumSize(new Dimension(100, 26));
    cancelB.setMinimumSize(new Dimension(100, 26));
    cancelB.setPreferredSize(new Dimension(100, 26));
    cancelB.setText(Local.getString("Cancel"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 26));
    okB.setMinimumSize(new Dimension(100, 26));
    okB.setPreferredSize(new Dimension(100, 26));
    okB.setText("Ok");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    this.getRootPane().setDefaultButton(okB);
    buttonsPanel.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    panel1.setBorder(border1);
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setBorder(border4);
    headerPanel.setMinimumSize(new Dimension(159, 52));
    headerPanel.setPreferredSize(new Dimension(159, 52));
    headerPanel.setLayout(flowLayout7);
    header.setFont(new java.awt.Font("Dialog", 0, 20));
    header.setForeground(new Color(0, 0, 124));
    header.setText(Local.getString("Table properties"));
    header.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/tablebig.png")));
    flowLayout7.setAlignment(FlowLayout.LEFT);
    flowLayout7.setHgap(2);
    flowLayout7.setVgap(2);
    jLabel4.setMaximumSize(new Dimension(70, 16));
    jLabel4.setMinimumSize(new Dimension(50, 16));
    jLabel4.setPreferredSize(new Dimension(70, 16));
    jLabel4.setText(Local.getString("Width"));
    flowLayout5.setAlignment(FlowLayout.LEFT);
    tdHeightField.setMinimumSize(new Dimension(30, 25));
    tdHeightField.setPreferredSize(new Dimension(50, 25));
    flowLayout2.setAlignment(FlowLayout.LEFT);
    jLabel7.setMaximumSize(new Dimension(70, 16));
    jLabel7.setMinimumSize(new Dimension(40, 16));
    jLabel7.setPreferredSize(new Dimension(70, 16));
    jLabel7.setText(Local.getString("Row span"));
    flowLayout6.setAlignment(FlowLayout.LEFT);
    jPanel7.setLayout(flowLayout6);
    tdPanel.setLayout(gridLayout2);
    tdPanel.setBorder(border2);
    tdWidthField.setPreferredSize(new Dimension(50, 25));
    tdWidthField.setMinimumSize(new Dimension(30, 25));
    jPanel6.setLayout(flowLayout5);
    tdAlignCB.setBackground(new Color(230, 230, 230));
    tdAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    tdAlignCB.setPreferredSize(new Dimension(63, 25));
    jLabel6.setText(Local.getString("Col span"));
    jLabel6.setPreferredSize(new Dimension(70, 16));
    jLabel6.setMinimumSize(new Dimension(50, 16));
    jLabel6.setMaximumSize(new Dimension(70, 16));
    gridLayout2.setColumns(1);
    gridLayout2.setRows(4);
    jPanel5.setLayout(flowLayout2);
    jLabel9.setMaximumSize(new Dimension(70, 16));
    jLabel9.setMinimumSize(new Dimension(40, 16));
    jLabel9.setPreferredSize(new Dimension(70, 16));
    jLabel9.setText(Local.getString("Vert. align"));
    jLabel5.setMaximumSize(new Dimension(70, 16));
    jLabel5.setMinimumSize(new Dimension(40, 16));
    jLabel5.setPreferredSize(new Dimension(70, 16));
    jLabel5.setText(Local.getString("Height"));
    trPanel.setLayout(gridLayout4);
    trPanel.setBorder(border2);
    gridLayout4.setColumns(1);
    gridLayout4.setRows(2);
    jLabel20.setText(Local.getString("Align"));
    jLabel20.setPreferredSize(new Dimension(70, 16));
    jLabel20.setMinimumSize(new Dimension(50, 16));
    jLabel20.setMaximumSize(new Dimension(70, 16));
    tdValignCB.setPreferredSize(new Dimension(63, 25));
    tdValignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    tdValignCB.setBackground(new Color(230, 230, 230));
    flowLayout12.setAlignment(FlowLayout.LEFT);
    tdBgcolorField.setPreferredSize(new Dimension(70, 25));
    tdBgcolorField.setMinimumSize(new Dimension(60, 25));
    jPanel14.setLayout(flowLayout12);
    jLabel21.setText(Local.getString("Fill color"));
    jLabel21.setPreferredSize(new Dimension(70, 16));
    jLabel21.setMinimumSize(new Dimension(50, 16));
    jLabel21.setMaximumSize(new Dimension(70, 16));
    tdBgcolorB.setMinimumSize(new Dimension(25, 25));
    tdBgcolorB.setPreferredSize(new Dimension(25, 25));
    tdBgcolorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
    tdBgcolorB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tdBgcolorB_actionPerformed(e);
      }
    });
    tdNowrapChB.setText(Local.getString("No text wrapping"));
    trAlignCB.setPreferredSize(new Dimension(100, 25));
    trAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    trAlignCB.setBackground(new Color(230, 230, 230));
    trValignCB.setBackground(new Color(230, 230, 230));
    trValignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    trValignCB.setPreferredSize(new Dimension(100, 25));
    jLabel22.setMaximumSize(new Dimension(50, 16));
    jLabel22.setMinimumSize(new Dimension(50, 16));
    jLabel22.setPreferredSize(new Dimension(50, 16));
    jLabel22.setText(Local.getString("Align"));
    flowLayout10.setAlignment(FlowLayout.LEFT);
    jLabel16.setText(Local.getString("Vert. align"));
    jLabel16.setPreferredSize(new Dimension(80, 16));
    jLabel16.setMinimumSize(new Dimension(40, 16));
    jLabel16.setMaximumSize(new Dimension(80, 16));
    jPanel11.setLayout(flowLayout10);
    trBgcolorField.setMinimumSize(new Dimension(60, 25));
    trBgcolorField.setPreferredSize(new Dimension(70, 25));
    jLabel23.setMaximumSize(new Dimension(70, 16));
    jLabel23.setMinimumSize(new Dimension(50, 16));
    jLabel23.setPreferredSize(new Dimension(50, 16));
    jLabel23.setText(Local.getString("Fill color"));
    jPanel15.setLayout(flowLayout13);
    flowLayout13.setAlignment(FlowLayout.LEFT);
    trBgcolorB.setMinimumSize(new Dimension(25, 25));
    trBgcolorB.setPreferredSize(new Dimension(25, 25));
    trBgcolorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
    trBgcolorB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        trBgcolorB_actionPerformed(e);
      }
    });
    tdColspan.setBorder(border5);
    tdColspan.setPreferredSize(new Dimension(50, 24));
    tdRowspan.setBorder(border5);
    tdRowspan.setPreferredSize(new Dimension(50, 24));
    jLabel8.setText(Local.getString("Width"));
    jLabel8.setPreferredSize(new Dimension(70, 16));
    jLabel8.setMinimumSize(new Dimension(60, 16));
    jLabel8.setMaximumSize(new Dimension(70, 16));
    jLabel12.setText(Local.getString("Border"));
    jLabel12.setPreferredSize(new Dimension(70, 16));
    jLabel12.setMinimumSize(new Dimension(60, 16));
    jLabel12.setMaximumSize(new Dimension(70, 16));
    cellpadding.setBorder(border5);
    cellpadding.setPreferredSize(new Dimension(50, 24));
    flowLayout8.setAlignment(FlowLayout.LEFT);
    flowLayout4.setAlignment(FlowLayout.LEFT);
    jLabel24.setMaximumSize(new Dimension(70, 16));
    jLabel24.setMinimumSize(new Dimension(60, 16));
    jLabel24.setPreferredSize(new Dimension(70, 16));
    jLabel24.setText(Local.getString("Align"));
    tablePanel.setBorder(border2);
    tablePanel.setLayout(gridLayout3);
    gridLayout3.setColumns(1);
    gridLayout3.setRows(4);
    border.setBorder(border5);
    border.setPreferredSize(new Dimension(50, 24));
    jPanel10.setLayout(flowLayout4);
    jPanel12.setLayout(flowLayout11);
    vAlignCB.setPreferredSize(new Dimension(63, 25));
    vAlignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    vAlignCB.setBackground(new Color(230, 230, 230));
    bgcolorField.setPreferredSize(new Dimension(70, 25));
    bgcolorField.setMinimumSize(new Dimension(60, 25));
    heightField.setMinimumSize(new Dimension(30, 25));
    heightField.setPreferredSize(new Dimension(50, 25));
    jLabel13.setMaximumSize(new Dimension(70, 16));
    jLabel13.setMinimumSize(new Dimension(40, 16));
    jLabel13.setPreferredSize(new Dimension(70, 16));
    jLabel13.setText(Local.getString("Cell spacing"));
    cellspacing.setBorder(border5);
    cellspacing.setPreferredSize(new Dimension(50, 24));
    jLabel17.setMaximumSize(new Dimension(70, 16));
    jLabel17.setMinimumSize(new Dimension(40, 16));
    jLabel17.setPreferredSize(new Dimension(70, 16));
    jLabel17.setText(Local.getString("Vert. align"));
    jLabel25.setMaximumSize(new Dimension(70, 16));
    jLabel25.setMinimumSize(new Dimension(70, 16));
    jLabel25.setPreferredSize(new Dimension(70, 16));
    jLabel25.setText(Local.getString("Fill color"));
    bgColorB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bgColorB_actionPerformed(e);
      }
    });
    bgColorB.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource("resources/icons/color.png")));
    bgColorB.setPreferredSize(new Dimension(25, 25));
    bgColorB.setMinimumSize(new Dimension(25, 25));
    widthField.setPreferredSize(new Dimension(50, 25));
    widthField.setMinimumSize(new Dimension(30, 25));
    flowLayout11.setAlignment(FlowLayout.LEFT);
    jPanel13.setLayout(flowLayout8);
    alignCB.setBackground(new Color(230, 230, 230));
    alignCB.setFont(new java.awt.Font("Dialog", 1, 10));
    alignCB.setPreferredSize(new Dimension(63, 25));
    jLabel14.setText(Local.getString("Cell padding"));
    jLabel14.setPreferredSize(new Dimension(70, 16));
    jLabel14.setMinimumSize(new Dimension(60, 16));
    jLabel14.setMaximumSize(new Dimension(70, 16));
    jPanel9.setLayout(flowLayout9);
    flowLayout9.setAlignment(FlowLayout.LEFT);
    jLabel15.setMaximumSize(new Dimension(70, 16));
    jLabel15.setMinimumSize(new Dimension(40, 16));
    jLabel15.setPreferredSize(new Dimension(70, 16));
    jLabel15.setText(Local.getString("Height"));
    jPanel14.add(jLabel21, null);
    jPanel14.add(tdBgcolorField, null);
    jPanel14.add(tdBgcolorB, null);
    jPanel14.add(component11, null);
    jPanel14.add(tdNowrapChB, null);
    getContentPane().add(panel1);
    panel1.add(buttonsPanel,  BorderLayout.SOUTH);
    buttonsPanel.add(okB, null);
    buttonsPanel.add(cancelB, null);
    panel1.add(jTabbedPane1, BorderLayout.NORTH);
    this.getContentPane().add(headerPanel, BorderLayout.NORTH);
    headerPanel.add(header, null);

    jPanel5.add(jLabel4, null);
    jPanel5.add(tdWidthField, null);
    jPanel5.add(component1, null);
    jPanel5.add(jLabel5, null);
    jPanel5.add(tdHeightField, null);
    tdPanel.add(jPanel5, null);
    tdPanel.add(jPanel6, null);
    tdPanel.add(jPanel7, null);
    jPanel6.add(jLabel6, null);
    jPanel6.add(tdColspan, null);
    jPanel6.add(component2, null);
    jPanel6.add(jLabel7, null);
    jPanel7.add(jLabel20, null);
    jPanel7.add(tdAlignCB, null);
    jPanel7.add(jLabel9, null);
    jPanel7.add(tdValignCB, null);
    jTabbedPane1.add(tdPanel,  Local.getString("Table cell"));
    jTabbedPane1.add(trPanel,  Local.getString("Table row"));
    tdPanel.add(jPanel14, null);
    jPanel11.add(jLabel22, null);
    jPanel11.add(trAlignCB, null);
    jPanel11.add(component12, null);
    jPanel11.add(jLabel16, null);
    jPanel11.add(trValignCB, null);
    jPanel11.add(component7, null);
    jPanel15.add(jLabel23, null);
    jPanel15.add(trBgcolorField, null);
    jPanel15.add(trBgcolorB, null);
    trPanel.add(jPanel11, null);
    trPanel.add(jPanel15, null);
    jPanel6.add(tdRowspan, null);
    Util.setBgcolorField(tdBgcolorField);
    tablePanel.add(jPanel10, null);
    jPanel10.add(jLabel8, null);
    jPanel10.add(widthField, null);
    jPanel10.add(component5, null);
    jPanel10.add(jLabel15, null);
    jPanel10.add(heightField, null);
    tablePanel.add(jPanel13, null);
    jPanel13.add(jLabel14, null);
    jPanel13.add(cellpadding, null);
    jPanel13.add(component3, null);
    jPanel13.add(jLabel13, null);
    jPanel13.add(cellspacing, null);
    tablePanel.add(jPanel9, null);
    jPanel9.add(jLabel12, null);
    jPanel9.add(border, null);
    jPanel9.add(component6, null);
    jPanel9.add(jLabel25, null);
    jPanel9.add(bgcolorField, null);
    jPanel9.add(bgColorB, null);
    tablePanel.add(jPanel12, null);
    jPanel12.add(jLabel24, null);
    jPanel12.add(alignCB, null);
    jPanel12.add(jLabel17, null);
    jPanel12.add(vAlignCB, null);
    jTabbedPane1.add(tablePanel,  Local.getString("Table"));
  }

  void okB_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    CANCELLED = true;
    this.dispose();
  }


  void tdBgcolorB_actionPerformed(ActionEvent e) {
     Color c = JColorChooser.showDialog(this, Local.getString("Table cell background color"), Util.decodeColor(tdBgcolorField.getText()));
     if (c == null) return;
     tdBgcolorField.setText(Util.encodeColor(c));
     Util.setBgcolorField(tdBgcolorField);
  }

  void trBgcolorB_actionPerformed(ActionEvent e) {
     Color c = JColorChooser.showDialog(this, Local.getString("Table row background color"), Util.decodeColor(trBgcolorField.getText()));
     if (c == null) return;
     trBgcolorField.setText(Util.encodeColor(c));
     Util.setBgcolorField(trBgcolorField);
  }

  void bgColorB_actionPerformed(ActionEvent e) {
    Color c = JColorChooser.showDialog(this, Local.getString("Table background color"), Util.decodeColor(bgcolorField.getText()));
     if (c == null) return;
     bgcolorField.setText(Util.encodeColor(c));
     Util.setBgcolorField(bgcolorField);
  }

}