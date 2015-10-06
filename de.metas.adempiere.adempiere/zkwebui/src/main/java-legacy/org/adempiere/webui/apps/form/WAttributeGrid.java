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
package org.adempiere.webui.apps.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Tab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.Tabpanels;
import org.adempiere.webui.component.Tabs;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.MAttribute;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorage;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Vbox;


/**
 *	Product Attribute Table.
 *	Select one or two attributes for view/etc.
 *	
 *  @author Jorg Janke
 *  @version $Id: VAttributeGrid.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WAttributeGrid extends ADForm implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 687630618195901592L;

	/**
	 * 	Init
	 *	@param WindowNo
	 *	@param frame
	 */
	protected void initForm()
    {
		m_attributes = MAttribute.getOfClient(Env.getCtx(), true, true);
		KeyNamePair[] vector = new KeyNamePair[m_attributes.length+1];
		vector[0] = new KeyNamePair(0, "");
		for (int i = 0; i < m_attributes.length; i++)
			vector[i+1] = m_attributes[i].getKeyNamePair();
		
		attributeCombo1 = new Listbox(vector);
		attributeCombo1.setMold("select");
		attributeCombo1.setSelectedIndex(0);
		
		attributeCombo2 = new Listbox(vector);
		attributeCombo2.setMold("select");
		attributeCombo2.setSelectedIndex(0);
		
		pickPriceList.setMold("select");
		pickWarehouse.setMold("select");
		
		fillPicks();
		
		for(int i = 0; i < MODES.length; i++)
			modeCombo.appendItem(MODES[i], MODES[i]);
		modeCombo.setMold("select");
		
		tabbox.setWidth("100%");
		tabbox.setHeight("85%");
		tabbox.appendChild(tabs);
		tabbox.appendChild(tabpanels);
		tabbox.addEventListener(Events.ON_SELECT, this);
		
		Grid gridSelection = new Grid();
		gridSelection.setWidth("500px");
		gridSelection.setStyle("margin:0; padding:0;");
		gridSelection.makeNoStrip();
		gridSelection.setOddRowSclass("even");
		
		gridView.setWidth("100%");
		gridView.setHeight("100%");
		gridView.setFixedLayout(true);
        
		Rows rows = new Rows();
		gridSelection.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(attributeLabel1);
		row.appendChild(div);
		row.appendChild(attributeCombo1);
		attributeCombo1.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(attributeLabel2);
		row.appendChild(div);
		row.appendChild(attributeCombo2);
		attributeCombo2.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(labelPriceList);
		row.appendChild(div);
		row.appendChild(pickPriceList);
		pickPriceList.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(labelWarehouse);
		row.appendChild(div);
		row.appendChild(pickWarehouse);
		pickWarehouse.setWidth("100%");
		
		div = new Div();
		div.setAlign("center");
		div.appendChild(gridSelection);
		
		Tabpanel tabSelectionPanel = new Tabpanel();
		tabSelectionPanel.appendChild(div);

		Tab tabSelection = new Tab(Msg.getMsg(Env.getCtx(), "Selection"));
		tabpanels.appendChild(tabSelectionPanel);
		tabs.appendChild(tabSelection);

		div = new Div();
		div.setAlign("center");
		div.appendChild(modeLabel);
		div.appendChild(modeCombo);
		modeCombo.addEventListener(Events.ON_CHANGE, this);
		
		Vbox vbAttributeGrid = new Vbox();
		vbAttributeGrid.appendChild(div);
		vbAttributeGrid.appendChild(gridView);

		Tabpanel tabAttributeGridPanel = new Tabpanel();
		tabAttributeGridPanel.appendChild(vbAttributeGrid);
		
		Tab tabAttributeGrid = new Tab(Msg.getMsg(Env.getCtx(), "AttributeGrid"));
		tabpanels.appendChild(tabAttributeGridPanel);
		tabs.appendChild(tabAttributeGrid);
		
		this.setWidth("100%");
		this.setHeight("100%");
		this.appendChild(tabbox);
		tabbox.addEventListener(Events.ON_SELECT, this);
		this.appendChild(confirmPanel);
		confirmPanel.addActionListener(this);
	}	//	init

	/**	Window No			*/
