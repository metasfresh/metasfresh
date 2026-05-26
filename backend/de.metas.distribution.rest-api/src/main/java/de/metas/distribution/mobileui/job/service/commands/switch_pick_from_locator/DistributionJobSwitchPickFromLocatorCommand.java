package de.metas.distribution.mobileui.job.service.commands.switch_pick_from_locator;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.mobileui.external_services.warehouse.NextPickFromLocatorResolver;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.service.DistributionJobLoader;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.i18n.AdMessageKey;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;

public class DistributionJobSwitchPickFromLocatorCommand
{
	static final AdMessageKey MSG_NOT_AVAILABLE = AdMessageKey.of("MobileUI_DDOrder_SwitchPickFromLocator_NotAvailable");

	@NonNull private final ITrxManager trxManager;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final NextPickFromLocatorResolver nextLocatorResolver;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DistributionJobId jobId;

	@Builder
	private DistributionJobSwitchPickFromLocatorCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final NextPickFromLocatorResolver nextLocatorResolver,
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DistributionJobId jobId)
	{
		this.trxManager = trxManager;
		this.loadingSupportServices = loadingSupportServices;
		this.nextLocatorResolver = nextLocatorResolver;
		this.ddOrderService = ddOrderService;
		this.jobId = jobId;
	}

	public DistributionJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private DistributionJob executeInTrx()
	{
		final DistributionJobLoader loader = new DistributionJobLoader(loadingSupportServices);
		final DistributionJob job = loader.loadByJobId(jobId);

		if (!job.canSwitchPickFromLocator())
		{
			throw new AdempiereException(MSG_NOT_AVAILABLE);
		}

		final LocatorId currentLocatorId = job.getSinglePickFromLocatorIdOrNull();
		// canSwitchPickFromLocator guarantees currentLocatorId is non-null
		final WarehouseId warehouseId = job.getPickFromWarehouse().getWarehouseId();
		final LocatorId nextLocatorId = nextLocatorResolver.resolveNext(warehouseId, currentLocatorId);

		final DDOrderId ddOrderId = jobId.toDDOrderId();
		final I_DD_Order ddOrder = loadingSupportServices.getDDOrderById(ddOrderId);
		final List<I_DD_OrderLine> lines = ddOrderService.retrieveLines(ddOrder);
		for (final I_DD_OrderLine line : lines)
		{
			line.setM_Locator_ID(nextLocatorId.getRepoId());
			InterfaceWrapperHelper.save(line);
		}

		return loader.loadByJobId(jobId);
	}
}
