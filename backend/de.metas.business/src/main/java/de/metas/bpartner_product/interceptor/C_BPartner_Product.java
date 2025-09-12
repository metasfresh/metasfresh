package de.metas.bpartner_product.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_C_BPartner_Product.class)
@Component
public class C_BPartner_Product
{
	private final static AdMessageKey MSG_C_BPartner_Product_Duplicate_ASI = AdMessageKey.of("C_BPartner_Product_Duplicate_ASI");

	private final IBPartnerProductBL bpartnerProductBL = Services.get(IBPartnerProductBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BPartner_Product.COLUMNNAME_M_AttributeSetInstance_ID)
	public void onASISet(final I_C_BPartner_Product bpProduct)
	{
		if (bpProduct.getM_AttributeSetInstance_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpProduct.getC_BPartner_ID());
		final ProductId productId = ProductId.ofRepoId(bpProduct.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(bpProduct.getAD_Org_ID());

		final List<I_C_BPartner_Product> existingBPartnerProductAssociations = Services.get(IBPartnerProductDAO.class).retrieveAllBPartnerProductAssociations(
				Env.getCtx(),
				bpartnerId,
				productId,
				orgId, ITrx.TRXNAME_ThreadInherited);

		final AttributeSetInstanceId bpProductASIId = AttributeSetInstanceId.ofRepoId(bpProduct.getM_AttributeSetInstance_ID());

		final boolean isASIValid = existingBPartnerProductAssociations.stream()
				.allMatch(bpProductAssociation -> isASIValid(bpProductAssociation, bpProductASIId));

		if (!isASIValid)
		{
			throw new AdempiereException(MSG_C_BPartner_Product_Duplicate_ASI).markAsUserValidationError();
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BPartner_Product.COLUMNNAME_IsExcludedFromPurchase)
	public void resetExclusionFromPurchaseReason(final I_C_BPartner_Product bpProduct)
	{
		if (!bpProduct.isExcludedFromPurchase())
		{
			bpProduct.setExclusionFromPurchaseReason(null);
		}
	}

	private boolean isASIValid(final I_C_BPartner_Product bpProduct, final AttributeSetInstanceId attributeSetInstanceId)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		if (bpProduct.getM_AttributeSetInstance_ID() <= 0)
		{
			return true;
		}

		final AttributeSetInstanceId bpProductASIId = AttributeSetInstanceId.ofRepoId(bpProduct.getM_AttributeSetInstance_ID());

		final ImmutableAttributeSet bpAttributeSet = attributeDAO.getImmutableAttributeSetById(bpProductASIId);
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(attributeSetInstanceId);

		return !attributeSet.equals(bpAttributeSet);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_BPartner_Product.COLUMNNAME_GTIN, I_C_BPartner_Product.COLUMNNAME_EAN_CU, I_C_BPartner_Product.COLUMNNAME_EAN13_ProductCode })
	private void normalizeProductCodeFields(@NonNull I_C_BPartner_Product record)
	{
		if (InterfaceWrapperHelper.isValueChanged(record, I_C_BPartner_Product.COLUMNNAME_GTIN))
		{
			final GTIN gtin = GTIN.ofNullableString(record.getGTIN());
			bpartnerProductBL.setProductCodeFieldsFromGTIN(record, gtin);
		}
		else if (InterfaceWrapperHelper.isValueChanged(record, I_C_BPartner_Product.COLUMNNAME_EAN_CU))
		{
			final EAN13 ean13 = EAN13.ofNullableString(record.getEAN_CU());
			final GTIN gtin = ean13 != null ? ean13.toGTIN() : null;
			bpartnerProductBL.setProductCodeFieldsFromGTIN(record, gtin);
		}
		// NOTE: syncing UPC to GTIN is not quite correct, but we have a lot of BPs which are relying on this logic
		else if (InterfaceWrapperHelper.isValueChanged(record, I_C_BPartner_Product.COLUMNNAME_UPC))
		{
			final GTIN gtin = GTIN.ofNullableString(record.getUPC());
			bpartnerProductBL.setProductCodeFieldsFromGTIN(record, gtin);
		}
		else if (InterfaceWrapperHelper.isValueChanged(record, I_C_BPartner_Product.COLUMNNAME_EAN13_ProductCode))
		{
			final EAN13ProductCode ean13ProductCode = EAN13ProductCode.ofNullableString(record.getEAN13_ProductCode());
			bpartnerProductBL.setProductCodeFieldsFromEAN13ProductCode(record, ean13ProductCode);
		}
	}

}
