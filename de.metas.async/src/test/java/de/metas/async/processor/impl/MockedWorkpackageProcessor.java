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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Ignore;
import org.slf4j.Logger;

import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.logging.LogManager;

/**
 * An {@link IWorkpackageProcessor} implementation whom behavior can be configured (e.g. return {@link Result#SUCCESS} for some packages, throw exception for others etc).
 *
 * @author tsa
 *
 */
@Ignore
public class MockedWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final Logger logger = LogManager.getLogger(MockedWorkpackageProcessor.class);

	private final Map<Integer, Result> results = new HashMap<>();
	private final Map<Integer, String> exceptions = new HashMap<>();
	private final Map<Integer, Integer> skipRequests = new HashMap<>();
	private Result defaultResult = Result.SUCCESS;

	private int processedCount = 0;
	private final List<I_C_Queue_WorkPackage> processedWorkpackages = Collections.synchronizedList(new ArrayList<I_C_Queue_WorkPackage>());
	private final List<Integer> processedWorkpackageIds = Collections.synchronizedList(new ArrayList<Integer>());
	private final Map<String, Integer> usedThreadNames = new ConcurrentHashMap<>();

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();

		Assert.assertFalse("Workpackage with ID " + workpackageId + " may not yet be processed! workkpackage=" + workpackageId,
				processedWorkpackageIds.contains(workpackageId));
		synchronized (processedWorkpackages)
		{
			processedWorkpackages.add(workpackage);
			processedWorkpackageIds.add(workpackageId);
			processedCount++;
		}

		//
		// Set ProcessedCount
		InterfaceWrapperHelper.setDynAttribute(workpackage, "ProcessedIndex", processedCount);

		//
		// Set thread name
		final String threadName = Thread.currentThread().getName();
		InterfaceWrapperHelper.setDynAttribute(workpackage, "ProcessedOnThread", threadName);
		synchronized (usedThreadNames)
		{
			Integer count = usedThreadNames.get(threadName);
			if (count == null)
			{
				count = 1;
			}
			else
			{
				count++;
			}
			usedThreadNames.put(threadName, count);
		}

		logger.debug("Processed " + workpackage + ", processedCount=" + processedCount);

		//
		// Produce Result
		if (exceptions.containsKey(workpackageId))
		{
			final String exceptionMsg = exceptions.get(workpackageId);
			throw new AdempiereException(exceptionMsg);
		}
		else if (skipRequests.get(workpackageId) != null)
		{
			final int skipTimeoutMillis = skipRequests.get(workpackageId);
			final Throwable cause = null;
			throw WorkpackageSkipRequestException.createWithTimeoutAndThrowable("Skip request", skipTimeoutMillis, cause);
		}

		if (results.containsKey(workpackageId))
		{
			final Result result = results.get(workpackageId);
			return result;
		}

		return defaultResult;
	}

	/**
	 * Sets which result shall be returned by this processor in case no specific package results was set
	 *
	 * @param defaultResult
	 * @return this
	 */
	public MockedWorkpackageProcessor setDefaultResult(Result defaultResult)
	{
		this.defaultResult = defaultResult;
		return this;
	}

	/**
	 * Configure processor to return given result when workpackage will be processed
	 *
	 * @param workpackage
	 * @param result
	 * @return this
	 */
	public MockedWorkpackageProcessor setResult(I_C_Queue_WorkPackage workpackage, Result result)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		results.put(workpackageId, result);
		return this;
	}

	/**
	 * Configure processor to throw an exception when given workpackage will be processed
	 *
	 * @param workpackage
	 * @param message exception message
	 * @return
	 */
	public MockedWorkpackageProcessor setException(I_C_Queue_WorkPackage workpackage, String message)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		exceptions.put(workpackageId, message);
		return this;
	}

	public String getException(final I_C_Queue_WorkPackage workpackage)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		return exceptions.get(workpackageId);
	}

	public MockedWorkpackageProcessor setSkip(I_C_Queue_WorkPackage workpackage, int timeoutMillis)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		skipRequests.put(workpackageId, timeoutMillis);
		return this;
	}

	public void reset()
	{
		processedCount = 0;
		processedWorkpackages.clear();
	}

	public List<I_C_Queue_WorkPackage> getProcessedWorkpackages()
	{
		return processedWorkpackages;
	}

	public String getStatisticsInfo()
	{
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter out = new PrintWriter(stringWriter);
		out.println(getClass().getName() + "Statistics ---------------------------------------------------");
		out.println("Processed workpackages: " + processedCount);
		synchronized (usedThreadNames)
		{
			final List<String> names = new ArrayList<>(usedThreadNames.keySet());
			Collections.sort(names, new Comparator<String>()
			{

				@Override
				public int compare(String o1, String o2)
				{
					final int count1 = usedThreadNames.get(o1);
					final int count2 = usedThreadNames.get(o2);
					return -1 * (count1 - count2);
				}
			});
			final StringBuilder sb = new StringBuilder();
			for (String name : names)
			{
				if (sb.length() > 0)
				{
					sb.append(", ");
				}
				sb.append(name).append("(").append(usedThreadNames.get(name)).append(")");
			}

			out.println("Used threads (" + names.size() + "): " + sb);
		}

		out.flush();

		return stringWriter.getBuffer().toString();
	}

	/**
	 * Remove given workpackage ID from processed list.
	 *
	 * @param workpackageId
	 */
	public void removeProcessedWorkpackageId(final int workpackageId)
	{
		processedWorkpackageIds.remove((Integer)workpackageId);
	}
}
