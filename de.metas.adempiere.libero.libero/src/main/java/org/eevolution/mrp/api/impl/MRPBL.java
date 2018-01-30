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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TrxRunnable;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPAllocDAO;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContextRunnable;
import org.eevolution.mrp.api.MRPFirmType;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;

public class MRPBL implements IMRPBL
{
	private static final transient Logger logger = LogManager.getLogger(MRPBL.class);

	@Override
	public I_PP_MRP createMRP(final Object contextProvider)
	{
		final I_PP_MRP mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, contextProvider);

		updateMRPFromContext(mrp);

		mrp.setValue("MRP");
		mrp.setName("MRP");
		mrp.setDateSimulation(SystemTime.asTimestamp());

		//
		// The available flag should be handled by MRP engine. Initial it should be disabled.
		// see : [ 2593359 ] Calculate Material Plan error related to MRP-060 notice
		// https://sourceforge.net/tracker/?func=detail&atid=934929&aid=2593359&group_id=176962
		//
		// NOTE: if this is triggered while calculating material plan,
		// the org.eevolution.mrp.api.IMRPExecutor.onMRPRecordBeforeCreate(I_PP_MRP) can also change the IsAvailable flag.
		// But, by default we consider it not available.
		mrp.setIsAvailable(false);

		return mrp;
	}

	@Override
	public I_PP_MRP_Alternative createMRPAlternative(final I_PP_MRP mrp)
	{
		I_PP_MRP_Alternative mrpAlternative = InterfaceWrapperHelper.newInstance(I_PP_MRP_Alternative.class, mrp);
		mrpAlternative.setPP_MRP(mrp);

		return mrpAlternative;
	}

	@Override
	public void updateMRPFromContext(final I_PP_MRP mrp)
	{
		final IMaterialPlanningContext mrpContext = mrpContextThreadLocal.get();
		if (mrpContext == null)
		{
			return;
		}

		// TODO: shall we update the PP_MRP_Parent even if the mrp is not new?

		final I_PP_MRP parentMRPRecord = mrpContext.getPP_MRP();
		mrp.setPP_MRP_Parent(parentMRPRecord);
		if (parentMRPRecord != null)
		{
			mrp.setC_OrderLineSO_ID(parentMRPRecord.getC_OrderLineSO_ID());
			mrp.setS_Resource_ID(parentMRPRecord.getS_Resource_ID()); // Plant
		}
	}

	@Override
	public void createMRPAllocationsFromContext(final I_PP_MRP mrpSupply)
	{
		final IMaterialPlanningContext mrpContext = mrpContextThreadLocal.get();
		if (mrpContext == null)
		{
			return;
		}

		final List<I_PP_MRP> mrpDemands = mrpContext.getMRPDemands();
		if (mrpDemands.isEmpty())
		{
			return;
		}

		final IMRPAllocDAO mrpAllocDAO = Services.get(IMRPAllocDAO.class);
		mrpAllocDAO.createMRPAllocs()
				.setMRPSupply(mrpSupply)
				.setMRPDemands(mrpDemands)
				.build();
	}

	@Override
	public boolean isReleased(final I_PP_MRP mrp)
	{
		final String docStatus = mrp.getDocStatus();
		if (docStatus == null)
		{
			return false;
		}

		return MRPFirmType.Firm.hasDocStatus(docStatus);
	}

	@Override
	public boolean isDemand(final I_PP_MRP mrp)
	{
		if (mrp == null)
		{
			return false;
		}

		final String typeMRP = mrp.getTypeMRP();
		return X_PP_MRP.TYPEMRP_Demand.equals(typeMRP);
	}

	@Override
	public boolean isSupply(final I_PP_MRP mrp)
	{
		if (mrp == null)
		{
			return false;
		}

		final String typeMRP = mrp.getTypeMRP();
		return X_PP_MRP.TYPEMRP_Supply.equals(typeMRP);
	}

	@Override
	public void setQty(final I_PP_MRP mrp, final BigDecimal qtyTarget, final BigDecimal qty, final I_C_UOM uom)
	{
		final BigDecimal qtyInStockingUOM;
		final BigDecimal qtyTargetInStockingUOM;
		if (uom != null)
		{
			final I_M_Product product = mrp.getM_Product();
			final I_C_UOM uomTo = getC_UOM(mrp);

			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
			final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);
			
			qtyTargetInStockingUOM = uomConversionBL.convertQty(uomConversionCtx, qtyTarget, uom, uomTo);
			qtyInStockingUOM = uomConversionBL.convertQty(uomConversionCtx, qty, uom, uomTo);
		}
		else
		{
			final LiberoException ex = new LiberoException("No UOM specified when setting quantity.");
			logger.warn(ex.getLocalizedMessage() + " [UOM Conversion skipped]", ex);
			
			qtyTargetInStockingUOM = qtyTarget;
			qtyInStockingUOM = qty;
		}

		mrp.setQtyRequiered(qtyTargetInStockingUOM);
		mrp.setQty(qtyInStockingUOM);
	}

	@Override
	public I_C_UOM getC_UOM(final I_PP_MRP mrp)
	{
		Check.assumeNotNull(mrp, "mrp not null");
		return Services.get(IProductBL.class).getStockingUOM(mrp.getM_Product_ID());
	}

	@Override
	public I_C_UOM getC_UOM(final I_PP_MRP_Alternative mrpAlternative)
	{
		Check.assumeNotNull(mrpAlternative, "mrpAlternative not null");
		return Services.get(IProductBL.class).getStockingUOM(mrpAlternative.getM_Product_ID());
	}

	@Override
	public String toString(final I_PP_MRP mrp)
	{
		final String description = mrp.getDescription();
		return mrp.getClass().getSimpleName() + "["
				+ ", TypeMRP=" + mrp.getTypeMRP()
				+ ", DocStatus=" + mrp.getDocStatus()
				+ ", Qty=" + mrp.getQty()
				+ ", DatePromised=" + mrp.getDatePromised()
				+ ", Schedule=" + mrp.getDateStartSchedule() + "/" + mrp.getDateFinishSchedule()
				+ ", IsAvailable=" + mrp.isAvailable()
				+ (!Check.isEmpty(description, true) ? ", Description=" + description : "")
				+ ", ID=" + mrp.getPP_MRP_ID()
				+ "]";
	}

	@Override
	public void executeInMRPContext(final IMaterialPlanningContext mrpContext, final IMRPContextRunnable runnable)
	{
		Check.assumeNotNull(mrpContext, LiberoException.class, "mrpContext not null");
		Check.assumeNotNull(runnable, LiberoException.class, "runnable not null");

		// services
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);

		//
		// Execute in transaction
		// TODO: maybe it would be a bit more performant to not create a savepoint in case we are already running in a transaction
		// (e.g. see how we did it on org.compiere.model.ModelValidationEngine.executeInTrx(String, int, Runnable) )
		trxManager.run(mrpContext.getTrxName(), new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				//
				// Create a local MRPContext for our transaction
				// based on the mrp context that we got as parameter
				final IMutableMRPContext mrpContextLocal = mrpContextFactory.createMRPContext(mrpContext);
				mrpContextLocal.setTrxName(localTrxName);

				// Make sure the supply is created in the current context, with the correct parent
				final IMaterialPlanningContext mrpContextFromThreadLocal = mrpContextThreadLocal.get();
				mrpContextThreadLocal.set(mrpContextLocal);
				try
				{
					//
					runnable.run(mrpContextLocal);
				}
				finally
				{
					// After creating the supply, set back the old mrp context so it doesn't interfere with the old open tasks
					mrpContextThreadLocal.set(mrpContextFromThreadLocal);
				}
			}
		});
	}

	private final transient InheritableThreadLocal<IMaterialPlanningContext> mrpContextThreadLocal = new InheritableThreadLocal<>();

	@Override
	public BigDecimal getQtyAbs(final I_PP_MRP mrp)
	{
		Check.assumeNotNull(mrp, LiberoException.class, "mrp not null");
		final BigDecimal qty = mrp.getQty();
		final String typeMRP = mrp.getTypeMRP();

		if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			return qty.negate();
		}
		else if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			return qty;
		}
		else
		{
			throw new LiberoException("Unknown TypeMRP '" + typeMRP + "' for " + mrp);
		}
	}

	@Override
	public IMRPSegment createMRPSegment(final I_PP_MRP mrp)
	{
		Check.assumeNotNull(mrp, "mrp not null");

		final int adClientId = mrp.getAD_Client_ID();
		final I_AD_Org adOrg = mrp.getAD_Org();
		final I_M_Warehouse warehouse = mrp.getM_Warehouse();
		final I_S_Resource plant = mrp.getS_Resource();
		final I_M_Product product = mrp.getM_Product();

		return new MRPSegment(adClientId, adOrg, warehouse, plant, product);
	}

	@Override
	public boolean isQtyOnHandReservation(final I_PP_MRP mrpSupply)
	{
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");

		final String typeMRP = mrpSupply.getTypeMRP();
		if (!X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			return false;
		}

		final String orderType = mrpSupply.getOrderType();
		if (!X_PP_MRP.ORDERTYPE_QuantityOnHandReservation.equals(orderType))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isQtyOnHandInTransit(final I_PP_MRP mrpSupply)
	{
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");

		final String typeMRP = mrpSupply.getTypeMRP();
		if (!X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			return false;
		}

		final String orderType = mrpSupply.getOrderType();
		if (!X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit.equals(orderType))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isQtyOnHandAnyReservation(final I_PP_MRP mrpSupply)
	{
		return isQtyOnHandReservation(mrpSupply)
				|| isQtyOnHandInTransit(mrpSupply);
	}
}
