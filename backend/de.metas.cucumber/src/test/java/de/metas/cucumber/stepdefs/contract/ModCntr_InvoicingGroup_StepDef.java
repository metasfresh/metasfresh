/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;

@RequiredArgsConstructor
public class ModCntr_InvoicingGroup_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final ModCntr_InvoicingGroup_StepDefData modCntrInvoicingGroupTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final AD_Message_StepDefData messageTable;
	@NonNull private final C_Year_StepDefData yearTable;

	@Given("metasfresh contains ModCntr_InvoicingGroup:")
	public void metasfresh_contains_ModCntr_InvoicingGroup(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			createModCntrInvoicingGroup(row);
		}
	}

	@And("the ModCntr_InvoicingGroup is updated expecting error:")
	public void update_ModCntr_InvoicingGroup_expectingError(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			boolean errorThrown = false;

			try
			{
				updateModCntrInvoicingGroup(row);
				assertThat(1).as("An Exception should have been thrown !").isEqualTo(2);
			}
			catch (final AdempiereException e)
			{
				errorThrown = true;

				final String errorMessageIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Message_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(errorMessageIdentifier))
				{
					final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);
					assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
				}
			}

			assertThat(errorThrown).isTrue();
		}
	}

	private void createModCntrInvoicingGroup(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Group_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Optional<ProductId> groupProductId = Optional.ofNullable(productIdentifier)
				.map(productTable::get)
				.map(I_M_Product::getM_Product_ID)
				.map(ProductId::ofRepoId);

		final String name = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Name);
		final String yearIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Year year = yearTable.get(yearIdentifier);

		final I_ModCntr_InvoicingGroup modCntrInvoicingGroupRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup.class)
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Group_Product_ID, groupProductId.orElse(null))
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Name, name)
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_C_Harvesting_Calendar_ID, year.getC_Calendar_ID())
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID, year.getC_Year_ID())
						.create()
						.firstOnlyOrNull(I_ModCntr_InvoicingGroup.class),
				() -> InterfaceWrapperHelper.newInstance(I_ModCntr_InvoicingGroup.class));
		groupProductId.ifPresent(productId -> modCntrInvoicingGroupRecord.setGroup_Product_ID(productId.getRepoId()));
		modCntrInvoicingGroupRecord.setName(name);
		modCntrInvoicingGroupRecord.setC_Harvesting_Calendar_ID(year.getC_Calendar_ID());
		modCntrInvoicingGroupRecord.setHarvesting_Year_ID(year.getC_Year_ID());
		final BigDecimal totalInterest = CoalesceUtil.coalesceNotNull(DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_TotalInterest), BigDecimal.ZERO);
		modCntrInvoicingGroupRecord.setTotalInterest(totalInterest);
		final String isoCode = DataTableUtil.extractStringForColumnName(row, "C_Currency.ISO_Code");
		modCntrInvoicingGroupRecord.setC_Currency_ID(getCurrencyIdByCurrencyISO(isoCode).getRepoId());

		InterfaceWrapperHelper.saveRecord(modCntrInvoicingGroupRecord);

		final String modCntrInvoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrInvoicingGroupTable.putOrReplace(modCntrInvoicingGroupIdentifier, modCntrInvoicingGroupRecord);
	}

	@NonNull
	private CurrencyId getCurrencyIdByCurrencyISO(@NonNull final String currencyISO)
	{
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(currencyISO);
		return currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCode);
	}

	private void updateModCntrInvoicingGroup(@NonNull final Map<String, String> row)
	{
		final String invoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_InvoicingGroup invoicingGroup = modCntrInvoicingGroupTable.get(invoicingGroupIdentifier);

		final String yearIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Year year = yearTable.get(yearIdentifier);
		invoicingGroup.setHarvesting_Year_ID(year.getC_Year_ID());

		InterfaceWrapperHelper.saveRecord(invoicingGroup);

		modCntrInvoicingGroupTable.putOrReplace(invoicingGroupIdentifier, invoicingGroup);
	}
}
