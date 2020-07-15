package de.metas.printing.callout;

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


import de.metas.logging.LogManager;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;

import de.metas.printing.model.I_C_Doc_Outbound_Config;
import org.slf4j.Logger;

@Callout(I_C_Doc_Outbound_Config.class)
public class C_Doc_Outbound_Config
{
	private final static transient Logger logger = LogManager.getLogger(C_Doc_Outbound_Config.class);

	public static final C_Doc_Outbound_Config instance = new C_Doc_Outbound_Config();

	private C_Doc_Outbound_Config()
	{
	};

	/**
	 * If <code>IsCreatePrintJob</code> is set to <code>true</code>, then also set <code>IsDirectEnqueue</code>, because without a printing queue, there is no print job.
	 * <p>
	 * Note that this is mainly for the user. The business logics (=>AD_Archive) already behave like this for some time.
	 * task http://dewiki908/mediawiki/index.php/09417_Massendruck_-_Sofort-Druckjob_via_Ausgehende-Belege_konfig_einstellbar_%28101934367465%29
	 */
	@CalloutMethod(columnNames = { I_C_Doc_Outbound_Config.COLUMNNAME_IsDirectProcessQueueItem })
	public void onIsCreatePrintJobChange(final I_C_Doc_Outbound_Config config)
	{
		if (config.isDirectProcessQueueItem() && !config.isDirectEnqueue())
		{
			logger.debug("C_Doc_Outbound_Config.isDirectProcessQueueItem is 'Y'; -> set isDirectEnqueue also to 'Y'");
			config.setIsDirectEnqueue(true);
		}
	}
	
	/**
	 * If <code>IsDirectEnqueue</code> is set to <code>false</code>, then also unset <code>IsCreatePrintJob</code>, because without a printing queue, there is no print job.
	 * <p>
	 * Note that this is mainly for the user. The business logics (=>AD_Archive) already behave like this for some time.
	 * task http://dewiki908/mediawiki/index.php/09417_Massendruck_-_Sofort-Druckjob_via_Ausgehende-Belege_konfig_einstellbar_%28101934367465%29
	 */
	@CalloutMethod(columnNames = { I_C_Doc_Outbound_Config.COLUMNNAME_IsDirectEnqueue })
	public void onIsDirectEnqueueChange(final I_C_Doc_Outbound_Config config)
	{
		if (!config.isDirectEnqueue() && config.isDirectProcessQueueItem())
		{
			logger.debug("C_Doc_Outbound_Config.isDirectEnqueue is 'N'; -> set isDirectProcessQueueItem also to 'N'");
			config.setIsDirectProcessQueueItem(false);
		}
	}
}
