package org.eevolution.mrp.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;

public interface IMRPSegmentBL extends ISingletonService
{
	/**
	 * Creates a {@link IMRPSegment} which has only the AD_Client_ID set.
	 * 
	 * @param adClientId
	 * @return
	 */
	IMRPSegment createMRPSegment(int adClientId);

	IMRPSegment createMRPSegment(IMaterialPlanningContext mrpContext);

	/**
	 * Creates fully defined {@link IMRPSegment}s.
	 * 
	 * @param ctx
	 * @param mrpSegment0 initial MRP segment (not fully defined)
	 * @return list of fully defined segments; never return null or empty
	 * @see #isFullyDefined(IMRPSegment).
	 */
	List<IMRPSegment> createFullyDefinedMRPSegments(Properties ctx, IMRPSegment mrpSegment0);

	/**
	 * Checks if given segment is fully defined.
	 * 
	 * A fully defined segment is a segment where all dimensions are set.
	 * 
	 * Following dimensions are not checked: M_Product_ID.
	 * 
	 * @param mrpSegment
	 * @return true if segment is fully defined.
	 */
	boolean isFullyDefined(IMRPSegment mrpSegment);

	/**
	 *
	 * @param parentSegment
	 * @param childSegment
	 * @return true if <code>parentSegment</code> includes <code>childSegment</code>
	 */
	boolean isSegmentIncludes(IMRPSegment parentSegment, IMRPSegment childSegment);

}
