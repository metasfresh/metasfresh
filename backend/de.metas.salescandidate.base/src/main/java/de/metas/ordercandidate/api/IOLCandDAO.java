package de.metas.ordercandidate.api;

/*
 * #%L
 * de.metas.swat.base
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

import com.google.common.collect.ImmutableMap;
import de.metas.async.AsyncBatchId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.util.ISingletonService;
import de.metas.util.time.LocalDateInterval;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public interface IOLCandDAO extends ISingletonService
{
	// makes no sense: we can't assume uniqueness among different external systems.
	//OptionalInt getOLCandIdByExternalId(final String olCandExternalId);

	<T extends I_C_OLCand> List<T> retrieveOLCands(I_C_OrderLine ol, Class<T> clazz);

	<T extends I_C_OLCand> List<T> retrieveOLCands(OrderLineId orderLineId, Class<T> clazz);

	/**
	 * Loads the order line candidates whose AD_Table_ID and Record_ID columns match the given parameters.
	 *
	 * @return matched order candidates
	 */
	List<I_C_OLCand> retrieveReferencing(Properties ctx, String tableName, int recordId, String trxName);

	/**
	 * Loads an returns all <code>C_Order_Line_Alloc</code> records that reference the given order line.<br>
	 * Note that this includes records with <code>IsActive='N'</code> as well as records that have a different <code>AD_Client_ID</code>.
	 */
	List<I_C_Order_Line_Alloc> retrieveAllOlas(I_C_OrderLine ol);

	/**
	 * Loads an returns all <code>C_Order_Line_Alloc</code> records that reference the given order candidate.<br>
	 * Note that this includes records with <code>IsActive='N'</code> as well as records that have a different <code>AD_Client_ID</code>.
	 */
	List<I_C_Order_Line_Alloc> retrieveAllOlas(I_C_OLCand olCand);

	ImmutableMap<PoReferenceLookupKey, Integer> getNumberOfRecordsWithTheSamePOReference(Set<PoReferenceLookupKey> targetKeySet, LocalDateInterval searchingTimeWindow);

	Set<OrderId> getOrderIdsByOLCandIds(Set<OLCandId> olCandIds);

	Map<OLCandId, I_C_OLCand> retrieveByIds(Set<OLCandId> olCandIds);

	I_C_OLCand retrieveByIds(OLCandId olCandId);
	
	Map<OLCandId, OrderLineId> retrieveOLCandIdToOrderLineId(Set<OLCandId> olCandIds);

	void assignAsyncBatchId(Set<OLCandId> olCandIds, AsyncBatchId asyncBatchId);
}
