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

package de.metas.cucumber.stepdefs.interiminvoice.invoice;

import de.metas.contracts.model.I_C_BPartner_InterimContract;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractId;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceCandidateService;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.contract.interim.C_BPartner_InterimContract_StepDefData;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_BPartner_InterimContract_GenerateInterimInvoice_StepDef
{
	private final InterimInvoiceCandidateService interimInvoiceCandidateService = SpringContextHolder.instance.getBean(InterimInvoiceCandidateService.class);
	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);

	private final C_BPartner_InterimContract_StepDefData partnerInterimTable;
	private final C_Flatrate_Term_StepDefData contractTable;
	private final C_Invoice_Candidate_StepDefData invoiceCandTable;

	public C_BPartner_InterimContract_GenerateInterimInvoice_StepDef(
			@NonNull final C_BPartner_InterimContract_StepDefData partnerInterimTable,
			@NonNull final C_Flatrate_Term_StepDefData contractTable,
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable)
	{
		this.partnerInterimTable = partnerInterimTable;
		this.contractTable = contractTable;
		this.invoiceCandTable = invoiceCandTable;
	}

	@And("invoke \"createInterimInvoiceCandidatesFor\" function:")
	public void invokeCreateInterimInvoiceCandidatesForFunction(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String flatrateTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Term flatrateTermRecord = contractTable.get(flatrateTermIdentifier);

			final Set<InvoiceCandidateId> generatedICIds = interimInvoiceCandidateService.createInterimInvoiceCandidatesFor(flatrateTermRecord);
			associateGeneratedIds(generatedICIds, row);
		}
	}

	private void associateGeneratedIds(@NonNull final Set<InvoiceCandidateId> invoiceCandidateIds, @NonNull final Map<String, String> row)
	{
		final String icsIdentifiersString = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isBlank(icsIdentifiersString))
		{
			return;
		}

		final List<String> icIdentifiers = StepDefUtil.splitIdentifiers(icsIdentifiersString);
		assertThat(icIdentifiers.size()).isEqualTo(invoiceCandidateIds.size());

		for (int index = 0; index < invoiceCandidateIds.size(); index++)
		{
			final InvoiceCandidateId invoiceCandidateId = invoiceCandidateIds.iterator().next();
			final String icIdentifier = icIdentifiers.get(index);

			final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.load(invoiceCandidateId, I_C_Invoice_Candidate.class);
			invoiceCandTable.putOrReplace(icIdentifier, invoiceCandidate);
		}
	}
}
