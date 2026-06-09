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
                DateDoc     character varying,
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



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_GLJournal_Details (IN p_GL_Journal_ID numeric,
                                                                                   IN p_AD_Language   Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_GLJournal_Details(IN p_GL_Journal_ID numeric,
                                                                                     IN p_AD_Language   Character Varying(6))
    RETURNS TABLE
            (
                Line        numeric(10, 0),
                Description character varying,
                Account_DR  character varying,
                AmtAcctDR   numeric,
                AmtSourceDR numeric,
                DR_Tax      character varying,
                DR_TaxAmt   numeric,
                DR_Product  character varying,
                Account_CR  character varying,
                AmtAcctCR   numeric,
                AmtSourceCR numeric,
                CR_Tax      character varying,
                CR_TaxAmt   numeric,
                CR_Product  character varying,
                CurSymbol   character varying
            )
AS
$$
SELECT gll.Line,
       gll.Description,
       vcd.combination             AS Account_DR,
       amtacctdr,
       amtsourcedr,
       td.name                     AS dr_tax,
       gll.dr_taxamt,
       COALESCE(ptd.name, pd.name) AS dr_product,
       vcc.combination             AS account_cr,
       amtacctcr,
       amtsourcecr,
       tc.name                     AS cr_tax,
       gll.cr_taxamt,
       COALESCE(ptc.name, pc.name) AS cr_product,
       c.cursymbol
FROM gl_journalline AS gll
         LEFT OUTER JOIN c_validcombination vcd ON vcd.c_validcombination_id = gll.account_dr_id
         LEFT OUTER JOIN c_validcombination vcc ON vcc.c_validcombination_id = gll.account_dr_id

    -- taxes
         LEFT OUTER JOIN c_tax td ON td.c_tax_id = gll.dr_tax_id
         LEFT OUTER JOIN c_tax tc ON tc.c_tax_id = gll.cr_tax_id

    -- debit product and product trl
         LEFT OUTER JOIN M_product pd ON pd.m_product_id = gll.dr_m_product_id
         LEFT OUTER JOIN M_Product_Trl ptd ON gll.dr_m_product_id = ptd.M_Product_ID AND ptd.AD_Language = p_AD_Language

    -- credit product and product trl
         LEFT OUTER JOIN M_product pc ON pc.m_product_id = gll.cr_m_product_id
         LEFT OUTER JOIN M_Product_Trl ptc ON gll.cr_m_product_id = ptc.M_Product_ID AND ptc.AD_Language = p_AD_Language
         INNER JOIN C_Currency c ON c.c_currency_id = gll.c_currency_id
WHERE gll.GL_Journal_ID = p_GL_Journal_ID
ORDER BY gll.line

$$
    LANGUAGE sql
    STABLE
;

