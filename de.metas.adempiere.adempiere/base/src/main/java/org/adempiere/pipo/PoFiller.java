package org.adempiere.pipo;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.compiere.model.PO;
import org.xml.sax.Attributes;


public class PoFiller{

	PO po = null;
	Attributes atts = null;
	
	public PoFiller(PO po, Attributes atts){
		
		this.po = po;
		
		this.atts = atts;
	}
	
	public void setString(String columnName){
		
		String value = atts.getValue(columnName);
	    
		value = "".equals(value) ? null : value;
		
		po.set_ValueOfColumn(columnName, value);
	}
	
	public void setBoolean(String columnName){
		
		String value = atts.getValue(columnName);
	    
		boolean bool = "true".equals(value) ? true : false;
		
		po.set_ValueOfColumn(columnName, bool);
	}
}
