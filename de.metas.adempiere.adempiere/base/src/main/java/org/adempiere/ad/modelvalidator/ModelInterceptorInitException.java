package org.adempiere.ad.modelvalidator;

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
import org.compiere.model.I_AD_Client;

import de.metas.util.Check;

public class ModelInterceptorInitException extends AdempiereException
{
	private static final long serialVersionUID = -1728277074600943213L;
	private final String modelInterceptorClassName;

	public ModelInterceptorInitException(final String modelInterceptorClassName, final I_AD_Client client, final Throwable error)
	{
		super(buildMsg(modelInterceptorClassName, client, error), error);
		this.modelInterceptorClassName = modelInterceptorClassName;
	}

	private final static String buildMsg(String modelInterceptorClassName, I_AD_Client client, Throwable error)
	{
		final String message = (Check.isEmpty(modelInterceptorClassName, true) ? "(general)" : modelInterceptorClassName.trim())
				+ ": "
				+ (error == null ? "Unknown error" : error.toString())
				+ " "
				+ (client == null ? "(global)" : "(" + client.getName() + ")");

		return message;
	}

	public String getModelInterceptorClassName()
	{
		return modelInterceptorClassName;
	}
}
