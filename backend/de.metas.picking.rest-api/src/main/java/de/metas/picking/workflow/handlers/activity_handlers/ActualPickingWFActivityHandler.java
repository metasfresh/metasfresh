/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.picking.workflow.handlers.activity_handlers;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobProgress;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonRejectReasonsList;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class ActualPickingWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("picking.actualPicking");
	private static final UIComponentType COMPONENTTYPE_PICK_PRODUCTS = UIComponentType.ofString("picking/pickProducts");

	private final PickingJobRestService pickingJobRestService;

	public ActualPickingWFActivityHandler(@NonNull final PickingJobRestService pickingJobRestService)
	{
		this.pickingJobRestService = pickingJobRestService;
	}

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);

		final JsonRejectReasonsList qtyRejectedReasons = JsonRejectReasonsList.of(pickingJobRestService.getQtyRejectedReasons(), jsonOpts);

		final JsonPickingJob jsonPickingJob = JsonPickingJob.of(pickingJob, jsonOpts);

		return UIComponent.builder()
				.type(COMPONENTTYPE_PICK_PRODUCTS)
				.properties(Params.builder()
						.valueObj("lines", jsonPickingJob.getLines())
						.valueObj("pickFromAlternatives", jsonPickingJob.getPickFromAlternatives())
						.valueObj("qtyRejectedReasons", qtyRejectedReasons)
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);
		return computeActivityState(pickingJob);
	}

	@NonNull
	private WFActivityStatus computeActivityState(final PickingJob pickingJob)
	{
		final PickingJobProgress progress = pickingJob.getProgress();
		switch (progress)
		{
			case NOT_STARTED:
				return WFActivityStatus.NOT_STARTED;
			case IN_PROGRESS:
				return WFActivityStatus.IN_PROGRESS;
			case DONE:
				return WFActivityStatus.COMPLETED;
			default:
				throw new AdempiereException("Unknown process status: " + progress);
		}
	}
}
