package de.metas.handlingunits.picking.job.massprinting;

import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Orchestration service for the mass-printing flow:
 * Scan LU → enumerate self-packed products → per-product FIFO selection →
 * create+pick+complete PRODUCT picking job → print one HU label per box.
 *
 * Per me03 #29942 (F00230.21 "Mass Printing Labels").
 */
@Service
@RequiredArgsConstructor
public class MassPrintingService
{
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;

	/**
	 * Scan the LU and for each self-packed product on it:
	 * <ol>
	 *   <li>Select open shipment schedules FIFO by preparation date, capped at units on LU.</li>
	 *   <li>Create a PRODUCT picking job restricted to those schedules.</li>
	 *   <li>Pick each schedule from the scanned LU (one box per unit via 1-CU-per-TU packTo PI).</li>
	 *   <li>Complete the picking job.</li>
	 *   <li>Print one HU label per box (best-effort, after commit).</li>
	 * </ol>
	 *
	 * @param request scan request carrying the LU id and the picker's user id
	 * @return per-product result summary (boxes packed, labels printed, leftovers)
	 */
	@NonNull
	public MassPrintingResult scan(@NonNull final MassPrintingScanRequest request)
	{
		throw new UnsupportedOperationException("MassPrintingService.scan() is not yet implemented — RED phase");
	}
}
