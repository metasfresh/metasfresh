package de.metas.handlingunits.attribute.impl;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_UOM;

import de.metas.handlingunits.attribute.IWeightableBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

public class WeightableBL implements IWeightableBL
{
	@Override
	public boolean isWeightableUOMType(final String uomType)
	{
		if (!X_C_UOM.UOMTYPE_Weigth.equals(uomType))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isWeightable(@Nullable final UomId uomId)
	{
		// guard against null, be tolerant, just return "not weightable"
		if (uomId == null)
		{
			return false;
		}

		final IUOMDAO uomdDao = Services.get(IUOMDAO.class);
		final I_C_UOM uomRecord = uomdDao.getById(uomId);
		final String uomType = uomRecord.getUOMType();

		return isWeightableUOMType(uomType);
	}
}
