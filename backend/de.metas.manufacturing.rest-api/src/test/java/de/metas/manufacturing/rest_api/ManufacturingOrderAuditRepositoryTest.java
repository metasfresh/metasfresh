package de.metas.manufacturing.rest_api;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.error.AdIssueId;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAuditItem;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class ManufacturingOrderAuditRepositoryTest
{
	private ManufacturingOrderExportAuditRepository auditRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		auditRepository = new ManufacturingOrderExportAuditRepository();
	}

	@Test
	void save_and_getByTransactionId()
	{
		final ManufacturingOrderExportAudit audit = ManufacturingOrderExportAudit.builder()
				.transactionId(APITransactionId.ofString("transactionId"))
				.item(ManufacturingOrderExportAuditItem.builder()
						.orderId(PPOrderId.ofRepoId(11))
						.orgId(OrgId.ofRepoId(10))
						.exportStatus(APIExportStatus.Exported)
						.jsonRequest("json1")
						.build())
				.item(ManufacturingOrderExportAuditItem.builder()
						.orderId(PPOrderId.ofRepoId(21))
						.orgId(OrgId.ofRepoId(10))
						.exportStatus(APIExportStatus.ExportedAndError)
						.issueId(AdIssueId.ofRepoId(20))
						.jsonRequest("json2")
						.build())
				.build();
		auditRepository.save(audit);

		final ManufacturingOrderExportAudit auditReloaded = auditRepository.getByTransactionId(audit.getTransactionId());
		assertThat(auditReloaded).isEqualToComparingFieldByField(audit);
	}
}
