package de.metas.material.cockpit.view;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * metasfresh-material-cockpit
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

public class MainDataRecordIdentifierTest
{
	private I_MD_Cockpit cockpitRecord;
	private I_MD_Cockpit someUnrelatedcockpitRecord;
	private ProductDescriptor productDescriptor;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		productDescriptor = createProductDescriptor();

		cockpitRecord = newInstance(I_MD_Cockpit.class);
		cockpitRecord.setM_Product_ID(productDescriptor.getProductId());
		cockpitRecord.setAttributesKey(productDescriptor.getStorageAttributesKey().getAsString());
		cockpitRecord.setDateGeneral(new Timestamp(NOW.getTime()));
		save(cockpitRecord);

		someUnrelatedcockpitRecord = newInstance(I_MD_Cockpit.class);
		someUnrelatedcockpitRecord.setM_Product_ID(productDescriptor.getProductId() + 10);
		someUnrelatedcockpitRecord.setAttributesKey(productDescriptor.getStorageAttributesKey().getAsString());
		someUnrelatedcockpitRecord.setDateGeneral(new Timestamp(NOW.getTime()));
		save(someUnrelatedcockpitRecord);
	}

	@Test
	public void createQueryBuilder()
	{
		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier.builder()
				.date(NOW)
				.productDescriptor(productDescriptor).build();

		// invoke the method under test
		final List<I_MD_Cockpit> retrievedCockpitRecords = mainDataRecordIdentifier.createQueryBuilder().create().list();

		assertThat(retrievedCockpitRecords).hasSize(1);
		assertThatModel(retrievedCockpitRecords.get(0)).hasSameIdAs(cockpitRecord);
	}

}
