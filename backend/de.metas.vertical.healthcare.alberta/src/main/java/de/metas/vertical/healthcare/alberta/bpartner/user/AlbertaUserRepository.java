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

package de.metas.vertical.healthcare.alberta.bpartner.user;

import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_AD_User_Alberta;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AlbertaUserRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public AlbertaUser save(final @NonNull AlbertaUser user)
	{
		final I_AD_User_Alberta record = InterfaceWrapperHelper.loadOrNew(user.getUserAlbertaId(), I_AD_User_Alberta.class);

		record.setAD_User_ID(user.getUserId().getRepoId());

		record.setTimestamp(TimeUtil.asTimestamp(user.getTimestamp()));

		record.setTitle(user.getTitle() != null ? user.getTitle().getCode() : null);
		record.setGender(user.getGender() != null ? user.getGender().getCode() : null);

		InterfaceWrapperHelper.save(record);

		return toAlbertaUser(record);
	}

	@NonNull
	public Optional<AlbertaUser> getByUserId(final @NonNull UserId userId)
	{
		return queryBL.createQueryBuilder(I_AD_User_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_Alberta.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_AD_User_Alberta.class)
				.map(this::toAlbertaUser);
	}

	@NonNull
	public AlbertaUser toAlbertaUser(final @NonNull I_AD_User_Alberta record)
	{
		final UserAlbertaId userAlbertaId = UserAlbertaId.ofRepoId(record.getAD_User_Alberta_ID());
		final UserId userId = UserId.ofRepoId(record.getAD_User_ID());

		return AlbertaUser.builder()
				.userAlbertaId(userAlbertaId)
				.userId(userId)
				.title(TitleType.ofCodeNullable(record.getTitle()))
				.gender(GenderType.ofCodeNullable(record.getGender()))
				.timestamp(TimeUtil.asInstant(record.getTimestamp()))
				.build();
	}
}
