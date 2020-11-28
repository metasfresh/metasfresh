
--need to deal with/migrate
--/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_Pack','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN C_UOM_ID NUMERIC(10)')
UPDATE EDI_DesadvLine_Pack p 
SET Updated='2020-01-10 08:01:14.325932+01', UpdatedBy=99, C_UOM_ID=(select C_UOM_ID from EDI_DesadvLine l where l.EDI_DesadvLine_ID=p.EDI_DesadvLine_ID)
WHERE p.C_UOM_ID IS NULL;
-- has to be done later, to avoid "cannot ALTER TABLE "edi_desadvline_pack" because it has pending trigger events"
-- INSERT INTO t_alter_column values('edi_desadvline_pack','C_UOM_ID',null,'NOT NULL',null); --DONE

--/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_Pack','ALTER TABLE public.EDI_DesadvLine_Pack ADD COLUMN M_InOutLine_ID NUMERIC(10)')
UPDATE EDI_DesadvLine_Pack p 
SET Updated='2020-01-10 08:01:14.325932+01', UpdatedBy=99, M_InOutLine_ID=data.M_InOutLine_ID
FROM (
    select iol.M_InOutLine_ID, iol.EDI_DesadvLine_ID from M_InOutLine iol where iol.EDI_DesadvLine_ID is not null
) data
WHERE p.M_InOutLine_ID IS NULL AND p.EDI_DesadvLine_ID=data.EDI_DesadvLine_ID;

DROP VIEW IF EXISTS EDI_DesadvLine_Pack_v;
CREATE TEMPORARY VIEW EDI_DesadvLine_Pack_v AS
select DISTINCT 
    e.EDI_Desadv_ID AS e_EDI_Desadv_ID, 
    e.POReference AS e_POReference, 
    e.EDI_ExportStatus AS e_EDI_ExportStatus, 
    e.IsActive AS e_IsActive, 
    el.EDI_DesadvLine_ID AS el_EDI_DesadvLine_ID, 
    el.Line AS el_Line, 
    el.Created AS el_Created,
--    el.Updated AS el_Updated,
    elp.EDI_DesadvLine_Pack_ID,
    elp.ipa_sscc18 AS elp_ipa_sscc18,
    elp.M_InOutLine_ID AS elp_M_InOutLine_ID,
    elp.M_InOut_ID AS elp_M_InOut_ID,
    io.M_InOut_ID AS io_M_InOut_ID, 
    io.POReference AS io_POReference, 
    io.DocStatus AS io_DocStatus, 
    iol_via_fk.M_InOutLine_ID AS iol_via_fk_M_InOutLine_ID
     --,e.*
from EDI_DesadvLine_Pack elp 
    join EDI_DesadvLine el ON el.EDI_DesadvLine_ID=elp.EDI_DesadvLine_ID
        left join M_InOutLine iol_via_fk ON iol_via_fk.EDI_DesadvLine_ID=el.EDI_DesadvLine_ID
    join EDI_Desadv e ON e.EDI_Desadv_ID=elp.EDI_Desadv_ID
        left join M_InOut io ON io.POReference=e.POReference 
            left join M_InOutLine iol_via_ref ON iol_via_ref.M_InOut_ID=io.M_InOut_ID AND iol_via_ref.Line=el.Line
--where elp.M_InOutLine_ID IS NULL
;

/* this delete would not remove all records with missing iol-references
DELETE FROM EDI_DesadvLine_Pack p
where p.EDI_DesadvLine_Pack_ID IN 
(
    select EDI_DesadvLine_Pack_ID from EDI_DesadvLine_Pack_v v where v.e_EDI_ExportStatus IN ('E', 'N') AND v.elp_M_InOutLine_ID IS NULL
);
*/
--Not making it mandatory now, because
-- * we have legacy systems with EDI-Desadv-Lines that are not referenced by any inout lines (200s out of 100000s but still).
-- * technically we don't need it to be mandtory right now.
-- * in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inoutLine 



--/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_Pack','ALTER TABLE public.EDI_DesadvLine_Pack ADD COLUMN M_InOut_ID NUMERIC(10)')
UPDATE EDI_DesadvLine_Pack p 
SET Updated='2020-01-10 08:01:14.325932+01', UpdatedBy=99, M_InOut_ID=v.io_M_InOut_ID
FROM EDI_DesadvLine_Pack_v v WHERE v.io_M_InOut_ID IS NOT NULL AND v.EDI_DesadvLine_Pack_ID=p.EDI_DesadvLine_Pack_ID and p.M_InOut_ID IS NULL;
--Not making it mandatory now, because
-- * we have legacy systems with EDI-Desadv-Lines that are not referenced by any inout lines (10s out of 100000s but still).
-- * technically we don't need it to be mandtory right now.
-- * in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inout.


UPDATE edi_desadvline_pack SET MovementQty=0 WHERE MovementQty Is NULL;

-- 2020-01-10T08:33:56.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-01-10 09:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569695
;

-- 2020-01-10T07:15:43.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-01-10 08:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569696
;

-- 2020-01-10T08:08:50.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='We have this column (also indexed) to be able to efficiently remove packs if an iol is deleted or voided. Non-mandatory because:
* we have legacy systems with EDI-Desadv-Lines that are not referned by and inout lines (200s out of 100000s but still).
* technically we don''t need it to be mandtory right now.
* in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inoutLine ',Updated=TO_TIMESTAMP('2020-01-10 09:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569696
;

-- 2020-01-10T08:10:08.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='We have this column (also indexed) to be able to efficiently remove packs if an iol is deleted or voided. Non-mandatory because:
* we have legacy systems with EDI-Desadv-Lines that are not referenced by any inout lines (200s out of 100000s but still).
* technically we don''t need it to be mandtory right now.
* in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inoutLine ',Updated=TO_TIMESTAMP('2020-01-10 09:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569696
;

-- 2020-01-10T08:25:41.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='We have this column (also indexed) to be able to efficiently remove packs if an iol is deleted or voided. Non-mandatory because:
* we have legacy systems with EDI-Desadv-Lines that are not referenced by any inout lines (200s out of 100000s but still).
* technically we don''t need it to be mandtory right now.
* in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inoutLine.',Updated=TO_TIMESTAMP('2020-01-10 09:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569696
;

-- 2020-01-10T08:27:09.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='we have this column (also indexed) for UI filtering. Non-mandatory because:
* we have legacy systems with EDI-Desadv-Lines that are not referenced by any inout lines (10s out of 100000s but still).
* technically we don''t need it to be mandtory right now.
* in future we might allow packs (EDI-Infos to be send) to be created Ad-Hoc, without an inout.',Updated=TO_TIMESTAMP('2020-01-10 09:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569697
;
