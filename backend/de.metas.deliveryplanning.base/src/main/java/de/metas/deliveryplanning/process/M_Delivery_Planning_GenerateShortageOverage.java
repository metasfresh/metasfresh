/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning.process;

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.DocTypeQuery.DocTypeQueryBuilder;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategy;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLineAggregatorFactory;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLinesCreationCtx;
import de.metas.handlingunits.inventory.draftlinescreator.ShortageAndOverageStrategy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.InventoryId;
import de.metas.inventory.impexp.InventoryImportProcess;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static de.metas.document.DocBaseType.MaterialPhysicalInventory;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.X_C_DocType.DOCSUBTYPE_InventoryOverageDocument;
import static org.compiere.model.X_C_DocType.DOCSUBTYPE_InventoryShortageDocument;

/**
 * Creates an inventory document for the given M_Delivery_Planning that corrects the respective quantities *after* they were received.
 */
public class M_Delivery_Planning_GenerateShortageOverage extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final Logger logger = LogManager.getLogger(InventoryImportProcess.class);
	private static final AdMessageKey MSG_Event_ShortageGenerated = AdMessageKey.of("Event_ShortageGenerated");
	private static final AdMessageKey MSG_Event_OverageGenerated = AdMessageKey.of("Event_OverageGenerated");
	private static final AdMessageKey MSG_ERROR_QUANTITY_ZERO = AdMessageKey.of("ShortageOverageQuantityZeroError");
	private static final String PARAM_QTY = "Quantity";
	private static final int PARAM_DEFAULT_VALUE_ZERO = 0;
	private static final String SYSCONFIG_SHORTAGE_OVERAGE_TARGET_WINDOW = "de.metas.deliveryplanning.shortageOverageDocument.TargetWindow";
	private static final int WINDOW_PHYSICAL_INVENTORY = 168;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final InventoryRepository inventoryRepository = SpringContextHolder.instance.getBean(InventoryRepository.class);
	private final HuForInventoryLineFactory huForInventoryLineFactory = SpringContextHolder.instance.getBean(HuForInventoryLineFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Param(parameterName = PARAM_QTY, mandatory = true)
	private BigDecimal p_Qty;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(context.getSingleSelectedRecordId());
		final Optional<DeliveryPlanningReceiptInfo> optionalDeliveryPlanningReceipt = deliveryPlanningService.getReceiptInfoIfIncomingType(deliveryPlanningId);
		if (!optionalDeliveryPlanningReceipt.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Delivery planning is not of Type Incoming");
		}

		final DeliveryPlanningReceiptInfo receiptInfo = optionalDeliveryPlanningReceipt.get();
		if (!receiptInfo.isReceived())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not received");
		}

		final boolean existsBlockedPartnerDeliveryPlannings = deliveryPlanningService.hasBlockedBPartner(deliveryPlanningId);

		if (existsBlockedPartnerDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_BlockedPartner));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if(PARAM_QTY.contentEquals(parameter.getColumnName()))
		{
			return PARAM_DEFAULT_VALUE_ZERO;
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt() throws Exception
	{
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(getRecord_ID());

		final DocTypeQueryBuilder docTypeQuery = DocTypeQuery.builder()
			.docBaseType(MaterialPhysicalInventory)
			.adClientId(getClientID().getRepoId())
			.adOrgId(OrgId.ANY.getRepoId());

		final AdMessageKey adMessageKey;
		if (p_Qty.signum() > 0)
		{
			adMessageKey = MSG_Event_OverageGenerated;
			docTypeQuery.docSubType(DOCSUBTYPE_InventoryOverageDocument);
		}
		else if (p_Qty.signum() < 0)
		{
			adMessageKey = MSG_Event_ShortageGenerated;
			docTypeQuery.docSubType(DOCSUBTYPE_InventoryShortageDocument);
		}
		else
		{
			throw new AdempiereException(MSG_ERROR_QUANTITY_ZERO);
		}

		final I_M_Inventory inventory = generateInventoryDocument(docTypeDAO.getDocTypeId(docTypeQuery.build()), deliveryPlanningId);
		createUserNotification(inventory, adMessageKey);

		return MSG_OK;
	}

	private I_M_Inventory generateInventoryDocument(@NonNull final DocTypeId doctypeId, @NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		deliveryPlanningService.validateDeliveryPlanning(deliveryPlanningId);

		final DeliveryPlanningReceiptInfo receiptInfo = deliveryPlanningService.getReceiptInfo(deliveryPlanningId);
		final InOutId inOutId = receiptInfo.getReceiptId();
		Check.assumeNotNull(inOutId, "InOutId shall be set, because of isReceived() check in preconditions");

		final I_M_InOut inOutRecord = inOutDAO.getById(inOutId);
		final Set<InOutAndLineId> inOutLinesIds = inOutDAO.retrieveLinesForInOutId(InOutId.ofRepoId(inOutRecord.getM_InOut_ID()));
		if(inOutLinesIds.size() == 0)
		{
			throw new AdempiereException("No receipt lines found");
		}
		if(inOutLinesIds.size() > 1)
		{
			throw new AdempiereException("More than 1 receipt line exists");
		}

		final InOutLineId receiptLineId = CollectionUtils.singleElement(inOutLinesIds).getInOutLineId();

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(receiptInfo.getOrgId().getRepoId());
		inventoryRecord.setC_DocType_ID(doctypeId.getRepoId());
		inventoryRecord.setM_Warehouse_ID(inOutRecord.getM_Warehouse_ID());
		inventoryRecord.setMovementDate(inOutRecord.getMovementDate());
		inventoryRecord.setC_BPartner_ID(inOutRecord.getC_BPartner_ID());
		inventoryRecord.setC_BPartner_Location_ID(inOutRecord.getC_BPartner_Location_ID());

		Optional.ofNullable(receiptInfo.getPurchaseOrderId())
				.map(OrderId::getRepoId)
				.ifPresent(inventoryRecord::setC_PO_Order_ID);

		saveRecord(inventoryRecord);
		logger.trace("Insert inventory - {}", inventoryRecord);

		createInventoryLine(InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID()), receiptLineId);

		return inventoryRecord;
	}

	private void createInventoryLine(@NonNull final InventoryId inventoryId, @NonNull final InOutLineId inOutLineId)
	{
		final TableRecordReference mInOutLineTableRecordReference = TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineId);
		final Set<TableRecordReference> mInOutLineTableRecordReferenceSet = Collections.singleton(mInOutLineTableRecordReference);
		final ImmutableSetMultimap<TableRecordReference, HuId> huIdsMultimap = huAssignmentDAO.retrieveHUsByRecordRefs(mInOutLineTableRecordReferenceSet);
		final Collection<HuId> huIds = huIdsMultimap.get(mInOutLineTableRecordReference);
		final I_M_HU huRecord = handlingUnitsDAO.getById(CollectionUtils.singleElement(huIds));

		final ShortageAndOverageStrategy shortageAndOverageStrategy = HUsForInventoryStrategies.shortageAndOverage()
				.huForInventoryLineFactory(huForInventoryLineFactory)
				.huRecord(huRecord)
				.shortageOverage(p_Qty)
				.build();

		final InventoryLinesCreationCtx ctx = createContext(
				inventoryId,
				shortageAndOverageStrategy);

		new DraftInventoryLinesCreator(ctx).execute();

	}

	private InventoryLinesCreationCtx createContext(
			@NonNull final InventoryId inventoryId,
			@NonNull final HUsForInventoryStrategy strategy)
	{
		return InventoryLinesCreationCtx.builder()
				.inventory(inventoryRepository.getById(inventoryId))
				.inventoryRepo(inventoryRepository)
				.inventoryLineAggregator(InventoryLineAggregatorFactory.SingleHUInventoryLineAggregator.INSTANCE)
				.strategy(strategy)
				.build();
	}

	private void createUserNotification(@NonNull final I_M_Inventory inventory, @NonNull final AdMessageKey adMessageKey)
	{
		final TableRecordReference inventoryRef = TableRecordReference.of(inventory);
		final AdWindowId targetWindow = AdWindowId.ofRepoId(sysConfigBL.getIntValue(SYSCONFIG_SHORTAGE_OVERAGE_TARGET_WINDOW, WINDOW_PHYSICAL_INVENTORY));

		notificationBL.sendAfterCommit(
						UserNotificationRequest.builder()
							.recipientUserId(UserId.ofRepoId(getAD_User_ID()))
							.contentADMessage(adMessageKey)
							.contentADMessageParam(inventoryRef)
							.targetAction(UserNotificationRequest.TargetRecordAction.ofRecordAndWindow(inventoryRef, targetWindow))
							.build()
					);


	}
}
