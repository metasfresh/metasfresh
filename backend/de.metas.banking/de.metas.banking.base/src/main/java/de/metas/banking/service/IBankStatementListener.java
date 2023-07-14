package de.metas.banking.service;

import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.payment.PaymentLinkResult;
import lombok.NonNull;

import java.util.List;

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

/**
 * Listens to bank statements events.
 * To register a new listener, please use {@link IBankStatementListenerService}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBankStatementListener
{
	void onPaymentsLinked(List<PaymentLinkResult> payments);

	void onPaymentsUnlinkedFromBankStatementLineReferences(@NonNull BankStatementLineReferenceList lineRefs);
}
