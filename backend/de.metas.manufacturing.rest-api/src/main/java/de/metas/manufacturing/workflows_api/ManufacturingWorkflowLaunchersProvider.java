package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.time.Duration;

public class ManufacturingWorkflowLaunchersProvider
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
	{
		ImmutableList<WorkflowLauncher> result = ppOrderDAO.retrieveManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.limit(suggestedLimit)
						.build())
				.stream()
				.map(this::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.launchers(result)
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toWorkflowLauncher(@NonNull final I_PP_Order order)
	{
		return WorkflowLauncher.builder()
				.handlerId(ManufacturingWFProcessHandler.HANDLER_ID)
				.caption(TranslatableStrings.anyLanguage(order.getDocumentNo()))
				.wfParameters(ManufacturingWFProcessStartParams.builder()
						.ppOrderId(PPOrderId.ofRepoId(order.getPP_Order_ID()))
						.build()
						.toParams())
				.build();
	}
}
