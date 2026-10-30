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
		createCostElement(CostingMethod.StandardCosting, true);
		final I_M_CostElement movingAvg = createCostElement(CostingMethod.MovingAverageInvoice, false);

		final String previous = SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod("M").build().execute();

		assertThat(previous).isEqualTo("S");
		assertThat(InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class).getCostingMethod()).isEqualTo("M");
		InterfaceWrapperHelper.refresh(movingAvg);
		assertThat(movingAvg.isActive()).isTrue();

		// restoring
		assertThat(SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod(previous).build().execute()).isEqualTo("M");
		assertThat(InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class).getCostingMethod()).isEqualTo("S");
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
