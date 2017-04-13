package de.metas.manufacturing.model.interceptor;

import java.time.Instant;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import de.metas.manufacturing.event.ManufacturingEventService;
import de.metas.manufacturing.event.TransactionEvent;

/*
 * #%L
 * de.metas.fresh.base
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
@Interceptor(I_M_Transaction.class)
public class M_Transaction
{
	static final M_Transaction INSTANCE = new M_Transaction();

	private M_Transaction()
	{
	}

	/**
	 * Note: it's important to enqueue the transaction after it was saved and before it is deleted, because we need its ID.
	 *
	 * @param purchaseCandidate
	 * @task https://github.com/metasfresh/metasfresh/issues/710
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */ })
	public void enqueuePurchaseCandidates(final I_M_Transaction transaction, final int timing)
	{

		final TransactionEvent event = TransactionEvent.builder()
				.transactionDeleted(timing == ModelValidator.TYPE_BEFORE_DELETE)
				.warehouseId(transaction.getM_Locator().getM_Warehouse_ID())
				.movementDate(transaction.getMovementDate())
				.productId(transaction.getM_Product_ID())
				.qty(transaction.getMovementQty())
				.reference(TableRecordReference.of(transaction))
				.when(Instant.now())
				.build();

		final String trxName = InterfaceWrapperHelper.getTrxName(transaction);
		ManufacturingEventService.get().fireEventAfterCommit(event, trxName);
	}
}
