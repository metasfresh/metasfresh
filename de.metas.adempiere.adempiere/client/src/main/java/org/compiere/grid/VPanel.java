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


import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.compiere.apps.APanel;
import org.compiere.apps.search.FindPanelContainer;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.FieldGroupVO;
import org.compiere.model.FieldGroupVO.FieldGroupType;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.model.GridTab;
import org.compiere.swing.CLabel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Services;

import com.google.common.base.MoreObjects;

/**
 * Form panel (i.e. single row layout panel) used to layout field labels and editors.
 */
public final class VPanel extends CTabbedPane
{
	private static final long serialVersionUID = 4901538622101315028L;

	/**
	 * Creates a {@link VPanel} used for standard window, single row layout.
	 * 
	 * @param mainTabName
	 * @param windowNo
	 * @return panel; never returns null
	 */
	public static final VPanel newStandardWindowPanel(final String mainTabName, final int windowNo)
	{
		final VPanelLayoutFactory layoutFactory = VPanelLayoutFactory.newStandardWindowLayout();
		final VPanel panel = new VPanel(mainTabName, windowNo, layoutFactory);
		return panel;
	}

	/**
	 * Creates a {@link VPanel} used in a custom form.
	 * 
	 * @param mainTabName
	 * @param windowNo
	 * @return panel; never returns null
	 */
	public static final VPanel newCustomFormPanel(final String mainTabName, final int windowNo)
	{
		final VPanelLayoutFactory layoutFactory = VPanelLayoutFactory.newCustomFormLayout();
		return new VPanel(mainTabName, windowNo, layoutFactory);
	}

	// services
	static final transient Logger log = LogManager.getLogger(VPanel.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	private final int _windowNo;

	private final VPanelFieldGroupFactory fieldGroupFactory;
	private final List<VPanelFieldGroup> fieldGroupPanels = new ArrayList<>();
	private final VPanelFieldGroup mainGroupPanel;

	private final Map<Integer, VPanelFieldGroup> includedGroupPanelsByTabId = new HashMap<>();
	private final Map<Integer, GridController> includedTabList = new HashMap<>();

	private final VPanelMnemonics mnemonics = new VPanelMnemonics();
	private final Map<String, VEditor> columnName2editor = new HashMap<>();
	private final Map<String, CLabel> columnName2label = new HashMap<>();

	private VPanel(final String mainTabName, final int windowNo, final VPanelLayoutFactory layoutFactory)
	{
		super();
		this._windowNo = windowNo;

		this.fieldGroupFactory = new VPanelFieldGroupFactory()
				.setLayoutFactory(layoutFactory);

		setHideIfOneTab(true); // default: hide tabs if the there is only one
		setBorder(BorderFactory.createEmptyBorder());

		//
		// Create main group panel
		this.mainGroupPanel = createAndAddFieldGroup(FieldGroupVO.build(mainTabName, FieldGroupType.Tab, false));

		// Inside the main panel add another panel which is without border and title bar.
		// For the user it will look like no panel, but on the other hand it will have the same insets as a regular collapsible panel,
		// so the fields will be aligned nicely.
		createAndAddFieldGroup(FieldGroupVO.NULL);
	}	// VPanel

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("mainTabName", mainGroupPanel == null ? "-" : mainGroupPanel.getFieldGroupName())
				.add("windowNo", _windowNo)
				.toString();
	}

	final int getWindowNo()
	{
		return this._windowNo;
	}

	/**
	 * Set Field Mnemonic
	 * 
	 * @param mField field
	 */
	public void setMnemonic(final GridField mField)
	{
		mnemonics.setMnemonic(mField);
	}

	/**
	 * Set Window level Mnemonics
	 *
	 * @param set true if set otherwise unregister
	 */
	public void setMnemonics(final boolean set)
	{
		mnemonics.setMnemonics(set);
	}

	/**
	 * Add an independent Field and Label to the Panel. Only for usage with a form! <br>
	 * <br>
	 * 
	 * NOTE: after you finished building the form field, don't forget to call {@link VPanelFormFieldBuilder#add()}, which will actually add the new field to this panel. The new Field will be added in
	 * the last field group.
	 * 
	 * @return builder
	 */
	public final VPanelFormFieldBuilder newFormField()
	{
		return new VPanelFormFieldBuilder(this);
	}

