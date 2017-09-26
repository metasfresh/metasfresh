package de.metas.contracts.subscription.inoutcandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_DocType;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IInOutCandHandler;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

public class SubscriptionInOutCandHandler implements IInOutCandHandler
{

	private static final Logger logger = LogManager.getLogger(SubscriptionInOutCandHandler.class);

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(final Object model)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IDocumentLocationBL documentLocationBL = Services.get(IDocumentLocationBL.class);
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final I_C_SubscriptionProgress subscriptionLine = create(model, I_C_SubscriptionProgress.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		Check.assume(subscriptionLine.getQty().signum() > 0, subscriptionLine + " has Qty>0");

		final I_M_ShipmentSchedule newSched = newInstance(I_M_ShipmentSchedule.class, model);

		final int tableId = adTableDAO.retrieveTableId(I_C_SubscriptionProgress.Table_Name);
		newSched.setAD_Table_ID(tableId);
		newSched.setRecord_ID(subscriptionLine.getC_SubscriptionProgress_ID());

		final I_C_Flatrate_Term term = subscriptionLine.getC_Flatrate_Term();

		Check.assume(term.getM_Product_ID() > 0, term + " has M_Product_ID>0");
		Check.assume(term.getC_OrderLine_Term_ID() > 0, term + " has C_OrderLine_Term_ID>0");
		newSched.setC_OrderLine_ID(term.getC_OrderLine_Term_ID());
		newSched.setC_Order_ID(term.getC_OrderLine_Term().getC_Order_ID());

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

		final IDocumentLocation documentLocation = InterfaceWrapperHelper.create(newSched, IDocumentLocation.class);
		documentLocationBL.setBPartnerAddress(documentLocation);

		final I_C_Order order = create(term.getC_Order_Term(), I_C_Order.class);
		newSched.setDeliveryRule(order.getDeliveryRule());
		newSched.setDeliveryViaRule(order.getDeliveryViaRule());

		newSched.setLineNetAmt(newSched.getQtyReserved().multiply(term.getC_OrderLine_Term().getPriceActual()));

		newSched.setQtyOrdered(subscriptionLine.getQty());
		newSched.setQtyOrdered_Calculated(subscriptionLine.getQty());
		newSched.setQtyReserved(subscriptionLine.getQty());

		newSched.setDateOrdered(subscriptionLine.getEventDate());

		newSched.setAD_Org_ID(subscriptionLine.getAD_Org_ID());

		Check.assume(newSched.getAD_Client_ID() == subscriptionLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule has the same AD_Client_ID as " + subscriptionLine + ", i.e." + newSched.getAD_Client_ID() + " == " + subscriptionLine.getAD_Client_ID());

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(term.getM_Product());
		newSched.setIsDisplayed(display);

		save(newSched);

		shipmentSchedulePA.invalidateForProducts(Collections.singletonList(newSched.getM_Product_ID()), trxName);

		subscriptionLine.setStatus(X_C_SubscriptionProgress.STATUS_LieferungOffen);
		subscriptionLine.setM_ShipmentSchedule_ID(newSched.getM_ShipmentSchedule_ID());

		save(subscriptionLine);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return Collections.singletonList(newSched);
	}

	@Override
	public void invalidateCandidatesFor(Object model)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_SubscriptionProgress subscriptionLine = InterfaceWrapperHelper.create(model, I_C_SubscriptionProgress.class);

		shipmentSchedulePA.invalidateForProducts(Collections.singletonList(subscriptionLine.getC_Flatrate_Term().getM_Product_ID()), trxName);
	}

	@Override
	public String getSourceTable()
	{
		return I_C_SubscriptionProgress.Table_Name;
	}

	@Override
	public List<Object> retrieveModelsWithMissingCandidates(
			final Properties ctx,
			final String trxName)
	{
		// Note: we used to also check if there is an active I_M_IolCandHandler_Log record referencing the C_SubscriptionProgress, but I don't see why.
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_C_SubscriptionProgress> subscriptionLines = queryBL
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_Status, X_C_SubscriptionProgress.STATUS_Geplant)
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.LESS_OR_EQUAL, SystemTime.asTimestamp())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_M_ShipmentSchedule_ID, null) // we didn't do this in the very old code which i found
				.addOnlyContextClient(ctx)
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMN_C_SubscriptionProgress_ID).endOrderBy()
				.create()
				.list();

		logger.debug("Identified {} C_SubscriptionProgress that need a shipment schedule", subscriptionLines.size());

		return new ArrayList<Object>(subscriptionLines);
	}

	@Override
	public IDeliverRequest createDeliverRequest(final I_M_ShipmentSchedule sched)
	{
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(sched);
		final TableRecordReference ref = new TableRecordReference(sched.getAD_Table_ID(), sched.getRecord_ID());

		final I_C_SubscriptionProgress subscriptionLine = ref.getModel(contextAware, I_C_SubscriptionProgress.class);

		return new IDeliverRequest()
		{
			@Override
			public BigDecimal getQtyOrdered()
			{
				return subscriptionLine.getQty();
			}
		};
	}
}
