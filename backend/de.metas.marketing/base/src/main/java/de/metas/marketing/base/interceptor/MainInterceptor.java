package de.metas.marketing.base.interceptor;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

/*
 * #%L
 * de.metas.marketing
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

public class MainInterceptor extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(MKTG_Campaign_ContactPerson.INSTANCE);
		engine.addModelValidator(MKTG_Channel.INSTANCE);
		engine.addModelValidator(AD_User_MKTG_Channels.INSTANCE);

	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(MKTG_Campaign_ContactPerson.INSTANCE);
		// calloutsRegistry.registerAnnotatedCallout(MKTG_Channel.INSTANCE);
		// calloutsRegistry.registerAnnotatedCallout(AD_User_MKTG_Channels.INSTANCE);
	}
}
