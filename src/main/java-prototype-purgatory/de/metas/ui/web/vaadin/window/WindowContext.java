package de.metas.ui.web.vaadin.window;

import de.metas.ui.web.vaadin.window.data.ILookupDataSource;
import de.metas.ui.web.vaadin.window.data.LookupDataSourceFactory;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class WindowContext
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int windowNo;
	private final LookupDataSourceFactory lookupDataSourceFactory;

	private WindowContext(final Builder builder)
	{
		super();

		windowNo = builder.windowNo;
		lookupDataSourceFactory = builder.lookupDataSourceFactory;
	}

	public int getWindowNo()
	{
		return windowNo;
	}

	public ILookupDataSource getLookupDataSource(final String columnName, final int displayType, final int adReferenceId)
	{
		return lookupDataSourceFactory.getLookupDataSource(getWindowNo(), columnName, displayType, adReferenceId);
	}

	public static final class Builder
	{
		private int windowNo;
		private LookupDataSourceFactory lookupDataSourceFactory;

		private Builder()
		{
			super();
		}

		public WindowContext build()
		{
			if (lookupDataSourceFactory == null)
			{
				lookupDataSourceFactory = new LookupDataSourceFactory();
			}
			return new WindowContext(this);
		}

		public Builder setWindowNo(final int windowNo)
		{
			this.windowNo = windowNo;
			return this;
		}

		public Builder setLookupDataSourceFactory(final LookupDataSourceFactory lookupDataSourceFactory)
		{
			this.lookupDataSourceFactory = lookupDataSourceFactory;
			return this;
		}
	}
}
