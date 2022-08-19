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

package de.metas.externalsystem.externalservice.authorization;

import de.metas.externalsystem.externalservice.utility.NotificationHelper;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartupHouseKeepingTask implements IStartupHouseKeepingTask
{

	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AD_USER_ID = "de.metas.externalsystem.externalservice.authorization.AD_User_ID";
	private static final String SYS_CONFIG_EXTERNAL_SYSTEM_AD_ROLE_ID = "de.metas.externalsystem.externalservice.authorization.AD_Role_ID";

	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND = AdMessageKey.of("External_Systems_Authorization_SysConfig_Not_Found_Error");
	private static final AdMessageKey EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_PROVIDED_SYSCONFIG_VALUE_DOES_NOT_EXIST = AdMessageKey.of("External_Systems_Authorization_SysConfig_Value_Does_Not_Exist");

	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final UserAuthTokenRepository authTokenRepository;

	public StartupHouseKeepingTask(@NonNull final UserAuthTokenRepository authTokenRepository)
	{
		this.authTokenRepository = authTokenRepository;
	}

	@Override
	public Optional<String> getAuthToken()
	{
		final Optional<I_AD_User> user = getAndValidateUserId();
		if (!user.isPresent())
		{
			return Optional.empty();
		}

		final UserId userId = UserId.ofRepoId(user.get().getAD_User_ID());
		final Optional<RoleId> roleId = getAndValidateRoleId(userId);
		if (!roleId.isPresent())

		{
			return Optional.empty();
		}

		final UserAuthToken userAuthToken = authTokenRepository.retrieveOptionalByUserAndRoleId(userId, roleId.get())
				.orElseGet(() -> createNewUserAuthToken(user.get(), roleId.get()));

		return Optional.of(userAuthToken.getAuthToken());
	}

	@NonNull
	private CreateUserAuthTokenRequest buildCreateUserAuthTokenRequest(@NonNull final I_AD_User user, @NonNull final RoleId roleId)
	{
		return CreateUserAuthTokenRequest.builder()
				.userId(UserId.ofRepoId(user.getAD_User_ID()))
				.roleId(roleId)
				.orgId(OrgId.ofRepoId(user.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(user.getAD_Client_ID()))
				.build();
	}

	@NonNull
	private UserAuthToken createNewUserAuthToken(@NonNull final I_AD_User user, @NonNull final RoleId givenRoleId)
	{
		final CreateUserAuthTokenRequest request = buildCreateUserAuthTokenRequest(user, givenRoleId);
		return authTokenRepository.createNew(request);
	}

	@NonNull
	private Optional<Integer> getSysConfigValue(@NonNull final String sysConfig)
	{
		final String sysConfigValueAsString = sysConfigBL.getValue(sysConfig);
		if (Check.isNotBlank(sysConfigValueAsString))
		{
			return Optional.of(Integer.parseInt(sysConfigValueAsString));
		}

		NotificationHelper.sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_SYSCONFIG_NOT_FOUND, sysConfig, null);

		return Optional.empty();
	}

	@NonNull
	private Optional<I_AD_User> getAndValidateUserId()
	{
		final Optional<UserId> givenUserId = getSysConfigValue(SYS_CONFIG_EXTERNAL_SYSTEM_AD_USER_ID)
				.map(UserId::ofRepoId);

		if (!givenUserId.isPresent())
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(userDAO.getById(givenUserId.get()));
		}
		catch (final Exception e)
		{
			final String msgParam = "AD_User_ID = " + givenUserId.get().getRepoId();
			NotificationHelper.sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_PROVIDED_SYSCONFIG_VALUE_DOES_NOT_EXIST, msgParam, e);

			return Optional.empty();
		}
	}

	@NonNull
	private Optional<RoleId> getAndValidateRoleId(@NonNull final UserId userId)
	{
		final Optional<RoleId> givenRoleId = getSysConfigValue(SYS_CONFIG_EXTERNAL_SYSTEM_AD_ROLE_ID)
				.map(RoleId::ofRepoId);

		if (!givenRoleId.isPresent())
		{
			return Optional.empty();
		}

		if (!roleDAO.retrieveUserIdsForRoleId(givenRoleId.get()).contains(userId))
		{
			final String msgParam = "AD_Role_ID = " + givenRoleId.get().getRepoId() + " that is associated with AD_User_ID = " + userId.getRepoId();
			NotificationHelper.sendErrorNotification(EXTERNAL_SYSTEM_AUTH_NOTIFICATION_CONTENT_PROVIDED_SYSCONFIG_VALUE_DOES_NOT_EXIST, msgParam, null);

			return Optional.empty();
		}

		return givenRoleId;
	}
}
