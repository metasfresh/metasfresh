package de.metas.acct.account_info.bpartner_balance;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.currency.CurrencyCode;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.elementvalue.AccountType;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import org.adempiere.acct.api.IFactAcctBL;
import de.metas.acct.api.IAcctSchemaBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Fact_Acct;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BPartnerBalanceViewDataServiceTest
{
	private BPartnerBalanceViewDataService service;
	private ElementValueService elementValueService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		elementValueService = mock(ElementValueService.class);
		when(elementValueService.getById(any())).thenReturn(ElementValue.builder()
				.id(ElementValueId.ofRepoId(1))
				.orgId(OrgId.ANY)
				.chartOfAccountsId(ChartOfAccountsId.ofRepoId(1))
				.value("1000")
				.name("Test Account")
				.accountSign("N")
				.accountType(AccountType.Asset)
				.isActive(true)
				.isSummary(false)
				.isDocControlled(false)
				.isPostActual(true)
				.isPostBudget(false)
				.isPostStatistical(false)
				.isOpenItem(false)
				.seqNo(10)
				.build());

		service = BPartnerBalanceViewDataService.builder()
				.factAcctBL(mock(IFactAcctBL.class))
				.acctSchemaBL(mock(IAcctSchemaBL.class))
				.moneyService(mock(MoneyService.class))
				.elementValueService(elementValueService)
				.build();
	}

	@Test
	void testRunningBalanceComputation()
	{
		final I_Fact_Acct record1 = createFactAcct(1, "2026-01-10", new BigDecimal("100"), BigDecimal.ZERO);
		final I_Fact_Acct record2 = createFactAcct(2, "2026-01-15", BigDecimal.ZERO, new BigDecimal("30"));
		final I_Fact_Acct record3 = createFactAcct(3, "2026-01-20", new BigDecimal("50"), BigDecimal.ZERO);

		final CurrencyCode eur = CurrencyCode.ofThreeLetterCode("EUR");

		final ImmutableList<BPartnerBalanceRow> rows = service.computeRunningBalances(
				java.util.stream.Stream.of(record1, record2, record3),
				eur);

		assertThat(rows).hasSize(3);

		// Row 1: DR=100, CR=0 => balance = 100
		assertThat(rows.get(0).getRunningBalance()).isEqualByComparingTo(new BigDecimal("100"));

		// Row 2: DR=0, CR=30 => balance = 100 - 30 = 70
		assertThat(rows.get(1).getRunningBalance()).isEqualByComparingTo(new BigDecimal("70"));

		// Row 3: DR=50, CR=0 => balance = 70 + 50 = 120
		assertThat(rows.get(2).getRunningBalance()).isEqualByComparingTo(new BigDecimal("120"));
	}

	@Test
	void testRunningBalanceWithNegativeResult()
	{
		final I_Fact_Acct record1 = createFactAcct(1, "2026-02-01", BigDecimal.ZERO, new BigDecimal("200"));
		final I_Fact_Acct record2 = createFactAcct(2, "2026-02-05", new BigDecimal("50"), BigDecimal.ZERO);

		final CurrencyCode eur = CurrencyCode.ofThreeLetterCode("EUR");

		final ImmutableList<BPartnerBalanceRow> rows = service.computeRunningBalances(
				java.util.stream.Stream.of(record1, record2),
				eur);

		assertThat(rows).hasSize(2);
		assertThat(rows.get(0).getRunningBalance()).isEqualByComparingTo(new BigDecimal("-200"));
		assertThat(rows.get(1).getRunningBalance()).isEqualByComparingTo(new BigDecimal("-150"));
	}

	@Test
	void testEmptyStream()
	{
		final CurrencyCode eur = CurrencyCode.ofThreeLetterCode("EUR");

		final ImmutableList<BPartnerBalanceRow> rows = service.computeRunningBalances(
				java.util.stream.Stream.empty(),
				eur);

		assertThat(rows).isEmpty();
	}

	@Test
	void testSortingByDateAndId()
	{
		// Records in wrong order — should be sorted by dateAcct then by ID
		final I_Fact_Acct record1 = createFactAcct(3, "2026-01-20", new BigDecimal("10"), BigDecimal.ZERO);
		final I_Fact_Acct record2 = createFactAcct(1, "2026-01-10", new BigDecimal("100"), BigDecimal.ZERO);
		final I_Fact_Acct record3 = createFactAcct(2, "2026-01-10", new BigDecimal("50"), BigDecimal.ZERO);

		final CurrencyCode eur = CurrencyCode.ofThreeLetterCode("EUR");

		final ImmutableList<BPartnerBalanceRow> rows = service.computeRunningBalances(
				java.util.stream.Stream.of(record1, record2, record3),
				eur);

		assertThat(rows).hasSize(3);
		// Sorted: record2 (id=1, 2026-01-10), record3 (id=2, 2026-01-10), record1 (id=3, 2026-01-20)
		assertThat(rows.get(0).getDateAcct()).isEqualTo(LocalDate.of(2026, 1, 10));
		assertThat(rows.get(0).getRunningBalance()).isEqualByComparingTo(new BigDecimal("100"));
		assertThat(rows.get(1).getRunningBalance()).isEqualByComparingTo(new BigDecimal("150"));
		assertThat(rows.get(2).getRunningBalance()).isEqualByComparingTo(new BigDecimal("160"));
	}

	private I_Fact_Acct createFactAcct(final int factAcctId, final String dateAcct, final BigDecimal amtDr, final BigDecimal amtCr)
	{
		final I_Fact_Acct record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_Fact_Acct.class);
		record.setFact_Acct_ID(factAcctId);
		record.setDateAcct(Timestamp.valueOf(dateAcct + " 00:00:00"));
		record.setAmtAcctDr(amtDr);
		record.setAmtAcctCr(amtCr);
		record.setAccount_ID(1);
		record.setDocumentNo("DOC-" + factAcctId);
		record.setDescription("Test " + factAcctId);
		record.setAD_Table_ID(318); // C_Invoice
		record.setRecord_ID(factAcctId * 100);
		return record;
	}
}
