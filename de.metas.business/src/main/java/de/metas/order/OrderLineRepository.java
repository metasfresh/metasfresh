package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import de.metas.money.Currency;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.payment.api.PaymentTermId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;

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
	private final CurrencyRepository currencyRepository;

	public OrderLineRepository(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	public OrderLine getById(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId.getRepoId(), I_C_OrderLine.class);
		return ofRecord(orderLineRecord);
	}

	public OrderLine ofRecord(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final int warehouseRepoId = Util.firstGreaterThanZeroSupplier(
				() -> orderLineRecord.getM_Warehouse_ID(),
				() -> orderLineRecord.getC_Order().getM_Warehouse_ID());

		final int bPartnerRepoId = Util.firstGreaterThanZeroSupplier(
				() -> orderLineRecord.getC_BPartner_ID(),
				() -> orderLineRecord.getC_Order().getC_BPartner_ID());

		final int paymentTermId = Util.firstGreaterThanZeroSupplier(
				() -> orderLineRecord.getC_PaymentTerm_Override_ID(),
				() -> orderLineRecord.getC_Order().getC_PaymentTerm_ID());

		return OrderLine.builder()
				.id(OrderLineId.ofRepoIdOrNull(orderLineRecord.getC_OrderLine_ID()))
				.orderId(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()))
				.orgId(OrgId.ofRepoId(orderLineRecord.getAD_Org_ID()))
				.line(orderLineRecord.getLine())
				.bPartnerId(BPartnerId.ofRepoId(bPartnerRepoId))
				.datePromised(TimeUtil.asLocalDateTime(orderLineRecord.getDatePromised()))
				.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
				.priceActual(moneyOfRecordsPriceActual(orderLineRecord))
				.orderedQty(quantityOfRecordsQtyEntered(orderLineRecord))
				.asiId(AttributeSetInstanceId.ofRepoId(orderLineRecord.getM_AttributeSetInstance_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouseRepoId))
				.PaymentTermId(PaymentTermId.ofRepoId(paymentTermId))
				.build();
	}

	private Money moneyOfRecordsPriceActual(@NonNull final I_C_OrderLine orderLineRecord)
	{
		// note that C_OrderLine.C_Currency_ID is mandatory, so there won't be an NPE
		final Currency currency = currencyRepository.ofRecord(orderLineRecord.getC_Currency());

		return Money.of(
				orderLineRecord.getPriceActual(),
				currency);
	}

	private Quantity quantityOfRecordsQtyEntered(@NonNull final I_C_OrderLine orderLineRecord)
	{
		return Quantity.of(
				orderLineRecord.getQtyEntered(),
				orderLineRecord.getC_UOM());
	}
}
