package org.adempiere.ad.migration.xml.impl;

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


import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.migration.xml.IXMLHandler;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class MigrationDataHandler implements IXMLHandler<I_AD_MigrationData>
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	public static final String NODENAME = "Data";

	/**
	 * Node: Column - Column Name
	 */
	private static final String NODE_Column = "Column";
	private static final String NODE_AD_Column_ID = "AD_Column_ID";
	private static final String NODE_isOldNull = "isOldNull";
	private static final String NODE_oldValue = "oldValue";
	private static final String NODE_isNewNull = "isNewNull";

	@Override
	public Node toXmlNode(Document document, I_AD_MigrationData data)
	{
		logger.info("Exporting data: " + data);

		final I_AD_Column column = data.getAD_Column();
		final Element dataNode = document.createElement(NODENAME);
		dataNode.setAttribute(NODE_Column, column.getColumnName());

		// TODO: handle the case when AD_Column_ID is not present in our database.
		// Idea: export/import also the column name. In this way we can configure to not import if the column does not exist
		dataNode.setAttribute(NODE_AD_Column_ID, Integer.toString(data.getAD_Column_ID()));

		final I_AD_MigrationStep step = data.getAD_MigrationStep();
		if (!X_AD_MigrationStep.ACTION_Insert.equals(step.getAction()))
		{
			if (data.isOldNull())
				dataNode.setAttribute(NODE_isOldNull, "true");
			else
				dataNode.setAttribute(NODE_oldValue, data.getOldValue());
		}
		if (data.isNewNull() || data.getNewValue() == null)
		{
			dataNode.setAttribute(NODE_isNewNull, "true");
		}
		else
		{
			dataNode.appendChild(document.createTextNode(data.getNewValue()));
		}

		return dataNode;
	}

	@Override
	public boolean fromXmlNode(I_AD_MigrationData data, Element element)
	{
		data.setColumnName(element.getAttribute(NODE_Column));
		data.setAD_Column_ID(Integer.parseInt(element.getAttribute(NODE_AD_Column_ID)));

		data.setIsOldNull("true".equals(element.getAttribute(NODE_isOldNull)));
		data.setOldValue(element.getAttribute(NODE_oldValue));

		data.setIsNewNull("true".equals(element.getAttribute(NODE_isNewNull)));
		data.setNewValue(getText(element));

		InterfaceWrapperHelper.save(data);
		logger.info("Imported data: " + data);
		return true;
	}
	
	// thx to http://www.java2s.com/Code/Java/XML/DOMUtilgetElementText.htm
		private String getText(Element element)
		{
			StringBuffer buf = new StringBuffer();
			NodeList list = element.getChildNodes();
			boolean found = false;
			for (int i = 0; i < list.getLength(); i++)
			{
				Node node = list.item(i);
				if (node.getNodeType() == Node.TEXT_NODE)
				{
					buf.append(node.getNodeValue());
					found = true;
				}
			}
			return found ? buf.toString() : null;
		}
}
