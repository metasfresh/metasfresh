package org.adempiere.ui.editor;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import de.metas.util.Check;

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

public class JComboBoxCopyPasteSupportEditor implements ICopyPasteSupportEditor
{
	/**
	 * @param comboBox
	 * @return {@link JTextComponentCopyPasteSupportEditor} instance or {@link NullCopyPasteSupportEditor} in case the text component is null.
	 */
	public static final ICopyPasteSupportEditor ofComponent(final JComboBox<?> comboBox)
	{
		if (comboBox == null)
		{
			return NullCopyPasteSupportEditor.instance;
		}

		ICopyPasteSupportEditor copyPasteSupport = (ICopyPasteSupportEditor)comboBox.getClientProperty(PROPERTY_CopyPasteSupportEditor);
		if (copyPasteSupport == null)
		{
			copyPasteSupport = new JComboBoxCopyPasteSupportEditor(comboBox);
			comboBox.putClientProperty(PROPERTY_CopyPasteSupportEditor, copyPasteSupport);
		}
		return copyPasteSupport;
	}

	private static final String PROPERTY_CopyPasteSupportEditor = ICopyPasteSupportEditor.class.getName();
	private final JComboBox<?> _comboBox;

	private JComboBoxCopyPasteSupportEditor(final JComboBox<?> comboBox)
	{
		super();
		Check.assumeNotNull(comboBox, "comboBox not null");
		_comboBox = comboBox;
	}

	private final JComboBox<?> getComboBox()
	{
		return _comboBox;
	}

	private final JTextComponent getTextComponent()
	{
		final JComboBox<?> comboBox = getComboBox();
		final ComboBoxEditor comboEditor = comboBox == null ? null : comboBox.getEditor();
		final Component comboEditorComponent = comboEditor == null ? null : comboEditor.getEditorComponent();
		if (comboEditorComponent instanceof JTextComponent)
		{
			return (JTextComponent)comboEditorComponent;
		}

		return null;
	}

	@Override
	public Action getCopyPasteAction(final CopyPasteActionType actionType)
	{
		final String swingActionMapName = actionType.getSwingActionMapName();
		final Action action = getComboBox().getActionMap().get(swingActionMapName);
		return action;
	}

	@Override
	public void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke)
	{
		final JComboBox<?> comboBox = getComboBox();
		final String swingActionMapName = actionType.getSwingActionMapName();
		comboBox.getActionMap().put(swingActionMapName, action);

		if (keyStroke != null)
		{
			comboBox.getInputMap().put(keyStroke, action.getValue(Action.NAME));
		}
	}

	@Override
	public boolean isCopyPasteActionAllowed(final CopyPasteActionType actionType)
	{
		if (actionType == CopyPasteActionType.Copy)
		{
			return hasTextToCopy();
		}
		else if (actionType == CopyPasteActionType.Cut)
		{
			return isEditable() && hasTextToCopy();
		}
		else if (actionType == CopyPasteActionType.Paste)
		{
			return isEditable();
		}
		else if (actionType == CopyPasteActionType.SelectAll)
		{
			return !isEmpty();
		}
		else
		{
			return false;
		}
	}

	@Override
	public void executeCopyPasteAction(final CopyPasteActionType actionType)
	{
		if (actionType == CopyPasteActionType.Cut)
		{
			doCut();
		}
		else if (actionType == CopyPasteActionType.Copy)
		{
			doCopy();
		}
		else if (actionType == CopyPasteActionType.Paste)
		{
			doPaste();
		}
		else if (actionType == CopyPasteActionType.SelectAll)
		{
			doSelectAll();
		}
	}

	private final boolean isEditable()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return false;
		}
		return textComponent.isEditable() && textComponent.isEnabled();
	}

	private final boolean hasTextToCopy()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return false;
		}
//		Document document = textComponent.getDocument();
//		final JComboBox<?> comboBox = getComboBox();
//		final ComboBoxEditor editor = comboBox.getEditor();
//		comboBox.getClass().getMethod("getDisplay").invoke(comboBox);
		

		final String selectedText = textComponent.getSelectedText();
		return selectedText != null && !selectedText.isEmpty();
	}

	private final boolean isEmpty()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return false;
		}
		final String text = textComponent.getText();
		return Check.isEmpty(text, false);
	}

	private void doCopy()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return;
		}

		textComponent.copy();
	}

	private void doCut()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return;
		}

		textComponent.cut();
	}

	private void doPaste()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return;
		}

		textComponent.paste();
	}

	private void doSelectAll()
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return;
		}

		// NOTE: we need to request focus first because it seems in some case the code below is not working when the component does not have the focus.
		textComponent.requestFocus();
		
		textComponent.selectAll();
	}
}
