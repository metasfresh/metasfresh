package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.contracts
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

@Repository
public class OrderLineRepository
{
	public OrderLine getById(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId.getRepoId(), I_C_OrderLine.class);
		return ofRecord(orderLineRecord);
	}

	public OrderLine ofRecord(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final int warehouseRepoId = CoalesceUtil.firstGreaterThanZeroSupplier(
				() -> orderLineRecord.getM_Warehouse_ID(),
				() -> orderLineRecord.getC_Order().getM_Warehouse_ID());

		final int bPartnerRepoId = CoalesceUtil.firstGreaterThanZeroSupplier(
				() -> orderLineRecord.getC_BPartner_ID(),
				() -> orderLineRecord.getC_Order().getC_BPartner_ID());

		final PaymentTermId paymentTermId = Services.get(IOrderLineBL.class).getPaymentTermId(orderLineRecord);

		final ZonedDateTime datePromised = CoalesceUtil.firstValidValue(
				date -> date != null,
				() -> TimeUtil.asZonedDateTime(orderLineRecord.getDatePromised()),
				() -> TimeUtil.asZonedDateTime(orderLineRecord.getC_Order().getDatePromised()));

		final de.metas.interfaces.I_C_OrderLine orderLineWithPackingMaterial = InterfaceWrapperHelper.create(orderLineRecord, de.metas.interfaces.I_C_OrderLine.class);

		return OrderLine.builder()
				.id(OrderLineId.ofRepoIdOrNull(orderLineRecord.getC_OrderLine_ID()))
				.orderId(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()))
				.orgId(OrgId.ofRepoId(orderLineRecord.getAD_Org_ID()))
				.line(orderLineRecord.getLine())
				.bPartnerId(BPartnerId.ofRepoId(bPartnerRepoId))
				.datePromised(datePromised)
				.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
				.priceActual(extractPriceActual(orderLineRecord))
				.orderedQty(extractQtyEntered(orderLineRecord))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(orderLineRecord.getM_AttributeSetInstance_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouseRepoId))
				.paymentTermId(paymentTermId)
				.soTrx(SOTrx.ofBoolean(orderLineRecord.getC_Order().isSOTrx()))
				.huPIItemProductId(HUPIItemProductId.ofRepoIdOrNull(orderLineWithPackingMaterial.getM_HU_PI_Item_Product_ID()))
				.build();
	}

	private ProductPrice extractPriceActual(@NonNull final I_C_OrderLine orderLineRecord)
	{
		// note that C_OrderLine C_Currency_ID and M_Product_ID are mandatory, so there won't be an NPE
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLineRecord.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLineRecord.getM_Product_ID());
		final UomId effectivePriceUomId = UomId.ofRepoId(firstGreaterThanZero(orderLineRecord.getPrice_UOM_ID(), orderLineRecord.getC_UOM_ID()));

		return ProductPrice
				.builder()
				.money(Money.of(orderLineRecord.getPriceActual(), currencyId))
				.productId(productId)
				.uomId(effectivePriceUomId)
				.build();
	}

	private Quantity extractQtyEntered(@NonNull final I_C_OrderLine orderLineRecord)
	{
		return Services.get(IOrderLineBL.class).getQtyEntered(orderLineRecord);
	}
}
