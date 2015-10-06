/**
 * 
 */
package de.metas.adempiere.form.terminal.zk;

/*
 * #%L
 * de.metas.swat.zkwebui
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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.util.Check;
import org.compiere.model.MImage;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class ZKTerminalButtonWrapper
		extends ZKTerminalComponentWrapper
		implements ITerminalButton
{
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	private String bgColor;
	private String actionCommand = "Action";

	private class ButtonActionListener implements EventListener
	{

		@Override
		public void onEvent(final Event e) throws Exception
		{
			listeners.firePropertyChange(ITerminalButton.PROPERTY_Action, null, actionCommand);
		}
	}

	protected ZKTerminalButtonWrapper(final ITerminalContext tc, final org.adempiere.webui.component.Button button)
	{
		super(tc, button);
		button.addActionListener(new ButtonActionListener());
	}

	// public ZKTerminalButtonWrapper(AppsAction action)
	// {
	// super(action.getButton());
	// action.setDelegate(new ButtonActionListener());
	// }

	@Override
	public void addListener(final PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(listener);
	}

	@Override
	public org.adempiere.webui.component.Button getComponent()
	{
		return (org.adempiere.webui.component.Button)super.getComponent();
	}

	private void setStyle()
	{
		final StringBuffer style = new StringBuffer();
		if (!Check.isEmpty(bgColor, true))
		{
			style.append("background-color: ").append(bgColor).append(";");
		}

		if (style.length() > 0)
		{
			getComponent().setStyle(style.toString());
		}
	}

	@Override
	public void setAD_Image_ID(final int AD_Image_ID)
	{
		final MImage image = MImage.get(getCtx(), AD_Image_ID);
		AImage aImage;
		try
		{
			aImage = new AImage(image.getName(), image.getData());
			getComponent().setImageContent(aImage);
		}
		catch (final IOException e)
		{
			log.log(Level.WARNING, e.getLocalizedMessage(), e);
		}
		// getComponent().setVerticalTextPosition(SwingConstants.BOTTOM);
		// getComponent().setHorizontalTextPosition(SwingConstants.CENTER);
	}

	private Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		getComponent().setEnabled(enabled);
	}

	@Override
	public void setVisible(final boolean visible)
	{
		getComponent().setVisible(visible);
	}

	@Override
	public void setBackground(final Color color)
	{
		final String htmlColor = "#" + Integer.toHexString(color.getRGB() & 0x00ffffff);
		setBackground(htmlColor);
	}

	public void setBackground(final String htmlColor)
	{
		bgColor = htmlColor;
		setStyle();
	}

	@Override
	public void setFocusable(final boolean focusable)
	{
		// TODO
		// getComponent().setFocusable(focusable);
	}

	@Override
	public void setFont(final Font font)
	{
		// TODO
		// getComponent().setFont(font);
	}

	@Override
	public void setAction(final String action)
	{
		actionCommand = action;
	}

	@Override
	public void setText(final String text)
	{
		getComponent().setLabel(text);
	}
	
	@Override
	public String getText()
	{
		return getComponent().getLabel();
	}

	@Override
	public void setTextAndTranslate(String text)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPressed(final boolean pressed)
	{
		// TODO
		// getComponent().getModel().setPressed(pressed);
		// getComponent().updateUI();
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPressed()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void requestFocus()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSelected(final boolean selected)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSelected()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setToolTipText(final String tooltipText)
	{
		getComponent().setTooltiptext(tooltipText);
	}

	@Override
	public int getWidth()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHeight()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void executeBeforePainting(Runnable updater)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearExecuteBeforePaintingQueue()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
