package de.metas.invoice.service;

import de.metas.util.ISingletonService;

public interface IMatchInvBL extends ISingletonService
{
	IMatchInvBuilder createMatchInvBuilder();

}
