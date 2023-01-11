/*
 * #%L
 * marketing-activecampaign
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

package de.metas.marketing.gateway.activecampaign;

import de.metas.marketing.base.model.PageDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ActiveCampaignPageDescriptor implements PageDescriptor
{
	int limit;
	int offset;

	@NonNull
	public static ActiveCampaignPageDescriptor cast(@NonNull final PageDescriptor pageDescriptor)
	{
		return (ActiveCampaignPageDescriptor)pageDescriptor;
	}

	@NonNull
	public static ActiveCampaignPageDescriptor ofLimit(final int limit)
	{
		return ActiveCampaignPageDescriptor.builder()
				.limit(limit)
				.build();
	}

	@NonNull
	public ActiveCampaignPageDescriptor createNext()
	{
		return ActiveCampaignPageDescriptor.builder()
				.limit(limit)
				.offset(offset + limit)
				.build();
	}
}
