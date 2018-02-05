package de.metas.handlingunits.shipmentschedule.async;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.util.Env;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.HUShippingFacade;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/**
 * Generates Shipment document from given loading units (LUs).
 * On demand it can also add the HUs to Shipper Transportation and it can also invoice the HUs.
 *
 * @author tsa
 *
 */
public class GenerateInOutFromHU extends WorkpackageProcessorAdapter
{
	//
	// Parameters
	private static final String PARAMETERNAME_AddToShipperTransportationId = "AddToShipperTransportationId";
	private static final String PARAMETERNAME_IsCompleteShipments = ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments;
	private static final String PARAMETERNAME_InvoiceMode = "InvoiceMode";

	public static enum BillAssociatedInvoiceCandidates
	{
		/**
		 * don't invoice any associated ICs (the default)
		 */
		NO,

		/**
		 * Invoice those related invoice candidates whose {@link de.metas.invoicecandidate.model.I_C_Invoice_Candidate#COLUMN_DateToInvoice_Effective}
		 * value allows this.
		 */
		IF_INVOICE_SCHEDULE_PERMITS,
	};

	//
	// State
	private InOutGenerateResult inoutGenerateResult = null;

	/**
	 * Create and enqueue a workpackage for given handling units. Created workpackage will be marked as ready for processing.
	 *
	 * @param ctx
	 * @param hus handling units to enqueue
	 * @return created workpackage.
	 */
	public static final I_C_Queue_WorkPackage enqueueWorkpackage(final Collection<I_M_HU> hus)
	{
		return prepareWorkpackage()
				.hus(hus)
				.completeShipments(false)
				.invoiceMode(BillAssociatedInvoiceCandidates.NO)
				.enqueue();
	}

	@Builder(builderMethodName = "prepareWorkpackage", buildMethodName = "enqueue")
	private static final I_C_Queue_WorkPackage enqueueWorkpackage(
			@NonNull @Singular("hu") final List<I_M_HU> hus,
			final int addToShipperTransportationId,
			final boolean completeShipments,
			@Nullable final BillAssociatedInvoiceCandidates invoiceMode)
	{
		Check.assumeNotEmpty(hus, "hus is not empty");

		final BillAssociatedInvoiceCandidates invoiceModeEffective = invoiceMode != null ? invoiceMode : BillAssociatedInvoiceCandidates.NO;

		final Properties ctx = Env.getCtx();
		return Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, GenerateInOutFromHU.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.bindToThreadInheritedTrx()
				.setUserInChargeId(Env.getAD_User_ID(ctx)) // invoker
				.parameters()
				.setParameter(PARAMETERNAME_AddToShipperTransportationId, addToShipperTransportationId > 0 ? addToShipperTransportationId : 0)
				.setParameter(PARAMETERNAME_InvoiceMode, invoiceModeEffective.name())
				.setParameter(PARAMETERNAME_IsCompleteShipments, completeShipments)
				.end()
				.addElements(hus)
				.build();
	}

	@Override
	public boolean isAllowRetryOnError()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		//
		// Retrieve enqueued HUs
		final List<I_M_HU> hus = retrieveItems(I_M_HU.class);
		if (hus.isEmpty())
		{
			Loggables.get().addLog("No HUs found");
			return Result.SUCCESS;
		}

		final IParams parameters = getParameters();

		final int addToShipperTransportationId = parameters.getParameterAsInt(PARAMETERNAME_AddToShipperTransportationId);
		final boolean completeShipments = parameters.getParameterAsBool(PARAMETERNAME_IsCompleteShipments);
		final BillAssociatedInvoiceCandidates invoiceMode = parameters.getParameterAsEnum(PARAMETERNAME_InvoiceMode, BillAssociatedInvoiceCandidates.class, BillAssociatedInvoiceCandidates.NO);
		HUShippingFacade.builder()
				.loggable(Loggables.get())
				.hus(hus)
				.addToShipperTransportationId(addToShipperTransportationId)
				.completeShipments(completeShipments)
				.invoiceMode(invoiceMode)
				.build()
				.generateShippingDocuments();

		return Result.SUCCESS;
	}

	/**
	 * Returns an instance of {@link CreateShipmentLatch}.
	 *
	 * @task http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29
	 */
	@Override
	public ILatchStragegy getLatchStrategy()
	{
		return CreateShipmentLatch.INSTANCE;
	}

	/**
	 * Gets the {@link InOutGenerateResult} created by {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
	 *
	 * @return shipment generation result; never return null
	 */
	public InOutGenerateResult getInOutGenerateResult()
	{
		Check.assumeNotNull(inoutGenerateResult, "workpackage shall be processed first");
		return inoutGenerateResult;
	}
}
