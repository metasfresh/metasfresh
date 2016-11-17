package org.adempiere.inout.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_AD_Client;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.product.IProductBL;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;

/**
 * A complex class to load and cache {@link IStorageRecord}s which are relevant to {@link I_M_ShipmentSchedule}s and their {@link I_C_OrderLine}s.
 *
 */
public class ShipmentScheduleQtyOnHandStorage
{
	// services
	private final transient IStorageEngineService storageEngineProvider = Services.get(IStorageEngineService.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);

	//
	// Parametes
	private IContextAware _context;
	/** date when storage is evaluated */
	@SuppressWarnings("unused")
	private Timestamp _date;

	//
	// Cache
	private CachedObjects _cachedObjects;
	private final Map<ArrayKey, IStorageQuery> _cachedStorageQueries = new HashMap<>();

	//
	// Storage
	private final IStorageEngine storageEngine;
	/**
	 * Loaded storage records (map of StorageId to "mutable storage record")
	 *
	 * NOTE: we are using a {@link LinkedHashMap} to preserve the order.
	 */
	private final LinkedHashMap<String, ShipmentScheduleStorageRecord> _storageRecords = new LinkedHashMap<>(50);

	//
	// Unconfirmed Quantities
	private Set<Integer> unconfirmedQtys_consideredInOutLineIds = new HashSet<>();
	private Map<Integer, BigDecimal> unconfirmedQtys_shipmentScheduleId2qty = new HashMap<>();

	public ShipmentScheduleQtyOnHandStorage()
	{
		super();
		_date = SystemTime.asDayTimestamp();

		storageEngine = storageEngineProvider.getStorageEngine();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void setContext(final IContextAware context)
	{
		_context = context;
	}

	private IContextAware getContext()
	{
		Check.assumeNotNull(_context, "context not null");
		return _context;
	}

	private Properties getCtx()
	{
		return getContext().getCtx();
	}

	public void setDate(final Timestamp date)
	{
		Check.assumeNotNull(date, "date not null");
		_date = date;
	}

	private CachedObjects getCachedObjects()
	{
		Check.assumeNotNull(_cachedObjects, "cachedObjects not null");
		return _cachedObjects;
	}

	public void setCachedObjects(final CachedObjects cachedObjects)
	{
		_cachedObjects = cachedObjects;
	}

	public void loadStoragesFor(final List<OlAndSched> lines)
	{
		if (Check.isEmpty(lines))
		{
			return;
		}

		final Set<Integer> productIds = new HashSet<>();

		//
		// Create storage queries from our lines.
		// Also collect the M_Product_IDs
		final Set<IStorageQuery> storageQueries = new HashSet<>(lines.size());
		for (final OlAndSched olAndSched : lines)
		{
			//
			// Create query
			// In case the DeliveryRule is Force, there is no point to load the storage, because it's not needed.
			final I_M_ShipmentSchedule shipmentSchedule = olAndSched.getSched();
			final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(shipmentSchedule);
			if (!X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule))
			{
				final IStorageQuery storageQuery = createStorageQuery(olAndSched);
				storageQueries.add(storageQuery);
			}

			// Collect product IDs
			final I_C_OrderLine orderLine = olAndSched.getOl();
			final int productId = orderLine.getM_Product_ID();
			if (productId > 0)
			{
				productIds.add(productId);
			}
		}

		//
		// Retrieve storage records for our queries
		if (!storageQueries.isEmpty())
		{
			final IContextAware context = getContext();
			final Collection<IStorageRecord> storageRecords = storageEngine.retrieveStorageRecords(context, storageQueries);
			addStorageRecords(storageRecords);
		}

		//
		// Load and subtract unconfirmed qty
		loadAndApplyUnconfirmedShipments(productIds);
	}

	public void loadStoragesFor(final OlAndSched olAndSched)
	{
		final IStorageQuery storageQuery = createStorageQuery(olAndSched);

		final IContextAware context = getContext();
		final Collection<IStorageRecord> storagesForLine = storageEngine.retrieveStorageRecords(context, storageQuery);

		addStorageRecords(storagesForLine);
	}

	private final void addStorageRecords(final Collection<IStorageRecord> storageRecords)
	{
		if (Check.isEmpty(storageRecords))
		{
			return;
		}

		for (final IStorageRecord storageRecord : storageRecords)
		{
			final ShipmentScheduleStorageRecord mutableStorageRecord = new ShipmentScheduleStorageRecord(storageRecord);

			// Make sure the storage record is not added twice
			final String storageId = mutableStorageRecord.getId();
			if (_storageRecords.containsKey(storageId))
			{
				continue;
			}

			_storageRecords.put(storageId, mutableStorageRecord);
		}
	}

