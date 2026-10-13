package de.metas.frontend_testing.masterdata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
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
		services = new CreateMasterdataCommandSupportingServices(externalReferenceRepository, new ExternalReferenceTypes(), new ExternalSystems());

		defaultGroupId = newBPGroup("Standard", true);
		customGroupId = newBPGroup("Kunde", false);

		germanCountryId = newCountry("DE", 101) /* the platform default country, see CountryDAO */;
		swissCountryId = newCountry("CH", 102);
	}

	private static int newBPGroup(final String name, final boolean isDefault)
	{
		final I_C_BP_Group record = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		record.setName(name);
		record.setIsDefault(isDefault);
		InterfaceWrapperHelper.setValue(record, "AD_Client_ID", ClientId.METASFRESH.getRepoId());
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
	void createsBPartnerWithLocationAndExternalReference()
	{
		final JsonCreateMasterdataResponse response = execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder()
						.code("BP-100")
						.name("Test Partner")
						.group("Kunde")
						.isVendor(true)
						.location(JsonCreateBPartnerRequest.Location.builder()
								.address1("Main St 1")
								.postal("8000")
								.city("Zurich")
								.countryCode("CH")
								.build())
						.build()))
				.externalReferences(ImmutableList.of(JsonCreateExternalReferenceRequest.builder()
						.type("BPartner")
						.externalSystem("Other")
						.value("12345")
						.referencedRecord("bp1")
						.build()))
				.build());

		final JsonCreateBPartnerResponse bpResponse = response.getBpartners().get("bp1");
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(bpResponse.getId(), I_C_BPartner.class);
		assertThat(bpartner.getValue()).isEqualTo("BP-100");
		assertThat(bpartner.getName()).isEqualTo("Test Partner");
		assertThat(bpartner.getC_BP_Group_ID()).isEqualTo(customGroupId);
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isTrue();

		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.load(bpResponse.getLocationId(), I_C_BPartner_Location.class);
		assertThat(bpLocation.getC_BPartner_ID()).isEqualTo(bpartner.getC_BPartner_ID());
		final I_C_Location location = bpLocation.getC_Location();
		assertThat(location.getAddress1()).isEqualTo("Main St 1");
		assertThat(location.getPostal()).isEqualTo("8000");
		assertThat(location.getCity()).isEqualTo("Zurich");
		assertThat(location.getC_Country_ID()).isEqualTo(swissCountryId);

		final List<I_S_ExternalReference> externalReferences = queryBL.createQueryBuilder(I_S_ExternalReference.class).create().list();
		assertThat(externalReferences).hasSize(1);
		final I_S_ExternalReference externalReference = externalReferences.get(0);
		assertThat(externalReference.getType()).isEqualTo("BPartner");
		assertThat(externalReference.getExternalSystem()).isEqualTo("Other");
		assertThat(externalReference.getExternalReference()).isEqualTo("12345");
		assertThat(externalReference.getRecord_ID()).isEqualTo(bpartner.getC_BPartner_ID());

		assertThat(response.getContext()).containsValue(bpartner.getC_BPartner_ID());
	}

	@Test
	void defaultsGroupNameAndCountry()
	{
		final JsonCreateMasterdataResponse response = execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().code("BP-200").build()))
				.build());

		final JsonCreateBPartnerResponse bpResponse = response.getBpartners().get("bp1");
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(bpResponse.getId(), I_C_BPartner.class);
		assertThat(bpartner.getName()).isEqualTo("BP-200");
		assertThat(bpartner.getC_BP_Group_ID()).isEqualTo(defaultGroupId);
		assertThat(bpartner.isCustomer()).isTrue();
		assertThat(bpartner.isVendor()).isFalse();

		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.load(bpResponse.getLocationId(), I_C_BPartner_Location.class);
		assertThat(bpLocation.getC_Location().getC_Country_ID()).isEqualTo(germanCountryId);
		assertThat(queryBL.createQueryBuilder(I_S_ExternalReference.class).create().count()).isZero();
	}

	@Test
	void externalReferenceToUnknownBPartner_isRejected()
	{
		assertThatThrownBy(() -> execute(JsonCreateMasterdataRequest.builder()
				.externalReferences(ImmutableList.of(JsonCreateExternalReferenceRequest.builder()
						.type("BPartner")
						.externalSystem("Other")
						.value("12345")
						.referencedRecord("missing")
						.build()))
				.build()))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("missing");
	}

	@Test
	void unknownExternalReferenceType_isRejected()
	{
		assertThatThrownBy(() -> execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().code("BP-300").build()))
				.externalReferences(ImmutableList.of(JsonCreateExternalReferenceRequest.builder()
						.type("NoSuchType")
						.externalSystem("Other")
						.value("12345")
						.referencedRecord("bp1")
						.build()))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchType");
	}

	@Test
	void unknownBPGroup_isRejected()
	{
		assertThatThrownBy(() -> execute(JsonCreateMasterdataRequest.builder()
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().code("BP-400").group("NoSuchGroup").build()))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchGroup");
	}
}
