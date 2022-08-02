package de.metas.order.createFrom.po_from_so.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.util.Check;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Used by {@link CreatePOFromSOsAggregator} to create the keys that decide which sales order line belongs to which purchase order.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class CreatePOFromSOsAggregationKeyBuilder extends AbstractOrderLineAggregationKeyBuilder
{
	private static final Logger logger = LogManager.getLogger(CreatePOFromSOsAggregationKeyBuilder.class);

	@VisibleForTesting
	public static final String ON_MISSING_C_B_PARTNER_PRODUCT_ERROR = "ERROR";

	private static final String ON_MISSING_C_B_PARTNER_PRODUCT_IGNORE = "IGNORE";

	private static final String ON_MISSING_C_B_PARTNER_PRODUCT_LOG = "LOG";

	@VisibleForTesting
	public static final String SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT = "de.metas.order.C_Order_CreatePOFromSOs.OnMissing_C_BPartner_Product";

	private static final AdMessageKey MSG_VENDOR_MISMATCH = AdMessageKey.of("de.metas.order.C_Order_CreatePOFromSOs.VendorMismatch");

	private static final AdMessageKey MSG_MISSING_C_B_PARTNER_PRODUCT_ID = AdMessageKey.of("de.metas.order.C_Order_CreatePOFromSOs.Missing_C_BPartner_Product_ID");

	private static final AdMessageKey MSG_MISSING_VENDOR_FOR_ORDERLINE_ID = AdMessageKey.of("de.metas.order.C_Order_CreatePOFromSOs.Missing_C_BPartner_Vendor_ID");

	/* package */static final String KEY_SKIP = "SKIP_SALES_ORDER_LINE";

	private final transient IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final int p_Vendor_ID;
	private final IContextAware context;
	private final boolean p_IsVendorInOrderLinesRequired;

	public CreatePOFromSOsAggregationKeyBuilder(
			final int p_Vendor_ID,
			final IContextAware context,
			final boolean p_IsVendorInOrderLinesRequired)
	{
		this.p_Vendor_ID = p_Vendor_ID;
		this.context = context;
		this.p_IsVendorInOrderLinesRequired = p_IsVendorInOrderLinesRequired;
	}

	/**
	 * @return the <code>C_BPartner.Value</code> of the vendor that shall be used for the sales order line's purchase order. If there is no such vendor, then {@link #KEY_SKIP} is returned.
	 */
	@Override
	public String buildKey(final I_C_OrderLine salesOrderLine)
	{
		final I_C_Order salesOrder = salesOrderLine.getC_Order();
		final I_C_BPartner soPartner = bPartnerDAO.getById(salesOrderLine.getC_BPartner_ID());
		final I_M_Product product = productDAO.getById(salesOrderLine.getM_Product_ID());

		// FRESH-334 the bp product should be of the products' organization or of the org 0
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());
		final BPartnerId orderLineVendorId = BPartnerId.ofRepoIdOrNull(salesOrderLine.getC_BPartner_Vendor_ID());

		if (orderLineVendorId != null)
		{
			final I_C_BPartner orderLineVendor = bPartnerDAO.getById(orderLineVendorId, I_C_BPartner.class);
			return orderLineVendor.getValue();
		}

		if (p_IsVendorInOrderLinesRequired)
		{
			throw new AdempiereException(MSG_MISSING_VENDOR_FOR_ORDERLINE_ID, salesOrder.getDocumentNo() + "-" + salesOrderLine.getLine());
		}

		final I_C_BPartner_Product bpProduct = bpProductDAO.retrieveBPProductForCustomer(soPartner, product, orgId);

		if (bpProduct == null)
		{
			return skipOrFail(salesOrder, salesOrderLine);
		}

		final int poPartnerRepoId = firstGreaterThanZero(bpProduct.getC_BPartner_Vendor_ID(), bpProduct.getC_BPartner_ID());
		final I_C_BPartner poBPartnerRecord = loadOutOfTrx(poPartnerRepoId, I_C_BPartner.class);

		// extra validation for the vendor
		if (p_Vendor_ID > 0 && poPartnerRepoId != p_Vendor_ID)
		{
			final String msg = msgBL.getMsg(context.getCtx(),
											MSG_VENDOR_MISMATCH,
											new Object[] { salesOrder.getDocumentNo(), salesOrderLine.getLine(), poBPartnerRecord.getValue(), poBPartnerRecord.getName() });

			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return KEY_SKIP;
		}
		return poBPartnerRecord.getValue();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private String skipOrFail(
			@NonNull final I_C_Order salesOrder,
			@NonNull final I_C_OrderLine salesOrderLine)
	{
		final String onMissingBPartnerProduct = sysConfigBL.getValue(SYSCONFIG_ON_MISSING_C_B_PARTNER_PRODUCT, ON_MISSING_C_B_PARTNER_PRODUCT_LOG);
		final boolean log = ON_MISSING_C_B_PARTNER_PRODUCT_LOG.equalsIgnoreCase(onMissingBPartnerProduct);
		final boolean ignore = ON_MISSING_C_B_PARTNER_PRODUCT_IGNORE.equalsIgnoreCase(onMissingBPartnerProduct);
		final boolean error = ON_MISSING_C_B_PARTNER_PRODUCT_ERROR.equalsIgnoreCase(onMissingBPartnerProduct);

		Check.assume(log || error || ignore,
					 "de.metas.order.C_Order_CreatePOFromSOs.OnMissing_C_BPartner_Product has a supported value! Actual value: {}",
					 onMissingBPartnerProduct);

		String msg = null;
		if (log || error)
		{
			msg = msgBL.getMsg(context.getCtx(),
							   MSG_MISSING_C_B_PARTNER_PRODUCT_ID,
							   new Object[] { salesOrder.getDocumentNo(), salesOrderLine.getLine() });

			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
		}
		if (log || ignore)
		{
			return KEY_SKIP;
		}

		throw new AdempiereException(msg); // note that msg is != null at this point
	}
}
