package de.metas.async.testWorkPackageProcessors;

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

	private final Map<Integer, Result> results = new HashMap<Integer, Result>();
	private final Map<Integer, String> exceptions = new HashMap<Integer, String>();
	private final Map<Integer, Long> durations = new HashMap<Integer, Long>();
	private Result defaultResult = Result.SUCCESS;

	private int processedCount = 0;
	private final List<I_C_Queue_WorkPackage> processedWorkpackages = Collections.synchronizedList(new ArrayList<I_C_Queue_WorkPackage>());
	private final List<Integer> processedWorkpackageIds = Collections.synchronizedList(new ArrayList<Integer>());
	private final Map<String, Integer> usedThreadNames = new ConcurrentHashMap<String, Integer>();

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();

		Assert.assertFalse("Workpackage " + workpackage + " already processed", processedWorkpackageIds.contains(workpackageId));
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

		logger.trace( "Processed " + workpackage + ", processedCount=" + processedCount);

		//
		// see if we shall delay the procesing
		if (durations.containsKey(workpackageId))
		{
			// thx to http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds
			try
			{
				Thread.sleep(durations.get(workpackageId));
			}
			catch (InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
		}

		//
		// Produce Result
		if (exceptions.containsKey(workpackageId))
		{
			final String exceptionMsg = exceptions.get(workpackageId);
			throw new AdempiereException(exceptionMsg);
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

	/**
	 * Configure the processor that processing the given work package shall take the given amount of time.
	 * 
	 * @param workpackage
	 * @param durationMillis
	 * @return
	 */
	public MockedWorkpackageProcessor setProcessingDuration(I_C_Queue_WorkPackage workpackage, long durationMillis)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		durations.put(workpackageId, durationMillis);
		return this;
	}

	public String getException(final I_C_Queue_WorkPackage workpackage)
	{
		final int workpackageId = workpackage.getC_Queue_WorkPackage_ID();
		return exceptions.get(workpackageId);
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
			final List<String> names = new ArrayList<String>(usedThreadNames.keySet());
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
}