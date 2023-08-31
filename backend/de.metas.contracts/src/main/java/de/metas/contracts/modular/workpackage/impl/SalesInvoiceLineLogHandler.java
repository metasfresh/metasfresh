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

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SalesInvoiceLineLogHandler implements IModularContractLogHandler<I_C_InvoiceLine>
{
	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description");

	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final ModularContractLogDAO contractLogDAO;
	private final SalesInvoiceLineModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_C_InvoiceLine> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REVERSED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_C_InvoiceLine> request)
	{
		final I_C_InvoiceLine invoiceLine = request.getHandleLogsRequest().getModel();

		final I_C_Flatrate_Term contract = flatrateBL.getById(request.getContractId());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(contract.getBill_BPartner_ID());

		final Quantity qtyEntered = extractQtyEntered(invoiceLine);

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final Money amount = Money.of(invoiceLine.getLineNetAmt(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = TranslatableStrings.adMessage(MSG_ON_COMPLETE_DESCRIPTION, productName, qtyEntered)
				.translate(Language.getBaseAD_Language());

		return ExplainedOptional.of(
				LogEntryCreateRequest.builder()
						.referencedRecord(TableRecordReference.of(I_C_InvoiceLine.Table_Name, invoiceLine.getC_InvoiceLine_ID()))
						.contractId(request.getContractId())
						.collectionPointBPartnerId(bpartnerId)
						.producerBPartnerId(bpartnerId)
						.invoicingBPartnerId(bpartnerId)
						.warehouseId(WarehouseId.ofRepoId(invoice.getM_Warehouse_ID()))
						.productId(productId)
						.documentType(LogEntryDocumentType.SALES_INVOICE)
						.contractType(LogEntryContractType.MODULAR_CONTRACT)
						.soTrx(SOTrx.PURCHASE)
						.processed(false)
						.quantity(qtyEntered)
						.amount(amount)
						.transactionDate(LocalDateAndOrgId.ofTimestamp(invoice.getDateInvoiced(), OrgId.ofRepoId(invoiceLine.getAD_Org_ID()), orgDAO::getTimeZone))
						.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
						.description(description)
						.modularContractTypeId(request.getTypeId())
						.build()
		);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final HandleLogsRequest<I_C_InvoiceLine> handleLogsRequest,
			@NonNull final FlatrateTermId contractId)
	{
		final TableRecordReference invoiceLineRef = TableRecordReference.of(I_C_InvoiceLine.Table_Name,
																			handleLogsRequest.getModel().getC_InvoiceLine_ID());

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(
				ModularContractLogQuery.builder()
						.flatrateTermId(contractId)
						.referenceSet(TableRecordReferenceSet.of(invoiceLineRef))
						.contractType(LogEntryContractType.MODULAR_CONTRACT)
						.build());

		final ProductId productId = ProductId.ofRepoId(handleLogsRequest.getModel().getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = TranslatableStrings.adMessage(MSG_ON_REVERSE_DESCRIPTION, productName, quantity)
				.translate(Language.getBaseAD_Language());

		return ExplainedOptional.of(
				LogEntryReverseRequest.builder()
						.referencedModel(invoiceLineRef)
						.flatrateTermId(contractId)
						.description(description)
						.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
						.build()
		);
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_C_InvoiceLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}

	@NonNull
	private static Quantity extractQtyEntered(final @NonNull I_C_InvoiceLine invoiceLine)
	{
		final UomId uomId = UomId.ofRepoId(invoiceLine.getC_UOM_ID());
		return Quantitys.create(invoiceLine.getQtyEntered(), uomId);
	}
}
