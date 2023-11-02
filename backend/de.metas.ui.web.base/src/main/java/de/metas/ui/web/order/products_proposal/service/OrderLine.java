package de.metas.ui.web.order.products_proposal.service;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;

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
	BigDecimal qtyEnteredCU;

	@Nullable
	AttributeSetInstanceId asiId;

	int qtyEnteredTU;

	String description;

	boolean isMatching(
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId packingMaterialId,
			@Nullable final AttributeSetInstanceId asiId)
	{
		return ProductId.equals(this.productId, productId)
				&& HUPIItemProductId.equals(
				HUPIItemProductId.nullToVirtual(this.packingMaterialId),
				HUPIItemProductId.nullToVirtual(packingMaterialId))
				&& attributesMatch(asiId);
	}

	private boolean attributesMatch(@Nullable final AttributeSetInstanceId asiId)
	{
		if (getAsiId() == null || asiId == null)
		{
			return asiId == getAsiId();
		}

		final AttributesKey orderLineKey = AttributesKeys.createAttributesKeyFromASIPricingAttributes(this.asiId).orElse(AttributesKey.NONE);
		final AttributesKey productProposalKey = AttributesKeys.createAttributesKeyFromASIPricingAttributes(asiId).orElse(AttributesKey.NONE);

		final boolean orderLineCondition = orderLineKey.contains(productProposalKey);

		boolean matches = orderLineCondition;
		if (orderLineCondition)
		{
			if(!productProposalKey.isNone())
			{
				for (final AttributesKeyPart productProposalPart : productProposalKey.getParts())
				{
					if (!orderLineKey.getValueByAttributeId(productProposalPart.getAttributeId()).equals(productProposalPart.getValue()))
					{
						matches = false;
						break;
					}

				}
			}
		}

		return matches;
	}
}
