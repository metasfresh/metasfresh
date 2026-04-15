package de.metas.frontend_testing.masterdata.purchase_order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.frontend_testing.masterdata.MasterdataContext;
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

@Builder
public class PurchaseOrderCreateCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final JsonPurchaseOrderCreateRequest request;
	@NonNull private final MasterdataContext context;

	//
	// State
	private transient OrderFactory purchaseOrderFactory;

	public JsonPurchaseOrderCreateResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonPurchaseOrderCreateResponse execute0()
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

		this.purchaseOrderFactory = OrderFactory.newPurchaseOrder()
				.shipBPartner(shipBPartnerId, shipBPartnerLocationId, null)
				.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
				.datePromised(request.getDatePromised());
		request.getLines().forEach(this::createOrderLine);

		final I_C_Order purchaseOrderRecord = purchaseOrderFactory.createAndComplete();

		return JsonPurchaseOrderCreateResponse.builder()
				.id(String.valueOf(purchaseOrderRecord.getC_Order_ID()))
				.documentNo(purchaseOrderRecord.getDocumentNo())
				.build();
	}

	private void createOrderLine(final JsonPurchaseOrderCreateRequest.Line line)
	{
		final ProductId productId = context.getId(line.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qty = Quantity.of(line.getQty(), uom);

		purchaseOrderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty);
	}
}
