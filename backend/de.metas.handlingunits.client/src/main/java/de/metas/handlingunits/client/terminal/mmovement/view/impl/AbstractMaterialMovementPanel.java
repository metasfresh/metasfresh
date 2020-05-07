package de.metas.handlingunits.client.terminal.mmovement.view.impl;

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


import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.terminal.AbstractVMSynchronizer;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalDialogListenerAdapter;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.IMaterialMovementModel;
import de.metas.handlingunits.client.terminal.mmovement.view.IMaterialMovementPanel;

/**
 * Base class for material movement panels
 *
 * @author al
 *
 * @param <T> model type
 */
public abstract class AbstractMaterialMovementPanel<T extends IMaterialMovementModel>
		extends TerminalDialogListenerAdapter
		implements IMaterialMovementPanel
{
	protected final T model;
	protected final IContainer panel;

	private final AbstractVMSynchronizer vmSynchronizer = new AbstractVMSynchronizer()
	{

		@Override
		protected void onSaveToModel()
		{
			AbstractMaterialMovementPanel.this.saveToModel();
		}

		@Override
		protected void onLoadFromModel()
		{
			AbstractMaterialMovementPanel.this.loadFromModel();
		}
	};

	private boolean disposed = false;

	public AbstractMaterialMovementPanel(final T model)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.model = model;

		model.addPropertyChangeListener(vmSynchronizer.getLoadFromModelPropertyChangeListener());

		panel = getTerminalFactory().createContainer("fill");

		getTerminalContext().addToDisposableComponents(this);
	}


	/**
	 * Does nothing, just sets our internal disposed flag.
	 */
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

	public final T getModel()
	{
		return model;
	}

	protected final AbstractVMSynchronizer getVMSynchronizer()
	{
		return vmSynchronizer;
	}

	/**
	 * Load the model's values in the view }
	 *
	 * public final T getModel() { return model;
	 */
	protected abstract void loadFromModel();

	/**
	 * Save the view's values back to the model
	 */
	protected void saveToModel()
	{
		// nothing at this level
	}

	@Override
	public final void onDialogOk(final ITerminalDialog dialog)
	{
		boolean isUserError = false;
		try
		{
			doOnDialogOK(dialog);
		}
		catch (final TerminalException e)
		{
			isUserError = true;
			warn(ITerminalFactory.TITLE_ERROR, e);
		}
		catch (final Exception e)
		{
			warn(ITerminalFactory.TITLE_INTERNAL_ERROR, e);
		}

		// If it's a system error, get out, because the HUs might be compromised
		// Otherwise, we want to let the user continue editing
		if (!isUserError)
		{
			model.dispose();
		}
		else
		{
			dialog.cancelDispose();
		}
	}

	/**
	 * Actual implementation of {@link #onDialogOk(ITerminalDialog)}, the former being used for handling exceptions.
	 *
	 * i.e. calls {@link #model}'s execute() method.
	 *
	 * @param dialog
	 * @throws MaterialMovementException
	 */
	protected final void doOnDialogOK(final ITerminalDialog dialog) throws MaterialMovementException
	{
		final Object component = dialog.getComponent();
		Services.get(IClientUI.class).executeLongOperation(component, new Runnable()
		{
			@Override
			public void run()
			{
				saveToModel(); // make sure everything is saved on model
				model.execute();
			}
		});
	}

	@Override
	public final boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		final Object component = dialog.getComponent();
		Services.get(IClientUI.class).executeLongOperation(component, new Runnable()
		{
			@Override
			public void run()
			{
				doOnDialogCanceling(dialog);
			}
		});
		model.dispose();
		return true;
	}

	/**
	 * Actual implementation of {@link #onDialogCanceling(ITerminalDialog)}, the former being used for disposing model
	 *
	 * @param dialog
	 */
	protected abstract void doOnDialogCanceling(final ITerminalDialog dialog);

	@Override
	public final Object getComponent()
	{
		return panel.getComponent();
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return model.getTerminalContext();
	}

	public final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	protected final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	protected void warn(final String title, final Exception e)
	{
		getTerminalFactory().showWarning(this, ITerminalFactory.TITLE_ERROR, e);
	}
}
