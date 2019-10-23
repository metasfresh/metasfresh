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


import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;

import de.metas.util.ISingletonService;

public interface IMigrationBL extends ISingletonService
{
	/**
	 * Updates {@link I_AD_Migration}'s status fields.
	 * 
	 * NOTE: this method is also saving the migration object.
	 * 
	 * @param migration
	 */
	void updateStatus(I_AD_Migration migration);

	/**
	 * Sort steps by creation TIMESTAMP.
	 * 
	 * @param migration
	 */
	void sortStepsByCreated(I_AD_Migration migration);

	/**
	 * Merge {@link I_AD_Migration} from -> to.
	 * 
	 * @param to
	 * @param from
	 */
	void mergeMigration(I_AD_Migration to, I_AD_Migration from);

	/**
	 * @param migration
	 * @return String migration summary
	 */
	String getSummary(I_AD_Migration migration);

	/**
	 * @param step
	 * @return String migration step summary
	 */
	String getSummary(I_AD_MigrationStep step);

	/**
	 * Increment migration {@link I_AD_Migration} sequence number.
	 * 
	 * @param migration
	 */
	void setSeqNo(I_AD_Migration migration);

	/**
	 * Increment migration step {@link I_AD_MigrationStep} sequence number.
	 * 
	 * @param step
	 */
	void setSeqNo(I_AD_MigrationStep step);
}
