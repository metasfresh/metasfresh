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


import java.awt.Component;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.layout.CC;

import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.swing.CLabel;

import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Helper class used to hold the UI components of a field group in a {@link VPanel}.
 * 
 * To create a new instance, please use {@link VPanelFieldGroupFactory}. Never use the constructors directly!
 * 
 * @author tsa
 *
 */
final class VPanelFieldGroup
{
	// services
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	private final VPanelLayoutFactory layoutFactory;

	private final VPanelTaskPane container;
	private final JPanel contentPanel;
	private String fieldGroupName = null;
	private boolean isIncludedTab = false;

	VPanelFieldGroup(final VPanelLayoutFactory layoutFactory, final boolean createCollapsibleContainer)
	{
		super();

		this.layoutFactory = layoutFactory;

		//
		// Setup content panel:
		{
			final boolean zeroInsets = true; // because this is an included panel
			contentPanel = layoutFactory.createFieldsPanel(zeroInsets);
		}

		//
		// Setup container (i.e. the collapsible pane)
		if (createCollapsibleContainer)
		{
			container = new VPanelTaskPane();
			container.setContentPanel(contentPanel);
		}
		else
		{
			container = null;
		}
	}

	/**
	 * Existing content panel wrapper constructor
	 * 
	 * @param contentPanel
	 */
	VPanelFieldGroup(final VPanelLayoutFactory layoutFactory, final JPanel contentPanel)
	{
		super();

		this.layoutFactory = layoutFactory;

		//
		// Setup content panel:
		Check.assumeNotNull(contentPanel, "contentPanel not null");
		this.contentPanel = contentPanel;

		//
		// Setup container (i.e. the collapsible pane)
		this.container = null; // no container
	}

	/**
	 * Sets the FieldGroup name.
	 * 
	 * It is also setting the field group title and internal component's name.
	 * 
	 * @param fieldGroupName
	 */
	final void setFieldGroupName(final String fieldGroupName)
	{
		this.fieldGroupName = fieldGroupName;

		if (container != null)
		{
			container.setName(fieldGroupName);
		}

		setTitle(fieldGroupName);
	}

	public String getFieldGroupName()
	{
		return fieldGroupName;
	}

	public void setTitle(final String title)
	{
		if (container != null)
		{
			container.setTitle(title);
		}
	}

	public boolean isIncludedTab()
	{
		return isIncludedTab;
	}

	void setIncludedTab(final boolean includedTab)
	{
		this.isIncludedTab = includedTab;
	}

	/** @return container component or {@link #getContentPane()} if there is no container */
	public final JComponent getComponent()
	{
		if (container != null)
		{
			return container;
		}
		
		return getContentPane();
	}

	public void setCollapsible(final boolean collapsible)
	{
		if (container != null)
		{
			container.setCollapsible(collapsible);
		}
	}

	public void setCollapsed(final boolean collapsed)
	{
		if (container != null)
		{
			container.setCollapsed(collapsed);
		}
	}

	public JPanel getContentPane()
	{
		return contentPanel;
	}

	/**
	 * Make this field group panel visible if there is at least one component visible inside it.
	 * 
	 * Analog, hide this field group panel if all containing components are not invisible (or there are no components).
	 */
	public void updateVisible()
	{
		if (container == null)
		{
			return;
		}

		boolean hasVisibleComponents = false;
		for (final Component comp : getContentPane().getComponents())
		{
			// Ignore layout components
			if (comp instanceof Box.Filler)
			{
				continue;
			}

			if (comp.isVisible())
			{
				hasVisibleComponents = true;
				break;
			}
		}

		container.setVisible(hasVisibleComponents);
	}

	private VPanelLayoutCallback getLayoutCallback()
	{
		return VPanelLayoutCallback.forContainer(contentPanel);
	}

	private void setLayoutCallback(final VPanelLayoutCallback layoutCallback)
	{
		VPanelLayoutCallback.setup(contentPanel, layoutCallback);
	}

	public void addIncludedFieldGroup(final VPanelFieldGroup childGroupPanel)
	{
		// If it's not an included tab we shall share the same layout constraints as the parent.
		// This will enforce same minimum widths to all labels and editors of this field group and also to child group panel.
		if (!childGroupPanel.isIncludedTab())
		{
			childGroupPanel.setLayoutCallback(getLayoutCallback());
		}

		final CC constraints = new CC()
				.spanX()
				.growX()
				.newline();
		contentPanel.add(childGroupPanel.getComponent(), constraints);
	}

	public void addLabelAndEditor(final CLabel fieldLabel, final VEditor fieldEditor, final boolean sameLine)
	{
		final GridField gridField = fieldEditor.getField();
		final GridFieldLayoutConstraints gridFieldConstraints = gridField.getLayoutConstraints();
		final boolean longField = gridFieldConstraints != null && gridFieldConstraints.isLongField();
		final JComponent editorComp = swingEditorFactory.getEditorComponent(fieldEditor);

		//
		// Update layout callback.
		final VPanelLayoutCallback layoutCallback = getLayoutCallback();
		layoutCallback.updateMinWidthFrom(fieldLabel);
		// NOTE: skip if long field because in that case the minimum length shall not influence the other fields.
		if (!longField)
		{
			layoutCallback.updateMinWidthFrom(fieldEditor);
		}

		//
		// Link Label to Field
		if (fieldLabel != null)
		{
			fieldLabel.setLabelFor(editorComp);
			fieldLabel.setVerticalAlignment(SwingConstants.TOP);
		}

		//
		// Add label
		final CC labelConstraints = layoutFactory.createFieldLabelConstraints(sameLine);
		contentPanel.add(fieldLabel, labelConstraints);

		//
		// Add editor
		final CC editorConstraints = layoutFactory.createFieldEditorConstraints(gridFieldConstraints);
		contentPanel.add(editorComp, editorConstraints);
	}
}
