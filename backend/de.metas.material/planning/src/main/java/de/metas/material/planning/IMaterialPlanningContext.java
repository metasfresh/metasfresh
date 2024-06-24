package de.metas.material.planning;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_MRP;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * MRP working context.
 *
 * It will continuously updated by MRP classes while they perform. See it as a workfile.
 *
 * @author tsa
 *
 */
public interface IMaterialPlanningContext extends IContextAware
{
	/** Default logger to be be used on MRP execution */
	String LOGGERNAME = "org.eevolution.mrp.MRP";

	//
	// Context
	@Override
	Properties getCtx();

	@Override
	String getTrxName();

	//
	// Loggers and listeners
	/**
	 * Gets MRP logger.
	 *
	 * @return MRP logger; never return null
	 */
	Logger getLogger();

	//
	// Planning Segment
	//@formatter:off
	ClientId getClientId();

	ResourceId getPlantId();

	OrgId getOrgId();
	I_AD_Org getAD_Org();

	WarehouseId getWarehouseId();
	I_M_Warehouse getM_Warehouse();

	ProductId getProductId();
	// int getM_Product_ID();
	I_M_Product getM_Product();

	AttributeSetInstanceId getAttributeSetInstanceId();
	//@formatter:on

	Date getDate();

	Timestamp getDateAsTimestamp();

	ProductPlanning getProductPlanning();

	/**
	 *
	 * @return Demand or Supply MRP record; also it can be <code>null</code> so please make sure you are doing NPE checks.
	 */
	I_PP_MRP getPP_MRP();

	/**
	 * @return current MRP demands in context; never return null
	 */
	List<I_PP_MRP> getMRPDemands();

	/**
	 * MRP Demand on which is enforced. MRP executor shall run only for that MRP demand.
	 *
	 * Used to narrow down current MRP context in case we want to run MRP and regenerate only those documents which are affected by given parent Supply MRP record.
	 *
	 * @return PP_MRP_ID (Demand)
	 */
	int getEnforced_PP_MRP_Demand_ID();

	/**
	 * Gets date until which we are planning ahead. Everything which is after this date shall not be in the scope of MRP planning.
	 *
	 * @return planning horizon.
	 */
	Timestamp getPlanningHorizon();

	int getPlanner_User_ID();

	Timestamp getTimeFence();

	BigDecimal getQtyProjectOnHand();

	void setQtyProjectOnHand(BigDecimal qtyProjectOnHand);

	/**
	 * Returns <code>true</code> if this is will a subsequent MRP executor call (i.e. MRP executor called again from inside MRP module).
	 *
	 * This information is mainly used to decide if we shall do the cleanup, MRP records locking in transaction or out of transaction.
	 *
	 * In case it's <code>true</code> the cleanup will be performed in transaction because we assume that the MRP executor caller already did some cleanups out of transaction so we need to avoid
	 * database deadlocks.
	 */
	boolean isSubsequentMRPExecutorCall();

	/**
	 *
	 * @return true if MRP is allowed to run MRP cleanup on this context/segment.
	 */
	boolean isAllowCleanup();

	void assertContextConsistent();
}
