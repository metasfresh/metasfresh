package de.metas.material.cockpit.stock;

import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

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
	public static StockChangeSourceInfo ofResetStockPInstanceId(@NonNull final ResetStockPInstanceId resetStockAdPinstanceId)
	{
		return new StockChangeSourceInfo(
				resetStockAdPinstanceId,
				-1);
	}

	public static StockChangeSourceInfo ofTransactionId(final int transactionId)
	{
		return new StockChangeSourceInfo(
				(ResetStockPInstanceId)null,
				Check.assumeGreaterThanZero(transactionId, "transactionId"));
	}

	ResetStockPInstanceId resetStockAdPinstanceId;
	int transactionId;

	private StockChangeSourceInfo(
			final ResetStockPInstanceId resetStockAdPinstanceId,
			final int transactionId)
	{
		this.resetStockAdPinstanceId = resetStockAdPinstanceId;
		this.transactionId = transactionId;
	}

}
