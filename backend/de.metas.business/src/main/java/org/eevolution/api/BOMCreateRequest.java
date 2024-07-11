package org.eevolution.api;

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
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

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
	@NonNull
	Instant validFrom;
	Boolean isActive;
	AttributeSetInstanceId attributeSetInstanceId;
	ImmutableList<BOMLine> lines;

	@Builder
	private BOMCreateRequest(
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final String productValue,
			@NonNull final String productName,
			@NonNull final UomId uomId,
			@Nullable final BOMType bomType,
			@Nullable final BOMUse bomUse,
			@Nullable final Instant validFrom,
			@Nullable final Boolean isActive,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
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
		this.isActive = isActive;
		this.lines = lines;
		this.validFrom = validFrom != null ? validFrom : Instant.now();
		this.attributeSetInstanceId = attributeSetInstanceId;
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

		@Nullable
		BOMIssueMethod issueMethod;

		@Nullable
		Boolean isQtyPercentage;

		@Nullable
		Integer line;

		@Nullable
		BigDecimal scrap;

		@Nullable
		AttributeSetInstanceId attributeSetInstanceId;
	}
}
