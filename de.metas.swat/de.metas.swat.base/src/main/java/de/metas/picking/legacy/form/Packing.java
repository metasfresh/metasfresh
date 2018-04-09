package de.metas.picking.legacy.form;

/*
 * #%L
 * de.metas.swat.base
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

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_DeliveryViaRule;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_Name;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_Value;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Shipper_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Warehouse_ID;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.I_M_PackagingTree;
import org.compiere.model.PackingTreeBL;
import org.compiere.model.X_C_Order;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.exception.NoContainerException;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.service.IPackagingBL;
import de.metas.i18n.Msg;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.terminal.Utils;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.product.IStoragePA;
import de.metas.quantity.Quantity;

/**
 * Packing View
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public abstract class Packing extends MvcGenForm
{
	protected final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);

	private final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final String MSG_DOING_PACKAGING = "Verarbeite Kommsionierung";
	private static final String MSG_SHIP_TO_ADDRESS = "Lieferanschrift";

	public static final String PROP_M_WAREHOUSE_ID = "M_Warehouse_ID";

	public static final String PROP_INFO_TEXT = "infoText";

	private final Properties ctx;

	/**
	 * AD_PInstance_ID to be used in shipment schedules locking/unlocking
	 */
	private final int adPInstanceId;

	public Packing()
	{
		super();

		this.ctx = Env.getCtx();
		this.adPInstanceId = Services.get(IADPInstanceDAO.class).createAD_PInstance_ID(ctx);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PackingMd getModel()
	{
		return super.getModel();
	}

	protected abstract void executePacking(IPackingDetailsModel detailsModel);

	@Override
	public void saveSelection()
	{
		final PackingMd model = getModel();
		model.setSelection(model.getSelectedScheduleIds());
	}

	public void dynInit() throws Exception
	{

		getModel().setTitle("KommissionierungsInfo");
		getModel().setReportEngineType(ReportEngine.SHIPMENT);
		getModel().setAskPrintMsg("PrintShipments");

		final int loginWarehouse = Env.getContextAsInt(ctx, "#M_Warehouse_ID");
		if (loginWarehouse > 0)
		{
			getModel().setM_Warehouse_ID(loginWarehouse);

			final IPackingView view = (IPackingView)getView();
			view.setSelectedWarehouseId(loginWarehouse);
			executeQuery();
			view.focusOnNextOneButton();
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

		final boolean displayNonItems = Services.get(IPackagingBL.class).isDisplayNonItemsEnabled(Env.getCtx());

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
				// System.out.println("key="+hashCode+", qtyToDeliver: "+singleRow.getQtyToDeliver());
				if (displayNonItems || singleRow.isDisplayed())
				{
					// sum only if QtyToDeliver>0, other lines are only for user information (if isDisplayNonDeliverableItems is set)
					if (singleRow.getQtyToDeliver().signum() > 0)
					{
						sumQtyToDeliver = sumQtyToDeliver.add(singleRow.getQtyToDeliver());
					}
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

	@Override
	public void configureMiniTable(final IMiniTable miniTable)
	{

		// create Columns
		miniTable.addColumn(COLUMNNAME_C_BPartner_Location_ID); // ID
		miniTable.addColumn(COLUMNNAME_Value);
		miniTable.addColumn(COLUMNNAME_Name);
		miniTable.addColumn(COLUMNNAME_C_BPartner_Location_ID);
		miniTable.addColumn(COLUMNNAME_DeliveryViaRule);
		miniTable.addColumn(COLUMNNAME_M_Shipper_ID);
		miniTable.addColumn(COLUMNNAME_M_Warehouse_ID);
		miniTable.addColumn(COLUMNNAME_QtyToDeliver);
		miniTable.addColumn(X_C_Order.COLUMNNAME_FreightCostRule); // 02891
		miniTable.setMultiSelection(false);

		// set details
		miniTable.setColumnClass(0, IDColumn.class, false, " ");
		miniTable.setColumnClass(1, String.class, true, Msg.translate(ctx, COLUMNNAME_Value));
		miniTable.setColumnClass(2, String.class, true, COLUMNNAME_Name);
		miniTable.setColumnClass(3, String.class, true, MSG_SHIP_TO_ADDRESS);
		miniTable.setColumnClass(4, String.class, true, Msg.translate(ctx, COLUMNNAME_DeliveryViaRule));

		miniTable.setColumnClass(5, String.class, true, Msg.translate(ctx, COLUMNNAME_M_Shipper_ID));
		miniTable.setColumnClass(6, String.class, true, Msg.translate(ctx, COLUMNNAME_M_Warehouse_ID));
		miniTable.setColumnClass(7, BigDecimal.class, true, Msg.translate(ctx, COLUMNNAME_QtyToDeliver));
		miniTable.setColumnClass(8, String.class, true, Msg.translate(ctx, COLUMNNAME_DeliveryViaRule));

		//
		miniTable.autoSize();
	}

	public final void createPackingDetails(final Properties ctx, final int[] rows)
	{
		if (rows == null || rows.length == 0)
		{
			return;
		}

		final PackingMd model = getModel();
		final Collection<Integer> shipmentScheduleIds = model.getScheduleIdsForRow(rows);

		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_ShipmentSchedule_ID@");
		}
		if (model.getM_Warehouse_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@");
		}

		final List<OlAndSched> olsAndScheds = new ArrayList<>();
		for (final int id : shipmentScheduleIds)
		{
			final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.create(ctx, id, I_M_ShipmentSchedule.class, ITrx.TRXNAME_None);
			if (sched == null)
			{
				continue;
			}

			final BigDecimal qtyToDeliver = Services.get(IShipmentScheduleEffectiveBL.class).getQtyToDeliver(sched);
			if (qtyToDeliver.signum() <= 0)
			{
				continue;
			}
			final OlAndSched olAndSched = new OlAndSched(sched.getC_OrderLine(), sched);
			olsAndScheds.add(olAndSched);
		}

		if (olsAndScheds.isEmpty())
		{
			return;
		}

		final boolean displayNonItems = Services.get(IPackagingBL.class).isDisplayNonItemsEnabled(ctx);

		final IPackingDetailsModel detailsModel;
		try
		{
			// prepare our "problem" description
			final Collection<IPackingItem> unallocatedLines = createUnallocatedLines(olsAndScheds, displayNonItems);

			final List<I_M_ShipmentSchedule> nonItemScheds = new ArrayList<>();

			for (final OlAndSched oldAndSched : olsAndScheds)
			{
				final I_M_ShipmentSchedule sched = oldAndSched.getSched();
				if (!(sched.isDisplayed() || displayNonItems))
				{
					nonItemScheds.add(sched);
				}
			}

			detailsModel = createPackingDetailsModel(ctx, rows, unallocatedLines, nonItemScheds);
			}
		catch (final Throwable t)
		{
			unlockShipmentSchedules();
			throw new AdempiereException(t.getLocalizedMessage(), t);
		}

		// related to https://github.com/metasfresh/metasfresh/issues/456
		// invokeProcess(detailsModel);
	}

	protected Collection<IPackingItem> createUnallocatedLines(final List<OlAndSched> olsAndScheds, boolean displayNonItems)
	{
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final Collection<IPackingItem> unallocatedLines = new ArrayList<>();

		final Map<ArrayKey, IPackingItem> key2Sched = new HashMap<>();

		for (final OlAndSched oldAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule sched = oldAndSched.getSched();
			if (sched.isDisplayed() || displayNonItems)
			{
				final BigDecimal qtyToDeliver = shipmentScheduleEffectiveBL.getQtyToDeliver(sched);

				final Map<I_M_ShipmentSchedule, Quantity> schedWithQty = Collections.singletonMap(
						sched,
						Quantity.of(
								qtyToDeliver,
								shipmentScheduleBL.getUomOfProduct(sched)));

				// #100 FRESH-435: in FreshPackingItem we rely on all scheds having the same effective C_BPartner_Location_ID, so we need to include that in the key
				final boolean includeBPartner = true;
				final ArrayKey key = shipmentScheduleBL.mkKeyForGrouping(sched, includeBPartner);

				IPackingItem item = key2Sched.get(key);
				if (item == null)
				{
					item = new LegacyPackingItem(schedWithQty, null);
					assert item.getGroupingKey() == key.hashCode();

					key2Sched.put(key, item);
					unallocatedLines.add(item);
				}
				else
				{
					item.addSchedules(schedWithQty);
				}
			}
		}

		return unallocatedLines;
	}

	protected IPackingDetailsModel createPackingDetailsModel(
			final Properties ctx,
			final int[] rows,
			final Collection<IPackingItem> unallocatedLines,
			final List<I_M_ShipmentSchedule> nonItemScheds)
	{
		final PackingMd model = getModel();

		final IStoragePA storagePA = Services.get(IStoragePA.class);

		final Collection<AvailableBins> containers = new ArrayList<>();

		final int warehouseId = model.getM_Warehouse_ID();
		final List<I_M_PackagingContainer> pcs = packagingDAO.retrieveContainers(warehouseId, ITrx.TRXNAME_None);

		if (pcs.isEmpty())
		{
			throw new NoContainerException(warehouseId, false, false);
		}

		for (final I_M_PackagingContainer pc : pcs)
		{
			final BigDecimal qtyAvail = storagePA.retrieveQtyAvailable(
					warehouseId,  // M_Warehouse_ID
					0,  // M_Locator_ID
					pc.getM_Product_ID(),
					0,  // M_AttributeSetInstance_ID
					ITrx.TRXNAME_None);

			final AvailableBins bin = new AvailableBins(ctx, pc, qtyAvail.intValue(), null);
			containers.add(bin);
		}

		BigDecimal qty = Env.ZERO;
		for (final IPackingItem item : unallocatedLines)
		{
			qty = qty.add(Utils.convertToItemUOM(item, item.getQtySum()));
		}
		//
		final int bpId = model.getBPartnerIdForRow(rows[0]); // in this case is only one row selected
		final int C_BPartner_Location_ID = model.getBPartnerLocationIdForRow(rows[0]); // in this case is only one row selected
		final int warehouseDestId = model.getWarehouseDestIdForRow(rows[0]); // in this case is only one row selected
		final I_M_PackagingTree savedTree = PackingTreeBL.getPackingTree(bpId, warehouseDestId, qty);
		//
		final PackingDetailsMd detailsModel;
		if (savedTree != null)
		{
			detailsModel = new PackingDetailsMd(ctx, savedTree, model.isRowUsesShipper(rows[0]), C_BPartner_Location_ID, warehouseDestId, false); // in this case is only one row selected
		}
		else
		{
			// No matched tree for given Qty found, so it's better to recreate it again
			detailsModel = new PackingDetailsMd(unallocatedLines, containers, model.isRowUsesShipper(rows[0]), nonItemScheds, bpId, C_BPartner_Location_ID, warehouseDestId, false); // in this case is only one row selected
		}

		final int shipperId = model.getShipperIdForRow(rows[0]);
		if (shipperId > 0)
		{
			detailsModel.selectedShipperId = shipperId;
		}

		final IBinPacker binPacker = new BinPacker();
		detailsModel.packer = binPacker;

		//
		// create a suggested solution
		final PackingTreeModel packingTreeModel = detailsModel.getPackingTreeModel();
		packingTreeModel.setBp_id(bpId);

		if (savedTree == null)
		{
			// Tree was not found or not matched the Qty, so we do the packing again
			binPacker.pack(ctx, packingTreeModel, ITrx.TRXNAME_None);
		}

		executePacking(detailsModel);

		return detailsModel;
	}

	/**
	 * Unlock all shipment schedules which were locked in {@link #createPackingDetails(Properties, int)}
	 */
	public void unlockShipmentSchedules()
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

	private void invokeProcess(final IPackingDetailsModel model)
	{
		// TODO: drop it - https://github.com/metasfresh/metasfresh/issues/456
		// NOTE assume this is not called
		throw new UnsupportedOperationException();

		// final int processId = processPA.retrieveProcessId(PerformPackaging.class, ITrx.TRXNAME_None);
		//
		// final ProcessInfo pi = new ProcessInfo(MSG_DOING_PACKAGING, processId);
		// pi.setTitle("Kommisionierungsbelege");
		// pi.setWindowNo(getModel().getWindowNo());
		// pi.setParameter(new ProcessInfoParameter[] {
		// new ProcessInfoParameter("selection", model.getPackingTreeModel().getUsedBins(), null, "pipSelectection", null) //
		// , new ProcessInfoParameter("shipper", model.getSelectedShipper(), null, "pipShipper", null) //
		// , new ProcessInfoParameter("nonItems", model.getNonItems(), null, "pipNonItems", null) //
		// });
		//
		// return ProcessCtl.builder()
		// .setAsyncParent(this)
		// .setProcessInfo(pi)
		// .execute();
	}

	@Override
	public void unlockUI(final ProcessInfo pi)
	{
		super.unlockUI(pi);

		final ProcessExecutionResult result = pi.getResult();
		final StringBuilder iText = new StringBuilder();
		iText.append("<b>") //
				.append(result.getSummary()) //
				.append("</b><br>(") //
				.append(Msg.getMsg(ctx, "Belegerstellung")) //
				.append(")<br>") //
				.append(result.getLogInfo(true));

		getView().modelPropertyChange(new PropertyChangeEvent(this, PROP_INFO_TEXT, null, iText.toString()));
	}

	@Override
	public abstract void initModel(MvcMdGenForm model);
}
