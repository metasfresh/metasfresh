package de.metas.handlingunits.client.terminal.select.view;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.beans.impl.UILoadingPropertyChangeListener;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.misc.model.WebCamReceiptScheduleModel;
import de.metas.handlingunits.client.terminal.misc.view.swing.WebCamReceiptSchedulePanel;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel;

public abstract class AbstractHUSelectPanel<MT extends AbstractHUSelectModel> implements IHUSelectPanel
{
	private static final String MSG_ErrorOpeningWebcamDialog = "de.metas.handlingunits.client.ErrorOpeningWebcamDialog";

	//
	// Services
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	protected final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	protected static final String ACTION_PhotoShoot = "PhotoShoot";
	protected static final String ACTION_CloseLine = "CloseLine";

	private ITerminalContext terminalContext;
	private MT model;
	private IContainer panel;

	//
	// Top Panels
	protected ITerminalKeyPanel warehousePanel;
	protected ITerminalKeyPanel bpartnersPanel;

	//
	// Table Rows
	protected ITerminalTable2<IPOSTableRow> linesTable;

	//
	// Confirm panel & buttons
	private IConfirmPanel confirmPanel;
	private ITerminalButton btnCloseLine;
	private ITerminalButton btnPhotoShoot;

	//
	private WeakPropertyChangeSupport pcs;
	private boolean _disposed = false;

	public AbstractHUSelectPanel(final ITerminalContext terminalContext, final MT model)
	{
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;

		Check.assumeNotNull(model, "model not null");
		this.model = model;

		pcs = terminalContext.createPropertyChangeSupport(this);

		//
		// Init components
		final ITerminalFactory factory = terminalContext.getTerminalFactory();
		panel = factory.createContainer("fill, ins 0 0");

		//
		// Confirm panel & actions
		confirmPanel = factory.createConfirmPanel(true, "");
		if (model.isDisplayPhotoShootButton())
		{
			btnPhotoShoot = confirmPanel.addButton(AbstractHUSelectPanel.ACTION_PhotoShoot);
		}
		if (model.isDisplayCloseLinesButton())
		{
			btnCloseLine = confirmPanel.addButton(AbstractHUSelectPanel.ACTION_CloseLine);
		}
		confirmPanel.addListener(new UILoadingPropertyChangeListener(panel.getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
				{
					return;
				}

				final String action = String.valueOf(evt.getNewValue());
				if (IConfirmPanel.ACTION_OK.equals(action))
				{
					if (linesTable != null)
					{
						linesTable.stopEditing();
					}

					try (final ITerminalContextReferences newReferences = getTerminalContext().newReferences())
					{
						doProcessSelectedLines(getModel());
					}
					catch (Exception e)
					{
						throw AdempiereException.wrapIfNeeded(e);
					}
					// Refresh lines (even if user canceled the editing)
					getModel().refreshLines(true);
				}
				else if (IConfirmPanel.ACTION_Cancel.equals(action))
				{
					dispose();
				}
				else if (AbstractHUSelectPanel.ACTION_PhotoShoot.equals(action))
				{
					doTakePhotoshot();
				}
				else if (AbstractHUSelectPanel.ACTION_CloseLine.equals(action))
				{
					doCloseLine();
				}
			}
		});

