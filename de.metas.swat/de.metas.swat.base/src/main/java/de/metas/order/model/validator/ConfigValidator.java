/**
 *
 */
package de.metas.order.model.validator;

/*
 * #%L
 * de.metas.swat.base
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
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.Services;

import de.metas.document.documentNo.IDocumentNoBL;
import de.metas.order.document.documentNo.OrderPOReferenceListener;

/**
 * Main Invoice Candidates validator
 *
 * @author tsa
 *
 */
public class ConfigValidator extends AbstractModuleInterceptor
{
	@Override
	public void onAfterInit()
	{
		Services.get(IDocumentNoBL.class).registerDocumentNoListener(OrderPOReferenceListener.INSTANCE); // task 09776
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.order.callout.C_Order());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.order.callout.C_OrderLine());
	}

}
