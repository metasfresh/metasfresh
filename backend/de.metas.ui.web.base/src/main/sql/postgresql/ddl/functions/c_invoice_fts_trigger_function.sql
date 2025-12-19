/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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


CREATE OR REPLACE FUNCTION c_invoice_fts_trigger_function()
    RETURNS trigger
AS
$$
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        PERFORM ops.reindex_c_invoice_fts(NEW.c_invoice_id);
    END IF;
    -- The DELETE case is handled automatically by the "ON DELETE CASCADE" constraint.
    -- CONSTRAINT CInvoice_CInvoiceFTS FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
    RETURN NULL;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION c_invoice_fts_trigger_function() IS 'Refresh the C_Invoice_FTS table when a C_Invoice record is inserted or updated'
;
