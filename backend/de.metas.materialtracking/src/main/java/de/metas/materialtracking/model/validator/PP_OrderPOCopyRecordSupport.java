/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.materialtracking.model.validator;

import com.google.common.collect.ImmutableList;
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.materialtracking.model.I_PP_Order;
import lombok.NonNull;
import org.compiere.model.PO;
import org.compiere.model.copy.ValueToCopy;

import java.math.BigDecimal;
import java.util.List;

public class PP_OrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return ImmutableList.of();
	}

	/**
	 * QtyOrdered: This is needed because
	 * 1. QtyOrdered is updated initially in a callout to value of QtyEntered, so at clone nothing happens if we don't copy it
	 * 2. QtyOrdered is updated when Closing the Order to QtyDelivered, see {@link org.eevolution.api.impl.PPOrderBL#closeQtyOrdered(org.eevolution.model.I_PP_Order)}
	 */
	@Override
	protected ValueToCopy getValueToCopy_Before(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		if (I_PP_Order.COLUMNNAME_QtyOrdered.equals(columnName))
		{
			final BigDecimal qtyOrderedBeforeOrderClose = from.get_ValueAsBigDecimal(I_PP_Order.COLUMNNAME_QtyBeforeClose);
			final BigDecimal qtyEntered = from.get_ValueAsBigDecimal(I_PP_Order.COLUMNNAME_QtyEntered);

			return ValueToCopy.explicitValueToSet(qtyOrderedBeforeOrderClose == null || qtyOrderedBeforeOrderClose.signum() == 0 ? qtyEntered : qtyOrderedBeforeOrderClose);
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}
}
