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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.interceptor.C_ElementValue;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ElementValueService
{
	private final ElementValueRepository elementValueRepository;
	private final TreeNodeService treeNodeService;

	public ElementValueService(
			@NonNull final ElementValueRepository elementValueRepository,
			@NonNull final TreeNodeService treeNodeService)
	{
		this.elementValueRepository = elementValueRepository;
		this.treeNodeService = treeNodeService;
	}

	public static ElementValueService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ElementValueService(
				new ElementValueRepository(),
				new TreeNodeService(
						new TreeNodeRepository(),
						new ChartOfAccountsService(new ChartOfAccountsRepository())
				)
		);
	}

	public ElementValue getById(@NonNull final ElementValueId id)
	{
		return elementValueRepository.getById(id);
	}

	public Optional<ElementValue> getByAccountNo(@NonNull final String accountNo, @NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		return elementValueRepository.getByAccountNo(accountNo, chartOfAccountsId);
	}

	public ElementValue createOrUpdate(@NonNull final ElementValueCreateOrUpdateRequest request)
	{
		return elementValueRepository.createOrUpdate(request);
	}

	public void reorderByAccountNo(@NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		final ArrayList<I_C_ElementValue> roots = new ArrayList<>();
		final ArrayListMultimap<ElementValueId, I_C_ElementValue> elementValuesByParentId = ArrayListMultimap.create();

		for (final I_C_ElementValue elementValue : elementValueRepository.getAllRecordsByChartOfAccountsId(chartOfAccountsId))
		{
			final ElementValueId parentId = ElementValueId.ofRepoIdOrNull(elementValue.getParent_ID());
			if (parentId == null)
			{
				roots.add(elementValue);
			}
			else
			{
				elementValuesByParentId.put(parentId, elementValue);
			}
		}

		final ArrayList<ElementValue> savedElementValues = new ArrayList<>();
		try (final IAutoCloseable ignored = C_ElementValue.temporaryDisableUpdateTreeNode())
		{
			for (final I_C_ElementValue root : roots)
			{
				root.setSeqNo(0);
				elementValueRepository.save(root);

				savedElementValues.add(ElementValueRepository.toElementValue(root));
			}

			for (final ElementValueId parentId : elementValuesByParentId.keySet())
			{
				final List<I_C_ElementValue> children = elementValuesByParentId.get(parentId);
				sortByAccountNoAndSave(children);

				children.forEach(child -> savedElementValues.add(ElementValueRepository.toElementValue(child)));
			}
		}

		if (!savedElementValues.isEmpty())
		{
			treeNodeService.recreateTree(savedElementValues);
		}
	}

	public void changeParentAndReorderByAccountNo(@NonNull final ElementValueParentChangeRequest request)
	{
		final ElementValueId newParentId = request.getNewParentId();
		final ElementValue newParent = elementValueRepository.getById(newParentId);
		if (!newParent.isSummary())
		{
			throw new AdempiereException("Parent element value must be a summary element value: " + newParent.getValue());
		}

		final I_C_ElementValue record = elementValueRepository.getRecordById(request.getElementValueId());
		record.setParent_ID(newParentId.getRepoId());
		// evRepo.save(record); // no need to save, we assume it will be saved when the SeqNo change will be saved too (see below)

		final HashMap<Integer, I_C_ElementValue> childrenById = elementValueRepository.getAllRecordsByParentId(newParentId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(I_C_ElementValue::getC_ElementValue_ID));
		childrenById.put(record.getC_ElementValue_ID(), record); // make sure *our* record is part of the map because our record contains the parent change

		sortByAccountNoAndSave(childrenById.values());
	}

	private void sortByAccountNoAndSave(@NonNull final Collection<I_C_ElementValue> records)
	{
		assertSameNotNullParentId(records);

		final ImmutableList<I_C_ElementValue> recordsSorted = records.stream()
				.sorted(Comparator.comparing(I_C_ElementValue::getValue)
						.thenComparing(I_C_ElementValue::getC_ElementValue_ID)) // just to have a predictable order
				.collect(ImmutableList.toImmutableList());

		final AtomicInteger nextSeqNo = new AtomicInteger(1);

		recordsSorted.forEach(record -> {
			final int seqNo = nextSeqNo.getAndIncrement();
			record.setSeqNo(seqNo);
			elementValueRepository.save(record);
		});
	}

	private void assertSameNotNullParentId(@NonNull final Collection<I_C_ElementValue> records)
	{
		if (records.isEmpty())
		{
			return;
		}

		ElementValueId commonParentId = null;
		for (final I_C_ElementValue record : records)
		{
			final ElementValueId parentId = ElementValueId.ofRepoIdOrNull(record.getParent_ID());
			if (parentId == null)
			{
				throw new AdempiereException("Element value has no parent set: " + record.getValue() + " (ID=" + record.getC_ElementValue_ID() + ")");
			}

			if (commonParentId == null)
			{
				commonParentId = parentId;
			}
			else if (!commonParentId.equals(parentId))
			{
				throw new AdempiereException("Element values have different parents: " + records);
			}
		}
	}

	// TODO: introduce ChartOfAccountsId as parameter
	public ImmutableSet<ElementValueId> getElementValueIdsBetween(final String accountValueFrom, final String accountValueTo)
	{
		return elementValueRepository.getElementValueIdsBetween(accountValueFrom, accountValueTo);
	}
}