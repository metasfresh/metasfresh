/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.incoterms.model.interceptor;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.ModelValidator;

import java.util.List;

@Interceptor(I_C_Incoterms.class)
@Callout(I_C_Incoterms.class)
public class C_Incoterms
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_Incoterms.COLUMNNAME_IsDefault)
	public void updatedDefaultIncoterms(@NonNull final I_C_Incoterms incoterm)
	{
		if (incoterm.isDefault())
		{
			final List<I_C_Incoterms> defaultIncoterms = queryBL.createQueryBuilder(I_C_Incoterms.class)
					.addEqualsFilter(I_C_Incoterms.COLUMNNAME_IsDefault, true)
					.addEqualsFilter(I_C_Incoterms.COLUMNNAME_AD_Org_ID, incoterm.getAD_Org_ID())
					.addNotEqualsFilter(I_C_Incoterms.COLUMNNAME_C_Incoterms_ID, incoterm.getC_Incoterms_ID())
					.create()
					.list(I_C_Incoterms.class);

			for (final I_C_Incoterms defaultIncoterm : defaultIncoterms)
			{
				defaultIncoterm.setIsDefault(false);
				defaultIncoterm.setDefaultLocation(null);
				InterfaceWrapperHelper.save(defaultIncoterm);
			}
		}

	}
}
