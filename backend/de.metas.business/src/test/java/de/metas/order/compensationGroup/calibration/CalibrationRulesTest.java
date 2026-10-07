package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalibrationRulesTest
{
	private static final OrgId ORG_A = OrgId.ofRepoId(1000000);
	private static final OrgId ORG_B = OrgId.ofRepoId(1000001);

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static CalibrationRule.CalibrationRuleBuilder rule(final int id, final int seqNo)
	{
		return CalibrationRule.builder()
				.id(CalibrationRuleId.ofRepoId(id))
				.seqNo(seqNo)
				.orgId(OrgId.ANY)
				.bpGroupId(BPGroupId.ofRepoId(20))
				.factor(Percent.ONE_HUNDRED);
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
		assertThat(rule(1, 10).bpGroupId(null).bpartnerId(BPartnerId.ofRepoId(10)).build().appliesTo(key().build())).isTrue();
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
	void requiresBPartnerOrBPGroup()
	{
		assertThatThrownBy(() -> rule(1, 10).bpGroupId(null).build())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_BPartnerOrGroupRequired");
	}

	@Test
	void rejectsNegativeFactor()
	{
		assertThatThrownBy(() -> rule(1, 10).factor(Percent.of(-1)).build())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_NegativeFactor");
	}

	@Test
	void findFirstMatching_lowestSeqNoThenIdWins()
	{
		final CalibrationRules rules = CalibrationRules.of(Arrays.asList(
				rule(3, 20).build(),
				rule(2, 10).bpartnerId(BPartnerId.ofRepoId(99)).build(),
				rule(5, 10).build(),
				rule(4, 10).build()));

		//noinspection DataFlowIssue
		assertThat(rules.findFirstMatching(key().build()).map(r -> r.getId().getRepoId())).contains(4);
	}

	@Test
	void ordering_ruleWithoutIdSortsBeforeRulesWithIdOnSameSeqNo()
	{
		final CalibrationRule unsaved = rule(1, 10).id(null).build();
		final CalibrationRules rules = CalibrationRules.of(Arrays.asList(
				rule(2, 10).build(),
				unsaved,
				rule(3, 5).build()));

		assertThat(rules.asList()).extracting(CalibrationRule::getId)
				.containsExactly(CalibrationRuleId.ofRepoId(3), null, CalibrationRuleId.ofRepoId(2));
	}

	@Test
	void findFirstMatching_noMatch()
	{
		final CalibrationRules rules = CalibrationRules.of(Collections.singletonList(
				rule(1, 10).productId(ProductId.ofRepoId(31)).build()
		));
		
		assertThat(rules.findFirstMatching(key().build())).isEmpty();
	}
}
