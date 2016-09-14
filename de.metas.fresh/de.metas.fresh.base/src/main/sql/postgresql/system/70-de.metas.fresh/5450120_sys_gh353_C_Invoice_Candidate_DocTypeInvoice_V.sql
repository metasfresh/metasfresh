
DROP VIEW IF EXISTS "de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V;
CREATE VIEW "de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V AS
SELECT *
FROM (
-- this inner SELECT concludes from the case number what shall be done
SELECT 
    *,
    CASE ic_C_DocTypeInvoice_ID_case
        WHEN '1' THEN dt_prod_C_DocType_ID
        WHEN '2' THEN ic_C_DocTypeInvoice_ID_current
        WHEN '3' THEN ic_C_DocTypeInvoice_ID_current
        WHEN '4' THEN NULL
    END AS ic_C_DocTypeInvoice_ID_should
FROM (
-- this innermost SELECT does almost all of the work
SELECT 
    ic.C_Invoice_Candidate_ID,
    ic.Processed,
    CASE WHEN ic_rc.C_Invoice_Candidate_ID IS NULL THEN 'N' ELSE 'Y' END AS recompute,
    CASE WHEN ic_l.Record_ID IS NULL THEN 'N' ELSE 'Y' END AS locked,
    bp.Value, bp.Name,
    bp.Fresh_Produzentenabrechnung,
    ic.C_DocTypeInvoice_ID AS ic_C_DocTypeInvoice_ID_current,
    dt_ic.Name AS dt_ic_Name,
    dt_prod.C_DocType_ID AS dt_prod_C_DocType_ID,
    dt_prod.Name as dt_prod_Name,
    -- instead of ic_C_DocTypeInvoice_ID_case we could directly select the ic_C_DocTypeInvoice_ID_should, 
    -- but explicitly outputing the "case" number makes it easier to unserstand why a certain ic_C_DocTypeInvoice_ID_should is presented
    CASE 
        WHEN bp.Fresh_Produzentenabrechnung='Y' and ic.C_DocTypeInvoice_ID IS NULL THEN '1' -- -> needs update to prod-doctype
        WHEN bp.Fresh_Produzentenabrechnung='Y' and ic.C_DocTypeInvoice_ID=dt_prod.C_DocType_ID THEN '2' -- -> nothing to do
        WHEN dt_ic.DocBaseType='API' and dt_ic.DocSubType IN ('DP', 'QI') THEN '3' -- -> an even more specific (material tracking) doctype, just leave it as it is
        WHEN bp.Fresh_Produzentenabrechnung='N' and dt_ic.C_Doctype_ID=dt_prod.C_DocType_ID THEN '4' -- -> not prod anymore, needs update to null
    END AS ic_C_DocTypeInvoice_ID_case
FROM C_Invoice_Candidate ic
    JOIN C_BPartner bp ON bp.C_BPArtner_ID=ic.Bill_BPartner_ID
    LEFT JOIN C_DocType dt_prod ON dt_prod.AD_Org_ID IN (ic.AD_Org_ID, 0) AND dt_prod.DocBaseType='API' AND dt_prod.DocSubType='VI'
    LEFT JOIN C_DocType dt_ic ON dt_ic.C_Doctype_ID=ic.C_DocTypeInvoice_ID
    LEFT JOIN C_Invoice_Candidate_Recompute ic_rc ON ic_rc.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID
    LEFT JOIN T_Lock ic_l ON ic_l.Record_ID=ic.C_Invoice_Candidate_ID AND ic_l.AD_Table_ID=get_table_id('C_Invoice_Candidate')
WHERE true
    AND ic.isSOTrx='N'
) innermost_sql
) inner_sql;
COMMENT ON VIEW "de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V IS '
Goals of this view:

1. allow us to diagnose which C_DocTypInvoice_ID a C_Invoice_Candidate has and why. For that we have ic_C_DocTypeInvoice_ID_case:
   1 => the IC needs update to prod-doctype
   2 => the IC''s doctype is as it should be, nothing to do
   3 => the IC has an even more specific (material tracking) doctype, just leave it as it is
   4 => the IC''s doctype is the producer-doctype, but needs to be unset

2. allow us to update unprocessed ICs after a C_BPartner''s Fresh_Produzentenabrechnung value was changed:

Note: 
  INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
  SELECT C_Invoice_Candidate_ID FROM "de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V 
  AND COALESCE(v.ic_C_DocTypeInvoice_ID_current,0)!=COALESCE(v.ic_C_DocTypeInvoice_ID_should,0) AND v.Processed=''N'' AND v.Recompute=''N'' AND AND v.Locked=''N'';
alone might not work, because currently, the conde to update the doctype is triggered from a model interceptor.
If the IC updated does not find any "other" change to make, then that MI won''t be fired.

So, instead use something like
  UPDATE C_Invoice_Candidate ic
  SET C_DocTypeInvoice_ID=v.ic_C_DocTypeInvoice_ID_should, Updated=now(), UpdatedBy=99
  FROM "de.metas.fresh".C_Invoice_Candidate_DocTypeInvoice_V v
  WHERE ic.C_Invoice_Candidate_ID=v.C_Invoice_Candidate_ID
    AND COALESCE(v.ic_C_DocTypeInvoice_ID_current,0)!=COALESCE(v.ic_C_DocTypeInvoice_ID_should,0) AND v.Processed=''N'' AND v.Recompute=''N'' AND v.Locked=''N'';
Still, it migmt be a good idea to also *prepend* the " INSERT INTO C_Invoice_Candidate_Recompute...", to make sure they are reevaluated after this change

IMPORTANT: keep in sync with IFreshInvoiceCandBL.updateC_DocTypeInvoice(I_C_Invoice_Candidate)

issue-URL: https://github.com/metasfresh/metasfresh/issues/353';
