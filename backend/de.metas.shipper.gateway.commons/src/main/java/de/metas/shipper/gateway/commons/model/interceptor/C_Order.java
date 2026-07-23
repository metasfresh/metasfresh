/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.shipper.gateway.commons.model.CarrierProductGoodsTypeAllocRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_Carrier_Service;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * {@link I_C_Order} interceptor — watches {@code Carrier_Product_ID} and keeps
 * {@code Carrier_Goods_Type_ID} and the {@code C_Order_Carrier_Service} bridge rows in sync:
 * <ul>
 *   <li>Exactly one allocated goods type → auto-set {@code Carrier_Goods_Type_ID}.</li>
 *   <li>Several allocated goods types → leave {@code Carrier_Goods_Type_ID} unset
 *       (the column val rule limits valid choices).</li>
 *   <li>Changed or cleared → clear {@code Carrier_Goods_Type_ID} and delete the
 *       {@code C_Order_Carrier_Service} rows for that order.</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
@Interceptor(I_C_Order.class)
public class C_Order
{
	@NonNull private final CarrierProductGoodsTypeAllocRepository goodsTypeAllocRepo;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Order.COLUMNNAME_Carrier_Product_ID)
	public void onCarrierProductChanged(@NonNull final I_C_Order order)
	{
		// Always clear previously derived values whenever Carrier_Product_ID changes or is cleared.
		order.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(null));
		clearOrderCarrierServices(order);

		final CarrierProductId carrierProductId = CarrierProductId.ofRepoIdOrNull(order.getCarrier_Product_ID());
		if (carrierProductId == null)
		{
			// Product was cleared — nothing more to do.
			return;
		}

		final ImmutableSet<CarrierGoodsTypeId> allocatedGoodsTypeIds = goodsTypeAllocRepo.getGoodsTypeIdsByCarrierProductId(carrierProductId);
		if (allocatedGoodsTypeIds.size() == 1)
		{
			// Exactly one allocated goods type → auto-set it.
			order.setCarrier_Goods_Type_ID(allocatedGoodsTypeIds.iterator().next().getRepoId());
		}
		// Multiple or zero allocations → leave Carrier_Goods_Type_ID unset;
		// the column val rule (constrained by the carrier product) limits valid choices when there are several.
	}

	private void clearOrderCarrierServices(@NonNull final I_C_Order order)
	{
		final int orderId = order.getC_Order_ID();
		if (orderId <= 0)
		{
			// New order not yet saved — no bridge rows can exist yet.
			return;
		}
		queryBL.createQueryBuilder(I_C_Order_Carrier_Service.class)
				.addEqualsFilter(I_C_Order_Carrier_Service.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.delete();
	}
}
