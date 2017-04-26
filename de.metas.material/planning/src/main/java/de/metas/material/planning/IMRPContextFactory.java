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

import org.adempiere.util.ISingletonService;

public interface IMRPContextFactory extends ISingletonService
{

	IMutableMRPContext createInitialMRPContext();

	IMutableMRPContext createMRPContext(IMaterialPlanningContext mrpContext);

	/**
	 * Creates {@link IMaterialPlanningContext} from a given initial context and applying the given {@link IMRPSegment} on it.
	 *
	 * @param mrpContext0
	 * @param mrpSegment
	 * @return MRP context
	 */
	IMaterialPlanningContext createMRPContext(IMaterialPlanningContext mrpContext0, IMRPSegment mrpSegment);
}
