/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.common.bprelation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "JsonBPRelationRole: \n" +
		"* `MainProducer` - Hauptlieferant\n" +
		"* `Hospital` - Krankenhaus / Hostpital\n" +
		"* `PhysicianDoctor` - Arzt / Doctor\n" +
		"* `GeneralPractitioner` - Hausarzt / General practitioner\n" +
		"* `HealthInsurance` - Krankenkasse / Health Insurance\n" +
		"* `NursingHome` - Pflegeheim / Nursing home\n" +
		"* `Caregiver` - Betreuer / Caregiver\n" +
		"* `CareTaker` - Patientenkontakt / Caretaker\n" +
		"* `PreferredPharmacy` - Wunschapotheke / Preferred Pharmacy\n" +
		"* `NursingService` - Pflegedienst / Nursing service\n" +
		"* `Payer` - Payer / Payer\n" +
		"* `Pharmacy` - Apotheke / Pharmacy\n" +
		"")
public enum JsonBPRelationRole
{
	MainProducer,
	Hospital,
	PhysicianDoctor,
	GeneralPractitioner,
	HealthInsurance,
	NursingHome,
	Caregiver,
	CareTaker,
	PreferredPharmacy,
	NursingService,
	Payer,
	Pharmacy
}
