package org.adempiere.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Split Pane UI
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class AdempiereSplitPaneUI extends BasicSplitPaneUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JSplitPane.class, "SplitPaneUI");

	public static final String CLIENT_PROPERTY_ApplyTabbedPaneTopGap = org.adempiere.plaf.AdempiereSplitPaneUI.class.getName() + ".ApplyTabbedPaneGap";

	public static ComponentUI createUI(final JComponent tabPane)
	{
		return new AdempiereSplitPaneUI();
	}

	public static final Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereSplitPaneUI.class.getName()
				, "SplitPaneDivider.border", AdempierePaneDividerBorder.instance
		};
	}

	private boolean applyTabbedPaneTopGap;

	@Override
	protected void installDefaults()
	{
		super.installDefaults();

		updateApplyTabbedPaneTopGap();
	}

	@Override
	protected PropertyChangeListener createPropertyChangeListener()
	{
		final PropertyChangeListener delegate = super.createPropertyChangeListener();
		return new PropertyChangeListener()
		{

			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				if (CLIENT_PROPERTY_ApplyTabbedPaneTopGap.equals(evt.getPropertyName()))
				{
					updateApplyTabbedPaneTopGap();
				}
				else
				{
					delegate.propertyChange(evt);
				}
			}
		};
	}

	protected void updateApplyTabbedPaneTopGap()
	{
		final Object applyTabbedPaneTopGapObj = splitPane.getClientProperty(CLIENT_PROPERTY_ApplyTabbedPaneTopGap);
		setApplyTabbedPaneTopGap(applyTabbedPaneTopGapObj != null && applyTabbedPaneTopGapObj instanceof Boolean && ((Boolean)applyTabbedPaneTopGapObj).booleanValue());
	}

	private void setApplyTabbedPaneTopGap(final boolean applyTabbedPaneTopGap)
	{
		if (this.applyTabbedPaneTopGap == applyTabbedPaneTopGap)
		{
			return;
		}

		this.applyTabbedPaneTopGap = applyTabbedPaneTopGap;
		splitPane.repaint();
	}

	public final boolean isApplyTabbedPaneTopGap()
	{
		return applyTabbedPaneTopGap;
	}

	@Override
	public BasicSplitPaneDivider createDefaultDivider()
	{
		return new AdempiereSplitPaneDivider(this);
	}

	/**
	 * Divider which will respect the {@link AdempiereSplitPaneUI#isApplyTabbedPaneTopGap()} option.
	 */
	private static final class AdempiereSplitPaneDivider extends BasicSplitPaneDivider
	{
		private static final long serialVersionUID = 1L;

		private final AdempiereSplitPaneUI ui;

		public AdempiereSplitPaneDivider(final AdempiereSplitPaneUI ui)
		{
			super(ui);
			setBorder(AdempierePaneDividerBorder.instance);
			this.ui = ui;
		}

		@Override
		public void paint(final Graphics g)
		{
			final Dimension size = getSize();
			final int x = 0;
			final int y = ui.isApplyTabbedPaneTopGap() ? AdempiereTabbedPaneUI.getGapBeforeFirstVerticalTab() - 2 : 0;
			final int width = size.width;
			final int height = size.height - y;

			final Color bgColor = getBackground();
			if (bgColor != null)
			{
				g.setColor(bgColor);
				g.fillRect(x, y, width, height);
			}

			// Paint the border.
			final Border border = getBorder();
			if (border != null)
			{
				border.paintBorder(this, g, x, y, width, height);
			}
		}
	}

	/**
	 * Divider border which will respect the given x, y, width and height bounds.
	 * 
	 * Please note that the original implementation was assuming x and y to be ZERO.
	 * 
	 * @see javax.swing.plaf.basic.BasicBorders#getSplitPaneDividerBorder()
	 */
	private static final class AdempierePaneDividerBorder implements Border, UIResource
	{
		public static final transient AdempierePaneDividerBorder instance = new AdempierePaneDividerBorder();

		private final Color highlight;
		private final Color shadow;

		private AdempierePaneDividerBorder()
		{
			super();

			highlight = UIManager.getColor("SplitPane.highlight");
			shadow = UIManager.getColor("SplitPane.darkShadow");
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height)
		{
			if (!(c instanceof BasicSplitPaneDivider))
			{
				return;
			}

			final JSplitPane splitPane = ((BasicSplitPaneDivider)c).getBasicSplitPaneUI().getSplitPane();

			// This is needed for the space between the divider and end of splitpane.
			g.setColor(c.getBackground());
			g.drawRect(x, y, width - 1, height - 1);

			if (splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
			{
				Component child = splitPane.getLeftComponent();
				if (child != null)
				{
					g.setColor(highlight);
					g.drawLine(x, y, x, y + height);
				}
				child = splitPane.getRightComponent();
				if (child != null)
				{
					g.setColor(shadow);
					g.drawLine(x + width - 1, y, x + width - 1, y + height);
				}
			}
			else
			{
				Component child = splitPane.getLeftComponent();
				if (child != null)
				{
					g.setColor(highlight);
					g.drawLine(x, y, x + width, y);
				}
				child = splitPane.getRightComponent();
				if (child != null)
				{
					g.setColor(shadow);
					g.drawLine(x, y + height - 1, x + width, y + height - 1);
				}
			}
		}

		@Override
		public Insets getBorderInsets(final Component c)
		{
			final Insets insets = new Insets(0, 0, 0, 0);
			if (c instanceof BasicSplitPaneDivider)
			{
				final BasicSplitPaneUI bspui = ((BasicSplitPaneDivider)c).getBasicSplitPaneUI();
				if (bspui != null)
				{
					final JSplitPane splitPane = bspui.getSplitPane();
					if (splitPane != null)
					{
						if (splitPane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
						{
							insets.top = insets.bottom = 0;
							insets.left = insets.right = 1;
							return insets;
						}
						// VERTICAL_SPLIT
						insets.top = insets.bottom = 1;
						insets.left = insets.right = 0;
						return insets;
					}
				}
			}
			insets.top = insets.bottom = insets.left = insets.right = 1;
			return insets;
		}

		@Override
		public boolean isBorderOpaque()
		{
			return true;
		}
	}

}
