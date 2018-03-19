package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentLocation;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLine;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.storage.impl.ImmutableStorageSegment;
import lombok.NonNull;

public class SubscriptionShipmentScheduleHandler extends ShipmentScheduleHandler
{
	@VisibleForTesting
	static final String SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS = "C_SubscriptionProgress.Create_ShipmentSchedulesInAdvanceDays";

	private static final Logger logger = LogManager.getLogger(SubscriptionShipmentScheduleHandler.class);

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(@NonNull final Object model)
	{
		final IDocumentLocationBL documentLocationBL = Services.get(IDocumentLocationBL.class);
		final I_C_SubscriptionProgress subscriptionLine = create(model, I_C_SubscriptionProgress.class);

		Check.assume(subscriptionLine.getQty().signum() > 0, subscriptionLine + " has Qty>0");

		final I_M_ShipmentSchedule newSched = newInstance(I_M_ShipmentSchedule.class, model);

		final int tableId = InterfaceWrapperHelper.getTableId(I_C_SubscriptionProgress.class);
		newSched.setAD_Table_ID(tableId);
		newSched.setRecord_ID(subscriptionLine.getC_SubscriptionProgress_ID());

		final I_C_Flatrate_Term term = subscriptionLine.getC_Flatrate_Term();

		Check.assume(term.getM_Product_ID() > 0, term + " has M_Product_ID>0");

		newSched.setM_Product_ID(term.getM_Product_ID());
		Services.get(IAttributeSetInstanceBL.class).cloneASI(term, newSched);

		newSched.setProductDescription(null);

		updateNewSchedWithValuesFromReferencedLine(newSched);

		final I_C_DocType doctypeForTerm = Services.get(IFlatrateBL.class).getDocTypeFor(term);
		newSched.setC_DocType_ID(doctypeForTerm.getC_DocType_ID());
		newSched.setDocSubType(doctypeForTerm.getDocSubType());

		newSched.setPriorityRule(X_M_ShipmentSchedule.PRIORITYRULE_High);

		newSched.setC_BPartner_Location_ID(subscriptionLine.getDropShip_Location_ID());
		newSched.setC_BPartner_ID(subscriptionLine.getDropShip_BPartner_ID());
		newSched.setAD_User_ID(subscriptionLine.getDropShip_User_ID());
		newSched.setBill_BPartner_ID(newSched.getC_BPartner_ID());

		final IDocumentLocation documentLocation = InterfaceWrapperHelper.create(newSched, IDocumentLocation.class);
		documentLocationBL.setBPartnerAddress(documentLocation);

		newSched.setDeliveryRule(term.getDeliveryRule());
		newSched.setDeliveryViaRule(term.getDeliveryViaRule());

		newSched.setQtyOrdered(subscriptionLine.getQty());
		newSched.setQtyOrdered_Calculated(subscriptionLine.getQty());
		newSched.setQtyReserved(subscriptionLine.getQty());

		newSched.setLineNetAmt(newSched.getQtyReserved().multiply(term.getPriceActual()));

		newSched.setDateOrdered(subscriptionLine.getEventDate());

		newSched.setAD_Org_ID(subscriptionLine.getAD_Org_ID());

		Check.assume(newSched.getAD_Client_ID() == subscriptionLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule has the same AD_Client_ID as " + subscriptionLine + ", i.e." + newSched.getAD_Client_ID() + " == " + subscriptionLine.getAD_Client_ID());

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(term.getM_Product());
		newSched.setIsDisplayed(display);

		save(newSched);

		subscriptionLine.setStatus(X_C_SubscriptionProgress.STATUS_Open);
		subscriptionLine.setM_ShipmentSchedule_ID(newSched.getM_ShipmentSchedule_ID());
		save(subscriptionLine);

		invalidateCandidatesFor(subscriptionLine);

		// Note: AllowConsolidateInOut is set on the first update of this schedule
		return Collections.singletonList(newSched);
	}

	private void updateNewSchedWithValuesFromReferencedLine(@NonNull final I_M_ShipmentSchedule newSched)
	{
		final ShipmentScheduleReferencedLine subscriptionFromgressInfos = Adempiere
				.getBean(ShipmentScheduleSubscriptionReferenceProvider.class)
				.provideFor(newSched);

		newSched.setM_Warehouse_ID(subscriptionFromgressInfos.getWarehouseId());
		newSched.setPreparationDate(subscriptionFromgressInfos.getPreparationDate());
		newSched.setDeliveryDate(subscriptionFromgressInfos.getDeliveryDate());
	}

	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

		final I_C_SubscriptionProgress subscriptionLine = InterfaceWrapperHelper.create(model, I_C_SubscriptionProgress.class);
		if (subscriptionLine.getM_ShipmentSchedule_ID() >= 0)
		{
			return;
		}

		final ImmutableStorageSegment segment = createStorageSegmentFor(subscriptionLine);
		shipmentSchedulePA.invalidate(ImmutableSet.of(segment));
	}

	private ImmutableStorageSegment createStorageSegmentFor(@NonNull final I_C_SubscriptionProgress subscriptionLine)
	{
		final I_M_Warehouse warehouse = Services.get(IShipmentScheduleEffectiveBL.class).getWarehouse(subscriptionLine.getM_ShipmentSchedule());
		final ImmutableSet<Integer> locatorIds = Services.get(IWarehouseDAO.class).retrieveLocators(warehouse).stream().map(I_M_Locator::getM_Locator_ID).collect(ImmutableSet.toImmutableSet());

		final ImmutableStorageSegment segment = ImmutableStorageSegment.builder()
				.M_Product_ID(subscriptionLine.getC_Flatrate_Term().getM_Product_ID())
				.C_BPartner_ID(subscriptionLine.getDropShip_BPartner_ID())
				.M_Locator_IDs(locatorIds)
				.build();
		return segment;
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
		final int daysInAdvance = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 0, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		final Timestamp eventDateMaximum = TimeUtil.addDays(SystemTime.asTimestamp(), daysInAdvance);

		// Note: we used to also check if there is an active I_M_IolCandHandler_Log record referencing the C_SubscriptionProgress, but I don't see why.
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_C_SubscriptionProgress> subscriptionLines = queryBL
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_Status, X_C_SubscriptionProgress.STATUS_Planned)
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_EventType, X_C_SubscriptionProgress.EVENTTYPE_Delivery)
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.LESS_OR_EQUAL, eventDateMaximum)
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_M_ShipmentSchedule_ID, null) // we didn't do this in the very old code which i found
				.addOnlyContextClient(ctx)
				.orderBy().addColumn(I_C_SubscriptionProgress.COLUMN_C_SubscriptionProgress_ID).endOrderBy()
				.create()
				.list();

		logger.debug("Identified {} C_SubscriptionProgress that need a shipment schedule", subscriptionLines.size());

		return new ArrayList<>(subscriptionLines);
	}

	@Override
	public IDeliverRequest createDeliverRequest(final I_M_ShipmentSchedule sched)
	{
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(sched);
		final TableRecordReference ref = new TableRecordReference(sched.getAD_Table_ID(), sched.getRecord_ID());

		final I_C_SubscriptionProgress subscriptionLine = ref.getModel(contextAware, I_C_SubscriptionProgress.class);

		return () -> subscriptionLine.getQty();
	}
}
