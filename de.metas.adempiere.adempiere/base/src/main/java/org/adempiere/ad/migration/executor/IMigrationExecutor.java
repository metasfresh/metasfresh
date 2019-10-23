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

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_Migration;

public interface IMigrationExecutor
{
	enum Action
	{
		Apply,
		Rollback,
	}

	enum CommitLevel
	{
		Batch,
		Step,
	}

	/**
	 * Get the current migration.
	 * 
	 * @return {@link I_AD_Migration} migration
	 */
	I_AD_Migration getAD_Migration();

	/**
	 * Get the current commit level.
	 * 
	 * @return {@link CommitLevel} commitLevel
	 */
	CommitLevel getCommitLevel();

	/**
	 * Sets the Migration Executor commit level.
	 * 
	 * @param commitLevel
	 */
	void setCommitLevel(CommitLevel commitLevel);

	/**
	 * Get the migration steps assigned to this migration.
	 * 
	 * @return {@link List}&lt;{@link I_AD_MigrationStep}&gt; migrationSteps
	 */
	List<I_AD_MigrationStep> getMigrationSteps();

	/**
	 * Sets the migration steps for the current migration.
	 * 
	 * @param steps
	 */
	void setMigrationSteps(List<I_AD_MigrationStep> steps);

	/**
	 * Execute migration depending on the migration {@link Action}. Action is detected.
	 */
	void execute();

	/**
	 * Parameterized {@link #execute()}
	 * 
	 * @param action
	 */
	void execute(final Action action);

	/**
	 * Get the status message (description) depending on the {@link X_AD_Migration} status code.
	 * 
	 * @return {@link String} status description
	 */
	String getStatusDescription();

	/**
	 * Get the exceptions that occured during the execution of the current migration.
	 * 
	 * @return {@link List}&lt;{@link Exception}&gt; exceptions
	 */
	List<Exception> getExecutionErrors();
}
