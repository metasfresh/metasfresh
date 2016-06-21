package de.metas.adempiere.form;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IPackingItem
{

	boolean isSameAs(final IPackingItem item);

	IPackingItem copy();

	I_C_UOM getC_UOM();

	int getGroupingKey();

	void setWeightSingle(final BigDecimal piWeightSingle);

	boolean canAddSchedule(final I_M_ShipmentSchedule schedToAdd);

	/**
	 * Clears current schedules and set them from given <code>packingItem</code>.
	 * 
	 * @param packingItem
	 */
	void setSchedules(final IPackingItem packingItem);

	void addSchedules(final IPackingItem packingItem);

	void addSchedules(final Map<I_M_ShipmentSchedule, BigDecimal> toAdd);

	/**
	 * 
	 * @param subtrahent
	 * @param acceptShipmentSchedulePredicate evaluates which shipment schedules shall be considered
	 * @return subtracted schedule/qty pairs
	 * @throws PackingItemSubtractException if required qty could not be fully subtracted (and there were no shipment schedules excluded by the accept predicate)
	 */
	Map<I_M_ShipmentSchedule, BigDecimal> subtract(final BigDecimal subtrahent, final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate);

	/**
	 * 
	 * @param subtrahent
	 * @return subtracted schedule/qty pairs
	 * @throws PackingItemSubtractException if required qty could not be fully subtracted
	 */
	Map<I_M_ShipmentSchedule, BigDecimal> subtract(final BigDecimal subtrahent);

	Map<I_M_ShipmentSchedule, BigDecimal> getQtys();

	BigDecimal getQtyForSched(final I_M_ShipmentSchedule sched);

	void addSingleSched(final I_M_ShipmentSchedule sched);

	int getProductId();

	I_M_Product getM_Product();

	BigDecimal retrieveVolumeSingle(final String trxName);

	BigDecimal computeWeight();

	BigDecimal retrieveWeightSingle(final String trxName);

	void setQtyForSched(final I_M_ShipmentSchedule sched, final BigDecimal qty);

	BigDecimal getQtySum();

	Set<I_M_ShipmentSchedule> getShipmentSchedules();

	void setClosed(boolean isClosed);

	boolean isClosed();

}
