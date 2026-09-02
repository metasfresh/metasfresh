package de.metas.ui.web.view.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.related_documents.relation_type.RelationTypeId;
import de.metas.document.references.related_documents.relation_type.RelationTypeRelatedDocumentsProvidersFactory;
import de.metas.document.references.related_documents.relation_type.SpecificRelationTypeRelatedDocumentsProvider;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessOpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import org.adempiere.service.ISysConfigBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import org.compiere.model.MQuery;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RelationTypeInOverlayProcessTest
{
	@BeforeEach
	void beforeEach()
	{
		// NOTE: initMocks (not openMocks) on purpose - openMocks is not available in the Mockito version used here.
		MockitoAnnotations.initMocks(this);
		AdempiereTestHelper.get().init();
		Services.registerService(IADProcessDAO.class, mock(IADProcessDAO.class));

		// SysConfig is read while resolving the max-selection precondition; return the passed default so the limit is active.
		final ISysConfigBL sysConfigBL = mock(ISysConfigBL.class);
		when(sysConfigBL.getIntValue(anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(1));
		Services.registerService(ISysConfigBL.class, sysConfigBL);

		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(100));
	}

	@Nested
	class CheckPreconditionsApplicable
	{
		RelationTypeRelatedDocumentsProvidersFactory providerFactory = mock(RelationTypeRelatedDocumentsProvidersFactory.class);
		IViewsRepository viewsRepo = mock(IViewsRepository.class);

		@Test
		void accepts_whenMultipleRowsSelectedAndWindowIdPresent()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.getAdWindowId()).thenReturn(AdWindowId.ofRepoId(100));
			when(ctx.isNoSelection()).thenReturn(false);
			when(ctx.isMoreThanAllowedSelected(anyInt())).thenReturn(false);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isTrue();
		}

		@Test
		void rejects_whenNoWindowId()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.getAdWindowId()).thenReturn(null);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void accepts_whenSingleSelectionAndWindowIdPresent()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.getAdWindowId()).thenReturn(AdWindowId.ofRepoId(100));
			when(ctx.isNoSelection()).thenReturn(false);
			when(ctx.isMoreThanAllowedSelected(anyInt())).thenReturn(false);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isTrue();
		}

		@Test
		void rejects_whenNoSelection()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.getAdWindowId()).thenReturn(AdWindowId.ofRepoId(100));
			when(ctx.isNoSelection()).thenReturn(true);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void rejects_whenMoreThanAllowedSelected()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.getAdWindowId()).thenReturn(AdWindowId.ofRepoId(100));
			when(ctx.isNoSelection()).thenReturn(false);
			when(ctx.isMoreThanAllowedSelected(anyInt())).thenReturn(true);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isFalse();
		}
	}

	@Nested
	class DoIt
	{
		RelationTypeRelatedDocumentsProvidersFactory providerFactory = mock(RelationTypeRelatedDocumentsProvidersFactory.class);
		IViewsRepository viewsRepo = mock(IViewsRepository.class);

		@Test
		void throws_whenNoRelatedDocumentsFound()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any()))
					.thenReturn(Collections.emptyList());

			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId);

			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NO_RELATED_DOCS_FOUND");
		}

		@Test
		void throws_whenRelationTypeProviderNotFound()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(99);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.empty());

			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId);

			// provider is absent -> empty list -> AdempiereException about no docs found
			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void setsOverlayViewOnResult_whenRelatedDocsFound()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			final RelatedDocumentsCandidateGroup group = buildCandidateGroupWithOneEntry(WindowId.of(AdWindowId.ofRepoId(200)));
			final IView mockView = mock(IView.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any()))
					.thenReturn(ImmutableList.of(group));
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId);
			process.doIt();

			assertThat(process.getResult().getWebuiViewToOpen()).isNotNull();
			assertThat(process.getResult().getWebuiViewToOpen().getViewId()).isEqualTo("200-someViewId");
		}

		@Test
		void throws_whenProcessInfoHasNoRelationTypeId()
		{
			// ProcessInfo with no AD_RelationType_ID set
			final RelationTypeInOverlayProcess process = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo);

			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("No AD_Process.AD_RelationType_ID defined");
		}

		@Test
		void doIt_whenOpenTargetIsNull_opensModalOverlay()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			final RelatedDocumentsCandidateGroup group = buildCandidateGroupWithOneEntry(WindowId.of(AdWindowId.ofRepoId(200)));
			final IView mockView = mock(IView.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any()))
					.thenReturn(ImmutableList.of(group));
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			// No .setOpenTarget(...) → ProcessInfo.openTarget is null → should default to ModalOverlay
			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId);
			process.doIt();

			assertThat(process.getResult().getWebuiViewToOpen()).isNotNull();
			assertThat(process.getResult().getWebuiViewToOpen().getTarget())
					.isEqualTo(ProcessExecutionResult.ViewOpenTarget.ModalOverlay);
		}

		@Test
		void doIt_whenOpenTargetIsModalOverlay_opensModalOverlay()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			final RelatedDocumentsCandidateGroup group = buildCandidateGroupWithOneEntry(WindowId.of(AdWindowId.ofRepoId(200)));
			final IView mockView = mock(IView.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any()))
					.thenReturn(ImmutableList.of(group));
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId, ProcessOpenTarget.ModalOverlay);
			process.doIt();

			assertThat(process.getResult().getWebuiViewToOpen()).isNotNull();
			assertThat(process.getResult().getWebuiViewToOpen().getTarget())
					.isEqualTo(ProcessExecutionResult.ViewOpenTarget.ModalOverlay);
		}

		@Test
		void doIt_whenOpenTargetIsNewBrowserTab_opensInNewBrowserTab()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			final RelatedDocumentsCandidateGroup group = buildCandidateGroupWithOneEntry(WindowId.of(AdWindowId.ofRepoId(200)));
			final IView mockView = mock(IView.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId)))
					.thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any()))
					.thenReturn(ImmutableList.of(group));
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildProcess(providerFactory, viewsRepo, relationTypeId, ProcessOpenTarget.NewBrowserTab);
			process.doIt();

			assertThat(process.getResult().getWebuiViewToOpen()).isNotNull();
			assertThat(process.getResult().getWebuiViewToOpen().getTarget())
					.isEqualTo(ProcessExecutionResult.ViewOpenTarget.NewBrowserTab);
		}

		// --- helpers ---

		private RelationTypeInOverlayProcess buildProcess(
				final RelationTypeRelatedDocumentsProvidersFactory factory,
				final IViewsRepository viewsRepo,
				final RelationTypeId relationTypeId)
		{
			return buildProcess(factory, viewsRepo, relationTypeId, null);
		}

		private RelationTypeInOverlayProcess buildProcess(
				final RelationTypeRelatedDocumentsProvidersFactory factory,
				final IViewsRepository viewsRepo,
				final RelationTypeId relationTypeId,
				final ProcessOpenTarget openTarget)
		{
			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setRecord(TableRecordReference.of("C_Order", 101))
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(relationTypeId)
					.setOpenTarget(openTarget)
					.build();

			// single record (C_Order/101) is set on the ProcessInfo, so the real getSelectedSourceRecordRefs() resolves it;
			// map the zoom source by that ref (a "fixed zoom" is just a single-entry map).
			final IZoomSource mockZoomSource = mock(IZoomSource.class);
			return RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					factory, viewsRepo, processInfo,
					null,
					ImmutableMap.of(TableRecordReference.of("C_Order", 101), mockZoomSource));
		}
	}

	@Nested
	class DoItMultiSelection
	{
		final RelationTypeRelatedDocumentsProvidersFactory providerFactory = mock(RelationTypeRelatedDocumentsProvidersFactory.class);
		final IViewsRepository viewsRepo = mock(IViewsRepository.class);

		@Test
		void opensSingleUnionView_withOredWhereClauses_forMultipleSelectedRows()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=2")));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final CreateViewRequest request = captor.getValue();

			// One combined view, not one per row, and no per-row referencing document (which would collapse the union to one source)
			assertThat(request.getReferencingDocumentPaths()).isEmpty();
			assertThat(request.getStickyFilters().toList()).hasSize(1);

			final DocumentFilter unionFilter = request.getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters()).hasSize(1);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1) OR (PC.C_OrderLine_ID=2)");

			assertThat(process.getResult().getWebuiViewToOpen()).isNotNull();
			assertThat(process.getResult().getWebuiViewToOpen().getViewId()).isEqualTo("200-someViewId");
		}

		@Test
		void unionUsesOnlyContributingSources_whenSomeHaveNoRelatedDocs()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(Collections.emptyList());

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final DocumentFilter unionFilter = captor.getValue().getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1)");
		}

		@Test
		void skipsUnloadableSourceRow_andOpensUnionOfTheRest()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference loadableRef = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference unloadableRef = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			// unloadableRef has no zoom source -> createZoomSource throws (simulates a concurrently-deleted / unloadable row)
			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(loadableRef, unloadableRef),
					ImmutableMap.of(loadableRef, zoom1));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final DocumentFilter unionFilter = captor.getValue().getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1)");
		}

		@Test
		void throws_whenNoRelatedDocsForAnySelectedRow()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any())).thenReturn(Collections.emptyList());

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NO_RELATED_DOCS_FOUND");
		}

		@Test
		void skipsCandidatesWithNullQuery_fromTheUnion()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));
			// a candidate whose querySupplier returns null (no MQuery at all) -> query != null guard -> skipped
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithOneEntry(targetWindow)));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final DocumentFilter unionFilter = captor.getValue().getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1)");
		}

		@Test
		void skipsCandidatesWithBlankWhereClause_fromTheUnion()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));
			// a candidate that yields documents but whose (non-null) MQuery has no restriction, so its where-clause is
			// blank -> Check.isBlank(whereClause) guard -> skipped
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithBlankWhereClause(targetWindow)));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final DocumentFilter unionFilter = captor.getValue().getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1)");
		}

		@Test
		void deduplicatesIdenticalWhereClauses_inTheUnion()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			// both selected rows resolve to the same related-documents where-clause
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_PurchaseCandidate_ID=7")));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_PurchaseCandidate_ID=7")));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final DocumentFilter unionFilter = captor.getValue().getStickyFilters().toList().get(0);
			// identical clause is not repeated as "(...) OR (...)"
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_PurchaseCandidate_ID=7)");
		}

		@Test
		void throws_whenAllSourceRowsAreUnloadable()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);

			// no zoom sources -> createZoomSource throws for both -> all rows skipped -> whereClauses empty
			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of());

			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NO_RELATED_DOCS_FOUND");
		}

		@Test
		void usesFirstWindowOnly_whenSelectedRowsTargetDifferentWindows()
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId firstWindow = WindowId.of(AdWindowId.ofRepoId(200));
			final WindowId otherWindow = WindowId.of(AdWindowId.ofRepoId(201));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(firstWindow, "PC.C_OrderLine_ID=1")));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(otherWindow, "PC.C_OrderLine_ID=2")));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final RelationTypeInOverlayProcess process = buildMultiProcess(
					providerFactory, viewsRepo, relationTypeId,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			final CreateViewRequest request = captor.getValue();

			// The union is scoped to the first-established window; the second row's group (different window) is dropped.
			assertThat(request.getViewId().getWindowId()).isEqualTo(firstWindow);
			final DocumentFilter unionFilter = request.getStickyFilters().toList().get(0);
			assertThat(unionFilter.getParameters().get(0).getSqlWhereClause().getSql())
					.isEqualTo("(PC.C_OrderLine_ID=1)");
		}

		@Test
		void createsCombinedView_withAutoFiltersEnabled_whenAD_Process_IsUseAutoFiltersIsY()
		{
			final CreateViewRequest request = doIt_multiSelection_capturingRequest(/* isUseAutoFilters */ true);

			assertThat(request.isUseAutoFilters()).isTrue();
		}

		@Test
		void createsCombinedView_withAutoFiltersDisabled_whenAD_Process_IsUseAutoFiltersIsN()
		{
			final CreateViewRequest request = doIt_multiSelection_capturingRequest(/* isUseAutoFilters */ false);

			assertThat(request.isUseAutoFilters()).isFalse();
		}

		private CreateViewRequest doIt_multiSelection_capturingRequest(final boolean isUseAutoFilters)
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final WindowId targetWindow = WindowId.of(AdWindowId.ofRepoId(200));

			final TableRecordReference ref1 = TableRecordReference.of("C_OrderLine", 1);
			final TableRecordReference ref2 = TableRecordReference.of("C_OrderLine", 2);
			final IZoomSource zoom1 = mock(IZoomSource.class);
			final IZoomSource zoom2 = mock(IZoomSource.class);

			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom1), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=1")));
			when(provider.retrieveRelatedDocumentsCandidates(eq(zoom2), any()))
					.thenReturn(ImmutableList.of(buildCandidateGroupWithQuery(targetWindow, "PC.C_OrderLine_ID=2")));

			final IView mockView = mock(IView.class);
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final ProcessInfo processInfo = buildMultiProcessInfoWithAD_Process(relationTypeId, isUseAutoFilters);
			final RelationTypeInOverlayProcess process = RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					providerFactory, viewsRepo, processInfo,
					ImmutableList.of(ref1, ref2),
					ImmutableMap.of(ref1, zoom1, ref2, zoom2));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			return captor.getValue();
		}

		private RelationTypeInOverlayProcess buildMultiProcess(
				final RelationTypeRelatedDocumentsProvidersFactory factory,
				final IViewsRepository viewsRepo,
				final RelationTypeId relationTypeId,
				final List<TableRecordReference> selectedRecordRefs,
				final Map<TableRecordReference, IZoomSource> zoomSourcesByRecordRef)
		{
			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(relationTypeId)
					// no single record set -> multi selection
					.build();

			return RelationTypeInOverlayProcess.newInstanceForUnitTesting(factory, viewsRepo, processInfo, selectedRecordRefs, zoomSourcesByRecordRef);
		}

		/**
		 * Builds a multi-selection {@link ProcessInfo} backed by a real {@link I_AD_Process} row (via
		 * {@link ProcessInfo.ProcessInfoBuilder#setAD_Process}), so {@code AD_Process.IsUseAutoFilters} actually drives
		 * {@link ProcessInfo#isUseAutoFilters()} the way it does in production.
		 */
		private ProcessInfo buildMultiProcessInfoWithAD_Process(final RelationTypeId relationTypeId, final boolean isUseAutoFilters)
		{
			final I_AD_Process adProcess = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
			adProcess.setAD_Process_ID(1);
			adProcess.setType(org.compiere.model.X_AD_Process.TYPE_RelationTypeInOverlay);
			adProcess.setAD_RelationType_ID(relationTypeId.getRepoId());
			adProcess.setIsUseAutoFilters(isUseAutoFilters);

			return ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process(adProcess)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					// no single record set -> multi selection
					.build();
		}

		private RelatedDocumentsCandidateGroup buildCandidateGroupWithBlankWhereClause(final WindowId targetWindowId)
		{
			// non-null MQuery but with no restriction -> getWhereClause(true) is blank
			final MQuery mquery = new MQuery("PurchaseCandidate");

			return RelatedDocumentsCandidateGroup.builder()
					.candidate(RelatedDocumentsCandidate.builder()
							.id(RelatedDocumentsId.ofString("test-blank-where"))
							.internalName("test-blank-where")
							.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(targetWindowId.toAdWindowIdOrNull()))
							.priority(Priority.MEDIUM)
							.windowCaption(TranslatableStrings.anyLanguage("testCaption"))
							.querySupplier(() -> mquery)
							.documentsCountSupplier((permissions) -> 1)
							.build())
					.build();
		}

		private RelatedDocumentsCandidateGroup buildCandidateGroupWithQuery(final WindowId targetWindowId, final String directWhereClause)
		{
			final MQuery mquery = new MQuery("PurchaseCandidate");
			mquery.addRestriction(directWhereClause);

			return RelatedDocumentsCandidateGroup.builder()
					.candidate(RelatedDocumentsCandidate.builder()
							.id(RelatedDocumentsId.ofString("test-" + directWhereClause))
							.internalName("test-" + directWhereClause)
							.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(targetWindowId.toAdWindowIdOrNull()))
							.priority(Priority.MEDIUM)
							.windowCaption(TranslatableStrings.anyLanguage("testCaption"))
							.querySupplier(() -> mquery)
							.documentsCountSupplier((permissions) -> 1)
							.build())
					.build();
		}
	}

	@Nested
	class DoItSingleSelection
	{
		final RelationTypeRelatedDocumentsProvidersFactory providerFactory = mock(RelationTypeRelatedDocumentsProvidersFactory.class);
		final IViewsRepository viewsRepo = mock(IViewsRepository.class);

		@Test
		void throwsFriendlyNoRelatedDocs_whenTheSingleSelectedSourceRowCannotBeLoaded()
		{
			// A single selected Purchase Cockpit row that resolves to an unloadable source record (e.g. RV_PurchaseCockpit/0)
			// must not blow up with a raw 500; it must surface the friendly "no related documents" message.
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final TableRecordReference unloadableRef = TableRecordReference.of("C_OrderLine", 1);

			// Uses the multi-selection factory (List/Map variant) even though this is a single-selection test:
			// it is the only factory that lets us inject a per-ref zoom source that throws (an empty map), simulating
			// an unloadable source. The size-1 list still routes through the single-source path (createSingleSourceView).
			final RelationTypeInOverlayProcess process = RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					providerFactory, viewsRepo,
					buildProcessInfo(relationTypeId),
					ImmutableList.of(unloadableRef),
					ImmutableMap.of() /* no zoom source -> createZoomSource throws */);

			assertThatThrownBy(process::doIt)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NO_RELATED_DOCS_FOUND");
		}

		@Test
		void getSelectedSourceRecordRefs_fallsThrough_whenSingleRecordIdIsZero()
		{
			// A single selected view row whose record resolves to id 0 (RV_PurchaseCockpit/0) is not a usable single record:
			// getRecordRefOrNull() accepts id 0, so we must not short-circuit on it. With no selection where-clause available
			// there is nothing to resolve -> @NoSelection@.
			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setRecord("C_OrderLine", 0)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(RelationTypeId.ofRepoId(42))
					.build();

			final RelationTypeInOverlayProcess process = RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					providerFactory, viewsRepo, processInfo, null, ImmutableMap.of());

			assertThatThrownBy(process::getSelectedSourceRecordRefs)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("NoSelection");
		}

		@Test
		void getSelectedSourceRecordRefs_returnsSingleRef_whenRecordIdIsPositive()
		{
			final TableRecordReference ref = TableRecordReference.of("C_OrderLine", 101);
			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setRecord(ref)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(RelationTypeId.ofRepoId(42))
					.build();

			final RelationTypeInOverlayProcess process = RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					providerFactory, viewsRepo, processInfo, null, ImmutableMap.of());

			assertThat(process.getSelectedSourceRecordRefs()).containsExactly(ref);
		}

		@Test
		void createsView_withAutoFiltersEnabled_whenAD_Process_IsUseAutoFiltersIsY()
		{
			final CreateViewRequest request = doIt_singleSelection_capturingRequest(/* isUseAutoFilters */ true);

			assertThat(request.isUseAutoFilters()).isTrue();
		}

		@Test
		void createsView_withAutoFiltersDisabled_whenAD_Process_IsUseAutoFiltersIsN()
		{
			final CreateViewRequest request = doIt_singleSelection_capturingRequest(/* isUseAutoFilters */ false);

			assertThat(request.isUseAutoFilters()).isFalse();
		}

		private CreateViewRequest doIt_singleSelection_capturingRequest(final boolean isUseAutoFilters)
		{
			final RelationTypeId relationTypeId = RelationTypeId.ofRepoId(42);
			final SpecificRelationTypeRelatedDocumentsProvider provider = mock(SpecificRelationTypeRelatedDocumentsProvider.class);
			final RelatedDocumentsCandidateGroup group = buildCandidateGroupWithOneEntry(WindowId.of(AdWindowId.ofRepoId(200)));
			final IView mockView = mock(IView.class);

			when(providerFactory.findRelatedDocumentsProvider(eq(relationTypeId))).thenReturn(Optional.of(provider));
			when(provider.retrieveRelatedDocumentsCandidates(any(), any())).thenReturn(ImmutableList.of(group));
			when(viewsRepo.createView(any())).thenReturn(mockView);
			when(mockView.getViewId()).thenReturn(ViewId.ofViewIdString("200-someViewId"));

			final TableRecordReference ref = TableRecordReference.of("C_OrderLine", 101);
			final IZoomSource zoomSource = mock(IZoomSource.class);
			final ProcessInfo processInfo = buildProcessInfoWithAD_Process(relationTypeId, isUseAutoFilters);

			final RelationTypeInOverlayProcess process = RelationTypeInOverlayProcess.newInstanceForUnitTesting(
					providerFactory, viewsRepo, processInfo,
					ImmutableList.of(ref),
					ImmutableMap.of(ref, zoomSource));

			process.doIt();

			final ArgumentCaptor<CreateViewRequest> captor = ArgumentCaptor.forClass(CreateViewRequest.class);
			verify(viewsRepo).createView(captor.capture());
			return captor.getValue();
		}

		private ProcessInfo buildProcessInfo(final RelationTypeId relationTypeId)
		{
			return ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(relationTypeId)
					.build();
		}

		/**
		 * Builds a {@link ProcessInfo} backed by a real {@link I_AD_Process} row (via {@link ProcessInfo.ProcessInfoBuilder#setAD_Process}),
		 * so {@code AD_Process.IsUseAutoFilters} actually drives {@link ProcessInfo#isUseAutoFilters()} the way it does in production.
		 */
		private ProcessInfo buildProcessInfoWithAD_Process(final RelationTypeId relationTypeId, final boolean isUseAutoFilters)
		{
			final I_AD_Process adProcess = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
			adProcess.setAD_Process_ID(1);
			adProcess.setType(org.compiere.model.X_AD_Process.TYPE_RelationTypeInOverlay);
			adProcess.setAD_RelationType_ID(relationTypeId.getRepoId());
			adProcess.setIsUseAutoFilters(isUseAutoFilters);

			return ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process(adProcess)
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.build();
		}
	}

	private RelationTypeInOverlayProcess buildProcessWithoutRelationTypeId(
			final RelationTypeRelatedDocumentsProvidersFactory factory,
			final IViewsRepository viewsRepo)
	{
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(1)
				.setRecord(TableRecordReference.of("C_Order", 101))
				.setAdWindowId(AdWindowId.ofRepoId(100))
				// no relation type id
				.build();

		return RelationTypeInOverlayProcess.newInstanceForUnitTesting(factory, viewsRepo, processInfo, null, ImmutableMap.of());
	}

	private static RelatedDocumentsCandidateGroup buildCandidateGroupWithOneEntry(final WindowId targetWindowId)
	{
		// build using the real builder - adjust if the API differs
		return RelatedDocumentsCandidateGroup.builder()
				.candidate(RelatedDocumentsCandidate.builder()
						.id(RelatedDocumentsId.ofString("test-123"))
						.internalName("test-123")
						.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(targetWindowId.toAdWindowIdOrNull()))
						.priority(Priority.MEDIUM)
						.windowCaption(TranslatableStrings.anyLanguage("testCaption"))
						.querySupplier(() -> null)
						.documentsCountSupplier((permissions) -> 0)
						.build())
				.build();
	}
}