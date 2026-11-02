package de.metas.ordercandidate.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.effective.BPartnerAddressEffectiveBL;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link IOLCandBL#getOrderDocTypeId(OLCandOrderDefaults, I_C_OLCand)}.
 * <p>
 * Precedence: 1. the candidate's own order type, 2. the buyer partner's order type, 3. the processor default.
 * The partner is only loaded when the candidate carries no order type of its own.
 */
@ExtendWith(AdempiereTestWatcher.class)
class OLCandBLGetOrderDocTypeIdTest
{
	private IBPartnerBL bpartnerBL;
	private OLCandBL olCandBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		bpartnerBL = mock(IBPartnerBL.class);
		olCandBL = new OLCandBL(
				bpartnerBL,
				BPartnerOrderParamsRepository.newInstanceForUnitTesting(),
				BPartnerAddressEffectiveBL.newInstanceForUnitTesting());
	}

	@Test
	void candidateWithOwnOrderType_doesNotLoadThePartner()
	{
		final DocTypeId ownDocTypeId = createSalesOrderDocType();
		final I_C_OLCand olCand = createOLCand(ownDocTypeId, createPartnerId());

		final DocTypeId result = olCandBL.getOrderDocTypeId(null, olCand);

		assertThat(result).isEqualTo(ownDocTypeId);
		verify(bpartnerBL, never()).getById(any(BPartnerId.class));
	}

	@Test
	void candidateWithoutOrderType_usesThePartnerOrderType()
	{
		final DocTypeId partnerDocTypeId = createSalesOrderDocType();
		final BPartnerId partnerId = createPartnerId();
		final I_C_BPartner partner = newInstanceOutOfTrx(I_C_BPartner.class);
		partner.setSO_DocTypeTarget_ID(partnerDocTypeId.getRepoId());
		when(bpartnerBL.getById(partnerId)).thenReturn(partner);

		final DocTypeId result = olCandBL.getOrderDocTypeId(null, createOLCand(null, partnerId));

		assertThat(result).isEqualTo(partnerDocTypeId);
	}

	private DocTypeId createSalesOrderDocType()
	{
		final I_C_DocType docType = newInstanceOutOfTrx(I_C_DocType.class);
		docType.setDocBaseType(DocBaseType.SalesOrder.getCode());
		docType.setAD_Org_ID(0);
		saveRecord(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	private BPartnerId createPartnerId()
	{
		final I_C_BPartner partner = newInstanceOutOfTrx(I_C_BPartner.class);
		saveRecord(partner);
		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}

	private I_C_OLCand createOLCand(final DocTypeId orderDocTypeId, final BPartnerId bpartnerId)
	{
		final I_C_OLCand olCand = newInstanceOutOfTrx(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bpartnerId.getRepoId());
		if (orderDocTypeId != null)
		{
			olCand.setC_DocTypeOrder_ID(orderDocTypeId.getRepoId());
		}
		saveRecord(olCand);
		return olCand;
	}
}
