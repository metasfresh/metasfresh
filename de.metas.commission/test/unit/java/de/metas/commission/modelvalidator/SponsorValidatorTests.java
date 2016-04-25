package de.metas.commission.modelvalidator;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.adempiere.util.Services;
import org.compiere.model.MBPartner;
import org.compiere.model.ModelValidator;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.MCSponsor;
import de.metas.commission.model.MCSponsorSalesRep;

public final class SponsorValidatorTests {

	private static final String SPONSOR_NO = "1234";

	private static final int BPARTNER_ID = 25;

	private static final int SPONSOR_ID = 35;


	@Mocked
	MBPartner bPartner;

	@Mocked
	MCSponsor sponsor;

	@Mocked
	MCSponsorSalesRep sponsorSalesRep;

	@Mocked
	Services unused = null;

	/**
	 * A new sponsor and ssr is created for a new bPartner
	 */
	@Test
	public void bPartnerNew() {

		new NonStrictExpectations() {
			{
				MCSponsor.createNewForCustomer(bPartner);
				returns(sponsor);
			}
		};
		
		new SponsorValidator().bPartnerChange(bPartner,
				ModelValidator.TYPE_AFTER_NEW);

		new Verifications() {
			{
				MCSponsor.createNewForCustomer(bPartner);
			}
		};
	}
	
	@Test
	public void bVendorNewNoParentSponsorId() {

		new NonStrictExpectations() {
			{
				bPartner.get_ValueAsInt(I_C_BPartner.COLUMNNAME_C_Sponsor_Parent_ID);
				returns(0);
				bPartner.isCustomer();
				returns(false);
				bPartner.isSalesRep();
				returns(false);
			}
		};

		new SponsorValidator().modelChange(bPartner,
				ModelValidator.TYPE_AFTER_NEW);

		new Verifications() {
			{
				MCSponsor.createNewForCustomer(bPartner);
			}
		};
	}

	@Mocked
	MCSponsorSalesRep ssr;
	
	@Before
	public void setUp() {

		new NonStrictExpectations() {
			{
				bPartner.getCtx();
				returns(POTest.CTX);

				bPartner.getC_BPartner_ID();
				returns(BPARTNER_ID);

				bPartner.get_TrxName();
				returns(POTest.TRX_NAME);

				bPartner.getValue();
				returns(SPONSOR_NO);

				sponsor.getC_Sponsor_ID();
				returns(SPONSOR_ID);

				sponsor.get_TrxName();
				returns(POTest.TRX_NAME);
			}
		};

	}
}