package de.metas.handlingunits.reservation.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.util.Check;
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
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

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
		final org.compiere.model.I_C_Order order = ordersRepo.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final Optional<HUPIItemProductId> huPiItemProductId = hupiItemProductDAO.retrieveDefaultIdForProduct(
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()),
				extractPriceDate(orderLine, order),
				getPriceListVersionIdOrNull(orderLine, order));
		final Properties ctx = Env.getCtx();
		final I_M_HU_PI_Item_Product noPackingItemProduct = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
		orderLine.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPiItemProductId.orElse(HUPIItemProductId.ofRepoId(noPackingItemProduct.getM_HU_PI_Item_Product_ID()))));
	}

	/**
	 * {@code C_OrderLine.DatePromised} is nullable — a line whose date has not been set yet is normal, and
	 * reading that column alone would blow up here. Coalesce to the order header, mirroring the sales
	 * branch of {@code OrderLineBL}'s own price-date resolution, and for the same reason.
	 * <p>
	 * This is the packing instruction's validity date and is deliberately DatePromised-based for sales and
	 * purchase alike, exactly as before this change — only the price-list restriction below is scoped.
	 */
	@NonNull
	private static ZonedDateTime extractPriceDate(
			@NonNull final de.metas.interfaces.I_C_OrderLine orderLine,
			@NonNull final org.compiere.model.I_C_Order order)
	{
		final ZonedDateTime lineDatePromised = TimeUtil.asZonedDateTime(orderLine.getDatePromised());
		if (lineDatePromised != null)
		{
			return lineDatePromised;
		}

		return Check.assumeNotNull(
				TimeUtil.asZonedDateTime(order.getDatePromised()),
				"C_Order {} has a DatePromised", order.getC_Order_ID());
	}

	/**
	 * @return the order's price list version, which restricts the default lookup to packing instructions
	 *         that a product price references — or {@code null} to leave the lookup unrestricted.
	 *         <p>
	 *         Null is returned when the document is not a sales order, when the rule is not enforced, and
	 *         when there is no price list to resolve one from: an order that has no price list yet must
	 *         keep today's behaviour rather than silently lose its packing instruction.
	 *         <p>
	 *         Note this site cannot simply skip the lookup the way the quick-input helper does. That
	 *         helper has a price-list step ahead of the fallback which has already returned any priced
	 *         packing instruction; here the {@code IsDefaultForProduct} lookup is the only source, so
	 *         skipping it would also strip the default where a product price legitimately references it.
	 */
	@Nullable
	private PriceListVersionId getPriceListVersionIdOrNull(
			@NonNull final de.metas.interfaces.I_C_OrderLine orderLine,
			@NonNull final org.compiere.model.I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			// Sales only, matching the quick-input helper, which reaches its IsDefaultForProduct fallback
			// under an SOTrx.SALES guard. Purchase order lines keep today's behaviour untouched. Whether
			// purchase should follow the same rule is a separate question, deliberately not decided here.
			return null;
		}

		if (!hupiItemProductBL.isEnforcePrecisePricePerHUItemProduct(ClientId.ofRepoId(orderLine.getAD_Client_ID())))
		{
			return null;
		}

		// orderLineBL.getPriceListVersion throws when there is neither a line-level override nor an order
		// price list, so bail out to "unrestricted" first rather than let an incomplete order fail here.
		if (orderLine.getM_PriceList_Version_ID() <= 0 && order.getM_PriceList_ID() <= 0)
		{
			return null;
		}

		// Deliberately the shared BL rather than a local re-resolution: it honours the line-level price
		// list version override and resolves the price date in the order's org timezone.
		final I_M_PriceList_Version priceListVersion = orderLineBL.getPriceListVersion(orderLine);
		return priceListVersion == null
				? null
				: PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());
	}
}
