/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.order.location;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Order;

/**
 * Marks an order whose invoice address was explicitly supplied by an order candidate, so that
 * {@code C_Order#setBillBPartnerIdFromEffectiveResolution} keeps it, also when it names the order's own partner.
 * The marker is a dynamic attribute of the order instance; it is not persisted.
 */
@UtilityClass
public class OrderBillLocationProvidedMarker
{
	private static final ModelDynAttributeAccessor<I_C_Order, Boolean> BILL_LOCATION_PROVIDED = new ModelDynAttributeAccessor<>(
			OrderBillLocationProvidedMarker.class.getName(),
			"BillLocationProvided",
			Boolean.class);

	public static IAutoCloseable temporaryMark(@NonNull final I_C_Order order)
	{
		return BILL_LOCATION_PROVIDED.temporarySetValue(order, Boolean.TRUE);
	}

	public static boolean isMarked(@NonNull final I_C_Order order)
	{
		return BILL_LOCATION_PROVIDED.is(order, Boolean.TRUE);
	}
}
