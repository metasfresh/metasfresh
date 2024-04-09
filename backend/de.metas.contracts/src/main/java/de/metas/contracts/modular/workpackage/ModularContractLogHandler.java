/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.workpackage;

import ch.qos.logback.classic.Level;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;

@Service
@RequiredArgsConstructor
class ModularContractLogHandler
{
	public static final Logger logger = LogManager.getLogger(ModularContractLogHandler.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

	@NonNull
	private final ModularContractLogHandlerRegistry handlerRegistry;
	@NonNull
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	@NonNull
	private final ModularContractLogDAO contractLogDAO;
	@NonNull
	private final ModularContractLogService modularLogService;
	@NonNull
	private final ShippingNotificationService notificationService;

	public void handleLogs(@NonNull final List<IModularContractLogHandler.HandleLogsRequest> requestList)
	{
		requestList.forEach(request -> handlerRegistry
				.streamHandlers(request)
				.forEach(handler -> handleLogs(handler, request)));

	}

	private <T> void handleLogs(
			@NonNull final IModularContractLogHandler handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(request.getContractId());
		if (settings == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModularContractSettings found for contractId: {}! no logs will be created!", request.getContractId());

			return;
		}

		final ProductId productId = handler.getProductId(request).orElse(null);
		if (productId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No Product found for contractId: {}! no logs will be created!", request.getContractId());

			return;
		}
		
		final ModuleConfig moduleConfig = settings.getModuleConfig(handler.getComputingMethod().getComputingMethodType(), productId)
				.orElse(null);
		if (moduleConfig == null)
		{
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("No ModuleConfig found for contractId: {} and settingsId: {}! no logs will be created!", request.getContractId(), settings.getId());

			return;
		}

		final IModularContractLogHandler.LogAction action = handler.getLogAction(request);

		final Supplier<IModularContractLogHandler.CreateLogRequest> buildCreateRequest = () -> IModularContractLogHandler.CreateLogRequest
				.builder()
				.handleLogsRequest(request)
				.modularContractSettings(settings)
				.configId(moduleConfig.getId())
				.typeId(moduleConfig.getModularContractType().getId())
				.build();

		switch (action)
		{
			case CREATE -> createLogs(handler, buildCreateRequest.get());
			case REVERSE -> reverseLogs(handler, request);
			case RECOMPUTE -> recreateLogs(handler, buildCreateRequest.get());
			default -> throw new AdempiereException("Unknown action: " + action);
		}
	}

	private <T> void createLogs(
			@NonNull final IModularContractLogHandler handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		createLogEntryCreateRequest(handler, request)
				.ifPresent(contractLogDAO::create)
				.ifAbsent(explanation -> Loggables.withLogger(logger, Level.DEBUG)
						.addLog("Method: {} | No logs created for request: {}! reason: {}!",
								"createLogs",
								request,
								explanation.getDefaultValue()));
	}

	private <T> void reverseLogs(
			@NonNull final IModularContractLogHandler handler,
			@NonNull final IModularContractLogHandler.HandleLogsRequest request)
	{
		handler.createLogEntryReverseRequest(request)
				.ifPresent(contractLogDAO::reverse)
				.ifAbsent(explanation -> Loggables.withLogger(logger, Level.DEBUG)
						.addLog("Method: {} | No logs created for contractId: {} & request: {}! reason: {}!"
								, "reverseLogs",
								request.getContractId(),
								request,
								explanation.getDefaultValue()));
	}

	private <T> void recreateLogs(
			@NonNull final IModularContractLogHandler handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		modularLogService.throwErrorIfProcessedLogsExistForRecord(request.getHandleLogsRequest().getTableRecordReference(), MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);

		contractLogDAO.delete(handler.getDeleteRequestFor(request.getHandleLogsRequest()));

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Method: {} | Logs were successfully deleted for request: {}!", "recreateLogs", request);

		createLogs(handler, request);
	}

	private ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final IModularContractLogHandler handler,
			@NonNull final IModularContractLogHandler.CreateLogRequest createLogRequest)
	{
		final BooleanWithReason areLogsRequired = doesRecordStateRequireLogCreation(
				createLogRequest.getHandleLogsRequest().getTableRecordReference());

		if (areLogsRequired.isFalse())
		{
			return ExplainedOptional.emptyBecause(areLogsRequired.getReason());
		}

		return handler.createLogEntryCreateRequest(createLogRequest);
	}

