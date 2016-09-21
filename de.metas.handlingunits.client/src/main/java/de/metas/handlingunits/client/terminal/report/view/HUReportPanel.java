/**
 *
 */
package de.metas.handlingunits.client.terminal.report.view;

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


import java.awt.Color;
import java.math.BigDecimal;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.TerminalDialogListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.PositiveNumericFieldConstraint;
import de.metas.handlingunits.client.terminal.report.model.HUReportModel;

/**
 * Panel responsible for generating HU labels and reports
 *
 * @author al
 */
public class HUReportPanel
		extends TerminalDialogListenerAdapter
		implements IComponent
{
	private final HUReportModel model;
	private final IContainer panel;

	private final ITerminalKeyPanel plPanel;
	private final ITerminalFactory factory;

	protected static final String PANEL_PrintCopies = "PrintCopies";
	private ITerminalNumericField printCopiesField = null;

	private boolean disposed = false;

	public HUReportPanel(final HUReportModel model)
	{
		super();

		this.model = model;

		final ITerminalContext terminalContext = model.getTerminalContext();
		factory = terminalContext.getTerminalFactory();

		//
		// Init components
		{
			panel = factory.createContainer();

			final IKeyLayout reportKeyLayout = model.getReportKeyLayout();
			reportKeyLayout.setDefaultColor(Color.LIGHT_GRAY);
			reportKeyLayout.setColumns(1);
			reportKeyLayout.setRows(0);
			plPanel = factory.createTerminalKeyPanel(reportKeyLayout);

			printCopiesField = createQtyField(PANEL_PrintCopies);
			printCopiesField.setValue(BigDecimal.ONE);
		}

		initLayout();

		terminalContext.addToDisposableComponents(this);
	}

	private void initLayout()
	{
		panel.add(plPanel, "dock north, grow, wrap");
		panel.addAfter(printCopiesField, plPanel, "dock north, grow, wrap");
	}

	@Override
	public Object getComponent()
	{
		return panel.getComponent();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return model.getTerminalContext();
	}

	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		boolean cancelDispose = true;
		try
		{
			final BigDecimal printCopies = printCopiesField.getValue();
			model.executeReport(printCopies);
			cancelDispose = false;
		}
		catch (final Exception e)
		{
			factory.showWarning(this, ITerminalFactory.TITLE_ERROR, e);

			// cancel dialog disposal on exception
			dialog.cancelDispose();
			cancelDispose = true;
		}

		if (!cancelDispose)
		{
			dispose();
		}
	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		dispose();
		return true; // allow canceling
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	protected final void finalize() throws Throwable
	{
		dispose();
	}

	@Override
	public void dispose()
	{
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	private final ITerminalNumericField createQtyField(final String label)
	{
		final ITerminalFactory terminalFactory = getTerminalContext().getTerminalFactory();
		final ITerminalNumericField qtyField = terminalFactory.createTerminalNumericField(
				label,
				DisplayType.Quantity,
				true, // withButtons=true
				false); // withLabel=false - we'll have a custom Label setup
		qtyField.setEditable(true);
		qtyField.addConstraint(PositiveNumericFieldConstraint.instance);
		return qtyField;
	}
}
