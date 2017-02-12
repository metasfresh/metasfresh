package de.metas.handlingunits.attribute.impl;

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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.impl.HandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.logging.LogManager;

public class HUPIAttributesDAO implements IHUPIAttributesDAO
{
	private static final transient Logger logger = LogManager.getLogger(HUPIAttributesDAO.class);

	@Override
	public List<I_M_HU_PI_Attribute> retrieveDirectPIAttributes(final I_M_HU_PI huPI)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);
		return retrieveDirectPIAttributes(version);
	}

	@Override
	public List<I_M_HU_PI_Attribute> retrieveDirectPIAttributes(final I_M_HU_PI_Version version)
	{
		Check.assumeNotNull(version, "version not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(version);
		final String trxName = InterfaceWrapperHelper.getTrxName(version);
		final int piVersionId = version.getM_HU_PI_Version_ID();

		return retrieveDirectPIAttributes(ctx, piVersionId, trxName);
	}

	@Cached(cacheName = I_M_HU_PI_Attribute.Table_Name
			+ "#by"
			+ "#" + I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID)
	/* package */List<I_M_HU_PI_Attribute> retrieveDirectPIAttributes(
			@CacheCtx final Properties ctx,
			final int piVersionId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_M_HU_PI_Attribute> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Attribute.class, ctx, trxName);

		queryBuilder.filter(new EqualsQueryFilter<I_M_HU_PI_Attribute>(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, piVersionId));

		queryBuilder.orderBy()
				.addColumn(I_M_HU_PI_Attribute.COLUMNNAME_SeqNo, Direction.Ascending, Nulls.Last)
				.addColumn(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, Direction.Ascending, Nulls.Last);

		final List<I_M_HU_PI_Attribute> piAttributes = queryBuilder.create()
				// Retrieve all attributes, including those inactive
				// .setOnlyActiveRecords(true)
				.listImmutable(I_M_HU_PI_Attribute.class); // immutable because this is a cached method

		//
		// If we are fetching out of transaction, make all PI Attributes readonly
		if (Services.get(ITrxManager.class).isNull(trxName))
		{
			for (final I_M_HU_PI_Attribute piAttribute : piAttributes)
			{
				InterfaceWrapperHelper.setSaveDeleteDisabled(piAttribute, true);
			}
		}

		//
		return piAttributes;
	}

	@Override
	public List<I_M_HU_PI_Attribute> retrievePIAttributes(final I_M_HU_PI_Version version)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(version);
		final int M_HU_PI_Version_ID = version.getM_HU_PI_Version_ID();
		return retrievePIAttributes(ctx, M_HU_PI_Version_ID);
	}

	@Override
	public List<I_M_HU_PI_Attribute> retrievePIAttributes(final Properties ctx, final int M_HU_PI_Version_ID)
	{
		final List<I_M_HU_PI_Attribute> attributes = new ArrayList<>();
		final Set<Integer> seenAttributeIds = new HashSet<>();

		//
		// Retrieve and add Direct Attributes
		// NOTE: we want to make use of caching, that's why we are retrieving out of transaction.
		// Anyway, this is master data and this method is not supposed to be used when you want to retrieve master data for modifying it.
		final List<I_M_HU_PI_Attribute> attributesDirect = retrieveDirectPIAttributes(ctx, M_HU_PI_Version_ID, ITrx.TRXNAME_None);
		attributes.addAll(attributesDirect);
		for (final I_M_HU_PI_Attribute piAttribute : attributesDirect)
		{
			seenAttributeIds.add(piAttribute.getM_Attribute_ID());
		}

		//
		// Retrieve an add template attributes (from NoPI)
		// only if given version is not of NoPI
		if (M_HU_PI_Version_ID != HandlingUnitsDAO.NO_HU_PI_Version_ID)
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

			final I_M_HU_PI noPI = handlingUnitsDAO.retrieveNoPI(ctx);
			final List<I_M_HU_PI_Attribute> noPIAttributes = retrieveDirectPIAttributes(noPI);

			// Iterate template attributes and add only those who were not added yet
			for (final I_M_HU_PI_Attribute noPIAttribute : noPIAttributes)
			{
				if (!seenAttributeIds.add(noPIAttribute.getM_Attribute_ID()))
				{
					// attribute was already added, skip it
					continue;
				}
				attributes.add(noPIAttribute);
			}
		}

		return attributes;
	}

	@Override
	public List<I_M_HU_PI_Attribute> retrievePIAttributes(final I_M_HU_PI huPI)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);
		return retrievePIAttributes(version);
	}

	@Override
	public boolean isTemplateAttribute(final I_M_HU_PI_Attribute huPIAttribute)
	{
		logger.trace("Checking if {} is a template attribute", huPIAttribute);
		
		//
		// If the PI attribute is from template then it's a template attribute
		if (huPIAttribute.getM_HU_PI_Version_ID() == HandlingUnitsDAO.NO_HU_PI_Version_ID)
		{
			if(!huPIAttribute.isActive())
			{
				logger.trace("Considering {} NOT a template attribute because even if it is direct template attribute it's INACTIVE", huPIAttribute);
				return false;
			}
			
			logger.trace("Considering {} a template attribute because it is direct template attribute", huPIAttribute);
			return true;
		}

		//
		// Get the Template PI attributes and search if this attribute is defined there.
		final int attributeId = huPIAttribute.getM_Attribute_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPIAttribute);
		final String trxName = InterfaceWrapperHelper.getTrxName(huPIAttribute);
		final List<I_M_HU_PI_Attribute> noPIAttributes = retrieveDirectPIAttributes(ctx, HandlingUnitsDAO.NO_HU_PI_Version_ID, trxName);
		for (final I_M_HU_PI_Attribute noPIAttribute : noPIAttributes)
		{
			if (noPIAttribute.getM_Attribute_ID() == attributeId && noPIAttribute.isActive())
			{
				logger.trace("Considering {} a template attribute because we found M_Attribute_ID={} in template attributes", huPIAttribute, attributeId);
				return true;
			}
		}

		//
		// Not a template attribute
		logger.trace("Considering {} NOT a template attribute", huPIAttribute);
		return false;
	}
}
