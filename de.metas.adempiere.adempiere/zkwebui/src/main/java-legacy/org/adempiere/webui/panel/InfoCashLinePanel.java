/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.panel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Datebox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

/**
* Based on InfoCashLine written by Jorg Janke
*  
* @author 	Niraj Sohun
* 			Aug 03, 2007
* 
* Zk Port
* @author Elaine
* @version	InfoCashLine.java Adempiere Swing UI 3.4.1 
*/

public class InfoCashLinePanel extends InfoPanel implements ValueChangeListener, EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3042929765363185887L;
	private Textbox fName = new Textbox();
	private Textbox fAmtTo = new Textbox();
	private Textbox fAmtFrom = new Textbox();
	
	private WEditor fCashBook_ID;
	private WEditor fInvoice_ID;
	private WEditor fBankAccount_ID;
	
	private Datebox fDateFrom = new Datebox();
	private Datebox fDateTo = new Datebox();

	private Checkbox cbAbsolute = new Checkbox();

	private Label lName = new Label(Msg.translate(Env.getCtx(), "Name"));
	private Label lDateFrom = new Label(Msg.translate(Env.getCtx(), "StatementDate"));
	private Label lDateTo = new Label("-");
	private Label lAmtFrom = new Label(Msg.translate(Env.getCtx(), "Amount")); 
	private Label lAmtTo = new Label("-");
	private Borderlayout layout;
	private Vbox southBody;

	/**  Array of Column Info    */
	private static final ColumnInfo[] s_cashLayout = {
		new ColumnInfo(" ", "cl.C_CashLine_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_CashBook_ID"),
			"(SELECT cb.Name FROM C_CashBook cb WHERE cb.C_CashBook_ID=c.C_CashBook_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"),
			"c.Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "StatementDate"),
			"c.StatementDate", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Line"),
			"cl.Line", Integer.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Amount"),
			"cl.Amount",  BigDecimal.class, true, true, null),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_Invoice_ID"),
			"(SELECT i.DocumentNo||'_'||" + DB.TO_CHAR("i.DateInvoiced",DisplayType.Date,Env.getAD_Language(Env.getCtx()))
				+ "||'_'||" + DB.TO_CHAR("i.GrandTotal",DisplayType.Amount,Env.getAD_Language(Env.getCtx()))
				+ " FROM C_Invoice i WHERE i.C_Invoice_ID=cl.C_Invoice_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"),
			"(SELECT b.Name||' '||ba.AccountNo FROM C_Bank b, C_BP_BankAccount ba WHERE b.C_Bank_ID=ba.C_Bank_ID AND ba.C_BP_BankAccount_ID=cl.C_BP_BankAccount_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_Charge_ID"),
			"(SELECT ca.Name FROM C_Charge ca WHERE ca.C_Charge_ID=cl.C_Charge_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "DiscountAmt"),
			"cl.DiscountAmt",  BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "WriteOffAmt"),
			"cl.WriteOffAmt",  BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Description"),
			"cl.Description", String.class)
	};

	/**
	 *  Detail Protected Constructor
	 *  
	 *  @param WindowNo window no
	 *  @param value query value
	 *  @param multiSelection multiple selections
	 *  @param whereClause where clause
	 */
	protected InfoCashLinePanel(	int WindowNo, String value,
									boolean multiSelection, String whereClause)
	{
		this(WindowNo, value, multiSelection, whereClause, true);
	}
	
	/**
	 *  Detail Protected Constructor
	 *
	 *  @param WindowNo window no
	 *  @param value query value
	 *  @param multiSelection multiple selections
	 *  @param whereClause where clause
	 */
	protected InfoCashLinePanel(	int WindowNo, String value,
									boolean multiSelection, String whereClause, boolean lookup)
	{
		super (WindowNo, "cl", "C_CashLine_ID", multiSelection, whereClause, lookup);
		log.info( "InfoCashLine");
		setTitle(Msg.getMsg(Env.getCtx(), "InfoCashLine"));

		try
		{
			statInit();
			p_loadedOK = initInfo ();
		}
		catch (Exception e)
		{
			return;
		}

		int no = contentPanel.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		
		if (value != null && value.length() > 0)
		{
			fName .setValue(value);
			executeQuery();
		}
	} // InfoCashLinePanel
	
	/**
	 *	Static Setup - add fields to parameterPanel
	 *  @throws Exception if Lookups cannot be created
	 */
	
	private void statInit() throws Exception
	{
		fName.setWidth("180px");
		fDateFrom.setWidth("165px");
		fDateTo.setWidth("165px");
		fAmtFrom.setWidth("180px");
		fAmtTo.setWidth("180px");
		
		fName.addEventListener(Events.ON_CHANGE, this);
		
		// 5249 - C_Cash.C_CashBook_ID
		fCashBook_ID = new WSearchEditor(
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5249, DisplayType.TableDir), 
				Msg.translate(Env.getCtx(), "C_CashBook_ID"), "", false, false, true);
		fCashBook_ID.addValueChangeListener(this);
		
		// 5354 - C_CashLine.C_Invoice_ID
		fInvoice_ID = new WSearchEditor(
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5354, DisplayType.Search), 
				Msg.translate(Env.getCtx(), "C_Invoice_ID"), "", false, false, true);
		fInvoice_ID.addValueChangeListener(this);
		
		//	5295 - C_CashLine.C_BP_BankAccount_ID
		fBankAccount_ID = new WSearchEditor(
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 5295, DisplayType.TableDir), 
				Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"), "", false, false, true);
		fBankAccount_ID.addValueChangeListener(this);
		
		//	5296 - C_CashLine.C_Charge_ID
		//	5291 - C_CashLine.C_Cash_ID

		cbAbsolute.setLabel(Msg.translate(Env.getCtx(), "AbsoluteAmt"));
		cbAbsolute.addEventListener(Events.ON_CHECK, this);
		
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(fCashBook_ID.getLabel().rightAlign());
		row.appendChild(fCashBook_ID.getComponent());
		row.appendChild(lName.rightAlign());
		row.appendChild(fName);
		row.appendChild(cbAbsolute);
		
		row = new Row();
		row.setSpans("1, 1, 1, 2");
		rows.appendChild(row);
		row.appendChild(fInvoice_ID.getLabel().rightAlign());
		row.appendChild(fInvoice_ID.getComponent());
		row.appendChild(lDateFrom.rightAlign());
		Hbox hbox = new Hbox();
		hbox.appendChild(fDateFrom);
		hbox.appendChild(lDateTo);		
		hbox.appendChild(fDateTo);
		row.appendChild(hbox);
		
		row = new Row();
		row.setSpans("1, 1, 1, 2");
		rows.appendChild(row);
		row.appendChild(fBankAccount_ID.getLabel().rightAlign());
		row.appendChild(fBankAccount_ID.getComponent());
		row.appendChild(lAmtFrom.rightAlign());
		hbox = new Hbox();
		hbox.appendChild(fAmtFrom);
		hbox.appendChild(lAmtTo);
		hbox.appendChild(fAmtTo);
		row.appendChild(hbox);
		
		layout = new Borderlayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        if (!isLookup())
        {
        	layout.setStyle("position: absolute");
        }
        this.appendChild(layout);

        North north = new North();
        layout.appendChild(north);
		north.appendChild(grid);

        Center center = new Center();
		layout.appendChild(center);
		center.setFlex(true);
		Div div = new Div();
		div.appendChild(contentPanel);
		if (isLookup())
			contentPanel.setWidth("99%");
        else
        	contentPanel.setStyle("width: 99%; margin: 0px auto;");
        contentPanel.setVflex(true);
		div.setStyle("width :100%; height: 100%");
		center.appendChild(div);
        
		South south = new South();
		layout.appendChild(south);
		southBody = new Vbox();
		southBody.setWidth("100%");
		south.appendChild(southBody);
		southBody.appendChild(confirmPanel);
		southBody.appendChild(new Separator());
		southBody.appendChild(statusBar);
	}
	
	/**
	 *	General Init
	 *	@return true, if success
	 */
	private boolean initInfo ()
	{
		// Prepare table
		StringBuffer where = new StringBuffer("cl.IsActive='Y'");
		
		if (p_whereClause.length() > 0)
			where.append(" AND ").append(Util.replace(p_whereClause, "C_CashLine.", "cl."));
		
		prepareTable (	s_cashLayout,	"C_CashLine cl INNER JOIN C_Cash c ON (cl.C_Cash_ID=c.C_Cash_ID)",
						where.toString(), "2,3,cl.Line");

		return true;
	} // initInfo

	
	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return sql where clause
	 */
	
	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();
	
		if (fName.getText().length() > 0)
			sql.append(" AND UPPER(c.Name) LIKE ?");

		if (fCashBook_ID.getDisplay() != "")
			sql.append(" AND c.C_CashBook_ID=?");
		
		if (fInvoice_ID.getDisplay() != "")
			sql.append(" AND cl.C_Invoice_ID=?");
		
		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Date f = fDateFrom.getValue();
			Timestamp from = new Timestamp(f.getTime());
			
			Date t = fDateTo.getValue();
			Timestamp to = new Timestamp(t.getTime());

			if (from == null && to != null)
				sql.append(" AND TRUNC(c.StatementDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(c.StatementDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(c.StatementDate) BETWEEN ? AND ?");
		}

		if (!isEmpty(fAmtFrom.getValue()) || !isEmpty(fAmtTo.getValue()))
		{
			BigDecimal from = isEmpty(fAmtFrom.getValue()) ? null : new BigDecimal(fAmtFrom.getValue());
			BigDecimal to = isEmpty(fAmtTo.getValue()) ? null : new BigDecimal(fAmtTo.getValue());
			
			if (cbAbsolute .isChecked())
				sql.append(" AND ABS(cl.Amount)");
			else
				sql.append(" AND cl.Amount");

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

		log.debug(sql.toString());
		return sql.toString();
	} // getSQLWhere

	private boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}

	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		if (fName.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fName));

		if (fCashBook_ID.getValue() != null)
		{
			Integer cb = (Integer)fCashBook_ID.getValue();
			pstmt.setInt(index++, cb.intValue());
			log.debug("CashBook=" + cb);
		}

		if (fInvoice_ID.getValue() != null)
		{
			Integer i = (Integer)fInvoice_ID.getValue();
			pstmt.setInt(index++, i.intValue());
			log.debug("Invoice=" + i);
		}

		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Date f = fDateFrom.getValue();
			Timestamp from = new Timestamp(f.getTime());
			
			Date t = fDateTo.getValue();
			Timestamp to = new Timestamp(t.getTime());

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

		if (!isEmpty(fAmtFrom.getValue()) || !isEmpty(fAmtTo.getValue()))
		{
			BigDecimal from = isEmpty(fAmtFrom.getValue()) ? null : new BigDecimal(fAmtFrom.getValue());
			BigDecimal to = isEmpty(fAmtTo.getValue()) ? null : new BigDecimal(fAmtTo.getValue());

			if (cbAbsolute.isChecked())
			{
				if (from != null)
					from = from.abs();
				if (to != null)
					to = to.abs();
			}
			
			log.debug("Amt From=" + from + ", To=" + to + ", Absolute=" + cbAbsolute.isChecked());
			
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
	} // setParameters

	/**
	 *  Get SQL WHERE parameter
	 *  @param f field
	 *  @return Upper case text with % at the end
	 */
	
	private String getSQLText (Textbox f)
	{
		String s = f.getText().toUpperCase();
		
		if (!s.endsWith("%"))
			s += "%";
		
		log.debug( "String=" + s);
		
		return s;
	} // getSQLText

	public void valueChange(ValueChangeEvent evt) 
	{
		
	}

	public void tableChanged(WTableModelEvent event) 
	{
	}
		
	@Override
	protected void insertPagingComponent()
    {
		southBody.insertBefore(paging, southBody.getFirstChild());
		layout.invalidate();
	}
	
}
