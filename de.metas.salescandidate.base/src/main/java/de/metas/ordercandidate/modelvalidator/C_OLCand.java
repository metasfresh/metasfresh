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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.IOLCandValidatorBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.purchasing.api.IBPartnerProductDAO;

// Note: this model validator used to have the class name 'OLCand'

@Validator(I_C_OLCand.class)
@Callout(I_C_OLCand.class)
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
	 * Calls {@link IOLCandValidatorBL#validate(I_C_OLCand)}.<br>
	 * 
	 * Before that it resets the pricing system if there is a new C_BPartner or C_BPartner_Override.<br>
	 * The {@link de.metas.ordercandidate.spi.IOLCandValidator} framework is then supposed to call {@link de.metas.ordercandidate.api.IOLCandBL} to come up with the then-correct pricing system.
	 * 
	 * @param olCand
	 * @task http://dewiki908/mediawiki/index.php/09686_PricingSystem_sometimes_not_updated_in_C_OLCand_%28105127201494%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void validateC_OLCand(final I_C_OLCand olCand)
	{
		final IOLCandValidatorBL olCandValdiatorBL = Services.get(IOLCandValidatorBL.class);
		if (olCandValdiatorBL.isValidationProcessInProgress())
		{
			return; // we are already within the validation process. no need to call the logic from here.
		}
		if (InterfaceWrapperHelper.isValueChanged(
				olCand,
				ImmutableSet.<String> of(
						I_C_OLCand.COLUMNNAME_C_BPartner_ID,
						I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)))
		{
			// task 09686
			olCand.setM_PricingSystem(null);
		}
		olCandValdiatorBL.validate(olCand);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
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

		final int orgId = product.getAD_Org_ID();

		final I_C_BPartner_Product bpp = InterfaceWrapperHelper.create(
				bpartnerProductDAO.retrieveBPartnerProductAssociation(partner, product, orgId),
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

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void onCBPartnerOverride(final I_C_OLCand olCand)
	{
		final I_C_BPartner bpartnerOverride = olCand.getC_BPartner_Override();
		if (bpartnerOverride == null)
		{
			// in case the bpartner Override was deleted, also delete the bpartner Location Override
			olCand.setC_BP_Location_Override(null);
			return;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location shipToLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, bpartnerOverride.getC_BPartner_ID(), trxName);

		if (shipToLocation == null)
		{
			// no location was found
			olCand.setC_BP_Location_Override(null);
			return;
		}

		olCand.setC_BP_Location_Override(shipToLocation);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID })
	public void onDropShipPartnerOverride(final I_C_OLCand olCand)
	{
		final I_C_BPartner dropShipPartnerOverride = olCand.getDropShip_BPartner_Override();
		if (dropShipPartnerOverride == null)
		{
			// in case the drop-ship bpartner Override was deleted, also delete the drop-ship Location Override
			olCand.setDropShip_Location_Override(null);

			return;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location dropShipLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, dropShipPartnerOverride.getC_BPartner_ID(), trxName);

		if (dropShipLocation == null)
		{
			// no location was found
			olCand.setDropShip_Location_Override(null);

			return;
		}

		olCand.setDropShip_Location_Override(dropShipLocation);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID)
	public void onHandOverPartnerOverrideIntercept(final I_C_OLCand olCand)
	{
		final boolean handoverLocatinOverrideChanged = InterfaceWrapperHelper.isValueChanged(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_Override_ID);

		if (handoverLocatinOverrideChanged)
		{
			// do nothing;
			// It is not wanted for the handoverLocationOverride to be changed if it was manually set to another value
		}

		else
		{
			updateHandoverLocationOverride(olCand);
		}
	}

	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID })
	public void onHandOverPartnerOverride(final I_C_OLCand olCand)
	{
		updateHandoverLocationOverride(olCand);
	}

	private void updateHandoverLocationOverride(I_C_OLCand olCand)
	{
		final I_C_BPartner handOverPartnerOverride = olCand.getHandOver_Partner_Override();
		if (handOverPartnerOverride == null)
		{
			// in case the handover bpartner Override was deleted, also delete the handover Location Override
			olCand.setHandOver_Location_Override(null);

			return;
		}

		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);

		final I_C_BP_Relation handoverRelation = Services.get(IBPRelationDAO.class).retrieveHandoverBPRelation(partner, handOverPartnerOverride);

		if (handoverRelation == null)
		{
			// this shall never happen, since both Handover_BPartner and Handover_BPartner_Override must come from such a bpp relation.
			// but I will leave this condition here as extra safety
			olCand.setHandOver_Location_Override(null);
		}

		org.compiere.model.I_C_BPartner_Location handOverLocation = handoverRelation.getC_BPartnerRelation_Location();

		if (handOverLocation == null)
		{
			// this should also not happen because C_BPartnerRelation_Location is mandatory
			olCand.setHandOver_Location_Override(null);

			return;
		}

		olCand.setHandOver_Location_Override(handOverLocation);

	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
}
