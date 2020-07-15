
-- 10.11.2016 16:47
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause=' (R_Request.SalesRep_ID=@#AD_User_ID@ OR R_Request.AD_Role_ID=@#AD_Role_ID@) AND (R_Request.R_Status_ID IS NULL OR EXISTS (select 1 from R_Status where R_Status_ID = R_Request.R_Status_ID and IsClosed=''N'')) AND EXISTS (select 1 from R_RequestType where R_RequestType_ID = R_Request.R_RequestType_ID and IsUseForPartnerRequestWindow = ''Y'')',Updated=TO_TIMESTAMP('2016-11-10 16:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=344
;

