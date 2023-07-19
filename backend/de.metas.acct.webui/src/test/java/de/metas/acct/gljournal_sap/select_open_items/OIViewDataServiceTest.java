package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.GLCategoryId;
import de.metas.acct.accounts.TaxAccountsRepository;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.gljournal_sap.service.SAPGLJournalCurrencyConverter;
import de.metas.acct.gljournal_sap.service.SAPGLJournalRepository;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.gljournal_sap.service.SAPGLJournalTaxProvider;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.acct.open_items.handlers.BPartnerOIHandler;
import de.metas.acct.open_items.handlers.PaymentOIHandler;
import de.metas.ad_reference.ADReferenceService;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ChartOfAccountsRepository;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.elementvalue.ElementValueService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.X_C_ElementValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

class OIViewDataServiceTest
{
	//
	// Services
	private OIViewDataService viewDataService;
	private MoneyService moneyService;

	//
	// Master data
	private static final CurrencyCode EUR = CurrencyCode.EUR;
	private CurrencyId EUR_ID;
	private static final CurrencyCode RON = CurrencyCode.ofThreeLetterCode("RON");
	private CurrencyId RON_ID;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.moneyService = new MoneyService(new CurrencyRepository());

		final ElementValueService elementValueService = new ElementValueService(
				new ElementValueRepository(),
				new TreeNodeService(new TreeNodeRepository(), new ChartOfAccountsService(new ChartOfAccountsRepository())));

		SAPGLJournalRepository glJournalRepository = new SAPGLJournalRepository();
		SAPGLJournalCurrencyConverter currencyConverter = new SAPGLJournalCurrencyConverter();
		this.viewDataService = OIViewDataService.builder()
				.lookupDataSourceFactory(new MockedLookupDataSourceFactory())
				.factAcctBL(Services.get(IFactAcctBL.class))
				.acctSchemaBL(Services.get(IAcctSchemaBL.class))
				.moneyService(moneyService)
				.glJournalService(new SAPGLJournalService(
						glJournalRepository,
						currencyConverter,
						new SAPGLJournalTaxProvider(new TaxAccountsRepository(), moneyService),
						elementValueService,
						new FAOpenItemsService(
								elementValueService,
								Optional.of(ImmutableList.of(
										new BPartnerOIHandler(),
										new PaymentOIHandler()
								))))
				)
				.elementValueService(elementValueService)
				.build();

		//
		//

