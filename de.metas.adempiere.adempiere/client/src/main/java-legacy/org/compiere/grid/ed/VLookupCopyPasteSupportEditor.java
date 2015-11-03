package org.compiere.grid.ed;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CTextField;

import com.google.common.collect.ImmutableMap;

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

class VLookupCopyPasteSupportEditor implements ICopyPasteSupportEditor
{
	public static final VLookupCopyPasteSupportEditor of(final CComboBox<?> comboBox, final CTextField textField)
	{
		return new VLookupCopyPasteSupportEditor(comboBox, textField);
	}

	private final ICopyPasteSupportEditor comboBoxCopyPasteSupport;
	private final ICopyPasteSupportEditor textFieldCopyPasteSupport;
	private final ImmutableMap<CopyPasteActionType, CopyPasteActionProxy> copyPasteActions;
	private ICopyPasteSupportEditor activeCopyPasteSupport;

	VLookupCopyPasteSupportEditor(final CComboBox<?> comboBox, final CTextField textField)
	{
		super();
		comboBoxCopyPasteSupport = comboBox.getCopyPasteSupport();
		textFieldCopyPasteSupport = textField.getCopyPasteSupport();

		final ImmutableMap.Builder<CopyPasteActionType, CopyPasteActionProxy> copyPasteActions = ImmutableMap.builder();
		for (final CopyPasteActionType actionType : CopyPasteActionType.values())
		{
			CopyPasteAction.getCreateAction(comboBoxCopyPasteSupport, actionType);
			CopyPasteAction.getCreateAction(textFieldCopyPasteSupport, actionType);

			copyPasteActions.put(actionType, new CopyPasteActionProxy(actionType));
		}
		this.copyPasteActions = copyPasteActions.build();

		activateCombo();
	}

	public void activateCombo()
	{
		activeCopyPasteSupport = comboBoxCopyPasteSupport;
	}

	public void activateTextField()
	{
		activeCopyPasteSupport = textFieldCopyPasteSupport;
	}

	@Override
	public void executeCopyPasteAction(final CopyPasteActionType actionType)
	{
		if (activeCopyPasteSupport == null)
		{
			return;
		}
		activeCopyPasteSupport.executeCopyPasteAction(actionType);
	}

	@Override
	public Action getCopyPasteAction(final CopyPasteActionType actionType)
	{
		return copyPasteActions.get(actionType);
	}

	@Override
	public void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke)
	{
		// do nothing because we already provided the right action to be used
		// comboBoxCopyPasteSupport.putCopyPasteAction(actionType, action, keyStroke);
		// textFieldCopyPasteSupport.putCopyPasteAction(actionType, action, keyStroke);
	}

	@Override
	public boolean isCopyPasteActionAllowed(final CopyPasteActionType actionType)
	{
		if (activeCopyPasteSupport == null)
		{
			return false;
		}
		return activeCopyPasteSupport.isCopyPasteActionAllowed(actionType);
	}

	private class CopyPasteActionProxy extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		private final CopyPasteActionType actionType;

		public CopyPasteActionProxy(final CopyPasteActionType actionType)
		{
			super();
			this.actionType = actionType;
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final ICopyPasteSupportEditor copyPasteSupport = getActiveCopyPasteEditor();
			if (copyPasteSupport == null)
			{
				return;
			}
			copyPasteSupport.executeCopyPasteAction(actionType);
		}

		private final ICopyPasteSupportEditor getActiveCopyPasteEditor()
		{
			return activeCopyPasteSupport;
		}
	}

	/**
	 * Action implementation which forwards a given {@link CopyPasteActionType} to {@link ICopyPasteSupportEditor}.
	 * 
	 * To be used only when the component does not already have a registered handler for this action type.
	 * 
	 * @author tsa
	 *
	 */
	private static class CopyPasteAction extends AbstractAction
	{
		private static final Action getCreateAction(final ICopyPasteSupportEditor copyPasteSupport, final CopyPasteActionType actionType)
		{
			Action action = copyPasteSupport.getCopyPasteAction(actionType);
			if (action == null)
			{
				action = new CopyPasteAction(copyPasteSupport, actionType);
			}

			copyPasteSupport.putCopyPasteAction(actionType, action, actionType.getKeyStroke());
			return action;
		}

		private static final long serialVersionUID = 1L;
		private final ICopyPasteSupportEditor copyPasteSupport;
		private final CopyPasteActionType actionType;

		public CopyPasteAction(final ICopyPasteSupportEditor copyPasteSupport, final CopyPasteActionType actionType)
		{
			super();
			this.copyPasteSupport = copyPasteSupport;
			this.actionType = actionType;
		}

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			copyPasteSupport.executeCopyPasteAction(actionType);
		}
	}

}
