package org.eevolution.api;

import java.time.LocalDate;
import java.time.Month;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class BOMCreateRequest
{
	OrgId orgId;
	ProductId productId;
	String productValue;
	String productName;
	UomId uomId;
	BOMType bomType;
	BOMUse bomUse;
	LocalDate validFrom;
	ImmutableList<BOMLine> lines;

	@Builder
	private BOMCreateRequest(
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final String productValue,
			@NonNull final String productName,
			@NonNull final UomId uomId,
			@NonNull final BOMType bomType,
			@NonNull final BOMUse bomUse,
			@Nullable final LocalDate validFrom,
			@NonNull @Singular final ImmutableList<BOMLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines is not empty");

		this.orgId = orgId;
		this.productId = productId;
		this.productValue = productValue;
		this.productName = productName;
		this.uomId = uomId;
		this.bomType = bomType;
		this.bomUse = bomUse;
		this.validFrom = validFrom != null ? validFrom : LocalDate.of(1970, Month.JANUARY, 1);
		this.lines = lines;
	}

	@Value
	@Builder
	public static class BOMLine
	{
		@NonNull
		@Default
		BOMComponentType componentType = BOMComponentType.Component;

		@NonNull
		ProductId productId;

		@NonNull
		Quantity qty;
	}
}
