/*
 * #%L
 * de.metas.business
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

package de.metas.product;

import de.metas.bpartner.BPartnerId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

@Value
@Builder
public class CockpitProduct
{
    @NonNull ProductId productId;
    @NonNull String name;
    @NonNull String value;
    @NonNull I_C_UOM uomRecord;
    @Nullable UomId packingUomId;
    @Nullable BPartnerId manufactureId;
    @Nullable String packageSize;

    public UomId getUomId()
    {
        return UomId.ofRepoId(uomRecord.getC_UOM_ID());
    }
}
