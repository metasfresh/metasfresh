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

package de.metas.vertical.healthcare.alberta.bpartner.role;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.vertical.healthcare.alberta.model.X_C_BPartner_AlbertaRole;

public enum AlbertaRoleType implements ReferenceListAwareEnum
{
	CAREGIVER(X_C_BPartner_AlbertaRole.ALBERTAROLE_Caregiver),
	CARETAKER(X_C_BPartner_AlbertaRole.ALBERTAROLE_Caretaker),
	GENERALPRACTITIONER(X_C_BPartner_AlbertaRole.ALBERTAROLE_GeneralPractitioner),
	HEALTHINSURANCE(X_C_BPartner_AlbertaRole.ALBERTAROLE_HealthInsurance),
	HOSTPITAL(X_C_BPartner_AlbertaRole.ALBERTAROLE_Hostpital),
	MAINPRODUCER(X_C_BPartner_AlbertaRole.ALBERTAROLE_MainProducer),
	NURSINGHOME(X_C_BPartner_AlbertaRole.ALBERTAROLE_NursingHome),
	NURSINGSERVICE(X_C_BPartner_AlbertaRole.ALBERTAROLE_NursingService),
	PAYER(X_C_BPartner_AlbertaRole.ALBERTAROLE_Payer),
	DOCTOR(X_C_BPartner_AlbertaRole.ALBERTAROLE_Doctor),
	PHARMACY(X_C_BPartner_AlbertaRole.ALBERTAROLE_Pharmacy),
	PREFERREDPHARMACY(X_C_BPartner_AlbertaRole.ALBERTAROLE_PreferredPharmacy),
	PACIENT(X_C_BPartner_AlbertaRole.ALBERTAROLE_Pacient);

	private final String code;

	private AlbertaRoleType(final String code)
	{
		this.code = code;
	}

	@Override
	public String getCode()
	{
		return code;
	}
}
