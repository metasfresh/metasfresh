/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class M_HU_Reservation_StepDef
{
	@NonNull private static final Logger logger = LogManager.getLogger(M_HU_Reservation_StepDef.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final HUReservationService huReservationService = SpringContextHolder.instance.getBean(HUReservationService.class);

	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

	@And("reserve HU to order")
	public void reserveHUToOrder(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final I_C_OrderLine orderLine = row.getAsIdentifier("C_OrderLine_ID").lookupIn(orderLineTable);
			final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());

			final I_M_HU hu = row.getAsIdentifier("M_HU_ID").lookupIn(huTable);
			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

			final I_M_HU_Storage huStorageRecord = getHUStorageRecord(huId);

			final HUReservation huReservation = huReservationService.makeReservation(
							ReserveHUsRequest.builder()
									.qtyToReserve(Quantitys.create(huStorageRecord.getQty(), UomId.ofRepoId(huStorageRecord.getC_UOM_ID())))
									.documentRef(HUReservationDocRef.ofSalesOrderLineId(orderLineId))
									.productId(ProductId.ofRepoId(huStorageRecord.getM_Product_ID()))
									.huId(huId)
									.build()
					)
					.orElse(null);

			assertThat(huReservation).as("Reservation for " + huId).isNotNull();
			logger.info("Reservation created: {}", huReservation);
		});
	}

	private I_M_HU_Storage getHUStorageRecord(@NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huId)
				.create()
				.firstOnlyOptional(I_M_HU_Storage.class)
				.orElseThrow(() -> new AdempiereException("No HU storage found for " + huId));
	}

}
