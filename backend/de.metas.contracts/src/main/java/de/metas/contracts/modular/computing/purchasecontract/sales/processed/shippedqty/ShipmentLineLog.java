package de.metas.contracts.modular.computing.purchasecontract.sales.processed.shippedqty;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.workpackage.impl.AbstractOrderPriceShipmentLogHandler;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShipmentLineLog extends AbstractOrderPriceShipmentLogHandler
{
	public ShipmentLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final ProcessedSalesShippedQtyComputingMethod computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
	}
}