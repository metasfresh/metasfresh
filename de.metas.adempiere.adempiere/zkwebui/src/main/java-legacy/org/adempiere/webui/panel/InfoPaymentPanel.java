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

import org.adempiere.webui.apps.AEnv;
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
import org.compiere.model.MQuery;
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
* Based on InfoPayment written by Jorg Janke
*  
* @author 	Niraj Sohun
* 			Aug, 02, 2007
* 
* Zk Port
* @author Elaine
* @version	InfoPayment.java Adempiere Swing UI 3.4.1
*/

public class InfoPaymentPanel extends InfoPanel implements ValueChangeListener, EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7346527589727807179L;
	private Textbox fDocumentNo;
	private Textbox fAmtTo;
	private Textbox fAmtFrom;

	private WEditor fBPartner_ID;

	private Datebox fDateTo;
	private Datebox fDateFrom;

	private Checkbox fIsReceipt;
	
	private Label lDocumentNo;
	private Label lDateFrom;
	private Label lDateTo;
	private Label lAmtFrom;
	private Label lAmtTo;
	private Borderlayout layout;
	private Vbox southBody;

	/**  Array of Column Info    */
	private static final ColumnInfo[] s_paymentLayout = {
		new ColumnInfo(" ", "p.C_Payment_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_BP_BankAccount_ID"),
			"(SELECT b.Name || ' ' || ba.AccountNo FROM C_Bank b, C_BP_BankAccount ba WHERE b.C_Bank_ID=ba.C_Bank_ID AND ba.C_BP_BankAccount_ID=p.C_BP_BankAccount_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"),
			"(SELECT Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=p.C_BPartner_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "DateTrx"),
			"p.DateTrx", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "DocumentNo"),
			"p.DocumentNo", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "IsReceipt"),
			"p.IsReceipt", Boolean.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_Currency_ID"),
			"(SELECT ISO_Code FROM C_Currency c WHERE c.C_Currency_ID=p.C_Currency_ID)", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "PayAmt"),
			"p.PayAmt",  BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "ConvertedAmount"),
			"currencyBase(p.PayAmt,p.C_Currency_ID,p.DateTrx, p.AD_Client_ID,p.AD_Org_ID)", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "DiscountAmt"),
			"p.DiscountAmt",  BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "WriteOffAmt"),
			"p.WriteOffAmt",  BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "IsAllocated"),
			"p.IsAllocated",  Boolean.class)
	};
	
	/**
	 *  Detail Protected Constructor
	 *  
	 *  @param modal modal
	 *  @param WindowNo window no
	 *  @param value query value
	 *  @param multiSelection multiple selections
	 *  @param whereClause where clause
	 */
	protected InfoPaymentPanel(int WindowNo, String value,
			boolean multiSelection, String whereClause)
	{
		this(WindowNo, value, multiSelection, whereClause, true);
	}
	
	/**
	 *  Detail Protected Constructor
	 *
	 *  @param modal modal
	 *  @param WindowNo window no
	 *  @param value query value
	 *  @param multiSelection multiple selections
	 *  @param whereClause where clause
	 */
	protected InfoPaymentPanel(int WindowNo, String value,
			boolean multiSelection, String whereClause, boolean lookup)
	{
		super(WindowNo, "p", "C_Payment_ID", multiSelection, whereClause, lookup);
		
		log.info( "InfoPaymentPanel");
		setTitle(Msg.getMsg(Env.getCtx(), "InfoPayment"));
		//
		initComponents();
		initLayout();
		p_loadedOK = initInfo();
		//
		int no = contentPanel.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		
		if (value != null && value.length() > 0)
		{
			fDocumentNo .setValue(value);
			executeQuery();
		}
	} // InfoPaymentPanel

	private void initComponents()
	{
		fDocumentNo = new Textbox();
		fAmtTo = new Textbox();
		fAmtFrom = new Textbox();
		fBPartner_ID = new WSearchEditor(	
				MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search), 
				Msg.translate(Env.getCtx(), "C_BPartner_ID"), "", false, false, true);
		fBPartner_ID.addValueChangeListener(this);
		fDateTo = new Datebox();
		fDateFrom = new Datebox();
		fIsReceipt = new Checkbox();
		lDocumentNo = new Label(Msg.translate(Env.getCtx(), "DocumentNo"));
		lDateFrom = new Label(Msg.translate(Env.getCtx(), "DateTrx"));
		lDateTo = new Label("-");
		lAmtFrom = new Label(Msg.translate(Env.getCtx(), "PayAmt"));
		lAmtTo = new Label("-");
	}
	
	/**
	 *	Static Setup - add fields to parameterPanel
	 */
	private void initLayout()
	{
		fDocumentNo.setWidth("100%");
		fDateFrom.setWidth("165px");
		fDateTo.setWidth("165px");
		fAmtFrom.setWidth("180px");
		fAmtTo.setWidth("180px");
		
		fDocumentNo.addEventListener(Events.ON_CHANGE, this);
		
		fIsReceipt.setLabel(Msg.translate(Env.getCtx(), "IsReceipt"));
		fIsReceipt.addEventListener(Events.ON_CHECK, this);
		fIsReceipt.setChecked(!"N".equals(Env.getContext(Env.getCtx(), p_WindowNo, "IsSOTrx")));
		
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lDocumentNo.rightAlign());
		row.appendChild(fDocumentNo);
		row.appendChild(fBPartner_ID.getLabel().rightAlign());
		row.appendChild(fBPartner_ID.getComponent());
		row.appendChild(fIsReceipt);
		
		row = new Row();
		row.setSpans("3, 2");
		rows.appendChild(row);
		row.appendChild(lDateFrom.rightAlign());
		Hbox hbox = new Hbox();
		hbox.appendChild(fDateFrom);
		hbox.appendChild(lDateTo);
		hbox.appendChild(fDateTo);
		row.appendChild(hbox);
		
		row = new Row();
		row.setSpans("3, 2");
		rows.appendChild(row);
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
		//  Set Defaults
		String bp = Env.getContext(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		
		if (bp != null && bp.length() != 0)
			fBPartner_ID.setValue(new Integer(bp));

		// Prepare table
		StringBuffer where = new StringBuffer("p.IsActive='Y'");
		
		if (p_whereClause.length() > 0)
			where.append(" AND ").append(Util.replace(p_whereClause, "C_Payment.", "p."));
		
		prepareTable(s_paymentLayout, " C_Payment_v p", where.toString(), "2,3,4");
		
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
		
		if (fDocumentNo.getText().length() > 0)
			sql.append(" AND UPPER(p.DocumentNo) LIKE ?");

		if (fBPartner_ID.getDisplay() != "")
			sql.append(" AND p.C_BPartner_ID=?");

		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Date f = fDateFrom.getValue();
			Timestamp from = new Timestamp(f.getTime());
			
			Date t = fDateTo.getValue();
			Timestamp to = new Timestamp(t.getTime());

			if (from == null && to != null)
				sql.append(" AND TRUNC(p.DateTrx) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(p.DateTrx) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(p.DateTrx) BETWEEN ? AND ?");
		}

		if (fAmtFrom.getText() != "" || fAmtTo.getText() != "")
		{
			BigDecimal from = new BigDecimal(fAmtFrom.getValue());
			BigDecimal to = new BigDecimal(fAmtTo.getValue());
			
			if (from == null && to != null)
				sql.append(" AND p.PayAmt <= ?");
			else if (from != null && to == null)
				sql.append(" AND p.PayAmt >= ?");
			else if (from != null && to != null)
				sql.append(" AND p.PayAmt BETWEEN ? AND ?");
		}
		
		sql.append(" AND p.IsReceipt=?");

		log.fine(sql.toString());
		return sql.toString();
	} // getSQLWhere

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
	
		if (fDocumentNo.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fDocumentNo));

		if (fBPartner_ID.getDisplay() != "")
		{
			Integer bp = (Integer)fBPartner_ID.getValue();
			pstmt.setInt(index++, bp.intValue());
			log.fine("BPartner=" + bp);
		}

		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Date f = fDateFrom.getValue();
			Timestamp from = new Timestamp(f.getTime());
			
			Date t = fDateTo.getValue();
			Timestamp to = new Timestamp(t.getTime());
			
			log.fine("Date From=" + from + ", To=" + to);
		
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

		if (fAmtFrom.getText() != "" || fAmtTo.getText() != "")
		{
			BigDecimal from = new BigDecimal(fAmtFrom.getValue());
			BigDecimal to = new BigDecimal(fAmtTo.getValue());
			log.fine("Amt From=" + from + ", To=" + to);
			
			if (from == null && to != null)
				pstmt.setBigDecimal(index++, to);
			else if (from != null && to == null)
				pstmt.setBigDecimal(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setBigDecimal(index++, from);
				pstmt.setBigDecimal(index++, to);
			}
		}
		
		pstmt.setString(index++, fIsReceipt.isChecked() ? "Y" : "N");
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
		
		log.fine( "String=" + s);
		
		return s;
	} // getSQLText

	// Elaine 2008/12/16
	/**
	 *	Zoom
	 */
	public void zoom()
	{
		log.info( "InfoPayment.zoom");
		Integer C_Payment_ID = getSelectedRowKey();
		if (C_Payment_ID == null)
			return;
		MQuery query = new MQuery("C_Payment");
		query.addRestriction("C_Payment_ID", MQuery.EQUAL, C_Payment_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("C_Payment", fIsReceipt.isSelected());
		AEnv.zoom (AD_WindowNo, query);
	}	//	zoom
	//
	
	/**
	 *	Has Zoom
	 *  @return true
	 */
	
	protected boolean hasZoom()
	{
		return true;
	} // hasZoom
	
	public void valueChange(ValueChangeEvent evt)
	{
		if (fBPartner_ID.equals(evt.getSource()))
		{
	    	fBPartner_ID.setValue(evt.getNewValue());
		}
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
