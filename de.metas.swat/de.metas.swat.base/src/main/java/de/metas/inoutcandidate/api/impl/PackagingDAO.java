package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_BPartnerAddress_Override;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_DeliveryViaRule;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_DeliveryViaRule_Override;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_PriorityRule;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_PriorityRule_Override;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.model.MPackagingContainer;
import org.adempiere.model.X_M_PackagingContainer;
import org.adempiere.util.Services;
import org.adempiere.util.collections.BlindIterator;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.Query;
import org.compiere.model.X_C_BPartner_Stats;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.exception.NoContainerException;
import de.metas.inoutcandidate.api.IPackageable;
import de.metas.inoutcandidate.api.IPackageableQuery;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.IStoragePA;

public class PackagingDAO implements IPackagingDAO
{
	private static final String COLUMNNAME_SHIPPER_NAME = "Shipper";

	private static final String COLUMNNAME_WAREHOUSE_NAME = "warehouseName";

	private static final String COLUMNNAME_BPARTNER_LOC_NAME = "bPartnerLocName";
	private static final String COLUMNNAME_BPARTNER_VALUE = "bPartnerValue";
	private static final String COLUMNNAME_BPARTNER_NAME = "bPartnerName";
	private static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * SQL: QtyAvailableToPick = QtyToDeliver
	 */
	private final static String SQL_ShipmentSchedule_QtyAvailableToPick = "("
			+ "COALESCE(s." + I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override + ", s." + I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver + ")"
			+ ")";

	private final static String SQL_ShipmentSchedule_DeliveryDate = "COALESCE("
			+ " s." + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override
			+ ", s." + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate
			+ ")";
	private final static String SQL_ShipmentSchedule_PreparationDate = "COALESCE("
			+ " s." + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override
			+ ", s." + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate
			+ ")";
	
	/**
	 * SQL: Select Packageable items (i.e. M_ReceiptSchedule).
	 * 
	 * <ul>
	 * <li>Param 1: IsDisplayNonDeliverableItems (Y/N)
	 * <li>Param 2: M_Warehouse_ID
	 * </ul>
	 */
	private final static String SQL_Packable_2P = " SELECT l.C_BPartner_Location_ID, " //
			+ "   p.Value AS " + COLUMNNAME_BPARTNER_VALUE + ", " //
			+ "   p.C_BPartner_ID, "
			+ "   (coalesce(p.Name,'') || coalesce(p.Name2,'')) AS " + COLUMNNAME_BPARTNER_NAME + ", "
			+ "   l.Name AS " + COLUMNNAME_BPARTNER_LOC_NAME + ", "
			+ "   s." + COLUMNNAME_BPartnerAddress_Override + ", "
			+ "   o.C_Order_ID,"
			+ "   o.DocumentNo, "
			+ "   o." + I_C_Order.COLUMNNAME_FreightCostRule + ", "
			+ "   dt." + I_C_DocType.COLUMNNAME_DocSubType + ", "
			+ "   w.M_Warehouse_ID, " //
			+ "   w.Name AS " + COLUMNNAME_WAREHOUSE_NAME + ", " //
			+ "   s.M_ShipmentSchedule_ID, "
			+ "   " + SQL_ShipmentSchedule_QtyAvailableToPick + " AS " + I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver + ", "
			+ "   COALESCE (s." + COLUMNNAME_DeliveryViaRule_Override + ", o.DeliveryViaRule) AS " + COLUMNNAME_DeliveryViaRule + ", " //
			+ "   s." + I_M_ShipmentSchedule.COLUMNNAME_IsDisplayed + ", " //
			+ "   sh." + I_M_Shipper.COLUMNNAME_Name + " as " + COLUMNNAME_SHIPPER_NAME + ", " //
			+ "   sh." + I_M_Shipper.COLUMNNAME_M_Shipper_ID
			+ "   , " + SQL_ShipmentSchedule_DeliveryDate + " AS " + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate
			+ "   , " + SQL_ShipmentSchedule_PreparationDate + " AS " + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate
			//
			+ "   , s." + I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID
			+ "   , prod." + I_M_Product.COLUMNNAME_Name + " AS " + COLUMNNAME_ProductName
			//
			+ " FROM " + I_M_ShipmentSchedule.Table_Name + " s " //
			+ "   LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID=s.C_OrderLine_ID " //
			+ "   LEFT JOIN M_Warehouse w ON w.M_Warehouse_ID= COALESCE(s."+I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID + ", s."+I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID + ")"//
			+ "   LEFT JOIN M_Shipper sh ON sh.M_Shipper_ID=ol.M_Shipper_ID " //
			+ "   LEFT JOIN C_BPartner p ON p.C_BPartner_ID=COALESCE(s." + COLUMNNAME_C_BPartner_Override_ID + ", ol.C_BPartner_ID) " //
			+ "   LEFT JOIN " + I_C_BPartner_Stats.Table_Name + " stats "
			+ "   ON p."+ I_C_BPartner.COLUMNNAME_C_BPartner_ID +  " = stats. "+ I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID
			+ "   LEFT JOIN C_BPartner_Location l ON l.C_BPartner_Location_ID=COALESCE(s." + COLUMNNAME_C_BP_Location_Override_ID + ", ol.C_BPartner_Location_ID) " //
			+ "   LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID " //
			+ "   LEFT JOIN C_DocType dt ON dt.C_DocType_ID=o.C_DocType_ID "
			+ "   LEFT JOIN M_Product prod ON (prod.M_Product_ID=s.M_Product_ID)"
			+ " WHERE " //
			//
			// Lines on which we have something to deliver:
			+ "   ("
			+ "   	  " + SQL_ShipmentSchedule_QtyAvailableToPick + " > 0"
			+ "       OR ?='Y'" // Param:DisplayNonDeliverableItems #1
			+ "   )"
			//
			+ "   AND COALESCE(s." + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID + ", s." + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID +") =? " // #2
			+ "   AND NOT EXISTS ( " // schedule is not locked by a different shipment/commissioning run
			+ "        select 1 from " + ShipmentSchedulePA.M_SHIPMENT_SCHEDULE_SHIPMENT_RUN + " sr "
			+ "        where sr." + COLUMNNAME_M_ShipmentSchedule_ID + "=s." + COLUMNNAME_M_ShipmentSchedule_ID
			+ "   ) "
			+ "   AND ( stats." + X_C_BPartner_Stats.COLUMNNAME_SOCreditStatus //
			+ "           NOT IN ('" + X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop + "', '" + X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold + "')" //
			+ "         OR stats." + X_C_BPartner_Stats.COLUMNNAME_SOCreditStatus + " IS NULL " //
			+ "   )"
			+ "   AND s." + I_M_ShipmentSchedule.COLUMNNAME_IsActive + "='Y' ";

