package de.metas.contracts.modular.computing.purchasecontract.sales.processed.shippedqty;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.workpackage.impl.AbstractOrderPriceShipmentLogHandler;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProformaShipmentLineLog extends AbstractOrderPriceShipmentLogHandler
{
	@NonNull LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PROFORMA_SHIPMENT;

	public ProformaShipmentLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ProcessedSalesShippedQtyComputingMethod computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
	}
}