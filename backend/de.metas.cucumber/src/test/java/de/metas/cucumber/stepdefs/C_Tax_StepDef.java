/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.location.ICountryDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;

import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_TaxCategory_InternalName;
import static de.metas.cucumber.stepdefs.StepDefConstants.DEFAULT_ValidFrom;

@RequiredArgsConstructor
public class C_Tax_StepDef
{
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final AD_Org_StepDefData orgTable;

	@And("metasfresh contains C_Tax")
	public void createC_Taxes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Tax.COLUMNNAME_C_Tax_ID)
				.forEach(this::createC_Tax);
	}

	private void createC_Tax(@NonNull final DataTableRow tableRow)
	{
		final String taxCategoryInternalName = tableRow.getAsOptionalString(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "." + I_C_TaxCategory.COLUMNNAME_InternalName)
				.orElse(DEFAULT_TaxCategory_InternalName);
		final TaxCategoryId taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName)
				.orElseThrow(() -> new AdempiereException("Missing taxCategory for internalName=" + taxCategoryInternalName));

		final ValueAndName valueAndName = tableRow.suggestValueAndName();
		final String taxName = valueAndName.getName();

		final I_C_Tax taxRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> retrieveTaxRecordByName(taxName),
				() -> InterfaceWrapperHelper.newInstance(I_C_Tax.class)
		);
		final boolean isNew = InterfaceWrapperHelper.isNew(taxRecord);

		taxRecord.setName(taxName);
		taxRecord.setC_TaxCategory_ID(taxCategoryId.getRepoId());
		if (isNew)
		{
			taxRecord.setSeqNo(getNextTaxSeqNo(taxCategoryId));
			taxRecord.setValidFrom(TimeUtil.asTimestamp(DEFAULT_ValidFrom));
		}

		tableRow.getAsOptionalLocalDateTimestamp(I_C_Tax.COLUMNNAME_ValidFrom)
				.ifPresent(taxRecord::setValidFrom);
		tableRow.getAsOptionalBigDecimal(I_C_Tax.COLUMNNAME_Rate)
				.ifPresent(taxRecord::setRate);
		tableRow.getAsOptionalCountryCode(I_C_Tax.COLUMNNAME_C_Country_ID)
				.map(countryDAO::getCountryIdByCountryCode)
				.ifPresent(countryId -> taxRecord.setC_Country_ID(countryId.getRepoId()));
		tableRow.getAsOptionalCountryCode(I_C_Tax.COLUMNNAME_To_Country_ID)
				.map(countryDAO::getCountryIdByCountryCode)
				.ifPresent(countryId -> taxRecord.setTo_Country_ID(countryId.getRepoId()));
		tableRow.getAsOptionalIdentifier(I_C_Tax.COLUMNNAME_AD_Org_ID)
				.map(orgTable::getId)
				.ifPresent(orgId -> taxRecord.setAD_Org_ID(orgId.getRepoId()));
		tableRow.getAsOptionalBoolean(I_C_Tax.COLUMNNAME_IsTaxExempt)
				.ifPresent(taxRecord::setIsTaxExempt);
		tableRow.getAsOptionalInt(I_C_Tax.COLUMNNAME_SeqNo)
				.ifPresent(taxRecord::setSeqNo);
		tableRow.getAsOptionalString(I_C_Tax.COLUMNNAME_TypeOfDestCountry)
				.ifPresent(taxRecord::setTypeOfDestCountry);


		InterfaceWrapperHelper.saveRecord(taxRecord);

		tableRow.getAsOptionalIdentifier()
				.ifPresent(identifier -> taxTable.putOrReplace(identifier, TaxUtils.from(taxRecord)));
	}

	private @Nullable I_C_Tax retrieveTaxRecordByName(final String taxName)
	{
		return queryBL.createQueryBuilder(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_Name, taxName)
				.create()
				.firstOnly(I_C_Tax.class);
	}

	private int getNextTaxSeqNo(final TaxCategoryId taxCategoryId)
	{
		return queryBL.createQueryBuilder(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId)
				.orderBy(I_C_Tax.COLUMNNAME_SeqNo)
				.create()
				.firstOptional(I_C_Tax.class)
				.map(I_C_Tax::getSeqNo)
				.map(currentMinSeqNo -> currentMinSeqNo - 1)
				.orElse(0);
	}
}