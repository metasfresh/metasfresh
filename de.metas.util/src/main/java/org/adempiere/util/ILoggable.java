package org.adempiere.util;

/*
 * #%L
 * de.metas.util
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



/**
 * Interface implementations can be passed to business logic to perform high-level logging.
 * 
 * NOTE: The signature of this interface's only method is chosen so that all classes like de.metas.process.JavaProcess subclasses can implement it without further code changes.
 *
 * @author ts
 *
 */
public interface ILoggable
{
	/** The null loggable which can be used without NPE, but doesn't do anything */
	public static ILoggable NULL = NullLoggable.instance;

	/** Holds the {@link ILoggable} instance of current thread */
	public static final ThreadLocalLoggableHolder THREADLOCAL = ThreadLocalLoggableHolder.instance;

	/**
	 * Add a log message.
	 *
	 * @param msg
	 * @param msgParameters
	 */
public void addLog(String msg, Object... msgParameters);
}
