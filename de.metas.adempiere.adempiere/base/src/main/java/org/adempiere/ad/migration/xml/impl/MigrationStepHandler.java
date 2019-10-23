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


import java.util.Properties;

import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.ad.migration.service.IMigrationDAO;
import org.adempiere.ad.migration.xml.IXMLHandler;
import org.adempiere.ad.migration.xml.IXMLHandlerFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Table;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.metas.logging.LogManager;
import de.metas.util.Services;

class MigrationStepHandler implements IXMLHandler<I_AD_MigrationStep>
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	public static final String NODENAME = "Step";

	private static final String NODE_SeqNo = "SeqNo";
	private static final String NODE_StepType = "StepType";
	private static final String NODE_Comments = "Comments";
	/**
	 * Node: Table - TableName
	 */
	private static final String NODE_Table = "Table";
	private static final String NODE_AD_Table_ID = "AD_Table_ID";
	private static final String NODE_Record_ID = "Record_ID";
	private static final String NODE_DBType = "DBType";
	private static final String NODE_SQLStatement = "SQLStatement";
	private static final String NODE_RollbackStatement = "RollbackStatement";
	private static final String NODE_PO = "PO";
	private static final String NODE_Action = "Action";
	private static final String NODE_Data = "Data";

	@Override
	public Node toXmlNode(Document document, I_AD_MigrationStep step)
	{
		logger.info("Exporting step: " + Services.get(IMigrationBL.class).getSummary(step));

		final Element root = document.createElement(NODENAME);
		root.setAttribute(NODE_SeqNo, Integer.toString(step.getSeqNo()));
		root.setAttribute(NODE_StepType, step.getStepType());

		// Comments
		if (step.getComments() != null)
		{
			final Element commentNode = document.createElement(NODE_Comments);
			root.appendChild(commentNode);
			commentNode.appendChild(document.createTextNode(step.getComments()));
		}

		if (X_AD_MigrationStep.STEPTYPE_ApplicationDictionary.equals(step.getStepType()))
		{
			final I_AD_Table table = step.getAD_Table();
			final Element poNode = document.createElement(NODE_PO);
			root.appendChild(poNode);

			poNode.setAttribute(NODE_Table, table.getTableName());
			poNode.setAttribute(NODE_Action, step.getAction());
			poNode.setAttribute(NODE_AD_Table_ID, Integer.toString(step.getAD_Table_ID()));
			poNode.setAttribute(NODE_Record_ID, Integer.toString(step.getRecord_ID()));

			for (final I_AD_MigrationData datum : Services.get(IMigrationDAO.class).retrieveMigrationData(step))
			{
				final Node datumNode = Services.get(IXMLHandlerFactory.class)
						.getHandler(I_AD_MigrationData.class)
						.toXmlNode(document, datum);
				if (datumNode != null)
				{
					poNode.appendChild(datumNode);
				}
			}

		}
		else if (X_AD_MigrationStep.STEPTYPE_SQLStatement.equals(step.getStepType()))
		{
			root.setAttribute(NODE_DBType, step.getDBType());

			if (step.getSQLStatement() != null)
			{
				final Element sqlNode = document.createElement(NODE_SQLStatement);
				root.appendChild(sqlNode);
				sqlNode.appendChild(document.createTextNode(step.getSQLStatement()));
			}
			if (step.getRollbackStatement() != null)
			{
				final Element rollbackNode = document.createElement(NODE_RollbackStatement);
				root.appendChild(rollbackNode);
				rollbackNode.appendChild(document.createTextNode(step.getRollbackStatement()));
			}
		}
		else
		{
			logger.warn("Unknown step type: " + step.getStepType());
			return null;
		}

		return root;
	}

	@Override
	public boolean fromXmlNode(final I_AD_MigrationStep step, final Element stepNode)
	{
		step.setSeqNo(Integer.parseInt(stepNode.getAttribute(NODE_SeqNo)));
		step.setStepType(stepNode.getAttribute(NODE_StepType));
		step.setStatusCode(X_AD_MigrationStep.STATUSCODE_Unapplied);

		final Node commentNode = stepNode.getElementsByTagName(NODE_Comments).item(0);
		if (commentNode != null)
		{
			step.setComments(MigrationHandler.getElementText(commentNode));
		}

		if (X_AD_MigrationStep.STEPTYPE_ApplicationDictionary.equals(step.getStepType()))
		{
			final NodeList children = stepNode.getElementsByTagName(NODE_PO);
			for (int i = 0; i < children.getLength(); i++)
			{
				final Element poNode = (Element)children.item(i);
				step.setAction(poNode.getAttribute(NODE_Action));
				step.setTableName(poNode.getAttribute(NODE_Table));

				step.setAD_Table_ID(Integer.parseInt(poNode.getAttribute(NODE_AD_Table_ID)));
				step.setRecord_ID(Integer.parseInt(poNode.getAttribute(NODE_Record_ID)));

				InterfaceWrapperHelper.save(step);

				final NodeList data = poNode.getElementsByTagName(NODE_Data);
				for (int j = 0; j < data.getLength(); j++)
				{
					final Properties ctx = InterfaceWrapperHelper.getCtx(step);
					final String trxName = InterfaceWrapperHelper.getTrxName(step);
					final I_AD_MigrationData stepData = InterfaceWrapperHelper.create(ctx, I_AD_MigrationData.class, trxName);
					stepData.setAD_MigrationStep(step);

					final Element dataElement = (Element)data.item(j);
					Services.get(IXMLHandlerFactory.class)
							.getHandler(I_AD_MigrationData.class)
							.fromXmlNode(stepData, dataElement);

					InterfaceWrapperHelper.save(stepData);
				}
			}
		}
		else if (X_AD_MigrationStep.STEPTYPE_SQLStatement.equals(step.getStepType()))
		{
			step.setDBType(stepNode.getAttribute(NODE_DBType));

			final Node sqlNode = stepNode.getElementsByTagName(NODE_SQLStatement).item(0);
			if (sqlNode != null)
			{
				step.setSQLStatement(MigrationHandler.getElementText(sqlNode));
			}
			final Node rollbackNode = stepNode.getElementsByTagName(NODE_RollbackStatement).item(0);
			if (rollbackNode != null)
			{
				step.setRollbackStatement(MigrationHandler.getElementText(rollbackNode));
			}
		}
		else
		{
			throw new AdempiereException("Unknown StepType: " + step.getStepType());
		}

		InterfaceWrapperHelper.save(step);
		logger.info("Imported step: " + Services.get(IMigrationBL.class).getSummary(step));
		return true;
	}
}
