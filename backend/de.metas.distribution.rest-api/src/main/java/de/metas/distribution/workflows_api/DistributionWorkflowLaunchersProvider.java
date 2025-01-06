package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import java.util.ArrayList;
import java.util.Optional;

class DistributionWorkflowLaunchersProvider
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final DistributionRestService distributionRestService;

	public DistributionWorkflowLaunchersProvider(
			final @NonNull DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit)
	{
		return computeLaunchers(userId, suggestedLimit);
	}

	private WorkflowLaunchersList computeLaunchers(
			final @NonNull UserId userId,
			final @NonNull QueryLimit limit)
	{
		final ArrayList<WorkflowLauncher> currentResult = new ArrayList<>();

		//
		// Already started launchers
		final ImmutableList<DDOrderReference> existingDDOrders = distributionRestService.streamActiveReferencesAssignedTo(userId)
				.collect(ImmutableList.toImmutableList());
		existingDDOrders.stream()
				.map(this::toExistingWorkflowLauncher)
				.forEach(currentResult::add);

		//
		// New launchers
		if (!limit.isLimitHitOrExceeded(currentResult))
		{
			distributionRestService.streamActiveReferencesNotAssigned()
					.limit(limit.minusSizeOf(currentResult).toIntOr(Integer.MAX_VALUE))
					.map(this::toNewWorkflowLauncher)
					.forEach(currentResult::add);
		}

		return WorkflowLaunchersList.builder()
				.launchers(ImmutableList.copyOf(currentResult))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toExistingWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID)
				.caption(computeCaption(ddOrderReference))
				.startedWFProcessId(WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderReference.getDdOrderId()))
				.build();
	}

	private WorkflowLauncher toNewWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		return WorkflowLauncher.builder()
				.applicationId(DistributionMobileApplication.APPLICATION_ID)
				.caption(computeCaption(ddOrderReference))
				.wfParameters(DistributionWFProcessStartParams.builder()
						.ddOrderId(ddOrderReference.getDdOrderId())
						.build()
						.toParams())
				.build();
	}

	@NonNull
	private WorkflowLauncherCaption computeCaption(@NonNull final DDOrderReference ddOrderReference)
	{
		final TranslatableStringBuilder translatableStringBuilder = TranslatableStrings.builder();
		getSourceDocumentTypeAndNo(ddOrderReference)
				.ifPresent(sourceDocTypeAndNo -> translatableStringBuilder
						.append(sourceDocTypeAndNo.getLeft())
						.append(" ")
						.append(sourceDocTypeAndNo.getRight())
						.append(" | "));

		return WorkflowLauncherCaption.of(
				translatableStringBuilder
						.append(warehouseBL.getWarehouseName(ddOrderReference.getFromWarehouseId()))
						.append(" > ")
						.append(warehouseBL.getWarehouseName(ddOrderReference.getToWarehouseId()))
						.append(" | ")
						.appendDateTime(ddOrderReference.getDatePromised().toZonedDateTime(orgDAO::getTimeZone))
						.build()
		);
	}

	@NonNull
	private Optional<ImmutablePair<ITranslatableString, String>> getSourceDocumentTypeAndNo(@NonNull final DDOrderReference ddOrderReference)
	{
		final ITranslatableString docTypeName;
		final String documentNo;
		if (ddOrderReference.getSalesOrderId() != null)
		{
			final I_C_Order order = orderBL.getById(ddOrderReference.getSalesOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
			documentNo = order.getDocumentNo();
		}
		else if (ddOrderReference.getPpOrderId() != null) {
			final I_PP_Order ppOrder = ppOrderBL.getById(ddOrderReference.getPpOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()));
			documentNo = ppOrder.getDocumentNo();
		}
		else
		{
			return Optional.empty();
		}

		return Optional.of(ImmutablePair.of(docTypeName, documentNo));
	}
}
