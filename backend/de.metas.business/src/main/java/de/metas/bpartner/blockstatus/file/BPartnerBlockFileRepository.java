/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.blockstatus.file;

import de.metas.impexp.config.DataImportConfigId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Block_File;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
class BPartnerBlockFileRepository
{
	@NonNull
	public BPartnerBlockStatusFile getById(@NonNull final BPartnerBlockFileId bPartnerBlockFileId)
	{
		return ofRecord(getRecordById(bPartnerBlockFileId));
	}

	@NonNull
	public BPartnerBlockStatusFile save(@NonNull final BPartnerBlockStatusFile blockFileToUpdate)
	{
		final I_C_BPartner_Block_File record = getRecordById(blockFileToUpdate.getId());

		record.setFileName(blockFileToUpdate.getFileName());
		record.setC_DataImport_ID(blockFileToUpdate.getDataImportConfigId().getRepoId());

		saveRecord(record);

		return ofRecord(record);
	}

	@NonNull
	private I_C_BPartner_Block_File getRecordById(@NonNull final BPartnerBlockFileId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_BPartner_Block_File.class);
	}

	@NonNull
	private static BPartnerBlockStatusFile ofRecord(@NonNull final I_C_BPartner_Block_File record)
	{
		return BPartnerBlockStatusFile.builder()
				.id(BPartnerBlockFileId.ofRepoId(record.getC_BPartner_Block_File_ID()))
				.fileName(record.getFileName())
				.dataImportConfigId(DataImportConfigId.ofRepoId(record.getC_DataImport_ID()))
				.build();
	}
}
