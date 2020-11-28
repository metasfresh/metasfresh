package de.metas.async.api;

/*
 * #%L
 * de.metas.async
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

import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.util.api.IParams;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;

/**
 * Creates {@link I_C_Queue_WorkPackage_Param}s for a {@link I_C_Queue_WorkPackage}.
 *
 * Please note that the parameters are created instantly and not when some "build" method is called.
 *
 * @author tsa
 *
 */
public interface IWorkPackageParamsBuilder
{
	/** Create all parameters */
	void build();

	/** @return parent builder */
	IWorkPackageBuilder end();

	/**
	 * Set/Updates given workpackage parameter
	 */
	IWorkPackageParamsBuilder setParameter(final String parameterName, @Nullable final Object parameterValue);

	/**
	 * Set/Updates given workpackage parameters
	 */
	IWorkPackageParamsBuilder setParameters(@Nullable final Map<String, ? extends Object> parameters);

	IWorkPackageParamsBuilder setParameters(@Nullable IParams parameters);
}
