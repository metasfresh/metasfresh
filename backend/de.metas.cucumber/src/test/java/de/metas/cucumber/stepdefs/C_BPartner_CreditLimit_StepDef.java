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

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.MANAGEMENT_CREDIT_LIMIT_TYPE_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;

public class C_BPartner_CreditLimit_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_CreditLimit_StepDefData creditLimitTable;

	public C_BPartner_CreditLimit_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_CreditLimit_StepDefData creditLimitTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.creditLimitTable = creditLimitTable;
	}

	@And("metasfresh contains C_BPartner_CreditLimit:")
	public void metasfresh_contains_c_bPartner_creditLimit(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createCBPartnerCreditLimit(tableRow);
		}
	}

	@And("update C_BPartner_CreditLimits")
	public void update_creditLimit(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			updateCreditLimit(tableRow);
		}
	}

	private void updateCreditLimit(@NonNull final Map<String, String> tableRow)
	{
		final String creditLimitIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_CreditLimit_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_CreditLimit creditLimitRecord = creditLimitTable.get(creditLimitIdentifier);

		final boolean processed = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_Processed);

		creditLimitRecord.setProcessed(processed);

		saveRecord(creditLimitRecord);
	}

	private void createCBPartnerCreditLimit(@NonNull final Map<String, String> tableRow)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bpartner = bPartnerTable.get(bpartnerIdentifier);

		final BigDecimal amount = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_Amount);

		final int approvedById = DataTableUtil.extractIntForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_ApprovedBy_ID);
		final boolean processed = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_Processed);

		final I_C_BPartner_CreditLimit creditLimitRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_CreditLimit.class);

		creditLimitRecord.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		creditLimitRecord.setAmount(amount);
		creditLimitRecord.setC_CreditLimit_Type_ID(MANAGEMENT_CREDIT_LIMIT_TYPE_ID);
		creditLimitRecord.setProcessed(processed);
		creditLimitRecord.setApprovedBy_ID(approvedById);

		saveRecord(creditLimitRecord);

		final String creditLimitIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_CreditLimit_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		creditLimitTable.put(creditLimitIdentifier, creditLimitRecord);
	}
}