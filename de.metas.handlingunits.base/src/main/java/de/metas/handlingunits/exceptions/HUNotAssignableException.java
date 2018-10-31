package de.metas.handlingunits.exceptions;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;

/**
 * Exception thrown when an HU is not assignable to a given document line
 *
 * @author tsa
 *
 */
public class HUNotAssignableException extends HUException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -438979286865114772L;

	/**
	 *
	 * @param message reason why is not assignable
	 * @param documentLineModel document line on which HU was tried to be assigned
	 * @param hu HU that wanted to be assigned
	 */
	public HUNotAssignableException(final String message, final Object documentLineModel, final I_M_HU hu)
	{
		super(buildMsg(message, documentLineModel, hu));
	}

	private static final String buildMsg(final String message, final Object documentLineModel, final I_M_HU hu)
	{
		final StringBuilder sb = new StringBuilder();

		if (!Check.isEmpty(message, true))
		{
			sb.append(message.trim());
		}

		//
		// Document Line Info
		if (documentLineModel != null)
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append("@Line@: ").append(documentLineModel);
		}

		//
		// HU Info
		if (hu != null)
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append("@M_HU_ID@: ").append(hu.getValue()).append(" (ID=").append(hu.getM_HU_ID()).append(")");
		}

		return sb.toString();
	}
}
