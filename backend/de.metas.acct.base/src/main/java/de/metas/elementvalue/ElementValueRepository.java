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

import com.google.common.annotations.VisibleForTesting;
<<<<<<< HEAD
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
=======
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.RPadQueryFilterModifier;
<<<<<<< HEAD
=======
import org.adempiere.ad.dao.impl.StringToNumericModifier;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
import java.math.BigDecimal;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ElementValueRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
<<<<<<< HEAD

	ElementValue getById(@NonNull final ElementValueId id)
	{
		final I_C_ElementValue record = getRecordById(id);
		return toElementValue(record);
=======
	private final CCache<Integer, ElementValuesMap> cache = CCache.<Integer, ElementValuesMap>builder()
			.tableName(I_C_ElementValue.Table_Name)
			.build();

	ElementValue getById(@NonNull final ElementValueId id)
	{
		return getMap().getById(id);
	}

	private ElementValuesMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private ElementValuesMap retrieveMap()
	{
		final ImmutableList<ElementValue> list = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.stream()
				.map(ElementValueRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new ElementValuesMap(list);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@NonNull
	I_C_ElementValue getRecordById(@NonNull final ElementValueId id)
	{
		final I_C_ElementValue record = load(id, I_C_ElementValue.class);
		Check.assumeNotNull(record, "C_ElementValue exists for {}", id);
		return record;
	}

	Optional<ElementValue> getByAccountNo(@NonNull final String accountNo, @NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_Value, accountNo)
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_C_Element_ID, chartOfAccountsId)
				.create()
				.firstOnlyOptional(I_C_ElementValue.class)
<<<<<<< HEAD
				.map(ElementValueRepository::toElementValue);
	}


=======
				.map(ElementValueRepository::fromRecord);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	void save(@NonNull final I_C_ElementValue record)
	{
		saveRecord(record);
	}

	List<I_C_ElementValue> getAllRecordsByParentId(@NonNull final ElementValueId parentId)
	{
		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				//.addOnlyActiveRecordsFilter() // commented because we return ALL
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_Parent_ID, parentId)
				.create()
				.list();
	}

	@NonNull
<<<<<<< HEAD
	public static ElementValue toElementValue(@NonNull final I_C_ElementValue record)
