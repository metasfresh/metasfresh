package de.metas.handlingunits.client.terminal.inventory.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Transaction;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.flatrate.interfaces.I_C_BPartner;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;

/**
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryHUEditorModel extends HUEditorModel
{
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private final I_M_Warehouse _warehouse;

	public InventoryHUEditorModel(final ITerminalContext terminalContext, final int warehouseId)
	{
		super(terminalContext);

		_warehouse = InterfaceWrapperHelper.create(terminalContext.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(_warehouse, "warehouse not null");
	}

	public I_M_Warehouse getM_Warehouse()
	{
		return _warehouse;
	}

	/**
	 * Move selected HUs to the warehouse for direct movements
	 *
	 * @task http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
	 */
	public I_M_Movement doDirectMoveToWarehouse()
	{
		//
		// Warehouse from: we are moving from this warehouse
		final I_M_Warehouse warehouseFrom = getM_Warehouse(); // shall not be null

		//
		// Warehouse to: we are moving the warehouses for direct movements
		final boolean exceptionIfNull = false;
		final I_M_Warehouse warehouseTo = huMovementBL.getDirectMove_Warehouse(getTerminalContext().getCtx(), exceptionIfNull);
		Check.assumeNotNull(warehouseTo, "warehouseTo not null"); // shall not happen, because if it's null the action button shall be hidden

		final I_M_Movement movement = doDirectMoveToWarehouse(warehouseFrom, warehouseTo);

		return movement;
	}

	public I_M_Movement moveToQualityWarehouse()
	{

		final List<de.metas.handlingunits.model.I_M_Warehouse> qualityWarehouses = Services.get(IHUWarehouseDAO.class).retrieveQualityReturnWarehouse(getCtx());

		if (Check.isEmpty(qualityWarehouses))
		{
			// TODO: Define message
			throw new TerminalException("@NoQualityReturnsWarehouse@");
		}

		// Move from the selected warehouse
		final I_M_Warehouse warehouseFrom = getM_Warehouse(); // shall not be null

		// TODO: Decide how to select the right quality warehouse
		final I_M_Warehouse warehouseTo = qualityWarehouses.get(0);

		return doDirectMoveToWarehouse(warehouseFrom, warehouseTo);
	}

	public I_M_InOut createVendorReturn()
	{
		// services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Get selected HUs
		final Set<HUKey> huKeys = new HashSet<>(getSelectedHUKeys());
		for (final Iterator<HUKey> huKeysIterator = huKeys.iterator(); huKeysIterator.hasNext();)
		{
			final HUKey huKey = huKeysIterator.next();
			final I_M_HU hu = huKey.getM_HU();

			// Exclude pure virtual HUs (i.e. those HUs which are linked to material HU Items)
			if (handlingUnitsBL.isPureVirtual(hu))
			{
				huKeysIterator.remove();
				continue;
			}
		}
		if (Check.isEmpty(huKeys))
		{
			throw new TerminalException("@NoSelection@");
		}

		final List<I_M_HU> hus = new ArrayList<I_M_HU>();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();
			hus.add(hu);
		}

		return createReturnInOut(hus);
	}

	/**
	 * Create return inouts for products of precarious quality
	 * 
	 * @param hus
	 * @return
	 */
	private I_M_InOut createReturnInOut(final List<I_M_HU> hus)
	{

		// services
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final Map<Integer, List<I_M_HU>> partnerstoHUs = new HashMap<>();

		// inoutline table id
		final int inOutLineTableId = InterfaceWrapperHelper.getTableId(I_M_InOutLine.class);

		for (final I_M_HU hu : hus)
		{

			final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

			final List<I_M_HU_Assignment> inOutLineHUAssignments = huAssignmentDAO.retrieveTableHUAssignments(ctxAware, inOutLineTableId, hu);

			for (final I_M_HU_Assignment assignment : inOutLineHUAssignments)
			{
				final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(ctxAware.getCtx(), assignment.getRecord_ID(), I_M_InOutLine.class, ITrx.TRXNAME_None);

				final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();

				final int bpartnerID = inOut.getC_BPartner_ID();

				partnerstoHUs.put(bpartnerID, Arrays.asList(hu));
			}
		}

		// there will be as many return inouts as there are partners

		Set<Integer> keySet = partnerstoHUs.keySet();

		for (final int partnerId : keySet)
		{
			createInOut(partnerId, partnerstoHUs.get(partnerId));
		}

		return null;
	}

	private final org.compiere.model.I_M_InOut createInOut(final int partnerId, List<I_M_HU> hus)
	{
		final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), partnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
		final IReturnsInOutProducer producer = huInOutBL.createQualityReturnsInOutProducer(getCtx(), hus);
		producer.setC_BPartner(partner);

		final I_C_BPartner_Location shipToLocation = bpartnerDAO.retrieveShipToLocation(getCtx(), partnerId, ITrx.TRXNAME_None);
		producer.setC_BPartner_Location(shipToLocation);

		final String movementType = X_M_Transaction.MOVEMENTTYPE_VendorReturns;

		producer.setMovementType(movementType);
		producer.setM_Warehouse(getM_Warehouse());

		final Timestamp movementDate = Env.getDate(getTerminalContext().getCtx()); // use Login date

		producer.setMovementDate(movementDate);

