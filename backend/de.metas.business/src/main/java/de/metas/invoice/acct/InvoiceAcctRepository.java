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

	public void save(@NonNull InvoiceAcct invoiceAcct)
	{
		//
		// Delete previous records
		queryBL.createQueryBuilder(I_C_Invoice_Acct.class)
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceAcct.getInvoiceId())
				.create()
				.delete();

		//
		// Save new
		for (InvoiceAcctRule rule : invoiceAcct.getRulesOrdered())
		{
			final I_C_Invoice_Acct record = InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class);
			record.setC_Invoice_ID(invoiceAcct.getInvoiceId().getRepoId());
			record.setAD_Org_ID(invoiceAcct.getOrgId().getRepoId());
			updateRecordFromRule(record, rule);
			InterfaceWrapperHelper.save(record);
		}
	}

	private void updateRecordFromRule(@NonNull I_C_Invoice_Acct record, @NonNull final InvoiceAcctRule from)
	{
		updateRecordFromRuleMatcher(record, from.getMatcher());
		record.setC_ElementValue_ID(from.getElementValueId().getRepoId());
	}

	private void updateRecordFromRuleMatcher(@NonNull I_C_Invoice_Acct record, @NonNull final InvoiceAcctRuleMatcher from)
	{
		record.setC_AcctSchema_ID(from.getAcctSchemaId().getRepoId());
		record.setC_InvoiceLine_ID(InvoiceAndLineId.toRepoId(from.getInvoiceAndLineId()));
		record.setAccountName(from.getAccountConceptualName() != null ? from.getAccountConceptualName().getAsString() : null);
	}
}
