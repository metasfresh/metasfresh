package org.adempiere.mm.attributes.api.impl;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

public class AttributeSetCalloutExecutor
{
	private static final transient Logger logger = LogManager.getLogger(AttributeSetCalloutExecutor.class);

	/**
	 * Execute the callout for the given <code>attribute</code> which was changed.
	 *
	 * Note that callouts now fire and change other attributes.
	 * <b>{@link IAttributeValueContext} is responsible for preventing setting values multiple times</b>
	 *
	 * @param attributeValueContext
	 * @param attributeSet
	 * @param attribute
	 * @param valueNew
	 * @param valueOld
	 */
	public final void executeCallout(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueNew,
			final Object valueOld)
	{
		// Get the M_Attribute from storage (to make sure we are using the same instance of it)
		final I_M_Attribute attributeCurrent = attributeSet.getAttributeByIdIfExists(attribute.getM_Attribute_ID());
		if (attributeCurrent == null)
		{
			if (logger.isWarnEnabled())
			{
				final AdempiereException ex = new AdempiereException("Skip executing the callout because the attribute was not found in attributesSet."
						+ "\n Attribute: " + attribute
						+ "\n AttributesSet: " + attributeSet
						+ "\n Context: " + attributeValueContext
						+ "\n Value Old:" + valueOld
						+ "\n Value New:" + valueNew);
				logger.warn(ex.getLocalizedMessage(), ex);
			}
			return;
		}

		final IAttributeValueCallout callout = attributeSet.getAttributeValueCallout(attributeCurrent);
		executeCallout(attributeValueContext, callout, attributeSet, attributeCurrent, valueNew, valueOld);
	}

	private final List<IAttributeValueCallout> calloutsActive = new ArrayList<IAttributeValueCallout>();

	private void executeCallout(
			final IAttributeValueContext attributeValueContext,
			final IAttributeValueCallout callout,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueNew,
			final Object valueOld)
	{
		if (calloutsActive.contains(callout))
		{
			// Callout is already running, not finished yet.
			// We shall skip executing it again because otherwise we will end up in infinite recursion
			if (logger.isWarnEnabled())
			{
				logger.warn("Skip executing callout " + callout + " because it's already running: " + calloutsActive);
			}

			return;
		}

		try
		{
			calloutsActive.add(callout);
			callout.onValueChanged(attributeValueContext, attributeSet, attribute, valueOld, valueNew);
		}
		finally
		{
			calloutsActive.remove(callout);
		}
	}

	@Override
	public String toString()
	{
		return "AttributeSetCalloutExecutor [calloutsActive=" + calloutsActive + "]";
	}

}
