package org.adempiere.ad.ui.api.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.IStatefulTabCallout;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.adempiere.ad.ui.spi.impl.CompositeTabCallout;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TabCalloutFactory implements ITabCalloutFactory
{
	private static final Logger logger = LogManager.getLogger(TabCalloutFactory.class);
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	private final SetMultimap<TableName, Class<? extends ITabCallout>> tableName2tabCalloutClasses = LinkedHashMultimap.create();
	private final ImmutableSetMultimap<TableName, ITabCallout> springBeansByTableName;

	private final CCache<TabCalloutInstanceSupplierCacheKey, TabCalloutInstanceSupplier> tabCalloutInstanceSuppliers = CCache.<TabCalloutInstanceSupplierCacheKey, TabCalloutInstanceSupplier>builder()
			.build();

	public TabCalloutFactory()
	{
		this.springBeansByTableName = computeSpringBeansByTableName();
		logger.info("Registered spring beans tab callouts: {}", this.springBeansByTableName);
	}

	private static ImmutableSetMultimap<TableName, ITabCallout> computeSpringBeansByTableName()
	{
		if (Adempiere.isUnitTestMode() && !SpringContextHolder.instance.isApplicationContextSet())
		{
			logger.info("Skip fetching tab callouts from spring context because context is not set");
			return ImmutableSetMultimap.of();
		}

		final ImmutableSetMultimap.Builder<TableName, ITabCallout> result = ImmutableSetMultimap.builder();
		for (final ITabCallout tabCallout : SpringContextHolder.instance.getBeansOfType(ITabCallout.class))
		{
			final TabCallout annotation = tabCallout.getClass().getAnnotation(TabCallout.class);
			if (annotation == null)
			{
				logger.warn("Ignore {} because it has no {} anotation", tabCallout, TabCallout.class);
				continue;
			}

			final Class<?> modelClass = annotation.value();
			final TableName tableName = TableName.ofString(InterfaceWrapperHelper.getTableName(modelClass));

			result.put(tableName, tabCallout);
		}

		return result.build();
	}

	@Override
	public ITabCallout createAndInitialize(final ICalloutRecord calloutRecord)
	{
		try
		{
			final AdTabId adTabId = AdTabId.ofRepoId(calloutRecord.getAD_Tab_ID());
			final TableName tableName = TableName.ofString(calloutRecord.getTableName());
			final TabCalloutInstanceSupplier supplier = getTabCalloutInstanceSupplier(adTabId, tableName);
			return supplier.supplyInstance(calloutRecord);
		}
		catch (final Exception e)
		{
			logger.error("Failed creating & initializing tab callouts for {}. Returning null.", calloutRecord, e);
			return ITabCallout.NULL;
		}
	}

	@Value(staticConstructor = "of")
	private static class TabCalloutInstanceSupplierCacheKey
	{
		@NonNull AdTabId adTabId;
		@NonNull TableName tableName;
	}

	@FunctionalInterface
	interface TabCalloutInstanceSupplier
	{
		ITabCallout supplyInstance(ICalloutRecord calloutRecord);
	}

	private TabCalloutInstanceSupplier getTabCalloutInstanceSupplier(
			@NonNull final AdTabId adTabId,
			@NonNull final TableName tableName)
	{
		return tabCalloutInstanceSuppliers.getOrLoad(
				TabCalloutInstanceSupplierCacheKey.of(adTabId, tableName),
				this::createTabCalloutInstanceSupplier);
	}

	private TabCalloutInstanceSupplier createTabCalloutInstanceSupplier(@NonNull final TabCalloutInstanceSupplierCacheKey key)
	{
		final AdTabId adTabId = key.getAdTabId();
		final TableName tableName = key.getTableName();

		final LinkedHashSet<Object> tabCalloutsList = new LinkedHashSet<>();
		boolean haveStatefulCallouts = false;
		final HashSet<String> classnamesConsidered = new HashSet<>();

		//
		// Spring Beans
		for (final ITabCallout tabCallout : springBeansByTableName.get(tableName))
		{
			tabCalloutsList.add(tabCallout);
			classnamesConsidered.add(tabCallout.getClass().getName());
		}

		//
		// Retrieve and instantiate tab callouts registered in application dictionary
		final List<I_AD_Tab_Callout> calloutsDefinition = adWindowDAO.retrieveTabCallouts(adTabId);
		for (final I_AD_Tab_Callout def : calloutsDefinition)
		{
			final String classname = StringUtils.trimBlankToNull(def.getClassname());

			// No classname was set? wtf, but continue
			if (classname == null)
			{
				logger.warn("No classname set on {}", def);
				continue;
			}

			//
			// Trim the classname and collect it (to make sure we are not instantiating it more then once)
			if (!classnamesConsidered.add(classname))
			{
				logger.debug("Skip {} because it was already considered", classname);
				continue;
			}

			// Skip those which are not active
			if (!def.isActive())
			{
				logger.debug("Skip {} because is not active", classname);
				continue;
			}

			//
			// Instantiate the tab callout class
			try
			{
				final Class<? extends ITabCallout> tabCalloutClass = Util.loadClass(ITabCallout.class, classname);
				if (IStatefulTabCallout.class.isAssignableFrom(tabCalloutClass))
				{
					tabCalloutsList.add(tabCalloutClass);
					haveStatefulCallouts = true;
				}
				else
				{
					final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);
					tabCalloutsList.add(tabCalloutInstance);
				}
			}
			catch (final Exception e)
			{
				logger.warn("Cannot load {} for AD_Tab_ID={}", classname, adTabId, e);
			}
		}

		//
		// Retrieve and instantiate callouts which were programmatically registered
		final Set<Class<? extends ITabCallout>> tableNameCalloutClasses = tableName2tabCalloutClasses.get(tableName);
		if (!tableNameCalloutClasses.isEmpty())
		{
			for (final Class<? extends ITabCallout> tabCalloutClass : tableNameCalloutClasses)
			{
				final String classname = tabCalloutClass.getName();
				if (!classnamesConsidered.add(classname))
				{
					logger.debug("Skip {} because it was already considered", classname);
				}

				//
				// Instantiate the tab callout class
				try
				{
					if (IStatefulTabCallout.class.isAssignableFrom(tabCalloutClass))
					{
						tabCalloutsList.add(tabCalloutClass);
						haveStatefulCallouts = true;
					}
					else
					{
						final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);
						tabCalloutsList.add(tabCalloutInstance);
					}
				}
				catch (final Exception e)
				{
					logger.warn("Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
				}
			}
		}

		if (haveStatefulCallouts)
		{
			final Set<Object> tabCalloutsListCopy = ImmutableSet.copyOf(tabCalloutsList);
			return (calloutRecord) -> copyAndInitialize(tabCalloutsListCopy, calloutRecord);
		}
		else
		{
			final ITabCallout tabCallouts = copyAndInitialize(tabCalloutsList, null);
			return (calloutRecord) -> tabCallouts;
		}
	}

	private static ITabCallout copyAndInitialize(final Collection<Object> tabCalloutsList, @Nullable final ICalloutRecord calloutRecord)
	{
		final CompositeTabCallout.Builder tabCalloutsBuilder = CompositeTabCallout.builder();
		for (final Object tabCalloutObj : tabCalloutsList)
		{
			if (tabCalloutObj instanceof Class)
			{
				try
				{
					final Class<?> tabCalloutClass = (Class<?>)tabCalloutObj;
					final ITabCallout tabCalloutInstance = Util.newInstance(ITabCallout.class, tabCalloutClass);

					if (tabCalloutInstance instanceof IStatefulTabCallout)
					{
						((IStatefulTabCallout)tabCalloutInstance).onInit(calloutRecord);
					}

					tabCalloutsBuilder.addTabCallout(tabCalloutInstance);
				}
				catch (final Exception e)
				{
					logger.warn("Failed to initialize {}. Ignored.", tabCalloutObj, e);
				}
			}
			else if (tabCalloutObj instanceof ITabCallout)
			{
				final ITabCallout tabCallout = (ITabCallout)tabCalloutObj;
				tabCalloutsBuilder.addTabCallout(tabCallout);
			}
			else
			{
				logger.warn("Unknown callout type {}. Ignored.", tabCalloutObj);
			}
		}

		return tabCalloutsBuilder.build();
	}

	@Override
	public void registerTabCalloutForTable(
			@NonNull final String tableName,
			@NonNull final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		tableName2tabCalloutClasses.put(TableName.ofString(tableName), tabCalloutClass);
		logger.info("Registered tab callout {} for table {}", tabCalloutClass, tableName);
	}

	@Override
	public void unregisterTabCalloutForTable(
			@NonNull final String tableName,
			@NonNull final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		tableName2tabCalloutClasses.remove(TableName.ofString(tableName), tabCalloutClass);
		logger.info("Unregistered tab callout {} for table {}", tabCalloutClass, tableName);
	}
}
