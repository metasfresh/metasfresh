package de.metas.invoice.acct.interceptor;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.acct.InvoiceAcctRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * On purchase invoice completion, materializes any per-line GL account overrides
 * ({@code C_ElementValue_Override_ID}) into {@code C_Invoice_Acct} rows,
 * one row per accounting schema × account concept (P_Expense_Acct, P_InventoryClearing_Acct).
 *
 * <p>Derives the {@code AccountConceptualName}s from the {@code I_M_Product_Acct} column names (the
 * same source {@code ProductAcctType.P_Expense_Acct/P_InventoryClearing_Acct.getAccountConceptualName()}
 * uses) — rename-safe, and avoids a compile dependency on {@code de.metas.acct.base}.</p>
 *
 * <p>Purchase-only (me03 30443): the override field is exposed only on purchase candidates/lines;
 * sales invoices are skipped via the {@code isSOTrx()} guard.</p>
 *
 * <p>Kept in its own class to separate this concern from the existing {@code C_Invoice} interceptor
 * (pattern: {@code de.metas.promotioncode.C_Invoice}).</p>
 */
@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice_AcctOverride
{
	// Mirrors ProductAcctType.P_Expense_Acct/P_InventoryClearing_Acct.getAccountConceptualName() from
	// de.metas.acct.base. We derive the conceptual names from the I_M_Product_Acct column names (the same
	// source ProductAcctType uses) to keep the binding to the product-acct columns explicit and rename-safe,
	// while avoiding a compile dependency on de.metas.acct.base.
	private static final AccountConceptualName CONCEPT_P_EXPENSE_ACCT = AccountConceptualName.ofString(I_M_Product_Acct.COLUMNNAME_P_Expense_Acct);
	private static final AccountConceptualName CONCEPT_P_INVENTORY_CLEARING_ACCT = AccountConceptualName.ofString(I_M_Product_Acct.COLUMNNAME_P_InventoryClearing_Acct);

	@NonNull private final InvoiceAcctRepository invoiceAcctRepository;

	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void materializeAcctOverrides(@NonNull final I_C_Invoice invoice)
	{
		// Purchase-only scope (me03 30443): the override field is exposed only on purchase candidates/lines.
		if (invoice.isSOTrx())
		{
			return;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		final ClientId clientId = ClientId.ofRepoId(invoice.getAD_Client_ID());
		final List<AcctSchema> acctSchemas = acctSchemaDAO.getAllByClient(clientId);

		for (final I_C_InvoiceLine line : invoiceBL.getLines(invoiceId))
		{
			final int overrideElementValueRepoId = line.getC_ElementValue_Override_ID();
			if (overrideElementValueRepoId <= 0)
			{
				continue;
			}
			final ElementValueId overrideElementValueId = ElementValueId.ofRepoId(overrideElementValueRepoId);
			final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoId(invoiceId, line.getC_InvoiceLine_ID());

			for (final AcctSchema acctSchema : acctSchemas)
			{
				final AcctSchemaId acctSchemaId = acctSchema.getId();
				invoiceAcctRepository.createOrUpdateLineOverride(
						invoiceAndLineId,
						orgId,
						acctSchemaId,
						CONCEPT_P_EXPENSE_ACCT,
						overrideElementValueId);
				invoiceAcctRepository.createOrUpdateLineOverride(
						invoiceAndLineId,
						orgId,
						acctSchemaId,
						CONCEPT_P_INVENTORY_CLEARING_ACCT,
						overrideElementValueId);
			}
		}
	}
}
