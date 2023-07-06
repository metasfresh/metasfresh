package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

class OIViewDataService
{
	private final IFactAcctBL factAcctBL;
	private final LookupDataSource validCombinationsLookup;
	private final LookupDataSource bpartnerLookup;
	private final SAPGLJournalService glJournalService;

	@Builder
	private OIViewDataService(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final IFactAcctBL factAcctBL,
			@NonNull final SAPGLJournalService glJournalService)
	{
		this.factAcctBL = factAcctBL;
		this.validCombinationsLookup = lookupDataSourceFactory.searchInTableLookup(I_C_ValidCombination.Table_Name);
		this.bpartnerLookup = lookupDataSourceFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		this.glJournalService = glJournalService;
	}

	public OIViewData getData(
			final SAPGLJournalId glJournalId,
			@Nullable final DocumentFilter filter)
	{
		final SAPGLJournal glJournal = glJournalService.getById(glJournalId);

		return OIViewData.builder()
				.viewDataService(this)
				.acctSchemaId(glJournal.getAcctSchemaId())
				.postingType(glJournal.getPostingType())
				.filter(filter)
				.build();
	}

	public List<OIRow> retrieveRows(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingType postingType,
			@Nullable final DocumentFilter filter)
	{
		return factAcctBL.stream(OIViewFilterHelper.toFactAcctQuery(acctSchemaId, postingType, filter))
				.map(this::toRow)
				.collect(ImmutableList.toImmutableList());
	}

	private OIRow toRow(final I_Fact_Acct record)
	{
		final Account account = factAcctBL.getAccount(record);
		final BigDecimal amtAcctDr = record.getAmtAcctDr();
		final BigDecimal amtAcctCr = record.getAmtAcctCr();
		final PostingSign postingSign = PostingSign.ofAmtDrAndCr(amtAcctDr, amtAcctCr);
		final BigDecimal amount = postingSign.isDebit() ? amtAcctDr : amtAcctCr;

		return OIRow.builder()
				.factAcctId(FactAcctId.ofRepoId(record.getFact_Acct_ID()))
				.postingSign(postingSign)
				.account(account)
				.accountCaption(getAccountCaption(account.getAccountId()))
				.amount(amount)
				.openAmount(amount) // TODO
				.dateAcct(record.getDateAcct().toInstant())
				.bpartner(bpartnerLookup.findById(record.getC_BPartner_ID()))
				.documentNo(record.getDocumentNo())
				.description(record.getDescription())
				.build();
	}

	private ITranslatableString getAccountCaption(@NonNull final AccountId accountId)
	{
		final LookupValue value = validCombinationsLookup.findById(accountId);
		return value != null ? value.getDisplayNameTrl() : LookupValue.unknownCaption(accountId.getRepoId());
	}

}
