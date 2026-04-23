package de.metas.ui.web.shipment.process;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.material.MovementType;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOut;

/**
 * Process to generate a vendor return document from a completed material receipt.
 * <p>
 * Creates a new vendor return (M_InOut with MovementType=V-) in Draft status,
 * copying all lines from the receipt. The user can then adjust quantities
 * and delete lines before completing the return.
 * <p>
 * Analogous to {@link M_InOut_GenerateCustomerReturn} but for the purchase side.
 */
public class M_InOut_GenerateVendorReturn extends JavaProcess implements IProcessPrecondition
{
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final InOutId receiptId = context.getSingleSelectedRecordId(InOutId.class);
		final I_M_InOut receipt = inoutBL.getById(receiptId);

		final MovementType movementType = MovementType.ofCode(receipt.getMovementType());
		if (movementType != MovementType.VendorReceipts)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not a vendor receipt");
		}

		final DocStatus docStatus = DocStatus.ofCode(receipt.getDocStatus());
		if (!docStatus.isCompletedOrClosed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Receipt is not completed or closed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_M_InOut receipt = inoutBL.getById(getReceiptId());

		final MovementType movementType = MovementType.ofCode(receipt.getMovementType());
		if (movementType != MovementType.VendorReceipts)
		{
			throw new AdempiereException("Not a vendor receipt");
		}

		final DocStatus docStatus = DocStatus.ofCode(receipt.getDocStatus());
		if (!docStatus.isCompletedOrClosed())
		{
			throw new AdempiereException("Receipt is not completed or closed");
		}

		// Find the vendor return doc type: DocBaseType=MMS (Shipment), isSOTrx=false, DocSubType=NONE
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.Shipment)
				.docSubType(DocSubType.NONE)
				.isSOTrx(false)
				.adClientId(receipt.getAD_Client_ID())
				.adOrgId(receipt.getAD_Org_ID())
				.build());

		// Use the same warehouse as the receipt (not the quality return warehouse)
		final WarehouseId warehouseId = WarehouseId.ofRepoId(receipt.getM_Warehouse_ID());
		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);

		// Create vendor return header by copying the receipt
		final I_M_InOut vendorReturn = InterfaceWrapperHelper.copy()
				.setSkipCalculatedColumns(true)
				.setFrom(receipt)
				.copyToNew(I_M_InOut.class);
		vendorReturn.setC_DocType_ID(docTypeId.getRepoId());
		vendorReturn.setIsSOTrx(false);
		vendorReturn.setMovementType(MovementType.VendorReturns.getCode());
		vendorReturn.setReturn_Origin_InOut_ID(receipt.getM_InOut_ID());
		vendorReturn.setMovementDate(SystemTime.asTimestamp());
		vendorReturn.setDateAcct(SystemTime.asTimestamp());
		vendorReturn.setM_Warehouse_ID(warehouseId.getRepoId());
		InterfaceWrapperHelper.save(vendorReturn);

		// Copy all lines (including packing material lines) from the receipt
		for (final org.compiere.model.I_M_InOutLine receiptLine : inoutBL.getLines(receipt))
		{
			final I_M_InOutLine returnLine = InterfaceWrapperHelper.copy()
					.setSkipCalculatedColumns(true)
					.setFrom(receiptLine)
					.copyToNew(I_M_InOutLine.class);
			returnLine.setM_InOut_ID(vendorReturn.getM_InOut_ID());
			returnLine.setReturn_Origin_InOutLine_ID(receiptLine.getM_InOutLine_ID());
			returnLine.setM_Locator_ID(locatorId.getRepoId());
			returnLine.setC_OrderLine_ID(OrderLineId.toRepoId(null));
			InterfaceWrapperHelper.save(returnLine);
		}

		// Open the created vendor return — let the framework resolve the correct window via zoom targets
		getResult().setRecordToOpen(RecordsToOpen.builder()
				.record(TableRecordReference.of(vendorReturn))
				.target(OpenTarget.SingleDocument)
				.build());

		return MSG_OK;
	}

	private InOutId getReceiptId()
	{
		return InOutId.ofRepoId(getRecord_ID());
	}
}