	private final static String SQL_Packable_OrderBy =
			" " + SQL_ShipmentSchedule_DeliveryDate
					+ " , prod." + I_M_Product.COLUMNNAME_Name
					+ " , COALESCE(s." + COLUMNNAME_PriorityRule_Override + ", s." + COLUMNNAME_PriorityRule + " )"
					+ " , ol." + I_C_OrderLine.COLUMNNAME_DateOrdered;

	@Override
	public I_M_PackagingContainer retrieveContainer(int containerId, String trxName)
	{

		return new X_M_PackagingContainer(Env.getCtx(), containerId, trxName);
	}

	@Override
	public I_M_PackagingContainer retrieveContainer(final BigDecimal volume, final int warehouseId, final String trxName)
	{
		final Properties ctx = Env.getCtx();
		final String where0 = I_M_PackagingContainer.COLUMNNAME_MaxVolume + ">=?";
		final String orderBy0 = I_M_PackagingContainer.COLUMNNAME_MaxVolume;

		final I_M_PackagingContainer c0 = new Query(ctx, I_M_PackagingContainer.Table_Name, where0, trxName)
				.setParameters(new Object[] { volume }).setOrderBy(orderBy0)
				.first(I_M_PackagingContainer.class);

		if (isContainerAvail(c0, warehouseId, trxName))
		{
			return c0;
		}

		final String orderBy1 = I_M_PackagingContainer.COLUMNNAME_MaxVolume + " DESC";
		final I_M_PackagingContainer c1 = new Query(ctx, I_M_PackagingContainer.Table_Name, "", trxName)
				.setOrderBy(orderBy1)
				.first(I_M_PackagingContainer.class);

		if (isContainerAvail(c1, warehouseId, trxName))
		{
			return c1;
		}

		throw new NoContainerException(warehouseId,
				false, // maxVolumeExceeded
				false // maxWeightExceeded
		);
	}

