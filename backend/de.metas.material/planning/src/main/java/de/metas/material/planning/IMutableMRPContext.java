package de.metas.material.planning;

import de.metas.product.ResourceId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_MRP;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public interface IMutableMRPContext extends IMaterialPlanningContext
{
	void setCtx(Properties ctx);

	void setTrxName(String trxName);

	void setLogger(Logger mrpLogger);

	void setDate(Date date);

	void setAD_Client_ID(int adClientId);

	void setAD_Org(I_AD_Org org);

	void setM_Product(I_M_Product product);

	void setM_AttributeSetInstance_ID(int attributeSetInstanceId);

	void setProductPlanning(ProductPlanning productPlanning);

	/**
	 * @see #getPP_MRP()
	 */
	void setPP_MRP(I_PP_MRP mrp);

	void setMRPDemands(List<I_PP_MRP> mrpDemands);

	void setPlantId(ResourceId plantId);

	void setM_Warehouse(I_M_Warehouse warehouse);

	void setPlanningHorizon(Timestamp planningHorizon);

	void setTimeFence(Timestamp timeFence);

	void setPlanner_User_ID(int userId);

	/**
	 * @param supplyPP_Parent_MRP_ID i.e. PP_MRP_ID (Demand)
	 */
	void setEnforced_PP_MRP_Demand_ID(int supplyPP_Parent_MRP_ID);

	/**
	 * @see #isSubsequentMRPExecutorCall()
	 */
	void setSubsequentMRPExecutorCall(final boolean subsequentMRPExecutorCall);

	/**
	 * Sets if MRP is allowed to run an MRP cleanup on this context/segment
	 *
	 * @see #isAllowCleanup()
	 */
	void setAllowCleanup(boolean allowCleanup);
}
