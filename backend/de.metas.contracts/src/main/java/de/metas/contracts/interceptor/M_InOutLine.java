package de.metas.contracts.interceptor;

import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.IFlatrateBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void updateFLatrateDataEntryQtyAdd(final I_M_InOutLine doc)
	{
		if (doc.getM_Product_ID() <= 0)
		{
			return; // nothing to do yet
		}

		flatrateBL.updateFlatrateDataEntryQty(
				loadOutOfTrx(doc.getM_Product_ID(), I_M_Product.class),
				doc.getMovementQty(),
				doc, // inOutLine
				false); // subtract = false
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void updateFLatrateDataEntryQtySubstract(final I_M_InOutLine doc)
	{
		if (doc.getM_Product_ID() <= 0)
		{
			return; // nothing to do/avoid NPE
		}
		flatrateBL.updateFlatrateDataEntryQty(
				loadOutOfTrx(doc.getM_Product_ID(), I_M_Product.class),
				doc.getMovementQty(),
				doc, // inOutLine
				true); // subtract = true
	}
}
