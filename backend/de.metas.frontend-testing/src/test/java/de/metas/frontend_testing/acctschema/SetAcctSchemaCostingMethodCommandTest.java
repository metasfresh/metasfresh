package de.metas.frontend_testing.acctschema;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.CostingLevel;
import de.metas.costing.impl.CostElementRepository;
import org.adempiere.service.ClientId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.compiere.model.I_AD_Client;
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

	/**
	 * Failure scenario: two active material cost elements of the same method make every cost detail, M_Cost row and
	 * posting appear twice (doubled Fact_Acct lines), so the switch must not activate a second one.
	 */
	@Test
	public void methodAlreadyHasAnActiveCostElement_activatesNothing()
	{
		final I_M_CostElement inactive = createCostElement(CostingMethod.MovingAverageInvoice, false);
		final I_M_CostElement active = createCostElement(CostingMethod.MovingAverageInvoice, true);

		final SetAcctSchemaCostingMethodCommand.Result result = SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod("M").build().execute();

		assertThat(result.getActivatedCostElementIds()).isEmpty();
		InterfaceWrapperHelper.refresh(inactive);
		assertThat(inactive.isActive()).isFalse();
		InterfaceWrapperHelper.refresh(active);
		assertThat(active.isActive()).isTrue();
	}

	/**
	 * Real-life case (CI, fresh DB): saving the new costing method makes {@code C_AcctSchema.checkCosting} call
	 * {@code getOrCreateMaterialCostElement}, which only sees ACTIVE elements and so creates a NEW active element when the
	 * method's seeded one is inactive. The command must then not activate the seeded one too, else the method has two
	 * active material cost elements (doubled postings; "Expected exactly one material cost element" on close).
	 * This mimics that interceptor (same call as {@code C_AcctSchema#checkCosting}).
	 */
	@Test
	public void schemaSaveAutoCreatesAnActiveCostElement_doesNotActivateTheSeededOneAsWell()
	{
		final CostElementRepository costElementRepository = new CostElementRepository(ADReferenceService.newMocked());
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new AbstractModelInterceptor()
		{
			@Override
			protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
			{
				engine.addModelChange(I_C_AcctSchema.Table_Name, this);
			}

			@Override
			public void onModelChange(final Object model, final ModelChangeType changeType)
			{
				if (changeType.isBefore() && changeType.isChange())
				{
					final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.create(model, I_C_AcctSchema.class);
					costElementRepository.getOrCreateMaterialCostElement(ClientId.ofRepoId(acctSchema.getAD_Client_ID()), CostingMethod.ofCode(acctSchema.getCostingMethod()));
				}
			}
		});
		final I_M_CostElement seeded = createCostElement(CostingMethod.MovingAverageInvoice, false);

		SetAcctSchemaCostingMethodCommand.builder().acctSchemaId(acctSchemaId).costingMethod("M").build().execute();

		final long activeMaterialElements = Services.get(org.adempiere.ad.dao.IQueryBL.class).createQueryBuilder(I_M_CostElement.class)
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostingMethod, CostingMethod.MovingAverageInvoice.getCode())
				.addEqualsFilter(I_M_CostElement.COLUMNNAME_CostElementType, CostElementType.Material.getCode())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
		assertThat(activeMaterialElements).as("active MAI material cost elements").isEqualTo(1);
		InterfaceWrapperHelper.refresh(seeded);
		assertThat(seeded.isActive()).as("seeded element stays inactive").isFalse();
	}
}
