/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.bpartner;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BPartnerRestController_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;

	private final TestContext testContext;

	public BPartnerRestController_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final TestContext testContext)
	{
		this.bPartnerTable = bPartnerTable;
		this.testContext = testContext;
	}

	@And("^store bpartner endpointPath (.*) in context$")
	public void store_credit_limit_endpointPath_in_context(@NonNull String endpointPath)
	{
		final String identifierRegex = ".*(:[a-zA-Z]+)/?.*";

		final Pattern pattern = Pattern.compile(identifierRegex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(endpointPath);

		while (matcher.find())
		{
			final String bPartnerIdentifierGroup = matcher.group(1);
			final String bpartnerIdentifier = bPartnerIdentifierGroup.replace(":", "");

			final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
			assertThat(bPartner).isNotNull();

			endpointPath = endpointPath.replace(bPartnerIdentifierGroup, String.valueOf(bPartner.getC_BPartner_ID()));

			testContext.setEndpointPath(endpointPath);
		}
	}
}
