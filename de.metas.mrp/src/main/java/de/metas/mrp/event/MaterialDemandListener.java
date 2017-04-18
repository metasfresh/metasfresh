package de.metas.mrp.event;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPContextFactory;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMutableMRPContext;
import org.eevolution.mrp.api.impl.MRPCreateSupplyRequest;
import org.eevolution.mrp.api.impl.MRPExecutor;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class MaterialDemandListener implements MaterialEventListener
{
	private static final transient Logger logger = LogManager.getLogger(MaterialDemandListener.class);

	public static final MaterialDemandListener INSTANCE = new MaterialDemandListener();

	private MaterialDemandListener()
	{
	}

	@Override
	public void onEvent(MaterialEvent event)
	{
		if (!(event instanceof MaterialDemandEvent))
		{
			return;
		}
		logger.info("Received event {}", event);

		handleMaterialDemandEvent((MaterialDemandEvent)event);
	}

	private void handleMaterialDemandEvent(final MaterialDemandEvent materialDemandEvent)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final MaterialDescriptor descr = materialDemandEvent.getDescr();

				final Properties ctx = Env.getCtx();

				final I_AD_Org org = InterfaceWrapperHelper.create(ctx, descr.getOrgId(), I_AD_Org.class, localTrxName);
				final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, descr.getWarehouseId(), I_M_Warehouse.class, localTrxName);
				final I_M_Product product = InterfaceWrapperHelper.create(ctx, descr.getProductId(), I_M_Product.class, localTrxName);

				final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

				final I_S_Resource plant = productPlanningDAO.findPlant(ctx,
						descr.getOrgId(),
						warehouse,
						descr.getProductId());

				final I_PP_Product_Planning productPlanning = productPlanningDAO.find(ctx,
						descr.getOrgId(),
						descr.getWarehouseId(),
						plant.getS_Resource_ID(),
						descr.getProductId(),
						localTrxName);

				final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
				final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

				mrpContext.setM_Product(product);
				mrpContext.setM_Warehouse(warehouse);
				mrpContext.setDate(descr.getDate());
				mrpContext.setCtx(ctx);
				mrpContext.setTrxName(localTrxName);
				mrpContext.setRequireDRP(true); // DRP means distribution resource planning? i.e. "consider making DD_Orders"?

				mrpContext.setProductPlanning(productPlanning);
				mrpContext.setPlant(plant);

				mrpContext.setAD_Client_ID(org.getAD_Client_ID());
				mrpContext.setAD_Org(org);

				final IMRPSupplyProducerFactory mrpSupplyProducerFactory = Services.get(IMRPSupplyProducerFactory.class);
				final IMRPSupplyProducer supplyProducer = mrpSupplyProducerFactory.getSupplyProducer(mrpContext);

				final IMRPCreateSupplyRequest request = new MRPCreateSupplyRequest(
						mrpContext,
						new MRPExecutor(),
						descr.getQty(),
						descr.getDate(),
						ImmutableList.of());
				mrpContext.setMRPDemands(request.getMRPDemandRecords());
				supplyProducer.createSupply(request);

				supplyProducer.createSupply(request);
				
				
			}
		});
	}

	
	
}