	/**
	 * Add Field and Label to Panel
	 * 
	 * @param fieldEditor editor
	 */
	public void addField(final VEditor fieldEditor)
	{
		final GridField gridField = fieldEditor.getField();

		//
		// Special case: Field which is an Included tab placeholder
		final int AD_Tab_ID = gridField.getIncluded_Tab_ID();
		if (AD_Tab_ID > 0)
		{
			// Create the included tab field group place holder.
			final VPanelFieldGroup groupPanel = fieldGroupFactory.newEmptyPanelForIncludedTab(AD_Tab_ID);
			mainGroupPanel.addIncludedFieldGroup(groupPanel);
			includedGroupPanelsByTabId.put(AD_Tab_ID, groupPanel);

			// If the grid controller for this tab was already added (see includeTab method),
			// We can really add the grid controller inside this field group.
			final GridController includedGC = includedTabList.get(AD_Tab_ID);
			if (includedGC != null)
			{
				includeTab(includedGC);
			}

			return;
		}

		final String columnName = gridField.getColumnName();
		final GridFieldLayoutConstraints layoutConstraints = gridField.getLayoutConstraints();
		boolean sameLine = layoutConstraints.isSameLine();

		//
		// Get/Create the field group panel
		final VPanelFieldGroup groupPanel;
		{
			final FieldGroupVO fieldGroup = gridField.getFieldGroup();

			// Get previous field group panel
			final VPanelFieldGroup previousFieldGroupPanel = getPreviousFieldGroupPanel(fieldGroup);

			if (previousFieldGroupPanel != null)
			{
				groupPanel = previousFieldGroupPanel;
			}
			else
			{
				groupPanel = createAndAddFieldGroup(fieldGroup);
				sameLine = false; // not same line anymore because we will add the field on a new field group panel
			}
		}

		//
		// Create editor's label
		CLabel fieldLabel = swingEditorFactory.getLabel(gridField);
		if (fieldLabel != null && gridField.isCreateMnemonic())
		{
			mnemonics.setMnemonic(fieldLabel, gridField.getMnemonic());
		}
		if (fieldLabel == null)
		{
			fieldLabel = new CLabel("");
		}

		//
		// Add field label and field editor
		groupPanel.addLabelAndEditor(fieldLabel, fieldEditor, sameLine);
		// Add to internal map
		columnName2label.put(columnName, fieldLabel);
		columnName2editor.put(columnName, fieldEditor);
	}	// addField

	/**
	 * Gets the previous field group panel where we can add a new field having the given <code>fieldGroup</code>.
	 * 
	 * The logic how an existing field group is considered suitable is a bit twisted (but documented inside the method body), but at least is backward compatible.
	 * 
	 * Long story told short is:
	 * <ul>
	 * <li>if the required field group does not have a name, the previously added field group will be used
	 * <li>if the previously added field group has same name as the required field group name we will use it
	 * <li>one exception is the horizontal tabs, on which we will use the first field group with the same name that we found, even if that field group is not right the previous one (backward
	 * compatibility).
	 * </ul>
	 * 
	 * 
	 * @param fieldGroup
	 * @return previous field group panel or <code>null</code> if no suitable field group panel found.
	 */
	private final VPanelFieldGroup getPreviousFieldGroupPanel(final FieldGroupVO fieldGroup)
	{
		final String fieldGroupName = fieldGroup.getFieldGroupName();

		VPanelFieldGroup lastFieldGroupPanel = null; // last field group which is not an included tab

		for (int i = fieldGroupPanels.size() - 1; i >= 0; i--)
		{
			final VPanelFieldGroup fieldGroupPanel = fieldGroupPanels.get(i);

			// Skip included tabs because those does not accept new fields
			if (fieldGroupPanel.isIncludedTab())
			{
				continue;
			}

			//
			// Remember the last field group panel, even if is not matching the field group name
			if (lastFieldGroupPanel == null)
			{
				lastFieldGroupPanel = fieldGroupPanel;
			}

			// No particular field group is required
			// => we can use the first one that we found
			if (fieldGroupName == null || fieldGroupName.length() == 0)
			{
				return fieldGroupPanel;
			}

			//
			// We found an existing field group panel having the required field group name
			// => try to use it if possible
			if (fieldGroupName.equals(fieldGroupPanel.getFieldGroupName()))
			{
				return fieldGroupPanel;
			}
		}

		// No suitable field group panel was found
		return null;
	}

