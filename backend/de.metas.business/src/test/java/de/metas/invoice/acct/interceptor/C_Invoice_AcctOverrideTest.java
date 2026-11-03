package de.metas.invoice.acct.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.AcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.acct.InvoiceAcctRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Discriminating unit test for {@link C_Invoice_AcctOverride}.
 *
 * <p>Q2a fix: on purchase-invoice completion the per-line GL override must be materialized into the
 * accounting schema resolved from the invoice line's OWN org
 * ({@link IAcctSchemaDAO#getByClientAndOrgOrNull(org.adempiere.service.ClientId, OrgId)}), NOT spread
 * across every client schema (the old {@code getAllByClient(clientId)} behaviour).</p>
 *
 * <p>The scenario gives the client TWO accounting schemas (A and B) and a single purchase invoice line
 * in org A. The test asserts the interceptor materializes ONLY into schema A (org-resolved) and NEVER
 * into schema B — exactly {@code 2} repository writes (one per account concept), both targeting schema A.</p>
 *
 * <p>This is the discrimination the cucumber scenario {@code @Id:S30443_TC5} cannot provide: cucumber runs
 * against the standard seed whose client has a single accounting schema, so both the old and the new code
 * materialize into the same one row and the scenario passes either way. A genuine two-schema cucumber setup
 * is infeasible/unsafe: there is no step-def to create a {@code C_AcctSchema} (it needs currency, GL,
 * default-account and element children), and a second client schema is GLOBAL seed state that
 * {@code PostingService.getAllByClient} would post every document to — polluting and breaking sibling
 * accounting scenarios on the shared executor. Hence this interceptor-level unit test, per the reviewer's
 * pre-approved faithful alternative.</p>
 *
 * <p>RED-on-revert: reverting the interceptor to {@code getAllByClient(clientId)} makes it write for BOTH
 * schemas ({@code 4} calls: 2 concepts × 2 schemas), so {@code verify(times(2))} / {@code containsOnly(A)}
 * fail. GREEN-on-fix: the org-resolved lookup writes only the 2 schema-A rows.</p>
 */
class C_Invoice_AcctOverrideTest
{
	private static final int CLIENT_ID = 1_000_000;
	private static final OrgId ORG_A = OrgId.ofRepoId(1_000_001);
	private static final ElementValueId OVERRIDE_ACCOUNT = ElementValueId.ofRepoId(555);

	private IInvoiceBL invoiceBL;
	private InvoiceAcctRepository invoiceAcctRepository;

	private AcctSchemaId schemaIdA;
	private AcctSchemaId schemaIdB;

	private C_Invoice_AcctOverride interceptor;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// Two real accounting schemas for the client. Build the AcctSchema value objects via the real DAO
		// from the (minimal) records AcctSchemaTestHelper creates, then hand them to a mocked IAcctSchemaDAO
		// so the test controls org-resolution independently of the seed.
		schemaIdA = AcctSchemaTestHelper.newAcctSchema().build();
		schemaIdB = AcctSchemaTestHelper.newAcctSchema().build();
		final AcctSchemaDAO realAcctSchemaDAO = new AcctSchemaDAO();
		final AcctSchema schemaA = realAcctSchemaDAO.getById(schemaIdA);
		final AcctSchema schemaB = realAcctSchemaDAO.getById(schemaIdB);

		final IAcctSchemaDAO acctSchemaDAO = mock(IAcctSchemaDAO.class);
		// Org-resolved lookup (the fix): org A resolves to schema A only.
		when(acctSchemaDAO.getByClientAndOrgOrNull(any(), any())).thenReturn(schemaA);
		// Client-wide lookup (the old behaviour): BOTH schemas — so a revert would write to A and B.
		when(acctSchemaDAO.getAllByClient(any())).thenReturn(ImmutableList.of(schemaA, schemaB));

		invoiceBL = mock(IInvoiceBL.class);
		invoiceAcctRepository = mock(InvoiceAcctRepository.class);

		// Register the Services.get(...) fields BEFORE constructing the interceptor (they are resolved in the
		// interceptor's field initializers at construction time).
		Services.registerService(IAcctSchemaDAO.class, acctSchemaDAO);
		Services.registerService(IInvoiceBL.class, invoiceBL);

		interceptor = new C_Invoice_AcctOverride(invoiceAcctRepository);
	}

	@Test
	void materializes_only_into_the_org_resolved_schema_not_every_client_schema()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(false);
		InterfaceWrapperHelper.setValue(invoice, "AD_Client_ID", CLIENT_ID);
		invoice.setAD_Org_ID(ORG_A.getRepoId());
		InterfaceWrapperHelper.saveRecord(invoice);

		final I_C_InvoiceLine line = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.setValue(line, "AD_Client_ID", CLIENT_ID);
		line.setAD_Org_ID(ORG_A.getRepoId());
		line.setC_ElementValue_Override_ID(OVERRIDE_ACCOUNT.getRepoId());
		InterfaceWrapperHelper.saveRecord(line);

		when(invoiceBL.getLines(any())).thenReturn(ImmutableList.of(line));

		interceptor.materializeAcctOverrides(invoice);

		// Exactly two materialized rows (P_Expense_Acct + P_InventoryClearing_Acct), BOTH into schema A.
		// A revert to getAllByClient would produce four calls (also into schema B) → both assertions fail.
		final ArgumentCaptor<AcctSchemaId> acctSchemaCaptor = ArgumentCaptor.forClass(AcctSchemaId.class);
		verify(invoiceAcctRepository, times(2)).createOrUpdateLineOverride(any(), any(), acctSchemaCaptor.capture(), any(), any());

		assertThat(acctSchemaCaptor.getAllValues())
				.as("override must be materialized ONLY into org A's resolved schema, never org B's schema")
				.containsOnly(schemaIdA)
				.doesNotContain(schemaIdB);
	}
}
