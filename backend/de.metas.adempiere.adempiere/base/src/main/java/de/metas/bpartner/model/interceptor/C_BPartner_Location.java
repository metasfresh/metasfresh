/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.bpartner.model.interceptor;

import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner_Location.class)
@Component
public class C_BPartner_Location
{
	/**
	 * Strips leading/trailing whitespace from the GLN; a blank GLN is stored as <code>null</code>
	 * (same normalization as {@link de.metas.bpartner.GLN#ofNullableString(String)}),
	 * so that GLN lookups by exact match are not defeated by stray whitespace.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_GLN)
	public void trimGLN(@NonNull final I_C_BPartner_Location bpLocation)
	{
		bpLocation.setGLN(StringUtils.trimBlankToNull(bpLocation.getGLN()));
	}
}
