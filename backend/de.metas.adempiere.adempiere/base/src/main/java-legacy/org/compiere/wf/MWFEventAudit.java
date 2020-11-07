package org.compiere.wf;

import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.Query;
import org.compiere.model.X_AD_WF_EventAudit;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
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
	/**
	 *
	 */
	private static final long serialVersionUID = 3760514881823970823L;

	/**
	 * Get Event Audit for node
	 *
	 * @param ctx              context
	 * @param AD_WF_Process_ID process
	 * @param AD_WF_Node_ID    optional node
	 * @return event audit or null
	 * @deprecated Deprecated since 3.4.0. Use instead {@link #get(Properties, int, int, String)}
	 */
	@Deprecated
	public static MWFEventAudit[] get(final Properties ctx, final int AD_WF_Process_ID, final int AD_WF_Node_ID)
	{
		return get(ctx, AD_WF_Process_ID, AD_WF_Node_ID, ITrx.TRXNAME_None);
	}

	/**
	 * Get Event Audit for node
	 *
	 * @param ctx              context
	 * @param AD_WF_Process_ID process
	 * @param AD_WF_Node_ID    optional node
	 * @return event audit or null
	 */
	public static MWFEventAudit[] get(final Properties ctx, final int AD_WF_Process_ID, final int AD_WF_Node_ID, @Nullable final String trxName)
	{
		final ArrayList<Object> params = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder("AD_WF_Process_ID=?");
		params.add(AD_WF_Process_ID);
		if (AD_WF_Node_ID > 0)
		{
			whereClause.append(" AND AD_WF_Node_ID=?");
			params.add(AD_WF_Node_ID);
		}
		final List<MWFEventAudit> list = new Query(ctx, Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOrderBy(COLUMNNAME_AD_WF_EventAudit_ID)
				.list(MWFEventAudit.class);
		//
		final MWFEventAudit[] retValue = new MWFEventAudit[list.size()];
		list.toArray(retValue);
		return retValue;
	}    //	get

	/**
	 * Get Event Audit for node
	 *
	 * @param ctx              context
	 * @param AD_WF_Process_ID process
	 * @return event audit or null
	 * @deprecated Deprecated since 3.4.0. Use instead {@link #get(Properties, int, String)}
	 */
	@Deprecated
	public static MWFEventAudit[] get(final Properties ctx, final int AD_WF_Process_ID)
	{
		return get(ctx, AD_WF_Process_ID, ITrx.TRXNAME_None);
	}

	/**
	 * Get Event Audit for node
	 *
	 * @return event audit or null
	 */
	public static MWFEventAudit[] get(final Properties ctx, final int AD_WF_Process_ID, @Nullable final String trxName)
	{
		return get(ctx, AD_WF_Process_ID, 0, trxName);
	}    //	get

	public MWFEventAudit(final Properties ctx, final int AD_WF_EventAudit_ID, final String trxName)
	{
		super(ctx, AD_WF_EventAudit_ID, trxName);
	}

	public MWFEventAudit(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Activity Constructor
	 *
	 * @param activity activity
	 */
	public MWFEventAudit(final MWFActivity activity)
	{
		super(activity.getCtx(), 0, activity.get_TrxName());
		setClientOrg(activity); // metas: tsa: 01799: use activity's client and org
		setAD_WF_Process_ID(activity.getAD_WF_Process_ID());
		setAD_WF_Node_ID(activity.getAD_WF_Node_ID());
		setAD_Table_ID(activity.getAD_Table_ID());
		setRecord_ID(activity.getRecord_ID());
		//
		setAD_WF_Responsible_ID(activity.getAD_WF_Responsible_ID());
		setAD_User_ID(activity.getAD_User_ID());
		//
		setWFState(activity.getWFState());
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
