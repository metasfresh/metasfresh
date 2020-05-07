package de.metas.material.planning.impl;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.material.planning.exception.MrpException;
import lombok.NonNull;

@Service
public class MRPContextFactory implements IMRPContextFactory
{

	private final ProductPlanningBL productPlanningBL;

	public MRPContextFactory(@NonNull final ProductPlanningBL productPlanningBL)
	{
		this.productPlanningBL = productPlanningBL;
	}

	@Override
	public IMutableMRPContext createInitialMRPContext()
	{
		return new MRPContext();
	}

	@Override
	public IMutableMRPContext createMRPContext(final IMaterialPlanningContext context)
	{
		final IMutableMRPContext mrpContextNew = new MRPContext();

		Check.assumeNotNull(context, MrpException.class, "context not null");

		mrpContextNew.setCtx(context.getCtx());
		mrpContextNew.setTrxName(context.getTrxName());
		mrpContextNew.setSubsequentMRPExecutorCall(context.isSubsequentMRPExecutorCall());
		mrpContextNew.setLogger(context.getLogger());
		mrpContextNew.setDate(context.getDate());
		mrpContextNew.setAllowCleanup(context.isAllowCleanup());

		mrpContextNew.setAD_Client_ID(context.getAD_Client_ID());
		mrpContextNew.setAD_Org(context.getAD_Org());
		mrpContextNew.setM_Product(context.getM_Product());
		// mrpContextNew.setProductPlanning(context.getProductPlanning()); // needs to be copied, see below

		mrpContextNew.setMRPDemands(context.getMRPDemands());
		mrpContextNew.setPP_MRP(context.getPP_MRP());
		mrpContextNew.setEnforced_PP_MRP_Demand_ID(context.getEnforced_PP_MRP_Demand_ID());

		// mrpContextNew.setQtyToSupply(context.getQtyToSupply());
		mrpContextNew.setQtyProjectOnHand(context.getQtyProjectOnHand());

		mrpContextNew.setPlant(context.getPlant());
		mrpContextNew.setM_Warehouse(context.getM_Warehouse());
		mrpContextNew.setPlanningHorizon(context.getPlanningHorizon());
		mrpContextNew.setTimeFence(context.getTimeFence());
		mrpContextNew.setPlanner_User_ID(context.getPlanner_User_ID());

		//
		// Make sure we have a clone of product planning in new context
		// because we will change it
		final I_PP_Product_Planning productPlanningOld = context.getProductPlanning();
		final I_PP_Product_Planning productPlanningNew;
		if (productPlanningOld != null)
		{
			productPlanningNew = productPlanningBL.createPlainProductPlanning(productPlanningOld);
		}
		else
		{
			productPlanningNew = null;
		}
		mrpContextNew.setProductPlanning(productPlanningNew);

		return mrpContextNew;
	}

	@Override
	public IMaterialPlanningContext createMRPContext(final IMaterialPlanningContext mrpContext0, final IMRPSegment mrpSegment)
	{
		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createMRPContext(mrpContext0);

		//
		// AD_Client_ID
		final int adClientId = mrpSegment.getAD_Client_ID();
		mrpContext.setAD_Client_ID(adClientId);

		//
		// Plant
		final I_S_Resource plant = mrpSegment.getPlant();
		Check.assumeNotNull(plant, MrpException.class, "plant not null");
		mrpContext.setPlant(plant);
		mrpContext.setPlanningHorizon(TimeUtil.addDays(mrpContext.getDate(), plant.getPlanningHorizon()));

		//
		// Org
		final I_AD_Org org = mrpSegment.getAD_Org();
		Check.assumeNotNull(org, MrpException.class, "organization not null");
		mrpContext.setAD_Org(org);

		//
		// Warehouse
		final I_M_Warehouse warehouse = mrpSegment.getM_Warehouse();
		Check.assumeNotNull(warehouse, MrpException.class, "warehouse not null");
		mrpContext.setM_Warehouse(warehouse);

		//
		// Product
		final I_M_Product product = mrpSegment.getM_Product();
		// null is also OK
		mrpContext.setM_Product(product);

		return mrpContext;
	}

}
