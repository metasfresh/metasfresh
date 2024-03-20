/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigProductMapping;
import de.metas.externalsystem.leichmehl.LeichMehlPluFileConfigGroupId;
import de.metas.externalsystem.leichmehl.ReplacementSource;
import de.metas.externalsystem.leichmehl.TargetFieldType;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl_ProductMapping;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_Config;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class ExternalSystemLeichMehlConfigProductMappingRepositoryTest
{
	private Expect expect;
	private ExternalSystemLeichMehlConfigProductMappingRepository externalSystemLeichMehlConfigProductMappingRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemLeichMehlConfigProductMappingRepository = new ExternalSystemLeichMehlConfigProductMappingRepository();
	}

	@Test
	void getExternalSystemLeichMehlConfigProductMappings_whenConfigWithAndWithoutPartnerExists_getPartnerOne()
	{
		// given
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(1);
		final ProductId productId = ProductId.ofRepoId(1);
		final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId = LeichMehlPluFileConfigGroupId.ofRepoId(1);

		final I_LeichMehl_PluFile_ConfigGroup pluFileConfigGroup = newInstance(I_LeichMehl_PluFile_ConfigGroup.class);
		pluFileConfigGroup.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());
		pluFileConfigGroup.setName("testGroupName");

		saveRecord(pluFileConfigGroup);

		final I_LeichMehl_PluFile_Config pluFileConfig = newInstance(I_LeichMehl_PluFile_Config.class);
		pluFileConfig.setLeichMehl_PluFile_Config_ID(1);
		pluFileConfig.setLeichMehl_PluFile_ConfigGroup_ID(pluFileConfigGroup.getLeichMehl_PluFile_ConfigGroup_ID());
		pluFileConfig.setTargetFieldName("targetFileName");
		pluFileConfig.setTargetFieldType(TargetFieldType.Date.getCode());
		pluFileConfig.setReplacement("replacement");
		pluFileConfig.setReplaceRegExp("replacePattern");
		pluFileConfig.setReplacementSource(ReplacementSource.PPOrder.getCode());

		saveRecord(pluFileConfig);

		final I_ExternalSystem_Config_LeichMehl_ProductMapping productMappingRecord = newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class);
		productMappingRecord.setExternalSystem_Config_LeichMehl_ProductMapping_ID(1);
		productMappingRecord.setM_Product_ID(productId.getRepoId());
		productMappingRecord.setPLU_File("pluFile");
		productMappingRecord.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());

		saveRecord(productMappingRecord);

		final I_ExternalSystem_Config_LeichMehl_ProductMapping productMappingRecord2 = newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class);
		productMappingRecord2.setExternalSystem_Config_LeichMehl_ProductMapping_ID(2);
		productMappingRecord2.setM_Product_ID(productId.getRepoId());
		productMappingRecord2.setC_BPartner_ID(bPartnerId.getRepoId());
		productMappingRecord2.setPLU_File("pluFilePartner");
		productMappingRecord2.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());

		saveRecord(productMappingRecord2);

		// when
		final Optional<ExternalSystemLeichMehlConfigProductMapping> result = externalSystemLeichMehlConfigProductMappingRepository.getByProductIdAndPartnerId(productId, bPartnerId);

		// then
		assertThat(result).isPresent();
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Test
	void getExternalSystemLeichMehlConfigProductMappings_emptyIfNotMatching()
	{
		// given
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(1);
		final BPartnerId bPartnerIdNotMatching = BPartnerId.ofRepoId(2);
		final ProductId productId = ProductId.ofRepoId(1);
		final ProductId productIdNotMatching = ProductId.ofRepoId(2);
		final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId = LeichMehlPluFileConfigGroupId.ofRepoId(1);

		final I_LeichMehl_PluFile_ConfigGroup pluFileConfigGroup = newInstance(I_LeichMehl_PluFile_ConfigGroup.class);
		pluFileConfigGroup.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());
		pluFileConfigGroup.setName("testGroupName");

		saveRecord(pluFileConfigGroup);

		final I_LeichMehl_PluFile_Config pluFileConfig = newInstance(I_LeichMehl_PluFile_Config.class);
		pluFileConfig.setLeichMehl_PluFile_Config_ID(1);
		pluFileConfig.setLeichMehl_PluFile_ConfigGroup_ID(pluFileConfigGroup.getLeichMehl_PluFile_ConfigGroup_ID());
		pluFileConfig.setTargetFieldName("targetFileName");
		pluFileConfig.setTargetFieldType(TargetFieldType.Date.getCode());
		pluFileConfig.setReplacement("replacement");
		pluFileConfig.setReplaceRegExp("replacePattern");
		pluFileConfig.setReplacementSource(ReplacementSource.PPOrder.getCode());

		saveRecord(pluFileConfig);

		final I_ExternalSystem_Config_LeichMehl_ProductMapping productMappingRecord = newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class);
		productMappingRecord.setExternalSystem_Config_LeichMehl_ProductMapping_ID(1);
		productMappingRecord.setM_Product_ID(productIdNotMatching.getRepoId());
		productMappingRecord.setPLU_File("pluFile");
		productMappingRecord.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());

		saveRecord(productMappingRecord);

		final I_ExternalSystem_Config_LeichMehl_ProductMapping productMappingRecord2 = newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class);
		productMappingRecord2.setExternalSystem_Config_LeichMehl_ProductMapping_ID(2);
		productMappingRecord2.setM_Product_ID(productId.getRepoId());
		productMappingRecord2.setC_BPartner_ID(bPartnerIdNotMatching.getRepoId());
		productMappingRecord2.setPLU_File("pluFile");
		productMappingRecord2.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());

		saveRecord(productMappingRecord2);

		final I_ExternalSystem_Config_LeichMehl_ProductMapping productMappingRecord3 = newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class);
		productMappingRecord3.setExternalSystem_Config_LeichMehl_ProductMapping_ID(3);
		productMappingRecord3.setM_Product_ID(productId.getRepoId());
		productMappingRecord3.setC_BPartner_ID(bPartnerId.getRepoId());
		productMappingRecord3.setPLU_File("pluFile");
		productMappingRecord3.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());
		productMappingRecord3.setIsActive(false);

		saveRecord(productMappingRecord3);

		// when
		final Optional<ExternalSystemLeichMehlConfigProductMapping> result = externalSystemLeichMehlConfigProductMappingRepository.getByProductIdAndPartnerId(productId, bPartnerId);

		// then
		assertThat(result).isEmpty();
	}
}
