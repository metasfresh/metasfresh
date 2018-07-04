package de.metas.vertical.pharma.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineInputValidatorResults;
import de.metas.order.OrderLineRepository;
import de.metas.product.ProductId;
import de.metas.vertical.pharma.PharmaBPartner;
import de.metas.vertical.pharma.PharmaBPartnerRepository;
import de.metas.vertical.pharma.PharmaOrderLineInputValidator;

/*
 * #%L
 * metasfresh-pharma
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
@Interceptor(I_C_OrderLine.class)
@Callout(I_C_OrderLine.class)
@Component
public class C_OrderLine_PharmaInterceptor
{
	@Autowired
	PharmaBPartnerRepository pharmaBPartnerRepo;

	@Autowired
	PharmaOrderLineInputValidator pharmaOrderLineInputValidator;

	@Autowired
	OrderLineRepository orderLineRepository;

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_C_BPartner_ID,
			I_C_OrderLine.COLUMNNAME_M_Product_ID })
	@CalloutMethod(columnNames = {I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void validatePrescriptionProduct(final I_C_OrderLine orderLineRecord)
	{
		final OrderLine orderLine = orderLineRepository.ofRecord(orderLineRecord);

		if (SOTrx.PURCHASE.equals(orderLine.getSoTrx()))
		{
			// nothing to do, only applies to sales orders
			return;
		}

		final BPartnerId bpartnerId = orderLine.getBPartnerId();

		final PharmaBPartner pharmaBPartner = pharmaBPartnerRepo.getById(bpartnerId);

		if (pharmaBPartner.isHasAtLeastOnePermission())
		{
			// the partner has permissions for buying prescribed medicine
			return;
		}

		final ProductId productId = orderLine.getProductId();

		final OrderLineInputValidatorResults orderLineValidationResult = pharmaOrderLineInputValidator.validate(bpartnerId, productId);

		if (orderLineValidationResult.isValid())
		{
			// the partner has permissions for buying prescribed medicine
			return;
		}

		throw new AdempiereException(orderLineValidationResult.getErrorMessage());

	}

}
