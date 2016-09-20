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


import org.adempiere.mm.attributes.api.impl.AttributeSetCalloutExecutor;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;

/**
 * This class uses {@link AttributeSetCalloutExecutor} to invoke the {@link IAttributeValueCallout} of the respective given attribute
 *
 * @see IAttributeValue#getM_Attribute()
 * @see AttributeSetCalloutExecutor#executeCallout(IAttributeValueContext, org.adempiere.mm.attributes.api.IAttributeSet, I_M_Attribute, Object, Object)
 */
public class CalloutAttributeStorageListener implements IAttributeStorageListener
{
	private final AttributeSetCalloutExecutor calloutExecutor = new AttributeSetCalloutExecutor();

	@Override
	public void onAttributeValueCreated(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final Object valueOld = null;
		final Object valueNew = attributeValue.getValue();
		calloutExecutor.executeCallout(attributeValueContext, storage, attribute, valueNew, valueOld);
	}

	@Override
	public void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue, final Object valueOld)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final Object valueNew = attributeValue.getValue();
		calloutExecutor.executeCallout(attributeValueContext, storage, attribute, valueNew, valueOld);
	}

	@Override
	public void onAttributeValueDeleted(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		final I_M_Attribute attribute = attributeValue.getM_Attribute();
		final Object valueOld = attributeValue.getValue();
		final Object valueNew = null;
		calloutExecutor.executeCallout(attributeValueContext, storage, attribute, valueNew, valueOld);
	}

	@Override
	public void onAttributeStorageDisposed(IAttributeStorage storage)
	{
		// nothing
	}

	@Override
	public String toString()
	{
		return "CalloutAttributeStorageListener [calloutExecutor=" + calloutExecutor + "]";
	}


}
