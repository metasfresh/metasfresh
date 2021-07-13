/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.ui.web.quickinput.field;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@Builder
public class DefaultPackingItemCriteria
{
	@NonNull
	private ProductId productId;

	@NonNull
	private BPartnerLocationId bPartnerLocationId;

	@NonNull
	private ZonedDateTime date;

	@Nullable
	private PricingSystemId pricingSystemId;

	@Nullable
	private PriceListId priceListId;

	@Nullable
	private SOTrx soTrx;
	
	@NonNull
	private ClientId clientId;

	public static Optional<DefaultPackingItemCriteria> of(final I_C_Order order, final ProductId productId)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(order.getDatePromised());
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());

		final boolean anyNull = Stream.of(bpartnerLocationId, pricingSystemId, date, productId).anyMatch(Objects::isNull);

		if (anyNull) {
			return Optional.empty();
		}

		return Optional.of(
				builder()
						.bPartnerLocationId(bpartnerLocationId)
						.productId(productId)
						.pricingSystemId(pricingSystemId)
						.date(date)
						.soTrx(soTrx)
						.clientId(clientId)
						.build() );
	}

	public static Optional<DefaultPackingItemCriteria> of(@NonNull final I_C_Invoice invoice, @NonNull final ProductId productId)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID());
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(invoice.getDateInvoiced());
		final ClientId clientId = ClientId.ofRepoId(invoice.getAD_Client_ID());
		
		final boolean anyNull = Stream.of(bpartnerLocationId,priceListId,date, productId).anyMatch(Objects::isNull);

		if (anyNull) {
			return Optional.empty();
		}

		return Optional.of(
				builder()
						.productId(productId)
						.priceListId(priceListId)
						.date(date)
						.bPartnerLocationId(bpartnerLocationId)
						.clientId(clientId)
						.build());
	}
}