	private boolean isContainerAvail(final I_M_PackagingContainer container, final int warehouseId, final String trxName)
	{
		if (container != null)
		{
			final IStoragePA storagePA = Services.get(IStoragePA.class);

			final BigDecimal qtyAvailable = storagePA.retrieveQtyAvailable(warehouseId, 0, container.getM_Product_ID(), 0, trxName);

			if (qtyAvailable.signum() > 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public List<I_M_PackagingContainer> retrieveContainers(final int warehouseId, final String trxName)
	{

		final List<MPackagingContainer> queryResult = new Query(Env.getCtx(),
				I_M_PackagingContainer.Table_Name, "", trxName)
				.setOnlyActiveRecords(true).list();

		final List<I_M_PackagingContainer> result = new ArrayList<I_M_PackagingContainer>();

		for (final MPackagingContainer container : queryResult)
		{

			if (isContainerAvail(container, warehouseId, trxName))
			{
				result.add(container);
			}
		}
		return result;
	}

	@Override
	public IPackageableQuery createPackageableQuery()
	{
		return new PackageableQuery();
	}

	@Override
	public Iterator<IPackageable> retrievePackableLines(final Properties ctx, final IPackageableQuery query)
	{
		final StringBuilder sql = new StringBuilder(SQL_Packable_2P);
		final StringBuilder sqlOrderBy = new StringBuilder(SQL_Packable_OrderBy);
		final List<Object> sqlParams = new ArrayList<Object>();

		//
		// Filter: DisplayNonDeliverableItems
		// (i.e. Param 1)
		sqlParams.add(query.isDisplayNonDeliverableItems());

		//
		// Filter: M_Warehouse_ID
		// (i.e. Param 2)
		sqlParams.add(query.getWarehouseId());


		//
		// Filter: DisplayTodayEntriesOnly
		// => filter by DeliveryDate
		if (query.isDisplayTodayEntriesOnly())
		{
			final Timestamp deliveryDateDay = SystemTime.asDayTimestamp();
			// NOTE: in our system DeliveryDate is Date+Time but we want to search only at day level
			sql.append(" AND (TRUNC(" + SQL_ShipmentSchedule_DeliveryDate + ", 'DD')=?"
					+ " OR " + SQL_ShipmentSchedule_DeliveryDate + " IS NULL)");
			sqlParams.add(deliveryDateDay);
			
		}
		
		//
		// Order BY

		final String sqlFinal = sql.toString()
				+ " ORDER BY "
				+ sqlOrderBy.toString();

		final BlindIterator<IPackageable> blindIterator = new AbstractPreparedStatementBlindIterator<IPackageable>()
		{

			@Override
			protected PreparedStatement createPreparedStatement() throws SQLException
			{
				final PreparedStatement pstmt = DB.prepareStatement(sqlFinal, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				return pstmt;
			}

			@Override
			protected IPackageable fetch(final ResultSet rs) throws SQLException
			{
				return fetchPackageable(ctx, rs);
			}
		};

		return IteratorUtils.asIterator(blindIterator);
	}

	private IPackageable fetchPackageable(final Properties ctx, final ResultSet rs) throws SQLException
	{
		final Packageable packageable = new Packageable();

		final int bpartnerId = rs.getInt("C_BPartner_ID");
		packageable.setBpartnerId(bpartnerId);

		final BigDecimal qtyToDeliver = rs.getBigDecimal(I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver);
		packageable.setQtyToDeliver(qtyToDeliver);

		final int bPartnerLocationId = rs.getInt(COLUMNNAME_C_BPartner_Location_ID);
		packageable.setBPartnerLocationId(bPartnerLocationId);

		final String bPartnerAddress = rs.getString(COLUMNNAME_BPartnerAddress_Override);
		packageable.setBPartnerAddress(bPartnerAddress);

		final int rowWarehouseId = rs.getInt(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);
		packageable.setWarehouseId(rowWarehouseId);
		final String warehouseName = rs.getString(COLUMNNAME_WAREHOUSE_NAME);
		packageable.setWarehouseName(warehouseName);

		final int productId = rs.getInt(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID);
		packageable.setProductId(productId);
		final String productName = rs.getString(COLUMNNAME_ProductName);
		packageable.setProductName(productName);

		final String deliveryVia = rs.getString(I_M_ShipmentSchedule.COLUMNNAME_DeliveryViaRule);
		packageable.setDeliveryVia(deliveryVia);

		final int shipperId = rs.getInt(I_M_Shipper.COLUMNNAME_M_Shipper_ID);
		packageable.setShipperId(shipperId);
		final String shipper = rs.getString(COLUMNNAME_SHIPPER_NAME);
		packageable.setShipperName(shipper);

		final Timestamp deliveryDate = rs.getTimestamp(I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate); // 01676
		packageable.setDeliveryDate(deliveryDate);

		final Timestamp preparationDate = rs.getTimestamp(I_M_ShipmentSchedule.COLUMNNAME_PreparationDate);
		packageable.setPreparationTime(preparationDate);

		final int shipmentScheduleId = rs.getInt(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
		packageable.setShipmentScheduleId(shipmentScheduleId);

		final String bPartnerValue = rs.getString(COLUMNNAME_BPARTNER_VALUE);
		packageable.setBPartnerValue(bPartnerValue);

		final String bPartnerName = rs.getString(COLUMNNAME_BPARTNER_NAME);
		packageable.setBPartnerName(bPartnerName);

		final String bPartnerLocName = rs.getString(COLUMNNAME_BPARTNER_LOC_NAME);
		packageable.setBPartnerLocationName(bPartnerLocName);

		final boolean isDisplayed = "Y".equals(rs.getString(I_M_ShipmentSchedule.COLUMNNAME_IsDisplayed));
		packageable.setDisplayed(isDisplayed);

		final int orderId = rs.getInt("C_Order_ID");
		packageable.setOrderId(orderId);
		final String docSubType = rs.getString(I_C_DocType.COLUMNNAME_DocSubType);
		packageable.setDocSubType(docSubType);

		final String freightCostRule = rs.getString(I_C_Order.COLUMNNAME_FreightCostRule);
		packageable.setFreightCostRule(freightCostRule);

		return packageable;
	}
}
