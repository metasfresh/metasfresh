package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceService;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The two stateless label resolvers as beans: all their services are constructor parameters; the ones that are no Spring beans
 * ({@code Services.get}) are resolved here once and handed in.
 */
@Configuration
class CreateFromLabelConfiguration
{
	@Bean
	LabelProductResolver labelProductResolver(final HUQRCodesService huQRCodesService)
	{
		return new LabelProductResolver(huQRCodesService, Services.get(IProductBL.class));
	}

	@Bean
	LabelTargetLocatorResolver labelTargetLocatorResolver(final WorkplaceService workplaceService)
	{
		return new LabelTargetLocatorResolver(workplaceService, Services.get(IWarehouseDAO.class), Services.get(IOrgDAO.class));
	}
}
