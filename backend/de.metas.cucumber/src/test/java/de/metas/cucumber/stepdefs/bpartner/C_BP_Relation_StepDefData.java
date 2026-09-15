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

package de.metas.cucumber.stepdefs.bpartner;

import de.metas.bpartner.service.BPRelationId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_C_BP_Relation;

/** Holds created {@code C_BP_Relation} records for cross-step reference. */
public class C_BP_Relation_StepDefData extends StepDefData<I_C_BP_Relation>
		implements StepDefDataGetIdAware<BPRelationId, I_C_BP_Relation>
{
	public C_BP_Relation_StepDefData()
	{
		super(I_C_BP_Relation.class);
	}

	@Override
	public BPRelationId extractIdFromRecord(final I_C_BP_Relation record)
	{
		return BPRelationId.ofRepoId(record.getC_BP_Relation_ID());
	}
}
