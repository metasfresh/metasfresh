package de.metas.material.cockpit.stock;

import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
				-1,
				null);
	}

	public static StockChangeSourceInfo ofTransactionId(final int transactionId)
	{
		return new StockChangeSourceInfo(
				(ResetStockPInstanceId)null,
				Check.assumeGreaterThanZero(transactionId, "transactionId"),
				null);
	}

	/**
	 * Source info for an MD_Stock qty re-key triggered by an HU attribute change.
	 * transactionId is -1 (no M_Transaction involved), resetStockPInstanceId is null.
	 * The huId is retained for provenance.
	 */
	public static StockChangeSourceInfo ofHuAttributeChange(final int huId)
	{
		Check.assumeGreaterThanZero(huId, "huId");
		return new StockChangeSourceInfo(
				(ResetStockPInstanceId)null,
				-1,
				huId);
	}

	ResetStockPInstanceId resetStockAdPinstanceId;
	int transactionId;

	/** HU that triggered the attribute change; {@code null} if not applicable. */
	@Nullable
	Integer huId;

	private StockChangeSourceInfo(
			final ResetStockPInstanceId resetStockAdPinstanceId,
			final int transactionId,
			@Nullable final Integer huId)
	{
		this.resetStockAdPinstanceId = resetStockAdPinstanceId;
		this.transactionId = transactionId;
		this.huId = huId;
	}

}
