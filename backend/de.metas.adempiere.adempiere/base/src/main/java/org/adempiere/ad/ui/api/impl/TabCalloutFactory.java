package org.adempiere.ad.ui.api.impl;

<<<<<<< HEAD
=======
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.cache.annotation.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
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
import org.adempiere.util.proxy.Cached;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

<<<<<<< HEAD
import javax.annotation.Nullable;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.ad.ui.spi.IStatefulTabCallout;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.impl.CompositeTabCallout;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.cache.annotation.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public class TabCalloutFactory implements ITabCalloutFactory
{
	private static final transient Logger logger = LogManager.getLogger(TabCalloutFactory.class);

	private final SetMultimap<String, Class<? extends ITabCallout>> tableName2tabCalloutClasses = LinkedHashMultimap.create();
<<<<<<< HEAD
=======
	private final ImmutableSetMultimap<TableName, ITabCallout> springBeansByTableName;

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Override
	public ITabCallout createAndInitialize(final ICalloutRecord calloutRecord)
	{
		try
		{
			final Properties ctx = Env.getCtx();
			final int adTabId = calloutRecord.getAD_Tab_ID();
			final String tableName = calloutRecord.getTableName();
			final Function<ICalloutRecord, ITabCallout> factory = getTabCalloutsFactory(ctx, adTabId, tableName);
			return factory.apply(calloutRecord);
		}
		catch (final Exception e)
		{
			logger.error("Failed creating & initializing tab callouts for {}. Returning null.", calloutRecord, e);
			return ITabCallout.NULL;
		}
	}

	@Cached
	Function<ICalloutRecord, ITabCallout> getTabCalloutsFactory(@CacheCtx final Properties ctx, final int adTabId, final String tableName)
	{
		final LinkedHashSet<Object> tabCalloutsList = new LinkedHashSet<>();
		boolean haveStatefulCallouts = false;

		final Set<String> classnamesConsidered = new HashSet<>();

		//
<<<<<<< HEAD
=======
		// Spring Beans
		for (final ITabCallout tabCallout : springBeansByTableName.get(TableName.ofString(tableName)))
		{
			tabCalloutsList.add(tabCallout);
			classnamesConsidered.add(tabCallout.getClass().getName());
		}

		//
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		// Retrieve and instantiate tab callouts registered in application dictionary
		final List<I_AD_Tab_Callout> calloutsDefinition = Services.get(IADWindowDAO.class).retrieveTabCallouts(AdTabId.ofRepoId(adTabId));
		for (final I_AD_Tab_Callout def : calloutsDefinition)
		{
			String classname = def.getClassname();

			// No classname was set? wtf, but continue
			if (Check.isEmpty(classname, true))
			{
				logger.warn("No classname set on {}", def);
				continue;
			}

			//
			// Trim the classname and collect it (to make sure we are not instantiating it more then once)
			classname = classname.trim();
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
				logger.warn("Cannot load " + classname + " for AD_Tab_ID=" + adTabId, e);
			}
		}

		//
		// Retrieve and instantiate callouts which were programatically registered
		final Set<Class<? extends ITabCallout>> tableNameCalloutClasses = tableName2tabCalloutClasses.get(tableName);
		if (!Check.isEmpty(tableNameCalloutClasses))
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
			final ITabCallout tabCallouts = copyAndInitialize(tabCalloutsList, (ICalloutRecord)null);
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
		tableName2tabCalloutClasses.put(tableName, tabCalloutClass);
	}

	@Override
	public void unregisterTabCalloutForTable(
			@NonNull final String tableName,
			@NonNull final Class<? extends ITabCallout> tabCalloutClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		tableName2tabCalloutClasses.remove(tableName, tabCalloutClass);
	}
}
