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
import de.metas.contracts.modular.ModCntrSpecificPrice;
import de.metas.contracts.modular.ModCntrSpecificPriceId;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.log.ModCntrLogPriceUpdateRequest;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class ModCntr_Specific_Price_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final static Logger logger = LogManager.getLogger(ModCntr_Specific_Price_StepDef.class);

	@NonNull private final ModularContractLogService contractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	@NonNull private final ModularContractPriceService modularContractPriceService = SpringContextHolder.instance.getBean(ModularContractPriceService.class);
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry = SpringContextHolder.instance.getBean(ModularContractLogHandlerRegistry.class);

	@NonNull private final C_Flatrate_Term_StepDefData contractTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final ModCntr_Module_StepDefData modCntrModuleTable;
	@NonNull private final ModCntr_Specific_Price_StepDefData specificPriceTable;

	@And("^after not more than (.*)s, ModCntr_Specific_Prices are found:$")
	public void there_are_specificPrices(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String specificPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Specific_Price_ID + "." + TABLECOLUMN_IDENTIFIER);
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadSpecificPrice(tableRow), () -> logger.error("Can't find specific price for: {}", specificPriceIdentifier));

			final SoftAssertions softly = new SoftAssertions();

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

		final IQueryBuilder<I_ModCntr_Specific_Price> queryBuilder = queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, contractRecord.getC_Flatrate_Term_ID())
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, moduleRecord.getModCntr_Module_ID());

		final boolean isScalePrice = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice, false);
		if (isScalePrice)
		{
			final BigDecimal minValue = DataTableUtil.extractBigDecimalForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_MinValue);
			queryBuilder.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice, true)
					.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_MinValue, minValue);
		}

		final Optional<I_ModCntr_Specific_Price> specificPrice = queryBuilder
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

	@And("update ModCntr_Specific_Prices")
	public void updateSpecificPrices(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String specificPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Specific_Price_ID + "." + TABLECOLUMN_IDENTIFIER);
			final ModCntrSpecificPriceId contractPriceId = ModCntrSpecificPriceId.ofRepoId(specificPriceTable.get(specificPriceIdentifier).getModCntr_Specific_Price_ID());

			final boolean p_asNewPrice = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT.isNewPrice", false);
			final BigDecimal p_price = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_Price);

			final String currencyIsoCode = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_C_Currency_ID + ".ISO_Code");
			final CurrencyId p_C_Currency_ID = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode)).getId();

			final String uomCode = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final UomId p_C_UOM_ID = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));

			final BigDecimal p_minValue = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_MinValue);

			final ModCntrSpecificPrice newContractPrice;

			if (p_asNewPrice)
			{
				newContractPrice = modularContractPriceService.cloneById(contractPriceId, contractPrice -> contractPrice.toBuilder()
						.amount(Money.of(p_price, p_C_Currency_ID))
						.uomId(p_C_UOM_ID)
						.minValue(p_minValue)
						.build());
			}
			else
			{
				newContractPrice = modularContractPriceService.updateById(contractPriceId, contractPrice -> contractPrice.toBuilder()
						.amount(Money.of(p_price, p_C_Currency_ID))
						.uomId(p_C_UOM_ID)
						.minValue(p_minValue)
						.build());
			}

			contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
							.unitPrice(newContractPrice.getProductPrice())
							.flatrateTermId(newContractPrice.flatrateTermId())
							.modularContractModuleId(newContractPrice.modularContractModuleId())
							.build(),
					logHandlerRegistry);
		}
	}

	@And("delete ModCntr_Specific_Prices")
	public void deleteSpecificPrices(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String specificPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Specific_Price_ID + "." + TABLECOLUMN_IDENTIFIER);
			final ModCntrSpecificPriceId contractPriceId = ModCntrSpecificPriceId.ofRepoId(specificPriceTable.get(specificPriceIdentifier).getModCntr_Specific_Price_ID());

			final boolean p_asNewPrice = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT.isNewPrice", false);
			final BigDecimal p_price = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_Price);

			final String currencyIsoCode = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_C_Currency_ID + ".ISO_Code");
			final CurrencyId p_C_Currency_ID = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyIsoCode)).getId();

			final String uomCode = DataTableUtil.extractStringForColumnName(tableRow, I_ModCntr_Specific_Price.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final UomId p_C_UOM_ID = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));

			final BigDecimal p_minValue = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_ModCntr_Specific_Price.COLUMNNAME_MinValue);

			final ModCntrSpecificPrice newContractPrice;

			if (p_asNewPrice)
			{
				newContractPrice = modularContractPriceService.cloneById(contractPriceId, contractPrice -> contractPrice.toBuilder()
						.amount(Money.of(p_price, p_C_Currency_ID))
						.uomId(p_C_UOM_ID)
						.minValue(p_minValue)
						.build());
			}
			else
			{
				newContractPrice = modularContractPriceService.updateById(contractPriceId, contractPrice -> contractPrice.toBuilder()
						.amount(Money.of(p_price, p_C_Currency_ID))
						.uomId(p_C_UOM_ID)
						.minValue(p_minValue)
						.build());
			}

			contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
							.unitPrice(newContractPrice.getProductPrice())
							.flatrateTermId(newContractPrice.flatrateTermId())
							.modularContractModuleId(newContractPrice.modularContractModuleId())
							.build(),
					logHandlerRegistry);
		}
	}
}
