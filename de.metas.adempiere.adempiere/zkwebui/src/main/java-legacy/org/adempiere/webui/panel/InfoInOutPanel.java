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
 * Based on InfoInOut written by Jorg Janke
 * 
 * @author Niraj Sohun Aug 03, 2007
 * 
 *         Zk Port
 * @author Elaine
 * @version InfoInOut.java Adempiere Swing UI 3.4.1
 */

public class InfoInOutPanel extends InfoPanel implements ValueChangeListener, EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3927370377224858985L;

	private Textbox fDocumentNo;

	private WEditor fBPartner_ID;

	private Textbox fDescription;
	private Textbox fPOReference;

	private Datebox fDateFrom;
	private Datebox fDateTo;

	private Checkbox fIsSOTrx;

	private Label lDocumentNo;
	private Label lDescription;
	private Label lPOReference;

	private Label lDateFrom;
	private Label lDateTo;

	private Vbox southBody;

	private Borderlayout layout;

	/** Array of Column Info */
	private static final ColumnInfo[] s_invoiceLayout = {
			new ColumnInfo(" ", "i.M_InOut_ID", IDColumn.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"), "(SELECT Name FROM C_BPartner bp WHERE bp.C_BPartner_ID=i.C_BPartner_ID)", String.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "MovementDate"), "i.MovementDate", Timestamp.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "DocumentNo"), "i.DocumentNo", String.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "Description"), "i.Description", String.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "POReference"), "i.POReference", String.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "IsSOTrx"), "i.IsSOTrx", Boolean.class)
	};

	/**
	 * Detail Protected Constructor
	 * 
	 * @param WindowNo
	 *            window no
	 * @param value
	 *            query value
	 * @param multiSelection
	 *            multiple selections
	 * @param whereClause
	 *            where clause
	 */
	protected InfoInOutPanel(int WindowNo, String value,
			boolean multiSelection, String whereClause)
	{
		this(WindowNo, value, multiSelection, whereClause, true);
	}

	/**
	 * Detail Protected Constructor
	 * 
	 * @param WindowNo
	 *            window no
	 * @param value
	 *            query value
	 * @param multiSelection
	 *            multiple selections
	 * @param whereClause
	 *            where clause
	 */
	protected InfoInOutPanel(int WindowNo, String value,
			boolean multiSelection, String whereClause, boolean lookup)
	{
		super(WindowNo, "i", "M_InOut_ID", multiSelection, whereClause, lookup);
		log.info("InfoInOut");
		setTitle(Msg.getMsg(Env.getCtx(), "InfoInOut"));
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
			fDocumentNo.setValue(value);
			executeQuery();
		}
	} // InfoInOutPanel

	private void initComponents()
	{
		fDocumentNo = new Textbox();
		fBPartner_ID = new WSearchEditor(
				MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, 3499, DisplayType.Search),
				Msg.translate(Env.getCtx(), "BPartner"), "", false, false, true);
		fDescription = new Textbox();
		fPOReference = new Textbox();
		fDateFrom = new Datebox();
		fDateTo = new Datebox();
		fIsSOTrx = new Checkbox();
		lDocumentNo = new Label(Msg.translate(Env.getCtx(), "DocumentNo"));
		lDescription = new Label(Msg.translate(Env.getCtx(), "Description"));
		lPOReference = new Label(Msg.translate(Env.getCtx(), "POReference"));
		lDateFrom = new Label(Msg.translate(Env.getCtx(), "MovementDate"));
		lDateTo = new Label("-");

	}
	
	/**
	 * Static Setup - add fields to parameterPanel
	 * 
	 */
	protected void initLayout()
	{
		fDocumentNo.setWidth("100%");
		fDescription.setWidth("100%");
		fPOReference.setWidth("100%");
		fDateFrom.setWidth("165px");
		fDateTo.setWidth("165px");

		fDocumentNo.addEventListener(Events.ON_CHANGE, this);
		fDescription.addEventListener(Events.ON_CHANGE, this);
		fPOReference.addEventListener(Events.ON_CHANGE, this);

		fIsSOTrx.setLabel(Msg.translate(Env.getCtx(), "IsSOTrx"));
		fIsSOTrx.setChecked(!"N".equals(Env.getContext(Env.getCtx(), p_WindowNo, "IsSOTrx")));
		fIsSOTrx.addEventListener(Events.ON_CHECK, this);

		fBPartner_ID.addValueChangeListener(this);

		Grid grid = GridFactory.newGridLayout();

		Rows rows = new Rows();
		grid.appendChild(rows);

		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lDocumentNo.rightAlign());
		row.appendChild(fDocumentNo);
		row.appendChild(fBPartner_ID.getLabel().rightAlign());
		row.appendChild(fBPartner_ID.getComponent());
		row.appendChild(fIsSOTrx);

		row = new Row();
		row.setSpans("1, 1, 1, 2");
		rows.appendChild(row);
		row.appendChild(lDescription.rightAlign());
		row.appendChild(fDescription);
		row.appendChild(lDateFrom.rightAlign());
		Hbox hbox = new Hbox();
		hbox.appendChild(fDateFrom);
		hbox.appendChild(lDateTo);
		hbox.appendChild(fDateTo);
		row.appendChild(hbox);

		row = new Row();
		row.setSpans("1, 1, 3");
		rows.appendChild(row);
		row.appendChild(lPOReference.rightAlign());
		row.appendChild(fPOReference);
		row.appendChild(new Label());

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
	 * General Init
	 * 
	 * @return true, if success
	 */

	private boolean initInfo()
	{
		// Set Defaults
		String bp = Env.getContext(Env.getCtx(), p_WindowNo, "C_BPartner_ID");

		if (bp != null && bp.length() != 0)
			fBPartner_ID.setValue(new Integer(bp));

		// Prepare table

		StringBuffer where = new StringBuffer("i.IsActive='Y'");

		if (p_whereClause.length() > 0)
			where.append(" AND ").append(Util.replace(p_whereClause, "M_InOut.", "i."));

		prepareTable(s_invoiceLayout, " M_InOut i", where.toString(), "2,3,4");

		return true;
	} // initInfo

	/*************************************************************************/

	/**
	 * Construct SQL Where Clause and define parameters. (setParameters needs to set parameters) Includes first AND
	 * 
	 * @return where clause
	 */

	protected String getSQLWhere()
	{
		StringBuffer sql = new StringBuffer();

		if (fDocumentNo.getText().length() > 0)
			sql.append(" AND UPPER(i.DocumentNo) LIKE ?");

		if (fDescription.getText().length() > 0)
			sql.append(" AND UPPER(i.Description) LIKE ?");

		if (fPOReference.getText().length() > 0)
			sql.append(" AND UPPER(i.POReference) LIKE ?");

		if (fBPartner_ID.getDisplay() != "")
			sql.append(" AND i.C_BPartner_ID=?");

		if (fDateFrom.getValue() != null || fDateTo.getValue() != null)
		{
			Date f = fDateFrom.getValue();
			Timestamp from = new Timestamp(f.getTime());

			Date t = fDateTo.getValue();
			Timestamp to = new Timestamp(t.getTime());

			if (from == null && to != null)
				sql.append(" AND TRUNC(i.MovementDate) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(i.MovementDate) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(i.MovementDate) BETWEEN ? AND ?");
		}
		sql.append(" AND i.IsSOTrx=?");

		return sql.toString();
	} // getSQLWhere

	/**
	 * Set Parameters for Query. (as defined in getSQLWhere)
	 * 
	 * @param pstmt
	 *            statement
	 * @param forCount
	 *            for counting records
	 * @throws SQLException
	 */

	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;

		if (fDocumentNo.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fDocumentNo));

		if (fDescription.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fDescription));

		if (fPOReference.getText().length() > 0)
			pstmt.setString(index++, getSQLText(fPOReference));

		if (fBPartner_ID.getDisplay() != "")
		{
			Integer bp = (Integer)fBPartner_ID.getValue();
			pstmt.setInt(index++, bp.intValue());
			log.debug("BPartner=" + bp);
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
		pstmt.setString(index++, fIsSOTrx.isChecked() ? "Y" : "N");
	} // setParameters

	/**
	 * Get SQL WHERE parameter
	 * 
	 * @param f
	 *            field
	 * @return sql part
	 */

	private String getSQLText(Textbox f)
	{
		String s = f.getText().toUpperCase();

		if (!s.endsWith("%"))
			s += "%";

		log.debug("String=" + s);
		return s;
	} // getSQLText

	// Elaine 2008/12/16
	/**
	 * Zoom
	 */
	public void zoom()
	{
		log.info("InfoInOut.zoom");
		Integer M_InOut_ID = getSelectedRowKey();
		if (M_InOut_ID == null)
			return;
		MQuery query = new MQuery("M_InOut");
		query.addRestriction("M_InOut_ID", MQuery.EQUAL, M_InOut_ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID("M_InOut", fIsSOTrx.isSelected());
		AEnv.zoom(AD_WindowNo, query);
	} // zoom

	//

	/**
	 * Has Zoom
	 * 
	 * @return true
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
