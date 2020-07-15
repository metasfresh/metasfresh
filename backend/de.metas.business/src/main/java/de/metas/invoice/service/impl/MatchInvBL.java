package de.metas.invoice.service.impl;

import de.metas.invoice.service.IMatchInvBL;
import de.metas.invoice.service.IMatchInvBuilder;

public class MatchInvBL implements IMatchInvBL
{
	@Override
	public IMatchInvBuilder createMatchInvBuilder()
	{
		return new MatchInvBuilder();
	}
}
