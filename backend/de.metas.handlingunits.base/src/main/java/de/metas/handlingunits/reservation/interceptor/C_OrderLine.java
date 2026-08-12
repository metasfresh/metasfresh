package de.metas.handlingunits.reservation.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
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
	private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteReservation(@NonNull final I_C_OrderLine orderLineRecord)
	{
		// TODO
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_Order.COLUMNNAME_M_Product_ID)
	@CalloutMethod(columnNames = de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_Product_ID)
	public void onProductSetOrChanged(final de.metas.interfaces.I_C_OrderLine orderLine)
	{
		final ZonedDateTime date = TimeUtil.asZonedDateTime(orderLine.getDatePromised());

		final Optional<HUPIItemProductId> huPiItemProductId = hupiItemProductDAO.retrieveDefaultIdForProduct(
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()),
				date,
				getPriceListVersionIdOrNull(orderLine, date));
		final Properties ctx = Env.getCtx();
		final I_M_HU_PI_Item_Product noPackingItemProduct = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		orderLine.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPiItemProductId.orElse(HUPIItemProductId.ofRepoId(noPackingItemProduct.getM_HU_PI_Item_Product_ID()))));
	}

	/**
	 * @return the order's price list version, which restricts the default lookup to packing instructions
	 *         that a product price references — or {@code null} to leave the lookup unrestricted.
	 *         <p>
	 *         Null is returned both when the rule is not enforced and when the price list version cannot
	 *         be resolved: an order that has no price list yet must keep today's behaviour rather than
	 *         silently lose its packing instruction.
	 *         <p>
	 *         Note this site cannot simply skip the lookup the way the quick-input helper does. That
	 *         helper has a price-list step ahead of the fallback which has already returned any priced
	 *         packing instruction; here the {@code IsDefaultForProduct} lookup is the only source, so
	 *         skipping it would also strip the default where a product price legitimately references it.
	 */
	@Nullable
	private PriceListVersionId getPriceListVersionIdOrNull(
			@NonNull final de.metas.interfaces.I_C_OrderLine orderLine,
			@NonNull final ZonedDateTime date)
	{
		if (!hupiItemProductBL.isEnforcePrecisePricePerHUItemProduct(ClientId.ofRepoId(orderLine.getAD_Client_ID())))
		{
			return null;
		}

		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(
				ordersRepo.getById(OrderId.ofRepoId(orderLine.getC_Order_ID())).getM_PriceList_ID());
		if (priceListId == null)
		{
			return null;
		}

		final I_M_PriceList_Version priceListVersion = priceListsRepo.retrievePriceListVersionOrNull(priceListId, date, null);
		return priceListVersion == null
				? null
				: PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());
	}
}
