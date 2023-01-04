/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.shopware6;

import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class UOMShopwareMapping
{
	@NonNull
	ExternalSystemShopware6ConfigId externalSystemShopware6ConfigId;

	@NonNull
	UomId uomId;

	@NonNull
	String shopwareCode;

	@Builder
	public UOMShopwareMapping(
			@NonNull final ExternalSystemShopware6ConfigId externalSystemShopware6ConfigId,
			@NonNull final UomId uomId,
			@NonNull final String shopwareCode)
	{
		this.externalSystemShopware6ConfigId = externalSystemShopware6ConfigId;
		this.uomId = uomId;
		this.shopwareCode = shopwareCode;
	}
}
