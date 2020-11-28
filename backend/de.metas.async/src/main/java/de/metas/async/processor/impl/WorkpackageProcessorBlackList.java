package de.metas.async.processor.impl;

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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.async.exceptions.ConfigurationException;

/**
 * Class used to manage workpackage processors which were blacklisted
 * 
 * @author tsa
 * 
 */
public class WorkpackageProcessorBlackList
{
	public static class BlackListItem
	{
		private final int packageProcessorId;
		private final String classname;
		private final Exception exception;
		private final AtomicInteger hitCount = new AtomicInteger(1);
		private final Date dateFirstRequest;
		private Date dateLastRequest;

		public BlackListItem(int packageProcessorId, String classname, Exception exception)
		{
			super();
			this.packageProcessorId = packageProcessorId;
			this.classname = classname;
			this.exception = exception;
			this.dateFirstRequest = new Date();
			this.dateLastRequest = dateFirstRequest;
		}

		@Override
		public String toString()
		{
			return "BlackListItem ["
					+ " packageProcessorId=" + packageProcessorId
					+ ", classname=" + classname
					+ ", hitCount=" + hitCount
					+ ", dateFirstRequest=" + dateFirstRequest
					+ ", dateLastRequest=" + dateLastRequest
					+ ", exception=" + exception
					+ "]";
		}

		public Exception getException()
		{
			return exception;
		}

		public int incrementHitCount()
		{
			dateLastRequest = new Date();
			return hitCount.incrementAndGet();
		}
	}

	private final transient Logger logger = LogManager.getLogger(getClass());
	private final Map<Integer, BlackListItem> blacklist = new ConcurrentHashMap<Integer, BlackListItem>();

	public WorkpackageProcessorBlackList()
	{
		super();
	}

	public boolean isBlacklisted(final int workpackageProcessorId)
	{
		return blacklist.containsKey(workpackageProcessorId);
	}

	public void addToBlacklist(final int packageProcessorId, String packageProcessorClassname, Exception e)
	{
		final ConfigurationException exception = ConfigurationException.wrapIfNeeded(e);
		final BlackListItem blacklistItemToAdd = new BlackListItem(packageProcessorId, packageProcessorClassname, exception);
		blacklist.put(packageProcessorId, blacklistItemToAdd);

		logger.warn("Processor blacklisted: " + blacklistItemToAdd, exception);
	}

	public void removeFromBlacklist(final int packageProcessorId)
	{
		final BlackListItem blacklistItem = blacklist.remove(packageProcessorId);
		if (blacklistItem != null)
		{
			logger.info("Processor removed from blacklist: " + blacklistItem);
		}
	}

	public void assertNotBlacklisted(final int workpackageProcessorId)
	{
		final BlackListItem blacklistItem = blacklist.get(workpackageProcessorId);
		if (blacklistItem != null)
		{
			blacklistItem.incrementHitCount();
			throw new ConfigurationException("Already blacklisted: " + blacklistItem, blacklistItem.getException());
		}
	}

	public List<BlackListItem> getItems()
	{
		return new ArrayList<BlackListItem>(blacklist.values());
	}

	public void clear()
	{
		blacklist.clear();
	}
}
