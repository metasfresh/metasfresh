package de.metas.picking.workflow.lauchers;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.edi.EDIProductLookupService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.product.ProductId;
import de.metas.product.ScannedProductCodeResolver;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
				new PickingJobRestService(helper.pickingJobService, helper.mobileProfileRepository),
				helper.mobileProfileRepository,
				helper.workplaceService,
				new DisplayValueProviderService(helper.documentLocationBL),
				helper.documentLocationBL,
				new ScannedProductCodeResolver(new EDIProductLookupService())
		);

	}

	// TODO improve or delete!
	@Nested
	class considerOnlyIfQtyAvailableAtPickingLocator
	{
		private ProductId p1_id;

		@BeforeEach
		void beforeEach()
		{
			helper.updateMobileProfile((profile) -> profile.toBuilder()
					.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
							.aggregationType(PickingJobAggregationType.PRODUCT)
							.build())
					.isConsiderOnlyJobScheduledToWorkplace(true)
					.build());

			helper.assignCurrentUserToWorkplace();

			this.p1_id = BusinessTestHelper.createProductId("P1", helper.uomEach);
		}

		@Test
		void test()
		{
			helper.createVHU(p1_id, "130");

			helper.packageable()
					.orderAndLineId(helper.createOrderAndLineId("salesOrder001"))
					.productId(p1_id)
					.qtyToDeliver("100")
					.assignToWorkplace(true)
					.build();

			final WorkflowLaunchersList launchers = launchersProvider.provideLaunchers(
					WorkflowLaunchersQuery.builder()
							.applicationId(PickingMobileApplication.APPLICATION_ID)
							.userId(UserId.METASFRESH)
							.build()
			);

			final ImmutableList<WorkflowLauncher> launchersList = ImmutableList.copyOf(launchers);
			assertThat(launchersList).hasSize(1);
		}
	}

}