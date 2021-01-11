package de.metas.handlingunits.reservation;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
	private static final BigDecimal ELEVEN = TEN.add(ONE);

	private I_C_UOM uomRecord;
	private HUReservationRepository huReservationRepository;

	@Before
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

		final I_M_HU vhu1 = createHuReservationRecord(orderLineId, TEN);
		final I_M_HU vhu2 = createHuReservationRecord(orderLineId, ONE);

		// invoke the method under test
		final HUReservation huReservation = huReservationRepository
				.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId))
				.get();

		assertThat(huReservation.getDocumentRef().getSalesOrderLineId()).isEqualTo(orderLineId);

		final Quantity reservedQtySum = huReservation.getReservedQtySum();
		assertThat(reservedQtySum.toBigDecimal()).isEqualByComparingTo(ELEVEN);
		assertThat(reservedQtySum.getUomId().getRepoId()).isEqualTo(uomRecord.getC_UOM_ID());

		final HuId expectedVhu1Id = HuId.ofRepoId(vhu1.getM_HU_ID());
		assertThat(huReservation.getVhuIds()).contains(expectedVhu1Id);
		assertThat(huReservation.getReservedQtyByVhuId(expectedVhu1Id).toBigDecimal()).isEqualByComparingTo(TEN);

		final HuId expectedVhu2Id = HuId.ofRepoId(vhu2.getM_HU_ID());
		assertThat(huReservation.getVhuIds()).contains(expectedVhu2Id);
		assertThat(huReservation.getReservedQtyByVhuId(expectedVhu2Id).toBigDecimal()).isEqualByComparingTo(ONE);
	}

	@Test
	public void getBySalesOrderLineId_no_records()
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(20);

		// invoke the method under test
		final Optional<HUReservation> huReservation = huReservationRepository
				.getByDocumentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId));
		assertThat(huReservation).isNotPresent();
	}

	private I_M_HU createHuReservationRecord(final OrderLineId orderLineId, final BigDecimal qtyReserved)
	{
		final I_M_HU vhu1 = newInstance(I_M_HU.class);
		saveRecord(vhu1);

		final I_M_HU_Reservation huReservationRecord = newInstance(I_M_HU_Reservation.class);
		huReservationRecord.setC_OrderLineSO_ID(orderLineId.getRepoId());
		huReservationRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		huReservationRecord.setVHU_ID(vhu1.getM_HU_ID());
		huReservationRecord.setQtyReserved(qtyReserved);
		saveRecord(huReservationRecord);
		return vhu1;
	}

	@Test
	public void save()
	{
		final HUReservation huReservation = HUReservation.builder()
				.documentRef(HUReservationDocRef.ofSalesOrderLineId(OrderLineId.ofRepoId(20)))
				.customerId(BPartnerId.ofRepoId(123))
				.reservedQtyByVhuId(HuId.ofRepoId(10), Quantity.of(TEN, uomRecord))
				.reservedQtyByVhuId(HuId.ofRepoId(11), Quantity.of(ONE, uomRecord))
				.build();

		huReservationRepository.save(huReservation);
		List<I_M_HU_Reservation> reservationRecords = POJOLookupMap.get().getRecords(I_M_HU_Reservation.class);
		assertThat(reservationRecords).hasSize(2);
		assertThat(reservationRecords).allMatch(r -> r.getC_OrderLineSO_ID() == 20);

		final HUReservation huReservation2 = huReservation.toBuilder()
				.reservedQtyByVhuId(HuId.ofRepoId(10), Quantity.of(TEN.add(ONE), uomRecord))
				.reservedQtyByVhuId(HuId.ofRepoId(11), Quantity.of(ONE.add(ONE), uomRecord))
				.build();

		huReservationRepository.save(huReservation2);
		reservationRecords = POJOLookupMap.get().getRecords(I_M_HU_Reservation.class);
		assertThat(reservationRecords).hasSize(2); // the existing records were update, none where added
		assertThat(reservationRecords).allMatch(r -> r.getC_OrderLineSO_ID() == 20);
	}
}
