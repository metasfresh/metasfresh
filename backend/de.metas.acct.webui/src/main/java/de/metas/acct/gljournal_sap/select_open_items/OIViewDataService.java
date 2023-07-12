package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.ITranslatableString;
import de.metas.money.CurrencyCodeToCurrencyIdBiConverter;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_SectionCode;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

class OIViewDataService
{
	private final IFactAcctBL factAcctBL;
	private final IAcctSchemaBL acctSchemaBL;
	private final MoneyService moneyService;
	private final LookupDataSource validCombinationsLookup;
	private final LookupDataSource bpartnerLookup;
	private final LookupDataSource sectionCodeLookup;
	private final SAPGLJournalService glJournalService;
	private final ElementValueService elementValueService;

	@Builder
	private OIViewDataService(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final IFactAcctBL factAcctBL,
			@NonNull final IAcctSchemaBL acctSchemaBL,
			@NonNull final MoneyService moneyService,
			@NonNull final SAPGLJournalService glJournalService,
			@NonNull final ElementValueService elementValueService)
	{
		this.factAcctBL = factAcctBL;
		this.validCombinationsLookup = lookupDataSourceFactory.searchInTableLookup(I_C_ValidCombination.Table_Name);
		this.bpartnerLookup = lookupDataSourceFactory.searchInTableLookup(I_C_BPartner.Table_Name);
		this.sectionCodeLookup = lookupDataSourceFactory.searchInTableLookup(I_M_SectionCode.Table_Name);
		this.acctSchemaBL = acctSchemaBL;
		this.moneyService = moneyService;
		this.glJournalService = glJournalService;
		this.elementValueService = elementValueService;
	}

	OIViewData getData(
			final SAPGLJournalId glJournalId,
			@Nullable final DocumentFilter filter,
			@Nullable OIRowUserInputParts initialUserInput)
	{
		final SAPGLJournal glJournal = getGlJournal(glJournalId);
		final AcctSchema acctSchema = acctSchemaBL.getById(glJournal.getAcctSchemaId());

		return OIViewData.builder()
				.viewDataService(this)
				.glJournal(glJournal)
				.acctSchema(acctSchema)
				.filter(filter)
				.initialUserInput(initialUserInput)
				.build();
	}

	@NonNull
	SAPGLJournal getGlJournal(final SAPGLJournalId glJournalId)
	{
		return glJournalService.getById(glJournalId);
	}

	CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter()
	{
		return moneyService;
	}

	Stream<OIRow> streamRows(@NonNull final OIViewDataQuery query)
	{
		final AcctSchema acctSchema = query.getAcctSchema();
		final CurrencyCode acctCurrencyCode = moneyService.getCurrencyCodeByCurrencyId(acctSchema.getCurrencyId());
		final FutureClearingAmountMap futureClearingAmounts = query.getFutureClearingAmounts();

		return factAcctBL.stream(toFactAcctQuery(query))
				.map(record -> toRow(record, acctCurrencyCode, futureClearingAmounts))
				.filter(Objects::nonNull);
	}

