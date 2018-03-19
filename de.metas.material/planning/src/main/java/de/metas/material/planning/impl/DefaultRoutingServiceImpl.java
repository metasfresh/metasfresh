/**
 *
 */
package de.metas.material.planning.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.Env;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWorkflow;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_Node;

import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.exception.MrpException;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.uom.UOMUtil;

/**
 * Default Routing Service Implementation
 *
 * @author Teo Sarca
 */
public class DefaultRoutingServiceImpl implements RoutingService
{
	// private final Logger log = CLogMgt.getLogger(getClass());

	@Override
	public BigDecimal estimateWorkingTime(final I_AD_WF_Node node)
	{
		final double duration;
		if (node.getUnitsCycles().signum() == 0)
		{
			duration = node.getDuration();
		}
		else
		{
			duration = node.getDuration() / node.getUnitsCycles().doubleValue();
		}
		return BigDecimal.valueOf(duration);
	}

	@Override
	public BigDecimal estimateWorkingTime(final I_PP_Order_Node node, final BigDecimal qty)
	{
		final double unitDuration = node.getDuration();
		final double cycles = calculateCycles(node.getUnitsCycles(), qty);
		final BigDecimal duration = BigDecimal.valueOf(unitDuration * cycles);
		return duration;
	}

	@Override
	public BigDecimal estimateWorkingTime(final I_PP_Cost_Collector cc)
	{
		final String trxName = cc instanceof PO ? ((PO)cc).get_TrxName() : ITrx.TRXNAME_None;

		final BigDecimal qty = cc.getMovementQty();
		final I_PP_Order_Node node = InterfaceWrapperHelper.create(Env.getCtx(), cc.getPP_Order_Node_ID(), I_PP_Order_Node.class, trxName);

		return estimateWorkingTime(node, qty);
	}

	/**
	 * Calculate how many cycles are needed for given qty and units per cycle
	 *
	 * @param unitsCycle
	 * @param qty
	 * @return number of cycles
	 */
	protected int calculateCycles(final int unitsCycle, final BigDecimal qty)
	{
		BigDecimal cycles = qty;
		final BigDecimal unitsCycleBD = BigDecimal.valueOf(unitsCycle);
		if (unitsCycleBD.signum() > 0)
		{
			cycles = qty.divide(unitsCycleBD, 0, RoundingMode.UP);
		}
		return cycles.intValue();
	}

	/**
	 * Calculate node duration in DurationUnit UOM (see AD_Workflow.DurationUnit)
	 *
	 * @param node
	 * @param setupTime setup time (workflow duration unit)
	 * @param durationTotal (workflow duration unit)
	 * @reeturn duration
	 */
	protected BigDecimal calculateDuration(I_AD_WF_Node node, final I_PP_Cost_Collector cc)
	{
		if (node == null)
		{
			node = cc.getPP_Order_Node().getAD_WF_Node();
		}
		final I_AD_Workflow workflow = node.getAD_Workflow();
		final double batchSize = workflow.getQtyBatchSize().doubleValue();
		final double setupTime;
		final double duration;
		if (cc != null)
		{
			setupTime = cc.getSetupTimeReal().doubleValue();
			duration = cc.getDurationReal().doubleValue();
		}
		else
		{
			setupTime = node.getSetupTime();
			// Estimate total duration for 1 unit of final product as duration / units cycles
			duration = estimateWorkingTime(node).doubleValue();
		}
		final double totalDuration = setupTime / batchSize + duration;
		return BigDecimal.valueOf(totalDuration);
	}

	@Override
	public BigDecimal calculateDuration(final I_AD_WF_Node node)
	{
		return calculateDuration(node, null);
	}

	@Override
	public BigDecimal calculateDuration(final I_PP_Cost_Collector cc)
	{
		return calculateDuration(getAD_WF_Node(cc), cc);
	}

	@Override
	public BigDecimal calculateDurationDays(
			final I_AD_Workflow wf,
			final I_S_Resource plant,
			final BigDecimal qty)
	{
		if (plant == null)
		{
			return BigDecimal.ZERO;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(wf);
		final I_S_ResourceType S_ResourceType = plant.getS_ResourceType();

		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);

		final BigDecimal AvailableDayTime = BigDecimal.valueOf(resourceProductService.getTimeSlotHoursForResourceType(S_ResourceType));
		final int AvailableDays = resourceProductService.getAvailableDaysWeekForResourceType(S_ResourceType);

		final double durationBaseSec = getDurationBaseSec(wf.getDurationUnit());

		double durationTotal = 0.0;
		final MWFNode[] nodes = ((MWorkflow)wf).getNodes(false, Env.getAD_Client_ID(ctx));
		for (final I_AD_WF_Node node : nodes)
		{
			// Qty independent times:
			durationTotal += node.getQueuingTime();
			durationTotal += node.getSetupTime();
			durationTotal += node.getWaitingTime();
			durationTotal += node.getMovingTime();

			// Get OverlapUnits - number of units that must be completed before they are moved the next activity
			double overlapUnits = qty.doubleValue();
			if (node.getOverlapUnits() > 0 && node.getOverlapUnits() < overlapUnits)
			{
				overlapUnits = node.getOverlapUnits();
			}
			final double durationBeforeOverlap = node.getDuration() * overlapUnits;

			durationTotal += durationBeforeOverlap;
		}
		final BigDecimal requiredTime = BigDecimal.valueOf(durationTotal * durationBaseSec / 60 / 60);
		// TODO: implement here, Victor's suggestion - https://sourceforge.net/forum/message.php?msg_id=5179460

		// Weekly Factor
		final BigDecimal WeeklyFactor = BigDecimal.valueOf(7).divide(BigDecimal.valueOf(AvailableDays), 8, RoundingMode.UP);

		return requiredTime.multiply(WeeklyFactor).divide(AvailableDayTime, 0, RoundingMode.UP);
	}

