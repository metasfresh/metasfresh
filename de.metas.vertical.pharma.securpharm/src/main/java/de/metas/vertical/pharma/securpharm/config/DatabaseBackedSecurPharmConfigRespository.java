/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.config;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Config;
import lombok.NonNull;

@Repository
@Primary
public class DatabaseBackedSecurPharmConfigRespository implements SecurPharmConfigRespository
{
	private final CCache<Integer, Optional<SecurPharmConfigId>> defaultConfigIdCache = CCache.<Integer, Optional<SecurPharmConfigId>> builder()
			.tableName(I_M_Securpharm_Config.Table_Name)
			.build();

	private final CCache<SecurPharmConfigId, SecurPharmConfig> configsCache = CCache.<SecurPharmConfigId, SecurPharmConfig> builder()
			.tableName(I_M_Securpharm_Config.Table_Name)
			.build();

	@Override
	public Optional<SecurPharmConfig> getDefaultConfig()
	{
		return getDefaultConfigId().map(this::getById);
	}

	private Optional<SecurPharmConfigId> getDefaultConfigId()
	{
		return defaultConfigIdCache.getOrLoad(0, this::retrieveDefaultConfigId);
	}

	private Optional<SecurPharmConfigId> retrieveDefaultConfigId()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final SecurPharmConfigId configId = queryBL
				.createQueryBuilder(I_M_Securpharm_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(SecurPharmConfigId::ofRepoIdOrNull);

		return Optional.ofNullable(configId);
	}

	@Override
	public SecurPharmConfig getById(@NonNull final SecurPharmConfigId configId)
	{
		return configsCache.getOrLoad(configId, this::retrieveById);
	}

	private SecurPharmConfig retrieveById(@NonNull final SecurPharmConfigId configId)
	{
		final I_M_Securpharm_Config record = loadOutOfTrx(configId, I_M_Securpharm_Config.class);
		return ofRecord(record);
	}

	private static SecurPharmConfig ofRecord(@NonNull final I_M_Securpharm_Config config)
	{
		return SecurPharmConfig.builder()
				.id(SecurPharmConfigId.ofRepoId(config.getM_Securpharm_Config_ID()))
				.certificatePath(config.getCertificatePath())
				.applicationUUID(config.getApplicationUUID())
				.authBaseUrl(config.getAuthPharmaRestApiBaseURL())
				.pharmaAPIBaseUrl(config.getPharmaRestApiBaseURL())
				.keystorePassword(config.getTanPassword())
				.supportUserId(UserId.ofRepoId(config.getSupport_User_ID()))
				.build();
	}
}
