/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.everhour.user;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.externalreference.ExternalId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.i18n.Language;
import de.metas.issue.tracking.everhour.api.EverhourClient;
import de.metas.issue.tracking.everhour.api.model.User;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.user.UserId;
import de.metas.user.UserQuery;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.serviceprovider.everhour.EverhourImportConstants.EverhourImporterSysConfig.ACCESS_TOKEN;
import static de.metas.serviceprovider.everhour.EverhourImportConstants.EverhourImporterSysConfig.BPARTNER_USER_IMPORT;

@Service
public class UserImporterService
{
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final ExternalReferenceRepository externalReferenceRepository;
	private final UserRepository userRepository;
	private final EverhourClient everhourClient;

	public UserImporterService(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final UserRepository userRepository,
			@NonNull final EverhourClient everhourClient)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.userRepository = userRepository;
		this.everhourClient = everhourClient;
	}

	@NonNull
	public UserId resolveUser(@NonNull final OrgId orgId, @NonNull final EverhourUserId everhourUserId)
	{
		final UserId userRecordId = getUserFromExternalReference(orgId, everhourUserId).orElse(null);

		if (userRecordId != null)
		{
			return userRecordId;
		}

		final ImportUsersRequest importUsersRequest = retrieveEverhourUsers(orgId);

		final Map<EverhourUserId, UserId> externalId2UserId = importEverhourUsers(importUsersRequest);

		return externalId2UserId.get(everhourUserId);
	}

	@NonNull
	private ImportUsersRequest retrieveEverhourUsers(@NonNull final OrgId orgId)
	{
		final BPartnerId bPartnerId = getConfiguredBPartnerId();

		final Language bpartnerLanguage = bPartnerBL.getLanguage(bPartnerId)
				.orElseGet(Env::getLanguage);

		final String apiKey = getEverhourApiKey();

		final Map<EverhourUserId, User> id2User = everhourClient.getUsers(apiKey)
				.stream()
				.collect(Collectors.toMap(user -> EverhourUserId.ofId(user.getId()),
										  Function.identity()));

		return ImportUsersRequest.builder()
				.orgId(orgId)
				.bpartnerId(bPartnerId)
				.bpartnerLanguage(bpartnerLanguage)
				.id2User(id2User)
				.build();
	}

	@NonNull
	private Map<EverhourUserId, UserId> importEverhourUsers(@NonNull final ImportUsersRequest importUsersRequest)
	{
		final ImmutableMap.Builder<EverhourUserId, UserId> externalId2UserId = ImmutableMap.builder();

		for (final Map.Entry<EverhourUserId, User> id2User : importUsersRequest.getId2User().entrySet())
		{
			final EverhourUserId everhourUserId = id2User.getKey();

			final UserId metasfreshUserId = getUserFromExternalReference(importUsersRequest.getOrgId(), everhourUserId)
					.orElseGet(() -> importUser(importUsersRequest, everhourUserId));

			externalId2UserId.put(everhourUserId, metasfreshUserId);
		}

		return externalId2UserId.build();
	}

	@NonNull
	private UserId importUser(
			@NonNull final ImportUsersRequest importUsersRequest,
			@NonNull final EverhourUserId everhourUserId)
	{
		final UserId userId = getExistingUserByEmail(importUsersRequest, everhourUserId)
				.orElseGet(() -> createUser(importUsersRequest, everhourUserId));

		final ExternalId userExternalId = ExternalId.of(ExternalSystem.EVERHOUR, String.valueOf(everhourUserId.getId()));

		insertUserExternalRef(userId, userExternalId, importUsersRequest.getOrgId());

		return userId;
	}

	@NonNull
	private Optional<UserId> getUserFromExternalReference(
			@NonNull final OrgId orgId,
			@NonNull final EverhourUserId everhourUserId)
	{
		final ExternalReferenceQuery query = ExternalReferenceQuery.builder()
				.orgId(orgId)
				.externalSystem(ExternalSystem.EVERHOUR)
				.externalReference(String.valueOf(everhourUserId.getId()))
				.externalReferenceType(ExternalUserReferenceType.USER_ID)
				.build();

		return Optional.ofNullable(externalReferenceRepository.getReferencedRecordIdOrNullBy(query))
				.map(UserId::ofRepoIdOrNull);
	}

	private Optional<UserId> getExistingUserByEmail(
			@NonNull final ImportUsersRequest importUsersRequest,
			@NonNull final EverhourUserId everhourUserId)
	{
		final User everhourUser = importUsersRequest.getId2User().get(everhourUserId);

		final UserQuery userQuery = UserQuery.builder()
				.orgId(importUsersRequest.getOrgId())
				.email(everhourUser.getEmail())
				.build();

		final List<de.metas.user.User> users = userRepository.getByQuery(userQuery);

		if (users.isEmpty())
		{
			return Optional.empty();
		}

		final de.metas.user.User existingUser = CollectionUtils.singleElement(users);

		return Optional.of(existingUser.getId());
	}

	@NonNull
	private UserId createUser(
			@NonNull final ImportUsersRequest importUsersRequest,
			@NonNull final EverhourUserId everhourUserId)
	{
		final User everhourUser = importUsersRequest.getId2User().get(everhourUserId);

		final de.metas.user.User userToCreate = de.metas.user.User.builder()
				.name(everhourUser.getName())
				.emailAddress(everhourUser.getEmail())
				.language(importUsersRequest.getBpartnerLanguage())
				.bpartnerId(importUsersRequest.getBpartnerId())
				.build();

		return userRepository.save(userToCreate).getId();
	}

	private void insertUserExternalRef(
			@NonNull final UserId userId,
			@NonNull final ExternalId externalId,
			@NonNull final OrgId orgId)
	{
		final ExternalReference externalReference = ExternalReference.builder()
				.orgId(orgId)
				.externalReference(externalId.getId())
				.recordId(userId.getRepoId())
				.externalSystem(externalId.getExternalSystem())
				.externalReferenceType(ExternalUserReferenceType.USER_ID)
				.build();

		externalReferenceRepository.save(externalReference);
	}

	@NonNull
	private BPartnerId getConfiguredBPartnerId()
	{
		return Optional.ofNullable(sysConfigBL.getValue(BPARTNER_USER_IMPORT.getName()))
				.map(Integer::parseInt)
				.map(BPartnerId::ofRepoId)
				.orElseThrow(() -> new AdempiereException("AD_SysConfig de.metas.issue.tracking.everhour.bpartnerUserImport needs to be set for Everhour user import.")
						.markAsUserValidationError());
	}

	@NonNull
	private String getEverhourApiKey()
	{
		return Optional.ofNullable(sysConfigBL.getValue(ACCESS_TOKEN.getName()))
				.orElseThrow(() -> new AdempiereException("AD_SysConfig de.metas.issue.tracking.everhour.accessToken needs to be set for Everhour user import.")
						.markAsUserValidationError());
	}
}
