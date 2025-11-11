package de.metas.picking.workflow.lauchers;

import de.metas.business.BusinessTestHelper;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.edi.EDIProductLookupService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.product.ProductId;
import de.metas.product.ScannedProductCodeResolver;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository = new MobileUIPickingUserProfileRepository();
		final DocumentLocationBL documentLocationBL = new DocumentLocationBL();
		this.launchersProvider = new PickingWorkflowLaunchersProvider(
				new PickingJobRestService(helper.pickingJobService, mobileUIPickingUserProfileRepository),
				mobileUIPickingUserProfileRepository,
				new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository()),
				new DisplayValueProviderService(documentLocationBL),
				documentLocationBL,
				new ScannedProductCodeResolver(new EDIProductLookupService())
		);
	}

	@Test
	void test()
	{
		final ProductId p1_id = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId p1_vhu = helper.createVHU(p1_id, "130");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder001");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(p1_id)
				.qtyToDeliver("100")
				.build();
		
		final WorkflowLaunchersList launchers = launchersProvider.provideLaunchers(WorkflowLaunchersQuery.builder()
				.applicationId(PickingMobileApplication.APPLICATION_ID)
				.userId(UserId.METASFRESH)
				.build());

		System.out.println(launchers);
	}

}