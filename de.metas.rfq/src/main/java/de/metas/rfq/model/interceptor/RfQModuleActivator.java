package de.metas.rfq.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;

/*
 * #%L
 * de.metas.business
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
 * RfQ module activator
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class RfQModuleActivator extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(IModelValidationEngine engine, I_AD_Client client)
	{
		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQ_Topic(), client);

		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQ(), client);
		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQLine(), client);

		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQResponse(), client);
		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQResponseLine(), client);
		engine.addModelValidator(new de.metas.rfq.model.interceptor.C_RfQResponseLineQty(), client);
	}
}
