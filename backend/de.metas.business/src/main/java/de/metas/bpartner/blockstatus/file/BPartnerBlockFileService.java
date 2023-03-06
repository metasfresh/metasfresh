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

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.springframework.stereotype.Service;

@Service
public class BPartnerBlockFileService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final BPartnerBlockFileRepository bPartnerBlockFileRepository;

	public BPartnerBlockFileService(@NonNull final BPartnerBlockFileRepository bPartnerBlockFileRepository)
	{
		this.bPartnerBlockFileRepository = bPartnerBlockFileRepository;
	}

	@NonNull
	public BPartnerBlockStatusFile getById(@NonNull final BPartnerBlockFileId id)
	{
		return bPartnerBlockFileRepository.getById(id);
	}

	@NonNull
	public BPartnerBlockStatusFile save(@NonNull final BPartnerBlockStatusFile file)
	{
		return bPartnerBlockFileRepository.save(file);
	}

	public void recomputeFlags(@NonNull final BPartnerBlockFileId fileId)
	{
		final BPartnerBlockStatusFile bPartnerBlockFile = getById(fileId);

		final boolean thereIsAtLeastOneFailingLine = queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, fileId)
				.addNotNull(I_I_BPartner_BlockStatus.COLUMNNAME_I_ErrorMsg)
				.orderBy(I_I_BPartner_BlockStatus.COLUMN_I_BPartner_BlockStatus_ID)
				.create()
				.firstId() > 0;

		bPartnerBlockFileRepository.save(bPartnerBlockFile.toBuilder()
												 .isProcessed(!thereIsAtLeastOneFailingLine)
												 .isError(thereIsAtLeastOneFailingLine)
												 .build());
	}
}
