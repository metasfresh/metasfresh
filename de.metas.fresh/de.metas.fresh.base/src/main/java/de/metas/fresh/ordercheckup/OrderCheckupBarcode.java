package de.metas.fresh.ordercheckup;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.compiere.model.I_C_OrderLine;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import lombok.EqualsAndHashCode;

/*
 * #%L
 * de.metas.fresh.base
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

/**
 * {@link I_C_Order_MFGWarehouse_ReportLine#COLUMNNAME_Barcode}'s barcode format.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@EqualsAndHashCode
public final class OrderCheckupBarcode
{
	public static OrderCheckupBarcode ofC_OrderLine_ID(final int orderLineId)
	{
		return new OrderCheckupBarcode(VERSION_1, C_OrderLine_Table_ID, orderLineId);
	}

	public static OrderCheckupBarcode fromBarcodeString(final String barcode)
	{
		final List<String> barcodeParts = Splitter.on(SEPARATOR).splitToList(barcode);
		if (barcodeParts.isEmpty())
		{
			throw new IllegalArgumentException("Invalid barcode: " + barcode);
		}

		final String version = barcodeParts.get(0);
		if (VERSION_1.equals(version))
		{
			return fromBarcodeString_v1(barcodeParts);
		}
		else
		{
			throw new IllegalArgumentException("Unknown barcode version: " + barcode);
		}
	}

	private static OrderCheckupBarcode fromBarcodeString_v1(final List<String> barcodeParts)
	{
		if (barcodeParts.size() < 3)
		{
			throw new IllegalArgumentException("Invalid barcode v1 format: " + barcodeParts);
		}

		final int adTableId = Integer.parseInt(barcodeParts.get(1));
		if (adTableId != C_OrderLine_Table_ID)
		{
			throw new IllegalArgumentException("Invalid barcode's adTableId: " + barcodeParts);
		}

		final int recordId = Integer.parseInt(barcodeParts.get(2));

		return new OrderCheckupBarcode(VERSION_1, adTableId, recordId);
	}

	private static final String SEPARATOR = "-";
	private static final transient String VERSION_1 = "1";
	/** @see I_C_OrderLine#Table_ID */
	private static final transient int C_OrderLine_Table_ID = 260;

	private final String version;
	private final int adTableId;
	private final int recordId;

	private OrderCheckupBarcode(final String version, final int adTableId, final int recordId)
	{
		Preconditions.checkArgument(version != null && !version.isEmpty(), "Invalid version: %s", version);
		Preconditions.checkArgument(adTableId > 0, "Invalid adTableId: %s", adTableId);
		Preconditions.checkArgument(recordId > 0, "Invalid recordId: %s", recordId);

		this.version = version;
		this.adTableId = adTableId;
		this.recordId = recordId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("version", version)
				.add("adTableId", adTableId)
				.add("recordId", recordId)
				.toString();
	}

	public String toBarcodeString()
	{
		// NOTE: don't use "_" because it might cause problems (see https://github.com/metasfresh/metasfresh/issues/1239#issuecomment-305407684 ).
		return version + SEPARATOR + adTableId + SEPARATOR + recordId;
	}

	public int getC_OrderLine_ID()
	{
		if (adTableId == C_OrderLine_Table_ID)
		{
			return recordId;
		}
		else
		{
			throw new IllegalStateException("Cannot get C_OrderLine_ID from " + this);
		}
	}
}
