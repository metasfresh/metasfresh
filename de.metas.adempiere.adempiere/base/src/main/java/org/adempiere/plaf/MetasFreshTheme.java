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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.plaf.PainterUIResource;

import com.google.common.collect.Ordering;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

public class MetasFreshTheme extends com.jgoodies.looks.plastic.theme.LightGray
{
	/** Theme Name */
	public static final String NAME = "metas Fresh Theme";

	public static final String KEY_Logo_TextColor = "Logo.text.color";
	static final ColorUIResource LOGO_TEXT_COLOR = new ColorUIResource(118, 184, 40); // #76B828
	private static final ColorUIResource LOGO_TEXT_COLOR_LIGHT = new ColorUIResource(187, 237, 128); // #BBED80

	public static final String KEY_Logo_BackgroundColor = "Logo.background.color";
	private static final ColorUIResource LOGO_BACKGROUND_COLOR = new ColorUIResource(Color.WHITE);

	public static final String KEY_Logo_TextFont = "Logo.text.font";
	private static final FontUIResource LOGO_TEXT_FONT = new FontUIResource("Serif", Font.ITALIC | Font.BOLD, 20); // italic bold 20 pt

	public static final String KEY_Logo_TextFontSmall = "Logo.text.font.small";
	private static final FontUIResource LOGO_TEXT_FONT_SMALL = new FontUIResource("Serif", Font.ITALIC, 10);

	static final ColorUIResource COLOR_RED_METAS = new ColorUIResource(135, 3, 0);
	private static final ColorUIResource COLOR_RED_LIGHT = new ColorUIResource(255, 204, 204);
	// task 09820
	// make the color red be always the same shade
	private static final ColorUIResource COLOR_RED = new ColorUIResource(new Color(204, 0, 0));
	static final ColorUIResource COLOR_LightGray = new ColorUIResource(243, 242, 228);
	static final ColorUIResource COLOR_Green = LOGO_TEXT_COLOR_LIGHT;
	static final ColorUIResource COLOR_LightGreen = new ColorUIResource(235, 250, 217);
	@SuppressWarnings("unused")
	private static final ColorUIResource COLOR_Transparent = new ColorUIResource(new Color(0, 0, 0, 0));

	public MetasFreshTheme()
	{
		super();
	}

