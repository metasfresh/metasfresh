/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.process.adprocess;

import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.websocket.sender.WebsocketSender;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers how {@link ADProcessPostProcessService} invalidates the view a process was run from.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ADProcessPostProcessServiceTest
{
	private static final WindowId windowId = WindowId.of(542175);

	private IViewsRepository viewsRepo;
	private IView view;
	private ViewId viewId;
	private ADProcessPostProcessService postProcessService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// ProcessInfo.builder() resolves the logged user eagerly
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		// the plain-refresh branch broadcasts the view change itself, which needs the websocket bean
		SpringContextHolder.registerJUnitBean(WebsocketSender.class, Mockito.mock(WebsocketSender.class));

		viewId = ViewId.random(windowId);
		view = Mockito.mock(IView.class);
		Mockito.when(view.getViewId()).thenReturn(viewId);

		viewsRepo = Mockito.mock(IViewsRepository.class);
		Mockito.when(viewsRepo.getViewIfExists(viewId)).thenReturn(view);

		postProcessService = ADProcessPostProcessService.builder()
				.viewsRepo(viewsRepo)
				.documentsCollection(Mockito.mock(DocumentCollection.class))
				.build();
	}

	/** A process that says nothing must leave the view alone, whatever else the framework does. */
	@Test
	void viewIsLeftAlone_whenTheProcessAsksForNothing()
	{
		postProcess(result -> {});

		Mockito.verify(view, Mockito.never()).invalidateSelection();
		Mockito.verify(view, Mockito.never()).invalidateAll();
	}

	/**
	 * Re-reading the rows is not enough when the process changed whether they still belong to the view:
	 * the selection is materialized, so it has to be built again.
	 */
	@Test
	void selectionIsRecreated_whenTheProcessAsksForIt()
	{
		postProcess(result -> result.setRecreateViewSelectionAfterExecution(true));

		Mockito.verify(view).invalidateSelection();
		// invalidateSelection() resets the row cache and broadcasts by itself, so adding invalidateAll()
		// here would only duplicate the websocket event
		Mockito.verify(view, Mockito.never()).invalidateAll();
	}

	/** The plain refresh keeps its old behaviour: re-read the rows of the selection the view already has. */
	@Test
	void onlyTheRowsAreReRead_whenTheProcessAsksForAPlainRefresh()
	{
		postProcess(result -> result.setRefreshAllAfterExecution(true));

		Mockito.verify(view).invalidateAll();
		Mockito.verify(view, Mockito.never()).invalidateSelection();
	}

	private void postProcess(@NonNull final java.util.function.Consumer<ProcessExecutionResult> resultCustomizer)
	{
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(createAdProcess())
				.setPInstanceId(createPInstanceId())
				.build();

		resultCustomizer.accept(processInfo.getResult());

		postProcessService.postProcess(ADProcessPostProcessRequest.builder()
				.viewId(viewId)
				.processInfo(processInfo)
				.processExecutionResult(processInfo.getResult())
				.build());
	}

	private PInstanceId createPInstanceId()
	{
		final I_AD_PInstance pinstance = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
		InterfaceWrapperHelper.saveRecord(pinstance);
		return PInstanceId.ofRepoId(pinstance.getAD_PInstance_ID());
	}

	private int createAdProcess()
	{
		final I_AD_Process adProcess = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
		adProcess.setValue("TestProcess");
		adProcess.setName("TestProcess");
		adProcess.setType(X_AD_Process.TYPE_Java);
		InterfaceWrapperHelper.saveRecord(adProcess);
		return adProcess.getAD_Process_ID();
	}
}
