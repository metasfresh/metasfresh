package de.metas.handlingunits.trace;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
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

	public List<I_M_HU> retrieveVhus(@NonNull final I_M_HU hu)
	{
		final List<I_M_HU> vhus = new ArrayList<>();

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		new HUIterator().setEnableStorageIteration(false)
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result afterHU(final I_M_HU currentHu)
					{
						if (handlingUnitsBL.isVirtual(currentHu))
						{
							vhus.add(currentHu);
						}
						return Result.CONTINUE;
					}
				}).iterate(hu);

		return vhus;
	}

	/**
	 *
	 * @param hu
	 * @return the {@code M_HU_ID} of the given {@code hu}'s topmost parent (or grandparent etc),
	 *         <b>or</b>{@code -1} if the given {@code hu} is not "physical" (see {@link IHandlingUnitsBL#isPhysicalHU(String)}).
	 */
	public int retrieveTopLevelHuId(@NonNull final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final String huStatus = hu.getHUStatus();
		if (handlingUnitsBL.isPhysicalHU(huStatus) || X_M_HU.HUSTATUS_Shipped.equals(huStatus))
		{
			return handlingUnitsBL.getTopLevelParent(hu).getM_HU_ID();
		}
		return -1;
	}

	public Optional<IPair<I_M_Product, BigDecimal>> retrieveProductAndQty(@NonNull final I_M_HU vhu)
	{
		final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
		final IHUStorage vhuStorage = storageFactory.getStorage(vhu);
		if (vhuStorage == null)
		{
			return Optional.empty();
		}

		final I_M_Product vhuProduct = vhuStorage.getSingleProductOrNull();
		if (vhuProduct == null)
		{
			return Optional.empty();
		}

		final BigDecimal qty = vhuStorage.getQty(vhuProduct, vhuProduct.getC_UOM());

		return Optional.of(ImmutablePair.of(vhuProduct, qty));
	}
}
