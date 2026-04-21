package de.metas.cucumber.stepdefs.tax_declaration;

import de.metas.acct.vatcode.VATCode;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Supplier;

@Builder
class TaxReportRowMatcher
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;
	@NonNull private final IdentifiersResolver identifiersResolver;

	@NonNull final TaxInfo taxInfo;

	@Nullable
	public TaxReportRow findAndRemoveMatch(@NonNull final DataTableRow expected, @NonNull final ArrayList<TaxReportRow> candidates)
	{
		for (int i = 0; i < candidates.size(); i++)
		{
			if (matches(expected, candidates.get(i)))
			{
				return candidates.remove(i);
			}
		}
		return null;
	}

	/**
	 * @return true if every non-blank column in {@code expected} equals the corresponding field on {@code actual}. Blank cells are "don't care".
	 */
	private boolean matches(
			@NonNull final DataTableRow expected,
			@NonNull final TaxReportRow actual)
	{
		return stringMatches(expected, "Level", actual.getLevel())
				&& vatCodeMatches(expected, actual)
				&& accountConceptualNameMatches(expected, actual, taxInfo)
				&& stringMatches(expected, "TaxName", actual.getTaxName())
				&& documentNoMatches(expected, actual)
				&& bpartnerMatches(expected, actual)
				&& amountMatches(expected, "TaxAmt", actual.getTaxAmt())
				&& amountMatches(expected, "NetAmt", actual.getNetAmt())
				&& amountMatches(expected, "TaxAmt_SUM", actual.getTaxAmtSum())
				&& amountMatches(expected, "NetAmt_SUM", actual.getNetAmtSum());
	}

	private static boolean stringMatches(@NonNull final DataTableRow expected, @NonNull final String column, @Nullable final String actual)
	{
		return expected.getAsOptionalString(column)
				.map(exp -> exp.equals(actual))
				.orElse(true);
	}

	private static boolean amountMatches(@NonNull final DataTableRow expected, @NonNull final String column, @Nullable final Amount actual)
	{
		final Supplier<CurrencyCode> defaultCurrency = actual != null ? actual::getCurrencyCode : () -> null;
		return expected.getAsOptionalAmount(column, defaultCurrency)
				.map(exp -> exp.equals(actual))
				.orElse(true);
	}

	/**
	 * Resolves the expected {@code DocumentNo} identifier (of a C_Invoice or C_AllocationHdr) to the
	 * document's {@code DocumentNo} and compares to the actual row's value. If the column is absent,
	 * matching is skipped; if the cell holds the null placeholder ({@code -} or {@code null}), the
	 * actual DocumentNo must be null. The document is loaded only on demand.
	 */
	private boolean documentNoMatches(@NonNull final DataTableRow expected, @NonNull final TaxReportRow actual)
	{
		final StepDefDataIdentifier identifier = expected.getAsOptionalIdentifier("DocumentNo").orElse(null);
		if (identifier == null)
		{
			return true;
		}
		if (identifier.isNullPlaceholder())
		{
			return actual.getDocumentNo() == null;
		}
		final String expectedDocumentNo = resolveExpectedDocumentNo(identifier);
		return expectedDocumentNo.equals(actual.getDocumentNo());
	}

	/**
	 * Resolves an Invoice or AllocationHdr identifier to its {@code DocumentNo} by loading the
	 * record on demand. The function's {@code DocumentNo} column is populated from those two tables
	 * only (see {@code de_metas_acct.tax_accounts_details_v}), so any other table is a test bug.
	 */
	private String resolveExpectedDocumentNo(@NonNull final StepDefDataIdentifier identifier)
	{
		final TableRecordReference ref = identifiersResolver.getTableRecordReference(identifier);
		switch (ref.getTableName())
		{
			case I_C_Invoice.Table_Name:
				return invoiceBL.getById(InvoiceId.ofRepoId(ref.getRecord_ID())).getDocumentNo();
			case I_C_AllocationHdr.Table_Name:
				return allocationDAO.getById(PaymentAllocationId.ofRepoId(ref.getRecord_ID())).getDocumentNo();
			default:
				throw new AdempiereException("DocumentNo in tax report expectations must reference C_Invoice or C_AllocationHdr, got: " + ref);
		}
	}

	/**
	 * Resolves the expected {@code C_VAT_Code_ID} identifier to its {@link VATCode#getCode()} via
	 * {@link C_VAT_Code_StepDefData} and compares to the actual row's {@code vatCode}. If the column is
	 * absent, matching is skipped; if the cell holds the null placeholder ({@code -} or {@code null}),
	 * the actual {@code vatCode} must be null.
	 */
	private boolean vatCodeMatches(@NonNull final DataTableRow expected, @NonNull final TaxReportRow actual)
	{
		final StepDefDataIdentifier identifier = expected.getAsOptionalIdentifier("C_VAT_Code_ID").orElse(null);
		if (identifier == null)
		{
			return true;
		}
		if (identifier.isNullPlaceholder())
		{
			return actual.getVatCode() == null;
		}
		final VATCode expectedVatCode = vatCodeTable.get(identifier);
		return expectedVatCode.getCode().equals(actual.getVatCode());
	}

	/**
	 * Resolves the expected {@code C_BPartner_ID} identifier to the BPartner's name (via
	 * {@link IBPartnerDAO#getBPartnerNameById(BPartnerId)}) and compares to the actual row's
	 * {@code bpartnerName}. If the column is absent, matching is skipped; if the cell holds the
	 * null placeholder ({@code -} or {@code null}), the actual name must be null.
	 */
	private boolean bpartnerMatches(@NonNull final DataTableRow expected, @NonNull final TaxReportRow actual)
	{
		final StepDefDataIdentifier identifier = expected.getAsOptionalIdentifier("C_BPartner_ID").orElse(null);
		if (identifier == null)
		{
			return true;
		}
		if (identifier.isNullPlaceholder())
		{
			return actual.getBpartnerName() == null;
		}
		final BPartnerId bpartnerId = bpartnerTable.getId(identifier);
		final String expectedName = bpartnerDAO.getBPartnerNameById(bpartnerId);
		return expectedName.equals(actual.getBpartnerName());
	}

	/**
	 * Resolves the expected {@code AccountConceptualName} ({@code T_Due_Acct} / {@code T_Credit_Acct})
	 * to the raw {@code "<value> <name>"} label the DB function emits in its {@code AccountName}
	 * column, via {@link TaxInfo}, and compares to the actual row's account name.
	 */
	private static boolean accountConceptualNameMatches(
			@NonNull final DataTableRow expected,
			@NonNull final TaxReportRow actual,
			@NonNull final TaxInfo taxInfo)
	{
		return expected.getAsOptionalString("AccountConceptualName")
				.map(conceptualName -> {
					switch (conceptualName)
					{
						case "T_Due_Acct":
							return taxInfo.getTaxDueAccountName().equals(actual.getAccountName());
						case "T_Credit_Acct":
							return taxInfo.getTaxCreditAccountName().equals(actual.getAccountName());
						default:
							throw new AdempiereException("Unsupported AccountConceptualName in expected table: " + conceptualName);
					}
				})
				.orElse(true);
	}

}
