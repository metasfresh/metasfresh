/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.outboundendpoint;

import de.metas.cache.CCache;
import de.metas.externalsystem.model.I_ExternalSystem_Outbound_Endpoint;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ExternalSystemOutboundEndpointRepository
{
	CCache<ExternalSystemOutboundEndpointId, ExternalSystemOutboundEndpoint> endpointsCache = CCache.<ExternalSystemOutboundEndpointId, ExternalSystemOutboundEndpoint>builder().tableName(I_ExternalSystem_Outbound_Endpoint.Table_Name)
			.build();

	@NonNull
	public ExternalSystemOutboundEndpoint getById(@NonNull final ExternalSystemOutboundEndpointId id)
	{
		return endpointsCache.getOrLoad(id, this::retrieveById);
	}

	@NonNull
	private ExternalSystemOutboundEndpoint retrieveById(@NonNull final ExternalSystemOutboundEndpointId id)
	{
		final I_ExternalSystem_Outbound_Endpoint endpointRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Outbound_Endpoint.class);
		if (endpointRecord == null)
		{
			throw new AdempiereException("No Outbound Endpoint found for " + id);
		}
		return fromRecord(endpointRecord);
	}

	@NonNull
	private static ExternalSystemOutboundEndpoint fromRecord(@NonNull final I_ExternalSystem_Outbound_Endpoint endpointRecord)
	{
		return ExternalSystemOutboundEndpoint.builder()
				.id(ExternalSystemOutboundEndpointId.ofRepoId(endpointRecord.getExternalSystem_Outbound_Endpoint_ID()))
				.value(endpointRecord.getValue())
				.endpointUrl(endpointRecord.getOutboundHttpEP())
				.method(endpointRecord.getOutboundHttpMethod())
				.authType(OutboundEndpointAuthType.ofCode(endpointRecord.getAuthType()))
				.clientId(endpointRecord.getClientId())
				.clientSecret(endpointRecord.getClientSecret())
				.token(endpointRecord.getAuthToken())
				.user(endpointRecord.getLoginUsername())
				.password(endpointRecord.getPassword())
				.sasSignature(endpointRecord.getSasSignature())
				.build();
	}
}
