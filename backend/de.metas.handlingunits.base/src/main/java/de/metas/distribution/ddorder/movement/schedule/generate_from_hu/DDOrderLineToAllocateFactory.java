package de.metas.distribution.ddorder.movement.schedule.generate_from_hu;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseBL;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine_Or_Alternative;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

class DDOrderLineToAllocateFactory
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final DDOrderService ddOrderService;

	DDOrderLineToAllocateFactory(
			@NonNull final DDOrderService ddOrderService)
	{
		this.ddOrderService = ddOrderService;
	}

	public ImmutableList<DDOrderLineToAllocate> ofDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		return ofDDOrderLines(ImmutableList.of(ddOrderLine));
	}

	public ImmutableList<DDOrderLineToAllocate> ofDDOrderLines(@NonNull final Collection<I_DD_OrderLine> ddOrderLines)
	{
		Check.assumeNotEmpty(ddOrderLines, "ddOrderLines not empty");

		// Sort distribution order lines by date promised (lowest date first)
		final ArrayList<I_DD_OrderLine> ddOrderLinesSorted = new ArrayList<>(ddOrderLines);
		ddOrderLinesSorted.sort(Comparator.comparing(I_DD_OrderLine::getDatePromised));

		// Create list with alternatives; keep the sorting
		final List<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt = extractDD_OrderLine_Or_Alternatives(ddOrderLinesSorted);
		return ofDDOrderLinesOrAlternatives(ddOrderLinesOrAlt);
	}

	private List<I_DD_OrderLine_Or_Alternative> extractDD_OrderLine_Or_Alternatives(final Collection<I_DD_OrderLine> ddOrderLines)
	{
		// Create list with alternatives; preserve the same sorting
		final List<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt = new ArrayList<>();
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			final I_DD_OrderLine_Or_Alternative ddOrderLineConv = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.add(ddOrderLineConv);

			final List<I_DD_OrderLine_Alternative> alternatives = ddOrderService.retrieveAllAlternatives(ddOrderLine);
			final List<I_DD_OrderLine_Or_Alternative> alternativesConv = InterfaceWrapperHelper.createList(alternatives, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.addAll(alternativesConv);
		}

		return ddOrderLinesOrAlt;
	}

	private ImmutableList<DDOrderLineToAllocate> ofDDOrderLinesOrAlternatives(final Collection<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt)
	{
		Check.assumeNotEmpty(ddOrderLinesOrAlt, "ddOrderLinesOrAlt not empty");
		return ddOrderLinesOrAlt.stream()
				.map(this::toDDOrderLineToAllocate)
				.collect(ImmutableList.toImmutableList());
	}

	private DDOrderLineToAllocate toDDOrderLineToAllocate(@NonNull final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		final I_DD_OrderLine ddOrderLine;
		final int ddOrderLineAlternativeId;
		final AttributeSetInstanceId fromAsiId;
		final AttributeSetInstanceId toAsiId;
		if (InterfaceWrapperHelper.isInstanceOf(ddOrderLineOrAlt, I_DD_OrderLine.class))
		{
			ddOrderLineAlternativeId = -1;
			ddOrderLine = InterfaceWrapperHelper.create(ddOrderLineOrAlt, I_DD_OrderLine.class);
			fromAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ddOrderLine.getM_AttributeSetInstance_ID());
			toAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ddOrderLine.getM_AttributeSetInstanceTo_ID());
		}
		else if (InterfaceWrapperHelper.isInstanceOf(ddOrderLineOrAlt, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(ddOrderLineOrAlt, I_DD_OrderLine_Alternative.class);
			ddOrderLineAlternativeId = ddOrderLineAlt.getDD_OrderLine_Alternative_ID();
			ddOrderLine = ddOrderLineAlt.getDD_OrderLine();
			fromAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ddOrderLineAlt.getM_AttributeSetInstance_ID());
			toAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ddOrderLine.getM_AttributeSetInstanceTo_ID());
		}
		else
		{
			throw new AdempiereException("Invalid I_DD_OrderLine_Or_Alternative implementation passed; Expected " + I_DD_OrderLine.class + " or " + I_DD_OrderLine_Alternative.COLUMNNAME_AD_Client_ID + ", but was " + ddOrderLineOrAlt);
		}

		return DDOrderLineToAllocate.builder()
				.uomConversionBL(uomConversionBL)
				//
				.ddOrderId(DDOrderId.ofRepoId(ddOrderLine.getDD_Order_ID()))
				.ddOrderLineId(DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID()))
				.ddOrderLineAlternativeId(ddOrderLineAlternativeId)
				.description(ddOrderLine.getDescription())
				.pickFromLocatorId(warehouseBL.getLocatorIdByRepoId(ddOrderLine.getM_Locator_ID()))
				.dropToLocatorId(warehouseBL.getLocatorIdByRepoId(ddOrderLine.getM_LocatorTo_ID()))
				.productId(ProductId.ofRepoId(ddOrderLineOrAlt.getM_Product_ID()))
				.fromAsiId(fromAsiId)
				.toAsiId(toAsiId)
				.qtyToShip(ddOrderService.getQtyToShip(ddOrderLineOrAlt))
				.build();
	}

}
