package de.metas.vertical.pharma.vendor.gateway.msv3.process;

import org.compiere.Adempiere;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.vendor.gateway.msv3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfigId;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfigRepository;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClient;
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

public class MSV3_Vendor_Config_TestConnection
		extends JavaProcess
		implements IProcessPrecondition
{
	private final MSV3ClientConfigRepository configsRepo = Adempiere.getBean(MSV3ClientConfigRepository.class);
	private final MSV3ConnectionFactory connectionFactory = Adempiere.getBean(MSV3ConnectionFactory.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		return ProcessPreconditionsResolution
				.acceptIf(context.isSingleSelection());

	}

	@Override
	protected String doIt() throws Exception
	{
		final MSV3ClientConfigId configId = MSV3ClientConfigId.ofRepoId(getRecord_ID());
		final MSV3ClientConfig config = configsRepo.getById(configId);

		final MSV3TestConnectionClient msv3Client = new MSV3TestConnectionClient(connectionFactory, config);
		msv3Client.testConnection(); // if the connection is not OK, an exception will be thrown

		return MSG_OK;
	}
}
