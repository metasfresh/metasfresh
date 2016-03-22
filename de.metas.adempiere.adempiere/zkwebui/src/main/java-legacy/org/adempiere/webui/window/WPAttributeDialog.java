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
package org.adempiere.webui.window;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Datebox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.NumberBox;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeInstance;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MLot;
import org.compiere.model.MLotCtl;
import org.compiere.model.MQuery;
import org.compiere.model.MSerNoCtl;
import org.compiere.model.X_M_MovementLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.impl.InputElement;

/**
 *  Product Attribute Set Product/Instance Dialog Editor.
 * 	Called from VPAttribute.actionPerformed
 *
 *  @author Jorg Janke
 *
 *  ZK Port
 *  @author Low Heng Sin
 */
public class WPAttributeDialog extends Window implements EventListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7810825026970615029L;

	/**
	 *	Product Attribute Instance Dialog
	 *	@param M_AttributeSetInstance_ID Product Attribute Set Instance id
	 * 	@param M_Product_ID Product id
	 * 	@param C_BPartner_ID b partner
	 * 	@param productWindow this is the product window (define Product Instance)
	 * 	@param AD_Column_ID column
	 * 	@param WindowNo window
	 */
	public WPAttributeDialog (int M_AttributeSetInstance_ID,
		int M_Product_ID, int C_BPartner_ID,
		boolean productWindow, int AD_Column_ID, int WindowNo)
	{
		super ();
		this.setTitle(Msg.translate(Env.getCtx(), "M_AttributeSetInstance_ID"));
		this.setAttribute("modal", Boolean.TRUE);
		this.setBorder("normal");
		this.setWidth("500px");
		this.setHeight("600px");
		this.setSizable(true);

		log.info("M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID
			+ ", M_Product_ID=" + M_Product_ID
			+ ", C_BPartner_ID=" + C_BPartner_ID
			+ ", ProductW=" + productWindow + ", Column=" + AD_Column_ID);
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
		m_M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		m_M_Product_ID = M_Product_ID;
		m_C_BPartner_ID = C_BPartner_ID;
		m_productWindow = productWindow;
		m_AD_Column_ID = AD_Column_ID;
		m_WindowNoParent = WindowNo;

		//get columnName from ad_column
		m_columnName = DB.getSQLValueString(null, "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID = ?", m_AD_Column_ID);
		if (m_columnName == null || m_columnName.trim().length() == 0)
		{
			//fallback
			m_columnName = "M_AttributeSetInstance_ID";
		}

		try
		{
			init();
		}
		catch(Exception ex)
		{
			log.error("VPAttributeDialog" + ex);
		}
		//	Dynamic Init
		if (!initAttributes ())
		{
			dispose();
			return;
		}
		AEnv.showCenterScreen(this);
	}	//	VPAttributeDialog

	private int						m_WindowNo;
	private MAttributeSetInstance	m_masi;
	private int 					m_M_AttributeSetInstance_ID;
	private int 					m_M_Locator_ID;
	private String					m_M_AttributeSetInstanceName;
	private int 					m_M_Product_ID;
	private int						m_C_BPartner_ID;
	private int						m_AD_Column_ID;
	private int						m_WindowNoParent;
	/**	Enter Product Attributes		*/
	private boolean					m_productWindow = false;
	/**	Change							*/
	private boolean					m_changed = false;

	private Logger					log = LogManager.getLogger(getClass());
	/** Row Counter					*/
	private int						m_row = 0;
	/** List of Editors				*/
	private ArrayList<HtmlBasedComponent>		m_editors = new ArrayList<HtmlBasedComponent>();
	/** Length of Instance value (40)	*/
	private static final int		INSTANCE_VALUE_LENGTH = 40;

	private Checkbox	cbNewEdit = new Checkbox();
	private Button		bSelect = new Button();
	//	Lot
