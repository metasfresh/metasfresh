package de.metas.distribution.mobileui.job.service.commands.switch_pick_from_locator;

import de.metas.distribution.ddorder.DDOrderId;
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
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.util.List;

public class DistributionJobSwitchPickFromLocatorCommand
{
	static final AdMessageKey MSG_NOT_AVAILABLE = AdMessageKey.of("MobileUI_DDOrder_SwitchPickFromLocator_NotAvailable");

	@NonNull private final ITrxManager trxManager;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final NextPickFromLocatorResolver nextLocatorResolver;
	@NonNull private final DistributionJobId jobId;

	@Builder
	private DistributionJobSwitchPickFromLocatorCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final NextPickFromLocatorResolver nextLocatorResolver,
			@NonNull final DistributionJobId jobId)
	{
		this.trxManager = trxManager;
		this.loadingSupportServices = loadingSupportServices;
		this.nextLocatorResolver = nextLocatorResolver;
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

		final LocatorId currentLocatorId = job.getSinglePickFromLocatorId();
		final LocatorId nextLocatorId = nextLocatorResolver.resolveNext(currentLocatorId);

		final DDOrderId ddOrderId = jobId.toDDOrderId();
		final I_DD_Order ddOrder = loadingSupportServices.getDDOrderById(ddOrderId);
		final List<I_DD_OrderLine> lines = loadingSupportServices.retrieveLines(ddOrder);
		for (final I_DD_OrderLine line : lines)
		{
			// Resilience: only move lines that still sit on the locator we resolved the next one from.
			if (LocatorId.equalsByRepoId(line.getM_Locator_ID(), currentLocatorId.getRepoId()))
			{
				switchLinePickFromLocator(line, nextLocatorId);
			}
		}

		// Reload with a FRESH loader: the `loader` above cached the pre-switch lines in its instance-level
		// ddOrderLinesCache, so reusing it would return the old M_Locator_ID and the mobile UI would not reflect
		// the switch (even though the DB is correctly updated).
		return new DistributionJobLoader(loadingSupportServices).loadByJobId(jobId);
	}

	/**
	 * Move the line's pick-from locator to {@code nextLocatorId}, carrying the (legacy, locator-level) reservation along.
	 * <p>
	 * {@code MDDOrderLine.beforeSave} forbids changing {@code M_Locator_ID} while {@code QtyReserved != 0}
	 * ({@code canChangeWarehouse} throws {@code @QtyReserved}). So un-reserve on the old locator (set qty to 0,
	 * which lets the locator change pass), then re-reserve the same qty on the new locator.
	 */
	private void switchLinePickFromLocator(@NonNull final I_DD_OrderLine line, @NonNull final LocatorId nextLocatorId)
	{
		final BigDecimal qtyReserved = line.getQtyReserved();

		if (qtyReserved.signum() != 0)
		{
			line.setQtyReserved(BigDecimal.ZERO);
		}
		line.setM_Locator_ID(nextLocatorId.getRepoId());
		loadingSupportServices.saveLine(line);

		if (qtyReserved.signum() != 0)
		{
			line.setQtyReserved(qtyReserved);
			loadingSupportServices.saveLine(line);
		}
	}
}
