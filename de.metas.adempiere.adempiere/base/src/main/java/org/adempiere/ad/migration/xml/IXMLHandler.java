package org.adempiere.ad.migration.xml;

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


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IXMLHandler<T>
{
	/**
	 * Creates and returns an {@link Node} based on given record
	 * 
	 * @param document
	 * @param record
	 * @return create XML node
	 */
	Node toXmlNode(Document document, T record);

	/**
	 * Updates given record from XML element.
	 * 
	 * @param record
	 * @param element
	 * @return true if something was imported; false if method did not import anything
	 */
	boolean fromXmlNode(T record, Element element);

}
