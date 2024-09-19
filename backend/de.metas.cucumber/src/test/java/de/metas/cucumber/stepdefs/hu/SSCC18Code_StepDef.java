/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;

public class SSCC18Code_StepDef

{

	@And("setup the SSCC18 code generator with GS1ManufacturerCode {int}, GS1ExtensionDigit {int} and next sequence number always={int}.")
	public void setupTheSSCCCodeGeneratorWithGSManufacturerCodeGSExtensionDigitAndNextSequenceNumber(
			final int manufacturerCode,
			final int extensionDigit,
			final int nextSequenceNumber)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, String.valueOf(manufacturerCode), ClientId.SYSTEM, OrgId.ANY);
		sysConfigBL.setValue(SSCC18CodeBL.SYSCONFIG_ExtensionDigit, String.valueOf(extensionDigit), ClientId.SYSTEM, OrgId.ANY);

		Services.registerService(ISSCC18CodeBL.class, new SSCC18CodeBL(orgId -> nextSequenceNumber));
	}

	@And("reset the SSCC18 code generator's next sequence number back to its actual AD_Sequence.")
	public void resetTheSSCCCodeGenerator()
	{
		Services.registerService(ISSCC18CodeBL.class, new SSCC18CodeBL());
	}
}
