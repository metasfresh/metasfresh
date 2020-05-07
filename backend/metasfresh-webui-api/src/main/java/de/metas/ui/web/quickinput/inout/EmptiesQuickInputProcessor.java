package de.metas.ui.web.quickinput.inout;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.edi.model.I_M_InOut;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class EmptiesQuickInputProcessor implements IQuickInputProcessor
{

	@Override
	public Set<DocumentId> process(@NonNull final QuickInput quickInput)
	{
		final I_M_InOut inout = quickInput.getRootDocumentAs(I_M_InOut.class);
		final IEmptiesQuickInput emptiesQuickInput = quickInput.getQuickInputDocumentAs(IEmptiesQuickInput.class);

		final Set<Integer> affectedDocumentIds = EmptiesInOutLinesProducer.newInstance(inout)
				.addSource(emptiesQuickInput.getM_HU_PackingMaterial(), emptiesQuickInput.getQty())
				.create()
				.getAffectedInOutLinesId();

		return affectedDocumentIds.stream()
				.map(DocumentId::of)
				.collect(ImmutableSet.toImmutableSet());
	}

}
