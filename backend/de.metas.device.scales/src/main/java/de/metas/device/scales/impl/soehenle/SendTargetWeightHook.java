/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.impl.soehenle;

import com.google.common.collect.ImmutableSet;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.IDeviceResponse;
import de.metas.device.api.hook.BeforeAcquireValueHook;
import de.metas.device.api.hook.RunParameters;
import de.metas.device.scales.request.GetInstantGrossWeighRequest;
import de.metas.device.scales.request.GetStableGrossWeighRequest;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Function;

public class SendTargetWeightHook implements BeforeAcquireValueHook
{
	private static final String QTY_TARGET_PARAM_NAME = "qtyTarget";
	private static final String POSITIVE_TOLERANCE_PARAM_NAME = "positiveTolerance";
	private static final String NEGATIVE_TOLERANCE_PARAM_NAME = "negativeTolerance";

	private final static Set<String> ACCEPTED_REQUEST_TYPES = ImmutableSet.of(GetInstantGrossWeighRequest.class.getSimpleName(),
																			  GetStableGrossWeighRequest.class.getSimpleName());

	@Override
	public void run(
			@NonNull final RunParameters parameters,
			@NonNull final IDevice device,
			@NonNull final IDeviceRequest<? extends IDeviceResponse> acquireValueRequest)
	{
		if (!ACCEPTED_REQUEST_TYPES.contains(acquireValueRequest.getClass().getSimpleName()))
		{
			return;
		}

		final Function<BigDecimal,BigDecimal> soehenleMultiplier = value -> value.multiply(new BigDecimal("100"));

		final BigDecimal targetWeight = parameters.getSingleAsBigDecimal(QTY_TARGET_PARAM_NAME).orElse(null);

		if (targetWeight == null)
		{
			return;
		}

		final SoehenleSendTargetWeightRequest sendTargetWeightRequest = SoehenleSendTargetWeightRequest.builder()
				.targetWeight(soehenleMultiplier.apply(targetWeight))
				.positiveTolerance(parameters.getSingleAsBigDecimal(POSITIVE_TOLERANCE_PARAM_NAME).map(soehenleMultiplier).orElse(BigDecimal.ZERO))
				.negativeTolerance(parameters.getSingleAsBigDecimal(NEGATIVE_TOLERANCE_PARAM_NAME).map(soehenleMultiplier).orElse(BigDecimal.ZERO))
				.build();

		device.accessDevice(sendTargetWeightRequest);
	}
}
