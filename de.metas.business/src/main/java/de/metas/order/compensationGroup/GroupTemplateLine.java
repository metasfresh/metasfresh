package de.metas.order.compensationGroup;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.product.ProductId;
import de.metas.util.lang.Percent;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class GroupTemplateLine
{
	public static GroupTemplateLine ofProductId(final ProductId productId)
	{
		return builder()
				.productId(productId)
				.build();
	}

	GroupTemplateLineId id;
	ProductId productId;
	private Percent percentage;
	private GroupMatcher groupMatcher;

	@Builder
	private GroupTemplateLine(
			@Nullable final GroupTemplateLineId id,
			@NonNull final ProductId productId,
			@Nullable final BigDecimal percentage,
			@Nullable GroupMatcher groupMatcher)
	{
		this.id = id;
		this.productId = productId;
		this.percentage = Percent.ofNullable(percentage);
		this.groupMatcher = groupMatcher != null ? groupMatcher : GroupMatchers.ALWAYS;
	}

}