	private BooleanWithReason doesRecordStateRequireLogCreation(@NonNull final TableRecordReference tableRecordReference)
	{
		switch (tableRecordReference.getTableName())
		{
			case (I_C_Flatrate_Term.Table_Name) -> {
				final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(tableRecordReference.getRecord_ID()));
				if (!DocStatus.ofCode(flatrateTermRecord.getDocStatus()).isCompleted())
				{
					return BooleanWithReason.falseBecause("The C_Flatrate_Term.DocStatus is " + flatrateTermRecord.getDocStatus());
				}

				return BooleanWithReason.TRUE;
			}
			case (I_C_InvoiceLine.Table_Name) -> {
				final DocStatus invoiceDocStatus = invoiceBL.getDocStatus(InvoiceId.ofRepoId(tableRecordReference.getRecord_ID()));
				if (!invoiceDocStatus.isCompleted())
				{
					return BooleanWithReason.falseBecause("The C_Invoice.DocStatus is " + invoiceDocStatus);
				}

				return BooleanWithReason.TRUE;
			}
			case (I_C_OrderLine.Table_Name) -> {
				final DocStatus orderDocStatus = orderBL.getDocStatus(orderLineBL.getOrderIdByOrderLineId(OrderLineId.ofRepoId(tableRecordReference.getRecord_ID())));
				if (!orderDocStatus.isCompleted())
				{
					return BooleanWithReason.falseBecause("The C_Order.DocStatus is " + orderDocStatus);
				}

				return BooleanWithReason.TRUE;
			}
			case (I_M_InOutLine.Table_Name) -> {
				final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(InOutLineId.ofRepoId(tableRecordReference.getRecord_ID()));
				final DocStatus inOutDocStatus = inOutBL.getDocStatus(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));

				if (!inOutDocStatus.isCompleted())
				{
					return BooleanWithReason.falseBecause("The M_Inout.DocStatus is " + inOutDocStatus);
				}

				return BooleanWithReason.TRUE;
			}
			case (I_M_Shipping_Notification.Table_Name) -> {

				final ShippingNotification shippingNotification = notificationService
						.getById(ShippingNotificationId.ofRepoId(tableRecordReference.getRecord_ID()));

				if (shippingNotification.getDocStatus().isCompletedOrClosed())
				{
					return BooleanWithReason.TRUE;
				}

				return BooleanWithReason.falseBecause("The M_Shipping_Notification.DocStatus is " + shippingNotification.getDocStatus());
			}
			case (I_I_ModCntr_Log.Table_Name) -> {
				final I_I_ModCntr_Log modCntrLogImportRecord = InterfaceWrapperHelper.load(tableRecordReference.getRecord_ID(), I_I_ModCntr_Log.class);
				if (!modCntrLogImportRecord.isProcessed())
				{
					return BooleanWithReason.falseBecause("The I_I_ModCntr_Log is not processed " + modCntrLogImportRecord.getI_ModCntr_Log_ID());
				}

				return BooleanWithReason.TRUE;
			}
			case (I_M_InventoryLine.Table_Name) -> {
				final I_M_InventoryLine inventoryLineRecord = inventoryBL.getLineById(InventoryLineId.ofRepoId(tableRecordReference.getRecord_ID()));
				final DocStatus inventoryDocStatus = inventoryBL.getDocStatus(InventoryId.ofRepoId(inventoryLineRecord.getM_Inventory_ID()));

				if (!inventoryDocStatus.isCompleted())
				{
					return BooleanWithReason.falseBecause("The M_Inventory.DocStatus is " + inventoryDocStatus);
				}

				return BooleanWithReason.TRUE;
			}
			case (I_PP_Cost_Collector.Table_Name) -> {
				if (!ppCostCollectorBL.getById(PPCostCollectorId.ofRepoId(tableRecordReference.getRecord_ID())).isProcessed())
				{
					return BooleanWithReason.falseBecause("The PP_Cost_Collector.Processed is false");
				}

				return BooleanWithReason.TRUE;
			}



			default -> {
				return BooleanWithReason.falseBecause("Unsupported table " + tableRecordReference.getTableName());
			}
		}
	}
}
