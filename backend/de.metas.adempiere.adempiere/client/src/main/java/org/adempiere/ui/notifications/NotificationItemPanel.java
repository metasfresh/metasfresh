package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.adempiere.plaf.SwingEventNotifierUI;
import org.compiere.Adempiere;

import de.metas.adempiere.gui.ADHyperlinkHandler;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import net.miginfocom.swing.MigLayout;

/**
 * UI component for {@link NotificationItem}.
 *
 * @author tsa
 *
 */
class NotificationItemPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final NotificationItem item;
	private final INotificationItemPanelCallback callback;

	private final JLabel summaryText = new JLabel();
	private final JEditorPane detailText = new JEditorPane();

	private boolean disposed = false;

	public NotificationItemPanel(final NotificationItem item, final INotificationItemPanelCallback callback)
	{
		super();

		Check.assumeNotNull(item, "item not null");
		this.item = item;
		this.callback = callback;

		setBackground(UIManager.getColor(SwingEventNotifierUI.ITEM_BackgroundColor));
		setBorder(UIManager.getBorder(SwingEventNotifierUI.ITEM_Border));

		//
		// Component: this panel container
		final Dimension minimumSize = UIManager.getDimension(SwingEventNotifierUI.ITEM_MinimumSize);
		if (minimumSize != null)
		{
			setMinimumSize(minimumSize);
		}
		final Dimension maximumSize = UIManager.getDimension(SwingEventNotifierUI.ITEM_MaximumSize);
		if (maximumSize != null)
		{
			setMaximumSize(maximumSize);
		}

		//
		// Component: icon
		final ImageIcon logoIcon = Adempiere.getProductLogoAsIcon();
		final JLabel logoComp = new JLabel();
		logoComp.setFocusable(false);
		logoComp.setBorder(BorderFactory.createEmptyBorder());
		logoComp.setIcon(logoIcon);
		logoComp.setBackground(SwingEventNotifierUI.COLOR_Transparent);
		logoComp.setVisible(logoIcon != null);

		//
		// Component: summary text
		summaryText.setFocusable(false);
		summaryText.setFont(UIManager.getFont(SwingEventNotifierUI.ITEM_SummaryText_Font));
		summaryText.setBorder(BorderFactory.createEmptyBorder());
		summaryText.setBackground(SwingEventNotifierUI.COLOR_Transparent);
		final Color textColor = UIManager.getColor(SwingEventNotifierUI.ITEM_TextColor);
		if (textColor != null)
		{
			summaryText.setForeground(textColor);
		}

		//
		// Component: detail text label
		detailText.setContentType("text/html");
		detailText.setEditable(false);
		detailText.setFocusable(false);
		detailText.setFont(UIManager.getFont(SwingEventNotifierUI.ITEM_DetailText_Font));
		detailText.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // add some space on left and right
		detailText.setBackground(SwingEventNotifierUI.COLOR_Transparent);
		if (textColor != null)
		{
			detailText.setForeground(textColor);
		}
		detailText.addHyperlinkListener(new ADHyperlinkHandler());

		//
		// Component: small close button
		int buttonSize = UIManager.getInt(SwingEventNotifierUI.ITEM_Button_Size);
		if (buttonSize <= 0)
		{
			buttonSize = 20;
		}
		final JButton closeButton = new JButton("x");
		closeButton.setMargin(UIManager.getInsets(SwingEventNotifierUI.ITEM_Button_Insets));
		final Dimension buttomDimension = new Dimension(buttonSize, buttonSize);
		closeButton.setPreferredSize(buttomDimension);
		closeButton.setMaximumSize(buttomDimension);
		closeButton.setMinimumSize(buttomDimension);
		closeButton.setContentAreaFilled(false);
		closeButton.setBorder(UIManager.getBorder(SwingEventNotifierUI.ITEM_Button_Border));
		closeButton.setFocusable(false);
		closeButton.setBackground(SwingEventNotifierUI.COLOR_Transparent);
		closeButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				onClose();
			}
		});

		//
		// Layout
		{
			this.setLayout(new MigLayout("fill, wrap 3, gap 1px", "[][]push[]"));
			this.add(logoComp, "span 1 2, grow 0");
			this.add(summaryText, "growx 100, height " + buttonSize + "!");
			this.add(closeButton, "grow 0, height " + buttonSize + "!");
			this.add(detailText, "span 2 1, grow 100");
		}

		//
		// Load
		load();
	}

	private void load()
	{
		if (isDisposed())
		{
			return;
		}

		boolean visible = false;

		if (!Check.isEmpty(item.getSummary(), true))
		{
			summaryText.setText(StringUtils.maskHTML(item.getSummary().trim(), false));
			visible = true;
		}
		else
		{
			summaryText.setText("");
		}

		if (!Check.isEmpty(item.getDetail(), true))
		{
			// don't escape HTML because assume they are already HTMLs
			detailText.setText("<html>" + item.getDetail().trim() + "</html>");
			visible = true;
		}
		else
		{
			detailText.setText("");
		}

		setVisible(visible);
	}

	private final void onClose()
	{
		if (isDisposed())
		{
			return;
		}

		if (callback == null)
		{
			return;
		}

		callback.notificationClosed(item);
	}

	public void dispose()
	{
		setVisible(false);
		disposed = true;
	}

	private boolean isDisposed()
	{
		return disposed;
	}

	/**
	 * Fade away and dispose this notification item.
	 */
	public void fadeAwayAndDispose()
	{
		// NOTE: atm we are just disposing it. Maybe in future we will really implement a nice fade away behaviour.
		dispose();
	}
}
