package de.schaeffer.compiere.forms;

/*
 * #%L
 * de.metas.banking.swingui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MQuery;
import org.compiere.process.DocAction;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.logging.LogManager;
import de.schaeffer.compiere.constants.Constants;
import de.schaeffer.compiere.tools.TableHelper;

public class VoidBankstatement extends CPanel implements FormPanel, ActionListener {

	private Logger log = LogManager.getLogger(VoidBankstatement.class);

	/** Combo Boxes */
	private JComboBox bankComboBox = null;
	private JComboBox bankStatementComboBox = null;
	
	/** Checkboxes */
	private JCheckBox loadAllData = null;

	/** Panels */
	private JPanel topPanel = null;
	private JPanel bottomPanel = null;
	private JPanel contentPane = null;

	/** DataTables */
	private JTable bankStatementLineTable = null;

	/** List with all bank statements (ids) of the selected bank */
	private List<Integer> bankStatementIDList = null;
	
	/** List with all banks (ids) */
	private List<Integer> bankIDList = null;

	/** Buttons */
	private JButton createStatementButton = null;
	private JButton cancelButton = null;


	private Trx trx;

	private FormFrame frame = null;

	private JPanel getBottomPanel() {
		if (bottomPanel == null) {

			bottomPanel = new JPanel();
			bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			bottomPanel.add(getCancelButton());
			bottomPanel.add(getCreateStatementButton());

		}
		return bottomPanel;
	}

	private JButton getCreateStatementButton() {
		if (createStatementButton==null) {
			createStatementButton = new JButton("create Statement");
			createStatementButton.addActionListener(this);
		}
		return createStatementButton;
	}

	private JButton getCancelButton() {
		if (cancelButton==null) {
			cancelButton = new JButton("cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}
	
	private Component getLoadAllDataCheckBox() {
		if (loadAllData == null) {
			loadAllData = new JCheckBox("show all data");
			loadAllData.addActionListener(this);
		}
		return loadAllData;
	}

	private JComboBox getBankComboBox() {
		if (bankComboBox==null) {
			List<Object[]> list = searchBank();
	
			bankIDList = new ArrayList<Integer>();
			Object[] bankaccounts = new Object[list.size()];
	
			for (int i = 0; i < list.size(); i++) {
				bankIDList.add(Integer.valueOf(list.get(i)[0].toString()));
				bankaccounts[i] = list.get(i)[1] ;
			}
			bankComboBox = new JComboBox(bankaccounts);
			bankComboBox.addActionListener(this);
		}
		return bankComboBox;
	}
	
	private Component getBankStatementComboBox(int bankId) {
		List<Object[]> list = searchBankStatemens(bankId);

		bankStatementIDList = new ArrayList<Integer>();
		Object[] statements = new Object[list.size()];

		for (int i = 0; i < list.size(); i++) {
			bankStatementIDList.add(Integer.valueOf(list.get(i)[0].toString()));
			statements[i] = list.get(i)[1] + " - " + list.get(i)[2];
		}
		bankStatementComboBox = new JComboBox(statements);
		bankStatementComboBox.addActionListener(this);
		return bankStatementComboBox;
	}


	private JPanel getTopPanel() {

		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());

		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 0;
		g1.gridy = 0;
		g1.insets = new Insets(0,0,0,5);
		GridBagConstraints g2 = new GridBagConstraints();
		g2.gridx = 1;
		g2.gridy = 0;
		g2.insets = new Insets(0,0,0,5);
		GridBagConstraints g3 = new GridBagConstraints();
		g3.gridx = 2;
		g3.gridy = 0;
		g3.insets = new Insets(0,0,0,5);
		GridBagConstraints g4 = new GridBagConstraints();
		g4.gridx = 3;
		g4.gridy = 0;
		g4.insets = new Insets(0,0,0,5);
		GridBagConstraints g5 = new GridBagConstraints();
		g5.gridx = 4;
		g5.gridy = 0;
		
		
		topPanel.add(getLoadAllDataCheckBox(), g5);
		topPanel.add(new JLabel("Bank"), g1);
		topPanel.add(getBankComboBox(), g2);
		int bankId = getSelectedBankId();
		topPanel.add(new JLabel("Bankausz�ge"), g3);
		topPanel.add(getBankStatementComboBox(getSelectedBankId()), g4);

		return topPanel;

	}


	/**
	 * @return id of the current selected bank
	 */
	private int getSelectedBankId() {
		if (bankComboBox==null) {
			return -1;
		}
		int selectedIndex = bankComboBox.getSelectedIndex();
		if (selectedIndex>=bankIDList.size()){
			return -1;
		}
		if (selectedIndex == -1) {
			return selectedIndex;
		}
		return bankIDList.get(selectedIndex).intValue();
	}

	/**
	 * @return id of the current selected bank statement
	 */
	private int getSelectedBankStatementId() {
		if (bankStatementComboBox==null) {
			return -1;
		}
		int selectedIndex = bankStatementComboBox.getSelectedIndex();
		if (selectedIndex>=bankStatementIDList.size()){
			return -1;
		}
		if (selectedIndex == -1) {
			return selectedIndex;
		}
		return bankStatementIDList.get(selectedIndex).intValue();
	}
	
	/**
	 * Returns the selected bankstatement lines.
	 * @return
	 */
	private MBankStatementLine[] getSelectedStatementLines() {
		if (bankStatementLineTable == null) {
			return null;
		}
		VBStTableModel tm = (VBStTableModel)bankStatementLineTable.getModel();
		Vector<MBankStatementLine> lines = new Vector<MBankStatementLine>();
		Integer[] selectedLineIds = tm.getSelectedLineIds();
		if (selectedLineIds.length==0) {
			return null;
		}
		for (Integer i : selectedLineIds) {
			MBankStatementLine line = new MBankStatementLine(Env.getCtx(), i.intValue(), null);
			lines.add(line);
		}
		MBankStatementLine[] a = new MBankStatementLine[lines.size()];
		lines.toArray(a);
		return a;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bankComboBox || e.getSource() == loadAllData) {
			reDo(false);
		} else if (e.getSource()==bankStatementComboBox) {
			reDo(true);
		} else if (e.getSource()==createStatementButton) {
			createVoidStatement();
		} else if (e.getSource()==cancelButton) {
			this.dispose();
		}
	}


	/**
	 * Create a bank statement with a copy of all checked statement lines but negated amounts.
	 */
	private void createVoidStatement() {
		
		final Trx trx = Trx.get(Trx.createTrxName("CheckAllocation_"), true); 
    	trx.start();
    	String trxName = trx.getTrxName();
    	
    	final MBankStatement origStatement = new MBankStatement(Env.getCtx(), getSelectedBankStatementId(), null);
		final MBankStatement voidStatementPO = new MBankStatement(Env.getCtx(), 0, trxName);
		final I_C_BankStatement voidStatement = InterfaceWrapperHelper.create(voidStatementPO, I_C_BankStatement.class);
		
		voidStatement.setStatementDate(origStatement.getStatementDate());
		voidStatement.setC_BP_BankAccount_ID(origStatement.getC_BP_BankAccount_ID());
		voidStatement.setName(origStatement.getName() + " - STORNO");
		voidStatement.setDocumentNo(origStatement.getDocumentNo() + "-V");
		voidStatement.setAD_Org_ID(origStatement.getAD_Org_ID());
		voidStatement.setBeginningBalance(Env.ZERO);
		voidStatement.setEndingBalance(Env.ZERO);
		voidStatementPO.saveEx();
		
		origStatement.setDescription(origStatement.getDescription() + " | (partly) voided **");
		if (!origStatement.save(trxName)) {
			trx.rollback();
			trx.close();
			return;
		}
		
		MBankStatementLine[] lines = getSelectedStatementLines();
		if (lines == null) {
			trx.rollback();
			trx.close();
			return;
		}
		
		for(final MBankStatementLine linePO : lines) {
			
			final I_C_BankStatementLine line = InterfaceWrapperHelper.create(linePO, I_C_BankStatementLine.class);
			
			final MBankStatementLine voidLinePO = new MBankStatementLine(Env.getCtx(), 0, trxName);
			final I_C_BankStatementLine voidLine = InterfaceWrapperHelper.create(voidLinePO, I_C_BankStatementLine.class);
			
			voidLine.setC_BankStatement_ID(voidStatement.getC_BankStatement_ID());
			
			voidLine.setStmtAmt(line.getStmtAmt().negate());
			voidLine.setTrxAmt(line.getTrxAmt().negate());
			voidLine.setOverUnderAmt(line.getOverUnderAmt().negate());
			voidLine.setDiscountAmt(line.getDiscountAmt().negate());
			voidLine.setChargeAmt(line.getChargeAmt().negate());
			voidLine.setInterestAmt(line.getInterestAmt().negate());
			
			voidLine.setLine(line.getLine());
			voidLine.setDescription("voided");
			voidLine.setIsReversal(true);
			voidLine.setDateAcct(line.getDateAcct()); 
			voidLine.setC_Currency_ID(line.getC_Currency_ID());
			if (line.getC_Charge_ID()>0) {
				voidLine.setC_Charge_ID(line.getC_Charge_ID());
			}
			voidLine.setReferenceNo(line.getReferenceNo());
			voidLine.setStatementLineDate(line.getStatementLineDate());
			voidLine.setC_BPartner_ID(line.getC_BPartner_ID());
			voidLine.setC_Payment_ID(line.getC_Payment_ID());
			voidLine.setC_Invoice_ID(0);
			voidLine.setProcessed(false);
			voidLine.setIsMultiplePayment(false);
			voidLine.setIsMultiplePaymentOrInvoice(false);
			voidLine.setIsOverUnderPayment(line.isOverUnderPayment());	
			
			voidLinePO.saveEx();
		}

		voidStatementPO.saveEx();
		
		trx.commit();
		trx.close();
		
		voidStatementPO.set_TrxName(null);
		voidStatement.setDocAction(DocAction.ACTION_Complete);
		if (voidStatementPO.processIt(DocAction.ACTION_Complete)) {
			voidStatement.setDocAction(DocAction.ACTION_Close);
			voidStatementPO.processIt(DocAction.ACTION_Close);
			voidStatement.setDocStatus(MBankStatement.DOCSTATUS_Reversed);
			voidStatement.setDocAction(MBankStatement.DOCACTION_None);
			origStatement.setDocStatus(MBankStatement.DOCSTATUS_Reversed);
			origStatement.set_TrxName(null);
			origStatement.save();
		}
		voidStatementPO.saveEx();
		openCreatedStatement(voidStatementPO);
		
	}

	/**
	 * Open the Bank Statement Window (filtered), show only the new created
	 * bank statement.
	 */
	private void openCreatedStatement(MBankStatement statement) {
		String whereString = " C_BankStatement_ID=" + statement.get_ID();
		final AWindow poFrame = new AWindow();
		final MQuery query = new MQuery("C_BankStatement");
		query.addRestriction(whereString);
		poFrame.initWindow(Constants.BANKSTATEMENT_WINDOW_ID, query);
		poFrame.pack();
		AEnv.showCenterScreen(poFrame);
		AEnv.addToWindowManager(poFrame);
		
	}


	/**
	 * Requery data and repaint frame.
	 * @param linesOnly if true don't update the dropdown fields.
	 */
	private void reDo(boolean linesOnly) {
		contentPane.removeAll();
		if (!linesOnly) {
			contentPane.add(getTopPanel(), BorderLayout.NORTH);
			contentPane.add(getBottomPanel(), BorderLayout.SOUTH);
		} else {
			contentPane.add(topPanel, BorderLayout.NORTH);
			contentPane.add(bottomPanel, BorderLayout.SOUTH);
		}
		
		JScrollPane statementLineTablePanel = new JScrollPane(getStatementLineTable());
		statementLineTablePanel.setPreferredSize(new Dimension(950, 650));
		JPanel centerPanel = new JPanel();
		centerPanel.add(statementLineTablePanel);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.validate();
		validate();
	}



	/**
	 * Returns a JTable with all bankstatement lines of the selected bank statement.
	 * @return
	 */
	private JTable getStatementLineTable() {
		List<Object[]> lines = searchBankStatementLines(getSelectedBankStatementId());
		
		// fillContent
		Object[][] content = new Object[lines.size()][5];
		
		for (int i = 0; i < lines.size(); i++) {
			content[i][0] = lines.get(i)[1];
			content[i][1] = lines.get(i)[2];
			content[i][2] = lines.get(i)[3];
			content[i][3] = Boolean.FALSE;
			content[i][4] = lines.get(i)[0];
		}

		VBStTableModel tm = new VBStTableModel(content);
		bankStatementLineTable = new JTable(tm);
		TableHelper.initColumnSizes(bankStatementLineTable);
		bankStatementLineTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		bankStatementLineTable.setIntercellSpacing(new Dimension(5, 0));
		return bankStatementLineTable;
	}

	/**
	 * Returns all banks of the actual client with existing bank statements.
	 * @return
	 */
	private List<Object[]> searchBank() {

		String sql = "select b.c_bank_id, b.name "
				+ "from c_bank b "
				+ "where b.ad_client_id = " + Env.getAD_Client_ID(Env.getCtx())
				+ " and exists (select * from c_bankstatement where "
				+ " c_bp_bankaccount_id in (select c_bp_bankaccount_id from c_bp_bankaccount"
				+ " where ad_client_id=1000000 and c_bp_bankaccount.c_bank_id = b.c_bank_id))"
				+ " order by b.name";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Object[]> accountList = new ArrayList<Object[]>();

		try {

			pstmt = DB.prepareStatement(sql, null);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Object[] a = { rs.getInt("c_bank_id"),
						rs.getString("name") };
				accountList.add(a);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, pstmt);
		}

		return accountList;

	}
	
	/**
	 * Returns a list containing all bankstatement lines (id, no, description, statement amount) 
	 * of a given bankstatement.
	 * @param bankStatementId
	 * @return
	 */
	private List<Object[]> searchBankStatementLines(int bankStatementId) {
		MBankStatement statement = new MBankStatement(Env.getCtx(), bankStatementId, null);
		MBankStatementLine[] lines = statement.getLines(false);
		List<Object[]> statementList = new ArrayList<Object[]>();
		
		for (int i = 0; i < lines.length; i++) {
			Object[] a = {lines[i].get_ID(),
					lines[i].getLine(), lines[i].getDescription(), lines[i].getStmtAmt()};
			statementList.add(a);
		}
		return statementList;
	}
	
	/**
	 * Returns a list containing all bankstatements (id, name, statementdate) of a given bank.
	 * If the loadAllData checkbox is checked the created date is ignored, otherwise the list
	 * contains bankstatements of the last 180 days only.
	 * @param bankId
	 * @return
	 */
	private List<Object[]> searchBankStatemens(int bankId) {
		//TODO - nur active / posted??
		String sql = "select st.c_bankstatement_id, st.name, st.statementdate "
			+ "from c_bankstatement st "
			+ "inner join c_bp_bankaccount a on (a.c_bp_bankaccount_id = st.c_bp_bankaccount_id) "
			+ "inner join c_bank b on (b.c_bank_id = a.c_bank_id) "
			+ "where a.ad_client_id = " +  Env.getAD_Client_ID(Env.getCtx())
			+ "and b.c_bank_id=? and st.docstatus in ('CO', 'CL') and st.isactive='Y' ";
			if (loadAllData!=null && !loadAllData.isSelected()) {
				sql += "and st.created>=now()-90 ";
			}
			sql += "order by st.statementdate";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		List<Object[]> statementList = new ArrayList<Object[]>();
	
		try {
	
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, bankId);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
	
				Object[] a = { rs.getInt("c_bankstatement_id"),
						rs.getString("name"), DateFormat.getDateInstance().format(rs.getDate("statementdate")) };
				statementList.add(a);
	
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, pstmt);
		}
	
		return statementList;
	}

	


	/**
	 * TableModel for replenishment tables.
	 * 
	 * @author Karsten Thiemann, kt@schaeffer-ag.de
	 */
	private class VBStTableModel extends AbstractTableModel {


		/** column names */
		private String[] columnNames = {"Line", "Description", "StatementAmount", "Void", };

		/** column types */
		private Class[] columnType = {Integer.class, String.class, Double.class, Boolean.class, };

		private Object[][] data;
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		@Override
		public int getRowCount() {
			return data.length;
		}


		public Integer[] getSelectedLineIds() {
			Vector<Integer> indices = new Vector<Integer>();
			for (int i = 0; i < data.length; i++) {
				if ((Boolean)data[i][3]) {
					indices.add((Integer) data[i][4]);
				}
			}
			Integer[] a = new Integer[indices.size()];
			return indices.toArray(a);
		}


		public VBStTableModel(Object [][] data) {
			this.data = data;
		}
		

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int c) {
			return columnType[c];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == 3) {
				return true;
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex >= data.length || rowIndex < 0) {
				return null;
			}
			return data[rowIndex][columnIndex];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			if (value == null) {
				return;
			}
			if (col==3) {
				data[row][col] = value;
			} else return;
			fireTableCellUpdated(row, col);
		}

	}
	

	@Override
	public void dispose() {
		if (frame != null) {
			frame.dispose();
		}
		frame = null;
	}

	@Override
	public void init(int WindowNo, FormFrame frame) {
		this.frame  = frame;
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		frame.setSize(1000, 800);
		setLocation(100, 100);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(contentPane,
				BorderLayout.CENTER);
		
		reDo(false);
		frame.pack();
	}
}
