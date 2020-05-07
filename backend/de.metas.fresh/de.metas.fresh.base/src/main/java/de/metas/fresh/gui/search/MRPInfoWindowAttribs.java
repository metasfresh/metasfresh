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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.i18n.IMsgBL;

/**
 * NOTE: consider moving this to either de.metas.handlingunits or org.adempiere.libero if and when one starts to depend on the other.
 */
public class MRPInfoWindowAttribs
{
	private static final String DIM_SPEC_INTERNALNAME_MRP_PRODUCT_INFO_ASI_VALUES = "MRP_Product_Info_ASI_Values";

	/**
	 * This is how the items in this table are ordered.
	 */
	private static final String SQL_ORDER_BY = " ORDER BY ASIKeyName, M_HU_PI_Item_Product";

	private final transient Logger log = LogManager.getLogger(getClass());

	private Properties _ctx;
	private int _windowNo;

	private IMiniTable stockTbl = null;
	private String m_sqlStock;

	public MRPInfoWindowAttribs(final Properties ctx, final int windowNo)
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
		final MiniTable table = new MiniTable();
		table.setRowSelectionAllowed(true);
		table.setShowTotals(true);
		stockTbl = table;
	}

	private void init()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = Env.getCtx();

		final ColumnInfo[] s_layoutStock = new ColumnInfo[] {
				new ColumnInfo(" ", "M_Product_ID", IDColumn.class),
				// new ColumnInfo(msgBL.translate(ctx, "ProductName"), "ProductName", String.class),
				new ColumnInfo(msgBL.translate(ctx, "ASIKeyName"), "ASIKeyName", String.class),
				// new ColumnInfo(msgBL.translate(ctx, "QtyAvailable"), "QtyAvailable", Double.class),
				// new ColumnInfo(msgBL.translate(ctx, "QtyOnHand"), "QtyOnHand", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "QtyReserved"), "QtyReserved", Double.class),
				// new ColumnInfo(msgBL.translate(ctx, "Fresh_QtyMRP"), "QtyReserved_MRP", Double.class), // fresh_08273
				new ColumnInfo(msgBL.translate(ctx, "QtyOrdered"), "QtyOrdered", Double.class),
				new ColumnInfo(msgBL.translate(ctx, "M_HU_PI_Item_Product_ID"), "M_HU_PI_Item_Product", String.class)
		};
		/** From Clause */
		final String s_sqlFrom = " " + "C_OrderLine_HU_Info_v" + " ";
		/** Where Clause */
		final String s_sqlWhere = "M_Product_ID = ? AND DateGeneral::DATE = '@DateGeneral@'::DATE";
		m_sqlStock = stockTbl.prepareTable(s_layoutStock, s_sqlFrom, s_sqlWhere, false, "C_OrderLine_HU_Info_v");
		// m_sqlStock += SQL_ORDER_BY; // appended in the refresh() method
		stockTbl.setMultiSelection(false);

		stockTbl.autoSize();
	}

	/**
	 * Reloads the table with the given filter criteria.
	 * 
	 * @param M_Product_ID
	 * @param attributeValues attribute values that belong to the {@link I_DIM_Dimension_Spec} with the internal name 
	 * {@value #DIM_SPEC_INTERNALNAME_MRP_PRODUCT_INFO_ASI_VALUES}.
	 */
	public void refresh(final int M_Product_ID, final String... attributeValues)
	{
		final StringBuilder sql = new StringBuilder(m_sqlStock);

		if (attributeValues.length > 0)
		{
			sql.append("AND ( false ");
			for (int i = 0; i < attributeValues.length; i++)
			{
				if ("DIM_EMPTY".equals(attributeValues[i]))
				{
					// match everything that is not matched by any other attribute value define by the spec
					sql.append(" OR ('DIM_EMPTY'=? AND ");
					sql.append(" NOT EXISTS (select 1 from \"de.metas.dimension\".DIM_Dimension_Spec_Attribute_AllValues dim2 where dim2.InternalName='"
							+ DIM_SPEC_INTERNALNAME_MRP_PRODUCT_INFO_ASI_VALUES
							+ "' and ASIKeyName ILIKE '%'||dim2.ValueName||'%') )");
				}
				else
				{
					// the normal case
					sql.append(" OR ASIKeyName ILIKE '%'||?||'%'");
				}

			}
			sql.append(")");
		}
		sql.append(SQL_ORDER_BY);

		final String sqlFinal = Env.parseContext(getCtx(), getWindowNo(),
				sql.toString(),
				false, // onlyWindow,
				false // ignoreUnparsable
				);
		if (Check.isEmpty(sqlFinal, true))
		{
			stockTbl.clear();
			return;
		}

		log.trace(sqlFinal);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{

			pstmt = DB.prepareStatement(sqlFinal, ITrx.TRXNAME_None);
			pstmt.setInt(1, M_Product_ID);
			for (int i = 0; i < attributeValues.length; i++)
			{
				pstmt.setString(i + 2, attributeValues[i]);
			}
			rs = pstmt.executeQuery();
			stockTbl.loadTable(rs);
			// rs = pstmt.executeQuery(); // why do we exiecute it again??
		}
		catch (final Exception e)
		{
			log.warn(sqlFinal, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	public java.awt.Component getComponent()
	{
		return (java.awt.Component)stockTbl;
	}
}
