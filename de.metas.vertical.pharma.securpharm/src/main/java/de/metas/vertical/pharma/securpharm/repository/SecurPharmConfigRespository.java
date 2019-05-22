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

package de.metas.vertical.pharma.securpharm.repository;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Config;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfigId;
import lombok.NonNull;

@Repository
public class SecurPharmConfigRespository
{
	private final CCache<Integer, Optional<SecurPharmConfig>> cache = CCache.<Integer, Optional<SecurPharmConfig>> builder()
			.tableName(I_M_Securpharm_Config.Table_Name)
			.build();

	public SecurPharmConfig getConfig()
	{
		return getConfigIfExists()
				.orElseThrow(() -> new AdempiereException("@NotFound@ @" + I_M_Securpharm_Config.COLUMNNAME_M_Securpharm_Config_ID + "@"));
	}

	public boolean isConfigured()
	{
		return getConfigIfExists().isPresent();
	}

	private Optional<SecurPharmConfig> getConfigIfExists()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	private Optional<SecurPharmConfig> retrieveConfig()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_M_Securpharm_Config configRecord = queryBL
				.createQueryBuilder(I_M_Securpharm_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Securpharm_Config.class);
		if (configRecord == null)
		{
			return null;
		}
		else
		{
			return Optional.of(ofRecord(configRecord));
		}
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
