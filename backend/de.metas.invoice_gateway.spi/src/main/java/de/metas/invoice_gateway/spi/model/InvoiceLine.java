package de.metas.invoice_gateway.spi.model;

import de.metas.invoice.InvoiceAndLineId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * metasfresh-invoice.gateway.commons
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

@Value
@Builder
public class InvoiceLine
{
	@NonNull
	InvoiceAndLineId id;

	@NonNull
	ProductId productId;

	@Singular
	List<String> externalIds;

	@NonNull
	Money lineAmount;
}