//	private VString fieldLotString = new VString ("Lot", false, false, true, 20, 20, null, null);
	private Textbox fieldLotString = new Textbox();
	private Listbox fieldLot = new Listbox();
	private Button bLot = new Button(Msg.getMsg (Env.getCtx(), "New"));
	//	Lot Popup
	Menupopup 					popupMenu = new Menupopup();
	private Menuitem 			mZoom;
	//	Ser No
	private Textbox fieldSerNo = new Textbox();
	private Button bSerNo = new Button(Msg.getMsg (Env.getCtx(), "New"));
	//	Date
	private Datebox fieldGuaranteeDate = new Datebox();
	//
	private Textbox fieldDescription = new Textbox(); //TODO: set length to 20
	//
	private Borderlayout mainLayout = new Borderlayout();
	private Panel centerPanel = new Panel();
	private Grid centerLayout = new Grid();
	private ConfirmPanel confirmPanel = new ConfirmPanel (true);

	private String m_columnName = null;

	/**
	 *	Layout
	 * 	@throws Exception
	 */
	private void init () throws Exception
	{
		mainLayout.setParent(this);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		Center center = new Center();
		center.setParent(mainLayout);
		center.setFlex(true);
		center.appendChild(centerPanel);

		South south = new South();
		south.setParent(mainLayout);
		south.appendChild(confirmPanel);

		centerPanel.appendChild(centerLayout);
		centerLayout.setOddRowSclass("even");
		//
		confirmPanel.addActionListener(Events.ON_CLICK, this);
	}	//	init

	/**
	 *	Dyanmic Init.
	 *  @return true if initialized
	 */
	private boolean initAttributes ()
	{
		Rows rows = new Rows();
		rows.setParent(centerLayout);

		if (m_M_Product_ID == 0 && !m_productWindow)
			return false;

		MAttributeSet as = null;

		if (m_M_Product_ID != 0)
		{
			//	Get Model
			m_masi = MAttributeSetInstance.get(Env.getCtx(), m_M_AttributeSetInstance_ID, m_M_Product_ID);
			if (m_masi == null)
			{
				log.error("No Model for M_AttributeSetInstance_ID=" + m_M_AttributeSetInstance_ID + ", M_Product_ID=" + m_M_Product_ID);
				return false;
			}
			Env.setContext(Env.getCtx(), m_WindowNo, "M_AttributeSet_ID", m_masi.getM_AttributeSet_ID());

			//	Get Attribute Set
			as = m_masi.getMAttributeSet();
		}
		else
		{
			int M_AttributeSet_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "M_AttributeSet_ID");
			m_masi = new MAttributeSetInstance (Env.getCtx(), 0, M_AttributeSet_ID, null);
			as = m_masi.getMAttributeSet();
		}

		//	Product has no Attribute Set
		if (as == null)
		{
			FDialog.error(m_WindowNo, this, "PAttributeNoAttributeSet");
			return false;
		}
		//	Product has no Instance Attributes
		if (!m_productWindow && !as.isInstanceAttribute())
		{
			FDialog.error(m_WindowNo, this, "PAttributeNoInstanceAttribute");
			return false;
		}

		//	Show Product Attributes
		if (m_productWindow)
		{
			MAttribute[] attributes = as.getMAttributes (false);
			log.debug("Product Attributes=" + attributes.length);
			for (int i = 0; i < attributes.length; i++)
			{
				if (!Services.get(IAttributeExcludeBL.class).isExcludedAttribute(attributes[i], as, m_AD_Column_ID, Env.isSOTrx(Env.getCtx(), m_WindowNoParent)))
				{
					addAttributeLine(rows, attributes[i], true, !m_productWindow);
				}
			}
		}
		else	//	Set Instance Attributes
		{
			Row row = new Row();

			//	New/Edit - Selection
			if (m_M_AttributeSetInstance_ID == 0)		//	new
				cbNewEdit.setLabel(Msg.getMsg(Env.getCtx(), "NewRecord"));
			else
				cbNewEdit.setLabel(Msg.getMsg(Env.getCtx(), "EditRecord"));
			cbNewEdit.addEventListener(Events.ON_CHECK, this);
			row.appendChild(cbNewEdit);
			bSelect.setLabel(Msg.getMsg(Env.getCtx(), "SelectExisting"));
			bSelect.setImage("images/PAttribute16.png");
			bSelect.addEventListener(Events.ON_CLICK, this);
			row.appendChild(bSelect);
			rows.appendChild(row);

			//	All Attributes
			MAttribute[] attributes = as.getMAttributes (true);
			log.debug("Instance Attributes=" + attributes.length);
			for (int i = 0; i < attributes.length; i++)
			{
				if (!Services.get(IAttributeExcludeBL.class).isExcludedAttribute(attributes[i], as, m_AD_Column_ID, Env.isSOTrx(Env.getCtx(), m_WindowNoParent)))
				{
					addAttributeLine(rows, attributes[i], true, !m_productWindow);
				}
			}
		}

		//	Lot
		if (!m_productWindow && as.isLot())
		{
			Row row = new Row();
			row.setParent(rows);
			m_row++;
			Label label = new Label (Msg.translate(Env.getCtx(), "Lot"));
			row.appendChild(label);
			row.appendChild(fieldLotString);
			fieldLotString.setText (m_masi.getLot());
			//	M_Lot_ID
		//	int AD_Column_ID = 9771;	//	M_AttributeSetInstance.M_Lot_ID
		//	fieldLot = new VLookup ("M_Lot_ID", false,false, true,
		//		MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.TableDir));
			String sql = "SELECT M_Lot_ID, Name "
				+ "FROM M_Lot l "
				+ "WHERE EXISTS (SELECT M_Product_ID FROM M_Product p "
					+ "WHERE p.M_AttributeSet_ID=" + m_masi.getM_AttributeSet_ID()
					+ " AND p.M_Product_ID=l.M_Product_ID)";
			fieldLot = new Listbox();
			fieldLot.setMold("select");
			KeyNamePair[] keyNamePairs = DB.getKeyNamePairs(sql, true);
			for (KeyNamePair pair : keyNamePairs) {
				fieldLot.appendItem(pair.getName(), pair.getKey());
			}

			label = new Label (Msg.translate(Env.getCtx(), "M_Lot_ID"));
			row = new Row();
			row.setParent(rows);
			m_row++;
			row.appendChild(label);
			row.appendChild(fieldLot);
			if (m_masi.getM_Lot_ID() != 0)
			{
				for (int i = 1; i < fieldLot.getItemCount(); i++)
				{
					ListItem pp = fieldLot.getItemAtIndex(i);
					if ((Integer)pp.getValue() == m_masi.getM_Lot_ID())
					{
						fieldLot.setSelectedIndex(i);
						fieldLotString.setReadonly(true);
						break;
					}
				}
			}
			fieldLot.addEventListener(Events.ON_SELECT, this);
			//	New Lot Button
			if (m_masi.getMAttributeSet().getM_LotCtl_ID() != 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(MLot.Table_ID, false)
					&& Env.getUserRolePermissions().isTableAccess(MLotCtl.Table_ID, false)
					&& !m_masi.isExcludeLot(m_AD_Column_ID, Env.isSOTrx(Env.getCtx(), m_WindowNoParent)))
				{
					row = new Row();
					row.setParent(rows);
					m_row++;
					row.appendChild(bLot);
					bLot.addEventListener(Events.ON_CLICK, this);
				}
			}
			//	Popup
