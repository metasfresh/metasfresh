package de.metas.material.planning.exception;

import org.adempiere.exceptions.AdempiereException;

/**
 * Generic libero-specifc exception. Please subclass this one rather than {@link MrpException} when adding libero related exceptions.
 *
 * @author ts
 *
 */
public class MrpException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4775225739207748132L;

	public MrpException(final String message)
	{
		super(message);
	}

	public MrpException(final Exception cause)
	{
		super(cause);
	}

	public MrpException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

}
