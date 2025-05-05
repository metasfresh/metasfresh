package de.metas.invoice.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceAcctRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void save_and_get_standardCase()
	{
		InvoiceAcctRepository repo = new InvoiceAcctRepository();

		final InvoiceAcct invoiceAcctToSave = InvoiceAcct.builder()
				.invoiceId(InvoiceId.ofRepoId(101))
				.orgId(OrgId.ofRepoId(102))
				.rules(ImmutableList.of(
						InvoiceAcctRule.builder()
								.matcher(InvoiceAcctRuleMatcher.builder()
										.acctSchemaId(AcctSchemaId.ofRepoId(901))
										.invoiceAndLineId(InvoiceAndLineId.ofRepoId(101, 141))
										.accountConceptualName(AccountConceptualName.ofString("Some_Acct"))
										.build())
								.elementValueId(ElementValueId.ofRepoId(902))
								.build(),
						InvoiceAcctRule.builder()
								.matcher(InvoiceAcctRuleMatcher.builder()
										.acctSchemaId(AcctSchemaId.ofRepoId(902))
										.build())
								.elementValueId(ElementValueId.ofRepoId(902))
								.build()
				))
				.build();

		repo.save(invoiceAcctToSave);

		Assertions.assertThat(repo.getById(InvoiceId.ofRepoId(101))).contains(invoiceAcctToSave);
	}

}