/*
 * #%L
 * de.metas.fresh.base
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_GLJournal_Description(IN p_GL_Journal_ID numeric,
                                                                                      IN p_language      Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_GLJournal_Description(p_GL_Journal_ID numeric,
                                                                                         p_language      character varying)
    RETURNS TABLE
            (
                PrintName   character varying,
                DocumentNo  character varying,
                Description character varying,
                DateDoc     timestamp WITHOUT TIME ZONE,
                TotalDR     numeric,
                TotalCR     numeric,
                CurSymbol   character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       gl.DocumentNo,
       gl.Description,
       gl.DateDoc,
       gl.TotalDR,
       gl.TotalCR,
       c.cursymbol
FROM gl_journal gl

         INNER JOIN C_DocType dt ON gl.C_DocType_ID = dt.C_DocType_ID
         INNER JOIN C_DocType_Trl dtt ON gl.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
         INNER JOIN C_Currency c ON c.c_currency_id = gl.c_currency_id
WHERE gl.GL_Journal_ID = p_GL_Journal_ID

$$
;
