package de.metas.handlingunits.ddorder.movement;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickSchedule;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.DDOrderId;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generate movements from {@link DDOrderPickSchedule}
 */
public class MovementsFromSchedulesGenerator
{
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUDDOrderBL ddOrderBL;
	private final DDOrderPickFromService ddOrderPickFromService;

	// Parameters
	private final ImmutableList<DDOrderPickSchedule> schedules;
	private final Instant movementDate = SystemTime.asInstant();
	private LocatorId locatorToIdOverride = null;
	private boolean doDirectMovements = false;
	private boolean skipCompletingDDOrder = false;

	// State
	private final HashMap<DDOrderId, MovementBuilder> shipmentMovementBuilder = new HashMap<>();
	private final HashMap<DDOrderId, MovementBuilder> receiptMovementBuilder = new HashMap<>();
	private final MovementBuilder.HuIdsWithPackingMaterialsTransferred huIdsWithPackingMaterialsTransferred;
	private final DDOrdersCache ddOrdersCache = new DDOrdersCache();

	private MovementsFromSchedulesGenerator(
			@NonNull final IHUDDOrderBL ddOrderBL,
			@NonNull final DDOrderPickFromService ddOrderPickFromService,
			@NonNull final List<DDOrderPickSchedule> schedules)
	{
		Check.assumeNotEmpty(schedules, "schedules not empty");

		this.ddOrderBL = ddOrderBL;
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.schedules = ImmutableList.copyOf(schedules);

		huIdsWithPackingMaterialsTransferred = new MovementBuilder.HuIdsWithPackingMaterialsTransferred();
	}

	public static MovementsFromSchedulesGenerator fromSchedules(
			@NonNull final List<DDOrderPickSchedule> schedules,
			@NonNull final IHUDDOrderBL ddOrderBL,
			@NonNull final DDOrderPickFromService ddOrderPickFromService)
	{
		return new MovementsFromSchedulesGenerator(ddOrderBL, ddOrderPickFromService, schedules);
	}

	public MovementsFromSchedulesGenerator locatorToIdOverride(@Nullable final LocatorId locatorToIdOverride)
	{
		this.locatorToIdOverride = locatorToIdOverride;
		return this;
	}

	/**
	 * Do direct movements (e.g. skip the InTransit warehouse)
	 */
	public MovementsFromSchedulesGenerator doDirectMovements()
	{
		this.doDirectMovements = true;
		return this;
	}

	public MovementsFromSchedulesGenerator skipCompletingDDOrder()
	{
		this.skipCompletingDDOrder = true;
		return this;
	}

	/**
	 * Process allocations and create material movement documents
	 */
	public void processWithinOwnTrx()
	{
		trxManager.runInNewTrx(this::process);
	}

	public void process()
	{
		// Clean previous state
		shipmentMovementBuilder.clear();
		receiptMovementBuilder.clear();
		huIdsWithPackingMaterialsTransferred.clear();

		//
		// Add lines to allocate to shipment and receipt builders
		schedules.forEach(this::processLine);

		//
		// After creating and loading movement builders, process shipments first
		processMovementBuilders(shipmentMovementBuilder);

		//
		// After creating and loading movement builders, process receipts second
		processMovementBuilders(receiptMovementBuilder);
	}

	private static void processMovementBuilders(final Map<DDOrderId, MovementBuilder> builders)
	{
		for (final MovementBuilder movementBuilder : builders.values())
		{
			movementBuilder.process();
		}
		builders.clear();
	}

	private void processLine(final DDOrderPickSchedule line)
	{
		// make sure we are running in transaction
		trxManager.assertThreadInheritedTrxExists();

		//
		// Make sure DD Order is completed
		if (!skipCompletingDDOrder)
		{
			final I_DD_Order ddOrder = getDDOrderById(line.getDdOrderId());
			ddOrderBL.completeDDOrderIfNeeded(ddOrder);
		}

		if (doDirectMovements)
		{
			// Generate direct movement: source locator -> destination locator
			getMovementBuilder(receiptMovementBuilder, line.getDdOrderId())
					.addMovementLineDirect(line);
		}
		else
		{
			// Generate movement (shipment): source locator -> in transit
			{
				getMovementBuilder(shipmentMovementBuilder, line.getDdOrderId())
						.addMovementLineShipment(line);
			}

			// Generate movement (receipt): in transit -> destination locator
			{
				getMovementBuilder(receiptMovementBuilder, line.getDdOrderId())
						.addMovementLineReceipt(line);
			}
		}
	}

	private MovementBuilder getMovementBuilder(@NonNull final Map<DDOrderId, MovementBuilder> ddOrderId2MovementBuilder, @NonNull final DDOrderId ddOrderId)
	{
		return ddOrderId2MovementBuilder.computeIfAbsent(ddOrderId, this::createMovementBuilder);
	}

	private MovementBuilder createMovementBuilder(@NonNull final DDOrderId ddOrderId)
	{
		return new MovementBuilder(ddOrderBL, ddOrderPickFromService, ddOrdersCache, huIdsWithPackingMaterialsTransferred, ddOrderId)
				.movementDate(movementDate)
				.locatorToIdOverride(locatorToIdOverride);
	}

	private I_DD_Order getDDOrderById(final DDOrderId ddOrderId)
	{
		return ddOrdersCache.getById(ddOrderId);
	}
}
