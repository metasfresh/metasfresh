/**
 *
 */
package de.metas.material.planning;

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

import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_Node;

/**
 * Rounting(Workflow Service)
 *
 * @author Teo Sarca, www.arhipac.ro
 */
public interface RoutingService
{
	public BigDecimal estimateWorkingTime(I_AD_WF_Node node);

	/**
	 * Estimate Activity Working Time for given qty.
	 * Please not that SetupTime or any other times are not considered.
	 *
	 * @param node activity
	 * @param qty qty required
	 * @return working time (using Workflow DurationUnit UOM)
	 */
	public BigDecimal estimateWorkingTime(I_PP_Order_Node node, BigDecimal qty);

	public BigDecimal estimateWorkingTime(I_PP_Cost_Collector cc);

	/**
	 * Calculate node duration for 1 item, AD_Workflow.DurationUnit UOM will be used
	 *
	 * @param node operation
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	public BigDecimal calculateDuration(I_AD_WF_Node node);

	/**
	 * Calculate workflow duration for given qty.
	 *
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	public BigDecimal calculateDurationDays(I_AD_Workflow wf, I_S_Resource plant, BigDecimal qty);

	/**
	 * Calculate activity duration based on reported data from Cost Collector.
	 *
	 * @param cc cost collector
	 * @return activity duration (using Workflow DurationUnit UOM)
	 */
	public BigDecimal calculateDuration(I_PP_Cost_Collector cc);

	/**
	 * Return cost collector base value in resource UOM (e.g. duration)
	 *
	 * @param S_Resource_ID resource
	 * @param cc cost collector
	 * @return value (e.g. duration)
	 */
	public BigDecimal getResourceBaseValue(int S_Resource_ID, I_PP_Cost_Collector cc);

	/**
	 * Return node base value in resource UOM (e.g. duration)
	 *
	 * @param S_Resource_ID resource
	 * @param node
	 * @return value (e.g. duration)
	 */
	public BigDecimal getResourceBaseValue(int S_Resource_ID, I_AD_WF_Node node);
}
