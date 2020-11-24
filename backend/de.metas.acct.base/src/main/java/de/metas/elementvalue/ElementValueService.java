/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.elementvalue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.metas.acct.api.impl.ElementValueId;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Service;

import lombok.NonNull;

/** I think the methods of this service could/should rather be a methods of {@link ElementValue}.
 * The flow would be:
 * <li>get a ElementValue-instance from the {@link ElementValueRepository}. That instance would already contain its children.
 * <li>invoke the ElementValue-instance's methods to do stuff, e.g. add another ElementValue-child, or resequence them
 * <li>call {@link ElementValueRepository#save(ElementValue)} so persist the stuff you did
 *
 */
@Service
public class ElementValueService
{
	private final ElementValueRepository evRepo;

	public ElementValueService(@NonNull final ElementValueRepository evRepo)
	{
		this.evRepo = evRepo;
	}

	public void updateElementValueAndResetSequences(@NonNull final ElementValueRequest request)
	{
		final Map<String, I_C_ElementValue> children = evRepo.retrieveChildren(request.getParentId());

		final I_C_ElementValue record = updateElementValueAndDoNotSave(request);

		// add newly updated
		final Map<String, I_C_ElementValue> childrenSorted = new TreeMap<String, I_C_ElementValue>(children);
		childrenSorted.put(record.getValue(), record);

		// update sequences
		updateSequencesAndSave(request.getParentId(), childrenSorted);
	}

	private I_C_ElementValue updateElementValueAndDoNotSave(@NonNull final ElementValueRequest request)
	{
		final I_C_ElementValue record = evRepo.getElementValueRecordById(request.getElementValueId());
		record.setParent_ID(request.getParentId().getRepoId());

		return record;
	}

	public void updateSequencesAndSave(@NonNull final ElementValueId parentId, @NonNull final Map<String, I_C_ElementValue> childrenSorted)
	{
		final Map<String, Integer> sequences = createSequences(childrenSorted.keySet());

		childrenSorted.forEach((value, record) -> {
			record.setSeqNo(sequences.get(value));
			evRepo.save(record);
		});
	}

	private Map<String, Integer> createSequences(@NonNull final Set<String> keys)
	{
		final Map<String, Integer> sequenceMap = new HashMap<String, Integer>();

		int seqNo = 1;
		for (String key : keys)
		{
			sequenceMap.put(key, seqNo);
			seqNo++;
		}

		return sequenceMap;
	}
}
