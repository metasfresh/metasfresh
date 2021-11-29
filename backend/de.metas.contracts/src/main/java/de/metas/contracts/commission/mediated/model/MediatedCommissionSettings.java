/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.mediated.model;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;
import java.util.Optional;

@Value
@Builder
public class MediatedCommissionSettings
{
	@NonNull
	MediatedCommissionSettingsId commissionSettingsId;

	@NonNull
	OrgId orgId;

	@NonNull
	ProductId commissionProductId;

	int pointsPrecision;

	@NonNull
	ImmutableList<MediatedCommissionSettingsLine> lines;

	@NonNull
	public Optional<MediatedCommissionSettingsLine> getLineForProductCategory(@NonNull final ProductCategoryId productCategoryId)
	{
		return lines.stream()
				.sorted(Comparator.comparingInt(MediatedCommissionSettingsLine::getSeqNo))
				.filter(line -> line.matchesForProductCategory(productCategoryId))
				.findFirst();
	}
}
