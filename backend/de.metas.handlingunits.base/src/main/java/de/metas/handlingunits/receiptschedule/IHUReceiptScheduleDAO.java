package de.metas.handlingunits.receiptschedule;

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
import java.util.Properties;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.util.ISingletonService;

public interface IHUReceiptScheduleDAO extends ISingletonService
{
	/**
	 * Retrieves those {@link I_M_ReceiptSchedule_Alloc} which have M_HU_ID set.
	 */
	List<I_M_ReceiptSchedule_Alloc> retrieveAllHandlingUnitAllocations(de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, String trxName);

	/**
	 * Delete all handling units allocations
	 */
	void deleteHandlingUnitAllocations(de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, String trxName);

	/**
	 * Delete all handling units allocations which are about any of the given HUs.
	 */
	void deleteHandlingUnitAllocations(de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule, Collection<I_M_HU> husToUnassign, String trxName);

	/**
	 * Delete all Trading Unit Allocations for any of the given HUs.
	 */
	void deleteTradingUnitAllocations(I_M_ReceiptSchedule receiptSchedule, Collection<I_M_HU> tusToUnassign, String trxName);

	/**
	 * Sum-up all {@link I_M_ReceiptSchedule_Alloc#COLUMNNAME_HU_QtyAllocated} values.
	 */
	BigDecimal getQtyAllocatedOnHUs(I_M_ReceiptSchedule schedule);

	/**
	 * Retrieve NOT processed packing material receipt schedules for given <code>headerAggregationKey</code> and given product.
	 *
	 * @param packingMaterialProductId packing material's M_Product_ID
	 * @return packing material receipt schedules
	 */
	List<I_M_ReceiptSchedule> retrievePackingMaterialReceiptSchedules(Properties ctx, String headerAggregationKey, int packingMaterialProductId);

	void updateAllocationLUForTU(I_M_HU tuHU);

	/**
	 * 
	 * @param vhu may not be {@code null} and has to be a virtual HU according to {@link IHandlingUnitsBL#isVirtual(I_M_HU)}
	 */
	I_M_ReceiptSchedule retrieveReceiptScheduleForVHU(I_M_HU vhu);
}
