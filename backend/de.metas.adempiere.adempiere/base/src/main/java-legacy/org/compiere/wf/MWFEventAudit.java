package org.compiere.wf;

import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import de.metas.workflow.WFResponsibleId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_AD_WF_EventAudit;
import org.compiere.model.X_AD_WF_EventAudit;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Workflow Event Audit
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1801842 ] DB connection fix & improvements for concurrent threads
 * <li>BF [ 1943723 ] WF Activity History is not translated
 * @version $Id: MWFEventAudit.java,v 1.3 2006/07/30 00:51:06 jjanke Exp $
 */
public class MWFEventAudit extends X_AD_WF_EventAudit
{
	private static final long serialVersionUID = 3760514881823970823L;

	public static List<I_AD_WF_EventAudit> getByProcess(final int AD_WF_Process_ID)
	{
		return getByWFNode(AD_WF_Process_ID, -1);
	}

	public static List<I_AD_WF_EventAudit> getByWFNode(final int AD_WF_Process_ID, final int AD_WF_Node_ID)
	{
		final IQueryBuilder<I_AD_WF_EventAudit> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_WF_EventAudit.class)
				.addEqualsFilter(I_AD_WF_EventAudit.COLUMNNAME_AD_WF_Process_ID, AD_WF_Process_ID)
				.orderBy(COLUMNNAME_AD_WF_EventAudit_ID);
		if (AD_WF_Node_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_AD_WF_EventAudit.COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
		}

		return queryBuilder.create().list();
	}

	public MWFEventAudit(final Properties ctx, final int AD_WF_EventAudit_ID, final String trxName)
	{
		super(ctx, AD_WF_EventAudit_ID, trxName);
	}

	public MWFEventAudit(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MWFEventAudit(final MWFActivity activity)
	{
		super(activity.getCtx(), 0, activity.get_TrxName());
		setClientOrg(activity); // metas: tsa: 01799: use activity's client and org
		setAD_WF_Process_ID(activity.getAD_WF_Process_ID());
		setAD_WF_Node_ID(activity.getAD_WF_Node_ID());
		setAD_Table_ID(activity.getAD_Table_ID());
		setRecord_ID(activity.getRecord_ID());
		//
		setAD_WF_Responsible_ID(WFResponsibleId.toRepoId(activity.getWFResponsibleId()));
		setAD_User_ID(UserId.toRepoId(activity.getUserId()));
		//
		setWFState(activity.getState().getCode());
		setEventType(EVENTTYPE_ProcessCreated);
		setElapsedTimeMS(BigDecimal.ZERO);
		//
		final WFNode node = activity.getNode();
		if (node != null)
		{
			final WFNodeAction action = node.getAction();
			if (WFNodeAction.SetVariable.equals(action)
					|| WFNodeAction.UserChoice.equals(action))
			{
				setAttributeName(node.getDocumentColumnName());
				setOldValue(String.valueOf(activity.getAttributeValue()));
				if (WFNodeAction.SetVariable.equals(action))
				{
					setNewValue(node.getAttributeValue());
				}
			}
		}
	}
}
