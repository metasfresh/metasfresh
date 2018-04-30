package de.metas.ui.web.order.sales.pricingConditions.process;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PricingSystem;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.sales.pricingConditions.view.Price;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRow;
import de.metas.ui.web.order.sales.pricingConditions.view.PricingConditionsRowChangeRequest;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

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
		final Price price = extractPriceFromTemplate(templateRow);

		return PricingConditionsRowChangeRequest.builder()
				.price(price).forceChangingPriceType(true)
				.discount(templateRow.getDiscount())
				.paymentTerm(Optional.ofNullable(templateRow.getPaymentTerm()))
				.sourceDiscountSchemaBreakId(templateRow.getDiscountSchemaBreakId())
				.build();
	}

	private Price extractPriceFromTemplate(final PricingConditionsRow templateRow)
	{
		final Price price = templateRow.getPrice();
		if (!price.isNoPrice())
		{
			return price;
		}

		// In case row does not have a price then use BPartner's pricing system
		final int bpartnerId = templateRow.getBpartnerId();
		final boolean isSOTrx = templateRow.isCustomer();
		final LookupValue pricingSystem = getPricingSystemForBPartnerId(bpartnerId, isSOTrx);
		if (pricingSystem == null)
		{
			return Price.none();
		}

		final BigDecimal basePriceAddAmt = BigDecimal.ZERO;
		return Price.basePricingSystem(pricingSystem, basePriceAddAmt);
	}

	private LookupValue getPricingSystemForBPartnerId(final int bpartnerId, final boolean isSOTrx)
	{
		final int pricingSystemId = bpartnersRepo.retrievePricingSystemId(bpartnerId, isSOTrx);
		if (pricingSystemId <= 0)
		{
			return null;
		}

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;
		final LookupDataSource pricingSystemLookup = lookupFactory.searchInTableLookup(I_M_PricingSystem.Table_Name);
		return pricingSystemLookup.findById(pricingSystemId);
	}
}
