package org.adempiere.mm.attributes.api.impl;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;

import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.fresh.base
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

final class UpdateASIAttributeFromModelCommand
{
	private final transient IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL;

	private final AttributeCode attributeCode;
	private final Object sourceModel;

	@Builder
	private UpdateASIAttributeFromModelCommand(
			@NonNull final IAttributeSetInstanceBL attributeSetInstanceBL,
			//
			@NonNull final AttributeCode attributeCode,
			@NonNull final Object sourceModel)
	{
		this.attributeSetInstanceBL = attributeSetInstanceBL;

		this.attributeCode = attributeCode;
		this.sourceModel = sourceModel;
	}

	public static class UpdateASIAttributeFromModelCommandBuilder
	{
		public void execute()
		{
			build().execute();
		}
	}

	public void execute()
	{
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(sourceModel);
		if (asiAware == null)
		{
			return;
		}

		if (asiAware.getM_Product_ID() <= 0)
		{
			return;
		}

		final AttributeId attributeId = attributeDAO.retrieveAttributeIdByValueOrNull(attributeCode);

		if (attributeId == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(asiAware.getM_Product_ID());
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(productId, attributeId);
		if (attribute == null)
		{
			return;
		}

		attributeSetInstanceBL.getCreateASI(asiAware);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNull(asiAware.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asiId, attributeId);

		if (ai != null)
		{
			// If it was set, just leave it as it is
			return;
		}

		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeId);
	}
}
