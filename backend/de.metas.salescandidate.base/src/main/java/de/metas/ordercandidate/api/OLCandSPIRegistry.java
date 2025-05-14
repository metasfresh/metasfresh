package de.metas.ordercandidate.api;

import com.google.common.collect.ImmutableList;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.ordercandidate.spi.IOLCandValidator;
import de.metas.ordercandidate.spi.NullOLCandListener;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class OLCandSPIRegistry
{

	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private final IOLCandListener listeners;
	private final IOLCandGroupingProvider groupingValuesProviders;
	private final IOLCandValidator validators;

	public OLCandSPIRegistry(
			final Optional<List<IOLCandListener>> optionalListeners,
			final Optional<List<IOLCandGroupingProvider>> optionalGroupingValuesProviders,
			final Optional<List<IOLCandValidator>> optionalValidators)
	{
		final List<IOLCandListener> listeners = optionalListeners.orElse(ImmutableList.of());
		this.listeners = !listeners.isEmpty()
				? new CompositeOLCandListener(listeners)
				: NullOLCandListener.instance;

		final List<IOLCandGroupingProvider> groupingValuesProviders = optionalGroupingValuesProviders.orElse(ImmutableList.of());
		this.groupingValuesProviders = !groupingValuesProviders.isEmpty()
				? new CompositeOLCandGroupingProvider(groupingValuesProviders)
				: NullOLCandGroupingProvider.instance;

		// sort validators by their SeqNo because the default validator sets UOMs that the PIIP validator then uses.
		final List<IOLCandValidator> validators = optionalValidators
				.orElse(ImmutableList.of())
				.stream()
				.sorted(Comparator.comparing(IOLCandValidator::getSeqNo))
				.collect(ImmutableList.toImmutableList());
		this.validators = !validators.isEmpty()
				? new CompositeOLCandValidator(validators, errorManager)
				: NullOLCandValidator.instance;
	}

	public IOLCandListener getListeners()
	{
		return listeners;
	}

	public IOLCandGroupingProvider getGroupingValuesProviders()
	{
		return groupingValuesProviders;
	}

	public IOLCandValidator getValidators()
	{
		return validators;
	}

	@ToString
	private static final class CompositeOLCandListener implements IOLCandListener
	{
		private final ImmutableList<IOLCandListener> listeners;

		private CompositeOLCandListener(final List<IOLCandListener> listeners)
		{
			this.listeners = ImmutableList.copyOf(listeners);
		}

		@Override
		public void onOrderLineCreated(final OLCand olCand, final I_C_OrderLine newOrderLine)
		{
			listeners.forEach(listener -> listener.onOrderLineCreated(olCand, newOrderLine));
		}
	}

	private static final class NullOLCandGroupingProvider implements IOLCandGroupingProvider
	{
		public static final transient NullOLCandGroupingProvider instance = new NullOLCandGroupingProvider();

		@Override
		public List<Object> provideLineGroupingValues(final OLCand cand)
		{
			return ImmutableList.of();
		}
	}

	@ToString
	private static final class CompositeOLCandGroupingProvider implements IOLCandGroupingProvider
	{
		private final ImmutableList<IOLCandGroupingProvider> providers;

		private CompositeOLCandGroupingProvider(final List<IOLCandGroupingProvider> providers)
		{
			this.providers = ImmutableList.copyOf(providers);
		}

		@Override
		public List<Object> provideLineGroupingValues(final OLCand cand)
		{
			return providers.stream()
					.flatMap(provider -> provider.provideLineGroupingValues(cand).stream())
					.collect(Collectors.toList());
		}
	}

	private static final class NullOLCandValidator implements IOLCandValidator
	{
		public static final transient NullOLCandValidator instance = new NullOLCandValidator();

		/** @return {@code 0} */
		@Override
		public int getSeqNo()
		{
			return 0;
		}

		@Override
		public void validate(final I_C_OLCand olCand)
		{
			// nothing to do
		}
	}

	private static final class CompositeOLCandValidator implements IOLCandValidator
	{
		private final ImmutableList<IOLCandValidator> validators;
		private final IErrorManager errorManager;

		private CompositeOLCandValidator(@NonNull final List<IOLCandValidator> validators, @NonNull final IErrorManager errorManager)
		{
			this.validators = ImmutableList.copyOf(validators);
			this.errorManager = errorManager;
		}

		/** @return {@code 0}. Actually, it doesn't matte for this validator. */
		@Override
		public int getSeqNo()
		{
			return 0;
		}

		/**
		 * Change {@link I_C_OLCand#COLUMN_IsError IsError}, {@link I_C_OLCand#COLUMN_ErrorMsg ErrorMsg},
		 * {@link I_C_OLCand#COLUMNNAME_AD_Issue_ID ADIssueID} accordingly, but <b>do not</b> save.
		 */
		@Override
		public void validate(@NonNull final I_C_OLCand olCand)
		{
			for (final IOLCandValidator olCandValdiator : validators)
			{
				try
				{
					olCandValdiator.validate(olCand);
				}
				catch (final Exception e)
				{
					final AdempiereException me = AdempiereException
							.wrapIfNeeded(e)
							.appendParametersToMessage()
							.setParameter("OLCandValidator", olCandValdiator.getClass().getSimpleName());
					olCand.setIsError(true);
					olCand.setErrorMsg(me.getLocalizedMessage());

					final AdIssueId issueId = errorManager.createIssue(e);
					olCand.setAD_Issue_ID(issueId.getRepoId());

					break;
				}
			}
		}
	}
}
