package org.eevolution.callout;

import java.math.BigDecimal;
import java.util.Properties;

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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.api.IDDOrderLineBL;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.MDDOrderLine;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

@Callout(I_DD_OrderLine.class)
public class DD_OrderLine
{
	public static final DD_OrderLine instance = new DD_OrderLine();

	private static final Logger logger = LogManager.getLogger(DD_OrderLine.class);

	/**
	 * Calls {@link IDDOrderLineBL#setUOMInDDOrderLine(I_DD_OrderLine)}.
	 * 
	 * @param ddOrderLine
	 * @param field
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08583_Erfassung_Packvorschrift_in_DD_Order_ist_crap_%28108882381939%29
	 *       ("UOM In manual DD_OrderLine shall always be the uom of the product ( as talked with Mark) ")
	 */
	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_M_Product_ID })
	public void setUOMInDDOrderLine(final I_DD_OrderLine ddOrderLine, final ICalloutField field)
	{
		Services.get(IDDOrderLineBL.class).setUOMInDDOrderLine(ddOrderLine);
	}

	@CalloutMethod(columnNames = {
			I_DD_OrderLine.COLUMNNAME_C_UOM_ID //
			, I_DD_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID //
			, I_DD_OrderLine.COLUMNNAME_M_AttributeSetInstanceTo_ID //
			, I_DD_OrderLine.COLUMNNAME_QtyEntered //
			, I_DD_OrderLine.COLUMNNAME_QtyOrdered //
	})
	public void qty(final I_DD_OrderLine ddOrderLine, final ICalloutField field)
	{
		if (field.getValue() == null)
		{
			return;
		}

		final Properties ctx = field.getCtx();
		final String columnName = field.getColumnName();
		final int windowNo = field.getWindowNo();

		final int M_Product_ID = ddOrderLine.getM_Product_ID();
		BigDecimal QtyOrdered = BigDecimal.ZERO;
		BigDecimal QtyEntered;

		// No Product
		if (M_Product_ID <= 0)
		{
			QtyEntered = ddOrderLine.getQtyEntered();
			QtyOrdered = QtyEntered;
			ddOrderLine.setQtyOrdered(QtyOrdered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (I_DD_OrderLine.COLUMNNAME_C_UOM_ID.equals(columnName))
		{
			int C_UOM_To_ID = ddOrderLine.getC_UOM_ID();
			QtyEntered = ddOrderLine.getQtyEntered();
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				logger.debug("Corrected QtyEntered Scale UOM={}; QtyEntered={}->{}", C_UOM_To_ID, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				ddOrderLine.setQtyEntered(QtyEntered);
			}

			QtyOrdered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			final boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			Env.setContext(ctx, windowNo, "UOMConversion", conversion);
			ddOrderLine.setQtyOrdered(QtyOrdered);
		}
		// QtyEntered changed - calculate QtyOrdered
		else if (I_DD_OrderLine.COLUMNNAME_QtyEntered.equals(columnName))
		{
			int C_UOM_To_ID = ddOrderLine.getC_UOM_ID();
			QtyEntered = ddOrderLine.getQtyEntered();
			final BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				logger.debug("Corrected QtyEntered Scale UOM={}; QtyEntered={}->{}", C_UOM_To_ID, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				ddOrderLine.setQtyEntered(QtyEntered);
			}
			QtyOrdered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			logger.debug("UOM={}, QtyEntered={}, conversion={}, QtyOrdered={}", C_UOM_To_ID, QtyEntered, conversion, QtyOrdered);
			Env.setContext(ctx, windowNo, "UOMConversion", conversion);
			ddOrderLine.setQtyOrdered(QtyOrdered);
		}
		// QtyOrdered changed - calculate QtyEntered (should not happen)
		else if (I_DD_OrderLine.COLUMNNAME_QtyOrdered.equals(columnName))
		{
			final int C_UOM_To_ID = ddOrderLine.getC_UOM_ID();
			QtyOrdered = ddOrderLine.getQtyOrdered();
			final int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision();
			final BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyOrdered.compareTo(QtyOrdered1) != 0)
			{
				logger.debug("Corrected QtyOrdered Scale: {}->{}", QtyOrdered, QtyOrdered1);
				QtyOrdered = QtyOrdered1;
				ddOrderLine.setQtyOrdered(QtyOrdered);
			}
			QtyEntered = MUOMConversion.convertFromProductUOM(ctx, M_Product_ID, C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			final boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			logger.debug("UOM={}, QtyEntered={}, conversion={}, QtyOrdered={}", C_UOM_To_ID, QtyEntered, conversion, QtyOrdered);
			Env.setContext(ctx, windowNo, "UOMConversion", conversion);
			ddOrderLine.setQtyEntered(QtyEntered);
		}
		else
		{
			QtyOrdered = ddOrderLine.getQtyOrdered();
		}

		// Storage
		if (M_Product_ID > 0
				&& QtyOrdered.signum() > 0		// no negative (returns)
				&& ddOrderLine.getDD_Order().isSOTrx())
		{
			MProduct product = MProduct.get(ctx, M_Product_ID);
			if (Services.get(IProductBL.class).isStocked(product))
			{
				final int M_Locator_ID = ddOrderLine.getM_Locator_ID();
				int M_AttributeSetInstance_ID = ddOrderLine.getM_AttributeSetInstance_ID();
				final int M_Warehouse_ID = MLocator.get(ctx, M_Locator_ID).getM_Warehouse_ID();

				BigDecimal qtyAvailable = MStorage.getQtyAvailable(M_Warehouse_ID, 0, M_Product_ID, M_AttributeSetInstance_ID, ITrx.TRXNAME_None);
				if (qtyAvailable == null)
					qtyAvailable = BigDecimal.ZERO;
				if (qtyAvailable.signum() == 0)
				{
					field.fireDataStatusEEvent("NoQtyAvailable", "0", false);
				}
				else if (qtyAvailable.compareTo(QtyOrdered) < 0)
				{
					field.fireDataStatusEEvent("InsufficientQtyAvailable", qtyAvailable.toString(), false);
				}
				else
				{
					final int DD_OrderLine_ID = ddOrderLine.getDD_OrderLine_ID();
					BigDecimal qtyNotReserved = MDDOrderLine.getNotReserved(ctx,
							M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID,
							DD_OrderLine_ID);
					if (qtyNotReserved == null)
						qtyNotReserved = BigDecimal.ZERO;
					final BigDecimal total = qtyAvailable.subtract(qtyNotReserved);
					if (total.compareTo(QtyOrdered) < 0)
					{
						final String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + qtyAvailable + "  -  @QtyNotReserved@=" + qtyNotReserved + "  =  " + total);
						field.fireDataStatusEEvent("InsufficientQtyAvailable", info, false);
					}
				}
			}
		}
	}
}
