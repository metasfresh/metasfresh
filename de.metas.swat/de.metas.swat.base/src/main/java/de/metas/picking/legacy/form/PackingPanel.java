package de.metas.picking.legacy.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.IClientUI;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.process.IADPInstanceDAO;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Packing View
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public abstract class PackingPanel extends MvcGenForm
{
	private static final Logger logger = LogManager.getLogger(PackingPanel.class);

	private final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Properties ctx;

	/**
	 * AD_PInstance_ID to be used in shipment schedules locking/unlocking
	 */
	private final int adPInstanceId;

	public PackingPanel()
	{
		this.ctx = Env.getCtx();
		this.adPInstanceId = Services.get(IADPInstanceDAO.class).createAD_PInstance_ID(ctx);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PackingMd getModel()
	{
		return super.getModel();
	}

	protected abstract void executePacking(List<OlAndSched> olsAndScheds);

	public void dynInit() throws Exception
	{

		getModel().setTitle("KommissionierungsInfo");
		getModel().setReportEngineType(ReportEngine.SHIPMENT);
		getModel().setAskPrintMsg("PrintShipments");

		final WarehouseId loginWarehouse = WarehouseId.ofRepoIdOrNull(Env.getContextAsInt(ctx, Env.CTXNAME_M_Warehouse_ID));
		if (loginWarehouse != null)
		{
			getModel().setWarehouseId(loginWarehouse);

			executeQuery();
		}
	}

	/**
	 * Display given error to user
	 *
	 * @param e
	 */
	protected void displayError(final Throwable e)
	{
		Services.get(IClientUI.class).warn(getModel().getWindowNo(), e);
	}

	@Override
	public final void executeQuery()
	{
		final PackingMd model = getModel();
		try
		{
			model.reload();
		}
		catch (final Exception ex)
		{
			displayError(ex);
		}

		updateMiniTable();
	}

	public final IMiniTable getMiniTable()
	{
		final PackingMd model = getModel();
		final IMiniTable miniTable = model.getMiniTable();
		return miniTable;
	}

	protected void updateMiniTable()
	{
		final PackingMd model = getModel();
		final IMiniTable miniTable = getMiniTable();

		final Set<TableRowKey> selectedTableRowKeys = model.getSelectedTableRowKeys();

		int rowIdx = 0;
		final List<TableRowKey> keys = model.getTableRowKeys();
		miniTable.setRowCount(keys.size());
		for (final TableRowKey key : keys)
		{
			// TODO: refactor and use de.metas.adempiere.form.PackingMd.getAggregatedTableRows()
			BigDecimal sumQtyToDeliver = BigDecimal.ZERO;

			final Collection<TableRow> tableRowsForKey = model.getTableRowsForKey(key);
			for (final TableRow singleRow : tableRowsForKey)
			{
				// sum only if QtyToDeliver>0, other lines are only for user information (if isDisplayNonDeliverableItems is set)
				if (singleRow.getQtyToDeliver().signum() > 0)
				{
					sumQtyToDeliver = sumQtyToDeliver.add(singleRow.getQtyToDeliver());
				}
			}

			// g one representative row for the current key
			final TableRow currentRow = tableRowsForKey.iterator().next();

			String bpartnerAddress = currentRow.getKey().getBpartnerAddress();
			if (bpartnerAddress != null)
			{
				bpartnerAddress = bpartnerAddress.replace('\n', ' ');
			}

			// set values
			miniTable.setValueAt(new IDColumn(key.hashCode()), rowIdx, 0); //
			miniTable.setValueAt(currentRow.getBpartnerValue(), rowIdx, 1); // Value
			miniTable.setValueAt(currentRow.getBpartnerName(), rowIdx, 2); // Name
			miniTable.setValueAt(bpartnerAddress, rowIdx, 3); // "Lieferadresse"
			miniTable.setValueAt(currentRow.getDeliveryVia(), rowIdx, 4);
			miniTable.setValueAt(currentRow.getShipper(), rowIdx, 5);
			miniTable.setValueAt(currentRow.getWarehouseName(), rowIdx, 6); // M_Warehouse_ID
			miniTable.setValueAt(sumQtyToDeliver, rowIdx, 7); // QtyToDeliver

			// prepare next
			rowIdx++;
		}
		miniTable.setRowCount(rowIdx);
		model.setSelectedTableRowKeys(selectedTableRowKeys);
		miniTable.autoSize();
	}

	public final void createPackingDetails()
	{
		final RowIndexes rows = getSelectedRows();
		if (rows.isEmpty())
		{
			logger.warn("createPackingDetails: No rows");
			return;
		}

		final PackingMd model = getModel();
		if (model.getWarehouseId() == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@");
		}

		final List<OlAndSched> olsAndScheds = getOlAndCands(rows);
		if (olsAndScheds.isEmpty())
		{
			logger.warn("createPackingDetails: No lines to pick for rows={}", rows);
			return;
		}

		try
		{
			executePacking(olsAndScheds);
		}
		catch (final Throwable ex)
		{
			unlockShipmentSchedules();
			throw AdempiereException.wrapIfNeeded(ex);
		}

		// related to https://github.com/metasfresh/metasfresh/issues/456
		// invokeProcess(detailsModel);
	}

	private List<OlAndSched> getOlAndCands(@NonNull final RowIndexes rows)
	{
		if (rows.isEmpty())
		{
			return ImmutableList.of();
		}

		final PackingMd model = getModel();
		final Set<ShipmentScheduleId> shipmentScheduleIds = model.getScheduleIdsForRow(rows);
		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_ShipmentSchedule_ID@");
		}

		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulesRepo.getByIdsOutOfTrx(shipmentScheduleIds);
		final List<OlAndSched> olsAndScheds = new ArrayList<>();
		for (final I_M_ShipmentSchedule sched : shipmentSchedules.values())
		{
			final BigDecimal qtyToDeliver = shipmentScheduleEffectiveBL.getQtyToDeliver(sched);
			if (qtyToDeliver.signum() <= 0)
			{
				continue;
			}

			final OrderAndLineId salesOrderLineId = OrderAndLineId.ofRepoIdsOrNull(sched.getC_Order_ID(), sched.getC_OrderLine_ID());
			final I_C_OrderLine salesOrderLine = salesOrderLineId != null ? ordersRepo.getOrderLineById(salesOrderLineId) : null;

			final OlAndSched olAndSched = new OlAndSched(salesOrderLine, sched);
			olsAndScheds.add(olAndSched);
		}

		return olsAndScheds;
	}

	/**
	 * Unlock all shipment schedules which were locked in {@link #createPackingDetails(Properties, int)}
	 */
	protected final void unlockShipmentSchedules()
	{
		final int adUserId = Env.getAD_User_ID(ctx);

		trxManager.run((TrxRunnable)localTrxName -> {
			final boolean updateOnlyLocked = true;

			shipmentScheduleUpdater.updateShipmentSchedule(
					ctx,
					adUserId,
					adPInstanceId,
					updateOnlyLocked,
					localTrxName);
		});
	}

	protected abstract RowIndexes getSelectedRows();

	@Override
	public abstract void initModel(MvcMdGenForm model);
}
