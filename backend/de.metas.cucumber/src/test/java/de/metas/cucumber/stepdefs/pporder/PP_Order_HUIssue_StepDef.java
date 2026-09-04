/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;

/**
 * Issues a whole handling unit to a manufacturing order's BOM line, the way the production desktop's
 * "issue whole HU" process does it.
 */
@RequiredArgsConstructor
public class PP_Order_HUIssue_StepDef
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBomLineTable;

	/**
	 * Mirrors {@code WEBUI_PP_Order_HUEditor_IssueTopLevelHUs}: the HU's own on-hand quantity is issued
	 * as-is, regardless of the BOM line's requirement, instead of the BOM line's requirement capping it.
	 */
	@And("^the handling unit identified by (.*) is issued whole to PP_Order_BOMLine (.*)$")
	public void issue_whole_hu_to_bom_line(
			@NonNull final String huIdentifier,
			@NonNull final String bomLineIdentifier)
	{
		final I_M_HU hu = huTable.get(huIdentifier);
		final I_PP_Order_BOMLine bomLine = ppOrderBomLineTable.get(bomLineIdentifier);
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(bomLine.getPP_Order_ID());

		final HUPPOrderIssueProducer issueProducer = huPPOrderBL.createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue the whole HU, not the BOM line's requirement
				.targetOrderBOMLine(bomLine);

		issueProducer.createIssue(hu);
	}
}
