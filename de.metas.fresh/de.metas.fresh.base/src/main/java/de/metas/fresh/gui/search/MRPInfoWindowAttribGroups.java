package de.metas.fresh.gui.search;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.dimension.IDimensionspecDAO;

/**
 * NOTE: consider moving this to either de.metas.handlingunits or org.adempiere.libero if and when one starts to depend on the other.
 * 
 * @task 08681
 */
public class MRPInfoWindowAttribGroups
{
	private static final String FUNCTION_NAME = "X_MRP_ProductInfo_AttributeVal_V";

	private final transient CLogger log = CLogger.getCLogger(getClass());

	private Properties _ctx;
	private int _windowNo;

	private MiniTable dimensionsTbl = null;

	private String m_sqlDimensions;

	private MRPInfoWindowAttribs panelWarehouse;

	public MRPInfoWindowAttribGroups(final Properties ctx, final int windowNo)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this._ctx = ctx;
		this._windowNo = windowNo;

		initUI();
		init();
	}

	private final Properties getCtx()
	{
		return _ctx;
	}

	private final int getWindowNo()
	{
		return _windowNo;
	}

	private void initUI()
	{
		dimensionsTbl = new MiniTable();
		dimensionsTbl.setRowSelectionAllowed(true);
	}

	private void init()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		final ColumnInfo[] s_layoutAttribValues = new ColumnInfo[] {
				new ColumnInfo(msgBL.translate(ctx, " "), "M_Product_ID", IDColumn.class).setColumnName("M_Product_ID"),
				// new ColumnInfo(msgBL.translate(ctx, "ProductValue"), "(select p.Value from M_Product p where p.M_Product_ID=" + TABLE_NAME + ".M_Product_ID)", String.class),
				new ColumnInfo(msgBL.translate(ctx, "ValueAggregateName"), "GroupName", String.class).setColumnName("GroupName"),
				new ColumnInfo(msgBL.translate(ctx, "qtyreserved_ondate"), "qtyreserved_ondate", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "qtyordered_ondate"), "qtyordered_ondate", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "qtymaterialentnahme"), "qtymaterialentnahme", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "fresh_qtyonhand_ondate"), "fresh_qtyonhand_ondate", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "fresh_qtypromised"), "fresh_qtypromised", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "fresh_qtymrp"), "fresh_qtymrp", Double.class)
		};
		/** From Clause */
		final String s_sqlFrom = FUNCTION_NAME + "('@DateGeneral@'::DATE, ?)";
		/** Where Clause */
		final String s_sqlWhere = "1 = 1";
		
		final boolean multiSelection = false; 
		final boolean addAccessSQL = false; // doesn't work with a function
		m_sqlDimensions = dimensionsTbl.prepareTable(s_layoutAttribValues, s_sqlFrom, s_sqlWhere, multiSelection, FUNCTION_NAME, addAccessSQL);

		m_sqlDimensions += " ORDER BY GroupName";
		dimensionsTbl.setMultiSelection(false);

		dimensionsTbl.autoSize();

		dimensionsTbl.addPropertyChangeListener(IMiniTable.PROPERTY_SelectionChanged, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				if (dimensionsTbl.isLoading() || dimensionsTbl.getSelectedRow() < 0 || dimensionsTbl.getRowCount() == 0)
				{
					return;
				}
				final IDColumn productId = (IDColumn)dimensionsTbl.getValueAt(dimensionsTbl.getSelectedRow(), "M_Product_ID");
				final String groupName = (String)dimensionsTbl.getValueAt(dimensionsTbl.getSelectedRow(), "GroupName");

				if (productId == null)
				{
					return;
				}

				final List<String> attributeValues = Services.get(IDimensionspecDAO.class)
						.retrieveAttributeValueForGroup("MRP_Product_Info_ASI_Values", groupName, new PlainContextAware(getCtx()));
				final String[] array = attributeValues.toArray(new String[0]);
				panelWarehouse.refresh(productId.getRecord_ID(), array);
			}
		});
	}

	public void refresh(final int M_Product_ID)
	{
		final String sql = Env.parseContext(getCtx(), getWindowNo(),
				m_sqlDimensions,
				false, // onlyWindow,
				false // ignoreUnparsable
				);
		if (Check.isEmpty(sql, true))
		{
			dimensionsTbl.clear();
			return;
		}

		log.finest(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, M_Product_ID);
			rs = pstmt.executeQuery();
			dimensionsTbl.loadTable(rs);
		}
		catch (final Exception e)
		{
			log.log(Level.WARNING, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (dimensionsTbl.getRowCount() > 0)
		{
			dimensionsTbl.setSelectedRows(Collections.singletonList(0));
		}
	}

	public java.awt.Component getComponent()
	{
		return dimensionsTbl;
	}

	public void setPanelWarehouse(MRPInfoWindowAttribs panelWarehouse)
	{
		this.panelWarehouse = panelWarehouse;
	}

}
