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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductionIssue_StepDef
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	public ProductionIssue_StepDef(final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable)
	{
		this.ppOrderBOMLineTable = ppOrderBOMLineTable;
	}

	@And("^validate no M_HUs available to be issued for bomLine identified by (.*)$")
	public void validate_no_hu_available_to_issue(@NonNull final String bomLineIdentifier)
	{
		final org.eevolution.model.I_PP_Order_BOMLine bomLine = ppOrderBOMLineTable.get(bomLineIdentifier);

		final List<HuId> availableHUs = huPPOrderBL.retrieveAvailableToIssue(bomLine);

		assertThat(availableHUs.size()).isEqualTo(0);
	}

}
