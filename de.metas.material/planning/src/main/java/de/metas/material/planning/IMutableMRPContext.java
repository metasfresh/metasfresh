package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.slf4j.Logger;

public interface IMutableMRPContext extends IMaterialPlanningContext
{
	void setCtx(Properties ctx);

	void setTrxName(String trxName);

	void setLogger(Logger mrpLogger);

	@Override
	Date getDate();

	void setDate(Date date);

	@Override
	Timestamp getDateAsTimestamp();

	@Override
	int getAD_Client_ID();

	void setAD_Client_ID(int adClientId);

	@Override
	I_AD_Org getAD_Org();

	void setAD_Org(final I_AD_Org org);

	@Override
	boolean isRequireDRP();

	void setRequireDRP(boolean requiredDRP);

	@Override
	I_M_Product getM_Product();

	@Override
	int getM_Product_ID();

	public void setM_Product(final I_M_Product product);

	@Override
	I_PP_Product_Planning getProductPlanning();

	void setProductPlanning(final I_PP_Product_Planning productPlanning);

	@Override
	I_PP_MRP getPP_MRP();

	/**
	 *
	 * @param mrp
	 * @see #getPP_MRP()
	 */
	void setPP_MRP(I_PP_MRP mrp);

	@Override
	List<I_PP_MRP> getMRPDemands();

	void setMRPDemands(List<I_PP_MRP> mrpDemands);

	@Override
	I_S_Resource getPlant();

	void setPlant(final I_S_Resource resource);

	@Override
	I_M_Warehouse getM_Warehouse();

	void setM_Warehouse(final I_M_Warehouse warehouse);

	@Override
	Timestamp getPlanningHorizon();

	public void setPlanningHorizon(final Timestamp planningHorizon);

	void setTimeFence(Timestamp timeFence);

	@Override
	Timestamp getTimeFence();

	@Override
	int getPlanner_User_ID();

	void setPlanner_User_ID(int userId);

	@Override
	void setQtyProjectOnHand(BigDecimal qtyProjectOnHand);

	@Override
	BigDecimal getQtyProjectOnHand();

	@Override
	int getEnforced_PP_MRP_Demand_ID();

	/**
	 * @param supplyPP_Parent_MRP_ID i.e. PP_MRP_ID (Demand)
	 */
	void setEnforced_PP_MRP_Demand_ID(int supplyPP_Parent_MRP_ID);

	@Override
	boolean isSubsequentMRPExecutorCall();

	/**
	 *
	 * @param subsequentMRPExecutorCall
	 * @see #isSubsequentMRPExecutorCall()
	 */
	void setSubsequentMRPExecutorCall(final boolean subsequentMRPExecutorCall);

	/**
	 * Sets if MRP is allowed to run an MRP cleanup on this context/segment
	 *
	 * @param allowCleanup
	 * @see #isAllowCleanup()
	 */
	void setAllowCleanup(boolean allowCleanup);
}
