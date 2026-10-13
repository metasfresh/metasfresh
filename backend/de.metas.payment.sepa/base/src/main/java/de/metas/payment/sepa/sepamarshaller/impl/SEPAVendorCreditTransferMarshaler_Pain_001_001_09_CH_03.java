package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.payment.sepa.model.I_SEPA_Export;

import java.io.OutputStream;

/*
 * #%L
 * de.metas.payment.sepa.base
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

/**
 * Placeholder for the pain.001.001.09.ch.03 credit transfer marshaller; the actual XML generation is not implemented yet.
 */
public class SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03 implements SEPAMarshaler
{
	@Override
	public void marshal(final I_SEPA_Export sepaDocument, final OutputStream out)
	{
		throw new UnsupportedOperationException("pain.001.001.09.ch.03 export is not implemented yet");
	}
}