		terminalContext.addToDisposableComponents(this);
	}

	protected final IConfirmPanel getConfirmPanel()
	{
		return confirmPanel;
	}

	/**
	 * @return {@link #ACTION_CloseLine} button or <code>null</code>
	 */
	protected final ITerminalButton getButtonCloseLines()
	{
		return btnCloseLine;
	}

	/**
	 * @return {@link #ACTION_PhotoShoot} button or <code>null</code>
	 */
	protected final ITerminalButton getButtonPhotoShoot()
	{
		return btnPhotoShoot;
	}

	/**
	 * Implement this method based on the requirements regarding processing in the Select Panel you are working on.
	 *
	 * Notes:
	 * <li>It is not mandatory to implement it only in case of a POS with lines.
	 * <li>Make sure not to create components that "lie outside" of the processed lines,
	 * because this method is invoked with its on {@link ITerminalContextReferences} that will be deleted right after the method finishes.
	 */
	protected abstract void doProcessSelectedLines(MT model);

	@Override
	public final void dispose()
	{
		// Make sure it was not disposed before
		if (_disposed)
		{
			return;
		}

		//
		// Call Before Dispose custom implementations
		if (!onBeforeDispose())
		{
			return;
		}

		//
		// Cleanup
		terminalContext = null;
		model = null; // don't dispose it because it was not created by us

		// note: we don't have to dispose our ITerminalKeyPanels and ITerminalTable2, because they add themselves to the terminal context as disposable compoments in their own constructors

		// Mark as disposed
		_disposed = true;

		//
		// Notify everybody that this window will be disposed
		firePropertyChange(IHUSelectPanel.PROPERTY_Disposed, false, true);
	}

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	/**
	 * Method called before actually disposing this panel
	 *
	 * @return true if was successfully executed; if false is returned, disposing is canceled
	 */
	protected boolean onBeforeDispose()
	{
		// nothing at this level
		return true;
	}

	protected final MT getModel()
	{
		return model;
	}

	@Override
	public final Object getComponent()
	{
		if (panel == null)
		{
			return null;
		}
		return panel.getComponent();
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	protected final void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue)
	{
		if (pcs == null)
		{
			return;
		}
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public final ITerminalFactory getTerminalFactory()
	{
		return terminalContext.getTerminalFactory();
	}

	protected final IComponent createPanel(final IComponent component, final Object constraints)
	{
		panel.add(component, constraints);

		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(panel);
		scroll.setUnitIncrementVSB(16);
		final IContainer card = getTerminalFactory().createContainer();
		card.add(scroll, "growx, growy");
		return card;
	}

	/**
	 * Opens HU Editor for editing given HUs. Does not cause a new {@link ITerminalContextReferences} instance to be created,
	 * because we should assume that one was already created around the given <code>huEditorModel</code>.
	 *
	 * @param huEditorModel
	 * @return <code>true</code> if editor was closed with OK; false if editor was closed with Cancel.
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public boolean editHUs(final HUEditorModel huEditorModel)
	{
		final HUEditorPanel editorPanel = createHUEditorPanelInstance(huEditorModel);

		// we already have our own terminal context ref that was created when 'huEditorModel' was created
		final boolean maintainOwnContextReferences = false;

		final ITerminalDialog editorDialog = getTerminalFactory()
				.createModalDialog(this, "Edit", editorPanel, maintainOwnContextReferences); // TODO ts: Hardcoded ?!?

		editorDialog.setSize(getTerminalContext().getScreenResolution());

		// Activate editor dialog and wait until user closes the window
		editorDialog.activate();

		// Return true if user actually edited the given HUs (i.e. he did not pressed Cancel)
		final boolean edited = !editorDialog.isCanceled();

		return edited;
	}

	/**
	 * Create an {@link HUEditorPanel} instance.
	 *
	 * @param huEditorModel
	 * @return {@link HUEditorPanel} instance.
	 */
	protected HUEditorPanel createHUEditorPanelInstance(final HUEditorModel huEditorModel)
	{
		final HUEditorPanel editorPanel = new HUEditorPanel(huEditorModel);
		return editorPanel;
	}

	/**
	 * Take a photoshot and attach it to underlying model of current selected row.
	 */
	protected final void doTakePhotoshot()
	{
		final MT model = getModel();
		final Object selectedRecord = model.getSelectedObject();
		if (selectedRecord == null)
		{
			return;
		}

		final WebCamReceiptScheduleModel webcamModel = new WebCamReceiptScheduleModel(getTerminalContext(), selectedRecord);
		final WebCamReceiptSchedulePanel webcamPanel = new WebCamReceiptSchedulePanel(webcamModel);
		final ITerminalDialog webCamDialog = getTerminalFactory().createModalDialog(this, "Web Photo", webcamPanel);

		try
		{
			webCamDialog.activate();
		}
		catch (final Exception e)
		{
			if (webCamDialog != null)
			{
				webCamDialog.dispose();
			}

			throw new AdempiereException("@" + MSG_ErrorOpeningWebcamDialog + "@", e);
		}
	}

	/**
	 * Button action: Close selected lines
	 */
	private final void doCloseLine()
	{
		// Close the line only if the conditions from BeforeCloseLine are accomplished
		final boolean closeLineOK = beforeCloseLine();

		if (closeLineOK)
		{
			final MT model = getModel();
			model.doCloseLines();
		}
	}

	protected boolean beforeCloseLine()
	{
		// At this level, simply return true. The line can be safely closed
		return true;
	}
}
