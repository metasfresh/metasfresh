package de.metas.ui.web.order.products_proposal.service;

import de.metas.currency.Currency;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

@Value
@Builder
public class OrderLine
{
	@NonNull
	OrderLineId orderLineId;

	@NonNull
	ProductId productId;

	@Nullable
	HUPIItemProductId packingMaterialId;
	boolean packingMaterialWithInfiniteCapacity;

	@NonNull
	BigDecimal priceActual;
	
	@NonNull
	BigDecimal priceEntered;

	@NonNull
	Currency currency;

	@NonNull
	BigDecimal qtyEnteredCU;

	@NonNull
	AttributeSetInstanceId asiId;

	int qtyEnteredTU;

	String description;

	public boolean isMatching(
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId packingMaterialId)

	{
		return ProductId.equals(this.productId, productId)
				&& HUPIItemProductId.equals(
						HUPIItemProductId.nullToVirtual(this.packingMaterialId),
				HUPIItemProductId.nullToVirtual(packingMaterialId));
	}

}