	private FactAcctQuery toFactAcctQuery(@NonNull final OIViewDataQuery query)
	{
		final FactAcctQuery.FactAcctQueryBuilder factAcctQueryBuilder = FactAcctQuery.builder()
				.includeFactAcctIds(query.getIncludeFactAcctIds())
				.acctSchemaId(query.getAcctSchemaId())
				.postingType(query.getPostingType())
				.isOpenItem(true)
				.isOpenItemReconciled(false)
				.openItemTrxType(FAOpenItemTrxType.OPEN_ITEM);

		//
		// Open Item Account(s)
		final DocumentFilter filter = query.getFilter();
		if (filter != null)
		{
			final ImmutableSet<ElementValueId> openItemAccountIds = elementValueService.getOpenItemIds();
			final ElementValueId accountId = filter.getParameterValueAsRepoIdOrNull(OIViewFilterHelper.PARAM_Account_ID, ElementValueId::ofRepoIdOrNull);
			if (accountId != null)
			{
				if (!openItemAccountIds.contains(accountId))
				{
					return null;
				}
				factAcctQueryBuilder.accountId(accountId);
			}
			else
			{
				factAcctQueryBuilder.accountIds(openItemAccountIds);
			}
		}

		//
		// Other user filters
		if (filter != null)
		{
			final BPartnerId bpartnerId = filter.getParameterValueAsRepoIdOrNull(OIViewFilterHelper.PARAM_C_BPartner_ID, BPartnerId::ofRepoIdOrNull);
			final InSetPredicate<BPartnerId> bpartnerIds = bpartnerId != null ? InSetPredicate.only(bpartnerId) : InSetPredicate.any();

			factAcctQueryBuilder.bpartnerIds(bpartnerIds)
					.sectionCodeId(filter.getParameterValueAsRepoIdOrNull(OIViewFilterHelper.PARAM_M_SectionCode_ID, SectionCodeId::ofRepoIdOrNull))
					.salesOrderId(filter.getParameterValueAsRepoIdOrNull(OIViewFilterHelper.PARAM_C_OrderSO_ID, OrderId::ofRepoIdOrNull))
					.dateAcct(filter.getParameterValueAsInstantOrNull(OIViewFilterHelper.PARAM_DateAcct))
					.documentNoLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_DocumentNo, null))
					.descriptionLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_Description, null))
					.poReferenceLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_POReference, null))
					.docStatus(filter.getParameterValueAsRefListOrNull(OIViewFilterHelper.PARAM_DocStatus, DocStatus::ofNullableCodeOrUnknown))
					.userElementString1Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString1, null))
					.userElementString2Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString2, null))
					.userElementString3Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString3, null))
					.userElementString4Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString4, null))
					.userElementString5Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString5, null))
					.userElementString6Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString6, null))
					.userElementString7Like(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_UserElementString7, null))
			;
		}

		return factAcctQueryBuilder.build();
	}

	@Nullable
	private OIRow toRow(
			@NonNull final I_Fact_Acct record,
			@NonNull final CurrencyCode acctCurrencyCode,
			@NonNull final FutureClearingAmountMap futureClearingAmounts)
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.optionalOfString(record.getOpenItemKey())
				.orElseThrow(() -> new AdempiereException("Line has no open item key: " + record)); // shall not happen

		final Amount amtAcctDr = Amount.of(record.getAmtAcctDr(), acctCurrencyCode);
		final Amount amtAcctCr = Amount.of(record.getAmtAcctCr(), acctCurrencyCode);
		final PostingSign postingSign = PostingSign.ofAmtDrAndCr(amtAcctDr.toBigDecimal(), amtAcctCr.toBigDecimal());
		final Amount acctBalance = amtAcctDr.subtract(amtAcctCr);

		Amount allocatedAmt = acctBalance.subtract(Amount.of(record.getOI_OpenAmount(), acctCurrencyCode));
		final Amount futureClearingAmount = futureClearingAmounts.getAmount(openItemKey).orElse(null);
		if (futureClearingAmount != null && !futureClearingAmount.isZero())
		{
			allocatedAmt = allocatedAmt.add(futureClearingAmount);
		}

		final Amount openAmount = acctBalance.add(allocatedAmt);

		if (openAmount.isZero())
		{
			return null;
		}

		final ElementValue elementValue = elementValueService.getById(ElementValueId.ofRepoId(record.getAccount_ID()));
		final Account account = factAcctBL.getAccount(record);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());

		return OIRow.builder()
				.factAcctId(FactAcctId.ofRepoId(record.getFact_Acct_ID()))
				.isOpenItem(elementValue.isOpenItem())
				.postingSign(postingSign)
				.account(account)
				.accountCaption(getAccountCaption(account.getAccountId()))
				.amount(acctBalance.negateIf(postingSign.isCredit()))
				.openAmount(openAmount.negateIf(postingSign.isCredit()))
				.dateAcct(record.getDateAcct().toInstant())
				.bpartnerId(bpartnerId)
				.bpartnerCaption(getBPartnerCaption(bpartnerId))
				.documentNo(record.getDocumentNo())
				.description(record.getDescription())
				.sectionCode(sectionCodeLookup.findById(record.getM_SectionCode_ID()))
				.userElementString1(record.getUserElementString1())
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
