package de.metas.vertical.pharma.securpharm.process;

import org.compiere.Adempiere;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;

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

public class M_Securpharm_Config_Test extends JavaProcess implements IProcessPrecondition
{
	private final SecurPharmConfigRespository configRepo = Adempiere.getBean(SecurPharmConfigRespository.class);
	private final SecurPharmClientFactory clientFactory = Adempiere.getBean(SecurPharmClientFactory.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SecurPharmConfigId configId = SecurPharmConfigId.ofRepoId(getRecord_ID());
		final SecurPharmConfig config = configRepo.getById(configId);

		final SecurPharmClient client = clientFactory.createClient(config);
		client.authenticate();

		return "@AuthenticationOK@";
	}
}
