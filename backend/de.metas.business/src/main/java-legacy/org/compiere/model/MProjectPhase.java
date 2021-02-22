package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * 	Project Phase Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProjectPhase.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectPhase extends X_C_ProjectPhase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445836323245259566L;

	public MProjectPhase (final Properties ctx, final int C_ProjectPhase_ID, final String trxName)
	{
		super (ctx, C_ProjectPhase_ID, trxName);
		if (C_ProjectPhase_ID == 0)
		{
		//	setC_ProjectPhase_ID (0);	//	PK
		//	setC_Project_ID (0);		//	Parent
		//	setC_Phase_ID (0);			//	FK
			setCommittedAmt (BigDecimal.ZERO);
			setIsCommitCeiling (false);
			setIsComplete (false);
			setSeqNo (0);
		//	setName (null);
			setQty (BigDecimal.ZERO);
		}
	}	//	MProjectPhase

	public MProjectPhase (final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProjectPhase

	public List<I_C_ProjectTask> getTasks()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_ProjectTask.class)
				.addEqualsFilter(I_C_ProjectTask.COLUMNNAME_C_ProjectPhase_ID, getC_ProjectPhase_ID())
				.orderBy(I_C_ProjectTask.COLUMNNAME_SeqNo)
				.create()
				.list();
	}

	/**
	 *	@return number of tasks copied
	 */
	public int copyTasksFrom (final MProjectPhase fromPhase)
	{
		if (fromPhase == null)
			return 0;
		int count = 0;
		//
		final List<I_C_ProjectTask> myTasks = getTasks();
		final List<I_C_ProjectTask> fromTasks = fromPhase.getTasks();
		//	Copy Project Tasks
		for (final I_C_ProjectTask fromTask : fromTasks)
		{
			//	Check if Task already exists
			final int C_Task_ID = fromTask.getC_Task_ID();
			boolean exists = false;
			if (C_Task_ID != 0)
			{
				for (final I_C_ProjectTask myTask : myTasks)
				{
					if (myTask.getC_Task_ID() == C_Task_ID)
					{
						exists = true;
						break;
					}
				}
			}
			//	Phase exist
			if (exists)
				log.info("Task already exists here, ignored - " + fromTask);
			else
			{
				final I_C_ProjectTask toTask = InterfaceWrapperHelper.create(getCtx(), I_C_ProjectTask.class, get_TrxName());
				PO.copyValues(
						InterfaceWrapperHelper.getPO(fromTask),
						InterfaceWrapperHelper.getPO(toTask),
						getAD_Client_ID(),
						getAD_Org_ID());
				toTask.setC_ProjectPhase_ID(getC_ProjectPhase_ID());
				InterfaceWrapperHelper.save(toTask);
				count++;
			}
		}
		if (fromTasks.size() != count)
			log.warn("Count difference - ProjectPhase=" + fromTasks.size() + " <> Saved=" + count);

		return count;
	}

	@Override
	public String toString ()
	{
		return "MProjectPhase[" + get_ID()
				+ "-" + getSeqNo()
				+ "-" + getName()
				+ "]";
	}
}
