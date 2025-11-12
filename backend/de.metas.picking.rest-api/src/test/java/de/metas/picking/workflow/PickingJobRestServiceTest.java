package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobRestServiceTest
{
	private PickingJobTestHelper helper;
	private PickingJobRestService pickingJobRestService;

	@BeforeEach
	void beforeEach()
	{
		this.helper = new PickingJobTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		this.pickingJobRestService = new PickingJobRestService(helper.pickingJobService, helper.mobileProfileRepository);
	}

	@Nested
	class considerOnlyIfQtyAvailableAtPickingLocator
	{
		private final AtomicInteger nextPackageableIndex = new AtomicInteger(1);
		private UserId userId;
		private ProductId p1_id;

		@BeforeEach
		void beforeEach()
		{
			userId = Env.getLoggedUserId();

			helper.assignCurrentUserToWorkplace();

			this.p1_id = BusinessTestHelper.createProductId("P1", helper.uomEach);
			this.nextPackageableIndex.set(1);
		}

		@Builder(builderMethodName = "mobileProfile", buildMethodName = "setup", builderClassName = "$SetupMobileProfileBuilder")
		private void setupMobileProfile(PickingJobAggregationType aggregationType)
		{
			helper.updateMobileProfile((profile) -> profile.toBuilder()
					.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
							.aggregationType(aggregationType)
							.build())
					.isConsiderOnlyJobScheduledToWorkplace(true)
					.build());
		}

		@SuppressWarnings("SameParameterValue")
		private void createQtyOnHand(final String qtyOnHand)
		{
			helper.createVHU(p1_id, qtyOnHand);
		}

		@Builder(builderMethodName = "packageable", builderClassName = "$PackageableBuilder")
		private Packageable createPackageable(@NonNull String qtyToDeliver)
		{
			final int index = nextPackageableIndex.getAndIncrement();
			final BPartnerLocationId shipToBPLocationId = helper.createBPartnerAndLocationId("Customer" + index);
			final OrderAndLineId salesOrderAndLineId = helper.createOrderAndLineId("salesOrder" + index);

			return helper.packageable()
					.orderAndLineId(salesOrderAndLineId)
					.shipToBPLocationId(shipToBPLocationId)
					.productId(p1_id)
					.qtyToDeliver(qtyToDeliver)
					.lockedBy(userId) // else it won't be found by streamPickingJobCandidates
					.assignToWorkplace(true)
					.build();
		}

		@Test
		void salesOrderAggregation_standardCase()
		{
			mobileProfile().aggregationType(PickingJobAggregationType.SALES_ORDER).setup();
			createQtyOnHand("130");

			final Packageable item1 = packageable().qtyToDeliver("50").build();
			final Packageable item2 = packageable().qtyToDeliver("50").build();
			final Packageable item3 = packageable().qtyToDeliver("50").build();
			packageable().qtyToDeliver("50").build();

			final ImmutableList<PickingJobCandidate> jobs = pickingJobRestService.streamPickingJobCandidates(
							PickingJobQuery.builder()
									.userId(userId) // lockedBy
									.scheduledForWorkplaceId(helper.workplace.getId())
									.onlyIfQtyAvailableAtPickingLocator(true)
									.warehouseId(helper.workplace.getWarehouseId())
									.build()
					)
					.collect(ImmutableList.toImmutableList());

			assertThat(jobs).hasSize(3);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(jobs.get(0).getSalesOrderId()).as("job0 - salesOrderId").isEqualTo(item1.getSalesOrderId());
			softly.assertThat(jobs.get(0).getQtyToDeliver()).as("job0 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(jobs.get(0).getQtyAvailableToPick()).as("job0 - qtyAvailableToPick").isEqualTo(helper.qty("50", p1_id));

			softly.assertThat(jobs.get(1).getSalesOrderId()).as("job1 - salesOrderId").isEqualTo(item2.getSalesOrderId());
			softly.assertThat(jobs.get(1).getQtyToDeliver()).as("job1 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(jobs.get(1).getQtyAvailableToPick()).as("job1 - qtyAvailableToPick").isEqualTo(helper.qty("50", p1_id));

			softly.assertThat(jobs.get(2).getSalesOrderId()).as("job2 - salesOrderId").isEqualTo(item3.getSalesOrderId());
			softly.assertThat(jobs.get(2).getQtyToDeliver()).as("job2 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(jobs.get(2).getQtyAvailableToPick()).as("job2 - qtyAvailableToPick").isEqualTo(helper.qty("30", p1_id));

			softly.assertAll();
		}

		@Test
		void productAggregation_standardCase()
		{
			mobileProfile().aggregationType(PickingJobAggregationType.PRODUCT).setup();
			createQtyOnHand("130");

			packageable().qtyToDeliver("50").build();
			packageable().qtyToDeliver("50").build();
			packageable().qtyToDeliver("50").build();
			packageable().qtyToDeliver("50").build();

			final ImmutableList<PickingJobCandidate> jobs = pickingJobRestService.streamPickingJobCandidates(
							PickingJobQuery.builder()
									.userId(userId) // lockedBy
									.scheduledForWorkplaceId(helper.workplace.getId())
									.onlyIfQtyAvailableAtPickingLocator(true)
									.warehouseId(helper.workplace.getWarehouseId())
									.build()
					)
					.collect(ImmutableList.toImmutableList());

			assertThat(jobs).hasSize(1);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(jobs.get(0).getSalesOrderId()).as("job0 - salesOrderId").isNull();
			softly.assertThat(jobs.get(0).getQtyToDeliver()).as("job0 - qtyToDeliver").isEqualTo(helper.qty("200", p1_id));
			softly.assertThat(jobs.get(0).getQtyAvailableToPick()).as("job0 - qtyAvailableToPick").isEqualTo(helper.qty("130", p1_id));

			softly.assertAll();
		}
	}

}