package de.metas.order.compensationGroup;

import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

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
	int id;
	String name;
	int productCategoryId;
	List<GroupTemplateLine> lines;

	@Builder
	private GroupTemplate(
			final int id,
			@NonNull final String name,
			final int productCategoryId,
			@Singular List<GroupTemplateLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines is not empty");

		this.id = id > 0 ? id : 0;
		this.name = name;
		this.productCategoryId = productCategoryId > 0 ? productCategoryId : 0;
		this.lines = ImmutableList.copyOf(lines);
	}
}
