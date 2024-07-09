/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.job.JobService;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.utils.OrgAndBPartnerCompositeLookupKey;
import de.metas.rest_api.utils.OrgAndBPartnerCompositeLookupKeyList;
import de.metas.rest_api.v2.bpartner.JsonGreetingService;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.sectionCode.SectionCodeRepository;
import de.metas.sectionCode.SectionCodeService;
import de.metas.title.TitleRepository;
import de.metas.user.UserRepository;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.Optional;

import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_ORG_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.BP_GROUP_RECORD_NAME;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_VALUE;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BP_GROUP_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.createBPartnerData;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
class JsonRetrieverServiceTest
{
	private final OrgId orgId = OrgId.ofRepoId(10);
	private JsonRetrieverService jsonRetrieverService;

	private SectionCodeRepository sectionCodeRepository;

	private IncotermsRepository incotermsRepository;
	private Expect expect;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), new BPartnerCreditLimitRepository());

		sectionCodeRepository = new SectionCodeRepository();

		incotermsRepository = new IncotermsRepository();

		final de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				new TitleRepository(),
				new CurrencyRepository(),
				JobService.newInstanceForUnitTesting(),
				Mockito.mock(ExternalReferenceRestControllerService.class),
				Mockito.mock(AlbertaBPartnerCompositeService.class),
				new SectionCodeService(sectionCodeRepository),
				incotermsRepository,
				new BPartnerCreditLimitRepository(),
				new JsonGreetingService(new GreetingRepository(), Mockito.mock(ExternalReferenceRestControllerService.class)));

		jsonRetrieverService = jsonServiceFactory.createRetriever();

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		bpGroupRecord.setName(BP_GROUP_RECORD_NAME);
		saveRecord(bpGroupRecord);

		createBPartnerData(0);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);
	}

	@Test
	void retrieveBPartnerComposite_byBPartnerCompositeLookupKeys()
	{
		final OrgAndBPartnerCompositeLookupKeyList bpartnerLookupKeys = OrgAndBPartnerCompositeLookupKeyList.of(
				orgId, ImmutableList.of(
						BPartnerCompositeLookupKey.ofMetasfreshId(MetasfreshId.of(C_BPARTNER_ID)),
						BPartnerCompositeLookupKey.ofCode(C_BPARTNER_VALUE))
		);

		final Optional<BPartnerComposite> result = jsonRetrieverService.getBPartnerComposite(bpartnerLookupKeys);

		assertThat(result).isNotEmpty();
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
	}

	/**
	 * verifies that if a bpartner is found&loaded by one lookup property, it is also cached to be found by the other properties
	 */
	@Test
	void retrieveBPartnerComposite_retrieveBPartnerCompositeAssertCacheHit()
	{
		final OrgAndBPartnerCompositeLookupKeyList bpartnerLookupKey = OrgAndBPartnerCompositeLookupKeyList.ofSingleLookupKey(
				orgId, BPartnerCompositeLookupKey.ofCode(C_BPARTNER_VALUE)
		);
		final Optional<BPartnerComposite> result = jsonRetrieverService.getBPartnerComposite(bpartnerLookupKey);

		final OrgAndBPartnerCompositeLookupKey bpartnerLookupKey2 = OrgAndBPartnerCompositeLookupKey.of(
				BPartnerCompositeLookupKey.ofMetasfreshId(MetasfreshId.of(C_BPARTNER_ID)), orgId);

		final Optional<BPartnerComposite> result2 = jsonRetrieverService.getBPartnerCompositeAssertCacheHit(ImmutableList.of(bpartnerLookupKey2));

		assertThat(result2).isNotSameAs(result); // not sameAs, because the composite is mutable and the retriever shall give us own own instance each time.
		assertThat(result2).isEqualTo(result);
	}

}
