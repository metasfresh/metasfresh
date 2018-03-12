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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import lombok.NonNull;

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

	private final List<HUToReport> husToReport;

	private HUADProcessKey selectedKey;

	private final DefaultKeyLayout reportKeyLayout;

	private boolean disposed = false;

	/**
	 * @param terminalContext
	 * @param referenceModel this model will be used to attach to it
	 */
	public HUReportModel(final ITerminalContext terminalContext,
			final HUToReport currentHU,
			final Set<HUToReport> selectedHUs)
	{
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

		// HUs to report on
		{
			final Set<HUToReport> husToCheck = !selectedHUs.isEmpty() ? selectedHUs : ImmutableSet.of(currentHU);
			final List<HUToReport> husToProcess = HUReportService.get().getHUsToProcess(husToCheck);

			// if there are still no HUs to process, try to take the current HU
			this.husToReport = !husToProcess.isEmpty() ? husToProcess : ImmutableList.of(currentHU);
		}

		// Available reports
		{
			final IKeyLayoutSelectionModel reportKeyLayoutSelectionModel = reportKeyLayout.getKeyLayoutSelectionModel();
			reportKeyLayoutSelectionModel.setAllowKeySelection(true);
			reportKeyLayoutSelectionModel.setAutoSelectIfOnlyOne(false);
			reportKeyLayout.setKeys(createHUADProcessKeys(terminalContext, husToReport));
		}

		terminalContext.addToDisposableComponents(this);
	}

	private static final List<ITerminalKey> createHUADProcessKeys(@NonNull final ITerminalContext terminalContext, final List<HUToReport> husToReport)
	{
		final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
		return huProcessDAO
				.getAllHUProcessDescriptors()
				.stream()
				.filter(createHUProcessDescriptorFilter(husToReport))
				.map(huProcessDescriptor -> new HUADProcessKey(terminalContext, huProcessDescriptor.getProcessId()))
				.sorted(HUADProcessKey.COMPARATOR_ByName)
				.collect(ImmutableList.toImmutableList());
	}

	private static final Predicate<HUProcessDescriptor> createHUProcessDescriptorFilter(final List<HUToReport> husToReport)
	{
		Check.assumeNotEmpty(husToReport, "husToReport is not empty");

		final ImmutableSet<String> requiredHUUnitTypes = husToReport.stream()
				.map(hu -> hu.getHUUnitType())
				.collect(ImmutableSet.toImmutableSet());

		return huProcessDescriptor -> huProcessDescriptor.appliesToAllHUUnitTypes(requiredHUUnitTypes);
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
		disposed = true;
		selectedKey = null;
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

	private final List<HUToReport> getHUsToReport()
	{
		return husToReport;
	}

	private final void setSelectedKey(final HUADProcessKey selectedKey)
	{
		this.selectedKey = selectedKey;
	}

	public final IKeyLayout getReportKeyLayout()
	{
		return reportKeyLayout;
	}

	public final void executeReport(final int printCopies)
	{
		final HUADProcessKey selectedKey = this.selectedKey;
		if (selectedKey == null)
		{
			throw new TerminalException("@" + HUReportModel.MSG_NoReportProcessSelected + "@");
		}

		final int adProcessId = selectedKey.getAD_Process_ID();

		HUReportExecutor.newInstance(getCtx())
				.windowNo(getTerminalContext().getWindowNo())
				.numberOfCopies(printCopies)
				.executeHUReportAfterCommit(adProcessId, getHUsToReport());
	}
}
