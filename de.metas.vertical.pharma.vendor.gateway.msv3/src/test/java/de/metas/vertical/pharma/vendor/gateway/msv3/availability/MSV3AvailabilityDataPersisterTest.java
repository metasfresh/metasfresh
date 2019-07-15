package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.AvailabilityType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public class MSV3AvailabilityDataPersisterTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void storeAvailabilityResponse()
	{
		final StockAvailabilityResponse response = StockAvailabilityResponse.builder()
				.id("response.id")
				.availabilityType(AvailabilityType.SPECIFIC)
				.item(StockAvailabilityResponseItem.builder()
						.pzn(PZN.of(123))
						.qty(Quantity.of(3))
						.build())
				.build();

		final MSV3AvailabilityDataPersister availabilityDataPersister = MSV3AvailabilityDataPersister.createNewInstance(OrgId.ofRepoId(30));

		// invoke the method under test
		availabilityDataPersister.storeAvailabilityResponse(
				response,
				ImmutableMap.of());

		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort responseRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class).create().first();
		assertThat(responseRecord).isNotNull();
		assertThat(responseRecord.getMSV3_Id()).isEqualTo("response.id");
		assertThat(responseRecord.getMSV3_VerfuegbarkeitTyp()).isEqualTo(X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.MSV3_VERFUEGBARKEITTYP_Spezifisch);
	}

}
