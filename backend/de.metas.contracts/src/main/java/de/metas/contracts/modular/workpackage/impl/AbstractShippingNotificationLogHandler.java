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

package de.metas.contracts.modular.workpackage.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import java.time.ZoneId;
import java.util.function.Function;

public abstract class AbstractShippingNotificationLogHandler extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnComplete.Description");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.AbstractShippingNotificationLogHandler.OnReverse.Description");

	@NonNull private final ShippingNotificationService notificationService;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull private final ModularContractLogDAO contractLogDAO;

	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.SHIPPING_NOTIFICATION;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	protected AbstractShippingNotificationLogHandler(final @NonNull ShippingNotificationService notificationService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractService modularContractService)
	{
		super(modularContractService);
		this.notificationService = notificationService;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.contractLogDAO = contractLogDAO;
	}

	@Override
	public @NonNull String getSupportedTableName()
	{
		return I_M_Shipping_NotificationLine.Table_Name;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_Shipping_NotificationLine notificationLine = notificationService.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));
		final I_M_Shipping_Notification notification = notificationService.getRecordById(ShippingNotificationId.ofRepoId(notificationLine.getM_Shipping_Notification_ID()));

		final NotificationAndLineWrapper wrapper = new NotificationAndLineWrapper(notification, notificationLine);
		final ProductId productId = wrapper.getProductId();
		final Quantity quantity = wrapper.getQuantity();

		final String description = msgBL.getMsg(MSG_ON_COMPLETE_DESCRIPTION, ImmutableList.of(String.valueOf(productId.getRepoId()), quantity.toString()));

		final LocalDateAndOrgId transactionDate = wrapper.getTransactionDate(orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(createLogRequest.getModularContractSettings().getRawProductId(), yearAndCalendarId)
				.orElse(null);

		final ProductPrice contractSpecificPrice = getContractSpecificPriceWithFlags(createLogRequest).toProductPrice();

		final BPartnerId warehouseBPartnerId = wrapper.getWarehouseBPartnerId(warehouseDAO);
		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.referencedRecord(wrapper.getLineReference())
				.contractId(createLogRequest.getContractId())
				.collectionPointBPartnerId(warehouseBPartnerId)
				.producerBPartnerId(warehouseBPartnerId)
				.invoicingBPartnerId(warehouseBPartnerId)
				.warehouseId(wrapper.getWarehouseId())
				.initialProductId(productId)
				.productId(contractSpecificPrice.getProductId())
				.productName(createLogRequest.getProductName())
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(getSOTrx())
				.processed(false)
				.quantity(quantity)
				.transactionDate(transactionDate)
				.year(wrapper.getHarvestingYearId())
				.description(description)
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.invoicingGroupId(invoicingGroupId)
				.priceActual(contractSpecificPrice)
				.amount(contractSpecificPrice.computeAmount(quantity, uomConversionBL))
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_Shipping_NotificationLine notificationLine = notificationService.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final TableRecordReference notificationLineRef = TableRecordReference.of(notificationLine);

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(ModularContractLogQuery.builder()
				.flatrateTermId(createLogRequest.getContractId())
				.referenceSet(TableRecordReferenceSet.of(notificationLineRef))
				.build());

		final String description = msgBL.getMsg(MSG_ON_REVERSE_DESCRIPTION, ImmutableList.of(String.valueOf(notificationLine.getM_Product_ID()), quantity.toString()));

		return ExplainedOptional.of(
				LogEntryReverseRequest.builder()
						.referencedModel(notificationLineRef)
						.flatrateTermId(createLogRequest.getContractId())
						.description(description)
						.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
						.contractModuleId(createLogRequest.getModularContractModuleId())
						.build());
	}

	public abstract SOTrx getSOTrx();

	private record NotificationAndLineWrapper(
			@NonNull I_M_Shipping_Notification notification,
			@NonNull I_M_Shipping_NotificationLine notificationLine)
	{
		public Quantity getQuantity()
		{
			return Quantitys.of(notificationLine.getMovementQty(), UomId.ofRepoId(notificationLine.getC_UOM_ID()));
		}

		public ProductId getProductId()
		{
			return ProductId.ofRepoId(notificationLine.getM_Product_ID());
		}

		public BPartnerId getBPartnerId()
		{
			return BPartnerId.ofRepoId(notification.getC_BPartner_ID());
		}

		public TableRecordReference getLineReference()
		{
			return TableRecordReference.of(notificationLine);
		}

		public WarehouseId getWarehouseId()
		{
			return WarehouseId.ofRepoId(notification.getM_Warehouse_ID());
		}

		public BPartnerId getWarehouseBPartnerId(@NonNull final IWarehouseDAO warehouseDAO)
		{
			return BPartnerId.ofRepoId(warehouseDAO.getById(getWarehouseId()).getC_BPartner_ID());
		}

		public OrgId getOrgId()
		{
			return OrgId.ofRepoId(notificationLine.getAD_Org_ID());
		}

		public LocalDateAndOrgId getTransactionDate(@NonNull final Function<OrgId, ZoneId> orgMapper)
		{
			return LocalDateAndOrgId.ofTimestamp(notification.getPhysicalClearanceDate(), getOrgId(), orgMapper);
		}

		public YearId getHarvestingYearId()
		{
			return YearId.ofRepoId(notification.getHarvesting_Year_ID());
		}
	}
}
