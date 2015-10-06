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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MWarehousePrice;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	POS Query Product
 *	
 *  @author Based on Modified Original Code, Revised and Optimized
 *         *Copyright (c) Jorg Janke
 */
public class QueryProduct extends PosQuery
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172276999827406833L;

	/**
	 * 	Constructor
	 */
	public QueryProduct (PosBasePanel posPanel)
	{
		super(posPanel);
	}	//	PosQueryProduct
	
	private PosTextField		f_value;
	private PosTextField		f_name;
	private PosTextField		f_upc;
	private PosTextField		f_sku;

	private int				m_M_Product_ID;
	private String			m_ProductName;
	private BigDecimal		m_Price;
	//
	private int 			m_M_PriceList_Version_ID;
	private int 			m_M_Warehouse_ID;
	private CButton f_refresh;
	private CButton f_ok;
	private CButton f_cancel;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(QueryProduct.class);
	
	
	/**	Table Column Layout Info			*/
	private static ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "M_Product_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Value"), "Value", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "UPC"), "UPC", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "SKU"), "SKU", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyAvailable"), "QtyAvailable", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "QtyOnHand", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "PriceStd"), "PriceStd", BigDecimal.class)
	};
	/**	From Clause							*/
	private static String s_sqlFrom = "RV_WarehousePrice";
	/** Where Clause						*/
	private static String s_sqlWhere = "IsActive='Y'"; 

	/**
	 * 	Set up Panel
	 */
	protected void init()
	{
		CPanel panel = new CPanel();
		
		panel.setLayout(new MigLayout("fill"));
		getContentPane().add(panel);
		//	North
		northPanel = new CPanel(new MigLayout("fill", "", "[50][50][]"));
		panel.add (northPanel, "north");
		northPanel.setBorder(new TitledBorder(Msg.getMsg(p_ctx, "Query")));
		
		//
		CLabel lvalue = new CLabel(Msg.translate(p_ctx, "Value"));
		northPanel.add (lvalue, "growy");
		f_value = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lvalue.setLabelFor(f_value);
		northPanel.add(f_value,  "h 30, w 200");
		f_value.addActionListener(this);
		//
		CLabel lupc = new CLabel(Msg.translate(p_ctx, "UPC"));
		northPanel.add (lupc, "growy");
		f_upc = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lupc.setLabelFor(f_upc);
		northPanel.add(f_upc,  "h 30, w 200, wrap");
		f_upc.addActionListener(this);
		//
		CLabel lname = new CLabel(Msg.translate(p_ctx, "Name"));
		northPanel.add (lname, "growy");
		f_name = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lname.setLabelFor(f_name);
		northPanel.add(f_name,  "h 30, w 200");
		f_name.addActionListener(this);
		//
		CLabel lsku = new CLabel(Msg.translate(p_ctx, "SKU"));
		northPanel.add (lsku, "growy");
		f_sku = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lsku.setLabelFor(f_sku);
		northPanel.add(f_sku,  "h 30, w 200");
		f_sku.addActionListener(this);
		//
		

		f_refresh = createButtonAction("Refresh", KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		northPanel.add(f_refresh, "w 50!, h 50!, wrap, alignx trailing");
		
		f_up = createButtonAction("Previous", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		northPanel.add(f_up, "w 50!, h 50!, span, split 4");
		f_down = createButtonAction("Next", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		northPanel.add(f_down, "w 50!, h 50!");
		
		f_ok = createButtonAction("Ok", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		northPanel.add(f_ok, "w 50!, h 50!");
		
		f_cancel = createButtonAction("Cancel", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		northPanel.add(f_cancel, "w 50!, h 50!");

		//	Center
		m_table = new PosTable();
		String sql = m_table.prepareTable (s_layout, s_sqlFrom, 
			s_sqlWhere, false, "RV_WarehousePrice")
			+ " ORDER BY Margin, QtyAvailable";
		m_table.addMouseListener(this);
		m_table.getSelectionModel().addListSelectionListener(this);
		m_table.setColumnVisibility(m_table.getColumn(0), false);
		m_table.getColumn(1).setPreferredWidth(175);
		m_table.getColumn(2).setPreferredWidth(175);
		m_table.getColumn(3).setPreferredWidth(100);
		m_table.getColumn(4).setPreferredWidth(75);
		m_table.getColumn(5).setPreferredWidth(75);
		m_table.getColumn(6).setPreferredWidth(75);
		m_table.getColumn(7).setPreferredWidth(75);
		enableButtons();
		m_table.setFillsViewportHeight( true ); //@Trifon
		m_table.growScrollbars();
		centerScroll = new CScrollPane(m_table);
		panel.add (centerScroll, "growx, growy,south");
		panel.setPreferredSize(new Dimension(800,600));
		f_value.requestFocus();
	}	//	init
	
	/**
	 * 	Set Query Data
	 *	@param M_PriceList_Version_ID plv
	 *	@param M_Warehouse_ID wh
	 */
	public void setQueryData (int M_PriceList_Version_ID, int M_Warehouse_ID)
	{
		m_M_PriceList_Version_ID = M_PriceList_Version_ID;
		m_M_Warehouse_ID = M_Warehouse_ID;
	}	//	setQueryData
	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		log.info(e.getActionCommand());
		if ("Refresh".equals(e.getActionCommand())
			|| e.getSource() == f_value || e.getSource() == f_upc
			|| e.getSource() == f_name || e.getSource() == f_sku)
		{
			setResults(MWarehousePrice.find (p_ctx,
				m_M_PriceList_Version_ID, m_M_Warehouse_ID,
				f_value.getText(), f_name.getText(), f_upc.getText(), f_sku.getText(), null));
			return;
		}
		else if ("Reset".equals(e.getActionCommand()))
		{
			reset();
			return;
		}
		else if ("Previous".equalsIgnoreCase(e.getActionCommand()))
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
		//	Exit
		close();
	}	//	actionPerformed
	
	
	/**
	 * 	Set/display Results
	 *	@param results results
	 */
	public void setResults (MWarehousePrice[] results)
	{
		m_table.loadTable(results);
		if (m_table.getRowCount() >0 )
			m_table.setRowSelectionInterval(0, 0);
		enableButtons();
	}	//	setResults

	/**
	 * 	Enable/Set Buttons and set ID
	 */
	protected void enableButtons()
	{
		m_M_Product_ID = -1;
		m_ProductName = null;
		m_Price = null;
		int row = m_table.getSelectedRow();
		boolean enabled = row != -1;
		if (enabled)
		{
			Integer ID = m_table.getSelectedRowKey();
			if (ID != null)
			{
				m_M_Product_ID = ID.intValue();
				m_ProductName = (String)m_table.getValueAt(row, 2);
				m_Price = (BigDecimal)m_table.getValueAt(row, 7);
			}
		}
		f_ok.setEnabled(enabled);
		log.fine("M_Product_ID=" + m_M_Product_ID + " - " + m_ProductName + " - " + m_Price); 
	}	//	enableButtons



	/**
	 * 	Close.
	 * 	Set Values on other panels and close
	 */
	protected void close()
	{
		log.fine("M_Product_ID=" + m_M_Product_ID); 
		
		if (m_M_Product_ID > 0)
		{
			p_posPanel.f_curLine.setM_Product_ID(m_M_Product_ID);
			p_posPanel.f_curLine.setPrice(m_Price);
		}
		else
		{
			p_posPanel.f_curLine.setM_Product_ID(0);
			p_posPanel.f_curLine.setPrice(Env.ZERO);
		}
		dispose();
	}	//	close


	@Override
	public void reset() {

		f_value.setText(null);
		f_name.setText(null);
		f_sku.setText(null);
		f_upc.setText(null);
		setResults(new MWarehousePrice[0]);
	}
	
}	//	PosQueryProduct
