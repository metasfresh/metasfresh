package de.metas.vertical.pharma.vendor.gateway.msv3;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_AD_System;

import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
 * %%
 * Copyright (C) 2018 metas GmbH
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

public final class MSV3TestingTools
{
	private MSV3TestingTools()
	{
	};

	public static void setDBVersion(@NonNull final String dbVersion)
	{
		final List<I_AD_System> adSystemRecords = POJOLookupMap.get().getRecords(I_AD_System.class);
		assertThat(adSystemRecords.size()).isLessThanOrEqualTo(1);

		final I_AD_System adSystem;
		if (adSystemRecords.isEmpty())
		{
			// AD_System is needed because we send the metasfresh-version to the MSV3 server
			adSystem = newInstance(I_AD_System.class);
		}
		else
		{
			adSystem = adSystemRecords.get(0);
		}
		adSystem.setDBVersion(dbVersion);
		save(adSystem);
	}

	public static MSV3ClientConfig createMSV3ClientConfig()
	{
		final I_MSV3_Vendor_Config configRecord = newInstance(I_MSV3_Vendor_Config.class);
		configRecord.setMSV3_BaseUrl("http://localhost:8089/msv3/v2.0");
		configRecord.setUserID("PLA\\apotheke1");
		configRecord.setPassword("passwort");
		configRecord.setC_BPartner_ID(999);
		save(configRecord);

		return MSV3ClientConfig.ofdataRecord(configRecord);
	}
}
