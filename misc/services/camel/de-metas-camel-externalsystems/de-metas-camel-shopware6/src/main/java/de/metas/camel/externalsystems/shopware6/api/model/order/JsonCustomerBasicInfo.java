/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@NonFinal
@Value
public abstract class JsonCustomerBasicInfo
{
	@NonNull
	String firstName;

	@NonNull
	String lastName;

	@Nullable
	String company;

	@Nullable
	String customerNumber;

	@Nullable
	String email;

	@Nullable
	ZonedDateTime createdAt;

	@Nullable
	ZonedDateTime updatedAt;

	@Nullable
	List<String> vatIds;

	@Nullable
	public String getVatIdOrNull()
	{
		return Optional.ofNullable(vatIds)
				.stream()
				.flatMap(List::stream)
				.filter(Check::isNotBlank)
				.findFirst()
				.orElse(null);
	}
}

