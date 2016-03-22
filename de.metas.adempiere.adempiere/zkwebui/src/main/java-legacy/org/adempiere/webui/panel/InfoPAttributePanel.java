/******************************************************************************
 * Copyright (C) 2008 Elaine Tan                                              *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.panel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Datebox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.NumberBox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.compiere.apps.search.InfoPAttribute;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeSet;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

/**
 * Search by Product Attribute.
 * This class is based on org.compiere.apps.search.InfoPAttribute written by Jorg Janke
 * @author Elaine
 *
 */
public class InfoPAttributePanel extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4922961793415942591L;

	/* the attribute set selected on the InfoProduct window */
	private int p_M_AttributeSet_ID = 0;
	
	/**
	 * 	Constructor.
	 * 	Called from InfoProduct,cmd_InfoPAttribute
	 *	@param parent
	 */
	public InfoPAttributePanel(Window parent)
	{
		super();
		if (parent instanceof InfoProductPanel) {
			p_M_AttributeSet_ID = ((InfoProductPanel)parent).getM_AttributeSet_ID();
		}
		setTitle(Msg.getMsg(Env.getCtx(), "InfoPAttribute"));
		this.setBorder("normal");
		this.setMaximizable(true);
		this.setSizable(true);
		
		try
		{
			jbInit();
			dynInit();
		}
		catch (Exception e)
		{
			log.error("InfoPAttribute", e);
		}
		AEnv.showCenterWindow(parent, this);
	}	//	InfoPAttribute
	
	/**	Resulting Query			*/
	private String		m_query = "";
	/**	Product Attribure Editors	*/
	private ArrayList<Component>	m_productEditors = new ArrayList<Component>();
	private ArrayList<Component>	m_productEditorsTo = new ArrayList<Component>();
	/**	Instance Attribute Editors	*/
	private ArrayList<Component>	m_instanceEditors = new ArrayList<Component>();
	private ArrayList<Component>	m_instanceEditorsTo = new ArrayList<Component>();
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(InfoPAttribute.class);

	private Rows rows = null;
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	//
	private Label serNoLabel = new Label(Msg.translate(Env.getCtx(), "SerNo"));
	private WStringEditor serNoField = new WStringEditor("SerNo", false, false, true, 10, 20, null, null); 
	private Label lotLabel = new Label(Msg.translate(Env.getCtx(), "Lot"));
	private WStringEditor lotField = new WStringEditor("Lot", false, false, true, 10, 20, null, null); 
	private Listbox guaranteeDateSelection = null;
	private Datebox guaranteeDateField = new Datebox();
	private Label lotLabel2 = new Label(Msg.translate(Env.getCtx(), "M_Lot_ID"));
	private Listbox lotSelection = null; 
	//

	/**
	 * 	Static Init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		Vbox vbox = new Vbox();
		this.appendChild(vbox);
		
		Grid grid = new Grid();
		grid.setWidth("400px");
		grid.setStyle("margin:0; padding:0;");
		grid.makeNoStrip();
		grid.setOddRowSclass("even");
		vbox.appendChild(grid);
        
		rows = new Rows();
		grid.appendChild(rows);
		
		//	ConfirmPanel
		confirmPanel.addActionListener(this);		
		vbox.appendChild(confirmPanel);
	}	//	jbInit

	/**
	 * 	Dynamic Init of the Center Panel
	 */
	private void dynInit()
	{
		addAttributes();

		boolean isGuarantee = true;
		boolean isSerial = true;
		boolean isLot = true;
		if (p_M_AttributeSet_ID > 0) {
			MAttributeSet as = new MAttributeSet(Env.getCtx(), p_M_AttributeSet_ID, null);
			isGuarantee = as.isGuaranteeDate();
			isSerial = as.isSerNo();
			isLot = as.isLot();
		}
		//
		String s = Msg.translate(Env.getCtx(), "GuaranteeDate");
		guaranteeDateSelection = new Listbox();
		guaranteeDateSelection.setRows(0);
		guaranteeDateSelection.setMultiple(false);
		guaranteeDateSelection.setMold("select");
		guaranteeDateSelection.setWidth("150px");
		guaranteeDateSelection.appendItem(s + " <", s + " <");
		guaranteeDateSelection.appendItem(s + " =", s + " =");
		guaranteeDateSelection.appendItem(s + " >", s + " >");
		initLotSelection();
		
		//	Fixed Instance Selection Fields
		Row row;
		Div div;
		if (isSerial) {
			row	= new Row();
			rows.appendChild(row);
			div = new Div();
			div.setAlign("right");
			div.appendChild(serNoLabel);
			row.appendChild(div);
			row.appendChild(serNoField.getComponent());
			serNoField.getComponent().setWidth("150px");
		}
		
		if (isLot) {
			row = new Row();
			rows.appendChild(row);
			div = new Div();
			div.setAlign("right");
			div.appendChild(lotLabel);
			row.appendChild(div);
			row.appendChild(lotField.getComponent());
			lotField.getComponent().setWidth("150px");

			row = new Row();
			rows.appendChild(row);
			div = new Div();
			div.setAlign("right");
			div.appendChild(lotLabel2);
			row.appendChild(div);
			row.appendChild(lotSelection);
		}

		if (isGuarantee) {
			row = new Row();
			rows.appendChild(row);
			div = new Div();
			div.setAlign("right");
			div.appendChild(guaranteeDateSelection);
			row.appendChild(div);
			row.appendChild(guaranteeDateField);
		}
	}	//	dynInit

	/**
	 * 	Add Attributes
	 *	@return rows
	 */
	private int addAttributes()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String whereAttributeSet;
		if (p_M_AttributeSet_ID > 0)
			whereAttributeSet = "AND M_Attribute_ID IN (SELECT M_Attribute_ID FROM M_AttributeUse WHERE M_AttributeSet_ID="+p_M_AttributeSet_ID+")";
		else
			whereAttributeSet = "";
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT M_Attribute_ID, Name, Description, AttributeValueType, IsInstanceAttribute "
			+ "FROM M_Attribute "
			+ "WHERE IsActive='Y' "
			+ whereAttributeSet
			+ " ORDER BY IsInstanceAttribute, Name", 
			"M_Attribute", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		boolean instanceLine = false;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int attribute_ID = rs.getInt(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				String attributeValueType = rs.getString(4);
				boolean isInstanceAttribute = "Y".equals(rs.getString(5)); 
				//	Instance switch
				if (!instanceLine && isInstanceAttribute)
				{
					Row row = new Row();
					rows.appendChild(row);
					row.setSpans("2");
    				Label group = new Label(Msg.translate(Env.getCtx(), "IsInstanceAttribute")); 
    				row.appendChild(group);
    				rows.appendChild(row);
    				
    				row = new Row();
					rows.appendChild(row);
					row.setSpans("2");
                    Separator separator = new Separator();
                    separator.setBar(true);
        			row.appendChild(separator);
        			rows.appendChild(row);
        			
					instanceLine = true;
				}
				//
				
				Row row = new Row();
				rows.appendChild(row);
				
				Label label = new Label(name);
				if (description != null && description.length() > 0)
					label.setTooltiptext(description);
				
				Div div = new Div();
				div.setAlign("right");
				div.appendChild(label);
				row.appendChild(div);
				
				Component field = null;
				if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
				{
					field = new Listbox();
					((Listbox) field).setRows(0);
					((Listbox) field).setMultiple(false);
					((Listbox) field).setMold("select");
					((Listbox) field).setWidth("150px");
					KeyNamePair[] knp = getAttributeList(attribute_ID);
					for(int i = 0; i < knp.length; i++)
						((Listbox) field).appendItem(knp[i].getName(), knp[i]);
				}
				else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
				{
					field = new WNumberEditor(name, false, false, true, DisplayType.Number, name).getComponent();
					((NumberBox) field).setWidth("150px");
				}
				else
				{
					field = new WStringEditor(name, false, false, true, 10, 40, null, null).getComponent();
					((Textbox) field).setWidth("150px");
				}
				row.appendChild(field);
				//
				field.setId(String.valueOf(attribute_ID));
				if (isInstanceAttribute)
					m_instanceEditors.add(field);
				else
					m_productEditors.add(field);
				
				//	To (numbers)
				Component fieldTo = null;
				if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
				{
					fieldTo = new WNumberEditor(name, false, false, true, DisplayType.Number, name).getComponent();
					((NumberBox) fieldTo).setWidth("150px");
					row = new Row();
					rows.appendChild(row);
					div = new Div();
					div.setAlign("right");
					div.appendChild(new Label("-"));
					row.appendChild(div);
					row.appendChild(fieldTo);
				}
				if (isInstanceAttribute)
					m_instanceEditorsTo.add(fieldTo);
				else
					m_productEditorsTo.add(fieldTo);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		// print instance line if not printed
		if (!instanceLine) {
			boolean isGuarantee = true;
			boolean isSerial = true;
			boolean isLot = true;
			if (p_M_AttributeSet_ID > 0) {
				MAttributeSet as = new MAttributeSet(Env.getCtx(), p_M_AttributeSet_ID, null);
				isGuarantee = as.isGuaranteeDate();
				isSerial = as.isSerNo();
				isLot = as.isLot();
			}
			if (isGuarantee || isSerial || isLot) {
				Row row = new Row();
				rows.appendChild(row);
				row.setSpans("2");
				Label group = new Label(Msg.translate(Env.getCtx(), "IsInstanceAttribute")); 
				row.appendChild(group);
				rows.appendChild(row);
				
				row = new Row();
				rows.appendChild(row);
				row.setSpans("2");
                Separator separator = new Separator();
                separator.setBar(true);
    			row.appendChild(separator);
    			rows.appendChild(row);
    			
				instanceLine = true;
			}
		}
		
		return 0;
	}	//	addProductAttributes

	/**
	 *	Get Attribute List
	 *	@param M_Attribute_ID attribure
	 *	@return array
	 */
	private KeyNamePair[] getAttributeList(int M_Attribute_ID)
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		list.add(new KeyNamePair(-1, ""));
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = Env.getUserRolePermissions().addAccessSQL( 
			"SELECT M_AttributeValue_ID, Value, Name "
			+ "FROM M_AttributeValue "
			+ "WHERE M_Attribute_ID=? "
			+ "ORDER BY 2",
			"M_AttributeValue", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Attribute_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(3)));
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		KeyNamePair[] retValue = new KeyNamePair[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getAttributeList


	/**
	 * 	Initialize Lot Selection
	 */
	private void initLotSelection()
	{
		ArrayList<KeyNamePair> list = new ArrayList<KeyNamePair>();
		list.add(new KeyNamePair(-1, ""));
		
		String whereAttributeSet;
		if (p_M_AttributeSet_ID > 0)
			whereAttributeSet = "AND M_Product_ID IN (SELECT M_Product_ID FROM M_Product WHERE M_AttributeSet_ID="+p_M_AttributeSet_ID+")";
		else
			whereAttributeSet = "";
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT M_Lot_ID, Name FROM M_Lot WHERE IsActive='Y' " + whereAttributeSet + " ORDER BY 2",
			"M_Lot", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	Create List
		KeyNamePair[] items = new KeyNamePair[list.size()];
		list.toArray(items);
		lotSelection = new Listbox();
		lotSelection.setRows(0);
		lotSelection.setMultiple(false);
		lotSelection.setMold("select");
		lotSelection.setWidth("150px");
		for(int i = 0; i < items.length; i++)
			lotSelection.appendItem(items[i].getName(), items[i]);
	}	//	initLotSelection


	
	/**
	 *	Action Listener
	 *	@param e event
	 */
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			createQuery();
			dispose();
		}
		else if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
		{
			m_query = null;
			dispose();
		}
	}	//	actionPerformed

	/**
	 * 	Create Query
	 *  <code>
	 * 	Available synonyms:
	 *		M_Product p
	 *		M_ProductPrice pr
	 *		M_AttributeSet pa
	 *	</code>
	 *	@return query
	 */
	private String createQuery()
	{
		/** Base Query
		SELECT *
		FROM M_Product p
 		 INNER JOIN M_ProductPrice pr ON (p.M_Product_ID=pr.M_Product_ID)
 		 LEFT OUTER JOIN M_AttributeSet pa ON (p.M_AttributeSet_ID=pa.M_AttributeSet_ID)
		WHERE
		**/
		
		/***	Instance Attributes		*/		
		StringBuffer sb = new StringBuffer();
		//	Serial No
		String s = serNoField.getComponent().getText();
		if (s != null && s.length() > 0)
		{
			sb.append(" AND asi.SerNo");
			if (s.indexOf('%') == -1 && s.indexOf('_') == 1)
				sb.append("=");
			else
				sb.append(" LIKE ");
			sb.append(DB.TO_STRING(s));
		}
		//	Lot Number
		s = lotField.getComponent().getText();
		if (s != null && s.length() > 0)
		{
			sb.append(" AND asi.Lot");
			if (s.indexOf('%') == -1 && s.indexOf('_') == 1)
				sb.append("=");
			else
				sb.append(" LIKE ");
			sb.append(DB.TO_STRING(s));
		}
		//	Lot ID
		ListItem li = lotSelection.getSelectedItem();
		if(li != null && li.getValue() != null)
		{
			KeyNamePair pp = (KeyNamePair) li.getValue();
			if (pp != null && pp.getKey() != -1)
			{
				int ID = pp.getKey();
				sb.append(" AND asi.M_Lot_ID=").append(ID);
			}
		}
		
		//	Guarantee Date
		Timestamp ts = (Timestamp)guaranteeDateField.getValue();
		if (ts != null)
		{
			sb.append(" AND TRUNC(asi.GuaranteeDate)");
			int index = guaranteeDateSelection.getSelectedIndex();	//	 < = >
			if (index == 0)
				sb.append("<");
			else if (index == 1)
				sb.append("=");
			else
				sb.append(">");
			sb.append(DB.TO_DATE(ts,true));
		}
		
		//	Instance Editors
		for (int i = 0; i < m_instanceEditors.size(); i++)
		{
			StringBuffer iAttr = new StringBuffer();
			Component c = (Component)m_instanceEditors.get(i);
			Component cTo = (Component)m_instanceEditorsTo.get(i);
			int M_Attribute_ID = Integer.parseInt(c.getId());
			if (c instanceof Listbox)
			{
				Listbox field = (Listbox)c;
				li = field.getSelectedItem();
				if(li != null && li.getValue() != null)
				{
					KeyNamePair pp = (KeyNamePair)li.getValue();
					if (pp != null && pp.getKey() != -1)
					{
						iAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
							.append(" AND M_AttributeValue_ID=").append(pp.getKey());
					} 
				}
			}
			else if (c instanceof NumberBox)
			{
				NumberBox field = (NumberBox)c;
				BigDecimal value = (BigDecimal)field.getValue();
				NumberBox fieldTo = (NumberBox)cTo;
				BigDecimal valueTo = (BigDecimal)fieldTo.getValue();
				if (value != null || valueTo != null)
				{
					iAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND ValueNumber");
					if (value != null && valueTo == null)
						iAttr.append("=").append(value);
					else if (value == null && valueTo != null)
						iAttr.append("<=").append(valueTo);
					else if (value != null && valueTo != null)
						iAttr.append(" BETWEEN ").append(value)
							.append(" AND ").append(valueTo);
				} 
			}
			else
			{
				Textbox field = (Textbox)c;
				String value = field.getText();
				if (value != null && value.length() > 0)
				{
					iAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND Value");
					if (value.indexOf('%') == -1 && value.indexOf('_') == -1)
						iAttr.append("=");
					else
						iAttr.append(" LIKE ");
					iAttr.append(DB.TO_STRING(value));
				}
			}
			//	Add to where
			if (iAttr.length() > 0)
				sb.append(" AND asi.M_AttributeSetInstance_ID IN "
					+ "(SELECT M_AttributeSetInstance_ID FROM M_AttributeInstance "
					+ "WHERE ")
					.append(iAttr).append(")");
		}
		
		//	finish Instance Attributes
		if (sb.length() > 0)
		{
			sb.insert(0, " AND EXISTS (SELECT * FROM M_Storage s"
				+ " INNER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) "
				+ "WHERE s.M_Product_ID=p.M_Product_ID");
			sb.append(")");
		}
		
		
		//	Product Attributes 
		for (int i = 0; i < m_productEditors.size(); i++)
		{
			StringBuffer pAttr = new StringBuffer();
			Component c = (Component)m_productEditors.get(i);
			Component cTo = (Component)m_productEditorsTo.get(i);
			int M_Attribute_ID = Integer.parseInt(c.getId());
			if (c instanceof Listbox)
			{
				Listbox field = (Listbox)c;
				li = field.getSelectedItem();
				if(li != null && li.getValue() != null)
				{
					KeyNamePair pp = (KeyNamePair)li.getValue();
					if (pp != null && pp.getKey() != -1)
					{
						pAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
							.append(" AND M_AttributeValue_ID=").append(pp.getKey());
					} 
				}
			}
			else if (c instanceof NumberBox)
			{
				NumberBox field = (NumberBox)c;
				BigDecimal value = (BigDecimal)field.getValue();
				NumberBox fieldTo = (NumberBox)cTo;
				BigDecimal valueTo = (BigDecimal)fieldTo.getValue();
				if (value != null || valueTo != null)
				{
					pAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND ValueNumber");
					if (value != null && valueTo == null)
						pAttr.append("=").append(value);
					else if (value == null && valueTo != null)
						pAttr.append("<=").append(valueTo);
					else if (value != null && valueTo != null)
						pAttr.append(" BETWEEN ").append(value)
							.append(" AND ").append(valueTo);
				} 
			}
			else
			{
				Textbox field = (Textbox)c;
				String value = field.getText();
				if (value != null && value.length() > 0)
				{
					pAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND Value");
					if (value.indexOf('%') == -1 && value.indexOf('_') == -1)
						pAttr.append("=");
					else
						pAttr.append(" LIKE ");
					pAttr.append(DB.TO_STRING(value));
				}
			}
			//	Add to Where
			if (pAttr.length() > 0)
				sb.append(" AND p.M_AttributeSetInstance_ID IN "
					+ "(SELECT M_AttributeSetInstance_ID "
					+ "FROM M_AttributeInstance WHERE ")
					.append(pAttr).append(")");
		}
		//
		m_query = null;
		if (sb.length() > 0)
			m_query = sb.toString();
		log.info(m_query);		
		return m_query;
	}	//	createQuery

	/**
	 * 	Get resulting Query WHERE
	 *	@return query or null
	 */
	public String getWhereClause()
	{
		return m_query;
	}	//	getQuery

}
