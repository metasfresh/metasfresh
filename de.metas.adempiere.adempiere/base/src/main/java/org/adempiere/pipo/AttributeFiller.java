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
import org.xml.sax.helpers.AttributesImpl;

public class AttributeFiller {

	private AttributesImpl atts = null;
	private PO po = null;
	
	/**
	 * Will clear attributes !!!
	 * @param _atts
	 */
	public AttributeFiller(AttributesImpl attributes){
		attributes.clear();
		atts = attributes;
		po = null;
	}
	
	/**
	 * Will clear attributes !!!
	 * @param _atts
	 */
	public AttributeFiller(AttributesImpl attributes, PO poToAutoFill){
		attributes.clear();
		atts = attributes;
		
		po = poToAutoFill;
	}
	
	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addUnchecked(String name, String value){

		atts.addAttribute("", "", name, "CDATA", value);
	}

	/**
	 * 
	 * @param name
	 * @param stringValue
	 */
	public void addString(String name, String stringValue){

		atts.addAttribute("", "", name, "CDATA", stringValue != null ? stringValue : "");

	}

	/**
	 * 
	 * @param name
	 * @param boolValue
	 */
	public void addBoolean(String name, boolean boolValue){

		atts.addAttribute("", "", name, "CDATA", boolValue == true ? "true" : "false");
	}
	
	
	/**
	 * 
	 * @param name
	 * @param stringValue
	 */
	public void add(String columnName){
		
		Object value = po.get_Value(columnName);
		
		if(value == null){
			
			atts.addAttribute("", "", columnName, "CDATA", "");
			return;
		}
		
		if(value instanceof String){
			atts.addAttribute("", "", columnName, "CDATA", (String)value);
			
		}else if(value instanceof Boolean) {
			atts.addAttribute("", "", columnName, "CDATA",  (Boolean)value == true ? "true" : "false");
			
		}else if(value instanceof Integer) {
			atts.addAttribute("", "", columnName, "CDATA",  value.toString());
			
		}else{
			
			throw new IllegalArgumentException("Add your own type implementation here.");
		}
	}
	
	
	/**
	 * 
	 *
	 */
	public void addIsActive(){
		
		atts.addAttribute("", "", "IsActive", "CDATA",  (Boolean)po.isActive() == true ? "true" : "false");
	}

	/**
	 * 
	 * @return
	 */
	public AttributesImpl getAttributes(){

		return atts;
	}
}
