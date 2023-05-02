package de.metas.audit.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@ToString
public final class ApiAuditConfigsMap
{
	public static ApiAuditConfigsMap ofList(@NonNull final List<ApiAuditConfig> list)
	{
		return !list.isEmpty() ? new ApiAuditConfigsMap(list) : EMPTY;
	}

	private static final ApiAuditConfigsMap EMPTY = new ApiAuditConfigsMap();

	private final ImmutableList<ApiAuditConfig> configs;
	private final ImmutableMap<ApiAuditConfigId, ApiAuditConfig> byId;

	private ApiAuditConfigsMap(@NonNull final List<ApiAuditConfig> list)
	{
		configs = ImmutableList.copyOf(list);
		byId = Maps.uniqueIndex(list, ApiAuditConfig::getApiAuditConfigId);
	}

	private ApiAuditConfigsMap()
	{
		configs = ImmutableList.of();
		byId = ImmutableMap.of();
	}

	public ImmutableList<ApiAuditConfig> getActiveConfigsByOrgId(@NonNull final OrgId orgId)
	{
		return configs.stream()
				.filter(ApiAuditConfig::isActive)
				.filter(config -> config.getOrgId().isAny()
						|| OrgId.equals(config.getOrgId(), orgId))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ApiAuditConfig getConfigById(@NonNull final ApiAuditConfigId id)
	{
		final ApiAuditConfig config = byId.get(id);
		if (config == null)
		{
			throw new AdempiereException("No config found for " + id);
		}
		return config;
	}
}
