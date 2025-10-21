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

package de.metas.cucumber.stepdefs.payment;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.process.C_PaySelection_CreateFrom;
import de.metas.banking.process.C_PaySelection_CreatePayments;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.payment.PaymentId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class C_PaySelection_StepDef
{
	private final @NonNull C_PaySelection_StepDefData paySelectionTable;
	private final @NonNull C_BP_BankAccount_StepDefData bankAccountTable;
	private final @NonNull C_BPartner_StepDefData bpartnerTable;
	private final @NonNull IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final @NonNull IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final @NonNull IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@And("metasfresh contains Pay Selection")
	public void addPaySelections(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
				.forEach(this::addPaySelection);
	}

	private void addPaySelection(@NonNull final DataTableRow row)
	{
		final I_C_PaySelection paySelectionRecord = newInstance(I_C_PaySelection.class);

		final ValueAndName valueAndName = row.suggestValueAndName();
		paySelectionRecord.setName(valueAndName.getName());

		row.getAsOptionalIdentifier(I_C_PaySelection.COLUMNNAME_C_BP_BankAccount_ID)
				.map(bankAccountTable::getId)
				.ifPresent(id -> paySelectionRecord.setC_BP_BankAccount_ID(id.getRepoId()));

		row.getAsOptionalInstantTimestamp(I_C_PaySelection.COLUMNNAME_PayDate)
				.ifPresent(paySelectionRecord::setPayDate);

		row.getAsOptionalString(I_C_PaySelection.COLUMNNAME_PaySelectionTrxType)
				.ifPresent(paySelectionRecord::setPaySelectionTrxType);

		saveRecord(paySelectionRecord);

		paySelectionTable.putOrReplace(row.getAsIdentifier(), paySelectionRecord);
	}

	@And("\"Create from...\" is invoked with parameters:")
	public void createFrom_invoked(@NonNull final DataTable dataTable)
	{
		runPaySelectionProcess(DataTableRows.of(dataTable)
						.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
						.singleRow(),
				PaySelectionProcessMode.CREATE_FROM);
	}

	@Then("\"Create Payments\" is invoked")
	public void createPayments_invoked(@NonNull final DataTable dataTable)
	{
		runPaySelectionProcess(DataTableRows.of(dataTable)
						.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
						.singleRow(),
				PaySelectionProcessMode.CREATE_PAYMENTS);
	}

	private void runPaySelectionProcess(@NonNull final DataTableRow row, @NonNull final PaySelectionProcessMode processMode)
	{
		final I_C_PaySelection paySelectionRecord = paySelectionTable.get(row.getAsIdentifier());
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processMode.getProcessClass());

		final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setRecord(I_C_PaySelection.Table_Name, paySelectionRecord.getC_PaySelection_ID());

		if (processMode == PaySelectionProcessMode.CREATE_FROM)
		{
			final BPartnerId bpartnerId = row.getAsOptionalIdentifier(I_C_PaySelectionLine.COLUMNNAME_C_BPartner_ID)
					.map(bpartnerTable::getId)
					.orElseThrow(() -> new AdempiereException("No Partner found"));

			final String type = row.getAsString("MatchRequirement");

			processInfoBuilder
					.addParameter("MatchRequirement", type)
					.addParameter(I_C_PaySelectionLine.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId());
		}

		processInfoBuilder
				.buildAndPrepareExecution()
				.executeSync();
	}

	@AllArgsConstructor
	private enum PaySelectionProcessMode
	{
		CREATE_FROM(C_PaySelection_CreateFrom.class),
		CREATE_PAYMENTS(C_PaySelection_CreatePayments.class);

		@Getter
		private final Class<? extends JavaProcess> processClass;

	}

	@And("^the pay selection identified by (.*) is completed$")
	public void paysSelection_Complete(@NonNull final String paySelectionIdentifier)
	{
		final I_C_PaySelection paySelection = paySelectionTable.get(paySelectionIdentifier);
		paySelection.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(paySelection, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@And("^the Pay selection identified by (.*) has pay selection lines with payments$")
	public void verifyPaySelectionLines(@NonNull final String paySelectionIdentifier)
	{
		final PaySelectionId paySelectionId = paySelectionTable.getId(paySelectionIdentifier);

		final Set<PaymentId> actualIds = paySelectionBL.getPaymentIds(paySelectionId);

		assertThat(actualIds.size()).as("payments were created").isEqualTo(4); // TODO specify expected number of payments in the step

	}

}
