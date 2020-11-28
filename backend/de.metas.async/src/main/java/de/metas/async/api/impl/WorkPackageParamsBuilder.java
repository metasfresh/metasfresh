package de.metas.async.api.impl;

import java.util.Collection;

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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;

import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageParamsBuilder;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/* package */class WorkPackageParamsBuilder implements IWorkPackageParamsBuilder
{
	// services
	private final transient IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);

	private final IWorkPackageBuilder _parentBuilder;
	private I_C_Queue_WorkPackage _workpackage;
	// private final Map<String, I_C_Queue_WorkPackage_Param> parameterName2param = new HashMap<>();
	private final Map<String, Object> parameterName2valueMap = new HashMap<>();

	// Status
	private final AtomicBoolean built = new AtomicBoolean(false);

	/* package */WorkPackageParamsBuilder(@NonNull final IWorkPackageBuilder parentBuilder)
	{
		_parentBuilder = parentBuilder;
	}

	@Override
	public void build()
	{
		markAsBuilt();

		if (parameterName2valueMap.isEmpty())
		{
			return;
		}

		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();
		for (final Map.Entry<String, Object> parameterName2value : parameterName2valueMap.entrySet())
		{
			final String parameterName = parameterName2value.getKey();
			final Object parameterValue = parameterName2value.getValue();

			final I_C_Queue_WorkPackage_Param workpackageParam = WorkPackageParamsUtil.createWorkPackageParamRecord(workpackage, parameterName);
			workpackageParamDAO.setParameterValue(workpackageParam, parameterValue);
			InterfaceWrapperHelper.save(workpackageParam);
		}
	}


	private final void assertNotBuilt()
	{
		Check.assume(!built.get(), "not already built");
	}

	private final void markAsBuilt()
	{
		final boolean wasAlreadyBuilt = built.getAndSet(true);
		Check.assume(!wasAlreadyBuilt, "not already built");
	}

	@Override
	public IWorkPackageBuilder end()
	{
		return _parentBuilder;
	}

	/* package */void setC_Queue_WorkPackage(final I_C_Queue_WorkPackage workpackage)
	{
		assertNotBuilt();
		_workpackage = workpackage;
	}

	private final I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		Check.assumeNotNull(_workpackage, "workpackage not null");
		return _workpackage;
	}

	@Override
	public IWorkPackageParamsBuilder setParameter(final String parameterName, final Object parameterValue)
	{
		assertNotBuilt();
		Check.assumeNotEmpty(parameterName, "parameterName not empty");

		parameterName2valueMap.put(parameterName, parameterValue);

		return this;
	}

	@Override
	public IWorkPackageParamsBuilder setParameters(final Map<String, ? extends Object> parameters)
	{
		assertNotBuilt();

		if (parameters == null || parameters.isEmpty())
		{
			return this;
		}

		for (final Map.Entry<String, ? extends Object> param : parameters.entrySet())
		{
			final String parameterName = param.getKey();
			Check.assumeNotEmpty(parameterName, "parameterName not empty");

			final Object parameterValue = param.getValue();
			parameterName2valueMap.put(parameterName, parameterValue);
		}

		return this;
	}
	
	@Override
	public IWorkPackageParamsBuilder setParameters(@Nullable final IParams parameters)
	{
		assertNotBuilt();

		if (parameters == null)
		{
			return this;
		}
		
		final Collection<String> parameterNames = parameters.getParameterNames();
		if(parameterNames.isEmpty())
		{
			return this;
		}

		for (final String parameterName : parameterNames)
		{
			Check.assumeNotEmpty(parameterName, "parameterName not empty");

			final Object parameterValue = parameters.getParameterAsObject(parameterName);
			parameterName2valueMap.put(parameterName, parameterValue);
		}

		return this;
	}
}
