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

package de.metas.vertical.pharma.securpharm.repository;

import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Config;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfigId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.user.UserId;
import org.springframework.stereotype.Repository;

@Repository
public class SecurPharmConfigRespository
{

	public SecurPharmConfig getConfig()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_M_Securpharm_Config configRecord = queryBL
				.createQueryBuilder(I_M_Securpharm_Config.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_M_Securpharm_Config.COLUMNNAME_Created)
				.create().first();
		if (configRecord == null)
		{
			return null;
		}
		else
		{
			return ofRecord(configRecord);
		}
	}

	private SecurPharmConfig ofRecord(@NonNull final I_M_Securpharm_Config config)
	{
		return SecurPharmConfig.builder()
				.certificatePath(config.getCertificatePath())
				.applicationUUID(config.getApplicationUUID())
				.authBaseUrl(config.getAuthPharmaRestApiBaseURL())
				.pharmaAPIBaseUrl(config.getPharmaRestApiBaseURL())
				.keystorePassword(config.getTanPassword())
				.securPharmConfigId(SecurPharmConfigId.ofRepoId(config.getM_Securpharm_Config_ID()))
				.supportUserId(UserId.ofRepoId(config.getSupport_User_ID()))
				.build();
	}
}
