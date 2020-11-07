package org.compiere.wf;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.MMenu;
import org.compiere.model.X_AD_Workflow;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MWorkflow extends X_AD_Workflow
{
	public MWorkflow(final Properties ctx, final int AD_Workflow_ID)
	{
		this(ctx, AD_Workflow_ID, ITrx.TRXNAME_None);
	}

	@Deprecated
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

	@Override
	public String toString()
	{
		return "MWorkflow[" + get_ID() + "-" + getName() + "]";
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return false;
		}

		if (newRecord)
		{
			// nothing
		}
		// Menu/Workflow update
		else if (is_ValueChanged(COLUMNNAME_IsActive) || is_ValueChanged(COLUMNNAME_Name)
				|| is_ValueChanged(COLUMNNAME_Description) || is_ValueChanged(COLUMNNAME_Help))
		{
			final MMenu[] menues = MMenu.get(getCtx(), "AD_Workflow_ID=" + getAD_Workflow_ID(), get_TrxName());
			for (final MMenu menue : menues)
			{
				menue.setIsActive(isActive());
				menue.setName(getName());
				menue.setDescription(getDescription());
				menue.saveEx();
			}
			// TODO: teo_sarca: why do we need to sync node name with workflow name? - see BF 2665963
			//			X_AD_WF_Node[] nodes = MWindow.getWFNodes(getCtx(), "AD_Workflow_ID=" + getAD_Workflow_ID(), get_TrxName());
			//			for (int i = 0; i < nodes.length; i++)
			//			{
			//				boolean changed = false;
			//				if (nodes[i].isActive() != isActive())
			//				{
			//					nodes[i].setIsActive(isActive());
			//					changed = true;
			//				}
			//				if (nodes[i].isCentrallyMaintained())
			//				{
			//					nodes[i].setName(getName());
			//					nodes[i].setDescription(getDescription());
			//					nodes[i].setHelp(getHelp());
			//					changed = true;
			//				}
			//				if (changed)
			//					nodes[i].saveEx();
			//			}
		}

		return success;
	}
}
