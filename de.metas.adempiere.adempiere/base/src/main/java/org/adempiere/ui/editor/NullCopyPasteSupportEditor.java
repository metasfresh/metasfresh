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

public final class NullCopyPasteSupportEditor implements ICopyPasteSupportEditor
{
	public static final transient NullCopyPasteSupportEditor instance = new NullCopyPasteSupportEditor();

	public static final boolean isNull(final ICopyPasteSupportEditor copyPasteSupport)
	{
		return copyPasteSupport == null || copyPasteSupport == instance;
	}

	private NullCopyPasteSupportEditor()
	{
		super();
	}

	@Override
	public void executeCopyPasteAction(CopyPasteActionType actionType)
	{
		// nothing
	}

	@Override
	public Action getCopyPasteAction(final CopyPasteActionType actionType)
	{
		return null;
	}

	/**
	 * @throws IllegalStateException always
	 */
	@Override
	public void putCopyPasteAction(final CopyPasteActionType actionType, final Action action, final KeyStroke keyStroke)
	{
		throw new IllegalStateException("Setting copy/paste action not supported");
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isCopyPasteActionAllowed(CopyPasteActionType actionType)
	{
		return false;
	}
}
