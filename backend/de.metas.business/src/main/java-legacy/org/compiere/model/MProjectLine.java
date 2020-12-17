package org.compiere.model;

import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Project Line Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectLine extends X_C_ProjectLine
{
	private MProject m_parent = null;

	public MProjectLine(final Properties ctx, final int C_ProjectLine_ID, final String trxName)
	{
		super(ctx, C_ProjectLine_ID, trxName);
		if (is_new())
		{
			//  setC_Project_ID (0);
			//	setC_ProjectLine_ID (0);
			setLine(0);
			setIsPrinted(true);
			setProcessed(false);
			setInvoicedAmt(BigDecimal.ZERO);
			setInvoicedQty(BigDecimal.ZERO);
			//
			setPlannedAmt(BigDecimal.ZERO);
			setPlannedMarginAmt(BigDecimal.ZERO);
			setPlannedPrice(BigDecimal.ZERO);
			setPlannedQty(BigDecimal.ONE);
		}
	}

	public MProjectLine(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MProjectLine(final MProject project)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setClientOrg(project);
		setC_Project_ID(project.getC_Project_ID());    // Parent
		setLine();
	}

	private void setLine()
	{
		setLine(DB.getSQLValue(get_TrxName(),
				"SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectLine WHERE C_Project_ID=?", getC_Project_ID()));
	}

	/**
	 * Set Product, committed qty, etc.
	 */
	public void setMProjectIssue(final MProjectIssue pi)
	{
		setC_ProjectIssue_ID(pi.getC_ProjectIssue_ID());
		setM_Product_ID(pi.getM_Product_ID());
		setCommittedQty(pi.getMovementQty());
		if (getDescription() != null)
		{
			setDescription(pi.getDescription());
		}
	}    //	setMProjectIssue

	private MProject getProject()
	{
		if (m_parent == null && getC_Project_ID() > 0)
		{
			m_parent = new MProject(getCtx(), getC_Project_ID(), get_TrxName());
			if (get_TrxName() != null)
			{
				m_parent.load(get_TrxName());
			}
		}
		return m_parent;
	}    //	getProject

	/**
	 * Get Limit Price if exists
	 */
	private BigDecimal getLimitPrice()
	{
		BigDecimal limitPrice = getPlannedPrice();
		if (getM_Product_ID() == 0)
		{
			return limitPrice;
		}
		if (getProject() == null)
		{
			return limitPrice;
		}
		final boolean isSOTrx = true;
		final MProductPricing pp = new MProductPricing(getM_Product_ID(), m_parent.getC_BPartner_ID(), getPlannedQty(), isSOTrx);
		pp.setM_PriceList_ID(m_parent.getM_PriceList_ID());
		if (pp.recalculatePrice())
		{
			limitPrice = pp.getPriceLimit();
		}
		return limitPrice;
	}    //	getLimitPrice

	@Override
	public String toString()
	{
		return "MProjectLine[" + get_ID()
				+ "-" + getLine()
				+ ",C_Project_ID=" + getC_Project_ID()
				+ ",C_ProjectPhase_ID=" + getC_ProjectPhase_ID()
				+ ",C_ProjectTask_ID=" + getC_ProjectTask_ID()
				+ ",C_ProjectIssue_ID=" + getC_ProjectIssue_ID()
				+ ", M_Product_ID=" + getM_Product_ID()
				+ ", PlannedQty=" + getPlannedQty()
				+ "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getLine() == 0)
		{
			setLine();
		}

		//	Planned Amount
		setPlannedAmt(getPlannedQty().multiply(getPlannedPrice()));

		//	Planned Margin
		if (is_ValueChanged(COLUMNNAME_M_Product_ID)
				|| is_ValueChanged(COLUMNNAME_M_Product_Category_ID)
				|| is_ValueChanged(COLUMNNAME_PlannedQty)
				|| is_ValueChanged(COLUMNNAME_PlannedPrice))
		{
			if (getM_Product_ID() > 0)
			{
				final BigDecimal marginEach = getPlannedPrice().subtract(getLimitPrice());
				setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
			}
			else if (getM_Product_Category_ID() > 0)
			{
				final MProductCategory category = MProductCategory.get(getCtx(), getM_Product_Category_ID());
				final BigDecimal marginEach = category.getPlannedMargin();
				setPlannedMarginAmt(marginEach.multiply(getPlannedQty()));
			}
		}

		//	Phase/Task
		if (is_ValueChanged(COLUMNNAME_C_ProjectTask_ID) && getC_ProjectTask_ID() > 0)
		{
			final MProjectTask pt = new MProjectTask(getCtx(), getC_ProjectTask_ID(), get_TrxName());
			if (pt == null || pt.get_ID() == 0)
			{
				log.warn("Project Task Not Found - ID=" + getC_ProjectTask_ID());
				return false;
			}
			else
			{
				setC_ProjectPhase_ID(pt.getC_ProjectPhase_ID());
			}
		}

		if (is_ValueChanged(COLUMNNAME_C_ProjectPhase_ID) && getC_ProjectPhase_ID() > 0)
		{
			final MProjectPhase pp = new MProjectPhase(getCtx(), getC_ProjectPhase_ID(), get_TrxName());
			if (pp == null || pp.get_ID() == 0)
			{
				log.warn("Project Phase Not Found - " + getC_ProjectPhase_ID());
				return false;
			}
			else
			{
				setC_Project_ID(pp.getC_Project_ID());
			}
		}

		return true;
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		updateHeader();
		return success;
	}

	@Override
	protected boolean afterDelete(final boolean success)
	{
		updateHeader();
		return success;
	}    //	afterDelete

	/**
	 * Update Header
	 */
	private void updateHeader()
	{
		final String sql = DB.convertSqlToNative("UPDATE C_Project p "
				+ "SET (PlannedAmt,PlannedQty,PlannedMarginAmt,"
				+ "	CommittedAmt,CommittedQty,"
				+ " InvoicedAmt, InvoicedQty) = "
				+ "(SELECT COALESCE(SUM(pl.PlannedAmt),0),COALESCE(SUM(pl.PlannedQty),0),COALESCE(SUM(pl.PlannedMarginAmt),0),"
				+ " COALESCE(SUM(pl.CommittedAmt),0),COALESCE(SUM(pl.CommittedQty),0),"
				+ " COALESCE(SUM(pl.InvoicedAmt),0), COALESCE(SUM(pl.InvoicedQty),0) "
				+ "FROM C_ProjectLine pl "
				+ "WHERE pl.C_Project_ID=p.C_Project_ID AND pl.IsActive='Y') "
				+ "WHERE C_Project_ID=" + getC_Project_ID());
		final int no = DB.executeUpdateEx(sql, get_TrxName());
		if (no != 1)
		{
			log.warn("updateHeader - #{}", no);
		}
	}
}