	protected BigDecimal convertDurationToResourceUOM(final BigDecimal duration, final int S_Resource_ID, final I_AD_WF_Node node)
	{
		final Properties ctx = Env.getCtx();

		final I_AD_Workflow wf = MWorkflow.get(ctx, node.getAD_Workflow_ID());
		final I_S_Resource resource = InterfaceWrapperHelper.create(ctx, S_Resource_ID, I_S_Resource.class, ITrx.TRXNAME_None);
		final I_C_UOM resourceUOM = retrieveUOM(resource);

		return convertDuration(duration, wf.getDurationUnit(), resourceUOM);
	}

	private I_C_UOM retrieveUOM(final I_S_Resource resource)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		final I_M_Product product = resourceProductService.retrieveProductForResource(resource);

		// product is not null because it was created when the resource was first saved.
		return product.getC_UOM();
	}

	@Override
	public BigDecimal getResourceBaseValue(final int S_Resource_ID, final I_PP_Cost_Collector cc)
	{
		return getResourceBaseValue(S_Resource_ID, null, cc);
	}

	@Override
	public BigDecimal getResourceBaseValue(final int S_Resource_ID, final I_AD_WF_Node node)
	{
		return getResourceBaseValue(S_Resource_ID, node, null);
	}

	protected BigDecimal getResourceBaseValue(final int S_Resource_ID, I_AD_WF_Node node, final I_PP_Cost_Collector cc)
	{
		if (node == null)
		{
			node = cc.getPP_Order_Node().getAD_WF_Node();
		}
		final Properties ctx = node instanceof PO ? ((PO)node).getCtx() : Env.getCtx();
		final I_S_Resource resource = InterfaceWrapperHelper.create(ctx, S_Resource_ID, I_S_Resource.class, ITrx.TRXNAME_None);

		final I_C_UOM resourceUOM = retrieveUOM(resource);

		if (UOMUtil.isTime(resourceUOM))
		{
			final BigDecimal duration = calculateDuration(node, cc);
			final I_AD_Workflow wf = MWorkflow.get(ctx, node.getAD_Workflow_ID());
			final BigDecimal convertedDuration = convertDuration(duration, wf.getDurationUnit(), resourceUOM);
			return convertedDuration;
		}
		else
		{
			throw new MrpException("@NotSupported@ @C_UOM_ID@ - " + resourceUOM);
		}
	}

	protected I_AD_WF_Node getAD_WF_Node(final I_PP_Cost_Collector cc)
	{
		final I_PP_Order_Node activity = cc.getPP_Order_Node();
		return activity.getAD_WF_Node();
	}

	/**
	 * Convert durationUnit to seconds
	 *
	 * @param durationUnit
	 * @return duration in seconds
	 */
	public long getDurationBaseSec(final String durationUnit)
	{
		if (durationUnit == null)
		{
			return 0;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Second.equals(durationUnit))
		{
			return 1;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Minute.equals(durationUnit))
		{
			return 60;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Hour.equals(durationUnit))
		{
			return 3600;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Day.equals(durationUnit))
		{
			return 86400;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Month.equals(durationUnit))
		{
			return 2592000;
		}
		else if (X_AD_Workflow.DURATIONUNIT_Year.equals(durationUnit))
		{
			return 31536000;
		}
		return 0;
	}	// getDurationSec

	/**
	 * Convert uom to seconds
	 *
	 * @param uom time UOM
	 * @return duration in seconds
	 * @throws LiberoException if UOM is not supported
	 */
	public long getDurationBaseSec(final I_C_UOM uom)
	{
		//
		if (UOMUtil.isWeek(uom))
		{
			return 60 * 60 * 24 * 7;
		}
		if (UOMUtil.isDay(uom))
		{
			return 60 * 60 * 24;
		}
		else if (UOMUtil.isHour(uom))
		{
			return 60 * 60;
		}
		else if (UOMUtil.isMinute(uom))
		{
			return 60;
		}
		else if (UOMUtil.isSecond(uom))
		{
			return 1;
		}
		else
		{
			throw new MrpException("@NotSupported@ @C_UOM_ID@=" + uom.getName());
		}
	}

	/**
	 * Convert duration from given UOM to given UOM
	 *
	 * @param duration
	 * @param fromDurationUnit duration UOM
	 * @param toUOM target UOM
	 * @return duration converted to toUOM
	 */
	public BigDecimal convertDuration(final BigDecimal duration, final String fromDurationUnit, final I_C_UOM toUOM)
	{
		final double fromMult = getDurationBaseSec(fromDurationUnit);
		final double toDiv = getDurationBaseSec(toUOM);
		BigDecimal convertedDuration = BigDecimal.valueOf(duration.doubleValue() * fromMult / toDiv);
		// Adjust scale to UOM precision
		final int precision = toUOM.getStdPrecision();
		if (convertedDuration.scale() > precision)
		{
			convertedDuration = convertedDuration.setScale(precision, RoundingMode.HALF_UP);
		}
		//
		return convertedDuration;
	}
}
