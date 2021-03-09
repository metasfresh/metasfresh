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

import de.metas.logging.LogManager;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.MRPFirmType;
import org.slf4j.Logger;

import java.math.BigDecimal;

public class MRPBL implements IMRPBL
{
	private static final transient Logger logger = LogManager.getLogger(MRPBL.class);

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
			final int productId = mrp.getM_Product_ID();
			final I_C_UOM uomTo = getC_UOM(mrp);

			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
			final UOMConversionContext uomConversionCtx = UOMConversionContext.of(productId);
			
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
		return Services.get(IProductBL.class).getStockUOM(mrp.getM_Product_ID());
	}

	@Override
	public I_C_UOM getC_UOM(final I_PP_MRP_Alternative mrpAlternative)
	{
		Check.assumeNotNull(mrpAlternative, "mrpAlternative not null");
		return Services.get(IProductBL.class).getStockUOM(mrpAlternative.getM_Product_ID());
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
		return X_PP_MRP.ORDERTYPE_QuantityOnHandReservation.equals(orderType);
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
		return X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit.equals(orderType);
	}

	@Override
	public boolean isQtyOnHandAnyReservation(final I_PP_MRP mrpSupply)
	{
		return isQtyOnHandReservation(mrpSupply)
				|| isQtyOnHandInTransit(mrpSupply);
	}
}
