package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

		final AttributeId attributeId = AttributeId.ofRepoId(record.getM_Attribute_ID());
		if (!attributesService.isStorageRelevant(attributeId))
		{
			return;
		}

		final HUAttributeChange change = extractHUAttributeChange(record);

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final HUAttributeChangesCollector changesCollector = trx != null
				? getOrCreateCollector(trx)
				: new HUAttributeChangesCollector(materialEventService);

		changesCollector.collect(change);

		if (trx == null)
		{
			changesCollector.createAndPostMaterialEvents();
		}
	}

	private HUAttributeChangesCollector getOrCreateCollector(@NonNull final ITrx trx)
	{
		return trx.getProperty(HUAttributeChanges.class.getName(), () -> {
			final HUAttributeChangesCollector collector = new HUAttributeChangesCollector(materialEventService);
			trx.runAfterCommit(() -> collector.createAndPostMaterialEvents());
			return collector;
		});
	}

	private static HUAttributeChange extractHUAttributeChange(final I_M_HU_Attribute record)
	{
		final I_M_HU_Attribute recordBeforeChanges = InterfaceWrapperHelper.createOld(record, I_M_HU_Attribute.class);

		return HUAttributeChange.builder()
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				//
				.valueString(record.getValue())
				.valueStringOld(recordBeforeChanges.getValue())
				//
				.valueNumber(extractValueNumberOrNull(record))
				.valueNumberOld(extractValueNumberOrNull(recordBeforeChanges))
				//
				.valueDate(record.getValueDate())
				.valueDateOld(recordBeforeChanges.getValueDate())
				//
				.build();
	}

	private static BigDecimal extractValueNumberOrNull(final I_M_HU_Attribute record)
	{
		return !InterfaceWrapperHelper.isNull(record, I_M_HU_Attribute.COLUMNNAME_ValueNumber)
				? record.getValueNumber()
				: null;
	}

	@Value
	@Builder(toBuilder = true)
	private static class HUAttributeChange
	{
		@NonNull
		final HuId huId;
		@NonNull
		final AttributeId attributeId;

		final String valueString;
		final String valueStringOld;

		final BigDecimal valueNumber;
		final BigDecimal valueNumberOld;

		final Timestamp valueDate;
		final Timestamp valueDateOld;

		public HUAttributeChange mergeWithNextChange(final HUAttributeChange nextChange)
		{
			Check.assumeEquals(attributeId, nextChange.attributeId, "Invalid attributeId for {}. Expected: {}", nextChange, attributeId);

			return toBuilder()
					.valueString(nextChange.getValueString())
					.valueNumber(nextChange.getValueNumber())
					.valueDate(nextChange.getValueDate())
					.build();
		}
	}

	private static class HUAttributeChanges
	{
		private final HuId huId;
		private final HashMap<AttributeId, HUAttributeChange> attributes = new HashMap<>();

		public HUAttributeChanges(@NonNull final HuId huId)
		{
			this.huId = huId;
		}

		public void collect(@NonNull HUAttributeChange change)
		{
			Check.assumeEquals(huId, change.getHuId(), "Invalid HuId for {}. Expected: {}", change, huId);

			attributes.compute(change.getAttributeId(), (attributeId, previousChange) -> mergeChange(previousChange, change));
		}

		private static HUAttributeChange mergeChange(
				@Nullable final HUAttributeChange previousChange,
				@NonNull final HUAttributeChange currentChange)
		{
			return previousChange != null
					? previousChange.mergeWithNextChange(currentChange)
					: currentChange;
		}
	}

	private static class HUAttributeChangesCollector
	{
		private final PostMaterialEventService materialEventService;

		private final AtomicBoolean disposed = new AtomicBoolean();
		private final HashMap<HuId, HUAttributeChanges> huAttributeChanges = new HashMap<>();

		public HUAttributeChangesCollector(@NonNull final PostMaterialEventService materialEventService)
		{
			this.materialEventService = materialEventService;
		}

		public void collect(@NonNull final HUAttributeChange change)
		{
			Check.assume(!disposed.get(), "Collector shall not be disposed: {}", this);
			final HUAttributeChanges huChanges = huAttributeChanges.computeIfAbsent(change.getHuId(), HUAttributeChanges::new);
			huChanges.collect(change);
		}

		public void createAndPostMaterialEvents()
		{
			if (disposed.getAndSet(true))
			{
				throw new AdempiereException("Collector was already disposed: " + this);
			}

			final ImmutableList<MaterialEvent> events = huAttributeChanges.values()
					.stream()
					.map(this::createMaterialEvent)
					.collect(ImmutableList.toImmutableList());

			this.huAttributeChanges.clear();

			materialEventService.postEventsNow(events);
		}

		private MaterialEvent createMaterialEvent(final HUAttributeChanges changes)
		{
			// TODO: introduce and handle a new event which implements MaterialEvent
			throw new UnsupportedOperationException();
		}
	}
}
