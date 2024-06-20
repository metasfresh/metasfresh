package de.metas.dunning.invoice;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DunningService
{

	final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
	final IBPartnerDAO partnersRepo = Services.get(IBPartnerDAO.class);

	public List<I_C_Invoice> retrieveDunnedInvoices(@NonNull final DunningDocId dunningDocId)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_DunningDoc_Line.class)
				.addEqualsFilter(I_C_DunningDoc_Line.COLUMN_C_DunningDoc_ID, dunningDocId)
				.andCollectChildren(I_C_DunningDoc_Line_Source.COLUMN_C_DunningDoc_Line_ID)
				.andCollect(I_C_DunningDoc_Line_Source.COLUMN_C_Dunning_Candidate_ID)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMN_AD_Table_ID, getTableId(I_C_Invoice.class))
				.andCollect(I_C_Dunning_Candidate.COLUMN_Record_ID, I_C_Invoice.class)
				.create()
				.list();
	}

	@Nullable
	public String getLocationEmail(@NonNull final DunningDocId dunningDocId)
	{
		final I_C_DunningDoc dunningDocRecord = dunningDAO.getByIdInTrx(dunningDocId);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(dunningDocRecord.getC_BPartner_ID());
		final I_C_BPartner_Location bpartnerLocation = partnersRepo.getBPartnerLocationByIdInTrx(BPartnerLocationId.ofRepoId(bpartnerId, dunningDocRecord.getC_BPartner_Location_ID()));

		final String locationEmail = bpartnerLocation.getEMail();
		if (!Check.isEmpty(locationEmail))
		{
			return locationEmail;
		}

		final BPartnerContactId dunningContactId = BPartnerContactId.ofRepoIdOrNull(bpartnerId, dunningDocRecord.getC_Dunning_Contact_ID());

		if (dunningContactId == null)
		{
			return null;
		}

		return partnersRepo.getContactLocationEmail(dunningContactId);
	}
}
