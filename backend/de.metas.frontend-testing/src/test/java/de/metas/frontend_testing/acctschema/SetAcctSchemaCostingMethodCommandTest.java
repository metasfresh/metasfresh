package de.metas.frontend_testing.acctschema;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingLevel;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetAcctSchemaCostingMethodCommandTest
{
	private AcctSchemaId acctSchemaId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		acctSchemaId = AcctSchemaTestHelper.newAcctSchema().costingLevel(CostingLevel.Client).costingMethod(CostingMethod.StandardCosting).build();
	}

	private static I_M_CostElement createCostElement(final CostingMethod method, final boolean isActive)
	{
		final I_M_CostElement element = InterfaceWrapperHelper.newInstance(I_M_CostElement.class);
		element.setName(method.name());
		element.setCostingMethod(method.getCode());
		element.setCostElementType(CostElementType.Material.getCode());
		element.setIsActive(isActive);
		InterfaceWrapperHelper.saveRecord(element);
		return element;
	}

	@Test
	public void switchesTheMethod_activatesItsCostElement_andReturnsThePrevious()
	{
		final I_M_CostElement standard = createCostElement(CostingMethod.StandardCosting, false);
		final I_M_CostElement movingAvg = createCostElement(CostingMethod.MovingAverageInvoice, false);

		final SetAcctSchemaCostingMethodCommand.Result previous = SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod("M").build().execute();

		assertThat(previous.getPreviousCostingMethod()).isEqualTo("S");
		assertThat(previous.getActivatedCostElementIds()).containsExactly(movingAvg.getM_CostElement_ID());
		assertThat(InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class).getCostingMethod()).isEqualTo("M");
		InterfaceWrapperHelper.refresh(movingAvg);
		assertThat(movingAvg.isActive()).isTrue();

		// restoring: the method AND the cost element it activated
		final SetAcctSchemaCostingMethodCommand.Result restored = SetAcctSchemaCostingMethodCommand.builder()
				.acctSchemaId(acctSchemaId)
				.costingMethod(previous.getPreviousCostingMethod())
				.deactivateCostElementIds(previous.getActivatedCostElementIds())
				.build().execute();
		assertThat(restored.getPreviousCostingMethod()).isEqualTo("M");
		assertThat(InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class).getCostingMethod()).isEqualTo("S");
		InterfaceWrapperHelper.refresh(movingAvg);
		assertThat(movingAvg.isActive()).as("cost element activated by the switch is inactive again after restore").isFalse();
		InterfaceWrapperHelper.refresh(standard);
		assertThat(standard.isActive()).as("restore must not activate the previous method's inactive element").isFalse();
	}

	@Test
	public void noCostElementForTheMethod_fails_andLeavesTheSchemaUntouched()
	{
		assertThatThrownBy(() -> SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod("M").build().execute())
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("No material cost element");
		assertThat(InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class).getCostingMethod()).isEqualTo("S");
	}
}
