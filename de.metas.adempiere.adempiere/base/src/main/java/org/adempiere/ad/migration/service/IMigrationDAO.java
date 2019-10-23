package org.adempiere.ad.migration.service;

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

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;

import de.metas.util.ISingletonService;

public interface IMigrationDAO extends ISingletonService
{
	/**
	 * @param ctx
	 * @param migrationId
	 * @return {@link I_AD_Migration} by migration id.
	 */
	I_AD_Migration retrieveMigrationOrNull(Properties ctx, int migrationId);

	/**
	 * @param ctx
	 * @param name
	 * @param seqNo
	 * @param entityType
	 * @param trxName
	 * @return true if migration was found using the specified properties, false otherwise.
	 */
	boolean existsMigration(Properties ctx, String name, int seqNo, String entityType, String trxName);

	/**
	 * @param migration
	 * @param statusCode
	 * @return integer the count of the migration steps for the specified migration.
	 */
	int countMigrationSteps(I_AD_Migration migration, String statusCode);

	/**
	 * Retrieve the migration steps for the specified migration.
	 * 
	 * @param migration
	 * @param sortAsc
	 * @return {@link List}&lt;{@link I_AD_MigrationStep}&gt;
	 */
	List<I_AD_MigrationStep> retrieveSteps(I_AD_Migration migration, boolean sortAsc);

	/**
	 * Merge {@link I_AD_Migration} from -> to.
	 * 
	 * @param to
	 * @param from
	 */
	void mergeMigration(I_AD_Migration to, I_AD_Migration from);

	/**
	 * @param migration
	 * @return integer the last {@link I_AD_Migration} sequence number.
	 */
	int getMigrationLastSeqNo(I_AD_Migration migration);

	/**
	 * @param step
	 * @return integer the last {@link I_AD_MigrationStep} sequence number.
	 */
	int getMigrationStepLastSeqNo(I_AD_MigrationStep step);

	/**
	 * @param step
	 * @return {@link List}&lt;{@link I_AD_MigrationData}&gt; of the specified migration step.
	 */
	List<I_AD_MigrationData> retrieveMigrationData(I_AD_MigrationStep step);
}