	/**
	 * Loads all InProgress shipment lines for given product IDs and then subtract their quantities from current QtyOnHand that we have.
	 *
	 * @param productIds
	 */
	private void loadAndApplyUnconfirmedShipments(final Set<Integer> productIds)
	{
		if (productIds.isEmpty())
		{
			return;
		}

		//
		// Retrieve shipment lines which are in progress
		// ... because when they will be processed, their qty will be subtracted from storage
		final IQueryBuilder<I_M_InOutLine> shipmentLinesInProgressQueryBuilder = inOutDAO.createUnprocessedShipmentLinesQuery(getCtx())
				.addInArrayFilter(I_M_InOutLine.COLUMN_M_Product_ID, productIds)
				.addNotEqualsFilter(I_M_InOutLine.COLUMNNAME_MovementQty, BigDecimal.ZERO);

		// Skip those shipment lines which were already considered
		if (!unconfirmedQtys_consideredInOutLineIds.isEmpty())
		{
			shipmentLinesInProgressQueryBuilder.addNotInArrayFilter(I_M_InOutLine.COLUMN_M_InOutLine_ID, unconfirmedQtys_consideredInOutLineIds);
		}

		//
		// Retrieve unconfirmed shipment lines
		final List<I_M_InOutLine> shipmentLinesInProgress = shipmentLinesInProgressQueryBuilder
				.create()
				.list();

		//
		// Iterate those in progress shipment lines and subtract their qty
		for (final I_M_InOutLine shipmentLine : shipmentLinesInProgress)
		{
			final BigDecimal qtyUnconfirmedPerShipmentLine = shipmentLine.getMovementQty();
			if (qtyUnconfirmedPerShipmentLine.signum() == 0)
			{
				continue;
			}

			//
			// Update Storage Lines (if there are any)
			if (hasStorageRecords())
			{
				final IStorageQuery storageQuery = createStorageQuery(shipmentLine);

				// NOTE: we are going to subtract the whole quantity from this shipment line
				// only from first storage
				// Not sure if this is ok, but this is how it was before and i did not invest to much time to really implement it right if it works ;)
				// The right way would be to distribute it to existing storages
				final ShipmentScheduleStorageRecord storageRecord = getFirstStorageRecordsMatching(storageQuery);
				if (storageRecord != null)
				{
					storageRecord.subtractQtyOnHand(qtyUnconfirmedPerShipmentLine);
				}
			}

			//
			// Update unconfirmed quantities per shipment schedule
			final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
			final List<I_M_ShipmentSchedule> schedulesForInOutLine = shipmentScheduleAllocDAO.retrieveSchedulesForInOutLine(shipmentLine);
			for (final I_M_ShipmentSchedule sched : schedulesForInOutLine)
			{
				addQtyUnconfirmedShipmentsPerShipmentSchedule(sched.getM_ShipmentSchedule_ID(), qtyUnconfirmedPerShipmentLine);
			}

			// remember this shipment line because we don't want to subtract it twice
			unconfirmedQtys_consideredInOutLineIds.add(shipmentLine.getM_InOutLine_ID());
		}
	}

	public IStorageQuery createStorageQuery(final OlAndSched olAndSched)
	{
		final de.metas.interfaces.I_C_OrderLine orderLine = olAndSched.getOl();
		final I_M_ShipmentSchedule sched = olAndSched.getSched();

		//
		// Get the storage query from cache if available
		final ArrayKey storageQueryCacheKey = Util.mkKey(
				I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID(),
				I_M_ShipmentSchedule.Table_Name, sched.getM_ShipmentSchedule_ID());
		IStorageQuery storageQuery = _cachedStorageQueries.get(storageQueryCacheKey);
		if (storageQuery != null)
		{
			return storageQuery;
		}

		//
		// Create stoarge query
		final CachedObjects co = getCachedObjects();
		final I_M_Product product = co.retrieveAndCacheProduct(orderLine);
		final I_M_Warehouse warehouse = warehouseAdvisor.evaluateWarehouse(orderLine);
		final I_C_BPartner bpartner = co.retrieveAndCacheBPartner(orderLine);

		storageQuery = storageEngine.newStorageQuery();
		storageQuery.addWarehouse(warehouse);
		storageQuery.addProduct(product);
		storageQuery.addPartner(bpartner);

		// Add query attributes
		final I_M_AttributeSetInstance asi = sched.getM_AttributeSetInstance_ID() > 0 ? sched.getM_AttributeSetInstance() : null;
		if (asi != null && asi.getM_AttributeSetInstance_ID() > 0)
		{
			final IAttributeSet attributeSet = storageEngine.getAttributeSet(asi);
			storageQuery.addAttributes(attributeSet);
		}

		//
		// Cache the storage query and return it
		_cachedStorageQueries.put(storageQueryCacheKey, storageQuery);
		return storageQuery;
	}

