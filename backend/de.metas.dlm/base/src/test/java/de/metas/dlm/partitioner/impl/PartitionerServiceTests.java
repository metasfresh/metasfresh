package de.metas.dlm.partitioner.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.logging.LogManager;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PartitionerServiceTests
{

	@BeforeEach
	public void before()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.DEBUG);
	}

	@Test
	public void testAugmentPartition()
	{
		final PartitionConfig config = PartitionConfig.builder().line(I_AD_Field.Table_Name).endLine().build();

		final List<TableRecordIdDescriptor> descriptors = ImmutableList.of(
				TableRecordIdDescriptor.of(I_AD_ChangeLog.Table_Name,
						I_AD_ChangeLog.COLUMNNAME_Record_ID,
						I_AD_Field.Table_Name));

		testAugmentPartitionSimple(config, descriptors);
	}

	private PartitionConfig testAugmentPartitionSimple(final PartitionConfig configtoAugment, final List<TableRecordIdDescriptor> descriptors)
	{
		final I_AD_Field adField = InterfaceWrapperHelper.newInstance(I_AD_Field.class);
		InterfaceWrapperHelper.save(adField);
		final ITableRecordReference adFieldReference = TableRecordReference.ofOrNull(adField);

		final I_AD_ChangeLog changelog = InterfaceWrapperHelper.newInstance(I_AD_ChangeLog.class);
		changelog.setAD_Table_ID(adFieldReference.getAD_Table_ID());
		changelog.setRecord_ID(adFieldReference.getRecord_ID());
		InterfaceWrapperHelper.save(changelog);

		final PartitionConfig augmentedConfig = new PartitionerService()
				.augmentPartitionerConfig(configtoAugment, descriptors);

		assertThat(augmentedConfig.getLines()).hasSize(2);
		assertThat(augmentedConfig.getLineNotNull(I_AD_Field.Table_Name).getReferences()).isEmpty();
		assertThat(augmentedConfig.getLineNotNull(I_AD_ChangeLog.Table_Name).getReferences()).hasSize(1);
		assertThat(augmentedConfig.getLineNotNull(I_AD_ChangeLog.Table_Name).getReferences().get(0).getReferencedTableName()).isEqualTo(I_AD_Field.Table_Name);
		assertThat(augmentedConfig.getLineNotNull(I_AD_ChangeLog.Table_Name).getReferences().get(0).getReferencingColumnName()).isEqualTo(I_AD_ChangeLog.COLUMNNAME_Record_ID);

		return augmentedConfig;
	}

	/**
	 * Calls {@link PartitionerServiceOld#augmentPartitionerConfig(PartitionConfig, List)} twice in a row, with an equal descriptor.
	 * Veiries that there are no duplicated references because of this.
	 *
	 */
	@Test
	public void testAugmentPartitionAvoidDuplicates()
	{
		final PartitionConfig config = PartitionConfig.builder().line(I_AD_Field.Table_Name).endLine().build();

		final List<TableRecordIdDescriptor> descriptors = ImmutableList.of(
				TableRecordIdDescriptor.of(I_AD_ChangeLog.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID, I_AD_Field.Table_Name));

		final PartitionConfig augmentedConfig = testAugmentPartitionSimple(config, descriptors);

		final List<TableRecordIdDescriptor> descriptors2 = ImmutableList.of(
				TableRecordIdDescriptor.of(I_AD_ChangeLog.Table_Name, I_AD_ChangeLog.COLUMNNAME_Record_ID, I_AD_Field.Table_Name));

		// make the same call again. there shall be no double lines or references.
		testAugmentPartitionSimple(augmentedConfig, descriptors2);
	}

	/**
	 * When adding a new reference, then this method verifies that there is also a new line added for the reference's referenced table, if necessary.
	 */
	@Test
	public void testAugmentPartitionAlsoAddLine()
	{
		// empty config
		final PartitionConfig config = PartitionConfig.builder().build();

		final List<TableRecordIdDescriptor> descriptors = ImmutableList.of(
				TableRecordIdDescriptor.of(I_AD_ChangeLog.Table_Name,
						I_AD_ChangeLog.COLUMNNAME_Record_ID,
						I_AD_Field.Table_Name));

		testAugmentPartitionSimple(config, descriptors);
	}
}
