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
package org.compiere.print;

import java.util.ArrayList;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *	SAX Handler for parsing PrintData
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: PrintDataHandler.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class PrintDataHandler extends DefaultHandler
{
	/**
	 *	Constructor
	 * 	@param ctx context
	 */
	public PrintDataHandler(Properties ctx)
	{
		m_ctx = ctx;
	}	//	PrintDataHandler

	/**	Context					*/
	private Properties		m_ctx = null;
	/**	Final Structure			*/
	private PrintData		m_pd = null;

	/** Current Active Element Name		*/
	private String			m_curPDEname = null;
	/** Current Active Element Value	*/
	private StringBuffer	m_curPDEvalue = null;
	/**	Current Active Print Data		*/
	private PrintData		m_curPD = null;

	/**
	 * 	Get PrintData
	 * 	@return PrintData
	 */
	public PrintData getPrintData()
	{
		return m_pd;
	}	//	getPrintData

	/*************************************************************************/

	/**
	 * 	Receive notification of the start of an element.
	 *
	 * 	@param uri namespace
	 * 	@param localName simple name
	 * 	@param qName qualified name
	 * 	@param attributes attributes
	 * 	@throws org.xml.sax.SAXException
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes)
		throws org.xml.sax.SAXException
	{
		if (qName.equals(PrintData.XML_TAG))
		{
			String name = attributes.getValue(PrintData.XML_ATTRIBUTE_NAME);
			if (m_pd == null)
			{
				m_pd = new PrintData(m_ctx, name);
				push(m_pd);
			}
			else
			{
				PrintData temp = new PrintData(m_ctx, name);
				m_curPD.addNode(temp);
				push(temp);
			}
		}
		else if (qName.equals(PrintData.XML_ROW_TAG))
		{
			m_curPD.addRow(false, 0);
		}
		else if (qName.equals(PrintDataElement.XML_TAG))
		{
			m_curPDEname = attributes.getValue(PrintDataElement.XML_ATTRIBUTE_NAME);
			m_curPDEvalue = new StringBuffer();
		}
	}	//	startElement

	/**
	 *	Receive notification of character data inside an element.
	 *
	 * 	@param ch buffer
	 * 	@param start start
	 * 	@param length length
	 * 	@throws SAXException
	 */
	public void characters (char ch[], int start, int length)
		throws SAXException
	{
		m_curPDEvalue.append(ch, start, length);
	}	//	characters

	/**
	 *	Receive notification of the end of an element.
	 * 	@param uri namespace
	 * 	@param localName simple name
	 * 	@param qName qualified name
	 * 	@throws SAXException
	 */
	public void endElement (String uri, String localName, String qName)
		throws SAXException
	{
		if (qName.equals(PrintData.XML_TAG))
		{
			pop();
		}
		else if (qName.equals(PrintDataElement.XML_TAG))
		{
			m_curPD.addNode(new PrintDataElement(m_curPDEname, m_curPDEvalue.toString(),0, null));
		}
	}	//	endElement

	/*************************************************************************/

	/**	Stack						*/
	private ArrayList<PrintData>	m_stack = new ArrayList<PrintData>();

	/**
	 * 	Push new PD on Stack and set m_cutPD
	 * 	@param newPD new PD
	 */
	private void push (PrintData newPD)
	{
		//	add
		m_stack.add(newPD);
		m_curPD = newPD;
	}	//	push

	/**
	 * 	Pop last PD from Stack and set m_cutPD
	 */
	private void pop ()
	{
		//	remove last
		if (m_stack.size() > 0)
			m_stack.remove(m_stack.size()-1);
		//	get previous
		if (m_stack.size() > 0)
			m_curPD = (PrintData)m_stack.get(m_stack.size()-1);
	}	//	pop

}	//	PrintDataHandler
