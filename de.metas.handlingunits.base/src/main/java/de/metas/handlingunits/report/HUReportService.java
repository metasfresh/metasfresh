package de.metas.handlingunits.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_AD_Process;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.process.api.IMHUProcessBL;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This service facade offers useful methods around to handling unit related jasper report processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUReportService
{
	private static final transient Logger logger = LogManager.getLogger(HUReportService.class);

	public static final String SYSCONFIG_RECEIPT_LABEL_PROCESS_ID = "de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID";

	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled";

	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED_C_BPARTNER_ID = SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED + ".C_BPartner_ID_";

	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Copies";

	private static final HUReportService INSTANCE = new HUReportService();

	public static HUReportService get()
	{
		return INSTANCE;
	}

	/**
	 * Return the AD_Process_ID that was configured via {@code SysConfig} {@value #SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_PROCESS_ID}, if any.
	 *
	 * @param ctx use to get {@code AD_Client_ID} and {@code AD_Org_ID} for the sysconfig call.
	 * @return AD_Process_ID or <code>-1</code>
	 */
	public int retrievePrintReceiptLabelProcessId(final Properties ctx)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int reportProcessId = sysConfigBL.getIntValue(SYSCONFIG_RECEIPT_LABEL_PROCESS_ID, -1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return reportProcessId > 0 ? reportProcessId : -1;
	}

	/**
	 * For the given {@code hu} and {@code process} this method traverses the HU hierarchy (using the hu as root) and collects every HU that is a fit for the process according to {@link IMHUProcessBL#processFitsType(I_AD_Process, String)}.
	 *
	 * @param hu
	 * @param process
	 * @return never {@code null}
	 */
	public List<I_M_HU> getHUsToProcess(final I_M_HU hu, final int adProcessId)
	{
		final List<I_M_HU> husToProcess = new ArrayList<>();

		final IMHUProcessBL huProcessBL = Services.get(IMHUProcessBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		logger.debug("Looking for processable HUs belong hu={}", hu);
		new HUIterator()
				.setEnableStorageIteration(false) // gh metasfresh-webui#222: we only care for HUs. Also note that to iterate storages, we would have to provide a date.
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result beforeHU(final IMutable<I_M_HU> hu)
					{
						final String huType = handlingUnitsBL.getHU_UnitType(hu.getValue());
						if (huProcessBL.processFitsType(adProcessId, huType))
						{
							logger.debug("adProcessId={}: add hu with type={} to husToProcess; hu={}", adProcessId, huType, hu);
							husToProcess.add(hu.getValue());
						}
						else
						{
							logger.debug("adProcessId={}: DO NOT add hu with type={} to husToProcess; hu={}", adProcessId, huType, hu);
						}
						return getDefaultResult();
					}
				})
				.iterate(hu);

		return husToProcess;
	}

	public int getReceiptLabelAutoPrintCopyCount(final Properties ctx)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int copies = sysConfigBL.getIntValue(SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES, 1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return copies;
	}

	/**
	 * Checks the sysconfig and returns true or if receipt label auto printing is enabled in general or for the given HU's C_BPartner_ID.
	 *
	 * @param ctx
	 * @param hu
	 * @param vendorBPartnerId the original vendor. By now, might not be the same as M_HU.C_BPartner_ID anymore
	 * @return
	 */
	public boolean isReceiptLabelAutoPrintEnabled(final Properties ctx, final I_M_HU hu, final int vendorBPartnerId)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String vendorSysconfigName = SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED_C_BPARTNER_ID + vendorBPartnerId;
		final String valueForBPartner = sysConfigBL.getValue(vendorSysconfigName, "NOT_SET", Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		logger.info("SysConfig {}={};", vendorSysconfigName, valueForBPartner);

		if (!"NOT_SET".equals(valueForBPartner))
		{
			return DisplayType.toBoolean(valueForBPartner, false);
		}

		final String genericValue = sysConfigBL.getValue(SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED, "N", Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		logger.info("SysConfig {}={};", SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED, genericValue);

		return DisplayType.toBoolean(genericValue, false);
}

}
