package de.metas.contracts.modular.computing.purchasecontract.definitiveinvoice.processed;

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.workpackage.impl.AbstractDefinitiveInvoiceShipmentLogHandler;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ShipmentLineLog extends AbstractDefinitiveInvoiceShipmentLogHandler
{

	public ShipmentLineLog(
			@NonNull final ModularContractService modularContractService,
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final DefinitiveInvoiceForProcessedProductComputingMethod computingMethod)
	{
		super(modularContractService, modCntrInvoicingGroupRepository, computingMethod);
	}
}