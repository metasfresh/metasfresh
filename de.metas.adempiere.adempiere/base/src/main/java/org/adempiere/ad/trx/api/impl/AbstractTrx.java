package org.adempiere.ad.trx.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Supplier;

import de.metas.logging.LogManager;

/**
 * Abstract {@link ITrx} implementation which has no dependencies on any native implementation.
 *
 * @author tsa
 *
 */
public abstract class AbstractTrx implements ITrx
{
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(AbstractTrx.class);

	private final ITrxManager trxManager;
	private final String m_trxName;

	/**
	 * set to <code>true</code> by {@link #start()}.
	 */
	private boolean m_active = false;
	private long m_startTime;

	private final boolean autoCommit;

	private volatile ConcurrentHashMap<String, Object> _properties = null;

	//
	// Debug info
	private Exception debugCreateStacktrace = null;
	private Exception debugCloseStacktrace = null;
	private String debugConnectionBackendId = null;

	/**
	 * Transaction Constructor
	 *
	 * @param trxName unique name
	 */
	public AbstractTrx(final ITrxManager trxManager, final String trxName, final boolean autoCommit)
	{
		// log.info(trxName);

		Check.assumeNotNull(trxManager, "trxManager not null");
		this.trxManager = trxManager;

		this.autoCommit = autoCommit;

		if (!trxManager.isValidTrxName(trxName))
		{
			throw new IllegalArgumentException("Invalid transaction name: " + trxName);
		}
		m_trxName = trxName;

		//
		// Debugging: store the create stack trace
		if (trxManager.isDebugTrxCreateStacktrace())
		{
			debugCreateStacktrace = new Exception("Create stacktrace");
		}
	}

	@Override
	public final String getTrxName()
	{
		return m_trxName;
	}

	private final void validateTrxSavepoint(final ITrxSavepoint savepoint)
	{
		if (savepoint == null)
		{
			throw new TrxException("Null savepoint was provided for transaction " + this);
		}
		if (savepoint.getTrx() != this)
		{
			throw new TrxException("Savepoint " + savepoint + " is not for transaction " + this + " but for " + savepoint.getTrx());
		}
	}

	@Override
	public boolean start()
	{
		if (m_active)
		{
			log.warn("Trx in progress " + m_trxName);
			return false;
		}
		m_active = true;
		m_startTime = System.currentTimeMillis();
		return true;
	}	// startTrx

	@Override
	public Date getStartTime()
	{
		return new Date(m_startTime);
	}

	@Override
	public boolean isActive()
	{
		return m_active;
	}	// isActive

	@Override
	public boolean isAutoCommit()
	{
		return autoCommit;
	}

	/**
	 * Returns true if the transaction was just started but no actual actions were performed on this transaction.
	 *
	 * NOTE: This method shall be overwritten by actual transaction implementations and it's used for optimizations.
	 *
	 * @return true if the transaction was just started
	 */
	protected boolean isJustStarted()
	{
		return false;
	}

	@Override
	public boolean rollback(boolean throwException) throws SQLException
	// metas: begin: 02367
	{
		boolean success = false;
		try
		{
			success = rollbackNative(throwException);
			return success;
		}
		finally
		{
			m_active = false;
			Services.get(IOpenTrxBL.class).onRollback(this); // metas 02367

			if (success)
			{
				getTrxListenerManager(false).fireAfterRollback(this);
			}
		}
	}

	/**
	 * Native (actual) rollback implementation.
	 *
	 * @param throwException
	 * @return
	 * @throws SQLException
	 */
	protected abstract boolean rollbackNative(boolean throwException) throws SQLException;

	@Override
	public boolean rollback()
	{
		try
		{
			final boolean throwException = false;
			return rollback(throwException);
		}
		catch (SQLException e)
		{
			return false;
		}
	}

	@Override
	public boolean rollback(final ITrxSavepoint savepoint)
	{
		validateTrxSavepoint(savepoint);
		try
		{
			return rollbackNative(savepoint);
		}
		catch (Exception e)
		{
			throw new DBException("Cannot rollback " + this + " to savepoint " + savepoint, e);
		}
		finally
		{
			Services.get(IOpenTrxBL.class).onRollback(this); // metas 02367
		}
	}

	/**
	 * Native (actual) rollback to savepoint implementation
	 *
	 * @param savepointNative
	 * @return
	 * @throws SQLException
	 */
	protected abstract boolean rollbackNative(ITrxSavepoint savepoint) throws Exception;

	@Override
	public boolean commit(boolean throwException) throws SQLException
	// metas: begin: 02367
	{
		final ITrxListenerManager trxListenerManager = getTrxListenerManager(false);

		boolean success = false;
		try
		{
			// Fire before-commit listeners
			trxListenerManager.fireBeforeCommit(this);

			// Actual native commit
			success = commitNative(throwException);
			return success;
		}
		finally
		{
			m_active = false;

			Services.get(IOpenTrxBL.class).onCommit(this); // metas 02367

			// 04265: If transaction was successfully committed fire listeners
			if (success)
			{
				trxListenerManager.fireAfterCommit(this);
			}
		}
	}

	/**
	 * Native (actual) commit implementation
	 *
	 * @param throwException
	 * @return
	 * @throws SQLException
	 */
	protected abstract boolean commitNative(boolean throwException) throws SQLException;

	@Override
	public ITrxSavepoint createTrxSavepoint(String name)
	{
		final ITrxSavepoint savepoint;
		try
		{
			savepoint = createTrxSavepointNative(name);
		}
		catch (Exception e)
		{
			throw new DBException("Cannot create savepoint '" + name + "' for transaction " + this, e);
		}

		if (savepoint != null)
		{
			Services.get(IOpenTrxBL.class).onSetSavepoint(this, savepoint); // metas 02367
		}

		return savepoint;
	}

