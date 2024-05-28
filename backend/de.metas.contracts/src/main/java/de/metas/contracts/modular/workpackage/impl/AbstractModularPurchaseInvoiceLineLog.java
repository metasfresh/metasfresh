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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper;
import de.metas.document.DocTypeId;
import de.metas.i18n.ExplainedOptional;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public abstract class AbstractModularPurchaseInvoiceLineLog extends AbstractModularContractLogHandler
{
	@NonNull protected final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull private final ModularContractLogDAO contractLogDAO;
	@NonNull private final ModularContractLogService modularContractLogService;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull private final String supportedTableName = I_C_InvoiceLine.Table_Name;

	public AbstractModularPurchaseInvoiceLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModularContractLogDAO contractLogDAO,
			@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository)
	{
		super(modularContractService);
		this.contractLogDAO = contractLogDAO;
		this.modularContractLogService = modularContractLogService;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference tableRecordReference = createLogRequest.getRecordRef();
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(InvoiceLineId.ofRepoId(tableRecordReference.getRecordIdAssumingTableName(I_C_InvoiceLine.Table_Name)));
		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLineRecord.getC_Invoice_ID()));

		final BPartnerId invoiceBpartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());

		final UomId uomId = UomId.ofRepoId(invoiceLineRecord.getC_UOM_ID());
		final Quantity qtyEntered = Quantitys.of(invoiceLineRecord.getQtyEntered(), uomId);

		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());
		final Money amount = Money.of(invoiceLineRecord.getLineNetAmt(), currencyId);
		final Money priceActual = Money.of(invoiceLineRecord.getPriceActual(), currencyId);
		final ProductId productId = ProductId.ofRepoId(invoiceLineRecord.getM_Product_ID());
		final ProductPrice productPrice = ProductPrice.builder()
				.productId(productId)
				.money(priceActual)
				.uomId(uomId)
				.build();

		final FlatrateTermId modularContractId = createLogRequest.getContractId();
		final I_C_Flatrate_Term modularContractRecord = flatrateBL.getById(modularContractId);
		final Optional<ModularContractLogEntry> modularContractLogEntryOptional = modularContractLogService.getLastModularContractLog(
				modularContractId,
				OrderLineId.ofRepoId(modularContractRecord.getC_OrderLine_Term_ID()));

		if (modularContractLogEntryOptional.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No ModularContractLogEntry found for"
					+ " modularContractId: " + modularContractId + ","
					+ " orderLineTermId: " + modularContractRecord.getC_OrderLine_Term_ID()
					+ "! No logs will be created");
		}

		final ModularContractLogEntry modularContractLogEntry = modularContractLogEntryOptional.get();

		final DocTypeId docTypeId = DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID());
		final String description = ModularContractLogHandlerHelper.getOnCompleteDescription(docTypeId, productId, qtyEntered);

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(invoiceRecord.getDateInvoiced(),
				OrgId.ofRepoId(invoiceLineRecord.getAD_Org_ID()),
				orgDAO::getTimeZone);

		final ProductId rawProductId = createLogRequest.getModularContractSettings().getRawProductId();
		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);
		final String productName = productBL.getProductValueAndName(productId);

		return ExplainedOptional.of(
				LogEntryCreateRequest.builder()
						.referencedRecord(tableRecordReference)
						.contractId(modularContractId)
						.collectionPointBPartnerId(invoiceBpartnerId)
						.producerBPartnerId(invoiceBpartnerId)
						.invoicingBPartnerId(invoiceBpartnerId)
						.warehouseId(modularContractLogEntry.getWarehouseId())
						.productId(productId)
						.productName(CoalesceUtil.firstNotBlank(invoiceLineRecord.getProductName(), productName))
						.documentType(getLogEntryDocumentType())
						.contractType(getLogEntryContractType())
						.soTrx(SOTrx.PURCHASE)
						.isBillable(false)
						.processed(false)
						.quantity(qtyEntered)
						.amount(amount)
						.transactionDate(transactionDate)
						.year(yearAndCalendarId.yearId())
						.description(description)
						.modularContractTypeId(createLogRequest.getTypeId())
						.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
						.priceActual(productPrice)
						.invoicingGroupId(invoicingGroupId)
						.build()
		);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference invoiceLineRef = createLogRequest.getRecordRef();
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(InvoiceLineId.ofRepoId(invoiceLineRef.getRecordIdAssumingTableName(getSupportedTableName())));
		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLineRecord.getC_Invoice_ID()));

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(
				ModularContractLogQuery.builder()
						.flatrateTermId(createLogRequest.getContractId())
						.referenceSet(TableRecordReferenceSet.of(invoiceLineRef))
						.contractType(getLogEntryContractType())
						.build());

		final ProductId productId = ProductId.ofRepoId(invoiceLineRecord.getM_Product_ID());
		final DocTypeId docTypeId = DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID());
		final String description = ModularContractLogHandlerHelper.getOnReverseDescription(docTypeId, productId, quantity);

		return ExplainedOptional.of(
				LogEntryReverseRequest.builder()
						.referencedModel(invoiceLineRef)
						.flatrateTermId(createLogRequest.getContractId())
						.description(description)
						.logEntryContractType(getLogEntryContractType())
						.contractModuleId(createLogRequest.getModularContractModuleId())
						.build()
		);
	}
}
