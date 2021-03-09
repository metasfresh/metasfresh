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
import java.time.Duration;

import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.ResourceId;

import javax.annotation.Nullable;

/**
 * Routing (Workflow Service)
 *
 * @author Teo Sarca, www.arhipac.ro
 */
public interface RoutingService
{
	WorkingTime estimateWorkingTimePerOneUnit(PPRoutingActivity activity);

	/**
	 * Calculate node duration for 1 item, AD_Workflow.DurationUnit UOM will be used
	 *
	 * @param activity operation
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	Duration calculateDuration(PPRoutingActivity activity);

	/**
	 * Calculate workflow duration for given qty.
	 *
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	int calculateDurationDays(PPRoutingId routingId, @Nullable ResourceId plantId, BigDecimal qty);

	/**
	 * Return node base value in resource UOM (e.g. duration)
	 *
	 * @return value (e.g. duration)
	 */
	Duration getResourceBaseValue(PPRoutingActivity activity);
}
