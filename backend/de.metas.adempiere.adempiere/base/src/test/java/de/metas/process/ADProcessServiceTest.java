/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.process;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.i18n.ImmutableTranslatableString;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Table_Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

@ExtendWith(AdempiereTestWatcher.class)
class ADProcessServiceTest
{
	private MockedCustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;
	private ADProcessService adProcessService;

	private AdTableId adTableId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.customizedWindowInfoMapRepository = new MockedCustomizedWindowInfoMapRepository();
		adProcessService = new ADProcessService(customizedWindowInfoMapRepository);

		adTableId = createTable("Test");
	}

	@SuppressWarnings("SameParameterValue")
	private AdTableId createTable(final String tableName)
	{
		final I_AD_Table adTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		adTable.setTableName(tableName);
		InterfaceWrapperHelper.saveRecord(adTable);
		return AdTableId.ofRepoId(adTable.getAD_Table_ID());
	}

	@Builder(builderMethodName = "relatedProcess", builderClassName = "$RelatedProcessBuilder")
	private void createRelatedProcess(
			@NonNull final AdProcessId processId,
			@Nullable final AdWindowId adWindowId,
			@Nullable final AdTabId adTabId)
	{
		final I_AD_Table_Process record = InterfaceWrapperHelper.newInstance(I_AD_Table_Process.class);
		record.setAD_Table_ID(adTableId.getRepoId());
		record.setAD_Process_ID(processId.getRepoId());
		record.setAD_Window_ID(AdWindowId.toRepoId(adWindowId));
		record.setAD_Tab_ID(AdTabId.toRepoId(adTabId));
		InterfaceWrapperHelper.saveRecord(record);
	}

	@Nested
	class getRelatedProcessDescriptors
	{
		@Test
		void relatedProcessAssignedOnTableLevel()
		{
			customizedWindowInfoMapRepository.set(CustomizedWindowInfoMap.empty());

			relatedProcess().processId(AdProcessId.ofRepoId(1)).build();

			Assertions.assertThat(
					adProcessService.getRelatedProcessDescriptors(adTableId, AdWindowId.ofRepoId(1), AdTabId.ofRepoId(11)))
					.hasSize(1)
					.element(0)
					.usingRecursiveComparison()
					.isEqualTo(RelatedProcessDescriptor.builder()
									   .tableId(adTableId)
									   .processId(AdProcessId.ofRepoId(1))
									   .windowId(null)
									   .tabId(null)
									   .displayPlace(RelatedProcessDescriptor.DisplayPlace.SingleDocumentActionsMenu)
									   .build());
		}

		@Test
		void relatedProcessAssignedOnWindowLevel()
		{
			customizedWindowInfoMapRepository.set(CustomizedWindowInfoMap.empty());

			relatedProcess().processId(AdProcessId.ofRepoId(1)).adWindowId(AdWindowId.ofRepoId(1)).build();

			Assertions.assertThat(
					adProcessService.getRelatedProcessDescriptors(adTableId, AdWindowId.ofRepoId(1), AdTabId.ofRepoId(11)))
					.hasSize(1)
					.element(0)
					.usingRecursiveComparison()
					.isEqualTo(RelatedProcessDescriptor.builder()
									   .tableId(adTableId)
									   .processId(AdProcessId.ofRepoId(1))
									   .windowId(AdWindowId.ofRepoId(1))
									   .tabId(null)
									   .displayPlace(RelatedProcessDescriptor.DisplayPlace.SingleDocumentActionsMenu)
									   .build());
		}

		@Test
		void relatedProcessAssignedOnTabLevel()
		{
			customizedWindowInfoMapRepository.set(CustomizedWindowInfoMap.empty());

			relatedProcess().processId(AdProcessId.ofRepoId(1)).adWindowId(AdWindowId.ofRepoId(1)).adTabId(AdTabId.ofRepoId(11)).build();

			Assertions.assertThat(
					adProcessService.getRelatedProcessDescriptors(adTableId, AdWindowId.ofRepoId(1), AdTabId.ofRepoId(11)))
					.hasSize(1)
					.element(0)
					.usingRecursiveComparison()
					.isEqualTo(RelatedProcessDescriptor.builder()
									   .tableId(adTableId)
									   .processId(AdProcessId.ofRepoId(1))
									   .windowId(AdWindowId.ofRepoId(1))
									   .tabId(AdTabId.ofRepoId(11))
									   .displayPlace(RelatedProcessDescriptor.DisplayPlace.SingleDocumentActionsMenu)
									   .build());
		}

		@Test
		void inheritForBaseWindow()
		{
			customizedWindowInfoMapRepository.set(CustomizedWindowInfoMap.ofList(
					ImmutableList.of(
							CustomizedWindowInfo.builder()
									.customizationWindowCaption(ImmutableTranslatableString.builder().defaultValue("test").build())
									.baseWindowId(AdWindowId.ofRepoId(1))
									.customizationWindowId(AdWindowId.ofRepoId(2))
									.build(),
							CustomizedWindowInfo.builder()
									.customizationWindowCaption(ImmutableTranslatableString.builder().defaultValue("test").build())
									.baseWindowId(AdWindowId.ofRepoId(2))
									.customizationWindowId(AdWindowId.ofRepoId(3))
									.build()
					)));
			System.out.println("CustomizedWindowInfoMap: " + customizedWindowInfoMapRepository.get());

			relatedProcess().processId(AdProcessId.ofRepoId(1)).adWindowId(AdWindowId.ofRepoId(1)).build();

			Assertions.assertThat(
					adProcessService.getRelatedProcessDescriptors(adTableId, AdWindowId.ofRepoId(2), AdTabId.ofRepoId(22)))
					.hasSize(1)
					.element(0)
					.usingRecursiveComparison()
					.isEqualTo(RelatedProcessDescriptor.builder()
									   .tableId(adTableId)
									   .processId(AdProcessId.ofRepoId(1))
									   .windowId(AdWindowId.ofRepoId(2))
									   .tabId(null)
									   .displayPlace(RelatedProcessDescriptor.DisplayPlace.SingleDocumentActionsMenu)
									   .build());
		}
	}
}