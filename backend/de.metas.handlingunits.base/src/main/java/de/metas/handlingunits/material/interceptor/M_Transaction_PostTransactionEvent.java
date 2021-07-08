package de.metas.handlingunits.material.interceptor;

import de.metas.handlingunits.material.interceptor.transactionevent.TransactionEventFactory;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptor;
import de.metas.handlingunits.material.interceptor.transactionevent.TransactionDescriptorFactory;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

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
@Component
public class M_Transaction_PostTransactionEvent
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final PostMaterialEventService materialEventService;
	private final TransactionEventFactory transactionEventCreator;
	private final TransactionDescriptorFactory transactionDescriptorFactory;

	public M_Transaction_PostTransactionEvent(
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final TransactionEventFactory transactionEventCreator)
	{
		this.materialEventService = materialEventService;
		this.transactionEventCreator = transactionEventCreator;
		this.transactionDescriptorFactory = new TransactionDescriptorFactory();
	}

	/**
	 * Note: it's important to enqueue the transaction after it was saved and before it is deleted, because we need its ID.
	 *
	 * task https://github.com/metasfresh/metasfresh/issues/710
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_Transaction_ID */
	})
	public void fireTransactionEvent(
			@NonNull final I_M_Transaction transactionRecord,
			@NonNull final ModelChangeType type)
	{
		final TransactionDescriptor transaction = transactionDescriptorFactory.ofRecord(transactionRecord);
		final boolean deleted = type.isDelete() || ModelChangeUtil.isJustDeactivated(transactionRecord);

		trxManager.runAfterCommit(() -> createAndPostEventsNow(transaction, deleted));
	}

	private void createAndPostEventsNow(@NonNull final TransactionDescriptor transaction, final boolean deleted)
	{
		final List<MaterialEvent> events = transactionEventCreator.createEventsForTransaction(transaction, deleted);
		for (final MaterialEvent event : events)
		{
			materialEventService.postEventNow(event);
		}
	}
}
