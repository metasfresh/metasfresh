package de.metas.handlingunits.client.terminal.ddorder.view;

/*
 * #%L
 * de.metas.handlingunits.client
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


import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.model.HUEditorCallbackAdapter;

/* package */class DDOrderHUEditorCallback extends HUEditorCallbackAdapter<HUEditorModel>
{
	private final DDOrderHUSelectPanel parent;

	public DDOrderHUEditorCallback(final DDOrderHUSelectPanel parent)
	{
		super();

		this.parent = parent;
	}

	@Override
	public boolean editHUs(final HUEditorModel huEditorModel)
	{
		return parent.editHUs(huEditorModel);
	}
}
