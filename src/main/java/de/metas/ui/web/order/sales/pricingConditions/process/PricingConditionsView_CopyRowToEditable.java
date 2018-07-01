package de.metas.ui.web.order.sales.pricingConditions.process;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.util.Services;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.Percent;
import de.metas.lang.SOTrx;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRow;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest.CompletePriceChange;
import lombok.NonNull;

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
		patchEditableRow(createChangeRequest(getSingleSelectedRow()));
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private PricingConditionsRowChangeRequest createChangeRequest(@NonNull final PricingConditionsRow templateRow)
	{
		final PricingConditionsBreak templatePricingConditionsBreak = templateRow.getPricingConditionsBreak();

		PriceOverride price = templatePricingConditionsBreak.getPriceOverride();
		if (price.isNoPrice())
		{
			// In case row does not have a price then use BPartner's pricing system
			final BPartnerId bpartnerId = templateRow.getBpartnerId();
			final SOTrx soTrx = SOTrx.ofBoolean(templateRow.isCustomer());
			price = createBasePricingSystemPrice(bpartnerId, soTrx);
		}

		final Percent discount = templatePricingConditionsBreak.getDiscount();

		final PaymentTermId paymentTermIdOrNull = templatePricingConditionsBreak.getPaymentTermIdOrNull();
		final Percent paymentDiscountOverrideOrNull = templatePricingConditionsBreak.getPaymentDiscountOverrideOrNull();

		return PricingConditionsRowChangeRequest.builder()
				.priceChange(CompletePriceChange.of(price))
				.discount(discount)
				.paymentTermId(Optional.ofNullable(paymentTermIdOrNull))
				.paymentDiscount(Optional.ofNullable(paymentDiscountOverrideOrNull))
				.sourcePricingConditionsBreakId(templatePricingConditionsBreak.getId())
				.build();
	}

	private PriceOverride createBasePricingSystemPrice(final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		final PricingSystemId pricingSystemId = bpartnersRepo.retrievePricingSystemId(bpartnerId, soTrx);
		if (pricingSystemId == null)
		{
			return PriceOverride.none();
		}

		final BigDecimal basePriceAddAmt = BigDecimal.ZERO;
		return PriceOverride.basePricingSystem(pricingSystemId, basePriceAddAmt);
	}
}
