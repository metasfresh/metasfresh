package de.metas.order.compensationGroup;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.product.ProductCategoryId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

/**
 * Template used when creating new groups.
 * 
 * It contains an name/productCategoryId and a list of compensation products to be added on group creation.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class GroupTemplate
{
	GroupTemplateId id;
	String name;
	ProductCategoryId productCategoryId;
	List<GroupTemplateLine> lines;

	@Builder
	private GroupTemplate(
			final GroupTemplateId id,
			@NonNull final String name,
			final ProductCategoryId productCategoryId,
			@Singular List<GroupTemplateLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines is not empty");

		this.id = id;
		this.name = name;
		this.productCategoryId = productCategoryId;
		this.lines = ImmutableList.copyOf(lines);
	}
}
