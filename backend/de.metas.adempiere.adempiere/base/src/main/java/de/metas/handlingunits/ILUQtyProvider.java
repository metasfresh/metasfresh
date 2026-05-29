/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits;

import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

/**
 * This is needed because the business logic that calculates the required qty for a given order line is only available in the handling units project. Other parts of the code (PO to shipper transportation mainly) need access to some of that business logic.
 * To be removed once the handling units project is merged into the main project.
 */
@FunctionalInterface
public interface ILUQtyProvider
{
	int getRequiredLUCount(@NonNull I_C_Order order, @NonNull I_C_OrderLine orderLine);
}
