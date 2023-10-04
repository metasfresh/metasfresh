/*
 * Copyright (c) 2001-2006 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package org.adempiere.plaf;

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


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.text.View;

import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;

/**
 * The JGoodies Plastic Look&amp;Feel implementation of <code>TabbedPaneUI</code>.
 * 
 * It differs from its superclass <code>MetalTabbedPaneUI</code> in that it paints new tab shapes,
 * provides two options, and supports ClearLook.
 * <p>
 * You can enable or disable icons in tabs globally via com.jgoodies.looks.Options.setTabIconsEnabled(boolean).
 * <p>
 * To disable the content border set
 * 
 * <pre>
 * JTabbedPane tabbedPane = new JTabbedPane();
 * tabbedPane.putClientProperty(Option.NO_CONTENT_BORDER_KEY, Boolean.TRUE);
 * </pre>
 * 
 * To paint embedded tabs use
 * 
 * <pre>
 * JTabbedPane tabbedPane = new JTabbedPane();
 * tabbedPane.putClientProperty(Option.EMBEDDED_TABS_KEY, Boolean.TRUE);
 * </pre>
 * <p>
 * There's a special mode that helps you detect content borders in heavily wrapped component hierarchies - such as the NetBeans IDE. In this marked mode the content border is painted as a Magenta
 * line. You can enable this mode by setting the System property <tt>markContentBorders</tt> to <tt>true</tt>; in a command line:
 * 
 * <pre>
 * java -DmarkContentBorders=true
 * </pre>
 * 
 * @author Karsten Lentzsch
 * @author Torge Husfeldt
 * @author Andrej Golovnin
 * @version $Revision: 1.4 $
 * 
 * @see Options
 */
public final class AdempiereTabbedPaneUI extends MetalTabbedPaneUI
{
	/** 
	 * The UI Class ID to bind this UI to
	 * See {@link JTabbedPane#getUIClassID()}.
	 */
	public static final String uiClassID = "TabbedPaneUI";

	public static final String KEY_AlignVerticalTabsWithHorizontalTabs_Enabled = "TabbedPane.AlignVerticalTabsWithHorizontalTabs";

	public static final String KEY_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab = "VPanel.StandardWindow.AlignVerticalTabsWithHorizontalTabs.GapBeforeFirstTab";
	public static final int DEFAULT_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab = 25;

	/**
	 * Creates the <code>PlasticTabbedPaneUI</code>.
	 * 
	 * @see javax.swing.plaf.ComponentUI#createUI(JComponent)
	 */
	public static ComponentUI createUI(JComponent tabPane)
	{
		return new AdempiereTabbedPaneUI();
	}
	
