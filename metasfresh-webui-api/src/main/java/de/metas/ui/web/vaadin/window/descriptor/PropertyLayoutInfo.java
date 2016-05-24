package de.metas.ui.web.vaadin.window.descriptor;

import java.io.Serializable;

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

/**
 * Editor layout instructions
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class PropertyLayoutInfo implements Serializable
{
	public static final PropertyLayoutInfo DEFAULT = new PropertyLayoutInfo();
	
	public static final Builder builder()
	{
		return new Builder();
	}

	private final boolean displayed;
	private final boolean nextColumn;
	private final int rowsSpan;

	/** Default constructor */
	private PropertyLayoutInfo()
	{
		this(new Builder());
	}
	
	private PropertyLayoutInfo(final Builder builder)
	{
		super();
		this.nextColumn = builder.nextColumn;
		this.rowsSpan = builder.rowsSpan;
		this.displayed = builder.displayed;
	}
	
	public boolean isDisplayed()
	{
		return displayed;
	}

	public boolean isNextColumn()
	{
		return nextColumn;
	}
	
	public int getRowsSpan()
	{
		return rowsSpan;
	}
	
	public static class Builder
	{
		public boolean displayed = true;
		public boolean nextColumn;
		public int rowsSpan = 1;

		private Builder()
		{
			super();
		}
		
		public PropertyLayoutInfo build()
		{
			return new PropertyLayoutInfo(this);
		}

		public Builder setNextColumn(boolean nextColumn)
		{
			this.nextColumn = nextColumn;
			return this;
		}

		public Builder setRowsSpan(int rowsSpan)
		{
			this.rowsSpan = rowsSpan;
			return this;
		}
		
		public Builder setDisplayed(final boolean displayed)
		{
			this.displayed = displayed;
			return this;
		}
	}
}
