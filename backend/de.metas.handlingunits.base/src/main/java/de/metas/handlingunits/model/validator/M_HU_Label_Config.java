package de.metas.handlingunits.model.validator;

import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.handlingunits.report.labels.HULabelConfigRepository;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.ModelValidator;

@Interceptor(I_M_HU_Label_Config.class)
public class M_HU_Label_Config
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_HU_Label_Config record)
	{
		if (HULabelConfigRepository.extractAcceptedHuUnitTypes(record).isEmpty())
		{
			throw new FillMandatoryException(
					I_M_HU_Label_Config.COLUMNNAME_IsApplyToLUs,
					I_M_HU_Label_Config.COLUMNNAME_IsApplyToTUs,
					I_M_HU_Label_Config.COLUMNNAME_IsApplyToCUs);
		}

		// Last thing, just make sure is valid, even if the error message won't be nice
		if (record.isActive())
		{
			HULabelConfigRepository.fromRecord(record);
		}
	}
}
