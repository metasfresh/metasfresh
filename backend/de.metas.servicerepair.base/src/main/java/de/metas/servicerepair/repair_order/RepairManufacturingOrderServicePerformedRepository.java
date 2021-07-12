/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.repair_order;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.repair_order.model.RepairManufacturingOrderServicePerformed;
import de.metas.servicerepair.repository.model.I_PP_Order_RepairService;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RepairManufacturingOrderServicePerformedRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<RepairManufacturingOrderServicePerformed> getByRepairOrderId(@NonNull final PPOrderId repairOrderId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_RepairService.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_RepairService.COLUMNNAME_PP_Order_ID, repairOrderId)
				.create()
				.stream()
				.map(record -> fromRecord(record))
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<RepairManufacturingOrderServicePerformed> getByRepairOrderAndServiceId(
			@NonNull final PPOrderId repairOrderId,
			@NonNull final ProductId serviceId)
	{
		return retrieveRecordByRepairOrderAndServiceId(repairOrderId, serviceId)
				.map(record -> fromRecord(record));
	}

	@NonNull
	private Optional<I_PP_Order_RepairService> retrieveRecordByRepairOrderAndServiceId(final @NonNull PPOrderId repairOrderId, final @NonNull ProductId serviceId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_RepairService.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_RepairService.COLUMNNAME_PP_Order_ID, repairOrderId)
				.addEqualsFilter(I_PP_Order_RepairService.COLUMNNAME_M_Product_ID, serviceId)
				.create()
				.firstOnlyOptional(I_PP_Order_RepairService.class);
	}

	private static RepairManufacturingOrderServicePerformed fromRecord(@NonNull final I_PP_Order_RepairService record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return RepairManufacturingOrderServicePerformed.builder()
				.repairOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.serviceId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qty(Quantitys.create(record.getQty(), uomId))
				.build();
	}

	public void addOrUpdateServicePerformed(
			@NonNull final PPOrderId repairOrderId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qty)
	{
		final I_PP_Order_RepairService record = retrieveRecordByRepairOrderAndServiceId(repairOrderId, productId)
				.orElseGet(() -> newRecord(repairOrderId, productId));

		record.setC_UOM_ID(qty.getUomId().getRepoId());
		record.setQty(qty.toBigDecimal());

		InterfaceWrapperHelper.saveRecord(record);
	}

	private I_PP_Order_RepairService newRecord(
			@NonNull final PPOrderId repairOrderId,
			@NonNull final ProductId productId)
	{
		final I_PP_Order_RepairService record = InterfaceWrapperHelper.newInstance(I_PP_Order_RepairService.class);
		record.setPP_Order_ID(repairOrderId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		return record;
	}
}
