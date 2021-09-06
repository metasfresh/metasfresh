package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductCategoryId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

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

/**
 * Template used when creating new groups.
 * <p>
 * It contains an name/productCategoryId and a list of compensation products to be added on group creation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class GroupTemplate
{
	@Nullable GroupTemplateId id;
	@NonNull String name;
	boolean isNamePrinted;
	@Nullable ActivityId activityId;
	@Nullable ProductCategoryId productCategoryId;

	@NonNull ImmutableList<GroupTemplateRegularLine> regularLinesToAdd;
	@NonNull ImmutableList<GroupTemplateCompensationLine> compensationLines;

	@Builder
	private GroupTemplate(
			@Nullable final GroupTemplateId id,
			@NonNull final String name,
			@Nullable final Boolean isNamePrinted,
			@Nullable final ActivityId activityId,
			@Nullable final ProductCategoryId productCategoryId,
			@NonNull final List<GroupTemplateRegularLine> regularLinesToAdd,
			@NonNull final @Singular List<GroupTemplateCompensationLine> compensationLines)
	{
		this.id = id;
		this.name = name;
		this.isNamePrinted = OptionalBoolean.ofNullableBoolean(isNamePrinted).orElseTrue();
		this.activityId = activityId;
		this.productCategoryId = productCategoryId;
		this.regularLinesToAdd = ImmutableList.copyOf(regularLinesToAdd);
		this.compensationLines = ImmutableList.copyOf(compensationLines);
	}
}
