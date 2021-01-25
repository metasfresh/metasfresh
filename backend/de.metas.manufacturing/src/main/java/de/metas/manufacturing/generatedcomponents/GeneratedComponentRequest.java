/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;

@Value
public class GeneratedComponentRequest
{
	@NonNull
	ProductId productId;

	int qty;

	@NonNull
	ImmutableAttributeSet attributes;

	@NonNull ClientId clientId;

	boolean overrideExistingValues;

	@Builder
	public GeneratedComponentRequest(
			@NonNull final ProductId productId,
			final int qty,
			@NonNull final ImmutableAttributeSet attributes,
			@NonNull final ClientId clientId,
			final boolean overrideExistingValues)
	{
		Check.errorIf(qty < 1, "qty {} must be > 0", qty);

		this.productId = productId;
		this.qty = qty;
		this.attributes = attributes;
		this.clientId = clientId;
		this.overrideExistingValues = overrideExistingValues;
	}
}
