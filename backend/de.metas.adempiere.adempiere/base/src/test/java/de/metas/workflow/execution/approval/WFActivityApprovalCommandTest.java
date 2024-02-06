package de.metas.workflow.execution.approval;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Order;
import de.metas.common.util.time.SystemTime;
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
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.WFResponsibleId;
import de.metas.workflow.WFResponsibleType;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import de.metas.workflow.execution.approval.WFActivityApprovalCommand.WFActivityApprovalCommandBuilder;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyId;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyTestHelper;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyType;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WFActivityApprovalCommandTest
{
	private static final WFResponsible WF_RESPONSIBLE_INVOKER = WFResponsible.builder().name("Invoker").id(WFResponsibleId.Invoker).type(WFResponsibleType.Human).build();

	private WFApprovalRequestRepository wfApprovalRequestRepository;
	private DocApprovalStrategyService docApprovalStrategyService;

	private static final TableRecordReference documentRef = TableRecordReference.of(I_C_Order.Table_Name, 123);
	private static final WFProcessId wfProcessId = WFProcessId.ofRepoId(1);
	private static final WFActivityId wfActivityId = WFActivityId.ofRepoId(2);
	private CurrencyId euroCurrencyId;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		this.wfApprovalRequestRepository = new WFApprovalRequestRepository();
		this.docApprovalStrategyService = DocApprovalStrategyService.newInstanceForUnitTesting();

		final PlainCurrencyDAO currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		this.euroCurrencyId = currencyDAO.getOrCreateByCurrencyCode(CurrencyCode.EUR).getId();

		DocApprovalStrategyTestHelper.createClient(ClientId.METASFRESH);
		this.orgId = AdempiereTestHelper.createOrgWithTimeZone();
	}

	private Money euro(String value) {return Money.of(value, euroCurrencyId);}

	private WFActivityApprovalCommandBuilder newCommand()
	{
		return WFActivityApprovalCommand.builder()
				.wfApprovalRequestRepository(wfApprovalRequestRepository)
				.docApprovalStrategyService(docApprovalStrategyService)
				//
				.evaluationDate(LocalDate.parse("2023-12-07"))
				.documentRef(documentRef)
				.additionalDocumentInfo(WFApprovalRequest.AdditionalDocumentInfo.builder().build())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, orgId))
				//.docApprovalStrategyId(docApprovalStrategyId)
				//
				//.documentOwnerId(documentOwnerId)
				//.requestorId(...)
				//.projectManagerId(...)
				//
				//.amountToApprove(null),
				//
				//.wfInvokerId(documentOwnerId)
				.wfResponsible(WF_RESPONSIBLE_INVOKER)
				.wfProcessId(wfProcessId)
				.wfActivityId(wfActivityId)
				;
	}

	@Test
	void noUserForJob()
	{
		final UserId userId = DocApprovalStrategyTestHelper.user().name("userId").build();

		final WFActivityApprovalCommand command = newCommand()
				.docApprovalStrategyId(DocApprovalStrategyTestHelper.createApprovalStrategy(
						DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Job).jobId(DocApprovalStrategyTestHelper.createJob("CFO"))
				))
				.documentOwnerId(userId)
				.requestorId(userId)
				.wfInvokerId(userId)
				.amountToApprove(euro("100"))
				.build();

		assertThatThrownBy(command::execute)
				.hasMessageStartingWith("DocApprovalStrategyTypeHandler.NoUsersFoundForJob - CFO");
	}

	@Nested
	class multiLevelScenario
	{
		private DocApprovalStrategyId docApprovalStrategyId;
		private UserId documentOwnerId;
		private UserId projectManagerId;
		private UserId approvalUserId1;
		private UserId approvalUserId2;
		private UserId approvalUserId3;
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
			this.approvalUserId3 = DocApprovalStrategyTestHelper.user().name("approvalUserId3").roleId(approvalRole3).supervisorId(null).build();
			this.approvalUserId2 = DocApprovalStrategyTestHelper.user().name("approvalUserId2").roleId(approvalRole2).supervisorId(approvalUserId3).build();
			this.approvalUserId1 = DocApprovalStrategyTestHelper.user().name("approvalUserId1").roleId(approvalRole1).supervisorId(approvalUserId2).build();

			this.cfoId = DocApprovalStrategyTestHelper.user().name("CFO").jobId(job_CFO).build();
			this.ceoId = DocApprovalStrategyTestHelper.user().name("CEO").jobId(job_CEO).build();

			this.docApprovalStrategyId = DocApprovalStrategyTestHelper.createApprovalStrategy(
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Requestor).checkSupervisorStrategyType(CheckSupervisorStrategyType.AllMathing).isProjectManagerSet(OptionalBoolean.FALSE),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.ProjectManager).isProjectManagerSet(OptionalBoolean.TRUE),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Job).jobId(job_CFO).minimumAmountThatRequiresApproval(euro("250")),
					DocApprovalStrategyLine.builder().type(DocApprovalStrategyType.Job).jobId(job_CEO).minimumAmountThatRequiresApproval(euro("5000"))
			);
		}

		private WFActivityApprovalCommandBuilder newCommand()
		{
			return WFActivityApprovalCommandTest.this.newCommand()
					.docApprovalStrategyId(docApprovalStrategyId)
					//
					.documentOwnerId(documentOwnerId)
					//.requestorId(...)
					//.projectManagerId(...)
					//
					//.amountToApprove(null),
					//
					.wfInvokerId(documentOwnerId);
		}

		private void approveByUserId(final UserId userId)
		{
			final WFApprovalRequestId requestId = wfApprovalRequestRepository.getByDocumentRef(documentRef)
					.stream()
					.filter(request -> UserId.equals(request.getUserId(), userId))
					.findFirst()
					.map(WFApprovalRequest::getId)
					.orElseThrow();

			wfApprovalRequestRepository.updateByIds(ImmutableSet.of(requestId), request -> request.approve(userId, SystemTime.asInstant()));
		}

		@Nested
		class withoutProjectManager
		{

			@Test
			void euro100()
			{
				WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("100")).requestorId(approvalUserId1);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId1));

				approveByUserId(approvalUserId1);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro1000()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("1000")).requestorId(approvalUserId1);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId1));

				approveByUserId(approvalUserId1);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId2));

				approveByUserId(approvalUserId2);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro2000()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("2000")).requestorId(approvalUserId1);
				WFActivityApprovalResponse response = command.execute();
				// System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
				// assertThat(userIdsToApprove).containsOnly(approvalUserId1, approvalUserId2, cfoId);
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId1));

				approveByUserId(approvalUserId1);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId2));

				approveByUserId(approvalUserId2);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId3));

				approveByUserId(approvalUserId3);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro3000()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("3000")).requestorId(approvalUserId1);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId1));

				approveByUserId(approvalUserId1);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId2));

				approveByUserId(approvalUserId2);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId3));

				approveByUserId(approvalUserId3);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro5000()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("5000")).requestorId(approvalUserId1);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId1));

				approveByUserId(approvalUserId1);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId2));

				approveByUserId(approvalUserId2);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(approvalUserId3));

				approveByUserId(approvalUserId3);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(ceoId));

				approveByUserId(ceoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}
		}

		@Nested
		class withProjectManager
		{
			@Test
			void euro1()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("1")).requestorId(approvalUserId1).projectManagerId(projectManagerId);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(projectManagerId));

				approveByUserId(projectManagerId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro250()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("250")).requestorId(approvalUserId1).projectManagerId(projectManagerId);
				WFActivityApprovalResponse response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(projectManagerId));

				approveByUserId(projectManagerId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}

			@Test
			void euro5000()
			{
				final WFActivityApprovalCommandBuilder command = newCommand().amountToApprove(euro("5000")).requestorId(approvalUserId1).projectManagerId(projectManagerId);
				WFActivityApprovalResponse response = command.execute();
				// final List<UserId> userIdsToApprove = service.getUsersToApprove(request);
				// System.out.println("userIdsToApprove=" + toUsersListString(userIdsToApprove));
				// assertThat(userIdsToApprove).containsOnly(projectManagerId, cfoId, ceoId);
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(projectManagerId));

				approveByUserId(projectManagerId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(cfoId));

				approveByUserId(cfoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.forwardTo(ceoId));

				approveByUserId(ceoId);
				response = command.execute();
				assertThat(response).isEqualTo(WFActivityApprovalResponse.APPROVED);
			}
		}
	}
}