package de.metas.ui.web.material.cockpit.rowfactory;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.util.TimeUtil;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class MainRowBucketId
{
	public static MainRowBucketId createInstanceForCockpitRecord(
			@NonNull final I_MD_Cockpit dataRecord)
	{
		return new MainRowBucketId(
				dataRecord.getM_Product_ID(),
				TimeUtil.getDay(dataRecord.getDateGeneral()));
	}

	public static MainRowBucketId createInstanceForStockRecord(
			@NonNull final I_MD_Stock stockRecord,
			@NonNull final Timestamp date)
	{
		return new MainRowBucketId(
				stockRecord.getM_Product_ID(),
				TimeUtil.getDay(date));
	}

	public static MainRowBucketId createPlainInstance(final int productId, @NonNull final Timestamp date)
	{
		return new MainRowBucketId(productId, date);
	}

	int productId;
	Timestamp date;

	private BigDecimal pmmQtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyReserved = BigDecimal.ZERO;

	private BigDecimal qtyOrdered = BigDecimal.ZERO;

	private BigDecimal qtyMaterialentnahme = BigDecimal.ZERO;

	private BigDecimal qtyMrp = BigDecimal.ZERO;

	private BigDecimal qtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyOnHand = BigDecimal.ZERO;

	private MainRowBucketId(final int productId, @NonNull final Timestamp date)
	{
		this.productId = productId;
		this.date = date;
	}



}
