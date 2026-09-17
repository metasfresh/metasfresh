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
import de.metas.order.OrderId;
import de.metas.shipper.gateway.commons.model.C_OrderCarrierServiceRepository;
import de.metas.shipper.gateway.commons.model.CarrierProductGoodsTypeAllocRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * {@link I_C_Order} interceptor — watches {@code Carrier_Product_ID} and keeps
 * {@code Carrier_Goods_Type_ID} and the {@code C_Order_Carrier_Service} bridge rows in sync.
 *
 * <p>This is a <em>data-integrity invariant</em>, not a UI-only default: the derived fields
 * must reflect the carrier-product allocation on every save path (UI, REST, import, copy).
 * No {@code isUIAction} guard is used intentionally.
 *
 * <ul>
 *   <li>Exactly one allocated goods type → auto-set {@code Carrier_Goods_Type_ID}.</li>
 *   <li>Several allocated goods types → leave {@code Carrier_Goods_Type_ID} unset
 *       (the column val rule limits valid choices to those allocated to the product).</li>
 *   <li>Changed or cleared {@code Carrier_Product_ID} → clear {@code Carrier_Goods_Type_ID}
 *       and delete the {@code C_Order_Carrier_Service} rows for that order.</li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
@Interceptor(I_C_Order.class)
@Callout(I_C_Order.class)
public class C_Order
{
	@NonNull private final CarrierProductGoodsTypeAllocRepository goodsTypeAllocRepo;
	@NonNull private final C_OrderCarrierServiceRepository orderCarrierServiceRepo;

	@Init
	public void init()
	{
		// Needed because Carrier_Goods_Type_ID is mandatory once a carrier product is set
		// (MandatoryLogic @Carrier_Product_ID@!0): on the initial selection the empty mandatory
		// goods type blocks the save, so the @ModelChange interceptor below never fires. The
		// callout fills the goods type in-UI first, before save.
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Order.COLUMNNAME_Carrier_Product_ID)
	public void onCarrierProductChanged(@NonNull final I_C_Order order)
	{
		syncGoodsType(order);
		deleteCarrierServices(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_Carrier_Product_ID)
	public void onCarrierProductChanged_callout(@NonNull final I_C_Order order)
	{
		// Callout path: mutate only the in-memory model (goods type). The C_Order_Carrier_Service
		// reset is a DB delete and must NOT run here: a WebUI callout fires on field change, not inside
		// the document's save transaction, so a delete would commit immediately and would not roll back
		// if the user abandons the edit. The service reset runs on the save path (@ModelChange above).
		syncGoodsType(order);
	}

	/**
	 * Keeps {@code Carrier_Goods_Type_ID} in sync with the selected carrier product (no DB writes; one
	 * repo read for the allocation): clear it, then auto-set it when the product has exactly one
	 * allocated goods type. Shared by the interceptor (every save path) and the callout (WebUI, before save).
	 */
	private void syncGoodsType(@NonNull final I_C_Order order)
	{
		order.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(null));

		final CarrierProductId carrierProductId = CarrierProductId.ofRepoIdOrNull(order.getCarrier_Product_ID());
		if (carrierProductId == null)
		{
			return;
		}

		final ImmutableSet<CarrierGoodsTypeId> allocatedGoodsTypeIds = goodsTypeAllocRepo.getGoodsTypeIdsByCarrierProductId(carrierProductId);
		if (allocatedGoodsTypeIds.size() == 1)
		{
			order.setCarrier_Goods_Type_ID(allocatedGoodsTypeIds.iterator().next().getRepoId());
		}
		// Multiple or zero allocations → leave Carrier_Goods_Type_ID unset;
		// the column val rule (constrained by the carrier product) limits valid choices when there are several.
	}

	/**
	 * Deletes the {@code C_Order_Carrier_Service} bridge rows for the order, so a changed or cleared
	 * carrier product never keeps services belonging to a different product. Runs only on the save path
	 * (interceptor), never in the callout — see {@link #onCarrierProductChanged_callout}.
	 */
	private void deleteCarrierServices(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(order.getC_Order_ID());
		if (orderId != null)
		{
			orderCarrierServiceRepo.deleteByOrderId(orderId);
		}
	}
}
