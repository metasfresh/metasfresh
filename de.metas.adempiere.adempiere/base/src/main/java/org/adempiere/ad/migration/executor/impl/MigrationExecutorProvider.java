package org.adempiere.ad.migration.executor.impl;

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


import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.migration.executor.IMigrationExecutor;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.IMigrationExecutorProvider;
import org.adempiere.ad.migration.executor.IMigrationStepExecutor;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.exceptions.AdempiereException;

public class MigrationExecutorProvider implements IMigrationExecutorProvider
{
	private Map<String, Class<? extends IMigrationStepExecutor>> stepExecutorsByType = new ConcurrentHashMap<String, Class<? extends IMigrationStepExecutor>>();

	public MigrationExecutorProvider()
	{
		registerMigrationStepExecutor(X_AD_MigrationStep.STEPTYPE_SQLStatement, SQLStatementMigrationStepExecutor.class);
		registerMigrationStepExecutor(X_AD_MigrationStep.STEPTYPE_ApplicationDictionary, POMigrationStepExecutor.class);
	}

	@Override
	public IMigrationExecutorContext createInitialContext(final Properties ctx)
	{
		return new MigrationExecutorContext(ctx, this);
	}

	@Override
	public IMigrationExecutor newMigrationExecutor(final IMigrationExecutorContext migrationCtx, final int migrationId)
	{
		return new MigrationExecutor(migrationCtx, migrationId);
	}

	@Override
	public void registerMigrationStepExecutor(final String stepType, final Class<? extends IMigrationStepExecutor> executorClass)
	{
		stepExecutorsByType.put(stepType, executorClass);
	}

	@Override
	public IMigrationStepExecutor newMigrationStepExecutor(final IMigrationExecutorContext migrationCtx, final I_AD_MigrationStep step)
	{
		final String stepType = step.getStepType();
		final Class<? extends IMigrationStepExecutor> executorClass = stepExecutorsByType.get(stepType);

		if (executorClass == null)
		{
			throw new AdempiereException("Step type not supported: " + stepType);
		}

		try
		{
			return executorClass
					.getConstructor(IMigrationExecutorContext.class, I_AD_MigrationStep.class)
					.newInstance(migrationCtx, step);
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot not load step executor for class " + executorClass, e);
		}
	}
}
