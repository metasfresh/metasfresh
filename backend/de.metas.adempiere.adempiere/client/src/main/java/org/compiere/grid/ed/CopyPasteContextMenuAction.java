package org.compiere.grid.ed;

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


import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import org.adempiere.ui.AbstractContextMenuAction;
import org.adempiere.ui.IContextMenuAction;
import org.adempiere.ui.IContextMenuActionContext;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditor.CopyPasteActionType;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.NullCopyPasteSupportEditor;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/**
 * Copy/Paste context menu action.
 * 
 * NOTE: this is actually a placeholder for all copy/paste actions which will be returned by {@link #getChildren()}.
 * 
 * @author tsa
 *
 */
public class CopyPasteContextMenuAction extends AbstractContextMenuAction
{
	private List<IContextMenuAction> _childActions;

	@Override
	public String getName()
	{
		// NOTE: it shall return null if we want the child actions to be dropped in parent menu
		return null;
	}

	@Override
	public String getIcon()
	{
		// NOTE: it shall return null if we want the child actions to be dropped in parent menu
		return null;
	}

	@Override
	public boolean isAvailable()
	{
		return true;
	}

	@Override
	public boolean isRunnable()
	{
		return true;
	}

	@Override
	public void run()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<IContextMenuAction> getChildren()
	{
		if (_childActions == null)
		{
			_childActions = ImmutableList.<IContextMenuAction> builder()
					.add(new ItemContextMenuAction(CopyPasteActionType.Cut))
					.add(new ItemContextMenuAction(CopyPasteActionType.Copy))
					.add(new ItemContextMenuAction(CopyPasteActionType.Paste))
					.add(new ItemContextMenuAction(CopyPasteActionType.SelectAll))
					.build();
		}
		return _childActions;
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

	private static class ItemContextMenuAction extends AbstractContextMenuAction
	{
		private final CopyPasteActionType _actionType;
		//
		private ICopyPasteSupportEditor _copyPasteSupport = null;

		public ItemContextMenuAction(final CopyPasteActionType actionType)
		{
			super();
			Check.assumeNotNull(actionType, "actionType not null");
			_actionType = actionType;
		}

		private final CopyPasteActionType getActionType()
		{
			return _actionType;
		}

		@Override
		public void setContext(final IContextMenuActionContext menuCtx)
		{
			super.setContext(menuCtx);
			getCopyPasteSupport(); // eagerly install the actions
		}

		private final ICopyPasteSupportEditor getCopyPasteSupport()
		{
			if (_copyPasteSupport == null)
			{
				_copyPasteSupport = createCopyPasteSupport(getEditor());
				if (!NullCopyPasteSupportEditor.isNull(_copyPasteSupport))
				{
					// Setup the copy/paste action if needed
					CopyPasteAction.getCreateAction(_copyPasteSupport, getActionType());
				}
			}
			return _copyPasteSupport;
		}

		private static ICopyPasteSupportEditor createCopyPasteSupport(final VEditor editor)
		{
			if (editor == null)
			{
				return NullCopyPasteSupportEditor.instance;
			}

			//
			// Check if editor implements our interface
			if (editor instanceof ICopyPasteSupportEditor)
			{
				return (ICopyPasteSupportEditor)editor;
			}

			//
			// Check if editor is aware of ICopyPasteSupport
			if (editor instanceof ICopyPasteSupportEditorAware)
			{
				final ICopyPasteSupportEditorAware copyPasteSupportAware = (ICopyPasteSupportEditorAware)editor;
				final ICopyPasteSupportEditor copyPasteSupport = copyPasteSupportAware.getCopyPasteSupport();
				return copyPasteSupport == null ? NullCopyPasteSupportEditor.instance : copyPasteSupport;
			}

			return NullCopyPasteSupportEditor.instance;
		}

		@Override
		public String getName()
		{
			return getActionType().getAD_Message();
		}

		@Override
		public String getIcon()
		{
			return null;
		}

		@Override
		public KeyStroke getKeyStroke()
		{
			return getActionType().getKeyStroke();
		}

		@Override
		public boolean isAvailable()
		{
			return !NullCopyPasteSupportEditor.isNull(getCopyPasteSupport());
		}

		@Override
		public boolean isRunnable()
		{
			return getCopyPasteSupport().isCopyPasteActionAllowed(getActionType());
		}

		@Override
		public boolean isHideWhenNotRunnable()
		{
			return false; // just gray it out
		}

		@Override
		public void run()
		{
			getCopyPasteSupport().executeCopyPasteAction(getActionType());
		}
	}
}
