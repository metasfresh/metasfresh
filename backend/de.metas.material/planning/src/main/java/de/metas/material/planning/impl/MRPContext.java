package de.metas.material.planning.impl;

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

import com.google.common.annotations.VisibleForTesting;
import de.metas.logging.LogManager;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.exception.MrpException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_MRP;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * It's possible to create instances of this class directly (intended for testing), but generally, please use a factory to do it.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@VisibleForTesting
public final class MRPContext implements IMutableMRPContext
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
	private ProductPlanning productPlanning;
	@Getter @Setter private ResourceId plantId;
	private I_M_Warehouse warehouse;
	private Timestamp planningHorizon;
	private Timestamp timeFence;
	private int plannerUserId;

	private I_PP_MRP mrp;
	private List<I_PP_MRP> mrpDemands;
	private int enforced_PP_MRP_Demand_ID = -1;

	private BigDecimal qtyProjectOnHand = BigDecimal.ZERO;

	private int attributeSetInstanceId = AttributeConstants.M_AttributeSetInstance_ID_None;

	@VisibleForTesting
	public MRPContext()
	{
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

		sb.append("\n plant=").append(plantId);

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

		sb.append("\n MRP Demands(" + (mrpDemands == null ? 0 : mrpDemands.size()) + "): ").append(mrpDemands);
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
	public ClientId getClientId()
	{
		return ClientId.ofRepoIdOrSystem(adClientId);
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
	public final OrgId getOrgId()
	{
		final I_AD_Org org = getAD_Org();
		if (org == null)
		{
			return OrgId.ANY; // i.e. all organizations
		}
		return OrgId.ofRepoIdOrAny(org.getAD_Org_ID());
	}

	@Override
	public void setAD_Org(final I_AD_Org org)
	{
		this.org = org;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public final ProductId getProductId()
	{
		final I_M_Product product = getM_Product();
		return product == null ? null : ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Override
	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceId);
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int attributeSetInstanceId)
	{
		this.attributeSetInstanceId = attributeSetInstanceId;
	}

	@Override
	public void setM_Product(final I_M_Product product)
	{
		this.product = product;
	}

	@Override
	public ProductPlanning getProductPlanning()
	{
		return productPlanning;
	}

	@Override
	public void setProductPlanning(final ProductPlanning productPlanning)
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
			mrp = this.mrpDemands.get(0);
		}
		else
		{
			mrp = null;
		}

	}

	@Override
	public List<I_PP_MRP> getMRPDemands()
	{
		if (mrpDemands == null)
		{
			return Collections.emptyList();
		}
		return mrpDemands;
	}

	@Override
	public I_M_Warehouse getM_Warehouse()
	{
		return warehouse;
	}

	@Override
	public final WarehouseId getWarehouseId()
	{
		final I_M_Warehouse warehouse = getM_Warehouse();
		if (warehouse == null)
		{
			return null;
		}
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
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
	public void setQtyProjectOnHand(final BigDecimal qtyProjectOnHand)
	{
		Check.assumeNotNull(qtyProjectOnHand, MrpException.class, "qtyProjectOnHand not null");
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
	public void setSubsequentMRPExecutorCall(final boolean subsequentMRPExecutorCall)
	{
		this.subsequentMRPExecutorCall = subsequentMRPExecutorCall;
	}

	@Override
	public boolean isAllowCleanup()
	{
		return allowCleanup;
	}

	@Override
	public void setAllowCleanup(final boolean allowCleanup)
	{
		this.allowCleanup = allowCleanup;
	}

	@Override
	public void assertContextConsistent()
	{
		final ProductId contextProductId = getProductId();
		final ProductId productPlanningProductId = getProductPlanning().getProductId();

		if (!Objects.equals(contextProductId, productPlanningProductId))
		{
			final String message = String.format("The given IMaterialPlanningContext has M_Product_ID=%s, but its included PP_Product_Planning has M_Product_ID=%s",
					contextProductId, productPlanningProductId);
			throw new AdempiereException(message)
					.appendParametersToMessage()
					.setParameter("IMaterialPlanningContext", this)
					.setParameter("IMaterialPlanningContext.M_Product_ID", contextProductId)
					.setParameter("IMaterialPlanningContext.ProductPlanning", getProductPlanning())
					.setParameter("IMaterialPlanningContext.ProductPlanning.M_Product_ID", productPlanningProductId);
		}
	}
}
