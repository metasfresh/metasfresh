package de.metas.vertical.pharma.vendor.gateway.mvs3.common;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungSubstitution;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitSubstitution;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class SubstitutionSaver
{
	public I_MSV3_Substitution storeSubstitutionOrNull(@Nullable final BestellungSubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final I_MSV3_Substitution substitutionRecord = newInstance(I_MSV3_Substitution.class);
		substitutionRecord.setMSV3_Grund(substitution.getGrund().toString());
		substitutionRecord.setMSV3_LieferPzn(Long.toString(substitution.getLieferPzn()));
		substitutionRecord.setMSV3_Substitutionsgrund(substitution.getSubstitutionsgrund().toString());
		save(substitutionRecord);

		return substitutionRecord;
	}

	public I_MSV3_Substitution storeSubstitutionOrNull(@Nullable final VerfuegbarkeitSubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final I_MSV3_Substitution substitutionRecord = newInstance(I_MSV3_Substitution.class);
		substitutionRecord.setMSV3_Grund(substitution.getGrund().toString());
		substitutionRecord.setMSV3_LieferPzn(Long.toString(substitution.getLieferPzn()));
		substitutionRecord.setMSV3_Substitutionsgrund(substitution.getSubstitutionsgrund().toString());
		save(substitutionRecord);

		return substitutionRecord;

	}
}
