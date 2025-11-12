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

			helper.updateMobileProfile((profile) -> profile.toBuilder()
					.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
							.aggregationType(PickingJobAggregationType.PRODUCT)
							.build())
					.isConsiderOnlyJobScheduledToWorkplace(true)
					.build());

			helper.assignCurrentUserToWorkplace();

			this.p1_id = BusinessTestHelper.createProductId("P1", helper.uomEach);
			this.nextPackageableIndex.set(1);
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
		void standardCase()
		{
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

			assertThat(jobs.get(0).getSalesOrderId()).isEqualTo(item1.getSalesOrderId());
			assertThat(jobs.get(0).getQtyToDeliver()).isEqualTo(helper.qty("50", p1_id));
			assertThat(jobs.get(0).getQtyAvailableToPick()).isEqualTo(helper.qty("50", p1_id));

			assertThat(jobs.get(1).getSalesOrderId()).isEqualTo(item2.getSalesOrderId());
			assertThat(jobs.get(1).getQtyToDeliver()).isEqualTo(helper.qty("50", p1_id));
			assertThat(jobs.get(1).getQtyAvailableToPick()).isEqualTo(helper.qty("50", p1_id));

			assertThat(jobs.get(2).getSalesOrderId()).isEqualTo(item3.getSalesOrderId());
			assertThat(jobs.get(2).getQtyToDeliver()).isEqualTo(helper.qty("30", p1_id));
			assertThat(jobs.get(2).getQtyAvailableToPick()).isEqualTo(helper.qty("30", p1_id));
		}
	}

}