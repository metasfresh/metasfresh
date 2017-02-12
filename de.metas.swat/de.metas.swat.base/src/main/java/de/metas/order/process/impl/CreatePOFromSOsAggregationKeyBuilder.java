package de.metas.order.process.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.purchasing.api.IBPartnerProductDAO;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Used by {@link CreatePOFromSOsAggregator} to create the keys that decide which sales order line belongs to which purchase order.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreatePOFromSOsAggregationKeyBuilder extends AbstractOrderLineAggregationKeyBuilder
{
	private static final String ON_MISSING_C_B_PARTNER_PRODUCT_ERROR = "ERROR";

	private static final String ON_MISSING_C_B_PARTNER_PRODUCT_IGNORE = "IGNORE";

	private static final String ON_MISSING_C_B_PARTNER_PRODUCT_LOG = "LOG";

	private static final String SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT = "de.metas.order.C_Order_CreatePOFromSOs.OnMissing_C_BPartner_Product";

	private static final String MSG_VENDOR_MISMATCH = "de.metas.order.C_Order_CreatePOFromSOs.VendorMismatch";

	private static final String MSG_MISSING_C_B_PARTNER_PRODUCT_ID = "de.metas.order.C_Order_CreatePOFromSOs.Missing_C_BPartner_Product_ID";

	/* package */static final String KEY_SKIP = "SKIP_SALES_ORDER_LINE";

	private final transient IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final int p_Vendor_ID;
	private final IContextAware context;

	public CreatePOFromSOsAggregationKeyBuilder(final int p_Vendor_ID,
			final IContextAware context)
	{
		this.p_Vendor_ID = p_Vendor_ID;
		this.context = context;
	}

	/**
	 * @return the <code>C_BPartner.Value</code> of the vendor that shall be used for the sales order line's purchase order. If there is no such vendor, then {@link #KEY_SKIP} is returned.
	 */
	@Override
	public String buildKey(final I_C_OrderLine salesOrderLine)
	{
		final I_C_Order salesOrder = salesOrderLine.getC_Order();
		final I_C_BPartner soPartner = salesOrderLine.getC_BPartner();
		final I_M_Product product = salesOrderLine.getM_Product();

		//FRESH-334 the bp product should be of the products' organization or of the org 0
		final int orgId = product.getAD_Org_ID();

		final I_C_BPartner_Product bpProduct = bpProductDAO.retrieveBPProductForCustomer(soPartner, product, orgId);

		final ILoggable loggable = Loggables.get();

		if (bpProduct == null)
		{
			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

			final String onMissingBPartnerProduct = sysConfigBL.getValue(SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT, ON_MISSING_C_B_PARTNER_PRODUCT_LOG);
			final boolean log = ON_MISSING_C_B_PARTNER_PRODUCT_LOG.equalsIgnoreCase(onMissingBPartnerProduct);
			final boolean ignore = ON_MISSING_C_B_PARTNER_PRODUCT_IGNORE.equalsIgnoreCase(onMissingBPartnerProduct);
			final boolean error = ON_MISSING_C_B_PARTNER_PRODUCT_ERROR.equalsIgnoreCase(onMissingBPartnerProduct);

			String msg = null;
			if (log || error)
			{
				msg = msgBL.getMsg(context.getCtx(),
						MSG_MISSING_C_B_PARTNER_PRODUCT_ID,
						new Object[] { salesOrder.getDocumentNo(), salesOrderLine.getLine() });
				loggable.addLog(msg);
			}
			if (log || ignore)
			{
				return KEY_SKIP;
			}
			if (error)
			{
				throw new AdempiereException(msg); // note that msg is != null at this point
			}
		}
		final I_C_BPartner poPartner;
		if (bpProduct.getC_BPartner_Vendor() != null)
		{
			poPartner = bpProduct.getC_BPartner_Vendor();
		}
		else
		{
			poPartner = bpProduct.getC_BPartner();
		}
		// extra validation for the vendor
		if (p_Vendor_ID > 0 && poPartner.getC_BPartner_ID() != p_Vendor_ID)
		{
			final String msg = msgBL.getMsg(context.getCtx(),
					MSG_VENDOR_MISMATCH,
					new Object[] { salesOrder.getDocumentNo(), salesOrderLine.getLine(), poPartner.getValue(), poPartner.getName() });

			loggable.addLog(msg);
			return KEY_SKIP;
		}

		return poPartner.getValue();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
