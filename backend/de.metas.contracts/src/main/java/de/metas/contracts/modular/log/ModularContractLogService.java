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

 package de.metas.contracts.modular.log;

 import com.google.common.collect.ImmutableList;
 import com.google.common.collect.ImmutableSet;
 import de.metas.adempiere.model.I_C_InvoiceLine;
 import de.metas.contracts.FlatrateTermId;
 import de.metas.contracts.IFlatrateBL;
 import de.metas.contracts.model.I_ModCntr_Log;
 import de.metas.contracts.modular.ComputingMethodType;
 import de.metas.contracts.modular.ContractSpecificPriceRequest;
 import de.metas.contracts.modular.ModularContractPriceService;
 import de.metas.contracts.modular.settings.ModularContractModuleId;
 import de.metas.contracts.modular.settings.ModularContractSettings;
 import de.metas.contracts.modular.settings.ModularContractSettingsService;
 import de.metas.contracts.modular.settings.ModuleConfig;
 import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
 import de.metas.currency.CurrencyConversionContext;
 import de.metas.currency.ICurrencyBL;
 import de.metas.document.DocTypeId;
 import de.metas.document.IDocTypeBL;
 import de.metas.i18n.AdMessageKey;
 import de.metas.invoice.InvoiceId;
 import de.metas.invoice.detail.InvoiceCandidateWithDetails;
 import de.metas.invoice.detail.InvoiceCandidateWithDetailsRepository;
 import de.metas.invoice.detail.InvoiceDetailItem;
 import de.metas.invoicecandidate.InvoiceCandidateId;
 import de.metas.invoicecandidate.api.IInvoiceCandDAO;
 import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
 import de.metas.lock.api.LockOwner;
 import de.metas.money.CurrencyId;
 import de.metas.money.Money;
 import de.metas.order.OrderLineId;
 import de.metas.organization.IOrgDAO;
 import de.metas.organization.LocalDateAndOrgId;
 import de.metas.organization.OrgId;
 import de.metas.pricing.IEditablePricingContext;
 import de.metas.pricing.IPricingResult;
 import de.metas.pricing.service.IPricingBL;
 import de.metas.process.PInstanceId;
 import de.metas.product.IProductBL;
 import de.metas.product.ProductId;
 import de.metas.product.ProductPrice;
 import de.metas.quantity.Quantity;
 import de.metas.quantity.Quantitys;
 import de.metas.quantity.StockQtyAndUOMQty;
 import de.metas.uom.IUOMConversionBL;
 import de.metas.uom.UomId;
 import de.metas.util.Check;
 import de.metas.util.Services;
 import lombok.NonNull;
 import lombok.RequiredArgsConstructor;
 import org.adempiere.ad.dao.IQueryFilter;
 import org.adempiere.ad.table.api.AdTableId;
 import org.adempiere.ad.table.api.IADTableDAO;
 import org.adempiere.exceptions.AdempiereException;
 import org.adempiere.model.InterfaceWrapperHelper;
 import org.adempiere.util.lang.impl.TableRecordReference;
 import org.adempiere.util.lang.impl.TableRecordReferenceSet;
 import org.compiere.Adempiere;
 import org.springframework.stereotype.Service;

 import javax.annotation.Nullable;
 import java.math.BigDecimal;
 import java.time.Instant;
 import java.util.Collection;
 import java.util.List;
 import java.util.Optional;
 import java.util.stream.Stream;

 import static de.metas.contracts.modular.ComputingMethodType.DEFINITIVE_INVOICE_SPECIFIC_METHODS;
 import static de.metas.contracts.modular.ComputingMethodType.DEFINITIVE_INVOICE_SPECIFIC_SALES_METHODS;
 import static de.metas.contracts.modular.log.LogEntryDocumentType.ALL_SHIPMENT_MODCNTR_LOG_DOCUMENTTYPES;
 import static de.metas.contracts.modular.log.LogEntryDocumentType.TO_UPDATE_WITH_AVERAGE_PRICE_DOCUMENTTYPES;

 @Service
 @RequiredArgsConstructor
 public class ModularContractLogService
 {
	 private static final AdMessageKey MSG_ERROR_DOCUMENT_LINE_DELETION = AdMessageKey.of("documentLineDeletionErrorBecauseOfRelatedModuleContractLog");
	 private static final String PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG = "ProductPrices of billable modular contract logs shouldn't be null";
	 public static final AdTableId INVOICE_LINE_TABLE_ID = AdTableId.ofRepoId(Services.get(IADTableDAO.class).retrieveTableId(I_C_InvoiceLine.Table_Name));
	 public static final String INVOICE_DETAILS_RECEIVED = "IN";
	 public static final String INVOICE_DETAILS_SHIPPED = "OUT";
	 public static final String INVOICE_DETAILS_FINAL_INVOICE_DATE = "finalInvoiceDate";

	 @NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	 @NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	 @NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	 @NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	 @NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	 @NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	 @NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	 @NonNull private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	 @NonNull private final ModularContractLogDAO modularContractLogDAO;
	 @NonNull private final InvoiceCandidateWithDetailsRepository invoiceCandidateWithDetailsRepository;
	 @NonNull private final ModularContractPriceService modularContractPriceService;
	 @NonNull private final ModularContractSettingsService modularContractSettingsService;

	 public static ModularContractLogService newInstanceForJUnitTesting()
	 {
		 Adempiere.assertUnitTestMode();
		 return new ModularContractLogService(
				 new ModularContractLogDAO(),
				 new InvoiceCandidateWithDetailsRepository(),
				 ModularContractPriceService.newInstanceForJUnitTesting(),
				 ModularContractSettingsService.newInstanceForJUnitTesting()
		 );
	 }

	 public void throwErrorIfLogExistsForDocumentLine(@NonNull final TableRecordReference tableRecordReference)
	 {
		 if (modularContractLogDAO.hasAnyModularLogs(tableRecordReference))
		 {
			 throw new AdempiereException(MSG_ERROR_DOCUMENT_LINE_DELETION);
		 }
	 }

	 public void changeBillableStatus(
			 @NonNull final ModularContractLogQuery query,
			 final boolean isBillable)
	 {
		 modularContractLogDAO.changeBillableStatus(query, isBillable);
	 }

	 @NonNull
	 public Optional<ModularContractLogEntry> getLastModularContractLog(
			 @NonNull final FlatrateTermId modularFlatrateTermId,
			 @NonNull final OrderLineId orderLineId)
	 {
		 return modularContractLogDAO.getLastModularContractLog(modularFlatrateTermId, orderLineId);
	 }

	 public void throwErrorIfProcessedLogsExistForRecord(
			 @NonNull final TableRecordReference tableRecordReference,
			 @NonNull final AdMessageKey errorMessage)
	 {
		 throwErrorIfProcessedLogsExistForRecord(tableRecordReference, null, errorMessage);
	 }

	 public void throwErrorIfProcessedLogsExistForRecord(
			 @NonNull final TableRecordReference tableRecordReference,
			 @Nullable final ModularContractModuleId moduleId,
			 @NonNull final AdMessageKey errorMessage)
	 {
		 if (hasAnyProcessedLogs(tableRecordReference, moduleId))
		 {
			 throw new AdempiereException(errorMessage);
		 }
	 }

	 public boolean hasAnyProcessedLogs(@NonNull final TableRecordReference tableRecordReference, @Nullable final ModularContractModuleId moduleId)
	 {
		 final ModularContractLogQuery query = ModularContractLogQuery.builder()
				 .referenceSet(TableRecordReferenceSet.of(tableRecordReference))
				 .processed(true)
				 .contractModuleId(moduleId)
				 .build();

		 return modularContractLogDAO.anyMatch(query);
	 }

	 public void setICProcessed(
			 @NonNull final ModularContractLogQuery query,
			 @NonNull final InvoiceCandidateId invoiceCandidateId)
	 {
		 modularContractLogDAO.setICProcessed(query, invoiceCandidateId);
	 }

	 public void unprocessLogsForInvoice(@NonNull final InvoiceId invoiceId, @NonNull final Collection<ComputingMethodType> computingMethodTypes)
	 {
		 final Collection<InvoiceCandidateId> candidateIds = invoiceCandDAO.retrieveInvoiceCandidateIds(invoiceId);
		 final ModularContractLogQuery query = ModularContractLogQuery.builder()
				 .isOnlyActiveComputingMethodTypes(false)
				 .processed(true)
				 .billable(true)
				 .computingMethodTypes(computingMethodTypes)
				 .invoiceCandidateIds(candidateIds)
				 .build();
		 modularContractLogDAO.unprocessLogs(query);
	 }

	 @NonNull
	 public ModularContractLogEntriesList getModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	 {
		 return modularContractLogDAO.getModularContractLogEntries(query);
	 }

	 @Nullable
	 public PInstanceId getModularContractLogEntrySelection(@NonNull final ModularContractLogQuery query)
	 {
		 return modularContractLogDAO.getModularContractLogEntrySelection(query);
	 }

	 @NonNull
	 public IQueryFilter<I_ModCntr_Log> getModularContractLogEntryFilter(@NonNull final ModularContractLogQuery query)
	 {
		 return modularContractLogDAO.getModularContractLogEntryFilter(query);
	 }

	 @NonNull
	 public Stream<ModularContractLogEntry> streamModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	 {
		 return modularContractLogDAO.streamModularContractLogEntries(query);
	 }

	public boolean anyMatch(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.anyMatch(query);
	}

	@NonNull
	public ImmutableSet<FlatrateTermId> getModularContractIds(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.getModularContractIds(query);
	}

	 @Nullable
	 public PInstanceId getSelection(@NonNull final LockOwner lockOwner)
	 {
		 return modularContractLogDAO.getSelection(lockOwner);
	 }

	 public void validateLogPrices(@NonNull final ModularContractLogEntriesList logs)
	 {
		 if (logs.isEmpty())
		 {
			 return;
		 }

		 final ProductPrice productPriceToMatch = logs.getFirstPriceActual();
		 Check.assumeNotNull(productPriceToMatch, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		 logs.forEach(log -> validateLogPrice(log.getPriceActual(), productPriceToMatch));
	 }

	 private void validateLogPrice(@Nullable final ProductPrice productPrice, @NonNull final ProductPrice productPriceToMatch)
	 {
		 Check.assumeNotNull(productPrice, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		 Check.assume(productPrice.isEqualByComparingTo(productPriceToMatch), "ProductPrices of billable modular contract logs should be identical", productPrice, productPriceToMatch);
	 }

	 public StockQtyAndUOMQty getStockQtyAndQtyInUOM(@NonNull final ModularContractLogEntriesList logs, @NonNull final UomId targetUomId)
	 {
		 final Quantity qtyInTargetUOM = logs.getQtySum(targetUomId, uomConversionBL);

		 final ProductId productId = logs.getSingleProductId();
		 final UomId stockUomId = productBL.getStockUOMId(productId);
		 final Quantity qtyInStockUOM = uomConversionBL.convertQuantityTo(qtyInTargetUOM, productId, stockUomId);

		 return StockQtyAndUOMQty.builder()
				 .productId(productId)
				 .stockQty(qtyInStockUOM)
				 .uomQty(qtyInTargetUOM)
				 .build();
	 }

	 public void updatePriceAndAmount(@NonNull final ModCntrLogPriceUpdateRequest request, @NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	 {
		 modularContractLogDAO.save(modularContractLogDAO.getModularContractLogEntries(ModularContractLogQuery.builder()
						 .flatrateTermId(request.flatrateTermId())
						 .processed(false)
						 .contractModuleId(request.modularContractModuleId())
						 .excludedReferencedTableId(INVOICE_LINE_TABLE_ID)
						 .build())
				 .withPriceActualAndCalculateAmount(request.unitPrice(), uomConversionBL, logHandlerRegistry));
	 }

	 public void updateModularLog(@NonNull final ModularContractLogEntry logEntry)
	 {
		 modularContractLogDAO.save(ModularContractLogEntriesList.ofSingle(logEntry));
	 }

	 public ModularContractLogEntryId create(@NonNull final LogEntryCreateRequest request)
	 {
		 return modularContractLogDAO.create(request);
	 }

	 public void setDefinitiveICLogsProcessed(
			 @NonNull final ModularContractLogQuery modularContractLogQuery,
			 @NonNull final InvoiceCandidateId invoiceCandidateId,
			 @NonNull final CurrencyId currencyId,
			 @NonNull final UomId uomId)
	 {
		 final ModularContractLogEntriesList modularContractLogEntries = getModularContractLogEntries(modularContractLogQuery);
		 final ModuleConfig moduleConfig = modularContractSettingsService.getByModuleId(modularContractLogEntries.getSingleModuleId());
		 final ModularContractLogEntriesList finalInvoiceRecords = modularContractLogEntries.subsetOf(LogEntryDocumentType.FINAL_INVOICE);
		 final ModularContractLogEntriesList shipmentRecords = modularContractLogEntries.subsetOf(LogEntryDocumentType.SHIPMENT);
		 final LocalDateAndOrgId finalInvoiceDate = finalInvoiceRecords.getSingleTransactionDate();

		 final ImmutableList<InvoiceDetailItem> invoiceDetailItems;
		 final InvoiceDetailItem finalInvoiceDateDetailItem = InvoiceDetailItem.builder()
				 .label(INVOICE_DETAILS_FINAL_INVOICE_DATE)
				 .date(finalInvoiceDate != null ? finalInvoiceDate.toLocalDate() : null)
				 .orgId(modularContractLogEntries.getSingleClientAndOrgId().getOrgId())
				 .build();

		 if(moduleConfig.isMatchingAnyOf(DEFINITIVE_INVOICE_SPECIFIC_SALES_METHODS))
		 {
			 final Quantity finalInvoicedQty = finalInvoiceRecords.getQtySum(uomId, uomConversionBL);
			 final Quantity shippedQty = shipmentRecords.getQtySum(uomId, uomConversionBL);
			 final OrgId orgId = modularContractLogEntries.getSingleClientAndOrgId().getOrgId();
			 invoiceDetailItems = ImmutableList.of(InvoiceDetailItem.builder()
														   .label(INVOICE_DETAILS_RECEIVED)
														   .qty(finalInvoicedQty.negate())
														   .orgId(orgId)
														   .build(),
												   InvoiceDetailItem.builder()
														   .label(INVOICE_DETAILS_SHIPPED)
														   .qty(shippedQty)
														   .orgId(orgId)
														   .build(),
												   finalInvoiceDateDetailItem
			 );
		 }
		 else if(moduleConfig.isMatchingAnyOf(ImmutableSet.of(ComputingMethodType.DefinitiveInvoiceStorageCost, ComputingMethodType.DefinitiveInvoiceAverageAVOnShippedQty)))
		 {
			 final Money finalInvoicedAmount = finalInvoiceRecords.getAmountSum().orElseGet(() -> Money.zero(currencyId));
			 final Money definitiveAmount = shipmentRecords.getAmountSum().orElseGet(() -> Money.zero(currencyId));
			 final OrgId orgId = modularContractLogEntries.getSingleClientAndOrgId().getOrgId();
			 invoiceDetailItems = ImmutableList.of(InvoiceDetailItem.builder()
														   .label(INVOICE_DETAILS_RECEIVED)
														   .price(finalInvoicedAmount.negate().toBigDecimal())
														   .orgId(orgId)
														   .build(),
												   InvoiceDetailItem.builder()
														   .label(INVOICE_DETAILS_SHIPPED)
														   .price(definitiveAmount.toBigDecimal())
														   .orgId(orgId)
														   .build(),
												   finalInvoiceDateDetailItem
			 );
		 }
		 else
		 {
			 throw new AdempiereException("Unknown Definitive Invoice Computing Method");
		 }

		 invoiceCandidateWithDetailsRepository.save(InvoiceCandidateWithDetails.builder()
				 .invoiceCandidateId(invoiceCandidateId)
				 .detailItems(invoiceDetailItems)
				 .build());

		 setICProcessed(modularContractLogQuery, invoiceCandidateId);
	 }

	 public void reverse(@NonNull final LogEntryReverseRequest logEntryReverseRequest)
	 {
		 modularContractLogDAO.reverse(logEntryReverseRequest);
	 }

	 public void unprocessModularContractLogs(@NonNull final InvoiceId invoiceId, @NonNull final DocTypeId docTypeId)
	 {
		 if (!(docTypeBL.isFinalInvoiceOrFinalCreditMemo(docTypeId)
				 || docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId)
				 || docTypeBL.isInterimInvoice(docTypeId)
		 		 || docTypeBL.isSalesFinalInvoiceOrFinalCreditMemo(docTypeId))
		 )
		 {
			 return;
		 }

		 unprocessLogsForInvoice(invoiceId, getComputingMethodTypes(docTypeId));

		 final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidates(invoiceId);
		 for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		 {
			 invoiceCandidate.setProcessed(false);//maybe should live in de.metas.invoicecandidate.api.impl.InvoiceCandBL.handleReversalForInvoice, but I'm afraid of consequences
			 invoiceCandidate.setIsActive(false);
		 }
		 InterfaceWrapperHelper.saveAll(invoiceCandidates);
		 if (docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId))
		 {
			 flatrateBL.reverseDefinitiveInvoice(ImmutableSet.of(Check.assumePresent(flatrateBL.getIdByInvoiceId(invoiceId), "FlatrateTermId should be present")));
		 }
	 }

	 @NonNull
	 private Collection<ComputingMethodType> getComputingMethodTypes(@NonNull final DocTypeId docTypeId)
	 {
		 if (docTypeBL.isFinalInvoiceOrFinalCreditMemo(docTypeId))
		 {
			 return ComputingMethodType.PURCHASE_FINAL_INVOICE_SPECIFIC_METHODS;
		 }
		 if (docTypeBL.isDefinitiveInvoiceOrDefinitiveCreditMemo(docTypeId))
		 {
			 return DEFINITIVE_INVOICE_SPECIFIC_METHODS;
		 }
		 if (docTypeBL.isInterimInvoice(docTypeId))
		 {
			 return ComputingMethodType.INTERIM_INVOICE_SPECIFIC_METHODS;
		 }
		 if (docTypeBL.isSalesFinalInvoiceOrFinalCreditMemo(docTypeId))
		 {
			 return ComputingMethodType.SALES_FINAL_INVOICE_SPECIFIC_METHODS;
		 }
		 throw new AdempiereException("Unexpected document type: " + docTypeId);
	 }

	 @NonNull
	 public ModularContractLogEntry getById(final ModularContractLogEntryId modularContractLogEntryId)
	 {
		 return modularContractLogDAO.getById(modularContractLogEntryId);
	 }

	 @NonNull
	 public CurrencyConversionContext getCurrencyConversionContext(@NonNull final ModularContractLogEntry logEntry)
	 {
		 final Instant conversionDate = logEntry.getTransactionDate().toInstant(orgDAO::getTimeZone);
		 return currencyBL.createCurrencyConversionContext(conversionDate,
														   logEntry.getClientAndOrgId().getClientId(),
														   logEntry.getClientAndOrgId().getOrgId());
	 }

	 public void updateAverageContractSpecificPrice(
			 @NonNull final ModuleConfig moduleConfig,
			 @NonNull final FlatrateTermId flatrateTermId,
			 @NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	 {
		 final ModularContractModuleId modularContractModuleId = moduleConfig.getModularContractModuleId();
		 final ModularContractLogEntriesList logs = modularContractLogDAO.getModularContractLogEntries(
				 ModularContractLogQuery.builder()
						 .billable(true)
						 .contractModuleId(modularContractModuleId)
						 .flatrateTermId(flatrateTermId)
						 .build()
		 );

		 final ModularContractLogEntriesList shipmentLogs = moduleConfig.isMatchingAnyOf(DEFINITIVE_INVOICE_SPECIFIC_METHODS) ?
				 logs.subsetOf(LogEntryDocumentType.SHIPMENT)
				 : logs.subsetOf(ALL_SHIPMENT_MODCNTR_LOG_DOCUMENTTYPES);
		 final ProductPrice averagePrice = getAveragePrice(shipmentLogs, flatrateTermId, moduleConfig);

		 final ContractSpecificPriceRequest contractSpecificPriceRequest = ContractSpecificPriceRequest.builder()
				 .flatrateTermId(flatrateTermId)
				 .modularContractModuleId(modularContractModuleId)
				 .build();
		 modularContractPriceService.updateAveragePrice(contractSpecificPriceRequest, averagePrice);
		 final ModularContractLogEntriesList logsToUpdate = logs.subsetOf(TO_UPDATE_WITH_AVERAGE_PRICE_DOCUMENTTYPES).subsetOf(false);
		 modularContractLogDAO.save(logsToUpdate.withPriceActualAndCalculateAmount(averagePrice, uomConversionBL, logHandlerRegistry));
	 }

	 @NonNull
	 private ProductPrice getAveragePrice(
			 @NonNull final ModularContractLogEntriesList logs,
			 @NonNull final FlatrateTermId flatrateTermId,
			 @NonNull final ModuleConfig moduleConfig
	 )
	 {
		 final ProductId productId = moduleConfig.getProductId();
		 final UomId targetUOMId = productBL.getStockUOMId(productId);
		 final ModularContractSettings settings = modularContractSettingsService.getById(moduleConfig.getModularContractSettingsId());

		 final Optional<ProductPrice> unprocessedLogsPrice = logs.subsetOf(false).getAveragePrice(productId, targetUOMId, uomConversionBL);
		 if (unprocessedLogsPrice.isPresent())
		 {
			 return unprocessedLogsPrice.get();
		 }

		 final Optional<ProductPrice> processedLogsPrice = logs.subsetOf(true).getAveragePrice(productId, targetUOMId, uomConversionBL);
		 if (processedLogsPrice.isPresent())
		 {
			 return processedLogsPrice.get();
		 }

		 if(ProductId.equals(productId, settings.getRawProductId()))
		 {
			 return Check.assumeNotNull(flatrateBL.extractPriceActualById(flatrateTermId), "contract product price shouldn't be null");
		 }
		 else if(ProductId.equals(productId, settings.getProcessedProductId()))
		 {
			 final IEditablePricingContext pricingContext = modularContractPriceService.createPricingContextTemplate(flatrateBL.getById(flatrateTermId), settings)
					 .setQty(Quantitys.of(BigDecimal.ONE, targetUOMId))
					 .setProductId(productId);

			 final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
			 return ProductPrice.builder()
					 .money(pricingResult.getPriceStdAsMoney())
					 .productId(pricingResult.getProductId())
					 .uomId(pricingResult.getPriceUomId())
					 .build();
		 }
		 else
		 {
			 throw new AdempiereException("Couldn't find average price, this shouldn't happen");
		 }
     }
 }
