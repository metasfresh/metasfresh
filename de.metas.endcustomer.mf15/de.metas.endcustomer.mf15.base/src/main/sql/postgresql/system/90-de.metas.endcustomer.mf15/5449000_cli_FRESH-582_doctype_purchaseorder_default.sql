-- 09.08.2016 16:33
-- URL zum Konzept
-- maybe this was already applied while the migration script sat in 10-de.metas.adempiere, so we only insert it if it didn't yet exists
INSERT INTO AD_Preference (AD_Preference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID, AD_User_ID, Attribute, Value) 
SELECT 545308,1000000,0, 'Y',now(),100,now(),100, 181,NULL,'C_DocTypeTarget_ID','1000016'
WHERE NOT EXISTS (select 1 from AD_Preference r where r.AD_Preference_ID=545308)
;

