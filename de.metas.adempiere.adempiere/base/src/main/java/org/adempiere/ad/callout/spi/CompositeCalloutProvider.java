package org.adempiere.ad.callout.spi;

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.impl.NullCalloutProvider;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/**
 * Immutable composite of {@link ICalloutProvider}s.
 * 
 * To manipulate the the composites you shall use {@link #compose(ICalloutProvider, ICalloutProvider)}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class CompositeCalloutProvider implements ICalloutProvider
{
	/**
	 * Compose given providers.
	 * 
	 * @param provider1
	 * @param provider2
	 * @return composed provider / provider1 / provider2 / {@link NullCalloutProvider}; never returns null
	 */
	public static ICalloutProvider compose(@Nullable final ICalloutProvider provider1, @Nullable final ICalloutProvider provider2)
	{
		if (NullCalloutProvider.isNull(provider1))
		{
			return NullCalloutProvider.isNull(provider2) ? NullCalloutProvider.instance : provider2;
		}

		if (provider1 instanceof CompositeCalloutProvider)
		{
			return ((CompositeCalloutProvider)provider1).compose(provider2);
		}
		
		if(NullCalloutProvider.isNull(provider2))
		{
			// at this point, we assume provider1 is not null
			return provider1;
		}

		// Avoid duplicates
		// (at this point we assume both providers are not null)
		if(provider1.equals(provider2))
		{
			return provider1;
		}

		return new CompositeCalloutProvider(ImmutableList.of(provider1, provider2));
	}

	private final ImmutableList<ICalloutProvider> providers;

	private CompositeCalloutProvider(final ImmutableList<ICalloutProvider> providers)
	{
		super();
		this.providers = providers;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("composite")
				.addValue(providers)
				.toString();
	}

	public List<ICalloutProvider> getProvidersList()
	{
		return providers;
	}

	/**
	 * Compose this provider with the given one and returns a new instance which will contain the given one too.
	 * 
	 * @param provider
	 * @return new composite containing the given provider too
	 */
	private CompositeCalloutProvider compose(final ICalloutProvider provider)
	{
		if (NullCalloutProvider.isNull(provider))
		{
			return this;
		}

		if (providers.contains(provider))
		{
			return this;
		}

		final ImmutableList<ICalloutProvider> providersList = ImmutableList.<ICalloutProvider> builder()
				.addAll(providers)
				.add(provider)
				.build();

		return new CompositeCalloutProvider(providersList);
	}

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final String tableName)
	{
		final TableCalloutsMap.Builder resultBuilder = TableCalloutsMap.builder();

		for (final ICalloutProvider provider : providers)
		{
			final TableCalloutsMap callouts = provider.getCallouts(ctx, tableName);
			resultBuilder.putAll(callouts);
		}

		return resultBuilder.build();
	}

}
