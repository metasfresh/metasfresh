package de.metas.vertical.pharma.msv3.server.stockAvailability;

import java.time.LocalDateTime;

import de.metas.vertical.pharma.msv3.server.types.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@Value
@Builder
public class StockAvailabilityShare
{
	/** the sum of ALL quantity fields = request quantity */
	@NonNull
	Quantity qty;

	@NonNull
	StockAvailabilityShareType type;

	/**
	 * Expected! e.g. 1.1.2011 15:00 Uhr.
	 * For {@link #type} = {@link StockAvailabilityShareType#NORMAL} or {@link StockAvailabilityShareType#COMPOSITE} and specific feedback, either delivery time or tour must be filled!
	 */
	LocalDateTime deliveryDate;

	/**
	 * Description of the tour e.g. "9 o'clock tour" or "Mittagstour".
	 * For {@link #type} = {@link StockAvailabilityShareType#NORMAL} or {@link StockAvailabilityShareType#COMPOSITE} and specific feedback, either delivery time or tour must be filled!
	 */
	String tour;

	/**
	 * A defined defect reason.
	 * With {@link #type} = {@link StockAvailabilityShareType#NORMAL} or {@link StockAvailabilityShareType#COMPOSITE} the value is always {@link StockAvailabilitySubstitutionReason#NO_INFO},
	 * otherwise with all other types one of the OTHERS! Values ​​required.
	 */
	StockAvailabilitySubstitutionReason reason;

	/**
	 * Only with type {@link StockAvailabilityShareType#NORMAL} and {@link StockAvailabilityShareType#COMPOSITE} is true allowed;
	 * true if due to BTM or chilled goods / transport exclusion can not be delivered on the next tour
	 */
	boolean tourDeviation;
}
