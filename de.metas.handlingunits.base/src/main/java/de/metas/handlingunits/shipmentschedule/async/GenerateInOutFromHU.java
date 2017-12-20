package de.metas.handlingunits.shipmentschedule.async;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
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

	public static enum InvoiceMode
	{
		None, AllWithoutInvoiceSchedule,
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
				.invoiceMode(InvoiceMode.None)
				.enqueue();
	}

	@Builder(builderMethodName = "prepareWorkpackage", buildMethodName = "enqueue")
	private static final I_C_Queue_WorkPackage enqueueWorkpackage(
			@NonNull @Singular("hu") final List<I_M_HU> hus,
			final int addToShipperTransportationId,
			final boolean completeShipments,
			@Nullable final InvoiceMode invoiceMode)
	{
		Check.assumeNotEmpty(hus, "hus is not empty");

		final InvoiceMode invoiceModeEffective = invoiceMode != null ? invoiceMode : InvoiceMode.None;

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
		final List<I_M_HU> hus = retriveWorkpackageHUs();
		if (hus.isEmpty())
		{
			Loggables.get().addLog("No HUs found");
			return Result.SUCCESS;
		}

		//
		// Add HUs to shipper transportation if needed
		final int addToShipperTransportationId = getAddToShipperTransportationId();
		if (addToShipperTransportationId > 0)
		{
			final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
			huShipperTransportationBL.addHUsToShipperTransportation(addToShipperTransportationId, hus);
			Loggables.get().addLog("HUs added to M_ShipperTransportation_ID={}", addToShipperTransportationId);
		}

		//
		// Generate shipments
		{
			final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
			final List<IShipmentScheduleWithHU> candidates = huShipmentScheduleDAO.retrieveShipmentSchedulesWithHUsFromHUs(hus);
			inoutGenerateResult = Services.get(IHUShipmentScheduleBL.class)
					.createInOutProducerFromShipmentSchedule()
					.setProcessShipments(isCompleteShipments())
					.setCreatePackingLines(false) // the packing lines shall only be created when the shipments are completed
					.setManualPackingMaterial(false) // use the HUs!
					.computeShipmentDate(true) // if this is ever used, it should be on true to keep legacy
					// Fail on any exception, because we cannot create just a part of those shipments.
					// Think about HUs which are linked to multiple shipments: you will not see then in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
					.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
					.createShipments(candidates);
			Loggables.get().addLog("Generated {}", inoutGenerateResult.toString());
		}

		//
		// Generate invoices
		generateInvoicesIfNeeded(inoutGenerateResult.getInOuts());

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

	private List<I_M_HU> retriveWorkpackageHUs()
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();

		final List<I_M_HU> hus = queueDAO.retrieveItems(workpackage, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);
		return hus;
	}

	@VisibleForTesting
	public List<IShipmentScheduleWithHU> retrieveCandidates()
	{
		final List<I_M_HU> hus = retriveWorkpackageHUs();
		if (hus.isEmpty())
		{
			Loggables.get().addLog("No HUs found");
			return ImmutableList.of();
		}
		else
		{
			final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
			return huShipmentScheduleDAO.retrieveShipmentSchedulesWithHUsFromHUs(hus);
		}
	}

	private void generateInvoicesIfNeeded(final List<I_M_InOut> shipments)
	{
		if (shipments.isEmpty())
		{
			return;
		}

		final InvoiceMode invoiceMode = getInvoiceMode();
		if (invoiceMode == InvoiceMode.None)
		{
			return;
		}

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		// TODO: atm createSelection from a query with UNIONs is not working. Fix that first.
		final List<Integer> invoiceCandidateIds = invoiceCandDAO.retrieveInvoiceCandidatesQueryForInOuts(shipments).listIds();
		final int invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, ITrx.TRXNAME_ThreadInherited);

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(InvoiceMode.AllWithoutInvoiceSchedule == invoiceMode);

		final ILoggable loggable = Loggables.get();
		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setLoggable(loggable)
				.setInvoicingParams(invoicingParams)
				//
				.enqueueSelection(invoiceCandidatesSelectionId);
		loggable.addLog("Invoice candidates enqueued: {}", enqueueResult);
	}

	private int getAddToShipperTransportationId()
	{
		return getParameters().getParameterAsInt(PARAMETERNAME_AddToShipperTransportationId);
	}

	private boolean isCompleteShipments()
	{
		return getParameters().getParameterAsBool(PARAMETERNAME_IsCompleteShipments);
	}

	private InvoiceMode getInvoiceMode()
	{
		final String invoiceModeStr = getParameters().getParameterAsString(PARAMETERNAME_InvoiceMode);
		return !Check.isEmpty(invoiceModeStr, true) ? InvoiceMode.valueOf(invoiceModeStr) : InvoiceMode.None;
	}
}
