package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonRawMaterialsIssueLine;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonRejectReasonsList;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonScaleDevice;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class RawMaterialsIssueActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssue");
	private static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("manufacturing/rawMaterialsIssue");

	private final ManufacturingJobService manufacturingJobService;
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	public RawMaterialsIssueActivityHandler(
			@NonNull final ManufacturingJobService manufacturingJobService)
	{
		this.manufacturingJobService = manufacturingJobService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcess);

		return UIComponent.builder()
				.type(COMPONENT_TYPE)
				.properties(Params.builder()
						.valueObj("scaleDevice", getCurrentScaleDevice(job, jsonOpts))
						.valueObj("lines", getLines(job, wfActivity.getId(), jsonOpts))
						.valueObj("qtyRejectedReasons", getJsonRejectReasonsList(jsonOpts))
						.build())
				.build();
	}

	@NonNull
	private static ManufacturingJob getManufacturingJob(final @NonNull WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(ManufacturingJob.class);
	}

	@Nullable
	private JsonScaleDevice getCurrentScaleDevice(final ManufacturingJob job, final @NonNull JsonOpts jsonOpts)
	{
		return manufacturingJobService.getCurrentScaleDevice(job)
				.map(scaleDevice -> JsonScaleDevice.of(scaleDevice, jsonOpts.getAdLanguage()))
				.orElse(null);
	}

	private ImmutableList<JsonRawMaterialsIssueLine> getLines(final ManufacturingJob job, final @NonNull WFActivityId wfActivityId, final @NonNull JsonOpts jsonOpts)
	{
		return job.getActivityById(wfActivityId)
				.getRawMaterialsIssueAssumingNotNull()
				.getLines().stream()
				.map(line -> JsonRawMaterialsIssueLine.of(line, jsonOpts))
				.collect(ImmutableList.toImmutableList());
	}

	private JsonRejectReasonsList getJsonRejectReasonsList(final @NonNull JsonOpts jsonOpts)
	{
		return JsonRejectReasonsList.of(adReferenceDAO.getRefListById(QtyRejectedReasonCode.REFERENCE_ID), jsonOpts);
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}
}
