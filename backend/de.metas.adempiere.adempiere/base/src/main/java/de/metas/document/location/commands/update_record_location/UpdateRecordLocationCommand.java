/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.location.commands.update_record_location;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.location.adapter.IDocumentLocationAdapterTemplate;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import java.util.Optional;
import java.util.function.Function;

public class UpdateRecordLocationCommand<RECORD, ADAPTER extends IDocumentLocationAdapterTemplate>
{
	private final DocumentLocationBL documentLocationBL;
	private final IBPartnerDAO bpartnerDAO;

	@NonNull
	private final RECORD record;
	@NonNull
	private final Class<RECORD> recordType;
	@NonNull
	private final ModelChangeType timing;
	@NonNull
	private final Function<RECORD, ADAPTER> toDocumentLocationAdapter;
	@NonNull
	private final Function<ADAPTER, Optional<DocumentLocation>> toPlainDocumentLocation;

	@Builder
	private UpdateRecordLocationCommand(
			final @NonNull DocumentLocationBL documentLocationBL,
			final @NonNull IBPartnerDAO bpartnerDAO,
			//
			final @NonNull RECORD record,
			final @NonNull Class<RECORD> recordType,
			final @NonNull ModelChangeType timing,
			final @NonNull Function<RECORD, ADAPTER> toDocumentLocationAdapter,
			final @NonNull Function<ADAPTER, Optional<DocumentLocation>> toPlainDocumentLocation)
	{
		this.documentLocationBL = documentLocationBL;
		this.bpartnerDAO = bpartnerDAO;

		this.record = record;
		this.recordType = recordType;
		this.timing = timing;
		this.toDocumentLocationAdapter = toDocumentLocationAdapter;
		this.toPlainDocumentLocation = toPlainDocumentLocation;
	}

	public void execute()
	{
		// do nothing if we are cloning an order
		if (InterfaceWrapperHelper.getDynAttribute(record, PO.DYNATTR_CopyRecordSupport) != null)
		{
			return;
		}

		final DocumentLocationCurrentAndPrevious currentAndPrevLocation = toDocumentLocationCurrentAndPrevious(
				record,
				recordType,
				timing,
				toDocumentLocationAdapter,
				toPlainDocumentLocation);

		if (currentAndPrevLocation.getCurrent() == null)
		{
			toDocumentLocationAdapter.apply(record).setRenderedAddressAndCapturedLocation(RenderedAddressAndCapturedLocation.NONE);
			return;
		}

		final LocationId oldLocationId = currentAndPrevLocation.getPreviousLocationId().orElse(null);
		final LocationId newLocationId;
		if (currentAndPrevLocation.isUpdateLocationRequiredBecauseRelatedChanges())
		{
			newLocationId = currentAndPrevLocation.getCurrentBPartnerLocationId()
					.map(bpartnerDAO::getBPartnerLocationByIdEvenInactive)
					.map(bpLocation -> LocationId.ofRepoId(bpLocation.getC_Location_ID()))
					.orElse(null);
		}
		else
		{
			newLocationId = currentAndPrevLocation.getCurrentLocationId().orElse(null);
		}

		if (!LocationId.equals(oldLocationId, newLocationId))
		{
			final RenderedAddressAndCapturedLocation renderedAddress = documentLocationBL.computeRenderedAddress(currentAndPrevLocation.getCurrent().withLocationId(newLocationId));
			toDocumentLocationAdapter.apply(record).setRenderedAddressAndCapturedLocation(renderedAddress);
		}
	}

	private static <RECORD, LA> DocumentLocationCurrentAndPrevious toDocumentLocationCurrentAndPrevious(
			@NonNull final RECORD record,
			@NonNull final Class<RECORD> recordType,
			@NonNull final ModelChangeType timing,
			@NonNull final Function<RECORD, LA> toDocumentLocationAdapter,
			@NonNull final Function<LA, Optional<DocumentLocation>> toPlainDocumentLocation)
	{
		final LA currentLocationAdapter = toDocumentLocationAdapter.apply(record);
		final DocumentLocation current = toPlainDocumentLocation.apply(currentLocationAdapter).orElse(null);
		if (timing.isNew())
		{
			return DocumentLocationCurrentAndPrevious.ofNewDocument(current);
		}
		else
		{
			final RECORD recordPreviousValues = InterfaceWrapperHelper.createOld(record, recordType);
			final DocumentLocation previous = toDocumentLocationAdapter.andThen(toPlainDocumentLocation).apply(recordPreviousValues).orElse(null);
			return DocumentLocationCurrentAndPrevious.ofChangedDocument(current, previous);
		}
	}

}
