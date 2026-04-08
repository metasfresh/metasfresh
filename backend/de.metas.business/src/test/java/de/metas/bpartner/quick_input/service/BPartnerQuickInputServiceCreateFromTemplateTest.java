package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.related.service.BpartnerRelatedRecordsRepository;
import de.metas.bpartner.attributes.service.BPartnerAttributesRepository;
import de.metas.bpartner.attributes.service.BPartnerContactAttributesRepository;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategy;
import de.metas.bpartner.name.strategy.FirstContactBPartnerNameAndGreetingStrategy;
import de.metas.bpartner.name.strategy.MembershipContactBPartnerNameAndGreetingStrategy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.document.NewRecordContext;
import de.metas.greeting.GreetingRepository;
import de.metas.organization.OrgId;
import de.metas.user.UserDefaultAttributesRepository;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class BPartnerQuickInputServiceCreateFromTemplateTest
{
	private BPartnerQuickInputService service;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(GreetingRepository.class, new GreetingRepository());

		final List<BPartnerNameAndGreetingStrategy> strategies = new ArrayList<>();
		strategies.add(new FirstContactBPartnerNameAndGreetingStrategy());
		strategies.add(new MembershipContactBPartnerNameAndGreetingStrategy(new GreetingRepository()));
		SpringContextHolder.registerJUnitBean(BPartnerNameAndGreetingStrategies.class, new BPartnerNameAndGreetingStrategies(Optional.of(strategies)));

		service = new BPartnerQuickInputService(
				new BPartnerQuickInputRepository(),
				new BPartnerQuickInputAttributesRepository(),
				new BPartnerQuickInputRelatedRecordsRepository(),
				new BPartnerContactQuickInputAttributesRepository(),
				new BPartnerNameAndGreetingStrategies(Optional.of(strategies)),
				new BPartnerCompositeRepository(new BPartnerBL(new UserRepository()), new MockLogEntriesRepository(), new UserRoleRepository()),
				new BPartnerAttributesRepository(),
				new BpartnerRelatedRecordsRepository(),
				new BPartnerContactAttributesRepository(),
				new UserGroupRepository(),
				new UserDefaultAttributesRepository());
	}

	@Test
	void alreadyProcessedTemplate_returnsExistingBPartnerId()
	{
		// Given: a template that was already processed
		final I_C_BPartner_QuickInput template = newInstance(I_C_BPartner_QuickInput.class);
		template.setProcessed(true);
		template.setC_BPartner_ID(12345);
		save(template);

		final NewRecordContext ctx = NewRecordContext.builder()
				.loginOrgId(OrgId.ofRepoId(1))
				.loggedUserId(UserId.ofRepoId(1))
				.loginLanguage(Env.DEFAULT_LANGUAGE)
				.build();

		// When: createBPartnerFromTemplate is called again (e.g. double-click)
		final BPartnerId result = service.createBPartnerFromTemplate(template, ctx);

		// Then: returns the existing BPartner ID without creating a new one
		assertThat(result).isEqualTo(BPartnerId.ofRepoId(12345));
	}
}
