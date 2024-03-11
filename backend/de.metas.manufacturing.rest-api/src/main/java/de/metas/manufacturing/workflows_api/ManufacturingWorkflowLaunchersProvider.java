package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.ManufacturingJobFacets;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.service.ManufacturingJobReferenceQuery;
import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;

public class ManufacturingWorkflowLaunchersProvider
{
	private final ManufacturingRestService manufacturingRestService;

	public ManufacturingWorkflowLaunchersProvider(
			@NonNull final ManufacturingRestService manufacturingRestService)
	{
		this.manufacturingRestService = manufacturingRestService;
	}

	public WorkflowLaunchersList provideLaunchers(
			@NonNull WorkflowLaunchersQuery query,
			@Nullable final ResourceId workstationId)
	{
		final GlobalQRCode filterByQRCode = query.getFilterByQRCode();
		final ResourceId plantOrWorkstationId;
		final PrintableQRCode filterByPrintableQRCode;
		if (filterByQRCode == null)
		{
			plantOrWorkstationId = null;
			filterByPrintableQRCode = null;
		}
		else if (ResourceQRCode.isTypeMatching(filterByQRCode))
		{
			final ResourceQRCode resourceQRCode = ResourceQRCode.ofGlobalQRCode(filterByQRCode);
			plantOrWorkstationId = resourceQRCode.getResourceId();
			filterByPrintableQRCode = resourceQRCode.toPrintableQRCode();
		}
		else
		{
			throw new AdempiereException("Invalid QR Code: " + filterByQRCode);
		}

		final Instant now = SystemTime.asInstant();
		final ImmutableList<WorkflowLauncher> launchers = manufacturingRestService.streamJobReferencesForUser(
						ManufacturingJobReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.plantOrWorkstationId(plantOrWorkstationId)
								.workstationId(workstationId)
								.now(now)
								.suggestedLimit(query.getLimit().orElse(QueryLimit.NO_LIMIT))
								.activeFacetIds(ManufacturingJobFacets.FacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getFacetIds()))
								.build()
				)
				.map(ManufacturingWorkflowLaunchersProvider::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.filterByQRCode(filterByPrintableQRCode)
				.launchers(launchers)
				.timestamp(now)
				.build();
	}

	private static WorkflowLauncher toWorkflowLauncher(final ManufacturingJobReference manufacturingJobReference)
	{
		if (manufacturingJobReference.isJobStarted())
		{
			return WorkflowLauncher.builder()
					.applicationId(ManufacturingMobileApplication.APPLICATION_ID)
					.caption(computeCaption(manufacturingJobReference))
					.startedWFProcessId(WFProcessId.ofIdPart(ManufacturingMobileApplication.APPLICATION_ID, manufacturingJobReference.getPpOrderId()))
					.build();
		}
		else
		{
			return WorkflowLauncher.builder()
					.applicationId(ManufacturingMobileApplication.APPLICATION_ID)
					.caption(computeCaption(manufacturingJobReference))
					.wfParameters(ManufacturingWFProcessStartParams.builder()
							.ppOrderId(manufacturingJobReference.getPpOrderId())
							.build()
							.toParams())
					.build();
		}
	}

	private static WorkflowLauncherCaption computeCaption(@NonNull final ManufacturingJobReference manufacturingJobReference)
	{
		return WorkflowLauncherCaption.of(
				TranslatableStrings.builder()
						.append(manufacturingJobReference.getDocumentNo())
						.append(" | ")
						.append(manufacturingJobReference.getProductName())
						.append(" | ")
						.appendQty(manufacturingJobReference.getQtyRequiredToProduce().toBigDecimal(), manufacturingJobReference.getQtyRequiredToProduce().getUOMSymbol())
						.append(" | ")
						.appendDateTime(manufacturingJobReference.getDatePromised())
						.build()
		);
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		return manufacturingRestService.getFacets(
						ManufacturingJobReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.now(SystemTime.asInstant())
								.activeFacetIds(ManufacturingJobFacets.FacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getActiveFacetIds()))
								.build()
				)
				.toWorkflowLaunchersFacetGroupList(query.getActiveFacetIds());
	}
}
