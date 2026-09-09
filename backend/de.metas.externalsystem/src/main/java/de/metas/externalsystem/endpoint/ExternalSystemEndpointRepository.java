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

package de.metas.externalsystem.endpoint;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.cache.CCache;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Repository Tables: ExternalSystem_Endpoint
 * Repository Cluster: sole owner of {@code ExternalSystem_Endpoint}.
 */
@Repository
public class ExternalSystemEndpointRepository
{
	CCache<ExternalSystemEndpointId, ExternalSystemEndpoint> endpointsCache = CCache.<ExternalSystemEndpointId, ExternalSystemEndpoint>builder().tableName(I_ExternalSystem_Endpoint.Table_Name)
			.build();

	@NonNull
	public ExternalSystemEndpoint getById(@NonNull final ExternalSystemEndpointId id)
	{
		return endpointsCache.getOrLoad(id, this::retrieveById);
	}

	/**
	 * Batch-load endpoints in a single query, to avoid a {@link #getById(ExternalSystemEndpointId)}-per-item N+1
	 * when resolving endpoints for a collection of items (e.g. inside a stream).
	 */
	@NonNull
	public ImmutableMap<ExternalSystemEndpointId, ExternalSystemEndpoint> getByIds(@NonNull final Collection<ExternalSystemEndpointId> ids)
	{
		final ImmutableSet<ExternalSystemEndpointId> distinctIds = ImmutableSet.copyOf(ids);
		if (distinctIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final List<I_ExternalSystem_Endpoint> endpointRecords = InterfaceWrapperHelper.loadByRepoIdAwares(distinctIds, I_ExternalSystem_Endpoint.class);

		return endpointRecords.stream()
				.map(ExternalSystemEndpointRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(ExternalSystemEndpoint::getId, Function.identity()));
	}

	@NonNull
	private ExternalSystemEndpoint retrieveById(@NonNull final ExternalSystemEndpointId id)
	{
		final I_ExternalSystem_Endpoint endpointRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Endpoint.class);
		if (endpointRecord == null)
		{
			throw new AdempiereException("No Endpoint found for " + id);
		}
		return fromRecord(endpointRecord);
	}

	@NonNull
	private static ExternalSystemEndpoint fromRecord(@NonNull final I_ExternalSystem_Endpoint endpointRecord)
	{
		return ExternalSystemEndpoint.builder()
				.id(ExternalSystemEndpointId.ofRepoId(endpointRecord.getExternalSystem_Endpoint_ID()))
				.value(endpointRecord.getValue())
				.transportType(TransportType.ofCode(endpointRecord.getTransportType()))
				// HTTP transport fields (nullable — only set for HTTP transport)
				.endpointUrl(endpointRecord.getHttpEndPoint())
				.method(parseHttpMethod(endpointRecord.getOutboundHttpMethod()))
				.contentType(parseMediaType(endpointRecord.getContentType()))
				// HTTP authentication fields
				.authType(EndpointAuthType.ofNullableCode(endpointRecord.getAuthType()))
				.clientId(endpointRecord.getClientId())
				.clientSecret(endpointRecord.getClientSecret())
				.token(endpointRecord.getAuthToken())
				.user(endpointRecord.getLoginUsername())
				.password(endpointRecord.getPassword())
				.sasSignature(endpointRecord.getSasSignature())
				// SFTP transport fields (nullable — only set for SFTP transport)
				.sftpHost(endpointRecord.getSftpHost())
				.sftpPort(endpointRecord.getSftpPort())
				.sftpUsername(endpointRecord.getSftpUsername())
				.sftpAuthType(parseSftpAuthType(endpointRecord.getSftpAuthType()))
				.sshPrivateKey(endpointRecord.getSshPrivateKey())
				.sftpRemotePath(endpointRecord.getSftpRemotePath())
				.sftpFilenamePattern(endpointRecord.getSftpFilenamePattern())
				.sftpPollingIntervalMs(endpointRecord.getSftpPollingIntervalMs() > 0 ? endpointRecord.getSftpPollingIntervalMs() : null)
				.processedDirectory(endpointRecord.getProcessedDirectory())
				.errorDirectory(endpointRecord.getErrorDirectory())
				.isArrayFanOut(endpointRecord.isArrayFanOut())
				// OAuth2 + file-upload fields
				.oauthTokenUrl(endpointRecord.getOAuthTokenUrl())
				.oauthScope(endpointRecord.getOAuthScope())
				.isFileUpload(endpointRecord.isFileUpload())
				.build();
	}

	@Nullable
	private static HttpMethod parseHttpMethod(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}
		return HttpMethod.ofCode(code);
	}

	@Nullable
	private static MediaType parseMediaType(@Nullable final String contentType)
	{
		if (Check.isBlank(contentType))
		{
			return null;
		}
		return MediaType.parseMediaType(contentType);
	}

	@Nullable
	private static SftpAuthType parseSftpAuthType(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return null;
		}
		return SftpAuthType.ofCode(code);
	}
}
