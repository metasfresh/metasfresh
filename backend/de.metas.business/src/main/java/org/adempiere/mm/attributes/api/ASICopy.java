package org.adempiere.mm.attributes.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import com.google.common.base.Preconditions;

import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Helper class used to copy a given ASI.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ASICopy
{

	public static ASICopy newInstance(@NonNull final I_M_AttributeSetInstance fromASI)
	{
		return new ASICopy(fromASI);
	}

	public static ASICopy newInstance(@NonNull final AttributeSetInstanceId fromAttributeSetInstanceId)
	{
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);
		final I_M_AttributeSetInstance fromASI = attributesDAO.getAttributeSetInstanceById(fromAttributeSetInstanceId);
		return new ASICopy(fromASI);
	}

	private final transient IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);
	private final I_M_AttributeSetInstance _fromASI;
	private AttributeSetId _overrideAttributeSetId;

	private List<Predicate<I_M_AttributeInstance>> attributeInstanceFilters = null;

	private ASICopy(@NonNull final I_M_AttributeSetInstance fromASI)
	{
		_fromASI = fromASI;
	}

	private I_M_AttributeSetInstance getFromASI()
	{
		return _fromASI;
	}

	/**
	 * Sets a M_AttributeSet_ID to override the one that is coming from "fromASI".
	 *
	 * If the parameter is zero or negative the it will be ignored, so the attribute set from "fromASI" will be used.
	 *
	 * @param overrideAttributeSetId
	 */
	public ASICopy overrideAttributeSetId(final AttributeSetId overrideAttributeSetId)
	{
		_overrideAttributeSetId = overrideAttributeSetId;
		return this;
	}

	private AttributeSetId getOverrideAttributeSetId()
	{
		return _overrideAttributeSetId;
	}

	/**
	 * Adds a filter to attribute instances of "fromASI".
	 *
	 * @param filter
	 */
	public ASICopy filter(final Predicate<I_M_AttributeInstance> filter)
	{
		Preconditions.checkNotNull(filter, "filter is null");
		if (attributeInstanceFilters == null)
		{
			attributeInstanceFilters = new ArrayList<>();
		}
		attributeInstanceFilters.add(filter);

		return this;
	}

	/**
	 * Creates and returns a copy of "fromASI".
	 */
	public I_M_AttributeSetInstance copy()
	{
		final I_M_AttributeSetInstance fromASI = getFromASI();

		//
		// Copy ASI header
		final I_M_AttributeSetInstance toASI;
		{
			toASI = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, fromASI);
			InterfaceWrapperHelper.copyValues(fromASI, toASI, true); // honorIsCalculated=true

			final AttributeSetId overrideAttributeSetId = getOverrideAttributeSetId();
			if (overrideAttributeSetId != null)
			{
				toASI.setM_AttributeSet_ID(overrideAttributeSetId.getRepoId());
			}

			InterfaceWrapperHelper.save(toASI);
		}

		//
		// Copy attribute instances
		for (final I_M_AttributeInstance fromAI : attributesDAO.retrieveAttributeInstances(fromASI))
		{
			// Check/skip attribute instance
			if (isSkip(fromAI))
			{
				continue;
			}

			final I_M_AttributeInstance toAI = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, toASI);
			InterfaceWrapperHelper.copyValues(fromAI, toAI, true); // honorIsCalculated=true
			toAI.setAD_Org_ID(toASI.getAD_Org_ID());
			toAI.setM_AttributeSetInstance(toASI);
			InterfaceWrapperHelper.save(toAI);
		}

		//
		return toASI;
	}

	private boolean isSkip(final I_M_AttributeInstance fromAI)
	{
		if (attributeInstanceFilters == null || attributeInstanceFilters.isEmpty())
		{
			return false; // don't skip
		}

		for (final Predicate<I_M_AttributeInstance> filter : attributeInstanceFilters)
		{
			final boolean accept = filter.test(fromAI);
			if (!accept)
			{
				return true; // skip
			}
		}

		return false; // don't skip
	}

}
