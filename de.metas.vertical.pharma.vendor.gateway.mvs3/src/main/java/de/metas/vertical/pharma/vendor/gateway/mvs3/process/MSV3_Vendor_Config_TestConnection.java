package de.metas.vertical.pharma.vendor.gateway.mvs3.process;

import de.metas.process.JavaProcess;
import de.metas.vendor.gateway.msv3.model.I_MSV3_Vendor_Config;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.testconnection.MSV3TestConnectionClient;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

public class MSV3_Vendor_Config_TestConnection extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final I_MSV3_Vendor_Config configDataRecord = getProcessInfo().getRecord(I_MSV3_Vendor_Config.class);

		final MSV3ConnectionFactory connectionFactory = new MSV3ConnectionFactory();
		final MSV3ClientConfig config = MSV3ClientConfig.ofdataRecord(configDataRecord);

		final MSV3TestConnectionClient msv3Client = new MSV3TestConnectionClient(connectionFactory, config);
		msv3Client.testConnection(); // if the connection is not OK, an exception will be thrown

		return MSG_OK;
	}

}
