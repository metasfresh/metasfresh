package de.metas.purchasecandidate.purchaseordercreation;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerId;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;
import de.metas.util.time.SystemTime;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, ProfitPriceActualFactory.class })
public class PurchaseCandidateToOrderWorkflowTest
{
	private static final String SOMETHING_WENT_WRONG = "something went wrong";

	@Injectable
	VendorGatewayInvokerFactory vendorGatewayInvokerFactory;

	@Mocked
	VendorGatewayInvoker vendorGatewayInvoker;

	@Mocked
	PurchaseCandidateRepository purchaseCandidateRepo;

	@Mocked
	PurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator;

	private I_C_UOM EACH;
	private Quantity ONE;

	private PurchaseCandidateToOrderWorkflow workflowUnderTest;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, 5);

		this.EACH = createUOM("Ea");
		this.ONE = Quantity.of(BigDecimal.ONE, EACH);

		workflowUnderTest = PurchaseCandidateToOrderWorkflow.builder()
				.purchaseCandidateRepo(purchaseCandidateRepo)
				.vendorGatewayInvokerFactory(vendorGatewayInvokerFactory)
				.purchaseOrderFromItemsAggregator(purchaseOrderFromItemsAggregator).build();
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
		final PurchaseCandidate candidate1 = createPurchaseCandidate(10, 20);
		final PurchaseCandidate candidate2 = createPurchaseCandidate(10, 30);
		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		assertThatThrownBy(() -> workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates))
				.hasMessageStartingWith("The given purchaseCandidates need to have unique IDs that are all > 0");
	}

	@Test
	public void executeForPurchaseCandidates()
	{
		final PurchaseCandidate candidate1 = createPurchaseCandidate(10, 20);
		final PurchaseCandidate candidate2 = createPurchaseCandidate(11, 20);

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// @formatter:off
		new Expectations()
		{{
			vendorGatewayInvokerFactory.createForVendorId(BPartnerId.ofRepoId(20)); result = NullVendorGatewayInvoker.INSTANCE;
		}};	// @formatter:on

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
		// @formatter:off
		new Verifications()
		{{
			Iterator<PurchaseOrderItem> iterator;
			purchaseOrderFromItemsAggregator.addAll(iterator=withCapture());

			assertThat(iterator).isNotNull();
			assertThat(iterator.hasNext()).as("iterator is not consumed").isTrue();
			assertThat(iterator).hasSize(2);
		}};	// @formatter:on
	}

	@Test
	public void executeForPurchaseCandidates_prevent_multiple_vendorIds()
	{
		final PurchaseCandidate candidate1 = createPurchaseCandidate(10, 20);
		final PurchaseCandidate candidate2 = createPurchaseCandidate(11, 30);

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// invoke the method under test
		assertThatThrownBy(() -> workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates))
				.hasMessageStartingWith("Error: The given purchaseCandidates have different vendorIds");
	}

	@Test
	public void executeForPurchaseCandidates_exception()
	{
		final PurchaseCandidate candidate1 = createPurchaseCandidate(10, 20);
		final PurchaseCandidate candidate2 = createPurchaseCandidate(11, 20);

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// @formatter:off
		new Expectations()
		{{
			vendorGatewayInvokerFactory.createForVendorId(BPartnerId.ofRepoId(20)); result = vendorGatewayInvoker;

			vendorGatewayInvoker.placeRemotePurchaseOrder(purchaseCandidates);
			result = new RuntimeException(SOMETHING_WENT_WRONG);

		}};	// @formatter:on

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

	@SuppressWarnings("unchecked")
	private void assertThatNoCandidateWasAddedToOrderAggregator()
	{
		// @formatter:off
		new Verifications()
		{{
			purchaseOrderFromItemsAggregator.addAll((Iterator<PurchaseOrderItem>)any); times = 0;
		}};	// @formatter:on
	}

	public PurchaseCandidate createPurchaseCandidate(
			final int purchaseCandidateId,
			final int vendorId)
	{
		final ProductId productId = ProductId.ofRepoId(5);
		return PurchaseCandidate.builder()
				.id(PurchaseCandidateId.ofRepoIdOrNull(purchaseCandidateId))
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(1, 2))
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.vendorId(BPartnerId.ofRepoId(vendorId))
				.productId(ProductId.ofRepoId(5))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(6))
				.vendorProductNo(String.valueOf(productId.getRepoId()))
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.qtyToPurchase(ONE)
				.purchaseDatePromised(SystemTime.asLocalDateTime().truncatedTo(ChronoUnit.DAYS))
				.processed(false)
				.locked(false)
				.build();
	}
}
