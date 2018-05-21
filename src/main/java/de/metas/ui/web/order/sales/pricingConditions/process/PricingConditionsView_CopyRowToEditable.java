package de.metas.ui.web.order.sales.pricingConditions.process;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Services;

import de.metas.pricing.conditions.PriceOverride;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRow;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;

/*
 * #%L
 * metasfresh-webui-api
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

public class PricingConditionsView_CopyRowToEditable extends PricingConditionsViewBasedProcess
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().hasEditableRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("view does not have an editable row");
		}

		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a single selected row");
		}

		final PricingConditionsRow row = getSingleSelectedRow();
		if (row.isEditable())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select other row than editable row");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		patchEditableRow(createChangeRequest());
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private PricingConditionsRowChangeRequest createChangeRequest()
	{
		final PricingConditionsRow templateRow = getSingleSelectedRow();
		final PriceOverride price = extractPriceFromTemplate(templateRow);

		return PricingConditionsRowChangeRequest.builder()
				.priceChange(CompletePriceChange.of(price))
				.discount(templateRow.getDiscount())
				.paymentTerm(Optional.ofNullable(templateRow.getPaymentTerm()))
				.sourcePricingConditionsBreakId(templateRow.getPricingConditionsBreakId())
				.build();
	}

	private PriceOverride extractPriceFromTemplate(final PricingConditionsRow templateRow)
	{
		final PriceOverride price = templateRow.getPrice();
		if (!price.isNoPrice())
		{
			return price;
		}

		// In case row does not have a price then use BPartner's pricing system
		final BPartnerId bpartnerId = templateRow.getBpartnerId();
		final boolean isSOTrx = templateRow.isCustomer();
		final int pricingSystemId = bpartnersRepo.retrievePricingSystemId(bpartnerId, isSOTrx);
		if (pricingSystemId <= 0)
		{
			return PriceOverride.none();
		}

		final BigDecimal basePriceAddAmt = BigDecimal.ZERO;
		return PriceOverride.basePricingSystem(pricingSystemId, basePriceAddAmt);
	}
}
