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
import org.adempiere.webui.component.NumberBox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

/**
 * Search Order info and return selection
 * Based on InfoOrder by Jorg Janke
 * 
 * @author Sendy Yagambrum
 * @date July 27, 2007
 * 
 * Zk Port
 * @author Elaine
 * @version	InfoOrder.java Adempiere Swing UI 3.4.1
 **/
public class InfoOrderPanel extends InfoPanel implements ValueChangeListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8725276769956103867L;
	private Label lblDocumentNo;
    private Label lblDescription;
    private Label lblDateOrdered;
    private Label lblOrderRef;
    private Label lblGrandTotal;
    
    private Textbox txtDocumentNo;
    private Textbox txtDescription;
    private Textbox txtOrderRef;
    
    private Datebox dateFrom;
    private Datebox dateTo;
    
    private NumberBox amountFrom;
    private NumberBox amountTo;
    
    private WSearchEditor editorBPartner;
    
    private Checkbox isSoTrx;
	private Borderlayout layout;
	private Vbox southBody;
   
    /**  Array of Column Info    */
    private static final ColumnInfo[] s_invoiceLayout = {
        new ColumnInfo(" ", "o.C_Order_ID", IDColumn.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"), "(SELECT Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=o.C_BPartner_ID)", String.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "DateOrdered"), "o.DateOrdered", Timestamp.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "DocumentNo"), "o.DocumentNo", String.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "C_Currency_ID"), "(SELECT ISO_Code FROM C_Currency c WHERE c.C_Currency_ID=o.C_Currency_ID)", String.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "GrandTotal"), "o.GrandTotal",  BigDecimal.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "ConvertedAmount"), "currencyBase(o.GrandTotal,o.C_Currency_ID,o.DateAcct, o.AD_Client_ID,o.AD_Org_ID)", BigDecimal.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "IsSOTrx"), "o.IsSOTrx", Boolean.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "Description"), "o.Description", String.class),
        new ColumnInfo(Msg.translate(Env.getCtx(), "POReference"), "o.POReference", String.class)
    };
    
    protected InfoOrderPanel(int WindowNo, String value,
            boolean multiSelection, String whereClause)
    {
    	this(WindowNo, value, multiSelection, whereClause, true);
    }

    protected InfoOrderPanel(int WindowNo, String value,
            boolean multiSelection, String whereClause, boolean lookup)
    {
        super ( WindowNo, "o", "C_Order_ID", multiSelection, whereClause, lookup);
        log.info( "InfoOrder");
        setTitle(Msg.getMsg(Env.getCtx(), "InfoOrder"));
        //
		initComponents();
		initLayout();
		p_loadedOK = initInfo();
		//
        int no = contentPanel.getRowCount();
        setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
        setStatusDB(Integer.toString(no));
        //
        if (value != null && value.length() > 0)
        {
            String values[] = value.split("_");
            txtDocumentNo.setText(values[0]);
            executeQuery();
            renderItems();
        }
    }
    public void initComponents()
    {
        lblDocumentNo = new Label(Util.cleanAmp(Msg.translate(Env.getCtx(), "DocumentNo")));
        lblDescription = new Label(Msg.translate(Env.getCtx(), "Description"));
        lblDateOrdered = new Label(Msg.translate(Env.getCtx(), "DateOrdered"));
        lblOrderRef = new Label(Msg.translate(Env.getCtx(), "POReference"));
        lblGrandTotal = new Label(Msg.translate(Env.getCtx(), "GrandTotal"));
        
        txtDocumentNo = new Textbox();
        txtDescription = new Textbox();
        txtOrderRef = new Textbox();
        
        dateFrom = new Datebox();
        dateTo= new Datebox();
        
        amountFrom = new NumberBox(false);
        amountTo = new NumberBox(false);
        
        isSoTrx = new Checkbox();
        isSoTrx.setLabel(Msg.translate(Env.getCtx(), "IsSOTrx"));
        isSoTrx.setChecked(!"N".equals(Env.getContext(Env.getCtx(), p_WindowNo, "IsSOTrx")));
        MLookup lookupBP = MLookupFactory.get(Env.getCtx(), p_WindowNo,
                0, 3499, DisplayType.Search);
        editorBPartner = new WSearchEditor(lookupBP, Msg.translate(
                Env.getCtx(), "C_BPartner_ID"), "", true, false, true);
        editorBPartner.addValueChangeListener(this);
        
    }
    
    private void initLayout()
    {
    	txtDocumentNo.setWidth("100%");
    	txtDescription.setWidth("100%");
    	txtOrderRef.setWidth("100%");
    	dateFrom.setWidth("165px");
		dateTo.setWidth("165px");
		amountFrom.getDecimalbox().setWidth("155px");
		amountTo.getDecimalbox().setWidth("155px");
		
    	Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lblDocumentNo.rightAlign());
		row.appendChild(txtDocumentNo);
		row.appendChild(editorBPartner.getLabel().rightAlign());
		row.appendChild(editorBPartner.getComponent());
		row.appendChild(isSoTrx);
		
		row = new Row();
		row.setSpans("1, 1, 1, 2");
		rows.appendChild(row);
		row.appendChild(lblDescription.rightAlign());
		row.appendChild(txtDescription);
		row.appendChild(lblDateOrdered.rightAlign());
		Hbox hbox = new Hbox();
		hbox.appendChild(dateFrom);
		hbox.appendChild(new Label("-"));
		hbox.appendChild(dateTo);
		row.appendChild(hbox);
		
		row = new Row();
		row.setSpans("1, 1, 1, 2");
		rows.appendChild(row);
		row.appendChild(lblOrderRef.rightAlign());
		row.appendChild(txtOrderRef);
		row.appendChild(lblGrandTotal.rightAlign());
		hbox = new Hbox();
		hbox.appendChild(amountFrom);
		hbox.appendChild(new Label("-"));
		hbox.appendChild(amountTo);
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
     *  General Init
     *  @return true, if success
     */
    private boolean initInfo ()
    {
        //  Set Defaults
        String bp = Env.getContext(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
        if (bp != null && bp.length() != 0)
            editorBPartner.setValue(new Integer(bp));

        //  prepare table
        StringBuffer where = new StringBuffer("o.IsActive='Y'");
        if (p_whereClause.length() > 0)
            where.append(" AND ").append(Util.replace(p_whereClause, "C_Order.", "o."));
       prepareTable(s_invoiceLayout,
            " C_Order o",
            where.toString(),"2,3,4");

        return true;
    }   //  initInfo
    @Override
    public String getSQLWhere()
    {
        StringBuffer sql = new StringBuffer();
        if (txtDocumentNo.getText().length() > 0)
            sql.append(" AND UPPER(o.DocumentNo) LIKE ?");
        if (txtDescription.getText().length() > 0)
            sql.append(" AND UPPER(o.Description) LIKE ?");
        if (txtOrderRef.getText().length() > 0)
            sql.append(" AND UPPER(o.POReference) LIKE ?");
        //
        if (editorBPartner.getValue() != null)
            sql.append(" AND o.C_BPartner_ID=?");
        //
        Date fromDate = null;
        Date toDate = null;
        try
        {
            fromDate = dateFrom.getValue();
        }
        catch (WrongValueException e)
        {
            
        }
        try
        {
            toDate = dateTo.getValue();
        }
        catch (WrongValueException e)
        {
            
        }
        if (fromDate == null && toDate != null)
        {
            sql.append(" AND TRUNC(o.DateOrdered) <= ?");
        }
        else if (fromDate != null && toDate == null)
        {
            sql.append(" AND TRUNC(o.DateOrdered) >= ?");
        }
        else if (fromDate != null && toDate != null)
        {    
                sql.append(" AND TRUNC(o.DateOrdered) BETWEEN ? AND ?");
        }
        //
        Double fromAmount = null;
        Double toAmount = null;
        if (amountFrom.getText() != null && amountFrom.getText().trim().length() > 0)
        {
            try
            {
                fromAmount = Double.parseDouble(amountFrom.getText());
            }
            catch (NumberFormatException e)
            {
                
            }
        }
        if (amountTo.getText() != null && amountTo.getText().trim().length() > 0)
        {
            try
            {
                toAmount = Double.parseDouble(amountTo.getText());
            }
            catch (NumberFormatException e)
            {
                
            }
        }
        if (fromAmount == null && toAmount != null)
        {
            sql.append(" AND o.GrandTotal <= ?");
        }
        else if (fromAmount != null && toAmount == null)
        {
            sql.append(" AND o.GrandTotal >= ?");
        }
        else if (fromAmount != null && toAmount != null)
        {
              sql.append(" AND o.GrandTotal BETWEEN ? AND ?");
        }
        sql.append(" AND o.IsSOTrx=?");

        log.finer(sql.toString());
        return sql.toString();
    }

    @Override
    protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
    {
        int index = 1;
        if (txtDocumentNo.getText().length() > 0)
            pstmt.setString(index++, getSQLText(txtDocumentNo));
        if (txtDescription.getText().length() > 0)
            pstmt.setString(index++, getSQLText(txtDescription));
        if (txtOrderRef.getText().length() > 0)
            pstmt.setString(index++, getSQLText(txtOrderRef));
        //
        if (editorBPartner.getValue() != null)
        {
            Integer bp = (Integer)editorBPartner.getValue();
            pstmt.setInt(index++, bp.intValue());
            log.fine("BPartner=" + bp);
        }
        //
        
            Date fromD = null;
            Date toD = null;
            Timestamp from = null;
            Timestamp to = null;
            try
            {
                if (dateFrom.getValue() != null)
                {
                    fromD = dateFrom.getValue();
                    from = new Timestamp(fromD.getTime());
                }
            }
            catch (WrongValueException e)
            {
                
            }
            try
            {
                if (dateTo.getValue() != null)
                {
                    toD = dateTo.getValue();
                    to = new Timestamp(toD.getTime());
                }
            }
            catch (WrongValueException e)
            {
                
            }
            
            log.fine("Date From=" + from + ", To=" + to);
            if (from == null && to != null)
            {
                pstmt.setTimestamp(index++, to);
            }
            else if (from != null && to == null)
            {
                pstmt.setTimestamp(index++, from);
            }
            else if (from != null && to != null)
            {
                pstmt.setTimestamp(index++, from);
                pstmt.setTimestamp(index++, to);
            }
        
        //
        BigDecimal fromBD = null;
        BigDecimal toBD = null;
        Double fromAmt = null;
        Double toAmt = null;
        
        if (amountFrom.getText() != null && amountFrom.getText().trim().length() > 0)
        {
            try
            {
                fromAmt = Double.parseDouble(amountFrom.getText());
                fromBD = BigDecimal.valueOf(fromAmt);
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (amountTo.getText() != null && amountTo.getText().trim().length() > 0)
        {
            try
            {
                toAmt = Double.parseDouble(amountTo.getText());
                toBD = BigDecimal.valueOf(toAmt);
            }
            catch (Exception e)
            {
                
            }
        }
        
        if (fromBD == null && toBD != null)
        {
            pstmt.setBigDecimal(index++, toBD);
        }
        else if (fromBD != null && toBD == null)
        {
            pstmt.setBigDecimal(index++, fromBD);
        }
        else if (fromBD != null && toBD != null)
        {
              pstmt.setBigDecimal(index++, fromBD);
              pstmt.setBigDecimal(index++, toBD);
        }
        
        pstmt.setString(index++, isSoTrx.isChecked() ? "Y" : "N");
        
    }

    /**
     *  Get SQL WHERE parameter
     *  @param f field
     *  @return sql
     */
    private String getSQLText (Textbox f)
    {
        String s = f.getText().toUpperCase();
        if (!s.endsWith("%"))
            s += "%";
        log.fine("String=" + s);
        return s;
    }   //  getSQLText
    
    // Elaine 2008/12/16
	/**
	 *	Zoom
	 */
	public void zoom()
	{
		log.info("");
		Integer C_Order_ID = getSelectedRowKey();
		if (C_Order_ID == null)
			return;
		MQuery query = new MQuery("C_Order");
		query.addRestriction("C_Order_ID", MQuery.EQUAL, C_Order_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("C_Order", isSoTrx.isSelected());
		AEnv.zoom (AD_WindowNo, query);
	}	//	zoom

	/**
	 *	Has Zoom
	 *  @return true
	 */
	protected boolean hasZoom()
	{
		return true;
	}	//	hasZoom
	//

    public void tableChanged(WTableModelEvent event)
    {
        
    }
    
    public void valueChange(ValueChangeEvent evt)
    {
        if (editorBPartner.equals(evt.getSource()))
        {
            editorBPartner.setValue(evt.getNewValue());
        }
        
    }

    @Override
	protected void insertPagingComponent()
    {
		southBody.insertBefore(paging, southBody.getFirstChild());
		layout.invalidate();
	}
}
