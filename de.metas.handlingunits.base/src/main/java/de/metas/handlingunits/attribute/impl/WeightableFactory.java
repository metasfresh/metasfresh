package de.metas.handlingunits.attribute.impl;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.util.Services;

public class WeightableFactory implements IWeightableFactory
{
	//
	// Weight related M_Attribute.Value(s)
	public static final String ATTR_WeightGross_Value = "WeightGross";
	public static final String ATTR_WeightNet_Value = "WeightNet";
	public static final String ATTR_WeightTare_Value = "WeightTare";
	public static final String ATTR_WeightTareAdjust_Value = "WeightTareAdjust";

	//
	// Weight attributes
	private I_M_Attribute attr_WeightGross;
	private I_M_Attribute attr_WeightNet;
	private I_M_Attribute attr_WeightTare;
	private I_M_Attribute attr_WeightTareAdjust;

	public WeightableFactory()
	{
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		attr_WeightGross = attributesDAO.retrieveAttributeByValue(ATTR_WeightGross_Value);
		attr_WeightNet = attributesDAO.retrieveAttributeByValue(ATTR_WeightNet_Value);
		attr_WeightTare = attributesDAO.retrieveAttributeByValue(ATTR_WeightTare_Value);
		attr_WeightTareAdjust = attributesDAO.retrieveAttributeByValue(ATTR_WeightTareAdjust_Value);
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
		return attr_WeightGross;
	}

	public I_M_Attribute getWeightNetAttribute()
	{
		return attr_WeightNet;
	}

	public I_M_Attribute getWeightTareAttribute()
	{
		return attr_WeightTare;
	}

	public I_M_Attribute getWeightTareAdjustAttribute()
	{
		return attr_WeightTareAdjust;
	}
}
