package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link BPartnerBL#getSalesOrderDocTypeId(BPartnerId)}.
 */
class BPartnerBL_GetSalesOrderDocTypeIdTest
{
	private BPartnerBL bpartnerBL;
	private IBPartnerDAO bpartnersRepo;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		bpartnersRepo = spy(Services.get(IBPartnerDAO.class));
		Services.registerService(IBPartnerDAO.class, bpartnersRepo);
		bpartnerBL = new BPartnerBL(new UserRepository());
	}

	@Test
	void partnerWithOrderType_returnsIt()
	{
		final BPartnerId partnerId = createPartner(123);

		assertThat(bpartnerBL.getSalesOrderDocTypeId(partnerId)).contains(DocTypeId.ofRepoId(123));
	}

	@Test
	void partnerWithoutOrderType_returnsEmpty()
	{
		final BPartnerId partnerId = createPartner(0);

		assertThat(bpartnerBL.getSalesOrderDocTypeId(partnerId)).isEmpty();
	}

	@Test
	void samePartnerAskedTwice_isLoadedOnlyOnce()
	{
		final BPartnerId partnerId = createPartner(123);

		final Optional<DocTypeId> result1 = bpartnerBL.getSalesOrderDocTypeId(partnerId);
		final Optional<DocTypeId> result2 = bpartnerBL.getSalesOrderDocTypeId(partnerId);

		assertThat(result1).contains(DocTypeId.ofRepoId(123));
		assertThat(result2).isEqualTo(result1);
		verify(bpartnersRepo, times(1)).getById(partnerId);
	}

	private BPartnerId createPartner(final int soDocTypeTargetId)
	{
		final I_C_BPartner partner = newInstanceOutOfTrx(I_C_BPartner.class);
		partner.setSO_DocTypeTarget_ID(soDocTypeTargetId);
		saveRecord(partner);
		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}
}
