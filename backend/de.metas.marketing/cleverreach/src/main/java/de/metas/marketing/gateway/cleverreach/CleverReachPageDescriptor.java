/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.marketing.gateway.cleverreach;

import de.metas.marketing.base.model.PageDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CleverReachPageDescriptor implements PageDescriptor
{
	int pageSize;
	int page;

	@NonNull
	public static CleverReachPageDescriptor cast(@NonNull final PageDescriptor pageDescriptor)
	{
		return (CleverReachPageDescriptor)pageDescriptor;
	}

	@NonNull
	public static CleverReachPageDescriptor ofPageSize(final int pageSize)
	{
		return CleverReachPageDescriptor.builder()
				.pageSize(pageSize)
				.build();
	}

	@NonNull
	public CleverReachPageDescriptor createNext()
	{
		return CleverReachPageDescriptor.builder()
				.pageSize(pageSize)
				.page(page + 1)
				.build();
	}
}
