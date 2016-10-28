package de.metas.dlm.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.connection.IConnectionCustomizerService;
import de.metas.dlm.connection.DLMPermanentSysConfigCustomizer;
import de.metas.dlm.coordinator.ICoordinatorService;
import de.metas.dlm.coordinator.impl.LastUpdatedInspector;
import de.metas.dlm.exception.DLMReferenceExceptionWrapper;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new DLM_Partition_Config(), client);
		engine.addModelValidator(new DLM_Partition_Config_Line(), client);
	}

	@Override
	protected void onAfterInit()
	{
		DBException.registerExceptionWrapper(DLMReferenceExceptionWrapper.INSTANCE);

		Services.get(IConnectionCustomizerService.class).registerPermanentCustomizer(DLMPermanentSysConfigCustomizer.PERMANENT_INSTANCE);

		Services.get(ICoordinatorService.class).registerInspector(LastUpdatedInspector.INSTANCE);
	}
}
