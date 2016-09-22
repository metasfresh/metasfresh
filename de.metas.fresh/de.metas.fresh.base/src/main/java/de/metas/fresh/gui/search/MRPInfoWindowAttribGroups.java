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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.dimension.IDimensionspecDAO;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;
import de.metas.logging.LogManager;

/**
 * NOTE: consider moving this to either de.metas.handlingunits or org.adempiere.libero if and when one starts to depend on the other.
 *
 * @task 08681
 */
public class MRPInfoWindowAttribGroups
{
	private static final String FUNCTION_NAME = "X_MRP_ProductInfo_AttributeVal_V";

	private final transient Logger log = LogManager.getLogger(getClass());

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

		//
		// gh #213: get individual AD_InfoColumns and check if they are active. Only add respective ColumnInfos to the mini-table if they are active
		final IADInfoWindowDAO adInfoWindowDAO = Services.get(IADInfoWindowDAO.class);
		final I_AD_InfoWindow infoWindow = adInfoWindowDAO.retrieveInfoWindowByTableName(ctx, I_X_MRP_ProductInfo_V.Table_Name);

		final List<ColumnInfo> s_layoutAttribValues = new ArrayList<ColumnInfo>();

		s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, " "), "M_Product_ID", IDColumn.class).setColumnName("M_Product_ID"));
		s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, "ValueAggregateName"), "GroupName", String.class).setColumnName("GroupName"));

		final I_AD_InfoColumn qtyOnHandColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyOnHand); // gh #213: new column as of this task
		if (qtyOnHandColumn != null && qtyOnHandColumn.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyOnHand), I_X_MRP_ProductInfo_V.COLUMNNAME_QtyOnHand, Double.class));
		}

		final I_AD_InfoColumn pmmQtyPromisedOnDateColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_PMM_QtyPromised_OnDate);
		if (pmmQtyPromisedOnDateColumn != null && pmmQtyPromisedOnDateColumn.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_PMM_QtyPromised_OnDate), I_X_MRP_ProductInfo_V.COLUMNNAME_PMM_QtyPromised_OnDate, Double.class)); // FRESH-86
		}

		s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, "qtyreserved_ondate"), "qtyreserved_ondate", Double.class));
		s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, "qtyordered_ondate"), "qtyordered_ondate", Double.class));

		final I_AD_InfoColumn qtyMaterialentnahmeColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyMaterialentnahme);
		if (qtyMaterialentnahmeColumn != null && qtyMaterialentnahmeColumn != null && qtyMaterialentnahmeColumn.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_QtyMaterialentnahme), I_X_MRP_ProductInfo_V.COLUMNNAME_QtyMaterialentnahme, Double.class));
		}

		final I_AD_InfoColumn freshQtyOnHandOnDate = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyOnHand_OnDate);
		if (freshQtyOnHandOnDate != null && freshQtyOnHandOnDate.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyOnHand_OnDate), I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyOnHand_OnDate, Double.class));
		}

		final I_AD_InfoColumn freshQtyPromisedColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyPromised);
		if (freshQtyPromisedColumn != null && freshQtyPromisedColumn.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyPromised), I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyPromised, Double.class));
		}

		final I_AD_InfoColumn freshQtyMRPColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(infoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyMRP);
		if (freshQtyMRPColumn != null && freshQtyMRPColumn.isActive())
		{
			s_layoutAttribValues.add(new ColumnInfo(msgBL.translate(ctx, I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyMRP), I_X_MRP_ProductInfo_V.COLUMNNAME_Fresh_QtyMRP, Double.class));
		}

		/** From Clause */
		final String s_sqlFrom = FUNCTION_NAME + "('@DateGeneral@'::DATE, ?)";
		/** Where Clause */
		final String s_sqlWhere = "1 = 1";

		final boolean multiSelection = false;
		final boolean addAccessSQL = false; // doesn't work with a function
		m_sqlDimensions = dimensionsTbl.prepareTable(s_layoutAttribValues.toArray(new ColumnInfo[0]), s_sqlFrom, s_sqlWhere, multiSelection, FUNCTION_NAME, addAccessSQL);

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
				false,             // onlyWindow,
				false // ignoreUnparsable
		);
		if (Check.isEmpty(sql, true))
		{
			dimensionsTbl.clear();
			return;
		}

		log.trace(sql);
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
			log.warn(sql, e);
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
