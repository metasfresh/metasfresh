package org.eevolution.mrp.api.impl;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.mrp.api.IMutableMRPContext;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/* package */final class MRPContext implements IMutableMRPContext
{
	// NOTE to developer: when adding a new field here, please also change into org.eevolution.mrp.api.impl.MRPContextFactory.createMRPContext(IMRPContext)

	public static final transient Logger loggerDefault = LogManager.getLogger(LOGGERNAME);

	private Properties ctx;
	private String trxName;
	private Logger mrpLogger = loggerDefault;
	private boolean subsequentMRPExecutorCall = false;
	private boolean allowCleanup = true;
	//
	private Date date;
	private int adClientId = -1;
	private I_AD_Org org;
	private boolean requireDRP;
	private I_M_Product product;
	private I_PP_Product_Planning productPlanning;
	private I_S_Resource plant;
	private I_M_Warehouse warehouse;
	private Timestamp planningHorizon;
	private Timestamp timeFence;
	private int plannerUserId;

	private I_PP_MRP mrp;
	private List<I_PP_MRP> mrpDemands;
	private int enforced_PP_MRP_Demand_ID = -1;

	private BigDecimal qtyProjectOnHand = BigDecimal.ZERO;

	/* package */ MRPContext()
	{
		super();
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MRPContext [");
		sb.append("\n adClientId=" + adClientId);

		sb.append("\n org=");
		if (org == null)
		{
			sb.append("-");
		}
		else
		{
			sb.append(org.getName()).append(" (AD_Org_ID=").append(org.getAD_Org_ID()).append(")");
		}

		sb.append("\n plant=");
		if (plant == null)
		{
			sb.append("-");
		}
		else
		{
			sb.append(plant.getName()).append(" (S_Resource_ID=").append(plant.getS_Resource_ID()).append(")");
		}

		sb.append("\n warehouse=");
		if (warehouse == null)
		{
			sb.append("-");
		}
		else
		{
			sb.append(warehouse.getName()).append(" (M_Warehouse_ID=").append(warehouse.getM_Warehouse_ID()).append(")");
		}

		//
		sb.append("\n product=");
		if (product == null)
		{
			sb.append("-");
		}
		else
		{
			sb.append(product.getName()).append(" (M_Product_ID=").append(product.getM_Product_ID()).append(")");
		}

		sb.append("\n qtyProjectOnHand=" + qtyProjectOnHand);
		//
		sb.append("\n productPlanning=" + productPlanning);
		//
		sb.append("\n trxName=" + trxName);
		sb.append("\n date=" + date);
		sb.append("\n requireDRP=" + requireDRP);

		sb.append("\n MRP Demands(" + (this.mrpDemands == null ? 0 : this.mrpDemands.size()) + "): ").append(this.mrpDemands);
		sb.append("\n PP_MRP_ID=");
		if (mrp == null)
		{
			sb.append("-");
		}
		else
		{
			sb.append(mrp.getPP_MRP_ID());
		}

		if (enforced_PP_MRP_Demand_ID > 0)
		{
			sb.append("\n enforced_PP_MRP_Demand_ID=").append(enforced_PP_MRP_Demand_ID);
		}

		sb.append("\n planningHorizon=" + planningHorizon);
		sb.append("\n timeFence=" + timeFence);
		sb.append("\n plannerUserId=" + plannerUserId);

		if (!allowCleanup)
		{
			sb.append("\n allowCleanup=" + allowCleanup);
		}

		sb.append("]");
		return sb.toString();
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	/**
	 * Returns <code>false</code> because we are going to work a lot on MRP, so we should do it currectly from the start.
	 */
	@Override
	public boolean isAllowThreadInherited()
	{
		return false;
	}

	@Override
	public void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public void setTrxName(final String trxName)
	{
		this.trxName = trxName;
	}

	@Override
	public final Logger getLogger()
	{
		return mrpLogger;
	}

	@Override
	public final void setLogger(final Logger mrpLogger)
	{
		Check.assumeNotNull(mrpLogger, "mrpLogger not null");
		this.mrpLogger = mrpLogger;
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public void setDate(final Date date)
	{
		this.date = date;
	}

	@Override
	public Timestamp getDateAsTimestamp()
	{
		return TimeUtil.asTimestamp(date);
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public void setAD_Client_ID(final int adClientId)
	{
		this.adClientId = adClientId;
	}

	@Override
	public I_AD_Org getAD_Org()
	{
		return org;
	}

	@Override
	public final int getAD_Org_ID()
	{
		final I_AD_Org org = getAD_Org();
		if (org == null)
		{
			return 0; // i.e. all organizations
		}
		return org.getAD_Org_ID();
	}

	@Override
	public void setAD_Org(final I_AD_Org org)
	{
		this.org = org;
	}

	@Override
	public boolean isRequireDRP()
	{
		return requireDRP;
	}

	@Override
	public void setRequireDRP(final boolean requiredDRP)
	{
		requireDRP = requiredDRP;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public final int getM_Product_ID()
	{
		final I_M_Product product = getM_Product();
		return product == null ? -1 : product.getM_Product_ID();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		// NOTE: MRP's UOM is Product's stocking UOM
		Check.assumeNotNull(product, LiberoException.class, "product not null");
		return product.getC_UOM();
	}

	@Override
	public void setM_Product(final I_M_Product product)
	{
		this.product = product;
	}

	@Override
	public I_PP_Product_Planning getProductPlanning()
	{
		return productPlanning;
	}

	@Override
	public void setProductPlanning(final I_PP_Product_Planning productPlanning)
	{
		this.productPlanning = productPlanning;
	}

	@Override
	public I_PP_MRP getPP_MRP()
	{
		return mrp;
	}

	@Override
	public void setPP_MRP(final I_PP_MRP mrp)
	{
		this.mrp = mrp;
	}

	@Override
	public void setMRPDemands(final List<I_PP_MRP> mrpDemands)
	{
		if (mrpDemands == null || mrpDemands.isEmpty())
		{
			this.mrpDemands = null;
		}
		else
		{
			this.mrpDemands = Collections.unmodifiableList(new ArrayList<>(mrpDemands));
		}

		// Update PP_MRP record
		if (this.mrpDemands != null && this.mrpDemands.size() == 1)
		{
			this.mrp = this.mrpDemands.get(0);
		}
		else
		{
			this.mrp = null;
		}

	}

	@Override
	public List<I_PP_MRP> getMRPDemands()
	{
		if (mrpDemands == null)
		{
			return Collections.emptyList();
		}
		return this.mrpDemands;
	}

	@Override
	public I_S_Resource getPlant()
	{
		return plant;
	}

	@Override
	public final int getPlant_ID()
	{
		final I_S_Resource plant = getPlant();
		if (plant == null)
		{
			return -1;
		}
		return plant.getS_Resource_ID();
	}

	@Override
	public void setPlant(final I_S_Resource plant)
	{
		this.plant = plant;
	}

	@Override
	public I_M_Warehouse getM_Warehouse()
	{
		return warehouse;
	}

	@Override
	public final int getM_Warehouse_ID()
	{
		final I_M_Warehouse warehouse = getM_Warehouse();
		if (warehouse == null)
		{
			return -1;
		}
		return warehouse.getM_Warehouse_ID();
	}

	@Override
	public void setM_Warehouse(final I_M_Warehouse warehouse)
	{
		this.warehouse = warehouse;
	}

	@Override
	public Timestamp getPlanningHorizon()
	{
		return planningHorizon;
	}

	@Override
	public void setPlanningHorizon(final Timestamp planningHorizon)
	{
		this.planningHorizon = planningHorizon;
	}

	@Override
	public void setTimeFence(final Timestamp timeFence)
	{
		this.timeFence = timeFence;
	}

	@Override
	public Timestamp getTimeFence()
	{
		return timeFence;
	}

	@Override
	public int getPlanner_User_ID()
	{
		return plannerUserId;
	}

	@Override
	public void setPlanner_User_ID(final int plannerUserId)
	{
		this.plannerUserId = plannerUserId;
	}

	@Override
	public BigDecimal getQtyProjectOnHand()
	{
		return qtyProjectOnHand;
	}

	@Override
	public void setQtyProjectOnHand(BigDecimal qtyProjectOnHand)
	{
		Check.assumeNotNull(qtyProjectOnHand, LiberoException.class, "qtyProjectOnHand not null");
		this.qtyProjectOnHand = qtyProjectOnHand;
	}

	@Override
	public int getEnforced_PP_MRP_Demand_ID()
	{
		return enforced_PP_MRP_Demand_ID;
	}

	@Override
	public void setEnforced_PP_MRP_Demand_ID(final int enforced_PP_MRP_Demand_ID)
	{
		this.enforced_PP_MRP_Demand_ID = enforced_PP_MRP_Demand_ID > 0 ? enforced_PP_MRP_Demand_ID : -1;
	}

	@Override
	public boolean isSubsequentMRPExecutorCall()
	{
		return subsequentMRPExecutorCall;
	}

	@Override
	public void setSubsequentMRPExecutorCall(boolean subsequentMRPExecutorCall)
	{
		this.subsequentMRPExecutorCall = subsequentMRPExecutorCall;
	}

	@Override
	public boolean isAllowCleanup()
	{
		return allowCleanup;
	}

	@Override
	public void setAllowCleanup(boolean allowCleanup)
	{
		this.allowCleanup = allowCleanup;
	}

}
