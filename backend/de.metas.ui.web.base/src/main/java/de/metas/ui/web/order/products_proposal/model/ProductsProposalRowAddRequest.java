package de.metas.ui.web.order.products_proposal.model;

import de.metas.currency.Amount;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.time.LocalDate;

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
public class ProductsProposalRowAddRequest
{
	@NonNull
	LookupValue product;

	@NonNull
	@Default
	ProductASIDescription asiDescription = ProductASIDescription.NONE;

	@Nullable
	AttributeSetInstanceId asiId;

	@NonNull
	Amount priceListPrice;

	@Nullable
	Integer lastShipmentDays;

	@Nullable
	ProductPriceId copiedFromProductPriceId;

	@Nullable
	HUPIItemProductId packingMaterialId;

	@Nullable
	ITranslatableString packingDescription;

	@Nullable
	LocalDate lastQuotationDate;

	@Nullable
	Amount lastQuotationPrice;

	@Nullable
	LookupValue lastQuotationPriceUOM;

	@Nullable
	LookupValue incoterms;

	@Nullable
	Boolean quotationOrdered;

	public ProductId getProductId()
	{
		return getProduct().getIdAs(ProductId::ofRepoId);
	}
}
