package de.metas.frontend_testing.masterdata.sales_order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.order.OrderFactory;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;

public class SalesOrderCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final JsonSalesOrderCreateRequest request;
	@NonNull private final MasterdataContext context;

	private OrderFactory salesOrderFactory;

	@Builder
	private SalesOrderCreateCommand(
			@NonNull final JsonSalesOrderCreateRequest request,
			@NonNull final MasterdataContext context)
	{
		this.request = request;
		this.context = context;
	}

	public JsonSalesOrderCreateResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonSalesOrderCreateResponse execute0()
	{
		final BPartnerLocationId shipBPartnerLocationId = request.getLocation() != null ? context.getId(request.getLocation(), BPartnerLocationId.class) : null;
		final BPartnerId shipBPartnerId = CoalesceUtil.coalesceSuppliers(
				() -> request.getBpartner() != null ? context.getId(request.getBpartner(), BPartnerId.class) : null,
				() -> shipBPartnerLocationId != null ? shipBPartnerLocationId.getBpartnerId() : null
		);
		if (shipBPartnerId == null)
		{
			throw new AdempiereException("At least one of bpartner or location shall be set");
		}
		if (shipBPartnerLocationId != null && !BPartnerId.equals(shipBPartnerId, shipBPartnerLocationId.getBpartnerId()))
		{
			throw new AdempiereException("Partner and location shall match")
					.setParameter("shipBPartnerId", shipBPartnerId)
					.setParameter("shipBPartnerLocationId", shipBPartnerLocationId)
					.setParameter("request", request);
		}

		this.salesOrderFactory = OrderFactory.newSalesOrder()
				.shipBPartner(shipBPartnerId, shipBPartnerLocationId, null)
				.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
				.datePromised(request.getDatePromised());

		request.getLines().forEach(this::createOrderLine);

		final I_C_Order salesOrderRecord = salesOrderFactory.createAndComplete();

		return JsonSalesOrderCreateResponse.builder()
				.id(String.valueOf(salesOrderRecord.getC_Order_ID()))
				.documentNo(salesOrderRecord.getDocumentNo())
				.build();
	}

	private void createOrderLine(final JsonSalesOrderCreateRequest.Line salesOrderLine)
	{
		final ProductId productId = context.getId(salesOrderLine.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qty = Quantity.of(salesOrderLine.getQty(), uom);
		final HUPIItemProductId piItemProductId = salesOrderLine.getPiItemProduct() != null
				? context.getId(salesOrderLine.getPiItemProduct(), HUPIItemProductId.class)
				: null;

		salesOrderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty)
				.piItemProductId(piItemProductId);
	}

}
