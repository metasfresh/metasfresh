/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.process;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.eevolution.api.impl.MockedProductCostingBL;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the precondition gate of {@link PP_Order_PostCalculation}: when the "post calculation" action is
 * offered on a manufacturing order.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PP_Order_PostCalculationTest
{
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(0);

	private CurrencyId currencyId;
	private ProductId finishedGoodId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);
		Env.setOrgId(Env.getCtx(), orgId);

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		finishedGoodId = BusinessTestHelper.createProductId("finished good", BusinessTestHelper.createUomEach());

		// the process resolves it in a field initializer; doIt() is not under test here
		SpringContextHolder.registerJUnitBean(PPOrderCostDifferenceDistributor.class, Mockito.mock(PPOrderCostDifferenceDistributor.class));
	}

	/** The costing methods whose manufacturing handlers accumulate into {@code PP_Order_Cost}. */
	private enum EligibleCostingMethod
	{
		AveragePO(CostingMethod.AveragePO),
		LastPOPrice(CostingMethod.LastPOPrice),
		MovingAverageInvoice(CostingMethod.MovingAverageInvoice);

		final CostingMethod costingMethod;

		EligibleCostingMethod(@NonNull final CostingMethod costingMethod) {this.costingMethod = costingMethod;}
	}

	@ParameterizedTest
	@EnumSource(EligibleCostingMethod.class)
	void offered_whenAcctSchemaAccumulatesOrderCosts(@NonNull final EligibleCostingMethod eligible)
	{
		givenAcctSchemaCostingMethod(eligible.costingMethod);

		assertThat(checkPreconditions(ppOrder(DocStatus.Completed)).isRejected()).isFalse();
	}

	@Test
	void notOffered_whenAcctSchemaIsStandardCosting()
	{
		// standard costing values everything at standard and accumulates nothing, so there is no residual to discharge
		givenAcctSchemaCostingMethod(CostingMethod.StandardCosting);

		assertThat(checkPreconditions(ppOrder(DocStatus.Completed)).isRejected()).isTrue();
	}

	@Test
	void notOffered_whenOrderIsClosed()
	{
		givenAcctSchemaCostingMethod(CostingMethod.AveragePO);

		assertThat(checkPreconditions(ppOrder(DocStatus.Closed)).isRejected()).isTrue();
	}

	@Test
	void notOffered_whenOrderIsNotCompleted()
	{
		givenAcctSchemaCostingMethod(CostingMethod.AveragePO);

		assertThat(checkPreconditions(ppOrder(DocStatus.Drafted)).isRejected()).isTrue();
		assertThat(checkPreconditions(ppOrder(DocStatus.InProgress)).isRejected()).isTrue();
	}

	/**
	 * The costing method has to come from the accounting schema, not from the product: only a cost element
	 * matching the schema's method is accountable, so a per-M_Product_Category_Acct override would disagree
	 * with what actually posts.
	 */
	@Test
	void schemaWinsOverProductCategoryOverride_whenTheProductSaysStandardCosting()
	{
		givenAcctSchemaCostingMethod(CostingMethod.AveragePO);
		givenProductCostingMethod(CostingMethod.StandardCosting);

		assertThat(checkPreconditions(ppOrder(DocStatus.Completed)).isRejected()).isFalse();
	}

	@Test
	void schemaWinsOverProductCategoryOverride_whenTheProductSaysAveragePO()
	{
		givenAcctSchemaCostingMethod(CostingMethod.StandardCosting);
		givenProductCostingMethod(CostingMethod.AveragePO);

		assertThat(checkPreconditions(ppOrder(DocStatus.Completed)).isRejected()).isTrue();
	}

	@Test
	void notOffered_whenMoreThanOneOrderSelected()
	{
		givenAcctSchemaCostingMethod(CostingMethod.AveragePO);

		final ProcessPreconditionsResolution resolution = new PP_Order_PostCalculation()
				.checkPreconditionsApplicable(new PreconditionsContext(ppOrder(DocStatus.Completed), SelectionSize.ofSize(2)));

		assertThat(resolution.isRejected()).isTrue();
	}

	private void givenAcctSchemaCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(costingMethod)
				.currencyId(currencyId)
				.build();
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);
	}

	/** what {@code IProductCostingBL} would answer, i.e. the per-product-category override */
	private void givenProductCostingMethod(@NonNull final CostingMethod costingMethod)
	{
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, costingMethod));
	}

	private I_PP_Order ppOrder(@NonNull final DocStatus docStatus)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		InterfaceWrapperHelper.setValue(ppOrder, I_PP_Order.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		ppOrder.setAD_Org_ID(orgId.getRepoId());
		ppOrder.setM_Product_ID(finishedGoodId.getRepoId());
		ppOrder.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.saveRecord(ppOrder);
		return ppOrder;
	}

	private ProcessPreconditionsResolution checkPreconditions(@NonNull final I_PP_Order ppOrder)
	{
		return new PP_Order_PostCalculation()
				.checkPreconditionsApplicable(new PreconditionsContext(ppOrder, SelectionSize.ofSize(1)));
	}

	/** minimal stand-in for the WebUI's context; only the selection is relevant to the gate */
	private static class PreconditionsContext implements IProcessPreconditionsContext
	{
		private final I_PP_Order selectedRecord;
		private final SelectionSize selectionSize;

		PreconditionsContext(@NonNull final I_PP_Order selectedRecord, @NonNull final SelectionSize selectionSize)
		{
			this.selectedRecord = selectedRecord;
			this.selectionSize = selectionSize;
		}

		@Nullable
		@Override
		public AdWindowId getAdWindowId() {return null;}

		@Nullable
		@Override
		public AdTabId getAdTabId() {return null;}

		@Override
		public String getTableName() {return I_PP_Order.Table_Name;}

		@Override
		public <T> T getSelectedModel(final Class<T> modelClass) {return InterfaceWrapperHelper.create(selectedRecord, modelClass);}

		@Override
		public <T> List<T> getSelectedModels(final Class<T> modelClass) {return ImmutableList.of(getSelectedModel(modelClass));}

		@NonNull
		@Override
		public <T> Stream<T> streamSelectedModels(@NonNull final Class<T> modelClass) {return getSelectedModels(modelClass).stream();}

		@Override
		public int getSingleSelectedRecordId() {return selectedRecord.getPP_Order_ID();}

		@Override
		public SelectionSize getSelectionSize() {return selectionSize;}

		@Override
		public <T> IQueryFilter<T> getQueryFilter(@NonNull final Class<T> recordClass) {throw new UnsupportedOperationException();}
	}
}
