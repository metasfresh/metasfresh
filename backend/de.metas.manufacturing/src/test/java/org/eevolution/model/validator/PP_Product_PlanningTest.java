package org.eevolution.model.validator;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PP_Product_PlanningTest
{
	private AttributesTestHelper attributesTestHelper;
	private IAttributeSetInstanceBL attributeSetInstanceBL;
	private IProductPlanningDAO productPlanningDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.attributesTestHelper = new AttributesTestHelper();
		this.attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		this.productPlanningDAO = Services.get(IProductPlanningDAO.class);

		//
		// Install the interceptor under test
		final ProductBOMVersionsDAO versionsDAO = new ProductBOMVersionsDAO();
		final PP_Product_Planning interceptor = new PP_Product_Planning(versionsDAO, new ProductBOMService(versionsDAO));
		POJOLookupMap.get().addModelValidator(interceptor);
	}

	private AttributeListValue createAttributeValue(String attributeName)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(attributeName, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true); // IMPORTANT
		save(attribute);
		return attributesTestHelper.createM_AttributeValue(attribute, attributeName + "-value1");
	}

	@NonNull
	private AttributeSetInstanceId createASI(final AttributeListValue attributeValue1, final AttributeListValue attributeValue2)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeValue1);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeValue2);

		return asiId;
	}

	@Test
	public void updateStorageAttributesKey()
	{
		final AttributeListValue attributeValue1 = createAttributeValue("test1");
		final AttributeListValue attributeValue2 = createAttributeValue("test2");
		final AttributeSetInstanceId asiId = createASI(attributeValue1, attributeValue2);

		final ProductPlanning productPlanning = productPlanningDAO.save(ProductPlanning.builder()
				.attributeSetInstanceId(asiId)
				.build());

		assertThat(productPlanning.getStorageAttributesKey())
				.isEqualTo(AttributesKey.ofAttributeValueIds(attributeValue1.getId(), attributeValue2.getId()).getAsString());
	}
}
