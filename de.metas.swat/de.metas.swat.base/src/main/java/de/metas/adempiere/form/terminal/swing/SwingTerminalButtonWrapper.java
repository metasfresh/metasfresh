/**
 *
 */
package de.metas.adempiere.form.terminal.swing;

/*
 * #%L
 * de.metas.swat.base
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.SwingConstants;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.apps.AppsAction;
import org.compiere.model.MImage;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

/**
 * @author tsa
 *
 */
/* package */class SwingTerminalButtonWrapper
		extends SwingTerminalComponentWrapper
		implements ITerminalButton
{
	private static final transient Logger logger = LogManager.getLogger(SwingTerminalButtonWrapper.class);
	private final WeakPropertyChangeSupport listeners;

	private AppsAction action;

	private final ActionListener buttonActionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final String action = e.getActionCommand();
			if (action == null || action.length() == 0)
			{
				return;
			}

			try
			{
				listeners.firePropertyChange(ITerminalButton.PROPERTY_Action, null, action);
			}
			catch (final Exception ex)
			{
				final ITerminalFactory terminalFactory = getTerminalFactory();
				terminalFactory.showWarning(SwingTerminalButtonWrapper.this, "Error", null, ex);

				// NOTE: if we reached this point it means we got an unhandled exception which needs to be displayed in console too (for customer support purposes)
				SwingTerminalButtonWrapper.logger.warn(ex.getLocalizedMessage(), ex);
			}
		}
	};

	public SwingTerminalButtonWrapper(final ITerminalContext terminalContext, final AbstractButton button)
	{
		super(terminalContext, button);

		this.listeners = terminalContext.createPropertyChangeSupport(this);
		button.addActionListener(buttonActionListener);
	}

	public SwingTerminalButtonWrapper(final ITerminalContext terminalContext, final AppsAction action)
	{
		super(terminalContext, action.getButton());

		this.action = action;
		this.listeners = terminalContext.createPropertyChangeSupport(this);

		action.setDelegate(buttonActionListener);
	}

	@Override
	public final void addListener(final PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(listener);
	}

	@Override
	public final AbstractButton getComponent()
	{
		return (AbstractButton)super.getComponent();
	}

	@Override
	public final void setAD_Image_ID(final int AD_Image_ID)
	{
		final MImage image = MImage.get(getCtx(), AD_Image_ID);
		final Icon icon = image.getIcon();
		getComponent().setIcon(icon);
		getComponent().setVerticalTextPosition(SwingConstants.BOTTOM);
		getComponent().setHorizontalTextPosition(SwingConstants.CENTER);
	}

	@Override
	public final void setEnabled(final boolean enabled)
	{
		getComponent().setEnabled(enabled);
	}

	@Override
	public final void setVisible(final boolean visible)
	{
		getComponent().setVisible(visible);
	}

	@Override
	public void setBackground(final Color color)
	{
		// Guard against NPE
		if (color == null)
		{
			return;
		}

		getComponent().setBackground(color);
	}

	@Override
	public void setFocusable(final boolean focusable)
	{
		getComponent().setFocusable(focusable);
	}

	@Override
	public void requestFocus()
	{
		getComponent().requestFocus();
	}

	@Override
	public void setFont(final Font font)
	{
		getComponent().setFont(font);
	}

	@Override
	public void setAction(final String action)
	{
		getComponent().setActionCommand(action);
	}

	@Override
	public String getAction()
	{
		return getComponent().getActionCommand();
	}

	@Override
	public void setText(final String text)
	{
		getComponent().setText(text);
	}

	@Override
	public String getText()
	{
		return getComponent().getText();
	}

	@Override
	public void setTextAndTranslate(final String text)
	{
		final String textTrl;
		if (text == null)
		{
			textTrl = null;
		}
		else if (text.indexOf("@") >= 0)
		{
			textTrl = Services.get(IMsgBL.class).parseTranslation(getCtx(), text);
		}
		else
		{
			textTrl = Services.get(IMsgBL.class).translate(getCtx(), text);
		}

		setText(textTrl);
	}

	@Override
	public void setPressed(final boolean pressed)
	{
		final AbstractButton buttonSwing = getComponent();
		final ButtonModel model = buttonSwing.getModel();
		model.setPressed(pressed);
		buttonSwing.updateUI();
	}

	@Override
	public boolean isPressed()
	{
		return getComponent().getModel().isPressed();
	}

	@Override
	public void setSelected(final boolean selected)
	{
		final AbstractButton buttonSwing = getComponent();
		buttonSwing.setSelected(selected);
		buttonSwing.updateUI();
	}

	@Override
	public boolean isSelected()
	{
		final AbstractButton buttonSwing = getComponent();
		return buttonSwing.isSelected();
	}

	@Override
	public void setToolTipText(final String tooltipText)
	{
		getComponent().setToolTipText(tooltipText);
	}

	private Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public int getWidth()
	{
		return getComponent().getWidth();
	}

	@Override
	public int getHeight()
	{
		return getComponent().getHeight();
	}

	@Override
	public String toString()
	{
		return "SwingTerminalButtonWrapper [listeners=" + listeners 
				+ ", getComponent()=" + getComponent() 
				+ ", getWidth()=" + getWidth() 
				+ ", getHeight()=" + getHeight() + "]";
	}

	@Override
	public void dispose()
	{
		final AbstractButton button = getComponent();
		if (button != null)
		{
			button.removeActionListener(buttonActionListener);
		}

		if (action != null)
		{
			action.setDelegate(null);
			action.dispose();
			action = null;
		}

		super.dispose();
	}
}
