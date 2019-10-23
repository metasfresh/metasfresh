package org.adempiere.ad.migration.logger.impl;

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


import org.adempiere.ad.migration.logger.IMigrationLoggerContext;
import org.adempiere.ad.migration.model.I_AD_Migration;

import de.metas.util.Check;

public class SingletonMigrationLoggerContext implements IMigrationLoggerContext
{
	private final I_AD_Migration migration;
	private boolean generateComments = false;

	public SingletonMigrationLoggerContext(final I_AD_Migration migration)
	{
		Check.assumeNotNull(migration, "migration not null");
		this.migration = migration;
	}

	/**
	 * @return true always
	 */
	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public I_AD_Migration getMigration(String key)
	{
		return migration;
	}

	@Override
	public void putMigration(String key, I_AD_Migration migration)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isGenerateComments()
	{
		return generateComments;
	}

	public void setGenerateComments(final boolean generateComments)
	{
		this.generateComments = generateComments;
	}
}
