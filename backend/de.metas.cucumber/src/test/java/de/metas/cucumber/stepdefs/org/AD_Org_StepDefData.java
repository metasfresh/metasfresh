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

package de.metas.cucumber.stepdefs.org;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.organization.OrgId;
import org.compiere.model.I_AD_Org;

public class AD_Org_StepDefData extends StepDefData<I_AD_Org>
		implements StepDefDataGetIdAware<OrgId, I_AD_Org>
{
	public AD_Org_StepDefData()
	{
		super(I_AD_Org.class);
	}

	@Override
	public OrgId extractIdFromRecord(final I_AD_Org record) {return OrgId.ofRepoId(record.getAD_Org_ID());}

	public int getIdAsInt(final StepDefDataIdentifier identifier) {return getId(identifier).getRepoId();}
}
