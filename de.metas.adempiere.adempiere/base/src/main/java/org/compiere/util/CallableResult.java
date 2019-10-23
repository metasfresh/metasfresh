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


import java.util.Map;

public class CallableResult extends ExecuteResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3931232160704716852L;
	private Map<Integer, OutputParameter> m_ordinalOutput = null;	
	private Map<String, OutputParameter> m_namedOutput = null;	
	
	public Map<Integer, OutputParameter> getOrdinalOutput() {
		return m_ordinalOutput;
	}
	public void setOrdinalOutput(Map<Integer, OutputParameter> ordinalOutput) {
		m_ordinalOutput = ordinalOutput;
	}
	
	public Map<String, OutputParameter> getNamedOutput() {
		return m_namedOutput;
	}
	
	public void setNamedOutput(Map<String, OutputParameter> namedOutput) {
		m_namedOutput = namedOutput;
	}
		 
}
