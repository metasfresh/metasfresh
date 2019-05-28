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

package de.metas.vertical.pharma.securpharm.client;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmConfigRespository;
import lombok.NonNull;

@Service
public class SecurPharmClientFactory
{
	private final SecurPharmConfigRespository configRespository;

	private final CCache<SecurPharmConfig, SecurPharmClient> clientsCache = CCache.<SecurPharmConfig, SecurPharmClient> builder()
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(5)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public SecurPharmClientFactory(@NonNull final SecurPharmConfigRespository configRespository)
	{
		this.configRespository = configRespository;
	}

	public SecurPharmClient createClient()
	{
		final SecurPharmConfig config = configRespository
				.getDefaultConfig()
				.orElseThrow(() -> new AdempiereException("No default SecurPharm config found"));

		return createClient(config);
	}

	public SecurPharmClient createClient(@NonNull final SecurPharmConfigId configId)
	{
		final SecurPharmConfig config = configRespository.getById(configId);
		return createClient(config);
	}

	private SecurPharmClient createClient(@NonNull final SecurPharmConfig config)
	{
		return clientsCache.getOrLoad(config, SecurPharmClient::createAndAuthenticate);
	}

}
