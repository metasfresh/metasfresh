package de.metas.ui.web.window.model;

import javax.annotation.PostConstruct;

import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.compiere.util.CacheMgt;
import org.compiere.util.ICacheResetListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This component listens to all cache invalidation events (see {@link CacheMgt}) and invalidates the right documents or included documents from {@link DocumentCollection}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class DocumentCacheInvalidationDispatcher implements ICacheResetListener
{
	private static final Logger logger = LogManager.getLogger(DocumentCacheInvalidationDispatcher.class);

	@Autowired
	private DocumentCollection documents;

	@PostConstruct
	private void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this);
	}

	@Override
	public int reset(final CacheInvalidateRequest request)
	{
		logger.debug("Got {}", request);

		final String rootTableName = request.getRootTableName();
		if (rootTableName == null)
		{
			logger.debug("Nothing to do, no rootTableName: {}", request);
			return 0;
		}

		final int rootRecordId = request.getRootRecordId();
		if (rootRecordId < 0)
		{
			logger.debug("Nothing to do, rootRecordId < 0: {}", request);
			return 0;
		}

		final String childTableName = request.getChildTableName();
		final int childRecordId = request.getChildRecordId();

		if (childTableName == null)
		{
			logger.debug("Invalidating the root document: {}", request);
			documents.invalidateDocumentByRecordId(rootTableName, rootRecordId);
			return 1;
		}
		else
		{
			logger.debug("Invalidating the included document: {}", request);
			documents.invalidateIncludedDocumentsByRecordId(rootTableName, rootRecordId, childTableName, childRecordId);
			return 1;
		}
	}

}
