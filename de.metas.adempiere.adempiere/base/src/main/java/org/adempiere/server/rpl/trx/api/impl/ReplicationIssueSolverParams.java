package org.adempiere.server.rpl.trx.api.impl;

import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.util.api.ForwardingParams;
import org.adempiere.util.api.IParams;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
/* package */final class ReplicationIssueSolverParams extends ForwardingParams implements IReplicationIssueSolverParams
{
	public ReplicationIssueSolverParams(@NonNull final IParams params)
	{
		super(params);
	}
}
