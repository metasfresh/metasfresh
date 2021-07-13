package de.metas.ui.web.order.products_proposal.model;

import java.math.BigDecimal;
import java.util.Optional;

import de.metas.pricing.ProductPriceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

public interface ProductsProposalRowChangeRequest
{
	@Builder
	@Value
	public static class UserChange implements ProductsProposalRowChangeRequest
	{
		Optional<BigDecimal> qty;
		Optional<BigDecimal> price;
		Optional<String> description;
	}

	@Value
	@Builder
	public static class RowUpdate implements ProductsProposalRowChangeRequest
	{
		ProductProposalPrice price;
		Integer lastShipmentDays;
		ProductPriceId copiedFromProductPriceId;
	}

	@Builder
	@Value
	public static class RowSaved implements ProductsProposalRowChangeRequest
	{
		@NonNull
		ProductPriceId productPriceId;
		@NonNull
		ProductProposalPrice price;
	}
}
