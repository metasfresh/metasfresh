/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.auction;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_Auction;

import java.util.Map;

public class C_Auction_StepDef
{
	private final C_Auction_StepDefData auctionTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_Auction_StepDef(@NonNull final C_Auction_StepDefData auctionTable)
	{
		this.auctionTable = auctionTable;
	}

	@And("metasfresh contains C_Auction:")
	public void add_C_Auction(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_Auction.COLUMNNAME_Name);

			final I_C_Auction auction = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_C_Auction.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_C_Auction.COLUMNNAME_Name, name)
							.create()
							.firstOnlyOrNull(I_C_Auction.class),
					() -> InterfaceWrapperHelper.newInstance(I_C_Auction.class));

			Assertions.assertThat(auction).isNotNull();

			auction.setName(name);
			auction.setDate(DataTableUtil.extractDateTimestampForColumnName(row, I_C_Auction.COLUMNNAME_Date));

			InterfaceWrapperHelper.saveRecord(auction);

			final String auctionIdentifier = DataTableUtil.extractStringForColumnName(row, StepDefConstants.TABLECOLUMN_IDENTIFIER);
			auctionTable.putOrReplace(auctionIdentifier, auction);
		}
	}
}
