package org.compiere.wf;

import de.metas.workflow.WFResponsibleType;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.X_AD_WF_Responsible;

import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MWFResponsible extends X_AD_WF_Responsible
{
	public MWFResponsible(final Properties ctx, final int AD_WF_Responsible_ID, final String trxName)
	{
		super(ctx, AD_WF_Responsible_ID, trxName);
	}

	public MWFResponsible(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		final WFResponsibleType type = WFResponsibleType.ofCode(getResponsibleType());
		//	if (RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getAD_User_ID() <= 0)
		//		return true;
		if (type == WFResponsibleType.Role
				&& getAD_Role_ID() <= 0
				&& getAD_Client_ID() > 0)
		{
			throw new FillMandatoryException(COLUMNNAME_AD_Role_ID);
		}
		//	User not used
		if (!RESPONSIBLETYPE_Human.equals(getResponsibleType()) && getAD_User_ID() > 0)
		{
			setAD_User_ID(-1);
		}

		//	Role not used
		if (type != WFResponsibleType.Role && getAD_Role_ID() > 0)
		{
			setAD_Role_ID(-1);
		}

		return true;
	}
}
