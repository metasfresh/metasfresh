/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid;

import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/**
 * The online checking service rejected the REQUEST ITSELF — because of how this system is configured — instead
 * of answering about the VAT-ID that was sent.
 *
 * <p><b>Why its own type.</b> {@link VATaxIDCheckRunService} deliberately swallows an ordinary per-target
 * failure so one target cannot abort a run over a whole selection. A configuration fault is the opposite case:
 * every remaining target would hit exactly the same wall, so left indistinguishable from a per-target failure
 * it produces one warn line per target for the entire selection (up to {@code MaxChecksPerRun}) and a run that
 * reports as if it merely had failures. Raising this type is how a checker says "stop the run and name what to
 * fix" rather than "this one target did not work out".
 *
 * <p><b>Never used for a service-side problem.</b> A service that is down, slow or unreachable is a recordable
 * outcome, reported as {@link VATaxIDStatus#ServiceUnavailable} and never thrown — see
 * {@link VATaxIDOnlineChecker}. Only a fault the operator can fix belongs here.
 *
 * <p><b>Why the base half.</b> It sits next to the {@link VATaxIDOnlineChecker} seam whose contract it is part
 * of, and deliberately NOT next to the VIES client that raises it: the {@code vies} half depends on the base
 * half, so a type declared there could not be referenced by {@link VATaxIDCheckRunService} — the one caller
 * that has to act on it.
 */
public class VATaxIDCheckRequestRejectedException extends AdempiereException
{
	/**
	 * The checking service's own error code, e.g. VIES's {@code INVALID_REQUESTER_INFO} — the single most
	 * useful thing to put in front of the operator, because it is what the service's own documentation is
	 * indexed by. Service-neutral in name because the base half knows only the
	 * {@link VATaxIDOnlineChecker} seam, not that VIES happens to be behind it.
	 */
	@Getter @NonNull private final String serviceErrorCode;

	/**
	 * @param serviceErrorCode see {@link #getServiceErrorCode()}. Also recorded as an exception parameter, so
	 *                         it survives into {@code AD_Issue} independently of how {@code message} is worded.
	 * @param message          what the operator has to fix, in their terms. Expected to name
	 *                         {@code serviceErrorCode} itself — callers log the message, not the code.
	 */
	public VATaxIDCheckRequestRejectedException(
			@NonNull final String serviceErrorCode,
			@NonNull final String message)
	{
		super(message);
		this.serviceErrorCode = serviceErrorCode;
		setParameter("serviceErrorCode", serviceErrorCode);
	}
}
