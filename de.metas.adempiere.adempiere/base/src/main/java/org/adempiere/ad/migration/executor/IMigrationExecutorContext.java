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


import java.util.List;
import java.util.Properties;

public interface IMigrationExecutorContext
{
	public static enum MigrationOperation
	{
		BOTH,
		DDL,
		DML,
	};

	/**
	 * Get current {@link Properties} context.
	 * 
	 * @return {@link Properties} ctx
	 */
	Properties getCtx();

	/**
	 * Get current Migration Executor Provider factory.
	 * 
	 * @return {@link IMigrationExecutorProvider} factory
	 */
	IMigrationExecutorProvider getMigrationExecutorProvider();

	/**
	 * Returns true if the migration should fail on the first error, false otherwise.
	 * 
	 * @return boolean
	 */
	boolean isFailOnFirstError();

	/**
	 * Set flag true if the migration should fail on the first error, false otherwise.
	 * 
	 * @param failOnFirstError
	 */
	void setFailOnFirstError(boolean failOnFirstError);

	/**
	 * Set current {@link MigrationOperation}
	 * 
	 * @param operation
	 */
	void setMigrationOperation(MigrationOperation operation);

	/**
	 * Returns true if the DML should be applied, false otherwise.
	 * 
	 * @return boolean
	 */
	boolean isApplyDML();

	/**
	 * Returns true if the DDL should be applied, false otherwise.
	 * 
	 * @return boolean
	 */
	boolean isApplyDDL();

	/**
	 * Returns true if missing columns should be skipped, false otherwise.
	 * 
	 * @return boolean
	 */
	boolean isSkipMissingColumns();

	/**
	 * Returns true if missing tables should be skipped, false otherwise.
	 * 
	 * @return boolean
	 */
	boolean isSkipMissingTables();

	/**
	 * Add method of Heap implementation for postponed executables.
	 * 
	 * @param ddlExecutable
	 */
	void addPostponedExecutable(IPostponedExecutable ddlExecutable);

	/**
	 * Pop method of Heap implementation for postponed executables.
	 * 
	 * @return {@link List}&lt;{@link IPostponedExecutable}&gt;
	 */
	List<IPostponedExecutable> popPostponedExecutables();
}
