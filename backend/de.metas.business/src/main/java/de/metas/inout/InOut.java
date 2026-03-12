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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.lang.SOTrx;
import de.metas.material.MovementType;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class InOut
{
    @Nullable InOutId inOutId;
    @NonNull SOTrx soTrx;
    @NonNull DocTypeId docTypeId;
    @NonNull DocStatus docStatus;
    @NonNull String docAction;
    @NonNull OrderId orderId;
    @Nullable String poReference;
    @Nullable String description;
    @NonNull WarehouseId warehouseId;
    @NonNull ClientAndOrgId clientAndOrgId;
    @NonNull BPartnerLocationAndCaptureId bPartnerLocationAndCaptureId;
    @NonNull LocalDateAndOrgId dateAcct;
    @Nullable LocalDateAndOrgId dateOrdered;
    @NonNull LocalDateAndOrgId movementDate;
    @NonNull MovementType movementType;
    @NonNull DeliveryRule deliveryRule;
    @NonNull DeliveryViaRule deliveryViaRule;
    @NonNull @Builder.Default List<InOutLine> lines = ImmutableList.of();

    public OrgId getOrgId()
    {
        return clientAndOrgId.getOrgId();
    }

    public ClientId getClientId()
    {
        return clientAndOrgId.getClientId();
    }
}
