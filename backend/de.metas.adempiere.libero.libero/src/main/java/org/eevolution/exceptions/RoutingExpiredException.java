/**
 * 
 */
package org.eevolution.exceptions;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.sql.Timestamp;

import org.compiere.model.I_AD_Workflow;

import de.metas.material.planning.pporder.LiberoException;

/**
 * Thrown when Routing(Workflow) is not valid on given date
 * @author Teo Sarca
 */
public class RoutingExpiredException extends LiberoException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7522979292063177848L;

	public RoutingExpiredException(I_AD_Workflow wf, Timestamp date)
	{
		super(buildMessage(wf, date));
	}
	
	private static final String buildMessage(I_AD_Workflow wf, Timestamp date)
	{
		return "@NotValid@ @AD_Workflow_ID@:"+wf.getValue()+" - @Date@:"+date;
	}

}
