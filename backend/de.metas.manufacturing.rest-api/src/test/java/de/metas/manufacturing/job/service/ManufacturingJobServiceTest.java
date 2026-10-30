package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.acct.PPOrderPostingService;
import de.metas.manufacturing.config.ReceiveUnitType;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.service.commands.receive.ReceiveGoodsCommand;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonTUReceivingTarget;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_Workflow;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ManufacturingJobServiceTest
{
	private ManufacturingJobService manufacturingJobService;
	private ISysConfigDAO sysConfigDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(PPOrderCostDifferenceDistributor.class, mock(PPOrderCostDifferenceDistributor.class));
		SpringContextHolder.registerJUnitBean(PPOrderPostingService.class, mock(PPOrderPostingService.class));
		this.manufacturingJobService = ManufacturingJobService.newInstanceForUnitTesting();
		this.sysConfigDAO = Services.get(ISysConfigDAO.class);
	}

	@Nested
	class getDefaultFilters
	{
		@Test
		void empty()
		{
			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet()).isEmpty();
		}

		@Test
		void userPlant()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant);
		}

		@Test
		void allEnumValues()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant, TodayDateStartSchedule", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant, ManufacturingJobDefaultFilter.TodayDateStartSchedule);
		}
	}

	@Nested
	class withActivityCompleted_lastActivity
	{
		private I_C_UOM uom;
		private ProductId productId;
		private PPOrderCostDifferenceDistributor distributor;
		private IHUPPOrderBL ppOrderBL;
		private ITrxManager trxManager;
		private ManufacturingJobService service;

		private final List<TableRecordReference> postedDocuments = new ArrayList<>();
		private final List<ITrx> trxsSeenByPoster = new ArrayList<>();
		@Nullable private RuntimeException postingFailure;
		/** What the accounting service does while we wait for its lock; null = it never finishes. */
		@Nullable private Runnable backgroundPostingProgress;
		/** What the accounting service does right before our post attempt; null = nothing. */
		@Nullable private Consumer<TableRecordReference> beforePostAttempt;
		private int sleeps;

		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();
			uom = BusinessTestHelper.createUomPCE();
			productId = BusinessTestHelper.createProductId("finished good", uom);

			distributor = mock(PPOrderCostDifferenceDistributor.class);
			SpringContextHolder.registerJUnitBean(PPOrderCostDifferenceDistributor.class, distributor);

			postedDocuments.clear();
			trxsSeenByPoster.clear();
			postingFailure = null;
			backgroundPostingProgress = null;
			beforePostAttempt = null;
			sleeps = 0;
			SpringContextHolder.registerJUnitBean(PPOrderPostingService.class, new PPOrderPostingService(this::fakePostNow, this::fakeSleep));

			ppOrderBL = spy(Services.get(IHUPPOrderBL.class));
			doNothing().when(ppOrderBL).closeOrder(any());
			Services.registerService(IHUPPOrderBL.class, ppOrderBL);

			trxManager = Services.get(ITrxManager.class);
			service = spy(ManufacturingJobService.newInstanceForUnitTesting());
		}

		@Test
		void handIssuedRawMaterial_distributorCalled_noSecondClose()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			when(distributor.distribute(job.getPpOrderId())).thenReturn(true);

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, times(1)).distribute(job.getPpOrderId());
			verify(ppOrderBL, never()).closeOrder(any());
		}

		@Test
		void onlyAutoIssuedPackaging_plainClose_distributorNotCalled()
		{
			final ManufacturingJob job = givenJob(1, bomLine(BOMComponentType.Packing, BOMComponentIssueMethod.IssueOnlyForReceived, "5", "5"));

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
		}

		@Test
		void nothingIssued_plainClose_distributorNotCalled()
		{
			final ManufacturingJob job = givenJob(1, bomLine(BOMComponentType.Component, BOMComponentIssueMethod.Issue, "0", "0"));

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
		}

		@Test
		void usageVarianceOnly_notCountedAsIssued_plainClose()
		{
			// the close's usage variance fills QtyDelivered only, never QtyDeliveredActual
			final ManufacturingJob job = givenJob(1, bomLine(BOMComponentType.Component, BOMComponentIssueMethod.Issue, "3", "0"));

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
		}

		@Test
		void handIssued_distributorReturnsFalse_plainClose()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			when(distributor.distribute(job.getPpOrderId())).thenReturn(false);
			final AtomicReference<ITrx> trxSeenByClose = new AtomicReference<>();
			doAnswer(invocation -> {
				trxSeenByClose.set(trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone));
				return null;
			}).when(ppOrderBL).closeOrder(any());

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, times(1)).distribute(job.getPpOrderId());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
			// the close must roll back together with the routing completion and the distribution
			assertThat(trxSeenByClose.get()).isNotNull();
			assertThat(trxManager.isNull(trxSeenByClose.get())).isFalse();
		}

		@Test
		void notLastActivity_noCloseNoDistribution()
		{
			final ManufacturingJob job = givenJob(2, handLine());
			final ManufacturingJobActivityId firstActivityId = job.getActivities().get(0).getId();

			service.withActivityCompleted(job, firstActivityId);

			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, never()).closeOrder(any());
		}

		@Test
		void distributorFailure_propagates_withinOneThreadInheritedTrx()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderRoutingActivityId routingActivityId = job.getActivities().get(0).getOrderRoutingActivityId();
			final AtomicReference<ITrx> trxSeenByDistributor = new AtomicReference<>();
			final AtomicReference<PPOrderRoutingActivityStatus> activityStatusSeenByDistributor = new AtomicReference<>();
			final AdempiereException failure = new AdempiereException("distribution failed");
			when(distributor.distribute(any())).thenAnswer(invocation -> {
				trxSeenByDistributor.set(trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone));
				activityStatusSeenByDistributor.set(Services.get(IPPOrderRoutingRepository.class)
						.getByOrderId(job.getPpOrderId())
						.getActivityById(routingActivityId)
						.getStatus());
				throw failure;
			});

			assertThatThrownBy(() -> service.withActivityCompleted(job, lastActivityId(job)))
					.hasMessageContaining("distribution failed");

			assertThat(trxSeenByDistributor.get()).isNotNull();
			assertThat(trxManager.isNull(trxSeenByDistributor.get())).isFalse();
			assertThat(activityStatusSeenByDistributor.get()).isEqualTo(PPOrderRoutingActivityStatus.COMPLETED);
		}

		@Test
		void deadlockOnFirstAttempt_retriedInFreshTrx()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final List<String> trxNames = new ArrayList<>();
			when(distributor.distribute(any())).thenAnswer(invocation -> {
				trxNames.add(trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone));
				if (trxNames.size() == 1)
				{
					throw new DBDeadLockDetectedException(new SQLException("deadlock detected", "40P01"), null);
				}
				return true;
			});

			service.withActivityCompleted(job, lastActivityId(job));

			verify(distributor, times(2)).distribute(job.getPpOrderId());
			assertThat(trxNames).hasSize(2).doesNotContainNull();
			assertThat(trxNames.get(0)).isNotEqualTo(trxNames.get(1));
			verify(ppOrderBL, never()).closeOrder(any());
		}

		@Test
		void outerTrxOpen_noRetry_runsOnceJoined()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final DBDeadLockDetectedException deadlock = new DBDeadLockDetectedException(new SQLException("deadlock detected", "40P01"), null);
			final AtomicReference<String> trxNameSeenByDistributor = new AtomicReference<>();
			when(distributor.distribute(any())).thenAnswer(invocation -> {
				trxNameSeenByDistributor.set(trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone));
				throw deadlock;
			});

			final AtomicReference<String> outerTrxName = new AtomicReference<>();
			final AtomicReference<Throwable> thrown = new AtomicReference<>();
			trxManager.runInThreadInheritedTrx(() -> {
				outerTrxName.set(trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone));
				thrown.set(catchThrowable(() -> service.withActivityCompleted(job, lastActivityId(job))));
			});

			assertThat(thrown.get()).isSameAs(deadlock);
			assertThat(outerTrxName.get()).isNotNull();
			assertThat(trxNameSeenByDistributor.get()).isEqualTo(outerTrxName.get());
			verify(distributor, times(1)).distribute(job.getPpOrderId());
		}

		@Test
		void unpostedCollectors_settlesWithAllIssuesAndReceipts()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderId ppOrderId = job.getPpOrderId();
			final I_PP_Cost_Collector issue = givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			final I_PP_Cost_Collector receipt = givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Completed, true);
			givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Drafted, false);

			final List<TableRecordReference> postedWhenDistributing = new ArrayList<>();
			when(distributor.distribute(ppOrderId)).thenAnswer(invocation -> {
				postedWhenDistributing.addAll(postedDocuments);
				return true;
			});

			service.withActivityCompleted(job, lastActivityId(job));

			// the order first: its posting creates the PP_Order_Cost rows the collectors accumulate into
			assertThat(postedWhenDistributing).containsExactly(
					TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId.getRepoId()),
					TableRecordReference.of(issue),
					TableRecordReference.of(receipt));
			assertThat(trxsSeenByPoster).allMatch(trxManager::isNull);
			verify(distributor, times(1)).distribute(ppOrderId);
		}

		@Test
		void postingErrorOfExistingCollectors_propagates_beforeCloseTrx()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderId ppOrderId = job.getPpOrderId();
			final I_PP_Order ppOrder = InterfaceWrapperHelper.load(ppOrderId, I_PP_Order.class);
			ppOrder.setPosted(true);
			InterfaceWrapperHelper.saveRecord(ppOrder);
			givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			postingFailure = new AdempiereException("posting failed");

			assertThatThrownBy(() -> service.withActivityCompleted(job, lastActivityId(job)))
					.isSameAs(postingFailure);

			assertThat(trxsSeenByPoster).hasSize(1);
			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, never()).closeOrder(any());
			assertThat(Services.get(IPPOrderRoutingRepository.class)
					.getByOrderId(ppOrderId)
					.getActivityById(job.getActivities().get(0).getOrderRoutingActivityId())
					.getStatus())
					.isEqualTo(PPOrderRoutingActivityStatus.NOT_STARTED);
		}

		@Test
		void noHandIssue_existingCollectorsNotPosted_plainClose()
		{
			final ManufacturingJob job = givenJob(1, bomLine(BOMComponentType.Packing, BOMComponentIssueMethod.IssueOnlyForReceived, "5", "5"));
			givenCostCollector(job.getPpOrderId(), X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			postingFailure = new AdempiereException("posting failed");

			service.withActivityCompleted(job, lastActivityId(job));

			assertThat(trxsSeenByPoster).isEmpty();
			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
		}

		@Test
		void collectorBeingPostedInBackground_waitsThenSettles()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderId ppOrderId = job.getPpOrderId();
			final I_PP_Cost_Collector receipt = givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			receipt.setProcessing(true);
			InterfaceWrapperHelper.saveRecord(receipt);
			backgroundPostingProgress = backgroundPostingFinishes(receipt);
			when(distributor.distribute(ppOrderId)).thenReturn(true);

			service.withActivityCompleted(job, lastActivityId(job));

			assertThat(sleeps).isPositive();
			assertThat(postedDocuments).containsExactly(TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId.getRepoId()));
			verify(distributor, times(1)).distribute(ppOrderId);
		}

		@Test
		void collectorLockedBetweenCheckAndPost_waitsThenSettles()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderId ppOrderId = job.getPpOrderId();
			final I_PP_Cost_Collector receipt = givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			// the accounting service takes the lock between our check and our lock, so our first post attempt fails to lock
			final TableRecordReference receiptRef = TableRecordReference.of(receipt);
			final AtomicReference<Boolean> lockTaken = new AtomicReference<>(false);
			beforePostAttempt = documentRef -> {
				if (documentRef.equals(receiptRef) && !lockTaken.getAndSet(true))
				{
					final I_PP_Cost_Collector reloaded = InterfaceWrapperHelper.load(receipt.getPP_Cost_Collector_ID(), I_PP_Cost_Collector.class);
					reloaded.setProcessing(true);
					InterfaceWrapperHelper.saveRecord(reloaded);
				}
			};
			backgroundPostingProgress = backgroundPostingFinishes(receipt);
			when(distributor.distribute(ppOrderId)).thenReturn(true);

			service.withActivityCompleted(job, lastActivityId(job));

			assertThat(lockTaken.get()).isTrue();
			assertThat(sleeps).isPositive();
			assertThat(postedDocuments).containsExactly(TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId.getRepoId()));
			verify(distributor, times(1)).distribute(ppOrderId);
		}

		@Test
		void collectorLockedForever_failsClearly_beforeCloseTrx()
		{
			final ManufacturingJob job = givenJob(1, handLine());
			final PPOrderId ppOrderId = job.getPpOrderId();
			final I_PP_Cost_Collector receipt = givenCostCollector(ppOrderId, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt, X_PP_Cost_Collector.DOCSTATUS_Completed, false);
			receipt.setProcessing(true);
			InterfaceWrapperHelper.saveRecord(receipt);

			assertThatThrownBy(() -> service.withActivityCompleted(job, lastActivityId(job)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(TableRecordReference.of(receipt).toString())
					.hasMessageContaining("still being posted");

			assertThat(sleeps).isEqualTo(120);
			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, never()).closeOrder(any());
		}

		@Test
		void accountingDisabled_handIssued_plainClose_nothingPosted()
		{
			Services.get(ISysConfigDAO.class).setValue("org.adempiere.acct.Enabled", "N", ClientAndOrgId.SYSTEM);
			final ManufacturingJob job = givenJob(1, handLine());
			givenCostCollector(job.getPpOrderId(), X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, X_PP_Cost_Collector.DOCSTATUS_Completed, false);

			service.withActivityCompleted(job, lastActivityId(job));

			assertThat(trxsSeenByPoster).isEmpty();
			verify(distributor, never()).distribute(any());
			verify(ppOrderBL, times(1)).closeOrder(job.getPpOrderId());
		}

		/**
		 * Stands in for {@code Doc.post(force=false, ...)}: fails to lock a document another poster holds,
		 * throws {@link #postingFailure} if set, else marks the document posted.
		 */
		private void fakePostNow(final TableRecordReference documentRef, final ClientId clientIdNotUsed)
		{
			trxsSeenByPoster.add(trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone));
			if (beforePostAttempt != null)
			{
				beforePostAttempt.accept(documentRef);
			}
			final Object document = loadDocument(documentRef);
			InterfaceWrapperHelper.refresh(document);
			final boolean lockedByOtherPoster = document instanceof I_PP_Order
					? ((I_PP_Order)document).isProcessing()
					: ((I_PP_Cost_Collector)document).isProcessing();
			if (lockedByOtherPoster)
			{
				throw new AdempiereException("Cannot Lock - ReSubmit or RePost with Force");
			}
			if (postingFailure != null)
			{
				throw postingFailure;
			}

			InterfaceWrapperHelper.setValue(document, I_PP_Order.COLUMNNAME_Posted, true);
			InterfaceWrapperHelper.saveRecord(document);
			postedDocuments.add(documentRef);
		}

		private void fakeSleep(final Duration durationNotUsed)
		{
			sleeps++;
			if (backgroundPostingProgress != null)
			{
				backgroundPostingProgress.run();
			}
		}

		private Object loadDocument(final TableRecordReference documentRef)
		{
			return I_PP_Order.Table_Name.equals(documentRef.getTableName())
					? documentRef.getModel(I_PP_Order.class)
					: documentRef.getModel(I_PP_Cost_Collector.class);
		}

		/** The accounting service finishes posting the document, i.e. it is posted and unlocked. */
		private Runnable backgroundPostingFinishes(final I_PP_Cost_Collector costCollector)
		{
			return () -> {
				final I_PP_Cost_Collector reloaded = InterfaceWrapperHelper.load(costCollector.getPP_Cost_Collector_ID(), I_PP_Cost_Collector.class);
				reloaded.setPosted(true);
				reloaded.setProcessing(false);
				InterfaceWrapperHelper.saveRecord(reloaded);
			};
		}

		private I_PP_Cost_Collector givenCostCollector(final PPOrderId ppOrderId, final String costCollectorType, final String docStatus, final boolean posted)
		{
			final I_PP_Cost_Collector costCollector = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
			costCollector.setPP_Order_ID(ppOrderId.getRepoId());
			costCollector.setCostCollectorType(costCollectorType);
			costCollector.setDocStatus(docStatus);
			costCollector.setProcessed(!X_PP_Cost_Collector.DOCSTATUS_Drafted.equals(docStatus));
			costCollector.setPosted(posted);
			InterfaceWrapperHelper.saveRecord(costCollector);
			return costCollector;
		}

		private ManufacturingJobActivityId lastActivityId(final ManufacturingJob job)
		{
			return job.getActivities().get(job.getActivities().size() - 1).getId();
		}

		/** Hand-issued raw material: QtyDeliveredActual is what the issue collectors fill, QtyDelivered deliberately differs. */
		private BomLineSpec handLine()
		{
			return bomLine(BOMComponentType.Component, BOMComponentIssueMethod.Issue, "7", "5");
		}

		private BomLineSpec bomLine(final BOMComponentType componentType, final BOMComponentIssueMethod issueMethod, final String qtyDelivered, final String qtyDeliveredActual)
		{
			return new BomLineSpec(componentType, issueMethod, new BigDecimal(qtyDelivered), new BigDecimal(qtyDeliveredActual));
		}

		private ManufacturingJob givenJob(final int activityCount, final BomLineSpec bomLineSpec)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
			ppOrder.setM_Product_ID(productId.getRepoId());
			ppOrder.setC_UOM_ID(uom.getC_UOM_ID());
			InterfaceWrapperHelper.saveRecord(ppOrder);
			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

			final I_PP_Order_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
			bomLine.setPP_Order_ID(ppOrderId.getRepoId());
			bomLine.setM_Product_ID(BusinessTestHelper.createProductId("component", uom).getRepoId());
			bomLine.setC_UOM_ID(uom.getC_UOM_ID());
			bomLine.setComponentType(bomLineSpec.componentType.getCode());
			bomLine.setIssueMethod(bomLineSpec.issueMethod.getCode());
			bomLine.setQtyDelivered(bomLineSpec.qtyDelivered);
			bomLine.setQtyDeliveredActual(bomLineSpec.qtyDeliveredActual);
			InterfaceWrapperHelper.saveRecord(bomLine);

			final I_PP_Order_Workflow workflow = InterfaceWrapperHelper.newInstance(I_PP_Order_Workflow.class);
			workflow.setPP_Order_ID(ppOrderId.getRepoId());
			workflow.setDurationUnit(X_PP_Order_Workflow.DURATIONUNIT_Second);
			workflow.setAD_Workflow_ID(1);
			InterfaceWrapperHelper.saveRecord(workflow);

			final ImmutableList.Builder<ManufacturingJobActivity> activities = ImmutableList.builder();
			for (int i = 1; i <= activityCount; i++)
			{
				final I_PP_Order_Node node = InterfaceWrapperHelper.newInstance(I_PP_Order_Node.class);
				node.setPP_Order_ID(ppOrderId.getRepoId());
				node.setPP_Order_Workflow_ID(workflow.getPP_Order_Workflow_ID());
				node.setAD_Workflow_ID(1);
				node.setAD_WF_Node_ID(i);
				node.setValue("activity" + i);
				node.setName("Activity " + i);
				node.setS_Resource_ID(BusinessTestHelper.createManufacturingResource("workstation" + i, uom).getRepoId());
				node.setC_UOM_ID(uom.getC_UOM_ID());
				node.setPP_Activity_Type(PPRoutingActivityType.WorkReport.getCode());
				node.setDocStatus(PPOrderRoutingActivityStatus.NOT_STARTED.getDocStatus());
				InterfaceWrapperHelper.saveRecord(node);
				if (i == 1)
				{
					workflow.setPP_Order_Node_ID(node.getPP_Order_Node_ID());
					InterfaceWrapperHelper.saveRecord(workflow);
				}

				final PPOrderRoutingActivityId routingActivityId = PPOrderRoutingActivityId.ofRepoId(ppOrderId, node.getPP_Order_Node_ID());
				activities.add(ManufacturingJobActivity.builder()
						.id(ManufacturingJobActivityId.of(routingActivityId))
						.name(node.getName())
						.type(PPRoutingActivityType.WorkReport)
						.orderRoutingActivityId(routingActivityId)
						.routingActivityStatus(PPOrderRoutingActivityStatus.NOT_STARTED)
						.alwaysAvailableToUser(PPAlwaysAvailableToUser.DEFAULT)
						.build());
			}

			final ManufacturingJob job = ManufacturingJob.builder()
					.ppOrderId(ppOrderId)
					.documentNo("PP1")
					.dateStartSchedule(SystemTime.asZonedDateTime())
					.responsibleId(UserId.ofRepoId(1234))
					.warehouseId(WarehouseId.ofRepoId(1))
					.activities(activities.build())
					.build();

			// reloading the job after the close needs masterdata that is irrelevant here
			doReturn(job).when(service).getJobById(ppOrderId);
			return job;
		}
	}

	private static class BomLineSpec
	{
		final BOMComponentType componentType;
		final BOMComponentIssueMethod issueMethod;
		final BigDecimal qtyDelivered;
		final BigDecimal qtyDeliveredActual;

		BomLineSpec(final BOMComponentType componentType, final BOMComponentIssueMethod issueMethod, final BigDecimal qtyDelivered, final BigDecimal qtyDeliveredActual)
		{
			this.componentType = componentType;
			this.issueMethod = issueMethod;
			this.qtyDelivered = qtyDelivered;
			this.qtyDeliveredActual = qtyDeliveredActual;
		}
	}

	/**
	 * Catch weight with a mandatory scan is received one at a time: one receipt = one piece carrying the one scanned weight.
	 * Any other qty (e.g. from a stale cached mobile client or a direct API call) would put one piece's weight on N pieces.
	 * The guard itself (counting pieces in every receive mode) is unit-tested in ReceiveGoodsCommandTest;
	 * the effective "scan required" condition in MobileUIManufacturingConfigTest.
	 */
	@Nested
	class catchWeightScanRequired_receiveOneAtATime
	{
		private I_C_UOM uomPCE;
		private UomId uomKgId;
		private ProductId productId;

		@BeforeEach
		void beforeEach()
		{
			uomPCE = BusinessTestHelper.createUomPCE();
			uomKgId = UomId.ofRepoId(BusinessTestHelper.createUomKg().getC_UOM_ID());
			productId = BusinessTestHelper.createProductId("catch weight product", uomPCE);
		}

		@Test
		void receiveGoods_rejectsMoreThanOnePiece()
		{
			final UserId responsibleId = UserId.ofRepoId(1234);
			userConfig(responsibleId, null);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertThatThrownBy(() -> manufacturingJobService.receiveGoods(receiveFrom(line, "2"), job, SystemTime.asZonedDateTime()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		/**
		 * In TU receive mode the qty is NOT a number of TUs when the scan is required: 2 means 2 pieces and is rejected.
		 */
		@Test
		void receiveGoods_TUMode_rejectsMoreThanOnePiece()
		{
			final UserId responsibleId = UserId.ofRepoId(1235);
			userConfig(responsibleId, ReceiveUnitType.TU);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertThatThrownBy(() -> manufacturingJobService.receiveGoods(receiveFrom(line, "2"), job, SystemTime.asZonedDateTime()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		/**
		 * Weight captured at picking, not at receipt: the operator enters the number of blocks and scans the last block's label,
		 * whose lot and best-before date apply to the whole receipt. So a qty of N is a normal receipt, not rejected as "one at a time".
		 */
		@Test
		void receiveGoods_catchWeightNotCapturedAtReceipt_moreThanOnePieceNotRejectedAsOneAtATime()
		{
			final UserId responsibleId = UserId.ofRepoId(1236);
			userConfig(responsibleId, null, false);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertNotRejectedAsOneAtATime(() -> manufacturingJobService.receiveGoods(receiveFromWithoutWeight(line, "2"), job, SystemTime.asZonedDateTime()));
		}

		@Test
		void receiveGoods_TUMode_catchWeightNotCapturedAtReceipt_moreThanOnePieceNotRejectedAsOneAtATime()
		{
			final UserId responsibleId = UserId.ofRepoId(1237);
			userConfig(responsibleId, ReceiveUnitType.TU, false);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertNotRejectedAsOneAtATime(() -> manufacturingJobService.receiveGoods(receiveFromWithoutWeight(line, "2"), job, SystemTime.asZonedDateTime()));
		}

		private void assertNotRejectedAsOneAtATime(final ThrowingCallable receive)
		{
			// The receipt itself may fail later on this minimal fixture (no HU masterdata); only the one-piece guard matters here.
			// The qty of a receipt of N is unit-tested in ReceiveGoodsCommandTest.
			final String errorMessage = Optional.ofNullable(catchThrowable(receive)).map(Throwable::getMessage).orElse("");
			assertThat(errorMessage).doesNotContain(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		private void userConfig(final UserId responsibleId, @Nullable final ReceiveUnitType receiveUnitType)
		{
			userConfig(responsibleId, receiveUnitType, true);
		}

		private void userConfig(final UserId responsibleId, @Nullable final ReceiveUnitType receiveUnitType, final boolean isCaptureCatchWeightAtReceipt)
		{
			final I_MobileUI_UserProfile_MFG userConfig = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
			userConfig.setAD_User_ID(responsibleId.getRepoId());
			userConfig.setIsCaptureCatchWeightAtReceipt(isCaptureCatchWeightAtReceipt ? "Y" : "N");
			userConfig.setIsFinishedGoodsCatchWeightScanRequired("Y");
			if (receiveUnitType != null)
			{
				userConfig.setReceiveUnitType(receiveUnitType.getCode());
			}
			InterfaceWrapperHelper.saveRecord(userConfig);
		}

		private FinishedGoodsReceiveLine receiveLine()
		{
			return FinishedGoodsReceiveLine.builder()
					.productId(productId)
					.productName(TranslatableStrings.anyLanguage("product"))
					.productValue("P")
					.attributes(ImmutableAttributeSet.EMPTY)
					.qtyToReceive(Quantity.of(BigDecimal.TEN, uomPCE))
					.qtyReceived(Quantity.zero(uomPCE))
					.catchWeightUOMId(uomKgId)
					.build();
		}

		private ManufacturingJob job(final FinishedGoodsReceiveLine line, final UserId responsibleId)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
			ppOrder.setM_Product_ID(productId.getRepoId());
			ppOrder.setC_UOM_ID(uomPCE.getC_UOM_ID());
			InterfaceWrapperHelper.saveRecord(ppOrder);

			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
			final PPOrderRoutingActivityId routingActivityId = PPOrderRoutingActivityId.ofRepoId(ppOrderId, 1);
			return ManufacturingJob.builder()
					.ppOrderId(ppOrderId)
					.documentNo("PP1")
					.dateStartSchedule(SystemTime.asZonedDateTime())
					.responsibleId(responsibleId)
					.warehouseId(WarehouseId.ofRepoId(1))
					.activities(ImmutableList.of(ManufacturingJobActivity.builder()
							.id(ManufacturingJobActivityId.of(routingActivityId))
							.name("Receive")
							.type(PPRoutingActivityType.MaterialReceipt)
							.finishedGoodsReceive(FinishedGoodsReceive.builder().linesById(ImmutableMap.of(line.getId(), line)).build())
							.orderRoutingActivityId(routingActivityId)
							.routingActivityStatus(PPOrderRoutingActivityStatus.NOT_STARTED)
							.alwaysAvailableToUser(PPAlwaysAvailableToUser.DEFAULT)
							.build()))
					.build();
		}

		private JsonManufacturingOrderEvent.ReceiveFrom receiveFrom(final FinishedGoodsReceiveLine line, final String qty)
		{
			return JsonManufacturingOrderEvent.ReceiveFrom.builder()
					.lineId(line.getId().toJson())
					.qtyReceived(new BigDecimal(qty))
					.aggregateToTU(JsonTUReceivingTarget.builder()
							.newTU(JsonNewTUTarget.builder().tuPIItemProductId(HUPIItemProductId.ofRepoId(1000)).build())
							.build())
					.catchWeight(new BigDecimal("0.101"))
					.catchWeightUomSymbol("kg")
					.build();
		}

		private JsonManufacturingOrderEvent.ReceiveFrom receiveFromWithoutWeight(final FinishedGoodsReceiveLine line, final String qty)
		{
			return JsonManufacturingOrderEvent.ReceiveFrom.builder()
					.lineId(line.getId().toJson())
					.qtyReceived(new BigDecimal(qty))
					.aggregateToTU(JsonTUReceivingTarget.builder()
							.newTU(JsonNewTUTarget.builder().tuPIItemProductId(HUPIItemProductId.ofRepoId(1000)).build())
							.build())
					.build();
		}
	}
}
