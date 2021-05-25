/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.bpartner.v2.request.alberta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.*;

public class JsonCompositeAlbertaBPartnerTest
{
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@BeforeClass
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonAlbertaBPartner bPartner = new JsonAlbertaBPartner();
		bPartner.setIsArchived(true);
		bPartner.setTitle("title");
		bPartner.setTitleShort("titleShort");
		bPartner.setTimestamp(Instant.parse("2019-11-22T00:00:00Z"));

		final JsonAlbertaPatient patient = new JsonAlbertaPatient();
		patient.setHospitalIdentifier("1111111");
		patient.setDischargeDate(LocalDate.parse("2019-11-22"));
		patient.setPayerIdentifier("2222222");
		patient.setPayerType("payerType");
		patient.setNumberOfInsured("333333");
		patient.setCopaymentFrom(LocalDate.parse("2021-05-24"));
		patient.setCopaymentTo(LocalDate.parse("2021-05-24"));
		patient.setIsTransferPatient(true);
		patient.setIVTherapy(true);
		patient.setFieldNurseIdentifier("4444444");
		patient.setDeactivationReason("reason");
		patient.setDeactivationDate(LocalDate.parse("2021-05-24"));
		patient.setDeactivationComment("comment");
		patient.setCreatedAt(Instant.parse("2019-11-22T00:00:00Z"));
		patient.setCreatedByIdentifier("5555555");
		patient.setUpdatedAt(Instant.parse("2019-11-22T00:00:00Z"));
		patient.setCreatedByIdentifier("6666666");

		final JsonAlbertaCareGiver careGiver = new JsonAlbertaCareGiver();
		careGiver.setCaregiverIdentifier("11111");
		careGiver.setType("type");
		careGiver.setIsLegalCarer(true);

		final ImmutableList.Builder<JsonAlbertaCareGiver> careGivers = ImmutableList.builder();
		careGivers.add(careGiver);

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner = JsonCompositeAlbertaBPartner.builder()
				.jsonAlbertaBPartner(bPartner)
				.role(JsonBPartnerRole.Patient)
				.jsonAlbertaPatient(patient)
				.jsonAlbertaCareGivers(careGivers.build())
				.build();

		final String string = mapper.writeValueAsString(compositeAlbertaBPartner);
		assertThat(string).isNotEmpty();

		final JsonCompositeAlbertaBPartner result = mapper.readValue(string, JsonCompositeAlbertaBPartner.class);

		assertThat(result).isEqualTo(compositeAlbertaBPartner);
		expect(result).toMatchSnapshot();
	}
}
