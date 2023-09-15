package org.compiere.model;

import de.metas.organization.OrgId;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
@SuppressWarnings("unused")
public class MSalesRegion extends X_C_SalesRegion
{
	/**
	 *
	 */
	@Serial private static final long serialVersionUID = 8582026748675153489L;

	public MSalesRegion(Properties ctx, int C_SalesRegion_ID, String trxName)
	{
		super(ctx, C_SalesRegion_ID, trxName);
	}

	public MSalesRegion(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	protected boolean beforeSave(boolean newRecord)
	{
		if (OrgId.ofRepoIdOrAny(getAD_Org_ID()).isRegular())
		{
			setAD_Org_ID(OrgId.ANY.getRepoId());
		}
		return true;
	}
}
