/* Query to identify the AD_Tables which have IsHighVolume=Y

select TableName, AD_Table_ID, AccessLevel
, 'UPDATE AD_Table set IsHighVolume=''N'' where TableName='''||t.TableName||''';' as sql_update
from AD_Table t
where true
and IsHighVolume='Y'
and IsView='N' -- skip views
and TableName not like 'AD_%' -- skip AD tables
and TableName not like 'T_%' -- skip temporary tables
and (
	accessLevel::integer & 2 > 0 -- only those tables which have Client access level
	or accessLevel::integer & 1 > 0 -- only those tables which have Org access level
)
and exists (select 1 from AD_Tab tt where tt.AD_Table_ID=t.AD_Table_ID and tt.SeqNo=10 and tt.IsActive='Y') -- only those tables which are first tab in a window
order by TableName;
*/


UPDATE AD_Table set IsHighVolume='N' where TableName='C_BPartner';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_Country';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_Currency';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_Letter';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_OLCand';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_Queue_WorkPackage';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_ReferenceNo';
UPDATE AD_Table set IsHighVolume='N' where TableName='DPD_Depot';
UPDATE AD_Table set IsHighVolume='N' where TableName='DPD_Route';
UPDATE AD_Table set IsHighVolume='N' where TableName='Fact_Acct';
UPDATE AD_Table set IsHighVolume='N' where TableName='HR_Movement';
UPDATE AD_Table set IsHighVolume='N' where TableName='HR_Process';
UPDATE AD_Table set IsHighVolume='N' where TableName='K_Index';
UPDATE AD_Table set IsHighVolume='N' where TableName='K_IndexLog';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_EDI';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_HU';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_HU_Trx_Hdr';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_Product';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_ShipmentSchedule';
UPDATE AD_Table set IsHighVolume='N' where TableName='M_Tour_Instance';
UPDATE AD_Table set IsHighVolume='N' where TableName='R_IssueKnown';
UPDATE AD_Table set IsHighVolume='N' where TableName='R_IssueProject';
UPDATE AD_Table set IsHighVolume='N' where TableName='R_IssueRecommendation';
UPDATE AD_Table set IsHighVolume='N' where TableName='R_IssueSystem';
UPDATE AD_Table set IsHighVolume='N' where TableName='R_IssueUser';

UPDATE AD_Table set IsHighVolume='N' where TableName='C_Invoice';
UPDATE AD_Table set IsHighVolume='N' where TableName='C_Invoice_Candidate';
