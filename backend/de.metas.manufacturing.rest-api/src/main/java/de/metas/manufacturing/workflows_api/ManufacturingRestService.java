package de.metas.manufacturing.workflows_api;

import de.metas.dao.ValueRestriction;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ManufacturingRestService
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ManufacturingJobLoaderSupportingServices loadingSupportServices;

	public ManufacturingRestService()
	{
		this.loadingSupportServices = ManufacturingJobLoaderSupportingServices.builder()
				.ppOrderDAO(ppOrderDAO)
				.ppOrderRoutingRepository(Services.get(IPPOrderRoutingRepository.class))
				.build();
	}

	public Stream<PPOrderReference> streamActiveReferencesAssignedTo(@NonNull final UserId responsibleId)
	{
		return ppOrderDAO.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.build())
				.map(this::toPPOrderReference);
	}

	public Stream<PPOrderReference> streamActiveReferencesNotAssigned()
	{
		return ppOrderDAO.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.isNull())
						.build())
				.map(this::toPPOrderReference);
	}

	private PPOrderReference toPPOrderReference(final I_PP_Order ppOrder)
	{
		return PPOrderReference.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.documentNo(ppOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(orgDAO::getTimeZone))
				.productName(productBL.getProductNameTrl(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
				.qtyRequiredToProduce(ppOrderBOMBL.getQuantities(ppOrder).getQtyRequiredToProduce())
				.build();
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		setResponsible(ppOrder, responsibleId);

		return newLoader()
				.addToCache(ppOrder)
				.load(ppOrderId);
	}

	private void setResponsible(@NonNull final I_PP_Order ppOrder, @NonNull final UserId responsibleId)
	{
		final UserId previousResponsibleId = UserId.ofRepoIdOrNullIfSystem(ppOrder.getAD_User_Responsible_ID());
		if (UserId.equals(previousResponsibleId, responsibleId))
		{
			//noinspection UnnecessaryReturnStatement
			return;
		}
		else if (previousResponsibleId != null)
		{
			throw new AdempiereException("Order is already assigned");
		}
		else
		{
			ppOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			ppOrderDAO.save(ppOrder);
		}
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId)
	{
		return newLoader().load(ppOrderId);
	}

	@NonNull
	private ManufacturingJobLoader newLoader()
	{
		return new ManufacturingJobLoader(loadingSupportServices);
	}
}
