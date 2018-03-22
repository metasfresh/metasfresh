package de.metas.handlingunits.ddorder.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnableAdapter;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.service.IWarehouseDAO;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer.HUToDistribute;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.i18n.IMsgBL;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
import lombok.NonNull;
import lombok.Value;

public class HUDDOrderBL implements IHUDDOrderBL
{
	private static final String ERR_M_Warehouse_NoQuarantineWarehouse = "M_Warehouse_NoQuarantineWarehouse";

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public DDOrderLinesAllocator createMovements()
	{
		return DDOrderLinesAllocator.newInstance();
	}

	@Override
	public void closeLine(final I_DD_OrderLine ddOrderLine)
	{
		ddOrderLine.setIsDelivered_Override(X_DD_OrderLine.ISDELIVERED_OVERRIDE_Yes);
		InterfaceWrapperHelper.save(ddOrderLine);

		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.clearHUsScheduledToMoveList(ddOrderLine);
	}

	@Override
	public void unassignHUs(final I_DD_OrderLine ddOrderLine, final Collection<I_M_HU> hus)
	{
		//
		// Unassign the given HUs from DD_OrderLine
		final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentBL.unassignHUs(ddOrderLine, hus);

		//
		// Remove those HUs from scheduled to move list (task 08639)
		final IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
		huDDOrderDAO.removeFromHUsScheduledToMoveList(ddOrderLine, hus);
	}

	@Override
	public void createQuarantineDDOrderForReceiptLines(final List<QuarantineInOutLine> receiptLines)
	{

		final List<HUToDistribute> husToQuarantine = new ArrayList<>();

		receiptLines.stream()
				.map(receiptLine -> createHUsToQuarantine(receiptLine))
				.collect(ImmutableList.toImmutableList())
				.stream()
				.forEach(hus -> husToQuarantine.addAll(hus));

		createQuarantineDDOrderForHUs(husToQuarantine);

	}

	private List<HUToDistribute> createHUsToQuarantine(final QuarantineInOutLine receiptLine)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		return huAssignmentDAO.retrieveTopLevelHUsForModel(receiptLine.getInOutLine())
				.stream()
				.map(hu -> HUToDistribute.of(hu, receiptLine.getLockLotNo()))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public void createQuarantineDDOrderForHUs(final List<HUToDistribute> husToDistribute)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final I_M_Warehouse quarantineWarehouse = warehouseDAO.retrieveQuarantineWarehouseOrNull();

		if (quarantineWarehouse == null)
		{
			throw new AdempiereException(msgBL.getMsg(Env.getCtx(), ERR_M_Warehouse_NoQuarantineWarehouse));
		}

		// Make sure this runs out of trx because there is a safety check in HUs2DDOrderProducer.process() about it being so.
		// Please, check de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer.process() for more details.
		trxManager.runOutOfTransaction(createQuarantineDDOrder(husToDistribute, quarantineWarehouse));
	}

	private TrxRunnable createQuarantineDDOrder(final List<HUToDistribute> hus, final I_M_Warehouse quarantineWarehouse)
	{
		final TrxRunnable trxRunnable = new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				HUs2DDOrderProducer.newProducer()
						.setContext(Env.getCtx())
						.setM_Warehouse_To(create(quarantineWarehouse, de.metas.handlingunits.model.I_M_Warehouse.class))
						.setHUs(hus.iterator())
						.process();
			}
		};

		return trxRunnable;
	}

	@Value
	public static final class QuarantineInOutLine
	{
		@NonNull
		I_M_InOutLine inOutLine;

		@NonNull
		I_M_Product_LotNumber_Lock lockLotNo;
	}
}
