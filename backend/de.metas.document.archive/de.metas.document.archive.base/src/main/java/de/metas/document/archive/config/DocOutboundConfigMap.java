package de.metas.document.archive.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.jetbrains.annotations.Nullable;

import java.util.List;

final class DocOutboundConfigMap
{
	private final ImmutableMap<DocOutboundConfigQuery, DocOutboundConfig> byQuery;
	private final ImmutableListMultimap<AdTableId, DocOutboundConfig> byTableId;

	DocOutboundConfigMap(final List<DocOutboundConfig> list)
	{
		this.byQuery = Maps.uniqueIndex(list, config -> DocOutboundConfigQuery.builder()
				.tableId(config.getTableId())
				.docBaseType(config.getDocBaseType())
				.orgId(config.getOrgId())
				.build()
		);
		this.byTableId = list.stream().collect(ImmutableListMultimap.toImmutableListMultimap(DocOutboundConfig::getTableId, config -> config));
	}

	public ImmutableSet<AdTableId> getTableIds() {return this.byTableId.keySet();}

	@Nullable
	public DocOutboundConfig getByQuery(@NonNull final DocOutboundConfigQuery query)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> byQuery.get(query),
				() -> byQuery.get(query.withDocBaseType(null)),
				() -> byQuery.get(query.withOrgId(OrgId.ANY)),
				() -> byQuery.get(query.withDocBaseType(null).withOrgId(OrgId.ANY))
		);
	}

	@NonNull
	public ImmutableList<DocOutboundConfig> getByTableId(@NonNull final AdTableId tableId)
	{
		return byTableId.get(tableId);
	}
}
