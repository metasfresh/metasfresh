package de.metas.acct.account_info.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.impl.ElementValueId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AccountHierarchyViewDataServiceTest
{
	@Test
	void testBuildTree_simpleHierarchy()
	{
		// L1 root (id=1) → L2 child (id=2) → Account leaf (id=3)
		final List<AccountHierarchyViewDataService.FlatAccountRecord> flatRecords = ImmutableList.of(
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(1))
						.value("1")
						.name("Aktiva")
						.isSummary(true)
						.parentElementValueId(null)
						.balance(BigDecimal.ZERO)
						.drYTD(BigDecimal.ZERO)
						.crYTD(BigDecimal.ZERO)
						.build(),
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(2))
						.value("10")
						.name("Umlaufvermögen")
						.isSummary(true)
						.parentElementValueId(ElementValueId.ofRepoId(1))
						.balance(BigDecimal.ZERO)
						.drYTD(BigDecimal.ZERO)
						.crYTD(BigDecimal.ZERO)
						.build(),
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(3))
						.value("1000")
						.name("Forderungen aus L&L")
						.isSummary(false)
						.parentElementValueId(ElementValueId.ofRepoId(2))
						.balance(new BigDecimal("500"))
						.drYTD(new BigDecimal("1000"))
						.crYTD(new BigDecimal("500"))
						.build()
		);

		final ImmutableList<AccountHierarchyRow> tree = AccountHierarchyViewDataService.buildTree(flatRecords);

		// Should have 1 top-level row (the root)
		assertThat(tree).hasSize(1);

		// Root (L1)
		final AccountHierarchyRow root = tree.get(0);
		assertThat(root.getElementValueId()).isEqualTo(ElementValueId.ofRepoId(1));
		assertThat(root.getRowType()).isEqualTo(AccountHierarchyRowType.L1);
		assertThat(root.getIncludedRows()).hasSize(1);
		// Root's balance should be aggregated from children
		assertThat(root.getBalance()).isEqualByComparingTo(new BigDecimal("500"));

		// L2 child
		final AccountHierarchyRow l2 = root.getIncludedRows().get(0);
		assertThat(l2.getElementValueId()).isEqualTo(ElementValueId.ofRepoId(2));
		assertThat(l2.getIncludedRows()).hasSize(1);
		assertThat(l2.getBalance()).isEqualByComparingTo(new BigDecimal("500"));

		// Account leaf
		final AccountHierarchyRow leaf = l2.getIncludedRows().get(0);
		assertThat(leaf.getElementValueId()).isEqualTo(ElementValueId.ofRepoId(3));
		assertThat(leaf.getRowType()).isEqualTo(AccountHierarchyRowType.ACCOUNT);
		assertThat(leaf.getIncludedRows()).isEmpty();
		assertThat(leaf.getBalance()).isEqualByComparingTo(new BigDecimal("500"));
		assertThat(leaf.getDebitYTD()).isEqualByComparingTo(new BigDecimal("1000"));
		assertThat(leaf.getCreditYTD()).isEqualByComparingTo(new BigDecimal("500"));
	}

	@Test
	void testBuildTree_multipleRoots()
	{
		final List<AccountHierarchyViewDataService.FlatAccountRecord> flatRecords = ImmutableList.of(
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(1))
						.value("1")
						.name("Aktiva")
						.isSummary(true)
						.parentElementValueId(null)
						.balance(BigDecimal.ZERO)
						.drYTD(BigDecimal.ZERO)
						.crYTD(BigDecimal.ZERO)
						.build(),
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(2))
						.value("2")
						.name("Passiva")
						.isSummary(true)
						.parentElementValueId(null)
						.balance(BigDecimal.ZERO)
						.drYTD(BigDecimal.ZERO)
						.crYTD(BigDecimal.ZERO)
						.build()
		);

		final ImmutableList<AccountHierarchyRow> tree = AccountHierarchyViewDataService.buildTree(flatRecords);

		assertThat(tree).hasSize(2);
	}

	@Test
	void testBuildTree_emptyInput()
	{
		final ImmutableList<AccountHierarchyRow> tree = AccountHierarchyViewDataService.buildTree(ImmutableList.of());
		assertThat(tree).isEmpty();
	}

	@Test
	void testBuildTree_balanceAggregation()
	{
		// Root with two children — balance should be sum
		final List<AccountHierarchyViewDataService.FlatAccountRecord> flatRecords = ImmutableList.of(
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(1))
						.value("1")
						.name("Aktiva")
						.isSummary(true)
						.parentElementValueId(null)
						.balance(BigDecimal.ZERO)
						.drYTD(BigDecimal.ZERO)
						.crYTD(BigDecimal.ZERO)
						.build(),
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(10))
						.value("1000")
						.name("Konto A")
						.isSummary(false)
						.parentElementValueId(ElementValueId.ofRepoId(1))
						.balance(new BigDecimal("300"))
						.drYTD(new BigDecimal("300"))
						.crYTD(BigDecimal.ZERO)
						.build(),
				AccountHierarchyViewDataService.FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(11))
						.value("1001")
						.name("Konto B")
						.isSummary(false)
						.parentElementValueId(ElementValueId.ofRepoId(1))
						.balance(new BigDecimal("200"))
						.drYTD(new BigDecimal("200"))
						.crYTD(BigDecimal.ZERO)
						.build()
		);

		final ImmutableList<AccountHierarchyRow> tree = AccountHierarchyViewDataService.buildTree(flatRecords);

		assertThat(tree).hasSize(1);
		final AccountHierarchyRow root = tree.get(0);
		assertThat(root.getBalance()).isEqualByComparingTo(new BigDecimal("500"));
		assertThat(root.getDebitYTD()).isEqualByComparingTo(new BigDecimal("500"));
		assertThat(root.getIncludedRows()).hasSize(2);
	}
}
