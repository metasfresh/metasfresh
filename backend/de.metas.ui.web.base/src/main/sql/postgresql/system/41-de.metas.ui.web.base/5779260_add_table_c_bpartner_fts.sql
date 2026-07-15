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

CREATE TABLE public.C_BPartner_FTS
(
    C_BPartner_ID NUMERIC(10)              NOT NULL,
    FTS_document  tsvector                 NOT NULL,
    FTS_string    TEXT                     NOT NULL,
    Updated       TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT C_BPartner_FTS_Key PRIMARY KEY (C_BPartner_ID),
    CONSTRAINT CBPartner_CBPartnerFTS FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED
)
;

CREATE INDEX IF NOT EXISTS c_bpartner_fts_document_idx
    ON c_bpartner_fts
        USING GIN (fts_document)
;

CREATE EXTENSION IF NOT EXISTS pg_trgm
;

CREATE INDEX IF NOT EXISTS c_bpartner_fts_string_trgm_gist_idx
    ON c_bpartner_fts
        USING GIST (fts_string gist_trgm_ops)
;
