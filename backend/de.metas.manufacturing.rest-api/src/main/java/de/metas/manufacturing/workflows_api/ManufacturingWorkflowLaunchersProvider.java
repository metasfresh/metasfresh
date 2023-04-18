package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
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
			final @NonNull UserId userId,
			@Nullable final GlobalQRCode filterByQRCode,
			final @NonNull QueryLimit limit)
	{
		final ResourceId plantId;
		final PrintableQRCode filterByPrintableQRCode;
		if (filterByQRCode == null)
		{
			plantId = null;
			filterByPrintableQRCode = null;
		}
		else if (ResourceQRCode.isTypeMatching(filterByQRCode))
		{
			final ResourceQRCode resourceQRCode = ResourceQRCode.ofGlobalQRCode(filterByQRCode);
			plantId = resourceQRCode.getResourceId();
			filterByPrintableQRCode = resourceQRCode.toPrintableQRCode();
		}
		else
		{
			throw new AdempiereException("Invalid QR Code: " + filterByQRCode);
		}

		final Instant now = SystemTime.asInstant();
		final ImmutableList<WorkflowLauncher> launchers = manufacturingRestService.streamJobReferencesForUser(userId, plantId, now, limit)
				.map(this::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.filterByQRCode(filterByPrintableQRCode)
				.launchers(launchers)
				.timestamp(now)
				.build();
	}

	private WorkflowLauncher toWorkflowLauncher(final ManufacturingJobReference manufacturingJobReference)
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

	private ITranslatableString computeCaption(@NonNull final ManufacturingJobReference manufacturingJobReference)
	{
		return TranslatableStrings.builder()
				.append(manufacturingJobReference.getDocumentNo())
				.append(" | ")
				.append(manufacturingJobReference.getProductName())
				.append(" | ")
				.appendQty(manufacturingJobReference.getQtyRequiredToProduce().toBigDecimal(), manufacturingJobReference.getQtyRequiredToProduce().getUOMSymbol())
				.append(" | ")
				.appendDateTime(manufacturingJobReference.getDatePromised())
				.build();
	}
}
