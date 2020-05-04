package de.metas.handlingunits.client.terminal.editor.model;

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


import java.util.Properties;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.ISingletonService;

public interface IHUPOSLayoutConstants extends ISingletonService
{
	String PROPERTY_HUEditor_KeyColumns = "HUEditor.KeyColumns";
	String PROPERTY_HUEditor_KeyFixedWidth = "HUEditor.KeyFixedWidth";
	String PROPERTY_HUEditor_KeyFixedWidthForStrings = "HUEditor.KeyFixedWidthForStrings";
	String PROPERTY_HUEditor_KeyFixedWidthPaddingForStrings = "HUEditor.KeyFixedWidthPaddingForStrings";
	String PROPERTY_HUEditor_HUPanelFixedWidth = "HUEditor.HUPanelFixedWidth";

	String PROPERTY_HUEditor_HUKeyFilter_FixedHeight = "HUEditor.HUKeyFilter.FixedHeight";
	String PROPERTY_HUEditor_HUKeyFilter_FixedWidth = "HUEditor.HUKeyFilter.FixedWidth";
	String PROPERTY_HUEditor_HUKeyFilter_Container_Constraints = "HUEditor.HUKeyFilter.ContainerConstraints";
	String PROPERTY_HUEditor_HUKeyFilter_Label_Constraints = "HUEditor.HUKeyFilter.LabelConstraints";
	String PROPERTY_HUEditor_HUKeyFilter_Editor_Constraints = "HUEditor.HUKeyFilter.EditorConstraints";

	String PROPERTY_HUEditor_Attributes_FixedWidth = "HUEditor.Attributes.FixedWidth";
	String PROPERTY_HUEditor_Attributes_Label_Constraints = "HUEditor.Attributes.LabelConstraints";
	String PROPERTY_HUEditor_Attributes_Editor_Constraints = "HUEditor.Attributes.EditorConstraints";

	String PROPERTY_HUEditor_BreadcrumbdFixedWidth = "HUEditor.BreadcrumbdFixedWidth";
	String PROPERTY_HUEditor_BreadcrumbFixedHeight = "HUEditor.BreadcrumbdFixedHeight";
	String PROPERTY_HUEditor_KeyBreadcrumbFixedWidth = "HUEditor.KeyBreadcrumbdWidth";
	String PROPERTY_HUEditor_BreadcrumbKeyColumns = "HUEditor.BreadcrumbKeyColumns";

	/** Bottom buttons panel layout constraints */
	String PROPERTY_HUEditor_ButtonsPanel = "HUEditor.ButtonsPanel";

	String PROPERTY_HUSplit_KeyColumns = "HUSplit.KeyColumns";
	String PROPERTY_HUSplit_KeyFixedWidth = "HUSplit.KeyFixedWidth";
	String PROPERTY_HUSplit_KeyFixedHeight = "HUSplit.KeyFixedHeight";

	String PROPERTY_HUIssue_KeyFixedWidth = "HUIssue.KeyFixedWidth";
	String PROPERTY_HUIssue_KeyFixedHeight = "HUIssue.KeyFixedHeight";
	String PROPERTY_HUIssue_KeyColumns = "HUIssue.KeyColumns";

	String PROPERTY_HUQty_PanelConstraints = "HUQty.PanelConstraints";

	Properties getConstants(ITerminalContext terminalContext);

	int getConstantAsInt(Properties constants, String propertyName);
}
