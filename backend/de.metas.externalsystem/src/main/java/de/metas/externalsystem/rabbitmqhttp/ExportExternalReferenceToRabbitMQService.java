/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.rabbitmqhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.util.Check;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.GetExternalReferenceByRecordIdReq;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.externalreference.ExportExternalReferenceToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ExportExternalReferenceToRabbitMQService extends ExportExternalReferenceToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_EXTERNAL_REFERENCE = "exportExternalReference";

	private final UserGroupRepository userGroupRepository;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;

	protected ExportExternalReferenceToRabbitMQService(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalReferenceRepository externalReferenceRepository,
			final @NonNull UserGroupRepository userGroupRepository,
			final @NonNull BPartnerCompositeRepository bpartnerCompositeRepository,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender, externalReferenceRepository, externalSystemConfigService);

		this.userGroupRepository = userGroupRepository;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
	}

	@Override
	protected @NonNull Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(final @NonNull ExternalReferenceId externalReferenceId)
	{
		final UserId createdBy = externalReferenceRepository.getCreatedBy(externalReferenceId);

		final Set<UserGroupId> assignedUserGroupIds = userGroupRepository.getAssignedGroupIdsByUserId(createdBy);

		if (Check.isEmpty(assignedUserGroupIds))
		{
			return Optional.empty();
		}

		final ImmutableList<ExternalSystemParentConfig> rabbitMQParentConfigs = externalSystemConfigRepo.getActiveByType(ExternalSystemType.RabbitMQ);

		return Optional.of(rabbitMQParentConfigs.stream()
								   .filter(ExternalSystemParentConfig::isActive)
								   .map(ExternalSystemParentConfig::getChildConfig)
								   .map(ExternalSystemRabbitMQConfig::cast)
								   .filter(ExternalSystemRabbitMQConfig::isSyncExternalReferencesToRabbitMQ)
								   .filter(externalSystemRabbitMQConfig -> externalSystemRabbitMQConfig.shouldExportBasedOnUserGroup(assignedUserGroupIds))
								   .map(IExternalSystemChildConfig::getId)
								   .collect(ImmutableSet.toImmutableSet()));
	}

	@Override
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, @NonNull final ExternalReferenceId externalReferenceId)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL, rabbitMQConfig.getRemoteUrl());
		parameters.put(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY, rabbitMQConfig.getRoutingKey());
		parameters.put(ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN, rabbitMQConfig.getAuthToken());
		parameters.put(ExternalSystemConstants.PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST, buildLookupRequest(externalReferenceId));

		return parameters;
	}

	@Override
	protected boolean isSyncExternalReferenceEnabled(final @NonNull IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemRabbitMQConfig rabbitMQConfig = ExternalSystemRabbitMQConfig.cast(childConfig);

		return rabbitMQConfig.isSyncExternalReferencesToRabbitMQ();
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_EXTERNAL_REFERENCE;
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.RabbitMQ;
	}

	@NonNull
	private String buildLookupRequest(@NonNull final ExternalReferenceId externalReferenceId)
	{
		final JsonExternalReferenceLookupRequest request = buildJsonExternalReferenceLookupRequest(externalReferenceId);

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(request);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to write JsonExternalReferenceLookupRequest as String")
					.appendParametersToMessage()
					.setParameter("JsonExternalReferenceLookupRequest", request);
		}
	}

	@NonNull
	private JsonExternalReferenceLookupRequest buildJsonExternalReferenceLookupRequest(@NonNull final ExternalReferenceId externalReferenceId)
	{
		final ExternalReference externalReference = externalReferenceRepository.getById(externalReferenceId);

		final ImmutableList.Builder<JsonExternalReferenceLookupItem> lookupItemCollector = ImmutableList.builder();

		lookupItemCollector.add(toJsonExternalReferenceLookupItem(externalReference));

		getAdditionalExternalReferenceItems(externalReference).forEach(lookupItemCollector::add);

		return JsonExternalReferenceLookupRequest.builder()
				.items(lookupItemCollector.build())
				.systemName(JsonExternalSystemName.of(externalReference.getExternalSystem().getCode()))
				.build();
	}

	@NonNull
	private Stream<JsonExternalReferenceLookupItem> getAdditionalExternalReferenceItems(@NonNull final ExternalReference externalReference)
	{
		if ((externalReference.getExternalReferenceType() instanceof BPartnerExternalReferenceType))
		{
			final BPartnerId bPartnerId = BPartnerId.ofRepoId(externalReference.getRecordId());

			return getAdditionalExternalReferenceItems(bPartnerId, externalReference.getExternalSystem());
		}

		return Stream.empty();
	}

	@NonNull
	private Stream<JsonExternalReferenceLookupItem> getAdditionalExternalReferenceItems(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final IExternalSystem externalSystem)
	{
		final ExternalReferenceResolver externalReferenceResolver = ExternalReferenceResolver.of(externalSystem, externalReferenceRepository);

		final BPartnerComposite bPartnerComposite = bpartnerCompositeRepository.getById(bPartnerId);

		final Stream<Integer> bPartnerLocationIds = bPartnerComposite.streamBPartnerLocationIds()
				.filter(Objects::nonNull)
				.map(BPartnerLocationId::getRepoId);

		final Stream.Builder<JsonExternalReferenceLookupItem> lookupItemCollector = Stream.builder();

		externalReferenceResolver.getLookupItems(bPartnerLocationIds, BPLocationExternalReferenceType.BPARTNER_LOCATION)
				.forEach(lookupItemCollector::add);

		final Stream<Integer> bPartnerContactIds = bPartnerComposite.streamContactIds()
				.filter(Objects::nonNull)
				.map(BPartnerContactId::getRepoId);

		externalReferenceResolver.getLookupItems(bPartnerContactIds, ExternalUserReferenceType.USER_ID)
				.forEach(lookupItemCollector::add);

		return lookupItemCollector.build();
	}

	@NonNull
	private static JsonExternalReferenceLookupItem toJsonExternalReferenceLookupItem(@NonNull final ExternalReference externalReference)
	{
		return JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReference.getExternalReference())
				.type(externalReference.getExternalReferenceType().getCode())
				.build();
	}

	@Value(staticConstructor = "of")
	private static class ExternalReferenceResolver
	{
		IExternalSystem externalSystemType;
		ExternalReferenceRepository externalReferenceRepository;

		public Stream<JsonExternalReferenceLookupItem> getLookupItems(
				@NonNull final Stream<Integer> metasfreshRecordIdStream,
				@NonNull final IExternalReferenceType externalReferenceType)
		{
			return metasfreshRecordIdStream
					.map(id -> GetExternalReferenceByRecordIdReq.builder()
							.recordId(id)
							.externalSystem(externalSystemType)
							.externalReferenceType(externalReferenceType)
							.build())
					.map(externalReferenceRepository::getExternalReferenceByMFReference)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.map(ExportExternalReferenceToRabbitMQService::toJsonExternalReferenceLookupItem);
		}
	}
}
