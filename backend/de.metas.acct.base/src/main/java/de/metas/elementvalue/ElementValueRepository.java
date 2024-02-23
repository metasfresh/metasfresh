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

import de.metas.acct.api.impl.ElementValueId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ElementValueRepository
{
	public ElementValue getById(@NonNull final ElementValueId id)
	{
		final I_C_ElementValue record = getElementValueRecordById(id);

		Check.assumeNotNull(record, "Element Value not null");

		return toElementValue(record);
	}

	/** TODO make private and only return ElementValue. */
	I_C_ElementValue getElementValueRecordById(@NonNull final ElementValueId id)
	{
		return load(id, I_C_ElementValue.class);
	}

	/** TODO make private and only return ElementValue. */
	public I_C_Element getElementRecordById(@NonNull final ElementId id)
	{
		return load(id, I_C_Element.class);
	}

<<<<<<< HEAD
	public void save(@NonNull final I_C_ElementValue record)
=======
	void save(@NonNull final I_C_ElementValue record)
>>>>>>> 17f25c32dfe (Export all accounts (#17430))
	{
		saveRecord(record);
	}

	/**
	 * If we never need the whole tree then make this method private and add the children directly to ElementValue.
	 * Anyways, avoid returning {@link I_C_ElementValue}
	 */
	public Map<String, I_C_ElementValue> retrieveChildren(@NonNull final ElementValueId parentId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_ElementValue.class)
				.addEqualsFilter(I_C_ElementValue.COLUMNNAME_Parent_ID, parentId)
				.addOnlyContextClient()
				.create()
				.setOnlyActiveRecords(true)
				.map(I_C_ElementValue.class, I_C_ElementValue::getValue);
	}

	@NonNull
	private ElementValue toElementValue(@NonNull final I_C_ElementValue record)
	{
		return ElementValue.builder()
				.id(ElementValueId.ofRepoId(record.getC_ElementValue_ID()))
				.elementId(ElementId.ofRepoId(record.getC_Element_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getValue())
				.name(record.getName())
				.parentId(ElementValueId.ofRepoIdOrNull(record.getParent_ID()))
				.seqNo(record.getSeqNo())
				.build();
	}
<<<<<<< HEAD
=======

	ElementValue createOrUpdate(@NonNull final ElementValueCreateOrUpdateRequest request)
	{
		//
		// Validate
		if (request.getParentId() != null)
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

		return toElementValue(record);
	}

	ImmutableSet<ElementValueId> getElementValueIdsBetween(final String accountValueFrom, final String accountValueTo)
	{
		final RPadQueryFilterModifier rpad = new RPadQueryFilterModifier(20, "0");

		final I_C_ElementValue from = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, accountValueFrom + "%")
				.setLimit(QueryLimit.ONE)
				.orderBy(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();

		final I_C_ElementValue to = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, accountValueTo + "%")
				.setLimit(QueryLimit.ONE)
				.orderByDescending(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();

		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_C_ElementValue.COLUMNNAME_Value, from.getValue(), to.getValue(), rpad)
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
>>>>>>> 17f25c32dfe (Export all accounts (#17430))
}
