package de.metas.banking.service.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import de.metas.banking.model.BankStatementLineReferenceList;
import de.metas.banking.service.IBankStatementListener;
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

final class CompositeBankStatementListener implements IBankStatementListener
{
	private final CopyOnWriteArrayList<IBankStatementListener> listeners = new CopyOnWriteArrayList<>();

	public void addListener(@NonNull final IBankStatementListener listener)
	{
		listeners.addIfAbsent(listener);
	}

	@Override
	public void onBeforeDeleteBankStatementLineReferences(@NonNull final BankStatementLineReferenceList lineRefs)
	{
		for (final IBankStatementListener listener : listeners)
		{
			listener.onBeforeDeleteBankStatementLineReferences(lineRefs);
		}
	}
}
