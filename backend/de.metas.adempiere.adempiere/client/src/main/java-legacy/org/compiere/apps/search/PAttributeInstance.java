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
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.util.Services;


/**
 *	Display Product Attribute Instance Info
 *
 *  @author     Jorg Janke
 *  @version    $Id: PAttributeInstance.java,v 1.3 2006/07/30 00:51:27 jjanke Exp $
 */
public class PAttributeInstance extends CDialog 
	implements ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3743466565716139916L;

	/**
	 * 	Constructor
	 * 	@param parent dialog parent
	 * 	@param title title
	 * 	@param warehouseId warehouse key name pair
	 * 	@param M_Locator_ID locator
	 * 	@param productId product key name pair
	 * 	@param bpartnerId bp
	 */
	public PAttributeInstance(final Window parent,
			final String title,
			final WarehouseId warehouseId,
			final int M_Locator_ID,
			final ProductId productId,
			final BPartnerId bpartnerId)
	{
		super (parent, Msg.getMsg(Env.getCtx(), "PAttributeInstance") + title, true);
		init (warehouseId, M_Locator_ID, productId, bpartnerId);
		AEnv.showCenterWindow(parent, this);
	}	//	PAttributeInstance

	private void init (WarehouseId warehouseId, int M_Locator_ID, ProductId productId, BPartnerId bpartnerId)
	{
		m_M_Warehouse_ID = WarehouseId.toRepoId(warehouseId);
		m_M_Locator_ID = M_Locator_ID;
		m_M_Product_ID = ProductId.toRepoId(productId);
		try
		{
			jbInit();
			dynInit(BPartnerId.toRepoIdOr(bpartnerId, -1));
		}
		catch (Exception e)
		{
			log.error("", e);
		}
	}	// init	

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel northPanel = new CPanel();
	private BorderLayout northLayout = new BorderLayout();
	private JScrollPane centerScrollPane = new JScrollPane();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private CCheckBox showAll = new CCheckBox (Msg.getMsg(Env.getCtx(), "ShowAll"));
	//
	private MiniTable 			m_table = new MiniTable();
	//	Parameter
	private int			 		m_M_Warehouse_ID;
	private int			 		m_M_Locator_ID;
	private int			 		m_M_Product_ID;
	//
	private int					m_M_AttributeSetInstance_ID = -1;
	private String				m_M_AttributeSetInstanceName = null;
	private String				m_sql;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(PAttributeInstance.class);

	/**
	 * 	Static Init
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		mainPanel.setLayout(mainLayout);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		//	North
		northPanel.setLayout(northLayout);
		northPanel.add(showAll, BorderLayout.EAST);
		showAll.addActionListener(this);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		//	Center
		mainPanel.add(centerScrollPane, BorderLayout.CENTER);
		centerScrollPane.getViewport().add(m_table, null);
		//	South
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}	//	jbInit

	/**	Table Column Layout Info			*/
	// NOTE: cannot be static because depends on context Language
	private final ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "s.M_AttributeSetInstance_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Description"), "asi.Description", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Lot"), "asi.Lot", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "SerNo"), "asi.SerNo", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "GuaranteeDate"), "asi.GuaranteeDate", Timestamp.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "M_Locator_ID"), "l.Value", KeyNamePair.class, "s.M_Locator_ID"),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOnHand"), "s.QtyOnHand", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyReserved"), "s.QtyReserved", Double.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "QtyOrdered"), "s.QtyOrdered", Double.class),
		//	See RV_Storage
		new ColumnInfo(Msg.translate(Env.getCtx(), "GoodForDays"), "(TRUNC(asi.GuaranteeDate)-TRUNC(now()))-p.GuaranteeDaysMin", Integer.class, true, true, null),
		new ColumnInfo(Msg.translate(Env.getCtx(), "ShelfLifeDays"), "TRUNC(asi.GuaranteeDate)-TRUNC(now())", Integer.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "ShelfLifeRemainingPct"), "CASE WHEN p.GuaranteeDays > 0 THEN TRUNC(((TRUNC(asi.GuaranteeDate)-TRUNC(now()))/p.GuaranteeDays)*100) ELSE 0 END", Integer.class),
	};
	/**	From Clause							*/
	private static String s_sqlFrom = "M_Storage s"
		+ " INNER JOIN M_Locator l ON (s.M_Locator_ID=l.M_Locator_ID)"
		+ " INNER JOIN M_Product p ON (s.M_Product_ID=p.M_Product_ID)"
		+ " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID)";
	/** Where Clause						*/
	private static String s_sqlWhere = "s.M_Product_ID=? AND l.M_Warehouse_ID=?"; 
	private static String s_sqlWhereWithoutWarehouse = " s.M_Product_ID=?"; 

	private String	m_sqlNonZero = " AND (s.QtyOnHand<>0 OR s.QtyReserved<>0 OR s.QtyOrdered<>0)";
	private String	m_sqlMinLife = "";

	/**
	 * 	Dynamic Init
	 * 	@param C_BPartner_ID BP
	 */
	private void dynInit(int C_BPartner_ID)
	{
		log.info("C_BPartner_ID=" + C_BPartner_ID);
		if (C_BPartner_ID > 0)
		{
			int ShelfLifeMinPct = 0;
			int ShelfLifeMinDays = 0;
			String sql = "SELECT bp.ShelfLifeMinPct, bpp.ShelfLifeMinPct, bpp.ShelfLifeMinDays "
				+ "FROM C_BPartner bp "
				+ " LEFT OUTER JOIN C_BPartner_Product bpp"
				+	" ON (bp.C_BPartner_ID=bpp.C_BPartner_ID AND bpp.M_Product_ID=?) "
				+ "WHERE bp.C_BPartner_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_M_Product_ID);
				pstmt.setInt(2, C_BPartner_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					ShelfLifeMinPct = rs.getInt(1);		//	BP
					int pct = rs.getInt(2);				//	BP_P
					if (pct > 0)	//	overwrite
						ShelfLifeMinDays = pct;
					ShelfLifeMinDays = rs.getInt(3);
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
			if (ShelfLifeMinPct > 0)
			{
				m_sqlMinLife = " AND COALESCE(TRUNC(((TRUNC(asi.GuaranteeDate)-TRUNC(now()))/p.GuaranteeDays)*100),0)>=" + ShelfLifeMinPct;
				log.info( "PAttributeInstance.dynInit - ShelfLifeMinPct=" + ShelfLifeMinPct);
			}
			if (ShelfLifeMinDays > 0)
			{
				m_sqlMinLife += " AND COALESCE((TRUNC(asi.GuaranteeDate)-TRUNC(now())),0)>=" + ShelfLifeMinDays;
				log.info( "PAttributeInstance.dynInit - ShelfLifeMinDays=" + ShelfLifeMinDays);
			}
		}	//	BPartner != 0

		m_sql = m_table.prepareTable (s_layout, s_sqlFrom, 
					m_M_Warehouse_ID == 0 ? s_sqlWhereWithoutWarehouse : s_sqlWhere, false, "s")
				+ " ORDER BY asi.GuaranteeDate, s.QtyOnHand";	//	oldest, smallest first
		//
		m_table.setRowSelectionAllowed(true);
		m_table.setMultiSelection(false);
		m_table.addMouseListener(this);
		m_table.getSelectionModel().addListSelectionListener(this);
		//
		refresh();
	}	//	dynInit

	/**
	 * 	Refresh Query
	 */
	private void refresh()
	{
		String sql = m_sql;
		int pos = m_sql.lastIndexOf(" ORDER BY ");
		if (!showAll.isSelected())
		{
			sql = m_sql.substring(0, pos) 
				+ m_sqlNonZero;
			if (m_sqlMinLife.length() > 0)
				sql += m_sqlMinLife;
			sql += m_sql.substring(pos);
		}
		//
		log.trace(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_M_Product_ID);
			if (m_M_Warehouse_ID != 0)
				pstmt.setInt(2, m_M_Warehouse_ID);
			rs = pstmt.executeQuery();
			m_table.loadTable(rs);
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		enableButtons();
	}	//	refresh

	/**
	 * 	Action Listener
	 *	@param e event 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			dispose();
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			m_M_AttributeSetInstance_ID = -1;
			m_M_AttributeSetInstanceName = null;
		}
		else if (e.getSource() == showAll)
		{
			refresh();
		}
	}	//	actionPerformed
 
	/**
	 * 	Table selection changed
	 *	@param e event
	 */
	@Override
	public void valueChanged (ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
		enableButtons();
	}	//	valueChanged

	/**
	 * 	Enable/Set Buttons and set ID
	 */
	private void enableButtons()
	{
		m_M_AttributeSetInstance_ID = -1;
		m_M_AttributeSetInstanceName = null;
		m_M_Locator_ID = 0;
		int row = m_table.getSelectedRow();
		boolean enabled = row != -1;
		if (enabled)
		{
			Integer ID = m_table.getSelectedRowKey();
			if (ID != null)
			{
				m_M_AttributeSetInstance_ID = ID.intValue();
				m_M_AttributeSetInstanceName = (String)m_table.getValueAt(row, 1);
				//
				Object oo = m_table.getValueAt(row, 5);
				if (oo instanceof KeyNamePair)
				{
					KeyNamePair pp = (KeyNamePair)oo;
					m_M_Locator_ID = pp.getKey();
				}
			}
		}
		confirmPanel.getOKButton().setEnabled(enabled);
		log.debug("M_AttributeSetInstance_ID=" + m_M_AttributeSetInstance_ID 
			+ " - " + m_M_AttributeSetInstanceName
			+ "; M_Locator_ID=" + m_M_Locator_ID);
	}	//	enableButtons

	/**
	 *  Mouse Clicked
	 *  @param e event
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		//  Double click with selected row => exit
		if (e.getClickCount() > 1 && m_table.getSelectedRow() != -1)
		{
			enableButtons();
			dispose();
		}
	}   //  mouseClicked


	/**
	 * Get Attribute Set Instance.
	 *
	 * @return selected M_AttributeSetInstance_ID or -1
	 */
	public int getM_AttributeSetInstance_ID()
	{
		return m_M_AttributeSetInstance_ID;
	}
	
	/**
	 * @return selected ASI or null
	 */
	public MAttributeSetInstance getM_AttributeSetInstance()
	{
		if (m_M_AttributeSetInstance_ID < 0)
		{
			return null;
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeSetInstance.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_AttributeSetInstance.COLUMN_M_AttributeSetInstance_ID, m_M_AttributeSetInstance_ID)
				.create()
				.firstOnlyNotNull(MAttributeSetInstance.class);
	}

	/**
	 * 	Get Instance Name
	 * 	@return Instance Name
	 */
	public String getM_AttributeSetInstanceName()
	{
		return m_M_AttributeSetInstanceName;
	}	//	getM_AttributeSetInstanceName

	/**
	 * 	Get Locator
	 *	@return M_Locator_ID or 0
	 */
	public int getM_Locator_ID()
	{
		return m_M_Locator_ID;
	}	//	getM_Locator_ID

}	//	PAttributeInstance
