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

/**
 * Interface implemented by those components which have copy/paste support.
 *
 * @author tsa
 *
 */
public interface ICopyPasteSupportEditor
{
	/** Copy/Paste action type */
	enum CopyPasteActionType
	{
		Cut("cut", "CopyPasteAction.Cut", KeyStroke.getKeyStroke("ctrl X")),
		Copy("copy", "CopyPasteAction.Copy", KeyStroke.getKeyStroke("ctrl C")),
		Paste("paste", "CopyPasteAction.Paste", KeyStroke.getKeyStroke("ctrl V")),
		SelectAll("select-all", "CopyPasteAction.SelectAll", KeyStroke.getKeyStroke("ctrl A"))
		//
		;

		private final String swingActionMapName;
		private final String adMessage;
		private final KeyStroke keyStroke;

		private CopyPasteActionType(final String swingActionMapName, final String adMessage, final KeyStroke keyStroke)
		{
			this.swingActionMapName = swingActionMapName;
			this.adMessage = adMessage;
			this.keyStroke = keyStroke;
		}

		public String getSwingActionMapName()
		{
			return swingActionMapName;
		}

		public String getAD_Message()
		{
			return adMessage;
		}

		public KeyStroke getKeyStroke()
		{
			return keyStroke;
		}
	}

	/**
	 * Execute cut/copy/paste action of given type.
	 *
	 * @param actionType
	 */
	void executeCopyPasteAction(final CopyPasteActionType actionType);

	/**
	 * Gets the copy/paste {@link Action} associated with given type.
	 *
	 * @param actionType
	 * @return action or <code>null</code>
	 */
	Action getCopyPasteAction(final CopyPasteActionType actionType);

	/**
	 * Associate given action with given action type.
	 *
	 * @param actionType
	 * @param action
	 * @param keyStroke optional key stroke to be binded to given action.
	 */
	void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke);

	/**
	 * @param actionType
	 * @return true if given action can be performed
	 */
	boolean isCopyPasteActionAllowed(final CopyPasteActionType actionType);
}
