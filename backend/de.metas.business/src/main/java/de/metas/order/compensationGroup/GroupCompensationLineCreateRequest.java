package de.metas.order.compensationGroup;

import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

	@Nullable GroupTemplateLineId groupTemplateLineId;

	@NonNull @With @Builder.Default ManualCompensationLinePosition position = ManualCompensationLinePosition.DEFAULT;
}