//		if (producer.isEmpty())
//		{
//			throw new AdempiereException("@NoSelection@");
//		}

		//
		// Create Shipment document and return it
		final org.compiere.model.I_M_InOut inOut = producer.create();
		return inOut;
	}

	public I_M_Movement doDirectMoveToWarehouse(final I_M_Warehouse warehouseFrom, final I_M_Warehouse warehouseTo)
	{
		// services
		final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Get selected HUs
		final Set<HUKey> huKeys = new HashSet<>(getSelectedHUKeys());
		for (final Iterator<HUKey> huKeysIterator = huKeys.iterator(); huKeysIterator.hasNext();)
		{
			final HUKey huKey = huKeysIterator.next();
			final I_M_HU hu = huKey.getM_HU();

			// Exclude pure virtual HUs (i.e. those HUs which are linked to material HU Items)
			if (handlingUnitsBL.isPureVirtual(hu))
			{
				huKeysIterator.remove();
				continue;
			}
		}
		if (Check.isEmpty(huKeys))
		{
			throw new TerminalException("@NoSelection@");
		}

		//
		// create our list of HUs to pass to the API service
		final List<I_M_HU> hus = new ArrayList<I_M_HU>();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();
			hus.add(hu);

			// guard: verify the the HU's current warehouse matches the selected warehouseFrom
			if (warehouseFrom != null)
			{
				final int huWarehouseID = hu.getM_Locator().getM_Warehouse_ID();
				Check.errorUnless(huWarehouseID == warehouseFrom.getM_Warehouse_ID(),
						"The selected HU {} has a M_Locator {} with M_Warehouse_ID {} which is != the M_Warehouse_ID {} of warehouse {}",
						hu, hu.getM_Locator(), huWarehouseID, warehouseFrom.getM_Warehouse_ID(), warehouseFrom);
			}
		}

		// make the movement-creating API call
		final List<I_M_Movement> movements = huMovementBL.generateMovementsToWarehouse(warehouseTo, hus, getTerminalContext());
		Check.assume(movements.size() <= 1, "We called the API with HUs {} that are all in the same warehouse {}, so there is just one movement created", hus, warehouseFrom);

		if (movements.isEmpty())
		{
			throw new TerminalException("@NotCreated@ @M_Movement_ID@");
		}
		final I_M_Movement movement = movements.get(0);

		//
		// Refresh the HUKeys
		{
			// Remove huKeys from their parents
			for (final HUKey huKey : huKeys)
			{
				removeHUKeyFromParentRecursivelly(huKey);
			}

			// Move back to Root HU Key
			setRootHUKey(getRootHUKey());

			//
			// Clear (attribute) cache (because it could be that we changed the attributes too)
			clearCache();
		}

		return movement;
	}

	private final void removeHUKeyFromParentRecursivelly(final IHUKey huKey)
	{
		if (huKey == null)
		{
			return;
		}

		final IHUKey parentKey = huKey.getParent();
		if (parentKey == null)
		{
			return;
		}

		// Remove child from it's parent
		parentKey.removeChild(huKey);

		// If after removing, the parent become empty then remove it from it's parent... and so on
		if (parentKey.getChildren().isEmpty())
		{
			removeHUKeyFromParentRecursivelly(parentKey);
		}
		else
		{
			// Update parent's name recursivelly up to the top
			// FIXME: this is a workaround because even though the name is updated, it's not udpated recursivelly up to the top
			IHUKey p = parentKey;
			while (p != null)
			{
				p.updateName();
				p = p.getParent();
			}

		}
	}
}