	/**
	 * Set defaults. Called when the theme is installed.
	 *
	 * @param table
	 */
	@Override
	public void addCustomEntriesToTable(final UIDefaults table)
	{
		super.addCustomEntriesToTable(table);

		// Key/Value defaults table:
		table.putDefaults(new Object[] {
				//
				// General colors
				"red", getRed()
				, "white", getWhite()
				, "black", getBlack()

				//
				// Logo
				, KEY_Logo_TextColor, LOGO_TEXT_COLOR
				, KEY_Logo_BackgroundColor, LOGO_BACKGROUND_COLOR
				, KEY_Logo_TextFont, LOGO_TEXT_FONT
				, KEY_Logo_TextFontSmall, LOGO_TEXT_FONT_SMALL

				//
				// Text
				, AdempiereLookAndFeel.MANDATORY_BG_KEY, LOGO_TEXT_COLOR_LIGHT
				, AdempiereLookAndFeel.ERROR_BG_KEY, COLOR_RED_LIGHT
				, AdempiereLookAndFeel.INACTIVE_BG_KEY, COLOR_LightGray
				
				//
				// Plastic L&F settings
				, Plastic3DLookAndFeel.IS_3D_KEY, false // all components shall be flat (task 09387)  
		});
		
		//
		// Toolbar
		table.putDefaults(AdempiereToolBarUI.getUIDefaults());
		// MenuBar
		{
			table.putDefaults(new Object[] {
					"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder())
			});
		}
		
		//
		// VTreePanel (the tree which is displayed in main window and in standard windows which have a tree)
		table.putDefaults(VTreePanelUI.getUIDefaults());

		//
		// TaskPane
		{
			table.putDefaults(new Object[] {
					//
					// TaskPaneContainer
					// (see org.jdesktop.swingx.plaf.basic.BasicTaskPaneUI)
					// Background (see http://stackoverflow.com/questions/14685838/how-to-change-the-bg-color-of-jxtaskpanecontainer )
					// , "TaskPaneContainer.background", getWhite() // not used
					// , "TaskPaneContainer.foreground", getWhite() // not used
					"TaskPaneContainer.backgroundPainter", new PainterUIResource<>(new MattePainter(getWhite())) // this is used
					, "TaskPaneContainer.border", new BorderUIResource(BorderFactory.createEmptyBorder(5, 5, 5, 5))
					//
					// TaskPane
					, "TaskPane.animate", Boolean.FALSE
					, "TaskPane.titleForeground", getWhite()
					, "TaskPane.titleOver", getWhite()
					, "TaskPane.titleBackgroundGradientStart", LOGO_TEXT_COLOR
					, "TaskPane.titleBackgroundGradientEnd", LOGO_TEXT_COLOR
					, "TaskPane.borderColor", LOGO_TEXT_COLOR
					, "TaskPane.background", getPrimary3()
					// No inside border. Components which need this, it shall add it to their panel.
					// NOTE: this is mainly required for JXTaskPanes where we want to include a JScrollPane.
					, AdempiereTaskPaneUI.KEY_TaskPane_InsideBorder, new BorderUIResource(BorderFactory.createEmptyBorder())
			});
		}

		//
		// TabbedPane
		{
			table.putDefaults(new Object[] {
					// ,"TabbedPane.selected", getWhite()
					// , "TabbedPane.selectHighlight", new ColorUIResource(231, 218, 188)
					"TabbedPane.unselectedBackground", COLOR_LightGray
					//
					// Tab's content border insets:
					// NOTE: atm we have a bug when drawing the edge borders, so if the left=0 the edge border will be over-drawn
					, "TabbedPane.contentBorderInsets", new InsetsUIResource(2, 1, 1, 0)
			});
		}

		//
		// ScrollPane/ScrollBar
		{
			table.putDefaults(AdempiereScrollPaneUI.getUIDefaults());
			table.putDefaults(MetasFreshScrollBarUI.getUIDefaults());
		}
		// Combobox
		table.putDefaults(AdempiereComboBoxUI.getUIDefaults());

		//
		// VPanel (i.e. standard window, single row layout)
		{
			table.putDefaults(VPanelUI.getUIDefaults());
		}
		//
		// VPanel - Field group task pane (i.e. that collapsible panel which groups the fields of a given field group)
		{
			final ColorUIResource vpanelTaskPaneForegroundColor = BLACK;
			final ColorUIResource vpanelTaskPaneBackgroundColor = COLOR_LightGreen;
			table.putDefaults(UISubClassIDHelper.applyUISubClassID(AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FieldGroup, new Object[] {
					"TaskPane.titleForeground", vpanelTaskPaneForegroundColor
					, "TaskPane.titleOver", vpanelTaskPaneForegroundColor
					, "TaskPane.titleBackgroundGradientStart", vpanelTaskPaneBackgroundColor
					, "TaskPane.titleBackgroundGradientEnd", vpanelTaskPaneBackgroundColor
					, "TaskPane.borderColor", vpanelTaskPaneBackgroundColor
			}));
		}
		//
		// VPanel - Find Panel(i.e. that collapsible panel used so search in window, displayed on top of standard window tab)
		{
			final ColorUIResource vpanelTaskPaneForegroundColor = BLACK;
			final ColorUIResource vpanelTaskPaneBackgroundColor = COLOR_LightGray;
			table.putDefaults(UISubClassIDHelper.applyUISubClassID(AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FindPanel, new Object[] {
					"TaskPane.titleForeground", vpanelTaskPaneForegroundColor
					, "TaskPane.titleOver", vpanelTaskPaneForegroundColor
					, "TaskPane.titleBackgroundGradientStart", vpanelTaskPaneBackgroundColor
					, "TaskPane.titleBackgroundGradientEnd", vpanelTaskPaneBackgroundColor
					, "TaskPane.borderColor", vpanelTaskPaneBackgroundColor
			}));
		}
		// Find panel settings
		table.putDefaults(FindPanelUI.getUIDefaults());

		//
		// Table
		// (used by all tables, including VTable)
		{
			table.putDefaults(new Object[] {
					"TableHeader.foreground", BLACK
					, "TableHeader.background", COLOR_LightGreen
					// , "TableHeader.font", ?
					, "TableHeader.cellBorder", new BorderUIResource(new TableHeaderBorder(WHITE))
			});
		}
		
		//
		// Button:
		table.putDefaults(new Object[] {
				"Button.is3DEnabled", false // all components shall be flat (task 09387)
				, "Button.borderPaintsFocus", true
		});

		//
		// VEditors
		table.putDefaults(VEditorUI.getUIDefaults());

		//
		// Swing Event Notifier
		table.putDefaults(SwingEventNotifierUI.getUIDefaults());
		
		//
		// Color date+time picker
		table.putDefaults(CalendarUI.getUIDefaults());
		
		//
		// Print Viewer
		table.putDefaults(PrintViewerUI.getUIDefaults());

		//
		// Load UIDefaults from sysconfigs database
		SysConfigUIDefaultsRepository
				.ofLookAndFeelId(AdempiereLookAndFeel.NAME)
				.loadAllFromSysConfigTo(table);
	}

