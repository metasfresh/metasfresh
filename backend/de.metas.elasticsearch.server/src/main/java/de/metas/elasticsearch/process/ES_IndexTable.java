package de.metas.elasticsearch.process;

import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;

import java.util.Collection;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * SysAdmin process used to completely index a given table using all registered model indexers.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ES_IndexTable extends AbstractModelIndexerProcess
{
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@Override
	protected Collection<ESModelIndexer> getModelIndexers()
	{
		final int adTableId = getRecord_ID();
		final String modelTableName = adTableDAO.retrieveTableName(adTableId);
		return modelIndexingService.getModelIndexersByTableName(modelTableName);
	}
}
