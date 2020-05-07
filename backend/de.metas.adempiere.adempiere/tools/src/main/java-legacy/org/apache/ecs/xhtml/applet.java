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
package org.apache.ecs.xhtml;

import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 * This class creates an &lt;applet&gt; tag.
 * 
 * @version $Id: applet.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>
 */
public class applet extends MultiPartElement
	implements Printable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 404866759736299122L;

	/**
     * Private initializer.
     */
	{
		setElementType ("applet");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	/**
     * Default constructor. Creates the &lt;applet/&gt; Element.<br>
     * use set* methods.
     */
	public applet ()
	{
	}

	/**
     * Determines the base url for this applet.
     * 
     * @param url
     *            base url for this applet.
     */
	public applet setCodeBase (String url)
	{
		addAttribute ("codebase", url);
		return (this);
	}

	/**
     * Comma seperated archive list.
     * 
     * @param url
     *            Comma seperate archive list.
     */
	public applet setArchive (String url)
	{
		addAttribute ("archive", url);
		return (this);
	}

	/**
     * Applet class file.
     * 
     * @param code
     *            applet class file.
     */
	public applet setCode (String code)
	{
		addAttribute ("code", code);
		return (this);
	}

	/**
     * Suggested height of applet.
     * 
     * @param height
     *            suggested link height.
     */
	public applet setHeight (String height)
	{
		addAttribute ("height", height);
		return (this);
	}

	/**
     * Suggested height of applet.
     * 
     * @param height
     *            suggested link height.
     */
	public applet setHeight (int height)
	{
		addAttribute ("height", Integer.toString (height));
		return (this);
	}

	/**
     * Suggested height of applet.
     * 
     * @param height
     *            suggested link height.
     */
	public applet setHeight (double height)
	{
		addAttribute ("height", Double.toString (height));
		return (this);
	}

	/**
     * Suggested width of applet.
     * 
     * @param width suggested link width.
     */
	public applet setWidth (String width)
	{
		addAttribute ("width", width);
		return (this);
	}

	/**
     * Suggested width of applet.
     * 
     * @param width suggested link width.
     */
	public applet setWidth (int width)
	{
		addAttribute ("width", Integer.toString (width));
		return (this);
	}

	/**
     * Suggested width of object.
     * 
     * @param width suggested link width.
     */
	public applet setWidth (double width)
	{
		addAttribute ("width", Double.toString (width));
		return (this);
	}

	/**
     * Suggested horizontal gutter.
     * 
     * @param hspace
     *            suggested horizontal gutter.
     */
	public applet setHSpace (String hspace)
	{
		addAttribute ("hspace", hspace);
		return (this);
	}

	/**
     * Suggested horizontal gutter.
     * 
     * @param hspace
     *            suggested horizontal gutter.
     */
	public applet setHSpace (int hspace)
	{
		addAttribute ("hspace", Integer.toString (hspace));
		return (this);
	}

	/**
     * Suggested horizontal gutter.
     * 
     * @param hspace
     *            suggested horizontal gutter.
     */
	public applet setHSpace (double hspace)
	{
		addAttribute ("hspace", Double.toString (hspace));
		return (this);
	}

	/**
     * Suggested vertical gutter.
     * 
     * @param vspace suggested vertical gutter.
     */
	public applet setVSpace (String vspace)
	{
		addAttribute ("vspace", vspace);
		return (this);
	}

	/**
     * Suggested vertical gutter.
     * 
     * @param vspace suggested vertical gutter.
     */
	public applet setVSpace (int vspace)
	{
		addAttribute ("vspace", Integer.toString (vspace));
		return (this);
	}

	/**
     * Suggested vertical gutter.
     * 
     * @param vspace suggested vertical gutter.
     */
	public applet setVSpace (double vspace)
	{
		addAttribute ("vspace", Double.toString (vspace));
		return (this);
	}

	/**
     * Set the horizontal or vertical alignment of this applet.<br>
     * Convience variables are in the AlignTypes interface.
     * 
     * @param alignment
     *            Set the horizontal or vertical alignment of this applet.<br>
     *            Convience variables are in the AlignTypes interface.
     */
	public applet setAlign (String alignment)
	{
		addAttribute ("align", alignment);
		return (this);
	}

	/**
     * Set the name of this applet.
     * 
     * @param name
     *            set the name of this applet.
     */
	public applet setName (String name)
	{
		addAttribute ("name", name);
		return (this);
	}

	/**
     * Serialized applet file.
     * 
     * @param object
     *            Serialized applet file.
     */
	// someone give me a better description of what this does.
	public applet setObject (String object)
	{
		addAttribute ("object", object);
		return (this);
	}

	/**
     * Breif description, alternate text for the applet.
     * 
     * @param alt
     *            alternat text.
     */
	public applet setAlt (String alt)
	{
		addAttribute ("alt", alt);
		return (this);
	}

	/**
     * Sets the lang="" and xml:lang="" attributes
     * 
     * @param lang
     *            the lang="" and xml:lang="" attributes
     */
	public Element setLang (String lang)
	{
		addAttribute ("lang", lang);
		addAttribute ("xml:lang", lang);
		return this;
	}

	/**
     * Adds an Element to the element.
     * 
     * @param hashcode
     *            name of element for hash table
     * @param element
     *            Adds an Element to the element.
     */
	public applet addElement (String hashcode, Element element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
     * Adds an Element to the element.
     * 
     * @param hashcode
     *            name of element for hash table
     * @param element
     *            Adds an Element to the element.
     */
	public applet addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
     * Add an element to the element
     * 
     * @param element
     *            a string representation of the element
     */
	public applet addElement (String element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
     * Add an element to the element
     * 
     * @param element
     *            an element to add
     */
	public applet addElement (Element element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
     * Removes an Element from the element.
     * 
     * @param hashcode
     *            the name of the element to be removed.
     */
	public applet removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}
}
