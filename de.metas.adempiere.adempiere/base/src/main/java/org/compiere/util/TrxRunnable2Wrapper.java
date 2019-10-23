/**
 * 
 */
package org.compiere.util;

import de.metas.util.Check;

/**
 * Wraps an {@link TrxRunnable} and make it to behave like an {@link TrxRunnable2}.
 * 
 * @author tsa
 *
 */
public final class TrxRunnable2Wrapper implements TrxRunnable2
{
	public static final TrxRunnable2 wrapIfNeeded(final TrxRunnable runnable)
	{
		if (runnable == null)
		{
			return null;
		}
		final TrxRunnable2 runnable2 = runnable instanceof TrxRunnable2 ? (TrxRunnable2)runnable : new TrxRunnable2Wrapper(runnable);
		return runnable2;
	}
	
	private final TrxRunnable runnable;

	private TrxRunnable2Wrapper(TrxRunnable r)
	{
		super();

		Check.assumeNotNull(r, "runnable not null");
		this.runnable = r;
	}

	@Override
	public String toString()
	{
		return "TrxRunnable2Wrapper [runnable=" + runnable + "]";
	}

	@Override
	public void run(String trxName) throws Exception
	{
		runnable.run(trxName);
	}

	@Override
	public boolean doCatch(Throwable e) throws Throwable
	{
		throw e;
	}

	@Override
	public void doFinally()
	{
		// nothing
	}

}
