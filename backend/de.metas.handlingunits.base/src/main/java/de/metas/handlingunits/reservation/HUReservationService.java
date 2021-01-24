package de.metas.handlingunits.reservation;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final HUReservationRepository huReservationRepository;

	@VisibleForTesting
	@Setter
	@NonNull
	private Supplier<HUTransformService> huTransformServiceSupplier = HUTransformService::newInstance;

	private static final String SYSCONFIG_AllowSqlWhenFilteringHUAttributes = "de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.AllowSqlWhenFilteringHUAttributes";

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
		huReservationRepository.warmup(huIds);
	}

	/**
	 * Creates an HU reservation record and creates dedicated reserved VHUs with HU status "reserved".
	 */
	public Optional<HUReservation> makeReservation(@NonNull final ReserveHUsRequest reservationRequest)
	{
		final Set<HuId> huIds = Check.assumeNotEmpty(reservationRequest.getHuIds(),
				"the given request needs to have huIds; request={}", reservationRequest);

		final List<I_M_HU> hus = handlingUnitsDAO.retrieveByIds(huIds);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHUs(hus)
				.qtyCU(reservationRequest.getQtyToReserve())
				.onlyFromUnreservedHUs(true)
				.keepNewCUsUnderSameParent(true)
				.productId(reservationRequest.getProductId())
				.build();

		final List<I_M_HU> newCUs = huTransformServiceSupplier
				.get()
				.husToNewCUs(husToNewCUsRequest);
		if (newCUs.isEmpty())
		{
			return Optional.empty();
		}

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final I_C_UOM uomRecord = reservationRequest.getQtyToReserve().getUOM();

		final Map<HuId, Quantity> reservedQtyByVhuId = new HashMap<>();
		for (final I_M_HU newCU : newCUs)
		{
			final Quantity qty = storageFactory
					.getStorage(newCU)
					.getQuantity(reservationRequest.getProductId(), uomRecord);

			reservedQtyByVhuId.put(HuId.ofRepoId(newCU.getM_HU_ID()), qty);

			// note: M_HU.IsReserved is also updated via model interceptor if M_HU_Reservation changes, but for clarify and unit test purposes, we explicitly do it here as well
			newCU.setIsReserved(true);
			handlingUnitsDAO.saveHU(newCU);
		}

		final HUReservation huReservation = HUReservation.builder()
				.documentRef(reservationRequest.getDocumentRef())
				.customerId(reservationRequest.getCustomerId())
				.reservedQtyByVhuIds(reservedQtyByVhuId)
				.build();

		huReservationRepository.save(huReservation);

		return Optional.of(huReservation);
	}

	/**
	 * Deletes the respective reservation records. The method is lenient towards not-VHU and also towards not-reserved HUs.
	 * <p>
	 * VHUs that were split off according to the reservation remain that way for the time being, but their {@code IsReserved} flag is changed to 'N'.
	 */
	public void deleteReservations(@NonNull final Collection<HuId> vhuIds)
	{
		if (vhuIds.isEmpty())
		{
			return;
		}

		trxManager.runInNewTrx(() -> deleteReservationInTrx(vhuIds));
	}

	private void deleteReservationInTrx(@NonNull final Collection<HuId> vhuIds)
	{
		handlingUnitsDAO.setReservedByHUIds(vhuIds, false);

		huReservationRepository.deleteReservationsByVhuIds(vhuIds);
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

	public Quantity retrieveUnreservableQty(@NonNull final RetrieveHUsQtyRequest request)
	{
		return retrieveQuantity(request, this::isHuUnreservable);
	}

	private Quantity retrieveQuantity(
			@NonNull final RetrieveHUsQtyRequest request,
			@NonNull final Predicate<I_M_HU> reservablePredicate)
	{
		final List<I_M_HU> hus = handlingUnitsDAO.retrieveByIds(request.getHuIds());
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
						final I_M_HU huRecord = hu.getValue();
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
							result.setValue(result.getValue() == null ? vhuResult : result.getValue().add(vhuResult));
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

	private boolean isHuUnreservable(@NonNull final I_M_HU huRecord)
	{
		if (!handlingUnitsBL.isVirtual(huRecord))
		{
			return false;
		}
		return huStatusBL.isStatusActive(huRecord) && huRecord.isReserved();
	}

	public Optional<Quantity> retrieveReservedQty(@NonNull final OrderLineId orderLineId)
	{
		return getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId))
				.map(HUReservation::getReservedQtySum);
	}

	public Optional<HUReservation> getBySalesOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId));
	}

	public Optional<HUReservation> getByDocumentRef(@NonNull final HUReservationDocRef documentRef)
	{
		return huReservationRepository.getByDocumentRef(documentRef);
	}

	@Builder(builderMethodName = "prepareHUQuery", builderClassName = "ReservationHUQueryBuilder")
	private IHUQueryBuilder createHUQuery(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@Nullable final OrderLineId reservedToSalesOrderLineIdOrNotReservedAtAll)
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
			final ImmutableAttributeSet attributeSet = attributesRepo.getImmutableAttributeSetById(asiId);
			// TODO: shall we consider only storage relevant attributes?
			huQuery.addOnlyWithAttributes(attributeSet);
			huQuery.allowSqlWhenFilteringAttributes(isAllowSqlWhenFilteringHUAttributes());
		}

		// Reservation
		if (reservedToSalesOrderLineIdOrNotReservedAtAll == null)
		{
			huQuery.setExcludeReserved();
		}
		else
		{
			huQuery.setExcludeReservedToOtherThan(reservedToSalesOrderLineIdOrNotReservedAtAll);
		}

		return huQuery;
	}

	private boolean isAllowSqlWhenFilteringHUAttributes()
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
