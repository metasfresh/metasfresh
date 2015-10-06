package de.metas.banking.model.validator;

/*
 * #%L
 * de.metas.banking.swingui
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


import java.awt.event.ActionEvent;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.grid.VCreateFromFactory;
import org.compiere.grid.VCreateFromStatementUI;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_BankStatement;
import org.compiere.util.Env;

import de.metas.adempiere.addon.AppsActionAdapter;
import de.metas.adempiere.addon.AppsActionListener;
import de.metas.adempiere.addon.IAddonService;
import de.schaeffer.pay.misc.CCValidate;

/**
 * Banking SwingUI module activator.
 * 
 * This activator will be loaded only if running with {@link RunMode#SWING_CLIENT} run mode.
 * 
 * NOTE: keep the name in sync with {@link Banking} and keep the suffix.
 * 
 * @author tsa
 *
 */
public class Banking_SwingUI extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);
		
		registerAddOns(Services.get(IAddonService.class));

		VCreateFromFactory.registerClass(I_C_BankStatement.Table_ID, VCreateFromStatementUI.class);
	}
	
	private final void registerAddOns(final IAddonService addonService)
	{
		final AppsAction appsAction = new AppsAction("Payment", null, false);

		final AppsActionListener appsActionListener = new AppsActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e, final AppsAction appsAction)
			{
				final APanel aPanel = addonService.getParentForAction(appsAction);
				new CCValidate(Env.getCtx(), aPanel).setVisible(true);
			}
		};

		final AppsActionAdapter adapter = new AppsActionAdapter();
		adapter.setAppsActionListener(appsActionListener);
		adapter.setAppsAction(appsAction);

		addonService.registerToolBarAction(appsAction, adapter);
	}

}
