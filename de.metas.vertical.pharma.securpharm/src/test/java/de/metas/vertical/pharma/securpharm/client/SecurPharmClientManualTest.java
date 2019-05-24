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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Ignore;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.DecodeDataMatrixResponse;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.VerifyProductResponse;

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

		final DataMatrixCode code = DataMatrixCode.ofBase64Encoded("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		System.out.println("Sending code: " + code);

		final DecodeDataMatrixResponse decodeResponse = client.decodeDataMatrix(code);
		System.out.println("decode response: " + decodeResponse);

		System.out.println("verifying product: " + decodeResponse.getProductData());
		VerifyProductResponse verifyResponse = client.verifyProduct(decodeResponse.getProductData());
		System.out.println("verify response: " + verifyResponse);
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
}
