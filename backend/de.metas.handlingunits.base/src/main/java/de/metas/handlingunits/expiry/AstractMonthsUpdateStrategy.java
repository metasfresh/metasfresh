package de.metas.handlingunits.expiry;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.util.Env;

import java.util.Iterator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public abstract class AstractMonthsUpdateStrategy
{
	// services
	final IHandlingUnitsBL handlingUnitsBL;
	final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Builder
	protected AstractMonthsUpdateStrategy(@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		this.handlingUnitsBL = handlingUnitsBL;
	}

	public abstract Iterator<HuId> getAllSuitableHUs();

	public UpdateMonthsResult execute()
	{
		int countChecked = 0;
		int countUpdated = 0;

		final Iterator<HuId> allSuitableHUs = getAllSuitableHUs();
		while (allSuitableHUs.hasNext())
		{
			final HuId huId = allSuitableHUs.next();
			countChecked++;

			final boolean updated = updateTopLevelHU(huId);
			if (updated)
			{
				countUpdated++;
			}
		}

		return UpdateMonthsResult.builder()
				.countChecked(countChecked)
				.countUpdated(countUpdated)
				.build();
	}

	boolean updateTopLevelHU(@NonNull final HuId topLevelHUId)
	{
		final ClientAndOrgId clientAndOrgId = handlingUnitsDAO.getClientAndOrgId(topLevelHUId);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), clientAndOrgId);
		final IAttributeStorage huAttributes = getHUAttributes(topLevelHUId, huContext);
		return updateRecursive(huAttributes);
	}

	private boolean updateRecursive(@NonNull final IAttributeStorage huAttributes)
	{
		boolean updated = update(huAttributes);

		for (final IAttributeStorage childHUAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (updateRecursive(childHUAttributes))
			{
				updated = true;
			}
		}

		return updated;
	}

	abstract boolean update(@NonNull final IAttributeStorage huAttributes);

	private IAttributeStorage getHUAttributes(@NonNull final HuId huId, @NonNull final IHUContext huContext)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		return huContext
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
	}
}
