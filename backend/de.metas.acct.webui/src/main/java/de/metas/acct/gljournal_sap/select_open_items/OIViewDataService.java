package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.annotations.VisibleForTesting;
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
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.ITranslatableString;
import de.metas.money.CurrencyCodeToCurrencyIdBiConverter;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
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
import java.util.Set;
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

	private static final ImmutableSet<DocStatus> ELIGIBLE_DOCSTATUSES = ImmutableSet.of(DocStatus.Completed, DocStatus.Closed);

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
		final FutureClearingAmountMap futureClearingAmounts = query.getFutureClearingAmounts();

		final FactAcctQuery factAcctQuery = toFactAcctQuery(query);
		if (factAcctQuery == null)
		{
			return Stream.of();
		}

		return factAcctBL.stream(factAcctQuery)
				.map(record -> toRow(record, futureClearingAmounts))
				.filter(Objects::nonNull);
	}

	@Nullable
	private FactAcctQuery toFactAcctQuery(@NonNull final OIViewDataQuery query)
	{
		final DocumentFilter filter = query.getFilter();

		final FactAcctQuery.FactAcctQueryBuilder factAcctQueryBuilder = FactAcctQuery.builder()
				.includeFactAcctIds(query.getIncludeFactAcctIds())
				.acctSchemaId(query.getAcctSchemaId())
				.postingType(query.getPostingType())
				.currencyId(query.getCurrencyId())
				.isOpenItem(true)
				.isOpenItemReconciled(false)
				.openItemTrxType(FAOpenItemTrxType.OPEN_ITEM);

		//
		// Open Item Account(s)
		{
			final Set<ElementValueId> openItemAccountIds = getOpenItemElementValueIds(filter);
			if (openItemAccountIds.isEmpty())
			{
				return null;
			}
			factAcctQueryBuilder.accountIds(openItemAccountIds);
		}

		//
		// DocStatus
		{
			final Set<DocStatus> docStatuses = getEligibleDocStatuses(filter);
			if (docStatuses.isEmpty())
			{
				return null;
			}
			factAcctQueryBuilder.docStatuses(docStatuses);
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
					.dateAcctGreaterOrEqualsTo(filter.getParameterValueAsInstantOrNull(OIViewFilterHelper.PARAM_DateAcct))
					.dateAcctLessOrEqualsTo(filter.getParameterValueToAsInstantOrNull(OIViewFilterHelper.PARAM_DateAcct))
					.documentNoLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_DocumentNo, null))
					.descriptionLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_Description, null))
					.poReferenceLike(filter.getParameterValueAsString(OIViewFilterHelper.PARAM_POReference, null))
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

	private static Set<DocStatus> getEligibleDocStatuses(@Nullable final DocumentFilter filter)
	{
		final DocStatus docStatus = filter != null
				? filter.getParameterValueAsRefListOrNull(OIViewFilterHelper.PARAM_DocStatus, DocStatus::ofNullableCode)
				: null;

		if (docStatus == null)
		{
			return ELIGIBLE_DOCSTATUSES;
		}
		else if (ELIGIBLE_DOCSTATUSES.contains(docStatus))
		{
			return ImmutableSet.of(docStatus);
		}
		else
		{
			return ImmutableSet.of();
		}
	}

	private Set<ElementValueId> getOpenItemElementValueIds(@Nullable final DocumentFilter filter)
	{
		final ImmutableSet<ElementValueId> openItemAccountIds = elementValueService.getOpenItemIds();
		if (filter != null)
		{
			final ElementValueId accountId = filter.getParameterValueAsRepoIdOrNull(OIViewFilterHelper.PARAM_Account_ID, ElementValueId::ofRepoIdOrNull);
			if (accountId != null)
			{
				return openItemAccountIds.contains(accountId)
						? ImmutableSet.of(accountId)
						: ImmutableSet.of();
			}
			else
			{
				return openItemAccountIds;
			}
		}
		else
		{
			return openItemAccountIds;
		}

	}

	@Nullable
	@VisibleForTesting
	OIRow toRow(
			@NonNull final I_Fact_Acct record,
			@NonNull final FutureClearingAmountMap futureClearingAmounts)
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.parseNullable(record.getOpenItemKey())
				.orElseThrow(() -> new AdempiereException("Line has no open item key: " + record)); // shall not happen

		final CurrencyCode sourceCurrency = moneyService.getCurrencyCodeByCurrencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()));
		final Amount amtSourceDr = Amount.of(record.getAmtSourceDr(), sourceCurrency);
		final Amount amtSourceCr = Amount.of(record.getAmtSourceCr(), sourceCurrency);
		final PostingSign postingSign = PostingSign.ofAmtDrAndCr(amtSourceDr.toBigDecimal(), amtSourceCr.toBigDecimal());
		final Amount balanceSrc = amtSourceDr.subtract(amtSourceCr);

		Amount openAmtSrc = Amount.of(record.getOI_OpenAmountSource(), sourceCurrency);
		final Amount futureClearingAmount = futureClearingAmounts.getAmountSrc(openItemKey).orElse(null);
		if (futureClearingAmount != null && !futureClearingAmount.isZero())
		{
			openAmtSrc = openAmtSrc.add(futureClearingAmount);
		}
		if (openAmtSrc.isZero())
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
				.amount(balanceSrc.negateIf(postingSign.isCredit()))
				.openAmount(openAmtSrc.negateIf(postingSign.isCredit()))
				.dateAcct(record.getDateAcct().toInstant())
				.bpartnerId(bpartnerId)
				.bpartnerCaption(getBPartnerCaption(bpartnerId))
				.documentNo(record.getDocumentNo())
				.description(record.getDescription())
				.sectionCode(sectionCodeLookup.findById(record.getM_SectionCode_ID()))
				.userElementString1(record.getUserElementString1())
				.openItemKey(openItemKey)
				.dimension(IFactAcctBL.extractDimension(record))
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
}
