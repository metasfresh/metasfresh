package de.metas.commission.modelvalidator;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import mockit.Mocked;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.junit.Test;

import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCSponsorSalesRep;

public class SponsorValidatorSSREasyMockTests {

	/**
	 * Checks if the validator registers for the right tables upon startup.
	 */
	@Test
	public void initialize() {

		final SponsorValidator sponsorValidator = new SponsorValidator();

		final ModelValidationEngine modelValidationEngine = createMock(ModelValidationEngine.class);

		modelValidationEngine.addModelChange(I_C_BPartner.Table_Name,
				sponsorValidator);
		modelValidationEngine.addModelChange(I_C_Sponsor_SalesRep.Table_Name,
				sponsorValidator);

		replay(modelValidationEngine);
		sponsorValidator.initialize(modelValidationEngine, null);
		verify(modelValidationEngine);
	}

	@Mocked
	MCSponsorSalesRep ssr;

	/**
	 * the model validator may not allow deletion of C_Sponsor_SalesReps.
	 */
	@Test
	public void testDeleteSponsorSalesRep() {

		final SponsorValidator sponsorValidator = new SponsorValidator();
		final String result = sponsorValidator.sponsorSalesRepChange(ssr,
				ModelValidator.TYPE_BEFORE_DELETE);

		assertNotNull(result);
	}
}
