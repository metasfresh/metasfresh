package de.metas.dunning.api;

/*
 * #%L
 * de.metas.dunning
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

import de.metas.dunning.interfaces.I_C_DunningLevel;

public interface IDunningCandidateQuery
{
	int getAD_Table_ID();

	int getRecord_ID();

	List<I_C_DunningLevel> getC_DunningLevels();

	boolean isActive();

	boolean isApplyClientSecurity();

	Boolean getProcessed();

	Boolean getWriteOff();

	/**
	 * If not empty, then this additional string will be <code>AND</code>ed to the query where clause.
	 * 
	 * @return
	 */
	String getAdditionalWhere();

	/**
	 * Defines the three possible levels of access filtering
	 *
	 */
	// 04766
	public enum ApplyAccessFilter
	{
		ACCESS_FILTER_RO,
		ACCESS_FILTER_RW,
		ACCESS_FILTER_NONE
	}
	
	/**
	 * If <code>true</code>, the result will only contain record to which the given user/role has read <b>and write</b> access. If <code>false</code>, then the result will contain records to this the
	 * user/role has read-access. The default is <code>false</code>.
	 * 
	 * @return
	 */
	ApplyAccessFilter getApplyAccessFilter();
}
