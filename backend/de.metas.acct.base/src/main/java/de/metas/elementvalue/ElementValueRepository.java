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

	public void save(@NonNull final I_C_ElementValue record)
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
}
