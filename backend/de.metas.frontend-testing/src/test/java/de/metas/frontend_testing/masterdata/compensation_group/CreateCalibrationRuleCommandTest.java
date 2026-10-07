package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.calibration.CalibrationRuleId;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link CreateCalibrationRuleCommand}: writes a {@code C_CompensationGroup_CalibrationRule} from the identifiers of the request.
 */
public class CreateCalibrationRuleCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("bp"), BPartnerId.ofRepoId(11));
		context.putIdentifier(Identifier.ofString("grp"), BPGroupId.ofRepoId(12));
		context.putIdentifier(Identifier.ofString("prod"), ProductId.ofRepoId(13));
		context.putIdentifier(Identifier.ofString("cat"), ProductCategoryId.ofRepoId(14));
		context.putIdentifier(Identifier.ofString("schema"), GroupTemplateId.ofRepoId(15));
	}

	private JsonCalibrationRuleResponse execute(final String identifier, final JsonCalibrationRuleRequest request)
	{
		return CreateCalibrationRuleCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	@Test
	public void execute_withAllFields_persistsEveryColumn_andRegistersIdentifier()
	{
		final JsonCalibrationRuleResponse response = execute("rule1", JsonCalibrationRuleRequest.builder()
				.seqNo(30)
				.bpartner(Identifier.ofString("bp"))
				.product(Identifier.ofString("prod"))
				.productCategory(Identifier.ofString("cat"))
				.schema(Identifier.ofString("schema"))
				.factor(new BigDecimal("1.5"))
				.build());

		final I_C_CompensationGroup_CalibrationRule rule = InterfaceWrapperHelper.load(response.getId(), I_C_CompensationGroup_CalibrationRule.class);
		assertThat(rule.isActive()).isTrue();
		assertThat(rule.getSeqNo()).isEqualTo(30);
		assertThat(rule.getC_BPartner_ID()).isEqualTo(11);
		assertThat(rule.getC_BP_Group_ID()).isZero();
		assertThat(rule.getM_Product_ID()).isEqualTo(13);
		assertThat(rule.getM_Product_Category_ID()).isEqualTo(14);
		assertThat(rule.getC_CompensationGroup_Schema_ID()).isEqualTo(15);
		assertThat(rule.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("1.5");
		assertThat(context.getId(Identifier.ofString("rule1"), CalibrationRuleId.class))
				.isEqualTo(response.getId());
	}

	@Test
	public void execute_withBpGroupOnly_leavesOptionalColumnsUnset()
	{
		final JsonCalibrationRuleResponse response = execute("rule2", JsonCalibrationRuleRequest.builder()
				.seqNo(10)
				.bpGroup(Identifier.ofString("grp"))
				.factor(BigDecimal.TEN)
				.build());

		final I_C_CompensationGroup_CalibrationRule rule = InterfaceWrapperHelper.load(response.getId(), I_C_CompensationGroup_CalibrationRule.class);
		assertThat(rule.getC_BP_Group_ID()).isEqualTo(12);
		assertThat(rule.getC_BPartner_ID()).isZero();
		assertThat(rule.getM_Product_ID()).isZero();
		assertThat(rule.getM_Product_Category_ID()).isZero();
		assertThat(rule.getC_CompensationGroup_Schema_ID()).isZero();
	}

	@Test
	public void execute_withoutSeqNo_appendsAfterTheHighestExisting()
	{
		execute("ruleA", JsonCalibrationRuleRequest.builder().seqNo(40).bpGroup(Identifier.ofString("grp")).factor(BigDecimal.ONE).build());
		final JsonCalibrationRuleResponse b = execute("ruleB", JsonCalibrationRuleRequest.builder().bpGroup(Identifier.ofString("grp")).factor(BigDecimal.ONE).build());

		assertThat(InterfaceWrapperHelper.load(b.getId(), I_C_CompensationGroup_CalibrationRule.class).getSeqNo()).isEqualTo(50);
	}
}
