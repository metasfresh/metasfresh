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

package de.metas.cucumber.stepdefs.gljournal_sap;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import lombok.NonNull;

/**
 * Stores {@link I_SAP_GLJournal} (SAP GL journal headers) by identifier.
 * Registered in {@link de.metas.cucumber.stepdefs.util.IdentifiersResolver}, so an identifier can be used wherever a document identifier is expected
 * (e.g. {@code Wait until documents ... are posted}, {@code Record_ID} of the UStVA report).
 */
public class SAP_GLJournal_StepDefData extends StepDefData<I_SAP_GLJournal>
		implements StepDefDataGetIdAware<SAPGLJournalId, I_SAP_GLJournal>
{
	public SAP_GLJournal_StepDefData()
	{
		super(I_SAP_GLJournal.class);
	}

	@Override
	public SAPGLJournalId extractIdFromRecord(@NonNull final I_SAP_GLJournal record)
	{
		return SAPGLJournalId.ofRepoId(record.getSAP_GLJournal_ID());
	}
}
