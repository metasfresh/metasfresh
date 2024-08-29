package de.metas.report;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.acct.api.ChartOfAccountsId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;

final public class DocOutboundConfigMap
{
	private final ImmutableMap<DocOutboundConfigId, DocOutboundConfig> byId;
	private final ImmutableList<DocOutboundConfig> list;

	public DocOutboundConfigMap(@NonNull final List<DocOutboundConfig> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byId = Maps.uniqueIndex(list, DocOutboundConfig::getId);
	}

	public DocOutboundConfig getById(@NonNull final DocOutboundConfigId id)
	{
		DocOutboundConfig config = byId.get(id);
		if (config == null)
		{
			throw new AdempiereException("No Docoutbound Congfig found for " + id);
		}
		return config;
	}

	public ImmutableList<DocOutboundConfig> getAllConfigs()
	{
		return ImmutableList.copyOf(list);
	}
}
