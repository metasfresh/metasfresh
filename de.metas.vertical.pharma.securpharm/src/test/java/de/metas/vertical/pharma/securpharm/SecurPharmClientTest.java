/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm;

import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import org.adempiere.user.UserId;
import org.junit.Ignore;

public class SecurPharmClientTest
{
	@Ignore
	public void testClient() throws Exception
	{
		//get real data when testing
		SecurPharmConfig config = SecurPharmConfig.builder()
				.securPharmConfigId(SecurPharmConfigId.ofRepoId(1))
				.applicationUUID("uuid")
				.authBaseUrl("url")
				.pharmaAPIBaseUrl("url")
				.certificatePath("path")
				.supportUserId(UserId.METASFRESH)
				.keystorePassword("passw").build();
		SecurPharmClient client = SecurPharmClient.createAndAuthenticate(config);
		SecurPharmProductDataResult productData = client.decodeDataMatrix("dataMatrix");

	}

}
