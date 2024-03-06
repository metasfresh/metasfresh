/* check
SELECT seqno, method, pathprefix, iswrapapiresponse, API_Audit_Config_id, *
FROM API_Audit_Config
ORDER BY seqno
;
 */

UPDATE API_Audit_Config
SET iswrapapiresponse='N',
    updated='2022-12-07',
    updatedby=99
WHERE API_Audit_Config_ID IN (
                              540000, -- GET *
                              540001 -- ANY
    )
;