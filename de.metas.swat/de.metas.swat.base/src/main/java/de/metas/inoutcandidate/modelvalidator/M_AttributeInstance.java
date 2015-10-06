package de.metas.inoutcandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.ModelValidator;

import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.impl.ImmutableStorageAttributeSegment;
import de.metas.storage.impl.ImmutableStorageSegment;

@Validator(I_M_AttributeInstance.class)
public class M_AttributeInstance
{
	/**
	 * Fire {@link IStorageSegment} changed when an {@link I_M_AttributeInstance} is changed.
	 *
	 * NOTE: there is no point to trigger this on AFTER_NEW because then an segment change will be fired on other level.
	 *
	 * @param ai attribute instance
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_M_AttributeInstance.COLUMNNAME_IsActive,
					I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID,
					I_M_AttributeInstance.COLUMNNAME_Value,
					I_M_AttributeInstance.COLUMNNAME_ValueNumber
			})
	public void fireAttributeSegmentChanged(final I_M_AttributeInstance ai)
	{
		final Set<Integer> productIds = ListUtils.asSet((Integer)null); // any product
		final Set<Integer> bpartnerIds = ListUtils.asSet((Integer)null); // any bpartner
		final Set<Integer> locatorIds = ListUtils.asSet((Integer)null); // any locator
		final Set<IStorageAttributeSegment> attributeSegments = new HashSet<>();

		//
		// Create and add Attribute Segments
		{
			final int attributeSetInstanceId = ai.getM_AttributeSetInstance_ID();
			final int attributeId = ai.getM_Attribute_ID();
			final ImmutableStorageAttributeSegment attributeSegment = new ImmutableStorageAttributeSegment(attributeSetInstanceId, attributeId);
			attributeSegments.add(attributeSegment);
		}

		//
		// Notify that a storage segment was changed
		final ImmutableStorageSegment storageSegment = new ImmutableStorageSegment(productIds, bpartnerIds, locatorIds, attributeSegments);
		Services.get(IStorageListeners.class).notifyStorageSegmentChanged(storageSegment);
	}
}