//	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
//	private FormFrame 		m_frame;
	/** Product Attributes	*/
	private MAttribute[]	m_attributes = null;
	/** Setting Grid		*/
	private boolean			m_setting = false;
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (WAttributeGrid.class);
	
	/**	Modes				*/
	private static String[]	MODES = new String[]{
		Msg.getMsg(Env.getCtx(), "ModeView")
	//	,Msg.getMsg(Env.getCtx(), "ModePO")
	//	,Msg.getMsg(Env.getCtx(), "ModePrice")
	};
	private static final int	MODE_VIEW = 0;
	private static final int	MODE_PO = 0;
	private static final int	MODE_PRICE = 0;
	
	/** Price List Version	*/
	private int				m_M_PriceList_Version_ID = 0;
	private DecimalFormat	m_price = DisplayType.getNumberFormat(DisplayType.CostPrice);
	/** Warehouse			*/
	private int				m_M_Warehouse_ID = 0;
	private DecimalFormat	m_qty = DisplayType.getNumberFormat(DisplayType.Quantity);
	
	/** UI			**/
	private Tabbox 		tabbox = new Tabbox();
	private Tabs 		tabs = new Tabs();
	private Tabpanels 	tabpanels = new Tabpanels();
	
	private Label		attributeLabel1 = new Label(Msg.getElement(Env.getCtx(), "M_Attribute_ID") + " 1");
	private Listbox		attributeCombo1 = null;
	private Label		attributeLabel2 = new Label(Msg.getElement(Env.getCtx(), "M_Attribute_ID") + " 2");
	private Listbox		attributeCombo2 = null;
	private Label 		labelPriceList = new Label(Msg.getElement(Env.getCtx(), "M_PriceList_ID"));
	private Listbox 	pickPriceList = new Listbox();
	private Label 		labelWarehouse = new Label(Msg.getElement(Env.getCtx(), "M_Warehouse_ID"));
	private Listbox 	pickWarehouse = new Listbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	//
	private Grid 		gridView = new Grid();
//	private CPanel		gridPanel = new CPanel(new BorderLayout());
//	private CPanel		modePanel = new CPanel();
	private Label		modeLabel = new Label(Msg.getMsg(Env.getCtx(), "Mode"));
	private Listbox 	modeCombo = new Listbox();//MODES);
	
	/**
	 * 	Dispose
	 */
