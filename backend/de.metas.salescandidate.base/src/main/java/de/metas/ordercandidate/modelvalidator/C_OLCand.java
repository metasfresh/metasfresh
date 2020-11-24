package de.metas.ordercandidate.modelvalidator;

import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_C_OLCand.class)
@Callout(I_C_OLCand.class)
@Component
public class C_OLCand
{
	private final OLCandValidatorService olCandValidatorService;

	public C_OLCand(@NonNull final OLCandValidatorService olCandValidatorService)
	{
		this.olCandValidatorService = olCandValidatorService;
	}

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
	 * Calls {@link OLCandValidatorService#validate(I_C_OLCand)}.<br>
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
		if (olCandValidatorService.isValidationProcessInProgress())
		{
			return; // we are already within the validation process. no need to call the logic from here.
		}
		if (InterfaceWrapperHelper.isValueChanged(
				olCand,
				ImmutableSet.<String> of(
						I_C_OLCand.COLUMNNAME_C_BPartner_ID,
						I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID))
				&& InterfaceWrapperHelper.isUIAction(olCand))
		{
			// task 09686
			olCand.setM_PricingSystem_ID(0);
		}
		olCandValidatorService.validate(olCand);
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

		// Services
		final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);
		final I_M_Product product = olCandEffectiveValuesBL.getM_Product_Effective(olCand);
		if (partner == null || product == null)
		{
			return; // don't try to set them unless we have both the product and partner
		}

		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

		final I_C_BPartner_Product bpp = InterfaceWrapperHelper.create(
				bpartnerProductDAO.retrieveBPartnerProductAssociation(partner, product, orgId),
				I_C_BPartner_Product.class);
		if (bpp == null)
		{
			return; // nothing that we can add here with any benefit
		}

		// If we have a BPP association, first try its ProductDescription, then its ProductName
		final String productName = product.getName().trim();
		final String productDescription = firstNotEmptyTrimmed(bpp.getProductDescription(), bpp.getProductName(), productName);

		final boolean descriptionHasAddedValue = !productName.contentEquals(productDescription.trim());
		if (descriptionHasAddedValue)
		{
			olCand.setProductDescription(productDescription);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void onCBPartnerOverride(final I_C_OLCand olCand)
	{
		final BPartnerId bpartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getC_BPartner_Override_ID());
		if (bpartnerOverrideId == null)
		{
			// in case the bpartner Override was deleted, also delete the bpartner Location Override
			olCand.setC_BP_Location_Override_ID(0);
			return;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location shipToLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, bpartnerOverrideId.getRepoId(), trxName);

		if (shipToLocation == null)
		{
			// no location was found
			olCand.setC_BP_Location_Override_ID(0);
			return;
		}

		olCand.setC_BP_Location_Override_ID(shipToLocation.getC_BPartner_Location_ID());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID })
	public void onDropShipPartnerOverride(final I_C_OLCand olCand)
	{
		final BPartnerId dropShipPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getDropShip_BPartner_Override_ID());
		if (dropShipPartnerOverrideId == null)
		{
			// in case the drop-ship bpartner Override was deleted, also delete the drop-ship Location Override
			olCand.setDropShip_Location_Override_ID(0);

			return;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location dropShipLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, dropShipPartnerOverrideId.getRepoId(), trxName);

		if (dropShipLocation == null)
		{
			// no location was found
			olCand.setDropShip_Location_Override_ID(0);

			return;
		}

		olCand.setDropShip_Location_Override_ID(dropShipLocation.getC_BPartner_Location_ID());
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
	public void onHandOverPartnerOverride(@NonNull final I_C_OLCand olCand)
	{
		updateHandoverLocationOverride(olCand);
	}

	private void updateHandoverLocationOverride(@NonNull final I_C_OLCand olCand)
	{
		final BPartnerId handOverPartnerOverrideId = BPartnerId.ofRepoIdOrNull(olCand.getHandOver_Partner_Override_ID());
		if (handOverPartnerOverrideId == null)
		{
			// in case the handover bpartner Override was deleted, also delete the handover Location Override
			olCand.setHandOver_Location_Override_ID(0);

			return;
		}

		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_BPartner partner = olCandEffectiveValuesBL.getC_BPartner_Effective(olCand);

		final I_C_BP_Relation handoverRelation = Services.get(IBPRelationDAO.class).retrieveHandoverBPRelation(
				partner,
				loadOutOfTrx(handOverPartnerOverrideId.getRepoId(), I_C_BPartner.class));

		if (handoverRelation == null)
		{
			// this shall never happen, since both Handover_BPartner and Handover_BPartner_Override must come from such a bpp relation.
			// but I will leave this condition here as extra safety
			olCand.setHandOver_Location_Override_ID(0);
		}
		else
		{
			org.compiere.model.I_C_BPartner_Location handOverLocation = handoverRelation.getC_BPartnerRelation_Location();
			if (handOverLocation == null)
			{
				// this should also not happen because C_BPartnerRelation_Location is mandatory
				olCand.setHandOver_Location_Override_ID(0);

				return;
			}
			olCand.setHandOver_Location_Override_ID(handOverLocation.getC_BPartner_Location_ID());
		}
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}
}
