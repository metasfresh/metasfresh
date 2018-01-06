/**
 *
 */
package org.adempiere.impexp.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.IImportProcess;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_I_BPartner;

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
public class PharmaImportPartnerInterceptor implements IImportInterceptor
{
	public static final PharmaImportPartnerInterceptor instance = new PharmaImportPartnerInterceptor();

	private PharmaImportPartnerInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT || !(targetModel instanceof org.compiere.model.I_C_BPartner))
		{
			return;
		}

		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(importModel, I_I_BPartner.class);
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(targetModel, I_C_BPartner.class);
		bpartner.setIsPharmaciePermission(ibpartner.isPharmaciePermission());
		if (!Check.isEmpty(ibpartner.getPharmaproductpermlaw52(),true))
		{
			bpartner.setPharmaproductpermlaw52(ibpartner.getPharmaproductpermlaw52());
		}
		save(bpartner);
	}

}
