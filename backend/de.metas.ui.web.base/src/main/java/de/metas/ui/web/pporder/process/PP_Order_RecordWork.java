/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.pporder.process;

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.FillMandatoryException;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;

public class PP_Order_RecordWork
		extends JavaProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final IPPOrderBL orderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderRoutingRepository orderRoutingRepository = Services.get(IPPOrderRoutingRepository.class);
	private final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);

	private static final String PARAM_PP_Order_Node_ID = "PP_Order_Node_ID";
	@Param(parameterName = PARAM_PP_Order_Node_ID, mandatory = true)
	private int orderRoutingActivityRepoId;

	private static final String PARAM_DurationUnit = "DurationUnit";
	@Param(parameterName = PARAM_DurationUnit, mandatory = true)
	private WFDurationUnit durationUnit;

	@Param(parameterName = "Duration", mandatory = true)
	private BigDecimal duration;

	private static final String PARAM_DurationBooked = "DurationBooked";
	@Param(parameterName = PARAM_DurationBooked, mandatory = true)
	private int durationAlreadyBooked;

	private DefaultParams _defaultParams; // lazy
	private final HashMap<PPOrderId, I_PP_Order> ordersCache = new HashMap<>();

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final PPOrderId orderId = PPOrderId.ofRepoId(context.getSingleSelectedRecordId());
		return checkPreconditionsApplicable(orderId);
	}

	private ProcessPreconditionsResolution checkPreconditionsApplicable(final PPOrderId orderId)
	{
		final I_PP_Order order = getOrderById(orderId);
		if (order == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not saved");
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(order.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("work recording allowed only when order is completed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAM_PP_Order_Node_ID.equals(parameter.getColumnName()))
		{
			return getDefaultParameters().getActivityId();
		}
		if (PARAM_DurationUnit.equals(parameter.getColumnName()))
		{
			return getDefaultParameters().getDurationUnit();
		}
		if (PARAM_DurationBooked.equals(parameter.getColumnName()))
		{
			return getDefaultParameters().getDurationAlreadyBooked();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Value
	@Builder
	private static class DefaultParams
	{
		PPOrderRoutingActivityId activityId;
		WFDurationUnit durationUnit;
		int durationAlreadyBooked;
	}

	private DefaultParams getDefaultParameters()
	{
		if (_defaultParams == null)
		{
			final PPOrderRoutingActivity firstActivity = orderRoutingRepository.getFirstActivity(getOrderId());
			final WFDurationUnit durationUnit = firstActivity.getDurationUnit();
			_defaultParams = DefaultParams.builder()
					.activityId(firstActivity.getId())
					.durationUnit(durationUnit)
					.durationAlreadyBooked(durationUnit.toInt(firstActivity.getDurationTotalBooked()))
					.build();
		}
		return _defaultParams;
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_PP_Order_Node_ID.equals(parameterName))
		{
			final PPOrderRoutingActivityId orderRoutingActivityId = PPOrderRoutingActivityId.ofRepoIdOrNull(getOrderId(), orderRoutingActivityRepoId);
			if (orderRoutingActivityId != null)
			{
				final PPOrderRoutingActivity orderRoutingActivity = orderRoutingRepository.getOrderRoutingActivity(orderRoutingActivityId);
				this.durationUnit = orderRoutingActivity.getDurationUnit();
				this.durationAlreadyBooked = durationUnit.toInt(orderRoutingActivity.getDurationTotalBooked());
			}
		}
	}

	@Override
	protected String doIt()
	{
		final Duration duration = getDuration();
		if (duration.isNegative() || duration.isZero())
		{
			throw new FillMandatoryException("Duration");
		}

		final PPOrderRoutingActivityId orderRoutingActivityId = getOrderRoutingActivityId();
		final PPOrderId orderId = orderRoutingActivityId.getOrderId();

		checkPreconditionsApplicable(orderId);

		final I_PP_Order order = getOrderById(orderId);
		final UomId finishedGoodsUomId = UomId.ofRepoId(order.getC_UOM_ID());
		final PPOrderRoutingActivity orderRoutingActivity = orderRoutingRepository.getOrderRoutingActivity(orderRoutingActivityId);

		costCollectorBL.createActivityControl(ActivityControlCreateRequest.builder()
				.order(order)
				.orderActivity(orderRoutingActivity)
				.movementDate(SystemTime.asZonedDateTime())
				.qtyMoved(Quantitys.zero(finishedGoodsUomId))
				.durationSetup(Duration.ZERO)
				.duration(duration)
				.build());

		return MSG_OK;
	}

	private I_PP_Order getOrderById(@NonNull final PPOrderId orderId)
	{
		return ordersCache.computeIfAbsent(orderId, orderBL::getById);
	}

	private PPOrderRoutingActivityId getOrderRoutingActivityId()
	{
		return PPOrderRoutingActivityId.ofRepoId(getOrderId(), orderRoutingActivityRepoId);
	}

	private PPOrderId getOrderId()
	{
		return PPOrderId.ofRepoId(getRecord_ID());
	}

	private Duration getDuration()
	{
		return DurationUtils.toWorkDurationRoundUp(duration, durationUnit.getTemporalUnit());
	}
}
