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

package de.metas.contracts.modular.computing.purchasecontract.sales.processed;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessedSalesManufacturingOrderLog implements IModularContractLogHandler
{
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.manufacturingOrderCompleteLogDescription");

	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;
	@NonNull private final ModularContractService modularContractService;

	@Getter @NonNull private final ProcessedSalesComputingMethod computingMethod;
	@Getter @NonNull private final String supportedTableName = I_PP_Order.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRODUCTION;

	public boolean applies(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		if (recordRef.getTableName().equals(getSupportedTableName()))
		{
			final PPOrderId ppOrderId = recordRef.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId);
			final I_PP_Order orderRecord = ppOrderBL.getById(ppOrderId);

			return request.getProductId().equals(ProductId.ofRepoId(orderRecord.getM_Product_ID()));
		}

		return false;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final PPOrderId ppOrderId = recordRef.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId);
		final FlatrateTermId contractId = createLogRequest.getContractId();
		final ModularContractSettings modularContractSettings = createLogRequest.getModularContractSettings();

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);

		final I_PP_Order ppOrderRecord = ppOrderBL.getById(ppOrderId);

		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(ppOrderRecord.getC_UOM_ID()));

		final ProductId productId = ProductId.ofRepoId(ppOrderRecord.getM_Product_ID());

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(ppOrderRecord.getDateOrdered(),
																				OrgId.ofRepoId(ppOrderRecord.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, transactionDate.toInstant(orgDAO::getTimeZone))
				.orElse(null);

		final Quantity quantity = Quantity.of(ppOrderRecord.getQtyEntered(), uom);
		final String productName = productBL.getProductValueAndName(productId);

		final String description = msgBL.getBaseLanguageMsg(MSG_ON_COMPLETE_DESCRIPTION, productName, quantity);

		final ProductPrice contractSpecificPrice = modularContractService.getContractSpecificPrice(createLogRequest.getModularContractModuleId(), createLogRequest.getContractId());

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(contractId)
											.referencedRecord(TableRecordReference.of(I_PP_Order.Table_Name, ppOrderRecord.getPP_Order_ID()))
											.productId(productId)
											.productName(createLogRequest.getProductName())
											.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
											.warehouseId(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()))
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.PURCHASE)
											.quantity(quantity)
											.transactionDate(transactionDate)
											.year(modularContractSettings.getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configId(createLogRequest.getConfigId())
											.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID()))
											.invoicingGroupId(invoicingGroupId)
											.isBillable(true)
											.priceActual(contractSpecificPrice)
											.amount(contractSpecificPrice.computeAmount(quantity, uomConversionBL))
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new UnsupportedOperationException();
	}
}