	/**
	 * Create and add a new field group panel
	 * 
	 * @param fieldGroup
	 * @return field group panel; never returns null
	 */
	private final VPanelFieldGroup createAndAddFieldGroup(final FieldGroupVO fieldGroup)
	{
		final FieldGroupType fieldGroupType = fieldGroup.getFieldGroupType();

		//
		// Case: horizontal tab
		final VPanelFieldGroup groupPanel;
		if (fieldGroupType == FieldGroupType.Tab)
		{
			groupPanel = fieldGroupFactory.newHorizontalFieldGroupPanel(fieldGroup.getFieldGroupName());

			final CScrollPane scrollPane = new CScrollPane();
			scrollPane.setViewportView(groupPanel.getContentPane());
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			addTab(groupPanel.getFieldGroupName(), scrollPane);
		}
		// Case: any other
		else
		{
			groupPanel = fieldGroupFactory.newCollapsibleFieldGroupPanel(fieldGroup);
			mainGroupPanel.addIncludedFieldGroup(groupPanel);
		}
		fieldGroupPanels.add(groupPanel);

		return groupPanel;
	}

	/**
	 * 
	 * @param detail
	 */
	void includeTab(final GridController detail)
	{
		final GridTab gridTab = detail.getMTab();
		final int adTabId = gridTab.getAD_Tab_ID();

		//
		// Add this included grid controller to our internal list.
		// If we are not able to add it now, we will add it later in "addField".
		if (!includedTabList.containsKey(adTabId))
		{
			includedTabList.put(adTabId, detail);
		}

		final VPanelFieldGroup groupPanel = includedGroupPanelsByTabId.get(adTabId);
		if (groupPanel == null)
		{
			// the field group was not created yet.
			// Do nothing. We expect this to be solved, later in "addField".
			return;
		}
		
		final APanel panel = new APanel(detail, getWindowNo());
		panel.setBorder(BorderFactory.createEmptyBorder());
		detail.setAPanel(panel); // metas: 02553: set the actual panel to be used and who will receive events
		final String name = gridTab.getName();
		groupPanel.setTitle(name);

		final JPanel groupPanelContent = groupPanel.getContentPane();
		groupPanelContent.removeAll(); // make sure the panel is empty
		groupPanelContent.setLayout(new BorderLayout());
		groupPanelContent.add(panel, BorderLayout.CENTER);
		
		//
		// When the find panel is expended, scroll the outer scroll pane,
		// in order to have the actual included tab content (single row panel or table) visible to user.
		// NOTE: usually the included tabs are the last field groups so the effect would be to scroll a bit down.
		final FindPanelContainer findPanel = detail.getFindPanel();
		if (findPanel != null)
		{
			findPanel.runOnExpandedStateChange(new Runnable()
			{

				@Override
				public void run()
				{
					// Skip if the find panel is not expanded
					if (!findPanel.isExpanded())
					{
						return;
					}

					// NOTE: because when the change event is triggered only the collapsed state is changed, and the actual component layout is happening later,
					// we are enqueuing an event to be processed in next EDT round.
					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							detail.scrollToVisible();
						}
					});
				}
			});
		}
	}

	public VEditor getEditor(final String columnName)
	{
		return columnName2editor.get(columnName);
	}

	public final List<String> getEditorColumnNames()
	{
		return new ArrayList<>(columnName2editor.keySet());
	}

	public final CLabel getEditorLabel(final String columnName)
	{
		return columnName2label.get(columnName);
	}

	public final void updateVisibleFieldGroups()
	{
		for (final VPanelFieldGroup panel : fieldGroupPanels)
		{
			panel.updateVisible();
		}
	}
}	// VPanel
