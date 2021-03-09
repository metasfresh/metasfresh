package org.eevolution.model.validator;

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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.util.Services;

@Validator(I_DD_Order.class)
public class DD_Order
{
	/**
	 * Task http://dewiki908/mediawiki/index.php/09687_DD_order_Copy_with_details_%28101015490340%29
	 */
	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_DD_Order.Table_Name);
	}

	/**
	 * Sys config used to Disable the Forward/Backward DD Order processing
	 *
	 * Task http://dewiki908/mediawiki/index.php/08059_Trigger_Fertigstellen_for_DD_Orders_%28107323649094%29
	 */
	private static final String SYSCONFIG_DisableProcessForwardAndBackwardDraftDDOrders = "org.eevolution.mrp.spi.impl.DDOrderMRPSupplyProducer.DisableProcessForwardAndBackwardDraftDDOrders";

	/**
	 * Before Delete
	 *
	 * @return true of it can be deleted
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_DD_Order ddOrder)
	{
		if (ddOrder.isProcessed())
		{
			return;
		}

		final List<I_DD_OrderLine> ddOrderLines = Services.get(IDDOrderDAO.class).retrieveLines(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			InterfaceWrapperHelper.delete(ddOrderLine);
		}
	}	// beforeDelete

	private boolean isProcessForwardAndBackwaredDDOrdersAutomatically(final I_DD_Order ddOrder)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int adClientId = ddOrder.getAD_Client_ID();
		final int adOrgId = ddOrder.getAD_Org_ID();
		final boolean disabled = sysConfigBL.getBooleanValue(SYSCONFIG_DisableProcessForwardAndBackwardDraftDDOrders,
				false, // default value
				adClientId,
				adOrgId);

		return !disabled;
	}

	/**
	 * If {@link I_DD_Order#COLUMN_MRP_AllowCleanup} is set to <code>false</code> then propagate this flag to forward and backward DD Orders too.
	 *
	 * Task http://dewiki908/mediawiki/index.php/08059_Trigger_Fertigstellen_for_DD_Orders_%28107323649094%29
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_DD_Order.COLUMNNAME_MRP_AllowCleanup)
	public void propagate_MRPDisallowCleanup(final I_DD_Order ddOrder)
	{
		if (!isProcessForwardAndBackwaredDDOrdersAutomatically(ddOrder))
		{
			return;
		}

		//
		// Shall we disallow cleanup on Forward and Backward DD Order?
		final boolean doDisallowCleanupOnForwardAndBackwardDDOrders =
				// Flag from this DD Order was set to false
				!ddOrder.isMRP_AllowCleanup()
						&& InterfaceWrapperHelper.isValueChanged(ddOrder, I_DD_Order.COLUMNNAME_MRP_AllowCleanup) // flag was just changed
						// There is no point to propagate this flag if the DD Order was already processed
						// because when a DD Order is completed, this flag is automatically set to false
						&& !ddOrder.isProcessed();

		//
		// If MRP_AllowCleanup flag was just set to false,
		// Set it to false on Forward and Backward DD Orders, if they are on the same plant (08059)
		if (doDisallowCleanupOnForwardAndBackwardDDOrders)
		{
			final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
			ddOrderBL.disallowMRPCleanupOnForwardAndBackwardDDOrders(ddOrder);
		}
	}

	/**
	 * Complete all forward and backward DD Orders (but on the same plant)
	 *
	 * Task http://dewiki908/mediawiki/index.php/08059_Trigger_Fertigstellen_for_DD_Orders_%28107323649094%29
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void completeForwardAndBackwardDDOrders(final I_DD_Order ddOrder)
	{
		if (!isProcessForwardAndBackwaredDDOrdersAutomatically(ddOrder))
		{
			return;
		}

		final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
		ddOrderBL.completeForwardAndBackwardDDOrders(ddOrder);
	}
}