	/**
	 * @param name
	 * @return savepoint or return <code>null</code> if no connection could be obtained of if we have an autoCommit connection.
	 * @throws Exception
	 */
	protected abstract ITrxSavepoint createTrxSavepointNative(String name) throws Exception;

	@Override
	public void releaseSavepoint(final ITrxSavepoint savepoint)
	{
		validateTrxSavepoint(savepoint);

		final boolean released;
		try
		{
			released = releaseSavepointNative(savepoint);
		}
		catch (Exception e)
		{
			throw new DBException("Cannot release savepoint " + savepoint + " on " + this, e);
		}

		if (released)
		{
			Services.get(IOpenTrxBL.class).onReleaseSavepoint(this, savepoint); // metas 02367
		}
	}

	/**
	 * Release native savepoint
	 *
	 * @param savepointNative
	 * @return true if released or if it was already realeased
	 */
	protected abstract boolean releaseSavepointNative(ITrxSavepoint savepoint) throws Exception;

	/**
	 * Commit
	 *
	 * @return true if success
	 */
	public boolean commit()
	{
		try
		{
			return commit(false);
		}
		catch (SQLException e)
		{
			return false;
		}
	}

	@Override
	public synchronized boolean close()
	// metas: begin: 02367
	{
		//
		// Debugging: store the close stack trace
		if (trxManager.isDebugTrxCreateStacktrace())
		{
			debugCloseStacktrace = new Exception("Close stacktrace");
		}

		trxManager.remove(this);

		//
		// Transaction was just started but no actual actions were performed => closing it directly
		if (isActive() && isJustStarted())
		{
			m_active = false;
			Services.get(IOpenTrxBL.class).onClose(this); // metas 02367
			log.debug("{} (direct)", getTrxName());
			return true;
		}

		//
		// Transaction is still active, we need to commit it first
		if (isActive())
		{
			commit();
		}

		//
		// Natively closing the transaction
		try
		{
			return closeNative();
		}
		finally
		{
			m_active = false;

			Services.get(IOpenTrxBL.class).onClose(this); // metas 02367

			getTrxListenerManager(false).fireAfterClose(this);

			log.debug("Closed: {}", getTrxName());
		}
	}

	/**
	 * Native (actual) transaction close implementation
	 *
	 * @return true on success
	 */
	protected abstract boolean closeNative();

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[")
				.append(getTrxName())
				.append(", Active=").append(isActive());

		final long lastStartTime = m_startTime;
		if (lastStartTime > 0)
		{
			sb.append(", LastStartTime=").append(new Timestamp(lastStartTime));
		}

		if (debugConnectionBackendId != null)
		{
			sb.append(", Backend ID=").append(debugConnectionBackendId);
		}

		if (debugCreateStacktrace != null)
		{
			sb.append(", Create stack trace=").append(Util.dumpStackTraceToString(debugCreateStacktrace));
		}
		if (debugCloseStacktrace != null)
		{
			sb.append(", Close stack trace=").append(Util.dumpStackTraceToString(debugCloseStacktrace));
		}

		sb.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Current {@link ITrxListenerManager}
	 *
	 * @task 04265
	 */
	private ITrxListenerManager trxListenerManager = null;

	/**
	 * Gets the {@link ITrxListenerManager} associated with this transaction.
	 *
	 * If no {@link ITrxListenerManager} was already created and <code>create</code> is true, a new transaction listener manager will be created and returned
	 *
	 * @param create
	 * @return
	 *
	 * @task 04265
	 */
	private ITrxListenerManager getTrxListenerManager(final boolean create)
	{
		if (trxListenerManager != null)
		{
			return trxListenerManager;
		}

		if (create)
		{
			trxListenerManager = new TrxListenerManager();
			return trxListenerManager;
		}

		return NullTrxListenerManager.instance;
	}

	@Override
	public ITrxListenerManager getTrxListenerManager()
	{
		return getTrxListenerManager(true); // create=true
	}

	@Override
	public final ITrxManager getTrxManager()
	{
		return trxManager;
	}
	
	private final Map<String, Object> getPropertiesMap()
	{
		if(_properties == null)
		{
			synchronized (this)
			{
				if(_properties == null)
				{
					_properties = new ConcurrentHashMap<>();
				}
			}
		}
		return _properties;
	}
	
	private final Map<String, Object> getPropertiesMapOrNull()
	{
		synchronized (this)
		{
			return _properties;
		}
	}

	@Override
	public final <T> T setProperty(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		
		// Handle null value case
		if(value == null)
		{
			final Map<String, Object> properties = getPropertiesMapOrNull();
			if(properties == null)
			{
				return null;
			}
			
			@SuppressWarnings("unchecked")
			final T valueOld = (T)properties.remove(name);
			return valueOld;
		}
		else
		{
			@SuppressWarnings("unchecked")
			final T valueOld = (T)getPropertiesMap().put(name, value);
			return valueOld;
		}
	}

	@Override
	public final <T> T getProperty(final String name)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)getPropertiesMap().get(name);
		return value;
	}

	@Override
	public <T> T getProperty(final String name, final Supplier<T> valueInitializer)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)getPropertiesMap().computeIfAbsent(name, key->valueInitializer.get());
		return value;
	}

	protected final void setDebugConnectionBackendId(final String debugConnectionBackendId)
	{
		this.debugConnectionBackendId = debugConnectionBackendId;
	}
}
