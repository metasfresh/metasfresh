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

package de.metas.fulltextsearch.query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.elasticsearch.IESSystem;
import de.metas.fulltextsearch.config.ESFieldName;
import de.metas.fulltextsearch.config.ESQueryTemplate;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSFilterDescriptor;
import de.metas.fulltextsearch.config.FTSJoinColumn;
import de.metas.fulltextsearch.config.FTSJoinColumnList;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.OrgIdAccessList;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.permissions.Access;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.DeprecationHandler;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class FTSSearchService
{
	private static final Logger logger = LogManager.getLogger(FTSSearchService.class);
	private final FTSConfigService ftsConfigService;
	private final FTSSearchResultRepository ftsSearchResultRepository;
	private final IESSystem elasticsearchSystem = Services.get(IESSystem.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	private static final String SYSCONFIG_ResultMaxSize = "fulltextsearch.query.limit";
	private static final int DEFAULT_ResultMaxSize = 100;

	public FTSSearchService(
			@NonNull final FTSConfigService ftsConfigService,
			@NonNull final FTSSearchResultRepository ftsSearchResultRepository)
	{
		this.ftsConfigService = ftsConfigService;
		this.ftsSearchResultRepository = ftsSearchResultRepository;
	}

	public FTSSearchResult search(@NonNull final FTSSearchRequest request)
	{
		logger.debug("search: request: {}", request);

		final FTSConfig ftsConfig = ftsConfigService.getConfigByESIndexName(request.getEsIndexName());
		logger.debug("search: ftsConfig: {}", ftsConfig);

		final ESQueryTemplate jsonQueryTemplate = ftsConfig.getQueryCommand();
		final String jsonQuery = jsonQueryTemplate.resolve(createQueryEvalCtx(request, ftsConfig));

		logger.debug("search: elasticsearch query: {}", jsonQuery);

		try
		{
			final SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());

			final XContentParser parser = XContentFactory
					.xContent(XContentType.JSON)
					.createParser(
							new NamedXContentRegistry(searchModule.getNamedXContents()),
							DeprecationHandler.THROW_UNSUPPORTED_OPERATION,
							jsonQuery);

			final RestHighLevelClient elasticsearchClient = elasticsearchSystem.elasticsearchClient();
			final SearchResponse elasticsearchResponse = elasticsearchClient
					.search(new SearchRequest()
									.indices(ftsConfig.getEsIndexName())
									.source(SearchSourceBuilder.fromXContent(parser)
											.size(getResultMaxSize())),
							RequestOptions.DEFAULT
					);

			final FTSSearchResult ftsResult = FTSSearchResult.builder()
					.searchId(request.getSearchId())
					.items(Arrays.stream(elasticsearchResponse.getHits().getHits())
							.map(hit -> extractFTSSearchResultItem(hit, request, ftsConfig))
							.collect(ImmutableList.toImmutableList()))
					.build();

			ftsSearchResultRepository.saveNew(ftsResult);

			logger.debug("search: result: {}", ftsResult);
			return ftsResult;
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private int getResultMaxSize()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_ResultMaxSize, DEFAULT_ResultMaxSize);
	}

	private Evaluatee createQueryEvalCtx(
			final @NonNull FTSSearchRequest request,
			final @NonNull FTSConfig ftsConfig)
	{
		final Evaluatees.MapEvaluateeBuilder builder = Evaluatees.mapBuilder();
		builder.put(ESQueryTemplate.PARAM_query, request.getSearchText());

		final ESQueryTemplate jsonQueryTemplate = ftsConfig.getQueryCommand();
		if (jsonQueryTemplate.isOrgFilterParameterRequired())
		{
			builder.put(ESQueryTemplate.PARAM_orgFilter, buildOrgIdsFilterPart(request.getUserRolePermissionsKey()));
		}

		return builder.build();
	}

	private String buildOrgIdsFilterPart(@Nullable final UserRolePermissionsKey userRolePermissionsKey)
	{
		if (userRolePermissionsKey == null)
		{
			return "";
		}

		final IUserRolePermissions userRolePermissions = userRolePermissionsDAO.getUserRolePermissions(userRolePermissionsKey);
		final OrgIdAccessList orgIdAccessList = userRolePermissions.getOrgAccess(null, Access.READ);
		if (orgIdAccessList.isAny())
		{
			return "";
		}
		else
		{
			final ImmutableSet<OrgId> orgIds = orgIdAccessList.toSet();
			String orgIdsCommaSeparated = orgIds.stream()
					.map(OrgId::getRepoId)
					.map(String::valueOf)
					.collect(Collectors.joining(", "));
			if (orgIdsCommaSeparated.isEmpty())
			{
				orgIdsCommaSeparated = "-1";
			}

			return ", \"filter\": { \"terms\": { \"ad_org_id\": [" + orgIdsCommaSeparated + "] } }";
		}
	}

	private static FTSSearchResultItem extractFTSSearchResultItem(
			@NonNull final SearchHit hit,
			@NonNull final FTSSearchRequest request,
			@NonNull final FTSConfig ftsConfig)
	{
		return FTSSearchResultItem.builder()
				.key(extractKey(hit, request.getFilterDescriptor(), ftsConfig))
				.json(toJson(hit))
				.build();
	}

	private static FTSSearchResultItem.KeyValueMap extractKey(
			@NonNull final SearchHit hit,
			@NonNull final FTSFilterDescriptor filterDescriptor,
			@NonNull final FTSConfig ftsConfig)
	{
		final FTSJoinColumnList joinColumns = filterDescriptor.getJoinColumns();

		final ArrayList<FTSSearchResultItem.KeyValue> keyValues = new ArrayList<>(joinColumns.size());
		for (final FTSJoinColumn joinColumn : joinColumns)
		{
			final ESFieldName esFieldName = ftsConfig.getEsFieldNameById(joinColumn.getEsFieldId());
			final Object value = extractValue(hit, esFieldName, joinColumn.getValueType());
			keyValues.add(FTSSearchResultItem.KeyValue.ofJoinColumnAndValue(joinColumn, value));
		}

		return new FTSSearchResultItem.KeyValueMap(keyValues);
	}

	@Nullable
	private static Object extractValue(
			@NonNull final SearchHit hit,
			@NonNull final ESFieldName esFieldName,
			@NonNull final FTSJoinColumn.ValueType valueType)
	{
		if (ESFieldName.ID.equals(esFieldName))
		{
			final String esDocumentId = hit.getId();
			return convertValueToType(esDocumentId, valueType);
		}
		else
		{
			final Object value = hit.getSourceAsMap().get(esFieldName.getAsString());
			return convertValueToType(value, valueType);
		}
	}

	@Nullable
	private static Object convertValueToType(@Nullable final Object valueObj, @NonNull final FTSJoinColumn.ValueType valueType)
	{
		if (valueObj == null)
		{
			return null;
		}
		if (valueType == FTSJoinColumn.ValueType.INTEGER)
		{
			return NumberUtils.asIntegerOrNull(valueObj);
		}
		else if (valueType == FTSJoinColumn.ValueType.STRING)
		{
			return valueObj.toString();
		}
		else
		{
			throw new AdempiereException("Cannot convert `" + valueObj + "` (" + valueObj.getClass() + ") to " + valueType);
		}
	}

	private static String toJson(@NonNull final SearchHit hit)
	{
		return Strings.toString(hit, true, true);
	}

	public FTSConfig getConfigById(@NonNull final FTSConfigId ftsConfigId)
	{
		return ftsConfigService.getConfigById(ftsConfigId);
	}
}
