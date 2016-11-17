package de.metas.aggregation.model.validator;

/*
 * #%L
 * de.metas.aggregation
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;
import org.compiere.util.CacheMgt;

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;

/**
 * Module activator
 *
 * @author tsa
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new de.metas.aggregation.model.validator.C_Aggregation(), client);
		engine.addModelValidator(new de.metas.aggregation.model.validator.C_AggregationItem(), client);
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.aggregation.callout.C_Aggregation());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.aggregation.callout.C_AggregationItem());
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		cachingService.addTableCacheConfigIfAbsent(I_C_Aggregation.class);
		cachingService.addTableCacheConfigIfAbsent(I_C_Aggregation_Attribute.class);

		final CacheMgt cacheMgt = CacheMgt.get();

		// important: if an aggregation changes, we not only want 50000 invoice candidate to be recomputed, but we also want them to be recomputed with the recent value
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_Aggregation.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_Aggregation_Attribute.Table_Name);
	}

}
