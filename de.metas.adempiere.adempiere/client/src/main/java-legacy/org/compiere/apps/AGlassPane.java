/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Glass Pane to display "waiting" and capture events while processing.
 *
 * @author Jorg Janke
 * @version $Id: AGlassPane.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * @author dev@metasfresh.com
 */
@SuppressWarnings("serial")
public final class AGlassPane extends JPanel implements MouseListener
{
	/**
	 * @return {@link AGlassPane} instance or <code>null</code>
	 */
	public static AGlassPane getGlassPane(final Component comp)
	{
		final RootPaneContainer rootPaneContainer = AEnv.getParentComponent(comp, RootPaneContainer.class);
		if (rootPaneContainer == null)
		{
			return null;
		}
		final Component glassPane = rootPaneContainer.getGlassPane();
		if (!(glassPane instanceof AGlassPane))
		{
			return null;
		}

		final AGlassPane aGlassPane = (AGlassPane)glassPane;
		return aGlassPane;
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(AGlassPane.class);

	/** The Image */
	private final Image logoImage = Adempiere.getProductLogoLarge();
	/** The Message Font */
	private final Font textFont = UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFont);
	/** The Message Color */
	private final Color textColor = UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor);

	/** Gap between components */
	private static final int GAP = 4;

	/** The Message */
	private String _messagePlain;

	/** Timer */
	private Timer _timer;
	/** Value of timer */
	private int m_timerValue = 0;
	private int m_timerMax = 0;

	public AGlassPane()
	{
		super();
		setOpaque(false);
		setVisible(false);
		addMouseListener(this);
		setMessage(null); // set default message
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
	private String getMessagePlain()
	{
		return _messagePlain;
	}

	@Override
	public void setVisible(final boolean visible)
	{
		if (visible)
		{
			startTimerIfNeeded();
		}
		else
		{
			stopTimer();
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
			m_timerValue = 0;
			m_timerMax = 0;
			stopTimer();
			if (isVisible())
			{
				repaint();
			}
			return;
		}

		m_timerValue = 0;
		m_timerMax = time;

		// Start Timer
		startTimerIfNeeded();

		if (!isVisible())
		{
			setVisible(true);
		}
		repaint();
	}

	private synchronized void startTimerIfNeeded()
	{
		final int timerMax = m_timerMax;
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
		final int timerMax = m_timerMax;
		if (timerMax <= 0 || !isVisible())
		{
			stopTimer();
			return;
		}

		final int timerValue = m_timerValue;
		final int timerValueNew = timerValue >= timerMax ? 0 : timerValue + 1;
		m_timerValue = timerValueNew;

		repaint();
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		final String message = Util.coalesce(getMessagePlain(), "");
		final Dimension panelSize = getSize();

		//
		// Background
		{
			g.setColor(new Color(1f, 1f, 1f, 0.4f));      // .5 is a bit too light
			g.fillRect(0, 0, panelSize.width, panelSize.height);
		}

		//
		// Message and Logo
		final Dimension progressSize = new Dimension(150, 15);
		final int yProgress;
		{
			g.setFont(textFont);
			g.setColor(textColor);
			final FontMetrics fm = g.getFontMetrics();
			final Dimension messageSize = new Dimension(fm.stringWidth(message), fm.getAscent() + fm.getDescent());
			final Dimension imageSize = new Dimension(logoImage.getWidth(this), logoImage.getHeight(this));

			// Horizontal layout
			final int height = imageSize.height + GAP + messageSize.height + GAP + progressSize.height;
			if (height > panelSize.height)
			{
				logger.error("Panel too small - height={}", panelSize.height);
				return;
			}
			final int yImage = panelSize.height / 2 - height / 2;
			final int yMessage = yImage + imageSize.height + GAP + fm.getAscent();
			yProgress = yMessage + fm.getDescent() + GAP;

			// Vertical layout
			if (imageSize.width > panelSize.width || messageSize.width > panelSize.width)
			{
				logger.error("Panel too small - width={}", panelSize.width);
				return;
			}
			final int xImage = panelSize.width / 2 - imageSize.width / 2;
			final int xMessage = panelSize.width / 2 - messageSize.width / 2;

			g.drawImage(logoImage, xImage, yImage, this);
			g.drawString(message, xMessage, yMessage);
		}

		//
		// Progress bar
		if (m_timerMax > 0)
		{
			final int xProgress = panelSize.width / 2 - progressSize.width / 2;

			final int pWidth = progressSize.width / m_timerMax * m_timerValue;
			g.setColor(AdempierePLAF.getPrimary3());
			g.fill3DRect(xProgress, yProgress, pWidth, progressSize.height, true);
			g.setColor(AdempierePLAF.getPrimary2());
			g.draw3DRect(xProgress, yProgress, progressSize.width, progressSize.height, true);
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}

	@Override
	public void mousePressed(final MouseEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}

	@Override
	public void mouseExited(final MouseEvent e)
	{
		if (isVisible())
		{
			e.consume();
		}
	}

}
