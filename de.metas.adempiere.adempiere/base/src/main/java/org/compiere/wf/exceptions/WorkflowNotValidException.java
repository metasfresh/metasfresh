package org.compiere.wf.exceptions;

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


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Workflow;

import de.metas.util.Check;

public class WorkflowNotValidException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6104914793209144328L;

	public WorkflowNotValidException(final I_AD_Workflow workflow, final String message)
	{
		super(buildMsg(workflow, message));
	}

	private final static String buildMsg(I_AD_Workflow workflow, String message)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@WorflowNotValid@: ").append(workflow.getName());
		if (!Check.isEmpty(message, true))
		{
			sb.append("\n").append(message);
		}
		return sb.toString();
	}
}
