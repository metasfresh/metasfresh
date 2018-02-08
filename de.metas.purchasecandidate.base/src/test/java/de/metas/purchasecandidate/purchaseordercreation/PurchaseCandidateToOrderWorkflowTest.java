package de.metas.purchasecandidate.purchaseordercreation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
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

	private PurchaseCandidateToOrderWorkflow workflowUnderTest;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, 5);

		workflowUnderTest = PurchaseCandidateToOrderWorkflow.builder()
				.purchaseCandidateRepo(purchaseCandidateRepo)
				.vendorGatewayInvokerFactory(vendorGatewayInvokerFactory)
				.purchaseOrderFromItemsAggregator(purchaseOrderFromItemsAggregator).build();
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
		final PurchaseCandidate candidate2 = createPurchaseCandidate(11, 30);

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// @formatter:off
		new Expectations()
		{{
			vendorGatewayInvokerFactory.createForVendorId(20); result = NullVendorGatewayInvoker.INSTANCE;
			vendorGatewayInvokerFactory.createForVendorId(30); result = NullVendorGatewayInvoker.INSTANCE;

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
			List<Iterator<PurchaseOrderItem>> iterators = new ArrayList<>();
			purchaseOrderFromItemsAggregator.addAll(withCapture(iterators)); times = 2;

			assertThat(iterators).hasSize(2);
			assertThat(iterators).allSatisfy(iterator -> {
					assertThat(iterator).isNotNull();
		    		assertThat(iterator.hasNext()).as("iterator is not consumed").isTrue();
			});
		}};	// @formatter:on
	}

	@Test
	public void executeForPurchaseCandidates_one_exception()
	{
		final PurchaseCandidate candidate1 = createPurchaseCandidate(10, 20);
		final PurchaseCandidate candidate2 = createPurchaseCandidate(11, 30);

		final ImmutableList<PurchaseCandidate> purchaseCandidates = ImmutableList.of(candidate1, candidate2);

		// @formatter:off
		new Expectations()
		{{
			vendorGatewayInvokerFactory.createForVendorId(20); result = NullVendorGatewayInvoker.INSTANCE;

			// candidate2 is for the mocked vendorGatewayInvoker (vendorId=30)
			vendorGatewayInvokerFactory.createForVendorId(30); result = vendorGatewayInvoker;
			vendorGatewayInvoker.placeRemotePurchaseOrder(ImmutableList.of(candidate2));
			result = new RuntimeException(SOMETHING_WENT_WRONG);

		}};	// @formatter:on

		// invoke the method under test
		workflowUnderTest.executeForPurchaseCandidates(purchaseCandidates);

		assertThatOneCandidateWereAddedToOrderAggregator();

		assertThat(candidate1.getPurchaseOrderItems()).hasSize(1);
		assertThat(candidate1.getPurchaseErrorItems()).isEmpty();

		assertThat(candidate2.getPurchaseOrderItems()).isEmpty();
		assertThat(candidate2.getPurchaseErrorItems()).hasSize(1);

		final Throwable candidate2Throwable = candidate2.getPurchaseErrorItems().get(0).getThrowable();
		assertThat(candidate2Throwable).hasMessageContaining(SOMETHING_WENT_WRONG);
	}

	private void assertThatOneCandidateWereAddedToOrderAggregator()
	{
		// @formatter:off
		new Verifications()
		{{
			Iterator<PurchaseOrderItem> iterator;
			purchaseOrderFromItemsAggregator.addAll(iterator = withCapture()); times = 1;

			assertThat(iterator).isNotNull();
			assertThat(iterator.hasNext()).as("iterator is not consumed").isTrue();
		}};	// @formatter:on
	}

	public static PurchaseCandidate createPurchaseCandidate(
			final int purchaseCandidateId,
			final int vendorId)
	{
		return PurchaseCandidate.builder()
				.purchaseCandidateId(purchaseCandidateId)
				.salesOrderId(1)
				.salesOrderLineId(2)
				.orgId(3)
				.warehouseId(4)
				.productId(5)
				.uomId(6)
				.vendorBPartnerId(vendorId)
				.vendorProductInfo(VendorProductInfo.builder()
						.bPartnerProductId(10)
						.vendorBPartnerId(vendorId).productId(20)
						.productNo("productNo")
						.productName("productName").build())
				.qtyToPurchase(BigDecimal.ONE)
				.dateRequired(SystemTime.asDayTimestamp())
				.processed(false)
				.locked(false)
				.build();
	}
}
