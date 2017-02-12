package org.adempiere.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Metasfresh window glass pane implementation.
 *
 * NOTE: this class was based on <code>org.compiere.apps.AGlassPane</code>, implemented by Jorg Janke.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class MetasfreshGlassPane extends JPanel
{
	/**
	 * Creates a new {@link MetasfreshGlassPane} instance and sets it to given root pane container.
	 *
	 * If the root pane container already has a {@link MetasfreshGlassPane} instance set, that one will be returned.
	 *
	 * @param rootPaneContainer
	 * @return {@link AGlassPane} instance; never returns null
	 */
	public static MetasfreshGlassPane install(final RootPaneContainer rootPaneContainer)
	{
		final Component glassPaneComp = rootPaneContainer.getGlassPane();
		if (glassPaneComp instanceof MetasfreshGlassPane)
		{
			final MetasfreshGlassPane glassPane = (MetasfreshGlassPane)glassPaneComp;
			return glassPane;
		}
		else
		{
			final MetasfreshGlassPane glassPane = new MetasfreshGlassPane();
			rootPaneContainer.setGlassPane(glassPane);
			return glassPane;
		}
	}

	public static final MetasfreshGlassPane getOrNull(final RootPaneContainer rootPaneContainer)
	{
		if (rootPaneContainer == null)
		{
			return null;
		}

		final Component glassPaneComp = rootPaneContainer.getGlassPane();
		if (glassPaneComp instanceof MetasfreshGlassPane)
		{
			return (MetasfreshGlassPane)glassPaneComp;
		}
		else
		{
			return null;
		}
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(MetasfreshGlassPane.class);

	//
	// UI settings
	private static final Color backgroundColor = new Color(1f, 1f, 1f, 0.4f);
	private final Image logoImage = Adempiere.getProductLogoLarge();
	private final int logoWidth = logoImage == null ? 0 : logoImage.getWidth(this);
	private final int logoHeight = logoImage == null ? 0 : logoImage.getHeight(this);
	private final Font textFont = UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFont);
	private final Color textColor = UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor);
	//
	private final int progressBarWidth = Math.max(logoWidth, 150);
	private final int progressBarHeight = 15;
	private final Color progressBarFillColor = textColor; // AdempierePLAF.getPrimary3();
	private final Color progressbarBorderColor = textColor; // AdempierePLAF.getPrimary2();

	/** Gap between components */
	private static final int GAP = 4;

	/** The Message */
	private String _messagePlain;

	/** Timer */
	private Timer _timer;
	/** Value of timer */
	private int _timerValue = 0;
	private int _timerMax = 0;

	private MetasfreshGlassPane()
	{
		super();
		setOpaque(false);
		setVisible(false);
		setMessage(null); // set default message

		// Consume all mouse events if glass pane is visible
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(final MouseEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void mousePressed(final MouseEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void mouseExited(final MouseEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void mouseEntered(final MouseEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void mouseClicked(final MouseEvent e)
			{
				consumeEventIfVisible(e);
			}
		});

		// Consume all keyboard events if glass pane is visible
		addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(final KeyEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void keyReleased(final KeyEvent e)
			{
				consumeEventIfVisible(e);
			}

			@Override
			public void keyPressed(final KeyEvent e)
			{
				consumeEventIfVisible(e);
			}
		});
	}

	/**
	 * Set Message using a given AD_Message.
	 *
	 * If you want to directly set a message, without any transaction, use {@link #setMessagePlain(String)}.
	 *
	 * @param AD_Message to be translated - null resets to default message.
	 */
	public void setMessage(final String AD_Message)
	{
		// Translate the message
		final String message;
		if (AD_Message == null)
		{
			message = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Processing");
		}
		else if (AD_Message.isEmpty())
		{
			message = ""; // nothing
		}
		else
		{
			message = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
		}

		// Set the plain message
		setMessagePlain(message);
	}

	/**
	 * Sets given message directly. No transaction or any other processing will be performed.
	 *
	 * @param messagePlain plain message to be set directly.
	 */
	public void setMessagePlain(final String messagePlain)
	{
		if (Check.equals(_messagePlain, messagePlain))
		{
			return;
		}

		_messagePlain = messagePlain;
		if (isVisible())
		{
			repaint();
		}
	}

	/**
	 * @return displayed message (plain)
	 */
	public String getMessagePlain()
	{
		return _messagePlain;
	}

	@Override
	public void setVisible(final boolean visible)
	{
		final boolean visibleOld = isVisible();
		if (visibleOld != visible)
		{
			if (visible)
			{
				startTimerIfNeeded();
			}
			else
			{
				stopTimer();
			}
		}

		super.setVisible(visible);
	}

	/**
	 * Set and start Busy Counter if over 2 seconds
	 *
	 * @param time in seconds
	 */
	public void setBusyTimer(final int time)
	{
		logger.info("Time={}", time);

		// should we display a progress bar?
		if (time < 2)
		{
			_timerValue = 0;
			_timerMax = 0;
			stopTimer();
			if (isVisible())
			{
				repaint();
			}
			return;
		}

		_timerValue = 0;
		_timerMax = time;

		// Start Timer
		startTimerIfNeeded();
		if (!isVisible())
		{
			setVisible(true);
		}
		repaint();
	}

	private int getTimerValue()
	{
		return _timerValue;
	}

	private int getTimerMax()
	{
		return _timerMax;
	}

	private synchronized void startTimerIfNeeded()
	{
		final int timerMax = _timerMax;
		if (timerMax <= 0)
		{
			return;
		}

		if (_timer == null)
		{
			_timer = new Timer(1000, (e) -> incrementTimerValue());     // every second
		}
		if (!_timer.isRunning())
		{
			_timer.start();
		}
	}

	private synchronized void stopTimer()
	{
		if (_timer == null)
		{
			return;
		}
		_timer.stop();
	}

	private final void incrementTimerValue()
	{
		final int timerMax = _timerMax;
		if (timerMax <= 0 || !isVisible())
		{
			stopTimer();
			return;
		}

		final int timerValue = _timerValue;
		final int timerValueNew = timerValue >= timerMax ? 0 : timerValue + 1;
		_timerValue = timerValueNew;

		repaint();
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		final Dimension panelSize = getSize();
		final int panelWidth = panelSize.width;
		final int panelHeight = panelSize.height;

		//
		// Background
		{
			g.setColor(backgroundColor);
			g.fillRect(0, 0, panelWidth, panelHeight);
			// g.setColor(Color.RED); g.drawRect(0, 0, panelWidth, panelHeight); // debugging: draw the box
		}

		//
		// Logo
		// Message (under logo)
		final int progressBarY;
		{
			//
			// Calculate coordinates
			final FontMetrics textFontMetrics = g.getFontMetrics(textFont);
			final String message = Util.coalesce(getMessagePlain(), "");
			final int messageWidth = textFontMetrics.stringWidth(message);
			final int messageHeight = textFontMetrics.getAscent() + textFontMetrics.getDescent();
			//
			final int logoAndMessageHeight = logoHeight + GAP + messageHeight + GAP + progressBarHeight;
			final int logoY = panelHeight / 2 - logoAndMessageHeight / 2;
			final int messageY = logoY + logoHeight + GAP + textFontMetrics.getAscent();
			progressBarY = messageY + textFontMetrics.getDescent() + GAP;
			//
			final int logoX = panelWidth / 2 - logoWidth / 2;
			final int messageX = panelWidth / 2 - messageWidth / 2;

			// Make sure the height of logo+message is not exceeding panel's height
			if (logoAndMessageHeight > panelHeight)
			{
				logger.info("Panel's height too small to display the logo+message (panelHeight={}, logo+message height={}", panelHeight, logoAndMessageHeight);
				return;
			}
			// Make sure the width of logo is not exceeding panel's weight
			if (logoWidth > panelWidth)
			{
				logger.info("Panel's width to small to display the logo (panelWidth={}, logoWidth={})", panelWidth, logoWidth);
				return;
			}
			// Make sure the width of message is not exceeding panel's weight
			if (messageWidth > panelWidth)
			{
				logger.info("Panel's width to small to display the message (panelWidth={}, messageWidth={})", panelWidth, messageWidth);
				return;
			}

			//
			// Paint the logo
			g.drawImage(logoImage, logoX, logoY, this);
			// g.setColor(Color.RED); g.drawRect(logoX, logoY, logoWidth, logoHeight); // debugging: draw the box

			//
			// Paint the message
			g.setFont(textFont);
			g.setColor(textColor);
			g.drawString(message, messageX, messageY);
			// g.setColor(Color.GREEN); g.drawRect(messageX, messageY - messageHeight, messageWidth, messageHeight); // debugging: draw the box
		}

		//
		// Progress bar (under Message)
		final int timerMax = getTimerMax();
		if (timerMax > 0)
		{
			// Calculate coordinates
			final int timerValue = Math.max(getTimerValue(), 0);
			final int progressBarX = panelWidth / 2 - progressBarWidth / 2;
			final int progressBarFillWidth = progressBarWidth / timerMax * timerValue;

			// Pain it
			g.setColor(progressBarFillColor);
			g.fillRect(progressBarX, progressBarY, progressBarFillWidth, progressBarHeight);
			//
			g.setColor(progressbarBorderColor);
			g.drawRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);
		}
	}

	/** Consumes given input event if this glass pane is visible */
	private final void consumeEventIfVisible(final InputEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}
}
