package de.metas.handlingunits.attribute.storage.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Creates {@link IAttributeStorage} for {@link I_M_AttributeSetInstance}.
 *
 * @author tsa
 *
 */
public class ASIAttributeStorageFactory extends AbstractModelAttributeStorageFactory<I_M_AttributeSetInstance, ASIAttributeStorage>
{

	@Override
	public boolean isHandled(final Object model)
	{
		if (model == null)
		{
			return false;
		}

		return InterfaceWrapperHelper.isInstanceOf(model, I_M_AttributeSetInstance.class);
	}

	@Override
	protected I_M_AttributeSetInstance getModelFromObject(final Object modelObj)
	{
		return InterfaceWrapperHelper.create(modelObj, I_M_AttributeSetInstance.class);
	}

	@Override
	protected ArrayKey mkKey(final I_M_AttributeSetInstance model)
	{
		return Util.mkKey(model.getClass().getName(), model.getM_AttributeSetInstance_ID());
	}

	@Override
	protected ASIAttributeStorage createAttributeStorage(final I_M_AttributeSetInstance model)
	{
		return new ASIAttributeStorage(this, model);
	}
}
