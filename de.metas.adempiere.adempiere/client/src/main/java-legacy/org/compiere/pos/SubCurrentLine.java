/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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

package org.compiere.pos;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.compiere.apps.ADialog;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MWarehousePrice;
import org.compiere.model.PO;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * Current Line Sub Panel
 * 
 * @author OpenXpertya
 * Based on Modified Original Code, Revised and Optimized
 *         *Copyright Jorg Janke
 * red1 - [2093355 ] Small bugs in OpenXpertya POS
 */
public class SubCurrentLine extends PosSubPanel implements ActionListener, FocusListener, ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4023538043556457231L;

	/**
	 * Constructor
	 * 
	 * @param posPanel POS Panel
	 */
	public SubCurrentLine(PosBasePanel posPanel) {
		super(posPanel);
	}

	private CButton f_up;
	private CButton f_delete;
	private CButton f_down;
	//
	private CButton f_plus;
	private CButton f_minus;
	private PosTextField f_price;
	private PosTextField f_quantity;
	protected PosTextField	f_name;
	private CButton			f_bSearch;
	private int orderLineId = 0;
	

	/**	The Product					*/
	private MProduct		m_product = null; 

	/** Warehouse					*/
	private int 			m_M_Warehouse_ID;
	/** PLV							*/
	private int 			m_M_PriceList_Version_ID;
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(SubCurrentLine.class);
	

	/** The Table					*/
	PosTable		m_table;
	/** The Query SQL				*/
	private String			m_sql;
	/**	Logger			*/
	
	/**	Table Column Layout Info			*/
	private static ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "C_OrderLine_ID", IDColumn.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Qty"), "QtyOrdered", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_UOM_ID"), "UOMSymbol", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "PriceActual"), "PriceActual", BigDecimal.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "LineNetAmt"), "LineNetAmt", BigDecimal.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_Tax_ID"), "TaxIndicator", String.class), 
	};
	/**	From Clause							*/
	private static String s_sqlFrom = "C_Order_LineTax_v";
	/** Where Clause						*/
	private static String s_sqlWhere = "C_Order_ID=? AND LineNetAmt <> 0"; 
	
	/**
	 * Initialize
	 */ 
	public void init() {
	
		//	Content
		setLayout(new MigLayout("fill, ins 0 0"));
		
		String buttonSize = "w 50!, h 50!,";
		//
		f_bSearch = createButtonAction ("Product", KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
		add (f_bSearch, buttonSize );
		
		CLabel productLabel = new CLabel(Msg.translate(Env.getCtx(), "M_Product_ID"));
		add(productLabel, "split 2, spanx 4, flowy, h 15");
		
		f_name = new PosTextField(Msg.translate(Env.getCtx(), "M_Product_ID"), p_posPanel, p_pos.getOSK_KeyLayout_ID());
		f_name.setName("Name");
		f_name.addActionListener(this);
		f_name.addFocusListener(this);
		f_name.requestFocusInWindow();
		
		add (f_name, " growx, h 30:30:, wrap");

		m_table = new PosTable();
		CScrollPane scroll = new CScrollPane(m_table);
		m_sql = m_table.prepareTable (s_layout, s_sqlFrom, 
				s_sqlWhere, false, "C_Order_LineTax_v")
				+ " ORDER BY Line";
		//	m_table.addMouseListener(this);
		m_table.getSelectionModel().addListSelectionListener(this);
		m_table.setColumnVisibility(m_table.getColumn(0), false);
		m_table.getColumn(1).setPreferredWidth(175);
		m_table.getColumn(2).setPreferredWidth(75);
		m_table.getColumn(3).setPreferredWidth(30);
		m_table.getColumn(4).setPreferredWidth(75);
		m_table.getColumn(5).setPreferredWidth(75);
		m_table.getColumn(6).setPreferredWidth(30);
		m_table.setFocusable(false);
		m_table.setFillsViewportHeight( true ); //@Trifon
		m_table.growScrollbars();

		add (scroll, "growx, spanx, growy, pushy, h 200:300:");
		
		f_up = createButtonAction("Previous", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		add (f_up, buttonSize);
		f_down = createButtonAction("Next", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		add (f_down, buttonSize);

		
		f_delete = createButtonAction("Cancel", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Event.SHIFT_MASK));
		add (f_delete, buttonSize);
		
		//
		f_minus = createButtonAction("Minus", null);
		add(f_minus, buttonSize);


		
		CLabel qtyLabel = new CLabel(Msg.translate(Env.getCtx(), "QtyOrdered"));
		add(qtyLabel, "split 2, flowy, h 15");

		//
		f_quantity = new PosTextField(Msg.translate(Env.getCtx(), "QtyOrdered"),
				p_posPanel,p_pos.getOSNP_KeyLayout_ID(), DisplayType.getNumberFormat(DisplayType.Quantity));
		f_quantity.setHorizontalAlignment(JTextField.TRAILING);
		f_quantity.addActionListener(this);
		add(f_quantity, "h 30:30:, w 100");
		setQty(Env.ONE);
		//
		f_plus = createButtonAction("Plus", null);
		add(f_plus, buttonSize);
		
		
		CLabel priceLabel = new CLabel(Msg.translate(Env.getCtx(), "PriceActual"));
		add(priceLabel, "split 2, flowy, h 15");
		
		//
		f_price = new PosTextField(Msg.translate(Env.getCtx(), "PriceActual"),
				p_posPanel,p_pos.getOSNP_KeyLayout_ID(), DisplayType.getNumberFormat(DisplayType.Amount));
		f_price.addActionListener(this);
		f_price.setHorizontalAlignment(JTextField.TRAILING);
		add(f_price, "h 30, w 100, wrap");
		setPrice(Env.ZERO);
		
		enableButtons();
	} //init


	/**
	 * Dispose - Free Resources
	 */
	public void dispose() {
		super.dispose();
	} //	dispose

	/**
	 * Action Listener
	 * 
	 * @param e event
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action == null || action.length() == 0)
			return;
		log.info( "SubCurrentLine - actionPerformed: " + action);
		
		//	Plus
		if (action.equals("Plus"))
		{
			if ( orderLineId > 0 )
			{
				MOrderLine line = new MOrderLine(p_ctx, orderLineId, null);
				if ( line != null )
				{
					line.setQty(line.getQtyOrdered().add(Env.ONE));
					line.saveEx();
					p_posPanel.updateInfo();
				}
			}

		}
		//	Minus
		else if (action.equals("Minus"))
		{
			if ( orderLineId > 0 )
			{
				MOrderLine line = new MOrderLine(p_ctx, orderLineId, null);
				if ( line != null )
				{
					line.setQty(line.getQtyOrdered().subtract(Env.ONE));
					line.saveEx();
					p_posPanel.updateInfo();
				}
			}

		}
		//	VNumber
		else if (e.getSource() == f_price)		{
			MOrderLine line = new MOrderLine(p_ctx, orderLineId, null);
			if ( line != null )
			{
				line.setQty(new BigDecimal(f_price.getValue().toString()));
				line.saveEx();
				p_posPanel.updateInfo();
			}
		}
		else if (e.getSource() == f_quantity && orderLineId > 0 )
		{
			MOrderLine line = new MOrderLine(p_ctx, orderLineId, null);
			if ( line != null )
			{
				line.setQty(new BigDecimal(f_quantity.getValue().toString()));
				line.saveEx();
				p_posPanel.updateInfo();
			}
		}
		//	Product
		if (action.equals("Product"))
		{
			setParameter();
			QueryProduct qt = new QueryProduct(p_posPanel);
			qt.setQueryData(m_M_PriceList_Version_ID, m_M_Warehouse_ID);
			qt.setVisible(true);
			findProduct();
			
			int row = m_table.getSelectedRow();
			if (row < 0) row = 0;
			m_table.getSelectionModel().setSelectionInterval(row, row);
			// https://sourceforge.net/tracker/?func=detail&atid=879332&aid=3121975&group_id=176962
			m_table.scrollRectToVisible(m_table.getCellRect(row, 1, true)); //@Trifon - BF[3121975]
		}
		//	Name
		else if (e.getSource() == f_name)
			findProduct();
		if ("Previous".equalsIgnoreCase(e.getActionCommand()))
		{
			int rows = m_table.getRowCount();
			if (rows == 0) 
				return;
			int row = m_table.getSelectedRow();
			row--;
			if (row < 0)
				row = 0;
			m_table.getSelectionModel().setSelectionInterval(row, row);
			// https://sourceforge.net/tracker/?func=detail&atid=879332&aid=3121975&group_id=176962
			m_table.scrollRectToVisible(m_table.getCellRect(row, 1, true)); //@Trifon - BF[3121975]
			return;
		}
		else if ("Next".equalsIgnoreCase(e.getActionCommand()))
		{
			int rows = m_table.getRowCount();
			if (rows == 0)
				return;
			int row = m_table.getSelectedRow();
			row++;
			if (row >= rows)
				row = rows - 1;
			m_table.getSelectionModel().setSelectionInterval(row, row);
			// https://sourceforge.net/tracker/?func=detail&atid=879332&aid=3121975&group_id=176962
			m_table.scrollRectToVisible(m_table.getCellRect(row, 1, true)); //@Trifon - BF[3121975]
			return;
		}
		//	Delete
		else if (action.equals("Cancel"))
		{
			int rows = m_table.getRowCount();
			if (rows != 0)
			{
				int row = m_table.getSelectedRow();
				if (row != -1)
				{
					if ( p_posPanel.m_order != null )
						p_posPanel.m_order.deleteLine(m_table.getSelectedRowKey());
					setQty(null);
					setPrice(null);
					
					orderLineId = 0;
				}
			}
		}
		p_posPanel.updateInfo();
	} //	actionPerformed
	
	/**
	 * 	Update Table
	 *	@param order order
	 */
	public void updateTable (PosOrderModel order)
	{
		int C_Order_ID = 0;
		if (order != null)
			C_Order_ID = order.getC_Order_ID();
		if (C_Order_ID == 0)
		{
			p_posPanel.f_curLine.m_table.loadTable(new PO[0]);
			p_posPanel.f_order.setSums(null);
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (m_sql, null);
			pstmt.setInt (1, C_Order_ID);
			rs = pstmt.executeQuery ();
			m_table.loadTable(rs);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, m_sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		for ( int i = 0; i < m_table.getRowCount(); i ++ )
		{
			IDColumn key = (IDColumn) m_table.getModel().getValueAt(i, 0);
			if ( key != null && orderLineId > 0 && key.getRecord_ID() == orderLineId )
			{
				m_table.getSelectionModel().setSelectionInterval(i, i);
				break;
			}
		}
		
		enableButtons();

		p_posPanel.f_order.setSums(order);
		
	}	//	updateTable
	
	private void enableButtons()
	{
		boolean enabled = true;
		if ( m_table == null || m_table.getRowCount() == 0 || m_table.getSelectedRowKey() == null )
		{
			enabled = false;
		}
		f_down.setEnabled(enabled);
		f_up.setEnabled(enabled);
		f_delete.setEnabled(enabled);
		f_minus.setEnabled(enabled);
		f_plus.setEnabled(enabled);
		f_quantity.setEnabled(enabled);
		f_price.setEnabled(enabled);
	}
	
	/**
	 * 	Set Query Parameter
	 */
	private void setParameter()
	{
		//	What PriceList ?
		m_M_Warehouse_ID = p_pos.getM_Warehouse_ID();
		m_M_PriceList_Version_ID = p_posPanel.f_order.getM_PriceList_Version_ID();
	}	//	setParameter

	/**
	 * Set Price
	 * 
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		if (price == null)
			price = Env.ZERO;
		f_price.setValue(price);
		boolean rw = Env.ZERO.compareTo(price) == 0 || p_pos.isModifyPrice();
		f_price.setEditable(rw);
	} //	setPrice

	/**
	 * Get Price
	 * 
	 * @return price
	 */
	public BigDecimal getPrice() {
		return (BigDecimal) f_price.getValue();
	} //	getPrice

	/**
	 * Set Qty
	 * 
	 * @param qty
	 */
	public void setQty(BigDecimal qty) {
		f_quantity.setValue(qty);
	} //	setQty

	/**
	 * Get Qty
	 * 
	 * @return qty
	 */
	public BigDecimal getQty() {
		return (BigDecimal) f_quantity.getValue();
	} //	getQty

	/***************************************************************************
	 * New Line
	 */
	public void newLine() {
		setM_Product_ID(0);
		setQty(Env.ONE);
		setPrice(Env.ZERO);
		orderLineId = 0;
		f_name.requestFocusInWindow();
	} //	newLine

	/**
	 * Save Line
	 * 
	 * @return true if saved
	 */
	public boolean saveLine() {
		MProduct product = getProduct();
		if (product == null)
			return false;
		BigDecimal QtyOrdered = (BigDecimal) f_quantity.getValue();
		BigDecimal PriceActual = (BigDecimal) f_price.getValue();
		
		if ( p_posPanel.m_order == null )
		{
			p_posPanel.m_order = PosOrderModel.createOrder(p_posPanel.p_pos, p_posPanel.f_order.getBPartner());
		}
		
		MOrderLine line = null;
		
		if ( p_posPanel.m_order != null )
		{
			line = p_posPanel.m_order.createLine(product, QtyOrdered, PriceActual);

			if (line == null)
				return false;
			if (!line.save())
				return false;
		}
		
		orderLineId = line.getC_OrderLine_ID();
		setM_Product_ID(0);
		//
		return true;
	} //	saveLine


	/**
	 * 	Get Product
	 *	@return product
	 */
	public MProduct getProduct()
	{
		return m_product;
	}	//	getProduct
	
	/**
	 * 	Set Price for defined product 
	 */
	public void setPrice()
	{
		if (m_product == null)
			return;
		//
		setParameter();
		MWarehousePrice result = MWarehousePrice.get (m_product,
			m_M_PriceList_Version_ID, m_M_Warehouse_ID, null);
		if (result != null)
			p_posPanel.f_curLine.setPrice(result.getPriceStd());
		else
			p_posPanel.f_curLine.setPrice(Env.ZERO);
	}	//	setPrice
	


	/**************************************************************************
	 * 	Find/Set Product & Price
	 */
	private void findProduct()
	{
		String query = f_name.getText();
		if (query == null || query.length() == 0)
			return;
		query = query.toUpperCase();
		//	Test Number
		boolean allNumber = true;
		try
		{
			Integer.getInteger(query);
		}
		catch (Exception e)
		{
			allNumber = false;
		}
		String Value = query;
		String Name = query;
		String UPC = (allNumber ? query : null);
		String SKU = (allNumber ? query : null);
		
		MWarehousePrice[] results = null;
		setParameter();
		//
		results = MWarehousePrice.find (p_ctx,
			m_M_PriceList_Version_ID, m_M_Warehouse_ID,
			Value, Name, UPC, SKU, null);
		
		//	Set Result
		if (results.length == 0)
		{
			String message = Msg.translate(p_ctx,  "search.product.notfound");
			ADialog.warn(0, p_posPanel, message + query);
			setM_Product_ID(0);
			p_posPanel.f_curLine.setPrice(Env.ZERO);
		}
		else if (results.length == 1)
		{
			setM_Product_ID(results[0].getM_Product_ID());
			setQty(Env.ONE);
			f_name.setText(results[0].getName());
			p_posPanel.f_curLine.setPrice(results[0].getPriceStd());
			saveLine();
		}
		else	//	more than one
		{
			QueryProduct qt = new QueryProduct(p_posPanel);
			qt.setResults(results);
			qt.setQueryData(m_M_PriceList_Version_ID, m_M_Warehouse_ID);
			qt.setVisible(true);
		}
	}	//	findProduct


	/**************************************************************************
	 * 	Set Product
	 *	@param M_Product_ID id
	 */
	public void setM_Product_ID (int M_Product_ID)
	{
		log.fine( "PosSubProduct.setM_Product_ID=" + M_Product_ID);
		if (M_Product_ID <= 0)
			m_product = null;
		else
		{
			m_product = MProduct.get(p_ctx, M_Product_ID);
			if (m_product.get_ID() == 0)
				m_product = null;
		}
		//	Set String Info
		if (m_product != null)
		{
			f_name.setText(m_product.getName());
			f_name.setToolTipText(m_product.getDescription());
		}
		else
		{
			f_name.setText(null);
			f_name.setToolTipText(null);
		}
	}	//	setM_Product_ID
	
	/**
	 * 	Focus Gained
	 *	@param e
	 */
	public void focusGained (FocusEvent e)
	{

		
	}	//	focusGained
		

	/**
	 * 	Focus Lost
	 *	@param e
	 */
	public void focusLost (FocusEvent e)
	{
		if (e.isTemporary())
			return;
		log.info( "PosSubProduct - focusLost");
		findProduct();
		
		p_posPanel.updateInfo();
	}	//	focusLost


	public void valueChanged(ListSelectionEvent e) {
		if ( e.getValueIsAdjusting() )
			return;

		int row = m_table.getSelectedRow();
		if (row != -1 )
		{
			Object data = m_table.getModel().getValueAt(row, 0);
			if ( data != null )
			{
				Integer id = (Integer) ((IDColumn)data).getRecord_ID();
				orderLineId = id;
				loadLine(id);
			}
		}
		enableButtons();
		
	}


	private void loadLine(int lineId) {
		
		if ( lineId <= 0 )
			return;
	
		log.fine("SubCurrentLine - loading line " + lineId);
		MOrderLine ol = new MOrderLine(p_ctx, lineId, null);
		if ( ol != null )
		{
			setPrice(ol.getPriceActual());
			setQty(ol.getQtyOrdered());
		}
		
	}

} //	PosSubCurrentLine
