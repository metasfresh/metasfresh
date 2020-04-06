package de.metas.shipping;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
@Repository
public class MPackageRepository
{
	public I_M_Package getById(final MPackageId mPackageId)
	{
		final I_M_Package mPackage = load(mPackageId.getRepoId(), I_M_Package.class);
		if (mPackage == null)
		{
			throw new AdempiereException("@NotFound@: " + mPackageId);
		}
		return mPackage;
	}

	public void closeMPackage(MPackageId mPackageId)
	{
		final I_M_Package mPackage = getById(mPackageId);

		mPackage.setIsClosed(true);

		save(mPackage);
	}
}
