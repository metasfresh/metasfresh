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


import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;

public class WeightableFactory implements IWeightableFactory
{
	//
	// Weight related M_Attribute.Value(s)
	public static final String ATTR_WeightGross_Value = "WeightGross";
	public static final String ATTR_WeightNet_Value = "WeightNet";
	public static final String ATTR_WeightTare_Value = "WeightTare";
	public static final String ATTR_WeightTareAdjust_Value = "WeightTareAdjust";

	/** true if this factory is initialized */
	private boolean initialized = false;

	//
	// Weight attributes
	private I_M_Attribute attr_WeightGross;
	private I_M_Attribute attr_WeightNet;
	private I_M_Attribute attr_WeightTare;
	private I_M_Attribute attr_WeightTareAdjust;

	public WeightableFactory()
	{
		super();
		init();
	}

	/**
	 * Initialize this factory if it was not initalized before
	 */
	private final void init()
	{
		if (initialized)
		{
			return;
		}

		final Properties ctx = Env.getCtx();
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		attr_WeightGross = attributesDAO.retrieveAttributeByValue(ctx, ATTR_WeightGross_Value, I_M_Attribute.class);
		attr_WeightNet = attributesDAO.retrieveAttributeByValue(ctx, ATTR_WeightNet_Value, I_M_Attribute.class);
		attr_WeightTare = attributesDAO.retrieveAttributeByValue(ctx, ATTR_WeightTare_Value, I_M_Attribute.class);
		attr_WeightTareAdjust = attributesDAO.retrieveAttributeByValue(ctx, ATTR_WeightTareAdjust_Value, I_M_Attribute.class);

		initialized = true;
	}

	@Override
	public IWeightable createWeightableOrNull(final IAttributeSet attributeSet)
	{
		if (attributeSet instanceof IAttributeStorage)
		{
			final IAttributeStorage attributeStorage = (IAttributeStorage)attributeSet;
			final IWeightable weightable = new AttributeStorageWeightable(this, attributeStorage);
			return weightable;
		}
		else
		{
			return null;
		}
	}

	public I_M_Attribute getWeightGrossAttribute()
	{
		init();
		return attr_WeightGross;
	}

	public I_M_Attribute getWeightNetAttribute()
	{
		init();
		return attr_WeightNet;
	}

	public I_M_Attribute getWeightTareAttribute()
	{
		init();
		return attr_WeightTare;
	}

	public I_M_Attribute getWeightTareAdjustAttribute()
	{
		init();
		return attr_WeightTareAdjust;
	}
}
