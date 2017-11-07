package de.metas.material.dispo.commons.repository;

import java.util.Date;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class MaterialDescriptorQuery
{
	private final int warehouseId;
	private final Date date;
	private final int productId;
	private final String storageAttributesKey;

	@Builder
	private MaterialDescriptorQuery(
			final int warehouseId,
			@NonNull final Date date,
			final int productId,
			@NonNull final String storageAttributesKey)
	{
		Preconditions.checkArgument(warehouseId > 0, "warehouseId > 0");
		Preconditions.checkArgument(productId > 0, "productId > 0");

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;

		this.warehouseId = warehouseId;

		this.date = date;
	}

}
