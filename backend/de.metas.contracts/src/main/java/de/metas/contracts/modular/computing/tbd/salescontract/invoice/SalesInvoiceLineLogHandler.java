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

package de.metas.contracts.modular.computing.tbd.salescontract.invoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
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
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.salescontract
 */
@Deprecated
@Component
class SalesInvoiceLineLogHandler extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description");

	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final ModularContractLogDAO contractLogDAO;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull private final SalesInvoiceLineModularContractHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_C_InvoiceLine.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.SALES_INVOICE;

	public SalesInvoiceLineLogHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull ModularContractLogDAO contractLogDAO,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull SalesInvoiceLineModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.contractLogDAO = contractLogDAO;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
	}

	@NonNull
	private static Quantity extractQtyEntered(final @NonNull I_C_InvoiceLine invoiceLine)
	{
		final UomId uomId = UomId.ofRepoId(invoiceLine.getC_UOM_ID());
		return Quantitys.of(invoiceLine.getQtyEntered(), uomId);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(InvoiceLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final I_C_Flatrate_Term contract = flatrateBL.getById(createLogRequest.getContractId());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(contract.getBill_BPartner_ID());

		final Quantity qtyEntered = extractQtyEntered(invoiceLine);

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final Money amount = Money.of(invoiceLine.getLineNetAmt(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_COMPLETE_DESCRIPTION, productName, qtyEntered);

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(invoice.getDateInvoiced(),
				OrgId.ofRepoId(invoiceLine.getAD_Org_ID()),
				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(
				LogEntryCreateRequest.builder()
						.referencedRecord(recordRef)
						.contractId(createLogRequest.getContractId())
						.collectionPointBPartnerId(bpartnerId)
						.producerBPartnerId(bpartnerId)
						.invoicingBPartnerId(bpartnerId)
						.warehouseId(WarehouseId.ofRepoId(invoice.getM_Warehouse_ID()))
						.productId(productId)
						.productName(createLogRequest.getProductName())
						.documentType(getLogEntryDocumentType())
						.contractType(getLogEntryContractType())
						.soTrx(SOTrx.PURCHASE)
						.processed(false)
						.quantity(qtyEntered)
						.amount(amount)
						.transactionDate(transactionDate)
						.year(yearAndCalendarId.yearId())
						.description(description)
						.modularContractTypeId(createLogRequest.getTypeId())
						.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
						.priceActual(invoiceLineBL.getPriceActual(invoiceLine))
						.invoicingGroupId(invoicingGroupId)
						.build()
		);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference invoiceLineRef = createLogRequest.getRecordRef();
		final I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(InvoiceLineId.ofRepoId(invoiceLineRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(
				ModularContractLogQuery.builder()
						.flatrateTermId(createLogRequest.getContractId())
						.referenceSet(TableRecordReferenceSet.of(invoiceLineRef))
						.contractType(LogEntryContractType.MODULAR_CONTRACT)
						.build());

		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_REVERSE_DESCRIPTION, productName, quantity);

		return ExplainedOptional.of(
				LogEntryReverseRequest.builder()
						.referencedModel(invoiceLineRef)
						.flatrateTermId(createLogRequest.getContractId())
						.description(description)
						.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
						.contractModuleId(createLogRequest.getModularContractModuleId())
						.build()
		);
	}
}
