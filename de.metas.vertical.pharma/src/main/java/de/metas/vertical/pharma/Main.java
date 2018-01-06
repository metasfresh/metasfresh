package de.metas.vertical.pharma;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.impl.PharmaImportPartnerInterceptor;
import org.adempiere.impexp.impl.PharmaImportProductInterceptor;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.impexp.product.PharmaProductImportProcess;
import de.metas.vertical.pharma.model.I_I_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_I_Product;

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
		super.onAfterInit();
		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Pharma_Product.class, PharmaProductImportProcess.class);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addImportInterceptor(I_I_BPartner.Table_Name, PharmaImportPartnerInterceptor.instance);
		engine.addImportInterceptor(I_I_Product.Table_Name, PharmaImportProductInterceptor.instance);
	}
}
