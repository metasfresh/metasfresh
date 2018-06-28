package de.metas.letters.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.letter.BoilerPlateId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Repository
public class LetterRepository
{
	public Letter toCLetter(@NonNull final I_C_Letter letterRecord)
	{
		return Letter.builder()
				.bpartnerId(BPartnerId.ofRepoId(letterRecord.getC_BPartner_ID()))
				.id(LetterId.ofRepoId(letterRecord.getC_BP_Contact_ID()))
				.address(letterRecord.getBPartnerAddress())
				.boilerPlateId(BoilerPlateId.ofRepoId(letterRecord.getAD_BoilerPlate_ID()))
				.body(letterRecord.getLetterBody())
				.body(letterRecord.getLetterBodyParsed())
				.subject(letterRecord.getLetterSubject())
				.build();
	}

	public Letter save(@NonNull final Letter letter)
	{
		final I_C_Letter letterRecord;
		if (letter.getId() == null)
		{
			letterRecord = newInstance(I_C_Letter.class);
		}
		else
		{
			letterRecord = load(letter.getId().getRepoId(), I_C_Letter.class);
		}

		letterRecord.setAD_BoilerPlate_ID(letter.getBoilerPlateId() == null ? -1 : letter.getBoilerPlateId().getRepoId());
		letterRecord.setLetterSubject(letter.getSubject());
		letterRecord.setLetterBody(letter.getBody());
		letterRecord.setLetterBodyParsed(letter.getBodyParsed());
		letterRecord.setBPartnerAddress(letter.getAddress());
		letterRecord.setC_BP_Contact_ID(letter.getUserId() == null ? -1 : letter.getUserId().getRepoId());
		letterRecord.setC_BPartner_ID(letter.getBpartnerId() == null ? -1 : letter.getBpartnerId().getRepoId());
		letterRecord.setC_BPartner_Location_ID(letter.getBpartnerLocationId() == null ? -1 : letter.getBpartnerLocationId().getRepoId());

		saveRecord(letterRecord);

		return letter
				.toBuilder()
				.id(LetterId.ofRepoId(letterRecord.getC_Letter_ID()))
				.build();
	}
}
