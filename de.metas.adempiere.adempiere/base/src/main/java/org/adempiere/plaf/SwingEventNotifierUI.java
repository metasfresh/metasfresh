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
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

/**
 * Defines UI defaults for swing event notifier (that one that pops up on the bottom-right corner of the screen).
 * 
 * @author tsa
 *
 */
public final class SwingEventNotifierUI
{
	public static final String NOTIFICATIONS_MaxDisplayed = "EventNotifier.MaxDisplayed";
	public static final int NOTIFICATIONS_MaxDisplayed_Default = 10;

	public static final String NOTIFICATIONS_AutoFadeAwayTimeMillis = "EventNotifier.AutoFadeAwayTimeMillis";
	public static final int NOTIFICATIONS_AutoFadeAwayTimeMillis_Default = 15 * 1000; // 13sec
	
	/** Defines the gap (in pixels) between last notification and screen bottom (see FRESH-441) */
	public static final String NOTIFICATIONS_BottomGap = "EventNotifier.BottomGap";
	public static final int NOTIFICATIONS_BottomGap_Default = 40;

	public static final String ITEM_BackgroundColor = "EventNotifier.Item.Background";
	public static final String ITEM_Border = "EventNotifier.Item.Border";
	public static final String ITEM_SummaryText_Font = "EventNotifier.Item.SummaryText.Font";
	public static final String ITEM_DetailText_Font = "EventNotifier.Item.DetailText.Font";
	public static final String ITEM_TextColor = "EventNotifier.Item.TextColor";
	public static final String ITEM_MinimumSize = "EventNotifier.Item.MinimumSize";
	public static final String ITEM_MaximumSize = "EventNotifier.Item.MaximumSize";
	//
	public static final String ITEM_Button_Insets = "EventNotifier.Item.Button.Insets";
	public static final String ITEM_Button_Border = "EventNotifier.Item.Button.Border";
	public static final String ITEM_Button_Size = "EventNotifier.Item.Button.Dimension";

	public static final Color COLOR_Transparent = new Color(0, 0, 0, 0);

	public static final Object[] getUIDefaults()
	{
		return new Object[] {
				//
				// Notifications settings
				NOTIFICATIONS_MaxDisplayed, NOTIFICATIONS_MaxDisplayed_Default
				, NOTIFICATIONS_AutoFadeAwayTimeMillis, NOTIFICATIONS_AutoFadeAwayTimeMillis_Default
				, NOTIFICATIONS_BottomGap, NOTIFICATIONS_BottomGap_Default

				//
				// Item settings
				, ITEM_BackgroundColor, AdempierePLAF.createActiveValueProxy(AdempiereLookAndFeel.MANDATORY_BG_KEY, Color.WHITE) // same as mandatory background color
				, ITEM_Border, new BorderUIResource(BorderFactory.createLineBorder(Color.GRAY, 1))
				, ITEM_SummaryText_Font, new FontUIResource("Serif", Font.BOLD, 12)
				, ITEM_DetailText_Font, new FontUIResource("Serif", Font.PLAIN, 12)
				, ITEM_TextColor, null
				, ITEM_MinimumSize, new DimensionUIResource(230, 45)
				, ITEM_MaximumSize, null
				//
				// Button settings (i.e. the close button)
				, ITEM_Button_Insets, new InsetsUIResource(1, 4, 1, 4)
				, ITEM_Button_Size, 20
				, ITEM_Button_Border, new BorderUIResource(BorderFactory.createEmptyBorder())
		};
	}

	private SwingEventNotifierUI()
	{
		super();
	}
}
