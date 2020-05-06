package de.metas.handlingunits.ddorder.api.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.api.impl.WarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_OrderLine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.ddorder.api.QuarantineInOutLine;
import de.metas.handlingunits.ddorder.api.impl.HUs2DDOrderProducer.HUToDistribute;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class HUDDOrderBL implements IHUDDOrderBL
{

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
	public List<I_DD_Order> createQuarantineDDOrderForReceiptLines(final List<QuarantineInOutLine> receiptLines)
	{

		final List<HUToDistribute> husToQuarantine = receiptLines.stream()
				.flatMap(receiptLine -> createHUsToQuarantine(receiptLine).stream())
				.collect(ImmutableList.toImmutableList());

		return createQuarantineDDOrderForHUs(husToQuarantine);

	}

	private List<HUToDistribute> createHUsToQuarantine(final QuarantineInOutLine receiptLine)
	{
		final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
		return huInOutDAO.retrieveHUsForReceiptLineId(receiptLine.getReceiptLineId())
				.stream()
				.map(hu -> HUToDistribute.builder()
						.hu(hu)
						.quarantineLotNo(receiptLine.getLotNumberQuarantine())
						.bpartnerId(receiptLine.getBpartnerId())
						.bpartnerLocationId(receiptLine.getBpartnerLocationId())
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<I_DD_Order> createQuarantineDDOrderForHUs(final List<HUToDistribute> husToDistribute)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

		final I_M_Warehouse quarantineWarehouse = warehouseDAO.retrieveQuarantineWarehouseOrNull();
		if (quarantineWarehouse == null)
		{
			throw new AdempiereException("@" + WarehouseDAO.MSG_M_Warehouse_NoQuarantineWarehouse + "@");
		}

		final I_M_Locator defaultLocator = warehouseBL.getDefaultLocator(quarantineWarehouse);

		final ImmutableSet<Entry<BPartnerAndLocationId, Collection<HUToDistribute>>> entries = husToDistribute
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(this::extractBPartnerAndLocationId, Function.identity()))
				.asMap()
				.entrySet();

		final ImmutableList.Builder<I_DD_Order> result = ImmutableList.builder();
		for (final Entry<BPartnerAndLocationId, Collection<HUToDistribute>> entry : entries)
		{
			final Optional<I_DD_Order> ddOrder = createDDOrder(entry.getKey(), defaultLocator, entry.getValue());
			ddOrder.ifPresent(result::add);
		}
		return result.build();
	}

	private Optional<I_DD_Order> createDDOrder(
			@NonNull final BPartnerAndLocationId bpartnerAndLocationId,
			@NonNull final I_M_Locator locatorTo,
			@NonNull final Collection<HUToDistribute> hus)
	{
		return HUs2DDOrderProducer.newProducer()
				.setContext(Env.getCtx())
				.setM_Locator_To(locatorTo)
				.setBpartnerId(bpartnerAndLocationId.getBpartnerId())
				.setBpartnerLocationId(bpartnerAndLocationId.getBpartnerLocationId())
				.setHUs(hus.iterator())
				.process();
	}

	private BPartnerAndLocationId extractBPartnerAndLocationId(final HUToDistribute huToDistribute)
	{
		return BPartnerAndLocationId.builder()
				.bpartnerId(huToDistribute.getBpartnerId())
				.bpartnerLocationId(huToDistribute.getBpartnerLocationId())
				.build();
	}

	@Value
	@Builder
	private static class BPartnerAndLocationId
	{
		int bpartnerId;
		int bpartnerLocationId;
	}
}
