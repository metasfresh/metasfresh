package de.metas.handlingunits.inout.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;

public class HUInOutDAO implements IHUInOutDAO
{
	@Override
	public List<I_M_HU> retrieveHandlingUnits(final I_M_InOut inOut)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);

		final LinkedHashMap<Integer, I_M_HU> hus = new LinkedHashMap<Integer, I_M_HU>();
		for (final I_M_InOutLine line : lines)
		{
			final List<I_M_HU> lineHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(line);
			for (final I_M_HU hu : lineHUs)
			{
				hus.put(hu.getM_HU_ID(), hu);
			}
		}
		return new ArrayList<>(hus.values());
	}

	@Override
	public List<I_M_InOutLine> retrievePackingMaterialLines(final I_M_InOut inOut)
	{
		return retrievePackingMaterialLinesQuery(inOut)
				.list(I_M_InOutLine.class);
	}

	private final IQuery<I_M_InOutLine> retrievePackingMaterialLinesQuery(final I_M_InOut inOut)
	{
		final IQueryBuilder<de.metas.handlingunits.model.I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(de.metas.handlingunits.model.I_M_InOutLine.class, inOut)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, true);

		queryBuilder.orderBy().addColumn(org.compiere.model.I_M_InOutLine.COLUMNNAME_Line);

		return queryBuilder
				.create();
	}

	@Override
	public boolean hasPackingMaterialLines(final I_M_InOut inOut)
	{
		return retrievePackingMaterialLinesQuery(inOut)
				.match();
	}
}
