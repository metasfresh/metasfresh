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

package de.metas.serviceprovider.github.controller.auth;

import de.metas.common.util.EncryptUtil;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.UserNotAuthorizedException;
import de.metas.serviceprovider.github.GithubConstants;
import de.metas.serviceprovider.github.config.GithubConfigRepository;
import de.metas.serviceprovider.model.I_S_GithubConfig;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.github.IAuthenticateGithubService;
import de.metas.util.web.request.EditableHttpServletRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static de.metas.serviceprovider.github.config.GithubConfigName.GITHUB_SECRET;
import static de.metas.serviceprovider.github.config.GithubConfigName.GITHUB_USER;

@Service
public class AuthenticateGithubService implements IAuthenticateGithubService
{
	private final static Logger logger = LogManager.getLogger(AuthenticateGithubService.class);

	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	private final GithubConfigRepository githubConfigRepository;
	private final UserAuthTokenRepository userAuthTokenRepository;

	public AuthenticateGithubService(
			@NonNull final GithubConfigRepository githubConfigRepository,
			@NonNull final UserAuthTokenRepository userAuthTokenRepository)
	{
		this.githubConfigRepository = githubConfigRepository;
		this.userAuthTokenRepository = userAuthTokenRepository;
	}

	@Override
	public void authenticateAndLocateAuthorization(@NonNull final EditableHttpServletRequest requestWrapper)
	{
		try
		{
			final String githubSiganture = requestWrapper.getHeader(GithubConstants.X_HUB_SIGNATURE_256_HEADER);

			if (Check.isBlank(githubSiganture))
			{
				throw new UserNotAuthorizedException(null, null);
			}

			final I_S_GithubConfig githubConfig = authenticate(githubSiganture, requestWrapper.getRequestBodyAsString());

			final OrgId orgId = OrgId.ofRepoId(githubConfig.getAD_Org_ID());

			final UserId githubUserId = retrieveGithubUserId(orgId);

			final String authorizationToken = getAuthorizationToken(githubUserId);

			requestWrapper.setHeader("Authorization", authorizationToken);
		}
		catch (final Exception ex)
		{
			logger.error("Got error while trying to authenticate github request:" + ex.getLocalizedMessage(), ex);

			throw new UserNotAuthorizedException(null, null);
		}
	}

	@NonNull
	private I_S_GithubConfig authenticate(@NonNull final String githubSignature, @NonNull final String payload) throws NoSuchAlgorithmException, InvalidKeyException
	{
		final List<I_S_GithubConfig> githubConfigList = githubConfigRepository.listByName(GITHUB_SECRET);

		for (final I_S_GithubConfig githubConfig : githubConfigList)
		{
			final String githubSignatureSecret = githubConfig.getConfig();

			final String encryptedHash = computeEncryptedHash(payload, githubSignatureSecret);

			if (EncryptUtil.equals(encryptedHash, githubSignature))
			{
				return githubConfig;
			}
		}

		throw new AdempiereException("No S_GithubConfig matched with Github signature!");
	}

	@NonNull
	private UserId retrieveGithubUserId(@NonNull final OrgId orgId)
	{
		return githubConfigRepository.getOptionalConfigByNameAndOrg(GITHUB_USER, orgId)
				.map(Integer::parseInt)
				.map(UserId::ofRepoId)
				.orElseThrow(() -> new AdempiereException("No Github user configured in S_GithubConfig for org=" + orgId.getRepoId() + "!"));
	}

	@NonNull
	private String getAuthorizationToken(@NonNull final UserId userId)
	{
		final RoleId webuiRoleId = roleDAO.getUserRoles(userId)
				.stream()
				.filter(Role::isWebuiRole)
				.findFirst()
				.map(Role::getId)
				.orElseThrow(() -> new AdempiereException("No AD_Users_Role found for userId:" + userId + "!"));

		return userAuthTokenRepository.retrieveByUserId(userId, webuiRoleId).getAuthToken();
	}

	@NonNull
	private static String computeEncryptedHash(@NonNull final String body, @NonNull final String secret) throws InvalidKeyException, NoSuchAlgorithmException
	{
		return GithubConstants.SHA_256_PREFIX + EncryptUtil.encryptWithHmacSHA256(body, secret);
	}
}