	public static final Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereTabbedPaneUI.class.getName()
				//
				, KEY_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab, DEFAULT_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab
		};
	}
	
	/**
	 * Helper method to apply apply the top gap used by the Tabbed pane to a included {@link JSplitPane} component.
	 * 
	 * @param splitPane
	 */
	public static final void applyTabbedPaneTopGapToLeftComponent(final JSplitPane splitPane)
	{
		//
		// Get split pane's left component
		final JComponent splitPaneLeftComp = (JComponent)splitPane.getLeftComponent();
		if (splitPaneLeftComp == null)
		{
			return;
		}
		
		//
		// Create a border around the left component which:
		// * will introduce a top gap to align with outer Tabbed pane
		// * will draw a line on top to "connect" with outer tabbed pane's Tab. 
		splitPaneLeftComp.setBorder(BorderFactory.createCompoundBorder(
				// outsideBorder:
				BorderFactory.createEmptyBorder(AdempiereTabbedPaneUI.getGapBeforeFirstVerticalTab() - 2, 0, 0, 0)
				// insideBorder:
				, BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("TabbedPane.darkShadow"))
				));
		splitPane.putClientProperty(AdempiereSplitPaneUI.CLIENT_PROPERTY_ApplyTabbedPaneTopGap, true);
		
		// NOTE: setting the divider size to 2 because in case of 1, the divider won't be drawn while dragging
		splitPane.setDividerSize(2);
	}

	// State ******************************************************************

	/**
	 * Describes if tabs are painted with or without icons.
	 */
	private static boolean isTabIconsEnabled = Options.isTabIconsEnabled();

	/**
	 * Describes if we paint no content border or not; is false by default.
	 * You can disable the content border by setting the client property
	 * Options.NO_CONTENT_BORDER_KEY to Boolean.TRUE;
	 */
	private Boolean noContentBorder;

	/**
	 * Describes if we paint tabs in an embedded style that is with
	 * less decoration; this is false by default.
	 * You can enable the embedded tabs style by setting the client property
	 * {@link Options#EMBEDDED_TABS_KEY} to Boolean.TRUE.
	 */
	private Boolean embeddedTabs;

	/**
	 * Holds the renderer that is used to render the tabs.
	 */
	private AbstractRenderer renderer;

	/** For use when tabLayoutPolicy == SCROLL_TAB_LAYOUT. */
	private ScrollableTabSupport tabScroller;

	private Boolean hideIfOneTab;
	
	/**
	 * Installs the UI.
	 * 
	 * @see javax.swing.plaf.ComponentUI#installUI(JComponent)
	 */
	@Override
	public void installUI(JComponent c)
	{
		super.installUI(c);
		embeddedTabs = (Boolean)c.getClientProperty(Options.EMBEDDED_TABS_KEY);
		noContentBorder = (Boolean)c.getClientProperty(Options.NO_CONTENT_BORDER_KEY);
		hideIfOneTab = (Boolean)c.getClientProperty(AdempiereLookAndFeel.HIDE_IF_ONE_TAB);
		renderer = createRenderer(tabPane);
	}

	/**
	 * Uninstalls the UI.
	 * 
	 * @see javax.swing.plaf.ComponentUI#uninstallUI(JComponent)
	 */
	@Override
	public void uninstallUI(JComponent c)
	{
		renderer = null;
		super.uninstallUI(c);
	}

	/**
	 * Creates and installs any required subcomponents for the JTabbedPane.
	 * Invoked by installUI.
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#installComponents()
	 */
	@Override
	protected void installComponents()
	{
		if (scrollableTabLayoutEnabled())
		{
			if (tabScroller == null)
			{
				tabScroller = new ScrollableTabSupport(tabPane.getTabPlacement());
				tabPane.add(tabScroller.viewport);
			}
		}
	}

	/**
	 * Removes any installed subcomponents from the JTabbedPane.
	 * Invoked by uninstallUI.
	 * 
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#uninstallComponents()
	 */
	@Override
	protected void uninstallComponents()
	{
		if (scrollableTabLayoutEnabled())
		{
			tabPane.remove(tabScroller.viewport);
			tabPane.remove(tabScroller.scrollForwardButton);
			tabPane.remove(tabScroller.scrollBackwardButton);
			tabScroller = null;
		}
	}

	@Override
	protected void installKeyboardActions()
	{
		super.installKeyboardActions();
		// if the layout policy is the SCROLL_TAB_LAYOUT, then replace
		// the forward and backward actions, installed in the action map
		// in the supper class, by our own.
		if (scrollableTabLayoutEnabled())
		{
			Action forwardAction = new ScrollTabsForwardAction();
			Action backwardAction = new ScrollTabsBackwardAction();
			ActionMap am = SwingUtilities.getUIActionMap(tabPane);
			am.put("scrollTabsForwardAction", forwardAction);
			am.put("scrollTabsBackwardAction", backwardAction);
			tabScroller.scrollForwardButton.setAction(forwardAction);
			tabScroller.scrollBackwardButton.setAction(backwardAction);
		}
	}
	
	/**
	 * Checks and answers if content border will be painted.
	 * This is controlled by the component's client property
	 * Options.NO_CONTENT_BORDER or Options.EMBEDDED.
	 */
	private boolean hasNoContentBorder()
	{
		return Boolean.TRUE.equals(noContentBorder);
	}
	
	/**
	 * @param contentBorderPlacement TOP, LEFT, RIGHT, BOTTOM
	 * @return true if given content border shall be drawn
	 */
	private boolean isDrawContentBorder(final int contentBorderPlacement)
	{
		if (hasNoContentBorder())
		{
			return false;
		}
		
		// Draw only the content border which is near our tabs
		// TODO: at the moment this is hardcoded (because it perfectly fulfills our needs), but in future we might consider to make it configurable
		final int tabPlacement = tabPane.getTabPlacement();
		if (tabPlacement != contentBorderPlacement)
		{
			return false;
		}

		return true;
	}

	/**
	 * Checks and answers if tabs are painted with minimal decoration.
	 */
	private boolean hasEmbeddedTabs()
	{
		return Boolean.TRUE.equals(embeddedTabs);
	}

	private boolean isHideIfOneTab()
	{
		return Boolean.TRUE.equals(hideIfOneTab);
	}

	/**
	 * Creates the renderer used to lay out and paint the tabs.
	 * 
	 * @param tabbedPane the UIs component
	 * @return AbstractRenderer the renderer that will be used to paint
	 */
	private AbstractRenderer createRenderer(JTabbedPane tabbedPane)
	{
		return hasEmbeddedTabs()
				? AbstractRenderer.createEmbeddedRenderer(tabbedPane)
				: AbstractRenderer.createRenderer(tabPane);
	}

	/**
	 * Creates and answer a handler that listens to property changes.
	 * Unlike the superclass BasicTabbedPane, the PlasticTabbedPaneUI
	 * uses an extended Handler.
	 */
	@Override
	protected PropertyChangeListener createPropertyChangeListener()
	{
		return new TabbedPanePropertyChangeHandler();
	}

	@Override
	protected ChangeListener createChangeListener()
	{
		return new TabSelectionHandler();
	}

	/*
	 * Private helper method for the next three methods.
	 */
	private void doLayout()
	{
		tabPane.revalidate();
		tabPane.repaint();
	}

	/**
	 * Updates the renderer and layout.
	 * 
	 * This message is sent by {@link TabbedPanePropertyChangeHandler} whenever a property which needs relayouting is changed.
	 */
	private void updateRendererAndDoLayout()
	{
		renderer = createRenderer(tabPane);
		if (scrollableTabLayoutEnabled())
		{
			tabScroller.createButtons();
		}
		doLayout();
	}

	/**
	 * Updates the embedded tabs property. This message is sent by
	 * my PropertyChangeHandler whenever the embedded tabs property changes.
	 */
	private void embeddedTabsPropertyChanged(Boolean newValue)
	{
		embeddedTabs = newValue;
		renderer = createRenderer(tabPane);
		doLayout();
	}

	/**
	 * Updates the no content border property. This message is sent
	 * by my PropertyChangeHandler whenever the noContentBorder
	 * property changes.
	 */
	private void noContentBorderPropertyChanged(Boolean newValue)
	{
		noContentBorder = newValue;
		tabPane.repaint();
	}

	@Override
	public void paint(Graphics g, JComponent c)
	{
		final int selectedIndex = tabPane.getSelectedIndex();
		final int tabPlacement = tabPane.getTabPlacement();

		ensureCurrentLayout();

		// Paint tab area
		// If scrollable tabs are enabled, the tab area will be
		// painted by the scrollable tab panel instead.
		//
		if (!scrollableTabLayoutEnabled())
		{ // WRAP_TAB_LAYOUT
			paintTabArea(g, tabPlacement, selectedIndex);
		}

		// Paint content border
		paintContentBorder(g, tabPlacement, selectedIndex);
	}

	@Override
	protected void paintTab(final Graphics g, final int tabPlacement, final Rectangle[] rects, final int tabIndex, final Rectangle iconRect, final Rectangle textRect)
	{
		final Rectangle tabRect = rects[tabIndex];
		final int selectedIndex = tabPane.getSelectedIndex();
		final boolean isSelected = selectedIndex == tabIndex;
		Graphics2D g2 = null;
		Polygon cropShape = null;
		Shape save = null;
		int cropx = 0;
		int cropy = 0;

		if (scrollableTabLayoutEnabled())
		{
			if (g instanceof Graphics2D)
			{
				g2 = (Graphics2D)g;

				// Render visual for cropped tab edge...
				Rectangle viewRect = tabScroller.viewport.getViewRect();
				int cropline;
				switch (tabPlacement)
				{
					case LEFT:
					case RIGHT:
						cropline = viewRect.y + viewRect.height;
						if ((tabRect.y < cropline)
								&& (tabRect.y + tabRect.height > cropline))
						{
							cropShape = createCroppedTabClip(tabPlacement, tabRect,
									cropline);
							cropx = tabRect.x;
							cropy = cropline - 1;
						}
						break;
					case TOP:
					case BOTTOM:
					default:
						cropline = viewRect.x + viewRect.width;
						if ((tabRect.x < cropline)
								&& (tabRect.x + tabRect.width > cropline))
						{
							cropShape = createCroppedTabClip(tabPlacement, tabRect,
									cropline);
							cropx = cropline - 1;
							cropy = tabRect.y;
						}
				}
				if (cropShape != null)
				{
					save = g.getClip();
					g2.clip(cropShape);
				}
			}
		}

		paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);

		paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);

		String title = tabPane.getTitleAt(tabIndex);
		Font font = tabPane.getFont();
		FontMetrics metrics = g.getFontMetrics(font);
		Icon icon = getIconForTab(tabIndex);

		layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);

		paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);

		paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);

		paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);

		if (cropShape != null)
		{
			paintCroppedTabEdge(g, tabPlacement, tabIndex, isSelected, cropx, cropy);
			g.setClip(save);
		}
	}

	/*
	 * This method will create and return a polygon shape for the given tab
	 * rectangle which has been cropped at the specified cropline with a torn
	 * edge visual. e.g. A "File" tab which has cropped been cropped just after
	 * the "i":
	 * -------------
	 * | ..... |
	 * | . |
	 * | ... . |
	 * | . . |
	 * | . . |
	 * | . . |
	 * --------------
	 * 
	 * The x, y arrays below define the pattern used to create a "torn" edge
	 * segment which is repeated to fill the edge of the tab. For tabs placed on
	 * TOP and BOTTOM, this righthand torn edge is created by line segments
	 * which are defined by coordinates obtained by subtracting xCropLen[i] from
	 * (tab.x + tab.width) and adding yCroplen[i] to (tab.y). For tabs placed on
	 * LEFT or RIGHT, the bottom torn edge is created by subtracting xCropLen[i]
	 * from (tab.y + tab.height) and adding yCropLen[i] to (tab.x).
	 */
	private int[] xCropLen = { 1, 1, 0, 0, 1, 1, 2, 2 };

	private int[] yCropLen = { 0, 3, 3, 6, 6, 9, 9, 12 };

	private static final int CROP_SEGMENT = 12;

	private Polygon createCroppedTabClip(int tabPlacement, Rectangle tabRect, int cropline)
	{
		int rlen = 0;
		int start = 0;
		int end = 0;
		int ostart = 0;

		switch (tabPlacement)
		{
			case LEFT:
			case RIGHT:
				rlen = tabRect.width;
				start = tabRect.x;
				end = tabRect.x + tabRect.width;
				ostart = tabRect.y;
				break;
			case TOP:
			case BOTTOM:
			default:
				rlen = tabRect.height;
				start = tabRect.y;
				end = tabRect.y + tabRect.height;
				ostart = tabRect.x;
		}
		int rcnt = rlen / CROP_SEGMENT;
		if (rlen % CROP_SEGMENT > 0)
		{
			rcnt++;
		}
		int npts = 2 + (rcnt * 8);
		int[] xp = new int[npts];
		int[] yp = new int[npts];
		int pcnt = 0;

		xp[pcnt] = ostart;
		yp[pcnt++] = end;
		xp[pcnt] = ostart;
		yp[pcnt++] = start;
		for (int i = 0; i < rcnt; i++)
		{
			for (int j = 0; j < xCropLen.length; j++)
			{
				xp[pcnt] = cropline - xCropLen[j];
				yp[pcnt] = start + (i * CROP_SEGMENT) + yCropLen[j];
				if (yp[pcnt] >= end)
				{
					yp[pcnt] = end;
					pcnt++;
					break;
				}
				pcnt++;
			}
		}
		if (tabPlacement == SwingConstants.TOP
				|| tabPlacement == SwingConstants.BOTTOM)
		{
			return new Polygon(xp, yp, pcnt);

		}
		// LEFT or RIGHT
		return new Polygon(yp, xp, pcnt);
	}

	/*
	 * If tabLayoutPolicy == SCROLL_TAB_LAYOUT, this method will paint an edge
	 * indicating the tab is cropped in the viewport display
	 */
	private void paintCroppedTabEdge(Graphics g, int tabPlacement, int tabIndex, boolean isSelected, int x, int y)
	{
		switch (tabPlacement)
		{
			case LEFT:
			case RIGHT:
				int xx = x;
				g.setColor(shadow);
				while (xx <= x + rects[tabIndex].width)
				{
					for (int i = 0; i < xCropLen.length; i += 2)
					{
						g.drawLine(xx + yCropLen[i], y - xCropLen[i], xx
								+ yCropLen[i + 1] - 1, y - xCropLen[i + 1]);
					}
					xx += CROP_SEGMENT;
				}
				break;
			case TOP:
			case BOTTOM:
			default:
				int yy = y;
				g.setColor(shadow);
				while (yy <= y + rects[tabIndex].height)
				{
					for (int i = 0; i < xCropLen.length; i += 2)
					{
						g.drawLine(x - xCropLen[i], yy + yCropLen[i], x
								- xCropLen[i + 1], yy + yCropLen[i + 1] - 1);
					}
					yy += CROP_SEGMENT;
				}
		}
	}

	private void ensureCurrentLayout()
	{
		if (!tabPane.isValid())
		{
			tabPane.validate();
		}
		/*
		 * If tabPane doesn't have a peer yet, the validate() call will
		 * silently fail. We handle that by forcing a layout if tabPane
		 * is still invalid. See bug 4237677.
		 */
		if (!tabPane.isValid())
		{
			TabbedPaneLayout layout = (TabbedPaneLayout)tabPane.getLayout();
			layout.calculateLayoutInfo();
		}
	}

	/**
	 * Returns the tab index which intersects the specified point
	 * in the JTabbedPane's coordinate space.
	 */
	@Override
	public int tabForCoordinate(JTabbedPane pane, int x, int y)
	{
		ensureCurrentLayout();
		Point p = new Point(x, y);

		if (scrollableTabLayoutEnabled())
		{
			translatePointToTabPanel(x, y, p);
			Rectangle viewRect = tabScroller.viewport.getViewRect();
			if (!viewRect.contains(p))
			{
				return -1;
			}
		}
		int tabCount = tabPane.getTabCount();
		for (int i = 0; i < tabCount; i++)
		{
			if (rects[i].contains(p.x, p.y))
			{
				return i;
			}
		}
		return -1;
	}

	@Override
	protected Rectangle getTabBounds(int tabIndex, Rectangle dest)
	{
		dest.width = rects[tabIndex].width;
		dest.height = rects[tabIndex].height;
		if (scrollableTabLayoutEnabled())
		{ // SCROLL_TAB_LAYOUT
			// Need to translate coordinates based on viewport location &
			// view position
			Point vpp = tabScroller.viewport.getLocation();
			Point viewp = tabScroller.viewport.getViewPosition();
			dest.x = rects[tabIndex].x + vpp.x - viewp.x;
			dest.y = rects[tabIndex].y + vpp.y - viewp.y;
		}
		else
		{ // WRAP_TAB_LAYOUT
			dest.x = rects[tabIndex].x;
			dest.y = rects[tabIndex].y;
		}
		return dest;
	}

	/**
	 * Returns the index of the tab closest to the passed in location, note
	 * that the returned tab may not contain the location x,y.
	 */
	private int getClosestTab(int x, int y)
	{
		int min = 0;
		int tabCount = Math.min(rects.length, tabPane.getTabCount());
		int max = tabCount;
		int tabPlacement = tabPane.getTabPlacement();
		boolean useX = (tabPlacement == TOP || tabPlacement == BOTTOM);
		int want = (useX) ? x : y;

		while (min != max)
		{
			int current = (max + min) / 2;
			int minLoc;
			int maxLoc;

			if (useX)
			{
				minLoc = rects[current].x;
				maxLoc = minLoc + rects[current].width;
			}
			else
			{
				minLoc = rects[current].y;
				maxLoc = minLoc + rects[current].height;
			}
			if (want < minLoc)
			{
				max = current;
				if (min == max)
				{
					return Math.max(0, current - 1);
				}
			}
			else if (want >= maxLoc)
			{
				min = current;
				if (max - min <= 1)
				{
					return Math.max(current + 1, tabCount - 1);
				}
			}
			else
			{
				return current;
			}
		}
		return min;
	}

	/**
	 * Returns a point which is translated from the specified point in the
	 * JTabbedPane's coordinate space to the coordinate space of the
	 * ScrollableTabPanel. This is used for SCROLL_TAB_LAYOUT ONLY.
	 */
	private Point translatePointToTabPanel(int srcx, int srcy, Point dest)
	{
		Point vpp = tabScroller.viewport.getLocation();
		Point viewp = tabScroller.viewport.getViewPosition();
		dest.x = srcx - vpp.x + viewp.x;
		dest.y = srcy - vpp.y + viewp.y;
		return dest;
	}

	@Override
	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex)
	{
		int tabCount = tabPane.getTabCount();

		Rectangle iconRect = new Rectangle(), textRect = new Rectangle();
		Rectangle clipRect = g.getClipBounds();

		// Paint tabRuns of tabs from back to front
		for (int i = runCount - 1; i >= 0; i--)
		{
			int start = tabRuns[i];
			int next = tabRuns[(i == runCount - 1) ? 0 : i + 1];
			int end = (next != 0 ? next - 1 : tabCount - 1);
			for (int j = end; j >= start; j--)
			{
				if (j != selectedIndex && rects[j].intersects(clipRect))
				{
					paintTab(g, tabPlacement, rects, j, iconRect, textRect);
				}
			}
		}

		// Paint selected tab if its in the front run
		// since it may overlap other tabs
		if (selectedIndex >= 0 && rects[selectedIndex].intersects(clipRect))
		{
			paintTab(g, tabPlacement, rects, selectedIndex, iconRect, textRect);
		}
	}

	/*
	 * Copied here from super(super)class to avoid labels being centered on
	 * vertical tab runs if they consist of icon and text
	 */
	@Override
	protected void layoutLabel(
			int tabPlacement,
			FontMetrics metrics,
			int tabIndex,
			String title,
			Icon icon,
			Rectangle tabRect,
			Rectangle iconRect,
			Rectangle textRect,
			boolean isSelected)
	{
		textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
		// fix of issue #4
		View v = getTextViewForTab(tabIndex);
		if (v != null)
		{
			tabPane.putClientProperty("html", v);
		}

		Rectangle calcRectangle = new Rectangle(tabRect);
		if (isSelected)
		{
			Insets calcInsets = getSelectedTabPadInsets(tabPlacement);
			calcRectangle.x += calcInsets.left;
			calcRectangle.y += calcInsets.top;
			calcRectangle.width -= calcInsets.left + calcInsets.right;
			calcRectangle.height -= calcInsets.bottom + calcInsets.top;
		}
		int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
		int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
		if ((tabPlacement == RIGHT || tabPlacement == LEFT) && icon != null && title != null && !title.equals(""))
		{
			SwingUtilities.layoutCompoundLabel(
					tabPane,
					metrics,
					title,
					icon,
					SwingConstants.CENTER,
					SwingConstants.LEFT,
					SwingConstants.CENTER,
					SwingConstants.TRAILING,
					calcRectangle,
					iconRect,
					textRect,
					textIconGap);
			xNudge += 4;
		}
		else
		{
			SwingUtilities.layoutCompoundLabel(
					tabPane,
					metrics,
					title,
					icon,
					SwingConstants.CENTER,
					SwingConstants.CENTER,
					SwingConstants.CENTER,
					SwingConstants.TRAILING,
					calcRectangle,
					iconRect,
					textRect,
					textIconGap);
			iconRect.y += calcRectangle.height % 2;
		}

		// fix of issue #4
		tabPane.putClientProperty("html", null);

		iconRect.x += xNudge;
		iconRect.y += yNudge;
		textRect.x += xNudge;
		textRect.y += yNudge;
	}

	/**
	 * Answers the icon for the tab with the specified index.
	 * In case, we have globally switched of the use tab icons,
	 * we answer <code>null</code> if and only if we have a title.
	 */
	@Override
	protected Icon getIconForTab(int tabIndex)
	{
		String title = tabPane.getTitleAt(tabIndex);
		boolean hasTitle = (title != null) && (title.length() > 0);
		return !isTabIconsEnabled && hasTitle
				? null
				: super.getIconForTab(tabIndex);
	}

	/**
	 * Creates the layout manager used to set the tab's bounds.
	 */
	@Override
	protected LayoutManager createLayoutManager()
	{
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT)
		{
			return new TabbedPaneScrollLayout();
		}
		/* WRAP_TAB_LAYOUT */
		return new TabbedPaneLayout();
	}

	/*
	 * In an attempt to preserve backward compatibility for programs
	 * which have extended BasicTabbedPaneUI to do their own layout, the
	 * UI uses the installed layoutManager (and not tabLayoutPolicy) to
	 * determine if scrollTabLayout is enabled.
	 */
	private boolean scrollableTabLayoutEnabled()
	{
		return tabPane.getLayout() instanceof TabbedPaneScrollLayout;
	}

	protected boolean isTabInFirstRun(int tabIndex)
	{
		return getRunForTab(tabPane.getTabCount(), tabIndex) == 0;
	}

	@Override
	protected void paintContentBorder(final Graphics g, final int tabPlacement, final int selectedIndex)
	{
		int width = tabPane.getWidth();
		int height = tabPane.getHeight();
		Insets insets = tabPane.getInsets();

		int x = insets.left;
		int y = insets.top;
		int w = width - insets.right - insets.left;
		int h = height - insets.top - insets.bottom;

		switch (tabPlacement)
		{
			case LEFT:
				x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
				w -= (x - insets.left);
				break;
			case RIGHT:
				w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
				break;
			case BOTTOM:
				h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
				break;
			case TOP:
			default:
				y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
				h -= (y - insets.top);
		}
		// Fill region behind content area
		g.setColor(selectColor == null
				? tabPane.getBackground()
				: selectColor);
		g.fillRect(x, y, w, h);

		final Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, calcRect);
		final boolean drawBroken = selectedIndex >= 0 && isTabInFirstRun(selectedIndex);
		// boolean isContentBorderPainted = !hasNoContentBorder();
		
		// It sounds a bit odd to call paintContentBorder with
		// a parameter isContentBorderPainted set to false.
		// But in this case the part of the border touching the tab
		// area will still be painted so best let the renderer decide.
		renderer.paintContentBorderTopEdge(g, x, y, w, h, drawBroken, selRect, isDrawContentBorder(TOP));
		renderer.paintContentBorderLeftEdge(g, x, y, w, h, drawBroken, selRect, isDrawContentBorder(LEFT));
		renderer.paintContentBorderBottomEdge(g, x, y, w, h, drawBroken, selRect, isDrawContentBorder(BOTTOM));
		renderer.paintContentBorderRightEdge(g, x, y, w, h, drawBroken, selRect, isDrawContentBorder(RIGHT));
	}

	//
	// Here comes a number of methods that are just delegated to the
	// appropriate renderer
	//
	/**
	 * Returns the insets (i.e. the width) of the content Border.
	 */
	@Override
	protected Insets getContentBorderInsets(int tabPlacement)
	{
		return renderer.getContentBorderInsets(super.getContentBorderInsets(tabPlacement));
	}

	/**
	 * Returns the amount by which the Tab Area is inset.
	 */
	@Override
	protected Insets getTabAreaInsets(int tabPlacement)
	{
		return renderer.getTabAreaInsets(super.getTabAreaInsets(tabPlacement));
	}

	/**
	 * Returns the amount by which the label should be shifted horizontally.
	 */
	@Override
	protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected)
	{
		return renderer.getTabLabelShiftX(tabIndex, isSelected);
	}

	/**
	 * Returns the amount by which the label should be shifted vertically.
	 */
	@Override
	protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected)
	{
		return renderer.getTabLabelShiftY(tabIndex, isSelected);
	}

	/**
	 * Returns the amount (in pixels) by which two runs should overlap.
	 */
	@Override
	protected int getTabRunOverlay(int tabPlacement)
	{
		return renderer.getTabRunOverlay(tabRunOverlay);
	}

	/**
	 * This boolean controls wheather the given run should be padded to
	 * use up as much space as the others (with more tabs in them).
	 */
	@Override
	protected boolean shouldPadTabRun(int tabPlacement, int run)
	{
		return renderer.shouldPadTabRun(run, super.shouldPadTabRun(tabPlacement, run));
	}

	/**
	 * Returns the amount by which the run number <code>run</code> should be indented. Add six pixels for every run to make
	 * diagonal lines align.
	 */
	@Override
	protected int getTabRunIndent(int tabPlacement, int run)
	{
		return renderer.getTabRunIndent(run);
	}

	/**
	 * Returns the insets for this tab.
	 */
	@Override
	protected Insets getTabInsets(int tabPlacement, int tabIndex)
	{
		Insets insets = renderer.getTabInsets(tabIndex, tabInsets);
		
		//
		// adempiere hierarchical tab
		if (tabPlacement == LEFT)
		{
			final int tabLevelIndent = getTabLevelIndent(tabIndex, tabPlacement);
			insets.left += tabLevelIndent;
		}
		return insets;
	}

	/**
	 * Returns the insets for selected tab.
	 */
	@Override
	protected Insets getSelectedTabPadInsets(int tabPlacement)
	{
		return renderer.getSelectedTabPadInsets();
	}

	/**
	 * Draws the rectancle around the Tab label which indicates keyboard focus.
	 */
	@Override
	protected void paintFocusIndicator(
			Graphics g,
			int tabPlacement,
			Rectangle[] rectangles,
			int tabIndex,
			Rectangle iconRect,
			Rectangle textRect,
			boolean isSelected)
	{
		renderer.paintFocusIndicator(g, rectangles, tabIndex, iconRect, textRect, isSelected);
	}

	/**
	 * Fills the background of the given tab to make sure overlap of
	 * tabs is handled correctly.
	 * Note: that tab backgrounds seem to be painted somewhere else, too.
	 */
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		renderer.paintTabBackground(g, tabIndex, x, y, w, h, isSelected);
	}

	/**
	 * Paints the border for one tab. Gets the bounds of the tab as parameters.
	 * Note that the result is not clipped so you can paint outside that
	 * rectangle. Tabs painted later on have a chance to overwrite though.
	 */
	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
		renderer.paintTabBorder(g, tabIndex, x, y, w, h, isSelected);
	}

	/**
	 * Answers wheather tab runs should be rotated. If true, the layout mechanism
	 * will move the run containing the selected tab so that it touches
	 * the content pane.
	 */
	@Override
	protected boolean shouldRotateTabRuns(int tabPlacement)
	{
		return false;
	}

	/**
	 * @param tabIndex
	 * @param tabPlacement
	 * @return indentation that was to be added based on tab level.
	 */
	protected int getTabLevelIndent(final int tabIndex, final int tabPlacement)
	{
		// Applies only to LEFT or RIGHT tab placement
		if (tabPlacement != LEFT && tabPlacement != RIGHT)
		{
			return 0;
		}
		
		int tabLevel = 0;
		final Component comp = tabPane.getComponentAt(tabIndex);
		if (comp instanceof JComponent)
		{
			final JComponent jc = (JComponent)comp;
			try
			{
				final Integer levelObj = (Integer)jc.getClientProperty(AdempiereLookAndFeel.TABLEVEL);
				if (levelObj != null)
					tabLevel = levelObj.intValue();
			}
			catch (Exception e)
			{
				System.err.println("AdempiereTabbedPaneUI - ClientProperty: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		if (tabLevel != 0)
		{
			return tabLevel * 10;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Adjust rectangle's X and width by tab level's insets.
	 * 
	 * @param rect
	 * @param tabIndex
	 * @param tabPlacement
	 */
	protected void adjustRectangleByTabLevelIndent(final Rectangle rect, final int tabIndex, final int tabPlacement)
	{
		final int tabLevelIndent = getTabLevelIndent(tabIndex, tabPlacement);
		if (tabPlacement == LEFT)
		{
			rect.x += tabLevelIndent;
		}
		rect.width -= tabLevelIndent;
	}
	
	protected static boolean isAlignVerticalTabsWithHorizontalTabs(final JTabbedPane tabbedPane, final int tabPlacement)
	{
		if (tabPlacement != LEFT)
		{
			return false;
		}
		final Boolean enabled = (Boolean)tabbedPane.getClientProperty(KEY_AlignVerticalTabsWithHorizontalTabs_Enabled);
		if (enabled == null)
		{
			return false; // default: do not align
		}
		return enabled;
	}
	
	public static int getGapBeforeFirstVerticalTab()
	{
		return AdempierePLAF.getInt(KEY_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab, DEFAULT_AlignVerticalTabsWithHorizontalTabs_GapBeforeFirstTab);
	}

	private class TabSelectionHandler implements ChangeListener
	{

		private Rectangle rect = new Rectangle();

		@Override
		public void stateChanged(ChangeEvent e)
		{
			JTabbedPane tabPane = (JTabbedPane)e.getSource();
			tabPane.revalidate();
			tabPane.repaint();

			if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT)
			{
				int index = tabPane.getSelectedIndex();
				if (index < rects.length && index != -1)
				{
					rect.setBounds(rects[index]);
					Point viewPosition = tabScroller.viewport.getViewPosition();
					if (rect.x < viewPosition.x)
					{
						rect.x -= renderer.getTabsOverlay();
					}
					else
					{
						rect.x += renderer.getTabsOverlay();
					}
					tabScroller.tabPanel.scrollRectToVisible(rect);
				}
			}
		}
	}

	/**
	 * Catches and handles property change events. In addition to the super
	 * class behavior we listen to changes of the ancestor, tab placement,
	 * and JGoodies options for content border, and embedded tabs.
	 */
	private class TabbedPanePropertyChangeHandler extends BasicTabbedPaneUI.PropertyChangeHandler
	{
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			final String propertyName = e.getPropertyName();

			if (null == propertyName)
			{
				return;
			}

			super.propertyChange(e);

			if (propertyName.equals("tabPlacement"))
			{
				updateRendererAndDoLayout();
				return;
			}
			else if (propertyName.equals(Options.EMBEDDED_TABS_KEY))
			{
				embeddedTabsPropertyChanged((Boolean)e.getNewValue());
				return;
			}
			else if (propertyName.equals(Options.NO_CONTENT_BORDER_KEY))
			{
				noContentBorderPropertyChanged((Boolean)e.getNewValue());
				return;
			}
			else if (propertyName.equals(AdempiereLookAndFeel.HIDE_IF_ONE_TAB))
			{
				hideIfOneTab = (Boolean)e.getNewValue();
			}
			else if (propertyName.equals(KEY_AlignVerticalTabsWithHorizontalTabs_Enabled))
			{
				updateRendererAndDoLayout();
			}
		}
	}

	/**
	 * Does all the layout work. The result is stored in the container
	 * class's instance variables. Mainly the rects[] vector.
	 */
	private class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout implements LayoutManager
	{

		@Override
		protected void calculateTabRects(int tabPlacement, int tabCount)
		{
			final FontMetrics metrics = getFontMetrics();
			final Dimension size = tabPane.getSize();
			final Insets insets = tabPane.getInsets();
			final Insets theTabAreaInsets = getTabAreaInsets(tabPlacement);
			final int fontHeight = metrics.getHeight();
			final int selectedIndex = tabPane.getSelectedIndex();
			final boolean verticalTabRuns = (tabPlacement == LEFT || tabPlacement == RIGHT);
			final boolean leftToRight = tabPane.getComponentOrientation().isLeftToRight();

			//
			// Calculate bounds within which a tab run must fit
			int tabX;
			int tabY;
			final int returnAt;
			switch (tabPlacement)
			{
				case LEFT:
					maxTabWidth = calculateMaxTabWidth(tabPlacement);
					tabX = insets.left + theTabAreaInsets.left;
					tabY = insets.top + theTabAreaInsets.top;
					returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
					break;
				case RIGHT:
					maxTabWidth = calculateMaxTabWidth(tabPlacement);
					tabX = size.width - insets.right - theTabAreaInsets.right - maxTabWidth;
					tabY = insets.top + theTabAreaInsets.top;
					returnAt = size.height - (insets.bottom + theTabAreaInsets.bottom);
					break;
				case BOTTOM:
					maxTabHeight = calculateMaxTabHeight(tabPlacement);
					tabX = insets.left + theTabAreaInsets.left;
					tabY = size.height - insets.bottom - theTabAreaInsets.bottom - maxTabHeight;
					returnAt = size.width - (insets.right + theTabAreaInsets.right);
					break;
				case TOP:
				default:
					maxTabHeight = calculateMaxTabHeight(tabPlacement);
					tabX = insets.left + theTabAreaInsets.left;
					tabY = insets.top + theTabAreaInsets.top;
					returnAt = size.width - (insets.right + theTabAreaInsets.right);
					break;
			}
			
			final int theTabRunOverlay = getTabRunOverlay(tabPlacement);
			runCount = 0;
			selectedRun = -1;
			// make a copy of returnAt for the current run and modify
			// that so returnAt may still be used later on
			int runReturnAt = returnAt;

			if (tabCount == 0)
			{
				return;
			}
			if (tabCount == 1 && isHideIfOneTab())
			{
				rects[0].height = 0;
				rects[0].width = 0;
				rects[0].x = 0;
				rects[0].y = 0;
				return;
			}

			// Keeps track of where we are in the current run.
			// This helps not to rely on fragile positioning.
			// Information to find out whether the active Tab is the first in run.
			int tabInRun = -1;

			//
			// Run through tabs and partition them into runs
			for (int tabIndex = 0; tabIndex < tabCount; tabIndex++)
			{
				final Rectangle rect = rects[tabIndex];
				tabInRun++;

				//
				// Tabs on TOP or BOTTOM....
				if (!verticalTabRuns)
				{
					if (tabIndex > 0)
					{
						rect.x = rects[tabIndex - 1].x + rects[tabIndex - 1].width;
					}
					else
					{
						tabRuns[0] = 0;
						runCount = 1;
						maxTabWidth = 0;
						rect.x = tabX;
						// tabInRun = 0;
					}
					rect.width = calculateTabWidth(tabPlacement, tabIndex, metrics);
					maxTabWidth = Math.max(maxTabWidth, rect.width);

					// Never move a TAB down a run if it is the first in run.
					// Even if there isn't enough room, moving it to a fresh
					// line won't help.
					// if (rect.x != 2 + insets.left && rect.x + rect.width > returnAt) {
					// Never rely on phisical position information to determine
					// logical position (if you can avoid it)
					if (tabInRun != 0 && rect.x + rect.width > runReturnAt)
					{
						if (runCount > tabRuns.length - 1)
						{
							expandTabRunsArray();
						}
						// just created a new run, adjust some counters
						tabInRun = 0;
						tabRuns[runCount] = tabIndex;
						runCount++;
						rect.x = tabX;
						runReturnAt = runReturnAt - 2 * getTabRunIndent(tabPlacement, runCount);
					}
					// Initialize y position in case there's just one run
					rect.y = tabY;
					rect.height = maxTabHeight /* - 2 */;

				}
				//
				// Tabs on LEFT or RIGHT...
				else
				{
					if (tabIndex > 0)
					{
						rect.y = rects[tabIndex - 1].y + rects[tabIndex - 1].height;
					}
					else
					{
						tabRuns[0] = 0;
						runCount = 1;
						maxTabHeight = 0;
						rect.y = tabY;
						// tabInRun = 0;
					}
					rect.height = calculateTabHeight(tabPlacement, tabIndex, fontHeight);
					maxTabHeight = Math.max(maxTabHeight, rect.height);

					// Never move a TAB over a run if it is the first in run.
					// Even if there isn't enough room, moving it to a fresh run won't help.
					// if (rect.y != 2 + insets.top && rect.y + rect.height > returnAt) {
					if (tabInRun != 0 && rect.y + rect.height > runReturnAt)
					{
						if (runCount > tabRuns.length - 1)
						{
							expandTabRunsArray();
						}
						tabRuns[runCount] = tabIndex;
						runCount++;
						rect.y = tabY;
						tabInRun = 0;
						runReturnAt -= 2 * getTabRunIndent(tabPlacement, runCount);
					}
					// Initialize x position in case there's just one column
					rect.x = tabX;
					rect.width = maxTabWidth /* - 2 */;

				}

				//
				// adempiere hierarchical tab
				adjustRectangleByTabLevelIndent(rect, tabIndex, tabPlacement);

				if (tabIndex == selectedIndex)
				{
					selectedRun = runCount - 1;
				}
			}

			if (runCount > 1)
			{
				// Re-distribute tabs in case last run has leftover space
				// last line flush left is OK
				// normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns? y : x, returnAt);
				// don't need to recalculate selectedRun if not changed
				// selectedRun = getRunForTab(tabCount, selectedIndex);

				// Rotate run array so that selected run is first
				if (shouldRotateTabRuns(tabPlacement))
				{
					rotateTabRuns(tabPlacement, selectedRun);
				}
			}

			// Step through runs from back to front to calculate
			// tab y locations and to pad runs appropriately
			for (int i = runCount - 1; i >= 0; i--)
			{
				int start = tabRuns[i];
				int next = tabRuns[i == (runCount - 1) ? 0 : i + 1];
				int end = (next != 0 ? next - 1 : tabCount - 1);
				int indent = getTabRunIndent(tabPlacement, i);
				
				//
				// Tabs on TOP or BOTTOM....
				if (!verticalTabRuns)
				{
					for (int j = start; j <= end; j++)
					{
						final Rectangle rect = rects[j];
						rect.y = tabY;
						rect.x += indent;
						// try to make tabRunIndent symmetric
						// rect.width -= 2* indent + 20;
					}
					if (shouldPadTabRun(tabPlacement, i))
					{
						padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
					}
					if (tabPlacement == BOTTOM)
					{
						tabY -= (maxTabHeight - theTabRunOverlay);
					}
					else
					{
						tabY += (maxTabHeight - theTabRunOverlay);
					}
				}
				//
				// Tabs on LEFT or RIGHT....
				else
				{
					for (int j = start; j <= end; j++)
					{
						final Rectangle rect = rects[j];
						rect.x = tabX;
						rect.y += indent;
					}
					if (shouldPadTabRun(tabPlacement, i))
					{
						padTabRun(tabPlacement, start, end, returnAt - 2 * indent);
					}
					if (tabPlacement == RIGHT)
					{
						tabX -= (maxTabWidth - theTabRunOverlay);
					}
					else
					{
						tabX += (maxTabWidth - theTabRunOverlay);
					}
				}
			}

			// Pad the selected tab so that it appears raised in front
			padSelectedTab(tabPlacement, selectedIndex);

			// if right to left and tab placement on the top or
			// the bottom, flip x positions and adjust by widths
			if (!leftToRight && !verticalTabRuns)
			{
				int rightMargin = size.width - (insets.right + theTabAreaInsets.right);
				for (int i = 0; i < tabCount; i++)
				{
					rects[i].x = rightMargin - rects[i].x - rects[i].width + renderer.getTabsOverlay();
				}
			}
		}

		/**
		 * Overridden to insure the same behavior in JDK 6.0 as in JDK 5.0.
		 */
		@Override
		protected void padSelectedTab(int tabPlacement, int selectedIndex)
		{
			if (selectedIndex >= 0)
			{
				Rectangle selRect = rects[selectedIndex];
				Insets padInsets = getSelectedTabPadInsets(tabPlacement);
				selRect.x -= padInsets.left;
				selRect.width += (padInsets.left + padInsets.right);
				selRect.y -= padInsets.top;
				selRect.height += (padInsets.top + padInsets.bottom);
			}
		}

	}

	// FIXME: this is not overriding the BasicTabbedPaneUI.requestFocusForVisibleComponent which is package level
	boolean requestFocusForVisibleComponent()
	{
		Component visibleComponent = getVisibleComponent();
		if (visibleComponent.isFocusable())
		{
			visibleComponent.requestFocus();
			return true;
		}
		if (visibleComponent instanceof JComponent)
		{
			if (((JComponent)visibleComponent).requestDefaultFocus())
			{
				return true;
			}
		}
		return false;
	}

	private static class ScrollTabsForwardAction extends AbstractAction
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -270810699887568982L;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			JTabbedPane pane = null;
			Object src = e.getSource();
			if (src instanceof JTabbedPane)
			{
				pane = (JTabbedPane)src;
			}
			else if (src != null
					&& src.getClass().getName().equals("com.jgoodies.looks.plastic.com.jgoodies.looks.plastic"))
			{
				pane = (JTabbedPane)((Component)src).getParent();
			}
			else
			{
				return; // shouldn't happen
			}
			AdempiereTabbedPaneUI ui = (AdempiereTabbedPaneUI)pane.getUI();

			if (ui.scrollableTabLayoutEnabled())
			{
				ui.tabScroller.scrollForward(pane.getTabPlacement());
			}
		}
	}

	private static class ScrollTabsBackwardAction extends AbstractAction
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3249390793846061809L;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			JTabbedPane pane = null;
			Object src = e.getSource();
			if (src instanceof JTabbedPane)
			{
				pane = (JTabbedPane)src;
			}
			else if (src != null
					&& src.getClass().getName().equals("com.jgoodies.looks.plastic.com.jgoodies.looks.plastic"))
			{
				pane = (JTabbedPane)((Component)src).getParent();
			}
			else
			{
				return; // shouldn't happen
			}
			
			final AdempiereTabbedPaneUI ui = (AdempiereTabbedPaneUI)pane.getUI();

			if (ui.scrollableTabLayoutEnabled())
			{
				ui.tabScroller.scrollBackward(pane.getTabPlacement());
			}
		}
	}

	private class TabbedPaneScrollLayout extends TabbedPaneLayout
	{

		@Override
		protected int preferredTabAreaHeight(int tabPlacement, int width)
		{
			return calculateMaxTabHeight(tabPlacement);
		}

		@Override
		protected int preferredTabAreaWidth(int tabPlacement, int height)
		{
			return calculateMaxTabWidth(tabPlacement);
		}

		@Override
		public void layoutContainer(Container parent)
		{
			int tabPlacement = tabPane.getTabPlacement();
			int tabCount = tabPane.getTabCount();
			Insets insets = tabPane.getInsets();
			int selectedIndex = tabPane.getSelectedIndex();
			Component visibleComponent = getVisibleComponent();

			calculateLayoutInfo();

			if (selectedIndex < 0)
			{
				if (visibleComponent != null)
				{
					// The last tab was removed, so remove the component
					setVisibleComponent(null);
				}
			}
			else
			{
				Component selectedComponent = tabPane.getComponentAt(selectedIndex);
				boolean shouldChangeFocus = false;

				// In order to allow programs to use a single component
				// as the display for multiple tabs, we will not change
				// the visible component if the currently selected tab
				// has a null component. This is a bit dicey, as we don't
				// explicitly state we support this in the spec, but since
				// programs are now depending on this, we're making it work.
				//
				if (selectedComponent != null)
				{
					if (selectedComponent != visibleComponent &&
							visibleComponent != null)
					{
						if (SwingUtilities.findFocusOwner(visibleComponent) != null)
						{
							shouldChangeFocus = true;
						}
					}
					setVisibleComponent(selectedComponent);
				}
				int tx, ty, tw, th; // tab area bounds
				int cx, cy, cw, ch; // content area bounds
				Insets contentInsets = getContentBorderInsets(tabPlacement);
				Rectangle bounds = tabPane.getBounds();
				int numChildren = tabPane.getComponentCount();

				if (numChildren > 0)
				{
					switch (tabPlacement)
					{
						case LEFT:
							// calculate tab area bounds
							tw = calculateTabAreaWidth(tabPlacement, runCount,
									maxTabWidth);
							th = bounds.height - insets.top - insets.bottom;
							tx = insets.left;
							ty = insets.top;

							// calculate content area bounds
							cx = tx + tw + contentInsets.left;
							cy = ty + contentInsets.top;
							cw = bounds.width - insets.left - insets.right - tw
									- contentInsets.left - contentInsets.right;
							ch = bounds.height - insets.top - insets.bottom
									- contentInsets.top - contentInsets.bottom;
							break;
						case RIGHT:
							// calculate tab area bounds
							tw = calculateTabAreaWidth(tabPlacement, runCount,
									maxTabWidth);
							th = bounds.height - insets.top - insets.bottom;
							tx = bounds.width - insets.right - tw;
							ty = insets.top;

							// calculate content area bounds
							cx = insets.left + contentInsets.left;
							cy = insets.top + contentInsets.top;
							cw = bounds.width - insets.left - insets.right - tw
									- contentInsets.left - contentInsets.right;
							ch = bounds.height - insets.top - insets.bottom
									- contentInsets.top - contentInsets.bottom;
							break;
						case BOTTOM:
							// calculate tab area bounds
							tw = bounds.width - insets.left - insets.right;
							th = calculateTabAreaHeight(tabPlacement, runCount,
									maxTabHeight);
							tx = insets.left;
							ty = bounds.height - insets.bottom - th;

							// calculate content area bounds
							cx = insets.left + contentInsets.left;
							cy = insets.top + contentInsets.top;
							cw = bounds.width - insets.left - insets.right
									- contentInsets.left - contentInsets.right;
							ch = bounds.height - insets.top - insets.bottom - th
									- contentInsets.top - contentInsets.bottom;
							break;
						case TOP:
						default:
							// calculate tab area bounds
							tw = bounds.width - insets.left - insets.right;
							th = calculateTabAreaHeight(tabPlacement, runCount,
									maxTabHeight);
							tx = insets.left;
							ty = insets.top;

							// calculate content area bounds
							cx = tx + contentInsets.left;
							cy = ty + th + contentInsets.top;
							cw = bounds.width - insets.left - insets.right
									- contentInsets.left - contentInsets.right;
							ch = bounds.height - insets.top - insets.bottom - th
									- contentInsets.top - contentInsets.bottom;
					}

					for (int i = 0; i < numChildren; i++)
					{
						Component child = tabPane.getComponent(i);

						if (tabScroller != null && child == tabScroller.viewport)
						{
							JViewport viewport = (JViewport)child;
							Rectangle viewRect = viewport.getViewRect();
							int vw = tw;
							int vh = th;
							Dimension butSize = tabScroller.scrollForwardButton.getPreferredSize();
							switch (tabPlacement)
							{
								case LEFT:
								case RIGHT:
									int totalTabHeight = rects[tabCount - 1].y
											+ rects[tabCount - 1].height;
									if (totalTabHeight > th)
									{
										// Allow space for scrollbuttons
										vh = (th > 2 * butSize.height) ? th - 2
												* butSize.height : 0;
										if (totalTabHeight - viewRect.y <= vh)
										{
											// Scrolled to the end, so ensure the
											// viewport size is
											// such that the scroll offset aligns
											// with a tab
											vh = totalTabHeight - viewRect.y;
										}
									}
									break;
								case BOTTOM:
								case TOP:
								default:
									int totalTabWidth = rects[tabCount - 1].x
											+ rects[tabCount - 1].width + renderer.getTabsOverlay();
									if (totalTabWidth > tw)
									{
										// Need to allow space for scrollbuttons
										vw = (tw > 2 * butSize.width) ? tw - 2
												* butSize.width : 0;
										if (totalTabWidth - viewRect.x <= vw)
										{
											// Scrolled to the end, so ensure the
											// viewport size is
											// such that the scroll offset aligns
											// with a tab
											vw = totalTabWidth - viewRect.x;
										}
									}
							}
							child.setBounds(tx, ty, vw, vh);

						}
						else if (tabScroller != null &&
								(child == tabScroller.scrollForwardButton ||
								child == tabScroller.scrollBackwardButton))
						{
							Component scrollbutton = child;
							Dimension bsize = scrollbutton.getPreferredSize();
							int bx = 0;
							int by = 0;
							int bw = bsize.width;
							int bh = bsize.height;
							boolean visible = false;

							switch (tabPlacement)
							{
								case LEFT:
								case RIGHT:
									int totalTabHeight = rects[tabCount - 1].y
											+ rects[tabCount - 1].height;
									if (totalTabHeight > th)
									{
										visible = true;
										bx = (tabPlacement == LEFT ? tx + tw
												- bsize.width : tx);
										by = (child == tabScroller.scrollForwardButton) ? bounds.height
												- insets.bottom - bsize.height
												: bounds.height - insets.bottom - 2
														* bsize.height;
									}
									break;

								case BOTTOM:
								case TOP:
								default:
									int totalTabWidth = rects[tabCount - 1].x
											+ rects[tabCount - 1].width;

									if (totalTabWidth > tw)
									{
										visible = true;
										bx = (child == tabScroller.scrollForwardButton) ? bounds.width
												- insets.left - bsize.width
												: bounds.width - insets.left - 2
														* bsize.width;
										by = (tabPlacement == TOP ? ty + th
												- bsize.height : ty);
									}
							}
							child.setVisible(visible);
							if (visible)
							{
								child.setBounds(bx, by, bw, bh);
							}

						}
						else
						{
							// All content children...
							child.setBounds(cx, cy, cw, ch);
						}
					}
					if (shouldChangeFocus)
					{
						if (!requestFocusForVisibleComponent())
						{
							tabPane.requestFocus();
						}
					}
				}
			}
		}

		@Override
		protected void calculateTabRects(int tabPlacement, int tabCount)
		{
			final FontMetrics metrics = getFontMetrics();
			final Dimension size = tabPane.getSize();
			final Insets insets = tabPane.getInsets();
			final Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			final int fontHeight = metrics.getHeight();
			final int selectedIndex = tabPane.getSelectedIndex();
			final boolean verticalTabRuns = (tabPlacement == LEFT || tabPlacement == RIGHT);
			final boolean leftToRight = tabPane.getComponentOrientation().isLeftToRight();
			final int tabX = tabAreaInsets.left;
			int tabY = tabAreaInsets.top;
			if (isAlignVerticalTabsWithHorizontalTabs(tabPane, tabPlacement))
			{
				tabY = getGapBeforeFirstVerticalTab();
			}
			int totalWidth = 0;
			int totalHeight = 0;

			//
			// Calculate bounds within which a tab run must fit
			//
			switch (tabPlacement)
			{
				case LEFT:
				case RIGHT:
					maxTabWidth = calculateMaxTabWidth(tabPlacement);
					break;
				case BOTTOM:
				case TOP:
				default:
					maxTabHeight = calculateMaxTabHeight(tabPlacement);
			}

			runCount = 0;
			selectedRun = -1;

			if (tabCount == 0)
			{
				return;
			}

			if (tabCount == 1 && isHideIfOneTab())
			{
				rects[0].height = 0;
				rects[0].width = 0;
				rects[0].x = 0;
				rects[0].y = 0;
				return;
			}

			selectedRun = 0;
			runCount = 1;

			// Run through tabs and lay them out in a single run
			for (int tabIndex = 0; tabIndex < tabCount; tabIndex++)
			{
				final Rectangle rect = rects[tabIndex];

				//
				// Tabs on TOP or BOTTOM....
				if (!verticalTabRuns)
				{
					if (tabIndex > 0)
					{
						rect.x = rects[tabIndex - 1].x + rects[tabIndex - 1].width;
					}
					else
					{
						tabRuns[0] = 0;
						maxTabWidth = 0;
						totalHeight += maxTabHeight;
						rect.x = tabX;
					}
					rect.width = calculateTabWidth(tabPlacement, tabIndex, metrics);
					totalWidth = rect.x + rect.width + renderer.getTabsOverlay();
					maxTabWidth = Math.max(maxTabWidth, rect.width);

					rect.y = tabY;
					rect.height = maxTabHeight/* - 2 */;

				}
				//
				// Tabs on LEFT or RIGHT...
				else
				{
					if (tabIndex > 0)
					{
						rect.y = rects[tabIndex - 1].y + rects[tabIndex - 1].height;
					}
					else
					{
						tabRuns[0] = 0;
						maxTabHeight = 0;
						totalWidth = maxTabWidth;
						rect.y = tabY;
					}
					rect.height = calculateTabHeight(tabPlacement, tabIndex, fontHeight);
					totalHeight = rect.y + rect.height;
					maxTabHeight = Math.max(maxTabHeight, rect.height);

					rect.x = tabX;
					rect.width = maxTabWidth/* - 2 */;

				}

				// adempiere hierarchical tab
				adjustRectangleByTabLevelIndent(rect, tabIndex, tabPlacement);
			}

			// Pad the selected tab so that it appears raised in front
			padSelectedTab(tabPlacement, selectedIndex);

			// if right to left and tab placement on the top or
			// the bottom, flip x positions and adjust by widths
			if (!leftToRight && !verticalTabRuns)
			{
				final int rightMargin = size.width - (insets.right + tabAreaInsets.right);
				for (int tabIndex = 0; tabIndex < tabCount; tabIndex++)
				{
					rects[tabIndex].x = rightMargin - rects[tabIndex].x - rects[tabIndex].width;
				}
			}
			tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
		}
	}

	private class ScrollableTabSupport implements ActionListener,
			ChangeListener
	{

		public ScrollableTabViewport viewport;
		public ScrollableTabPanel tabPanel;
		public JButton scrollForwardButton;
		public JButton scrollBackwardButton;
		public int leadingTabIndex;
		private Point tabViewPosition = new Point(0, 0);

		ScrollableTabSupport(int tabPlacement)
		{
			viewport = new ScrollableTabViewport();
			tabPanel = new ScrollableTabPanel();
			viewport.setView(tabPanel);
			viewport.addChangeListener(this);
			createButtons();
		}

		/**
		 * Recreates the scroll buttons and adds them to the TabbedPane.
		 */
		void createButtons()
		{
			if (scrollForwardButton != null)
			{
				tabPane.remove(scrollForwardButton);
				scrollForwardButton.removeActionListener(this);
				tabPane.remove(scrollBackwardButton);
				scrollBackwardButton.removeActionListener(this);
			}
			int tabPlacement = tabPane.getTabPlacement();
			int width = UIManager.getInt("ScrollBar.width");
			if (tabPlacement == TOP || tabPlacement == BOTTOM)
			{
				scrollForwardButton = new ArrowButton(EAST, width);
				scrollBackwardButton = new ArrowButton(WEST, width);
			}
			else
			{ // tabPlacement = LEFT || RIGHT
				scrollForwardButton = new ArrowButton(SOUTH, width);
				scrollBackwardButton = new ArrowButton(NORTH, width);
			}
			scrollForwardButton.addActionListener(this);
			scrollBackwardButton.addActionListener(this);
			tabPane.add(scrollForwardButton);
			tabPane.add(scrollBackwardButton);
		}

		public void scrollForward(int tabPlacement)
		{
			Dimension viewSize = viewport.getViewSize();
			Rectangle viewRect = viewport.getViewRect();

			if (tabPlacement == TOP || tabPlacement == BOTTOM)
			{
				if (viewRect.width >= viewSize.width - viewRect.x)
				{
					return; // no room left to scroll
				}
			}
			else
			{ // tabPlacement == LEFT || tabPlacement == RIGHT
				if (viewRect.height >= viewSize.height - viewRect.y)
				{
					return;
				}
			}
			setLeadingTabIndex(tabPlacement, leadingTabIndex + 1);
		}

		public void scrollBackward(int tabPlacement)
		{
			if (leadingTabIndex == 0)
			{
				return; // no room left to scroll
			}
			setLeadingTabIndex(tabPlacement, leadingTabIndex - 1);
		}

		public void setLeadingTabIndex(int tabPlacement, int index)
		{
			leadingTabIndex = index;
			Dimension viewSize = viewport.getViewSize();
			Rectangle viewRect = viewport.getViewRect();

			switch (tabPlacement)
			{
				case TOP:
				case BOTTOM:
					tabViewPosition.x = leadingTabIndex == 0 ? 0
							: rects[leadingTabIndex].x - renderer.getTabsOverlay();

					if ((viewSize.width - tabViewPosition.x) < viewRect.width)
					{
						// We've scrolled to the end, so adjust the viewport size
						// to ensure the view position remains aligned on a tab
						// boundary
						Dimension extentSize = new Dimension(viewSize.width
								- tabViewPosition.x, viewRect.height);
						viewport.setExtentSize(extentSize);
					}
					break;
				case LEFT:
				case RIGHT:
					tabViewPosition.y = leadingTabIndex == 0 ? 0
							: rects[leadingTabIndex].y;

					if ((viewSize.height - tabViewPosition.y) < viewRect.height)
					{
						// We've scrolled to the end, so adjust the viewport size
						// to ensure the view position remains aligned on a tab
						// boundary
						Dimension extentSize = new Dimension(viewRect.width,
								viewSize.height - tabViewPosition.y);
						viewport.setExtentSize(extentSize);
					}
			}
			viewport.setViewPosition(tabViewPosition);
		}

		@Override
		public void stateChanged(ChangeEvent e)
		{
			JViewport viewport = (JViewport)e.getSource();
			int tabPlacement = tabPane.getTabPlacement();
			int tabCount = tabPane.getTabCount();
			Rectangle vpRect = viewport.getBounds();
			Dimension viewSize = viewport.getViewSize();
			Rectangle viewRect = viewport.getViewRect();

			leadingTabIndex = getClosestTab(viewRect.x, viewRect.y);

			// If the tab isn't right aligned, adjust it.
			if (leadingTabIndex + 1 < tabCount)
			{
				switch (tabPlacement)
				{
					case TOP:
					case BOTTOM:
						if (rects[leadingTabIndex].x < viewRect.x)
						{
							leadingTabIndex++;
						}
						break;
					case LEFT:
					case RIGHT:
						if (rects[leadingTabIndex].y < viewRect.y)
						{
							leadingTabIndex++;
						}
						break;
				}
			}
			Insets contentInsets = getContentBorderInsets(tabPlacement);
			switch (tabPlacement)
			{
				case LEFT:
					tabPane.repaint(vpRect.x + vpRect.width, vpRect.y,
							contentInsets.left, vpRect.height);
					scrollBackwardButton.setEnabled(viewRect.y > 0
							&& leadingTabIndex > 0);
					scrollForwardButton.setEnabled(leadingTabIndex < tabCount - 1
							&& viewSize.height - viewRect.y > viewRect.height);
					break;
				case RIGHT:
					tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y,
							contentInsets.right, vpRect.height);
					scrollBackwardButton.setEnabled(viewRect.y > 0
							&& leadingTabIndex > 0);
					scrollForwardButton.setEnabled(leadingTabIndex < tabCount - 1
							&& viewSize.height - viewRect.y > viewRect.height);
					break;
				case BOTTOM:
					tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom,
							vpRect.width, contentInsets.bottom);
					scrollBackwardButton.setEnabled(viewRect.x > 0
							&& leadingTabIndex > 0);
					scrollForwardButton.setEnabled(leadingTabIndex < tabCount - 1
							&& viewSize.width - viewRect.x > viewRect.width);
					break;
				case TOP:
				default:
					tabPane.repaint(vpRect.x, vpRect.y + vpRect.height,
							vpRect.width, contentInsets.top);
					scrollBackwardButton.setEnabled(viewRect.x > 0
							&& leadingTabIndex > 0);
					scrollForwardButton.setEnabled(leadingTabIndex < tabCount - 1
							&& viewSize.width - viewRect.x > viewRect.width);
			}
		}

		/**
		 * ActionListener for the scroll buttons.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ActionMap map = tabPane.getActionMap();

			if (map != null)
			{
				String actionKey;

				if (e.getSource() == scrollForwardButton)
				{
					actionKey = "scrollTabsForwardAction";
				}
				else
				{
					actionKey = "scrollTabsBackwardAction";
				}
				Action action = map.get(actionKey);

				if (action != null && action.isEnabled())
				{
					action.actionPerformed(new ActionEvent(tabPane,
							ActionEvent.ACTION_PERFORMED, null, e.getWhen(), e
									.getModifiers()));
				}
			}
		}

	}

	private class ScrollableTabViewport extends JViewport implements UIResource
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 327251688467352279L;

		public ScrollableTabViewport()
		{
			super();
			setName("TabbedPane.scrollableViewport");
			setScrollMode(SIMPLE_SCROLL_MODE);
			setOpaque(tabPane.isOpaque());
			Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
			if (bgColor == null)
			{
				bgColor = tabPane.getBackground();
			}
			setBackground(bgColor);
		}
	}

	private class ScrollableTabPanel extends JPanel implements UIResource
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7751954262914422261L;

		public ScrollableTabPanel()
		{
			super(null);
			setOpaque(tabPane.isOpaque());
			Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
			if (bgColor == null)
			{
				bgColor = tabPane.getBackground();
			}
			setBackground(bgColor);
		}

		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			AdempiereTabbedPaneUI.this.paintTabArea(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex());
		}
	}

	private static class ArrowButton extends JButton implements UIResource
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2076478876425671827L;
		private final int buttonWidth;
		private final int direction;
		private boolean mouseIsOver;

		ArrowButton(int direction, int buttonWidth)
		{
			this.direction = direction;
			this.buttonWidth = buttonWidth;
			setRequestFocusEnabled(false);
		}

		@Override
		protected void processMouseEvent(MouseEvent e)
		{
			super.processMouseEvent(e);
			switch (e.getID())
			{
				case MouseEvent.MOUSE_ENTERED:
					mouseIsOver = true;
					revalidate();
					repaint();
					break;
				case MouseEvent.MOUSE_EXITED:
					mouseIsOver = false;
					revalidate();
					repaint();
					break;
			}
		}

		@Override
		protected void paintBorder(Graphics g)
		{
			if (mouseIsOver && isEnabled())
			{
				super.paintBorder(g);
			}
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			if (mouseIsOver)
			{
				super.paintComponent(g);
			}
			else
			{
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			paintArrow(g);
		}

		private void paintArrow(Graphics g)
		{
			Color oldColor = g.getColor();

			boolean isEnabled = isEnabled();
			g.setColor(isEnabled ? PlasticLookAndFeel.getControlInfo()
					: PlasticLookAndFeel.getControlDisabled());

			int arrowWidth, arrowHeight;
			switch (direction)
			{
				case NORTH:
				case SOUTH:
					arrowWidth = 9;
					arrowHeight = 5;
					break;
				case WEST:
				case EAST:
				default:
					arrowWidth = 5;
					arrowHeight = 9;
					break;
			}
			int x = (getWidth() - arrowWidth) / 2;
			int y = (getHeight() - arrowHeight) / 2;
			g.translate(x, y);

			boolean paintShadow = !mouseIsOver || !isEnabled;
			Color shadow = isEnabled ? PlasticLookAndFeel.getControlShadow()
					: UIManager.getColor("ScrollBar.highlight");

			switch (direction)
			{
				case NORTH:
					g.fillRect(0, 4, 9, 1);
					g.fillRect(1, 3, 7, 1);
					g.fillRect(2, 2, 5, 1);
					g.fillRect(3, 1, 3, 1);
					g.fillRect(4, 0, 1, 1);
					if (paintShadow)
					{
						g.setColor(shadow);
						g.fillRect(1, 5, 9, 1);
					}
					break;
				case SOUTH:
					g.fillRect(0, 0, 9, 1);
					g.fillRect(1, 1, 7, 1);
					g.fillRect(2, 2, 5, 1);
					g.fillRect(3, 3, 3, 1);
					g.fillRect(4, 4, 1, 1);
					if (paintShadow)
					{
						g.setColor(shadow);
						g.drawLine(5, 4, 8, 1);
						g.drawLine(5, 5, 9, 1);
					}
					break;
				case WEST:
					g.fillRect(0, 4, 1, 1);
					g.fillRect(1, 3, 1, 3);
					g.fillRect(2, 2, 1, 5);
					g.fillRect(3, 1, 1, 7);
					g.fillRect(4, 0, 1, 9);
					if (paintShadow)
					{
						g.setColor(shadow);
						g.fillRect(5, 1, 1, 9);
					}
					break;
				case EAST:
					g.fillRect(0, 0, 1, 9);
					g.fillRect(1, 1, 1, 7);
					g.fillRect(2, 2, 1, 5);
					g.fillRect(3, 3, 1, 3);
					g.fillRect(4, 4, 1, 1);
					if (paintShadow)
					{
						g.setColor(shadow);
						g.drawLine(1, 8, 4, 5);
						g.drawLine(1, 9, 5, 5);
					}
					break;
			}

			g.translate(-x, -y);
			g.setColor(oldColor);
		}

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(buttonWidth, buttonWidth);
		}

		@Override
		public Dimension getMinimumSize()
		{
			return getPreferredSize();
		}

		@Override
		public Dimension getMaximumSize()
		{
			return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
		}
	}

	/**
	 * This is the abstract superclass for all TabbedPane renderers.
	 * Those will be defined in the rest of this file
	 */
	private abstract static class AbstractRenderer
	{

		protected static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
		protected static final Insets NORTH_INSETS = new Insets(1, 0, 0, 0);
		protected static final Insets WEST_INSETS = new Insets(0, 1, 0, 0);
		protected static final Insets SOUTH_INSETS = new Insets(0, 0, 1, 0);
		protected static final Insets EAST_INSETS = new Insets(0, 0, 0, 1);

		protected final JTabbedPane tabPane;
		@SuppressWarnings("unused")
		protected final int tabPlacement;
		protected final Color shadowColor;
		protected final Color darkShadow;
		protected final Color selectColor;
		protected final Color unselectColor;
		protected final Color selectHighlight;
//		protected final Color lightHighlight;
		protected final Color focus;

		private AbstractRenderer(JTabbedPane tabPane)
		{
			super();
			
			this.tabPane = tabPane;
			this.tabPlacement = tabPane.getTabPlacement();
			
			shadowColor = UIManager.getColor("TabbedPane.shadow");
			darkShadow = UIManager.getColor("TabbedPane.darkShadow");
			selectColor = UIManager.getColor("TabbedPane.selected");
			focus = UIManager.getColor("TabbedPane.focus");
			selectHighlight = UIManager.getColor("TabbedPane.selectHighlight");
			// lightHighlight = UIManager.getColor("TabbedPane.highlight");
			
			Color unselectColor = UIManager.getColor("TabbedPane.unselectedBackground");
			if (unselectColor == null)
			{
				unselectColor = new Color(
								(2 * selectColor.getRed() + selectHighlight.getRed()) / 3,
								(2 * selectColor.getGreen() + selectHighlight.getGreen()) / 3,
								(2 * selectColor.getBlue() + selectHighlight.getBlue()) / 3);
			}
			this.unselectColor = unselectColor;
		}

		private static AbstractRenderer createRenderer(final JTabbedPane tabPane)
		{
			switch (tabPane.getTabPlacement())
			{
				case SwingConstants.TOP:
					return new TopRenderer(tabPane);
				case SwingConstants.BOTTOM:
					return new BottomRenderer(tabPane);
				case SwingConstants.LEFT:
					return new LeftRenderer(tabPane);
				case SwingConstants.RIGHT:
					return new RightRenderer(tabPane);
				default:
					return new TopRenderer(tabPane);
			}
		}

		private static AbstractRenderer createEmbeddedRenderer(JTabbedPane tabPane)
		{
			switch (tabPane.getTabPlacement())
			{
				case SwingConstants.TOP:
					return new TopEmbeddedRenderer(tabPane);
				case SwingConstants.BOTTOM:
					return new BottomEmbeddedRenderer(tabPane);
				case SwingConstants.LEFT:
					return new LeftEmbeddedRenderer(tabPane);
				case SwingConstants.RIGHT:
					return new RightEmbeddedRenderer(tabPane);
				default:
					return new TopEmbeddedRenderer(tabPane);
			}
		}

		protected boolean isFirstDisplayedTab(int tabIndex, int position, int paneBorder)
		{
			return tabIndex == 0;
			// return (position - paneBorder) < 8;
		}

		protected Insets getTabAreaInsets(Insets defaultInsets)
		{
			return defaultInsets;
		}

		protected Insets getContentBorderInsets(Insets defaultInsets)
		{
			return defaultInsets;
		}

		/**
		 * Returns the amount by which the label should be shifted horizontally.
		 */
		protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
		{
			return 0;
		}

		/**
		 * Returns the amount by which the label should be shifted vertically.
		 */
		protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
		{
			return 0;
		}

		/**
		 * Returns the amount of overlap for two Runs.
		 */
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return tabRunOverlay;
		}

		/**
		 * Returns if a run should be padded with empty space
		 * to take up as much room as the others.
		 */
		protected boolean shouldPadTabRun(int run, boolean aPriori)
		{
			return aPriori;
		}

		/**
		 * Returns the amount by which the run number <code>run</code> should be indented. Add a few pixels for every run to make
		 * diagonal lines align.
		 */
		protected int getTabRunIndent(int run)
		{
			return 0;
		}

		/**
		 * Returns the insets for the given tab.
		 */
		protected abstract Insets getTabInsets(int tabIndex, Insets tabInsets);

		/**
		 * Draws the rectancle around the Tab label which indicates keyboard focus.
		 */
		protected abstract void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected);

		/**
		 * Fills the background of the given tab to make sure overlap of
		 * tabs is handled correctly.
		 */
		protected abstract void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected);

		/**
		 * Paints the border around the given tab.
		 */
		protected abstract void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected);

		/**
		 * Returns additional the insets for the selected tab. This allows to "raise"
		 * The selected tab over the others
		 */
		protected Insets getSelectedTabPadInsets()
		{
			return EMPTY_INSETS;
		}

		/**
		 * Draws the top edge of the border around the content area.
		 * Draw unbroken line for tabs are not on TOP
		 * override where appropriate.
		 */
		protected void paintContentBorderTopEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			if (isContentBorderPainted)
			{
				g.setColor(selectHighlight);
				g.fillRect(x, y, w - 1, 1);
			}
		}

		/**
		 * Draws the bottom edge of the Border around the content area.
		 * Draw broken line if selected tab is visible and adjacent to content
		 * and TabPlacement is same as painted edge.
		 */
		protected void paintContentBorderBottomEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			if (isContentBorderPainted)
			{
				g.setColor(darkShadow);
				g.fillRect(x, y + h - 1, w - 1, 1);
			}
		}

		/**
		 * Draws the left edge of the Border around the content area.
		 * Draw broken line if selected tab is visible and adjacent to content
		 * and TabPlacement is same as painted edge
		 */
		protected void paintContentBorderLeftEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			if (isContentBorderPainted)
			{
				g.setColor(selectHighlight);
				g.fillRect(x, y, 1, h - 1);
			}
		}

		/**
		 * Draws the right edge of the Border around the content area.
		 * Draw broken line if selected tab is visible and adjacent to content
		 * and TabPlacement is same as painted edge
		 */
		protected void paintContentBorderRightEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			if (isContentBorderPainted)
			{
				g.setColor(darkShadow);
				g.fillRect(x + w - 1, y, 1, h);
			}
		}

		/**
		 * Returns the amount of overlap for two tabs.
		 */
		protected int getTabsOverlay()
		{
			return 0;
		}
	}

	/**
	 * The renderer for the case where tabs are displayed below the contents
	 * and with minimal decoration.
	 */
	private static final class BottomEmbeddedRenderer extends AbstractRenderer
	{

		private BottomEmbeddedRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets insets)
		{
			return EMPTY_INSETS;
		}

		@Override
		protected Insets getContentBorderInsets(Insets defaultInsets)
		{
			return SOUTH_INSETS;
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return EMPTY_INSETS;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
		}

		/**
		 * Paints no focus: minimal decoration is really minimal.
		 */
		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{
			// Embedded tabs paint no focus.
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.setColor(selectColor);
			g.fillRect(x, y, w + 1, h);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h;
			int right = w + 1;

			g.translate(x, y);
			if (isFirstDisplayedTab(tabIndex, x, tabPane.getBounds().x))
			{
				if (isSelected)
				{
					// selected and first in line
					g.setColor(shadowColor);
					g.fillRect(right, 0, 1, bottom - 1);
					g.fillRect(right - 1, bottom - 1, 1, 1);
					// it is open to discussion if the outer border of the tab
					// should be painted because in the primary case it won't
					// be visible anyway. uncomment the following two lines if wanted
					// g.fillRect(0,bottom, right, 1);
					// g.fillRect(-1,0,1,bottom;
					g.setColor(selectHighlight);
					g.fillRect(0, 0, 1, bottom);
					g.fillRect(right - 1, 0, 1, bottom - 1);
					g.fillRect(1, bottom - 1, right - 2, 1);
				}
				else
				{
					// not selected and first in line
				}
			}
			else
			{
				if (isSelected)
				{
					// selected and not first in line
					g.setColor(shadowColor);
					g.fillRect(0, 0, 1, bottom - 1);
					g.fillRect(1, bottom - 1, 1, 1);
					g.fillRect(right, 0, 1, bottom - 1);
					g.fillRect(right - 1, bottom - 1, 1, 1);
					// outside line:
					// g.fillRect(2,bottom, right-3, 1);
					g.setColor(selectHighlight);
					g.fillRect(1, 0, 1, bottom - 1);
					g.fillRect(right - 1, 0, 1, bottom - 1);
					g.fillRect(2, bottom - 1, right - 3, 1);
				}
				else
				{
					g.setColor(shadowColor);
					g.fillRect(1, h / 2, 1, h - (h / 2));
				}
			}
			g.translate(-x, -y);
		}

		@Override
		protected void paintContentBorderBottomEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{

			g.setColor(shadowColor);
			g.fillRect(x, y + h - 1, w, 1);
		}

	}

	/**
	 * The renderer for the case where Tabs are below the content and
	 * decoration is standard.
	 */
	private static final class BottomRenderer extends AbstractRenderer
	{

		private BottomRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets defaultInsets)
		{
			return new Insets(defaultInsets.top, defaultInsets.left + 5, defaultInsets.bottom, defaultInsets.right);
		}

		@Override
		protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
		{
			return isSelected ? 0 : -1;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return tabRunOverlay - 2;
		}

		@Override
		protected int getTabRunIndent(int run)
		{
			return 6 * run;
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return SOUTH_INSETS;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left - 2, tabInsets.bottom, tabInsets.right - 2);
		}

		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{

			if (!tabPane.hasFocus() || !isSelected)
				return;
			Rectangle tabRect = rects[tabIndex];
			int top = tabRect.y;
			int left = tabRect.x + 6;
			int height = tabRect.height - 3;
			int width = tabRect.width - 12;
			g.setColor(focus);
			g.drawRect(left, top, width, height);
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.setColor(selectColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h - 1;
			int right = w + 4;

			g.translate(x - 3, y);

			// Paint Border
			g.setColor(selectHighlight);

			// Paint left
			g.fillRect(0, 0, 1, 2);
			g.drawLine(0, 2, 4, bottom - 4);
			g.fillRect(5, bottom - 3, 1, 2);
			g.fillRect(6, bottom - 1, 1, 1);

			// Paint bootom
			g.fillRect(7, bottom, 1, 1);
			g.setColor(darkShadow);
			g.fillRect(8, bottom, right - 13, 1);

			// Paint right
			g.drawLine(right + 1, 0, right - 3, bottom - 4);
			g.fillRect(right - 4, bottom - 3, 1, 2);
			g.fillRect(right - 5, bottom - 1, 1, 1);

			g.translate(-x + 3, -y);
		}

		@Override
		protected void paintContentBorderBottomEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			int bottom = y + h - 1;
			int right = x + w - 1;
			g.translate(x, bottom);
			if (drawBroken && selRect.x >= x && selRect.x <= x + w)
			{
				// Break line to show visual connection to selected tab
				g.setColor(darkShadow);
				g.fillRect(0, 0, selRect.x - x - 2, 1);
				if (selRect.x + selRect.width < x + w - 2)
				{
					g.setColor(darkShadow);
					g.fillRect(selRect.x + selRect.width + 2 - x, 0, right - selRect.x - selRect.width - 2, 1);
				}
			}
			else
			{
				g.setColor(darkShadow);
				g.fillRect(0, 0, w - 1, 1);
			}
			g.translate(-x, -bottom);
		}

		@Override
		protected int getTabsOverlay()
		{
			return 4;
		}

	}

	/**
	 * The renderer for tabs on the left with minimal decoration.
	 */
	private static final class LeftEmbeddedRenderer extends AbstractRenderer
	{

		private LeftEmbeddedRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets insets)
		{
			return EMPTY_INSETS;
		}

		@Override
		protected Insets getContentBorderInsets(Insets defaultInsets)
		{
			return WEST_INSETS;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return 0;
		}

		@Override
		protected boolean shouldPadTabRun(int run, boolean aPriori)
		{
			return false;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return EMPTY_INSETS;
		}

		/**
		 * minimal decoration is really minimal: no focus.
		 */
		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{
			// Embedded tabs paint no focus.
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{
			g.setColor(selectColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h;
			int right = w;

			g.translate(x, y);

			if (isFirstDisplayedTab(tabIndex, y, tabPane.getBounds().y))
			{
				if (isSelected)
				{
					// selected and first in line
					g.setColor(selectHighlight);
					g.fillRect(0, 0, right, 1);
					g.fillRect(0, 0, 1, bottom - 1);
					g.fillRect(1, bottom - 1, right - 1, 1);
					g.setColor(shadowColor);
					g.fillRect(0, bottom - 1, 1, 1);
					g.fillRect(1, bottom, right - 1, 1);
					// outside line:
					// g.fillRect(-1,0,1,bottom-1)
				}
				else
				{
					// not selected but first in line
				}
			}
			else
			{
				if (isSelected)
				{
					// selected but not first in line
					g.setColor(selectHighlight);
					g.fillRect(1, 1, right - 1, 1);
					g.fillRect(0, 2, 1, bottom - 2);
					g.fillRect(1, bottom - 1, right - 1, 1);
					g.setColor(shadowColor);
					g.fillRect(1, 0, right - 1, 1);
					g.fillRect(0, 1, 1, 1);
					g.fillRect(0, bottom - 1, 1, 1);
					g.fillRect(1, bottom, right - 1, 1);
					// outside line:
					// g.fillRect(-1,2,1,bottom-3)
				}
				else
				{
					g.setColor(shadowColor);
					g.fillRect(0, 0, right / 3, 1);
				}
			}

			g.translate(-x, -y);
		}

		@Override
		protected void paintContentBorderLeftEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			g.setColor(shadowColor);
			g.fillRect(x, y, 1, h);
		}
	}

	/**
	 * Renderer for tabs on the left with normal decoration.
	 */
	private static final class LeftRenderer extends AbstractRenderer
	{

		private LeftRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets defaultInsets)
		{
			return new Insets(defaultInsets.top + 4, defaultInsets.left, defaultInsets.bottom, defaultInsets.right);
		}

		@Override
		protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
		{
			return 1;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return 1;
		}

		@Override
		protected boolean shouldPadTabRun(int run, boolean aPriori)
		{
			return false;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return WEST_INSETS;
		}

		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{

			if (!tabPane.hasFocus() || !isSelected)
				return;
			Rectangle tabRect = rects[tabIndex];
			int top = tabRect.y + 2;
			int left = tabRect.x + 3;
			int height = tabRect.height - 5;
			int width = tabRect.width - 6;
			g.setColor(focus);
			g.drawRect(left, top, width, height);
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{
			if (!isSelected)
			{
				g.setColor(unselectColor);
				g.fillRect(x + 1, y + 1, w - 1, h - 2);
			}
			else
			{
				g.setColor(selectColor);
				g.fillRect(x + 1, y + 1, w - 3, h - 2);
			}
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h - 1;
			int left = 0;
			g.translate(x, y);

			// Paint Border
			g.setColor(selectHighlight);
			// Paint top
			g.fillRect(left + 2, 0, w - 2 - left, 1);

			// Paint left
			g.fillRect(left + 1, 1, 1, 1);
			g.fillRect(left, 2, 1, bottom - 3);
			g.setColor(darkShadow);
			g.fillRect(left + 1, bottom - 1, 1, 1);

			// Paint bottom
			g.fillRect(left + 2, bottom, w - 2 - left, 1);

			g.translate(-x, -y);
		}

		@Override
		protected void paintContentBorderLeftEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			g.setColor(selectHighlight);
			if (drawBroken && selRect.y >= y && selRect.y <= y + h)
			{
				// Break line to show visual connection to selected tab
				
				// line from top until tab starts
				final int yGap;
				if (isAlignVerticalTabsWithHorizontalTabs(tabPane, LEFT))
				{
					yGap = getGapBeforeFirstVerticalTab();
				}
				else
				{
					yGap = 0;
				}
				g.fillRect(x, y + yGap, 1, selRect.y + 1 - y - yGap);

				// line from tab end until bottom
				if (selRect.y + selRect.height < y + h - 2)
				{
					g.fillRect(x, selRect.y + selRect.height - 1, 1, y + h - selRect.y - selRect.height);
				}
			}
			else
			{
				g.fillRect(x, y, 1, h - 1);
			}
		}

	}

	/**
	 * The renderer for tabs on the right with minimal decoration.
	 */
	private static final class RightEmbeddedRenderer extends AbstractRenderer
	{

		private RightEmbeddedRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets insets)
		{
			return EMPTY_INSETS;
		}

		@Override
		protected Insets getContentBorderInsets(Insets defaultInsets)
		{
			return EAST_INSETS;
		}

		@Override
		protected int getTabRunIndent(int run)
		{
			return 4 * run;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return 0;
		}

		@Override
		protected boolean shouldPadTabRun(int run, boolean aPriori)
		{
			return false;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return EMPTY_INSETS;
		}

		/**
		 * Minimal decoration: no focus.
		 */
		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{
			// Embedded tabs paint no focus.
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.setColor(selectColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h;
			int right = w - 1;

			g.translate(x + 1, y);

			if (isFirstDisplayedTab(tabIndex, y, tabPane.getBounds().y))
			{
				if (isSelected)
				{
					// selected and first in line
					g.setColor(shadowColor);
					// outside lines:
					// g.fillRect(0,-1,right,1);
					// g.fillRect(right,-1,1,bottom);
					g.fillRect(right - 1, bottom - 1, 1, 1);
					g.fillRect(0, bottom, right - 1, 1);
					g.setColor(selectHighlight);
					g.fillRect(0, 0, right - 1, 1);
					g.fillRect(right - 1, 0, 1, bottom - 1);
					g.fillRect(0, bottom - 1, right - 1, 1);
				}
			}
			else
			{
				if (isSelected)
				{
					// selected but not first in line
					g.setColor(shadowColor);
					g.fillRect(0, -1, right - 1, 1);
					g.fillRect(right - 1, 0, 1, 1);
					// outside line:
					// g.fillRect(right,0,1,bottom);
					g.fillRect(right - 1, bottom - 1, 1, 1);
					g.fillRect(0, bottom, right - 1, 1);
					g.setColor(selectHighlight);
					g.fillRect(0, 0, right - 1, 1);
					g.fillRect(right - 1, 1, 1, bottom - 2);
					g.fillRect(0, bottom - 1, right - 1, 1);
				}
				else
				{
					// not selected and not first in line
					g.setColor(shadowColor);
					g.fillRect(2 * right / 3, 0, right / 3, 1);
				}
			}
			g.translate(-x - 1, -y);
		}

		@Override
		protected void paintContentBorderRightEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			g.setColor(shadowColor);
			g.fillRect(x + w - 1, y, 1, h);
		}

	}

	/**
	 * Renderer for tabs on the right with normal decoration.
	 */
	private static final class RightRenderer extends AbstractRenderer
	{

		private RightRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected int getTabLabelShiftX(int tabIndex, boolean isSelected)
		{
			return 1;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return 1;
		}

		@Override
		protected boolean shouldPadTabRun(int run, boolean aPriori)
		{
			return false;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return EAST_INSETS;
		}

		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{

			if (!tabPane.hasFocus() || !isSelected)
				return;
			Rectangle tabRect = rects[tabIndex];
			int top = tabRect.y + 2;
			int left = tabRect.x + 3;
			int height = tabRect.height - 5;
			int width = tabRect.width - 6;
			g.setColor(focus);
			g.drawRect(left, top, width, height);
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{
			if (!isSelected)
			{
				g.setColor(unselectColor);
				g.fillRect(x, y, w, h);
			}
			else
			{
				g.setColor(selectColor);
				g.fillRect(x + 2, y, w - 2, h);
			}
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int bottom = h - 1;
			int right = w;

			g.translate(x, y);

			// Paint Border

			g.setColor(selectHighlight);
			g.fillRect(0, 0, right - 1, 1);
			// Paint right
			g.setColor(darkShadow);
			g.fillRect(right - 1, 1, 1, 1);
			g.fillRect(right, 2, 1, bottom - 3);
			// Paint bottom
			g.fillRect(right - 1, bottom - 1, 1, 1);
			g.fillRect(0, bottom, right - 1, 1);

			g.translate(-x, -y);
		}

		@Override
		protected void paintContentBorderRightEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			g.setColor(darkShadow);
			if (drawBroken && selRect.y >= y && selRect.y <= y + h)
			{
				// Break line to show visual connection to selected tab
				g.fillRect(x + w - 1, y, 1, selRect.y - y);
				if (selRect.y + selRect.height < y + h - 2)
				{
					g.fillRect(x + w - 1, selRect.y + selRect.height, 1, y + h - selRect.y - selRect.height);
				}
			}
			else
			{
				g.fillRect(x + w - 1, y, 1, h - 1);
			}
		}
	}

	/**
	 * Renderer for tabs on top with minimal decoration.
	 */
	private static final class TopEmbeddedRenderer extends AbstractRenderer
	{

		private TopEmbeddedRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets insets)
		{
			return EMPTY_INSETS;
		}

		@Override
		protected Insets getContentBorderInsets(Insets defaultInsets)
		{
			return NORTH_INSETS;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top, tabInsets.left + 1, tabInsets.bottom, tabInsets.right);
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return EMPTY_INSETS;
		}

		/**
		 * Minimal decoration: no focus.
		 */
		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{
			// Embedded tabs paint no focus.
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.setColor(selectColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.translate(x, y);

			int right = w;
			int bottom = h;

			if (isFirstDisplayedTab(tabIndex, x, tabPane.getBounds().x))
			{
				if (isSelected)
				{
					g.setColor(selectHighlight);
					// left
					g.fillRect(0, 0, 1, bottom);
					// top
					g.fillRect(0, 0, right - 1, 1);
					// right
					g.fillRect(right - 1, 0, 1, bottom);
					g.setColor(shadowColor);
					// top-right corner
					g.fillRect(right - 1, 0, 1, 1);
					// right
					g.fillRect(right, 1, 1, bottom);
				}
			}
			else
			{
				if (isSelected)
				{
					g.setColor(selectHighlight);
					// left
					g.fillRect(1, 1, 1, bottom - 1);
					// top
					g.fillRect(2, 0, right - 3, 1);
					// right
					g.fillRect(right - 1, 1, 1, bottom - 1);
					g.setColor(shadowColor);
					// left
					g.fillRect(0, 1, 1, bottom - 1);
					// topleft corner
					g.fillRect(1, 0, 1, 1);
					// topright corner
					g.fillRect(right - 1, 0, 1, 1);
					// right
					g.fillRect(right, 1, 1, bottom);
				}
				else
				{
					g.setColor(shadowColor);
					g.fillRect(0, 0, 1, bottom + 2 - bottom / 2);
				}
			}
			g.translate(-x, -y);
		}

		@Override
		protected void paintContentBorderTopEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			g.setColor(shadowColor);
			g.fillRect(x, y, w, 1);
		}

	}

	/**
	 * Renderer for tabs on top with normal decoration.
	 */
	private static final class TopRenderer extends AbstractRenderer
	{

		private TopRenderer(JTabbedPane tabPane)
		{
			super(tabPane);
		}

		@Override
		protected Insets getTabAreaInsets(Insets defaultInsets)
		{
			return new Insets(defaultInsets.top, defaultInsets.left + 4, defaultInsets.bottom, defaultInsets.right);
		}

		@Override
		protected int getTabLabelShiftY(int tabIndex, boolean isSelected)
		{
			return isSelected ? -1 : 0;
		}

		@Override
		protected int getTabRunOverlay(int tabRunOverlay)
		{
			return tabRunOverlay - 2;
		}

		@Override
		protected int getTabRunIndent(int run)
		{
			return 6 * run;
		}

		@Override
		protected Insets getSelectedTabPadInsets()
		{
			return NORTH_INSETS;
		}

		@Override
		protected Insets getTabInsets(int tabIndex, Insets tabInsets)
		{
			return new Insets(tabInsets.top - 1, tabInsets.left - 4, tabInsets.bottom, tabInsets.right - 4);
		}

		@Override
		protected void paintFocusIndicator(
				Graphics g,
				Rectangle[] rects,
				int tabIndex,
				Rectangle iconRect,
				Rectangle textRect,
				boolean isSelected)
		{

			if (!tabPane.hasFocus() || !isSelected)
				return;
			Rectangle tabRect = rects[tabIndex];
			int top = tabRect.y + 1;
			int left = tabRect.x + 4;
			int height = tabRect.height - 3;
			int width = tabRect.width - 9;
			g.setColor(focus);
			g.drawRect(left, top, width, height);
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			int sel = (isSelected) ? 0 : 1;
			g.setColor(selectColor);
			g.fillRect(x, y + sel, w, h / 2);
			g.fillRect(x - 1, y + sel + h / 2, w + 2, h - h / 2);
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected)
		{

			g.translate(x - 4, y);

			int top = 0;
			int right = w + 6;

			// Paint Border
			g.setColor(selectHighlight);

			// Paint left
			g.drawLine(1, h - 1, 4, top + 4);
			g.fillRect(5, top + 2, 1, 2);
			g.fillRect(6, top + 1, 1, 1);

			// Paint top
			g.fillRect(7, top, right - 12, 1);

			// Paint right
			g.setColor(darkShadow);
			g.drawLine(right, h - 1, right - 3, top + 4);
			g.fillRect(right - 4, top + 2, 1, 2);
			g.fillRect(right - 5, top + 1, 1, 1);

			g.translate(-x + 4, -y);
		}

		@Override
		protected void paintContentBorderTopEdge(
				Graphics g,
				int x,
				int y,
				int w,
				int h,
				boolean drawBroken,
				Rectangle selRect,
				boolean isContentBorderPainted)
		{
			int right = x + w - 1;
			int top = y;
			g.setColor(selectHighlight);

			if (drawBroken && selRect.x >= x && selRect.x <= x + w)
			{
				// Break line to show visual connection to selected tab
				g.fillRect(x, top, selRect.x - 2 - x, 1);
				if (selRect.x + selRect.width < x + w - 2)
				{
					g.fillRect(selRect.x + selRect.width + 2, top, right - 2 - selRect.x - selRect.width, 1);
				}
				else
				{
					g.fillRect(x + w - 2, top, 1, 1);
				}
			}
			else
			{
				g.fillRect(x, top, w - 1, 1);
			}
		}

		@Override
		protected int getTabsOverlay()
		{
			return 6;
		}
	}

}
