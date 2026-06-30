package de.metas.handlingunits.reservation.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

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

@Component
@Interceptor(I_C_OrderLine.class)
@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	// Note: receipt schedule cleanup belongs conceptually in de.metas.business, but adding
	// IReceiptScheduleDAO there would create a circular Maven dependency
	// (de.metas.handlingunits.base → de.metas.swat.base → de.metas.business).
	// This interceptor lives in de.metas.handlingunits.base, which already depends on
	// de.metas.swat.base, making it the lowest layer where the call is possible.
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeOrderLineDeleted(@NonNull final I_C_OrderLine orderLineRecord)
	{
		// Receipt schedules only exist for purchase order lines
		if (orderBL.isSalesOrder(OrderId.ofRepoId(orderLineRecord.getC_Order_ID())))
		{
			return;
		}
		receiptScheduleDAO.deleteByOrderLineId(OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID()));
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Order.COLUMNNAME_M_Product_ID)
	@CalloutMethod(columnNames = de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_Product_ID)
	public void onProductSetOrChanged(final de.metas.interfaces.I_C_OrderLine orderLine)
	{
		final Optional<HUPIItemProductId> huPiItemProductId = hupiItemProductDAO.retrieveDefaultIdForProduct(ProductId.ofRepoId(orderLine.getM_Product_ID()),
				BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()),
				TimeUtil.asZonedDateTime(orderLine.getDatePromised()));
		final Properties ctx = Env.getCtx();
		final I_M_HU_PI_Item_Product noPackingItemProduct = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		orderLine.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPiItemProductId.orElse(HUPIItemProductId.ofRepoId(noPackingItemProduct.getM_HU_PI_Item_Product_ID()))));
	}
}
