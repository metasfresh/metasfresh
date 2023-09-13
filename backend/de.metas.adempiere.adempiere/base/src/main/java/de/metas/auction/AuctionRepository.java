/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.auction;

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Auction;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class AuctionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<AuctionId> getIdByName(@NonNull final String name)
	{
		return queryBL.createQueryBuilder(I_C_Auction.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Auction.COLUMNNAME_Name, name)
				.create()
				.firstIdOnlyOptional(AuctionId::ofRepoId);
	}

	@Nullable
	public Auction getByIdOrNull(@NonNull final AuctionId auctionId)
	{
		final I_C_Auction auction = InterfaceWrapperHelper.load(auctionId.getRepoId(), I_C_Auction.class);
		if (auction == null)
		{
			return null;
		}
		return fromDB(auction);
	}

	private Auction fromDB(final I_C_Auction auction)
	{
		return Auction.builder()
				.auctionId(AuctionId.ofRepoId(auction.getC_Auction_ID()))
				.harvestingYearId(YearId.ofRepoIdOrNull(auction.getHarvesting_Year_ID()))
				.harvestingCalendarId(CalendarId.ofRepoIdOrNull(auction.getC_Harvesting_Calendar_ID()))
				.build();
	}
}
