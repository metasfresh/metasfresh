package de.metas.distribution.workflows_api;

import de.metas.common.util.pair.ImmutablePair;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import java.util.Optional;

public class DistributionLauncherCaptionProvider
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	public WorkflowLauncherCaption compute(@NonNull final DDOrderReference ddOrderReference)
	{
		final TranslatableStringBuilder captionBuilder = TranslatableStrings.builder();
		getSourceDocumentTypeAndNo(ddOrderReference)
				.ifPresent(sourceDocTypeAndNo -> captionBuilder
						.append(sourceDocTypeAndNo.getLeft())
						.append(" ")
						.append(sourceDocTypeAndNo.getRight())
						.append(" | "));

		captionBuilder
				.append(warehouseBL.getWarehouseName(ddOrderReference.getFromWarehouseId()))
				.append(" > ")
				.append(warehouseBL.getWarehouseName(ddOrderReference.getToWarehouseId()))
				.append(" | ")
				.appendDateTime(ddOrderReference.getDisplayDate().toZonedDateTime(orgDAO::getTimeZone));

		if (ddOrderReference.getProductId() != null)
		{
			captionBuilder.append(" | ").append(productBL.getProductValueAndName(ddOrderReference.getProductId()));
		}

		if (ddOrderReference.getQty() != null)
		{
			captionBuilder.append(" | ").appendQty(ddOrderReference.getQty().toBigDecimal(), ddOrderReference.getQty().getUOMSymbol());
		}

		return WorkflowLauncherCaption.of(captionBuilder.build());
	}

	@NonNull
	private Optional<ImmutablePair<ITranslatableString, String>> getSourceDocumentTypeAndNo(@NonNull final DDOrderReference ddOrderReference)
	{
		final ITranslatableString docTypeName;
		final String documentNo;
		if (ddOrderReference.getSalesOrderId() != null)
		{
			final I_C_Order order = orderBL.getById(ddOrderReference.getSalesOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
			documentNo = order.getDocumentNo();
		}
		else if (ddOrderReference.getPpOrderId() != null)
		{
			final I_PP_Order ppOrder = ppOrderBL.getById(ddOrderReference.getPpOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()));
			documentNo = ppOrder.getDocumentNo();
		}
		else
		{
			return Optional.empty();
		}

		return Optional.of(ImmutablePair.of(docTypeName, documentNo));
	}

}
