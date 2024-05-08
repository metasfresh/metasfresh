package org.adempiere.model;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordSupport.IOnRecordCopiedListener;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * {@link CopyRecordSupport} factory.
 *
 * @author Cristina Ghita, METAS.RO
 */
@ThreadSafe
public final class CopyRecordFactory
{
	private static final Logger logger = LogManager.getLogger(CopyRecordFactory.class);

	private static final String SYSCONFIG_ENABLE_COPY_WITH_DETAILS = "ENABLE_COPY_WITH_DETAILS";

	private static final ConcurrentHashMap<String, Class<? extends CopyRecordSupport>> tableName2copyRecordSupportClass = new ConcurrentHashMap<>();

	/**
	 * List of table names for whom Copy With Details button is activated in Window toolbar
	 */
	private static final CopyOnWriteArraySet<String> enabledTableNames = new CopyOnWriteArraySet<>();

	private static final List<IOnRecordCopiedListener> staticOnRecordCopiedListeners = new CopyOnWriteArrayList<>();

	/**
	 * @return {@link CopyRecordSupport}; never returns null
	 */
	public static CopyRecordSupport getCopyRecordSupport(@NonNull final String tableName)
	{
		final CopyRecordSupport result;
		final Class<? extends CopyRecordSupport> copyRecordSupportClass = tableName2copyRecordSupportClass.get(tableName);
		if (copyRecordSupportClass == null)
		{
			result = new GeneralCopyRecordSupport();
		}
		else
		{
			try
			{
				result = copyRecordSupportClass.newInstance();
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("Failed creating " + copyRecordSupportClass + " instance for " + tableName, ex);
			}
		}

		for (final IOnRecordCopiedListener listener : staticOnRecordCopiedListeners)
		{
			result.addOnRecordCopiedListener(listener);
		}

		return result;
	}

	public static void registerCopyRecordSupport(
			@NonNull final String tableName,
			@NonNull final Class<? extends CopyRecordSupport> copyRecordSupportClass)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		tableName2copyRecordSupportClass.put(tableName, copyRecordSupportClass);
		logger.info("Registered for `{}`: {}", tableName, copyRecordSupportClass);
	}

	/**
	 * @return true if copy-with-details functionality is enabled
	 */
	public static boolean isEnabled()
	{
		final boolean copyWithDetailsEnabledDefault = false;
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_ENABLE_COPY_WITH_DETAILS, copyWithDetailsEnabledDefault);
	}

	/**
	 * @return true if copy-with-details functionality is enabled for given <code>tableName</code>
	 */
	public static boolean isEnabledForTableName(final String tableName)
	{
		return enabledTableNames.contains(tableName);
	}

	public static void enableForTableName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		enabledTableNames.add(tableName);
		logger.info("Enabled for table: {}", tableName);
	}

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 * Add a listener here, and it will automatically be added to each {@link CopyRecordSupport} instance that is returned by {@link #getCopyRecordSupport(String)}.
	 */
	public static void addOnRecordCopiedListener(@NonNull final IOnRecordCopiedListener listener)
	{
		staticOnRecordCopiedListeners.add(listener);
		logger.info("Registered listener: {}", listener);
	}
}
