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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.window.FDialog;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;

/**
 * Zk Port
 * @author Elaine
 * @version	InfoGeneral.java Adempiere Swing UI 3.4.1 
 */
public class InfoGeneralPanel extends InfoPanel implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -665127800885078238L;
	private Textbox txt1;
	private Textbox txt2;
	private Textbox txt3;
	private Textbox txt4;
	
	private Label lbl1;
	private Label lbl2;
	private Label lbl3;
	private Label lbl4;
	
	/** String Array of Column Info */
	private ColumnInfo[] m_generalLayout;
	
	/** list of query columns */
	private ArrayList<String> m_queryColumns = new ArrayList<String>();
	
	/** list of query columns (SQL) */
	private ArrayList<String> m_queryColumnsSql = new ArrayList<String>();
	private Borderlayout layout;
	private Vbox southBody;
	
	public InfoGeneralPanel(String queryValue, int windowNo,String tableName,String keyColumn, boolean isSOTrx, String whereClause) 
	{
		super(windowNo, tableName, keyColumn, false,whereClause);
				
		setTitle(Msg.getMsg(Env.getCtx(), "Info"));

		try
		{
			initComponents();
			initLayout();
			
			if (queryValue != null && queryValue.length() > 0)
			{
				txt1.setValue(queryValue);
			}

			p_loadedOK = initInfo ();
		}
		catch (Exception e)
		{
			return;
		}

		// Elaine 2008/12/15
		int no = contentPanel.getRowCount();
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		//
		
		if (queryValue != null && queryValue.length() > 0)
        {
            executeQuery();
            renderItems();
        }
		
	}
	
	private void initLayout()
	{
		Grid grid = GridFactory.newGridLayout();
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lbl1.rightAlign());
		row.appendChild(txt1);
		row.appendChild(lbl2.rightAlign());
		row.appendChild(txt2);
		row.appendChild(lbl3.rightAlign());
		row.appendChild(txt3);
		row.appendChild(lbl4.rightAlign());
		row.appendChild(txt4);
		
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

	private void initComponents()
	{
		txt1 = new Textbox();
		txt2 = new Textbox();
		txt3 = new Textbox();
		txt4 = new Textbox();
		
		lbl1 = new Label();
		lbl2 = new Label();
		lbl3 = new Label();
		lbl4 = new Label();
	}
	
	private boolean initInfo ()
	{
		if (!initInfoTable())
			return false;

		//  Prepare table
		
		StringBuffer where = new StringBuffer("IsActive='Y'");
		
		if (p_whereClause.length() > 0)
			where.append(" AND ").append(p_whereClause);
		prepareTable(m_generalLayout, p_tableName, where.toString(), "2");

		//	Set & enable Fields
		
		lbl1.setValue(Util.cleanAmp(Msg.translate(Env.getCtx(), m_queryColumns.get(0).toString())));
		
		if (m_queryColumns.size() > 1)
		{
			lbl2.setValue(Msg.translate(Env.getCtx(), m_queryColumns.get(1).toString()));
		}
		else
		{
			lbl2.setVisible(false);
			txt2.setVisible(false);
		}
		
		if (m_queryColumns.size() > 2)
		{
			lbl3.setValue(Msg.translate(Env.getCtx(), m_queryColumns.get(2).toString()));
		}
		else
		{
			lbl3.setVisible(false);
			txt3.setVisible(false);
		}
		
		if (m_queryColumns.size() > 3)
		{
			lbl4.setValue(Msg.translate(Env.getCtx(), m_queryColumns.get(3).toString()));
		}
		else
		{
			lbl4.setVisible(false);
			txt4.setVisible(false);
		}
		return true;
	}

	private boolean initInfoTable ()
	{
		//	Get Query Columns
		
		String sql = "SELECT c.ColumnName, t.AD_Table_ID, t.TableName, c.ColumnSql "
			+ "FROM AD_Table t"
			+ " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID)"
			+ "WHERE c.AD_Reference_ID=10"
			+ " AND t.TableName=?"	//	#1
			//	Displayed in Window
			+ " AND EXISTS (SELECT * FROM AD_Field f "
				+ "WHERE f.AD_Column_ID=c.AD_Column_ID"
				+ " AND f.IsDisplayed='Y' AND f.IsEncrypted='N' AND f.ObscureType IS NULL) "
			+ "ORDER BY c.IsIdentifier DESC, c.SeqNo";
		
		int AD_Table_ID = 0;
		String tableName = null;
		
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, p_tableName);
			ResultSet rs = pstmt.executeQuery();
		
			while (rs.next())
			{
				m_queryColumns.add(rs.getString(1));
				String columnSql = rs.getString(4);
				
				if (columnSql != null && columnSql.length() > 0)
					m_queryColumnsSql.add(columnSql);
				else
					m_queryColumnsSql.add(rs.getString(1));
				
				if (AD_Table_ID == 0)
				{
					AD_Table_ID = rs.getInt(2);
					tableName = rs.getString(3);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return false;
		}
		
		//	Miminum check
		if (m_queryColumns.size() == 0)
		{
			log.error("No query columns found");
			return false;
		}
		
		log.trace("Table " + tableName + ", ID=" + AD_Table_ID 
			+ ", QueryColumns #" + m_queryColumns.size());
		
		//	Only 4 Query Columns
		while (m_queryColumns.size() > 4) 
		{
			m_queryColumns.remove(m_queryColumns.size()-1);
			m_queryColumnsSql.remove(m_queryColumnsSql.size()-1);
		}
		
		//  Set Title
		String title = Msg.translate(Env.getCtx(), tableName + "_ID");  //  best bet
		
		if (title.endsWith("_ID"))
			title = Msg.translate(Env.getCtx(), tableName);             //  second best bet
		
		setTitle(getTitle() + " " + title);

		//	Get Display Columns
		
		ArrayList<ColumnInfo> list = new ArrayList<ColumnInfo>();
		sql = "SELECT c.ColumnName, c.AD_Reference_ID, c.IsKey, f.IsDisplayed, c.AD_Reference_Value_ID, c.ColumnSql "
			+ "FROM AD_Column c"
			+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID)"
			+ " INNER JOIN AD_Tab tab ON (t.AD_Window_ID=tab.AD_Window_ID)"
			+ " INNER JOIN AD_Field f ON (tab.AD_Tab_ID=f.AD_Tab_ID AND f.AD_Column_ID=c.AD_Column_ID) "
			+ "WHERE t.AD_Table_ID=? "
			+ " AND (c.IsKey='Y' OR "
				+ " (f.IsEncrypted='N' AND f.ObscureType IS NULL)) "
			+ "ORDER BY c.IsKey DESC, f.SeqNo";
		
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Table_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String columnName = rs.getString(1);
				int displayType = rs.getInt(2);
				boolean isKey = rs.getString(3).equals("Y");
				boolean isDisplayed = rs.getString(4).equals("Y");
				int AD_Reference_Value_ID = rs.getInt(5);
				String columnSql = rs.getString(6);

				if (columnSql == null || columnSql.length() == 0)
					columnSql = columnName;
				
				//  Default
				StringBuffer colSql = new StringBuffer(columnSql);
				Class<?> colClass = null;
				
				if (isKey)
					colClass = IDColumn.class;
				else if (!isDisplayed)
					;
				else if (displayType == DisplayType.YesNo)
					colClass = Boolean.class;
				else if (displayType == DisplayType.Amount)
					colClass = BigDecimal.class;
				else if (displayType == DisplayType.Number || displayType == DisplayType.Quantity)
					colClass = Double.class;
				else if (displayType == DisplayType.Integer)
					colClass = Integer.class;
				else if (displayType == DisplayType.String || displayType == DisplayType.Text || displayType == DisplayType.Memo)
					colClass = String.class;
				else if (DisplayType.isDate(displayType))
					colClass = Timestamp.class;
				//  ignore Binary, Button, ID, RowID
				else if (displayType == DisplayType.List)
				{
					if (Env.isBaseLanguage(Env.getCtx(), "AD_Ref_List"))
						colSql = new StringBuffer("(SELECT l.Name FROM AD_Ref_List l WHERE l.AD_Reference_ID=")
							.append(AD_Reference_Value_ID).append(" AND l.Value=").append(columnSql)
							.append(") AS ").append(columnName);
					else
						colSql = new StringBuffer("(SELECT t.Name FROM AD_Ref_List l, AD_Ref_List_Trl t "
							+ "WHERE l.AD_Ref_List_ID=t.AD_Ref_List_ID AND l.AD_Reference_ID=")
							.append(AD_Reference_Value_ID).append(" AND l.Value=").append(columnSql)
							.append(" AND t.AD_Language='").append(Env.getAD_Language(Env.getCtx()))
							.append("') AS ").append(columnName);
					colClass = String.class;
				}

				if (colClass != null)
				{
					list.add(new ColumnInfo(Msg.translate(Env.getCtx(), columnName), colSql.toString(), colClass));
					log.trace("Added Column=" + columnName);
				}
				else
					log.trace("Not Added Column=" + columnName);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return false;
		}
		
		if (list.size() == 0)
		{
			FDialog.error(p_WindowNo, this, "Error", "No Info Columns");
			log.error("No Info for AD_Table_ID=" + AD_Table_ID + " - " + sql);
			return false;
		}
		
		log.trace("InfoColumns #" + list.size()); 

		//  Convert ArrayList to Array
		m_generalLayout = new ColumnInfo[list.size()];
		list.toArray(m_generalLayout);
		return true;
	}
	
	@Override
	public String getSQLWhere() 
	{
		StringBuffer sql = new StringBuffer();
		addSQLWhere (sql, 0, txt1.getText().toUpperCase());
		addSQLWhere (sql, 1, txt2.getText().toUpperCase());
		addSQLWhere (sql, 2, txt3.getText().toUpperCase());
		addSQLWhere (sql, 3, txt4.getText().toUpperCase());
		return sql.toString();
	}
	
	private void addSQLWhere(StringBuffer sql, int index, String value)
	{
		if (!(value.equals("") || value.equals("%")) && index < m_queryColumns.size())
		{
			// Angelo Dabala' (genied) nectosoft: [2893220] avoid to append string parameters directly because of special chars like quote(s)
			sql.append(" AND UPPER(").append(m_queryColumnsSql.get(index).toString()).append(") LIKE ?");
		}
	}

	/**
	 *  Get SQL WHERE parameter
	 *  @param f field
	 *  @return sql part
	 */
	private String getSQLText (Textbox f)
	{
		String s = f.getText().toUpperCase();
		if (!s.endsWith("%"))
			s += "%";
		log.debug( "String=" + s);
		return s;
	}   //  getSQLText

	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 * 	@param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		int index = 1;
		if (txt1.getText().length() > 0)
			pstmt.setString(index++, getSQLText(txt1));
		if (txt2.getText().length() > 0)
			pstmt.setString(index++, getSQLText(txt2));
		if (txt3.getText().length() > 0)
			pstmt.setString(index++, getSQLText(txt3));
		if (txt4.getText().length() > 0)
			pstmt.setString(index++, getSQLText(txt4));
	}   //  setParameters

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
