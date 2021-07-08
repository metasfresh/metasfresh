package org.compiere.wf;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.X_AD_Workflow;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MWorkflow extends X_AD_Workflow
{
	public MWorkflow(final Properties ctx, final int AD_Workflow_ID, @Nullable final String trxName)
	{
		super(ctx, AD_Workflow_ID, trxName);
		if (is_new())
		{
			setAccessLevel(ACCESSLEVEL_Organization);
			setAuthor("metasfresh gmbH");
			setDurationUnit(DURATIONUNIT_Day);
			setDuration(1);
			setEntityType(ENTITYTYPE_UserMaintained);    // U
			setIsDefault(false);
			setPublishStatus(PUBLISHSTATUS_UnderRevision);    // U
			setVersion(0);
			setCost(BigDecimal.ZERO);
			setWaitingTime(0);
			setWorkingTime(0);
			setIsBetaFunctionality(false);
		}
	}

	public MWorkflow(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}
}
