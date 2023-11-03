package de.metas.vertical.pharma;

import com.google.common.collect.ImmutableSet;
import de.metas.impexp.bpartner.IFABPartnerImportProcess;
import de.metas.impexp.bpartner.PharmaImportPartnerInterceptor;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.product.IFAProductImportProcess;
import de.metas.impexp.product.PharmaImportProductInterceptor;
import de.metas.pricing.service.IPricingBL;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_I_Product;
import de.metas.vertical.pharma.pricing.PharmaPriceLimitRule;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

import java.util.Set;

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
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Pharma_Product.class, IFAProductImportProcess.class);
		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Pharma_BPartner.class, IFABPartnerImportProcess.class);
		Services.get(IPricingBL.class).registerPriceLimitRule(new PharmaPriceLimitRule());
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addImportInterceptor(I_I_BPartner.Table_Name, PharmaImportPartnerInterceptor.instance);
		engine.addImportInterceptor(I_I_Product.Table_Name, PharmaImportProductInterceptor.instance);
	}

	@Override
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(I_I_Pharma_BPartner.Table_Name, I_I_Pharma_Product.Table_Name);
	}
}
