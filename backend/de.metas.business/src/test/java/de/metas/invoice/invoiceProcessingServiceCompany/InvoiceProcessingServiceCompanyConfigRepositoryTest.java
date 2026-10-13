package de.metas.invoice.invoiceProcessingServiceCompany;

import de.metas.bpartner.BPartnerId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

class InvoiceProcessingServiceCompanyConfigRepositoryTest
{
	private InvoiceProcessingServiceCompanyConfigRepository repository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new InvoiceProcessingServiceCompanyConfigRepository();
	}

	@Test
	void getServiceCompanyBPartnerIdByConfigId_returnsTheServiceCompanyOfTheConfig()
	{
		final InvoiceProcessingServiceCompanyConfigId configId = createConfig(BPartnerId.ofRepoId(111), true);

		assertThat(repository.getServiceCompanyBPartnerIdByConfigId(configId)).isEqualTo(BPartnerId.ofRepoId(111));
	}

	@Test
	void getServiceCompanyBPartnerIdByConfigId_inactiveConfig_isStillResolved()
	{
		final InvoiceProcessingServiceCompanyConfigId configId = createConfig(BPartnerId.ofRepoId(111), false);

		assertThat(repository.getServiceCompanyBPartnerIdByConfigId(configId)).isEqualTo(BPartnerId.ofRepoId(111));
	}

	@Test
	void getServiceCompanyBPartnerIdByConfigId_savingTheConfig_isSeenByTheNextRead()
	{
		final I_InvoiceProcessingServiceCompany record = newInstance(I_InvoiceProcessingServiceCompany.class);
		record.setServiceCompany_BPartner_ID(111);
		saveRecord(record);
		final InvoiceProcessingServiceCompanyConfigId configId = InvoiceProcessingServiceCompanyConfigId.ofRepoId(record.getInvoiceProcessingServiceCompany_ID());
		assertThat(repository.getServiceCompanyBPartnerIdByConfigId(configId)).isEqualTo(BPartnerId.ofRepoId(111));

		record.setServiceCompany_BPartner_ID(112);
		saveRecord(record);

		assertThat(repository.getServiceCompanyBPartnerIdByConfigId(configId)).isEqualTo(BPartnerId.ofRepoId(112));
	}

	private static InvoiceProcessingServiceCompanyConfigId createConfig(final BPartnerId serviceCompanyId, final boolean isActive)
	{
		final I_InvoiceProcessingServiceCompany record = newInstance(I_InvoiceProcessingServiceCompany.class);
		record.setServiceCompany_BPartner_ID(serviceCompanyId.getRepoId());
		record.setIsActive(isActive);
		saveRecord(record);
		return InvoiceProcessingServiceCompanyConfigId.ofRepoId(record.getInvoiceProcessingServiceCompany_ID());
	}
}
