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
package org.apache.ecs.xml;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;

import org.apache.ecs.ConcreteElement;

/**
 * XMLDocument This is the container for XML elements that can be used similar
 * to org.apache.ecs.Document. However, it correctly handles XML elements and
 * doesn't have any notion of a head, body, etc., that is associated with HTML
 * documents.
 * 
 * @author <a href="mailto:bmclaugh@algx.net">Brett McLaughlin</a>
 */
public class XMLDocument
	implements Serializable, Cloneable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2777290564966686983L;

	/** Default Version */
	private static final float DEFAULT_XML_VERSION = 1.0f;

	/** Version Declaration - FIXME!! */
	private String			 versionDecl;

	/** Prolog */
	private Vector<Object>	 prolog;

	/** "Body" of document */
	private XML				content;

	/** @serial codeset codeset */
	private String			 codeset			 = null;

	/**
     * This sets the document up. Since an XML document can be pretty much
     * anything you want, all it does is create an XML Instruction for the
     * default version and sets the document to be standalone.
     */
	public XMLDocument ()
	{
		this (DEFAULT_XML_VERSION, true);
	}

	/**
     * This sets the document up. Since an XML document can be pretty much
     * anything, all this does is create the XML Instruction for the version
     * specified and set the document to be standalone.
     * 
     * @param version -
     *            version of XML this document is
     */
	public XMLDocument (double version)
	{
		this (version, true);
	}

	/**
     * This sets the document up. Since an XML document can be pretty much
     * anything, all this does is create the XML Instruction with the version
     * specified, and identifies the document as standalone if set
     * 
     * @param version -
     *            version of XML document is
     * @param standalone -
     *            boolean: <code>true</code> if standalone, else false
     */
	public XMLDocument (double version, boolean standalone)
	{
		prolog = new Vector<Object> (2);
		StringBuffer versionStr = new StringBuffer ();
		versionStr.append ("<?xml version=\"");
		versionStr.append (version);
		versionStr.append ("\" standalone=\"");
		if (standalone)
			versionStr.append ("yes\"?>");
		else
			versionStr.append ("no\"?>\n");
		this.versionDecl = versionStr.toString ();
		/**
         * FIXME: ECS currently does not do any ordering of attributes. Although
         * about 99% of the time, this has no problems, in the initial XML
         * declaration, it can be a problem in certain frameworks (e.g.
         * Cocoon/Xerces/Xalan). So instead of adding an element here, we have
         * to store this first command in a String and add it to the output at
         * output time.
         */
		/**
         * PI versionDecl = new PI().setTarget("xml"); if (standalone)
         * versionDecl.addInstruction("standalone", "yes"); else
         * versionDecl.addInstruction("standalone", "no");
         * versionDecl.setVersion(version); prolog.addElement(versionDecl);
         */
	}

	/**
     * This sets the document up. Since an XML document can be pretty much
     * anything, all this does is create the XML Instruction with the version
     * specified, and identifies the document as standalone if set. This also
     * allows the codeset to be set as well.
     * 
     * @param version -
     *            version of XML document is
     * @param standalone -
     *            boolean: <code>true</code if standalone, else false
	 * @param codeset - String codeset to use
     */
	public XMLDocument (double version, boolean standalone, String codeset)
	{
		this (version, standalone);
		setCodeset (codeset);
	}

	/**
     * This sets the codeset for this document
     * 
     * @param codeset -
     *            String representation of codeset for this document
     */
	public void setCodeset (String codeset)
	{
		this.codeset = codeset;
	}

	/**
     * This gets the codeset for this document
     * 
     * @return String the codeset for this document
     */
	public String getCodeset ()
	{
		return codeset;
	}

	/**
     * This adds a stylesheet to the XML document.
     * 
     * @param href -
     *            String reference to stylesheet
     * @param type -
     *            String type of stylesheet
     */
	public XMLDocument addStylesheet (String href, String type)
	{
		PI pi = new PI ();
		pi.setTarget ("xml-stylesheet").addInstruction ("href", href)
			.addInstruction ("type", type);
		prolog.addElement (pi);
		return (this);
	}

	/**
     * This adds a stylesheet to the XML document, and assumes the default
     * <code>text/xsl</code> type.
     * 
     * @param href =
     *            String reference to stylesheet
     */
	public XMLDocument addStylesheet (String href)
	{
		return addStylesheet (href, "text/xsl");
	}

	/**
     * This adds the specified element to the prolog of the document
     * 
     * @param element -
     *            Element to add
     */
	public XMLDocument addToProlog (ConcreteElement element)
	{
		prolog.addElement (element);
		return (this);
	}

	/**
     * This adds an element to the XML document. If the document is empty, it
     * sets the passed in element as the root element.
     * 
     * @param element -
     *            XML Element to add
     * @return XMLDocument - modified document
     */
	public XMLDocument addElement (XML element)
	{
		if (content == null)
			content = element;
		else
			content.addElement (element);
		return (this);
	}

	/**
     * Write the document to the OutputStream
     * 
     * @param out -
     *            OutputStream to write to
     */
	public void output (OutputStream out)
	{
		/**
         * FIXME: The other part of the version hack! Add the version
         * declaration to the beginning of the document.
         */
		try
		{
			out.write (versionDecl.getBytes ());
		}
		catch (Exception e)
		{
		}
		for (int i = 0; i < prolog.size (); i++)
		{
			ConcreteElement e = (ConcreteElement)prolog.elementAt (i);
			e.output (out);
		}
		if (content != null)
			content.output (out);
	}

	/**
     * Write the document to the PrinteWriter
     * 
     * @param out -
     *            PrintWriter to write to
     */
	public void output (PrintWriter out)
	{
		/**
         * FIXME: The other part of the version hack! Add the version
         * declaration to the beginning of the document.
         */
		out.write (versionDecl);
		for (int i = 0; i < prolog.size (); i++)
		{
			ConcreteElement e = (ConcreteElement)prolog.elementAt (i);
			e.output (out);
		}
		if (content != null)
			content.output (out);
	}

	/**
     * Override toString so it does something useful
     * 
     * @return String - representation of the document
     */
	public final String toString ()
	{
		StringBuffer retVal = new StringBuffer ();
		if (codeset != null)
		{
			for (int i = 0; i < prolog.size (); i++)
			{
				ConcreteElement e = (ConcreteElement)prolog.elementAt (i);
				retVal.append (e.toString (getCodeset ()) + "\n");
			}
			if (content != null)
				retVal.append (content.toString (getCodeset ()));
		}
		else
		{
			for (int i = 0; i < prolog.size (); i++)
			{
				ConcreteElement e = (ConcreteElement)prolog.elementAt (i);
				retVal.append (e.toString () + "\n");
			}
			if (content != null)
				retVal.append (content.toString ());
		}
		/**
         * FIXME: The other part of the version hack! Add the version
         * declaration to the beginning of the document.
         */
		return versionDecl + retVal.toString ();
	}

	/**
     * Override toString so it prints something useful
     * 
     * @param codeset -
     *            String codeset to use
     * @return String - representation of the document
     */
	public final String toString (String codeset)
	{
		StringBuffer retVal = new StringBuffer ();
		for (int i = 0; i < prolog.size (); i++)
		{
			ConcreteElement e = (ConcreteElement)prolog.elementAt (i);
			retVal.append (e.toString (getCodeset ()) + "\n");
		}
		if (content != null)
			retVal.append (content.toString (getCodeset ()) + "\n");
		/**
         * FIXME: The other part of the version hack! Add the version
         * declaration to the beginning of the document.
         */
		return versionDecl + retVal.toString ();
	}

	/**
     * Clone this document
     * 
     * @return Object - cloned XMLDocument
     */
	public Object clone ()
	{
		return content.clone ();
	}
}
