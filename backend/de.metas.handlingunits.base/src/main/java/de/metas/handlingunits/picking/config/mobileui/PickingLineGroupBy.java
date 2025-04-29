/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.config.mobileui;

import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.compiere.model.X_MobileUI_UserProfile_Picking.PICKINGLINEGROUPBY_Product_Category;

@AllArgsConstructor
@Getter
public enum PickingLineGroupBy implements ReferenceListAwareEnum
{
	NONE("NONE"),
	PRODUCT_CATEGORY(PICKINGLINEGROUPBY_Product_Category);

	private static final ReferenceListAwareEnums.ValuesIndex<PickingLineGroupBy> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static PickingLineGroupBy ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static PickingLineGroupBy ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private final String code;

	@NonNull
	public Map<String, List<PickingJobLine>> groupLines(@NonNull final List<PickingJobLine> pickingJobLines, @NonNull final PickingLineSortBy sortBy)
	{
		return pickingJobLines.stream()
				.collect(Collectors.groupingBy(
						this::getGroupKey,
						Collectors.collectingAndThen(Collectors.toList(), sortBy::sort)
				));
	}

	@NonNull
	private String getGroupKey(@NonNull final PickingJobLine pickingJobLine)
	{
		switch (this)
		{
			case PRODUCT_CATEGORY:
				return pickingJobLine.getProductCategoryId().toString();
			case NONE:
				return NONE.getCode();
			default:
				throw new RuntimeException("Value not supported! this=" + this);
		}
	}
}
