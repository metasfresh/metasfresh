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

import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

	@NonNull
	public PInstanceId getUnprocessedRowsSelectionId(@NonNull final BPartnerBlockFileId bPartnerBlockFileId)
	{
		return Optional.ofNullable(getUnprocessedActiveRowsQuery(bPartnerBlockFileId).createSelection())
				.orElseThrow(() -> new AdempiereException("No unprocessed rows found for " + bPartnerBlockFileId));
	}

	public boolean isImported(@NonNull final BPartnerBlockFileId fileId)
	{
		return queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, fileId)
				.orderBy(I_I_BPartner_BlockStatus.COLUMNNAME_I_BPartner_BlockStatus_ID)
				.create()
				.firstId() > 0;
	}

	public boolean hasUnprocessedRows(@NonNull final BPartnerBlockFileId fileId)
	{
		return getUnprocessedActiveRowsQuery(fileId)
				.firstId() > 0;
	}

	@NonNull
	private IQuery<I_I_BPartner_BlockStatus> getUnprocessedActiveRowsQuery(@NonNull final BPartnerBlockFileId fileId)
	{
		return queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, fileId)
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_Processed, false)
				.orderBy(I_I_BPartner_BlockStatus.COLUMNNAME_I_BPartner_BlockStatus_ID)
				.create();
	}
}
