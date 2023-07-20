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

import de.metas.common.util.StringUtils;
import de.metas.letter.BoilerPlateId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_Tax;

@UtilityClass
public class TaxUtils
{
	public Tax from(@NonNull final I_C_Tax from)
	{
		return Tax.builder()
				.taxId(TaxId.ofRepoId(from.getC_Tax_ID()))
				.orgId(OrgId.ofRepoId(from.getAD_Org_ID()))
				.validFrom(from.getValidFrom())
				.countryId(CountryId.ofRepoIdOrNull(from.getC_Country_ID()))
				.toCountryId(CountryId.ofRepoIdOrNull(from.getTo_Country_ID()))
				.typeOfDestCountry(TypeOfDestCountry.ofNullableCode(from.getTypeOfDestCountry()))
				.taxCategoryId(TaxCategoryId.ofRepoId(from.getC_TaxCategory_ID()))
				.sopoType(SOPOType.ofNullableCode(from.getSOPOType()))
				.requiresTaxCertificate(StringUtils.toBoolean(from.getRequiresTaxCertificate()))
				.isSmallBusiness(StringUtils.toBoolean(from.getIsSmallbusiness(), null))
				.isFiscalRepresentation(StringUtils.toBoolean(from.getIsFiscalRepresentation(), null))
				.isWholeTax(from.isWholeTax())
				.isReverseCharge(from.isReverseCharge())
				.isDocumentLevel(from.isDocumentLevel())
				.isTaxExempt(from.isTaxExempt())
				.rate(from.getRate())
				.boilerPlateId(BoilerPlateId.ofRepoIdOrNull(from.getAD_BoilerPlate_ID()))
				.seqNo(from.getSeqNo())
				.taxCode(StringUtils.trimBlankToNull(from.getTaxCode()))
				.build();
	}
}
