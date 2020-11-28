package de.metas.security;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.security.process.WEBUI_UserGroupRecordAccess_Grant;
import de.metas.security.process.WEBUI_UserGroupRecordAccess_Revoke;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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

@Component
public class WebuiSecurityMainInterceptor extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(WebuiSecurityMainInterceptor.class);

	@Override
	protected void onAfterInit()
	{
		registerProcessNoFail(WEBUI_UserGroupRecordAccess_Grant.class);
		registerProcessNoFail(WEBUI_UserGroupRecordAccess_Revoke.class);
	}

	private void registerProcessNoFail(final Class<? extends JavaProcess> processClass)
	{
		try
		{
			final IADProcessDAO adProcessesRepo = Services.get(IADProcessDAO.class);
			adProcessesRepo.registerTableProcess(RelatedProcessDescriptor.builder()
					.processId(adProcessesRepo.retrieveProcessIdByClass(processClass))
					.anyTable()
					.displayPlace(DisplayPlace.ViewActionsMenu)
					.build());
		}
		catch (final Exception ex)
		{
			logger.warn("Cannot register process {}. Skip", processClass, ex);
		}
	}
}
