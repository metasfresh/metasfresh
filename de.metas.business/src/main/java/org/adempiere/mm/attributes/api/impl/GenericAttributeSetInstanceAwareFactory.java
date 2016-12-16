package org.adempiere.mm.attributes.api.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;

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


import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Dynamically wraps a given model to {@link IAttributeSetInstanceAware} if the given model has the column names required by {@link IAttributeSetInstanceAware}.
 * 
 * @author tsa
 *
 */
/* package */class GenericAttributeSetInstanceAwareFactory implements IAttributeSetInstanceAwareFactory
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public IAttributeSetInstanceAware createOrNull(final Object referencedObj)
	{
		if (referencedObj == null)
		{
			return null;
		}

		if (!InterfaceWrapperHelper.hasModelColumnName(referencedObj, IAttributeSetInstanceAware.COLUMNNAME_M_Product_ID))
		{
			return null;
		}

		if (!InterfaceWrapperHelper.hasModelColumnName(referencedObj, IAttributeSetInstanceAware.COLUMNNAME_M_AttributeSetInstance_ID))
		{
			return null;
		}

		try
		{
			final IAttributeSetInstanceAware asiAware = InterfaceWrapperHelper.create(referencedObj, IAttributeSetInstanceAware.class);
			return asiAware;
		}
		catch (final Exception e)
		{
			final AdempiereException e2 = new AdempiereException("Cannot wrap " + referencedObj + " to " + IAttributeSetInstanceAware.class, e);
			logger.warn(e2.getLocalizedMessage(), e2);
		}

		return null;
	}
}
