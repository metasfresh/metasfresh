package de.metas.lock.exceptions;

/*
 * #%L
 * de.metas.async
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.lock.spi.ExistingLockInfo;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Arrays;

/**
 * General exception thrown on any locking/unlocking error.
 * 
 * This is the root of all locking exceptions.
 * 
 * Please avoid throwing this one directly but prefer using a specific exception
 * 
 * @author tsa
 *
 */
public abstract class LockException extends AdempiereException
{
	private static final long serialVersionUID = -2526563115872381426L;

	private String sql;
	private Object[] sqlParams;
	
	@Getter
	private ImmutableList<ExistingLockInfo> existingLocks;
	
	public LockException(final String message)
	{
		super(message);
	}

	public LockException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	@Override
	protected final ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();

		message.append(super.buildMessage());

		final TableRecordReference record = getRecord();
		if (record != null)
		{
			message.append("\n Record: ").append(record.toString());
		}

		if (sql != null)
		{
			message.append("\n SQL: ").append(sql);
		}
		if (sqlParams != null)
		{
			message.append("\n SQL Params: ").append(Arrays.toString(sqlParams));
		}
		if (existingLocks != null)
		{
			message.append("\n Existing Locks: ").append(existingLocks.toString());
		}
		
		appendParameters(message);

		return message.build();
	}

	@OverridingMethodsMustInvokeSuper
	public LockException setSql(final String sql, final Object[] sqlParams)
	{
		this.sql = sql;
		this.sqlParams = sqlParams;
		resetMessageBuilt();
		return this;
	}

 	@Override
	@OverridingMethodsMustInvokeSuper
	public LockException setRecord(final @NonNull TableRecordReference record)
	{
		super.setRecord(record);
		resetMessageBuilt();
		return this;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public LockException setParameter(final @NonNull String name, final Object value)
	{
		super.setParameter(name, value);
		return this;
	}

	public LockException setExistingLocks(@NonNull final ImmutableList<ExistingLockInfo> existingLocks)
	{
		this.existingLocks = existingLocks;
		return this;
	}
}
