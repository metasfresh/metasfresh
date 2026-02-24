package de.metas.ui.web.view.process;

import com.google.common.collect.ImmutableList;
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
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RelationTypeInOverlayProcessTest
{
	@BeforeEach
	void beforeEach()
	{
		MockitoAnnotations.initMocks(this);
		AdempiereTestHelper.get().init();
		Services.registerService(IADProcessDAO.class, mock(IADProcessDAO.class));
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(100));
	}

	@Nested
	class CheckPreconditionsApplicable
	{
		RelationTypeRelatedDocumentsProvidersFactory providerFactory = mock(RelationTypeRelatedDocumentsProvidersFactory.class);
		IViewsRepository viewsRepo = mock(IViewsRepository.class);

		@Test
		void rejects_whenMultipleRowsSelected()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.isSingleSelection()).thenReturn(false);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void rejects_whenNoWindowId()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.isSingleSelection()).thenReturn(true);
			when(ctx.getAdWindowId()).thenReturn(null);

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void accepts_whenSingleSelectionAndWindowIdPresent()
		{
			final IProcessPreconditionsContext ctx = mock(IProcessPreconditionsContext.class);
			when(ctx.isSingleSelection()).thenReturn(true);
			when(ctx.getAdWindowId()).thenReturn(AdWindowId.ofRepoId(100));

			final ProcessPreconditionsResolution resolution = buildProcessWithoutRelationTypeId(providerFactory, viewsRepo)
					.checkPreconditionsApplicable(ctx);

			assertThat(resolution.isAccepted()).isTrue();
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

		// --- helpers ---

		private RelationTypeInOverlayProcess buildProcess(
				final RelationTypeRelatedDocumentsProvidersFactory factory,
				final IViewsRepository viewsRepo,
				final RelationTypeId relationTypeId)
		{
			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(1)
					.setRecord(TableRecordReference.of("C_Order", 101))
					.setAdWindowId(AdWindowId.ofRepoId(100))
					.setAdRelationTypeId(relationTypeId)
					.build();

			final IZoomSource mockZoomSource = mock(IZoomSource.class);
			return RelationTypeInOverlayProcess.newInstanceForUnitTesting(factory, viewsRepo, processInfo, mockZoomSource);
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

		final IZoomSource mockZoomSource = mock(IZoomSource.class);
		return RelationTypeInOverlayProcess.newInstanceForUnitTesting(factory, viewsRepo, processInfo, mockZoomSource);
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