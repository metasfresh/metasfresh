package org.eevolution.exceptions;

import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPRouting;

import java.time.Instant;

/**
 * Thrown when Routing(Workflow) is not valid on given date
 *
 * @author Teo Sarca
 */
public class RoutingExpiredException extends LiberoException
{
	public RoutingExpiredException(final PPRouting routing, final Instant date)
	{
		super(buildMessage(routing, date));
	}

	private static String buildMessage(final PPRouting routing, final Instant date)
	{
		return "@NotValid@ @AD_Workflow_ID@:" + routing.getCode() + " - @Date@:" + date;
	}

}
