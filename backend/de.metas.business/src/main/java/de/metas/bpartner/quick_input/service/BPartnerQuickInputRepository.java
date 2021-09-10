/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class BPartnerQuickInputRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(final I_C_BPartner_QuickInput template)
	{
		InterfaceWrapperHelper.saveRecord(template);
	}

	public I_C_BPartner_QuickInput getById(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		final I_C_BPartner_QuickInput bpartnerQuickInput = InterfaceWrapperHelper.load(bpartnerQuickInputId, I_C_BPartner_QuickInput.class);
		if (bpartnerQuickInput == null)
		{
			throw new AdempiereException("Not found " + I_C_BPartner_QuickInput.class.getSimpleName() + " for ID=" + bpartnerQuickInputId);
		}
		return bpartnerQuickInput;
	}

	public List<I_C_BPartner_Contact_QuickInput> retrieveContactsByQuickInputId(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		return queryBL
				.createQueryBuilder(I_C_BPartner_Contact_QuickInput.class)
				.addEqualsFilter(I_C_BPartner_Contact_QuickInput.COLUMNNAME_C_BPartner_QuickInput_ID, bpartnerQuickInputId)
				.orderBy(I_C_BPartner_Contact_QuickInput.COLUMNNAME_C_BPartner_Contact_QuickInput_ID)
				.create()
				.list();
	}
}
