package de.metas.handlingunits.reservation;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class HUReservationService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final HUReservationRepository huReservationRepository;

	@VisibleForTesting
	@Setter(AccessLevel.PACKAGE)
	@NonNull
	private Supplier<HUTransformService> huTransformServiceSupplier = HUTransformService::newInstance;

	private static final String SYSCONFIG_AllowSqlWhenFilteringHUAttributes = "de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.AllowSqlWhenFilteringHUAttributes";
	private final static AdMessageKey NO_QTY_RESERVED_ERROR_MSG = AdMessageKey.of("de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.NO_QTY_RESERVED_ERROR_MSG");
	private final static AdMessageKey RESERVED_ERROR_MSG = AdMessageKey.of("de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.RESERVED_ERROR_MSG");

	private final ImmutableSet<DocStatus> docStatusesThatAllowReservation = ImmutableSet.of(
			DocStatus.Drafted,
			DocStatus.InProgress,
			DocStatus.WaitingPayment,
			DocStatus.Completed);

	public HUReservationService(@NonNull final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;
	}

	public void warmup(@NonNull final Collection<HuId> huIds)
	{
		huReservationRepository.warmupByVHUIds(huIds);
	}

	/**
	 * Creates an HU reservation record and creates dedicated reserved VHUs with HU status "reserved".
	 */
	public Optional<HUReservation> makeReservation(@NonNull final ReserveHUsRequest reservationRequest)
	{
		return trxManager.callInThreadInheritedTrx(() -> makeReservationInTrx(reservationRequest));
	}

	private Optional<HUReservation> makeReservationInTrx(@NonNull final ReserveHUsRequest reservationRequest)
	{
		final List<I_M_HU> husToReserve;
		if (reservationRequest.isReserveActualHUs())
		{
			husToReserve = getHUsToReserve(reservationRequest);
		}
		else
		{
			//
			// Separate the VHUs that we are going to reserve
			husToReserve = huTransformServiceSupplier.get().husToNewCUs(
							HUsToNewCUsRequest.builder()
									.sourceHUs(handlingUnitsBL.getByIds(reservationRequest.getHuIds()))
									.qtyCU(reservationRequest.getQtyToReserve())
									.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
									.keepNewCUsUnderSameParent(true)
									.productId(reservationRequest.getProductId())
									.build())
					.getNewCUs();
		}

		if (husToReserve.isEmpty())
		{
			return Optional.empty();
		}

		//
		// Prepare the reservation records
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final I_C_UOM uomRecord = reservationRequest.getQtyToReserve().getUOM();
		final ArrayList<HUReservationEntryUpdateRepoRequest> repoRequests = new ArrayList<>();
		for (final I_M_HU newCU : husToReserve)
		{
			final Quantity qty = storageFactory
					.getStorage(newCU)
					.getQuantity(reservationRequest.getProductId(), uomRecord);

			repoRequests.add(HUReservationEntryUpdateRepoRequest.builder()
					.documentRef(reservationRequest.getDocumentRef())
					.customerId(reservationRequest.getCustomerId())
					.vhuId(HuId.ofRepoId(newCU.getM_HU_ID()))
					.qtyReserved(qty)
					.build());
		}

		//
		// Make sure the whole requested quantity was reserved
		final Quantity qtyReserved = repoRequests.stream()
				.map(HUReservationEntryUpdateRepoRequest::getQtyReserved)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException(NO_QTY_RESERVED_ERROR_MSG)
						.appendParametersToMessage()
						.setParameter("request", reservationRequest));
		if (!reservationRequest.isReserveActualHUs() && reservationRequest.getQtyToReserve().compareTo(qtyReserved) != 0)
		{
			throw new AdempiereException(RESERVED_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("reservationRequest", reservationRequest)
					.setParameter("reservations", repoRequests);
		}

		//
		// set M_HU.IsReserved=Y
		// note: M_HU.IsReserved is also updated via model interceptor if M_HU_Reservation changes, but for clarify and unit test purposes, we explicitly do it here as well
		for (final I_M_HU hu : husToReserve)
		{
			final boolean reserved = handlingUnitsBL.setReservedRecursively(hu, true);
			if (!reserved)
			{
				// Could not reserve this HU (already reserved or child is reserved)
				return Optional.empty();
			}
		}

		//
		// Create the reservation records and return
		final ImmutableList<HUReservationEntry> entries = huReservationRepository.createOrUpdateEntries(repoRequests);
		return Optional.of(HUReservation.ofEntries(entries));
	}

	private ImmutableList<I_M_HU> getHUsToReserve(final @NonNull ReserveHUsRequest reservationRequest)
	{
		// Step 1: Load all HUs from the request
		final List<I_M_HU> allHUs = handlingUnitsDAO.getByIds(reservationRequest.getHuIds());

		// Step 2: Build the complete Set of input HU IDs FIRST (order-independent)
		final Set<HuId> allHuIds = allHUs.stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());

		// Step 3: Filter out HUs whose ancestors are in the Set
		// Step 4: Filter out already reserved HUs
		// Step 5: Filter out HUs whose ancestors are already reserved (even if ancestor not in input list)
		// Step 6: Filter out HUs that have any reserved descendants
		final ImmutableList<I_M_HU> toReserveHUs = allHUs.stream()
				.filter(hu -> !hasAncestorInList(hu, allHuIds))
				.filter(hu -> !hu.isReserved())
				.filter(hu -> !hasReservedAncestor(hu))
				.filter(hu -> !hasReservedDescendant(hu))
				.collect(ImmutableList.toImmutableList());

		// Step 7: After filtering, delete any existing child reservations for HUs we're about to reserve
		deleteChildReservations(toReserveHUs);

		return toReserveHUs;
	}

	/**
	 * Deletes existing reservations for all descendants (children, grandchildren, etc.) of the given HUs.
	 * This ensures that when we reserve a parent HU, any existing child reservations are removed
	 * to prevent double-reservation.
	 *
	 * @param hus The HUs that are being reserved (we'll delete reservations for their children)
	 */
	private void deleteChildReservations(final @NonNull List<I_M_HU> hus)
	{
		final Set<HuId> childHuIds = hus.stream()
				.flatMap(hu -> getDescendantHuIds(hu).stream())
				.collect(ImmutableSet.toImmutableSet());

		if (!childHuIds.isEmpty())
		{
			deleteReservationsByVHUIdsInTrx(childHuIds);
		}
	}

	/**
	 * Recursively collects all descendant HU IDs (children, grandchildren, etc.) for the given HU.
	 *
	 * @param hu The parent HU
	 * @return Set of all descendant HU IDs
	 */
	private Set<HuId> getDescendantHuIds(final @NonNull I_M_HU hu)
	{
		final List<I_M_HU> children = handlingUnitsDAO.retrieveIncludedHUs(hu);
		if (children.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableSet.Builder<HuId> descendantIds = ImmutableSet.builder();
		for (final I_M_HU child : children)
		{
			descendantIds.add(HuId.ofRepoId(child.getM_HU_ID()));
			// Recursively add grandchildren, great-grandchildren, etc.
			descendantIds.addAll(getDescendantHuIds(child));
		}

		return descendantIds.build();
	}

	/**
	 * Checks if the given HU has any ancestor (parent, grandparent, etc.) in the provided Set.
	 * This method walks up the hierarchy until it finds an ancestor in the Set or reaches the root.
	 * <p>
	 * This ensures order-independent filtering: whether the input is [parent, child] or [child, parent],
	 * the result will be the same - only the parent will be kept.
	 *
	 * @param hu The HU to check
	 * @param huIds The Set of HU IDs from the original input (for O(1) lookup)
	 * @return true if any ancestor is found in the Set, false otherwise
	 */
	@VisibleForTesting
	boolean hasAncestorInList(final @NonNull I_M_HU hu, final @NonNull Set<HuId> huIds)
	{
		I_M_HU parent = handlingUnitsDAO.retrieveParent(hu);

		while (parent != null)
		{
			final HuId parentId = HuId.ofRepoId(parent.getM_HU_ID());
			if (huIds.contains(parentId))
			{
				return true; // Found an ancestor in the input list
			}
			parent = handlingUnitsDAO.retrieveParent(parent);
		}

		return false; // No ancestor found in the input list
	}

	/**
	 * Checks if the given HU has any ancestor that is already reserved.
	 * This prevents creating a reservation for a child HU when its parent is already reserved.
	 * <p>
	 * This is a critical business rule: if a parent HU is reserved, we should not allow
	 * reserving its children, as the parent reservation already includes all child inventory.
	 *
	 * @param hu The HU to check
	 * @return true if any ancestor is already reserved, false otherwise
	 */
	@VisibleForTesting
	boolean hasReservedAncestor(final @NonNull I_M_HU hu)
	{
		I_M_HU parent = handlingUnitsDAO.retrieveParent(hu);

		while (parent != null)
		{
			if (parent.isReserved())
			{
				return true; // Found a reserved ancestor
			}
			parent = handlingUnitsDAO.retrieveParent(parent);
		}

		return false; // No reserved ancestor found
	}

	/**
	 * Checks if the given HU has any descendant (child, grandchild, etc.) that is already reserved.
	 * This prevents creating a reservation for a parent HU when any of its children are already reserved.
	 * <p>
	 * This is a critical business rule: if a child HU is reserved, we should not allow
	 * reserving its parent, as that would create a conflict with the existing child reservation.
	 *
	 * @param hu The HU to check
	 * @return true if any descendant is already reserved, false otherwise
	 */
	@VisibleForTesting
	boolean hasReservedDescendant(final @NonNull I_M_HU hu)
	{
		final List<I_M_HU> children = handlingUnitsDAO.retrieveIncludedHUs(hu);

		for (final I_M_HU child : children)
		{
			if (child.isReserved())
			{
				return true; // Found a reserved child
			}
			// Recursively check grandchildren
			if (hasReservedDescendant(child))
			{
				return true; // Found a reserved grandchild
			}
		}

		return false; // No reserved descendants found
	}

	/**
	 * Deletes the respective reservation records. The method is lenient towards not-VHU and also towards not-reserved HUs.
	 * <p>
	 * VHUs that were split off according to the reservation remain that way for the time being, but their {@code IsReserved} flag is changed to 'N'.
	 */
	public void deleteReservationsByVHUIds(@NonNull final Set<HuId> vhuIds)
	{
		if (vhuIds.isEmpty()) {return;}
		trxManager.runInNewTrx(() -> deleteReservationsByVHUIdsInTrx(vhuIds));
	}

	private void deleteReservationsByVHUIdsInTrx(@NonNull final Set<HuId> vhuIds)
	{
		huReservationRepository.deleteReservationsByVhuIds(vhuIds);
		handlingUnitsBL.setReservedByHUIds(vhuIds, false);
	}

	public void deleteReservationsByDocumentRefs(@NonNull final Set<HUReservationDocRef> documentRefs)
	{
		if (documentRefs.isEmpty()) {return;}
		trxManager.runInNewTrx(() -> deleteReservationsByDocumentRefsInTrx(documentRefs));
	}

	private void deleteReservationsByDocumentRefsInTrx(@NonNull final Set<HUReservationDocRef> documentRefs)
	{
		final Set<HuId> releasedVHUIds = huReservationRepository.deleteReservationsByDocumentRefs(documentRefs);
		handlingUnitsDAO.setReservedByHUIds(releasedVHUIds, false);
	}

	public boolean isReservationAllowedForDocStatus(@NonNull final DocStatus docStatus)
	{
		return docStatusesThatAllowReservation.contains(docStatus);
	}

	public boolean isVhuIdReservedToSalesOrderLineId(@NonNull final HuId vhuId, @NonNull final OrderLineId salesOrderLineId)
	{
		final OrderLineId reservedForSalesOrderLineId = getOrderLineIdByReservedVhuId(vhuId).orElse(null);
		return Objects.equals(salesOrderLineId, reservedForSalesOrderLineId);
	}

	public Optional<OrderLineId> getOrderLineIdByReservedVhuId(@NonNull final HuId vhuId)
	{
		return huReservationRepository.getOrderLineIdByReservedVhuId(vhuId);
	}

	public Quantity retrieveReservableQty(@NonNull final RetrieveHUsQtyRequest request)
	{
		return retrieveQuantity(request, this::isHuReservable);
	}

	/**
	 * Retrieves the total quantity that is actually reserved for the given HU IDs.
	 * This method looks up actual reservation entries from the database rather than just checking the reserved flag.
	 *
	 * @param request Contains the HU IDs and product ID to check
	 * @return The sum of all reserved quantities for the given HU IDs
	 */
	public Quantity retrieveActualReservedQty(@NonNull final RetrieveHUsQtyRequest request)
	{
		final ImmutableList<HUReservationEntry> entries = huReservationRepository.getEntriesByVHUIds(request.getHuIds());

		if (entries.isEmpty())
		{
			final I_C_UOM stockingUomRecord = productBL.getStockUOM(request.getProductId());
			return Quantity.zero(stockingUomRecord);
		}

		return entries.stream()
				.map(HUReservationEntry::getQtyReserved)
				.reduce(Quantity::add)
				.orElseGet(() -> {
					final I_C_UOM stockingUomRecord = productBL.getStockUOM(request.getProductId());
					return Quantity.zero(stockingUomRecord);
				});
	}

	private Quantity retrieveQuantity(
			@NonNull final RetrieveHUsQtyRequest request,
			@NonNull final Predicate<I_M_HU> reservablePredicate)
	{
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(request.getHuIds());
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

		final Mutable<Quantity> result = new Mutable<>();
		final HashSet<HuId> alreadySeenHuIds = new HashSet<>();
		new HUIterator()
				.setDate(SystemTime.asZonedDateTime())
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result beforeHU(@NonNull final IMutable<I_M_HU> hu)
					{
						final I_M_HU huRecord = Objects.requireNonNull(hu.getValue());
						if (!alreadySeenHuIds.add(HuId.ofRepoId(huRecord.getM_HU_ID())))
						{
							return Result.SKIP_DOWNSTREAM; // if we already saw the current HU, then we also already saw its children
						}
						if (reservablePredicate.test(huRecord)) // only count "elementary" storages
						{
							final List<IHUProductStorage> huProductStorages = storageFactory.getHUProductStorages(ImmutableList.of(huRecord), request.getProductId());

							Quantity vhuResult = null;

							// this can be done with stream, but old-school seems easier to understand&debug to me
							for (final IHUProductStorage huProductStorage : huProductStorages)
							{
								vhuResult = vhuResult == null ? huProductStorage.getQty() : vhuResult.add(huProductStorage.getQty());
							}
							if (vhuResult != null && !vhuResult.isZero())
							{
								result.setValue(result.getValue() == null ? vhuResult : result.getValue().add(vhuResult));
							}
						}
						return Result.CONTINUE;
					}
				})
				.iterate(hus);

		if (result.getValue() == null)
		{
			final I_C_UOM stockingUomRecord = productBL.getStockUOM(request.getProductId());
			return Quantity.zero(stockingUomRecord);
		}

		return result.getValue();
	}

	private boolean isHuReservable(@NonNull final I_M_HU huRecord)
	{
		if (!handlingUnitsBL.isVirtual(huRecord))
		{
			return false;
		}
		return huStatusBL.isStatusActive(huRecord) && !huRecord.isReserved();
	}

	public Optional<Quantity> retrieveReservedQty(@NonNull final OrderLineId orderLineId)
	{
		return getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId))
				.map(HUReservation::getReservedQtySum);
	}

	@NonNull
	public Optional<HUReservation> getByDocumentRef(
			@NonNull final HUReservationDocRef documentRef,
			@Nullable final ImmutableSet<HuId> onlyHuIds)
	{
		if (onlyHuIds == null)
		{
			return getByDocumentRef(documentRef);
		}
		return huReservationRepository.getByDocumentRef(documentRef, onlyHuIds);
	}

	@NonNull
	public Optional<HUReservation> getByDocumentRef(@NonNull final HUReservationDocRef documentRef)
	{
		return huReservationRepository.getByDocumentRef(documentRef, ImmutableSet.of());
	}

	public ImmutableSet<HuId> getVHUIdsByDocumentRef(@NonNull final HUReservationDocRef documentRef)
	{
		return getByDocumentRef(documentRef).map(HUReservation::getVhuIds).orElseGet(ImmutableSet::of);
	}

	/**
	 * Collect all VHU IDs reserved by any of the given document references.
	 * Used by on-the-fly picking to whitelist "my" reserved VHUs while skipping those reserved for others.
	 */
	@NonNull
	public ImmutableSet<HuId> getVHUIdsReservedByAnyOf(@NonNull final HUReservationDocRef... documentRefs)
	{
		final ImmutableSet.Builder<HuId> result = ImmutableSet.builder();
		for (final HUReservationDocRef ref : documentRefs)
		{
			result.addAll(getVHUIdsByDocumentRef(ref));
		}
		return result.build();
	}

	public ImmutableList<HUReservationEntry> getEntriesByVHUIds(@NonNull final Collection<HuId> vhuIds)
	{
		return huReservationRepository.getEntriesByVHUIds(vhuIds);
	}

	public boolean isAnyOfVHUIdsReserved(@NonNull final Collection<HuId> vhuIds)
	{
		return !huReservationRepository.getEntriesByVHUIds(vhuIds).isEmpty();
	}

	@Builder(builderMethodName = "prepareHUQuery", builderClassName = "AvailableHUQueryBuilder")
	private IHUQueryBuilder createHUQuery(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final AttributeSetInstanceId asiId,
			@Nullable final HUReservationDocRef reservedToDocumentOrNotReservedAtAll)
	{
		final Set<WarehouseId> pickingWarehouseIds = warehousesRepo.getWarehouseIdsOfSamePickingGroup(warehouseId);

		final IHUQueryBuilder huQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyInWarehouseIds(pickingWarehouseIds)
				.addOnlyWithProductId(productId)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active);

		// ASI
		if (asiId != null)
		{
			final ImmutableAttributeSet attributeSet = asiBL.getImmutableAttributeSetById(asiId);

			huQuery.addOnlyWithAttributeValuesMatchingPartnerAndProduct(bpartnerId, productId, attributeSet);
			huQuery.allowSqlWhenFilteringAttributes(isAllowSqlWhenFilteringHUAttributes());
		}

		// Reservation
		if (reservedToDocumentOrNotReservedAtAll == null)
		{
			huQuery.setExcludeReserved();
		}
		else
		{
			huQuery.setExcludeReservedToOtherThan(reservedToDocumentOrNotReservedAtAll);
		}

		return huQuery;
	}

	// FIXME: move it to AttributeDAO
	@Deprecated
	public boolean isAllowSqlWhenFilteringHUAttributes()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_AllowSqlWhenFilteringHUAttributes, true);
	}

	public void transferReservation(
			@NonNull final HUReservationDocRef from,
			@NonNull final HUReservationDocRef to,
			@NonNull final Set<HuId> vhuIds)
	{
		huReservationRepository.transferReservation(ImmutableSet.of(from), to, vhuIds);
	}

	public void transferReservation(
			@NonNull final Collection<HUReservationDocRef> from,
			@NonNull final HUReservationDocRef to)
	{
		final Set<HuId> vhuIds = ImmutableSet.of();
		huReservationRepository.transferReservation(from, to, vhuIds);
	}

}
