/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.inoutcandidate.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.MOrderLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;

public class ShipmentSchedulePA implements IShipmentSchedulePA
{
	private final static Logger logger = LogManager.getLogger(ShipmentSchedulePA.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * When mass cache invalidation, above this threshold we will invalidate ALL shipment schedule records instead of particular IDS
	 */
	private static final int CACHE_INVALIDATE_ALL_THRESHOLD = 200;
	/**
	 * Order by clause used to fetch {@link I_M_ShipmentSchedule}s.
	 * <p>
	 * NOTE: this ordering is VERY important because that's the order in which the available QtyOnHand will be allocated.
	 */
	private static final String ORDER_CLAUSE = "\n ORDER BY " //
			//
			// Priority
			+ "\n   COALESCE(" + I_M_ShipmentSchedule.COLUMNNAME_PriorityRule_Override + ", " + I_M_ShipmentSchedule.COLUMNNAME_PriorityRule + ")," //
			//
			// QtyToDeliver_Override:
			// NOTE: (Mark) If we want to force delivering something, that shall get higher priority,
			// so that's why QtyToDeliver_Override is much more important than PreparationDate, DeliveryDate etc
			+ "\n   COALESCE(" + I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override + ", 0) DESC,"
			//
			// manufacture-to-order - look at scheds for whose order lines actual HUs were created
			+ "\n CASE WHEN EXISTS(SELECT 1" + "\n                  FROM PP_Order ppo" + "\n                       JOIN PP_Order_Qty ppoq ON ppoq.PP_Order_ID=ppo.PP_Order_ID" + "\n                            JOIN M_HU hu ON hu.M_HU_ID=ppoq.M_HU_ID" + "\n                  WHERE ppo.C_OrderLine_ID = M_ShipmentSchedule.C_OrderLine_ID" + "\n                        AND ppoq.IsActive = 'Y'"
			+ "\n                        AND hu.IsActive='Y' AND hu.HUStatus NOT IN ('D'/*Destroyed*/, 'P'/*Planning*/, 'E'/*Shipped*/))" + "\n THEN FALSE ELSE TRUE END," // false comes before true, so we evaluate to false if there is such a PP_Order
			//
			// Reservation 1 - look at scheds for which there is a reservation
			+ "\n CASE WHEN EXISTS(SELECT 1" + "\n                  FROM M_HU_Reservation res" + "\n                            JOIN M_HU hu ON hu.M_HU_ID=res.VHU_ID" + "\n                  WHERE res.C_OrderLineSO_ID = M_ShipmentSchedule.C_OrderLine_ID" + "\n                        AND res.IsActive = 'Y'"
			+ "\n                        AND hu.IsActive='Y' AND hu.HUStatus NOT IN ('D'/*Destroyed*/, 'P'/*Planning*/, 'E'/*Shipped*/))" + "\n THEN FALSE ELSE TRUE END,"
			//
			// Reservation 2 - look at scheds for whose bpartners there are *dedicated* HUs.
			+ "\n CASE WHEN EXISTS(SELECT 1" + "\n                  FROM M_HU hu" + "\n                  WHERE hu.C_BPartner_ID = COALESCE(M_ShipmentSchedule.C_BPartner_Override_ID, M_ShipmentSchedule.C_BPartner_ID)" + "\n                        AND hu.IsActive = 'Y'" + "\n                        AND hu.HUStatus NOT IN ('D'/*Destroyed*/, 'P'/*Planning*/, 'E'/*Shipped*/)) "
			+ "\n THEN FALSE ELSE TRUE END," // false comes before true, so we evaluate to false if there is such an HU
			//
			// Preparation Date
			+ "\n   COALESCE(" + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override + ", " + I_M_ShipmentSchedule.COLUMNNAME_PreparationDate + "),"
			//
			// Delivery Date
			// NOTE: stuff that shall be delivered first shall have a higher prio
			+ "\n   COALESCE(" + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override + ", " + I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate + ")," // stuff that shall be delivered first shall have
			// a higher prio
			//
			// Date Ordered
			+ "\n   " + I_M_ShipmentSchedule.COLUMNNAME_DateOrdered + ", "
			//
			// Order Line
			+ "\n   " + I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID;

	@Override
	public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		return getById(id, I_M_ShipmentSchedule.class);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> T getById(@NonNull final ShipmentScheduleId id, @NonNull final Class<T> modelClass)
	{
		final T shipmentSchedule = load(id, modelClass);
		if (shipmentSchedule == null)
		{
			throw new AdempiereException("@NotFound@ @M_ShipmentSchedule_ID@: " + id);
		}
		return shipmentSchedule;
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return getByIdsOutOfTrx(ids, I_M_ShipmentSchedule.class);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIdsOutOfTrx(@NonNull final Set<ShipmentScheduleId> ids, final Class<T> modelClass)
	{
		final List<T> shipmentSchedules = loadByRepoIdAwaresOutOfTrx(ids, modelClass);
		return Maps.uniqueIndex(shipmentSchedules, ss -> ShipmentScheduleId.ofRepoId(ss.getM_ShipmentSchedule_ID()));
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = loadByRepoIdAwares(ids, I_M_ShipmentSchedule.class);
		return Maps.uniqueIndex(shipmentSchedules, ss -> ShipmentScheduleId.ofRepoId(ss.getM_ShipmentSchedule_ID()));
	}

	@Override
	public I_M_ShipmentSchedule getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return getByOrderLineIdQuery(orderLineId).create().firstOnly(I_M_ShipmentSchedule.class);
	}

	@Override
	public ShipmentScheduleId getShipmentScheduleIdByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return getByOrderLineIdQuery(orderLineId).create().firstIdOnly(ShipmentScheduleId::ofRepoIdOrNull);
	}

