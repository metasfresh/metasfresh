/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.inout;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class InOutLine
{
    @Nullable InOutAndLineId inOutAndLineId;
    @Nullable OrderAndLineId orderAndLineId;
    @NonNull ProductId productId;
    @NonNull AttributeSetInstanceId attributeSetInstanceId;
    @NonNull Quantity qtyEntered;
    @NonNull Quantity movementQuantity;
    @NonNull SeqNo lineNo;
    @NonNull LocatorId locatorId;
    @Nullable YearAndCalendarId yearAndCalendarId;

    @Nullable
    public InOutLineId getId()
    {
        return inOutAndLineId != null ? inOutAndLineId.getInOutLineId() : null;
    }

    @Nullable
    public OrderId getOrderId()
    {
        return orderAndLineId != null ? orderAndLineId.getOrderId() : null;
    }

    @Nullable
    public OrderLineId getOrderLineId()
    {
        return orderAndLineId != null ? orderAndLineId.getOrderLineId() : null;
    }
}
