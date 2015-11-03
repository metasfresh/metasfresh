package de.metas.handlingunits.ddorder.api.impl;

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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;
import org.eevolution.model.X_DD_OrderLine;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HUDDOrderBL implements IHUDDOrderBL
{
	@Override
	public void createMovements(final I_DD_OrderLine ddOrderLine)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(ddOrderLine);

		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(ddOrderLine);
		Check.assumeNotEmpty(hus, "Exist HUs assigned to {0}", ddOrderLine);

		final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		final List<IHUProductStorage> huProductStorages = huStorageFactory.getHUProductStorages(hus, ddOrderLine.getM_Product());

		final Collection<I_DD_OrderLine> ddOrderLines = Collections.singletonList(ddOrderLine);
		createMovements(ddOrderLines, huProductStorages);
	}

	@Override
	public void createMovements(final Collection<I_DD_OrderLine> ddOrderLines, final Collection<IHUProductStorage> huProductStorages)
	{
		Check.assumeNotEmpty(ddOrderLines, "ddOrderLines not empty");
		Check.assumeNotEmpty(huProductStorages, "huProductStorages not empty");

		//
		// Services
		final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

		//
		// Sort distribution order lines by date promised (lowest date first)
		final List<I_DD_OrderLine> ddOrderLinesSorted = new ArrayList<>(ddOrderLines);
		Collections.sort(ddOrderLinesSorted, new Comparator<I_DD_OrderLine>()
		{
			@Override
			public int compare(final I_DD_OrderLine o1, final I_DD_OrderLine o2)
			{
				final Timestamp datePromised1 = o1.getDatePromised();
				final Timestamp datePromised2 = o2.getDatePromised();
				return datePromised1.compareTo(datePromised2);
			}
		});

		//
		// Create list with alternatives; keep the sorting
		final List<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt = new ArrayList<I_DD_OrderLine_Or_Alternative>();
		for (final I_DD_OrderLine ddOrderLine : ddOrderLinesSorted)
		{
			final I_DD_OrderLine_Or_Alternative ddOrderLineConv = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.add(ddOrderLineConv);

			final List<I_DD_OrderLine_Alternative> alternatives = ddOrderDAO.retrieveAllAlternatives(ddOrderLine);
			final List<I_DD_OrderLine_Or_Alternative> alternativesConv = InterfaceWrapperHelper.createList(alternatives, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.addAll(alternativesConv);
		}

		createMovements0(ddOrderLinesOrAlt, huProductStorages);
	}

	private final void createMovements0(final Collection<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt, final Collection<IHUProductStorage> huProductStorages)
	{
		//
		// Create allocator and set distribution order lines and alternatives pool
		final DDOrderLinesAllocator ddOrderLinesAllocator = new DDOrderLinesAllocator();
		ddOrderLinesAllocator.setDDOrderLines(ddOrderLinesOrAlt);

		//
		// Allocate quantities for each given product storage
		for (final IHUProductStorage huProductStorage : huProductStorages)
		{
			ddOrderLinesAllocator.allocate(huProductStorage);
		}

		//
		// Finally, process allocations and create material movements
		ddOrderLinesAllocator.process();
	}

	@Override
	public void closeLine(final I_DD_OrderLine ddOrderLine)
	{
		ddOrderLine.setIsDelivered_Override(X_DD_OrderLine.ISDELIVERED_OVERRIDE_Yes);
		InterfaceWrapperHelper.save(ddOrderLine);
		
		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.clearHUsScheduledToMoveList(ddOrderLine);
	}

	@Override
	public void unassignHUs(final I_DD_OrderLine ddOrderLine, final Collection<I_M_HU> hus)
	{
		//
		// Unassign the given HUs from DD_OrderLine
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.unassignHUs(ddOrderLine, hus);

		//
		// Remove those HUs from scheduled to move list (task 08639)
		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.removeFromHUsScheduledToMoveList(ddOrderLine, hus);
	}
}
