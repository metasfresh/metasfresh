package org.adempiere.acct.api.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.acct.api.IFactAcctListener;
import org.adempiere.acct.api.IFactAcctListenersService;
import org.adempiere.util.Check;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import de.metas.document.engine.IDocument;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class FactAcctListenersService implements IFactAcctListenersService
{
	private final CopyOnWriteArrayList<IFactAcctListener> listeners = new CopyOnWriteArrayList<>();

	public FactAcctListenersService()
	{
		super();

		registerListener(FactAcctListener2ModelValidationEngineAdapter.instance);
	}

	@Override
	public void registerListener(final IFactAcctListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		listeners.addIfAbsent(listener);
	}

	@Override
	public void fireBeforePost(final Object document)
	{
		for (final IFactAcctListener listener : listeners)
		{
			listener.onBeforePost(document);
		}
	}

	@Override
	public void fireAfterPost(final Object document)
	{
		for (final IFactAcctListener listener : listeners)
		{
			listener.onAfterPost(document);
		}
	}

	@Override
	public void fireAfterUnpost(final Object document)
	{
		for (final IFactAcctListener listener : listeners)
		{
			listener.onAfterUnpost(document);
		}
	}

	/**
	 * Listens Fact_Acct events and forward them to {@link ModelValidationEngine}.
	 */
	private static final class FactAcctListener2ModelValidationEngineAdapter implements IFactAcctListener
	{
		public static final transient FactAcctListener2ModelValidationEngineAdapter instance = new FactAcctListener2ModelValidationEngineAdapter();

		private FactAcctListener2ModelValidationEngineAdapter()
		{
		}

		private final void fireDocValidate(final Object document, final int timing)
		{
			final Object model;
			if(document instanceof IDocument)
			{
				model = ((IDocument)document).getDocumentModel();
			}
			else
			{
				model = document;
			}
			
			ModelValidationEngine.get().fireDocValidate(model, timing);
		}

		@Override
		public void onBeforePost(final Object document)
		{
			fireDocValidate(document, ModelValidator.TIMING_BEFORE_POST);
		}

		@Override
		public void onAfterPost(final Object document)
		{
			fireDocValidate(document, ModelValidator.TIMING_AFTER_POST);
		}

		@Override
		public void onAfterUnpost(final Object document)
		{
			fireDocValidate(document, ModelValidator.TIMING_AFTER_UNPOST);
		}
	}
}
