package de.metas.security;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_User_AuthToken;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class UserAuthTokenRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, UserAuthToken> authTokensByToken = CCache.newCache(I_AD_User_AuthToken.Table_Name + "#by#token", 50, CCache.EXPIREMINUTES_Never);

	public UserAuthToken getByToken(@NonNull final String token)
	{
		return authTokensByToken.getOrLoad(token, () -> retrieveByToken(token));
	}

	private UserAuthToken retrieveByToken(@NonNull final String token)
	{
		final List<I_AD_User_AuthToken> userAuthTokens = queryBL
				.createQueryBuilder(I_AD_User_AuthToken.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AuthToken, token)
				.setLimit(QueryLimit.TWO)
				.create()
				.list(I_AD_User_AuthToken.class);

		return extractSingleToken(userAuthTokens);
	}

	/**
	 * Supposed to be called from model interceptor.
	 */
	public void beforeSave(final I_AD_User_AuthToken userAuthTokenPO)
	{
		if (Check.isEmpty(userAuthTokenPO.getAuthToken(), true))
		{
			userAuthTokenPO.setAuthToken(generateAuthTokenString());
		}
		fromRecord(userAuthTokenPO); // make sure it's valid
	}

	public void resetAuthTokensAndSave(@NonNull final UserId userId, @NonNull final RoleId roleId)
	{
		final List<I_AD_User_AuthToken> userAuthTokenRecords = queryBL
				.createQueryBuilder(I_AD_User_AuthToken.class)
				// .addOnlyActiveRecordsFilter() reset 'em all; we don't want inactive tokens to retain their static values infinitely
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_User_ID, userId)
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_Role_ID, roleId)
				.create()
				.list(I_AD_User_AuthToken.class);

		for (final I_AD_User_AuthToken userAuthTokenRecord : userAuthTokenRecords)
		{
			userAuthTokenRecord.setAuthToken(generateAuthTokenString());
			saveRecord(userAuthTokenRecord);
		}
	}

	private static String generateAuthTokenString()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}

	public UserAuthToken retrieveByUserId(@NonNull final UserId userId, @NonNull final RoleId roleId)
	{
		final List<I_AD_User_AuthToken> userAuthTokens = queryBL
				.createQueryBuilder(I_AD_User_AuthToken.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_User_ID, userId)
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_Role_ID, roleId)
				.setLimit(QueryLimit.TWO)
				.create()
				.list(I_AD_User_AuthToken.class);

		return extractSingleToken(userAuthTokens);
	}

	private UserAuthToken extractSingleToken(@NonNull final List<I_AD_User_AuthToken> userAuthTokens)
	{
		if (userAuthTokens.isEmpty())
		{
			throw new AdempiereException("Invalid token (1)");
		}
		else if (userAuthTokens.size() > 1)
		{
			throw new AdempiereException("Invalid token (2)");
		}

		return fromRecord(userAuthTokens.get(0));
	}

	private static UserAuthToken fromRecord(final I_AD_User_AuthToken userAuthTokenPO)
	{
		return UserAuthToken.builder()
				.userId(UserId.ofRepoId(userAuthTokenPO.getAD_User_ID()))
				.authToken(userAuthTokenPO.getAuthToken())
				.description(userAuthTokenPO.getDescription())
				.clientId(ClientId.ofRepoId(userAuthTokenPO.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(userAuthTokenPO.getAD_Org_ID()))
				.roleId(RoleId.ofRepoId(userAuthTokenPO.getAD_Role_ID()))
				.build();
	}

	public UserAuthToken getOrCreateNew(@NonNull final CreateUserAuthTokenRequest request)
	{
		return getExisting(request).orElseGet(() -> createNew(request));
	}

	private Optional<UserAuthToken> getExisting(@NonNull final CreateUserAuthTokenRequest request)
	{
		return queryBL
				.createQueryBuilder(I_AD_User_AuthToken.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AD_User_ID, request.getUserId())
				.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AD_Client_ID, request.getClientId())
				.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AD_Org_ID, request.getOrgId())
				.addEqualsFilter(I_AD_User_AuthToken.COLUMNNAME_AD_Role_ID, request.getRoleId())
				.orderByDescending(I_AD_User_AuthToken.COLUMNNAME_AD_User_AuthToken_ID)
				.create()
				.firstOptional(I_AD_User_AuthToken.class)
				.map(UserAuthTokenRepository::fromRecord);
	}

	public UserAuthToken createNew(@NonNull final CreateUserAuthTokenRequest request)
	{
		final I_AD_User_AuthToken record = newInstanceOutOfTrx(I_AD_User_AuthToken.class);
		record.setAD_User_ID(request.getUserId().getRepoId());
		InterfaceWrapperHelper.setValue(record, I_AD_User_AuthToken.COLUMNNAME_AD_Client_ID, request.getClientId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setAD_Role_ID(request.getRoleId().getRepoId());
		record.setDescription(request.getDescription());
		record.setAuthToken(generateAuthTokenString());
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public void deleteUserAuthTokenByUserId(@NonNull final UserId userId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_AuthToken.class)
				.addEqualsFilter(I_AD_User_AuthToken.COLUMN_AD_User_ID, userId)
				.create()
				.delete();
	}
}
