package de.metas.bpartner.product.stats.interceptor;

import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.BPartnerProductStatsEventSender;
import de.metas.bpartner.product.stats.InOutChangedEvent;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private final BPartnerProductStatsEventSender eventSender;

	public M_InOut()
	{
		eventSender = new BPartnerProductStatsEventSender();
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onComplete(final I_M_InOut inout)
	{
		if (isMaterialReturn(inout))
		{
			return;
		}

		if (isReversal(inout))
		{
			// this is the reversal completion. no need to fire event because a reversal event will be fired
			return;
		}

		final boolean reversal = false;
		eventSender.send(createInOutChangedEvent(inout, reversal));
	}

	private boolean isReversal(final I_M_InOut inout)
	{
		return inout.getReversal_ID() > 0;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void onReverse(final I_M_InOut inout)
	{
		if (isMaterialReturn(inout))
		{
			return;
		}

		final boolean reversal = true;
		eventSender.send(createInOutChangedEvent(inout, reversal));
	}

	private InOutChangedEvent createInOutChangedEvent(final I_M_InOut inout, final boolean reversal)
	{
		return InOutChangedEvent.builder()
				.bpartnerId(BPartnerId.ofRepoId(inout.getC_BPartner_ID()))
				.movementDate(TimeUtil.asInstant(inout.getMovementDate()))
				.soTrx(SOTrx.ofBoolean(inout.isSOTrx()))
				.productIds(extractProductIds(inout))
				.reversal(reversal)
				.build();
	}

	private boolean isMaterialReturn(final I_M_InOut inout)
	{
		final String movementType = inout.getMovementType();
		return Services.get(IInOutBL.class).isReturnMovementType(movementType);
	}

	private Set<ProductId> extractProductIds(final I_M_InOut inout)
	{
		final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);

		return inoutsRepo.retrieveLines(inout)
				.stream()
				.map(I_M_InOutLine::getM_Product_ID)
				.map(ProductId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
