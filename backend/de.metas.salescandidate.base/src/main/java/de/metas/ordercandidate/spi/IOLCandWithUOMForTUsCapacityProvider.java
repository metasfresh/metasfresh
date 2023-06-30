package de.metas.ordercandidate.spi;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.impl.DefaultOLCandValidator;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * de.metas.salescandidate.base
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

/**
 * Note to dev: this interface's implementor is injected into {@link DefaultOLCandValidator}. The implementation is part of {@code de.metas.util}.
 * We need this because for UOMs such as "TO" and "COLI", we need to turn to {@code de.metas.util} in order to get the PIIP's capacity information.
 *
 * Disclaimer: I don't think this approach is great (just look at the amount of javadoc!); but I think this way it's at clear(er) what's going on and why.
 */
public interface IOLCandWithUOMForTUsCapacityProvider
{
	/**
	 * @param olCand shall not be changed by this method.
	 * @return {@code true} iff the given {@code olCand} (because of it's UOM) requires this provider to compute the CU-per-TU capacity.
	 */
	boolean isProviderNeededForOLCand(@NonNull I_C_OLCand olCand);

	/**
	 * Assume that {@link #isProviderNeededForOLCand(I_C_OLCand)} was called and returned {@code true} before.
	 *
	 * @param olCand shall not be changed by this method.
	 * @return the number of CUs that fit into the {@code olCand}'s TU, in the respective product's stock-UOM. Might also be {@link Quantity#isInfinite()}. Throw a user-friendly exception if the capacity can't be found.
	 */
	@NonNull
	Quantity computeQtyItemCapacity(@NonNull I_C_OLCand olCand);
}
