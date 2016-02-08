package de.metas.flatrate.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.contracts
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


import static de.metas.flatrate.model.I_C_SubscriptionProgress.COLUMNNAME_EventDate;
import static de.metas.flatrate.model.X_C_SubscriptionProgress.EVENTTYPE_Lieferung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.api.IInOutCandHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IInOutCandHandler;
import de.metas.product.IProductBL;

/**
 * Default implementation for sales order lines.
 * 
 * @author ts
 * 
 */
public class SubscriptionInOutCandHandler implements IInOutCandHandler
{

	private static final CLogger logger = CLogger.getCLogger(SubscriptionInOutCandHandler.class);

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(final Object model)
	{
		final I_C_SubscriptionProgress subscriptionLine = InterfaceWrapperHelper.create(model, I_C_SubscriptionProgress.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		Check.assume(subscriptionLine.getQty().signum() > 0, subscriptionLine + " has Qty>0");

		final I_M_ShipmentSchedule newSched = POWrapper.create(ctx, I_M_ShipmentSchedule.class, trxName);

		newSched.setAD_Table_ID(MTable.getTable_ID(I_C_SubscriptionProgress.Table_Name));
		newSched.setRecord_ID(subscriptionLine.getC_SubscriptionProgress_ID());

		final I_C_Flatrate_Term term = subscriptionLine.getC_Flatrate_Term();

		Check.assume(term.getM_Product_ID() > 0, term + " has M_Product_ID>0");
		
		if (term.isNewTermCreatesOrder())
		{
			Check.assume(term.getC_OrderLine_Term_ID() > 0, term + " has C_OrderLine_Term_ID>0");
			newSched.setC_OrderLine_ID(term.getC_OrderLine_Term_ID());
			newSched.setC_Order_ID(term.getC_OrderLine_Term().getC_Order_ID());
		}
		
		newSched.setM_Product_ID(term.getM_Product_ID());

		newSched.setProductDescription(null);

		newSched.setM_Warehouse_ID(Services.get(IFlatrateBL.class).getWarehouse(term));


		final I_C_DocType doctypeForTerm = Services.get(IFlatrateBL.class).getDocTypeFor(term);

		newSched.setPriorityRule(X_M_ShipmentSchedule.PRIORITYRULE_High);

		newSched.setC_DocType_ID(doctypeForTerm.getC_DocType_ID());

		newSched.setDocSubType(doctypeForTerm.getDocSubType());

		newSched.setC_BPartner_Location_ID(subscriptionLine.getDropShip_Location_ID());
		newSched.setC_BPartner_ID(subscriptionLine.getDropShip_BPartner_ID());
		newSched.setAD_User_ID(subscriptionLine.getDropShip_User_ID());

		Services.get(IDocumentLocationBL.class).setBPartnerAddress(
				InterfaceWrapperHelper.create(newSched, IDocumentLocation.class));

		final I_C_Order order = InterfaceWrapperHelper.create(term.getC_Order_Term(), I_C_Order.class);
		newSched.setDeliveryRule(order.getDeliveryRule());
		newSched.setDeliveryViaRule(order.getDeliveryViaRule());

		newSched.setLineNetAmt(newSched.getQtyReserved().multiply(term.getC_OrderLine_Term().getPriceActual()));

		newSched.setQtyReserved(subscriptionLine.getQty());
		newSched.setQtyOrdered(subscriptionLine.getQty());

		newSched.setDateOrdered(subscriptionLine.getEventDate());

		newSched.setAD_Org_ID(subscriptionLine.getAD_Org_ID());

		Check.assume(newSched.getAD_Client_ID() == subscriptionLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule has the same AD_Client_ID as " + subscriptionLine + ", i.e." + newSched.getAD_Client_ID() + " == " + subscriptionLine.getAD_Client_ID());

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(term.getM_Product());
		newSched.setIsDisplayed(display);

		POWrapper.save(newSched);

		Services.get(IShipmentSchedulePA.class).invalidateForProducts(Collections.singletonList(newSched.getM_Product_ID()), trxName);

		subscriptionLine.setStatus(X_C_SubscriptionProgress.STATUS_LieferungOffen);
		subscriptionLine.setM_ShipmentSchedule_ID(newSched.getM_ShipmentSchedule_ID());

		POWrapper.save(subscriptionLine);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return Collections.singletonList(newSched);
	}

	@Override
	public void invalidateCandidatesFor(Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_SubscriptionProgress subscriptionLine = InterfaceWrapperHelper.create(model, I_C_SubscriptionProgress.class);

		Services.get(IShipmentSchedulePA.class)
				.invalidateForProducts(Collections.singletonList(subscriptionLine.getC_Flatrate_Term().getM_Product_ID()), trxName);
	}

	@Override
	public String getSourceTable()
	{
		return I_C_SubscriptionProgress.Table_Name;
	}

	public List<Object> retrieveModelsWithMissingCandidates(
			final Properties ctx,
			final String trxName)
	{
		final String wc =
				I_C_SubscriptionProgress.COLUMNNAME_EventType + "='" + EVENTTYPE_Lieferung + "'" +
						"   AND " + I_C_SubscriptionProgress.COLUMNNAME_Status + " = '" + X_C_SubscriptionProgress.STATUS_Geplant + "'" +
						"   AND " + COLUMNNAME_EventDate + "<=?" +

						// only if we didn't yet look at the subscription line
						"   AND NOT EXISTS (" +
						"      select 1 from " + I_M_IolCandHandler_Log.Table_Name + " log " +
						"      where log." + I_M_IolCandHandler_Log.COLUMNNAME_M_IolCandHandler_ID + "=?" +
						"        and log." + I_M_IolCandHandler_Log.COLUMNNAME_AD_Table_ID + "=?" +
						"        and log." + I_M_IolCandHandler_Log.COLUMNNAME_Record_ID + "=" + I_C_SubscriptionProgress.Table_Name + "."
						+ I_C_SubscriptionProgress.COLUMNNAME_C_SubscriptionProgress_ID +
						"        and log." + I_M_IolCandHandler_Log.COLUMNNAME_IsActive + "='Y'" +
						"   )";

		final List<I_C_SubscriptionProgress> subscriptionLines = new Query(ctx, getSourceTable(), wc, trxName)
				.setParameters(
						SystemTime.asTimestamp(),
						Services.get(IInOutCandHandlerBL.class).retrieveHandlerRecordOrNull(ctx, this.getClass().getName(), trxName).getM_IolCandHandler_ID(),
						MTable.getTable_ID(I_C_SubscriptionProgress.Table_Name))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_C_SubscriptionProgress_ID)
				.list(I_C_SubscriptionProgress.class);

		logger.info("Identified " + subscriptionLines.size() + " C_SubscriptionProgress that need a shipment schedule");

		return new ArrayList<Object>(subscriptionLines);
	}
}
