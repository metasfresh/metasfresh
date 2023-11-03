package de.metas.acct.gljournal_sap.select_open_items;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalCurrencyConversionCtx;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FutureClearingAmountMapTest
{
	//
	// services
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

		this.EUR_ID = PlainCurrencyDAO.createCurrencyId(EUR);
		this.RON_ID = PlainCurrencyDAO.createCurrencyId(RON);
	}

	@Builder(builderMethodName = "glJournal", builderClassName = "$GLJournalBuilder")
	SAPGLJournal createGLJournal(
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
				.acctSchemaId(AcctSchemaId.ofRepoId(1))
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
	class ofGLJournal
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.parse("Acct#MyTable#1");

		@Test
		void debit_line()
		{
			final SAPGLJournal glJournal = glJournal()
					.sign(PostingSign.DEBIT)
					.amountSrc(Money.of("35", RON_ID))
					.amountAcct(Money.of("7", EUR_ID))
					.openItemKey(openItemKey)
					.build();

			assertThat(FutureClearingAmountMap.ofGLJournal(glJournal, moneyService))
					.usingRecursiveComparison()
					.isEqualTo(FutureClearingAmountMap.ofList(ImmutableList.of(
							FutureClearingAmount.builder().key(openItemKey).amountSrc(Amount.of("35", RON)).build()
					)));
		}

		@Test
		void credit_line()
		{
			final SAPGLJournal glJournal = glJournal()
					.sign(PostingSign.CREDIT)
					.amountSrc(Money.of("35", RON_ID))
					.amountAcct(Money.of("7", EUR_ID))
					.openItemKey(openItemKey)
					.build();

			assertThat(FutureClearingAmountMap.ofGLJournal(glJournal, moneyService))
					.usingRecursiveComparison()
					.isEqualTo(FutureClearingAmountMap.ofList(ImmutableList.of(
							FutureClearingAmount.builder().key(openItemKey).amountSrc(Amount.of("-35", RON)).build()
					)));
		}
	}

	@Nested
	class extractFutureClearingAmount
	{
		@Test
		void debit_line()
		{
			final FAOpenItemKey openItemKey = FAOpenItemKey.parse("Acct#MyTable#1");
			final SAPGLJournalLine line = SAPGLJournalLine.builder()
					.line(SeqNo.ofInt(10))
					.account(Account.ofId(AccountId.ofRepoId(1)))
					.postingSign(PostingSign.DEBIT)
					.amount(Money.of("500", RON_ID))
					.amountAcct(Money.of("100", EUR_ID))
					.orgId(OrgId.MAIN)
					.dimension(Dimension.EMPTY)
					.openItemTrxInfo(FAOpenItemTrxInfo.clearing(openItemKey))
					.build();

			assertThat(FutureClearingAmountMap.extractFutureClearingAmount(line, moneyService))
					.isEqualTo(FutureClearingAmount.builder().key(openItemKey).amountSrc(Amount.of("500", RON)).build());
		}

		@Test
		void credit_line()
		{
			final FAOpenItemKey openItemKey = FAOpenItemKey.parse("Acct#MyTable#1");
			final SAPGLJournalLine line = SAPGLJournalLine.builder()
					.line(SeqNo.ofInt(10))
					.account(Account.ofId(AccountId.ofRepoId(1)))
					.postingSign(PostingSign.CREDIT)
					.amount(Money.of("500", RON_ID))
					.amountAcct(Money.of("100", EUR_ID))
					.orgId(OrgId.MAIN)
					.dimension(Dimension.EMPTY)
					.openItemTrxInfo(FAOpenItemTrxInfo.clearing(openItemKey))
					.build();

			assertThat(FutureClearingAmountMap.extractFutureClearingAmount(line, moneyService))
					.isEqualTo(FutureClearingAmount.builder().key(openItemKey).amountSrc(Amount.of("-500", RON)).build());
		}

	}
}