/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.bpartner.albertabpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_Alberta;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AlbertaBPartnerRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AlbertaBPartner save(@NonNull final AlbertaBPartner bPartner)
	{
		final I_C_BPartner_Alberta record = InterfaceWrapperHelper.loadOrNew(bPartner.getBPartnerAlbertaId(), I_C_BPartner_Alberta.class);

		record.setC_BPartner_ID(bPartner.getBPartnerId().getRepoId());

		record.setTimestamp(TimeUtil.asTimestamp(bPartner.getTimestamp()));

		record.setTitle(bPartner.getTitle());
		record.setTitleShort(bPartner.getTitleShort());

		record.setIsArchived(bPartner.isArchived());

		InterfaceWrapperHelper.save(record);

		return toAlbertaBPartner(record);
	}

	@NonNull
	public Optional<AlbertaBPartner> getByPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Alberta.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.create()
				.firstOnlyOptional(I_C_BPartner_Alberta.class)
				.map(this::toAlbertaBPartner);
	}

	@NonNull
	public AlbertaBPartner toAlbertaBPartner(@NonNull final I_C_BPartner_Alberta record)
	{
		final BPartnerAlbertaId bPartnerAlbertaId = BPartnerAlbertaId.ofRepoId(record.getC_BPartner_Alberta_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());

		return AlbertaBPartner.builder()
				.bPartnerAlbertaId(bPartnerAlbertaId)
				.bPartnerId(bPartnerId)
				.title(record.getTitle())
				.titleShort(record.getTitleShort())
				.isArchived(record.isArchived())
				.timestamp(TimeUtil.asInstant(record.getTimestamp()))
				.build();
	}
}
