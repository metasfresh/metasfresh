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

package de.metas.document.location;

import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.location.AddressDisplaySequence;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author tsa
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/03120:_Error_in_DocumentLocation_callout_%282012080910000142%29">task</a>
 */
public interface IDocumentLocationBL
{
	RenderedAddressAndCapturedLocation computeRenderedAddress(@NonNull DocumentLocation location);

	String computeRenderedAddressString(@NonNull DocumentLocation location);

	RenderedAddressAndCapturedLocation computeRenderedAddress(@NonNull DocumentLocation location, @Nullable AddressDisplaySequence displaySequence);

	Optional<DocumentLocation> toPlainDocumentLocation(@NonNull IDocumentLocationAdapter locationAdapter);

	Optional<DocumentLocation> toPlainDocumentLocation(@NonNull IDocumentBillLocationAdapter locationAdapter);

	Optional<DocumentLocation> toPlainDocumentLocation(@NonNull IDocumentDeliveryLocationAdapter locationAdapter);

	Optional<DocumentLocation> toPlainDocumentLocation(@NonNull IDocumentHandOverLocationAdapter locationAdapter);

	void updateRenderedAddressAndCapturedLocation(IDocumentLocationAdapter locationAdapter);

	void updateCapturedLocation(IDocumentLocationAdapter locationAdapter);

	void updateCapturedLocation(IDocumentBillLocationAdapter locationAdapter);

	void updateRenderedAddressAndCapturedLocation(IDocumentBillLocationAdapter locationAdapter);

	void updateRenderedAddressAndCapturedLocation(IDocumentDeliveryLocationAdapter locationAdapter);

	void updateCapturedLocation(IDocumentDeliveryLocationAdapter locationAdapter);

	void updateRenderedAddressAndCapturedLocation(IDocumentHandOverLocationAdapter locationAdapter);

	void updateCapturedLocation(IDocumentHandOverLocationAdapter locationAdapter);
}
