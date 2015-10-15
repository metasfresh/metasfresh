/*
select t.TableName, fa.AD_Table_ID, count(*) cnt from Fact_Acct fa
inner join AD_Table t on (t.AD_Table_ID=fa.AD_Table_ID)
group by t.TableName, fa.AD_Table_ID
*/

-- "C_BankStatement";392
update Fact_Acct fa set DocStatus=(select DocStatus from C_BankStatement d where d.C_BankStatement_ID=fa.Record_ID) where fa.AD_Table_ID=392;
-- "M_Movement";323
update Fact_Acct fa set DocStatus=(select DocStatus from M_Movement d where d.M_Movement_ID=fa.Record_ID) where fa.AD_Table_ID=323;
-- "M_InOut";319
update Fact_Acct fa set DocStatus=(select DocStatus from M_InOut d where d.M_InOut_ID=fa.Record_ID) where fa.AD_Table_ID=319;
-- "C_AllocationHdr";735
update Fact_Acct fa set DocStatus=(select DocStatus from C_AllocationHdr d where d.C_AllocationHdr_ID=fa.Record_ID) where fa.AD_Table_ID=735;
-- "C_Payment";335
update Fact_Acct fa set DocStatus=(select DocStatus from C_Payment d where d.C_Payment_ID=fa.Record_ID) where fa.AD_Table_ID=335;
-- "M_Inventory";321
update Fact_Acct fa set DocStatus=(select DocStatus from M_Inventory d where d.M_Inventory_ID=fa.Record_ID) where fa.AD_Table_ID=321;
-- "C_Invoice";318
update Fact_Acct fa set DocStatus=(select DocStatus from C_Invoice d where d.C_Invoice_ID=fa.Record_ID) where fa.AD_Table_ID=318;
-- "GL_Journal";224
update Fact_Acct fa set DocStatus=(select DocStatus from GL_Journal d where d.GL_Journal_ID=fa.Record_ID) where fa.AD_Table_ID=224;
-- "M_MatchInv";472 => does not have DocStatus
-- update Fact_Acct fa set DocStatus=(select DocStatus from d where d.=fa.Record_ID) where fa.AD_Table_ID=;

