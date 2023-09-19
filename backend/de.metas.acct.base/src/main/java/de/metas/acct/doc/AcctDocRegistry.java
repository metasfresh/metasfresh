package de.metas.acct.doc;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
		}

		@Override
		public Set<String> getDocTableNames()
		{
			return providersByDocTableName.keySet();
		}

		@Override
		public Doc<?> getOrNull(
				@NonNull final AcctDocRequiredServicesFacade services,
				@NonNull final List<AcctSchema> acctSchemas,
				@NonNull final TableRecordReference documentRef)
		{
			try
			{
				final String docTableName = documentRef.getTableName();
				final IAcctDocProvider provider = providersByDocTableName.get(docTableName);
				if (provider == null)
				{
					return null;
				}

				return provider.getOrNull(services, acctSchemas, documentRef);
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
