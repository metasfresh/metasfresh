package de.metas.handlingunits.document;

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


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

/**
 * Implementations of this interface are used to manage HU allocations to a particular document line.
 *
 * NOTE: all these methods are using {@link ITrx#TRXNAME_ThreadInherited} transaction.
 *
 * @author tsa
 *
 */
public interface IHUAllocations
{
	/**
	 * Create LU/TU {@link I_M_HU_Assignment} and allocate given HUs. Assignment is fairly generic, but creating allocations depends on this interface's implementor.
	 *
	 * @param luHU
	 * @param tuHU
	 * @param vhu
	 * @param qtyToAllocate quantity to allocate
	 * @param uom qtyToAllocate's unit of measure
	 * @param deleteOldTUAllocations if true, delete ALL old allocations between the TU and the document (be careful with this, as it might delete allocations which are still desired)
	 */
	void allocate(final I_M_HU luHU, 
			final I_M_HU tuHU, 
			final I_M_HU vhu,
			final BigDecimal qtyToAllocate, 
			final I_C_UOM uom,
			final boolean deleteOldTUAllocations);

	/**
	 * Remove all assignments and allocations (directly, from database)
	 */
	void clearAssignmentsAndAllocations();

	/**
	 * Gets assigned HUs.
	 * 
	 * WARNING: this method will NOT exclude those HUs which are destroyed. We will keep this logic because there could be API which are depending to this feature.
	 * 
	 * @return assigned HUs
	 */
	public List<I_M_HU> getAssignedHUs();

	/**
	 * Assign given HUs.
	 *
	 * @param husToAssign
	 */
	void addAssignedHUs(Collection<I_M_HU> husToAssign);

	/**
	 * Unassign given HUs and also delete their allocations.
	 *
	 * @param husToUnassign
	 */
	void removeAssignedHUs(Collection<I_M_HU> husToUnassign);

	/**
	 * Unassign given HU and then mark it as destroyed.
	 *
	 * @param huToDestroy
	 */
	void destroyAssignedHU(I_M_HU huToDestroy);
}
