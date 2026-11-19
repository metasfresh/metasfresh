package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.grai.GRAIRequired;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateBPartnerCommandTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void execute_setsGRAIRequired()
	{
		// given: a partner created with GRAIRequired = 'N'
		final JsonCreateBPartnerResponse created = CreateBPartnerCommand.builder()
				.currencyRepository(new CurrencyRepository())
				.context(new MasterdataContext())
				.request(JsonCreateBPartnerRequest.builder()
						.bpartnerCode("BP_GRAI_UPD")
						.graiRequired(GRAIRequired.No)
						.build())
				.identifier("bpGraiUpd")
				.build()
				.execute();
		assertThat(InterfaceWrapperHelper.load(created.getId(), I_C_BPartner.class).getGRAIRequired()).isEqualTo("N");

		// when
		UpdateBPartnerCommand.builder()
				.request(JsonUpdateBPartnerRequest.builder()
						.bpartnerId(String.valueOf(created.getId().getRepoId()))
						.graiRequired(GRAIRequired.Yes)
						.build())
				.build()
				.execute();

		// then: row re-read
		assertThat(InterfaceWrapperHelper.load(created.getId(), I_C_BPartner.class).getGRAIRequired()).isEqualTo("Y");
	}
}
