/*
 * #%L
 * de.metas.handlingunits.mobileui
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

package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.handlingunits.JsonAllowedHUClearanceStatuses;
import de.metas.common.handlingunits.JsonClearanceStatusInfo;
import de.metas.common.handlingunits.JsonDisposalReason;
import de.metas.common.handlingunits.JsonDisposalReasonsList;
import de.metas.common.handlingunits.JsonGRAICodesResponse;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Data_Export_Audit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * The HU-manager endpoints live under the handling-units path that {@link HUAuditService} audits, but most of them do not return a
 * {@code JsonGetSingleHUResponse}. The service tells the response types apart by trying to deserialize the exported object as a
 * {@code JsonGetSingleHUResponse}; that probe has to fail for any other response type, otherwise the audit NPEs on a {@code null} HU and
 * the endpoint answers HTTP 500 (mobile HU manager: GRAI screen, clearance statuses and disposal reasons broken).
 */
class HUAuditServiceTest
{
	private static final String HU_ID = "1000023";
	private static final String HU_MANAGER_PATH = HandlingUnitsRestController.HU_REST_CONTROLLER_PATH + "/hu-manager";

	private HUAuditService huAuditService;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		huAuditService = new HUAuditService(new DataExportAuditService(new DataExportAuditRepository(), new DataExportAuditLogRepository()));
	}

	static Stream<GenericDataExportAuditRequest> huManagerResponsesThatAreNoSingleHU()
	{
		return Stream.of(
				request(HU_MANAGER_PATH + "/" + HU_ID + "/grai",
						JsonGRAICodesResponse.builder().graiCodes(ImmutableList.of("7613264.00309.100691412000")).build()),
				request(HU_MANAGER_PATH + "/allowedClearanceStatuses",
						JsonAllowedHUClearanceStatuses.builder()
								.clearanceStatuses(ImmutableList.of(JsonClearanceStatusInfo.builder().key("Q").caption("Quarantined").build()))
								.build()),
				request(HU_MANAGER_PATH + "/disposalReasons",
						JsonDisposalReasonsList.builder()
								.reasons(ImmutableList.of(JsonDisposalReason.builder().key("N").caption("Not needed").build()))
								.build()));
	}

	@ParameterizedTest
	@MethodSource("huManagerResponsesThatAreNoSingleHU")
	void responseThatIsNoSingleHU_isNotAudited(final GenericDataExportAuditRequest request)
	{
		assertThat(huAuditService.isHandled(request)).as("precondition: the URI is one HUAuditService audits").isTrue();

		assertThatCode(() -> huAuditService.performDataAuditForRequest(request)).doesNotThrowAnyException();

		assertThat(POJOLookupMap.get().getRecords(I_Data_Export_Audit.class))
				.as("no response here is a single HU, so nothing may be audited at all")
				.isEmpty();
	}

	private static GenericDataExportAuditRequest request(final String requestURI, final Object exportedObject)
	{
		return GenericDataExportAuditRequest.builder()
				.requestURI(requestURI)
				.exportedObject(exportedObject)
				.build();
	}
}
