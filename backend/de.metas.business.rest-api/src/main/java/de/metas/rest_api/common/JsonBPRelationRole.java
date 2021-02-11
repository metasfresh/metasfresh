package de.metas.rest_api.common;

import de.pentabyte.springfox.ApiEnum;

/*
 * #%L
 * de.metas.rest_api.common
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

public enum JsonBPRelationRole
{
	@ApiEnum("Hauptlieferant")
	MainProducer,
	@ApiEnum("Krankenhaus / Hostpital")
	Hospital,
	@ApiEnum("Arzt / Doctor")
	PhysicianDoctor,
	@ApiEnum("Hausarzt / General practitioner")
	GeneralPractitioner,
	@ApiEnum("Krankenkasse / Health Insurance")
	HealthInsurance,
	@ApiEnum("Pflegeheim / Nursing home")
	NursingHome,
	@ApiEnum("Betreuer / Caregiver")
	Caregiver,
	@ApiEnum("Wunschapotheke / Preferred Pharmacy")
	PreferredPharmacy,
	@ApiEnum("Pflegedienst / Nursing service")
	NursingService
}
