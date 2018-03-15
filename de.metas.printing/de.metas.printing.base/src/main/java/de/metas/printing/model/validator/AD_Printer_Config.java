package de.metas.printing.model.validator;

import java.util.Objects;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.util.Ini;

import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.client.IPrintingClientDelegate;
import de.metas.printing.model.I_AD_Printer_Config;

/**
 *
 */
@Validator(I_AD_Printer_Config.class)
public class AD_Printer_Config
{
	/**
	 * If our own hostkey's printer config uses a shared config () i.e. another/external printing client, then we stop our embedded printing client. Otherwise we start it.
	 *
	 * @param printerConfig
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_AD_Printer_Config.COLUMNNAME_AD_Printer_Config_Shared_ID)
	public void startOrStopEmbeddedClient(final I_AD_Printer_Config printerConfig)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerConfig);

		final String hostKey = Services.get(IPrintPackageBL.class).getHostKeyOrNull(ctx);

		if (Check.isEmpty(hostKey, true) || !Objects.equals(hostKey, printerConfig.getConfigHostKey()))
		{
			return; // 'printerConfig' does not belong the host which we run on
		}
		if (!Ini.isClient())
		{
			return; // task 08569: we only start the embedded client is we are inside the swing client (and *not* when running in the server)
		}
		final IPrintingClientDelegate printingClientDelegate = Services.get(IPrintingClientDelegate.class);
		if (printerConfig.getAD_Printer_Config_Shared_ID() > 0
				&& printingClientDelegate.isStarted())
		{
			printingClientDelegate.stop();
		}
		else if (!printingClientDelegate.isStarted())
		{
			printingClientDelegate.start();
		}
	}
}
