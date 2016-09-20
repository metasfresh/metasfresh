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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Creates {@link IAttributeStorage} from an {@link IAttributeSetInstanceAware} model.
 *
 * @author tsa
 *
 */
public class ASIAwareAttributeStorageFactory extends AbstractModelAttributeStorageFactory<I_M_AttributeSetInstance, ASIAttributeStorage>
{
	private final IAttributeSetInstanceAwareFactoryService asiAwareFactory = Services.get(IAttributeSetInstanceAwareFactoryService.class);

	@Override
	public boolean isHandled(final Object modelObj)
	{
		final I_M_AttributeSetInstance asi = getModelFromObject(modelObj);
		return asi != null;
	}

	@Override
	protected I_M_AttributeSetInstance getModelFromObject(final Object modelObj)
	{
		if (modelObj == null)
		{
			return null;
		}

		final IAttributeSetInstanceAware asiAware = asiAwareFactory.createOrNull(modelObj);
		if (asiAware == null)
		{
			return null;
		}

		if (asiAware.getM_AttributeSetInstance_ID() <= 0)
		{
			// We are dealing with an ASI Aware model, but there is no ASI
			// Return a Null ASI marker which will be handled later.
			// NOTE: this particular case is handled by isNullModel() method which will return true
			final I_M_AttributeSetInstance asiNew = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, modelObj);
			InterfaceWrapperHelper.setSaveDeleteDisabled(asiNew, true);
			return asiNew;
		}

		final I_M_AttributeSetInstance asi = asiAware.getM_AttributeSetInstance();
		return asi;
	}

	@Override
	protected ArrayKey mkKey(final I_M_AttributeSetInstance model)
	{
		return Util.mkKey(model.getClass().getName(), model.getM_AttributeSetInstance_ID());
	}

	@Override
	protected boolean isNullModel(final I_M_AttributeSetInstance model)
	{
		if (model == null)
		{
			return true;
		}

		// Case: null marker was returned. See "getModelFromObject" method.
		if (model.getM_AttributeSetInstance_ID() <= 0)
		{
			return true;
		}

		return false;
	}

	@Override
	protected ASIAttributeStorage createAttributeStorage(final I_M_AttributeSetInstance model)
	{
		return new ASIAttributeStorage(this, model);
	}

	@Override
	public IAttributeStorage getAttributeStorageIfHandled(final Object modelObj)
	{
		final I_M_AttributeSetInstance asi = getModelFromObject(modelObj);
		if (asi == null)
		{
			return null;
		}

		// Case: this modelObj is handled by this factory but there was no ASI value set
		// => don't go forward and ask other factories but instead return an NullAttributeStorage
		if (asi.getM_AttributeSetInstance_ID() <= 0)
		{
			return NullAttributeStorage.instance;
		}

		return getAttributeStorageForModel(asi);
	}

}
