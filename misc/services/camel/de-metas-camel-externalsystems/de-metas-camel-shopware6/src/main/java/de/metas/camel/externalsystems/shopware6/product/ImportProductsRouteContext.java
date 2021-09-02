/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.product;

import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;

@Data
@Builder
public class ImportProductsRouteContext
{
	@NonNull
	@Setter(AccessLevel.NONE)
	private ShopwareClient shopwareClient;

	@NonNull
	@Setter(AccessLevel.NONE)
	private JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	@Setter(AccessLevel.NONE)
	private Instant nextImportStartingTimestamp;

	public void setNextImportStartingTimestamp(@NonNull final Instant candidate)
	{
		if (candidate.isAfter(nextImportStartingTimestamp))
		{
			nextImportStartingTimestamp = candidate;
		}
	}
}