package org.adempiere.mm.attributes.api.impl;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.asi_aware.factory.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.I_M_AttributeInstance;

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

@Builder
final class UpdateASIAttributeFromModelCommand
{
	@NonNull private final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService;
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final IAttributeSetInstanceBL attributeSetInstanceBL;

	@NonNull private final AttributeCode attributeCode;
	@NonNull private final Object sourceModel;

	@SuppressWarnings("unused")
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

		final ProductId productId = ProductId.ofRepoIdOrNull(asiAware.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final AttributeId attributeId = attributeDAO.retrieveActiveAttributeIdByValueOrNull(attributeCode);
		if (attributeId == null)
		{
			return;
		}

		attributeSetInstanceBL.getCreateASI(asiAware);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNull(asiAware.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance ai = attributeSetInstanceBL.getAttributeInstance(asiId, attributeId);
		if (ai != null)
		{
			// If it was set, just leave it as it is
			return;
		}

		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeId);
	}
}
