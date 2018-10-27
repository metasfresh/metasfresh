package de.metas.handlingunits.exceptions;

import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.util.Check;

/**
 * Exception thrown when {@link HULoader} fails to fully load the requested qty to it's {@link IAllocationDestination}.
 *
 * @author tsa
 *
 */
public class HULoadException extends HUException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6554990044648764732L;

	private final IAllocationRequest request;
	private final IAllocationResult result;

	/**
	 *
	 * @param message
	 * @param request initial request
	 * @param result last load result, which failed
	 */
	public HULoadException(final String message, final IAllocationRequest request, final IAllocationResult result)
	{
		super(message);

		Check.assumeNotNull(request, "request not null");
		this.request = request;

		Check.assumeNotNull(result, "result not null");
		this.result = result;
	}

	public IAllocationRequest getAllocationRequest()
	{
		return request;
	}

	public IAllocationResult getAllocationResult()
	{
		return result;
	}
}
