package org.adempiere.ad.persistence.exceptions;

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

/**
 * Thrown by persistance layer when a model class is not supported.
 * 
 * @author tsa
 *
 */
public class ModelClassNotSupportedException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3746201325167390358L;

	public ModelClassNotSupportedException(final Object model)
	{
		super(buildMsg(model));
	}

	private static final String buildMsg(final Object model)
	{
		return "Model class is not supported: " + (model == null ? "null" : model.getClass());
	}
}
