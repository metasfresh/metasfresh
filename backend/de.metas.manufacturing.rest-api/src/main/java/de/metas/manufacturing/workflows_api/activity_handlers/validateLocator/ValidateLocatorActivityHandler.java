/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.manufacturing.workflows_api.activity_handlers.validateLocator;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.JsonQRCode;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupportHelper;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class ValidateLocatorActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.validateLocators");

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return SetScannedBarcodeSupportHelper.uiComponent()
				.componentType(UIComponentType.SCAN_AND_VALIDATE_BARCODE)
				.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser())
				.validOptions(getSourceLocatorQRCodes(wfProcess, wfActivity))
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return WFActivityStatus.NOT_STARTED;
	}

	@Nullable
	private ImmutableList<JsonQRCode> getSourceLocatorQRCodes(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		final ManufacturingJobActivity activity = job.getActivityById(wfActivity.getId());

		if (activity.getSourceLocatorValidate() == null)
		{
			return null;
		}

		return activity.getSourceLocatorValidate()
				.getSourceLocatorList()
				.stream()
				.map(locatorInfo -> JsonQRCode.builder()
						.caption(locatorInfo.getCaption())
						.qrCode(LocatorQRCode.builder()
										.locatorId(locatorInfo.getId())
										.caption(locatorInfo.getCaption())
										.build().toGlobalQRCodeJsonString())
						.build())
				.collect(ImmutableList.toImmutableList());
	}
}
