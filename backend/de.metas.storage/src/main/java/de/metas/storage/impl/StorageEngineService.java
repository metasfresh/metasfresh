package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
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


import org.adempiere.util.Check;

import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;

public class StorageEngineService implements IStorageEngineService
{
	private IStorageEngine storageEngine = null;

	@Override
	public void registerStorageEngine(final IStorageEngine storageEngine)
	{
		Check.assumeNotNull(storageEngine, "storageEngine not null");

		if (this.storageEngine == storageEngine)
		{
			return;
		}
		
		Check.assumeNull(this.storageEngine, "Storage engine was not already configured");
		this.storageEngine = storageEngine;
	}

	@Override
	public IStorageEngine getStorageEngine()
	{
		Check.assumeNotNull(this.storageEngine, "Storage engine shall be configured first");
		return this.storageEngine;
	}

}
