/*
 * #%L
 * de.metas.edi
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

-- Supports the "does this shipment have any transmitted EPCIS SSCC?" lookup that guards
-- reversing/reactivating/voiding a shipment whose SSCCs were already sent to the receiver.
CREATE INDEX IF NOT EXISTS EDI_EPCIS_Transmitted_SSCC_M_InOut_ID
    ON public.EDI_EPCIS_Transmitted_SSCC (M_InOut_ID)
;
