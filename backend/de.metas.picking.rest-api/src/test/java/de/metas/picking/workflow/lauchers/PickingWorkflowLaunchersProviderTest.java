package de.metas.picking.workflow.lauchers;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.edi.EDIProductLookupService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.Packageable;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.product.ProductId;
import de.metas.product.ScannedProductCodeResolver;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PickingWorkflowLaunchersProviderTest
{
	private PickingJobTestHelper helper;
	private PickingWorkflowLaunchersProvider launchersProvider;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		setupServices();
	}

	private void setupServices()
	{
		this.launchersProvider = new PickingWorkflowLaunchersProvider(
				helper.configService,
				helper.bpartnerService,
				new PickingJobRestService(helper.pickingJobService, helper.configService),
				helper.warehouseService,
				helper.huService,
				new DisplayValueProviderService(helper.bpartnerService),
				new ScannedProductCodeResolver(new EDIProductLookupService())
		);

	}

	// TODO improve or delete!
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

		private List<PickingWFProcessStartParams> retrieveLaunchers()
		{
			return launchersProvider.provideLaunchers(
							WorkflowLaunchersQuery.builder()
									.applicationId(PickingMobileApplication.APPLICATION_ID)
									.userId(UserId.METASFRESH)
									.filterByQtyAvailableAtPickFromLocator(true)
									.build()
					).stream()
					.map(launcher -> PickingWFProcessStartParams.ofParams(launcher.getWfParameters()))
					.collect(Collectors.toList());
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

			final List<PickingWFProcessStartParams> launchers = retrieveLaunchers();

			assertThat(launchers).hasSize(3);
			System.out.println(launchers);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(launchers.get(0).getSalesOrderId()).as("job0 - salesOrderId").isEqualTo(item1.getSalesOrderId());
			softly.assertThat(launchers.get(0).getQtyToDeliver()).as("job0 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(launchers.get(0).getQtyAvailableToPick()).as("job0 - qtyAvailableToPick").isEqualTo(helper.qty("50", p1_id));

			softly.assertThat(launchers.get(1).getSalesOrderId()).as("job1 - salesOrderId").isEqualTo(item2.getSalesOrderId());
			softly.assertThat(launchers.get(1).getQtyToDeliver()).as("job1 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(launchers.get(1).getQtyAvailableToPick()).as("job1 - qtyAvailableToPick").isEqualTo(helper.qty("50", p1_id));

			softly.assertThat(launchers.get(2).getSalesOrderId()).as("job2 - salesOrderId").isEqualTo(item3.getSalesOrderId());
			softly.assertThat(launchers.get(2).getQtyToDeliver()).as("job2 - qtyToDeliver").isEqualTo(helper.qty("50", p1_id));
			softly.assertThat(launchers.get(2).getQtyAvailableToPick()).as("job2 - qtyAvailableToPick").isEqualTo(helper.qty("30", p1_id));

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

			final List<PickingWFProcessStartParams> launchers = retrieveLaunchers();

			assertThat(launchers).hasSize(1);
			System.out.println(launchers);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(launchers.get(0).getSalesOrderId()).as("job0 - salesOrderId").isNull();
			softly.assertThat(launchers.get(0).getQtyToDeliver()).as("job0 - qtyToDeliver").isEqualTo(helper.qty("200", p1_id));
			softly.assertThat(launchers.get(0).getQtyAvailableToPick()).as("job0 - qtyAvailableToPick").isEqualTo(helper.qty("130", p1_id));

			softly.assertAll();
		}
	}

}