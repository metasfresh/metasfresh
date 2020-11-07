package org.compiere.wf;

import org.compiere.model.X_AD_WF_NodeNext;

import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MWFNodeNext extends X_AD_WF_NodeNext
{
	public MWFNodeNext(final Properties ctx, final int AD_WF_NodeNext_ID, final String trxName)
	{
		super(ctx, AD_WF_NodeNext_ID, trxName);
		if (is_new())
		{
			//	setAD_WF_Next_ID (0);
			//	setAD_WF_Node_ID (0);
			setEntityType(ENTITYTYPE_UserMaintained);    // U
			setIsStdUserWorkflow(false);
			setSeqNo(10);    // 10
		}
	}

	public MWFNodeNext(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MWFNodeNext
}
