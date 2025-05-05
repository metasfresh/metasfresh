/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.bpartner;

import de.metas.common.bpartner.v2.request.JsonRequestGreeting;
import de.metas.common.bpartner.v2.request.JsonRequestGreetingUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.greeting.GreetingExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.greeting.UpsertGreetingRequest;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.JsonPersisterService;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JsonGreetingService
{
	private static final Logger logger = LogManager.getLogger(JsonGreetingService.class);

	@NonNull
	private final GreetingRepository greetingRepository;
	private final ExternalReferenceRestControllerService externalReferenceService;

	@Nullable
	public GreetingId persistGreeting(
			@NonNull final ExternalIdentifier contactIdentifier,
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestGreetingUpsertItem greetingUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final JsonPersisterService.ResponseItemCollector responseItemCollector)
	{
		final SyncAdvise effectiveSyncAdvise = CoalesceUtil.coalesceNotNull(greetingUpsertItem.getSyncAdvise(), parentSyncAdvise);

		final ExternalIdentifier greetingIdentifier = ExternalIdentifier.of(greetingUpsertItem.getIdentifier());

		final Greeting existingGreeting = resolveExternalGreetingIdentifier(orgId, greetingIdentifier)
				.map(greetingRepository::getById)
				.orElse(null);

		if (existingGreeting == null)
		{
			if (effectiveSyncAdvise.isFailIfNotExists() || greetingUpsertItem.getGreetingInfo() == null)
			{
				throw MissingResourceException.builder()
						.resourceName("Greeting")
						.resourceIdentifier(greetingUpsertItem.getIdentifier())
						.build();
			}
		}
		else if (!effectiveSyncAdvise.getIfExists().isUpdate() || greetingUpsertItem.getGreetingInfo() == null)
		{
			responseItemCollector.collectGreeting(contactIdentifier.getRawValue(), greetingIdentifier.getRawValue(), JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
			return existingGreeting.getId();
		}

		final UpsertGreetingRequest.UpsertGreetingRequestBuilder greetingBuilder = Optional.ofNullable(existingGreeting)
				.map(JsonGreetingService::initUpsertRequest)
				.map(UpsertGreetingRequest::toBuilder)
				.orElseGet(() -> UpsertGreetingRequest.builder().orgId(orgId));

		final JsonRequestGreeting greetingRequest = greetingUpsertItem.getGreetingInfo();

		if (greetingRequest.isNameSet())
		{
			if (greetingRequest.getName() == null)
			{
				logger.debug("Ignoring property \"name\" : null ");
			}
			else
			{
				greetingBuilder.name(greetingRequest.getName());
			}
		}

		if (greetingRequest.isGreetingSet())
		{
			if (greetingRequest.getGreeting() == null)
			{
				logger.debug("Ignoring property \"greeting\" : null ");
			}
			else
			{
				greetingBuilder.greeting(greetingRequest.getGreeting());
			}
		}

		if (greetingRequest.isLetterSalutationSet())
		{
			greetingBuilder.letterSalutation(greetingRequest.getLetterSalutation());
		}

		final UpsertGreetingRequest upsertGreetingRequest = greetingBuilder.build();

		final JsonResponseUpsertItem.SyncOutcome syncOutcome = upsertGreetingRequest.getGreetingId() != null
				? JsonResponseUpsertItem.SyncOutcome.UPDATED
				: JsonResponseUpsertItem.SyncOutcome.CREATED;

		responseItemCollector.collectGreeting(contactIdentifier.getRawValue(), greetingIdentifier.getRawValue(), syncOutcome);

		final Greeting greeting = greetingRepository.upsertGreeting(upsertGreetingRequest);

		return greeting.getId();
	}

	@NonNull
	private Optional<GreetingId> resolveExternalGreetingIdentifier(
			@NonNull final OrgId orgId,
			@NonNull final ExternalIdentifier greetingIdentifier)
	{
		switch (greetingIdentifier.getType())
		{
			case METASFRESH_ID:
				final GreetingId greetingId = greetingIdentifier.asMetasfreshId().mapToRepoId(GreetingId::ofRepoId);
				return Optional.of(greetingId);
			case EXTERNAL_REFERENCE:
				return externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, greetingIdentifier, GreetingExternalReferenceType.GREETING)
						.map(JsonMetasfreshId::getValue)
						.map(GreetingId::ofRepoId);
			case NAME:
				return greetingRepository.getByName(greetingIdentifier.asName())
						.map(Greeting::getId);
			default:
				throw new InvalidIdentifierException("Given external identifier type is not supported!")
						.setParameter("externalIdentifierType", greetingIdentifier.getType())
						.setParameter("rawExternalIdentifier", greetingIdentifier.getRawValue());
		}
	}

	@NonNull
	private static UpsertGreetingRequest initUpsertRequest(@NonNull final Greeting greeting)
	{
		return UpsertGreetingRequest.builder()
				.greetingId(greeting.getId())
				.greeting(greeting.getGreeting(Language.getBaseAD_Language()))
				.orgId(greeting.getOrgId())
				.letterSalutation(greeting.getLetterSalutation())
				.name(greeting.getName())
				.standardType(greeting.getStandardType())
				.build();
	}
}
