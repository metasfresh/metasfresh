package de.metas.pricing.process;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.MutateCustomerPricesResult;
import de.metas.process.JavaProcess;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.List;

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

public class M_PricelistVersion_MutateCustomerPrices extends JavaProcess
{
	private static final int MAX_DETAIL_LINES = 20;

	private static final AdMessageKey MSG_Summary = AdMessageKey.of("MutateCustomerPrices_Summary");
	private static final AdMessageKey MSG_NewVersion = AdMessageKey.of("MutateCustomerPrices_NewVersion");
	private static final AdMessageKey MSG_Skipped = AdMessageKey.of("MutateCustomerPrices_Skipped");
	private static final AdMessageKey MSG_SkipReason_SameDay = AdMessageKey.of("MutateCustomerPrices_SkipReason_SameDay");
	private static final AdMessageKey MSG_SkipReason_NoActiveVersion = AdMessageKey.of("MutateCustomerPrices_SkipReason_NoActiveVersion");
	private static final AdMessageKey MSG_MoreLines = AdMessageKey.of("MutateCustomerPrices_MoreLines");
	private static final AdMessageKey MSG_NoneQualifies = AdMessageKey.of("MutateCustomerPrices_NoneQualifies");

	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	protected String doIt()
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(getRecord_ID());

		final MutateCustomerPricesResult result = priceListsRepo.mutateCustomerPrices(priceListVersionId, UserId.ofRepoId(getAD_User_ID()));

		return buildSummary(result);
	}

	private String buildSummary(@NonNull final MutateCustomerPricesResult result)
	{
		if (result.isEmpty())
		{
			return msgBL.getMsg(getCtx(), MSG_NoneQualifies);
		}

		final StringBuilder summary = new StringBuilder(msgBL.getMsg(getCtx(), MSG_Summary, new Object[] { result.getNewVersionsCount() }));

		final List<MutateCustomerPricesResult.Outcome> outcomes = result.getOutcomes();
		outcomes.stream().limit(MAX_DETAIL_LINES).forEach(outcome -> summary.append('\n').append(toLine(outcome)));
		if (outcomes.size() > MAX_DETAIL_LINES)
		{
			summary.append('\n').append(msgBL.getMsg(getCtx(), MSG_MoreLines, new Object[] { outcomes.size() - MAX_DETAIL_LINES }));
		}
		return summary.toString();
	}

	private String toLine(@NonNull final MutateCustomerPricesResult.Outcome outcome)
	{
		final String priceListName = priceListsRepo.getById(outcome.getPriceListId()).getName();
		if (outcome.isNewVersionCreated())
		{
			return msgBL.getMsg(getCtx(), MSG_NewVersion, new Object[] { priceListName });
		}

		final AdMessageKey reasonKey = outcome.getSkipReason() == MutateCustomerPricesResult.SkipReason.SAME_DAY_VERSION_EXISTS
				? MSG_SkipReason_SameDay
				: MSG_SkipReason_NoActiveVersion;
		return msgBL.getMsg(getCtx(), MSG_Skipped, new Object[] { priceListName, msgBL.getMsg(getCtx(), reasonKey) });
	}
}
