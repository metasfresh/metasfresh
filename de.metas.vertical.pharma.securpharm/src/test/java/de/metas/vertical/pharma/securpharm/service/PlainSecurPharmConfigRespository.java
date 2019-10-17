package de.metas.vertical.pharma.securpharm.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Ignore;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
@Ignore
public class PlainSecurPharmConfigRespository implements SecurPharmConfigRespository
{
	public static PlainSecurPharmConfigRespository ofDefaultSandboxProperties()
	{
		return ofPropertiesFilename("./sandbox.properties");
	}

	public static PlainSecurPharmConfigRespository ofPropertiesFilename(final String propertiesFilename)
	{
		final SecurPharmConfig config = loadConfigFromPropertiesFile(propertiesFilename);
		return new PlainSecurPharmConfigRespository(config);
	}

	private static SecurPharmConfig loadConfigFromPropertiesFile(final String propertiesFilename)
	{
		try (InputStream in = new FileInputStream(propertiesFilename))
		{
			final Properties props = new Properties();
			props.load(in);
			return SecurPharmConfig.builder()
					.applicationUUID(props.getProperty("applicationUUID"))
					.authBaseUrl(props.getProperty("authBaseUrl"))
					.pharmaAPIBaseUrl(props.getProperty("pharmaAPIBaseUrl"))
					.certificatePath(props.getProperty("certificatePath"))
					.supportUserId(UserId.METASFRESH)
					.keystorePassword(props.getProperty("keystorePassword"))
					.build();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed loading " + propertiesFilename, e);
		}
	}

	private final Optional<SecurPharmConfig> config;

	private PlainSecurPharmConfigRespository(@Nullable final SecurPharmConfig config)
	{
		this.config = Optional.ofNullable(config);
	}

	@Override
	public Optional<SecurPharmConfig> getDefaultConfig()
	{
		return config;
	}

	@Override
	public SecurPharmConfig getById(final SecurPharmConfigId id)
	{
		throw new UnsupportedOperationException();
	}

}
