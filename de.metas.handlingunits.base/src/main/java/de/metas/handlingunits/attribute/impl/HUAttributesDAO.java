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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;

public final class HUAttributesDAO implements IHUAttributesDAO
{
	public static final HUAttributesDAO instance = new HUAttributesDAO();

	private HUAttributesDAO()
	{
		super();
	}

	@Override
	public I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, contextProvider);
		return huAttribute;
	}

	@Override
	public void save(final I_M_HU_Attribute huAttribute)
	{
		InterfaceWrapperHelper.save(huAttribute);
	}

	@Override
	public void delete(final I_M_HU_Attribute huAttribute)
	{
		InterfaceWrapperHelper.delete(huAttribute);
	}

	@Override
	public void initHUAttributes(final I_M_HU hu)
	{
		// nothing
	}

	@Override
	public List<I_M_HU_Attribute> retrieveAttributesOrdered(final I_M_HU hu)
	{
		// NOTE: don't cache on this level. Caching is handled on upper levels

		// there are only some dozen attributes at most, so i think it'S fine to order them after loading
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.filter(new EqualsQueryFilter<I_M_HU_Attribute>(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_Attribute.class);

		// Optimization: set M_HU link
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			huAttribute.setM_HU(hu);
		}

		// Make sure they are sorted
		Collections.sort(huAttributes, HUAttributesBySeqNoComparator.instance);

		return huAttributes;
	}

	private final List<I_M_HU_Attribute> retrieveAttributes(final I_M_HU hu, final I_M_Attribute attribute)
	{
		final List<I_M_HU_Attribute> huAttributes = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Attribute.class, hu)
				.filter(new EqualsQueryFilter<I_M_HU_Attribute>(I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID()))
				.filter(new EqualsQueryFilter<I_M_HU_Attribute>(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_Attribute.class);

		// Optimization: set M_HU link
		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			huAttribute.setM_HU(hu);
		}

		return huAttributes;
	}

	@Override
	public I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final I_M_Attribute attribute)
	{
		final List<I_M_HU_Attribute> huAttributes = retrieveAttributes(hu, attribute);
		if (huAttributes.isEmpty())
		{
			return null;
		}
		else if (huAttributes.size() == 1)
		{
			return huAttributes.get(0);
		}
		else
		{
			throw new AdempiereException("More than one HU Attributes were found for " + attribute.getName() + " on " + hu.getM_HU_ID() + ": " + huAttributes);
		}
	}

	/**
	 * @return {@link NullAutoCloseable} always
	 */
	@Override
	public IAutoCloseable temporaryDisableAutoflush()
	{
		// NOTE: conceptualy this DAO implementation is continuously auto-flushing because it's saving/deleting direclty in database,
		// but we cannot disable this functionality
		return NullAutoCloseable.instance;
	}

	@Override
	public void flushAndClearCache()
	{
		// nothing because there is no internal cache
	}


}
