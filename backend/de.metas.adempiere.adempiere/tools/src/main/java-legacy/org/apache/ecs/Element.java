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

import java.io.OutputStream;
import java.io.PrintWriter;

/**
    This class describes an ElementFactory.

    @version $Id: Element.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface Element extends ElementRegistry
{
    /**
        Element to be rendered in all CAPS
    */
    public static final int UPPERCASE = 1;

    /**
        Element to be rendered in all lowercase
    */
    public static final int LOWERCASE = 2;

    /**
        Element to be rendered as specified by subclass
    */
    public static final int MIXEDCASE = 3;

    /**
        Element tag to be rendered to the center of start_tag end_tag &lt;_tag&gt; <br>
        This is the default.
    */
    public static final int CENTER = 4;

    /**
        Element tag to be rendered to the left of start_tag end_tag _tag&lt;&gt;
    */
    public static final int LEFT = 5;

    /**
        Element tag to be rendered to the right of start_tag end_tag &lt;&gt;_tag
    */
    public static final int RIGHT = 6;

    /**
        Set case type
    */
    public void setCase(int type);

    /**
        Used to determine case setting
    */
    public int getCase();

    /**
        Get the version number of this codebase
    */
    public String getVersion();

    /**
        Set the HtmlElement type
    */
    public void setElementType(String element_type);

    /**
        Get the HtmlElement type
    */
    public String getElementType();

    /**
        Set wether or not this Element needs a closing tag
    */
    public void setNeedClosingTag(boolean close_tag);

    /**
        Get whether or not this Element needs a closing tag
    */
    public  boolean getNeedClosingTag();

    /**
        Determine if this element needs a line break, if pretty printing.
    */
    public boolean getNeedLineBreak();

    /**
        Set tag position. ElementFactory CENTER | LEFT | RIGHT
    */
    public void setTagPosition(int position);

    /**
        Get tag position. How is the element supposed to be rendered.
    */
    public int getTagPosition();

    /**
        Set the start tag character.
    */
    public void setStartTagChar(char start_tag);

    /**
        Get the start tag character.
    */
    public char getStartTagChar();

    /**
        Set the end tag character.
    */
    public void setEndTagChar(char end_tag);

    /**
        Get the end tag character.
    */
    public char getEndTagChar();

    /*
        Set a modifer for the start of the tag.
    */
    public Element setBeginStartModifier(char start_modifier);

    /**
        Get a modifier for the start of the tag if one exists.
    */
    public char getBeginStartModifier();

    /**
        Set a modifer for the end of the tag.
    */
    public Element setBeginEndModifier(char start_modifier);

    /**
        Get the modifier for the end of the tag if one exists.
    */
    public char getBeginEndModifier();

    /*
        Set a modifer for the start of the tag.
    */
    public Element setEndStartModifier(char start_modifier);

    /**
        Get a modifier for the start of the tag if one exists.
    */
    public char getEndStartModifier();

    /**
        Set a modifer for the end of the tag.
    */
    public Element setEndEndModifier(char start_modifier);

    /**
        Get the modifier for the end of the tag if one exists.
    */
    public char getEndEndModifier();

    /**
        Set the filter state of the element.
    */
    public Element setFilterState(boolean state);

    /**
        Get the filter state of the element.
    */
    public boolean getFilterState();

    /**
        Set the prettyPrint state of the element.
    */
    public Element setPrettyPrint(boolean pretty_print);

    /**
        Get the prettyPrint state of the element.
    */
    public boolean getPrettyPrint();

    /**
		Set the tab level for pretty printing
	*/
	public void setTabLevel(int tabLevel);

    /**
        Get the tab level for pretty printing.
    */
    
	public int getTabLevel();

    /**
        Provide a way to output the element to a stream.
    */
    public void output(OutputStream out);

    /**
        Provide a way to output the element to a PrintWriter.
    */
    public void output(PrintWriter out);

}
