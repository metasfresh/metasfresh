/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.exportaudit;

import de.metas.error.AdIssueId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Exported;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndError;
import static org.assertj.core.api.Assertions.*;

class ShipmentScheduleAuditRepositoryTest
{
	private ShipmentScheduleAuditRepository shipmentScheduleAuditRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleAuditRepository = new ShipmentScheduleAuditRepository();
	}

	@Test
	void getByTransactionId()
	{
		final APIExportAudit auditOrig = performSaveTest();

		final APIExportAudit audit = shipmentScheduleAuditRepository.getByTransactionId(auditOrig.getTransactionId());

		assertThat(audit).isEqualTo(auditOrig);
	}

	@Test
	void save()
	{
		performSaveTest();
	}

	private APIExportAudit performSaveTest()
	{
		// given
		final ShipmentScheduleId shipmentScheduleId1 = ShipmentScheduleId.ofRepoId(11);
		final ShipmentScheduleId shipmentScheduleId2 = ShipmentScheduleId.ofRepoId(21);
		final APIExportAudit audit = APIExportAudit.builder()
				.transactionId("transactionId")
				.item(
						shipmentScheduleId1,
						ShipmentScheduleExportAuditItem.builder()
								.orgId(OrgId.ofRepoId(10))
								.exportStatus(Exported)
								.repoIdAware(shipmentScheduleId1)
								.issueId(AdIssueId.ofRepoId(20))
								.build())
				.item(
						shipmentScheduleId2,
						ShipmentScheduleExportAuditItem.builder()
								.orgId(OrgId.ofRepoId(10))
								.exportStatus(ExportedAndError)
								.repoIdAware(shipmentScheduleId2)
								.build())
				.build();

		// when
		shipmentScheduleAuditRepository.save(audit);

		// then
		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(2);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo("transactionId");

		final List<I_M_ShipmentSchedule_ExportAudit> exportAuditLines = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAuditLines).extracting("TransactionIdAPI", "AD_Org_ID", "AD_Issue_ID", "ExportStatus", "M_ShipmentSchedule_ID")
				.containsExactlyInAnyOrder(
						tuple("transactionId", 10, 20, Exported.getCode(), 11),
						tuple("transactionId", 10, -1, ExportedAndError.getCode(), 21)
				);

		return audit;
	}
}