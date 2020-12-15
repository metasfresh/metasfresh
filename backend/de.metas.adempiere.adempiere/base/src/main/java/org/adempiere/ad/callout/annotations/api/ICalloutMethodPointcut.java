package org.adempiere.ad.callout.annotations.api;

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

import org.adempiere.ad.callout.api.ICalloutField;

public interface ICalloutMethodPointcut
{

	Set<String> getColumnNames();

	Method getMethod();

	Class<?> getModelClass();

	/** @return true if when invoking the callout method we need to provide the {@link ICalloutField} parameter too */
	boolean isParamCalloutFieldRequired();

	/** @return true if we shall skip invoking this callout if we are in record copying mode */
	boolean isSkipIfCopying();

	/** @return true if we shall skip invoking this callout if it's called not directly but via another callout */
	boolean isSkipIfIndirectlyCalled();
}
