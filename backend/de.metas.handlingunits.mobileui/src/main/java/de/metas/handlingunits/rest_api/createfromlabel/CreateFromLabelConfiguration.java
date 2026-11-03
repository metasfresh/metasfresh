package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The stateless label resolvers and the label quantity / weight service as beans: all their services are constructor parameters; the ones that are no Spring beans
 * ({@code Services.get}) are resolved here once and handed in.
 */
@Configuration
class CreateFromLabelConfiguration
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Bean
	LabelProductResolver labelProductResolver(@NonNull final HUQRCodesService huQRCodesService)
	{
		return new LabelProductResolver(huQRCodesService, productBL);
	}

	@Bean
	LabelTargetLocatorResolver labelTargetLocatorResolver(@NonNull final WorkplaceService workplaceService)
	{
		return new LabelTargetLocatorResolver(workplaceService, warehouseDAO, orgDAO);
	}

	@Bean
	LabelQtyAndWeightService labelQtyAndWeightService()
	{
		return new LabelQtyAndWeightService(productBL, uomDAO);
	}
}
