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

import org.junit.Ignore;

import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.service.PlainSecurPharmConfigRespository;

@Ignore
public class SecurPharmClientManualTest
{
	public static void main(final String[] args)
	{
		new SecurPharmClientManualTest().run();
	}

	private void run()
	{
		final PlainSecurPharmConfigRespository configRepo = PlainSecurPharmConfigRespository.ofDefaultSandboxProperties();
		final SecurPharmConfig config = configRepo.getDefaultConfig().get();
		System.out.println("Using config: " + config);

		final SecurPharmClient client = SecurPharmClient.createAndAuthenticate(config);

		final DataMatrixCode code = DataMatrixCode.ofBase64Encoded("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		System.out.println("Sending code: " + code);

		final DecodeDataMatrixClientResponse decodeResponse = client.decodeDataMatrix(code);
		System.out.println("decode response: " + decodeResponse);

		System.out.println("verifying product: " + decodeResponse.getProductDetails());
		VerifyProductClientResponse verifyResponse = client.verifyProduct(decodeResponse.getProductDetails());
		System.out.println("verify response: " + verifyResponse);
	}
}
