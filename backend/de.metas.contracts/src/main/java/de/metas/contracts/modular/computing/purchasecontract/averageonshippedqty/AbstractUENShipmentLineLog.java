/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing.purchasecontract.averageonshippedqty;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.purchasecontract.storagecost.StorageCostComputingMethod;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public abstract class AbstractUENShipmentLineLog extends AbstractModularContractLogHandler
{
	protected static final AdMessageKey MSG_INFO_SHIPMENT_COMPLETED = AdMessageKey.of("de.metas.contracts.ShipmentCompleted");
	private static final AdMessageKey MSG_INFO_SHIPMENT_REVERSED = AdMessageKey.of("de.metas.contracts.ShipmentReversed");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ModularContractService modularContractService;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@NonNull @Getter private final String supportedTableName = I_M_InOutLine.Table_Name;
	@NonNull @Getter private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.SHIPMENT;
	@NonNull @Getter private final StorageCostComputingMethod computingMethod;
	@NonNull private final ColumnOption column;

	public AbstractUENShipmentLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final StorageCostComputingMethod computingMethod,
			@NonNull final ColumnOption column)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.modularContractService = modularContractService;

		this.computingMethod = computingMethod;
		this.column = column;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(getInOutLineId(recordRef));

		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(createLogRequest.getContractId());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID());

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final LocalDateAndOrgId transactionDate = extractTransactionDate(inOutRecord);

		final ContractSpecificScalePriceRequest contractSpecificScalePriceRequest = ContractSpecificScalePriceRequest.builder()
				.modularContractModuleId(createLogRequest.getModularContractModuleId())
				.flatrateTermId(createLogRequest.getContractId())
				.column(column)
				.value(getUserElementNumberValue(inOutLineRecord))
				.build();

		final ProductPrice contractSpecificPrice = modularContractService.getContractSpecificScalePrice(contractSpecificScalePriceRequest);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(createLogRequest.getContractId())
				.productId(createLogRequest.getProductId())
				.productName(createLogRequest.getProductName())
				.initialProductId(productId)
				.referencedRecord(recordRef)
				.collectionPointBPartnerId(bpartnerId)
				.producerBPartnerId(bpartnerId)
				.invoicingBPartnerId(bpartnerId)
				.warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.isBillable(true)
				.quantity(quantity)
				.amount(calculateAmount(quantity, contractSpecificPrice, uomConversionBL))
				.transactionDate(transactionDate)
				.priceActual(contractSpecificPrice)
				.year(yearAndCalendarId.yearId())
				.description(msgBL.getBaseLanguageMsg(MSG_INFO_SHIPMENT_COMPLETED, productName, quantity.abs()))
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.invoicingGroupId(invoicingGroupId)
				.build());
	}

	private BigDecimal getUserElementNumberValue(final I_M_InOutLine inOutLineRecord)
	{
		return switch (column)
		{
			case UserElementNumber1 -> inOutLineRecord.getUserElementNumber1();
			case UserElementNumber2 -> inOutLineRecord.getUserElementNumber2();
		};
	}

	@NotNull
	protected LocalDateAndOrgId extractTransactionDate(final I_M_InOut inOutRecord)
	{
		return LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(),
				OrgId.ofRepoId(inOutRecord.getAD_Org_ID()),
				orgDAO::getTimeZone);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(getInOutLineId(recordRef));

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);

		final String productName = productBL.getProductValueAndName(productId);

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
				.referencedModel(recordRef)
				.flatrateTermId(createLogRequest.getContractId())
				.description(msgBL.getBaseLanguageMsg(MSG_INFO_SHIPMENT_REVERSED, productName, quantity.abs()))
				.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
				.contractModuleId(createLogRequest.getModularContractModuleId())
				.build());
	}

	@Override
	public @NonNull ModularContractLogEntry calculateAmount(final @NonNull ModularContractLogEntry logEntry, final @NonNull QuantityUOMConverter uomConverter)
	{
		final Quantity qty = Check.assumeNotNull(logEntry.getQuantity(), "Quantity shouldn't be null");
		final ProductPrice price = Check.assumeNotNull(logEntry.getPriceActual(), "PriceActual shouldn't be null");

		return logEntry.toBuilder()
				.amount(calculateAmount(qty, price, uomConverter))
				.build();
	}

	@NonNull
	protected Money calculateAmount(@NonNull final Quantity qty, @NonNull final ProductPrice price, final @NonNull QuantityUOMConverter uomConverter)
	{
		return price.computeAmount(qty, uomConverter);
	}

	protected static InOutLineId getInOutLineId(@NonNull final TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId);
	}
}
