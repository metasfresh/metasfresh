package de.metas.handlingunits.attribute.impl;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;

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

		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		attr_WeightGross = attributesDAO.retrieveAttributeByValue(ATTR_WeightGross_Value);
		attr_WeightNet = attributesDAO.retrieveAttributeByValue(ATTR_WeightNet_Value);
		attr_WeightTare = attributesDAO.retrieveAttributeByValue(ATTR_WeightTare_Value);
		attr_WeightTareAdjust = attributesDAO.retrieveAttributeByValue(ATTR_WeightTareAdjust_Value);

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
