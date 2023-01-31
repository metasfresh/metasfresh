package de.metas.handlingunits.trace;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * The job of this service is to access the {@link I_M_HU}s and hu-items, hu-storages etc, to get the data needed by {@link HUTraceEventsService}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class HUAccessService
{
	public List<I_M_HU_Assignment> retrieveHuAssignments(@NonNull final Object model)
	{
		final TableRecordReference modelRef = TableRecordReference.of(model);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_M_HU_Assignment> huAssignments = queryBL.createQueryBuilder(I_M_HU_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, modelRef.getAD_Table_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, modelRef.getRecord_ID())
				.orderBy()
				.addColumn(I_M_HU_Assignment.COLUMN_M_HU_ID).endOrderBy()
				.create()
				.list();
		return huAssignments;
	}

	public List<I_M_HU> retrieveVhus(@NonNull final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		return handlingUnitsBL.getVHUs(huId);
	}

	public List<I_M_HU> retrieveVhus(@NonNull final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		return handlingUnitsBL.getVHUs(hu);
	}

	/**
	 *
	 * @param hu
	 * @return the {@code M_HU_ID} of the given {@code hu}'s topmost parent (or grandparent etc),
	 *         <b>or</b>{@code -1} if the given {@code hu} is not "physical" (see {@link IHandlingUnitsBL#isPhysicalHU(String)}).
	 */
	public int retrieveTopLevelHuId(@NonNull final I_M_HU hu)
	{
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		if (huStatusBL.isPhysicalHU(hu) || huStatusBL.isStatusShipped(hu))
		{
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			return handlingUnitsBL.getTopLevelParent(hu).getM_HU_ID();
		}
		return -1;
	}

	public Optional<IPair<ProductId, Quantity>> retrieveProductAndQty(@NonNull final I_M_HU vhu)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUStorage vhuStorage = storageFactory.getStorage(vhu);
		if (vhuStorage == null)
		{
			return Optional.empty();
		}

		final ProductId vhuProductId = vhuStorage.getSingleProductIdOrNull();
		if (vhuProductId == null)
		{
			return Optional.empty();
		}

		final I_C_UOM stockingUOM = Services.get(IProductBL.class).getStockUOM(vhuProductId);
		final Quantity qty = vhuStorage.getQuantity(vhuProductId, stockingUOM);

		return Optional.of(ImmutablePair.of(vhuProductId, qty));
	}
}