//	public void dispose ()
//	{
//		if (m_frame != null)
//			m_frame.dispose();
//		m_frame = null;
//	}	//	dispose

	/**
	 *	Fill Picks with values
	 */
	private void fillPicks ()
	{
		//	Price List
		String sql = "SELECT M_PriceList_Version.M_PriceList_Version_ID,"
			+ " M_PriceList_Version.Name || ' (' || c.Iso_Code || ')' AS ValueName "
			+ "FROM M_PriceList_Version, M_PriceList pl, C_Currency c "
			+ "WHERE M_PriceList_Version.M_PriceList_ID=pl.M_PriceList_ID"
			+ " AND pl.C_Currency_ID=c.C_Currency_ID"
			+ " AND M_PriceList_Version.IsActive='Y' AND pl.IsActive='Y'";
		//	Add Access & Order
		sql = Env.getUserRolePermissions().addAccessSQL (sql, "M_PriceList_Version", true, false)	// fully qualidfied - RO 
			+ " ORDER BY M_PriceList_Version.Name";
		try
		{
			pickPriceList.appendItem("", 0);
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair (rs.getInt(1), rs.getString(2));
				pickPriceList.appendItem(kn.getName(), kn.getKey());
			}
			rs.close();
			pstmt.close();

			//	Warehouse
			sql = "SELECT M_Warehouse_ID, Value || ' - ' || Name AS ValueName "
				+ "FROM M_Warehouse "
				+ "WHERE IsActive='Y'";
			sql = Env.getUserRolePermissions().addAccessSQL (sql,
					"M_Warehouse", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO)
				+ " ORDER BY Value";
			pickWarehouse.appendItem("", 0);
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair
					(rs.getInt("M_Warehouse_ID"), rs.getString("ValueName"));
				pickWarehouse.appendItem(kn.getName(), kn.getKey());
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}	//	fillPicks

	public void onEvent(Event e)
	{
		if (e.getTarget() instanceof Tab)
		{
			if(tabbox.getSelectedIndex() == 1)
				createGrid();
		}
		else if(e.getTarget() ==  modeCombo)
			createGrid();
		else if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			if (tabbox.getSelectedIndex() == 0)
				createGrid();
			else
				gridOK();
		}
		else if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			onClose();
	}	//	actionPerformed
	
	private void gridOK()
	{
		int mode = modeCombo.getSelectedIndex();
		//	Create PO
		if (mode == MODE_PO)
		{
			createPO();
			modeCombo.setSelectedIndex(MODE_VIEW);
			return;
		}
		//	Update Prices
		else if (mode == MODE_PRICE)
		{
			updatePrices();
			modeCombo.setSelectedIndex(MODE_VIEW);
			return;
		}
		else if (mode == MODE_VIEW)
			;
		onClose();
	}	//	gridOK
	
	private void createPO()
	{
		
	}
	private void updatePrices()
	{
		
	}
	/**
	 * 	Create Grid
	 */
	private void createGrid()
	{
		if (attributeCombo1 == null || m_setting)
			return;		//	init
		
		Object attr1 = attributeCombo1.getSelectedItem().getValue();
		Object attr2 = attributeCombo2.getSelectedItem().getValue();
		
		if (attr1.equals(attr2))
		{
			FDialog.warn(m_WindowNo, "Same Attribute Selected", getTitle());
			log.warning("Same Attribute Selected");
			tabbox.setSelectedIndex(0);
			return;
		}
		m_setting = true;
		m_M_PriceList_Version_ID = 0;
		
		ListItem pl = pickPriceList.getSelectedItem();
		if (pl != null)
			m_M_PriceList_Version_ID = Integer.valueOf(pl.getValue().toString());
		m_M_Warehouse_ID = 0;
		ListItem wh = pickWarehouse.getSelectedItem();
		if (wh != null)
			m_M_Warehouse_ID = Integer.valueOf(wh.getValue().toString());
		
		//	x dimension
		int noOfCols = 2;
		int indexAttr1 = 0;
		I_M_AttributeValue[] xValues = null;
		if (attr1 != null)
		{
			int value = Integer.parseInt(attr1.toString());
			for(int i = 0; i < m_attributes.length; i++)
			{
				if(m_attributes[i].getKeyNamePair().getKey() == value)
				{
					xValues = m_attributes[i].getMAttributeValues(null);
					indexAttr1 = i;
					break;
				}
			}
		}
		if (xValues != null)
			noOfCols = xValues.length;
		
		//	y dimension
		int noOfRows = 2;
		int indexAttr2 = 0;
		I_M_AttributeValue[] yValues = null;
		if (attr2 != null)
		{
			int value = Integer.parseInt(attr2.toString());
			for(int i = 0; i < m_attributes.length; i++)
			{
				if(m_attributes[i].getKeyNamePair().getKey() == value)
				{
					yValues = m_attributes[i].getMAttributeValues(null);
					indexAttr2 = i;
					break;
				}
			}
		}
		
		if (yValues != null)
			noOfRows = yValues.length;
		
		gridView.getChildren().clear();
		
		Rows rows = new Rows();
		gridView.appendChild(rows);
		
		log.info("Rows=" + noOfRows + " - Cols=" + noOfCols);
		for (int rowIndex = 0; rowIndex < noOfRows; rowIndex++)
		{
			Row row = new Row();
			row.setWidth("100%");
			rows.appendChild(row);
			
			for (int colIndex = 0; colIndex < noOfCols; colIndex++)
			{
				I_M_AttributeValue xValue = null;
				if (xValues != null)
					xValue = xValues[colIndex];
				I_M_AttributeValue yValue = null;
				if (yValues != null)
					yValue = yValues[rowIndex];
				
				if (rowIndex == 0 && colIndex == 0)
				{
					Vbox descr = new Vbox();
					descr.setWidth("100%");
					if (xValues != null)
					{
						Div div = new Div();
						div.setAlign("right");
						div.appendChild(new Label(m_attributes[indexAttr1].getName()));
						descr.appendChild(div);
					}
					if (yValues != null)
						descr.appendChild(new Label(m_attributes[indexAttr2].getName()));
					
					row.appendChild(descr);
				}
				else if (rowIndex == 0)	//	column labels
				{
					if (xValue != null)
					{
						Div div = new Div();
						div.setAlign("center");
						div.appendChild(new Label(xValue.getName()));
						row.appendChild(div);
					}
					else
						row.appendChild(new Label());
				}
				else if (colIndex == 0)	//	row labels
				{
					if (yValue != null)
						row.appendChild(new Label(yValue.getName()));
					else
						row.appendChild(new Label());
				}
				else
				{
					row.appendChild(getGridElement (xValue, yValue));
				}
			}
		}
		
		tabbox.setSelectedIndex(1);
		m_setting = false;
	}	//	createGrid
	
	/**
	 * 	Get Grid Element
	 *	@param xValue X value
	 *	@param yValue Y value
	 *	@return Panel with Info
	 */
	private Panel getGridElement (I_M_AttributeValue xValue, I_M_AttributeValue yValue)
	{
		Panel element = new Panel();
		element.setStyle("border-width: thin; border-color: black;");
		
		String sql = "SELECT * FROM M_Product WHERE IsActive='Y'";
		//	Product Attributes
		if (xValue != null)
			sql += " AND M_AttributeSetInstance_ID IN "
				+ "(SELECT M_AttributeSetInstance_ID "
				+ "FROM M_AttributeInstance "
				+ "WHERE M_Attribute_ID=" + xValue.getM_Attribute_ID()
				+ " AND M_AttributeValue_ID=" + xValue.getM_AttributeValue_ID() + ")"; 
		if (yValue != null)
			sql += " AND M_AttributeSetInstance_ID IN "
				+ "(SELECT M_AttributeSetInstance_ID "
				+ "FROM M_AttributeInstance "
				+ "WHERE M_Attribute_ID=" + yValue.getM_Attribute_ID()
				+ " AND M_AttributeValue_ID=" + yValue.getM_AttributeValue_ID() + ")"; 
		sql = Env.getUserRolePermissions().addAccessSQL(sql, "M_Product", 
			IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		PreparedStatement pstmt = null;
		int noProducts = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MProduct product = new MProduct(Env.getCtx(), rs, null);
				addProduct (element, product);
				noProducts++;
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		int mode = modeCombo.getSelectedIndex();
		//	No Products
		if (noProducts == 0 && mode == MODE_VIEW)
		{
		//	CButton button = ConfirmPanel.createNewButton(true);
		//	button.addActionListener(this);
		//	element.add(button);
		}
		else	//	Additional Elements
		{
			if (mode == MODE_PRICE)
			{
				//	Price Field
			}
			else if (mode == MODE_PO)
			{
				//	Qty Field
			}
		}		
		return element;
	}	//	getGridElement
	
	/**
	 * 	Add Product
	 *	@param element panel
	 *	@param product product
	 */
	private void addProduct(Panel element, MProduct product)
	{
		int M_Product_ID = product.getM_Product_ID();
		Vbox pe = new Vbox();
		pe.setStyle("border-width: thin; border-color: blue;");
		
		//	Product Value - Price
		pe.appendChild(new Label(product.getValue()));
		String formatted = "";
		if (m_M_PriceList_Version_ID != 0)
		{
			MProductPrice pp = MProductPrice.get(Env.getCtx(), m_M_PriceList_Version_ID, M_Product_ID, null);
			if (pp != null)
			{
				BigDecimal price = pp.getPriceStd();
				formatted = m_price.format(price);
			}
			else
				formatted = "-";
		}
		pe.appendChild(new Label(formatted));
		
		//	Product Name - Qty
		pe.appendChild(new Label(product.getName()));
		formatted = "";
		if (m_M_Warehouse_ID != 0)
		{
			BigDecimal qty = MStorage.getQtyAvailable(m_M_Warehouse_ID, M_Product_ID, 0, null);
			if (qty == null)
				formatted = "-";
			else
				formatted = m_qty.format(qty);
		}
		pe.appendChild(new Label(formatted));
		//
		element.appendChild(pe);
	}	//	addProduct
}	//	VAttributeTable
