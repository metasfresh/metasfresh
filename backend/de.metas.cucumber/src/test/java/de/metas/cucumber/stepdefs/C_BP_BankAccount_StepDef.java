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

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Currency;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;

public class C_BP_BankAccount_StepDef
{
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final CurrencyRepository currencyRepository;

	public C_BP_BankAccount_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable
	)
	{
		this.bpartnerTable = bpartnerTable;
		this.currencyRepository = currencyRepository;
		this.bpBankAccountTable = bpBankAccountTable;
	}

	@And("metasfresh contains C_BP_BankAccount")
	public void addC_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			create_C_BP_BankAccount(dataTableRow);
		}
	}

	private void create_C_BP_BankAccount(@NonNull final Map<String, String> row)
	{

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final String isoCode = DataTableUtil.extractStringForColumnName(row, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode));

		final I_C_BP_BankAccount bpBankAccount = newInstance(I_C_BP_BankAccount.class);

		bpBankAccount.setC_BPartner_ID(bPartnerId);
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());

		InterfaceWrapperHelper.save(bpBankAccount);

		final String bankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
		bpBankAccountTable.putOrReplace(bankAccountIdentifier, bpBankAccount);
	}
}
