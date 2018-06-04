package org.adempiere.mm.attributes.api.impl;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

/*
 * #%L
 * de.metas.fresh.base
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

public class LotNumberAttributeUpdater
{
	private final transient IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private Object sourceModel;
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);


	public void updateASI()
	{
		final Object sourceModel = getSourceModel();

		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(sourceModel);
		if (asiAware == null)
		{
			return;
		}

		if (asiAware.getM_Product_ID() <= 0)
		{
			return;
		}

		final I_M_Attribute lotNoAttribute = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttribute(Env.getCtx());
		
		if(lotNoAttribute == null)
		{
			return;
		}
		
		final int attributeId  = lotNoAttribute.getM_Attribute_ID();

		final I_M_Product product = asiAware.getM_Product();
		Check.assumeNotNull(product, "Product is not null");
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(product, attributeId);
		
		if (attribute == null)
		{
			return;
		}

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.getCreateASI(asiAware);

		final I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asi, attributeId);
	
		if (ai != null)
		{
			// If it was set, just leave it as it is
			return;
		}

		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeId);
	}

	public LotNumberAttributeUpdater setSourceModel(final Object sourceModel)
	{
		this.sourceModel = sourceModel;
		return this;
	}

	private final Object getSourceModel()
	{
		Check.assumeNotNull(sourceModel, "sourceModel not null");
		return sourceModel;
	}
}
