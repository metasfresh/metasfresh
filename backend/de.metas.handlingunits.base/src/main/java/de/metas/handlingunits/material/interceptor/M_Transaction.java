package de.metas.handlingunits.material.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import lombok.NonNull;

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
	public static final M_Transaction INSTANCE = new M_Transaction();

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
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */
	})
	public void fireTransactionEvent(
			@NonNull final I_M_Transaction transaction,
			@NonNull final ModelChangeType type)
	{
		final TransactionDescriptor transactionDescriptor = TransactionDescriptor.ofRecord(transaction);
		final boolean deleted = type.isDelete() || InterceptorUtil.isJustDeactivated(transaction);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trxEvent -> createAndPostEventsNow(transactionDescriptor, deleted));
	}

	private void createAndPostEventsNow(final TransactionDescriptor transaction, final boolean deleted)
	{
		final List<MaterialEvent> events = new ArrayList<>();
		events.addAll(M_Transaction_TransactionEventCreator.INSTANCE.createEventsForTransaction(transaction, deleted));

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		for (final MaterialEvent event : events)
		{
			materialEventService.postEventNow(event);
		}
	}
}
