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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.ProductPriceQuery;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VComboBox;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MAttribute;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.logging.LogManager;


/**
 *	Product Attribute Table.
 *	Select one or two attributes for view/etc.
 *	
 *  @author Jorg Janke
 *  @version $Id: VAttributeGrid.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VAttributeGrid extends CPanel
	implements FormPanel, ChangeListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3207678550566202041L;

	/**
	 * 	Init
	 *	@param WindowNo
	 *	@param frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		//	Layout
		frame.getContentPane().add(this, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(confirmPanel, BorderLayout.SOUTH);
		tabbedPane.addChangeListener(this);
		confirmPanel.setActionListener(this);
		//
		tabbedPane.add(selectPanel, Msg.getMsg(Env.getCtx(), "Selection"));
		selectPanel.add(attributeLabel1, new ALayoutConstraint(0,0));
		m_attributes = MAttribute.getOfClient(Env.getCtx(), true, true);
		Vector<KeyNamePair> vector = new Vector<KeyNamePair>();
		vector.add(new KeyNamePair(0,""));
		for (int i = 0; i < m_attributes.length; i++)
			vector.add(m_attributes[i].getKeyNamePair());
		attributeCombo1 = new CComboBox(vector);
		selectPanel.add(attributeCombo1, null);
		selectPanel.add(attributeLabel2, new ALayoutConstraint(1,0));
		attributeCombo2 = new CComboBox(vector);
		selectPanel.add(attributeCombo2, null);
		//
		fillPicks();
		selectPanel.add(labelPriceList, new ALayoutConstraint(2,0));
		selectPanel.add(pickPriceList);
		selectPanel.add(labelWarehouse, new ALayoutConstraint(3,0));
		selectPanel.add(pickWarehouse);
		//
		selectPanel.setPreferredSize(new Dimension (300,200));
		//
		//	Grid
		tabbedPane.add(gridPanel, "AttributeGrid");
		modePanel.add(modeLabel);
		modePanel.add(modeCombo);
		modeCombo.addActionListener(this);
	}	//	init

	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private FormFrame 		m_frame;
	/** Product Attributes	*/
	private MAttribute[]	m_attributes = null;
	/** Setting Grid		*/
	private boolean			m_setting = false;
	/**	Logger	*/
	private static Logger log = LogManager.getLogger(VAttributeGrid.class);
	
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
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel		selectPanel = new CPanel(new ALayout());
	private CLabel		attributeLabel1 = new CLabel(Msg.getElement(Env.getCtx(), "M_Attribute_ID") + " 1");
	private CComboBox	attributeCombo1 = null;
	private CLabel		attributeLabel2 = new CLabel(Msg.getElement(Env.getCtx(), "M_Attribute_ID") + " 2");
	private CComboBox	attributeCombo2 = null;
	private CLabel 		labelPriceList = new CLabel(Msg.getElement(Env.getCtx(), "M_PriceList_ID"));
	private VComboBox 	pickPriceList = new VComboBox();
	private CLabel 		labelWarehouse = new CLabel(Msg.getElement(Env.getCtx(), "M_Warehouse_ID"));
	private VComboBox 	pickWarehouse = new VComboBox();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	//
	private CPanel		gridPanel = new CPanel(new BorderLayout());
	private CPanel		modePanel = new CPanel();
	private CLabel		modeLabel = new CLabel(Msg.getMsg(Env.getCtx(), "Mode"));
	private CComboBox	modeCombo = new CComboBox(MODES);
	
	/**
	 * 	Dispose
	 */
	@Override
	public void dispose ()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

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
			pickPriceList.addItem(new KeyNamePair (0, ""));
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair (rs.getInt(1), rs.getString(2));
				pickPriceList.addItem(kn);
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
			pickWarehouse.addItem(new KeyNamePair (0, ""));
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair kn = new KeyNamePair
					(rs.getInt("M_Warehouse_ID"), rs.getString("ValueName"));
				pickWarehouse.addItem(kn);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
	}	//	fillPicks

	
	/**
	 * 	Change Listener
	 *	@param e event
	 */
	@Override
	public void stateChanged (ChangeEvent e)
	{
		if (e.getSource() != tabbedPane)
			return;
		if (tabbedPane.getSelectedIndex() == 1)
			createGrid();
	}	//	stateChanged

	/**
	 * 	Action Performed
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
	//	log.debug(e.toString());
		if (e.getSource() == modeCombo)
			createGrid();
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (tabbedPane.getSelectedIndex() == 0)
				createGrid();
			else
				gridOK();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			m_frame.dispose();
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
		m_frame.dispose();
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
		int indexAttr1 = attributeCombo1.getSelectedIndex();
		int indexAttr2 = attributeCombo2.getSelectedIndex();
		if (indexAttr1 == indexAttr2)
		{
			log.warn("Same Attribute Selected");
			tabbedPane.setSelectedIndex(0);
			return;
		}
		m_setting = true;
		m_M_PriceList_Version_ID = 0;
		KeyNamePair pl = (KeyNamePair)pickPriceList.getSelectedItem();
		if (pl != null)
			m_M_PriceList_Version_ID = pl.getKey();
		m_M_Warehouse_ID = 0;
		KeyNamePair wh = (KeyNamePair)pickWarehouse.getSelectedItem();
		if (wh != null)
			m_M_Warehouse_ID = wh.getKey();
		
		//	x dimension
		int cols = 2;
		I_M_AttributeValue[] xValues = null;
		if (indexAttr1 > 0)
			xValues = m_attributes[indexAttr1-1].getMAttributeValues(null);
		if (xValues != null)
		{
			cols = xValues.length;
			log.info("X - " + m_attributes[indexAttr1-1].getName() + " #" + xValues.length);
		}
		
		//	y dimension
		int rows = 2;
		I_M_AttributeValue[] yValues = null;
		if (indexAttr2 > 0)
			yValues = m_attributes[indexAttr2-1].getMAttributeValues(null);
		if (yValues != null)
		{
			rows = yValues.length;
			log.info("Y - " + m_attributes[indexAttr2-1].getName() + " #" + yValues.length);
		}
		
		//
		gridPanel.removeAll();
		CPanel grid = new CPanel(new GridLayout(rows, cols, 5,5));
		gridPanel.add(modePanel, BorderLayout.NORTH);
		gridPanel.add(new CScrollPane(grid), BorderLayout.CENTER);
		//
		log.info("Rows=" + rows + " - Cols=" + cols);
		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				I_M_AttributeValue xValue = null;
				if (xValues != null)
					xValue = xValues[col];
				I_M_AttributeValue yValue = null;
				if (yValues != null)
					yValue = yValues[row];
			//	log.debug("Row=" + row + " - Col=" + col);
				//
				if (row == 0 && col == 0)
				{
					CPanel descr = new CPanel(new GridLayout(2,1,0,0));
					if (xValues != null)
						descr.add(new JLabel(m_attributes[indexAttr1-1].getName(),JLabel.TRAILING));
					if (yValues != null)
						descr.add(new JLabel(m_attributes[indexAttr2-1].getName()));
					grid.add(descr);
				}
				else if (row == 0)	//	column labels
				{
					if (xValue != null)
					{
						grid.add(new JLabel(xValue.getName(), JLabel.TRAILING));
					}
					else
						grid.add(new JLabel());
				}
				else if (col == 0)	//	row labels
				{
					if (yValue != null)
						grid.add(new JLabel(yValue.getName()));
					else
						grid.add(new JLabel());
				}
				else
				{
					grid.add(getGridElement (xValue, yValue));
				}
			}
		}
		//
		tabbedPane.setSelectedIndex(1);
		m_setting = false;
		m_frame.pack();
	}	//	createGrid
	
	/**
	 * 	Get Grid Element
	 *	@param xValue X value
	 *	@param yValue Y value
	 *	@return Panel with Info
	 */
	private CPanel getGridElement (I_M_AttributeValue xValue, I_M_AttributeValue yValue)
	{
		CPanel element = new CPanel();
		element.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		element.setLayout(new BoxLayout(element, BoxLayout.Y_AXIS));
		
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
			log.error(sql, e);
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
	private void addProduct(CPanel element, MProduct product)
	{
		Insets ii = new Insets(2,4,2,4);
		int M_Product_ID = product.getM_Product_ID();
		CPanel pe = new CPanel();
		pe.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		pe.setLayout(new GridBagLayout());
		
		//	Product Value - Price
		pe.add(new JLabel(product.getValue()), new GridBagConstraints(0,0, 1,1, 0,0, 
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, ii, 0,0));
		String formatted = "";
		if (m_M_PriceList_Version_ID > 0)
		{
			final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(Env.getCtx(), m_M_PriceList_Version_ID, I_M_PriceList_Version.class, ITrx.TRXNAME_None);
			final I_M_ProductPrice pp = ProductPriceQuery.retrieveMainProductPriceIfExists(plv, M_Product_ID)
					.orElse(null);
			if (pp != null)
			{
				BigDecimal price = pp.getPriceStd();
				formatted = m_price.format(price);
			}
			else
			{
				formatted = "-";
			}
		}
		pe.add(new JLabel(formatted, JLabel.RIGHT), new GridBagConstraints(1,0, 1,1, .5,0, 
			GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, ii, 0,0));
		
		//	Product Name - Qty
		pe.add(new JLabel(product.getName()), new GridBagConstraints(0,1, 1,1, 0,0, 
			GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, ii, 0,0));
		formatted = "";
		if (m_M_Warehouse_ID != 0)
		{
			BigDecimal qty = MStorage.getQtyAvailable(m_M_Warehouse_ID, M_Product_ID, 0, null);
			if (qty == null)
				formatted = "-";
			else
				formatted = m_qty.format(qty);
		}
		pe.add(new JLabel(formatted, JLabel.RIGHT), new GridBagConstraints(1,1, 1,1, .5,0, 
			GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, ii, 0,0));
		//
		element.add(pe);
	}	//	addProduct
}	//	VAttributeTable
