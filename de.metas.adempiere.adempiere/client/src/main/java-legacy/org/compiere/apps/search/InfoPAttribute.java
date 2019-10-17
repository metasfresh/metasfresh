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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.Box;

import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLine;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MAttribute;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Services;

/**
 *	Search by Product Attribute.
 *
 *  @author     Jorg Janke
 *  @version    $Id: InfoPAttribute.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class InfoPAttribute extends CDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4307517787262162793L;
	
	/**
	 * 	Constructor.
	 * 	Called from InfoProduct,cmd_InfoPAttribute
	 *	@param parent
	 */
	public InfoPAttribute (Window parent)
	{
// metas: begin
		this(parent, "p");
	}
	public InfoPAttribute (Window parent, String productTableName)
	{
// metas: end
		super (parent, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "InfoPAttribute"), true);
		this.productTableName = productTableName; // metas
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

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private static final transient Logger log = LogManager.getLogger(InfoPAttribute.class);

	private String productTableName; // metas
	/**	Resulting Query			*/
	private String		m_query = "";
	/**	Product Attribure Editors	*/
	private ArrayList<Component>	m_productEditors = new ArrayList<Component>();
	private ArrayList<Component>	m_productEditorsTo = new ArrayList<Component>();
	/**	Instance Attribute Editors	*/
	private ArrayList<Component>	m_instanceEditors = new ArrayList<Component>();
	private ArrayList<Component>	m_instanceEditorsTo = new ArrayList<Component>();

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel centerPanel = new CPanel();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withoutText()
			.build();
	//
	private CLabel serNoLabel = new CLabel(msgBL.translate(Env.getCtx(), "SerNo"));
	private VString serNoField = new VString("SerNo", false, false, true, 10, 20, null, null); 
	private CLabel lotLabel = new CLabel(msgBL.translate(Env.getCtx(), "Lot"));
	private VString lotField = new VString("Lot", false, false, true, 10, 20, null, null); 
	private CComboBox<String> guaranteeDateSelection = null;
	private VDate guaranteeDateField = new VDate ("GuaranteeDate", false, false, true, DisplayType.Date, msgBL.translate(Env.getCtx(), "GuaranteeDate")); 
	private CLabel lotLabel2 = new CLabel(msgBL.translate(Env.getCtx(), "M_Lot_ID"));
	private CComboBox<KeyNamePair> lotSelection = null; 
	//

	/**
	 * 	Static Init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(mainLayout);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new ALayout());
		//	ConfirmPanel
		confirmPanel.setActionListener(this);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
	}	//	jbInit

	/**
	 * 	Dynamic Init of the Center Panel
	 */
	private void dynInit()
	{
		int row = addAttributes();
		//
		final String s = msgBL.translate(Env.getCtx(), "GuaranteeDate");
		guaranteeDateSelection = new CComboBox<> (new String[] {
				s + " <"
				, s + " ="
				, s + " >"});
	//	guaranteeDateSelection.setPreferredSize();
		initLotSelection();
		//	Fixed Instance Selection Fields		
		centerPanel.add(serNoLabel, new ALayoutConstraint(row++, 0));
		centerPanel.add(serNoField, null);
		centerPanel.add(lotLabel, new ALayoutConstraint(row++, 0));
		centerPanel.add(lotField, null);
		centerPanel.add(lotLabel2, new ALayoutConstraint(row++, 0));
		centerPanel.add(lotSelection, null);
		centerPanel.add(guaranteeDateSelection, new ALayoutConstraint(row++, 0));
		centerPanel.add(guaranteeDateField, null);
		//
		Dimension d = centerPanel.getPreferredSize();
		d.width = 400;
		centerPanel.setPreferredSize(d);
	}	//	dynInit

	/**
	 * 	Add Attributes
	 *	@return rows
	 */
	private int addAttributes()
	{
		int row = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT M_Attribute_ID, Name, Description, AttributeValueType, IsInstanceAttribute "
			+ "FROM M_Attribute "
			+ "WHERE IsActive='Y' "
			+ "ORDER BY IsInstanceAttribute, Name", 
			"M_Attribute", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ);
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			boolean instanceLine = false;
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
					CPanel group = new CPanel();
					group.setBorder(new VLine(msgBL.translate(Env.getCtx(), "IsInstanceAttribute")));
					group.add(Box.createVerticalStrut(VLine.SPACE));
					centerPanel.add(group, new ALayoutConstraint(row++, 0));
					instanceLine = true;
				}
				//
				CLabel label = new CLabel(name);
				if (description != null && description.length() > 0)
					label.setToolTipText(description);
				centerPanel.add(label, new ALayoutConstraint(row++, 0));
				Component field = null;
				if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
				{
					field = new CComboBox<>(getAttributeList(attribute_ID));
				}
				else if (MAttribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
				{
					field = new VDate(name, false, false, true, DisplayType.Date, name);
				}
				else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
				{
					field = new VNumber(name, false, false, true, DisplayType.Number, name);
				}
				else
				{
					field = new VString(name, false, false, true, 10, 40, null, null);
				}
				label.setLabelFor(field);
				centerPanel.add(field, null);
				//
				field.setName(String.valueOf(attribute_ID));
				if (isInstanceAttribute)
					m_instanceEditors.add(field);
				else
					m_productEditors.add(field);
				
				//	To (numbers)
				Component fieldTo = null;
				if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
				{
					fieldTo = new VNumber(name, false, false, true, DisplayType.Number, name);
					centerPanel.add(new CLabel("-"), null);
					centerPanel.add(fieldTo, null);
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
		return row;
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
			"M_AttributeValue",
			IUserRolePermissions.SQL_NOTQUALIFIED,
			Access.READ);
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
		
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT M_Lot_ID, Name FROM M_Lot WHERE IsActive='Y' ORDER BY 2",
			"M_Lot", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ);
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
		lotSelection = new CComboBox<>(items);
	}	//	initLotSelection


	
	/**
	 *	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			createQuery();
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
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
		String s = serNoField.getText();
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
		s = lotField.getText();
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
		KeyNamePair pp = lotSelection.getSelectedItem();
		if (pp != null && pp.getKey() != -1)
		{
			int ID = pp.getKey();
			sb.append(" AND asi.M_Lot_ID=").append(ID);
		}
		
		//	Guarantee Date
		Timestamp ts = guaranteeDateField.getValue();
		if (ts != null)
		{
			sb.append(" AND TRUNC(asi." + I_M_AttributeSetInstance.COLUMNNAME_GuaranteeDate + ")");
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
			Component c = m_instanceEditors.get(i);
			Component cTo = m_instanceEditorsTo.get(i);
			int M_Attribute_ID = Integer.parseInt(c.getName());
			if (c instanceof CComboBox)
			{
				@SuppressWarnings("unchecked")
				CComboBox<KeyNamePair> field = (CComboBox<KeyNamePair>)c;
				pp = field.getSelectedItem();
				if (pp != null && pp.getKey() != -1)
				{
					iAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND M_AttributeValue_ID=").append(pp.getKey());
				} 
			}
			else if (c instanceof VNumber)
			{
				VNumber field = (VNumber)c;
				BigDecimal value = (BigDecimal)field.getValue();
				VNumber fieldTo = (VNumber)cTo;
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
			else if (c instanceof VDate)
			{
				VDate field = (VDate)c;
				final Timestamp value = field.getValue();
				VDate fieldTo = (VDate)cTo;
				final Timestamp valueTo = fieldTo == null ? null : fieldTo.getValue();
				if (value != null || valueTo != null)
				{
					iAttr.append("M_Attribute_ID=").append(M_Attribute_ID).append(" AND ").append(I_M_AttributeInstance.COLUMNNAME_ValueDate);
					if (value != null && valueTo == null)
					{
						iAttr.append("=").append(DB.TO_DATE(value));
					}
					else if (value == null && valueTo != null)
					{
						iAttr.append("<=").append(DB.TO_DATE(valueTo));
					}
					else if (value != null && valueTo != null)
					{
						iAttr.append(" BETWEEN ").append(DB.TO_DATE(value))
							.append(" AND ").append(DB.TO_DATE(valueTo));
					}
				} 
			}
			else
			{
				VString field = (VString)c;
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
				+ "WHERE s.M_Product_ID="+productTableName+".M_Product_ID");
			sb.append(")");
		}
		
		
		//	Product Attributes 
		for (int i = 0; i < m_productEditors.size(); i++)
		{
			StringBuffer pAttr = new StringBuffer();
			Component c = m_productEditors.get(i);
			Component cTo = m_productEditorsTo.get(i);
			int M_Attribute_ID = Integer.parseInt(c.getName());
			if (c instanceof CComboBox)
			{
				@SuppressWarnings("unchecked")
				CComboBox<KeyNamePair> field = (CComboBox<KeyNamePair>)c;
				pp = field.getSelectedItem();
				if (pp != null && pp.getKey() != -1)
				{
					pAttr.append("M_Attribute_ID=").append(M_Attribute_ID)
						.append(" AND M_AttributeValue_ID=").append(pp.getKey());
				} 
			}
			else if (c instanceof VNumber)
			{
				VNumber field = (VNumber)c;
				BigDecimal value = (BigDecimal)field.getValue();
				VNumber fieldTo = (VNumber)cTo;
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
			else if (c instanceof VDate)
			{
				final VDate field = (VDate)c;
				final Timestamp value = field.getValue();
				final VDate fieldTo = (VDate)cTo;
				final Timestamp valueTo = fieldTo == null ? null : fieldTo.getValue();
				if (value != null || valueTo != null)
				{
					pAttr.append("M_Attribute_ID=").append(M_Attribute_ID).append(" AND ").append(I_M_AttributeInstance.COLUMNNAME_ValueDate);
					if (value != null && valueTo == null)
						pAttr.append("=").append(DB.TO_DATE(value));
					else if (value == null && valueTo != null)
						pAttr.append("<=").append(DB.TO_DATE(valueTo));
					else if (value != null && valueTo != null)
						pAttr.append(" BETWEEN ").append(DB.TO_DATE(value))
							.append(" AND ").append(DB.TO_DATE(valueTo));
				} 
			}
			else
			{
				VString field = (VString)c;
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
				sb.append(" AND "+productTableName+".M_AttributeSetInstance_ID IN "
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

}	//	InfoPAttribute
