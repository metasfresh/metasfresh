package de.metas.marketing.base.process;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_BPartner;

import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class MKTG_ContactPerson_ProcessParams
{
	@Nullable
	private IQueryFilter<I_C_BPartner> selectionFilter;
	@NonNull
	private CampaignId campaignId;
	@Nullable
	private DefaultAddressType addresType;
	private boolean removeAllExistingContactsFromCampaign;

}
