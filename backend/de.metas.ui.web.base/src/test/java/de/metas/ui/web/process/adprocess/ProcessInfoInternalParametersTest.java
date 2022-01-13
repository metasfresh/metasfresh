package de.metas.ui.web.process.adprocess;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfo.ProcessInfoBuilder;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessType;
import de.metas.ui.web.process.CreateProcessInstanceRequest;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.user.UserId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ProcessInfoInternalParametersTest
{

	private ProcessInfoBuilder processInfoBuilder;
	private ProcessId processId;

	private static UserId loggedUserId = UserId.ofRepoId(1234567);

	@BeforeEach
	public void beforeEach()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		adempiereTestHelper.setupContext_AD_Client_IfNotSet();

		Env.setLoggedUserId(Env.getCtx(), loggedUserId);

		processId = createProcessId();
		processInfoBuilder = createProcessInfoBuilder();

	}

	private ProcessInfoBuilder createProcessInfoBuilder()
	{
		final Set<TableRecordReference> selectedIncludedRecords = ImmutableSet.of(
				TableRecordReference.of(I_C_OLCand.Table_Name, 1), TableRecordReference.of(I_C_OLCand.Table_Name, 2),
				TableRecordReference.of(I_C_OLCand.Table_Name, 3));

		return ProcessInfo.builder().setCtx(Env.getCtx()).setCreateTemporaryCtx().setAD_Process_ID(processId.getProcessIdAsInt())
				.setSelectedIncludedRecords(selectedIncludedRecords);
	}

	private CreateProcessInstanceRequest createProcessInstanceRequestrequest(final @Nullable ViewRowIdsSelection viewRowIdsSelection,
			final @Nullable ViewRowIdsSelection parentViewRowIdsSelection,
			final @Nullable ViewRowIdsSelection childViewRowIdsSelection)
	{
		final CreateProcessInstanceRequest request = CreateProcessInstanceRequest.builder()
				.processId(processId)
				.viewRowIdsSelection(viewRowIdsSelection)
				.parentViewRowIdsSelection(parentViewRowIdsSelection)
				.childViewRowIdsSelection(childViewRowIdsSelection)
				.build();

		return request;
	}

	private ViewRowIdsSelection createViewRowIdSelection(final @NonNull Set<String> set)
	{
		return ViewRowIdsSelection.ofNullableStrings("540095-T", set);
	}

	private ProcessId createProcessId()
	{
		final I_AD_Process processPO = newInstanceOutOfTrx(I_AD_Process.class);
		processPO.setValue("Test");
		processPO.setName("Test");
		processPO.setType(ProcessType.SQL.getCode());
		save(processPO);
		return ProcessId.ofAD_Process_ID(processPO.getAD_Process_ID());
	}

	
	/**
	 * <ul>Tests the parameter name and the fact that the string of selected records is constructed using comma</ul>
	 * <ul>if fails, do not fix it according to the new logic and beware that are sql processes that count on this</ul>
	 * 
	 */
	@Test
	public void test_getCommaSeparatedViewRowInternalParameters()
	{
		assertThat(ViewBasedProcessTemplate.PARAM_ViewSelectedIds).isEqualTo("$WEBUI_ViewSelectedIds");
		
		// prepare data
		final ViewRowIdsSelection viewRowIdsSelection = createViewRowIdSelection(ImmutableSet.of("1", "2", "3", "4", "5"));
		final CreateProcessInstanceRequest request = createProcessInstanceRequestrequest(viewRowIdsSelection, null, null);

		// run
		ADProcessInstancesRepository.addViewInternalParameters(request, processInfoBuilder);

		// expectations
		final List<ProcessInfoParameter> parameters = processInfoBuilder.build().getParameter();
		final Optional<ProcessInfoParameter> para = parameters
				.stream()
				.filter(param -> param.getParameterName().equals(ViewBasedProcessTemplate.PARAM_ViewSelectedIds))
				.findAny();

		final String actual = para.get().getParameterAsString();
		final String expected = "1,2,3,4,5";
		assertThat(actual).isEqualTo(expected);
	}

	/**
	 * <ul>Tests the parameter name and the fact that the string of selected records is constructed using comma</ul>
	 * <ul>if fails, do not fix it according to the new logic and beware that are sql processes that count on this</ul>
	 * 
	 */
	@Test
	public void test_getCommaSeparatedParentViewRowIdsSelection()
	{
		assertThat(ViewBasedProcessTemplate.PARAM_ParentViewSelectedIds).isEqualTo("$WEBUI_ParentViewSelectedIds");
		
		// prepare data
		final ViewRowIdsSelection parentViewRowIdsSelection = createViewRowIdSelection(ImmutableSet.of("100", "200", "300"));
		final CreateProcessInstanceRequest request = createProcessInstanceRequestrequest(null, parentViewRowIdsSelection, null);

		// run
		ADProcessInstancesRepository.addViewInternalParameters(request, processInfoBuilder);

		// expectations
		final List<ProcessInfoParameter> parameters = processInfoBuilder.build().getParameter();
		final Optional<ProcessInfoParameter> para = parameters.stream().filter(param -> param.getParameterName().equals(ViewBasedProcessTemplate.PARAM_ParentViewSelectedIds))
				.findAny();

		final String actual = para.get().getParameterAsString();
		final String expected = "100,200,300";
		assertThat(actual).isEqualTo(expected);
	}

	/**
	 * <ul>Tests the parameter name and the fact that the string of selected records is constructed using comma</ul>
	 * <ul>if fails, do not fix it according to the new logic and beware that are sql processes that count on this</ul>
	 * 
	 */
	@Test
	public void test_getCommaSeparatedChildViewRowIdsSelection()
	{
		assertThat(ViewBasedProcessTemplate.PARAM_ChildViewSelectedIds).isEqualTo("$WEBUI_ChildViewSelectedIds");
		
		// prepare data
		final ViewRowIdsSelection childViewRowIdsSelection = createViewRowIdSelection(ImmutableSet.of("10", "20"));
		final CreateProcessInstanceRequest request = createProcessInstanceRequestrequest(null, null, childViewRowIdsSelection);

		// run
		ADProcessInstancesRepository.addViewInternalParameters(request, processInfoBuilder);

		// expectations
		final List<ProcessInfoParameter> parameters = processInfoBuilder.build().getParameter();
		final Optional<ProcessInfoParameter> para = parameters.stream().filter(param -> param.getParameterName().equals(ViewBasedProcessTemplate.PARAM_ChildViewSelectedIds))
				.findAny();

		final String actual = para.get().getParameterAsString();
		final String expected = "10,20";
		assertThat(actual).isEqualTo(expected);
	}

}
