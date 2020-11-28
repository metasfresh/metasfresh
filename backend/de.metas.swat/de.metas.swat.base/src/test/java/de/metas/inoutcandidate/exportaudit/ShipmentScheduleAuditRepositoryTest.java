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
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item;
import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Exported;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndError;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
				.orgId(OrgId.ANY)
				.exportStatus(ExportedAndError)
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
								.orgId(OrgId.ofRepoId(11))
								.exportStatus(ExportedAndError)
								.repoIdAware(shipmentScheduleId2)
								.build())
				.build();
		audit.setForwardedData("forwardedData");
		audit.setIssueId(AdIssueId.ofRepoId(30));

		// when
		shipmentScheduleAuditRepository.save(audit);

		// then
		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		final I_M_ShipmentSchedule_ExportAudit exportAudit = exportAudits.get(0);
		assertThat(exportAudit.getTransactionIdAPI()).isEqualTo("transactionId");
		assertThat(exportAudits).extracting("TransactionIdAPI", "AD_Org_ID", "AD_Issue_ID", "ExportStatus", "ForwardedData")
				.containsExactlyInAnyOrder(
						tuple("transactionId", 0, 30, ExportedAndError.getCode(), "forwardedData"));

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditLines = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditLines).extracting("M_ShipmentSchedule_ExportAudit_ID", "AD_Org_ID", "AD_Issue_ID", "ExportStatus", "M_ShipmentSchedule_ID")
				.containsExactlyInAnyOrder(
						tuple(exportAudit.getM_ShipmentSchedule_ExportAudit_ID(), 10, 20, Exported.getCode(), 11),
						tuple(exportAudit.getM_ShipmentSchedule_ExportAudit_ID(), 11, -1, ExportedAndError.getCode(), 21)
				);

		return audit;
	}
}