package de.metas.order.compensationGroup;

import java.math.BigDecimal;

import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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
@Builder
public class GroupCompensationLineCreateRequest
{
	ProductId productId;
	UomId uomId;

	@NonNull GroupCompensationType type;
	@NonNull GroupCompensationAmtType amtType;

	Percent percentage;
	BigDecimal qtyEntered;
	BigDecimal price;

	GroupTemplateLineId groupTemplateLineId;

	/** Product category the discount is computed on; {@code null} = computed on the whole group's regular lines */
	@Nullable
	ProductCategoryId appliesToProductCategoryId;

	/** Packing-material category the discount is restricted to; {@code null} = no packing restriction */
	@Nullable
	ProductCategoryId packingMaterialProductCategoryId;

	/** see {@link GroupCompensationLine#hasOwnBase()} */
	boolean ownBase;

	/** Free-text description written onto the created {@code C_OrderLine}; {@code null} = none */
	@Nullable
	String description;
}
