
CREATE OR REPLACE VIEW RV_Prt_Bericht_Statistik_List_Per_Org AS

SELECT list.DocumentNo, list.LastName, list.FirstName, list.companyname, list.BPartnerAddress, list.Grandtotal,
list.C_Print_Job_ID, list.C_Print_Job_Name, pp.C_Print_Package_ID, list.C_Invoice_ID, i.DateInvoiced, pi.C_Print_Job_Instructions_ID,

-- Standard fields
list.AD_Org_ID,
list.AD_Client_ID,
list.Created AS Created,
0 AS CreatedBy, 
list.Updated AS Updated, 
0 AS UpdatedBy,
'Y'::character(1) AS IsActive

from RV_Bericht_List_Per_Print_Job list
LEFT OUTER JOIN C_Print_Job_Instructions pi
ON pi.C_Print_Job_ID = list.C_Print_Job_ID
LEFT OUTER JOIN C_Print_Package pp
ON pi.C_Print_Job_Instructions_ID = pp.C_Print_Job_Instructions_ID
LEFT OUTER JOIN C_Invoice i
ON i.C_Invoice_ID = list.C_Invoice_ID;


