/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.service;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Value
@Builder
public class AlbertaCompositeProductInfo
{
	@NonNull
	ProductId productId;

	@Nullable
	String albertaArticleId;

	@Nullable
	String productGroupId;

	@NonNull
	Instant lastUpdated;

	@Nullable
	String additionalDescription;

	@Nullable
	String size;

	@Nullable
	String medicalAidPositionNumber;

	@Nullable
	String inventoryType;

	@Nullable
	String status;

	@Nullable
	BigDecimal stars;

	@Nullable
	String assortmentType;

	@Nullable
	String purchaseRating;

	@Nullable
	BigDecimal pharmacyPrice;

	@Nullable
	BigDecimal fixedPrice;

	@Nullable
	List<String> therapyIds;

	@Nullable
	List<String> billableTherapyIds;

	@Nullable
	List<AlbertaPackagingUnit> albertaPackagingUnitList;
}
