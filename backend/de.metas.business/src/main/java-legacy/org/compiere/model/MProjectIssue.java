package org.compiere.model;

import de.metas.common.util.time.SystemTime;
import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Project Issue Model
 *
 * @author Jorg Janke
 * @version $Id: MProjectIssue.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProjectIssue extends X_C_ProjectIssue
{
	private static final long serialVersionUID = 4714411434615096132L;

	public MProjectIssue(final Properties ctx, final int C_ProjectIssue_ID, final String trxName)
	{
		super(ctx, C_ProjectIssue_ID, trxName);
		if (is_new())
		{
			//	setC_Project_ID (0);
			//	setLine (0);
			//	setM_Locator_ID (0);
			//	setM_Product_ID (0);
			//	setMovementDate (new Timestamp(System.currentTimeMillis()));
			setMovementQty(BigDecimal.ZERO);
			setPosted(false);
			setProcessed(false);
		}
	}    //	MProjectIssue

	public MProjectIssue(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MProjectIssue(final MProject project)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setClientOrg(project.getAD_Client_ID(), project.getAD_Org_ID());
		setC_Project_ID(project.getC_Project_ID());    //	Parent
		setLine(getNextLine());
		//
		//	setM_Locator_ID (0);
		//	setM_Product_ID (0);
		//
		setMovementDate(SystemTime.asTimestamp());
		setMovementQty(BigDecimal.ZERO);
		setPosted(false);
		setProcessed(false);
	}    //	MProjectIssue

	/**
	 * Get the next Line No
	 *
	 * @return next line no
	 */
	private int getNextLine()
	{
		return DB.getSQLValue(get_TrxName(),
				"SELECT COALESCE(MAX(Line),0)+10 FROM C_ProjectIssue WHERE C_Project_ID=?", getC_Project_ID());
	}    //	getLineFromProject

	/**
	 * Set Mandatory Values
	 */
	public void setMandatory(final int M_Locator_ID, final int M_Product_ID, final BigDecimal MovementQty)
	{
		setM_Locator_ID(M_Locator_ID);
		setM_Product_ID(M_Product_ID);
		setMovementQty(MovementQty);
	}    //	setMandatory

	public void process()
	{
		saveEx();

		if (getM_Product_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_M_Product_ID);
		}

		final IProductBL productBL = Services.get(IProductBL.class);
		if (productBL.isStocked(ProductId.ofRepoIdOrNull(getM_Product_ID())))
		{
			//	**	Create Material Transactions **
			final MTransaction mTrx = new MTransaction(getCtx(),
					getAD_Org_ID(),
					MTransaction.MOVEMENTTYPE_WorkOrderPlus,
					getM_Locator_ID(),
					getM_Product_ID(),
					getM_AttributeSetInstance_ID(),
					getMovementQty().negate(),
					getMovementDate(),
					get_TrxName());
			mTrx.setC_ProjectIssue_ID(getC_ProjectIssue_ID());
			InterfaceWrapperHelper.save(mTrx);

			final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
			final IStorageBL storageBL = Services.get(IStorageBL.class);

			final I_M_Locator loc = warehouseDAO.getLocatorByRepoId(getM_Locator_ID());
			storageBL.add(getCtx(), loc.getM_Warehouse_ID(), getM_Locator_ID(),
					getM_Product_ID(), getM_AttributeSetInstance_ID(), getM_AttributeSetInstance_ID(),
					getMovementQty().negate(), null, null, get_TrxName());
		}

		setProcessed(true);
		saveEx();
	}

}    //	MProjectIssue
