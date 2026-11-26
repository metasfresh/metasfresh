package de.metas.handlingunits.shipmentschedule.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ShipmentScheduleHUPackingAware;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.TULoader;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.model.I_M_Picking_Job_Step_PickedHU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUComparator;
import de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipping.IHUShipperTransportationBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.ShipmentScheduleLoadingCache;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUShipmentScheduleBL implements IHUShipmentScheduleBL
{
	private final Logger logger = LogManager.getLogger(getClass());
	private final IShipmentSchedulePA shipmentScheduleDAO = Services.get(IShipmentSchedulePA.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IInOutCandidateBL inOutCandidateBL = Services.get(IInOutCandidateBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

	private static final String SYSCONFIG_ShipmentConsolidationPeriod = "de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL.ShipmentConsolidationPeriod";
	private static final String DEFAULT_ShipmentConsolidationPeriod = null;

	public static final AdMessageKey MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P = AdMessageKey.of("WEBUI_Picking_Already_Shipped");

	@Override
	public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		return shipmentScheduleDAO.getById(id, I_M_ShipmentSchedule.class);
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return shipmentScheduleDAO.getByIds(ids, I_M_ShipmentSchedule.class);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule);
	}

	@Override
	public LocatorId getDefaultLocatorId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getDefaultLocatorId(shipmentSchedule);
	}

	@Override
	public ShipmentScheduleWithHU addQtyPickedAndUpdateHU(final AddQtyPickedRequest request)
	{
		@NonNull final IHUContext huContext = request.getHuContext();

		final ShipmentScheduleAndJobScheduleId scheduleId = request.getScheduleId();
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = getShipmentSchedule(request);

		// Retrieve VHU, TU and LU
		final LUTUCUPair husPair;
		{
			@NonNull final I_M_HU tuOrVHU = request.getHu();
			husPair = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(tuOrVHU);
		}

		// Create ShipmentSchedule Qty Picked record
		final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(shipmentSchedule, request.getQtyPicked(), I_M_ShipmentSchedule_QtyPicked.class);
		if (scheduleId.getJobScheduleId() != null)
		{
			qtyPickedRecord.setM_Picking_Job_Schedule_ID(scheduleId.getJobScheduleId().getRepoId());
		}
		qtyPickedRecord.setIsAnonymousHuPickedOnTheFly(request.isAnonymousHuPickedOnTheFly()); // mark this as an 'anonymousOnTheFly` pick
		setHUs(qtyPickedRecord, husPair); // Set HU specific stuff
		createCandidatesForQtyPicked(qtyPickedRecord, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER).forEach(ShipmentScheduleWithHU::updateQtyTUAndQtyLU);
		huShipmentScheduleDAO.saveQtyPicked(qtyPickedRecord);

		//
		// Update LU/TU/VHU
		final I_M_HU topLevelHU = husPair.getTopLevelHU();
		setHUStatusToPicked(topLevelHU);
		setHUPartnerAndLocationFromSched(topLevelHU, shipmentSchedule);
		handlingUnitsDAO.saveHU(topLevelHU);
		huContext.flush();

		return ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedWithHuContext(qtyPickedRecord, huContext);
	}

	@Override
	public boolean tryMergeQtyPickedIntoExistingForVHU(@NonNull final AddQtyPickedRequest request)
	{
		// Eligibility — only HU-trx-listener-shaped picks may be merged.
		// Order: cheapest request-shape guards first; HU lookup last.
		final ShipmentScheduleAndJobScheduleId scheduleId = request.getScheduleId();
		if (scheduleId.getJobScheduleId() != null)
		{
			return false;
		}
		if (request.isAnonymousHuPickedOnTheFly())
		{
			return false;
		}

		// Catch-weight merging is not supported here — fall through to create-new so each
		// catch reading keeps its own row.
		final StockQtyAndUOMQty qtyToAdd = request.getQtyPicked();
		if (qtyToAdd.isUOMQtySet())
		{
			return false;
		}

		// Only merge strictly positive allocations. Negative qty represents an un-allocation
		// (see ShipmentScheduleHUTrxListener#trxLineProcessed Javadoc) and must remain its own
		// audit row so the netting is visible — silently subtracting from a sibling can drive
		// a row to zero/negative and mask a stock discrepancy.
		if (qtyToAdd.getStockQty().signum() <= 0)
		{
			return false;
		}

		final I_M_HU vhu = request.getHu();
		if (!handlingUnitsBL.isVirtual(vhu))
		{
			return false;
		}

		final HuId vhuId = HuId.ofRepoId(vhu.getM_HU_ID());
		final List<I_M_ShipmentSchedule_QtyPicked> candidates = huShipmentScheduleDAO.retrieveMergeableListenerQtyPickedForVHU(
				scheduleId.getShipmentScheduleId(),
				vhuId);

		if (candidates.isEmpty())
		{
			return false;
		}
		if (candidates.size() > 1)
		{
			// Pre-existing duplicates — not our job to silently collapse them. Log and fall through;
			// the new-row path will leave the duplicates intact for human inspection.
			Loggables.withLogger(logger, Level.WARN).addLog(
					"tryMergeQtyPickedIntoExistingForVHU: found {} pre-existing un-shipped QtyPicked rows for shipmentScheduleId={} vhuId={} — falling through to create-new",
					candidates.size(), scheduleId.getShipmentScheduleId(), vhuId);
			return false;
		}

		final I_M_ShipmentSchedule_QtyPicked existing = candidates.get(0);
		// Defensive: even though the request carries no catch UOM (guard above), reject merging
		// into a row that was previously written with one. Could only happen if a different code
		// path bypassed the request-side guard; better to fall through than risk arithmetic that
		// loses the catch reading.
		final BigDecimal existingCatchQty = existing.getQtyDeliveredCatch();
		if (existing.getCatch_UOM_ID() > 0 || (existingCatchQty != null && existingCatchQty.signum() != 0))
		{
			return false;
		}

		existing.setQtyPicked(existing.getQtyPicked().add(qtyToAdd.getStockQty().toBigDecimal()));
		final IHUContext huContext = request.getHuContext();
		ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(existing, huContext)
				.forEach(ShipmentScheduleWithHU::updateQtyTUAndQtyLU);
		huShipmentScheduleDAO.saveQtyPicked(existing);

		// Drive the same HU side-effects as the create-new path. On the well-formed reversal
		// flow the topLevelHU is already Picked with the right partner/location (the FIRST
		// trx-line in the same transaction went through addQtyPickedAndUpdateHU), so each call
		// is a verified no-op:
		//   - HUStatusBL.setHUStatus returns early when status is unchanged
		//   - PO setters with equal values do not produce an UPDATE
		// Re-running them here is a safety net for edge cases where the first row's flow
		// didn't reach this point (e.g. a row pre-existed from a prior session).
		final LUTUCUPair husPair = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(vhu);
		final I_M_HU topLevelHU = husPair.getTopLevelHU();
		setHUStatusToPicked(topLevelHU);
		setHUPartnerAndLocationFromSched(topLevelHU, getShipmentSchedule(request));
		handlingUnitsDAO.saveHU(topLevelHU);
		huContext.flush();

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"tryMergeQtyPickedIntoExistingForVHU: merged qty={} into M_ShipmentSchedule_QtyPicked_ID={} (shipmentScheduleId={} vhuId={})",
				qtyToAdd.getStockQty(), existing.getM_ShipmentSchedule_QtyPicked_ID(), scheduleId.getShipmentScheduleId(), vhuId);
		return true;
	}

	@Override
	public void restoreUnshippedQtyPickedIfMissing(@NonNull final Collection<I_M_ShipmentSchedule_QtyPicked> reversedAllocations)
	{
		final RestoreState state = RestoreState.of(reversedAllocations, huContextFactory.createMutableHUContext(), productBL);
		for (final I_M_ShipmentSchedule_QtyPicked alloc : reversedAllocations)
		{
			final HuId vhuId = HuId.ofRepoIdOrNull(alloc.getVHU_ID());
			if (vhuId != null)
			{
				restoreUnshippedQtyPickedForUnit(alloc, vhuId, RestoredQty.ofWholeRow(alloc), state);
			}
			else
			{
				restoreUnshippedQtyPickedForBoxUnits(alloc, state);
			}
		}
	}

	private void restoreUnshippedQtyPickedForBoxUnits(
			@NonNull final I_M_ShipmentSchedule_QtyPicked boxAlloc,
			@NonNull final RestoreState state)
	{
		final HuId tuId = HuId.ofRepoIdOrNull(boxAlloc.getM_TU_HU_ID());
		if (tuId == null || handlingUnitsBL.isVirtual(handlingUnitsBL.getById(tuId)))
		{
			return; // neither a unit nor a box: nothing to restore this way
		}
		final IHUContext huContext = state.getHuContext();
		final ProductId productId = ProductId.ofRepoId(boxAlloc.getM_ShipmentSchedule().getM_Product_ID());
		final BoxKey boxKey = new BoxKey(ShipmentScheduleId.ofRepoId(boxAlloc.getM_ShipmentSchedule_ID()), tuId);
		for (final I_M_HU unit : retrieveBoxUnitsOfSchedule(handlingUnitsBL.getById(tuId), boxAlloc, huContext.getHUStorageFactory()))
		{
			final HuId unitId = HuId.ofRepoId(unit.getM_HU_ID());
			if (state.getUnitIdsOfRowsWithUnit().contains(unitId))
			{
				continue;
			}
			final Quantity unitQty = huContext.getHUStorageFactory().getStorage(unit).getProductStorage(productId).getQty();
			// the unit's weight comes from the unit itself, as the reopen of a completed job takes it
			final StockQtyAndUOMQty unitQtys = CatchWeightHelper.extractQtys(huContext, productId, unitQty, unit);
			// a box shared by several schedules or picks: restore no more than the reversed rows of this schedule picked from it
			// (whole units, in the order the box lists them: when the unit sizes do not add up to the quantity picked, less is restored)
			if (!state.takeFromBoxBudget(boxKey, unitQtys.getStockQty()))
			{
				continue;
			}
			restoreUnshippedQtyPickedForUnit(boxAlloc, unitId, RestoredQty.ofUnitOfBoxRow(unitQtys), state);
		}
	}

	private void restoreUnshippedQtyPickedForUnit(
			@NonNull final I_M_ShipmentSchedule_QtyPicked alloc,
			@NonNull final HuId vhuId,
			@NonNull final RestoredQty restoredQty,
			@NonNull final RestoreState state)
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(alloc.getM_ShipmentSchedule_ID());
		if (!state.getProcessedKeys().add(scheduleId.getRepoId() + "|" + vhuId.getRepoId()))
		{
			return; // already handled this (schedule, VHU) — allocations are consolidated to one row per VHU
		}

		// If the picking-job reopen (a Completed job) already restored an active un-shipped row, leave it.
		if (huShipmentScheduleDAO.existsActiveUnshippedQtyPickedForVHU(scheduleId, vhuId))
		{
			return;
		}

		// Nothing was restored (e.g. the shipment was recreated via "Generate Shipments", leaving the
		// picking job Drafted, so its reopen was a no-op). Re-create an active copy of the consolidated
		// reversed allocation so the picked qty survives this reverse. Direct copy — no step-replay, no
		// tryMerge — so the qty is not inflated and exactly one active row per tuple is preserved.
		// M_Picking_Job_Schedule_ID is intentionally NOT copied: the restored row is a plain picked row,
		// matching the shape a Completed-job reopen produces.
		final I_M_ShipmentSchedule_QtyPicked restored = newInstance(I_M_ShipmentSchedule_QtyPicked.class, alloc);
		restored.setAD_Org_ID(alloc.getAD_Org_ID());
		restored.setM_ShipmentSchedule_ID(alloc.getM_ShipmentSchedule_ID());
		restored.setVHU_ID(vhuId.getRepoId());
		restored.setM_TU_HU_ID(alloc.getM_TU_HU_ID());
		restored.setM_LU_HU_ID(alloc.getM_LU_HU_ID());
		restored.setIsAnonymousHuPickedOnTheFly(alloc.isAnonymousHuPickedOnTheFly());
		restoredQty.applyTo(restored);
		// M_InOutLine_ID stays null (un-shipped); IsActive defaults Y; Processed defaults N.
		huShipmentScheduleDAO.saveQtyPicked(restored);

		// The reverse reactivated the HU to Active; a picked row needs the HU to be Picked
		// (retrieveNotShippedRecords keeps only Picked/Shipped HUs). Also re-stamp BPartner/Location
		// from the schedule, exactly like addQtyPickedAndUpdateHU / tryMergeQtyPickedIntoExistingForVHU —
		// every pick-creation path must leave the top-level HU with the delivery partner/location set.
		final I_M_HU vhu = handlingUnitsDAO.getById(vhuId);
		final LUTUCUPair husPair = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(vhu);
		final I_M_HU topLevelHU = husPair.getTopLevelHU();
		setHUStatusToPicked(topLevelHU);
		setHUPartnerAndLocationFromSched(topLevelHU, shipmentScheduleBL.getById(scheduleId));
		handlingUnitsDAO.saveHU(topLevelHU);

		Loggables.withLogger(logger, Level.INFO).addLog(
				"restoreUnshippedQtyPickedIfMissing: re-created active QtyPicked_ID={} for shipmentScheduleId={} vhuId={} (reverse left no completed-job reopen to restore it)",
				restored.getM_ShipmentSchedule_QtyPicked_ID(), scheduleId, vhuId);
	}

	private de.metas.inoutcandidate.model.I_M_ShipmentSchedule getShipmentSchedule(final AddQtyPickedRequest request)
	{
		final ShipmentScheduleAndJobScheduleId scheduleId = request.getScheduleId();
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule cachedShipmentSchedules = request.getCachedShipmentSchedule();

		if (cachedShipmentSchedules != null
				&& ShipmentScheduleId.equals(ShipmentScheduleId.ofRepoId(cachedShipmentSchedules.getM_ShipmentSchedule_ID()), scheduleId.getShipmentScheduleId()))
		{
			return cachedShipmentSchedules;
		}

		return shipmentScheduleBL.getById(scheduleId.getShipmentScheduleId());
	}

	private static void setHUs(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord, final LUTUCUPair husPair)
	{
		qtyPickedRecord.setM_LU_HU(husPair.getM_LU_HU());
		qtyPickedRecord.setM_TU_HU(husPair.getM_TU_HU());
		qtyPickedRecord.setVHU(husPair.getVHU());
	}

	private void setHUStatusToPicked(@NonNull final I_M_HU hu)
	{
		// huContext might be needed if the M_HU_Status record has ExChangeGebindeLagerWhenEmpty='Y'
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();

		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Picked);
		// handlingUnitsDAO.saveHU(hu); // don't save it here
	}

	/**
	 * This method updates the given <code>hu</code>'s <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> from the given <code>sched</code>'s effective values.
	 * <p>
	 * NOTE:
	 * * this method is NOT saving the <code>hu</code>
	 * * these changes will be propagated to the HU's children by a model interceptor.
	 *
	 * @see IShipmentScheduleEffectiveBL
	 * @see de.metas.handlingunits.model.validator.M_HU#updateChildren(I_M_HU)
	 */
	private void setHUPartnerAndLocationFromSched(
			final I_M_HU hu,
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched)
	{
		final BPartnerId schedEffectiveBPartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);
		final BPartnerLocationId schedEffectiveBPLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(sched);

		hu.setC_BPartner_ID(schedEffectiveBPartnerId.getRepoId());
		hu.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(schedEffectiveBPLocationId));
	}

	@Override
	public void updateAllocationLUForTU(final I_M_HU tuHU)
	{
		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuHU), "{} shall be a TU", tuHU);

		final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);

		final IHUContext huContext = huContextFactory.createMutableHUContext(getContextAware(tuHU));

		//
		// Iterate all QtyPicked records and update M_LU_HU_ID
		final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(tuHU);
		for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
		{
			// Don't touch those which were already delivered
			if (shipmentScheduleAllocBL.isDelivered(ssQtyPicked))
			{
				continue;
			}

			// Update LU
			ssQtyPicked.setM_LU_HU(luHU);
			createCandidatesForQtyPicked(ssQtyPicked, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER).forEach(ShipmentScheduleWithHU::updateQtyTUAndQtyLU);

			huShipmentScheduleDAO.saveQtyPicked(ssQtyPicked);
		}
	}

	@Override
	public void updateAllocationLUAndTUForCU(final I_M_HU cuHU)
	{
		Check.assume(handlingUnitsBL.isPureVirtual(cuHU), "{} shall be a CU", cuHU);

		final I_M_HU tuHU = handlingUnitsBL.getTransportUnitHU(cuHU);
		final I_M_HU luHU = tuHU != null ? handlingUnitsBL.getLoadingUnitHU(tuHU) : null;

		final IHUContext huContext = huContextFactory.createMutableHUContext(getContextAware(cuHU));

		//
		// Iterate all QtyPicked records and update M_LU_HU_ID
		final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(cuHU);
		for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
		{
			// Don't touch those which were already delivered
			if (shipmentScheduleAllocBL.isDelivered(ssQtyPicked))
			{
				continue;
			}

			// Update LU
			ssQtyPicked.setM_TU_HU(tuHU);
			ssQtyPicked.setM_LU_HU(luHU);
			createCandidatesForQtyPicked(ssQtyPicked, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER).forEach(ShipmentScheduleWithHU::updateQtyTUAndQtyLU);

			huShipmentScheduleDAO.saveQtyPicked(ssQtyPicked);
		}
	}

	@Override
	public void unallocateTU(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
							 final I_M_HU tuHU,
							 final String trxName)
	{
		//
		// Retrieve QtyPicked records.
		// If there are not QtyPicked records for our TU then do nothing.
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(sched.getM_ShipmentSchedule_ID(), tuHU.getM_HU_ID(), trxName);
		if (qtyPickedList.isEmpty())
		{
			return;
		}

		//
		// Check if total quantity assigned is ZERO
		BigDecimal qtySum = ZERO;
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (!qtyPicked.isActive())
			{
				continue;
			}

			final BigDecimal qty = qtyPicked.getQtyPicked();
			qtySum = qtySum.add(qty);
		}
		if (qtySum.signum() != 0)
		{
			throw new AdempiereException("Cannot unassign " + tuHU + " from " + sched + " because currently assigned quantity is " + qtySum + " instead of ZERO.");
		}

		//
		// Inactivate all qty picked records
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (!qtyPicked.isActive())
			{
				continue;
			}

			qtyPicked.setIsActive(false);
			huShipmentScheduleDAO.saveQtyPicked(qtyPicked);
		}

		// also reset the HU's partner and location, unless another schedule still holds an active picked row on it
		resetConsigneeIfNoActivePickedRows(tuHU);
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU createInOutProducerFromShipmentSchedule()
	{
		final InOutGenerateResult result = inOutCandidateBL.createEmptyInOutGenerateResult(true); // storeReceipts = true

		return new InOutProducerFromShipmentScheduleWithHU(result);
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link InOutProducerFromShipmentScheduleWithHU#createShipmentHeader(I_M_ShipmentSchedule)}
	 */
	@Nullable
	@SuppressWarnings("JavadocReference")
	@Override
	public I_M_InOut getOpenShipmentOrNull(final @NonNull ShipmentScheduleWithHU candidate, final @NonNull LocalDate movementDate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = create(candidate.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

		final IContextAware contextProvider = getContextAware(shipmentSchedule);
		final IQueryBuilder<I_M_InOut> queryBuilder = queryBL.createQueryBuilder(I_M_InOut.class, contextProvider)
				.addOnlyActiveRecordsFilter();

		//
		// AD_Org_ID
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_AD_Org_ID, shipmentSchedule.getAD_Org_ID());
		}

		//
		// Document Type
		{
			final DocTypeQuery query = DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_MaterialDelivery)
					.adClientId(shipmentSchedule.getAD_Client_ID())
					.adOrgId(shipmentSchedule.getAD_Org_ID())
					.build();
			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(query);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeId);

			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_MovementType, X_M_InOut.MOVEMENTTYPE_CustomerShipment);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_IsSOTrx, true);
		}

		//
		// Document action/status
		// i.e. shipment shall be Drafted
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_DocAction, X_M_InOut.DOCACTION_Complete);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_DocStatus, X_M_InOut.DOCSTATUS_Drafted);
		}

		//
		// BPartner, Location & Contact
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_ID, shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule));
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_Location_ID, shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule));
			final BPartnerContactId adUserID = shipmentScheduleEffectiveBL.getBPartnerContactId(shipmentSchedule);
			if (adUserID != null)
			{
				queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_AD_User_ID, BPartnerContactId.toRepoId(adUserID));
			}
		}

		//
		// Document Dates:
		// * make sure our shipments are in the consolidation period (08225)
		final String shipmentConsolidationPeriod = getShipmentConsolidationPeriod();
		if (!Check.isEmpty(shipmentConsolidationPeriod, true)
				&& !"-".equals(shipmentConsolidationPeriod))
		{
			Check.assumeNotNull(movementDate, "movementDate not null");
			final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(shipmentConsolidationPeriod);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_MovementDate, movementDate, dateTruncModifier);
		}

		//
		// Warehouse
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_M_Warehouse_ID, shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule));
		}

		//
		// Match Shipper Transportation
		{
			//
			// Add the candidate's HUs so that they're checked to have a common shipper transportation (though only one of them should have it)
			final List<I_M_HU> hus = new ArrayList<>();
			hus.add(candidate.getVHU());
			hus.add(candidate.getM_TU_HU());
			hus.add(candidate.getM_LU_HU());
			final I_M_ShipperTransportation shipperTransportation = huShipperTransportationBL.getCommonM_ShipperTransportationOrNull(hus);
			if (shipperTransportation != null)
			{
				queryBuilder.addEqualsFilter(I_M_InOut.COLUMNNAME_M_ShipperTransportation, shipperTransportation.getM_ShipperTransportation_ID());
			}
		}

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_M_InOut.class)
				.addColumn(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID, Direction.Descending, Nulls.Last)
				.createQueryOrderBy();

		//
		// NO order filter // C_Order reference

		//
		// Fetch matching shipments
		final List<I_M_InOut> shipments = queryBuilder
				.create()
				.setOrderBy(orderBy)
				.list(I_M_InOut.class);
		if (shipments.isEmpty())
		{
			return null;
		}

		final int shipmentCount = shipments.size();
		if (shipmentCount > 1)
		{
			logger.warn("More than 1 open shipment found for schedule {}. Returning the first one (out of {}).", shipmentSchedule, shipmentCount);
		}

		return shipments.iterator().next();
	}

	@Override
	@Nullable
	public HUPIItemProductId getEffectivePackingMaterialId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule huShipmentSchedule = create(shipmentSchedule, I_M_ShipmentSchedule.class);

		final HUPIItemProductId pip = HUPIItemProductId.ofRepoIdOrNull(
				firstGreaterThanZero(
						huShipmentSchedule.getM_HU_PI_Item_Product_Override_ID(),
						huShipmentSchedule.getM_HU_PI_Item_Product_ID()));
		if (pip != null)
		{
			return pip;
		}

		// if is not set in shipment schedule, return the one form order line or null
		return extractOrderLinePackingMaterialIdOrNull(shipmentSchedule);
	}

	@Nullable
	private HUPIItemProductId extractOrderLinePackingMaterialIdOrNull(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIdsOrNull(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			return null;
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId, I_C_OrderLine.class);
		return HUPIItemProductId.ofRepoIdOrNull(orderLine.getM_HU_PI_Item_Product_ID());
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_IgnoringPickedHUs(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final HUPIItemProductId packingMaterialId = getEffectivePackingMaterialId(shipmentSchedule);
		if (packingMaterialId == null)
		{
			return null;
		}

		return huPIItemProductDAO.getRecordById(packingMaterialId);
	}

	@Override
	public Optional<TULoader> createTULoader(final I_M_ShipmentSchedule schedule)
	{
		final HUPIItemProductId tuPIItemProductId = getEffectivePackingMaterialId(schedule);
		if (tuPIItemProductId == null || tuPIItemProductId.isVirtualHU())
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Item_Product tuPIItemProduct = huPIItemProductDAO.getRecordById(tuPIItemProductId);
		final ProductId cuProductId = ProductId.ofRepoId(schedule.getM_Product_ID());
		final I_C_UOM cuUOM = shipmentScheduleBL.getUomOfProduct(schedule);

		final TULoader tuLoader = TULoader.builder()
				.huContext(handlingUnitsBL.createMutableHUContext())
				.tuPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI())
				.capacity(huCapacityBL.getCapacity(tuPIItemProduct, cuProductId, cuUOM))
				.build();

		return Optional.of(tuLoader);
	}

	@Override
	public I_M_HU_LUTU_Configuration deriveM_HU_LUTU_Configuration(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product_IgnoringPickedHUs(schedule);
		if (tuPIItemProduct == null || tuPIItemProduct.getM_HU_PI_Item_Product_ID() <= 0)
		{
			throw new HUException("@NotFound@ @M_HU_PI_Item_Product_ID@ (" + schedule + ")");
		}

		final UomId cuUOMId = shipmentScheduleBL.getUomIdOfProduct(schedule);
		final ProductId cuProductId = ProductId.ofRepoId(schedule.getM_Product_ID());

		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(schedule);
		final BPartnerLocationId bpartnerLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(schedule);
		final LocatorId locatorId = shipmentScheduleEffectiveBL.getDefaultLocatorId(schedule);

		// NOTE: we are not checking if tuPIItemProduct is for an PI of Type Transport Unit (TU)
		// because it can also be a Virtual PI and also because it's enough for us to find out an LU for it

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		lutuConfiguration.setM_Locator_ID(locatorId.getRepoId());
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);

		return lutuConfiguration;
	}

	@Override
	public IAggregationKeyBuilder<ShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder()
	{
		return new HUShipmentScheduleHeaderAggregationKeyBuilder();
	}

	public final String getShipmentConsolidationPeriod()
	{
		return sysConfigBL.getValue(SYSCONFIG_ShipmentConsolidationPeriod, DEFAULT_ShipmentConsolidationPeriod);
	}

	@Override
	public boolean isHUAllocation(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord)
	{
		Check.assumeNotNull(qtyPickedRecord, "qtyPickedRecord not null");
		return qtyPickedRecord.getVHU_ID() > 0;
	}

	@Override
	public void updateEffectiveValues(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule shipmentScheduleToUse = //
				create(shipmentSchedule, I_M_ShipmentSchedule.class);

		// initialize values with the calculated ones
		BigDecimal qtyTU_Effective = shipmentScheduleToUse.getQtyTU_Calculated();
		I_M_HU_PI_Item_Product piItemProduct_Effective = shipmentScheduleToUse.getM_HU_PI_Item_Product_Calculated();

		if (!isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override))
		{
			qtyTU_Effective = shipmentScheduleToUse.getQtyTU_Override();
		}

		if (!isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID))
		{
			piItemProduct_Effective = shipmentScheduleToUse.getM_HU_PI_Item_Product_Override();

			// safe because only applied when M_HU_PI_Item_Product_Effective is not null
			if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(piItemProduct_Effective.getM_HU_PI_Item().getM_HU_PI_Version().getHU_UnitType()))
			{
				qtyTU_Effective = ONE;
				shipmentScheduleToUse.setQtyTU_Override(ONE);
			}
		}

		shipmentScheduleToUse.setQtyOrdered_TU(qtyTU_Effective);
		shipmentScheduleToUse.setM_HU_PI_Item_Product(piItemProduct_Effective);

		// set pack description
		final String description;

		if (piItemProduct_Effective == null)
		{
			description = "";
		}
		else
		{
			description = piItemProduct_Effective.getDescription();
		}

		shipmentScheduleToUse.setPackDescription((Check.isEmpty(description, true) ? "" : description));
	}

	@Override
	public void updateHURelatedValuesFromOrderLine(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule shipmentScheduleToUse = create(shipmentSchedule, I_M_ShipmentSchedule.class);

		final HUPIItemProductId orderLinePackingMaterialId = extractOrderLinePackingMaterialIdOrNull(shipmentSchedule);
		if (shipmentSchedule.getC_OrderLine_ID() <= 0 || !HUPIItemProductId.isRegular(orderLinePackingMaterialId))
		{
			logger.debug("C_OrderLine_ID={}; orderLinePackingMaterialId={} is regular={}; -> unset M_HU_PI_Item_Product_ID and PackDescription",
					shipmentSchedule.getC_OrderLine_ID(), HUPIItemProductId.toRepoId(orderLinePackingMaterialId), HUPIItemProductId.isRegular(orderLinePackingMaterialId));
			shipmentScheduleToUse.setM_HU_PI_Item_Product_ID(-1);
			shipmentScheduleToUse.setPackDescription(null);
			return;

		}
		final I_C_OrderLine orderLine = create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		updatePackingInstructionsFromOrderLine(shipmentScheduleToUse, orderLine);
		updateHUQuantitiesFromOrderLine(shipmentScheduleToUse, orderLine);
		updatePackingRelatedQtys(shipmentScheduleToUse);
	}

	@Override
	public void updateExternalLineIdFromOrderLine(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final de.metas.interfaces.I_C_OrderLine orderLine = orderDAO.getOrderLineById(shipmentSchedule.getC_OrderLine_ID());

		shipmentSchedule.setExternalLineId(orderLine.getExternalId());
	}

	private void updatePackingInstructionsFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		final I_M_HU_PI_Item_Product piItemProduct_Effective = orderLine.getM_HU_PI_Item_Product();

		shipmentSchedule.setM_HU_PI_Item_Product_Calculated(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product(piItemProduct_Effective);

		if (shipmentSchedule.getM_HU_PI_Item_Product_Override_ID() <= 0)
		{
			shipmentSchedule.setPackDescription(orderLine.getPackDescription());
		}
	}

	private void updateHUQuantitiesFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyTU_Effective = orderLine.getQtyEnteredTU();

		shipmentSchedule.setQtyTU_Calculated(qtyTU_Effective);
		getEffectivePackingMaterialId(shipmentSchedule);

		shipmentSchedule.setQtyOrdered_TU(qtyTU_Effective);

		final I_M_HU_LUTU_Configuration lutuConfiguration = deriveM_HU_LUTU_Configuration(shipmentSchedule);

		//
		// task:10876 : calculate Qty Ordered LU  based on the Packing Material Max. Load Weight
		final I_M_HU_PI_Item m_lu_hu_pi_item = lutuConfiguration.getM_LU_HU_PI_Item();
		final HuPackingInstructionsVersionId versionId = Objects.nonNull(m_lu_hu_pi_item) ?
				HuPackingInstructionsVersionId.ofRepoId(m_lu_hu_pi_item.getM_HU_PI_Version_ID()) :
				null;
		final I_M_HU_PackingMaterial packingMaterial = Objects.nonNull(versionId) ?
				handlingUnitsDAO.retrievePackingMaterialByPIVersionID(versionId, BPartnerId.ofRepoId(shipmentSchedule.getC_BPartner_ID())) :
				null;

		if (Objects.nonNull(packingMaterial)
				&& packingMaterial.isQtyLUByMaxLoadWeight())
		{
			final BigDecimal qtyOrderedLU = lutuConfigurationFactory.calculateQtyLUForTotalQtyTUsByMaxWeight(lutuConfiguration, qtyTU_Effective, packingMaterial);
			shipmentSchedule.setQtyOrdered_LU(qtyOrderedLU);
		}
		//
		// Fallback to default behavior when no Packing Material is found OR isQtyLUByMaxLoadWeight is N
		else
		{
			final int qtyOrderedLU = lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(lutuConfiguration, qtyTU_Effective);
			shipmentSchedule.setQtyOrdered_LU(BigDecimal.valueOf(qtyOrderedLU));
		}
	}

	private void updatePackingRelatedQtys(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleHUPackingAware packingAware = new ShipmentScheduleHUPackingAware(shipmentSchedule);

		final BigDecimal qtyTUCalculated = shipmentSchedule.getQtyTU_Calculated();

		if (!qtyTUCalculated.equals(shipmentSchedule.getQtyOrdered_TU()))
		{
			// Calculate and set QtyEntered(CU) from M_HU_PI_Item_Product and QtyEnteredTU(aka QtyPacks)
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, packingAware.getQtyTU());
		}

		final int hupipCalculatedID = shipmentSchedule.getM_HU_PI_Item_Product_Calculated_ID();
		final int currentHUPIPID = shipmentSchedule.getM_HU_PI_Item_Product_ID();

		if (hupipCalculatedID != currentHUPIPID)
		{
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, packingAware.getQtyTU());

			shipmentSchedule.setQtyOrdered_Override(packingAware.getQty());
		}
	}

	@Override
	public void closeShipmentSchedule(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleBL.closeShipmentSchedule(shipmentSchedule);
	}

	@Override
	public void closeShipmentSchedules(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		shipmentScheduleBL.closeShipmentSchedules(shipmentScheduleIds);
	}

	@Override
	public void deleteByTopLevelHUAndShipmentScheduleId(@NonNull final HuId topLevelHUId, @NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_HU topLevelHU = handlingUnitsBL.getById(topLevelHUId);
		deleteByTopLevelHUsAndShipmentScheduleId(ImmutableList.of(topLevelHU), shipmentScheduleId);
	}

	@Override
	public void deleteByTopLevelHUsAndShipmentScheduleId(@NonNull final Collection<I_M_HU> topLevelHUs, @NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		for (final I_M_HU topLevelHU : topLevelHUs)
		{
			final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = huShipmentScheduleDAO.retrieveByTopLevelHUAndShipmentScheduleId(topLevelHU, shipmentScheduleId);
			assertNotAlreadyShipped(qtyPickedRecords, HuId.ofRepoId(topLevelHU.getM_HU_ID()));
			shipmentScheduleAllocBL.deleteRecords(qtyPickedRecords);

			// also reset the HU's partner and location, unless another schedule still holds an active picked row on it
			resetConsigneeIfNoActivePickedRows(topLevelHU);
		}
	}

	@Override
	public void reduceQtyPickedForPickToTU(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final HuId pickToTuId,
			@NonNull final Quantity qtyToReduce)
	{
		if (qtyToReduce.isZeroOrNegative())
		{
			return;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(
				shipmentScheduleId.getRepoId(), pickToTuId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
		if (qtyPickedRecords.isEmpty())
		{
			return;
		}

		assertNotAlreadyShipped(qtyPickedRecords, pickToTuId);

		// Newest-first (highest M_ShipmentSchedule_QtyPicked_ID = newest pick), mirroring the HU-side
		// LIFO order the unpick command already used to select which picked HUs to unpick.
		final List<I_M_ShipmentSchedule_QtyPicked> newestFirst = qtyPickedRecords.stream()
				.sorted(Comparator.comparingInt(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID).reversed())
				.collect(Collectors.toList());

		final I_M_HU tuHU = handlingUnitsBL.getById(pickToTuId);
		final IHUContext huContext = huContextFactory.createMutableHUContext(getContextAware(tuHU));

		final List<I_M_ShipmentSchedule_QtyPicked> fullyConsumedRecords = new ArrayList<>();
		BigDecimal remaining = qtyToReduce.getAsBigDecimal();

		for (final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : newestFirst)
		{
			if (remaining.signum() <= 0)
			{
				break;
			}

			final BigDecimal rowQtyPicked = qtyPickedRecord.getQtyPicked();
			if (rowQtyPicked.compareTo(remaining) <= 0)
			{
				// Row fully consumed by the unpick qty -> delete it entirely.
				fullyConsumedRecords.add(qtyPickedRecord);
				remaining = remaining.subtract(rowQtyPicked);
			}
			else
			{
				// Row only partially consumed -> reduce QtyPicked (+ QtyTU/QtyLU, QtyDeliveredCatch) and stop.
				final BigDecimal newQtyPicked = rowQtyPicked.subtract(remaining);
				reduceCatchWeightProportionally(qtyPickedRecord, rowQtyPicked, newQtyPicked);
				qtyPickedRecord.setQtyPicked(newQtyPicked);
				createCandidatesForQtyPicked(qtyPickedRecord, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER)
						.forEach(ShipmentScheduleWithHU::updateQtyTUAndQtyLU);
				huShipmentScheduleDAO.saveQtyPicked(qtyPickedRecord);
				remaining = BigDecimal.ZERO;
			}
		}

		Check.assume(remaining.signum() <= 0,
				"qtyToReduce={} must not exceed the total active QtyPicked for shipmentScheduleId={} and pickToTuId={}",
				qtyToReduce, shipmentScheduleId, pickToTuId);

		if (!fullyConsumedRecords.isEmpty())
		{
			shipmentScheduleAllocBL.deleteRecords(fullyConsumedRecords);
		}

		// also reset the HU's partner and location, unless another schedule (or this one's partial
		// remainder) still holds an active picked row on it. A bare TU can be shared across schedules,
		// so we must not strip the consignee while another line still holds picked qty on it.
		resetConsigneeIfNoActivePickedRows(tuHU);
	}

	/**
	 * Resets the top-level HU's consignee (C_BPartner_ID / C_BPartner_Location_ID) back to none, UNLESS another
	 * active {@link I_M_ShipmentSchedule_QtyPicked} row (this schedule's partial remainder, or another schedule
	 * sharing the HU) still holds picked qty on it. Callers MUST delete/inactivate their own now-obsolete rows
	 * BEFORE calling this, so the unscoped {@code topLevelHU} query below reflects the post-delete state.
	 */
	private void resetConsigneeIfNoActivePickedRows(@NonNull final I_M_HU topLevelHU)
	{
		if (huShipmentScheduleDAO.hasActiveQtyPickedForTopLevelHU(topLevelHU))
		{
			return; // another schedule (or this one's partial remainder) still holds picked qty on this HU
		}

		topLevelHU.setC_BPartner_ID(0);
		topLevelHU.setC_BPartner_Location_ID(0);
		handlingUnitsDAO.save(topLevelHU);
	}

	/**
	 * Scales {@code QtyDeliveredCatch} by the stock-qty ratio {@code newQtyPicked / oldQtyPicked}, mirroring how
	 * the pick side spreads catch weight across CUs ({@code PickingJobPickCommand#getCatchWeight().spreadEqually}).
	 * No-op when the row does not track a catch qty (guard: an unset {@code Catch_UOM_ID} must never reach
	 * {@code UomId.ofRepoId(...)}, which asserts a positive id).
	 */
	private static void reduceCatchWeightProportionally(
			@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord,
			@NonNull final BigDecimal oldQtyPicked,
			@NonNull final BigDecimal newQtyPicked)
	{
		if (qtyPickedRecord.getCatch_UOM_ID() <= 0)
		{
			return;
		}

		final BigDecimal oldQtyDeliveredCatch = qtyPickedRecord.getQtyDeliveredCatch();
		if (oldQtyDeliveredCatch == null || oldQtyPicked.signum() == 0)
		{
			return;
		}

		final BigDecimal newQtyDeliveredCatch = oldQtyDeliveredCatch
				.multiply(newQtyPicked)
				.divide(oldQtyPicked, Math.max(oldQtyDeliveredCatch.scale(), 2), RoundingMode.HALF_UP);
		qtyPickedRecord.setQtyDeliveredCatch(newQtyDeliveredCatch);
	}

	private static void assertNotAlreadyShipped(final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords, @NonNull final HuId huIdInScope)
	{
		for (final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			assertNotAlreadyShipped(qtyPickedRecord, huIdInScope);
		}
	}

	private static void assertNotAlreadyShipped(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord, @NonNull final HuId huIdInScope)
	{
		if (qtyPickedRecord.getM_InOutLine_ID() > 0)
		{
			throw new AdempiereException(
					MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P,
					huIdInScope.getRepoId(),
					qtyPickedRecord.getM_InOutLine().getM_InOut().getDocumentNo());
		}
	}

	@Override
	public WarehouseId getWarehouseId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getWarehouseId(schedule);
	}

	@Override
	public BPartnerId getBPartnerId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerId(schedule);
	}

	@Override
	public Quantity getQtyToDeliver(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleBL.getQtyToDeliver(schedule);
	}

	@Override
	public Quantity getQtyScheduledForPicking(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		return shipmentScheduleBL.getQtyScheduledForPicking(shipmentScheduleRecord);
	}

	@Override
	public Quantity getQtyRemainingToScheduleForPicking(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		return shipmentScheduleBL.getQtyRemainingToScheduleForPicking(shipmentScheduleRecord);
	}

	@Override
	public void flagForRecompute(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty()) {return;}

		final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
		invalidSchedulesService.flagForRecompute(shipmentScheduleIds);
	}

	@Override
	public ShipmentScheduleLoadingCache<I_M_ShipmentSchedule> newLoadingCache()
	{
		return shipmentScheduleBL.newLoadingCache(I_M_ShipmentSchedule.class);
	}

	@Nullable
	@Override
	public ProjectId extractSingleProjectIdOrNull(@NonNull final List<ShipmentScheduleWithHU> candidates)
	{
		final Set<ProjectId> projectIdsFromShipmentSchedules = candidates.stream()
				.map(ShipmentScheduleWithHU::getProjectId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		if (projectIdsFromShipmentSchedules.size() == 1)
		{
			return projectIdsFromShipmentSchedules.iterator().next();
		}
		return null;
	}

	@Override
	public List<ShipmentScheduleWithHU> createCandidatesForQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPicked,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		// Picking via PickingJobPickCommand records only M_TU_HU_ID (VHU_ID stays NULL).
		final HuId vhuId = HuId.ofRepoIdOrNull(qtyPicked.getVHU_ID());
		final HuId tuHuId = HuId.ofRepoIdOrNull(qtyPicked.getM_TU_HU_ID());
		final HuId luHuId = HuId.ofRepoIdOrNull(qtyPicked.getM_LU_HU_ID());
		final HuId huToInspectId = CoalesceUtil.coalesce(vhuId, tuHuId, luHuId);
		if (huToInspectId == null)
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		final I_M_HU huToInspect = handlingUnitsBL.getById(huToInspectId);
		if (handlingUnitsBL.isVirtual(huToInspect))
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		final ProductId productId = ProductId.ofRepoId(qtyPicked.getM_ShipmentSchedule().getM_Product_ID());
		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final IAttributeStorageFactory attrFactory = huContext.getHUAttributeStorageFactory();

		// Split only by attributes whitelisted in M_ShipmentSchedule_AttributeConfig — the same criterion
		// the M_InOutLine aggregation uses (ShipmentScheduleWithHU#computeAttributeValues).
		final Predicate<AttributeId> attributeSplitsShipmentLine = attributeSplitsShipmentLinePredicate(qtyPicked);

		// One pass: for each child VHU compute its UseInASI fingerprint once, then emit one
		// (fingerprint, productStorage) entry per non-empty product storage of that VHU.
		// Filter by product so VHUs carrying a different product in the same TU are excluded.
		// UOM consistency is guaranteed: qtyPicked links to one M_ShipmentSchedule which has one
		// M_Product_ID and therefore one stocking UOM; all filtered storages carry that same product
		// and are in the same UOM — no conversion needed.
		final ImmutableListMultimap<AttributesKey, IHUProductStorage> storagesByFingerprint =
				handlingUnitsDAO.retrieveIncludedHUs(huToInspect)
						.stream()
						.filter(handlingUnitsBL::isVirtual)
						.flatMap(vhu -> groupProductStoragesByFingerprint(vhu, productId, storageFactory, attrFactory, attributeSplitsShipmentLine))
						.collect(ImmutableListMultimap.toImmutableListMultimap(Map.Entry::getKey, Map.Entry::getValue));

		if (storagesByFingerprint.isEmpty())
		{
			// No VHUs found for this product — nothing to expand, use default candidate.
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		// Case 1: find the VHU whose qty exactly matches QtyPicked — one candidate using that VHU.
		// Using the specific VHU as override ensures its UseInASI attributes are read from the VHU
		// itself rather than from the destination TU, which may carry mixed/null attribute values.
		// UOM is consistent (same product → same stocking UOM), so BigDecimal comparison is safe.
		final BigDecimal pickedQtyBD = qtyPicked.getQtyPicked();
		final IHUProductStorage exactMatchStorage = storagesByFingerprint.values().stream()
				.filter(s -> s.getQty().getAsBigDecimal().compareTo(pickedQtyBD) == 0)
				.findFirst()
				.orElse(null);
		if (exactMatchStorage != null)
		{
			return ImmutableList.of(ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedForVHU(
					qtyPicked, huContext, exactMatchStorage.getM_HU(), exactMatchStorage.getQty(), qtyTypeToUse));
		}

		// Case 2: whole-TU-pick — the QtyPicked covers the entire TU content (one pick for all VHUs).
		// Guard: if the picked qty does not equal the total qty across all VHU storages, this QtyPicked covers
		// only part of the TU and another QtyPicked record covers the rest — fall back to a single
		// default candidate so each QtyPicked is not independently inflated by the full group sums.
		final boolean totalVHUQtyEqualsPickedQty = storagesByFingerprint.values().stream()
				.map(IHUProductStorage::getQty)
				.reduce(Quantity::add)
				.filter(total -> total.qtyAndUomCompareToEquals(Quantity.of(pickedQtyBD, total.getUOM())))
				.isPresent();
		if (!totalVHUQtyEqualsPickedQty)
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		// Split the single whole-TU pick into one M_ShipmentSchedule_QtyPicked row per attribute-fingerprint group,
		// so every resulting M_InOutLine (one per distinct UseInASI attribute set) binds its OWN allocation — exactly
		// as the multi-pick path already does. Sharing one row across groups would leave all but one line with no
		// QtyPicked, breaking qty-sync (M_InOutLine.onMovementQtyChange) and HU-based package/content resolution.
		// Reuse the original row for the first group; split off a copy (same TU/LU) for each further group.
		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		final List<AttributesKey> fingerprints = ImmutableList.copyOf(storagesByFingerprint.keySet());
		final List<Quantity> groupQtys = fingerprints.stream()
				.map(fp -> storagesByFingerprint.get(fp).stream()
						.map(IHUProductStorage::getQty)
						.reduce(Quantity::add)
						.get())
				.collect(ImmutableList.toImmutableList());
		final List<Optional<Quantity>> catchWeightShares = splitCatchWeightByQty(qtyPicked, groupQtys);
		int groupIndex = -1;
		for (final AttributesKey fingerprint : fingerprints)
		{
			groupIndex++;
			final List<IHUProductStorage> group = storagesByFingerprint.get(fingerprint);
			// Any VHU of the group supplies the group's (shared) attribute set — the candidate/line reads its
			// attributes from it. All members carry the same fingerprint, so the choice is arbitrary.
			final IHUProductStorage attributeSourceStorage = group.get(0);
			final I_M_HU attributeSourceVHU = attributeSourceStorage.getM_HU();
			final Quantity groupStorageQty = group.stream()
					.map(IHUProductStorage::getQty)
					.reduce(attributeSourceStorage.getQty().toZero(), Quantity::add);

			final I_M_ShipmentSchedule_QtyPicked groupQtyPicked = rowForPart(qtyPicked, groupIndex == 0, groupStorageQty, catchWeightShares.get(groupIndex));
			// Reference the VHU on the row only when the group is a single VHU — then the row's qty equals that
			// VHU's qty exactly, so it is accurate and each VHU is referenced by at most one row. For a multi-VHU
			// group keep VHU_ID null: the group's summed qty must not be attributed to one child VHU (that would
			// over-state it and mis-restore it on reversal). Either way the representative VHU is the candidate's
			// attribute source (in-memory override) so the line's attributes are read from it.
			result.add(saveRowAndCreateCandidate(
					groupQtyPicked, new CandidatePart(groupStorageQty, group.size() == 1 ? attributeSourceVHU : null, attributeSourceVHU), huContext, qtyTypeToUse));
		}
		return result.build();
	}

	@Override
	public List<ShipmentScheduleWithHU> createCandidatesForBoxPicks(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		// Rows that point to a box (non-virtual TU) are collected per (shipment schedule, box); every other row is handled alone.
		// The candidates keep the order of the given rows: a box's candidates come where the box's first row is.
		// The order matters because consecutive candidates are merged into one shipment line where they can be.
		final Map<HuId, Boolean> isVirtualByTuId = new HashMap<>();
		final LinkedHashMap<Object, List<I_M_ShipmentSchedule_QtyPicked>> rowsByBoxOrRow = new LinkedHashMap<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : qtyPickedRecords)
		{
			final HuId tuId = HuId.ofRepoIdOrNull(row.getM_TU_HU_ID());
			final boolean isBoxRow = tuId != null
					&& !isVirtualByTuId.computeIfAbsent(tuId, id -> handlingUnitsBL.isVirtual(handlingUnitsBL.getById(id)));
			final Object key = isBoxRow ? new BoxKey(ShipmentScheduleId.ofRepoId(row.getM_ShipmentSchedule_ID()), tuId) : new Object();
			rowsByBoxOrRow.computeIfAbsent(key, k -> new ArrayList<>()).add(row);
		}

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		rowsByBoxOrRow.forEach((key, rows) -> result.addAll(key instanceof BoxKey
				? createCandidatesForBox((BoxKey)key, rows, huContext, qtyTypeToUse)
				: createCandidatesForQtyPicked(rows.get(0), huContext, qtyTypeToUse)));
		return result.build();
	}

	@Override
	public ImmutableSet<HuId> retrievePickedVhuIds(@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPicked)
	{
		final HuId vhuId = HuId.ofRepoIdOrNull(qtyPicked.getVHU_ID());
		if (vhuId != null)
		{
			return ImmutableSet.of(vhuId);
		}

		final HuId tuId = HuId.ofRepoIdOrNull(qtyPicked.getM_TU_HU_ID());
		if (tuId == null || handlingUnitsBL.isVirtual(handlingUnitsBL.getById(tuId)))
		{
			return ImmutableSet.of();
		}

		// a box row stands for the units of the schedule's product that the schedule picked into the box
		final I_M_HU box = handlingUnitsBL.getById(tuId);
		final IHUStorageFactory storageFactory = huContextFactory.createMutableHUContext(getContextAware(box)).getHUStorageFactory();
		return retrieveBoxUnitsOfSchedule(box, qtyPicked, storageFactory).stream()
				.map(vhu -> HuId.ofRepoId(vhu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return the virtual HUs (CUs) of the box that hold the schedule's product. If the schedule's picking job steps recorded the
	 * units they picked, only those count: a box that several schedules picked into holds the units of all of them.
	 */
	private List<I_M_HU> retrieveBoxUnitsOfSchedule(
			@NonNull final I_M_HU box,
			@NonNull final I_M_ShipmentSchedule_QtyPicked anyRowOfSchedule,
			@NonNull final IHUStorageFactory storageFactory)
	{
		final ProductId productId = ProductId.ofRepoId(anyRowOfSchedule.getM_ShipmentSchedule().getM_Product_ID());
		final Set<HuId> unitIdsRecordedBySteps = retrieveUnitIdsRecordedByPickingJobSteps(ShipmentScheduleId.ofRepoId(anyRowOfSchedule.getM_ShipmentSchedule_ID()));
		final boolean isEveryUnitCounted = unitIdsRecordedBySteps.isEmpty() || unitIdsRecordedBySteps.contains(HuId.ofRepoId(box.getM_HU_ID()));

		return retrieveUnitsHoldingProduct(box, productId, storageFactory).stream()
				.filter(vhu -> isEveryUnitCounted || unitIdsRecordedBySteps.contains(HuId.ofRepoId(vhu.getM_HU_ID())))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return the virtual HUs (CUs) of the box that hold the product
	 */
	private List<I_M_HU> retrieveUnitsHoldingProduct(
			@NonNull final I_M_HU box,
			@NonNull final ProductId productId,
			@NonNull final IHUStorageFactory storageFactory)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(box)
				.stream()
				.filter(handlingUnitsBL::isVirtual)
				.filter(vhu -> {
					final IHUProductStorage productStorage = storageFactory.getStorage(vhu).getProductStorageOrNull(productId);
					return productStorage != null && !productStorage.isEmpty();
				})
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableSet<HuId> retrieveUnitIdsRecordedByPickingJobSteps(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final IQuery<I_M_Picking_Job_Step> stepsOfSchedule = queryBL.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Step.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create();
		return queryBL.createQueryBuilder(I_M_Picking_Job_Step_PickedHU.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Picking_Job_Step_PickedHU.COLUMNNAME_M_Picking_Job_Step_ID, I_M_Picking_Job_Step.COLUMNNAME_M_Picking_Job_Step_ID, stepsOfSchedule)
				.create()
				.listDistinctAsImmutableSet(I_M_Picking_Job_Step_PickedHU.COLUMNNAME_Picked_HU_ID, Integer.class)
				.stream()
				.map(HuId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @param rows all active rows of the schedule that point to the box
	 */
	private List<ShipmentScheduleWithHU> createCandidatesForBox(
			@NonNull final BoxKey box,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> rows,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		// A row that already references its unit (e.g. from an earlier generation) is not a box row and is not part of the content to hand out.
		final List<I_M_ShipmentSchedule_QtyPicked> rowsWithUnit = rows.stream()
				.filter(row -> HuId.ofRepoIdOrNull(row.getVHU_ID()) != null)
				.collect(ImmutableList.toImmutableList());
		final Set<HuId> unitIdsOfRowsWithUnit = rowsWithUnit.stream()
				.map(row -> HuId.ofRepoId(row.getVHU_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final List<I_M_ShipmentSchedule_QtyPicked> boxRows = rows.stream()
				.filter(row -> HuId.ofRepoIdOrNull(row.getVHU_ID()) == null)
				.sorted(Comparator.comparingInt(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		rowsWithUnit.forEach(row -> result.addAll(createCandidatesForQtyPicked(row, huContext, qtyTypeToUse)));
		if (!boxRows.isEmpty())
		{
			final List<BoxContentGroup> contentGroups = retrieveBoxContentGroups(box, boxRows.get(0), unitIdsOfRowsWithUnit, huContext);
			// One combination only in the whole box (units of other products do not count): there is nothing to split, so the rows
			// keep the handling they had before, the line takes the box's attributes. Units that other schedules or rows of their own
			// stand for count: they decide which attributes the box shows.
			final boolean hasSeveralCombinations = contentGroups.size() > 1 || countCombinationsOfProductInBox(box, boxRows.get(0), huContext) > 1;
			if (hasSeveralCombinations && rowsPickedWholeContent(boxRows, contentGroups))
			{
				result.addAll(createCandidatesPerContentGroup(boxRows, contentGroups, huContext, qtyTypeToUse));
			}
			else
			{
				// The box content is not exactly what these rows picked (e.g. a box shared with another schedule): each row alone.
				result.addAll(createCandidatesPerRow(boxRows, huContext, qtyTypeToUse));
			}
		}
		return result.build();
	}

	/**
	 * @return how many different attribute combinations (as far as they split the shipment line) the units of the box that hold the
	 * schedule's product have, whoever picked them
	 */
	private long countCombinationsOfProductInBox(
			@NonNull final BoxKey box,
			@NonNull final I_M_ShipmentSchedule_QtyPicked anyBoxRow,
			@NonNull final IHUContext huContext)
	{
		final ProductId productId = ProductId.ofRepoId(anyBoxRow.getM_ShipmentSchedule().getM_Product_ID());
		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final IAttributeStorageFactory attrFactory = huContext.getHUAttributeStorageFactory();
		final Predicate<AttributeId> attributeSplitsShipmentLine = attributeSplitsShipmentLinePredicate(anyBoxRow);
		return retrieveUnitsHoldingProduct(handlingUnitsBL.getById(box.getBoxId()), productId, storageFactory).stream()
				.flatMap(vhu -> groupProductStoragesByFingerprint(vhu, productId, storageFactory, attrFactory, attributeSplitsShipmentLine))
				.map(Map.Entry::getKey)
				.distinct()
				.count();
	}

	/**
	 * @return the content of the box per attribute combination (first-seen order of the box's VHUs, which follows pick order),
	 * without the units that rows already reference
	 */
	private List<BoxContentGroup> retrieveBoxContentGroups(
			@NonNull final BoxKey box,
			@NonNull final I_M_ShipmentSchedule_QtyPicked anyBoxRow,
			@NonNull final Set<HuId> unitIdsToSkip,
			@NonNull final IHUContext huContext)
	{
		final ProductId productId = ProductId.ofRepoId(anyBoxRow.getM_ShipmentSchedule().getM_Product_ID());
		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final IAttributeStorageFactory attrFactory = huContext.getHUAttributeStorageFactory();
		final Predicate<AttributeId> attributeSplitsShipmentLine = attributeSplitsShipmentLinePredicate(anyBoxRow);

		final LinkedHashMap<AttributesKey, List<IHUProductStorage>> storagesByFingerprint = new LinkedHashMap<>();
		retrieveBoxUnitsOfSchedule(handlingUnitsBL.getById(box.getBoxId()), anyBoxRow, storageFactory)
				.stream()
				.filter(vhu -> !unitIdsToSkip.contains(HuId.ofRepoId(vhu.getM_HU_ID())))
				.flatMap(vhu -> groupProductStoragesByFingerprint(vhu, productId, storageFactory, attrFactory, attributeSplitsShipmentLine))
				.forEach(fingerprintAndStorage -> storagesByFingerprint
						.computeIfAbsent(fingerprintAndStorage.getKey(), fingerprint -> new ArrayList<>())
						.add(fingerprintAndStorage.getValue()));
		return storagesByFingerprint.values().stream()
				.map(BoxContentGroup::new)
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean rowsPickedWholeContent(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> boxRows,
			@NonNull final List<BoxContentGroup> contentGroups)
	{
		if (contentGroups.isEmpty() || boxRows.stream().anyMatch(row -> row.getQtyPicked().signum() < 0))
		{
			return false;
		}
		final Quantity contentQty = contentGroups.stream()
				.map(BoxContentGroup::totalQty)
				.reduce(Quantity::add)
				.get();
		final Quantity pickedQty = boxRows.stream()
				.map(row -> Quantity.of(row.getQtyPicked(), contentQty.getUOM())) // both in the stock UOM
				.reduce(Quantity::add)
				.get();
		return contentQty.qtyAndUomCompareToEquals(pickedQty);
	}

	private List<ShipmentScheduleWithHU> createCandidatesPerRow(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> boxRows,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		return boxRows.stream()
				.flatMap(row -> createCandidatesForQtyPicked(row, huContext, qtyTypeToUse).stream())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Walks rows and content groups together, consuming quantity: every overlap of a row part and a group is one candidate.
	 * <p>
	 * Limit: rows are given to the groups in row-id order. When the picks of one lot are interleaved with picks of another
	 * (lot A, lot B, lot A), the catch weight and the unit a lot's line carries can come from the other lot's pick.
	 * Quantities and totals stay right.
	 */
	private List<ShipmentScheduleWithHU> createCandidatesPerContentGroup(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> boxRows,
			@NonNull final List<BoxContentGroup> contentGroups,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		final I_C_UOM uom = contentGroups.get(0).uom();
		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		int groupIndex = 0;
		for (final I_M_ShipmentSchedule_QtyPicked row : boxRows)
		{
			final List<RowPart> parts = new ArrayList<>();
			Quantity rowRemaining = Quantity.of(row.getQtyPicked(), uom);
			while (rowRemaining.signum() > 0)
			{
				final BoxContentGroup group = contentGroups.get(groupIndex);
				final Quantity partQty = rowRemaining.min(group.remainingQty());
				parts.add(new RowPart(group, partQty, group.wholeVhuOrNull(partQty)));
				rowRemaining = rowRemaining.subtract(partQty);
				if (group.remainingQty().compareTo(partQty) == 0)
				{
					groupIndex++;
				}
				group.consume(partQty);
			}

			if (parts.isEmpty())
			{
				// a row without quantity gets its candidate as on every other path
				result.addAll(ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(row, huContext, qtyTypeToUse));
			}
			else
			{
				result.addAll(createCandidatesForRowParts(row, parts, huContext, qtyTypeToUse));
			}
		}
		return result.build();
	}

	private List<ShipmentScheduleWithHU> createCandidatesForRowParts(
			@NonNull final I_M_ShipmentSchedule_QtyPicked row,
			@NonNull final List<RowPart> parts,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		final List<Optional<Quantity>> catchWeightShares = parts.size() > 1
				? splitCatchWeightByQty(row, parts.stream().map(RowPart::getQty).collect(ImmutableList.toImmutableList()))
				: ImmutableList.of(Optional.<Quantity>empty());

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		for (int i = 0; i < parts.size(); i++)
		{
			final RowPart part = parts.get(i);
			final I_M_ShipmentSchedule_QtyPicked partRecord = rowForPart(row, i == 0, part.getQty(), catchWeightShares.get(i));
			result.add(saveRowAndCreateCandidate(partRecord, new CandidatePart(part.getQty(), part.getWholeVhu(), part.getGroup().firstVhu()), huContext, qtyTypeToUse));
		}
		return result.build();
	}

	/**
	 * @return the source row itself for the first part (it keeps its identity), a new row split off the source for every further part
	 */
	private I_M_ShipmentSchedule_QtyPicked rowForPart(
			@NonNull final I_M_ShipmentSchedule_QtyPicked source,
			final boolean isFirstPart,
			@NonNull final Quantity partQty,
			@NonNull final Optional<Quantity> catchWeightShare)
	{
		if (isFirstPart)
		{
			catchWeightShare.ifPresent(share -> source.setQtyDeliveredCatch(share.toBigDecimal()));
			return source;
		}
		return splitOffQtyPickedRow(source, partQty, catchWeightShare);
	}

	/**
	 * Creates (does not save) a row for a part of the source row's quantity.
	 * Copies the columns that tell where and by which pick the quantity was picked: box, pallet, picking job schedule and the
	 * picked-on-the-fly flag, so the part is found by the queries that look for them. {@code QtyTU} and {@code QtyLU} are not
	 * copied: they count boxes and pallets of the whole pick and would multiply them.
	 */
	private I_M_ShipmentSchedule_QtyPicked splitOffQtyPickedRow(
			@NonNull final I_M_ShipmentSchedule_QtyPicked source,
			@NonNull final Quantity partQty,
			@NonNull final Optional<Quantity> catchWeightShare)
	{
		final ProductId productId = ProductId.ofRepoId(source.getM_ShipmentSchedule().getM_Product_ID());
		final StockQtyAndUOMQty partStockAndCatchQty = catchWeightShare.isPresent()
				? StockQtyAndUOMQtys.create(partQty.toBigDecimal(), productId, catchWeightShare.get().toBigDecimal(), UomId.ofRepoIdOrNull(source.getCatch_UOM_ID()))
				: StockQtyAndUOMQtys.ofQtyInStockUOM(partQty, productId);
		final I_M_ShipmentSchedule_QtyPicked part = shipmentScheduleAllocBL.createNewQtyPickedRecordNoSave(
				source.getM_ShipmentSchedule(),
				partStockAndCatchQty,
				I_M_ShipmentSchedule_QtyPicked.class);
		part.setM_TU_HU_ID(source.getM_TU_HU_ID());
		part.setM_LU_HU_ID(source.getM_LU_HU_ID());
		part.setM_Picking_Job_Schedule_ID(source.getM_Picking_Job_Schedule_ID());
		part.setIsAnonymousHuPickedOnTheFly(source.isAnonymousHuPickedOnTheFly());
		return part;
	}

	private ShipmentScheduleWithHU saveRowAndCreateCandidate(
			@NonNull final I_M_ShipmentSchedule_QtyPicked row,
			@NonNull final CandidatePart part,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		if (part.getWholeVhu() != null)
		{
			row.setVHU_ID(part.getWholeVhu().getM_HU_ID());
		}
		row.setQtyPicked(part.getQty().toBigDecimal());
		huShipmentScheduleDAO.saveQtyPicked(row);
		return ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedForVHU(row, huContext, part.getAttributeSourceVhu(), part.getQty(), qtyTypeToUse);
	}

	/**
	 * The part of a row's quantity that becomes one candidate.
	 */
	@Value
	private static class CandidatePart
	{
		@NonNull Quantity qty;
		/** the unit the quantity is exactly; {@code null} if it cannot be attributed to one unit */
		@Nullable I_M_HU wholeVhu;
		/** the unit whose attributes the candidate (and so the shipment line) takes */
		@NonNull I_M_HU attributeSourceVhu;
	}

	/**
	 * What one call of {@link #restoreUnshippedQtyPickedIfMissing(Collection)} keeps between its rows.
	 */
	@Getter
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static class RestoreState
	{
		@NonNull private final IHUContext huContext;
		/** units that a row of its own already stands for: not part of a box row */
		@NonNull private final Set<HuId> unitIdsOfRowsWithUnit;
		/** (schedule, unit) pairs already handled */
		@NonNull private final Set<String> processedKeys = new HashSet<>();
		/** per (schedule, box): the quantity of the reversed box rows that is not restored yet */
		@NonNull @Getter(AccessLevel.NONE) private final Map<BoxKey, Quantity> boxQtyLeft;

		static RestoreState of(@NonNull final Collection<I_M_ShipmentSchedule_QtyPicked> reversedAllocations, @NonNull final IHUContext huContext, @NonNull final IProductBL productBL)
		{
			final Set<HuId> unitIds = new HashSet<>();
			final Map<BoxKey, Quantity> boxQtyLeft = new HashMap<>();
			for (final I_M_ShipmentSchedule_QtyPicked alloc : reversedAllocations)
			{
				final HuId vhuId = HuId.ofRepoIdOrNull(alloc.getVHU_ID());
				final HuId tuId = HuId.ofRepoIdOrNull(alloc.getM_TU_HU_ID());
				if (vhuId != null)
				{
					unitIds.add(vhuId);
				}
				else if (tuId != null)
				{
					// QtyPicked is in the stock UOM of the product
					final ProductId productId = ProductId.ofRepoId(alloc.getM_ShipmentSchedule().getM_Product_ID());
					final Quantity qtyPicked = Quantity.of(alloc.getQtyPicked(), productBL.getStockUOM(productId));
					boxQtyLeft.merge(new BoxKey(ShipmentScheduleId.ofRepoId(alloc.getM_ShipmentSchedule_ID()), tuId), qtyPicked, Quantity::add);
				}
			}
			return new RestoreState(huContext, unitIds, boxQtyLeft);
		}

		/**
		 * @return true (and reduces what is left) if the box still has {@code qty} left to restore
		 */
		boolean takeFromBoxBudget(@NonNull final BoxKey boxKey, @NonNull final Quantity qty)
		{
			final Quantity left = boxQtyLeft.get(boxKey);
			if (left == null || left.isLessThan(qty))
			{
				return false;
			}
			boxQtyLeft.put(boxKey, left.subtract(qty));
			return true;
		}
	}

	/**
	 * The quantities a restored row gets.
	 */
	@Value
	private static class RestoredQty
	{
		@NonNull BigDecimal qtyPicked;
		@Nullable BigDecimal qtyTU;
		@Nullable BigDecimal qtyLU;
		int catchUomId;
		@Nullable BigDecimal qtyDeliveredCatch;

		/** the whole row: quantity, box and pallet counts and weight as the row has them */
		static RestoredQty ofWholeRow(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
		{
			return new RestoredQty(row.getQtyPicked(), row.getQtyTU(), row.getQtyLU(), row.getCatch_UOM_ID(), row.getQtyDeliveredCatch());
		}

		/** one unit of a box row: its box and pallet counts are not the unit's; its weight is the one of the unit */
		static RestoredQty ofUnitOfBoxRow(@NonNull final StockQtyAndUOMQty unitQtys)
		{
			final Optional<Quantity> catchQty = unitQtys.getUOMQtyOpt();
			return new RestoredQty(
					unitQtys.getStockQty().toBigDecimal(),
					null,
					null,
					catchQty.map(qty -> qty.getUomId().getRepoId()).orElse(0),
					catchQty.map(qty -> qty.toBigDecimal()).orElse(null));
		}

		void applyTo(@NonNull final I_M_ShipmentSchedule_QtyPicked restored)
		{
			restored.setQtyPicked(qtyPicked);
			if (qtyTU != null)
			{
				restored.setQtyTU(qtyTU);
			}
			if (qtyLU != null)
			{
				restored.setQtyLU(qtyLU);
			}
			if (catchUomId > 0)
			{
				restored.setCatch_UOM_ID(catchUomId);
				restored.setQtyDeliveredCatch(qtyDeliveredCatch);
			}
		}
	}

	@Value
	private static class BoxKey
	{
		@NonNull ShipmentScheduleId shipmentScheduleId;
		@NonNull HuId boxId;
	}

	@Value
	private static class RowPart
	{
		@NonNull BoxContentGroup group;
		@NonNull Quantity qty;
		@Nullable I_M_HU wholeVhu;
	}

	/**
	 * The VHU storages of a box that share one attribute combination, with the quantity not yet handed out to rows.
	 * All quantities are in the product's stock UOM, like {@code M_ShipmentSchedule_QtyPicked.QtyPicked}.
	 */
	@VisibleForTesting
	static final class BoxContentGroup
	{
		private final List<IHUProductStorage> storages;
		private Quantity consumed;

		BoxContentGroup(@NonNull final List<IHUProductStorage> storages)
		{
			this.storages = storages;
			this.consumed = storages.get(0).getQtyInStockingUOM().toZero();
		}

		Quantity totalQty()
		{
			return storages.stream().map(IHUProductStorage::getQtyInStockingUOM).reduce(Quantity::add).get();
		}

		Quantity remainingQty()
		{
			return totalQty().subtract(consumed);
		}

		I_C_UOM uom()
		{
			return storages.get(0).getQtyInStockingUOM().getUOM();
		}

		I_M_HU firstVhu()
		{
			return storages.get(0).getM_HU();
		}

		/**
		 * @return the VHU only if this group has exactly one VHU and the next {@code partQty} are all of it, else null
		 */
		@Nullable
		I_M_HU wholeVhuOrNull(@NonNull final Quantity partQty)
		{
			if (storages.size() != 1 || !consumed.isZero())
			{
				return null;
			}
			return storages.get(0).getQtyInStockingUOM().compareTo(partQty) == 0 ? storages.get(0).getM_HU() : null;
		}

		void consume(@NonNull final Quantity qty)
		{
			consumed = consumed.add(qty);
		}
	}

	/**
	 * Splits the record's catch weight across the given groups proportionally to their qtys.
	 * The last group gets the remainder, so the shares always sum up to the record's weight.
	 *
	 * @return one entry per group; all empty if the record has no catch UOM
	 */
	private static List<Optional<Quantity>> splitCatchWeightByQty(
			@NonNull final I_M_ShipmentSchedule_QtyPicked record,
			@NonNull final List<Quantity> groupQtys)
	{
		if (record.getCatch_UOM_ID() <= 0)
		{
			return groupQtys.stream().map(q -> Optional.<Quantity>empty()).collect(ImmutableList.toImmutableList());
		}

		final I_C_UOM catchUom = Services.get(IUOMDAO.class).getById(record.getCatch_UOM_ID());
		final Quantity totalWeight = Quantity.of(record.getQtyDeliveredCatch(), catchUom);
		final BigDecimal totalQty = groupQtys.stream()
				.map(q -> q.toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		final int scale = totalWeight.getUOMPrecision().toInt();

		final ImmutableList.Builder<Optional<Quantity>> shares = ImmutableList.builder();
		Quantity allocated = totalWeight.toZero();
		for (int i = 0; i < groupQtys.size(); i++)
		{
			final Quantity share = i < groupQtys.size() - 1
					? totalWeight.multiply(groupQtys.get(i).toBigDecimal()).divide(totalQty, scale, RoundingMode.HALF_UP)
					: totalWeight.subtract(allocated);
			allocated = allocated.add(share);
			shares.add(Optional.of(share));
		}
		return shares.build();
	}

	@Override
	public List<ShipmentScheduleWithHU> retrieveShipmentSchedulesWithHUsFromHUs(@NonNull final List<I_M_HU> hus)
	{
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();
		final ArrayList<ShipmentScheduleWithHU> result = new ArrayList<>();
		for (final I_M_HU hu : hus)
		{
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("HU " + hu + " shall be top level");
			}
			final List<ShipmentScheduleWithHU> candidatesForHU = new ArrayList<>();
			for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : huShipmentScheduleDAO.retrieveQtyPickedNotDeliveredForTopLevelHU(hu))
			{
				if (!qtyPicked.isActive())
				{
					continue;
				}
				candidatesForHU.addAll(createCandidatesForQtyPicked(qtyPicked, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER));
			}
			result.addAll(candidatesForHU);
			if (candidatesForHU.isEmpty())
			{
				Loggables.addLog("No eligible {} records found for hu {}",
						I_M_ShipmentSchedule_QtyPicked.Table_Name,
						handlingUnitsBL.getDisplayName(hu));
			}
		}
		result.sort(new ShipmentScheduleWithHUComparator());
		return result;
	}

	private static Stream<Map.Entry<AttributesKey, IHUProductStorage>> groupProductStoragesByFingerprint(
			@NonNull final I_M_HU vhu,
			@NonNull final ProductId productId,
			@NonNull final IHUStorageFactory storageFactory,
			@NonNull final IAttributeStorageFactory attrFactory,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		final IHUProductStorage productStorage = storageFactory.getStorage(vhu).getProductStorageOrNull(productId);
		if (productStorage == null || productStorage.isEmpty())
		{
			return Stream.empty();
		}
		final AttributesKey fingerprint = computeUseInASIFingerprint(vhu, attrFactory, attributeSplitsShipmentLine);
		return Stream.of(Maps.immutableEntry(fingerprint, productStorage));
	}

	private static AttributesKey computeUseInASIFingerprint(
			@NonNull final I_M_HU vhu,
			@NonNull final IAttributeStorageFactory factory,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		// IAttributeStorage does not implement getAttributeValueIdOrNull, so we cannot use
		// AttributesKeys.createAttributesKeyFromAttributeSet here — list-type attributes
		// would be silently dropped. Instead, we build the key directly from IAttributeValue,
		// using the value string for all attribute types (consistent for grouping purposes).
		final ImmutableSet<AttributesKeyPart> parts = factory.getAttributeStorage(vhu)
				.getAttributeValues()
				.stream()
				.filter(av -> av.isUseInASI() && !av.isEmpty())
				.filter(av -> attributeSplitsShipmentLine.test(av.getAttributeId()))
				.map(HUShipmentScheduleBL::toAttributesKeyPart)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		return parts.isEmpty() ? AttributesKey.NONE : AttributesKey.ofParts(parts);
	}

	/**
	 * Predicate deciding whether an attribute splits the shipment line, i.e. whether it is whitelisted in
	 * {@code M_ShipmentSchedule_AttributeConfig} for the schedule's handler — the same rule the M_InOutLine
	 * aggregation applies. Resolved via {@code getHandlerForOrNull}: handlers are registered once at server
	 * startup ({@code InOutCandidateValidator#onInit}), so during real picking the handler is present and the
	 * whitelist restricts the split. It is null only in narrow init/test contexts that bypass that
	 * registration; there we fall back to "no restriction" rather than fail.
	 */
	private Predicate<AttributeId> attributeSplitsShipmentLinePredicate(@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPicked)
	{
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule = qtyPicked.getM_ShipmentSchedule();
		final ShipmentScheduleHandler handler = shipmentScheduleHandlerBL.getHandlerForOrNull(shipmentSchedule);
		if (handler == null)
		{
			return attributeId -> true;
		}
		return attributeId -> handler.attributeShallBePartOfShipmentLine(shipmentSchedule, attributeDAO.getAttributeRecordById(attributeId));
	}

	@Nullable
	private static AttributesKeyPart toAttributesKeyPart(@NonNull final IAttributeValue av)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(av.getM_Attribute().getM_Attribute_ID());
		if (AttributeValueType.ofCode(av.getAttributeValueType()).isNumber())
		{
			// Use BigDecimal-specific part so scale-differences like 3.0 vs 3.00 are normalized
			final BigDecimal value = av.getValueAsBigDecimal();
			return value != null && value.signum() != 0
					? AttributesKeyPart.ofNumberAttribute(attributeId, value)
					: null;
		}
		// String, List and Date attributes all carry their value via getValueAsString()
		final String valueStr = av.getValueAsString();
		return Check.isNotBlank(valueStr)
				? AttributesKeyPart.ofStringAttribute(attributeId, valueStr)
				: null;
	}
}
