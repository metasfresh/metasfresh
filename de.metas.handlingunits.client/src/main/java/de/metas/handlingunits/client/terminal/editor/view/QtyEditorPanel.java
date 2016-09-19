package de.metas.handlingunits.client.terminal.editor.view;

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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.TerminalDialogListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.adempiere.form.terminal.field.constraint.PositiveNumericFieldConstraint;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;

/**
 * Small popup used to ask your for a Quantity.
 *
 * @author ad
 *
 */
public class QtyEditorPanel
		extends TerminalDialogListenerAdapter
		implements IComponent
{
	private final ITerminalContext terminalContext;
	private final IContainer panel;
	private final ITerminalNumericField quantityField;

	private boolean disposed = false;

	private static final String ERR_QTY_NOT_NULL = "QtyNotNull";
	private static final String ERR_NEGATIVE_QTY_NOT_ALLOWED = "NegativeQtyNotAllowed";

	public QtyEditorPanel(final ITerminalContext terminalContext)
	{
		super();

		final Properties layoutConstants;
		this.terminalContext = terminalContext;
		layoutConstants = Services.get(IHUPOSLayoutConstants.class).getConstants(terminalContext);

		final ITerminalFactory factory = terminalContext.getTerminalFactory();
		panel = factory.createContainer(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUQty_PanelConstraints));

		// Init terminal field.
		quantityField = factory.createTerminalNumericField("Quantity", DisplayType.Quantity, true, false);

		initLayout();

		terminalContext.addToDisposableComponents(this);
	}

	private void initLayout()
	{

		final ITerminalFieldConstraint<BigDecimal> positiveNumericFieldConstraint =
				new PositiveNumericFieldConstraint(ERR_QTY_NOT_NULL, ERR_NEGATIVE_QTY_NOT_ALLOWED);
		quantityField.addConstraint(positiveNumericFieldConstraint);
		quantityField.setEditable(true);
		quantityField.setValue(1);

		panel.add(quantityField, "w 60px");
	}

	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		// Do noting.

	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		return true; // allow canceling
	}

	@Override
	public Object getComponent()
	{
		return panel.getComponent();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public int getQtyEntered()
	{
		final BigDecimal value = quantityField.getValue();
		if (null != value && value.signum() > 0)
		{
			return value.intValueExact();
		}
		return -1;
	}

	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

}
