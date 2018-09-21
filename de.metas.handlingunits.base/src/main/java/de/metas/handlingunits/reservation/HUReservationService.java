package de.metas.handlingunits.reservation;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.CCache;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocument;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.reservation.HUReservation.HUReservationBuilder;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Setter;

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
	/** In unit test mode, we need to use {@link HUTransformService#newInstance(de.metas.handlingunits.IMutableHUContext)} to get a new instance. */
	@Setter
	private Supplier<HUTransformService> huTransformServiceSupplier = () -> HUTransformService.newInstance();

	private final HUReservationRepository huReservationRepository;

	public HUReservationService(@NonNull final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;
	}

	/**
	 * Creates an HU reservation record and creates dedicated reserved VHUs with HU status "reserved".
	 */
	public HUReservation makeReservation(@NonNull final HUReservationRequest reservationRequest)
	{
		final List<HuId> huIds = Check.assumeNotEmpty(reservationRequest.getHuIds(),
				"the given request needs to have huIds; request={}", reservationRequest);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final List<I_M_HU> hus = handlingUnitsDAO.retrieveByIds(huIds);

		final HUsToNewCUsRequest husToNewCUsRequest = HUsToNewCUsRequest.builder()
				.sourceHUs(hus)
				.qtyCU(reservationRequest.getQtyToReserve())
				.onlyFromUnreservedHUs(true)
				.keepNewCUsUnderSameParent(true)
				.productId(reservationRequest.getProductId())
				// TODO: also add attributes
				.build();

		final List<I_M_HU> newCUs = huTransformServiceSupplier
				.get()
				.husToNewCUs(husToNewCUsRequest);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final I_M_Product productRecord = loadOutOfTrx(reservationRequest.getProductId(), I_M_Product.class);
		final I_C_UOM uomRecord = reservationRequest.getQtyToReserve().getUOM();

		final HUReservationBuilder huReservationBuilder = HUReservation
				.builder()
				.salesOrderLineId(reservationRequest.getSalesOrderLineId());

		Quantity reservedQtySum = Quantity.zero(uomRecord);
		for (final I_M_HU newCU : newCUs)
		{
			final Quantity qty = storageFactory
					.getStorage(newCU)
					.getQuantity(productRecord, uomRecord);

			reservedQtySum = reservedQtySum.add(qty);
			huReservationBuilder.vhuId2reservedQty(HuId.ofRepoId(newCU.getM_HU_ID()), qty);

			// note: M_HU.IsReserved is also updated via model interceptor if M_HU_Reservation changes, but for clarify and unit test purposes, we explicitly do it here as well
			newCU.setIsReserved(true);
			handlingUnitsDAO.saveHU(newCU);
		}

		final HUReservation huReservation = huReservationBuilder
				.reservedQtySum(Optional.of(reservedQtySum))
				.build();

		huReservationRepository.save(huReservation);

		return huReservation;
	}

	/**
	 * Deletes the respective reservation records. The method is lenient towards not-VHU and also towards not-reserved HUs.
	 * <p>
	 * VHUs that were split off according to the reservation remain that way for the time being, but their {@code IsReserved} flag is changed to 'N'.
	 */
	public void deleteReservations(@NonNull final Collection<HuId> vhuIds)
	{
		Services.get(ITrxManager.class).run(() -> deleteReservation0(vhuIds));
	}

	private void deleteReservation0(@NonNull final Collection<HuId> vhuIds)
	{
		for (final HuId vhuId : vhuIds)
		{
			// note: M_HU.IsReserved is also updated via model interceptor if M_HU_Reservation changes, but for clarify and unit test purposes, we explicitly do it here as well
			final I_M_HU vhu = load(vhuId, I_M_HU.class);
			vhu.setIsReserved(false);
			Services.get(IHandlingUnitsDAO.class).saveHU(vhu);
		}

		Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, vhuIds)
				.create()
				.delete();
	}

	public ImmutableSet<String> getDocstatusesThatAllowReservation()
	{
		return ImmutableSet.of(
				IDocument.STATUS_Drafted,
				IDocument.STATUS_InProgress,
				IDocument.STATUS_WaitingPayment,
				IDocument.STATUS_Completed);
	}

	private static final CCache<HuId, Optional<OrderLineId>> HU_ID_2_ORDERLINE_ID = CCache.newLRUCache(
			I_M_HU_Reservation.Table_Name + "#by#" + I_M_HU_Reservation.COLUMNNAME_VHU_ID, 500, 5);

	public Optional<OrderLineId> getReservedForOrderLineId(@NonNull final HuId huId)
	{
		return HU_ID_2_ORDERLINE_ID.getOrLoad(huId, this::getReservedForOrderLineId0);
	}

	public Optional<OrderLineId> getReservedForOrderLineId0(@NonNull final HuId huId)
	{
		final I_M_HU_Reservation huReservationRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Reservation.COLUMN_VHU_ID, huId) // we have a UC constraint on VHU_ID
				.create()
				.firstOnly(I_M_HU_Reservation.class);
		if (huReservationRecord == null)
		{
			return Optional.empty();
		}
		return Optional.of(OrderLineId.ofRepoId(huReservationRecord.getC_OrderLineSO_ID()));
	}

	public void warmup(@NonNull final Collection<HuId> huIds)
	{
		HU_ID_2_ORDERLINE_ID.getAllOrLoad(huIds, this::getReservedForOrderLineId0);
	}

	public Map<HuId, Optional<OrderLineId>> getReservedForOrderLineId0(@NonNull final Collection<HuId> huIds)
	{
		final HashMap<HuId, Optional<OrderLineId>> map = new HashMap<>();
		for (final HuId huId : huIds)
		{
			map.put(huId, Optional.empty());
		}

		final List<I_M_HU_Reservation> huReservationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Reservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_Reservation.COLUMN_VHU_ID, huIds)
				.create()
				.list();
		for (final I_M_HU_Reservation huReservationRecord : huReservationRecords)
		{
			final HuId huId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
			final OrderLineId orderLineId = OrderLineId.ofRepoId(huReservationRecord.getC_OrderLineSO_ID());
			map.put(huId, Optional.of(orderLineId));
		}
		return ImmutableMap.copyOf(map);
	}
}
