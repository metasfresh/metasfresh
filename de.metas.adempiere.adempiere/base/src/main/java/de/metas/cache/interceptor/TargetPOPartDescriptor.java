package de.metas.cache.interceptor;

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


import java.lang.reflect.Method;

import org.adempiere.util.proxy.Cached;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * In case target object is an {@link PO} and {@link Cached#ignoreInstance()} is not set,
 * this method will extract TableId/ID/TrxName from {@link PO} and will add them to key builder.
 * 
 * @author tsa
 *
 */
class TargetPOPartDescriptor implements ICachedMethodPartDescriptor
{
	private static final transient Logger logger = LogManager.getLogger(TargetPOPartDescriptor.class);

	public static final TargetPOPartDescriptor createIfApplies(Method method, Cached annotation)
	{
		if (annotation.ignoreInstance())
		{
			logger.debug("not including the target object in the key");
			return null;
		}

		return instance;
	}

	public static final transient TargetPOPartDescriptor instance = new TargetPOPartDescriptor();

	@Override
	public void extractKeyParts(CacheKeyBuilder keyBuilder, Object targetObject, Object[] params)
	{
		if (targetObject instanceof PO && ((PO)targetObject).get_ID() > 0)
		{
			final PO po = (PO)targetObject;
			keyBuilder.add(po.get_Table_ID());
			keyBuilder.add(po.get_ID());
			keyBuilder.add(po.get_TrxName());
		}
	}
}
