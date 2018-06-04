package de.metas.ordercandidate.rest;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.Services;
import org.compiere.util.Ini;
import org.springframework.stereotype.Component;

import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.InputDataSourceCreateRequest;
import de.metas.ordercandidate.OrderCandidate_Constants;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class OrderCandidateRestModuleInit extends AbstractModuleInterceptor
{
	public OrderCandidateRestModuleInit()
	{

	}

	@Override
	protected void onAfterInit()
	{
		if (!Ini.isClient())
		{
			ensureDataSourceExists();
		}
	}

	private void ensureDataSourceExists()
	{
		Services.get(IInputDataSourceDAO.class).createIfMissing(InputDataSourceCreateRequest.builder()
				.entityType(OrderCandidate_Constants.ENTITY_TYPE)
				.internalName(OrderCandidatesRestControllerImpl.DATA_SOURCE_INTERNAL_NAME)
				.destination(false)
				.build());
	}

}
