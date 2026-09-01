/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.interceptor;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_CostElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers the guard that refuses switching {@code C_AcctSchema.CostingMethod} to a costing method whose
 * material cost element has no {@code M_CostDetail} rows at all in that accounting schema.
 */
public class C_AcctSchema_CostingMethodSeededGuardTest
{
	private C_AcctSchema interceptor;
	private AcctSchemaId acctSchemaId;
	private I_C_AcctSchema acctSchema;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ICostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		final ICurrentCostsRepository currentCostsRepository = new CurrentCostsRepository(costElementRepo);
		final ICostDetailRepository costDetailRepository = new CostDetailRepository();
		interceptor = new C_AcctSchema(costElementRepo, currentCostsRepository, costDetailRepository);

		acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingMethod(CostingMethod.StandardCosting)
				.build();
		acctSchema = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
	}

	@Test
	@DisplayName("refuses the switch when the target method's material cost element has no cost details")
	public void refuse_whenTargetMethodHasNoCostDetails()
	{
		createMaterialCostElement(CostingMethod.MovingAverageInvoice);
		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());

		assertThatThrownBy(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("refuses the switch when the target method has no material cost element at all")
	public void refuse_whenTargetMethodHasNoCostElement()
	{
		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());

		assertThatThrownBy(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("refuses the switch when only the PREVIOUS method's cost element has cost details")
	public void refuse_whenOnlyPreviousMethodHasCostDetails()
	{
		final CostElementId previousCostElementId = createMaterialCostElement(CostingMethod.StandardCosting);
		createCostDetail(acctSchemaId, previousCostElementId);
		createMaterialCostElement(CostingMethod.MovingAverageInvoice);

		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());

		assertThatThrownBy(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("refuses the switch when the cost details belong to a DIFFERENT accounting schema")
	public void refuse_whenCostDetailsBelongToAnotherAcctSchema()
	{
		final AcctSchemaId otherAcctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingMethod(CostingMethod.MovingAverageInvoice)
				.build();
		final CostElementId targetCostElementId = createMaterialCostElement(CostingMethod.MovingAverageInvoice);
		createCostDetail(otherAcctSchemaId, targetCostElementId);

		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());

		assertThatThrownBy(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	@DisplayName("allows the switch once the target method's cost element has cost details in this schema")
	public void allow_whenTargetMethodIsSeeded()
	{
		final CostElementId targetCostElementId = createMaterialCostElement(CostingMethod.MovingAverageInvoice);
		createCostDetail(acctSchemaId, targetCostElementId);

		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());

		assertThatCode(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("skips the check when the caller opted out via DISABLE_CHECK_COSTING_METHOD_SEEDED")
	public void allow_whenOptedOut()
	{
		createMaterialCostElement(CostingMethod.MovingAverageInvoice);
		acctSchema.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		C_AcctSchema.DISABLE_CHECK_COSTING_METHOD_SEEDED.setValue(acctSchema, Boolean.TRUE);

		assertThatCode(() -> interceptor.assertTargetCostingMethodIsSeeded(acctSchema))
				.doesNotThrowAnyException();
	}

	private CostElementId createMaterialCostElement(final CostingMethod costingMethod)
	{
		final I_M_CostElement record = newInstance(I_M_CostElement.class);
		record.setName("CostElement-" + costingMethod.getCode());
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(costingMethod.getCode());
		saveRecord(record);
		return CostElementId.ofRepoId(record.getM_CostElement_ID());
	}

	private void createCostDetail(final AcctSchemaId acctSchemaId, final CostElementId costElementId)
	{
		final I_M_CostDetail record = newInstance(I_M_CostDetail.class);
		record.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		record.setM_CostElement_ID(costElementId.getRepoId());
		saveRecord(record);
	}
}
