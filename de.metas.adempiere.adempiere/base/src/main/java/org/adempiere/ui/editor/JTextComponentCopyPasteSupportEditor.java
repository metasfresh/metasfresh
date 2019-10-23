package org.adempiere.ui.editor;

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


import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import de.metas.util.Check;

/**
 * Wraps a component which is an {@link JTextComponent} extension and make it behave like an {@link ICopyPasteSupportEditor}.
 *
 * @author tsa
 *
 */
public final class JTextComponentCopyPasteSupportEditor implements ICopyPasteSupportEditor
{
	/**
	 * @param textComponent
	 * @return {@link JTextComponentCopyPasteSupportEditor} instance
	 */
	public static final ICopyPasteSupportEditor ofComponent(final JTextComponent textComponent)
	{
		return new JTextComponentCopyPasteSupportEditor(textComponent);
	}

	private final JTextComponent _textComponent;

	private JTextComponentCopyPasteSupportEditor(final JTextComponent textComponent)
	{
		super();
		Check.assumeNotNull(textComponent, "textComponent not null");
		_textComponent = textComponent;
	}

	private final JTextComponent getTextComponent()
	{
		return _textComponent;
	}

	@Override
	public void executeCopyPasteAction(final CopyPasteActionType actionType)
	{
		final JTextComponent textComponent = getTextComponent();
		if (textComponent == null)
		{
			return; // shall not happen
		}
		if (actionType == CopyPasteActionType.Cut)
		{
			textComponent.cut();
		}
		else if (actionType == CopyPasteActionType.Copy)
		{
			textComponent.copy();
		}
		else if (actionType == CopyPasteActionType.Paste)
		{
			textComponent.paste();
		}
		else if (actionType == CopyPasteActionType.SelectAll)
		{
			// NOTE: we need to request focus first because it seems in some case the code below is not working when the component does not have the focus.
			textComponent.requestFocus();
			
			final Document doc = textComponent.getDocument();
			if (doc != null)
			{
				textComponent.setCaretPosition(0);
				textComponent.moveCaretPosition(doc.getLength());
			}
		}
	}

	@Override
	public Action getCopyPasteAction(final CopyPasteActionType actionType)
	{
		final String swingActionMapName = actionType.getSwingActionMapName();
		return getTextComponent().getActionMap().get(swingActionMapName);
	}

	@Override
	public void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke)
	{
		final JTextComponent textComponent = getTextComponent();
		final String swingActionMapName = actionType.getSwingActionMapName();
		textComponent.getActionMap().put(swingActionMapName, action);

		if (keyStroke != null)
		{
			textComponent.getInputMap().put(keyStroke, action.getValue(Action.NAME));
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

	private final boolean isEditable()
	{
		final JTextComponent textComponent = getTextComponent();
		return textComponent.isEditable() && textComponent.isEnabled();
	}

	private final boolean hasTextToCopy()
	{
		final JTextComponent textComponent = getTextComponent();
		final String selectedText = textComponent.getSelectedText();
		return selectedText != null && !selectedText.isEmpty();
	}

	private final boolean isEmpty()
	{
		final JTextComponent textComponent = getTextComponent();
		final String text = textComponent.getText();
		return Check.isEmpty(text, false);
	}
}
