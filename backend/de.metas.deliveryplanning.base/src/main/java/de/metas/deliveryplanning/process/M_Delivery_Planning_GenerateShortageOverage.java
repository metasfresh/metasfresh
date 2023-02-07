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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.ShortageAndOverageStrategy;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.inventory.impexp.InventoryImportProcess;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

import static de.metas.document.DocBaseType.MaterialPhysicalInventory;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.X_C_DocType.DOCSUBTYPE_InventoryOverageDocument;
import static org.compiere.model.X_C_DocType.DOCSUBTYPE_InventoryShortageDocument;

public class M_Delivery_Planning_GenerateShortageOverage extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private static final Logger logger = LogManager.getLogger(InventoryImportProcess.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final InventoryRepository inventoryRepository = SpringContextHolder.instance.getBean(InventoryRepository.class);
	private final HuForInventoryLineFactory huForInventoryLineFactory = SpringContextHolder.instance.getBean(HuForInventoryLineFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final AdMessageKey MSG_Event_ShortageGenerated = AdMessageKey.of("Event_ShortageGenerated");
	private static final AdMessageKey MSG_Event_OverageGenerated = AdMessageKey.of("Event_OverageGenerated");
	private static final String PARAM_QTY = "Quantity";
	private static final int PARAM_DEFAULT_VALUE_ZERO = 0;
	private static final String SYSCONFIG_SHORTAGE_OVERAGE_TARGET_WINDOW = "de.metas.deliveryplanning.shortageOverageDocument.TargetWindow";
	private static final int WINDOW_PHYSICAL_INVENTORY = 168;

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
		final I_M_Delivery_Planning record = load(deliveryPlanningId, I_M_Delivery_Planning.class);

		final DocTypeId docTypeId;
		final AdMessageKey adMessageKey;
		if (p_Qty.signum() > 0)
		{
			adMessageKey = MSG_Event_OverageGenerated;
			docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
											.docBaseType(MaterialPhysicalInventory)
											.docSubType(DOCSUBTYPE_InventoryOverageDocument)
											.adClientId(getClientID().getRepoId())
											.adOrgId(OrgId.ANY.getRepoId())
											.build());
		}
		else if (p_Qty.signum() < 0)
		{
			adMessageKey = MSG_Event_ShortageGenerated;
			docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
														.docBaseType(MaterialPhysicalInventory)
														.docSubType(DOCSUBTYPE_InventoryShortageDocument)
														.adClientId(getClientID().getRepoId())
														.adOrgId(OrgId.ANY.getRepoId())
														.build());
		}
		else
		{
			return MSG_Error + "Quantity 0 is not allowed";
		}

		final I_M_Inventory inventory = generateInventoryDocument(docTypeId, record);
		createUserNotification(inventory, adMessageKey);

		return MSG_OK;
	}

	private I_M_Inventory generateInventoryDocument(@NonNull final DocTypeId doctypeId, @NonNull final I_M_Delivery_Planning deliveryPlanningRecord)
	{
		final DeliveryPlanningReceiptInfo receiptInfo = deliveryPlanningService.getReceiptInfo(DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID()));
		final InOutId inOutId = receiptInfo.getReceiptId();
		Check.assumeNotNull(inOutId, "InOutId shall be set, because of isReceived() check in preconditions");

		final I_M_InOut inOutRecord = load(inOutId, I_M_InOut.class);
		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLines(inOutRecord);
		if(inOutLines.size() == 0)
		{
			throw new AdempiereException("No receipt lines found");
		}
		if(inOutLines.size() > 1)
		{
			throw new AdempiereException("More than 1 receipt line exists");
		}

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(deliveryPlanningRecord.getAD_Org_ID());
		inventoryRecord.setC_DocType_ID(doctypeId.getRepoId());
		inventoryRecord.setM_Warehouse_ID(inOutRecord.getM_Warehouse_ID());
		inventoryRecord.setMovementDate(inOutRecord.getMovementDate());

		saveRecord(inventoryRecord);
		logger.trace("Insert inventory - {}", inventoryRecord);

		createInventoryLine(inventoryRecord, inOutRecord);

		return inventoryRecord;
	}

	private void createInventoryLine(@NonNull final I_M_Inventory inventoryRecord, @NonNull final I_M_InOut inOutRecord)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLines(inOutRecord);
		final I_M_InOutLine firstInOutLineRecord = inOutLines.get(0);
		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(firstInOutLineRecord.getM_Locator_ID());
		Check.assumeNotNull(locatorId, "locator shall be set");

		final ProductId productId = ProductId.ofRepoId(firstInOutLineRecord.getM_Product_ID());
		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(firstInOutLineRecord.getM_AttributeSetInstance_ID());
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventoryRecord.getM_Warehouse_ID());

		final TableRecordReference mInOutLineTableRecordReference = TableRecordReference.of(firstInOutLineRecord);
		final Set<TableRecordReference> mInOutLineTableRecordReferenceSet = Collections.singleton(mInOutLineTableRecordReference);
		final ImmutableSetMultimap<TableRecordReference, HuId> huIdsMultimap = huAssignmentDAO.retrieveHUsByRecordRefs(mInOutLineTableRecordReferenceSet);
		final Collection<HuId> huIds = huIdsMultimap.get(mInOutLineTableRecordReference);

		final ShortageAndOverageStrategy shortageAndOverageStrategy = HUsForInventoryStrategies.shortageAndOverage()
				.warehouseId(warehouseId)
				.locatorId(locatorId)
				.productId(productId)
				.huForInventoryLineFactory(huForInventoryLineFactory)
				.huIds(huIds)
				.build();
		final List<HuForInventoryLine> hus = shortageAndOverageStrategy.streamHus()
				.collect(ImmutableList.toImmutableList());

		final Quantity qtyBooked = computeTotalQtyBooked(hus, stockingUOM, UOMConversionContext.of(productId));
		if(qtyBooked.toBigDecimal().add(p_Qty).signum() < 0)
		{
			throw new AdempiereException("Shortage is greater than booked quantity");
		}

		final I_M_InventoryLine inventoryLineRecord = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setM_Inventory_ID(inventoryRecord.getM_Inventory_ID());
		inventoryLineRecord.setAD_Org_ID(inventoryRecord.getAD_Org_ID());
		inventoryLineRecord.setM_Locator_ID(locatorId.getRepoId());
		inventoryLineRecord.setM_Product_ID(productId.getRepoId());
		inventoryLineRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());
		inventoryLineRecord.setHUAggregationType(HUAggregationType.toCodeOrNull(HUAggregationType.SINGLE_HU));
		inventoryLineRecord.setC_UOM_ID(qtyBooked.getUomId().getRepoId());
		inventoryLineRecord.setQtyCount(qtyBooked.toBigDecimal().add(p_Qty));
		inventoryLineRecord.setQtyBook(qtyBooked.toBigDecimal());
		inventoryLineRecord.setIsCounted(true);
		inventoryLineRecord.setM_HU_ID(huIds.stream().findFirst().get().getRepoId());

		InterfaceWrapperHelper.saveRecord(inventoryLineRecord);
		logger.trace("Insert inventory line - {}", inventoryLineRecord);

		final List<InventoryLineHU> inventoryLineHUs;
		inventoryLineHUs = toInventoryLineHUs(hus, productId, UomId.ofRepoId(stockingUOM.getC_UOM_ID()));

		final InventoryLine inventoryLine = inventoryRepository.toInventoryLine(inventoryLineRecord)
				.withInventoryLineHUs(inventoryLineHUs);

		inventoryRepository.saveInventoryLineHURecords(inventoryLine, inventoryId);
	}

	private Quantity computeTotalQtyBooked(
			final List<HuForInventoryLine> hus,
			final I_C_UOM targetUOM,
			final UOMConversionContext conversionCtx)
	{
		return hus.stream()
				.map(HuForInventoryLine::getQuantityBooked)
				.map(qty -> uomConversionBL.convertQuantityTo(qty, conversionCtx, targetUOM))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantity.zero(targetUOM));
	}

	private List<InventoryLineHU> toInventoryLineHUs(
			@NonNull final List<HuForInventoryLine> hus,
			@NonNull final ProductId productId,
			@NonNull final UomId targetUomId)
	{
		final UnaryOperator<Quantity> uomConverter = qty -> uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
		return hus.stream()
				.map(DraftInventoryLinesCreator::toInventoryLineHU)
				.map(inventoryLineHU -> inventoryLineHU.convertQuantities(uomConverter))
				.collect(ImmutableList.toImmutableList());
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
