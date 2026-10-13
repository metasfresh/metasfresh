package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
import de.metas.frontend_testing.masterdata.inlinefilter.InlineFilterRepository;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateMasterdataCommandBPartnerTest
{
	private IQueryBL queryBL;
	private CreateMasterdataCommandSupportingServices services;
	private int defaultGroupId;
	private int customGroupId;
	private int germanCountryId;
	private int swissCountryId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);

		final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(queryBL, new ExternalSystems(), new ExternalReferenceTypes());
		services = new CreateMasterdataCommandSupportingServices(externalReferenceRepository, new ExternalReferenceTypes(), new ExternalSystems(), new InlineFilterRepository());

		defaultGroupId = newBPGroup("Standard", true);
		customGroupId = newBPGroup("Kunde", false);

		germanCountryId = newCountry("DE", 201) /* deliberately not the platform default country 101: a missing countryCode must still mean DE, as on new_dawn */;
		swissCountryId = newCountry("CH", 102);
	}

	private static int newBPGroup(final String name, final boolean isDefault)
	{
		final I_C_BP_Group record = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		record.setName(name);
		record.setIsDefault(isDefault);
		InterfaceWrapperHelper.setValue(record, I_C_BP_Group.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		record.setAD_Org_ID(0);
		InterfaceWrapperHelper.saveRecord(record);
		return record.getC_BP_Group_ID();
	}

	private static int newCountry(final String code, final int repoId)
	{
		final I_C_Country record = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		record.setC_Country_ID(repoId);
		record.setCountryCode(code);
		record.setName(code);
		InterfaceWrapperHelper.saveRecord(record);
		return record.getC_Country_ID();
	}

	private JsonCreateMasterdataResponse execute(final JsonCreateMasterdataRequest request)
	{
		return CreateMasterdataCommand.builder().services(services).request(request).build().execute();
	}

	@Test
	void createsBPartnerWithLocationsAndExternalReference()
	{
		final JsonCreateMasterdataResponse response = execute(JsonCreateMasterdataRequest.builder()
				.context(ImmutableMap.of(BPGroupId.class.getName() + ":grp", customGroupId))
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder()
						.bpartnerCode("BP-100")
						.name("Test Partner")
						.bpGroup(Identifier.ofString("grp"))
						.isVendor(true)
						.locations(ImmutableMap.of(
								"loc1", JsonCreateBPartnerRequest.Location.builder()
										.address1("Main St 1")
										.postal("8000")
										.city("Zurich")
										.countryCode("CH")
										.build(),
								"loc2", JsonCreateBPartnerRequest.Location.builder().build()))
						.build()))
				.externalReferences(ImmutableList.of(JsonCreateExternalReferenceRequest.builder()
						.type("BPartner")
						.externalSystem("Other")
						.value("12345")
						.referencedRecord("bp1")
						.build()))
				.build());

		final JsonCreateBPartnerResponse bpResponse = response.getBpartners().get("bp1");
		assertThat(bpResponse.getBpartnerCode()).isEqualTo("BP-100");
		assertThat(bpResponse.getBpartnerLocationId()).isNull();
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(bpResponse.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isEqualTo("BP-100");
		assertThat(bpartner.getName()).isEqualTo("Test Partner");
		assertThat(bpartner.getC_BP_Group_ID()).isEqualTo(customGroupId);
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isTrue();

		final I_C_BPartner_Location bpLocation1 = InterfaceWrapperHelper.load(bpResponse.getLocations().get("loc1").getId(), I_C_BPartner_Location.class);
		assertThat(bpLocation1.getC_BPartner_ID()).isEqualTo(bpartner.getC_BPartner_ID());
		assertThat(bpLocation1.isBillToDefault()).isTrue();
		final I_C_Location location = bpLocation1.getC_Location();
		assertThat(location.getAddress1()).isEqualTo("Main St 1");
		assertThat(location.getPostal()).isEqualTo("8000");
		assertThat(location.getCity()).isEqualTo("Zurich");
		assertThat(location.getC_Country_ID()).isEqualTo(swissCountryId);

		final I_C_BPartner_Location bpLocation2 = InterfaceWrapperHelper.load(bpResponse.getLocations().get("loc2").getId(), I_C_BPartner_Location.class);
		assertThat(bpLocation2.isBillToDefault()).isFalse();
		assertThat(bpLocation2.getC_Location().getC_Country_ID()).isEqualTo(germanCountryId);

		final List<I_S_ExternalReference> externalReferences = queryBL.createQueryBuilder(I_S_ExternalReference.class).create().list();
		assertThat(externalReferences).hasSize(1);
		final I_S_ExternalReference externalReference = externalReferences.get(0);
		assertThat(externalReference.getType()).isEqualTo("BPartner");
		assertThat(externalReference.getExternalSystem()).isEqualTo("Other");
		assertThat(externalReference.getExternalReference()).isEqualTo("12345");
		assertThat(externalReference.getRecord_ID()).isEqualTo(bpartner.getC_BPartner_ID());

		assertThat(response.getContext())
				.containsEntry(BPartnerId.class.getName() + ":bp1", bpartner.getC_BPartner_ID())
				.containsEntry(BPartnerLocationId.class.getName() + ":loc1", bpartner.getC_BPartner_ID() + "_" + bpResponse.getLocations().get("loc1").getId());
	}

	@Test
	void defaultsCodeNameGroupAndSingleLocation()
	{
		final JsonCreateMasterdataResponse response = execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().build()))
				.build());

		final JsonCreateBPartnerResponse bpResponse = response.getBpartners().get("bp1");
		assertThat(bpResponse.getBpartnerCode()).startsWith("bp1_");
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(bpResponse.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isEqualTo(bpResponse.getBpartnerCode());
		assertThat(bpartner.getName()).isEqualTo(bpResponse.getBpartnerCode());
		assertThat(bpartner.getC_BP_Group_ID()).isEqualTo(defaultGroupId);
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isFalse();

		assertThat(bpResponse.getLocations()).isNull();
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.load(bpResponse.getBpartnerLocationId(), I_C_BPartner_Location.class);
		assertThat(bpLocation.getC_BPartner_ID()).isEqualTo(bpartner.getC_BPartner_ID());
		assertThat(bpLocation.getC_Location().getC_Country_ID()).isEqualTo(germanCountryId);
		assertThat(response.getContext()).containsKey(BPartnerLocationId.class.getName() + ":bp1" + MasterdataContext.SINGLE_BP_LOCATION_SUFFIX);
		assertThat(queryBL.createQueryBuilder(I_S_ExternalReference.class).create().count()).isZero();
	}

	@Test
	void externalReferenceToUnknownBPartner_isRejected()
	{
		assertThatThrownBy(() -> execute(JsonCreateMasterdataRequest.builder()
				.externalReferences(ImmutableList.of(externalReference("BPartner", "missing")))
				.build()))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("missing");
	}

	@Test
	void unknownExternalReferenceType_isRejected()
	{
		assertThatThrownBy(() -> execute(requestWithBPartnerAndExternalReference("NoSuchType")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchType");
	}

	@Test
	void externalReferenceTypeOfOtherTable_isRejected()
	{
		assertThatThrownBy(() -> execute(requestWithBPartnerAndExternalReference("Product")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Product");
		assertThat(queryBL.createQueryBuilder(I_S_ExternalReference.class).create().count()).isZero();
	}

	@Test
	void unknownBPGroupIdentifier_isRejected()
	{
		assertThatThrownBy(() -> execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().bpGroup(Identifier.ofString("noSuchGroup")).build()))
				.build()))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("noSuchGroup");
	}

	@Test
	void requestJsonContract() throws Exception
	{
		final JsonCreateMasterdataRequest request = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(
				"{\"bpartners\":{\"bp1\":{\"bpartnerCode\":\"X1\",\"name\":\"N1\",\"bpGroup\":\"grp\","
						+ "\"locations\":{\"loc1\":{\"city\":\"Berlin\",\"postal\":\"10115\",\"address1\":\"A 1\",\"countryCode\":\"DE\"}},"
						+ "\"isVendor\":true,\"isCustomer\":false}},"
						+ "\"externalReferences\":[{\"type\":\"BPartner\",\"externalSystem\":\"Other\",\"value\":\"12345\",\"referencedRecord\":\"bp1\"}]}",
				JsonCreateMasterdataRequest.class);

		final JsonCreateBPartnerRequest bpartner = request.getBpartners().get("bp1");
		assertThat(bpartner.getBpartnerCode()).isEqualTo("X1");
		assertThat(bpartner.getName()).isEqualTo("N1");
		assertThat(bpartner.getBpGroup()).isEqualTo(Identifier.ofString("grp"));
		assertThat(bpartner.getLocations().get("loc1")).isEqualTo(JsonCreateBPartnerRequest.Location.builder()
				.city("Berlin").postal("10115").address1("A 1").countryCode("DE").build());
		assertThat(bpartner.isVendor()).isTrue();
		assertThat(bpartner.isCustomer()).isFalse();
		assertThat(request.getExternalReferences()).containsExactly(externalReference("BPartner", "bp1"));
	}

	@Test
	void responseJsonContract() throws Exception
	{
		final JsonCreateMasterdataResponse response = JsonCreateMasterdataResponse.builder()
				.bpartners(ImmutableMap.of(
						"bp1", JsonCreateBPartnerResponse.builder().id(BPartnerId.ofRepoId(11)).bpartnerCode("X1").bpartnerLocationId(12).build(),
						"bp2", JsonCreateBPartnerResponse.builder().id(BPartnerId.ofRepoId(21)).bpartnerCode("X2")
								.locations(ImmutableMap.of("loc1", JsonCreateBPartnerResponse.Location.builder().id(22).build()))
								.build()))
				.build();

		final JsonNode json = JsonObjectMapperHolder.sharedJsonObjectMapper().valueToTree(response);

		assertThat(json.at("/bpartners/bp1/id").asInt()).isEqualTo(11);
		assertThat(json.at("/bpartners/bp1/bpartnerCode").asText()).isEqualTo("X1");
		assertThat(json.at("/bpartners/bp1/bpartnerLocationId").asInt()).isEqualTo(12);
		assertThat(json.at("/bpartners/bp2/locations/loc1/id").asInt()).isEqualTo(22);
	}

	private static JsonCreateExternalReferenceRequest externalReference(final String type, final String referencedRecord)
	{
		return JsonCreateExternalReferenceRequest.builder()
				.type(type)
				.externalSystem("Other")
				.value("12345")
				.referencedRecord(referencedRecord)
				.build();
	}

	private static JsonCreateMasterdataRequest requestWithBPartnerAndExternalReference(final String type)
	{
		return JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().bpartnerCode("BP-300").build()))
				.externalReferences(ImmutableList.of(externalReference(type, "bp1")))
				.build();
	}
}
