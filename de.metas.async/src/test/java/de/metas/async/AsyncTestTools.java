package de.metas.async;


public final class AsyncTestTools
{
	private AsyncTestTools()
	{
	}

	/**
	 * Thanks to <a href="http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds">stackoverflow</a>.
	 * 
	 * @param millis
	 */
	public static void sleep(final long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}
}
