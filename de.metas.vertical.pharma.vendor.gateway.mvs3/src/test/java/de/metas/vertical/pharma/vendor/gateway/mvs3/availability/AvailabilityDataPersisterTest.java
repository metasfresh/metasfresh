package de.metas.vertical.pharma.vendor.gateway.mvs3.availability;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitTyp;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

public class AvailabilityDataPersisterTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void storeAvailabilityResponse()
	{
		final ObjectFactory objectFactory = new ObjectFactory();

		final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort = objectFactory.createVerfuegbarkeitsanfrageEinzelneAntwort();
		verfuegbarkeitsanfrageEinzelneAntwort.setId("verfuegbarkeitsanfrageEinzelneAntwort.id");
		verfuegbarkeitsanfrageEinzelneAntwort.setRTyp(VerfuegbarkeitTyp.SPEZIFISCH);

		final MSV3AvailabilityDataPersister availabilityDataPersister = MSV3AvailabilityDataPersister.createNewInstance(30);

		// invoke the method under test
		availabilityDataPersister.storeAvailabilityResponse(
				verfuegbarkeitsanfrageEinzelneAntwort,
				ImmutableMap.of());

		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class).create().first();
		assertThat(verfuegbarkeitsanfrageEinzelneAntwortRecord).isNotNull();
		assertThat(verfuegbarkeitsanfrageEinzelneAntwortRecord.getMSV3_Id())
				.isEqualTo("verfuegbarkeitsanfrageEinzelneAntwort.id");
		assertThat(verfuegbarkeitsanfrageEinzelneAntwortRecord.getMSV3_VerfuegbarkeitTyp())
				.isEqualTo(X_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.MSV3_VERFUEGBARKEITTYP_Spezifisch);
	}

}
