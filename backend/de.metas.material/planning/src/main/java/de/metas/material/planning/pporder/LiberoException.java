package de.metas.material.planning.pporder;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import org.adempiere.exceptions.AdempiereException;

/**
 * Generic libero-specifc exception. Please subclass this one rather than {@link LiberoException} when adding libero related exceptions.
 *
 * @author ts
 */
public class LiberoException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4775225739207748132L;

	public LiberoException(String message)
	{
		super(message);
	}

	public LiberoException(AdMessageKey message)
	{
		super(message);
	}

	public LiberoException(ITranslatableString message)
	{
		super(message);
	}

	public LiberoException(Throwable cause)
	{
		super(cause);
	}

	public LiberoException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
