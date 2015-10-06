/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *****************************************************************************/
package org.adempiere.pipo;

import java.util.Properties;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;

/**
 * Interface for handling import and export of table data to xml
 * @author Low Heng Sin
 *
 */
public interface ElementHandler {

	/**
	 * Handle start of xml element ( import ).
	 * @param ctx
	 * @param element
	 * @throws org.xml.sax.SAXException
	 */
	public void startElement (Properties ctx, Element element)
	throws org.xml.sax.SAXException;
	
	/**
	 * Handle closing of xml element ( import ).
	 * @param ctx
	 * @param element
	 * @throws SAXException
	 */
	public void endElement (Properties ctx, Element element) throws SAXException;
	
	/**
	 * Create xml element ( export ).
	 * @param ctx
	 * @param document
	 * @throws SAXException
	 */
	public void create(Properties ctx, TransformerHandler document) throws SAXException;
}
