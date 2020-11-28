package de.metas.adempiere.util;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.util.DisplayType;

public class Parameter {

	public final String name;

	public final String displayName;

	public final String description;

	public final int displayType;

	public int seqNo;

	private Object value;

	public Parameter(final String name, final String displayName,
			final String description, final int displayType, final int seqNo) {

		this.name = name;
		this.displayName = displayName;
		this.description = description;
		this.displayType = displayType;
		this.seqNo = seqNo;

		if (displayType != DisplayType.String
				&& displayType != DisplayType.FilePath
				&& displayType != DisplayType.Integer
				&& displayType != DisplayType.Number
				&& displayType != DisplayType.YesNo) {
			
			throw new IllegalArgumentException("Illegal display type "
					+ displayType);
		}

	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
}
