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

package de.metas.vertical.pharma.securpharm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Ignore;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;

@Ignore
public class SecurPharmClientManualTest
{
	public static void main(final String[] args)
	{
		new SecurPharmClientManualTest().run();
	}

	private void run()
	{
		final SecurPharmConfig config = getConfig();
		System.out.println("Using config: " + config);

		final SecurPharmClient client = SecurPharmClient.createAndAuthenticate(config);

		final String code = fromBase64("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		System.out.println("Sending code: " + code);

		final SecurPharmProductDataResult productData = client.decodeDataMatrix(code);
		System.out.println("response: " + productData);

	}

	private SecurPharmConfig getConfig()
	{
		try (InputStream in = new FileInputStream("./sandbox.properties"))
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
			throw new AdempiereException(e);
		}
	}

	private static String fromBase64(final String s)
	{
		return new String(Base64.getDecoder().decode(s.getBytes()));
	}
}
