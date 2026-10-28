package de.metas.payment.sepa.api.impl;

import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

class SEPADocumentDAOTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void save_persistsTheProcessedFlagOfTheExport()
	{
		final I_SEPA_Export export = newInstance(I_SEPA_Export.class);
		export.setProcessed(true);
		Services.get(ISEPADocumentDAO.class).save(export);

		assertThat(export.getSEPA_Export_ID()).isGreaterThan(0);
		assertThat(load(export.getSEPA_Export_ID(), I_SEPA_Export.class).isProcessed()).isTrue();
	}
}
