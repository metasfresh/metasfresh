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

import java.util.Base64;

import org.junit.Test;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;

public class SecurPharmClientTest
{
	// @Ignore
	@Test
	public void testClient()
	{
		// get real data when testing
		final SecurPharmConfig config = SecurPharmConfig.builder()
				.securPharmConfigId(SecurPharmConfigId.ofRepoId(1))
				.applicationUUID("apt1031993")
				.authBaseUrl("https://auth.ngdalabor.de/")
				.pharmaAPIBaseUrl("https://securpharm.ngdalabor.de/apserver/api")
				.certificatePath("D:\\downloads\\apt1031993.p12")
				.supportUserId(UserId.METASFRESH)
				.keystorePassword("1lAAyn")
				.build();
		final SecurPharmClient client = SecurPharmClient.createAndAuthenticate(config);

		// final String GS = "";
		// final String code = "[)>\u001e06\u001d" + "9N" + "111234568408" + GS + "1T" + "47U5217" + GS + "D" + "220800" + GS + "S" + "18019731537612" + "\u001e" + "\u0004";
		// final SecurPharmProductDataResult productData = client.decodeDataMatrix(code);

		String code = "Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E";
		code = new String(Base64.getDecoder().decode(code.getBytes()));
		//System.out.println("code: " + new String(Base64.getDecoder().decode(code.getBytes())));
		final SecurPharmProductDataResult productData = client.decodeDataMatrix(code);
		System.out.println("response: " + productData);

	}

}
