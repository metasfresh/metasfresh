package de.metas.acct.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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

@ToString(of = "elements")
public class AcctSchemaElementsMap implements Iterable<AcctSchemaElement>
{
	public static AcctSchemaElementsMap of(@NonNull final List<AcctSchemaElement> elements)
	{
		if (elements.isEmpty())
		{
			return EMPTY;
		}

		return new AcctSchemaElementsMap(elements);
	}

	private static final AcctSchemaElementsMap EMPTY = new AcctSchemaElementsMap(ImmutableList.of());

	private final ImmutableList<AcctSchemaElement> elements;
	private final ImmutableMap<AcctSchemaElementType, AcctSchemaElement> elementsByType;

	private AcctSchemaElementsMap(final List<AcctSchemaElement> elements)
	{
		this.elements = elements.stream()
				.sorted(Comparator.comparing(AcctSchemaElement::getSeqNo) // NOTE: ordering by SeqNo first it's uber important! (07539)
						.thenComparing(AcctSchemaElement::getElementType))
				.collect(ImmutableList.toImmutableList());

		this.elementsByType = Maps.uniqueIndex(this.elements, AcctSchemaElement::getElementType);
	}

	public boolean isEmpty()
	{
		return elements.isEmpty();
	}

	public AcctSchemaElementsMap onlyDisplayedInEditor()
	{
		final ImmutableList<AcctSchemaElement> elementsFiltered = elements.stream()
				.filter(AcctSchemaElement::isDisplayedInEditor)
				.collect(ImmutableList.toImmutableList());

		if (elementsFiltered.size() == elements.size())
		{
			return this;
		}
		else
		{
			return of(elementsFiltered);
		}
	}

	@Nullable
	public AcctSchemaElement getByElementType(@NonNull final AcctSchemaElementType elementType)
	{
		return elementsByType.get(elementType);
	}

	public ImmutableSet<AcctSchemaElementType> getElementTypes()
	{
		return elementsByType.keySet();
	}

	@Override
	public Iterator<AcctSchemaElement> iterator()
	{
		return elements.iterator();
	}

	public ChartOfAccountsId getChartOfAccountsId()
	{
		final AcctSchemaElement accountSchemaElement = getByElementType(AcctSchemaElementType.Account);
		if (accountSchemaElement == null)
		{
			throw new AdempiereException("No schema element of type " + AcctSchemaElementType.Account + " found");
		}

		final ChartOfAccountsId chartOfAccountsId = accountSchemaElement.getChartOfAccountsId();
		if (chartOfAccountsId == null)
		{
			throw new AdempiereException("No Chart of Accounts defined for " + accountSchemaElement);
		}

		return chartOfAccountsId;
	}

}
