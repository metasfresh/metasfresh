DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_description (IN p_Record_ID numeric,
                                                                      IN p_Language  Character Varying(6))
;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_description(p_Record_ID numeric,
                                                             p_Language  character varying)
    RETURNS TABLE
            (
                doctype     character varying,
                docno       character varying,
                noteheader  character varying,
                note        character varying,
                dunningdate timestamp without time zone,
                bp_value    character varying,
                org_value   character varying,
                taxid       character varying,
                condition   character varying,
                ExternalID  character varying,
                poreference character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(dlt.PrintName, dl.PrintName)         AS DocType,
       dd.documentno                                 AS docno,
       COALESCE(dlt.noteheader, dl.noteheader),
       COALESCE(dlt.note, dl.note),
       dd.dunningdate,
       bp.value                                      AS bp_value,
       o.value                                       AS org_value,
       inf.taxid,
       REPLACE(
               REPLACE(
                       REPLACE(
                               COALESCE(pt.name_invoice, pt.name),
                               '$datum_netto',
                               TO_CHAR(dd.dunningdate + p.netdays, 'DD.MM.YYYY')
                       ),
                       '$datum_skonto_1',
                       TO_CHAR(dd.dunningdate::date + p.discountdays, 'DD.MM.YYYY')
               ),
               '$datum_skonto_2',
               TO_CHAR(dd.dunningdate::date + p.discountdays2, 'DD.MM.YYYY')
       )                                             AS condition,
       report.getPartnerExternalID(bp.C_BPartner_ID) AS ExternalID,
       i.poreference                                 AS poreference

FROM C_DunningDoc dd
         JOIN C_DunningLevel dl ON dd.C_Dunninglevel_ID = dl.C_DunningLevel_ID
         LEFT JOIN C_DunningLevel_Trl dlt
                   ON dd.C_Dunninglevel_ID = dlt.C_DunningLevel_ID AND dlt.ad_Language = p_Language
         LEFT JOIN C_BPartner bp ON dd.C_BPartner_ID = bp.C_BPartner_ID
         LEFT JOIN AD_Org o ON dd.AD_Org_ID = o.AD_Org_ID
         LEFT JOIN AD_OrgInfo inf ON o.AD_Org_ID = inf.AD_Org_ID
         LEFT JOIN C_PaymentTerm p ON dl.C_PaymentTerm_ID = p.C_PaymentTerm_ID
         LEFT JOIN C_PaymentTerm_Trl pt
                   ON dl.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.ad_Language = p_Language
         LEFT JOIN C_DunningDoc_line dll ON dd.C_DunningDoc_ID = dll.C_DunningDoc_ID
         LEFT JOIN C_DunningDoc_Line_Source dls ON dll.C_DunningDoc_Line_ID = dls.C_DunningDoc_Line_ID
         LEFT JOIN C_Dunning_Candidate dc ON dls.C_Dunning_Candidate_ID = dc.C_Dunning_Candidate_ID
         LEFT JOIN C_Invoice_v i ON (dc.Record_ID = i.C_Invoice_ID AND
                                     dc.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice'))
WHERE dd.C_DunningDoc_ID = p_Record_ID
    ;
$$
;