//			fieldLot.addMouseListener(new VPAttributeDialog_mouseAdapter(this));    //  popup
			mZoom = new Menuitem(Msg.getMsg(Env.getCtx(), "Zoom"), "images/Zoom16.png");
			mZoom.addEventListener(Events.ON_CLICK, this);
			popupMenu.appendChild(mZoom);
			this.appendChild(popupMenu);
		}	//	Lot

		//	SerNo
		if (!m_productWindow && as.isSerNo())
		{
			Row row = new Row();
			row.setParent(rows);
			m_row++;
			Label label = new Label (Msg.translate(Env.getCtx(), "SerNo"));
			row.appendChild(label);
			row.appendChild(fieldSerNo);
			fieldSerNo.setText(m_masi.getSerNo());

			//	New SerNo Button
			if (m_masi.getMAttributeSet().getM_SerNoCtl_ID() != 0)
			{
				if (Env.getUserRolePermissions().isTableAccess(MSerNoCtl.Table_ID, false)
					&& !m_masi.isExcludeSerNo(m_AD_Column_ID, Env.isSOTrx(Env.getCtx(), m_WindowNoParent)))
				{
					row = new Row();
					row.setParent(rows);
					m_row++;
					row.appendChild(bSerNo);
					bSerNo.addEventListener(Events.ON_CLICK, this);
				}
			}
		}	//	SerNo

		//	GuaranteeDate
		if (!m_productWindow && as.isGuaranteeDate())
		{
			Row row = new Row();
			row.setParent(rows);
			m_row++;
			Label label = new Label (Msg.translate(Env.getCtx(), "GuaranteeDate"));
			if (m_M_AttributeSetInstance_ID == 0)
			{
				// TODO: needs to be calculated based on M_Product_ID, BPartner and Document date
				//fieldGuaranteeDate.setValue(m_masi.getGuaranteeDate(true));
			}
			else
			{
				fieldGuaranteeDate.setValue(m_masi.getGuaranteeDate());
			}
			row.appendChild(label);
			row.appendChild(fieldGuaranteeDate);
		}	//	GuaranteeDate

		if (m_row == 0)
		{
			FDialog.error(m_WindowNo, this, "PAttributeNoInfo");
			return false;
		}

		//	New/Edit Window
		if (!m_productWindow)
		{
			cbNewEdit.setChecked(m_M_AttributeSetInstance_ID == 0);
			cmd_newEdit();
		}

		//	Attrribute Set Instance Description
		Label label = new Label (Msg.translate(Env.getCtx(), "Description"));
