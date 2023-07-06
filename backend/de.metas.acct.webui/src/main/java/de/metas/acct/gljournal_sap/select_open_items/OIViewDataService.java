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
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.exceptions.AdempiereException;
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
		final FAOpenItemKey openItemKey = FAOpenItemKey.optionalOfString(record.getOpenItemKey())
				.orElseThrow(() -> new AdempiereException("Line has no open item key: " + record)); // shall not happen

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());

		return OIRow.builder()
				.factAcctId(FactAcctId.ofRepoId(record.getFact_Acct_ID()))
				.postingSign(postingSign)
				.account(account)
				.accountCaption(getAccountCaption(account.getAccountId()))
				.amount(amount)
				.openAmount(amount) // TODO compute open amount
				.dateAcct(record.getDateAcct().toInstant())
				.bpartnerId(bpartnerId)
				.bpartnerCaption(getBPartnerCaption(bpartnerId))
				.documentNo(record.getDocumentNo())
				.description(record.getDescription())
				.openItemKey(openItemKey)
				.dimension(extractDimension(record))
				.build();
	}

	private ITranslatableString getAccountCaption(@NonNull final AccountId accountId)
	{
		final LookupValue value = validCombinationsLookup.findById(accountId);
		return value != null ? value.getDisplayNameTrl() : LookupValue.unknownCaption(accountId.getRepoId());
	}

	private ITranslatableString getBPartnerCaption(@Nullable final BPartnerId bpartnerId)
	{
		final LookupValue value = bpartnerId != null ? bpartnerLookup.findById(bpartnerId) : null;
		return value != null ? value.getDisplayNameTrl() : null;
	}

	private static Dimension extractDimension(final I_Fact_Acct record)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.bpartnerId2(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.user1_ID(record.getUser1_ID())
				.user2_ID(record.getUser2_ID())
				.userElement1Id(record.getUserElement1_ID())
				.userElement2Id(record.getUserElement2_ID())
				.userElementString1(record.getUserElementString1())
				.userElementString2(record.getUserElementString2())
				.userElementString3(record.getUserElementString3())
				.userElementString4(record.getUserElementString4())
				.userElementString5(record.getUserElementString5())
				.userElementString6(record.getUserElementString6())
				.userElementString7(record.getUserElementString7())
				.build();
	}

}
