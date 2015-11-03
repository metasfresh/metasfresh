package de.metas.handlingunits.ddorder.api;

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

import java.util.Collection;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;

public interface IHUDDOrderBL extends ISingletonService
{
	/**
	 * Creates a Movement-Receipt and a Movement-Shipment (with one line each) for all HUs which are assigned to given <code>ddOrderLine</code>.<br>
	 * It also unassigns those HUs from the DD Order Line and assigns them to both created movements' lines.
	 * <p>
	 * Notes:
	 * <ul>
	 * <li>the method assumes that there is <b>at least one</b> HU assigned to the line</li>
	 * <li>the HUs are assigned via <code>M_HU_Assignment</code>, see {@link de.metas.handlingunits.IHUAssignmentDAO#retrieveHUAssignmentsForModel(Object)}</li>
	 * <li>the method retrieves those assigned HUs' {@link IHUProductStorage}s and then calls {@link #createMovements(I_DD_OrderLine, Collection)}</li>
	 * </ul>
	 *
	 *
	 * @param ddOrderLine
	 *
	 */
	void createMovements(I_DD_OrderLine ddOrderLine);

	/**
	 * Create Movement-Receipt and Movement-Shipment for given {@link IHUProductStorage}s.<br>
	 * Note that this method does most of the actual work when you call {@link #createMovements(I_DD_OrderLine)}.
	 *
	 * @param ddOrderLines
	 * @param huProductStorages the storages whose HUs are unassigned from the given <code>ddOrderLine</code> and assigned to the two new movement lines.
	 * @see #createMovements(I_DD_OrderLine)
	 */
	void createMovements(Collection<I_DD_OrderLine> ddOrderLine, Collection<IHUProductStorage> huProductStorages);

	/**
	 * Close given distribution order line.
	 * 
	 * @param ddOrderLine
	 */
	void closeLine(I_DD_OrderLine ddOrderLine);

	/**
	 * Unassigns given HUs from given DD order line.
	 * 
	 * @param ddOrderLine
	 * @param hus HUs to unassign
	 */
	void unassignHUs(I_DD_OrderLine ddOrderLine, Collection<I_M_HU> hus);
}