		this.EUR_ID = PlainCurrencyDAO.createCurrencyId(EUR);
		this.RON_ID = PlainCurrencyDAO.createCurrencyId(RON);
	}

	@SuppressWarnings("SameParameterValue")
	private ElementValueId createElementValue(String value, boolean isOpenItem)
	{
		final I_C_ElementValue record = InterfaceWrapperHelper.newInstance(I_C_ElementValue.class);
		record.setC_Element_ID(111);
		record.setValue(value);
		record.setName(value);
		record.setAccountSign(X_C_ElementValue.ACCOUNTSIGN_Natural);
		record.setAccountType(X_C_ElementValue.ACCOUNTTYPE_Asset);
		record.setIsOpenItem(isOpenItem);
		InterfaceWrapperHelper.saveRecord(record);
		return ElementValueId.ofRepoId(record.getC_ElementValue_ID());
	}

	@Builder(builderMethodName = "glJournal", builderClassName = "$GLJournalBuilder")
	SAPGLJournal createGLJournal(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingSign sign,
			@NonNull final Money amountSrc,
			@NonNull final Money amountAcct,
			@NonNull final FAOpenItemKey openItemKey)
	{
		final CurrencyId acctCurrencyId = amountAcct.getCurrencyId();

		return SAPGLJournal.builder()
				.id(SAPGLJournalId.ofRepoId(1))
				.conversionCtx(SAPGLJournalCurrencyConversionCtx.builder()
						.acctCurrencyId(acctCurrencyId)
						.currencyId(amountSrc.getCurrencyId())
						.date(SystemTime.asInstant())
						.clientAndOrgId(ClientAndOrgId.MAIN)
						.build())
				.docTypeId(DocTypeId.ofRepoId(1))
				.acctSchemaId(acctSchemaId)
				.postingType(PostingType.Actual)
				.docStatus(DocStatus.Drafted)
				.line(SAPGLJournalLine.builder()
						.line(SeqNo.ofInt(10))
						.account(Account.ofId(AccountId.ofRepoId(1)))
						.postingSign(sign)
						.amount(amountSrc)
						.amountAcct(amountAcct)
						.orgId(OrgId.MAIN)
						.dimension(Dimension.EMPTY)
						.openItemTrxInfo(FAOpenItemTrxInfo.clearing(openItemKey))
						.build())
				.totalAcctDR(amountAcct.toZero())
				.totalAcctCR(amountAcct.toZero())
				.orgId(OrgId.MAIN)
				.dimension(Dimension.EMPTY)
				.description("description")
				.glCategoryId(GLCategoryId.NONE)
				.build();
	}

	@Nested
	class toRow
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.parse("Acct#MyTable#1");
		
		@Test
		void debit()
		{
			final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().currencyId(EUR_ID).build();
			final ElementValueId accountId = createElementValue("1234", true);

			final I_Fact_Acct factAcct = InterfaceWrapperHelper.newInstance(I_Fact_Acct.class);
			factAcct.setFact_Acct_ID(1);
			factAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
			factAcct.setAccount_ID(accountId.getRepoId());
			factAcct.setPostingType(PostingType.Actual.getCode());
			factAcct.setDateAcct(SystemTime.asDayTimestamp());
			factAcct.setDateTrx(factAcct.getDateAcct());
			factAcct.setC_Currency_ID(RON_ID.getRepoId());
			factAcct.setAmtSourceDr(new BigDecimal("500"));
			factAcct.setAmtSourceCr(new BigDecimal("0"));
			factAcct.setAmtAcctDr(new BigDecimal("100"));
			factAcct.setAmtAcctCr(new BigDecimal("0"));
			factAcct.setOpenItemKey(openItemKey.getAsString());
			factAcct.setOI_TrxType(FAOpenItemTrxType.OPEN_ITEM.getCode());
			factAcct.setOI_OpenAmountSource(new BigDecimal("150"));
			factAcct.setOI_OpenAmount(new BigDecimal("30"));
			factAcct.setIsOpenItemsReconciled(false);
			InterfaceWrapperHelper.save(factAcct);

			final SAPGLJournal glJournal = glJournal()
					.acctSchemaId(acctSchemaId)
					.sign(PostingSign.CREDIT)
					.amountSrc(Money.of("35", RON_ID))
					.amountAcct(Money.of("7", EUR_ID))
					.openItemKey(openItemKey)
					.build();
			final FutureClearingAmountMap futureClearingAmounts = FutureClearingAmountMap.ofGLJournal(glJournal, moneyService);

			final OIRow row = viewDataService.toRow(factAcct, futureClearingAmounts);
			Assertions.assertThat(row).isNotNull();
			Assertions.assertThat(row.getPostingSign()).isEqualTo(PostingSign.DEBIT);
			Assertions.assertThat(row.getAmount()).isEqualTo(Amount.of("500", RON));
			Assertions.assertThat(row.getOpenAmountEffective()).isEqualTo(Amount.of("115", RON));
		}

		@Test
		void credit()
		{
			final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().currencyId(EUR_ID).build();
			final ElementValueId accountId = createElementValue("1234", true);

			final I_Fact_Acct factAcct = InterfaceWrapperHelper.newInstance(I_Fact_Acct.class);
			factAcct.setFact_Acct_ID(1);
			factAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
			factAcct.setAccount_ID(accountId.getRepoId());
			factAcct.setPostingType(PostingType.Actual.getCode());
			factAcct.setDateAcct(SystemTime.asDayTimestamp());
			factAcct.setDateTrx(factAcct.getDateAcct());
			factAcct.setC_Currency_ID(RON_ID.getRepoId());
			factAcct.setAmtSourceDr(new BigDecimal("0"));
			factAcct.setAmtSourceCr(new BigDecimal("500"));
			factAcct.setAmtAcctDr(new BigDecimal("0"));
			factAcct.setAmtAcctCr(new BigDecimal("100"));
			factAcct.setOpenItemKey(openItemKey.getAsString());
			factAcct.setOI_TrxType(FAOpenItemTrxType.OPEN_ITEM.getCode());
			factAcct.setOI_OpenAmountSource(new BigDecimal("-150"));
			factAcct.setOI_OpenAmount(new BigDecimal("-30"));
			factAcct.setIsOpenItemsReconciled(false);
			InterfaceWrapperHelper.save(factAcct);

			final SAPGLJournal glJournal = glJournal()
					.acctSchemaId(acctSchemaId)
					.sign(PostingSign.DEBIT)
					.amountSrc(Money.of("35", RON_ID))
					.amountAcct(Money.of("7", EUR_ID))
					.openItemKey(openItemKey)
					.build();
			final FutureClearingAmountMap futureClearingAmounts = FutureClearingAmountMap.ofGLJournal(glJournal, moneyService);

			final OIRow row = viewDataService.toRow(factAcct, futureClearingAmounts);
			Assertions.assertThat(row).isNotNull();
			Assertions.assertThat(row.getPostingSign()).isEqualTo(PostingSign.CREDIT);
			Assertions.assertThat(row.getAmount()).isEqualTo(Amount.of("500", RON));
			Assertions.assertThat(row.getOpenAmountEffective()).isEqualTo(Amount.of("115", RON));
		}
	}

	//
	//
	//

	private static class MockedLookupDataSourceFactory extends LookupDataSourceFactory
	{

		public MockedLookupDataSourceFactory()
		{
			super(new LookupDescriptorProviders(ADReferenceService.newMocked()));
		}

		@Override
		public LookupDataSource searchInTableLookup(final String tableName)
		{
			return MockedLookupDataSource.withNamePrefix(tableName);
		}
	}
}