package de.metas.purchasecandidate.purchaseordercreation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidate.PurchaseCandidateBuilder;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.purchaseordercreation.localorder.MockedPurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class PurchaseCandidateToOrderWorkflowTest
{
	private static final String SOMETHING_WENT_WRONG = "something went wrong";

	private VendorGatewayInvokerFactory vendorGatewayInvokerFactory;
	private MockedPurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator;
	private PurchaseCandidateToOrderWorkflow workflowUnderTest;

	private int nextPurchaseCandidateId = 1000000;

	private I_C_UOM EACH;
	private Quantity ONE;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, 5);

		EACH = createUOM("Ea");
		ONE = Quantity.of(BigDecimal.ONE, EACH);

		final PurchaseCandidateRepository purchaseCandidateRepo = Mockito.mock(PurchaseCandidateRepository.class);
		vendorGatewayInvokerFactory = Mockito.mock(VendorGatewayInvokerFactory.class);
		purchaseOrderFromItemsAggregator = new MockedPurchaseOrderFromItemsAggregator();
		workflowUnderTest = PurchaseCandidateToOrderWorkflow.builder()
				.purchaseCandidateRepo(purchaseCandidateRepo)
				.vendorGatewayInvokerFactory(vendorGatewayInvokerFactory)
				.purchaseOrderFromItemsAggregator(purchaseOrderFromItemsAggregator)
				.build();
	}

	private I_C_UOM createUOM(final String name)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		save(uom);
		return uom;
	}

	@Test
	public void executeForPurchaseCandidates_prevent_duplicates()
	{
		final PurchaseCandidate candidate1 = preparePurchaseCandidate().build();
		final PurchaseCandidate candidate2 = preparePurchaseCandidate().id(candidate1.getId()).build();
		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		assertThatThrownBy(() -> workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates))
				.hasMessageStartingWith("The given purchaseCandidates need to have unique IDs that are all > 0");
	}

	@Test
	public void executeForPurchaseCandidates()
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(20);

		final PurchaseCandidate candidate1 = preparePurchaseCandidate().vendorId(vendorId).build();
		final PurchaseCandidate candidate2 = preparePurchaseCandidate().vendorId(vendorId).build();

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		useVendorGatewayInvoker(vendorId, NullVendorGatewayInvoker.INSTANCE);

		// invoke the method under test
		workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates);

		assertThatBothCandidatesWereAddedToOrderAggregator();

		assertThat(candidate1.getPurchaseOrderItems()).hasSize(1);
		assertThat(candidate1.getPurchaseErrorItems()).isEmpty();

		assertThat(candidate2.getPurchaseOrderItems()).hasSize(1);
		assertThat(candidate2.getPurchaseErrorItems()).isEmpty();
	}

	private void assertThatBothCandidatesWereAddedToOrderAggregator()
	{
		assertThat(purchaseOrderFromItemsAggregator.getAddAllCalls()).hasSize(1);

		final Iterator<PurchaseOrderItem> iterator = purchaseOrderFromItemsAggregator.getAddAllCalls().get(0).getItems();
		assertThat(iterator).isNotNull();
		assertThat(iterator).as("iterator is not consumed").hasNext();
		assertThat(iterator).toIterable().hasSize(2);
	}

	@Test
	public void executeForPurchaseCandidates_prevent_multiple_vendorIds()
	{
		final PurchaseCandidate candidate1 = preparePurchaseCandidate().vendorId(BPartnerId.ofRepoId(20)).build();
		final PurchaseCandidate candidate2 = preparePurchaseCandidate().vendorId(BPartnerId.ofRepoId(30)).build();

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// invoke the method under test
		assertThatThrownBy(() -> workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates))
				.hasMessageStartingWith("Error: The given purchaseCandidates have different vendorIds");
	}

	@Test
	public void executeForPurchaseCandidates_VendorGatewayException()
	{
		final BPartnerId vendorId = BPartnerId.ofRepoId(20);

		final PurchaseCandidate candidate1 = preparePurchaseCandidate().vendorId(vendorId).build();
		final PurchaseCandidate candidate2 = preparePurchaseCandidate().vendorId(vendorId).build();

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		useVendorGatewayInvoker(vendorId, new VendorGatewayInvoker()
		{
			@Override
			public List<PurchaseItem> placeRemotePurchaseOrder(final Collection<PurchaseCandidate> purchaseCandidates)
			{
				throw new RuntimeException(SOMETHING_WENT_WRONG);
			}

			@Override
			public void updateRemoteLineReferences(final Collection<PurchaseOrderItem> purchaseOrderItem)
			{
				throw new UnsupportedOperationException();
			}
		});

		// invoke the method under test
		assertThatThrownBy(() -> workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates))
				.hasMessageContaining(SOMETHING_WENT_WRONG);

		assertThatNoCandidateWasAddedToOrderAggregator();

		assertThat(candidate1.getPurchaseOrderItems()).isEmpty();
		assertThat(candidate1.getPurchaseErrorItems()).hasSize(1);

		assertThat(candidate2.getPurchaseOrderItems()).isEmpty();
		assertThat(candidate2.getPurchaseErrorItems()).hasSize(1);

		assertThat(purchaseCandidates).allSatisfy(candidate -> {
			final Throwable candidate2Throwable = candidate.getPurchaseErrorItems().get(0).getThrowable();
			assertThat(candidate2Throwable).hasMessageContaining(SOMETHING_WENT_WRONG);
		});
	}

	@Test
	public void executeForPurchaseCandidates_not_sorted()
	{
		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(
				preparePurchaseCandidate().warehouseId(WarehouseId.ofRepoId(1)).build(),
				preparePurchaseCandidate().warehouseId(WarehouseId.ofRepoId(2)).build(),
				preparePurchaseCandidate().warehouseId(WarehouseId.ofRepoId(1)).build(),
				preparePurchaseCandidate().warehouseId(WarehouseId.ofRepoId(2)).build());

		useVendorGatewayInvoker(purchaseCandidates.get(0).getVendorId(), NullVendorGatewayInvoker.INSTANCE);

		workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates);
	}

	private void assertThatNoCandidateWasAddedToOrderAggregator()
	{
		assertThat(purchaseOrderFromItemsAggregator.getAddAllCalls()).hasSize(0);
	}

	private PurchaseCandidateBuilder preparePurchaseCandidate()
	{
		return PurchaseCandidate.builder()
				.id(PurchaseCandidateId.ofRepoId(nextPurchaseCandidateId++))
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(1, 2))
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.vendorId(BPartnerId.ofRepoId(20))
				.productId(ProductId.ofRepoId(5))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(6))
				.vendorProductNo("5")
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.qtyToPurchase(ONE)
				.purchaseDatePromised(SystemTime.asZonedDateTime().truncatedTo(ChronoUnit.DAYS))
				.processed(false)
				.locked(false);
	}

	private void useVendorGatewayInvoker(final BPartnerId vendorId, final VendorGatewayInvoker vendorGatewayInvoker)
	{
		Mockito.doReturn(vendorGatewayInvoker)
				.when(vendorGatewayInvokerFactory)
				.createForVendorId(vendorId);
	}
}
