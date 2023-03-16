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
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class SendTargetWeightHook implements BeforeAcquireValueHook
{
	private final static Logger logger = LogManager.getLogger(SendTargetWeightHook.class);

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	//FIXME: use sys config instead
	private static final UomId HARDCODED_SCALE_UOM_ID = UomId.ofRepoId(540017); // kg
	//FIXME: Use UomId or x12de355 instead
	private static final String QTY_UOM_SYMBOL_PARAM_NAME = "uom";
	private static final String QTY_TARGET_PARAM_NAME = "qtyTarget";
	private static final String POSITIVE_TOLERANCE_PARAM_NAME = "positiveTolerance";
	private static final String NEGATIVE_TOLERANCE_PARAM_NAME = "negativeTolerance";
	private static final String TARGET_WEIGHT_SCALE_SYSCONFIG_SUFFIX = ".TargetWeightScale";

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

		final BigDecimal targetWeight = parameters.getSingleAsBigDecimal(QTY_TARGET_PARAM_NAME).orElse(null);

		if (targetWeight == null)
		{
			return;
		}

		final I_C_UOM weightUOM = getQtyUOM(parameters).orElse(null);
		if (weightUOM == null)
		{
			logger.warn("*** SendTargetWeightHook: No weightUOM was found! See all parameters: {}!", parameters);
			return;
		}

		final Function<BigDecimal, Quantity> toQty = bd -> Quantity.of(bd, weightUOM);
		
		final Integer targetWeightScale = parameters.getSingleAsIntegerForSuffix(TARGET_WEIGHT_SCALE_SYSCONFIG_SUFFIX)
				.orElse(null);

		final SoehenleSendTargetWeightRequest sendTargetWeightRequest = SoehenleSendTargetWeightRequest.builder()
				.targetWeight(convertToScaleUOM(toQty.apply(targetWeight)))
				.targetWeightScale(targetWeightScale)
				.positiveTolerance(parameters.getSingleAsBigDecimal(POSITIVE_TOLERANCE_PARAM_NAME)
										   .map(toQty)
										   .map(this::convertToScaleUOM)
										   .orElse(BigDecimal.ZERO))
				.negativeTolerance(parameters.getSingleAsBigDecimal(NEGATIVE_TOLERANCE_PARAM_NAME)
										   .map(toQty)
										   .map(this::convertToScaleUOM)
										   .orElse(BigDecimal.ZERO))
				.build();

		device.accessDevice(sendTargetWeightRequest);
	}

	@NonNull
	private Optional<I_C_UOM> getQtyUOM(@NonNull final RunParameters parameters)
	{
		return parameters.getSingle(QTY_UOM_SYMBOL_PARAM_NAME)
				.flatMap(uomDAO::getBySymbol);
	}

	@NonNull
	private BigDecimal convertToScaleUOM(@NonNull final Quantity qtyToConvert)
	{
		return uomConversionBL.convertQuantityTo(
				qtyToConvert,
				UOMConversionContext.of((ProductId)null), //FIXME: send also the ProductId
				HARDCODED_SCALE_UOM_ID).toBigDecimal();
	}
}
