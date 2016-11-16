/**
 *
 */
package de.metas.handlingunits.client.terminal.report.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.model.I_AD_Process;
import org.compiere.report.IJasperService;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Language;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.process.api.IMHUProcessBL;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;

/**
 * Model responsible for generating HU labels and reports
 *
 * @author al
 */
public class HUReportModel implements IDisposable
{
	private static final String MSG_NoReportProcessSelected = "NoReportProcessSelected";

	private final WeakPropertyChangeSupport pcs;
	private final ITerminalContext terminalContext;

	private I_M_HU currentHU;
	private Set<I_M_HU> selectedHUs;
	private List<I_M_HU> husToProcess;

	private HUADProcessKey selectedKey;

	private final DefaultKeyLayout reportKeyLayout;

	private boolean disposed = false;

	/**
	 * @param terminalContext
	 * @param referenceModel this model will be used to attach to it
	 */
	public HUReportModel(final ITerminalContext terminalContext,
			final I_M_HU currentHU,
			final Set<I_M_HU> selectedHUs)
	{
		this.currentHU = currentHU;

		this.selectedHUs = selectedHUs;

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		reportKeyLayout = new DefaultKeyLayout(terminalContext);
		reportKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				onReportKeyPressed(key);
			}
		});

		final IKeyLayoutSelectionModel reportKeyLayoutSelectionModel = reportKeyLayout.getKeyLayoutSelectionModel();
		reportKeyLayoutSelectionModel.setAllowKeySelection(true);
		reportKeyLayoutSelectionModel.setAutoSelectIfOnlyOne(false);

		loadAvailableReportKeys();

		terminalContext.addToDisposableComponents(this);
	}

	private void loadAvailableReportKeys()
	{
		// list of HUs that are Transport Units (could be the selected ones and the included ones from the LU)
		final List<I_M_HU> tuHUs = new ArrayList<>();

		// list of HUs that are Loading Units
		final List<I_M_HU> luHUs = new ArrayList<>();

		final List<I_AD_Process> availableReportProcesses = Services.get(IADProcessDAO.class).retrieveReportProcessesForTable(getCtx(), I_M_HU.Table_Name);

		final List<ITerminalKey> availableProcessKeys = new ArrayList<ITerminalKey>(availableReportProcesses.size());

		// In case of no selected HUs display the available processes for the current HU

		final Set<I_M_HU> selectedHUs = getSelectedHUs();
		if (selectedHUs.isEmpty())
		{
			selectedHUs.add(getCurrentHU());
		}

		for (final I_M_HU hu : selectedHUs)
		{
			final String selectedHUUnitType = hu.getM_HU_PI_Version().getHU_UnitType();

			// BL NOT IMPLEMENTED YET FOR VIRTUAL PI REPORTS, because we don't have any

			if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(selectedHUUnitType))
			{
				continue;
			}

			if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(selectedHUUnitType))
			{
				luHUs.add(hu);
			}
			else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(selectedHUUnitType))
			{
				tuHUs.add(hu);
			}
			else
			{
				throw new AdempiereException("Invalid unit type: " + selectedHUUnitType);
			}
		}

		final String unitTypeToSelectProcesses;

		if (!tuHUs.isEmpty())
		{
			unitTypeToSelectProcesses = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;
		}
		else if (!luHUs.isEmpty())
		{
			unitTypeToSelectProcesses = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
		}
		else
		{
			unitTypeToSelectProcesses = X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI;
		}

		List<I_M_HU> currentHUsToProcess = new ArrayList<>();
		if (!luHUs.isEmpty() || !tuHUs.isEmpty())
		{
			currentHUsToProcess = loadHUsToProcess(unitTypeToSelectProcesses, luHUs, tuHUs);
		}

		// if there are still no HUs to process, try to take the current HU

		if (currentHUsToProcess.isEmpty())
		{
			currentHUsToProcess.add(getCurrentHU());
		}

		setHUsToProcess(currentHUsToProcess);

		final IMHUProcessBL mHUPorcessBL = Services.get(IMHUProcessBL.class);

		for (final I_AD_Process process : availableReportProcesses)
		{
			final boolean processFitsType = mHUPorcessBL.processFitsType(process, unitTypeToSelectProcesses);

			// If the process (or report) was defined for another handling unit type, do not display it
			if (!processFitsType)
			{
				continue;
			}
			final HUADProcessKey processKey = new HUADProcessKey(getTerminalContext(), process);
			availableProcessKeys.add(processKey);
		}
		reportKeyLayout.setKeys(availableProcessKeys);
	}

	/**
	 * In case at least one TU was selected, we will deliver the processes for TUs.
	 *
	 * This will happen even though we have, for instance, just 1 TU and some LUs selected.
	 *
	 * The HUs to have the processes applied will be the 1 TU and the included TUs of the selected LUs
	 *
	 * @param unitType
	 * @param luHUs
	 * @param tuHUs
	 * @return
	 */
	private List<I_M_HU> loadHUsToProcess(final String unitType, final List<I_M_HU> luHUs, final List<I_M_HU> tuHUs)
	{
		// In case the unit type is Virtual PI we don't have to return anything, since we don't have processes for virtual PIs
		if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(unitType))
		{
			return Collections.emptyList();
		}

		// In case we the unit type is LU we just have to process the LUs
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(unitType))
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
		final List<I_M_HU> currentHusToProcess = new ArrayList<>();

		// first, add all the selected TUs
		currentHusToProcess.addAll(tuHUs);

		for (final I_M_HU hu : luHUs)
		{
			final List<I_M_HU> includedHus = Services.get(IHandlingUnitsDAO.class).retrieveIncludedHUs(hu);
			currentHusToProcess.addAll(includedHus);
		}
		return currentHusToProcess;
	}

	private final void onReportKeyPressed(final ITerminalKey currentKey)
	{
		if (currentKey == null)
		{
			return;
		}
		final HUADProcessKey selectedKey = (HUADProcessKey)currentKey;
		setSelectedKey(selectedKey);
	}

	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	private final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	protected final void finalize() throws Throwable
	{
		dispose();
	};

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		currentHU = null;
		selectedHUs = null;
		selectedKey = null;
		husToProcess = null;

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public final I_M_HU getCurrentHU()
	{
		return currentHU;
	}

	public final void setCurrentHU(final I_M_HU currentHU)
	{
		this.currentHU = currentHU;
	}

	public final Set<I_M_HU> getSelectedHUs()
	{
		return selectedHUs;
	}

	public final void setSelectedHUs(final Set<I_M_HU> selectedHUs)
	{
		this.selectedHUs = selectedHUs;
	}

	public final List<I_M_HU> getHUsToProcess()
	{
		return husToProcess;
	}

	public final void setHUsToProcess(final List<I_M_HU> husToProcess)
	{
		this.husToProcess = husToProcess;
	}

	public final HUADProcessKey getSelectedKey()
	{
		return selectedKey;
	}

	public final void setSelectedKey(final HUADProcessKey selectedKey)
	{
		this.selectedKey = selectedKey;
	}

	public final IKeyLayout getReportKeyLayout()
	{
		return reportKeyLayout;
	}

	public final void executeReport(final BigDecimal printCopies)
	{
		if (selectedKey == null)
		{
			throw new TerminalException("@" + HUReportModel.MSG_NoReportProcessSelected + "@");
		}
		final I_AD_Process process = selectedKey.getProcess();
		executeReport0(process, printCopies);
	}

	/**
	 * AD_SysConfig for "BarcodeServlet".
	 */
	private static final String SYSCONFIG_BarcodeServlet = "de.metas.adempiere.report.barcode.BarcodeServlet";
	private static final String PARA_BarcodeURL = "barcodeURL";

	private final void executeReport0(final I_AD_Process process, final BigDecimal printCopies)
	{
		//
		// Collect HU's C_BPartner_IDs and M_HU_IDs
		final Set<Integer> huBPartnerIds = new HashSet<>();
		final List<Integer> huIds = new ArrayList<>();
		for (final I_M_HU hu : getHUsToProcess())
		{
			final int huId = hu.getM_HU_ID();
			huIds.add(huId);

			// Collect HU's BPartner ID ... we will need that to advice the report to use HU's BPartner Language Locale
			final int bpartnerId = hu.getC_BPartner_ID();
			if (bpartnerId > 0)
			{
				huBPartnerIds.add(bpartnerId);
			}
		}

		//
		// Use BPartner's Language as reporting language if our HUs have an unique BPartner
		final Language reportLanguage;
		if (huBPartnerIds.size() == 1)
		{
			final int bpartnerId = huBPartnerIds.iterator().next();
			reportLanguage = Services.get(IBPartnerBL.class).getLanguage(getCtx(), bpartnerId);
		}
		else
		{
			reportLanguage = null; // N/A
		}

		final Properties ctx = getCtx();
		final String barcodeServlet = Services.get(ISysConfigBL.class).getValue(HUReportModel.SYSCONFIG_BarcodeServlet,
				null, // defaultValue,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process(process)
				.setWindowNo(getTerminalContext().getWindowNo())
				.setTableName(I_M_HU.Table_Name)
				.setReportLanguage(reportLanguage)
				.addParameter(HUReportModel.PARA_BarcodeURL, barcodeServlet)
				.addParameter(IJasperService.PARAM_PrintCopies, printCopies)
				.build();


		//
		// Execute report in a new transaction
		ProcessExecutor.builder()
				.setProcessInfo(pi)
				.callBefore(processInfo -> DB.createT_Selection(processInfo.getAD_PInstance_ID(), huIds, ITrx.TRXNAME_ThreadInherited))
				.executeSync();
	}
}
