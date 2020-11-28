/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.apache.ecs;

import java.util.ResourceBundle;

/**
    This class is responsible for loading the ecs.properties file and 
    getting the default settings for ECS. This allows you to edit a 
    simple text file instead of having to edit the .java files and 
    recompile.

    @version $Id: ECSDefaults.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
*/
public class ECSDefaults
{
   protected static ResourceBundle resource;

	// private initializer.
	static {
		try
		{   
			resource = ResourceBundle.getBundle("org.apache.ecs.ecs");
		}
		catch(Exception e)
		{
			System.err.println("Cannot find org.apache.ecs.ecs.properties.");
		}
	}

	private static boolean filter_state = new Boolean(resource.getString("filter_state")).booleanValue();

    private static boolean filter_attribute_state = new Boolean(resource.getString("filter_attribute_state")).booleanValue();

    private static char attribute_equality_sign = resource.getString("attribute_equality_sign").charAt(1);

	private static char begin_start_modifier = resource.getString("begin_start_modifier").charAt(1);

	private static char end_start_modifier = resource.getString("end_start_modifier").charAt(1);

	private static char begin_end_modifier = resource.getString("begin_end_modifier").charAt(1);

	private static char end_end_modifier = resource.getString("end_end_modifier").charAt(1);

    private static char attribute_quote_char = resource.getString("attribute_quote_char").charAt(0);

    private static boolean attribute_quote = new Boolean(resource.getString("attribute_quote")).booleanValue();

	private static boolean end_element = new Boolean(resource.getString("end_element")).booleanValue();

	private static String codeset = resource.getString("codeset");

	private static int position = Integer.parseInt(resource.getString("position"));
	
	private static int case_type = Integer.parseInt(resource.getString("case_type"));

	private static char start_tag = resource.getString("start_tag").charAt(0);

	private static char end_tag = resource.getString("end_tag").charAt(0);

	private static boolean pretty_print = new Boolean(resource.getString("pretty_print")).booleanValue();


	/**
		Should we filter the value of &lt;&gt;VALUE&lt;/&gt;
	*/
	public static boolean getDefaultFilterState()
	{
		return filter_state;
	}

    /**
        Should we filter the value of the element attributes
    */
    public static boolean getDefaultFilterAttributeState()
	{
		return filter_attribute_state;
	}
	
    /**
        What is the equality character for an attribute.
    */
    public static char getDefaultAttributeEqualitySign()
	{
		return attribute_equality_sign;
	}

	/**
		What the start modifier should be
	*/
	public static char getDefaultBeginStartModifier()
	{
		return begin_start_modifier;
	}

	/**
		What the start modifier should be
	*/
	public static char getDefaultEndStartModifier()
	{
		return end_start_modifier;
	}
	
	/**
		What the end modifier should be
	*/
	public static char getDefaultBeginEndModifier()
	{
		return begin_end_modifier;
	}

	/**
		What the end modifier should be
	*/
	public static char getDefaultEndEndModifier()
	{
		return end_end_modifier;
	}

    /*
        What character should we use for quoting attributes.
    */
    public static char getDefaultAttributeQuoteChar()
	{
		return attribute_quote_char;
	}

    /*
        Should we wrap quotes around an attribute?
    */
    public static boolean getDefaultAttributeQuote()
	{
		return attribute_quote;
	}

	/**
		Does this element need a closing tag?
	*/
	public static boolean getDefaultEndElement()
	{
		return end_element;
	}

	/**
		What codeset are we going to use the default is 8859_1
	*/
	public static String getDefaultCodeset()
	{
		return codeset;
	}

	/**
		position of tag relative to start and end.
	*/
	public static int getDefaultPosition()
	{
		return position;
	}
	
	/**
		Default value to set case type
	*/
	public static int getDefaultCaseType()
	{
		return case_type;
	}

	public static char getDefaultStartTag()
	{
		return start_tag;
	}

	public static char getDefaultEndTag()
	{
		return end_tag;
	}

	/**
		Should we print html in a more readable format?
	*/
	public static boolean getDefaultPrettyPrint()
	{
		return pretty_print;
	}

}
