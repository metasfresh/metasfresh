package de.metas.util.web.audit;

import com.google.common.collect.Range;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.audit.apirequest.config.ApiAuditConfigRepository;
import de.metas.audit.apirequest.config.ApiCallOutcome;
import de.metas.audit.apirequest.config.NotificationTriggerType;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.Status;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.response.ApiResponseAuditRepository;
import de.metas.audit.data.service.CompositeDataAuditService;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.util.web.audit.dto.ApiResponse;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.user.UserGroupsCollection;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ApiAuditServiceTest
{
	private static final UserGroupId USER_GROUP_ID = UserGroupId.ofRepoId(20);
	private static final UserId ASSIGNED_USER_ID = UserId.ofRepoId(30);

	private INotificationBL notificationBL;
	private ApiAuditService apiAuditService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		notificationBL = Mockito.mock(INotificationBL.class);
		Services.registerService(INotificationBL.class, notificationBL);

		final UserGroupRepository userGroupRepository = Mockito.mock(UserGroupRepository.class);
		Mockito.when(userGroupRepository.getByUserGroupId(USER_GROUP_ID)).thenReturn(UserGroupsCollection.of(Collections.singletonList(
				UserGroupUserAssignment.builder()
						.userGroupId(USER_GROUP_ID)
						.userId(ASSIGNED_USER_ID)
						.validDates(Range.all())
						.build())));

		apiAuditService = new ApiAuditService(
				Mockito.mock(ApiAuditConfigRepository.class),
				Mockito.mock(ApiRequestAuditRepository.class),
				Mockito.mock(ApiResponseAuditRepository.class),
				Mockito.mock(ApiAuditRequestLogDAO.class),
				userGroupRepository,
				Mockito.mock(CompositeDataAuditService.class));
	}

	private static ApiAuditConfig config(final NotificationTriggerType trigger)
	{
		return ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.notifyUserInCharge(trigger)
				.userGroupInChargeId(USER_GROUP_ID)
				.build();
	}

	private static ApiRequestAudit requestAudit()
	{
		return ApiRequestAudit.builder()
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(40))
				.orgId(OrgId.ofRepoId(10))
				.roleId(RoleId.ofRepoId(50))
				.userId(UserId.ofRepoId(60))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.status(Status.PROCESSED)
				.path("/api/v2/some/path")
				.time(Instant.now())
				.build();
	}

	private List<UserNotificationRequest> notify(final NotificationTriggerType trigger, final ApiCallOutcome outcome)
	{
		apiAuditService.notifyUserInCharge(config(trigger), requestAudit(), outcome);

		final ArgumentCaptor<UserNotificationRequest> captor = ArgumentCaptor.forClass(UserNotificationRequest.class);
		Mockito.verify(notificationBL, Mockito.atLeast(0)).send(captor.capture());
		return captor.getAllValues();
	}

	@Test
	void errorOr207_notifiesOnError_withFailureMessage()
	{
		final List<UserNotificationRequest> sent = notify(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR, ApiCallOutcome.ofHttpStatus(500));
		assertThat(sent).hasSize(1);
		assertThat(sent.get(0).getRecipient().getUserId()).isEqualTo(ASSIGNED_USER_ID);
		assertThat(sent.get(0).getContentADMessage().toAD_Message()).isEqualTo("de.metas.util.web.audit.invocation_failed");
	}

	@Test
	void errorOr207_notifiesOn207_withPartialMessage()
	{
		final List<UserNotificationRequest> sent = notify(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR, ApiCallOutcome.ofHttpStatus(207));
		assertThat(sent).hasSize(1);
		assertThat(sent.get(0).getContentADMessage().toAD_Message()).isEqualTo("de.metas.util.web.audit.invocation_partially_failed");
	}

	@Test
	void errorOr207_doesNotNotifyOn200()
	{
		assertThat(notify(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR, ApiCallOutcome.ofHttpStatus(200))).isEmpty();
	}

	@Test
	void onlyOnError_doesNotNotifyOn207()
	{
		assertThat(notify(NotificationTriggerType.ONLY_ON_ERROR, ApiCallOutcome.ofHttpStatus(207))).isEmpty();
	}

	@Test
	void always_notifiesOn200_withSuccessMessage()
	{
		final List<UserNotificationRequest> sent = notify(NotificationTriggerType.ALWAYS, ApiCallOutcome.ofHttpStatus(200));
		assertThat(sent).hasSize(1);
		assertThat(sent.get(0).getContentADMessage().toAD_Message()).isEqualTo("de.metas.util.web.audit.successful_invocation");
	}

	@Test
	void always_notifiesOn207_withPartialMessage()
	{
		final List<UserNotificationRequest> sent = notify(NotificationTriggerType.ALWAYS, ApiCallOutcome.ofHttpStatus(207));
		assertThat(sent).hasSize(1);
		assertThat(sent.get(0).getContentADMessage().toAD_Message()).isEqualTo("de.metas.util.web.audit.invocation_partially_failed");
	}

	@Test
	void auditResponse_with207_onErrorOr207Config_notifiesWithPartialMessage()
	{
		final ApiResponse response = ApiResponse.builder().statusCode(207).build();

		apiAuditService.auditResponse(config(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR), response, requestAudit());

		final ArgumentCaptor<UserNotificationRequest> captor = ArgumentCaptor.forClass(UserNotificationRequest.class);
		Mockito.verify(notificationBL, Mockito.times(1)).send(captor.capture());
		assertThat(captor.getValue().getContentADMessage().toAD_Message()).isEqualTo("de.metas.util.web.audit.invocation_partially_failed");
	}
}