	@SuppressWarnings("unused")
	private static final void dump(final UIDefaults table)
	{
		final List<Object> keys = new ArrayList<>(table.keySet());
		Collections.sort(keys, Ordering.usingToString());
		for (final Object key : keys)
		{
			final Object value = table.get(key);
			System.out.println("" + key + " = " + value);
		}

	}

	/** @return Theme Name */
	@Override
	public final String getName()
	{
		return NAME;
	}   // getName

	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	protected ColorUIResource getPrimary1()
	{
		return LOGO_TEXT_COLOR;
	}

	@Override
	protected ColorUIResource getPrimary3()
	{
		// used for:
		// * standard scrollbar thumb's background color - NOTE: we replace it with our scrollbarUI
		// * panel backgrounds

		// return new ColorUIResource(247, 255, 238); // very very light green
		return getWhite();
	}

	@Override
	protected ColorUIResource getSecondary3()
	{
		return getPrimary3();
	}

	protected ColorUIResource getRed()
	{
		return COLOR_RED;
	}

	@Override
	public ColorUIResource getFocusColor()
	{
		return LOGO_TEXT_COLOR;
	}

	/**
	 * Table header border.
	 * 
	 * This is a slightly changed version of {@link javax.swing.plaf.metal.MetalBorders.TableHeaderBorder}.
	 * 
	 * @author tsa
	 *
	 */
	public static class TableHeaderBorder extends javax.swing.border.AbstractBorder
	{
		private static final long serialVersionUID = 1L;
		private final Insets editorBorderInsets = new Insets(2, 2, 2, 2);
		private final Color borderColor;

		public TableHeaderBorder(final Color borderColor)
		{
			super();
			this.borderColor = borderColor;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h)
		{
			g.translate(x, y);

			g.setColor(borderColor);
			g.drawLine(w - 1, 0, w - 1, h - 1); // right line
			g.drawLine(1, h - 1, w - 1, h - 1); // bottom line

			// g.setColor(MetalLookAndFeel.getControlHighlight());
			// g.drawLine(0, 0, w - 2, 0); // top line
			// g.drawLine(0, 0, 0, h - 2); // left line

			g.translate(-x, -y);
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets insets)
		{
			insets.set(editorBorderInsets.top, editorBorderInsets.left, editorBorderInsets.bottom, editorBorderInsets.right);
			return insets;
		}
	}

}
