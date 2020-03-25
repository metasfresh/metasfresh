package de.metas.banking.service.impl;

import java.util.List;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.payment.PaymentLinkResult;
import de.metas.banking.service.IBankStatementListener;
import de.metas.banking.service.IBankStatementListenerService;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
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

public class BankStatementListenerService implements IBankStatementListenerService
{
	private static final Logger logger = LogManager.getLogger(BankStatementListenerService.class);
	private final CompositeBankStatementListener listeners = new CompositeBankStatementListener();

	@Override
	public void addListener(@NonNull final IBankStatementListener listener)
	{
		final boolean added = listeners.addListener(listener);
		if (added)
		{
			logger.info("Registered listener: `" + listener + "`");
		}
		else
		{
			logger.warn("Failed registering listener `" + listener + "` because it was already registered: `" + listeners + "`");
		}
	}

	@Override
	public void firePaymentLinked(@NonNull final PaymentLinkResult payment)
	{
		listeners.onPaymentsLinked(ImmutableList.of(payment));
	}

	@Override
	public void firePaymentsLinked(@NonNull final List<PaymentLinkResult> payments)
	{
		if (payments.isEmpty())
		{
			return;
		}

		listeners.onPaymentsLinked(payments);
	}

	@Override
	public void firePaymentsUnlinkedFromBankStatementLineReferences(@NonNull BankStatementLineReferenceList lineRefs)
	{
		listeners.onPaymentsUnlinkedFromBankStatementLineReferences(lineRefs);
	}
}
