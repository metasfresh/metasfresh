package org.compiere.wf.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.wf.exceptions.WorkflowNotValidException;

public interface IADWorkflowBL extends ISingletonService
{

	/**
	 * Check if the workflow is valid for given date
	 * 
	 * @param workflow
	 * @param date
	 * @return true if valid
	 */
	boolean isValidFromTo(I_AD_Workflow workflow, Date date);

	/**
	 * Check if the workflow node is valid for given date
	 * 
	 * @param node
	 * @param date
	 * @return true if valid
	 */
	boolean isValidFromTo(I_AD_WF_Node node, Date date);

	/**
	 * Validates given workflow, sets {@link I_AD_Workflow#COLUMNNAME_IsDefault} (but it does not save).
	 * 
	 * @param workflow
	 * @throws WorkflowNotValidException if workflow is not valid
	 */
	public void validate(final I_AD_Workflow workflow);

	public String getWorkflowName(int AD_Workflow_ID);

	/**
	 * Create a processing workflow for the document table given as parameter.
	 * Create the specific document nodes for this workflow and link them together.
	 * 
	 * @param document
	 * @return
	 */
	I_AD_Workflow createWorkflowForDocument(I_AD_Table document);
}
