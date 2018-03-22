package org.adempiere.ad.modelvalidator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.reflect.Method;
import java.util.Set;

/* package */interface IPointcut
{
	String getPointcutId();

	Method getMethod();

	String getTableName();

	Class<?> getModelClass();

	/** @return true if the actual pointcut shall be executed after transaction commit */
	boolean isAfterCommit();

	boolean isMethodRequiresTiming();

	/**
	 * Converts given timing to the type which is expected by {@link #getMethod()}.
	 * 
	 * @param timing
	 * @return timing parameter converted to method's parameter type.
	 */
	Object convertToMethodTimingParameterType(int timing);

	PointcutType getType();

	Set<Integer> getTimings();

	/**
	 * If only changes in particular columns shall be considered, then return a set of those columns. Empty set means, no "filtering".
	 * 
	 * @return set of columns to be checked for changes or an empty set if no particular column was specified.
	 */
	Set<String> getColumnsToCheckForChanges();

	boolean isOnlyIfUIAction();
	
	boolean isSkipIfCopying();
}
