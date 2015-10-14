package org.adempiere.ad.security.model.validator;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;

public class SecurityMainInterceptor extends AbstractModuleInterceptor
{
	public static final transient SecurityMainInterceptor instance = new SecurityMainInterceptor();

	private SecurityMainInterceptor()
	{
		super();
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Role.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Role_Included.instance, client);

		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Org.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Window.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Process.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Form.instance, client);
		engine.addModelValidator(org.adempiere.ad.security.model.validator.AD_Workflow.instance, client);
	}
}
