package de.metas.handlingunits.material.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.material.event.PostMaterialEventService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Interceptor(I_M_HU_Attribute.class)
@Component
public class M_HU_Attribute
{
	private final IAttributesBL attributesService = Services.get(IAttributesBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUTrxBL huTrxBL=Services.get(IHUTrxBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	
	private final PostMaterialEventService materialEventService;

	public M_HU_Attribute(@NonNull final PostMaterialEventService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_M_HU_Attribute.COLUMNNAME_Value,
			I_M_HU_Attribute.COLUMNNAME_ValueNumber,
			I_M_HU_Attribute.COLUMNNAME_ValueDate
	})
	public void onAttributeChanged(@NonNull final I_M_HU_Attribute record)
	{
		Check.assume(record.isActive(), "changing IsActive flag to false is not allowed: {}", record);

		if(handlingUnitsBL.isHULoaderInProgress())
		{
			// don't fire attribute change events from within the HU loader, because there we don't have *real* HU-attribute-changes.
			// It's rather e.g. HUs are split and then HU-attributes are updated from the split source.
			// this MI on the other hand is explicitly about existing HUs where attributes such as the time-until-expiry are changed
			return; 
		}
		
		final AttributeId attributeId = AttributeId.ofRepoId(record.getM_Attribute_ID());
		final I_M_Attribute attribute = attributesService.getAttributeById(attributeId);
		if (!attribute.isStorageRelevant())
		{
			return;
		}

		final HUAttributeChange change = extractHUAttributeChange(record, attribute);
		if (Objects.equals(change.getOldAttributeKeyPartOrNull(), change.getNewAttributeKeyPartOrNull()))
		{
			return;
		}

		getOrCreateCollector().collect(change);
	}


	private HUAttributeChangesCollector getOrCreateCollector()
	{
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail); // at this point we always run in trx
		return trx.getPropertyAndProcessAfterCommit(
				HUAttributeChanges.class.getName(),
				() -> new HUAttributeChangesCollector(materialEventService),
				HUAttributeChangesCollector::createAndPostMaterialEvents);
	}

	private HUAttributeChange extractHUAttributeChange(
			final I_M_HU_Attribute record,
			final I_M_Attribute attribute)
	{
		final I_M_HU_Attribute recordBeforeChanges = InterfaceWrapperHelper.createOld(record, I_M_HU_Attribute.class);

		final AttributeValueType attributeValueType = AttributeValueType.ofCode(attribute.getAttributeValueType());

		final Object valueNew;
		final Object valueOld;
		if (AttributeValueType.STRING.equals(attributeValueType))
		{
			valueNew = record.getValue();
			valueOld = recordBeforeChanges.getValue();
		}
		else if (AttributeValueType.NUMBER.equals(attributeValueType))
		{
			valueNew = extractValueNumberOrNull(record);
			valueOld = extractValueNumberOrNull(recordBeforeChanges);
		}
		else if (AttributeValueType.DATE.equals(attributeValueType))
		{
			valueNew = record.getValueDate();
			valueOld = recordBeforeChanges.getValueDate();
		}
		else if (AttributeValueType.LIST.equals(attributeValueType))
		{
			final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());

			final AttributeListValue attributeListValueNew = attributesService.retrieveAttributeValueOrNull(attributeId, record.getValue());
			valueNew = attributeListValueNew != null ? attributeListValueNew.getId() : null;

			final AttributeListValue attributeListValueOld = attributesService.retrieveAttributeValueOrNull(attributeId, recordBeforeChanges.getValue());
			valueOld = attributeListValueOld != null ? attributeListValueOld.getId() : null;
		}
		else
		{
			throw new AdempiereException("Unknown attribute value type: " + attributeValueType);
		}

		return HUAttributeChange.builder()
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.attributeValueType(attributeValueType)
				.valueNew(valueNew)
				.valueOld(valueOld)
				.date(TimeUtil.asInstantNonNull(record.getUpdated()))
				.build();
	}

	private static BigDecimal extractValueNumberOrNull(final I_M_HU_Attribute record)
	{
		return !InterfaceWrapperHelper.isNull(record, I_M_HU_Attribute.COLUMNNAME_ValueNumber)
				? record.getValueNumber()
				: null;
	}
}
