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

package org.adempiere.warehouse;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Repository
public class WarehouseRepository
{
    IQueryBL queryBL = Services.get(IQueryBL.class);
    @VisibleForTesting
    public static WarehouseRepository newInstanceForUnitTesting()
    {
        Adempiere.assertUnitTestMode();
        return new WarehouseRepository();
    }

    private final CCache<WarehouseId, Warehouse> cache = CCache.<WarehouseId, Warehouse>builder()
            .tableName(I_M_Warehouse.Table_Name)
            .build();

    @NonNull
    public Warehouse getById (@NonNull final WarehouseId warehouseId)
    {
        return cache.getOrLoad(warehouseId, this::retrieveById);
    }

    private Warehouse retrieveById(@NonNull final WarehouseId warehouseId)
    {
        return fromRecord(loadOutOfTrx(warehouseId, I_M_Warehouse.class));
    }

    private Warehouse fromRecord(@NonNull final I_M_Warehouse warehouse)
    {
        return Warehouse.builder()
                .warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
                .name(warehouse.getName())
                .resourceId(ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID()))
                .build();
    }

    public ImmutableSet<WarehouseId> retrieveAllActiveIds()
    {
        return queryBL.createQueryBuilder(I_M_Warehouse.class)
                .addOnlyActiveRecordsFilter()
                .create()
                .listIds(WarehouseId::ofRepoId);
    }
}
