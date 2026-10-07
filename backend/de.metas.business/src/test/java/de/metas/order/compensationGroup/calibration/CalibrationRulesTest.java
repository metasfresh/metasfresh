package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class CalibrationRulesTest
{
	private static final OrgId ORG_A = OrgId.ofRepoId(1000000);
	private static final OrgId ORG_B = OrgId.ofRepoId(1000001);

	private static CalibrationRule.CalibrationRuleBuilder rule(final int id, final int seqNo)
	{
		return CalibrationRule.builder()
				.id(CalibrationRuleId.ofRepoId(id))
				.seqNo(seqNo)
				.orgId(OrgId.ANY)
				.factor(BigDecimal.ONE);
	}

	private static CalibrationMatchKey.CalibrationMatchKeyBuilder key()
	{
		return CalibrationMatchKey.builder()
				.orgId(ORG_A)
				.bpartnerId(BPartnerId.ofRepoId(10))
				.bpGroupId(BPGroupId.ofRepoId(20))
				.productId(ProductId.ofRepoId(30))
				.productCategoryId(ProductCategoryId.ofRepoId(40))
				.groupTemplateId(GroupTemplateId.ofRepoId(50));
	}

	@Test
	void emptyColumnsMatchAnything()
	{
		assertThat(rule(1, 10).build().appliesTo(key().build())).isTrue();
	}

	@Test
	void everyFilledColumnMustEqual()
	{
		assertThat(rule(1, 10).bpartnerId(BPartnerId.ofRepoId(10)).build().appliesTo(key().build())).isTrue();
		assertThat(rule(1, 10).bpartnerId(BPartnerId.ofRepoId(11)).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10).bpGroupId(BPGroupId.ofRepoId(21)).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10).productId(ProductId.ofRepoId(31)).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10).productCategoryId(ProductCategoryId.ofRepoId(41)).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10).schemaId(GroupTemplateId.ofRepoId(51)).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10)
				.bpartnerId(BPartnerId.ofRepoId(10)).bpGroupId(BPGroupId.ofRepoId(20))
				.productId(ProductId.ofRepoId(30)).productCategoryId(ProductCategoryId.ofRepoId(40))
				.schemaId(GroupTemplateId.ofRepoId(50))
				.build().appliesTo(key().build())).isTrue();
	}

	@Test
	void orgMustBeAnyOrEqual()
	{
		assertThat(rule(1, 10).orgId(ORG_A).build().appliesTo(key().build())).isTrue();
		assertThat(rule(1, 10).orgId(ORG_B).build().appliesTo(key().build())).isFalse();
		assertThat(rule(1, 10).orgId(OrgId.ANY).build().appliesTo(key().build())).isTrue();
	}

	@Test
	void findFirstMatching_lowestSeqNoThenIdWins()
	{
		final CalibrationRules rules = new CalibrationRules(Arrays.asList(
				rule(3, 20).build(),
				rule(2, 10).bpartnerId(BPartnerId.ofRepoId(99)).build(),
				rule(5, 10).build(),
				rule(4, 10).build()));

		assertThat(rules.findFirstMatching(key().build()).map(r -> r.getId().getRepoId())).contains(4);
	}

	@Test
	void findFirstMatching_noMatch()
	{
		final CalibrationRules rules = new CalibrationRules(Arrays.asList(rule(1, 10).productId(ProductId.ofRepoId(31)).build()));
		assertThat(rules.findFirstMatching(key().build())).isEmpty();
	}
}
