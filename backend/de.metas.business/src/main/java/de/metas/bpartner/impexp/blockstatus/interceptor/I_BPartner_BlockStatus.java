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

package de.metas.bpartner.impexp.blockstatus.interceptor;

import de.metas.bpartner.blockfile.BPartnerBlockFile;
import de.metas.bpartner.blockfile.BPartnerBlockFileId;
import de.metas.bpartner.blockfile.BPartnerBlockFileRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_I_BPartner_BlockStatus.class)
@Component
public class I_BPartner_BlockStatus
{
	private final BPartnerBlockFileRepository bPartnerBlockFileRepository;

	public I_BPartner_BlockStatus(@NonNull final BPartnerBlockFileRepository bPartnerBlockFileRepository)
	{
		this.bPartnerBlockFileRepository = bPartnerBlockFileRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_I_BPartner_BlockStatus.COLUMNNAME_Processed})
	public void propagateProcessedFlagToBlockFile(@NonNull final I_I_BPartner_BlockStatus importRecord)
	{
		final BPartnerBlockFileId blockFileId = BPartnerBlockFileId.ofRepoId(importRecord.getC_BPartner_Block_File_ID());
		final BPartnerBlockFile bPartnerBlockFile = bPartnerBlockFileRepository.getById(blockFileId);

		final BPartnerBlockFile.BPartnerBlockFileBuilder recordToUpdate = bPartnerBlockFile.toBuilder();
		if (bPartnerBlockFile.isProcessed() != importRecord.isProcessed())
		{
			recordToUpdate.isProcessed(importRecord.isProcessed());
		}

		bPartnerBlockFileRepository.update(recordToUpdate.build());
	}
}