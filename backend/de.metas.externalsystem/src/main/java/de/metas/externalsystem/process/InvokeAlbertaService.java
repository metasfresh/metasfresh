/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.AlbertaExternalSystem;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.GetExternalReferenceByRecordIdReq;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerReference;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRole;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleRepository;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class InvokeAlbertaService
{
	private static final String EXTERNAL_SYSTEM_COMMAND_SYNC_BPARTNER = "syncBPartnerById";
	private static final Set<AlbertaRoleType> ALBERTA_ROLE_TYPES_TO_SYNC = ImmutableSet.of(
			AlbertaRoleType.Hospital,
			AlbertaRoleType.NursingHome,
			AlbertaRoleType.NursingService,
			AlbertaRoleType.Payer,
			AlbertaRoleType.PhysicianDoctor,
			AlbertaRoleType.Pharmacy);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final AlbertaRoleRepository albertaRoleRepository;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ExternalSystemConfigRepo externalSystemConfigDAO;
	private final ExternalSystemConfigService externalSystemConfigService;

	public InvokeAlbertaService(
			@NonNull final AlbertaRoleRepository albertaRoleRepository,
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigDAO,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		this.albertaRoleRepository = albertaRoleRepository;
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalSystemConfigDAO = externalSystemConfigDAO;
		this.externalSystemConfigService = externalSystemConfigService;
	}

	@NonNull
	public Stream<JsonExternalSystemRequest> streamSyncExternalRequestsForBPartnerIds(
			@NonNull final Set<BPartnerId> bPartnerIds,
			@NonNull final ExternalSystemAlbertaConfigId configId,
			@NonNull final PInstanceId pInstanceId,
			@NonNull final OrgId orgId)
	{
		return bPartnerIds
				.stream()
				.map(this::getAlbertaRoleForBPartner)
				.flatMap(Collection::stream)
				.map(this::toOptionalAlbertaBPartnerReference)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(albertaReference -> this.toSyncBPartnerExternalSystemRequest(orgId, configId, pInstanceId, albertaReference));
	}

	@NonNull
	private ImmutableList<AlbertaRole> getAlbertaRoleForBPartner(final @NonNull BPartnerId bPartnerId)
	{
		return albertaRoleRepository.getByPartnerId(bPartnerId)
				.stream()
				.filter(albertaRole -> ALBERTA_ROLE_TYPES_TO_SYNC.contains(albertaRole.getRole()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Optional<AlbertaBPartnerReference> toOptionalAlbertaBPartnerReference(final @NonNull AlbertaRole albertaRole)
	{
		final AlbertaBPartnerReference.AlbertaBPartnerReferenceBuilder builder = AlbertaBPartnerReference.builder();
		builder.albertaRoleType(albertaRole.getRole());

		final GetExternalReferenceByRecordIdReq getExternalRefRequest = GetExternalReferenceByRecordIdReq.builder()
				.externalReferenceType(BPartnerExternalReferenceType.BPARTNER)
				.externalSystem(AlbertaExternalSystem.ALBERTA)
				.recordId(albertaRole.getBPartnerId().getRepoId())
				.build();

		return externalReferenceRepository.getExternalReferenceByMFReference(getExternalRefRequest)
				.map(ExternalReference::getExternalReference)
				.map(externalRef -> builder.externalReference(externalRef).build());
	}

	@NonNull
	private JsonExternalSystemRequest toSyncBPartnerExternalSystemRequest(
			@NonNull final OrgId orgId,
			@NonNull final ExternalSystemAlbertaConfigId configId,
			@NonNull final PInstanceId pInstanceId,
			@NonNull final AlbertaBPartnerReference albertaBPartnerReference)
	{
		final ExternalSystemParentConfig config = externalSystemConfigDAO.getById(configId);

		return JsonExternalSystemRequest.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
				.externalSystemName(JsonExternalSystemName.of(ExternalSystemType.Alberta.getName()))
				.parameters(extractParameters(config, albertaBPartnerReference))
				.orgCode(orgDAO.getById(orgId).getValue())
				.command(EXTERNAL_SYSTEM_COMMAND_SYNC_BPARTNER)
				.adPInstanceId(JsonMetasfreshId.of(PInstanceId.toRepoId(pInstanceId)))
				.traceId(externalSystemConfigService.getTraceId())
				.writeAuditEndpoint(config.getAuditEndpointIfEnabled())
				.build();
	}

	@NonNull
	private Map<String, String> extractParameters(
			@NonNull final ExternalSystemParentConfig externalSystemParentConfig,
			@NonNull final AlbertaBPartnerReference albertaBPartnerReference)
	{
		final ExternalSystemAlbertaConfig albertaConfig = ExternalSystemAlbertaConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_API_KEY, albertaConfig.getApiKey());
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, albertaConfig.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_TENANT, albertaConfig.getTenant());
		parameters.put(ExternalSystemConstants.PARAM_ALBERTA_ID, albertaBPartnerReference.getExternalReference());
		parameters.put(ExternalSystemConstants.PARAM_ALBERTA_ROLE, albertaBPartnerReference.getAlbertaRoleType().name());
		parameters.put(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE, albertaConfig.getValue());

		return parameters;
	}
}
