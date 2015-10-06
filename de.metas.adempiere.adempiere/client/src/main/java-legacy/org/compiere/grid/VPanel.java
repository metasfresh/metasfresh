/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ]
 *****************************************************************************/
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.APanel;
import org.compiere.grid.ed.VButton;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.X_AD_FieldGroup;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

/**
 * Single Row Panel. Called from GridController
 * 
 * Uses MigLayout
 *
 * @author Jorg Janke
 * @version $Id: VPanel.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ] Carlos Ruiz - globalqss / Fix bug 2307133 - Swing client hiding fields incorrectly
 */
public final class VPanel extends CTabbedPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227080366665213627L;

	// services
	private static final transient CLogger log = CLogger.getCLogger(VPanel.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	private final int _windowNo;
	/** Previous Field Group Header */
	private String previousFieldGroup = null;
	/** Previous Field Group Type */
	private String previousFieldGroupType = null;
	private final Map<String, FieldGroupPanel> fieldGroup2panel = new HashMap<>();
	private final Map<String, JPanel> fieldGroup2contentPanel = new HashMap<>();
	private final Map<Integer, FieldGroupPanel> includedGroupPanelsByTabId = new HashMap<>();
	private final CPanel mainGroupPanel;

	public VPanel(final String mainTabName)
	{
		this(mainTabName, Env.WINDOW_None);
	}

	/**
	 * 
	 * @param mainTabName
	 * @param windowNo
	 */
	public VPanel(final String mainTabName, final int windowNo)
	{
		super();
		this._windowNo = windowNo;

		setHideIfOneTab(true); // default: hide tabs if the there is only one
		setBorder(BorderFactory.createEmptyBorder());

		//
		// Create main group panel
		mainGroupPanel = createGroupPanel(mainTabName);
		fieldGroup2contentPanel.put("main", mainGroupPanel);

		addTabWithScrollbars(mainTabName, mainGroupPanel);
	}	// VPanel
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("mainTabName", mainGroupPanel == null ? "-" : mainGroupPanel.getName())
				.add("windowNo", _windowNo)
				.toString();
	}

	private final CPanel createGroupPanel(final String name)
	{
		final CPanel groupPanel = new CPanel();
		groupPanel.setName(name);
		groupPanel.setBackground(AdempierePLAF.getFormBackground());

		groupPanel.setLayout(new MigLayout(FieldGroupPanel.defaultLayoutConstraints));

		final VPanelLayoutCallback layoutCallback = new VPanelLayoutCallback();
		layoutCallback.setEnforceSameSizeOnAllLabels(false);
		VPanelLayoutCallback.setup(groupPanel, layoutCallback);

		return groupPanel;
	}

	/**
	 * Adds an horizontal tab with scrollbars.
	 * 
	 * @param title tab title
	 * @param contentPanel tab content panel
	 */
	private final void addTabWithScrollbars(final String title, final Component contentPanel)
	{
		final CScrollPane scrollPane = new CScrollPane();
		scrollPane.setViewportView(contentPanel);
		// increase mouse-wheel scroll speed:
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

		addTab(title, scrollPane);
	}

	public final void setHideIfOneTab(final boolean hideIfOneTab)
	{
		putClientProperty(AdempiereLookAndFeel.HIDE_IF_ONE_TAB, Boolean.TRUE);
	}

	/**
	 * Use layout callback to implement size groups across panels and to shrink hidden components vertically.
	 */
	// private final VPanelLayoutCallback layoutCallback = new VPanelLayoutCallback();

	private final int getWindowNo()
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
		if (mField.isCreateMnemonic())
		{
			return;
		}

		final String text = mField.getHeader();
		int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)	// We have a mnemonic - creates Ctrl_Shift_
		{
			char mnemonic = text.toUpperCase().charAt(pos + 1);
			if (mnemonic != ' ')
			{
				if (!m_mnemonics.contains(mnemonic))
				{
					mField.setMnemonic(mnemonic);
					m_mnemonics.add(mnemonic);
				}
				else
					log.warning(mField.getColumnName() + " - Conflict - Already exists: " + mnemonic + " (" + text + ")");
			}
		}
	}	// setMnemonic

	/**
	 * Add an independent Field and Label to the Panel. Only for usage with a form! <br>
	 * <br>
	 * 
	 * NOTE: after you finished building the form field, don't forget to call {@link FormFieldBuilder#add()}, which will actually add the new field to this panel. The new Field will be added in the
	 * last field group.
	 * 
	 * @return builder
	 */
	public final FormFieldBuilder newFormField()
	{
		return new FormFieldBuilder();
	}

	/**
	 * Add Field and Label to Panel
	 * 
	 * @param editor editor
	 * @param mField field model
	 */
	public void addField(final VEditor editor, final GridField mField)
	{
		//
		// Special case: Included tab
		final int AD_Tab_ID = mField.getIncluded_Tab_ID();
		if (AD_Tab_ID > 0)
		{
			final FieldGroupPanel groupPanel = FieldGroupPanel.newEmptyPanelForIncludedTab(AD_Tab_ID);
			mainGroupPanel.add(groupPanel.getComponent(), "newline, spanx, growx");

			includedGroupPanelsByTabId.put(AD_Tab_ID, groupPanel);

			final GridController includedGC = includedTabList.get(AD_Tab_ID);
			if (includedGC != null)
			{
				includeTab(includedGC);
			}

			return;
		}

		//
		// Create label.
		// Do an early check because if label are editor are nulls, there is no point to continue.
		CLabel label = swingEditorFactory.getLabel(mField);
		if (label == null && editor == null)
		{
			return;
		}

		boolean sameLine = mField.isSameLine();

		// [ 1757088 ] // sets top
		String fieldGroup = mField.getFieldGroup();
		String fieldGroupType = mField.getFieldGroupType();
		if (Check.isEmpty(fieldGroup))
		{
			fieldGroup = previousFieldGroup;
			fieldGroupType = previousFieldGroupType;
		}
		//
		if (addGroup(fieldGroup, fieldGroupType, mField.getIsCollapsedByDefault()))
		{
			// if a new group was added, the current field is not same line anymore
			sameLine = false;
		}
		else
		{
			fieldGroup = previousFieldGroup;
			fieldGroupType = previousFieldGroupType;
		}

		final JPanel groupPanel = getGroupPanel(fieldGroup, fieldGroupType);
		final VPanelLayoutCallback layoutCallback = VPanelLayoutCallback.forContainer(groupPanel);

		//
		// Add the label
		{
			if (label == null)
				label = new CLabel("");
			//
			if (mField.isCreateMnemonic())
				setMnemonic(label, mField.getMnemonic());

			// label constraints
			String labelConstraints = "align trailing";
			if (!sameLine)
			{
				labelConstraints += ", newline";
			}

			layoutCallback.updateMinWidthFrom(label);
			groupPanel.add(label, labelConstraints);
		}

		//
		// Add the field
		if (editor != null)
		{
			final Component editorComp = (Component)editor;
			// field constraints
			// long fields span all remaining columns
			String editorConstraints = "growx, pushx";
			if (mField.isLongField())
			{
				editorConstraints += mField.isLongField() ? ",spanx" : "";
			}

			// Add Field
			layoutCallback.updateMinWidthFrom(editorComp, mField);
			groupPanel.add(editorComp, editorConstraints);

			// Link Label to Field
			label.setLabelFor(editorComp);

			//
			// Add components to internal maps
			final String columnName = mField.getColumnName();
			columnName2label.put(columnName, label);
			columnName2editor.put(columnName, editor);
		}
	}	// addField

	/**
	 * 
	 * @param fieldGroup
	 * @param fieldGroupType
	 * @return group panel
	 * @see #addGroup(String, String, boolean)
	 */
	private final JPanel getGroupPanel(final String fieldGroup, final String fieldGroupType)
	{
		final JPanel groupPanel = fieldGroup2contentPanel.get(fieldGroup);
		if (groupPanel != null)
		{
			return groupPanel;
		}
		
		// Fallback: No particular field group type
		// => use main tab panel
		return mainGroupPanel;
	}

	/**
	 * Add Group
	 * 
	 * @param fieldGroup field group
	 * @param fieldGroupType
	 * @return true if group added
	 */
	private boolean addGroup(final String fieldGroup, final String fieldGroupType, final boolean collapsed)
	{
		// First time - add top
		if (previousFieldGroup == null)
		{
			previousFieldGroup = "";
			previousFieldGroupType = "";
		}

		if (fieldGroup == null || fieldGroup.length() == 0)
		{
			return false;
		}

		if (fieldGroup.equals(previousFieldGroup))
		{
			return false;
		}

		// [ 1757088 ]
		if (fieldGroup2contentPanel.get(fieldGroup) != null)
		{
			return false;
		}

		// Field group: horizontal tab
		if (X_AD_FieldGroup.FIELDGROUPTYPE_Tab.equals(fieldGroupType))
		{
			final CPanel groupPanel = createGroupPanel(fieldGroup);

			addTabWithScrollbars(fieldGroup, groupPanel);

			fieldGroup2contentPanel.put(fieldGroup, groupPanel);
		}
		// Field group: others
		else
		{
			final FieldGroupPanel groupPanel = FieldGroupPanel.newEmptyPanelForFieldGroup(fieldGroup, collapsed);
			
			groupPanel.setCollapsible(X_AD_FieldGroup.FIELDGROUPTYPE_Collapse.equals(fieldGroupType));

			final JPanel groupPanelContent = groupPanel.getContentPane();
			VPanelLayoutCallback.setup(groupPanelContent, VPanelLayoutCallback.forContainer(mainGroupPanel));

			mainGroupPanel.add(groupPanel.getComponent(), "newline, spanx, growx");

			fieldGroup2panel.put(fieldGroup, groupPanel);
			fieldGroup2contentPanel.put(fieldGroup, groupPanel.getContentPane());
		}

		previousFieldGroup = fieldGroup;
		previousFieldGroupType = fieldGroupType;
		return true;
	}	// addGroup

	/**
	 * Set Mnemonic for Label CTRL_SHIFT_x
	 *
	 * @param label label
	 * @param predefinedMnemonic predefined Mnemonic
	 */
	private void setMnemonic(final CLabel label, final char predefinedMnemonic)
	{
		String text = label.getText();
		int pos = text.indexOf('&');
		if (pos != -1 && predefinedMnemonic != 0)
		{
			text = text.substring(0, pos) + text.substring(pos + 1);
			label.setText(text);
			label.setSavedMnemonic(predefinedMnemonic);
			m_fields.add(label);
			log.finest(predefinedMnemonic + " - " + label.getName());
		}
		else
		{
			char mnemonic = getMnemonic(text, label);
			if (mnemonic != 0)
				label.setSavedMnemonic(mnemonic);
			// label.setDisplayedMnemonic(mnemonic);
		}
	}	// setMnemonic

	/**
	 * Set Mnemonic for Check Box or Button
	 *
	 * @param editor check box or button - other ignored
	 * @param predefinedMnemonic predefined Mnemonic
	 */
	private void setMnemonic(VEditor editor, char predefinedMnemonic)
	{
		if (editor instanceof VCheckBox)
		{
			VCheckBox cb = (VCheckBox)editor;
			String text = cb.getText();
			int pos = text.indexOf('&');
			if (pos != -1 && predefinedMnemonic != 0)
			{
				text = text.substring(0, pos) + text.substring(pos + 1);
				cb.setText(text);
				cb.setSavedMnemonic(predefinedMnemonic);
				m_fields.add(cb);
				log.finest(predefinedMnemonic + " - " + cb.getName());
			}
			else
			{
				char mnemonic = getMnemonic(text, cb);
				if (mnemonic != 0)
					cb.setSavedMnemonic(mnemonic);
				// cb.setMnemonic(mnemonic);
			}
		}
		// Button
		else if (editor instanceof VButton)
		{
			VButton b = (VButton)editor;
			String text = b.getText();
			int pos = text.indexOf('&');
			if (pos != -1 && predefinedMnemonic != 0)
			{
				text = text.substring(0, pos) + text.substring(pos + 1);
				b.setText(text);
				b.setSavedMnemonic(predefinedMnemonic);
				m_fields.add(b);
				log.finest(predefinedMnemonic + " - " + b.getName());
			}
			else if (b.getColumnName().equals("DocAction"))
			{
				b.getInputMap(WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.SHIFT_MASK, false), "pressed");
				b.getInputMap(WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.SHIFT_MASK, true), "released");
				// Util.printActionInputMap(b);
			}
			else if (b.getColumnName().equals("Posted"))
			{
				b.getInputMap(WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.SHIFT_MASK, false), "pressed");
				b.getInputMap(WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, Event.SHIFT_MASK, true), "released");
				// Util.printActionInputMap(b);
			}
			else
			{
				char mnemonic = getMnemonic(text, b);
				if (mnemonic != 0)
					b.setSavedMnemonic(mnemonic);
			}
		}
	}	// setMnemonic

	/**
	 * Get Mnemonic from text
	 *
	 * @param text text
	 * @param source component
	 * @return Mnemonic or 0 if not unique
	 */
	private char getMnemonic(String text, Component source)
	{
		if (text == null || text.length() == 0)
			return 0;
		String oText = text;
		text = text.trim().toUpperCase();
		char mnemonic = text.charAt(0);
		if (m_mnemonics.contains(mnemonic))
		{
			mnemonic = 0;
			// Beginning new word
			int index = text.indexOf(' ');
			while (index != -1 && text.length() > index)
			{
				char c = text.charAt(index + 1);
				if (Character.isLetterOrDigit(c) && !m_mnemonics.contains(c))
				{
					mnemonic = c;
					break;
				}
				index = text.indexOf(' ', index + 1);
			}
			// Any character
			if (mnemonic == 0)
			{
				for (int i = 1; i < text.length(); i++)
				{
					char c = text.charAt(i);
					if (Character.isLetterOrDigit(c) && !m_mnemonics.contains(c))
					{
						mnemonic = c;
						break;
					}
				}
			}
			// Nothing found
			if (mnemonic == 0)
			{
				log.finest("None for: " + oText);
				return 0;	// if first char would be returned, the first occurrence is invalid.
			}
		}
		m_mnemonics.add(mnemonic);
		m_fields.add(source);
		log.finest(mnemonic + " - " + source.getName());
		return mnemonic;
	}	// getMnemonic

	/** Used Mnemonics */
	private ArrayList<Character> m_mnemonics = new ArrayList<Character>(30);
	/** Mnemonic Fields */
	private ArrayList<Component> m_fields = new ArrayList<Component>(30);
	private final Map<String, VEditor> columnName2editor = new HashMap<>();
	private final Map<String, CLabel> columnName2label = new HashMap<>();

	private HashMap<Integer, GridController> includedTabList = new HashMap<Integer, GridController>();

	/**
	 * Set Window level Mnemonics
	 *
	 * @param set true if set otherwise unregister
	 */
	public void setMnemonics(boolean set)
	{
		int size = m_fields.size();
		for (int i = 0; i < size; i++)
		{
			Component c = m_fields.get(i);
			if (c instanceof CLabel)
			{
				CLabel l = (CLabel)c;
				if (set)
					l.setDisplayedMnemonic(l.getSavedMnemonic());
				else
					l.setDisplayedMnemonic(0);
			}
			else if (c instanceof VCheckBox)
			{
				VCheckBox cb = (VCheckBox)c;
				if (set)
					cb.setMnemonic(cb.getSavedMnemonic());
				else
					cb.setMnemonic(0);
			}
			else if (c instanceof VButton)
			{
				VButton b = (VButton)c;
				if (set)
					b.setMnemonic(b.getSavedMnemonic());
				else
					b.setMnemonic(0);
			}
		}
	}	// setMnemonics

	/**
	 * 
	 * @param detail
	 */
	void includeTab(final GridController detail)
	{
		final int adTabId = detail.getMTab().getAD_Tab_ID();

		final FieldGroupPanel groupPanel = includedGroupPanelsByTabId.get(adTabId);
		if (groupPanel != null)
		{
			final APanel panel = new APanel(detail, getWindowNo());
			panel.setBorder(BorderFactory.createEmptyBorder());
			detail.setAPanel(panel); // metas: 02553: set the actual panel to be used and who will receive events
			final String name = detail.getMTab().getName();
			groupPanel.setTitle(name);

			final JPanel groupPanelContent = groupPanel.getContentPane();
			groupPanelContent.removeAll(); // make sure the panel is empty
			groupPanelContent.setLayout(new BorderLayout());
			groupPanelContent.add(panel, BorderLayout.CENTER);

			//
			// Apply included tab height
			int includedTabHeight = detail.getIncludedTabHeight();
			if (includedTabHeight <= 0)
			{
				includedTabHeight = UIManager.getInt("VPanel.IncludedTab.Height");
			}
			if (includedTabHeight > 0)
			{
				detail.setPreferredSize(new Dimension(detail.getPreferredSize().width, includedTabHeight));
				detail.setMinimumSize(new Dimension(200, includedTabHeight));
				detail.setMaximumSize(new Dimension(9999, includedTabHeight));
			}
		}

		// this can be call before addField
		if (!includedTabList.containsKey(adTabId))
		{
			includedTabList.put(adTabId, detail);
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
		for (final FieldGroupPanel panel : fieldGroup2panel.values())
		{
			panel.updateVisible();
		}
	}

	private static class FieldGroupPanel
	{
		public static final FieldGroupPanel newEmptyPanelForIncludedTab(final int adTabId)
		{
			final FieldGroupPanel panel = new FieldGroupPanel();
			panel.setName("IncludedTab#" + adTabId);
			panel.setTitle(""); // to be set later
			panel.setCollapsed(false);
			return panel;
		}

		public static final FieldGroupPanel newEmptyPanelForFieldGroup(final String fieldGroup, final boolean collapsed)
		{
			final FieldGroupPanel panel = new FieldGroupPanel();
			panel.setName(fieldGroup);
			panel.setTitle(fieldGroup);
			panel.setCollapsed(collapsed);
			
			// Set content pane border.
			// top/bottom=0 because it seems the JXTaskPane is already adding those
			panel.contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			
			return panel;
		}

		private static boolean DEBUG_LAYOUT = false;
		private static final String defaultLayoutConstraints = "hidemode 3"   // hidden fields take no part in grid
				+", fillx" // fill the whole available width in the container
				+ (DEBUG_LAYOUT ? ", debug" : "") // debugging
		;

		private final VPanelTaskPane container = new VPanelTaskPane();
		private final JPanel contentPanel = new JPanel();

		private FieldGroupPanel()
		{
			super();

			container.setContentPanel(contentPanel);

			//
			// Setup content panel:
			{
				contentPanel.setBackground(AdempierePLAF.getFormBackground());
				// groupPanelContent.setBorder(BorderFactory.createEmptyBorder());

				final String constraints = defaultLayoutConstraints
						// 0 inset left and right as this is a nested panel
						// 0 inset top because of the struts added below
						+ ", ins 0 0 n 0";
				contentPanel.setLayout(new MigLayout(constraints));
				// VPanelLayoutCallback.setup(contentPanel, VPanelLayoutCallback.forContainer(mainGroupPanel));

				// for compatibility with old layout, force collapsible field groups
				// to have a minimum of two columns by inserting invisible components
				final Component strut1 = Box.createVerticalStrut(1);
				final Component strut2 = Box.createVerticalStrut(1);
				contentPanel.add(new CLabel(""), "gap 0 0 0 0");
				contentPanel.add(strut1, "pushx, growx, gap 0 0 0 0");
				contentPanel.add(new CLabel(""), "");
				contentPanel.add(strut2, "pushx, growx, gap 0 0 0 0, wrap");
			}
		}

		private final void setName(final String name)
		{
			container.setName(name);
		}

		public void setTitle(String title)
		{
			container.setTitle(title);
		}

		public final Component getComponent()
		{
			return container;
		}

		public void setCollapsible(final boolean collapsible)
		{
			container.setCollapsible(collapsible);
		}

		public void setCollapsed(final boolean collapsed)
		{
			container.setCollapsed(collapsed);
		}

		public JPanel getContentPane()
		{
			return contentPanel;
		}
		
		public void updateVisible()
		{
			boolean hasVisibleComponents = false;
			for (final Component comp : getContentPane().getComponents())
			{
				if (comp.isVisible())
				{
					hasVisibleComponents = true;
					break;
				}
			}
			
			container.setVisible(hasVisibleComponents);
		}
	}

	/**
	 * Creates and adds a new form field to {@link VPanel}.
	 * 
	 * To start adding a new form field, please call {@link VPanel#newFormField()}.
	 * 
	 * @author tsa
	 */
	public final class FormFieldBuilder
	{
		private int displayType;
		private String header;
		private String columnName;
		private String fieldGroup;
		private String fieldGroupType;
		private int displayLength = 0;
		private boolean readOnly = false;

		public static final boolean DEFAULT_SameLine = true; // default=true, there is business logic which depends on this
		private boolean sameLine = DEFAULT_SameLine;

		private static final boolean DEFAULT_Mandatory = false; // default=false, there is business logic which depends on this
		private boolean mandatory = DEFAULT_Mandatory;
		private Boolean autocomplete;
		private int AD_Column_ID = 0;
		private EventListener editorListener;
		private boolean bindEditorToModel = false; // default=false, backward compatibility

		private FormFieldBuilder()
		{
			super();
		}

		/**
		 * Creates and adds the new field.
		 * 
		 * @return newly created field. If you want to get the editor, please use {@link VPanel#getEditor(String)}.
		 */
		public GridField add()
		{
			final VPanel vpanel = VPanel.this;
			final int windowNo = vpanel.getWindowNo();

			// Create a value object for the new Field.
			final GridFieldVO fieldVO = GridFieldVO.createStdField(Env.getCtx(),
					windowNo,
					0, // TabNo
					0, // AD_Window_ID
					0, // AD_Tab_ID
					false, // tabReadOnly
					false, // isCreated
					false // isTimestamp
					);
			fieldVO.Header = getHeader();
			fieldVO.IsFieldOnly = false;
			fieldVO.IsEncryptedField = false;
			fieldVO.FieldGroup = getFieldGroup();
			fieldVO.setColumnName(getColumnName());
			fieldVO.FieldGroupType = getFieldGroupType();
			fieldVO.IsSameLine = isSameLine();
			fieldVO.IsMandatory = isMandatory();
			fieldVO.setAutocomplete(isAutocomplete());
			fieldVO.setDisplayType(getDisplayType());
			fieldVO.AD_Reference_Value_ID = 0; // otherwise buttons with Column_ID = 0 cause errors in VEditorFactory.getEditor(field, false);
			fieldVO.AD_Column_ID = getAD_Column_ID();
			fieldVO.DisplayLength = displayLength;
			fieldVO.IsReadOnly = readOnly;
			if (fieldVO.getAD_Column_ID() > 0)
			{
				fieldVO.setLookupLoadFromColumn(true);
			}
			fieldVO.setIsDisplayed(true); // cg: task 05419

			// Create the new field based on the value object.
			final GridField field = new GridField(fieldVO);
			field.setGridTab(null); // There's no GridTab in a form.
			field.setDisplayed(true);
			// field.loadLookup(); // Does nothing if this field is not a lookup. // metas: already called from GridField ctor

			//
			// Create an editor for the field.
			final VEditor editor = Services.get(ISwingEditorFactory.class).getEditor(field, false); // tableEditor=false

			//
			// Add Field -> Editor listener
			field.addPropertyChangeListener(editor); // Field -> Editor

			// Add Editor -> Field automatic binding
			if (bindEditorToModel)
			{
				editor.addVetoableChangeListener(new VetoableChangeListener()
				{

					@Override
					public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
					{
						if (!Check.equals(field.getColumnName(), evt.getPropertyName()))
						{
							return;
						}
						final Object value = evt.getNewValue();
						if (value == null)
						{
							// NOTE: skip null values because of how editor works.
							// Maybe we will fix that in future, but atm there are to many events triggered with a null new value.
							return;
						}
						field.setValue(value, true);
					}
				});
			}

			//
			// Add provided editor listener (if any)
			final EventListener editorListener = getEditorListener();
			boolean editorListenerInstalled = false;
			if (editorListener instanceof VetoableChangeListener)
			{
				// Editor:VetoableChange -> Form
				editor.addVetoableChangeListener((VetoableChangeListener)editorListener);
				editorListenerInstalled = true;
			}
			if (editorListener instanceof ActionListener && getDisplayType() == DisplayType.Button)
			{
				// Button:Action -> Form
				// Only needed for Buttons because other editors are fully covered by VetoableChange support.
				editor.addActionListener((ActionListener)editorListener);
				editorListenerInstalled = true;
			}
			// Notify the developer if the editor listener could not be installed
			if (editorListener != null && !editorListenerInstalled)
			{
				new AdempiereException("Unsupported editor listener: " + editorListener + " from " + this)
						.throwOrLogWarning(Services.get(IDeveloperModeBL.class).isEnabled(), log);
			}

			// Add the new field
			vpanel.addField(editor, field);

			return field;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		private int getDisplayType()
		{
			Check.assume(displayType > 0, "displayType > 0");
			return displayType;
		}

		public FormFieldBuilder setDisplayType(int displayType)
		{
			this.displayType = displayType;
			return this;
		}

		private String getHeader()
		{
			// null allowed
			return header;
		}

		/**
		 * @param header String which is Displayed as Label.
		 */
		public FormFieldBuilder setHeader(String header)
		{
			this.header = header;
			return this;
		}

		private String getColumnName()
		{
			Check.assumeNotEmpty(columnName, "columnName not empty");
			return columnName;
		}

		/**
		 * @param columnName Will be the name of the GridField.
		 */
		public FormFieldBuilder setColumnName(String columnName)
		{
			this.columnName = columnName;
			return this;
		}

		private String getFieldGroup()
		{
			// null allowed
			return fieldGroup;
		}

		public FormFieldBuilder setFieldGroup(String fieldGroup)
		{
			this.fieldGroup = fieldGroup;
			return this;
		}

		private String getFieldGroupType()
		{
			// null allowed
			return fieldGroupType;
		}

		public FormFieldBuilder setFieldGroupType(String fieldGroupType)
		{
			this.fieldGroupType = fieldGroupType;
			return this;
		}

		private boolean isSameLine()
		{
			return sameLine;
		}

		/**
		 * Default: {@link #DEFAULT_SameLine}
		 * 
		 * @param sameline If true, the new Field will be added in the same line.
		 */
		public FormFieldBuilder setSameLine(boolean sameLine)
		{
			this.sameLine = sameLine;
			return this;
		}

		private boolean isMandatory()
		{
			return mandatory;
		}

		/**
		 * Default: {@link #DEFAULT_Mandatory}
		 * 
		 * @param mandatory true if this field shall be marked as mandatory
		 */
		public FormFieldBuilder setMandatory(boolean mandatory)
		{
			this.mandatory = mandatory;
			return this;
		}

		private boolean isAutocomplete()
		{
			if (autocomplete != null)
			{
				return autocomplete;
			}

			// if Search, always auto-complete
			if (DisplayType.Search == displayType)
			{
				return true;
			}

			return false;
		}

		public FormFieldBuilder setAutocomplete(boolean autocomplete)
		{
			this.autocomplete = autocomplete;
			return this;
		}

		private int getAD_Column_ID()
		{
			// not set is allowed
			return AD_Column_ID;
		}

		/**
		 * @param AD_Column_ID Column for lookups.
		 */
		public FormFieldBuilder setAD_Column_ID(int AD_Column_ID)
		{
			this.AD_Column_ID = AD_Column_ID;
			return this;
		}

		public FormFieldBuilder setAD_Column_ID(final String tableName, final String columnName)
		{
			return setAD_Column_ID(Services.get(IADTableDAO.class).retrieveColumn(tableName, columnName).getAD_Column_ID());
		}

		private EventListener getEditorListener()
		{
			// null allowed
			return editorListener;
		}

		/**
		 * @param listener VetoableChangeListener that gets called if the field is changed.
		 */
		public FormFieldBuilder setEditorListener(EventListener listener)
		{
			this.editorListener = listener;
			return this;
		}

		public FormFieldBuilder setBindEditorToModel(boolean bindEditorToModel)
		{
			this.bindEditorToModel = bindEditorToModel;
			return this;
		}

		public FormFieldBuilder setDisplayLength(int displayLength)
		{
			this.displayLength = displayLength;
			return this;
		}

		public FormFieldBuilder setReadOnly(boolean readOnly)
		{
			this.readOnly = readOnly;
			return this;
		}
	}
}	// VPanel
