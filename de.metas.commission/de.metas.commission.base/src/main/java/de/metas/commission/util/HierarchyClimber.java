package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;
import java.util.Map;

import de.metas.commission.model.I_C_Sponsor;

/**
 * Subclasses ascend or descent the commission hierarchy starting at a given node (sponsor) and going up or down a specified number of levels. On each level the method
 * {@link #actOnLevel(I_C_Sponsor, Map)} is called.
 * 
 * @author ts
 * 
 */
public abstract class HierarchyClimber
{

	/**
	 * Parameter <code>contextInfo</code> of method {@link #actOnLevel(I_C_Sponsor, int, int, Map)} contains an entry with this key and the {@link Result} of the current sponsor's predecessor (parent
	 * or child).
	 */
	public static final String CTX_PREDECESSOR_RESULT = "Result_Of_Predecessor";

	/**
	 * Parameter <code>contextInfo</code> of method {@link #actOnLevel(I_C_Sponsor, int, int, Map)} contains an entry with this key and sponsor's predecessor (I_C_Sponsor, parent or child).
	 */
	public static final String CTX_PREDECESSOR_SPONSOR = "Sponsor_Of_Predecessor";

	public enum Result
	{
		/**
		 * Count the current logical level and go on (unless logical <code>maxLevel</code> was reached)
		 */
		GO_ON,

		/**
		 * Don't count the current logical level, and don't increase the original logical <code>maxLevel</code> value by one.
		 */
		SKIP_IGNORE,

		/**
		 * Count the current logical level, but also increase the original logical <code>maxLevel</code> value by one.
		 */
		SKIP_EXTEND,

		/**
		 * Don't evaluate further levels, even if logical <code>maxLevel</code> has not been reached.
		 */
		FINISHED
	}

	/**
	 * 
	 * @param sponsorCurrentLevel
	 * @param logicalLevel
	 * @param contextInfo a map that can be used to provide arbitrary information between different invocations. Also contains {@link #CTX_PREDECESSOR_RESULT} and {@link #CTX_PREDECESSOR_SPONSOR}.
	 * @return a {@link Result} to indicate how to go on
	 */
	public abstract Result actOnLevel(
			I_C_Sponsor sponsorCurrentLevel,
			int logicalLevel,
			int hierarchyLevel,
			Map<String, Object> contextInfo);

	/**
	 * 
	 * @param sponsor the sponsor to start iterating the hierarchy with. Note: {@link #actOnLevel(I_C_Sponsor, Map)} will also be called for this sponsor.
	 * @param date the date for which the hierarchy is iterated
	 * @param maxLevel the number of logical levels in the hierarchy to iterate. The numbers of hierarchy levels actually visited can vary according to <code>actOnLevel</code> return values.
	 */
	public abstract void climb(I_C_Sponsor sponsor, int maxLevel);

	public abstract HierarchyClimber setDate(Timestamp date);
}
