package de.metas.material.dispo.client.repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.DB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.business.BusinessTestHelper;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.StockRepository;
import mockit.Mocked;

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
 * #L%
 */

public class AvailableStockServiceTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private StockRepository stockRepository;

	@Mocked
	private DB db;

	private I_M_Product product;

	private RepositoryTestHelper repositoryTestHelper;

	private AttributesTestHelper attributesTestHelper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		stockRepository = new StockRepository();

		final I_C_UOM uom = BusinessTestHelper.createUOM("uom");
		product = BusinessTestHelper.createProduct("product", uom);

		final CandidateRepositoryCommands candidateRepositoryCommands = new CandidateRepositoryCommands();
		repositoryTestHelper = new RepositoryTestHelper(candidateRepositoryCommands);

		attributesTestHelper = new AttributesTestHelper();
	}

	@Test
	public void createStorageAttributeKeysForDimensionSpec()
	{
		final I_DIM_Dimension_Spec dimensionSpec = newInstance(I_DIM_Dimension_Spec.class);
		save(dimensionSpec);

		final I_DIM_Dimension_Spec_Attribute dsa = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dsa.setDIM_Dimension_Spec(dimensionSpec);
		dsa.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_List);

		final I_M_Attribute attr1 = attributesTestHelper.createM_Attribute("attr1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attributesTestHelper.createM_AttributeValue(attr1, "value");
		dsa.setM_Attribute(attr1);
		save(dsa);

		final AvailableStockService availableStockService = new AvailableStockService(stockRepository);

		final Collection<String> storageKeys = availableStockService.extractStorageAttributeKeysForDimensionSpec(dimensionSpec.getDIM_Dimension_Spec_ID());
		assertThat(storageKeys).containsOnly("", "value");
	}

}
