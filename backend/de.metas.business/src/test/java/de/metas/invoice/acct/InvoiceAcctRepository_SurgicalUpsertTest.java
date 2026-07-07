package de.metas.invoice.acct;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice_Acct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD test for {@link InvoiceAcctRepository#createOrUpdateLineOverride}.
 */
class InvoiceAcctRepository_SurgicalUpsertTest
{
	private InvoiceAcctRepository repo;
	private IQueryBL queryBL;

	// fixed test IDs
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(1001);
	private static final InvoiceAndLineId LINE_ID = InvoiceAndLineId.ofRepoId(INVOICE_ID, 2001);
	private static final InvoiceAndLineId OTHER_LINE_ID = InvoiceAndLineId.ofRepoId(INVOICE_ID, 2002);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000000);
	private static final AcctSchemaId SCHEMA_ID = AcctSchemaId.ofRepoId(3001);
	private static final AcctSchemaId OTHER_SCHEMA_ID = AcctSchemaId.ofRepoId(3002);
	private static final AccountConceptualName CONCEPT_EXPENSE = AccountConceptualName.ofString("P_Expense_Acct");
	private static final AccountConceptualName CONCEPT_INVENTORY = AccountConceptualName.ofString("P_InventoryClearing_Acct");
	private static final ElementValueId ELEMENT_A = ElementValueId.ofRepoId(5001);
	private static final ElementValueId ELEMENT_B = ElementValueId.ofRepoId(5002);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		repo = new InvoiceAcctRepository();
		queryBL = Services.get(IQueryBL.class);
	}

	// -----------------------------------------------------------------------
	// Scenario 1: new override inserted when none exists
	// -----------------------------------------------------------------------
	@Test
	void newOverride_inserted_when_none_exists()
	{
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);

		final List<I_C_Invoice_Acct> rows = allActiveRows();
		assertThat(rows).hasSize(1);

		final I_C_Invoice_Acct row = rows.get(0);
		assertThat(row.getC_Invoice_ID()).isEqualTo(INVOICE_ID.getRepoId());
		assertThat(row.getC_InvoiceLine_ID()).isEqualTo(LINE_ID.getRepoId());
		assertThat(row.getC_AcctSchema_ID()).isEqualTo(SCHEMA_ID.getRepoId());
		assertThat(row.getAccountName()).isEqualTo(CONCEPT_EXPENSE.getAsString());
		assertThat(row.getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());
		assertThat(row.isActive()).isTrue();
		assertThat(row.getAD_Org_ID()).as("materialized row must carry the passed org").isEqualTo(ORG_ID.getRepoId());
	}

	// -----------------------------------------------------------------------
	// Scenario 2: contradicting row deactivated + new active row inserted
	// -----------------------------------------------------------------------
	@Test
	void contradicting_row_deactivated_and_new_active_row_inserted()
	{
		// seed: existing row with ELEMENT_A
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);
		assertThat(allActiveRows()).hasSize(1);

		// now override with ELEMENT_B (different account)
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_B);

		// old row must be inactive
		final List<I_C_Invoice_Acct> allRows = queryBL
				.createQueryBuilder(I_C_Invoice_Acct.class)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, INVOICE_ID)
				.create()
				.list();
		assertThat(allRows).hasSize(2);

		final long activeCount = allRows.stream().filter(I_C_Invoice_Acct::isActive).count();
		assertThat(activeCount).isEqualTo(1);

		final Optional<I_C_Invoice_Acct> activeRowOpt = allRows.stream().filter(I_C_Invoice_Acct::isActive).findFirst();
		assertThat(activeRowOpt).as("active row after override").isPresent();
		final I_C_Invoice_Acct activeRow = activeRowOpt.get();
		assertThat(activeRow.getC_ElementValue_ID()).isEqualTo(ELEMENT_B.getRepoId());

		final Optional<I_C_Invoice_Acct> inactiveRowOpt = allRows.stream().filter(r -> !r.isActive()).findFirst();
		assertThat(inactiveRowOpt).as("deactivated row after override").isPresent();
		final I_C_Invoice_Acct inactiveRow = inactiveRowOpt.get();
		assertThat(inactiveRow.getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());
	}

	// -----------------------------------------------------------------------
	// Scenario 3: unrelated rows (different concept / different line) untouched
	// -----------------------------------------------------------------------
	@Test
	void unrelated_rows_remain_untouched()
	{
		// unrelated row 1: same line, different concept
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_INVENTORY, ELEMENT_A);
		// unrelated row 2: different line, same concept
		repo.createOrUpdateLineOverride(OTHER_LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);

		// now update the target (LINE_ID / CONCEPT_EXPENSE) with ELEMENT_B
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_B);

		// All three are still active (the two unrelated + the new one)
		assertThat(allActiveRows()).hasSize(3);

		// unrelated rows still have ELEMENT_A
		final Optional<I_C_Invoice_Acct> inventoryRowOpt = allActiveRows().stream()
				.filter(r -> CONCEPT_INVENTORY.getAsString().equals(r.getAccountName())
						&& r.getC_InvoiceLine_ID() == LINE_ID.getRepoId())
				.findFirst();
		assertThat(inventoryRowOpt).as("missing unrelated inventory row").isPresent();
		final I_C_Invoice_Acct inventoryRow = inventoryRowOpt.get();
		assertThat(inventoryRow.getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());

		final Optional<I_C_Invoice_Acct> otherLineRowOpt = allActiveRows().stream()
				.filter(r -> CONCEPT_EXPENSE.getAsString().equals(r.getAccountName())
						&& r.getC_InvoiceLine_ID() == OTHER_LINE_ID.getRepoId())
				.findFirst();
		assertThat(otherLineRowOpt).as("missing unrelated other-line row").isPresent();
		final I_C_Invoice_Acct otherLineRow = otherLineRowOpt.get();
		assertThat(otherLineRow.getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());
	}

	// -----------------------------------------------------------------------
	// Scenario 4: idempotent – same account again produces no duplicate
	// -----------------------------------------------------------------------
	@Test
	void idempotent_same_account_no_duplicate()
	{
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);

		assertThat(allActiveRows()).hasSize(1);
		assertThat(allActiveRows().get(0).getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());
	}

	// -----------------------------------------------------------------------
	// Scenario 5: different AcctSchema → rows are isolated by schema
	// -----------------------------------------------------------------------
	@Test
	void different_schema_rows_are_isolated()
	{
		// insert for SCHEMA_ID
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);
		// insert for OTHER_SCHEMA_ID (independent tuple)
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, OTHER_SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_B);

		// both rows are active
		assertThat(allActiveRows()).hasSize(2);

		// updating SCHEMA_ID with a new element must NOT touch the OTHER_SCHEMA_ID row
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_B);

		// OTHER_SCHEMA_ID row is still active with ELEMENT_B
		final Optional<I_C_Invoice_Acct> otherSchemaRowOpt = allActiveRows().stream()
				.filter(r -> r.getC_AcctSchema_ID() == OTHER_SCHEMA_ID.getRepoId())
				.findFirst();
		assertThat(otherSchemaRowOpt).as("OTHER_SCHEMA_ID row must remain active").isPresent();
		assertThat(otherSchemaRowOpt.get().getC_ElementValue_ID()).isEqualTo(ELEMENT_B.getRepoId());
	}

	// -----------------------------------------------------------------------
	// Scenario 6: a manually-saved real-org row + a materialized same-org row
	//             must be retrievable (extractOrgId's uniqueElementOrThrow must not throw).
	//             This is the regression the orgId fix (real invoice org, not OrgId.ANY) addresses.
	// -----------------------------------------------------------------------
	@Test
	void manual_and_materialized_rows_with_same_real_org_are_retrievable()
	{
		// seed a manually-inserted row with the real org (simulates the window save() path)
		final I_C_Invoice_Acct manualRow = InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class);
		manualRow.setC_Invoice_ID(INVOICE_ID.getRepoId());
		manualRow.setAD_Org_ID(ORG_ID.getRepoId());
		manualRow.setC_AcctSchema_ID(SCHEMA_ID.getRepoId());
		manualRow.setAccountName(CONCEPT_INVENTORY.getAsString());
		manualRow.setC_ElementValue_ID(ELEMENT_A.getRepoId());
		manualRow.setIsActive(true);
		InterfaceWrapperHelper.save(manualRow);

		// materialized override for a different concept, same invoice + real org
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_B);

		// getById must succeed — all rows share the same org, so uniqueElementOrThrow does not throw
		final Optional<InvoiceAcct> result = repo.getById(INVOICE_ID);
		assertThat(result).as("invoice override must be retrievable with mixed manual+materialized same-org rows").isPresent();
		assertThat(result.get().getOrgId()).isEqualTo(ORG_ID);
	}

	// -----------------------------------------------------------------------
	// Scenario 7: two active rows already exist for the SAME (schema, invoice, line, concept)
	//             tuple — one matching the incoming account, one contradicting it. The matching
	//             row must NOT short-circuit deactivation of the contradicting one. This
	//             invariant-violating starting state can arise from a manually-inserted
	//             C_Invoice_Acct override row (the feature supports manual rows) sitting next
	//             to a materialized one; the index on the tuple is not UNIQUE, so nothing at
	//             the DB level prevents it. A contradicting active row left behind would make
	//             the per-line override ambiguous (two accounts for one concept).
	// -----------------------------------------------------------------------
	@Test
	void contradicting_second_active_row_is_deactivated_even_when_first_matches()
	{
		// seed the MATCHING row first (lower PK → returned first by the tuple query),
		// then the CONTRADICTING row — this is the order that triggers the early-return bug.
		seedActiveRow(ELEMENT_A);
		seedActiveRow(ELEMENT_B);
		assertThat(allActiveRows()).hasSize(2);

		// materialize the override with ELEMENT_A (matches the first seeded row)
		repo.createOrUpdateLineOverride(LINE_ID, ORG_ID, SCHEMA_ID, CONCEPT_EXPENSE, ELEMENT_A);

		// exactly one active row must remain, carrying ELEMENT_A; the contradicting ELEMENT_B row deactivated
		final List<I_C_Invoice_Acct> active = allActiveRows();
		assertThat(active).as("contradicting row must be deactivated even though a matching row was hit first").hasSize(1);
		assertThat(active.get(0).getC_ElementValue_ID()).isEqualTo(ELEMENT_A.getRepoId());
	}

	// -----------------------------------------------------------------------
	// helpers
	// -----------------------------------------------------------------------
	private void seedActiveRow(final ElementValueId elementValueId)
	{
		final I_C_Invoice_Acct row = InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class);
		row.setC_Invoice_ID(INVOICE_ID.getRepoId());
		row.setC_InvoiceLine_ID(LINE_ID.getRepoId());
		row.setAD_Org_ID(ORG_ID.getRepoId());
		row.setC_AcctSchema_ID(SCHEMA_ID.getRepoId());
		row.setAccountName(CONCEPT_EXPENSE.getAsString());
		row.setC_ElementValue_ID(elementValueId.getRepoId());
		row.setIsActive(true);
		InterfaceWrapperHelper.save(row);
	}

	private List<I_C_Invoice_Acct> allActiveRows()
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, INVOICE_ID)
				.create()
				.list();
	}
}
