package de.metas.elementvalue;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Repository;

import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ElementValueRepository
{
	public ElementValue getById(@NonNull final ElementValueId id)
	{
		final I_C_ElementValue record = load(id, I_C_ElementValue.class);

		Check.assumeNotNull(record, "Element Value not null");

		return toElementValue(record);
	}
	
	public I_C_Element getElementRecordById(@NonNull final ElementId id)
	{
		return load(id, I_C_Element.class);
	}

	@NonNull
	public ElementValue toElementValue(@NonNull final I_C_ElementValue record)
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

	public ElementValue save(@NonNull final ElementValue elementValue)
	{
		final I_C_ElementValue elementValueRecord;
		if (elementValue.getId() == null)
		{
			elementValueRecord = newInstance(I_C_ElementValue.class);
		}
		else
		{
			elementValueRecord = load(elementValue.getId().getRepoId(), I_C_ElementValue.class);
		}
		elementValueRecord.setValue(elementValue.getValue());
		elementValueRecord.setName(elementValue.getName());
		elementValueRecord.setAD_Org_ID(elementValue.getOrgId().getRepoId());
		elementValueRecord.setParent_ID(elementValue.getParentId().getRepoId());
		elementValueRecord.setC_Element_ID(elementValue.getElementId().getRepoId());
		elementValueRecord.setSeqNo(elementValue.getSeqNo());
		saveRecord(elementValueRecord);

		return elementValue
				.toBuilder()
				.id(ElementValueId.ofRepoId(elementValueRecord.getC_ElementValue_ID()))
				.build();
	}
}