	private IStorageQuery createStorageQuery(final I_M_InOutLine shipmentLine)
	{
		//
		// Get the storage query from cache if available
		final ArrayKey storageQueryCacheKey = Util.mkKey(I_M_InOutLine.Table_Name, shipmentLine.getC_OrderLine_ID());
		IStorageQuery storageQuery = _cachedStorageQueries.get(storageQueryCacheKey);
		if (storageQuery != null)
		{
			return storageQuery;
		}

		//
		// Create stoarge query
		final CachedObjects co = getCachedObjects();
		final I_M_Product product = co.retrieveAndCacheProduct(shipmentLine);
		final I_M_InOut shipment = shipmentLine.getM_InOut();
		final I_M_Warehouse warehouse = shipment.getM_Warehouse();
		final I_C_BPartner bpartner = co.retrieveAndCacheBPartner(shipment);

		storageQuery = storageEngine.newStorageQuery();
		storageQuery.addWarehouse(warehouse);
		storageQuery.addProduct(product);
		storageQuery.addPartner(bpartner);

		// Add query attributes
		final I_M_AttributeSetInstance asi = shipmentLine.getM_AttributeSetInstance();
		if (asi != null && asi.getM_AttributeSetInstance_ID() > 0)
		{
			final IAttributeSet attributeSet = storageEngine.getAttributeSet(asi);
			storageQuery.addAttributes(attributeSet);
		}

		//
		// Cache the storage query and return it
		_cachedStorageQueries.put(storageQueryCacheKey, storageQuery);
		return storageQuery;
	}

	@SuppressWarnings("unused")
	// old code... and our new storage API does not support MMPolicies
	private boolean isFifoMMPolicy(final I_M_Product product)
	{
		final CachedObjects co = getCachedObjects();

		//
		// Get Product's MMPolicy
		String MMPolicy = co.getMmPolicyCache().get(product.getM_Product_ID());
		if (MMPolicy == null)
		{
			MMPolicy = productBL.getMMPolicy(product);
			co.getMmPolicyCache().put(product.getM_Product_ID(), MMPolicy);
		}
		final boolean isFiFo = X_AD_Client.MMPOLICY_FiFo.equals(MMPolicy);
		return isFiFo;
	}

	private Collection<ShipmentScheduleStorageRecord> getStorageRecords()
	{
		return _storageRecords.values();
	}

	private boolean hasStorageRecords()
	{
		return !_storageRecords.isEmpty();
	}

	public List<ShipmentScheduleStorageRecord> getStorageRecordsMatching(final OlAndSched olAndSched)
	{
		if (!hasStorageRecords())
		{
			return Collections.emptyList();
		}

		final IStorageQuery storageQuery = createStorageQuery(olAndSched);
		return getStorageRecordsMatching(storageQuery);
	}

	public List<ShipmentScheduleStorageRecord> getStorageRecordsMatching(final IStorageQuery storageQuery)
	{
		return getStorageRecords()
				.stream()
				.filter(storageRecord -> storageQuery.matches(storageRecord))
				.collect(Collectors.toList());
	}

	public ShipmentScheduleStorageRecord getFirstStorageRecordsMatching(final IStorageQuery storageQuery)
	{
		final Collection<ShipmentScheduleStorageRecord> storageRecordsAll = getStorageRecords();
		if (storageRecordsAll.isEmpty())
		{
			return null;
		}

		for (final ShipmentScheduleStorageRecord storageRecord : getStorageRecords())
		{
			// Skip storage records which are not matching our query
			if (!storageQuery.matches(storageRecord))
			{
				continue;
			}

			return storageRecord;
		}

		return null;
	}

	private void addQtyUnconfirmedShipmentsPerShipmentSchedule(int m_ShipmentSchedule_ID, BigDecimal qtyUnconfirmedToAdd)
	{
		Check.assume(m_ShipmentSchedule_ID > 0, "m_ShipmentSchedule_ID > 0");

		BigDecimal qtyUnconfirmedOld = unconfirmedQtys_shipmentScheduleId2qty.get(m_ShipmentSchedule_ID);
		if (qtyUnconfirmedOld == null)
		{
			qtyUnconfirmedOld = BigDecimal.ZERO;
		}
		final BigDecimal qtyUnconfirmedNew = qtyUnconfirmedOld.add(qtyUnconfirmedToAdd);
		unconfirmedQtys_shipmentScheduleId2qty.put(m_ShipmentSchedule_ID, qtyUnconfirmedNew);
	}

	public BigDecimal getQtyUnconfirmedShipmentsPerShipmentSchedule(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "Param 'sched' is not null");

		final BigDecimal qtyUnconfirmed = unconfirmedQtys_shipmentScheduleId2qty.get(sched.getM_ShipmentSchedule_ID());
		if (qtyUnconfirmed == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyUnconfirmed;
	}
}
