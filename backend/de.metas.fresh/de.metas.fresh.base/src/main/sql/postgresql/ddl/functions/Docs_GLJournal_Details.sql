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