
--DROP VIEW IF EXISTS RV_Printing_Bericht_List_Per_Print_Job; 

create or replace view RV_Printing_Bericht_List_Per_Print_Job as

SELECT
(d.name || '-' :: bpchar ||i.DocumentNo) as documentNo
, u.lastName, u.firstName, bp.companyname
, i.GrandTotal
, pj.C_Print_Job_ID
, (pj.C_Print_Job_ID || '-' :: bpchar || pj.created ||'-' :: bpchar || o.name) as C_Print_Job_Name
, pjl.C_Printing_Queue_ID
, ar.AD_Archive_ID
, i.C_Invoice_ID
, u.AD_User_ID
,replace(i.BPartnerAddress, '\n',', ')::varchar(360) as BPartnerAddress,
--
-- Standard fields
pj.AD_Org_ID,
pj.AD_Client_ID,
pj.Created AS Created,
0 AS CreatedBy, 
pj.Updated AS Updated, 
0 AS UpdatedBy,
'Y'::character(1) AS IsActive
FROM C_Print_Job pj
LEFT OUTER JOIN AD_ORG o ON o.AD_ORG_ID = pj.AD_ORG_ID
LEFT OUTER JOIN C_Print_Job_Line pjl ON pj.C_Print_Job_ID = pjl.C_Print_Job_ID
LEFT OUTER JOIN C_Printing_Queue pq ON pjl.C_Printing_Queue_ID = pq.C_Printing_Queue_ID
LEFT OUTER JOIN AD_Archive ar ON ar.AD_Archive_ID = pq.AD_Archive_ID
LEFT OUTER JOIN C_Invoice i ON i.C_Invoice_ID = ar.Record_ID
LEFT OUTER JOIN C_Doctype d on d.C_Doctype_ID = i.C_Doctype_ID
LEFT OUTER JOIN C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID
LEFT OUTER JOIN AD_User u ON u.AD_User_ID = i.AD_User_ID
LEFT OUTER JOIN C_BPartner_Location bpl ON i.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
LEFT OUTER JOIN C_Location l ON bpl.C_Location_ID = l.C_Location_ID
WHERE ar.AD_Table_ID = (select AD_Table_ID from AD_Table where tablename = 'C_Invoice')
and i.C_Invoice_ID is not null;



