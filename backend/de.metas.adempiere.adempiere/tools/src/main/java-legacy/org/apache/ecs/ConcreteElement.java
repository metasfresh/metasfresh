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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
	This class is to be subclassed by those elements that are made up of
	other elements. i.e. BODY,HEAD,etc.

	@version $Id: ConcreteElement.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
	@author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
	@author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class ConcreteElement extends ElementAttributes implements Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6348409634468876553L;

	/** HTML nbsp				*/
	public static final String	NBSP = "&nbsp;";

	/**
	 * keep a list of elements that need to be added to the element 
	 * @serial registry registry */
	private Hashtable<String,Element> registry = new Hashtable<String,Element>(4); 
	/** Maintain an ordered list of elements */
	private Vector<String> registryList = new Vector<String>(2);

	public ConcreteElement()
	{
	}

	/**
		If the object is in the registry return otherwise return null.
		@param element the name of the object to locate.
	*/
	public ConcreteElement getElement(String element)
	{
		if(registry.containsKey(element))
		{
			return (ConcreteElement)registry.get(element);
		}
		return null;
	}

	/**
		Registers an element in the head element list
		@param   element element to be added to the registry.
	*/
	public Element addElementToRegistry(Element element)
	{
		if ( element == null )
			return(this);
		int hc = element.hashCode();    //  causes error when compiles in 1.4 ??
		String s = Integer.toString(hc);
		addElementToRegistry(s, element);
		return(this);
	}

	/**
		Registers an element in the head element list
		@param   hashcode internal name of element
		@param   element element to be added to the registry.
	*/
	public Element addElementToRegistry(String hashcode,Element element)
	{
		if ( hashcode == null || element == null )
			return(this);

		 element.setFilterState(getFilterState());
		 if(ECSDefaults.getDefaultPrettyPrint() != element.getPrettyPrint())
			  element.setPrettyPrint(getPrettyPrint());
		 registry.put(hashcode,element);
		 if(!registryList.contains(hashcode))
			registryList.addElement(hashcode);
		 return(this);
	}

	/**
		Registers an element in the head element list
		@hashcode named element for hashcode
		@param   element element to be added to the registry.
		@param   filter does this need to be filtered?
	*/
	public Element addElementToRegistry(Element element,boolean filter)
	{
		if ( element == null )
			return(this);
		setFilterState(filter);
		addElementToRegistry(Integer.toString(element.hashCode()),element);
		return(this);
	}

	/**
		Registers an element in the head element list
		@param   element element to be added to the registry.
		@param   filter  should we filter this element?
	*/
	public Element addElementToRegistry(String hashcode, Element element,boolean filter)
	{
		if ( hashcode == null )
			return(this);
		setFilterState(filter);
		addElementToRegistry(hashcode,element);
		return(this);
	}

	/**
		Registers an element in the head element list
		@param   value element to be added to the registry.
		@param   filter does this need to be filtered?
	*/
	public Element addElementToRegistry(String value,boolean filter)
	{
		if ( value == null )
			return(this);
		setFilterState(filter);
		addElementToRegistry(Integer.toString(value.hashCode()),value);
		return(this);
	}

	/**
		Registers an element in the head element list
		@hashcode named element for hashcode
		@param   value element to be added to the registry.
		@param   filter does this need to be filtered?
	*/
	public Element addElementToRegistry(String hashcode, String value,boolean filter)
	{
		if ( hashcode == null )
			return(this);
		setFilterState(filter);
		addElementToRegistry(hashcode,value);
		return(this);
	}

	/**
		Registers an element in the head element list
		@param   value element to be added to the registry.
	*/
	public Element addElementToRegistry(String value)
	{
		if ( value == null )
			return(this);
		addElementToRegistry(new StringElement(value));
		return(this);
	}

	/**
		Registers an element in the head element list
		@param   value element to be added to the registry.
	*/
	public Element addElementToRegistry(String hashcode,String value)
	{
		if ( hashcode == null )
			return(this);

		// We do it this way so that filtering will work.
		// 1. create a new StringElement(element) - this is the only way that setTextTag will get called
		// 2. copy the filter state of this string element to this child.
		// 3. copy the prettyPrint state of the element to this child
		// 4. copy the filter for this string element to this child.

		StringElement se = new StringElement(value);
		se.setFilterState(getFilterState());
		se.setFilter(getFilter());
		se.setPrettyPrint(getPrettyPrint());
		addElementToRegistry(hashcode,se);
		return(this);
	}

	/**
		Removes an element from the element registry
		@param   element element to be added to the registry.
	*/
	public Element removeElementFromRegistry(Element element)
	{
		removeElementFromRegistry(Integer.toString(element.hashCode()));
		return(this);
	}

	/**
		Removes an element from the head element registry
		@param   hashcode element to be added to the registry.
	*/
	public Element removeElementFromRegistry(String hashcode)
	{
		registry.remove(hashcode);
		registryList.removeElement(hashcode);
		return(this);
	}

	/**
		Find out if this element is in the element registry.
		@param element find out if this element is in the registry
	*/
	public boolean registryHasElement(Element element)
	{
		return(registry.contains(element));
	}

	/**
		Get the keys of this element.
	*/
	public Enumeration<String> keys()
	{
		return(registryList.elements());
	}

	/**
		Get an enumeration of the elements that this element contains.
	*/
	public Enumeration<Element> elements()
	{
		return(registry.elements());
	}

	/**
		Find out if this element is in the element registry.
		@param hashcode find out if this element is in the registry
	*/
	public boolean registryHasElement(String hashcode)
	{
		return(registry.containsKey(hashcode));
	}

	/**
		Override output(OutputStream) in case any elements are in the registry.
		@param out OutputStream to write to.
	*/
	public void output(OutputStream out)
	{
		boolean prettyPrint = getPrettyPrint();
		int tabLevel = getTabLevel();
		try
		{
			if (registry.size() == 0)
			{
				if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
					putTabs(tabLevel, out);
				super.output(out);
			}
			else
			{
				if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
					putTabs(tabLevel, out);

				out.write(createStartTag().getBytes());

				// If this is a StringElement that has ChildElements still print the TagText
				if(getTagText() != null)
					out.write(getTagText().getBytes());

				Enumeration<String> en = registryList.elements();

				while(en.hasMoreElements())
				{
					Object obj = registry.get(en.nextElement());
					if(obj instanceof GenericElement)
					{
						Element e = (Element)obj;
						if (prettyPrint && this instanceof Printable)
						{
							if ( getNeedLineBreak() )
							{
								out.write('\n');
								e.setTabLevel(tabLevel + 1);
							}
						}
						e.output(out);
					}
					else
					{
						if (prettyPrint && this instanceof Printable)
						{
							if ( getNeedLineBreak() )
							{
								out.write('\n');
								putTabs(tabLevel + 1, out);
							}
						}
						String string = obj.toString();
						out.write(string.getBytes());
					}
				}
				if (getNeedClosingTag())
				{
					if (prettyPrint && this instanceof Printable)
					{
						if ( getNeedLineBreak() )
						{
							out.write('\n');
							if (tabLevel > 0)
								putTabs(tabLevel, out);
						}
					}
				   out.write(createEndTag().getBytes());
				}
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(new PrintWriter(out));
		}
	}

	/**
		Writer version of this method.
	*/
	public void output(Writer out)
	{
		output ( new PrintWriter(out) );
	}

	/**
		Override output(BufferedWriter) incase any elements are in the registry.
		@param out OutputStream to write to.
	*/
	public void output(PrintWriter out)
	{
		boolean prettyPrint = getPrettyPrint();
		int tabLevel = getTabLevel();
		if (registry.size() == 0)
		{
			if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
				putTabs(tabLevel, out);

			super.output(out);
		}
		else
		{
			if ((prettyPrint && this instanceof Printable) && (tabLevel > 0))
				putTabs(tabLevel, out);

			out.write(createStartTag());
			// If this is a StringElement that has ChildElements still print the TagText
			if(getTagText() != null)
				out.write(getTagText());

			Enumeration<String> en = registryList.elements();
			while(en.hasMoreElements())
			{
				Object obj = registry.get(en.nextElement());
				if(obj instanceof GenericElement)
				{
					Element e = (Element)obj;
					if (prettyPrint && this instanceof Printable)
					{
						if (getNeedLineBreak()) {
							out.write('\n');
							e.setTabLevel(tabLevel + 1);
						}
					}
					e.output(out);
				}
				else
				{
					if (prettyPrint && this instanceof Printable)
					{
						if (getNeedLineBreak()) {
							out.write('\n');
							putTabs(tabLevel + 1, out);
						}
					}
					String string = obj.toString();
					if(getFilterState())
						out.write(getFilter().process(string));
					else
						out.write(string);
				}
			}
			if (getNeedClosingTag())
			{
				if (prettyPrint && this instanceof Printable)
				{
					if (getNeedLineBreak()) {
						out.write('\n');
						if (tabLevel > 0)
							putTabs(tabLevel, out);
					}
				}
			   out.write(createEndTag());
			}
		}
	}

	/**
	 * Allows all Elements the ability to be cloned.
	 */
	public Object clone()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(this);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone =  in.readObject();
			in.close();
			return(clone);
		}
		catch(ClassNotFoundException cnfe)
		{
			throw new InternalError(cnfe.toString());
		}
		catch(StreamCorruptedException sce)
		{
			throw new InternalError(sce.toString());
		}
		catch(IOException ioe)
		{
			throw new InternalError(ioe.toString());
		}
	}
}
