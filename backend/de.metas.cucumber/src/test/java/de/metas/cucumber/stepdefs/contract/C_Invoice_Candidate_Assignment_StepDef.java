/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoicecandidate.C_Invoice_Candidate_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_DocType;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Responsible for the refund invoice candidates of refund terms and for their {@link I_C_Invoice_Candidate_Assignment}s
 * to the invoice candidates the refund is based on.
 */
@RequiredArgsConstructor
public class C_Invoice_Candidate_Assignment_StepDef
{
	private static final long POLL_INTERVAL_MS = 500;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	@NonNull private final C_Flatrate_Term_StepDefData contractTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_Tax_StepDefData taxTable;

	/**
	 * Waits for the refund invoice candidate of a refund term and checks its values.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_Candidate_ID</b> — (required) alias the found refund invoice candidate is registered under<br>
	 *   <b>C_Flatrate_Term_ID</b> — (required, identifier-ref) the refund term<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) selects the refund invoice candidate booked on this product<br>
	 *   <b>PriceActual</b> — (optional) refund amount<br>
	 *   <b>NetAmtToInvoice</b> — (optional) refund amount to invoice<br>
	 *   <b>DateToInvoice</b> — (optional, yyyy-MM-dd) date the refund can be invoiced from<br>
	 *   <b>C_Tax_ID</b> — (optional, identifier-ref) expected tax of the refund<br>
	 *   <b>Bill_BPartner_ID</b> — (optional, identifier-ref) the partner the refund is issued to<br>
	 *   <b>DocBaseType</b>, <b>DocSubType</b> — (optional) type of the document the refund is invoiced with<br>
	 * @cucumber.depends StepDefData: C_Flatrate_Term_StepDefData, M_Product_StepDefData, C_BPartner_StepDefData, C_Invoice_Candidate_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, refund C_Invoice_Candidates are found:
	 *   | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | NetAmtToInvoice | DateToInvoice | DocBaseType | DocSubType |
	 *   | refundIC               | refundTerm         | 200             | 2026-07-31    | ARC         | RI         |
	 * </pre>
	 */
	@And("^after not more than (.*)s, refund C_Invoice_Candidates are found:$")
	public void refund_C_Invoice_Candidates_are_found(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> waitForRefundInvoiceCandidate(timeoutSec, row));
	}

	/**
	 * Asserts that a refund term has no refund invoice candidate (e.g. because no sale is in its base).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the C_Flatrate_Term identified by refundTerm has no refund C_Invoice_Candidate
	 * </pre>
	 */
	@And("^the C_Flatrate_Term identified by (.*) has no refund C_Invoice_Candidate$")
	public void C_Flatrate_Term_has_no_refund_C_Invoice_Candidate(@NonNull final String termIdentifier)
	{
		final I_C_Flatrate_Term term = contractTable.get(termIdentifier);
		final int count = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, term.getC_Flatrate_Term_ID())
				.create()
				.count();
		assertThat(count).as("refund invoice candidates of C_Flatrate_Term_ID=%s", term.getC_Flatrate_Term_ID()).isZero();
	}

	private void waitForRefundInvoiceCandidate(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Flatrate_Term term = row.getAsIdentifier(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Flatrate_Term_ID).lookupNotNullIn(contractTable);

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, term.getC_Flatrate_Term_ID());
		row.getAsOptionalIdentifier(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID)
				.ifPresent(productIdentifier -> queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, productTable.getId(productIdentifier)));

		final StringBuilder lastMismatch = new StringBuilder();
		try
		{
			StepDefUtil.tryAndWait(timeoutSec, POLL_INTERVAL_MS, () -> {
				lastMismatch.setLength(0);
				final List<I_C_Invoice_Candidate> candidates = queryBuilder.create().list();
				if (candidates.size() != 1)
				{
					lastMismatch.append("expected exactly 1 refund invoice candidate of C_Flatrate_Term_ID=").append(term.getC_Flatrate_Term_ID()).append(" but found ").append(candidates.size());
					return false;
				}
				final I_C_Invoice_Candidate candidate = candidates.get(0);
				lastMismatch.append(findMismatch(row, candidate));
				if (lastMismatch.length() > 0)
				{
					return false;
				}
				invoiceCandTable.putOrReplace(row.getAsIdentifier(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID), candidate);
				return true;
			}, null);
		}
		catch (final AssertionError e)
		{
			throw new AssertionError("Refund invoice candidate does not match: " + lastMismatch, e);
		}
	}

	/** @return empty string if the candidate matches the row's expectations */
	@NonNull
	private String findMismatch(@NonNull final DataTableRow row, @NonNull final I_C_Invoice_Candidate candidate)
	{
		final StringBuilder mismatch = new StringBuilder();

		row.getAsOptionalBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_PriceActual)
				.filter(expected -> candidate.getPriceActual().compareTo(expected) != 0)
				.ifPresent(expected -> mismatch.append("PriceActual expected=").append(expected).append(" actual=").append(candidate.getPriceActual()).append("; "));

		row.getAsOptionalBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice)
				.filter(expected -> candidate.getNetAmtToInvoice().compareTo(expected) != 0)
				.ifPresent(expected -> mismatch.append("NetAmtToInvoice expected=").append(expected).append(" actual=").append(candidate.getNetAmtToInvoice()).append("; "));

		row.getAsOptionalLocalDate(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice)
				.filter(expected -> !expected.equals(TimeUtil.asLocalDate(candidate.getDateToInvoice())))
				.ifPresent(expected -> mismatch.append("DateToInvoice expected=").append(expected).append(" actual=").append(candidate.getDateToInvoice()).append("; "));

		row.getAsOptionalIdentifier(I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID)
				.map(identifier -> taxTable.getId(identifier).getRepoId())
				.filter(expectedTaxId -> expectedTaxId != candidate.getC_Tax_ID())
				.ifPresent(expectedTaxId -> mismatch.append("C_Tax_ID expected=").append(expectedTaxId).append(" actual=").append(candidate.getC_Tax_ID()).append("; "));

		row.getAsOptionalIdentifier(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
				.map(identifier -> bpartnerTable.getId(identifier).getRepoId())
				.filter(expectedBPartnerId -> expectedBPartnerId != candidate.getBill_BPartner_ID())
				.ifPresent(expectedBPartnerId -> mismatch.append("Bill_BPartner_ID expected=").append(expectedBPartnerId).append(" actual=").append(candidate.getBill_BPartner_ID()).append("; "));

		final String expectedDocBaseType = row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocBaseType).orElse(null);
		final String expectedDocSubType = row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocSubType).orElse(null);
		if (expectedDocBaseType != null || expectedDocSubType != null)
		{
			final I_C_DocType docType = candidate.getC_DocTypeInvoice_ID() > 0
					? docTypeDAO.getById(DocTypeId.ofRepoId(candidate.getC_DocTypeInvoice_ID()))
					: null;
			if (docType == null)
			{
				mismatch.append("no C_DocTypeInvoice_ID; ");
			}
			else
			{
				if (expectedDocBaseType != null && !expectedDocBaseType.equals(docType.getDocBaseType()))
				{
					mismatch.append("DocBaseType expected=").append(expectedDocBaseType).append(" actual=").append(docType.getDocBaseType()).append("; ");
				}
				if (expectedDocSubType != null && !expectedDocSubType.equals(docType.getDocSubType()))
				{
					mismatch.append("DocSubType expected=").append(expectedDocSubType).append(" actual=").append(docType.getDocSubType()).append("; ");
				}
			}
		}

		return mismatch.toString();
	}

	/**
	 * Waits for the assignment of an invoice candidate to a refund invoice candidate and checks its values.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_Candidate_Term_ID</b> — (required, identifier-ref) the refund invoice candidate<br>
	 *   <b>C_Invoice_Candidate_Assigned_ID</b> — (required, identifier-ref) the invoice candidate the refund is based on<br>
	 *   <b>C_Flatrate_Term_ID</b> — (optional, identifier-ref) expected refund term<br>
	 *   <b>AssignedMoneyAmount</b> — (optional) expected refund amount of this assignment<br>
	 *   <b>AssignedQuantity</b> — (optional) expected assigned quantity<br>
	 * @cucumber.depends StepDefData: C_Invoice_Candidate_StepDefData, C_Flatrate_Term_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, C_Invoice_Candidate_Assignments are found:
	 *   | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | AssignedMoneyAmount |
	 *   | refundIC                    | salesIC                         | 200                 |
	 * </pre>
	 */
	@And("^after not more than (.*)s, C_Invoice_Candidate_Assignments are found:$")
	public void C_Invoice_Candidate_Assignments_are_found(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> waitForAssignment(timeoutSec, row));
	}

	private void waitForAssignment(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Invoice_Candidate refundCandidate = row.getAsIdentifier(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Invoice_Candidate_Term_ID).lookupNotNullIn(invoiceCandTable);
		final I_C_Invoice_Candidate assignedCandidate = row.getAsIdentifier(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Invoice_Candidate_Assigned_ID).lookupNotNullIn(invoiceCandTable);

		final StringBuilder lastMismatch = new StringBuilder();
		try
		{
			StepDefUtil.tryAndWait(timeoutSec, POLL_INTERVAL_MS, () -> {
				lastMismatch.setLength(0);
				final List<I_C_Invoice_Candidate_Assignment> assignments = queryBL.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Invoice_Candidate_Term_ID, refundCandidate.getC_Invoice_Candidate_ID())
						.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Invoice_Candidate_Assigned_ID, assignedCandidate.getC_Invoice_Candidate_ID())
						.create()
						.list();
				if (assignments.size() != 1)
				{
					lastMismatch.append("expected exactly 1 assignment but found ").append(assignments.size());
					return false;
				}
				final I_C_Invoice_Candidate_Assignment assignment = assignments.get(0);

				row.getAsOptionalIdentifier(I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Flatrate_Term_ID)
						.map(identifier -> identifier.lookupNotNullIn(contractTable))
						.filter(term -> term.getC_Flatrate_Term_ID() != assignment.getC_Flatrate_Term_ID())
						.ifPresent(term -> lastMismatch.append("C_Flatrate_Term_ID expected=").append(term.getC_Flatrate_Term_ID()).append(" actual=").append(assignment.getC_Flatrate_Term_ID()).append("; "));
				appendIfDifferent(lastMismatch, "AssignedMoneyAmount", row.getAsOptionalBigDecimal(I_C_Invoice_Candidate_Assignment.COLUMNNAME_AssignedMoneyAmount).orElse(null), assignment.getAssignedMoneyAmount());
				appendIfDifferent(lastMismatch, "AssignedQuantity", row.getAsOptionalBigDecimal(I_C_Invoice_Candidate_Assignment.COLUMNNAME_AssignedQuantity).orElse(null), assignment.getAssignedQuantity());
				return lastMismatch.length() == 0;
			}, null);
		}
		catch (final AssertionError e)
		{
			throw new AssertionError("Assignment does not match: " + lastMismatch, e);
		}
	}

	private static void appendIfDifferent(@NonNull final StringBuilder mismatch, @NonNull final String name, final BigDecimal expected, final BigDecimal actual)
	{
		if (expected != null && (actual == null || actual.compareTo(expected) != 0))
		{
			mismatch.append(name).append(" expected=").append(expected).append(" actual=").append(actual).append("; ");
		}
	}
}
