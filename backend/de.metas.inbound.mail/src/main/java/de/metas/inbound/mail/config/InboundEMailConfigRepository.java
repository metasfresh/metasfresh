package de.metas.inbound.mail.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.inbound.mail.model.I_C_InboundMailConfig;
import de.metas.organization.OrgId;
import de.metas.request.RequestTypeId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
@DependsOn(Adempiere.BEAN_NAME) // requires database access
public class InboundEMailConfigRepository
{
	private final CCache<Integer, Map<InboundEMailConfigId, InboundEMailConfig>> //
	configsCache = CCache.newCache(I_C_InboundMailConfig.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	public List<InboundEMailConfig> getAll()
	{
		final Map<InboundEMailConfigId, InboundEMailConfig> configs = getAllConfigsIndexById();
		return ImmutableList.copyOf(configs.values());
	}

	public InboundEMailConfig getById(@NonNull final InboundEMailConfigId id)
	{
		final InboundEMailConfig config = getAllConfigsIndexById().get(id);
		if (config == null)
		{
			throw new AdempiereException("No config found for " + id);
		}
		return config;
	}

	public List<InboundEMailConfig> getByIds(@NonNull final Collection<InboundEMailConfigId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		final Map<InboundEMailConfigId, InboundEMailConfig> allConfigsIndexById = getAllConfigsIndexById();

		return ids.stream()
				.map(allConfigsIndexById::get)
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private Map<InboundEMailConfigId, InboundEMailConfig> getAllConfigsIndexById()
	{
		return configsCache.getOrLoad(0, this::retrieveAllConfigsIndexById);
	}

	private Map<InboundEMailConfigId, InboundEMailConfig> retrieveAllConfigsIndexById()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_InboundMailConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(InboundEMailConfigRepository::toInboundEMailConfig)
				.collect(GuavaCollectors.toImmutableMapByKey(InboundEMailConfig::getId));
	}

	private static InboundEMailConfig toInboundEMailConfig(final I_C_InboundMailConfig record)
	{
		return InboundEMailConfig.builder()
				.id(InboundEMailConfigId.ofRepoId(record.getC_InboundMailConfig_ID()))
				.protocol(InboundEMailProtocol.forCode(record.getProtocol()))
				.host(record.getHost())
				.port(record.getPort())
				.folder(record.getFolder())
				.username(record.getUserName())
				.password(record.getPassword())
				.debugProtocol(record.isDebugProtocol())
				.adClientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.requestTypeId(RequestTypeId.ofRepoIdOrNull(record.getR_RequestType_ID()))
				.build();
	}
}
