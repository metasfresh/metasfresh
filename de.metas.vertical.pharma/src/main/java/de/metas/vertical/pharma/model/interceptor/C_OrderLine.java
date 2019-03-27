package de.metas.vertical.pharma.model.interceptor;

import de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator;
import de.metas.vertical.pharma.PharmaSalesOrderLineInputValidator;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
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
import de.metas.util.Services;
import de.metas.vertical.pharma.PharmaBPartnerRepository;

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
@Callout(I_C_OrderLine.class)
@Interceptor(I_C_OrderLine.class)
@Component("de.metas.vertical.pharma.model.interceptor.C_OrderLine")
public class C_OrderLine
{
	@Autowired
	PharmaBPartnerRepository pharmaBPartnerRepo;

	@Autowired
	PharmaSalesOrderLineInputValidator pharmaSalesOrderLineInputValidator;

	@Autowired
	PharmaPurchaseOrderLineInputValidator pharmaPurchaseOrderLineInputValidator;

	@Autowired
	OrderLineRepository orderLineRepository;

	@Init
	public void registerCallout()
	{
		// this class serves as both model validator an callout
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_OrderLine.COLUMNNAME_C_BPartner_ID,
			I_C_OrderLine.COLUMNNAME_M_Product_ID })
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void validateTheProducts(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (orderLineRecord.getM_Product_ID() <= 0)
		{
			return; // in case of "before_new" there might not be a product yet
		}

		final OrderLine orderLine = orderLineRepository.ofRecord(orderLineRecord);
		final BPartnerId bPartnerId = orderLine.getBPartnerId();
		final ProductId productId = orderLine.getProductId();

		if (SOTrx.PURCHASE.equals(orderLine.getSoTrx()))
		{
			checksForPurchaseOrder(bPartnerId, productId);
		}
		else
		{
			checksForSalesOrder(bPartnerId, productId);
		}
	}

	private void checksForSalesOrder(final BPartnerId bPartnerId, final ProductId productId)
	{
		final OrderLineInputValidatorResults orderLineValidationResult = pharmaSalesOrderLineInputValidator.validate(bPartnerId, productId);

		if (orderLineValidationResult.isValid())
		{
			// the partner has permissions for buying prescribed medicine
			return;
		}

		throw new AdempiereException(orderLineValidationResult.getErrorMessage());
	}

	private void checksForPurchaseOrder(final BPartnerId bPartnerId, final ProductId productId)
	{
		final OrderLineInputValidatorResults validatorResults = pharmaPurchaseOrderLineInputValidator.validate(bPartnerId, productId);

		if (validatorResults.isValid())
		{
			// the partner has permissions for receiving the medicine
			return;
		}

		throw new AdempiereException(validatorResults.getErrorMessage());

	}

}
