package de.metas.handlingunits.client.terminal.editor.model.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.util.Check;

public final class HUPOSLayoutConstants implements IHUPOSLayoutConstants
{
	private final Properties constantsGeneric;
	private final Map<Dimension, Properties> _screen2constants = new HashMap<Dimension, Properties>();

	public HUPOSLayoutConstants()
	{
		super();

		constantsGeneric = createConstants_Generic();
		init();
	}

	private void init()
	{
		_screen2constants.clear();
		_screen2constants.put(new Dimension(1024, 740), createConstants_1024_768()); // for 1024x768 we must use here height 740 because they will not see the confirmPanel otherwise
	}

	private Properties createConstants_1024_768()
	{
		final Properties constants = new Properties();
		constants.setProperty(PROPERTY_HUEditor_HUPanelFixedWidth, "w 560:560:560,");
		constants.setProperty(PROPERTY_HUEditor_KeyColumns, "3");
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidth, "177"); // width(minus space use between buttons) of the panel / no of columns
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidthForStrings, "150"); // width used when deciding if we truncate the string displayed on keys
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidthPaddingForStrings, "40");

		// constants for HU key filter panel
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_FixedHeight, "h 30:30:30,");
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_FixedWidth, "w 100%:100%:100%,");
		// NOTE: we use "wmax 100" for labels to avoid horizontal scrollbars when not needed
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Container_Constraints, "wmax 100%, align left"
				// Insets [top/all [left] [bottom] [right]]
				+ ", insets 2 10 1 5"
				);
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Label_Constraints, "right, shrink 100");
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Editor_Constraints, "growy, shrink 0, wmin 100, hmax 200, wrap");

		// constants for attributes editing panel
		constants.setProperty(PROPERTY_HUEditor_Attributes_FixedWidth, "w 440:440:440,");
		// NOTE: we use "wmax 100" for labels to avoid horizontal scrollbars when not needed
		constants.setProperty(PROPERTY_HUEditor_Attributes_Label_Constraints, "right, wmin 50, wmax 100, shrink 100");
		constants.setProperty(PROPERTY_HUEditor_Attributes_Editor_Constraints, "growx, shrink 0, wmax 280, wrap");

		// constants for breadcrumb panel
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbFixedHeight, "h 137:137:137,");
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbdFixedWidth, "w 100%:100%:100%,");
		constants.setProperty(PROPERTY_HUEditor_KeyBreadcrumbFixedWidth, "139");
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbKeyColumns, "7");

		// constans for buttons panel
		constants.setProperty(PROPERTY_HUEditor_ButtonsPanel, "wmax 1000"); // we assume the window width is ~1000px

		// constants for split panel
		constants.setProperty(PROPERTY_HUSplit_KeyColumns, "9");
		constants.setProperty(PROPERTY_HUSplit_KeyFixedHeight, "70");
		constants.setProperty(PROPERTY_HUSplit_KeyFixedWidth, "80");

		// constants for HU Issue panel
		constants.setProperty(PROPERTY_HUIssue_KeyColumns, "5");
		constants.setProperty(PROPERTY_HUIssue_KeyFixedWidth, 1500 / 5 + ""); // width(minus space use between buttons) of the panel / no of columns
		constants.setProperty(PROPERTY_HUIssue_KeyFixedHeight, "85");

		// constants for HU Quantity panel
		constants.setProperty(PROPERTY_HUQty_PanelConstraints, "fill, h 100px!, w 230px!");

		return constants;
	}

	private Properties createConstants_Generic()
	{
		final Properties constants = new Properties();
		constants.setProperty(PROPERTY_HUEditor_HUPanelFixedWidth, "w 75%:75%:75%");
		constants.setProperty(PROPERTY_HUEditor_KeyColumns, "5");
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidth, "");
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidthForStrings, "150"); // width used when deciding if we truncate the string displayed on keys
		constants.setProperty(PROPERTY_HUEditor_KeyFixedWidthPaddingForStrings, "40");

		// constants for HU key filter panel
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_FixedHeight, "");
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_FixedWidth, "w 100%:100%:100%,");
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Container_Constraints, "wmax 100%, align left"
				// Insets [top/all [left] [bottom] [right]]
				+ ", insets 2 10 1 5"
				);
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Label_Constraints, "right, shrink 100");
		constants.setProperty(PROPERTY_HUEditor_HUKeyFilter_Editor_Constraints, "growy, shrink 0, wmin 100, wrap");

		// constants for attributes editing panel
		constants.setProperty(PROPERTY_HUEditor_Attributes_FixedWidth, "");
		constants.setProperty(PROPERTY_HUEditor_Attributes_Label_Constraints, "right, wmin 50, shrink 100");
		constants.setProperty(PROPERTY_HUEditor_Attributes_Editor_Constraints, "growx, shrink 0, wrap");

		// constants for breadcrumb panel
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbFixedHeight, "");
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbdFixedWidth, "w 100%:100%:100%,");
		constants.setProperty(PROPERTY_HUEditor_KeyBreadcrumbFixedWidth, "");
		constants.setProperty(PROPERTY_HUEditor_BreadcrumbKeyColumns, "6");

		// constans for buttons panel
		constants.setProperty(PROPERTY_HUEditor_ButtonsPanel, "wmax 99%");

		// constants for split panel
		constants.setProperty(PROPERTY_HUSplit_KeyColumns, "10");
		constants.setProperty(PROPERTY_HUSplit_KeyFixedHeight, "70");
		constants.setProperty(PROPERTY_HUSplit_KeyFixedWidth, "110");

		// constants for HU Issue panel
		constants.setProperty(PROPERTY_HUIssue_KeyColumns, "5");
		constants.setProperty(PROPERTY_HUIssue_KeyFixedWidth, 970 / 5 + ""); // width(minus space use between buttons) of the panel / no of columns
		constants.setProperty(PROPERTY_HUIssue_KeyFixedHeight, "65");

		// constants for HU Quantity panel
		constants.setProperty(PROPERTY_HUQty_PanelConstraints, "fill, h 100px!, w 230px!");

		return constants;
	}

	@Override
	public Properties getConstants(final ITerminalContext terminalContext)
	{
		if (terminalContext.isShowDebugInfo())
		{
			init();
		}

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		final Dimension screenResolution = terminalContext.getScreenResolution();

		Properties constants = _screen2constants.get(screenResolution);
		if (constants == null)
		{
			constants = constantsGeneric;
		}

		//
		// Return a copy of current constants
		final Properties result = new Properties(constantsGeneric);
		result.putAll(constants);
		return result;
	}

	@Override
	public int getConstantAsInt(final Properties constants, final String propertyName)
	{
		final String constant = constants.getProperty(propertyName);
		if (Check.isEmpty(constant, true))
		{
			return 0;
		}
		return Integer.parseInt(constant.trim());
	}
}
