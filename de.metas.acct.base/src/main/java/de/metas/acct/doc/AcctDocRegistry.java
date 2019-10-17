package de.metas.acct.doc;

import java.util.List;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchema;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;

@Service
public class AcctDocRegistry
{
	private static final Logger logger = LogManager.getLogger(AcctDocRegistry.class);

	private final AggregatedAcctDocProvider docProviders;

	public AcctDocRegistry(final List<IAcctDocProvider> acctDocProviders)
	{
		docProviders = new AggregatedAcctDocProvider(acctDocProviders);
		logger.info("Using: {}", docProviders);
	}

	public Doc<?> getOrNull(@NonNull final List<AcctSchema> acctSchemas, @NonNull final TableRecordReference documentRef)
	{
		return docProviders.getOrNull(acctSchemas, documentRef);
	}

	public Set<String> getDocTableNames()
	{
		return docProviders.getDocTableNames();
	}

	@ToString
	private static class AggregatedAcctDocProvider implements IAcctDocProvider
	{
		private final ImmutableList<IAcctDocProvider> providers;

		private AggregatedAcctDocProvider(final List<IAcctDocProvider> providers)
		{
			this.providers = ImmutableList.copyOf(providers);
		}

		@Override
		public Set<String> getDocTableNames()
		{
			return providers.stream()
					.flatMap(provider -> provider.getDocTableNames().stream())
					.collect(ImmutableSet.toImmutableSet());
		}

		@Override
		public Doc<?> getOrNull(final List<AcctSchema> acctSchemas, final TableRecordReference documentRef)
		{
			try
			{
				for (final IAcctDocProvider provider : providers)
				{
					final Doc<?> acctDoc = provider.getOrNull(acctSchemas, documentRef);
					if (acctDoc != null)
					{
						return acctDoc;
					}
				}

				// no accountable document found
				return null;
			}
			catch (final Exception ex)
			{
				throw PostingExecutionException.wrapIfNeeded(ex);
			}
		}
	}
}
