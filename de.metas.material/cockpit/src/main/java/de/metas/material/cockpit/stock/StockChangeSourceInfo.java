package de.metas.material.cockpit.stock;

import lombok.Value;

import de.metas.util.Check;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Value
public class StockChangeSourceInfo
{
	public static StockChangeSourceInfo ofResetStockAdPinstanceId(int resetStockAdPinstanceId)
	{
		return new StockChangeSourceInfo(
				Check.assumeGreaterThanZero(resetStockAdPinstanceId, "resetStockAdPinstanceId"),
				-1);
	}

	public static StockChangeSourceInfo ofTransactionId(int transactionId)
	{
		return new StockChangeSourceInfo(
				-1,
				Check.assumeGreaterThanZero(transactionId, "transactionId"));
	}

	int resetStockAdPinstanceId;

	int transactionId;
}
