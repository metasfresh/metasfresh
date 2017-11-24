package de.metas.vertical.pharma;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.impexp.ImportService;
import org.adempiere.impexp.impl.PharmaImportInterceptor;
import org.compiere.Adempiere;

import de.metas.vertical.pharma.model.I_C_BPartner;

/*
 * #%L
 * de.metas.procurement.base
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

/**
 * Module activator
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		setupPharmaImportListeners();
	}

	private void setupPharmaImportListeners()
	{
		final ImportService importService = Adempiere.getBean(ImportService.class);
		importService.registerListenerForTableName(PharmaImportInterceptor.instance, I_C_BPartner.Table_Name);
	}
}
