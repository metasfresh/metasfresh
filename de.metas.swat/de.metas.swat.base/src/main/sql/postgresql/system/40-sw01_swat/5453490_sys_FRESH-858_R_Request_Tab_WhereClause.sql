-- 16.11.2016 13:16
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='   (exists 	( 		select 1 from ad_user u where ad_user_ID = R_Request.salesrep_ID and 		exists 			( 				select 1 from AD_User_Roles ur where ur.ad_user_ID = u.ad_user_ID and  				exists 					( 						select 1 from AD_User_Roles ur2 where ur.ad_role_id = ur2.ad_role_id and ad_user_ID = @#AD_User_ID@ and isactive = ''Y'' 					) 			) 	)) 	AND (R_Request.R_Status_ID IS NULL OR EXISTS (select 1 from R_Status where R_Status_ID = R_Request.R_Status_ID and IsClosed=''N'')) AND EXISTS (select 1 from R_RequestType where R_RequestType_ID = R_Request.R_RequestType_ID and IsUseForPartnerRequestWindow = ''Y'')   ',Updated=TO_TIMESTAMP('2016-11-16 13:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=344
;

