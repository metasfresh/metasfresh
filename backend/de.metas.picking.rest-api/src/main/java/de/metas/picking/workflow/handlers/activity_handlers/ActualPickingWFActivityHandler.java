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

import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.workflow.model.PickingJob;
import de.metas.picking.workflow.model.PickingJobProgress;
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

import java.util.List;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
public class ActualPickingWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("picking.actualPicking");

	private static final UIComponentType COMPONENTTYPE_PICK_PRODUCTS = UIComponentType.ofString("picking/pickProducts");

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

		final List<JsonPickingJobLine> lines = JsonPickingJob.of(pickingJob, jsonOpts).getLines();

		return UIComponent.builder()
				.type(COMPONENTTYPE_PICK_PRODUCTS)
				.properties(Params.builder()
						.valueObj("lines", lines)
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
			case NOTHING_PICKED:
				return WFActivityStatus.NOT_STARTED;
			case PARTIAL_PICKED:
				return WFActivityStatus.IN_PROGRESS;
			case FULLY_PICKED:
				return WFActivityStatus.COMPLETED;
			default:
				throw new AdempiereException("Unknown process status: " + progress);
		}
	}
}
