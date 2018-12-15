/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.search;

import java.awt.Frame;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MCashLine;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.i18n.Msg;
import de.metas.util.StringUtils;

/**
 *  Info Payment
 *
 *  @author Jorg Janke
 *  @version  $Id: InfoCashLine.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>FR [ 1976044 ] Info Cash Line: search by Charge
 */
public class InfoCashLine extends Info
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3315036454469996930L;

	/**
	 *  Detail Protected Contructor
	 *  @param frame parent frame
	 *  @param modal modal
	 *  @param WindowNo window no
	 *  @param value query value
	 *  @param multiSelection multiple selections
	 *  @param whereClause whwre clause
	 */
	protected InfoCashLine(Frame frame, boolean modal, int WindowNo, String value,
		boolean multiSelection, String whereClause)
	{
		super (frame, modal, WindowNo, "cl", "C_CashLine_ID", multiSelection, whereClause);
		log.info( "InfoCashLine");
		setTitle(Msg.getMsg(Env.getCtx(), "InfoCashLine"));
		//
		try
		{
			statInit();
			p_loadedOK = initInfo ();
		}
		catch (Exception e)
		{
			return;
		}
		//
		int no = p_table.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		if (value != null && value.length() > 0)
		{
			fName.setValue(value);
			executeQueryOnInit();
		}
		//
		getWindow().pack();
		//	Focus
		fName.requestFocus();
	}   //  InfoCashLine


	//  Static Info
	private CLabel lName = new CLabel(Msg.translate(Env.getCtx(), "Name"));
	private CTextField fName = new CTextField(10);
	private CLabel lCashBook_ID = new CLabel(Msg.translate(Env.getCtx(), "C_CashBook_ID"));
	private VLookup fCashBook_ID;