	@Override
	public boolean existsExportedShipmentScheduleForOrder(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, orderId).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false).addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_ExportStatus, APIExportStatus.EXPORTED_STATES).create().anyMatch();
	}

	private IQueryBuilder<I_M_ShipmentSchedule> getByOrderLineIdQuery(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_OrderLine.class)).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Record_ID, orderLineId).orderBy(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public Set<ShipmentScheduleId> retrieveUnprocessedIdsByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Processed, false).addEqualsFilter(I_M_ShipmentSchedule.COLUMN_C_Order_ID, orderId).create().listIds(ShipmentScheduleId::ofRepoId);
	}

	@Override
	public List<I_M_ShipmentSchedule> retrieveUnprocessedForRecord(@NonNull final TableRecordReference recordRef)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Processed, false).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID()).addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Record_ID, recordRef.getRecord_ID()).orderBy(I_M_ShipmentSchedule.COLUMN_M_ShipmentSchedule_ID).create()
				.listImmutable(I_M_ShipmentSchedule.class);
	}

	/**
	 * Note: The {@link I_C_OrderLine}s contained in the {@link OlAndSched} instances are {@link MOrderLine}s.
	 */
	@Override
	public List<OlAndSched> retrieveInvalid(@NonNull final PInstanceId pinstanceId)
	{
		final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
		// 1.
		// Mark the M_ShipmentSchedule_Recompute records that point to the scheds which we will work with
		// This allows us to distinguish them from records created later

		// task 08727: Tag the recompute records out-of-trx.
		// This is crucial because the invalidation-SQL checks if there exist un-tagged recompute records to avoid creating too many unneeded records.
		// So if the tagging was in-trx, then the invalidation-SQL would still see them as un-tagged and therefore the invalidation would fail.
		invalidSchedulesRepo.markAllToRecomputeOutOfTrx(pinstanceId);

		// 2.
		// Load the scheds the are pointed to by our marked M_ShipmentSchedule_Recompute records
		final List<I_M_ShipmentSchedule> shipmentSchedules = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().filter(invalidSchedulesRepo.createInvalidShipmentSchedulesQueryFilter(pinstanceId)).create().setOrderBy(queryBL.createSqlQueryOrderBy(ORDER_CLAUSE)).list();
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}

		return createOlAndScheds(shipmentSchedules);
	}

	private static OrderAndLineId extractOrderAndLineId(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return OrderAndLineId.ofRepoIdsOrNull(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
	}

	private List<OlAndSched> createOlAndScheds(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final Set<OrderAndLineId> orderLineIds = shipmentSchedules.stream().map(ShipmentSchedulePA::extractOrderAndLineId).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());

		final Map<OrderAndLineId, I_C_OrderLine> orderLines = orderDAO.getOrderLinesByIds(orderLineIds);

		final ImmutableSet<OrderId> orderIds = orderLineIds.stream().map(OrderAndLineId::getOrderId).collect(ImmutableSet.toImmutableSet());

		final Map<OrderId, I_C_Order> orderId2record = Maps.uniqueIndex(orderDAO.getByIds(orderIds, I_C_Order.class), order -> OrderId.ofRepoId(order.getC_Order_ID()));

		final List<OlAndSched> result = new ArrayList<>();

		for (final I_M_ShipmentSchedule schedule : shipmentSchedules)
		{
			final OrderAndLineId orderLineId = extractOrderAndLineId(schedule);
			final I_C_OrderLine orderLine;
			final I_C_Order order;
			if (orderLineId == null)
			{
				orderLine = null;
				order = null;
			}
			else
			{
				orderLine = orderLines.get(orderLineId);
				order = orderId2record.get(orderLineId.getOrderId());
			}

			final OlAndSched olAndSched = OlAndSched.builder().shipmentSchedule(schedule).orderLineOrNull(orderLine).orderOrNull(order).build();
			result.add(olAndSched);
		}
		return result;
	}

	@Override
	public void setIsDiplayedForProduct(@NonNull final ProductId productId, final boolean displayed)
	{
		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, productId).create().updateDirectly().addSetColumnValue(I_M_ShipmentSchedule.COLUMNNAME_IsDisplayed, displayed).execute();
	}

	@Override
	public Stream<I_M_ShipmentSchedule> streamUnprocessedByPartnerIdAndAllowConsolidateInOut(@NonNull final BPartnerId bpartnerId, final boolean allowConsolidateInOut)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false).addCoalesceEqualsFilter(bpartnerId, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID).addEqualsFilter(I_M_ShipmentSchedule.COLUMN_AllowConsolidateInOut, allowConsolidateInOut)
				.orderBy(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				//
				.create().setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // because the Processed flag might change while we iterate
				.setOption(IQuery.OPTION_IteratorBufferSize, 500).iterateAndStream();
	}

	/**
	 * Mass-update a given shipment schedule column.
	 * <p>
	 * If there were any changes and the invalidate parameter is on true, those shipment schedules will be invalidated.
	 *
	 * @param inoutCandidateColumnName {@link I_M_ShipmentSchedule}'s column to update
	 * @param value                    value to set (you can also use {@link ModelColumnNameValue})
	 * @param updateOnlyIfNull         if true then it will update only if column value is null (not set)
	 * @param selectionId              ShipmentSchedule selection (AD_PInstance_ID)
	 */
	private <ValueType> void updateColumnForSelection(final String inoutCandidateColumnName, final ValueType value, final boolean updateOnlyIfNull, final PInstanceId selectionId, final boolean invalidate)
	{
		//
		// Create the selection which we will need to update
		final IQueryBuilder<I_M_ShipmentSchedule> selectionQueryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).setOnlySelection(selectionId).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false) // do not touch the processed shipment schedules
				;

		if (updateOnlyIfNull)
		{
			selectionQueryBuilder.addEqualsFilter(inoutCandidateColumnName, null);
		}
		final PInstanceId selectionToUpdateId = selectionQueryBuilder.create().createSelection();
		if (selectionToUpdateId == null)
		{
			// nothing to update
			return;
		}

		//
		// Update our new selection
		final int countUpdated = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).setOnlySelection(selectionToUpdateId).create().updateDirectly().addSetColumnValue(inoutCandidateColumnName, value).execute();

		//
		// Cache invalidate
		// We have to do this even if invalidate=false
		cacheInvalidateBySelectionId(selectionToUpdateId, countUpdated);

		//
		// Invalidate the inout candidates which we updated
		if (invalidate)
		{
			final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
			invalidSchedulesRepo.invalidateSchedulesForSelection(selectionToUpdateId);
		}
	}

	private void cacheInvalidateBySelectionId(@NonNull final PInstanceId selectionId, final long estimatedSize)
	{
		final CacheInvalidateMultiRequest request;
		if (estimatedSize < 0)
		{
			// unknown estimated size
			request = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ShipmentSchedule.Table_Name);
		}
		else if (estimatedSize == 0)
		{
			// no records
			// unknown estimated size
			request = null;
		}
		else if (estimatedSize <= CACHE_INVALIDATE_ALL_THRESHOLD)
		{
			// relatively small amount of records
			// => fetch and reset individually
			final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).setOnlySelection(selectionId).create().listIds(ShipmentScheduleId::ofRepoId);
			if (!shipmentScheduleIds.isEmpty())
			{
				request = CacheInvalidateMultiRequest.rootRecords(I_M_ShipmentSchedule.Table_Name, shipmentScheduleIds);
			}
			else
			{
				// no records found => do nothing
				request = null;
			}
		}
		else
		{
			// large amount of records
			// => instead of fetching all IDs better invalidate the whole table
			request = CacheInvalidateMultiRequest.allRecordsForTable(I_M_ShipmentSchedule.Table_Name);
		}

		//
		// Perform the actual cache invalidation
		if (request != null)
		{
			CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
		}
	}

	@Override
	public void updateDeliveryDate_Override(final Timestamp deliveryDate, final PInstanceId pinstanceId)
	{
		// No need of invalidation after deliveryDate update because it is not used for anything else than preparation date calculation.
		// In case this calculation is needed, the invalidation will be done on preparation date updating
		// see de.metas.inoutcandidate.api.impl.ShipmentSchedulePA.updatePreparationDate_Override(Timestamp, int, String)

		final boolean invalidate = false;

		updateColumnForSelection(I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override,               // inoutCandidateColumnName
				deliveryDate,               // value
				false,               // updateOnlyIfNull
				pinstanceId,               // selectionId
				invalidate               // invalidate schedules = false
		);
	}

	@Override
	public void updatePreparationDate_Override(final Timestamp preparationDate, final PInstanceId pinstanceId)
	{
		// in case the preparation date is given, it will only be set. No Invalidation needed
		// in case it is not given (null) an invalidation is needed because it will be calculated based on the delivery date
		final boolean invalidate = preparationDate == null;

		updateColumnForSelection(I_M_ShipmentSchedule.COLUMNNAME_PreparationDate_Override,               // inoutCandidateColumnName
				preparationDate,               // value
				false,               // updateOnlyIfNull
				pinstanceId,               // selectionId
				invalidate               // invalidate schedules
		);
	}

	@Override
	public IQueryBuilder<I_M_ShipmentSchedule> createQueryForShipmentScheduleSelection(final Properties ctx, final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class, ctx, ITrx.TRXNAME_None).filter(userSelectionFilter).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false).addOnlyActiveRecordsFilter().addOnlyContextClient();
	}

	@Override
	public Set<I_M_ShipmentSchedule> retrieveForInvoiceCandidate(@NonNull final I_C_Invoice_Candidate candidate)
	{
		final Set<I_M_ShipmentSchedule> schedules;

		final int tableID = candidate.getAD_Table_ID();

		// invoice candidate references an orderline
		if (tableID == InterfaceWrapperHelper.getTableId(I_C_OrderLine.class))
		{
			Check.errorIf(candidate.getC_OrderLine_ID() <= 0, "C_Invoice_Candidate has AD_Table_ID=>C_OrderLine, but does not reference any C_OrderLine_ID; candidate={}", candidate);
			final OrderLineId orderLineId = OrderLineId.ofRepoId(candidate.getC_OrderLine_ID());
			final I_M_ShipmentSchedule shipmentSchedule = getByOrderLineId(orderLineId);
			schedules = shipmentSchedule != null ? ImmutableSet.of(shipmentSchedule) : ImmutableSet.of();
		}

		// invoice candidate references an inoutline
		else if (tableID == InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
		{
			final I_M_InOutLine inoutLine = TableRecordReference.ofReferenced(candidate).getModel(PlainContextAware.newWithThreadInheritedTrx(), I_M_InOutLine.class);

			schedules = ImmutableSet.copyOf(retrieveForInOutLine(inoutLine));
		}
		else
		{
			schedules = ImmutableSet.of();
		}

		return schedules;
	}

	@Override
	public Set<I_M_ShipmentSchedule> retrieveForInOutLine(@NonNull final org.compiere.model.I_M_InOutLine inoutLine)
	{
		final Map<Integer, I_M_ShipmentSchedule> schedules = new LinkedHashMap<>();

		// add all the shipment schedules from the QtyPicked entries
		final Map<Integer, I_M_ShipmentSchedule> schedulesForInOutLine = Services.get(IShipmentScheduleAllocDAO.class).retrieveSchedulesForInOutLineQuery(inoutLine).create().mapById(I_M_ShipmentSchedule.class);
		schedules.putAll(schedulesForInOutLine);

		// fallback to the case when the inoutline has an orderline set but has no Qty Picked entries
		// this happens when we create manual Shipments
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inoutLine.getC_OrderLine_ID());
		if (orderLineId != null)
		{
			final I_M_ShipmentSchedule schedForOrderLine = getByOrderLineId(orderLineId);
			if (schedForOrderLine != null)
			{
				schedules.put(schedForOrderLine.getM_ShipmentSchedule_ID(), schedForOrderLine);
			}
		}

		return ImmutableSet.copyOf(schedules.values());
	}

	@Override
	public void deleteAllForReference(@Nullable final TableRecordReference referencedRecord)
	{
		if (referencedRecord == null)
		{
			logger.debug("given parameter referencedRecord is null; nothing to delete");
			return;
		}
		final int deletedCount = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, referencedRecord.getAD_Table_ID()).addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Record_ID, referencedRecord.getRecord_ID()).create().delete(); // don't "deleteDirectly". we need model interceptors to fire

		logger.debug("Deleted {} M_ShipmentSchedule records for referencedRecord={}", deletedCount, referencedRecord);
	}

	@Override
	public Set<ProductId> getProductIdsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addInArrayFilter(I_M_ShipmentSchedule.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds).create().listDistinct(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, Integer.class).stream().map(ProductId::ofRepoId).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void save(@NonNull final I_M_ShipmentSchedule record)
	{
		InterfaceWrapperHelper.saveRecord(record);
	}

	@Override
	public ImmutableList<I_M_ShipmentSchedule> getByReferences(@NonNull final ImmutableList<TableRecordReference> recordRefs)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).setJoinOr().setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true);

		for (final TableRecordReference recordRef : recordRefs)
		{
			final ICompositeQueryFilter<I_M_ShipmentSchedule> filter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID()).addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Record_ID, recordRef.getRecord_ID());
			queryBuilder.filter(filter);
		}

		return queryBuilder.create().listImmutable(I_M_ShipmentSchedule.class);
	}

	@Override
	public Collection<I_M_ShipmentSchedule> getByOrderId(@NonNull final OrderId orderId)
	{
		return queryByOrderIds(ImmutableSet.of(orderId)).list();
	}

	@Override
	public Collection<I_M_ShipmentSchedule> getByOrderIds(@NonNull final Collection<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryByOrderIds(orderIds).list();
	}

	@Override
	public ImmutableSet<ShipmentScheduleId> retrieveScheduleIdsByOrderId(@NonNull final OrderId orderId)
	{
		return queryByOrderIds(ImmutableSet.of(orderId)).create().listIds(ShipmentScheduleId::ofRepoId);
	}

	@Override
	public boolean anyMatchByOrderId(@NonNull final OrderId orderId)
	{
		return queryByOrderIds(ImmutableSet.of(orderId)).anyMatch();
	}

	@Override
	public boolean anyMatchByOrderIds(@NonNull final Collection<OrderId> orderIds)
	{
		return !orderIds.isEmpty() && queryByOrderIds(orderIds).anyMatch();
	}

	private IQueryBuilder<I_M_ShipmentSchedule> queryByOrderIds(final @NonNull Collection<OrderId> orderIds)
	{
		Check.assumeNotEmpty(orderIds, "orderIds not empty");
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addOnlyActiveRecordsFilter().addInArrayFilter(I_M_ShipmentSchedule.COLUMN_C_Order_ID, orderIds);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIds(@NonNull final Set<ShipmentScheduleId> ids, @NonNull final Class<T> clazz)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, ids).create().mapByRepoIdAware(ShipmentScheduleId::ofRepoId, clazz);
	}

	@Override
	public ImmutableSet<OrderId> getOrderIds(@NonNull final IQueryFilter<? extends I_M_ShipmentSchedule> filter)
	{
		//noinspection unchecked
		final ImmutableList<OrderId> orderIds = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class).filter((IQueryFilter<I_M_ShipmentSchedule>)filter).addOnlyActiveRecordsFilter().addNotNull(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID).create().listDistinct(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, OrderId.class);
		return ImmutableSet.copyOf(orderIds);
	}

}
