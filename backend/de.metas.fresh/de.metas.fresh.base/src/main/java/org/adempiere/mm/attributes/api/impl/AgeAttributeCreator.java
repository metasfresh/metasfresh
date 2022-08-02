package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * metasfresh-pharma
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

import de.metas.handlingunits.age.AgeAttributesService;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;

public class AgeAttributeCreator
{
	private final transient IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final transient AgeAttributesService ageAttributesService = Adempiere.getBean(AgeAttributesService.class);

	private final Object sourceModel;

	public AgeAttributeCreator(final @NonNull Object sourceModel)
	{
		this.sourceModel = sourceModel;
	}

	/**
	 * Note: this explanation may not be 100% correct
	 * ASI = AttributeSetInstance
	 * AttributeSet is the dataType
	 * AttributeSetInstance is the instance (a new object) of an AttributeSet
	 */
	@SuppressWarnings({ "Duplicates" })
	public void createASI()
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

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final AttributeId ageAttributeId = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_Age);

		if (ageAttributeId == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(asiAware.getM_Product_ID());
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(productId, ageAttributeId);
		if (attribute == null)
		{
			return;
		}

		attributeSetInstanceBL.getCreateASI(asiAware);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asiAware.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asiId, ageAttributeId);

		if (ai != null)
		{
			// If it was set, just leave it as it is
			return;
		}

		attributeSetInstanceBL.getCreateAttributeInstance(asiId, ageAttributeId);
		attributeSetInstanceBL.setAttributeInstanceValue(asiId, ageAttributeId, ageAttributesService.computeDefaultAgeOrNull());
	}

	private @NonNull Object getSourceModel()
	{
		Check.assumeNotNull(sourceModel, "sourceModel not null");
		return sourceModel;
	}

}
