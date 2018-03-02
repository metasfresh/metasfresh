package de.metas.handlingunits.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.logging.LogManager;
import lombok.NonNull;

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
	public static HUReportService get()
	{
		return INSTANCE;
	}

	private static final transient Logger logger = LogManager.getLogger(HUReportService.class);

	public static final String SYSCONFIG_RECEIPT_LABEL_PROCESS_ID = "de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID";
	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled";
	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED_C_BPARTNER_ID = SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED + ".C_BPartner_ID_";
	public static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Copies";

	private static final HUReportService INSTANCE = new HUReportService();

	private HUReportService()
	{
	}

	/**
	 * Return the AD_Process_ID that was configured via {@code SysConfig} {@value #SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_PROCESS_ID}, if any.
	 *
	 * @return AD_Process_ID or <code>-1</code>
	 */
	public int retrievePrintReceiptLabelProcessId()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final Properties ctx = Env.getCtx();
		final int reportProcessId = sysConfigBL.getIntValue(SYSCONFIG_RECEIPT_LABEL_PROCESS_ID, -1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return reportProcessId > 0 ? reportProcessId : -1;
	}

	public List<I_M_HU> getHUsToProcess(final I_M_HU hu, final int adProcessId)
	{
		final List<I_M_HU> husToProcess = new ArrayList<>();

		final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
		final HUProcessDescriptor huProcessDescriptor = huProcessDAO.getByProcessIdOrNull(adProcessId);
		if (huProcessDescriptor == null)
		{
			return husToProcess;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		new HUIterator()
				.setEnableStorageIteration(false) // gh metasfresh-webui#222: we only care for HUs. Also note that to iterate storages, we would have to provide a date.
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result beforeHU(final IMutable<I_M_HU> hu)
					{
						final String huType = handlingUnitsBL.getHU_UnitType(hu.getValue());
						if (huProcessDescriptor.appliesToHUUnitType(huType))
						{
							husToProcess.add(hu.getValue());
						}
						return getDefaultResult();
					}
				})
				.iterate(hu);

		return husToProcess;
	}

	public int getReceiptLabelAutoPrintCopyCount()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final Properties ctx = Env.getCtx();
		final int copies = sysConfigBL.getIntValue(SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES, 1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return copies;
	}

	/**
	 * Checks the sysconfig and returns true or if receipt label auto printing is enabled in general or for the given HU's C_BPartner_ID.
	 *
	 * @param vendorBPartnerId the original vendor. By now, might not be the same as M_HU.C_BPartner_ID anymore
	 * @return
	 */
	public boolean isReceiptLabelAutoPrintEnabled(final int vendorBPartnerId)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final Properties ctx = Env.getCtx();

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

	public List<I_M_HU> getHUsToProcess(@NonNull final Set<I_M_HU> husToCheck)
	{
		if (husToCheck.isEmpty())
		{
			return ImmutableList.of();
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final List<I_M_HU> tuHUs = new ArrayList<>();
		final List<I_M_HU> luHUs = new ArrayList<>();
		for (final I_M_HU hu : husToCheck)
		{
			final String huUnitType = handlingUnitsBL.getEffectivePIVersion(hu).getHU_UnitType();

			// BL NOT IMPLEMENTED YET FOR VIRTUAL PI REPORTS, because we don't have any
			if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
			{
				continue;
			}

			if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
			{
				luHUs.add(hu);
			}
			else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
			{
				tuHUs.add(hu);
			}
			else
			{
				throw new AdempiereException("Invalid unit type: " + huUnitType);
			}
		}

		final String huUnitTypeToReport;
		if (!tuHUs.isEmpty())
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;
		}
		else if (!luHUs.isEmpty())
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
		}
		else
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI;
		}

		if (!luHUs.isEmpty() || !tuHUs.isEmpty())
		{
			return extractHUsToProcess(huUnitTypeToReport, luHUs, tuHUs);
		}
		else
		{
			return ImmutableList.of();
		}
	}

	/**
	 * In case at least one TU was selected, we will deliver the processes for TUs.
	 *
	 * This will happen even though we have, for instance, just 1 TU and some LUs selected.
	 *
	 * The HUs to have the processes applied will be the 1 TU and the included TUs of the selected LUs
	 *
	 * @param huUnitType
	 * @param luHUs
	 * @param tuHUs
	 */
	private static List<I_M_HU> extractHUsToProcess(final String huUnitType, final List<I_M_HU> luHUs, final List<I_M_HU> tuHUs)
	{
		// In case the unit type is Virtual PI we don't have to return anything, since we don't have processes for virtual PIs
		if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			return ImmutableList.of();
		}

		// In case we the unit type is LU we just have to process the LUs
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			return luHUs;
		}

		// In case the unit type is TU we have 2 possibilities:
		// In case the are no selected LUs, simply return the TUs
		if (luHUs.isEmpty())
		{
			return tuHUs;
		}

		// if this point is reached, it means we have both TUs and LUs selected
		final ImmutableList.Builder<I_M_HU> husToProcess = ImmutableList.builder();

		// first, add all the selected TUs
		husToProcess.addAll(tuHUs);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		for (final I_M_HU hu : luHUs)
		{
			final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
			husToProcess.addAll(includedHUs);
		}
		return husToProcess.build();
	}
}
