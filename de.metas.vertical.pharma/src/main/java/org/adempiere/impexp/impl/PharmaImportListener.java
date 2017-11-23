/**
 *
 */
package org.adempiere.impexp.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.impexp.IImportListener;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_I_BPartner;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PharmaImportListener implements IImportListener
{
	public static final PharmaImportListener instance = new PharmaImportListener();

	private PharmaImportListener()
	{

	}

	@Override
	public void onImport(@NonNull final Object importRecord, @NonNull final Object model)
	{
		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(model, I_I_BPartner.class);
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(model, I_C_BPartner.class);
		bpartner.setIsPharmaciePermission(ibpartner.isPharmaciePermission());
		bpartner.setPharmaproductpermlaw52(ibpartner.getPharmaproductpermlaw52());
		save(bpartner);
	}

}
