package de.metas.distribution.service.external;

import de.metas.common.util.pair.ImmutablePair;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

@Service
public class DistributionSourceDocService
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	public String getPlantName(@NonNull final ResourceId plantId)
	{
		return ppOrderBL.getResourceName(plantId);
	}

	public ImmutablePair<ITranslatableString, String> getDocumentTypeAndName(@NonNull OrderId salesOrderId)
	{
		final I_C_Order order = orderBL.getById(salesOrderId);
		final ITranslatableString docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
		final String documentNo = order.getDocumentNo();
		return ImmutablePair.of(docTypeName, documentNo);
	}

	public ImmutablePair<ITranslatableString, String> getDocumentTypeAndName(@NonNull PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final ITranslatableString docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()));
		final String documentNo = ppOrder.getDocumentNo();
		return ImmutablePair.of(docTypeName, documentNo);
	}
}
