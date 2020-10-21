/**
 *
 */
package org.eevolution.exceptions;

import java.time.LocalDateTime;

import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPRouting;

/**
 * Thrown when Routing(Workflow) is not valid on given date
 *
 * @author Teo Sarca
 */
@SuppressWarnings("serial")
public class RoutingExpiredException extends LiberoException
{
	public RoutingExpiredException(final PPRouting routing, final LocalDateTime date)
	{
		super(buildMessage(routing, date));
	}

	private static final String buildMessage(final PPRouting routing, final LocalDateTime date)
	{
		return "@NotValid@ @AD_Workflow_ID@:" + routing.getCode() + " - @Date@:" + date;
	}

}
