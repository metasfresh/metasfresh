package org.compiere.grid;

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


import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.compiere.model.FieldGroupVO;
import org.compiere.model.FieldGroupVO.FieldGroupType;

import de.metas.util.Check;

/**
 * {@link VPanelFieldGroup} factory.
 * 
 * @author tsa
 *
 */
final class VPanelFieldGroupFactory
{
	private VPanelLayoutFactory layoutFactory;

	public final VPanelFieldGroup newEmptyPanelForIncludedTab(final int adTabId)
	{
		final boolean createCollapsibleContainer = true;
		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, createCollapsibleContainer);
		panel.setFieldGroupName("IncludedTab#" + adTabId);
		panel.setTitle(""); // to be set later
		panel.setCollapsible(true);
		panel.setCollapsed(false);
		panel.setIncludedTab(true);
		return panel;
	}

	public final VPanelFieldGroup newCollapsibleFieldGroupPanel(final FieldGroupVO fieldGroup)
	{
		final boolean createCollapsibleContainer = fieldGroup != FieldGroupVO.NULL;
		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, createCollapsibleContainer);
		panel.setFieldGroupName(fieldGroup.getFieldGroupName());
		panel.setCollapsed(fieldGroup.isCollapsedByDefault());
		panel.setCollapsible(fieldGroup.getFieldGroupType() == FieldGroupType.Collapsible);
		
		// Set content pane border.
		// top/bottom=0 because it seems the JXTaskPane is already adding those
		panel.getContentPane().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // top, left, bottom, right

		return panel;
	}

	public final VPanelFieldGroup newHorizontalFieldGroupPanel(final String fieldGroupName)
	{
		final boolean zeroInsets = false;
		final JPanel contentPanel = layoutFactory.createFieldsPanel(zeroInsets);
		contentPanel.setName(fieldGroupName);

		final VPanelLayoutCallback layoutCallback = layoutFactory.createFieldsPanelLayoutCallback();
		VPanelLayoutCallback.setup(contentPanel, layoutCallback);

		final VPanelFieldGroup panel = new VPanelFieldGroup(layoutFactory, contentPanel);
		panel.setFieldGroupName(fieldGroupName);
		return panel;
	}
	
	public VPanelFieldGroupFactory setLayoutFactory(final VPanelLayoutFactory layoutFactory)
	{
		Check.assumeNotNull(layoutFactory, "layoutFactory not null");
		this.layoutFactory = layoutFactory;
		return this;
	}
}