//		label.setLabelFor(fieldDescription);
		fieldDescription.setText(m_masi.getDescription());
		fieldDescription.setReadonly(true);
		Row row = new Row();
		row.setParent(rows);
		row.appendChild(label);
		row.appendChild(fieldDescription);

		return true;
	}	//	initAttribute

	/**
	 * 	Add Attribute Line
	 *	@param attribute attribute
	 * 	@param product product level attribute
	 * 	@param readOnly value is read only
	 */
	private void addAttributeLine (Rows rows, MAttribute attribute, boolean product, boolean readOnly)
	{
		log.debug(attribute + ", Product=" + product + ", R/O=" + readOnly);

		m_row++;
		Label label = new Label (attribute.getName());
		if (product)
			label.setStyle("font-weight: bold");

		if (attribute.getDescription() != null)
			label.setTooltiptext(attribute.getDescription());

		Row row = rows.newRow();
		row.appendChild(label.rightAlign());
		//
		MAttributeInstance instance = attribute.getMAttributeInstance (m_M_AttributeSetInstance_ID);
		if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			I_M_AttributeValue[] values = attribute.getMAttributeValues(Env.getSOTrxOrNull(Env.getCtx(), m_WindowNo));	//	optional = null
			Listbox editor = new Listbox();
			editor.setMold("select");
			for (I_M_AttributeValue value : values)
			{
				ListItem item = new ListItem(value != null ? value.getName() : "", value);
				editor.appendChild(item);
			}
			boolean found = false;
			if (instance != null)
			{
				for (int i = 0; i < values.length; i++)
				{
					if (values[i] != null && values[i].getM_AttributeValue_ID () == instance.getM_AttributeValue_ID ())
					{
						editor.setSelectedIndex (i);
						found = true;
						break;
					}
				}
				if (found)
					log.debug("Attribute=" + attribute.getName() + " #" + values.length + " - found: " + instance);
				else
					log.warn("Attribute=" + attribute.getName() + " #" + values.length + " - NOT found: " + instance);
			}	//	setComboBox
			else
				log.debug("Attribute=" + attribute.getName() + " #" + values.length + " no instance");
			row.appendChild(editor);
			if (readOnly)
				editor.setEnabled(false);
			else
				m_editors.add (editor);
		}
		else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attribute.getAttributeValueType()))
		{
//			VNumber editor = new VNumber(attribute.getName(), attribute.isMandatory(),
//				false, true, DisplayType.Number, attribute.getName());
			NumberBox editor = new NumberBox(false);
			if (instance != null)
				editor.setValue(instance.getValueNumber());
			else
				editor.setValue(Env.ZERO);
			row.appendChild(editor);
			if (readOnly)
				editor.setEnabled(false);
			else
				m_editors.add (editor);
		}
		else	//	Text Field
		{
//			VString editor = new VString (attribute.getName(), attribute.isMandatory(),
//				false, true, 20, INSTANCE_VALUE_LENGTH, null, null);
			Textbox editor = new Textbox();
			if (instance != null)
				editor.setText(instance.getValue());
			row.appendChild(editor);
			if (readOnly)
				editor.setEnabled(false);
			else
				m_editors.add (editor);
		}
	}	//	addAttributeLine

	/**
	 *	dispose
	 */
	@Override
	public void dispose()
	{
		Env.clearWinContext(m_WindowNo);
		//
		Env.setContext(Env.getCtx(), m_WindowNoParent, Env.TAB_INFO, m_columnName,
			String.valueOf(m_M_AttributeSetInstance_ID));
		Env.setContext(Env.getCtx(), m_WindowNoParent, Env.TAB_INFO, "M_Locator_ID",
			String.valueOf(m_M_Locator_ID));
		//
		this.detach();
	}	//	dispose

	@Override
	public void onEvent(Event e) throws Exception
	{
		//	Select Instance
		if (e.getTarget() == bSelect)
		{
			if (cmd_select())
				dispose();
		}
		//	New/Edit
		else if (e.getTarget() == cbNewEdit)
		{
			cmd_newEdit();
		}
		//	Select Lot from existing
		else if (e.getTarget() == fieldLot)
		{
			ListItem pp = fieldLot.getSelectedItem();
			if (pp != null && (Integer)pp.getValue() != -1)
			{
				fieldLotString.setText(pp.getLabel());
				fieldLotString.setReadonly(true);
				m_masi.setM_Lot_ID((Integer)pp.getValue());
			}
			else
			{
				fieldLotString.setReadonly(false);
				m_masi.setM_Lot_ID(0);
			}
		}
		//	Create New Lot
		else if (e.getTarget() == bLot)
		{
			KeyNamePair pp = m_masi.createLot(m_M_Product_ID);
			if (pp != null)
			{
				ListItem item = new ListItem(pp.getName(), pp.getKey());
				fieldLot.appendChild(item);
				fieldLot.setSelectedItem(item);
			}
		}
		//	Create New SerNo
		else if (e.getTarget() == bSerNo)
		{
			fieldSerNo.setText(m_masi.getSerNo(true));
		}

		//	OK
		else if (e.getTarget().getId().equals("Ok"))
		{
			if (saveSelection())
				dispose();
		}
		//	Cancel
		else if (e.getTarget().getId().equals("Cancel"))
		{
			m_changed = false;
			m_M_AttributeSetInstance_ID = 0;
			m_M_Locator_ID = 0;
			dispose();
		}
		//	Zoom M_Lot
		else if (e.getTarget() == mZoom)
		{
			cmd_zoom();
		}
		else
			log.error("not found - " + e);
	}	//	actionPerformed

	/**
	 * 	Instance Selection Button
	 * 	@return true if selected
	 */
	private boolean cmd_select()
	{
		log.info("");

		int M_Warehouse_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "M_Warehouse_ID");

		int C_DocType_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, "C_DocType_ID");
		if (C_DocType_ID > 0) {
			MDocType doctype = new MDocType (Env.getCtx(), C_DocType_ID, null);
			String docbase = doctype.getDocBaseType();
			if (docbase.equals(MDocType.DOCBASETYPE_MaterialReceipt))
				M_Warehouse_ID = 0;
		}

		// teo_sarca [ 1564520 ] Inventory Move: can't select existing attributes
		int M_Locator_ID = 0;
		if (m_AD_Column_ID == 8551) { // TODO: hardcoded: M_MovementLine[324].M_AttributeSetInstance_ID[8551]
			M_Locator_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNoParent, X_M_MovementLine.COLUMNNAME_M_Locator_ID, true); // only window
		}

		String title = "";
		//	Get Text
		String sql = "SELECT p.Name, w.Name, w.M_Warehouse_ID FROM M_Product p, M_Warehouse w "
			+ "WHERE p.M_Product_ID=? AND w.M_Warehouse_ID"
				+ (M_Locator_ID <= 0 ? "=?" : " IN (SELECT M_Warehouse_ID FROM M_Locator where M_Locator_ID=?)"); // teo_sarca [ 1564520 ]
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, M_Locator_ID <= 0 ? M_Warehouse_ID : M_Locator_ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				title = rs.getString(1) + " - " + rs.getString(2);
				M_Warehouse_ID = rs.getInt(3); // fetch the actual warehouse - teo_sarca [ 1564520 ]
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		WPAttributeInstance pai = new WPAttributeInstance(title,
			M_Warehouse_ID, M_Locator_ID, m_M_Product_ID, m_C_BPartner_ID);
		if (pai.getM_AttributeSetInstance_ID() != -1)
		{
			m_M_AttributeSetInstance_ID = pai.getM_AttributeSetInstance_ID();
			m_M_AttributeSetInstanceName = pai.getM_AttributeSetInstanceName();
			m_M_Locator_ID = pai.getM_Locator_ID();
			m_changed = true;
			return true;
		}
		return false;
	}	//	cmd_select

	/**
	 * 	Instance New/Edit
	 */
	private void cmd_newEdit()
	{
		boolean rw = cbNewEdit.isChecked();
		log.info("R/W=" + rw + " " + m_masi);
		//
		fieldLotString.setReadonly(!(rw && m_masi.getM_Lot_ID()==0));
		if (fieldLot != null)
			fieldLot.setEnabled(rw);
		bLot.setEnabled(rw);
		fieldSerNo.setReadonly(!rw);
		bSerNo.setEnabled(rw);
		fieldGuaranteeDate.setReadonly(!rw);
		//
		for (int i = 0; i < m_editors.size(); i++)
		{
			HtmlBasedComponent editor = m_editors.get(i);
			if (editor instanceof InputElement)
				((InputElement)editor).setReadonly(!rw);
			else if (editor instanceof Listbox)
				((Listbox)editor).setEnabled(rw);
			else if (editor instanceof NumberBox)
				((NumberBox)editor).setEnabled(rw);
		}
	}	//	cmd_newEdit

	/**
	 * 	Zoom M_Lot
	 */
	private void cmd_zoom()
	{
		int M_Lot_ID = 0;
		ListItem pp = fieldLot.getSelectedItem();
		if (pp != null)
			M_Lot_ID = (Integer) pp.getValue();
		MQuery zoomQuery = new MQuery("M_Lot");
		zoomQuery.addRestriction("M_Lot_ID", MQuery.EQUAL, M_Lot_ID);
		log.info(zoomQuery.toString());
		//
		//TODO: to port
		/*
		int AD_Window_ID = 257;		//	Lot
		AWindow frame = new AWindow();
		if (frame.initWindow(AD_Window_ID, zoomQuery))
		{
			this.setVisible(false);
			this.setModal (false);	//	otherwise blocked
			this.setVisible(true);
			AEnv.addToWindowManager(frame);
			AEnv.showScreen(frame, SwingConstants.EAST);
		}*/
	}	//	cmd_zoom

	/**
	 *	Save Selection
	 *	@return true if saved
	 */
	private boolean saveSelection()
	{
		log.info("");
		MAttributeSet as = m_masi.getMAttributeSet();
		if (as == null)
			return true;
		//
		m_changed = false;
		String mandatory = "";
		if (!m_productWindow && as.isLot())
		{
			log.debug("Lot=" + fieldLotString.getText ());
			String text = fieldLotString.getText();
			m_masi.setLot (text);
			if (as.isLotMandatory() && (text == null || text.length() == 0))
				mandatory += " - " + Msg.translate(Env.getCtx(), "Lot");
			m_changed = true;
		}	//	Lot
		if (!m_productWindow && as.isSerNo())
		{
			log.debug("SerNo=" + fieldSerNo.getText());
			String text = fieldSerNo.getText();
			m_masi.setSerNo(text);
			if (as.isSerNoMandatory() && (text == null || text.length() == 0))
				mandatory += " - " + Msg.translate(Env.getCtx(), "SerNo");
			m_changed = true;
		}	//	SerNo
		if (!m_productWindow && as.isGuaranteeDate())
		{
			log.debug("GuaranteeDate=" + fieldGuaranteeDate.getValue());
			Date gDate = fieldGuaranteeDate.getValue();
			Timestamp ts = gDate != null ? new Timestamp(gDate.getTime()) : null;
			m_masi.setGuaranteeDate(ts);
			if (as.isGuaranteeDateMandatory() && ts == null)
				mandatory += " - " + Msg.translate(Env.getCtx(), "GuaranteeDate");
			m_changed = true;
		}	//	GuaranteeDate

		//	***	Save Attributes ***
		//	New Instance
		if (m_changed || m_masi.getM_AttributeSetInstance_ID() == 0)
		{
			m_masi.save ();
			m_M_AttributeSetInstance_ID = m_masi.getM_AttributeSetInstance_ID ();
			m_M_AttributeSetInstanceName = m_masi.getDescription();
		}

		//	Save Instance Attributes
		int current = -1;
		MAttribute[] attributes = as.getMAttributes(!m_productWindow);
		for (int i = 0; i < attributes.length; i++)
		{
			if (Services.get(IAttributeExcludeBL.class).isExcludedAttribute(attributes[i], as, m_AD_Column_ID, Env.isSOTrx(Env.getCtx(), m_WindowNoParent)))
			{
				// If the attribute is excluded, go to the next one.
				continue;
			}
			else
			{
				// We need this to keep track of attributes that are excluded, since they don't have an editor.
				current++;
			}
			if (MAttribute.ATTRIBUTEVALUETYPE_List.equals(attributes[i].getAttributeValueType()))
			{
				Listbox editor = (Listbox)m_editors.get(current);
				ListItem item = editor.getSelectedItem();
				I_M_AttributeValue value = item != null ? (I_M_AttributeValue)item.getValue() : null;
				log.debug(attributes[i].getName() + "=" + value);
				if (attributes[i].isMandatory() && value == null)
					mandatory += " - " + attributes[i].getName();
				attributes[i].setMAttributeInstance(m_M_AttributeSetInstance_ID, value);
			}
			else if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attributes[i].getAttributeValueType()))
			{
				NumberBox editor = (NumberBox)m_editors.get(current);
				BigDecimal value = editor.getValue();
				log.debug(attributes[i].getName() + "=" + value);
				if (attributes[i].isMandatory() && value == null)
					mandatory += " - " + attributes[i].getName();
				//setMAttributeInstance doesn't work without decimal point
				if (value != null && value.scale() == 0)
					value = value.setScale(1, BigDecimal.ROUND_HALF_UP);
				attributes[i].setMAttributeInstance(m_M_AttributeSetInstance_ID, value);
			}
			else
			{
				Textbox editor = (Textbox)m_editors.get(current);
				String value = editor.getText();
				log.debug(attributes[i].getName() + "=" + value);
				if (attributes[i].isMandatory() && (value == null || value.length() == 0))
					mandatory += " - " + attributes[i].getName();
				attributes[i].setMAttributeInstance(m_M_AttributeSetInstance_ID, value);
			}
			m_changed = true;
		}	//	for all attributes

		//	Save Model
		if (m_changed)
		{
			Services.get(IAttributeSetInstanceBL.class).setDescription(m_masi);
			m_masi.save ();
		}
		m_M_AttributeSetInstance_ID = m_masi.getM_AttributeSetInstance_ID ();
		m_M_AttributeSetInstanceName = m_masi.getDescription();
		//
		if (mandatory.length() > 0)
		{
			FDialog.error(m_WindowNo, this, "FillMandatory", mandatory);
			return false;
		}
		return true;
	}	//	saveSelection


	/**************************************************************************
	 * 	Get Instance ID
	 * 	@return Instance ID
	 */
	public int getM_AttributeSetInstance_ID()
	{
		return m_M_AttributeSetInstance_ID;
	}	//	getM_AttributeSetInstance_ID

	/**
	 * 	Get Instance Name
	 * 	@return Instance Name
	 */
	public String getM_AttributeSetInstanceName()
	{
		return m_M_AttributeSetInstanceName;
	}	//	getM_AttributeSetInstanceName

	/**
	 * Get Locator ID
	 * @return M_Locator_ID
	 */
	public int getM_Locator_ID()
	{
		return m_M_Locator_ID;
	}

	/**
	 * 	Value Changed
	 *	@return true if changed
	 */
	public boolean isChanged()
	{
		return m_changed;
	}	//	isChanged

} //	WPAttributeDialog
