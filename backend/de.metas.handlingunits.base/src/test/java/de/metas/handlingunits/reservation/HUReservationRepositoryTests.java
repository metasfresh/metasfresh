package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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

public class HUReservationRepositoryTests
{
	private I_C_UOM uomRecord;
	private HUReservationRepository huReservationRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		huReservationRepository = new HUReservationRepository();
	}

	@Test
	public void getBySalesOrderLineId()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(20);

		final I_M_HU vhu1 = createHuReservationRecord(orderLineId, "10");
		final I_M_HU vhu2 = createHuReservationRecord(orderLineId, "1");

		// invoke the method under test
		//noinspection OptionalGetWithoutIsPresent
		final HUReservation huReservation = huReservationRepository
				.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId), ImmutableSet.of())
				.get();

		assertThat(huReservation.getDocumentRef().getSalesOrderLineId()).isEqualTo(orderLineId);

		final Quantity reservedQtySum = huReservation.getReservedQtySum();
		assertThat(reservedQtySum.toBigDecimal()).isEqualByComparingTo("11");
		assertThat(reservedQtySum.getUomId().getRepoId()).isEqualTo(uomRecord.getC_UOM_ID());

		final HuId expectedVhu1Id = HuId.ofRepoId(vhu1.getM_HU_ID());
		assertThat(huReservation.getVhuIds()).contains(expectedVhu1Id);
		assertThat(huReservation.getReservedQtyByVhuId(expectedVhu1Id).toBigDecimal()).isEqualByComparingTo("10");

		final HuId expectedVhu2Id = HuId.ofRepoId(vhu2.getM_HU_ID());
		assertThat(huReservation.getVhuIds()).contains(expectedVhu2Id);
		assertThat(huReservation.getReservedQtyByVhuId(expectedVhu2Id).toBigDecimal()).isEqualByComparingTo("1");
	}

	@Test
	public void getBySalesOrderLineId_no_records()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(20);

		// invoke the method under test
		final Optional<HUReservation> huReservation = huReservationRepository
				.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId), ImmutableSet.of());
		assertThat(huReservation).isNotPresent();
	}

	private I_M_HU createHuReservationRecord(final OrderLineId orderLineId, final String qtyReserved)
	{
		final I_M_HU vhu1 = newInstance(I_M_HU.class);
		saveRecord(vhu1);

		final I_M_HU_Reservation huReservationRecord = newInstance(I_M_HU_Reservation.class);
		huReservationRecord.setC_OrderLineSO_ID(orderLineId.getRepoId());
		huReservationRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		huReservationRecord.setVHU_ID(vhu1.getM_HU_ID());
		huReservationRecord.setQtyReserved(new BigDecimal(qtyReserved));
		saveRecord(huReservationRecord);
		return vhu1;
	}

	@Test
	public void createOrUpdateEntries_then_load()
	{
		final HUReservationDocRef documentRef = HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20));

		final HUReservationEntryUpdateRepoRequest.HUReservationEntryUpdateRepoRequestBuilder entryBuilder = HUReservationEntryUpdateRepoRequest.builder()
				.documentRef(documentRef)
				.customerId(BPartnerId.ofRepoId(123));

		final HUReservationEntry.HUReservationEntryBuilder expectedEntryBuilder = HUReservationEntry.builder()
				.documentRef(documentRef)
				.customerId(BPartnerId.ofRepoId(123));

		//
		// Create new entries and check
		{
			huReservationRepository.createOrUpdateEntries(ImmutableList.of(
					entryBuilder.vhuId(HuId.ofRepoId(10)).qtyReserved(Quantity.of("10", uomRecord)).build(),
					entryBuilder.vhuId(HuId.ofRepoId(11)).qtyReserved(Quantity.of("20", uomRecord)).build()
			));

			assertThat(huReservationRepository.getByDocumentRef(documentRef, ImmutableSet.of()).orElse(null))
					.usingRecursiveComparison()
					.isEqualTo(HUReservation.ofEntries(ImmutableList.of(
							expectedEntryBuilder.vhuId(HuId.ofRepoId(10)).qtyReserved(Quantity.of("10", uomRecord)).build(),
							expectedEntryBuilder.vhuId(HuId.ofRepoId(11)).qtyReserved(Quantity.of("20", uomRecord)).build())));
		}

		//
		// Update previous entries and check
		{
			huReservationRepository.createOrUpdateEntries(ImmutableList.of(
					entryBuilder.vhuId(HuId.ofRepoId(10)).qtyReserved(Quantity.of("11", uomRecord)).build(),
					entryBuilder.vhuId(HuId.ofRepoId(12)).qtyReserved(Quantity.of("31", uomRecord)).build()));

			assertThat(huReservationRepository.getByDocumentRef(documentRef, ImmutableSet.of()).orElse(null))
					.usingRecursiveComparison()
					.isEqualTo(HUReservation.ofEntries(ImmutableList.of(
							expectedEntryBuilder.vhuId(HuId.ofRepoId(10)).qtyReserved(Quantity.of("11", uomRecord)).build(),
							expectedEntryBuilder.vhuId(HuId.ofRepoId(11)).qtyReserved(Quantity.of("20", uomRecord)).build(),
							expectedEntryBuilder.vhuId(HuId.ofRepoId(12)).qtyReserved(Quantity.of("31", uomRecord)).build())));
		}
	}
}
