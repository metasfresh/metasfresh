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

package de.metas.bpartner.blockfile;

import de.metas.impexp.config.DataImportConfigId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Block_File;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BPartnerBlockFileRepository
{
	@NonNull
	public BPartnerBlockFile getById(@NonNull final BPartnerBlockFileId bPartnerBlockFileId)
	{
		return Optional.ofNullable(load(bPartnerBlockFileId, I_C_BPartner_Block_File.class))
				.map(this::ofRecord)
				.orElseThrow(() -> new AdempiereException("Couldn't find any I_C_BPartner_Block_File record for provided id!")
						.appendParametersToMessage()
						.setParameter(I_C_BPartner_Block_File.COLUMNNAME_C_BPartner_Block_File_ID, bPartnerBlockFileId.getRepoId()));
	}

	@NonNull
	public BPartnerBlockFile update(@NonNull final BPartnerBlockFile blockFileToUpdate)
	{
		final I_C_BPartner_Block_File record = load(blockFileToUpdate.getId(), I_C_BPartner_Block_File.class);

		record.setFileName(blockFileToUpdate.getFileName());
		record.setC_DataImport_ID(blockFileToUpdate.getDataImportConfigId().getRepoId());
		record.setIsError(blockFileToUpdate.isError());
		record.setProcessed(blockFileToUpdate.isProcessed());

		saveRecord(record);

		return ofRecord(record);
	}

	@NonNull
	private BPartnerBlockFile ofRecord(@NonNull final I_C_BPartner_Block_File record)
	{
		return BPartnerBlockFile.builder()
				.id(BPartnerBlockFileId.ofRepoId(record.getC_BPartner_Block_File_ID()))
				.fileName(record.getFileName())
				.dataImportConfigId(DataImportConfigId.ofRepoId(record.getC_DataImport_ID()))
				.isProcessed(record.isProcessed())
				.isError(record.isError())
				.build();
	}
}
