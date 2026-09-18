package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/** {@code C_Order_MFGWarehouse_Report.DocumentType}: which of an order's checkup sheets a report is. */
public enum OrderCheckupDocumentType implements ReferenceListAwareEnum
{
	/** One sheet per manufacturing workflow, listing the order lines made there. */
	Warehouse(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse),

	/** One sheet for the whole order, aggregated on plant level for transportation and the plant manager. */
	Plant(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant);

	@Getter
	private final String code;

	OrderCheckupDocumentType(@NonNull final String code)
	{
		this.code = code;
	}

	public static OrderCheckupDocumentType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static OrderCheckupDocumentType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public boolean isWarehouse() {return Warehouse == this;}

	public boolean isPlant() {return Plant == this;}

	private static final ValuesIndex<OrderCheckupDocumentType> index = ReferenceListAwareEnums.index(values());
}
