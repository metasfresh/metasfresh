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
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
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
	private DataExportAuditRepository dataExportAuditRepository;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		dataExportAuditRepository = new DataExportAuditRepository();
		huAuditService = new HUAuditService(new DataExportAuditService(dataExportAuditRepository, new DataExportAuditLogRepository()));
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

		assertThat(dataExportAuditRepository.getByTableRecordReference(TableRecordReference.of(I_M_HU.Table_Name, Integer.parseInt(HU_ID))))
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
