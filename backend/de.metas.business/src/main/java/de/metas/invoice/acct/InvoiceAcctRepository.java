package de.metas.invoice.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cache.CCache;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Acct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Tables: C_Invoice_Acct
 * Repository Cluster: InvoiceAcctRepository (sole owner)
 */
@Repository
public class InvoiceAcctRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<InvoiceId, Optional<InvoiceAcct>> cache = CCache.<InvoiceId, Optional<InvoiceAcct>>builder()
			.tableName(I_C_Invoice_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public Optional<InvoiceAcct> getById(@NonNull final InvoiceId invoiceId)
	{
		return cache.getOrLoad(invoiceId, this::retrieveByInvoiceId);
	}

	private Optional<InvoiceAcct> retrieveByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		final List<I_C_Invoice_Acct> records = queryBL
				.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.list();
		if (records.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(
				InvoiceAcct.builder()
						.invoiceId(invoiceId)
						.orgId(extractOrgId(records))
						.rules(records
								.stream()
								.map(InvoiceAcctRepository::toRule)
								.collect(ImmutableList.toImmutableList()))
						.build());
	}

	private static OrgId extractOrgId(final List<I_C_Invoice_Acct> records)
	{
		return records.stream()
				.map(record -> OrgId.ofRepoId(record.getAD_Org_ID()))
				.collect(GuavaCollectors.uniqueElementOrThrow(orgIds -> new AdempiereException("Unique org expected but got " + orgIds)));
	}

	private static InvoiceAcctRule toRule(final I_C_Invoice_Acct record)
	{
		return InvoiceAcctRule.builder()
				.matcher(toRuleMatcher(record))
				.elementValueId(ElementValueId.ofRepoId(record.getC_ElementValue_ID()))
				.build();
	}

	private static InvoiceAcctRuleMatcher toRuleMatcher(final I_C_Invoice_Acct record)
	{
		return InvoiceAcctRuleMatcher.builder()
				.invoiceAndLineId(InvoiceAndLineId.ofRepoIdOrNull(record.getC_Invoice_ID(), record.getC_InvoiceLine_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.accountConceptualName(AccountConceptualName.ofNullableString(record.getAccountName()))
				.build();
	}

	public void save(@NonNull final InvoiceAcct invoiceAcct)
	{
		//
		// Delete previous records
		queryBL.createQueryBuilder(I_C_Invoice_Acct.class)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceAcct.getInvoiceId())
				.create()
				.delete();

		//
		// Save new
		for (final InvoiceAcctRule rule : invoiceAcct.getRulesOrdered())
		{
			final I_C_Invoice_Acct record = InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class);
			record.setC_Invoice_ID(invoiceAcct.getInvoiceId().getRepoId());
			record.setAD_Org_ID(invoiceAcct.getOrgId().getRepoId());
			updateRecordFromRule(record, rule);
			InterfaceWrapperHelper.save(record);
		}
	}

	private void updateRecordFromRule(@NonNull final I_C_Invoice_Acct record, @NonNull final InvoiceAcctRule from)
	{
		updateRecordFromRuleMatcher(record, from.getMatcher());
		record.setC_ElementValue_ID(from.getElementValueId().getRepoId());
	}

	private void updateRecordFromRuleMatcher(@NonNull final I_C_Invoice_Acct record, @NonNull final InvoiceAcctRuleMatcher from)
	{
		record.setC_AcctSchema_ID(from.getAcctSchemaId().getRepoId());
		record.setC_InvoiceLine_ID(InvoiceAndLineId.toRepoId(from.getInvoiceAndLineId()));
		record.setAccountName(from.getAccountConceptualName() != null ? from.getAccountConceptualName().getAsString() : null);
	}

	/**
	 * Surgical upsert for a single (schema, invoice, line, concept) tuple.
	 * <ul>
	 * <li>No existing row → insert new active row.</li>
	 * <li>Existing row with same {@code elementValueId} → no-op (idempotent).</li>
	 * <li>Existing row with different {@code elementValueId} → deactivate it, then insert new active row.</li>
	 * </ul>
	 * Never touches rows belonging to other (schema / invoice / line / concept) tuples.
	 * Cache is reset automatically after any write (the CCache table-change listener on C_Invoice_Acct fires on save).
	 */
	public void createOrUpdateLineOverride(
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final AccountConceptualName accountConceptualName,
			@NonNull final ElementValueId elementValueId)
	{
		final InvoiceId invoiceId = invoiceAndLineId.getInvoiceId();

		// Query for the exact (schema, invoice, line, concept) tuple — active rows only.
		final List<I_C_Invoice_Acct> existing = queryBL
				.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID, invoiceAndLineId)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_AccountName, accountConceptualName.getAsString())
				.create()
				.list();

		boolean exactMatchFound = false;
		for (final I_C_Invoice_Acct row : existing)
		{
			if (ElementValueId.equals(ElementValueId.ofRepoIdOrNull(row.getC_ElementValue_ID()), elementValueId))
			{
				// Exact match — keep it, but keep scanning so any contradicting row is still deactivated.
				// (The tuple index is not UNIQUE and manual rows are possible, so more than one active
				// row for the same tuple can exist; returning on the first match would leave a
				// contradicting row active → an ambiguous per-line override.)
				exactMatchFound = true;
			}
			else
			{
				// Different account → deactivate.
				row.setIsActive(false);
				InterfaceWrapperHelper.save(row);
			}
		}
		if (exactMatchFound)
		{
			// A matching active row already exists (any contradictors deactivated above) — nothing to insert.
			return;
		}

		// Insert new active row.
		final I_C_Invoice_Acct newRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class);
		newRecord.setC_Invoice_ID(invoiceId.getRepoId());
		newRecord.setC_InvoiceLine_ID(invoiceAndLineId.getRepoId());
		newRecord.setAD_Org_ID(orgId.getRepoId());
		newRecord.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		newRecord.setAccountName(accountConceptualName.getAsString());
		newRecord.setC_ElementValue_ID(elementValueId.getRepoId());
		newRecord.setIsActive(true);
		InterfaceWrapperHelper.save(newRecord);
		// No explicit cache eviction needed: this CCache is registered on C_Invoice_Acct.Table_Name,
		// so saving the row above already fires the table-change listener that resets it.
	}
}
