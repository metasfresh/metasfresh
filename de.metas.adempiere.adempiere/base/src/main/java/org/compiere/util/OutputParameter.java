package org.compiere.util;

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


import java.io.Serializable;

public class OutputParameter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5269261802164086098L;

	public OutputParameter(int sqlType, int scale, String typeName) {
		this.sqlType = sqlType;
		this.scale = scale;
		this.typeName = typeName;
	}
	
	private int sqlType = -1;
	private int scale = -1;
	private String typeName = null;
	
	private Serializable value = null;
	
	public Serializable getValue() {
		return value;
	}
	
	public void setValue(Serializable value) {
		this.value = value;
	}

	public int getSqlType() {
		return sqlType;
	}

	public int getScale() {
		return scale;
	}

	public String getTypeName() {
		return typeName;
	}
}
