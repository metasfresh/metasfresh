package de.metas.printing.model.validator;

import de.metas.logging.TableRecordMDC;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.client.IPrintingClientDelegate;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.util.Ini;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Properties;

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

@Interceptor(I_AD_Printer_Config.class)
@Component
public class AD_Printer_Config
{

	private final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);

	/**
	 * If our own hostkey's printer config uses a shared config () i.e. another/external printing client, then we stop our embedded printing client. Otherwise we start it.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_AD_Printer_Config.COLUMNNAME_AD_Printer_Config_Shared_ID)
	public void startOrStopEmbeddedClient(@NonNull final I_AD_Printer_Config printerConfig)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printerConfig);

		final String hostKey = printClientsBL.getHostKeyOrNull(ctx);

		if (Check.isBlank(hostKey) || !Objects.equals(hostKey, printerConfig.getConfigHostKey()))
		{
			return; // 'printerConfig' does not belong the host which we run on
		}
		if (!Ini.isSwingClient())
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

	/**
	 * Needed because we want to order by ConfigHostKey and "unspecified" shall always be last.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_Printer_Config.COLUMNNAME_ConfigHostKey)
	public void trimBlankHostKeyToNull(@NonNull final I_AD_Printer_Config printerConfig)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(printerConfig))
		{
			final String normalizedString = StringUtils.trimBlankToNull(printerConfig.getConfigHostKey());
			printerConfig.setConfigHostKey(normalizedString);
		}
	}
}
