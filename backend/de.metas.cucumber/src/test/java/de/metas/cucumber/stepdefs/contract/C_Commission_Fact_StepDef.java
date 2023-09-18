/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_Commission_Fact.COLUMNNAME_C_Commission_Share_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Fact.COLUMNNAME_C_Invoice_Candidate_Commission_ID;
import static de.metas.contracts.commission.model.I_C_Commission_Fact.COLUMNNAME_CommissionPoints;
import static de.metas.contracts.commission.model.I_C_Commission_Fact.COLUMNNAME_Commission_Fact_State;
import static de.metas.contracts.commission.model.I_C_Commission_Fact.COLUMNNAME_Updated;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Commission_Fact_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_Commission_Share_StepDefData commissionShareTable;

	public C_Commission_Fact_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_Commission_Share_StepDefData commissionShareTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.commissionShareTable = commissionShareTable;
	}

	@And("^validate commission fact for (.*)$")
	public void validate_commission_fact(@NonNull final String commissionShareIdentifier, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		final I_C_Commission_Share commissionShare = commissionShareTable.get(commissionShareIdentifier);
		assertThat(commissionShare).isNotNull();

		final List<I_C_Commission_Fact> commissionFacts = queryBL.createQueryBuilder(I_C_Commission_Fact.class)
				.addEqualsFilter(COLUMNNAME_C_Commission_Share_ID, commissionShare.getC_Commission_Share_ID())
				.orderByDescending(I_C_Commission_Fact.COLUMNNAME_C_Commission_Fact_ID)
				.create()
				.list();

		assertThat(commissionFacts).isNotNull();
		assertThat(tableRows.size()).isEqualTo(commissionFacts.size());

		for (int factIndex = 0; factIndex < commissionFacts.size(); factIndex++)
		{
			final I_C_Commission_Fact actualCommissionFact = commissionFacts.get(factIndex);
			final Map<String, String> expectedCommissionFact = tableRows.get(factIndex);

			final String commissionFactState = DataTableUtil.extractStringForColumnName(expectedCommissionFact, COLUMNNAME_Commission_Fact_State);
			assertThat(actualCommissionFact.getCommission_Fact_State()).isEqualTo(commissionFactState);

			final BigDecimal commissionPoints = DataTableUtil.extractBigDecimalForColumnName(expectedCommissionFact, COLUMNNAME_CommissionPoints);
			assertThat(actualCommissionFact.getCommissionPoints()).isEqualTo(commissionPoints);

			final String settlementIdentifier = DataTableUtil.extractStringOrNullForColumnName(expectedCommissionFact, "OPT." + COLUMNNAME_C_Invoice_Candidate_Commission_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(settlementIdentifier))
			{
				assertThat(actualCommissionFact.getC_Invoice_Candidate_Commission_ID()).isNotNull();

				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(actualCommissionFact.getC_Invoice_Candidate_Commission_ID());
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandDAO.getByIdOutOfTrx(invoiceCandidateId);

				invoiceCandTable.putOrReplace(settlementIdentifier, invoiceCandidate);
			}
		}
	}
}
