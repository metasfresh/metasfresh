/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api;

import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EDIBPartnerConfigTest
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(1);
	private static final ExternalSystemParentConfigId EXTERNAL_SYSTEM_CONFIG_ID = ExternalSystemParentConfigId.ofRepoId(100);

	@Test
	void flagOff_returnsFalse()
	{
		final EDIBPartnerConfig config = EDIBPartnerConfig.builder()
				.bPartnerId(BPARTNER_ID)
				.isEdiDesadvRecipient(true)
				.ediDesadvSendingMode(EDISendingMode.ExternalSystem)
				.ediDesadvExternalSystemParentConfigId(EXTERNAL_SYSTEM_CONFIG_ID)
				.isEdiOneEDIDesadvPerShipment(false)
				.isEdiInvoicRecipient(false)
				.ediInvoicSendingMode(EDISendingMode.ReplicationInterface)
				.build();

		assertThat(config.isDESADVOneDesadvPerShipment()).isFalse();
	}

	@Test
	void flagOn_modeExternalSystem_returnsTrue()
	{
		final EDIBPartnerConfig config = EDIBPartnerConfig.builder()
				.bPartnerId(BPARTNER_ID)
				.isEdiDesadvRecipient(true)
				.ediDesadvSendingMode(EDISendingMode.ExternalSystem)
				.ediDesadvExternalSystemParentConfigId(EXTERNAL_SYSTEM_CONFIG_ID)
				.isEdiOneEDIDesadvPerShipment(true)
				.isEdiInvoicRecipient(false)
				.ediInvoicSendingMode(EDISendingMode.ReplicationInterface)
				.build();

		assertThat(config.isDESADVOneDesadvPerShipment()).isTrue();
	}

	@Test
	void flagOn_modeReplicationInterface_returnsFalse()
	{
		final EDIBPartnerConfig config = EDIBPartnerConfig.builder()
				.bPartnerId(BPARTNER_ID)
				.isEdiDesadvRecipient(true)
				.ediDesadvSendingMode(EDISendingMode.ReplicationInterface)
				.ediDesadvExternalSystemParentConfigId(null)
				.isEdiOneEDIDesadvPerShipment(true)
				.isEdiInvoicRecipient(false)
				.ediInvoicSendingMode(EDISendingMode.ReplicationInterface)
				.build();

		assertThat(config.isDESADVOneDesadvPerShipment()).isFalse();
	}

	@Test
	void flagOn_notRecipient_returnsFalse()
	{
		final EDIBPartnerConfig config = EDIBPartnerConfig.builder()
				.bPartnerId(BPARTNER_ID)
				.isEdiDesadvRecipient(false)
				.ediDesadvSendingMode(EDISendingMode.ExternalSystem)
				.ediDesadvExternalSystemParentConfigId(EXTERNAL_SYSTEM_CONFIG_ID)
				.isEdiOneEDIDesadvPerShipment(true)
				.isEdiInvoicRecipient(false)
				.ediInvoicSendingMode(EDISendingMode.ReplicationInterface)
				.build();

		assertThat(config.isDESADVOneDesadvPerShipment()).isFalse();
	}
}
