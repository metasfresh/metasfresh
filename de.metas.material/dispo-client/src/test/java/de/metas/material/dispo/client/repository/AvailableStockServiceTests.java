package de.metas.material.dispo.client.repository;

import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.client.repository.AvailableStockResult.Group;
import de.metas.material.dispo.client.repository.AvailableStockResult.Group.Type;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.event.commons.StorageAttributesKey;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
 * #L%t
 */

public class AvailableStockServiceTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private AttributesTestHelper attributesTestHelper;

	private AvailableStockService availableStockService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		final StockRepository stockRepository = new StockRepository();

		attributesTestHelper = new AttributesTestHelper();

		availableStockService = new AvailableStockService(stockRepository);
	}

	@Test
	public void extractAttributeSetFromStorageAttributesKey()
	{
		final I_M_Attribute attr1 = attributesTestHelper.createM_Attribute("attr1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		final I_M_AttributeValue attributeValue1 = attributesTestHelper.createM_AttributeValue(attr1, "value1");

		final I_M_Attribute attr2 = attributesTestHelper.createM_Attribute("attr2", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		final I_M_AttributeValue attributeValue2 = attributesTestHelper.createM_AttributeValue(attr2, "value2");

		// invoke the method under test
		final StorageAttributesKey storageAttributesKey = StorageAttributesKey.ofAttributeValueIds(attributeValue1.getM_AttributeValue_ID(), attributeValue2.getM_AttributeValue_ID());
		final List<I_M_AttributeValue> result = availableStockService.extractAttributeSetFromStorageAttributesKey(storageAttributesKey);

		assertThat(result).hasSize(2);

		assertThat(result).anySatisfy(attributeValue -> {
			assertThatModel(attributeValue).hasSameIdAs(attributeValue1);
			assertThat(attributeValue.getValue()).isEqualTo("value1");
		});

		assertThat(result).anySatisfy(attributeValue -> {
			assertThatModel(attributeValue).hasSameIdAs(attributeValue2);
			assertThat(attributeValue.getValue()).isEqualTo("value2");
		});
	}

	@Test
	public void extractType_all()
	{
		final Group.Type type = availableStockService.extractType(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_ALL);
		assertThat(type).isSameAs(Type.ALL_STORAGE_KEYS);
	}

	@Test
	public void extractType_other()
	{
		final Group.Type type = availableStockService.extractType(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER);
		assertThat(type).isSameAs(Type.OTHER_STORAGE_KEYS);
	}

	@Test
	public void extractType_attributeSet()
	{
		final StorageAttributesKey storageAttributesKey = StorageAttributesKey.ofAttributeValueIds(12345);
		final Group.Type type = availableStockService.extractType(storageAttributesKey);
		assertThat(type).isSameAs(Type.ATTRIBUTE_SET);
	}

}
