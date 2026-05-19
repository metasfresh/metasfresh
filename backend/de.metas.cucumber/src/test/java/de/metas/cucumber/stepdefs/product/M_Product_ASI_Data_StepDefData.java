package de.metas.cucumber.stepdefs.product;

import de.metas.cucumber.stepdefs.StepDefData;
import org.compiere.model.I_M_Product_ASI_Data;

/**
 * Stores {@link I_M_Product_ASI_Data} instances keyed by step-def identifier,
 * enabling cross-step references to records created by {@link M_Product_ASI_Data_StepDef}.
 *
 * @see M_Product_ASI_Data_StepDef
 */
public class M_Product_ASI_Data_StepDefData extends StepDefData<I_M_Product_ASI_Data>
{
	public M_Product_ASI_Data_StepDefData()
	{
		super(I_M_Product_ASI_Data.class);
	}
}
