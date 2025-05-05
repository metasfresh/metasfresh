package de.metas.workflow.execution.approval.strategy;

import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.job.JobId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFResponsibleType;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService.GetUsersToApproveRequest;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyType;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DocApprovalStrategyServiceTest
{
	private static final WFResponsible WF_RESPONSIBLE_INVOKER = WFResponsible.builder().name("Invoker").id(WFResponsibleId.Invoker).type(WFResponsibleType.Human).build();

	private DocApprovalStrategyService service;
	private IUserBL userBL;

	private CurrencyId euroCurrencyId;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		this.service = DocApprovalStrategyService.newInstanceForUnitTesting();
		this.userBL = Services.get(IUserBL.class);

		final PlainCurrencyDAO currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		this.euroCurrencyId = currencyDAO.getOrCreateByCurrencyCode(CurrencyCode.EUR).getId();

		DocApprovalStrategyTestHelper.createClient(ClientId.METASFRESH);
		this.orgId = AdempiereTestHelper.createOrgWithTimeZone();
	}

	private Money euro(String value) {return Money.of(value, euroCurrencyId);}

	String toUsersListString(final List<UserId> userIds)
	{
		return userIds.stream().map(userId -> userBL.getUserFullNameById(userId)).collect(Collectors.joining(", "));
	}

	@Nested
	class getUsersToApprove_standardScenario
	{
		private DocApprovalStrategyId approvalStrategyId;
		private UserId documentOwnerId;
		private UserId projectManagerId;
		private UserId approvalUserId1;
		private UserId approvalUserId2;
		private UserId approvalUserId3;
		private UserId approvalUserId4;
		private UserId approvalUserId5;
		private UserId cfoId;
		private UserId ceoId;

		@BeforeEach
		void beforeEach()
		{
			final JobId job_CFO = DocApprovalStrategyTestHelper.createJob("CFO");
			final JobId job_CEO = DocApprovalStrategyTestHelper.createJob("CEO");

			this.documentOwnerId = DocApprovalStrategyTestHelper.user().name("documentOwnerId").build();
			this.projectManagerId = DocApprovalStrategyTestHelper.user().name("projectManagerId").build();

			final RoleId approvalRole1 = DocApprovalStrategyTestHelper.role().name("approvalRole1").approvalAmt(euro("1000")).build();
			final RoleId approvalRole2 = DocApprovalStrategyTestHelper.role().name("approvalRole2").approvalAmt(euro("2000")).build();
			final RoleId approvalRole3 = DocApprovalStrategyTestHelper.role().name("approvalRole3").approvalAmt(euro("3000")).build();
			final RoleId approvalRole4 = DocApprovalStrategyTestHelper.role().name("approvalRole4").approvalAmt(euro("4000")).build();
			final RoleId approvalRole5 = DocApprovalStrategyTestHelper.role().name("approvalRole5").approvalAmt(euro("5000")).build();
			this.approvalUserId5 = DocApprovalStrategyTestHelper.user().name("approvalUserId5").roleId(approvalRole5).supervisorId(null).build();
			this.approvalUserId4 = DocApprovalStrategyTestHelper.user().name("approvalUserId4").roleId(approvalRole4).supervisorId(approvalUserId5).build();
			this.approvalUserId3 = DocApprovalStrategyTestHelper.user().name("approvalUserId3").roleId(approvalRole3).supervisorId(approvalUserId4).build();
			this.approvalUserId2 = DocApprovalStrategyTestHelper.user().name("approvalUserId2").roleId(approvalRole2).supervisorId(approvalUserId3).build();
			this.approvalUserId1 = DocApprovalStrategyTestHelper.user().name("approvalUserId1").roleId(approvalRole1).supervisorId(approvalUserId2).build();

			this.cfoId = DocApprovalStrategyTestHelper.user().name("CFO").jobId(job_CFO).build();
			this.ceoId = DocApprovalStrategyTestHelper.user().name("CEO").jobId(job_CEO).build();

			this.approvalStrategyId = DocApprovalStrategyTestHelper.createApprovalStrategy(
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Requestor).checkSupervisorStrategyType(CheckSupervisorStrategyType.DoNotCheck),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Requestor).checkSupervisorStrategyType(CheckSupervisorStrategyType.AllMathing).isProjectManagerSet(OptionalBoolean.FALSE),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.ProjectManager).isProjectManagerSet(OptionalBoolean.TRUE),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Job).jobId(job_CFO).minimumAmountThatRequiresApproval(euro("250")),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Job).jobId(job_CEO).minimumAmountThatRequiresApproval(euro("5000"))
			);
		}

		private GetUsersToApproveRequest.GetUsersToApproveRequestBuilder newRequest()
		{
			return GetUsersToApproveRequest.builder()
					.evaluationDate(LocalDate.parse("2023-12-07"))
					.docApprovalStrategyId(approvalStrategyId)
					.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
					//.amountToApprove(...)
					.workflowInvokerId(documentOwnerId)
					.workflowResponsible(WF_RESPONSIBLE_INVOKER)
					.documentOwnerId(documentOwnerId)
					//.requestorId(...)
					//.projectManagerId(...)
					;
		}

		@Test
		void euro100()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest()
					.amountToApprove(euro("100"))
					.requestorId(approvalUserId1)
					.build());

			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1);
		}

		@Test
		void euro1000()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("1000")).requestorId(approvalUserId1).build());
			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, cfoId);
		}

		@Test
		void euro2000()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("2000")).requestorId(approvalUserId1).build());
			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, approvalUserId3, cfoId);
		}

		@Test
		void euro3000()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("3000")).requestorId(approvalUserId1).build());
			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, approvalUserId3, approvalUserId4, cfoId);
		}

		@Test
		void euro4000()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("4000")).requestorId(approvalUserId1).build());
			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, approvalUserId3, approvalUserId4, approvalUserId5, cfoId);
		}

		@Test
		void euro5000()
		{
			final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("5000")).requestorId(approvalUserId1).build());
			System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
			assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, approvalUserId3, approvalUserId4, approvalUserId5, cfoId, ceoId);
		}

		@Nested
		class withProjectManager
		{
			@Test
			void euro1()
			{
				final List<UserId> userIdsToApprove = service.getUsersToApprove(newRequest().amountToApprove(euro("1")).requestorId(approvalUserId1).projectManagerId(projectManagerId).build());
				System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
				assertThat(userIdsToApprove).containsOnly(approvalUserId1, projectManagerId);
			}

			@Test
			void euro250()
			{
				final GetUsersToApproveRequest request = newRequest().amountToApprove(euro("250")).requestorId(approvalUserId1).projectManagerId(projectManagerId).build();
				final List<UserId> userIdsToApprove = service.getUsersToApprove(request);
				System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
				assertThat(userIdsToApprove).containsOnly(approvalUserId1, projectManagerId, cfoId);
			}

			@Test
			void euro5000()
			{
				final GetUsersToApproveRequest request = newRequest().amountToApprove(euro("5000")).requestorId(approvalUserId1).projectManagerId(projectManagerId).build();
				final List<UserId> userIdsToApprove = service.getUsersToApprove(request);
				System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
				assertThat(userIdsToApprove).containsOnly(approvalUserId1, projectManagerId, cfoId, ceoId);
			}
		}
	}
}