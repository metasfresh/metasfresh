/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.document.archive.config;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocBaseType;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.organization.OrgId;
import de.metas.report.PrintFormatId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Nullable;
import static org.assertj.core.api.Assertions.assertThat;


public class DocOutboundConfigRepositoryTest
{
	private final AdTableId TABLE_1 = AdTableId.ofRepoId(101);
	private final AdTableId TABLE_2 = AdTableId.ofRepoId(102);

	private DocOutboundConfigRepository repository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		repository = DocOutboundConfigRepository.newInstanceForUnitTesting();
	}

	@Test
	public void getByQuery_exactMatch()
	{
		final DocOutboundConfig configSpecific = newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_2).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();

		final DocOutboundConfigQuery query = DocOutboundConfigQuery.builder().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();

		assertThat(repository.getByQuery(query)).isEqualTo(configSpecific);
	}

	@Test
	public void getByQuery_fallbackToGenericDocBaseType()
	{
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();
		final DocOutboundConfig configGenericDoc = newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_2).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.MAIN).build();

		final DocOutboundConfigQuery query = DocOutboundConfigQuery.builder().tableId(TABLE_1).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.MAIN).build();

		assertThat(repository.getByQuery(query)).isEqualTo(configGenericDoc);
	}

	@Test
	public void getByQuery_fallbackToAnyOrg()
	{
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();
		final DocOutboundConfig configGenericOrg = newConfig().tableId(TABLE_1).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.ANY).build();
		newConfig().tableId(TABLE_2).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.MAIN).build();

		final DocOutboundConfigQuery query = DocOutboundConfigQuery.builder().tableId(TABLE_1).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.MAIN).build();

		assertThat(repository.getByQuery(query)).isEqualTo(configGenericOrg);
	}

	@Test
	public void getByQuery_fallbackToGenericDocAndAnyOrg()
	{
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_1).docBaseType(DocBaseType.SalesOrder).orgId(OrgId.ANY).build();
		final DocOutboundConfig configGenericAll = newConfig().tableId(TABLE_1).docBaseType(null).orgId(OrgId.ANY).build();

		final DocOutboundConfigQuery query = DocOutboundConfigQuery.builder().tableId(TABLE_1).docBaseType(DocBaseType.PurchaseOrder).orgId(OrgId.ANY).build();

		assertThat(repository.getByQuery(query)).isEqualTo(configGenericAll);
	}


	@Test
	public void getDistinctConfigTableIds_test()
	{
		// Given
		newConfig().tableId(TABLE_1).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_2).orgId(OrgId.MAIN).build();
		newConfig().tableId(TABLE_2).orgId(OrgId.ANY).build();

		// When
		final ImmutableSet<AdTableId> result = repository.getDistinctConfigTableIds();

		// Then
		assertThat(result).containsExactlyInAnyOrder(TABLE_1, TABLE_2);
	}

	@Builder(builderMethodName = "newConfig", builderClassName = "ConfigBuilder")
	private DocOutboundConfig createConfig(
			@NonNull final AdTableId tableId,
			@Nullable final DocBaseType docBaseType,
			@NonNull final OrgId orgId)
	{
		final I_C_Doc_Outbound_Config record = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Config.class);
		record.setAD_Table_ID(tableId.getRepoId());
		record.setDocBaseType(docBaseType != null ? docBaseType.getCode() : null);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setAD_PrintFormat_ID(123);
		record.setCCPath("/tmp/cc");
		record.setIsDirectProcessQueueItem(true);
		record.setIsDirectEnqueue(false);
		record.setIsAutoSendDocument(true);
		InterfaceWrapperHelper.save(record);

		return DocOutboundConfig.builder()
				.id(DocOutboundConfigId.ofRepoId(record.getC_Doc_Outbound_Config_ID()))
				.tableId(tableId)
				.docBaseType(docBaseType)
				.orgId(orgId)
				.printFormatId(PrintFormatId.ofRepoId(123))
				.ccPath("/tmp/cc")
				.isDirectProcessQueueItem(true)
				.isDirectEnqueue(false)
				.isAutoSendDocument(true)
				.build();
	}
}
