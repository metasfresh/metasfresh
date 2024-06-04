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

package de.metas.cucumber.stepdefs.finalinvoice;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.finalinvoice.workpackage.ModularContractInvoiceEnqueuer;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.process.params.InvoicingParams.PARA_DateAcct;
import static de.metas.invoicecandidate.process.params.InvoicingParams.PARA_DateInvoiced;
import static de.metas.invoicecandidate.process.params.InvoicingParams.PARA_IgnoreInvoiceSchedule;
import static de.metas.invoicecandidate.process.params.InvoicingParams.PARA_IsCompleteInvoices;
import static de.metas.invoicecandidate.process.params.InvoicingParams.PARA_OverrideDueDate;

public class C_ModularFinalInvoice_StepDef
{
	private final ModularContractInvoiceEnqueuer finalInvoiceEnqueuer = SpringContextHolder.instance.getBean(ModularContractInvoiceEnqueuer.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private final C_Flatrate_Term_StepDefData contractTable;

	public C_ModularFinalInvoice_StepDef(@NonNull final C_Flatrate_Term_StepDefData contractTable)
	{
		this.contractTable = contractTable;
	}

	@And("create final invoice")
	public void create_finalInvoice(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			createFinalInvoice(row);
		}
	}

	private void createFinalInvoice(@NonNull final Map<String, String> row)
	{
		final String contractIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Flatrate_Term contractRecord = contractTable.get(contractIdentifier);

		final String userLogin = DataTableUtil.extractStringForColumnName(row, "UserLogin");

		final UserId userId = Optional.ofNullable(userDAO.retrieveUserIdByLogin(userLogin))
				.orElseThrow(() -> new AdempiereException("No User found for UserLogin !")
						.appendParametersToMessage()
						.setParameter("UserLogin", userLogin));

		finalInvoiceEnqueuer.enqueueFinalInvoice(ImmutableSet.of(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID())),
										userId,
										createDefaultIInvoicingParams(row));
	}

	@NonNull
	private InvoicingParams createDefaultIInvoicingParams(@NonNull final Map<String, String> row)
	{
		final boolean ignoreInvoiceSchedule = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + PARA_IgnoreInvoiceSchedule, true);
		final boolean completeInvoices = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + PARA_IsCompleteInvoices, true);
		final LocalDate dateInvoiced = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + PARA_DateInvoiced);
		final LocalDate dateAcct = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + PARA_DateAcct);
		final LocalDate overrideDueDate = DataTableUtil.extractLocalDateOrNullForColumnName(row, "OPT." + PARA_OverrideDueDate);

		return InvoicingParams.builder()
				.ignoreInvoiceSchedule(ignoreInvoiceSchedule)
				.dateInvoiced(dateInvoiced)
				.completeInvoices(completeInvoices)
				.dateAcct(dateAcct)
				.overrideDueDate(overrideDueDate)
				.build();
	}
}