//	private CLabel lOrg_ID = new CLabel(Msg.translate(Env.getCtx(), "AD_Org_ID"));
//	private VLookup fOrg_ID;
	private CLabel lInvoice_ID = new CLabel(Msg.translate(Env.getCtx(), "C_Invoice_ID"));
	private VLookup fInvoice_ID;
	private CLabel lCharge_ID = new CLabel(Msg.translate(Env.getCtx(), "C_Charge_ID"));
	private VLookup fCharge_ID;
	private CLabel lBankAccount_ID = new CLabel(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"));
	private VLookup fBankAccount_ID;
	private CCheckBox cbAbsolute = new CCheckBox (Msg.translate(Env.getCtx(), "AbsoluteAmt"));
	//
	private CLabel lDateFrom = new CLabel(Msg.translate(Env.getCtx(), "StatementDate"));
	private VDate fDateFrom = new VDate("DateFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateFrom"));
	private CLabel lDateTo = new CLabel("-");
	private VDate fDateTo = new VDate("DateTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateTo"));
	private CLabel lAmtFrom = new CLabel(Msg.translate(Env.getCtx(), "Amount"));
	private VNumber fAmtFrom = new VNumber("AmtFrom", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtFrom"));
	private CLabel lAmtTo = new CLabel("-");
	private VNumber fAmtTo = new VNumber("AmtTo", false, false, true, DisplayType.Amount, Msg.translate(Env.getCtx(), "AmtTo"));

	/**  Array of Column Info    */
	private static final Info_Column[] s_cashLayout = {
		new Info_Column(" ", "cl.C_CashLine_ID", IDColumn.class),
		new Info_Column(Msg.translate(Env.getCtx(), "C_CashBook_ID"),
			"(SELECT cb.Name FROM C_CashBook cb WHERE cb.C_CashBook_ID=c.C_CashBook_ID)", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Name"),
			"c.Name", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "StatementDate"),
			"c.StatementDate", Timestamp.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Line"),
			"cl.Line", Integer.class),
	//	new Info_Column(Msg.translate(Env.getCtx(), "C_Currency_ID"),
	//		"(SELECT ISO_Code FROM C_Currency c WHERE c.C_Currency_ID=cl.C_Currency_ID)", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Amount"),
			"cl.Amount",  BigDecimal.class, true, true, null),
		//
		new Info_Column(Msg.translate(Env.getCtx(), "C_Invoice_ID"),
			"(SELECT i.DocumentNo||'_'||" + DB.TO_CHAR("i.DateInvoiced",DisplayType.Date,Env.getAD_Language(Env.getCtx()))
				+ "||'_'||" + DB.TO_CHAR("i.GrandTotal",DisplayType.Amount,Env.getAD_Language(Env.getCtx()))
				+ " FROM C_Invoice i WHERE i.C_Invoice_ID=cl.C_Invoice_ID)", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"),
			"(SELECT b.Name||' '||ba.AccountNo FROM C_Bank b, C_BP_BankAccount ba WHERE b.C_Bank_ID=ba.C_Bank_ID AND ba.C_BP_BankAccount_ID=cl.C_BP_BankAccount_ID)", String.class),
		new Info_Column(Msg.translate(Env.getCtx(), "C_Charge_ID"),
			"(SELECT ca.Name FROM C_Charge ca WHERE ca.C_Charge_ID=cl.C_Charge_ID)", String.class),
		//
		new Info_Column(Msg.translate(Env.getCtx(), "DiscountAmt"),
			"cl.DiscountAmt",  BigDecimal.class),
		new Info_Column(Msg.translate(Env.getCtx(), "WriteOffAmt"),
			"cl.WriteOffAmt",  BigDecimal.class),
		new Info_Column(Msg.translate(Env.getCtx(), "Description"),
			"cl.Description", String.class)
	};

	/**
	 *	Static Setup - add fields to parameterPanel
	 *  @throws Exception if Lookups cannot be created
	 */
	private void statInit() throws Exception
	{
		final int p_WindowNo = getWindowNo();

		lName.setLabelFor(fName);
		fName.setBackground(AdempierePLAF.getInfoBackground());
		fName.addActionListener(this);
		//
		//	5249 - C_Cash.C_CashBook_ID
		fCashBook_ID = new VLookup("C_CashBook_ID", false, false, true,
			MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5249, DisplayType.TableDir));
		lCashBook_ID.setLabelFor(fCashBook_ID);
		fCashBook_ID.setBackground(AdempierePLAF.getInfoBackground());
		//	5354 - C_CashLine.C_Invoice_ID
		fInvoice_ID = new VLookup("C_Invoice_ID", false, false, true,
			MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5354, DisplayType.Search));
		lInvoice_ID.setLabelFor(fInvoice_ID);
		fInvoice_ID.setBackground(AdempierePLAF.getInfoBackground());
		//	5295 - C_CashLine.C_BP_BankAccount_ID
		fBankAccount_ID = new VLookup("C_BP_BankAccount_ID", false, false, true,
			MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5295, DisplayType.TableDir));
		lBankAccount_ID.setLabelFor(fBankAccount_ID);
		fBankAccount_ID.setBackground(AdempierePLAF.getInfoBackground());
		//	5296 - C_CashLine.C_Charge_ID
		fCharge_ID = new VLookup(MCashLine.COLUMNNAME_C_Charge_ID, false, false, true,
				MLookupFactory.get(Env.getCtx(), p_WindowNo, 0,
						MColumn.getColumn_ID(MCashLine.Table_Name, MCashLine.COLUMNNAME_C_Charge_ID),
						DisplayType.TableDir) );
		//	5291 - C_CashLine.C_Cash_ID
		//
		lDateFrom.setLabelFor(fDateFrom);
		fDateFrom.setBackground(AdempierePLAF.getInfoBackground());
		fDateFrom.setToolTipText(Msg.translate(Env.getCtx(), "DateFrom"));
		lDateTo.setLabelFor(fDateTo);
		fDateTo.setBackground(AdempierePLAF.getInfoBackground());
		fDateTo.setToolTipText(Msg.translate(Env.getCtx(), "DateTo"));
		lAmtFrom.setLabelFor(fAmtFrom);
		fAmtFrom.setBackground(AdempierePLAF.getInfoBackground());
		fAmtFrom.setToolTipText(Msg.translate(Env.getCtx(), "AmtFrom"));
		lAmtTo.setLabelFor(fAmtTo);
		fAmtTo.setBackground(AdempierePLAF.getInfoBackground());
		fAmtTo.setToolTipText(Msg.translate(Env.getCtx(), "AmtTo"));
		//
		parameterPanel.setLayout(new ALayout());
		//  First Row
		parameterPanel.add(lCashBook_ID, new ALayoutConstraint(0,0));
		parameterPanel.add(fCashBook_ID, null);
		parameterPanel.add(lName, null);
		parameterPanel.add(fName, null);
		parameterPanel.add(cbAbsolute, new ALayoutConstraint(0,5));
		//  2nd Row
		parameterPanel.add(lInvoice_ID, new ALayoutConstraint(1,0));
		parameterPanel.add(fInvoice_ID, null);
		parameterPanel.add(lDateFrom, null);
		parameterPanel.add(fDateFrom, null);
		parameterPanel.add(lDateTo, null);
		parameterPanel.add(fDateTo, null);
		//  3rd Row
		parameterPanel.add(lBankAccount_ID, new ALayoutConstraint(2,0));
		parameterPanel.add(fBankAccount_ID, null);
		parameterPanel.add(lAmtFrom, null);
		parameterPanel.add(fAmtFrom, null);
		parameterPanel.add(lAmtTo, null);
		parameterPanel.add(fAmtTo, null);
		// 4th Row
		parameterPanel.add(lCharge_ID, new ALayoutConstraint(3,0));
		parameterPanel.add(fCharge_ID, null);
	}	//	statInit

	/**
	 *	General Init
	 *	@return true, if success
	 */
	private boolean initInfo ()
	{
		//  prepare table
		StringBuffer where = new StringBuffer("cl.IsActive='Y'");
		if (p_whereClause.length() > 0)
			where.append(" AND ").append(StringUtils.replace(p_whereClause, "C_CashLine.", "cl."));
		prepareTable (s_cashLayout,
			"C_CashLine cl INNER JOIN C_Cash c ON (cl.C_Cash_ID=c.C_Cash_ID)",
			where.toString(),
			"2,3,cl.Line");

		return true;
	}	//	initInfo


	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return sql where clause
	 */
	@Override
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();
		if (fName.getText().length() > 0)
			sql.append(" AND UPPER(c.Name) LIKE ?");
		//
		if (fCashBook_ID.getValue() != null)
			sql.append(" AND c.C_CashBook_ID=?");
		//
		if (fInvoice_ID.getValue() != null)
			sql.append(" AND cl.C_Invoice_ID=?");
		//
		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Timestamp from = fDateFrom.getValue();
			Timestamp to = fDateTo.getValue();
			if (from == null && to != null)
				sql.append(" AND TRUNC(c.StatementDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(c.StatementDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(c.StatementDate) BETWEEN ? AND ?");
		}
		//
		if (fAmtFrom.getValue() != null || fAmtTo.getValue() != null)
		{
			BigDecimal from = (BigDecimal)fAmtFrom.getValue();
			BigDecimal to = (BigDecimal)fAmtTo.getValue();
			if (cbAbsolute.isSelected())
				sql.append(" AND ABS(cl.Amount)");
			else
				sql.append(" AND cl.Amount");
			//
			if (from == null && to != null)
				sql.append(" <=?");
			else if (from != null && to == null)
				sql.append(" >=?");
			else if (from != null && to != null)
			{
				if (from.compareTo(to) == 0)
					sql.append(" =?");
				else
					sql.append(" BETWEEN ? AND ?");
			}
		}
		if (fCharge_ID.getValue() != null) {
			sql.append(" AND cl.").append(MCashLine.COLUMNNAME_C_Charge_ID).append("=?");
		}

		log.debug(sql.toString());
		return sql.toString();
	}	//	getSQLWhere

	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	@Override
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		if (fName.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fName));
		//
		if (fCashBook_ID.getValue() != null)
		{
			Integer cb = (Integer)fCashBook_ID.getValue();
			pstmt.setInt(index++, cb.intValue());
			log.debug("CashBook=" + cb);
		}
		//
		if (fInvoice_ID.getValue() != null)
		{
			Integer i = (Integer)fInvoice_ID.getValue();
			pstmt.setInt(index++, i.intValue());
			log.debug("Invoice=" + i);
		}
		//
		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Timestamp from = fDateFrom.getValue();
			Timestamp to = fDateTo.getValue();
			log.debug("Date From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
		//
		if (fAmtFrom.getValue() != null || fAmtTo.getValue() != null)
		{
			BigDecimal from = (BigDecimal)fAmtFrom.getValue();
			BigDecimal to = (BigDecimal)fAmtTo.getValue();
			if (cbAbsolute.isSelected())
			{
				if (from != null)
					from = from.abs();
				if (to != null)
					to = to.abs();
			}
			log.debug("Amt From=" + from + ", To=" + to + ", Absolute=" + cbAbsolute.isSelected());
			if (from == null && to != null)
				pstmt.setBigDecimal(index++, to);
			else if (from != null && to == null)
				pstmt.setBigDecimal(index++, from);
			else if (from != null && to != null)
			{
				if (from.compareTo(to) == 0)
					pstmt.setBigDecimal(index++, from);
				else
				{
					pstmt.setBigDecimal(index++, from);
					pstmt.setBigDecimal(index++, to);
				}
			}
		}
		if (fCharge_ID.getValue() != null) {
			Integer i = (Integer)fCharge_ID.getValue();
			pstmt.setInt(index++, i.intValue());
			log.debug("Charge=" + i);
		}
	}   //  setParameters

	/**
	 *  Get SQL WHERE parameter
	 *  @param f field
	 *  @return Upper case text with % at the end
	 */
	private String getSQLText (CTextField f)
	{
		String s = f.getText().toUpperCase();
		if (!s.endsWith("%"))
			s += "%";
		log.debug( "String=" + s);
		return s;
	}   //  getSQLText
}   //  InfoCashLine
