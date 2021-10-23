package de.metas.ddorder.movement.generate;

import de.metas.util.Services;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.HashMap;

class DDOrdersCache
{
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

	private final HashMap<DDOrderId, I_DD_Order> ddOrders = new HashMap<>();
	private final HashMap<DDOrderLineId, I_DD_OrderLine> ddOrderLines = new HashMap<>();

	public I_DD_Order getById(final DDOrderId ddOrderId)
	{
		return ddOrders.computeIfAbsent(ddOrderId, ddOrderDAO::getById);
	}

	public I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineId)
	{
		return ddOrderLines.computeIfAbsent(ddOrderLineId, ddOrderDAO::getLineById);
	}
}
