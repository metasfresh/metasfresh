package org.adempiere.ad.migration.executor;

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


import java.util.Properties;

import org.adempiere.ad.migration.model.I_AD_MigrationStep;

import de.metas.util.ISingletonService;

public interface IMigrationExecutorProvider extends ISingletonService
{
	/**
	 * Creates a new instance of a {@link IMigrationExecutorContext} implementation and returns it.
	 * 
	 * @param ctx
	 * @return {@link IMigrationExecutorContext} migrationExecutorContext
	 */
	IMigrationExecutorContext createInitialContext(Properties ctx);

	/**
	 * Registers a new {@link MigrationStepExecutor} for the current {@link IMigrationExecutorProvider} instance.
	 * 
	 * @param stepType
	 * @param executorClass
	 */
	void registerMigrationStepExecutor(String stepType, Class<? extends IMigrationStepExecutor> executorClass);

	/**
	 * Creates a new instance of a {@link IMigrationExecutor} implementation for the current context and returns it.
	 * 
	 * @param ctx
	 * @param migrationId
	 * @return {@link IMigrationExecutor} migrationExecutor
	 */
	IMigrationExecutor newMigrationExecutor(IMigrationExecutorContext ctx, int migrationId);

	/**
	 * Creates a new instance of a {@link IMigrationStepExecutor} implementation for the current migration context and returns it.
	 * 
	 * @param migrationCtx
	 * @param step
	 * @return {@link IMigrationStepExecutor} migrationStepExecutor
	 */
	IMigrationStepExecutor newMigrationStepExecutor(IMigrationExecutorContext migrationCtx, I_AD_MigrationStep step);
}
