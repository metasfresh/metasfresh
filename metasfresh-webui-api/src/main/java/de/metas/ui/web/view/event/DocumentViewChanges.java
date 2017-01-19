package de.metas.ui.web.view.event;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DocumentViewChanges
{
	private final String viewId;
	private final int adWindowId;

	private boolean fullyChanged;

	/* package */ DocumentViewChanges(final String viewId, final int adWindowId)
	{
		super();
		this.viewId = viewId;
		this.adWindowId = adWindowId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("AD_Window_ID", adWindowId)
				.add("fullyChanged", fullyChanged ? Boolean.TRUE : null)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public int getAD_Window_ID()
	{
		return adWindowId;
	}

	public void setFullyChanged()
	{
		fullyChanged = true;
	}

	public boolean isFullyChanged()
	{
		return fullyChanged;
	}

	public Boolean getFullyChanged()
	{
		return fullyChanged ? Boolean.TRUE : null;
	}

	public void collectFrom(final DocumentViewChanges changes)
	{
		if (changes.isFullyChanged())
		{
			fullyChanged = true;
		}
	}
}
