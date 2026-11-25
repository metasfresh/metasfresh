package de.metas.frontend_testing.masterdata.hu_label_config;

import de.metas.JsonObjectMapperHolder;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyRepository;
import de.metas.pricing.pricelist.PriceListVersionRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.handlingunits.report.labels.HULabelConfigRepository;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateHULabelConfigCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
	}

	private BPartnerId createPartner(final String identifier)
	{
		return CreateBPartnerCommand.builder()
				.currencyRepository(new CurrencyRepository())
				.priceListVersionRepository(new PriceListVersionRepository())
				.context(context)
				.request(JsonCreateBPartnerRequest.builder().bpartnerCode(identifier).build())
				.identifier(identifier)
				.build()
				.execute()
				.getId();
	}

	private I_M_HU_Label_Config singleRecord()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Label_Config.class)
				.create()
				.firstOnlyNotNull(I_M_HU_Label_Config.class);
	}

	@Test
	public void execute_createsPartnerScopedLUConfig()
	{
		final BPartnerId bpartnerId = createPartner("BP1");

		CreateHULabelConfigCommand.builder()
				.huLabelConfigRepository(new HULabelConfigRepository())
				.context(context)
				.request(JsonHULabelConfigRequest.builder()
						.bpartner(Identifier.ofString("BP1"))
						.huUnitType(HuUnitType.LU)
						.sourceDocType(HULabelSourceDocType.Picking)
						.process(AdProcessId.ofRepoId(540412))
						.autoPrint(true)
						.build())
				.build()
				.execute();

		final I_M_HU_Label_Config record = singleRecord();
		assertThat(record.getC_BPartner_ID()).isEqualTo(bpartnerId.getRepoId());
		assertThat(record.isApplyToLUs()).isTrue();
		assertThat(record.isApplyToTUs()).isFalse();
		assertThat(record.isApplyToCUs()).isFalse();
		assertThat(record.getHU_SourceDocType()).isEqualTo("PI");
		assertThat(record.getLabelReport_Process_ID()).isEqualTo(540412);
		assertThat(record.isAutoPrint()).isTrue();
		assertThat(record.isActive()).isTrue();
	}

	@Test
	public void execute_withoutSourceDocType_matchesAnySourceDocType()
	{
		createPartner("BP1");

		CreateHULabelConfigCommand.builder()
				.huLabelConfigRepository(new HULabelConfigRepository())
				.context(context)
				.request(JsonHULabelConfigRequest.builder()
						.bpartner(Identifier.ofString("BP1"))
						.huUnitType(HuUnitType.TU)
						.process(AdProcessId.ofRepoId(540412))
						.build())
				.build()
				.execute();

		final I_M_HU_Label_Config record = singleRecord();
		assertThat(record.getHU_SourceDocType()).isNull();
		assertThat(record.isApplyToTUs()).isTrue();
		assertThat(record.isApplyToLUs()).isFalse();
		assertThat(record.isAutoPrint()).isFalse();
	}

	@Test
	public void request_deserializesFromJson_withVirtualHUCode() throws Exception
	{
		final JsonHULabelConfigRequest request = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(
				"{\"bpartner\":\"BP1\",\"huUnitType\":\"V\",\"process\":540412}", JsonHULabelConfigRequest.class);

		createPartner("BP1");
		CreateHULabelConfigCommand.builder().huLabelConfigRepository(new HULabelConfigRepository()).context(context).request(request).build().execute();

		assertThat(singleRecord().isApplyToCUs()).isTrue();
	}
}