=======
	public static ElementValue fromRecord(@NonNull final I_C_ElementValue record)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return ElementValue.builder()
				.id(ElementValueId.ofRepoId(record.getC_ElementValue_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.chartOfAccountsId(ChartOfAccountsId.ofRepoId(record.getC_Element_ID()))
				.value(record.getValue())
				.name(record.getName())
				.accountSign(record.getAccountSign())
<<<<<<< HEAD
				.accountType(record.getAccountType())
=======
				.accountType(AccountType.ofCode(record.getAccountType()))
				.isActive(record.isActive())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.isSummary(record.isSummary())
				.isDocControlled(record.isDocControlled())
				.isPostActual(record.isPostActual())
				.isPostBudget(record.isPostBudget())
				.isPostStatistical(record.isPostStatistical())
				.parentId(ElementValueId.ofRepoIdOrNull(record.getParent_ID()))
				.seqNo(record.getSeqNo())
				.defaultAccountName(record.getDefault_Account())
				.build();
	}

	ElementValue createOrUpdate(@NonNull final ElementValueCreateOrUpdateRequest request)
	{
		//
		// Validate
<<<<<<< HEAD
		if(request.getParentId() != null)
=======
		if (request.getParentId() != null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			final ElementValue parent = getById(request.getParentId());
			if (!parent.isSummary())
			{
				throw new AdempiereException("Parent element value must be a summary element value: " + parent.getValue());
			}
		}

		//
		// Actual update & save
		final ElementValueId existingElementValueId = request.getExistingElementValueId();
		final I_C_ElementValue record = existingElementValueId != null
				? getRecordById(existingElementValueId)
				: InterfaceWrapperHelper.newInstance(I_C_ElementValue.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_Element_ID(request.getChartOfAccountsId().getRepoId());
		record.setValue(request.getValue());
		record.setName(request.getName());
		record.setAccountSign(request.getAccountSign());
		record.setAccountType(request.getAccountType());
		record.setIsSummary(request.isSummary());
		record.setIsDocControlled(request.isDocControlled());
		record.setPostActual(request.isPostActual());
		record.setPostBudget(request.isPostBudget());
		record.setPostStatistical(request.isPostStatistical());
		record.setParent_ID(ElementValueId.toRepoId(request.getParentId()));
		if (request.getSeqNo() != null)
		{
			record.setSeqNo(request.getSeqNo());
		}
		record.setDefault_Account(request.getDefaultAccountName());

		InterfaceWrapperHelper.saveRecord(record);

<<<<<<< HEAD
		return toElementValue(record);
	}

	ImmutableSet<ElementValueId> getElementValueIdsBetween(final String accountValueFrom, final String accountValueTo)
	{
		final RPadQueryFilterModifier rpad = new RPadQueryFilterModifier(20, "0");

=======
		return fromRecord(record);
	}

	public enum AccountValueComparisonMode
	{
		RESPECT_ACCOUNTS_TREE,
		NUMERIC,
		;

		public static AccountValueComparisonMode ofNullableString(@Nullable final String value)
		{
			final String valueNorm = StringUtils.trimBlankToNull(value);
			if (valueNorm == null)
			{
				return RESPECT_ACCOUNTS_TREE;
			}
			else
			{
				return valueOf(valueNorm);
			}
		}
	}

	ImmutableSet<ElementValueId> getElementValueIdsBetween(final String accountValueFrom, final String accountValueTo, @NonNull final AccountValueComparisonMode comparisonMode)
	{
		switch (comparisonMode)
		{
			case RESPECT_ACCOUNTS_TREE:
				return getElementValueIdsBetween_RESPECT_ACCOUNTS_TREE(accountValueFrom, accountValueTo);
			case NUMERIC:
				return getElementValueIdsBetween_NUMERIC(accountValueFrom, accountValueTo);
			default:
				throw new AdempiereException("Unknown comparison mode: " + comparisonMode);
		}
	}

	private ImmutableSet<ElementValueId> getElementValueIdsBetween_RESPECT_ACCOUNTS_TREE(final String accountValueFrom, final String accountValueTo)
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final I_C_ElementValue from = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, accountValueFrom + "%")
				.setLimit(QueryLimit.ONE)
				.orderBy(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();
<<<<<<< HEAD
=======
		final String fromValue = from != null ? from.getValue() : null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final I_C_ElementValue to = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, accountValueTo + "%")
				.setLimit(QueryLimit.ONE)
				.orderByDescending(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();
<<<<<<< HEAD

		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_C_ElementValue.COLUMNNAME_Value, from.getValue(), to.getValue(), rpad)
=======
		final String toValue = to != null ? to.getValue() : null;

		if(from == null || to == null)
		{
			return ImmutableSet.of();
		}

		final RPadQueryFilterModifier rpad = new RPadQueryFilterModifier(20, "0");

		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_C_ElementValue.COLUMNNAME_Value, fromValue, toValue, rpad)
				.create()
				.listIds(ElementValueId::ofRepoId);
	}

	private ImmutableSet<ElementValueId> getElementValueIdsBetween_NUMERIC(final String accountValueFrom, final String accountValueTo)
	{
		final BigDecimal from = NumberUtils.asBigDecimal(accountValueFrom);
		final BigDecimal to = NumberUtils.asBigDecimal(accountValueTo);

		final StringToNumericModifier stringToNumericModifier = new StringToNumericModifier();

		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_C_ElementValue.COLUMNNAME_Value, from, to, stringToNumericModifier)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.create()
				.listIds(ElementValueId::ofRepoId);
	}

	@VisibleForTesting
	public List<I_C_ElementValue> getAllRecordsByChartOfAccountsId(final ChartOfAccountsId chartOfAccountsId)
	{
		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				//.addOnlyActiveRecordsFilter() // commented because we return ALL
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_C_Element_ID, chartOfAccountsId)
				.create()
				.list();
	}
<<<<<<< HEAD
=======

	//
	//
	//
	//
	//

	private static final class ElementValuesMap
	{
		private final ImmutableMap<ElementValueId, ElementValue> byId;
		private ImmutableSet<ElementValueId> _openItemIds;

		private ElementValuesMap(final List<ElementValue> list)
		{
			byId = Maps.uniqueIndex(list, ElementValue::getId);
		}

		public ElementValue getById(final ElementValueId id)
		{
			final ElementValue elementValue = byId.get(id);
			if (elementValue == null)
			{
				throw new AdempiereException("No Element Value found for " + id);
			}
			return elementValue;
		}

	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
