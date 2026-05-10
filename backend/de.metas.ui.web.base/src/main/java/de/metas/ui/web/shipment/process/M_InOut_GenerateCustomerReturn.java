package de.metas.ui.web.shipment.process;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.material.MovementType;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
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

public class M_InOut_GenerateCustomerReturn extends JavaProcess implements IProcessPrecondition
{
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Param(parameterName = "M_Warehouse_ID", mandatory = false)
	private int p_M_Warehouse_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final InOutId shipmentId = context.getSingleSelectedRecordId(InOutId.class);
		final I_M_InOut shipment = inoutBL.getById(shipmentId);
		final MovementType movementType = MovementType.ofCode(shipment.getMovementType());
		if (movementType != MovementType.CustomerShipment)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not a customer shipment");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_M_InOut shipment = inoutBL.getById(getShipmentId());
		final MovementType movementType = MovementType.ofCode(shipment.getMovementType());
		if (movementType != MovementType.CustomerShipment)
		{
			throw new AdempiereException("Not a customer shipment");
		}

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.MaterialReceipt)
				.isSOTrx(true)
				.adClientId(shipment.getAD_Client_ID())
				.adOrgId(shipment.getAD_Org_ID())
				.build());

		final WarehouseId warehouseIdOverride = WarehouseId.ofRepoIdOrNull(p_M_Warehouse_ID);
		final WarehouseId warehouseId = warehouseIdOverride != null
				? warehouseIdOverride
				: huWarehouseDAO.retrieveFirstQualityReturnWarehouseId();
		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);

		final I_M_InOut customerReturn = InterfaceWrapperHelper.copy()
				.setSkipCalculatedColumns(true)
				.setFrom(shipment)
				.copyToNew(I_M_InOut.class);
		customerReturn.setC_DocType_ID(docTypeId.getRepoId());
		customerReturn.setIsSOTrx(true);
		customerReturn.setMovementType(MovementType.CustomerReturns.getCode());
		customerReturn.setReturn_Origin_InOut_ID(shipment.getM_InOut_ID());
		customerReturn.setMovementDate(SystemTime.asTimestamp());
		customerReturn.setDateAcct(SystemTime.asTimestamp());
		customerReturn.setM_Warehouse_ID(warehouseId.getRepoId());
		InterfaceWrapperHelper.save(customerReturn);

		for (final org.compiere.model.I_M_InOutLine shipmentLine : inoutBL.getLines(shipment))
		{
			final I_M_InOutLine returnLine = InterfaceWrapperHelper.copy()
					.setSkipCalculatedColumns(true)
					.setFrom(shipmentLine)
					.copyToNew(I_M_InOutLine.class);
			returnLine.setM_InOut_ID(customerReturn.getM_InOut_ID());
			returnLine.setReturn_Origin_InOutLine_ID(shipmentLine.getM_InOutLine_ID());
			returnLine.setM_Locator_ID(locatorId.getRepoId());
			returnLine.setC_OrderLine_ID(OrderLineId.toRepoId(null));
			InterfaceWrapperHelper.save(returnLine);
		}

		getResult().setRecordToOpen(RecordsToOpen.builder()
				.record(TableRecordReference.of(customerReturn))
				.target(OpenTarget.SingleDocument)
				.build());

		return MSG_OK;
	}

	private InOutId getShipmentId()
	{
		return InOutId.ofRepoId(getRecord_ID());
	}

}
