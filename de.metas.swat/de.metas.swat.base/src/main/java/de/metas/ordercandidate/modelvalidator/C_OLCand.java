package de.metas.ordercandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.IOLCandValdiatorBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.purchasing.api.IBPartnerProductDAO;

// Note: this model validator used to have the class name 'OLCand'
@Validator(I_C_OLCand.class)
public class C_OLCand
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_IsError)
	public void onIsErrorUnset(final I_C_OLCand olCand)
	{
		if (!olCand.isError())
		{
			olCand.setAD_Note_ID(0);
			olCand.setErrorMsg(null);
		}
	}

	/**
	 * Method is fired before an order candidate is deleted.<br>
	 * It deletes all {@link I_C_Order_Line_Alloc}s referencing the given order candidate.
	 *
	 * @param olCand
	 */
	// 03472
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_C_OLCand olCand)
	{
		final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
		final List<I_C_Order_Line_Alloc> olasToDelete = olCandDAO.retrieveAllOlas(olCand);

		for (final I_C_Order_Line_Alloc ola : olasToDelete)
		{
			InterfaceWrapperHelper.delete(ola);
		}
	}

	/**
	 * Reset the pricing system if there is a new C_BPArtner or C_BPartner location.<br>
	 * The {@link de.metas.ordercandidate.spi.IOLCandValdiator} framework is then supposed to call {@link de.metas.ordercandidate.api.IOLCandBL} to come up with the then-correct pricing system.
	 * 
	 * @param olCand
	 * @task http://dewiki908/mediawiki/index.php/09686_PricingSystem_sometimes_not_updated_in_C_OLCand_%28105127201494%29
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {
					I_C_OLCand.COLUMNNAME_C_BPartner_ID,
					I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void resetPricingSytem(final I_C_OLCand olCand)
	{
		olCand.setM_PricingSystem(null);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void validateC_OLCand(final I_C_OLCand olCand)
	{
		final IOLCandValdiatorBL olCandValdiatorBL = Services.get(IOLCandValdiatorBL.class);
		if (olCandValdiatorBL.isValidationProcessInProgress())
		{
			return; // we are already within the validation process. no need to call the logic from here.
		}
		olCandValdiatorBL.validate(olCand);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_OLCand.COLUMNNAME_C_BPartner_ID, I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID,
					I_C_OLCand.COLUMNNAME_M_Product_ID, I_C_OLCand.COLUMNNAME_M_Product_Override_ID })
	public void setProductDescription(final I_C_OLCand olCand)
	{
		if (!Check.isEmpty(olCand.getProductDescription(), true))
		{
			return; // if olCand is new and product description is already set, do not copy anything
		}

		//
		// Services
		final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
		final I_M_Product product = olCandEffectiveValuesBL.getM_Product_Effective(olCand);
		if (partner == null || product == null)
		{
			return; // don't try to set them unless we have both the product and partner
		}

		final I_C_BPartner_Product bpp = InterfaceWrapperHelper.create(
				bpartnerProductDAO.retrieveBPartnerProductAssociation(partner, product),
				I_C_BPartner_Product.class);

		final String productDescriptionToUse;
		if (bpp != null)
		{
			//
			// If we have a BPP association, first try it's ProductDescription, then it's ProductName, then fallback to M_Product.Name
			String bppDescription = bpp.getProductDescription(); // BPP.ProductDescription
			if (Check.isEmpty(bppDescription, true))
			{
				bppDescription = bpp.getProductName(); // BPP.ProductName fallback
			}
			if (Check.isEmpty(bppDescription, true))
			{
				bppDescription = product.getName(); // M_Product.Name fallback
			}
			productDescriptionToUse = bppDescription;
		}
		else
		{
			//
			// If we don't have a BPP association, just use the M_Product.Name
			productDescriptionToUse = product.getName();
		}
		olCand.setProductDescription(productDescriptionToUse);
	}
}
