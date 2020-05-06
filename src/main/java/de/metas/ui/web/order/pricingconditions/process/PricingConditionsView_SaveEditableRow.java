package de.metas.ui.web.order.pricingconditions.process;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsBreakChangeRequest;
import de.metas.pricing.conditions.service.PricingConditionsBreakChangeRequest.PricingConditionsBreakChangeRequestBuilder;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRow;
import de.metas.ui.web.order.pricingconditions.view.PricingConditionsRowActions;
import de.metas.util.Services;
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

public class PricingConditionsView_SaveEditableRow extends PricingConditionsViewBasedProcess
{
	private static final AdMessageKey MSG_BPARTNER_HAS_NO_PRICING_CONDITIONS = AdMessageKey.of("de.metas.ui.web.order.pricingconditions.C_BPartnerHasNoPricingConditions");
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

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
		if (!row.isEditable())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the editable row");
		}

		if (row.getPricingConditionsId() == null)
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_BPARTNER_HAS_NO_PRICING_CONDITIONS);
			return ProcessPreconditionsResolution.reject(msg);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PricingConditionsBreak pricingConditionsBreak = pricingConditionsRepo.changePricingConditionsBreak(createPricingConditionsBreakChangeRequest(getEditableRow()));

		patchEditableRow(PricingConditionsRowActions.saved(pricingConditionsBreak));

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private static PricingConditionsBreakChangeRequest createPricingConditionsBreakChangeRequest(final PricingConditionsRow row)
	{
		if (!row.isEditable())
		{
			throw new AdempiereException("Saving not editable rows is not allowed")
					.setParameter("row", row);
		}

		final PricingConditionsId pricingConditionsId = row.getPricingConditionsId();
		final PricingConditionsBreak pricingConditionsBreak = row.getPricingConditionsBreak();
		final PricingConditionsBreakId updateFromPricingConditionsBreakId = row.getCopiedFromPricingConditionsBreakId();

		return preparePricingConditionsBreakChangeRequest(pricingConditionsBreak)
				.pricingConditionsId(pricingConditionsId)
				.updateFromPricingConditionsBreakId(updateFromPricingConditionsBreakId)
				.build();
	}

	private static PricingConditionsBreakChangeRequestBuilder preparePricingConditionsBreakChangeRequest(
			@NonNull final PricingConditionsBreak pricingConditionsBreak)
	{
		return PricingConditionsBreakChangeRequest.builder()
				.pricingConditionsBreakId(pricingConditionsBreak.getId())
				.matchCriteria(pricingConditionsBreak.getMatchCriteria())

				.price(pricingConditionsBreak.getPriceSpecification())
				.discount(pricingConditionsBreak.getDiscount())

				.paymentTermId(Optional.ofNullable(pricingConditionsBreak.getPaymentTermIdOrNull()))
				.paymentDiscount(Optional.ofNullable(pricingConditionsBreak.getPaymentDiscountOverrideOrNull()));
	}
}
