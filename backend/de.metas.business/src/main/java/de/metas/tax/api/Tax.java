/*
 * #%L
 * de.metas.business
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

package de.metas.tax.api;

import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

@Value
public class Tax
{
	@NonNull TaxId taxId;
	@NonNull OrgId orgId;
	@NonNull Timestamp validFrom;
	@NonNull CountryId countryId;
	@Nullable
	CountryId toCountryId;
	@Nullable
	TypeOfDestCountry typeOfDestCountry;
	@NonNull TaxCategoryId taxCategoryId;
	boolean requiresTaxCertificate;
	SOPOType sopoType;
	boolean isTaxExempt;
	boolean isFiscalRepresentation;
	boolean isSmallBusiness;

	@Builder
	public Tax(final @NonNull TaxId taxId,
			final @NonNull OrgId orgId,
			final @NonNull Timestamp validFrom,
			final @NonNull CountryId countryId,
			final @Nullable CountryId toCountryId,
			final @Nullable TypeOfDestCountry typeOfDestCountry,
			final @NonNull TaxCategoryId taxCategoryId,
			final boolean requiresTaxCertificate,
			final SOPOType sopoType,
			final boolean isTaxExempt,
			final boolean isFiscalRepresentation,
			final boolean isSmallBusiness)
	{
		this.taxId = taxId;
		this.orgId = orgId;
		this.validFrom = validFrom;
		this.countryId = countryId;

		this.toCountryId = toCountryId;
		this.typeOfDestCountry = typeOfDestCountry;
		this.taxCategoryId = taxCategoryId;
		this.requiresTaxCertificate = requiresTaxCertificate;
		this.sopoType = sopoType;
		this.isTaxExempt = isTaxExempt;
		this.isFiscalRepresentation = isFiscalRepresentation;
		this.isSmallBusiness = isSmallBusiness;
	}
}
