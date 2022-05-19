/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CachingKeysMapper;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_User_Assigned_Role;
import org.compiere.model.I_C_User_Role;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;

public class BPartnerCompositeCachingKeysMapper implements CachingKeysMapper<BPartnerId>
{
	private final static transient Logger logger = LogManager.getLogger(BPartnerCompositeCachingKeysMapper.class);
	private final IUserDAO userDAO;

	public BPartnerCompositeCachingKeysMapper(@NonNull final IUserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Override
	public Collection<BPartnerId> computeCachingKeys(@NonNull final TableRecordReference recordRef)
	{
		return extractBPartnerIds(recordRef);
	}

	private Collection<BPartnerId> extractBPartnerIds(@NonNull final TableRecordReference recordRef)
	{
		final ImmutableList<BPartnerId> result;
		if (I_C_BPartner.Table_Name.equals(recordRef.getTableName()))
		{
			result = ImmutableList.of(BPartnerId.ofRepoId(recordRef.getRecord_ID()));
		}
		else if (I_C_BPartner_Location.Table_Name.equals(recordRef.getTableName()))
		{
			final I_C_BPartner_Location bpartnerLocationRecord = recordRef.getModel(I_C_BPartner_Location.class);
			if (bpartnerLocationRecord == null) // can happen while we are in the process of storing a bpartner with locations and contacts
			{
				result = ImmutableList.of();
			}
			else
			{
				result = ImmutableList.of(BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()));
			}
		}
		else if (I_AD_User.Table_Name.equals(recordRef.getTableName()))
		{
			final I_AD_User userRecord = recordRef.getModel(I_AD_User.class);
			result = extractBPartnerIds(userRecord);
		}
		else if (I_C_User_Assigned_Role.Table_Name.equals(recordRef.getTableName()))
		{
			final I_C_User_Assigned_Role userRoleRecord = recordRef.getModel(I_C_User_Assigned_Role.class);
			if (userRoleRecord == null) // can happen while we are in the process of storing a bpartner with locations and contacts
			{
				result = ImmutableList.of();
			}
			else
			{
				final I_AD_User userRecord = userDAO.getById(UserId.ofRepoId(userRoleRecord.getAD_User_ID()));
				result = extractBPartnerIds(userRecord);
			}
		}
		else if (I_C_User_Role.Table_Name.equals(recordRef.getTableName()))
		{
			result = ImmutableList.of(); // isResetAll returned true
		}
		else
		{
			throw new AdempiereException("Given recordRef has unexpected tableName=" + recordRef.getTableName() + "; recordRef=" + recordRef);
		}

		logger.debug("extractBPartnerIds for recordRef={} returns result={}", recordRef, result);
		return result;
	}

	private ImmutableList<BPartnerId> extractBPartnerIds(@Nullable final I_AD_User userRecord)
	{
		final ImmutableList<BPartnerId> result;
		if (userRecord == null) // can happen while we are in the process of storing a bpartner with locations and contacts
		{
			result = ImmutableList.of();
		}
		else
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());
			result = bpartnerId != null ? ImmutableList.of(bpartnerId) : ImmutableList.of();
		}
		return result;
	}

	@Override
	public boolean isResetAll(@NonNull final TableRecordReference recordRef)
	{
		if (I_C_BPartner.Table_Name.equals(recordRef.getTableName()))
		{
			return false; // recordRef.getRecord_ID() returns the C_BPartner_ID to invalidate => no need to reset all
		}
		else if (I_C_User_Role.Table_Name.equals(recordRef.getTableName())
				|| I_C_User_Assigned_Role.Table_Name.equals(recordRef.getTableName()))
		{
			return true; // there might be too many C_BPartners using the given role; also this change is not frequent; so, better reset the whole cache
		}
		
		// If we can't retrieve the model - e.g. because it was already deleted - then we can't extract the C_BPartner_ID to invalidate. 
		// In that case we need to reset all
		final Object recordRefModel = recordRef.getModel(Object.class);
		return recordRefModel == null;
	}
}
