/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.query.process;

import com.google.common.base.Stopwatch;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSFilterDescriptor;
import de.metas.fulltextsearch.config.FTSFilterDescriptorId;
import de.metas.fulltextsearch.query.FTSSearchRequest;
import de.metas.fulltextsearch.query.FTSSearchResult;
import de.metas.fulltextsearch.query.FTSSearchResultItem;
import de.metas.fulltextsearch.query.FTSSearchService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import org.compiere.SpringContextHolder;

import java.util.UUID;

public class ES_FTS_Filter_Test extends JavaProcess
{
	private final FTSConfigService ftsConfigService = SpringContextHolder.instance.getBean(FTSConfigService.class);
	private final FTSSearchService ftsSearchService = SpringContextHolder.instance.getBean(FTSSearchService.class);

	@Param(parameterName = "TestSearchText")
	private String p_TestSearchText;

	@Override
	protected String doIt() throws Exception
	{
		final FTSFilterDescriptorId filterId = FTSFilterDescriptorId.ofRepoId(getRecord_ID());
		final FTSFilterDescriptor filter = ftsConfigService.getFilterById(filterId);
		final FTSConfig ftsConfig = ftsConfigService.getConfigById(filter.getFtsConfigId());

		final FTSSearchRequest request = FTSSearchRequest.builder()
				.searchId("test-search-" + UUID.randomUUID())
				.searchText(p_TestSearchText)
				.esIndexName(ftsConfig.getEsIndexName())
				.filterDescriptor(filter)
				.build();
		addLog("Request: {}", request);

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final FTSSearchResult result = ftsSearchService.search(request);
		stopwatch.stop();

		addLog("Got {} items. Took {}", result.getItems().size(), stopwatch);

		for (final FTSSearchResultItem item : result.getItems())
		{
			addLog("Result item: {}", item);
		}

		return MSG_OK;
	}
}
