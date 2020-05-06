package de.metas.invoice;

import de.metas.util.ISingletonService;

public interface IMatchInvBL extends ISingletonService
{
	IMatchInvBuilder createMatchInvBuilder();

}
