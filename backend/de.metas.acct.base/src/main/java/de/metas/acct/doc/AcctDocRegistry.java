package de.metas.acct.doc;

<<<<<<< HEAD
import java.util.List;
import java.util.Set;

=======
import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchema;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
=======
import java.util.List;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

@Service
public class AcctDocRegistry
{
	private static final Logger logger = LogManager.getLogger(AcctDocRegistry.class);

	private final AggregatedAcctDocProvider docProviders;
	private final AcctDocRequiredServicesFacade acctDocRequiredServices;

	public AcctDocRegistry(
			@NonNull final List<IAcctDocProvider> acctDocProviders,
			@NonNull final AcctDocRequiredServicesFacade acctDocRequiredServices)
	{
		docProviders = new AggregatedAcctDocProvider(acctDocProviders);
		logger.info("Using: {}", docProviders);

		this.acctDocRequiredServices = acctDocRequiredServices;
	}

	@NonNull
	public Doc<?> get(@NonNull final List<AcctSchema> acctSchemas, @NonNull final TableRecordReference documentRef)
	{
		final Doc<?> doc = docProviders.getOrNull(acctDocRequiredServices, acctSchemas, documentRef);
		if (doc == null)
		{
			throw new PostingExecutionException("No accountable document found: " + documentRef);
		}
		return doc;
	}

	public Set<String> getDocTableNames()
	{
		return docProviders.getDocTableNames();
	}

<<<<<<< HEAD
	@ToString
	private static class AggregatedAcctDocProvider implements IAcctDocProvider
	{
		private final ImmutableList<IAcctDocProvider> providers;

		private AggregatedAcctDocProvider(final List<IAcctDocProvider> providers)
		{
			this.providers = ImmutableList.copyOf(providers);
=======
	public boolean isAccountingTable(final String docTableName)
	{
		return docProviders.isAccountingTable(docTableName);
	}

	@ToString
	private static class AggregatedAcctDocProvider implements IAcctDocProvider
	{
		private final ImmutableMap<String, IAcctDocProvider> providersByDocTableName;

		private AggregatedAcctDocProvider(final List<IAcctDocProvider> providers)
		{
			final ImmutableMap.Builder<String, IAcctDocProvider> mapBuilder = ImmutableMap.builder();
			for (final IAcctDocProvider provider : providers)
			{
				for (final String docTableName : provider.getDocTableNames())
				{
					mapBuilder.put(docTableName, provider);
				}
			}
			this.providersByDocTableName = mapBuilder.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		@Override
		public Set<String> getDocTableNames()
		{
<<<<<<< HEAD
			return providers.stream()
					.flatMap(provider -> provider.getDocTableNames().stream())
					.collect(ImmutableSet.toImmutableSet());
=======
			return providersByDocTableName.keySet();
		}

		public boolean isAccountingTable(final String docTableName)
		{
			return getDocTableNames().contains(docTableName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		@Override
		public Doc<?> getOrNull(
				@NonNull final AcctDocRequiredServicesFacade services,
				@NonNull final List<AcctSchema> acctSchemas,
				@NonNull final TableRecordReference documentRef)
		{
			try
			{
<<<<<<< HEAD
				for (final IAcctDocProvider provider : providers)
				{
					final Doc<?> acctDoc = provider.getOrNull(services, acctSchemas, documentRef);
					if (acctDoc != null)
					{
						return acctDoc;
					}
				}

				// no accountable document found
				return null;
=======
				final String docTableName = documentRef.getTableName();
				final IAcctDocProvider provider = providersByDocTableName.get(docTableName);
				if (provider == null)
				{
					return null;
				}

				return provider.getOrNull(services, acctSchemas, documentRef);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
			catch (final AdempiereException ex)
			{
				throw ex;
			}
			catch (final Exception ex)
			{
				throw PostingExecutionException.wrapIfNeeded(ex);
			}
		}
	}
}
