package de.metas.material.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.model.I_AD_Client;
import org.springframework.stereotype.Component;

import de.metas.inoutcandidate.spi.ShipmentScheduleReferencedLineFactory;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class Main extends AbstractModelInterceptor
{
	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final PostMaterialEventService postMaterialEventService;
	private final ShipmentScheduleReferencedLineFactory referencedLineFactory;

	public Main(
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final ShipmentScheduleReferencedLineFactory referencedLineFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
		this.postMaterialEventService = postMaterialEventService;
		this.referencedLineFactory = referencedLineFactory;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new M_Forecast(productDescriptorFactory, postMaterialEventService), client);
		engine.addModelValidator(new M_ShipmentSchedule(postMaterialEventService, referencedLineFactory, productDescriptorFactory), client);
	}

}
