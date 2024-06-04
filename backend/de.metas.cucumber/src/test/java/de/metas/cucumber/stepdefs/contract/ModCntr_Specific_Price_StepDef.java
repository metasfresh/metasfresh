/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ModCntr_Specific_Price_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final C_Flatrate_Term_StepDefData contractTable;
	private final M_Product_StepDefData productTable;
	private final ModCntr_Module_StepDefData modCntrModuleTable;
	private final ModCntr_Specific_Price_StepDefData specificPriceTable;

	public ModCntr_Specific_Price_StepDef(
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final ModCntr_Module_StepDefData modCntrModuleTable,
			@NonNull final ModCntr_Specific_Price_StepDefData specificPriceTable)
	{
		this.contractTable = contractTable;
		this.productTable = productTable;
		this.modCntrModuleTable = modCntrModuleTable;
		this.specificPriceTable = specificPriceTable;
	}

	@And("^after not more than (.*)s, ModCntr_Specific_Prices are found:$")
	public void there_are_specificPrices(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadSpecificPrice(tableRow));

			final SoftAssertions softly = new SoftAssertions();

			final String specificPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Specific_Price_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_ModCntr_Specific_Price specificPrice = specificPriceTable.get(specificPriceIdentifier);
			InterfaceWrapperHelper.refresh(specificPrice);

			final Integer seqNo = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_SeqNo);
			if (seqNo != null)
			{
				softly.assertThat(specificPrice.getSeqNo()).as(I_ModCntr_Specific_Price.COLUMNNAME_SeqNo).isEqualTo(seqNo);
			}

			final BigDecimal price = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_Price);
			if (price != null)
			{
				softly.assertThat(specificPrice.getPrice()).as(I_ModCntr_Specific_Price.COLUMNNAME_Price).isEqualByComparingTo(price);
			}

			final String currencyIsoCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_C_Currency_ID + ".ISO_Code");
			if (Check.isNotBlank(currencyIsoCode))
			{
				final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode));
				softly.assertThat(specificPrice.getC_Currency_ID()).as(I_ModCntr_Specific_Price.COLUMNNAME_C_Currency_ID + ".ISO_Code").isEqualTo(currency.getId().getRepoId());
			}

			final String uomCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			if (Check.isNotBlank(uomCode))
			{
				final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));
				softly.assertThat(specificPrice.getC_UOM_ID()).as(I_ModCntr_Specific_Price.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName()).isEqualTo(uomId.getRepoId());
			}

			final Boolean isScalePrice = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice);
			if (isScalePrice != null)
			{
				softly.assertThat(specificPrice.isScalePrice()).as(I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice).isEqualTo(isScalePrice);
			}

			final BigDecimal minValue = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_MinValue);
			if (minValue != null)
			{
				softly.assertThat(specificPrice.getMinValue()).as(I_ModCntr_Specific_Price.COLUMNNAME_MinValue).isEqualByComparingTo(minValue);
			}

			softly.assertAll();
		}
	}

	@NonNull
	private Boolean loadSpecificPrice(@NonNull final Map<String, String> tableRow)
	{
		final String contractIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Term contractRecord = contractTable.get(contractIdentifier);
		assertThat(contractRecord).isNotNull();

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		assertThat(productRecord).isNotNull();

		final String moduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_Module moduleRecord = modCntrModuleTable.get(moduleIdentifier);
		assertThat(productRecord).isNotNull();

		final Optional<I_ModCntr_Specific_Price> specificPrice = queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, contractRecord.getC_Flatrate_Term_ID())
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, moduleRecord.getModCntr_Module_ID())
				.create()
				.firstOnlyOptional(I_ModCntr_Specific_Price.class);

		if (specificPrice.isEmpty())
		{
			return false;
		}

		final String specificPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Specific_Price_ID + "." + TABLECOLUMN_IDENTIFIER);
		specificPriceTable.putOrReplace(specificPriceIdentifier, specificPrice.get());

		return true;
	}
}
