package de.metas.material.event.procurement;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

import static de.metas.material.event.MaterialEventConstants.PMM_PURCHASECANDIDATE_TABLE_NAME;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractPurchaseOfferEvent implements MaterialEvent
{
	@NonNull
	private final EventDescriptor eventDescriptor;

	@NonNull
	private final ProductDescriptor productDescriptor;

	@NonNull
	private final Instant date;

	@NonNull
	private final BigDecimal qty;

	private final int procurementCandidateId;

	public AbstractPurchaseOfferEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final ProductDescriptor productDescriptor,
			@NonNull final Instant date,
			@NonNull final BigDecimal qty,
			final int procurementCandidateId)
	{
		this.eventDescriptor = eventDescriptor;
		this.productDescriptor = productDescriptor;
		this.date = date;
		this.qty = qty;
		this.procurementCandidateId = procurementCandidateId;
	}

	public abstract BigDecimal getQtyDelta();

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(PMM_PURCHASECANDIDATE_TABLE_NAME, procurementCandidateId);
	}
}
