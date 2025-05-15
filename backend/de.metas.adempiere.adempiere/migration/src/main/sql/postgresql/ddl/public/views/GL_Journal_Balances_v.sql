DROP VIEW IF EXISTS GL_Journal_Balances_v
;

CREATE OR REPLACE VIEW GL_Journal_Balances_v AS
SELECT
    /*This view is only for a tab in the GL_Journal window where the gl_journal_id is unique. Summing it up with the account ID will ensure uniqueness on window level.
      If the view's purpose must be extended, another primary key formula must be applied!
     */
    t.gl_journal_id + t.account_id AS gl_journal_balances_v_id,
    t.gl_journal_id,
    t.account_id,
    t.accountconceptualname,
    SUM(amtacctdr)                 AS amtacctdr,
    SUM(amtacctcr)                 AS amtacctcr,
    --
    j.ad_client_id,
    j.ad_org_id,
    j.created,
    j.createdby,
    j.updated,
    j.updatedby,
    j.isactive
FROM (
         --
         SELECT jl.gl_journal_id,
                ev.c_elementvalue_id        AS account_id,
                jl.dr_accountconceptualname AS accountconceptualname,
                SUM(jl.amtacctdr)           AS amtacctdr,
                0::numeric                  AS amtacctcr
         FROM gl_journalline jl
                  INNER JOIN c_validcombination vc ON vc.c_validcombination_id = jl.account_dr_id
                  INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = vc.account_id
         WHERE jl.isactive = 'Y'
         GROUP BY jl.gl_journal_id,
                  ev.c_elementvalue_id,
                  jl.dr_accountconceptualname

         --
         UNION ALL
         --
         SELECT jl.gl_journal_id,
                ev.c_elementvalue_id        AS account_id,
                jl.cr_accountconceptualname AS accountconceptualname,
                0::numeric                  AS amtacctdr,
                SUM(jl.amtacctcr)           AS amtacctdr
         FROM gl_journalline jl
                  INNER JOIN c_validcombination vc ON vc.c_validcombination_id = jl.account_cr_id
                  INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = vc.account_id
         WHERE jl.isactive = 'Y'
         GROUP BY jl.gl_journal_id,
                  ev.c_elementvalue_id,
                  jl.cr_accountconceptualname

         --
     ) t
         INNER JOIN gl_journal j ON j.gl_journal_id = t.gl_journal_id
GROUP BY t.gl_journal_id,
         t.account_id,
         t.accountconceptualname,
         --
         j.ad_client_id, j.ad_org_id, j.created, j.createdby, j.updated, j.updatedby, j.isactive
;




/*
-- Test
SELECT --
       b.gl_journal_balances_v_id,
       b.gl_journal_id,
       b.accountconceptualname,
       ev.value    AS account,
       b.amtacctdr AS amtacctdr,
       b.amtacctcr AS amtacctcr
FROM GL_Journal_Balances_v b
         LEFT OUTER JOIN c_elementvalue ev ON ev.c_elementvalue_id = b.account_id
WHERE b.gl_journal_id = 1000000
ORDER BY b.gl_journal_id, ev.value
;
*/
