package de.metas.handlingunits.receiptschedule.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.CompositeDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.receiptschedule.IHUToReceiveValidator;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.spi.impl.InOutProducerFromReceiptScheduleHU;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.AdProcessId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBForeignKeyConstraintException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.createList;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class HUReceiptScheduleBL implements IHUReceiptScheduleBL
{
	private final IDocumentLUTUConfigurationHandler<I_M_ReceiptSchedule> lutuConfigurationHandler = ReceiptScheduleDocumentLUTUConfigurationHandler.instance;
	private final IDocumentLUTUConfigurationHandler<List<I_M_ReceiptSchedule>> lutuConfigurationListHandler = CompositeDocumentLUTUConfigurationHandler.of(lutuConfigurationHandler);

	private final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

	private static final transient Logger logger = LogManager.getLogger(HUReceiptScheduleBL.class);

	@Override
	public BigDecimal getQtyOrderedTUOrNull(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(receiptSchedule.getC_OrderLine(), I_C_OrderLine.class);
		if (ol == null)
		{
			return null;
		}

		final BigDecimal qtyTU = ol.getQtyEnteredTU();
		return qtyTU;
	}

	@Override
	public BigDecimal getQtyOrderedTUOrZero(final I_M_ReceiptSchedule receiptSchedule)
	{
		final BigDecimal qtyOrderedTU = getQtyOrderedTUOrNull(receiptSchedule);
		if (qtyOrderedTU == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyOrderedTU;
	}

	@Override
	@NonNull
	public BigDecimal getQtyToMoveTU(final I_M_ReceiptSchedule receiptSchedule)
	{
		final BigDecimal qtyOrderedTU = getQtyOrderedTUOrZero(receiptSchedule);
		final BigDecimal qtyMovedTU = receiptSchedule.getQtyMovedTU();
		final BigDecimal qtyToMoveTU = qtyOrderedTU.subtract(qtyMovedTU);

		return qtyToMoveTU;
	}

	// @Override public
	private IInOutProducer createInOutProducerFromReceiptScheduleHU(
			@NonNull final CreateReceiptsParameters parameters,
			@NonNull final InOutGenerateResult result)

	{
		final InOutProducerFromReceiptScheduleHU producer = new InOutProducerFromReceiptScheduleHU(parameters, result);
		return producer;
	}

	@Override
	public void destroyHandlingUnits(final List<I_M_ReceiptSchedule_Alloc> allocs, final String trxName)
	{
		if (allocs.isEmpty())
		{
			// do nothing
			return;
		}

		Services.get(ITrxManager.class).run(trxName, (TrxRunnable)localTrxName -> {
			final IContextAware context = Services.get(ITrxManager.class).createThreadContextAware(allocs.get(0));
			final IHUContext huContext = huContextFactory.createMutableHUContextForProcessing(context);

			Services.get(IHUTrxBL.class)
					.createHUContextProcessorExecutor(huContext)
					.run((IHUContextProcessor)huContext1 -> {
						destroyHandlingUnits(huContext1, allocs);
						return IHUContextProcessor.NULL_RESULT;
					});
		});
	}

	@Override
	public void destroyHandlingUnits(final IHUContext huContext, final List<I_M_ReceiptSchedule_Alloc> allocs)
	{
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

		//
		// Iterate all receipt schedule allocations and:
		// * destroy LUs and TUs if those are still planning
		// * unassign LUs and TUs
		for (final I_M_ReceiptSchedule_Alloc rsa : allocs)
		{
			// Ignore inactive allocations
			if (!rsa.isActive())
			{
				// we ignore the receipt schedule lines that are not active. those HUs are already delivered
				//
				continue;
			}

			// Ignore allocations which were already received
			if (rsa.getM_InOutLine_ID() > 0)
			{
				continue;
			}

			final List<HuId> husToUnassign = new ArrayList<>(2);

			final I_M_HU tuHU = rsa.getM_TU_HU();
			if (tuHU != null && tuHU.isActive()
					// rsa.isActive()=Y does not mean the HU can be destroyed! Only destroy if it is still in the planning stage
					&& X_M_HU.HUSTATUS_Planning.equals(tuHU.getHUStatus()))
			{
				handlingUnitsBL.markDestroyed(huContext, tuHU);
				husToUnassign.add(HuId.ofRepoId(tuHU.getM_HU_ID()));
			}

			final I_M_HU luHU = rsa.getM_LU_HU();
			if (luHU != null && luHU.isActive()
					// rsa.isActive()=Y does not mean the HU can be destroyed! Only destroy if it is still in the planning stage
					&& X_M_HU.HUSTATUS_Planning.equals(luHU.getHUStatus()))
			{
				handlingUnitsBL.markDestroyed(huContext, luHU);
				husToUnassign.add(HuId.ofRepoId(luHU.getM_HU_ID()));
			}

			//
			// Inactivate allocation
			rsa.setIsActive(false);

			//
			// Save allocation
			InterfaceWrapperHelper.save(rsa);

			//
			// Make sure HUs are unassigned
			final String trxName = InterfaceWrapperHelper.getTrxName(rsa);
			final de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule = rsa.getM_ReceiptSchedule();
			huAssignmentBL.unassignHUs(receiptSchedule, husToUnassign, trxName);
		}
	}

	@Override
	public IProductStorage createProductStorage(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule rs)
	{
		final boolean enforceCapacity = true;
		return new ReceiptScheduleProductStorage(rs, enforceCapacity);
	}

	@Override
	public IAllocationSource createAllocationSource(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IProductStorage productStorage = createProductStorage(receiptSchedule);
		final IAllocationSource allocationSource = new GenericAllocationSourceDestination(productStorage, receiptSchedule);
		return allocationSource;
	}

	@Override
	public IDocumentLUTUConfigurationManager createLUTUConfigurationManager(final I_M_ReceiptSchedule receiptSchedule)
	{
		return new DocumentLUTUConfigurationManager<>(receiptSchedule, lutuConfigurationHandler);
	}

	@Override
	public IDocumentLUTUConfigurationManager createLUTUConfigurationManager(final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		Check.assumeNotEmpty(receiptSchedules, "receiptSchedules not empty");
		if (receiptSchedules.size() == 1)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptSchedules.get(0);
			return createLUTUConfigurationManager(receiptSchedule);
		}
		else
		{
			return new DocumentLUTUConfigurationManager<>(receiptSchedules, lutuConfigurationListHandler);
		}
	}

	@Override
	public InOutGenerateResult processReceiptSchedules(@NonNull final CreateReceiptsParameters parameters)
	{
		try
		{
			final ITrxManager trxManager = Services.get(ITrxManager.class);
			final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);

			return trxManager.call(trxName, () -> processReceiptSchedules0(parameters));
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("CreateReceiptsParameters", parameters);
		}
	}

	/**
	 * Actually process receipt schedules.
	 * <p>
	 * At this point we assume that we have a thread inherited transaction.
	 */
	private InOutGenerateResult processReceiptSchedules0(@NonNull final CreateReceiptsParameters parameters)
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = createList(parameters.getReceiptSchedules(), I_M_ReceiptSchedule.class);

		final Set<HuId> selectedHuIds = parameters.getSelectedHuIds() != null
				? parameters.getSelectedHuIds()
				: retrieveAllocatedHuIds(receiptSchedules); //if no selectedHuIds were provided, load all the HUs allocated to the target receipt schedules

		// Iterate all selected receipt schedules, get assigned HUs and adjust their Product Storage Qty to WeightNet
		// note: selectedHUs might well be empty or null, for packing-material-M_ReceiptSchedules
		if (selectedHuIds != null && !selectedHuIds.isEmpty())
		{
			validateHuIds(selectedHuIds);
			
			final HUReceiptScheduleWeightNetAdjuster huWeightNetAdjuster = new HUReceiptScheduleWeightNetAdjuster(parameters.getCtx(), ITrx.TRXNAME_ThreadInherited);
			huWeightNetAdjuster.setInScopeHU_IDs(selectedHuIds);
			for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
			{
				// Adjust HU's product storages to their Weight Net Attribute
				huWeightNetAdjuster.addReceiptSchedule(receiptSchedule);
			}
		}

		final InOutGenerateResult result = createReceipts(parameters);

		if (parameters.isPrintReceiptLabels())
		{
			printReceiptLabels(result);
		}
		createMovementsOrDistributionOrders(result, parameters.getDestinationLocatorIdOrNull());

		return result;
	}

	private void validateHuIds(@NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			throw new HUException("@NoSelection@ @M_HU_ID@");
		}

		final IHUToReceiveValidator huToReceiveValidator = CompositeHUToReceiveValidator.of(SpringContextHolder.instance.getBeansOfType(IHUToReceiveValidator.class));

		for (final HuId huId : huIds)
		{
			final I_M_HU huRecord = load(huId, I_M_HU.class);
			if (!Services.get(IHUStatusBL.class).isStatusPlanned(huRecord))
			{
				throw new HUException("@Invalid@ @HUStatus@: " + huRecord.getValue());
			}

			huToReceiveValidator.assertValidForReceiving(huRecord);
		}
	}

	/**
	 * Generate receipt from selected receipt schedules and return the result
	 */
	private InOutGenerateResult createReceipts(@NonNull final CreateReceiptsParameters parameters)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IInOutCandidateBL inOutCandidateBL = Services.get(IInOutCandidateBL.class);

		// Get the transaction to be used
		final ITrx threadTrx;
		if (parameters.isCommitEachReceiptIndividually())
		{
			// this processor shall run with a local transaction, so there will be a commit after each item (i.e. in this case each chunk)
			threadTrx = ITrx.TRX_None;
		}
		else
		{
			threadTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail);
		}

		// Create result collector
		final InOutGenerateResult result = inOutCandidateBL.createEmptyInOutGenerateResult(true); // storeReceipts=true

		// Create Receipt producer
		final IInOutProducer producer = createInOutProducerFromReceiptScheduleHU(
				parameters,
				result);

		// Create executor and generate receipts with it
		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(parameters.getCtx(), threadTrx);

		executorService.<de.metas.inoutcandidate.model.I_M_ReceiptSchedule, InOutGenerateResult>createExecutor()
				.setContext(processorCtx)
				.setProcessor(producer)
				// Configure executor to fail on first error
				// NOTE: we expect max. 1 receipt, so it's fine to fail anyways
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				// Process schedules => receipt(s) will be generated
				.process(parameters.getReceiptSchedules());

		return result;
	}

	/**
	 * Create the material receipt label right here.
	 * We used to create it in ReceiptInOutLineHUAssignmentListener, but there we could not detect the code
	 * being called multiple times in a row, from different transactions.
	 * This happens if there are >1 inout lines sharing the same LU.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1905
	 */
	private void printReceiptLabels(final InOutGenerateResult result)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		final Set<Integer> seenHUIds = new HashSet<>();

		final List<I_M_InOut> inouts = createList(result.getInOuts(), I_M_InOut.class);
		for (final I_M_InOut inout : inouts)
		{
			final int vendorBPartnerId = inout.getC_BPartner_ID();

			final List<I_M_InOutLine> inoutLines = inOutDAO.retrieveLines(inout);
			for (final I_M_InOutLine inoutLine : inoutLines)
			{
				final List<I_M_HU_Assignment> huAssignments = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(inoutLine);
				for (final I_M_HU_Assignment huAssignment : huAssignments)
				{
					final int huId = huAssignment.getM_HU_ID();
					if (huId > 0 && seenHUIds.add(huId))
					{
						final HUToReportWrapper hu = HUToReportWrapper.of(huAssignment.getM_HU());
						printReceiptLabel(hu, vendorBPartnerId);
					}
				}
			}
		}
	}

	private void createMovementsOrDistributionOrders(
			@NonNull final InOutGenerateResult result,
			@Nullable final LocatorId destinationLocatorId)
	{
		final DistributeAndMoveReceiptCreator distributeAndMoveReceiptCreator = Adempiere.getBean(DistributeAndMoveReceiptCreator.class);

		final List<I_M_InOut> receipts = createList(result.getInOuts(), I_M_InOut.class);
		for (final I_M_InOut receipt : receipts)
		{
			distributeAndMoveReceiptCreator.createDocumentsFor(receipt, destinationLocatorId);
		}
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh-webui/issues/209
	 */
	private void printReceiptLabel(@Nullable final HUToReport hu, final int vendorBPartnerId)
	{
		if (hu == null)
		{
			logger.debug("Param 'hu'==null; nothing to do");
			return;
		}

		final HUReportService huReportService = HUReportService.get();
		if (!huReportService.isReceiptLabelAutoPrintEnabled(vendorBPartnerId))
		{
			logger.debug("Auto printing receipt labels is not enabled via SysConfig; nothing to do");
			return;
		}

		if (!hu.isTopLevel())
		{
			logger.debug("We only print top level HUs; nothing to do; hu={}", hu);
			return;
		}

		final AdProcessId adProcessId = huReportService.retrievePrintReceiptLabelProcessIdOrNull();
		if (adProcessId == null)
		{
			logger.debug("No process configured via SysConfig {}; nothing to do", HUReportService.SYSCONFIG_RECEIPT_LABEL_PROCESS_ID);
			return;
		}

		final List<HUToReport> husToProcess = huReportService
				.getHUsToProcess(hu, adProcessId)
				.stream()
				.collect(ImmutableList.toImmutableList());
		if (husToProcess.isEmpty())
		{
			logger.debug("The selected hu does not match process {}; nothing to do; hu={}", adProcessId, hu);
			return;
		}

		final int copies = huReportService.getReceiptLabelAutoPrintCopyCount();

		HUReportExecutor.newInstance(Env.getCtx())
				.numberOfCopies(copies)
				.executeHUReportAfterCommit(adProcessId, husToProcess);
	}

	@Override
	public IAllocationRequest setInitialAttributeValueDefaults(final IAllocationRequest request,
			final Collection<? extends de.metas.inoutcandidate.model.I_M_ReceiptSchedule> receiptSchedules)
	{
		Check.assumeNotNull(request, "request not null");
		Check.assumeNotEmpty(receiptSchedules, "receiptSchedule not empty");

		//
		// Iterate all receipt schedules and get:
		// * PriceActual; Make sure it's unique for all receipt schedule lines.
		// * C_OrderLine_IDs
		BigDecimal priceActual = null;
		final Set<Integer> purchaseOrderLineIds = new HashSet<>();
		for (final de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
			final org.compiere.model.I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
			Check.assumeNotNull(orderLine, "orderLine not null");

			final BigDecimal receiptSchedule_priceActual = orderLine.getPriceActual();

			if (priceActual == null)
			{
				priceActual = receiptSchedule_priceActual;
			}
			else if (priceActual.compareTo(receiptSchedule_priceActual) != 0)
			{
				throw new HUException("Got different PriceActual."
											  + "\n @PriceActual@: " + priceActual + ", " + receiptSchedule_priceActual
											  + "\n @M_ReceiptSchedule_ID@: " + receiptSchedules);
			}

			final int orderLineId = receiptSchedule.getC_OrderLine_ID();
			if (orderLineId > 0)
			{
				purchaseOrderLineIds.add(orderLineId);
			}
		}

		//
		// Set PriceActual in HUContext
		Check.assumeNotNull(priceActual, "priceActual not null");
		final IHUContext huContext = request.getHUContext();
		Map<AttributeId, Object> initialAttributeValueDefaults = huContext.getProperty(HUAttributeConstants.CTXATTR_DefaultAttributesValue);
		if (initialAttributeValueDefaults == null)
		{
			initialAttributeValueDefaults = new HashMap<>();
			huContext.setProperty(HUAttributeConstants.CTXATTR_DefaultAttributesValue, initialAttributeValueDefaults);
		}
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final AttributeId attrId_CostPrice = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_CostPrice);
		initialAttributeValueDefaults.put(attrId_CostPrice, priceActual);

		//
		// Set HU_PurchaseOrderLine_ID (task 09741)
		if (purchaseOrderLineIds.size() == 1)
		{
			final AttributeId attrId_PurchaseOrderLine = attributesRepo.retrieveAttributeIdByValue(HUAttributeConstants.ATTR_PurchaseOrderLine_ID);
			initialAttributeValueDefaults.put(attrId_PurchaseOrderLine, purchaseOrderLineIds.iterator().next());
		}

		return request;
	}

	@Override
	public IAllocationRequest setInitialAttributeValueDefaults(final IAllocationRequest request, final de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
		final Set<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> receiptSchedules = Collections.singleton(receiptSchedule);
		return setInitialAttributeValueDefaults(request, receiptSchedules);
	}

	@Override
	public void attachPhoto(final I_M_ReceiptSchedule receiptSchedule, final String filename, final BufferedImage image)
	{
		final byte[] imagePDFBytes = org.adempiere.pdf.Document.toPDFBytes(image);

		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);

		final IArchiveStorage archiveStorage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx);
		final I_AD_Archive archive = archiveStorage.newArchive(ctx, trxName);

		final int tableID = InterfaceWrapperHelper.getModelTableId(receiptSchedule);
		final int recordID = InterfaceWrapperHelper.getId(receiptSchedule);
		archive.setAD_Table_ID(tableID);
		archive.setRecord_ID(recordID);
		archive.setC_BPartner_ID(receiptSchedule.getC_BPartner_ID());
		archive.setAD_Org_ID(receiptSchedule.getAD_Org_ID());
		archive.setName(filename);
		archive.setIsReport(false);
		archiveStorage.setBinaryData(archive, imagePDFBytes);
		InterfaceWrapperHelper.save(archive);
	}

	/**
	 * Generate it's LU-TU structure automatically
	 */
	public void generateHUsIfNeeded(final I_M_ReceiptSchedule receiptSchedule, @NonNull final Properties context)
	{
		// Skip Receipt schedules which are about Packing Materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return;
		}

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final List<I_M_ReceiptSchedule_Alloc> allocsAll = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_ThreadInherited);
		if (!allocsAll.isEmpty())
		{
			loggable.addLog("M_ReceiptSchedule_ID={} - already has HUs assinged to it; not creating HUs", receiptSchedule.getM_ReceiptSchedule_ID());
		}

		try
		{
			loggable.addLog("M_ReceiptSchedule_ID={} - creating HUs and allocating HUs on the fly", receiptSchedule.getM_ReceiptSchedule_ID());

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID());

			final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(context, clientAndOrgId);

			final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContextInitial);

			huGenerator.addM_ReceiptSchedule(receiptSchedule);

			final I_M_HU_LUTU_Configuration lutuConfig = createLUTUConfigurationManager(receiptSchedule)
					.getCreateLUTUConfiguration();

			save(lutuConfig);
			huGenerator.setM_HU_LUTU_Configuration(lutuConfig);

			final BigDecimal qtyToAllocate = receiptScheduleBL.getQtyOrdered(receiptSchedule);

			huGenerator
					.setQtyToAllocateTarget(Quantity.of(qtyToAllocate, loadOutOfTrx(receiptSchedule.getC_UOM_ID(), I_C_UOM.class)))
					.generateWithinInheritedTransaction();
		}
		catch (final DBForeignKeyConstraintException e)
		{
			// task 09016: this case happens from time to time (aprox. 90 times in the first 6 months), if the M_ReceiptsSchedule is deleted due to an order reactivation
			// don't rethrow the exception;
			final String msg = "Detected a FK constraint violation; We assume that everything was rolled back, but we do not let the processing fail. Check the java comments for details";
			Loggables.withLogger(logger, Level.WARN).addLog(msg);
		}
	}

	private Set<HuId> retrieveAllocatedHuIds(@NonNull final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		return receiptSchedules.stream()
				.map(receiptSchedule -> huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_ThreadInherited))
				.flatMap(Collection::stream)
				.flatMap(allocation -> Stream.of(allocation.getVHU_ID(), allocation.getM_TU_HU_ID(), allocation.getM_LU_HU_ID()))
				.filter(huId -> huId > 0)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void adjustPlanningHUStorageFromNetWeight(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = huAttributesBL.getM_HU_OrNull(attributeSet);

		if (hu == null)
		{
			return;
		}

		if (!X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
		{
			return;
		}

		final HUReceiptScheduleWeightNetAdjuster huWeightNetAdjuster = new HUReceiptScheduleWeightNetAdjuster(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		huWeightNetAdjuster.setInScopeHU_IDs(org.elasticsearch.common.collect.Set.of(HuId.ofRepoId(hu.getM_HU_ID())));

		final List<I_M_ReceiptSchedule> receiptSchedules = huAssignmentDAO.retrieveModelsForHU(hu, I_M_ReceiptSchedule.class);

		for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			// Adjust HU's product storages to their Weight Net Attribute
			huWeightNetAdjuster.addReceiptSchedule(receiptSchedule);
		}
	}

}
