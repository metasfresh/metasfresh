package de.metas.banking.model.validator;

/*
 * #%L
 * de.metas.banking.zkwebui
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
import org.adempiere.webui.apps.form.WCreateFromFactory;
import org.adempiere.webui.apps.form.WCreateFromStatementUI;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_BankStatement;

/**
 * Banking ZkwebUI module activator
 * 
 * This activator will be loaded only if running with {@link RunMode#WEBUI} run mode.
 * 
 * NOTE: keep the name in sync with {@link Banking} and keep the suffix.
 * 
 * @author tsa
 *
 */
public class Banking_ZkwebUI extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		WCreateFromFactory.registerClass(I_C_BankStatement.Table_ID, WCreateFromStatementUI.class);
	}

}
