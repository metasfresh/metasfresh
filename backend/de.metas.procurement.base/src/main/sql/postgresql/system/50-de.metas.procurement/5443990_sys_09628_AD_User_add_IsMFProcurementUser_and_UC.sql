
-- 31.03.2016 13:21
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543059,0,'IsMFProcurementUser',TO_TIMESTAMP('2016-03-31 13:21:55','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und ein Leifervertrag hinterlegt ist, bei der Liefermeldungs-WebUI anmelden kann','de.metas.procurement','Y','Liefermeldung WebUI','Liefermeldung WebUI',TO_TIMESTAMP('2016-03-31 13:21:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.03.2016 13:21
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543059 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.03.2016 13:22
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554318,543059,0,20,114,'N','IsMFProcurementUser',TO_TIMESTAMP('2016-03-31 13:22:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und ein Leifervertrag hinterlegt ist, bei der Liefermeldungs-WebUI anmelden kann','de.metas.procurement',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Liefermeldung WebUI',0,TO_TIMESTAMP('2016-03-31 13:22:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 31.03.2016 13:22
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554318 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 31.03.2016 13:27
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540374,0,114,TO_TIMESTAMP('2016-03-31 13:27:22','YYYY-MM-DD HH24:MI:SS'),100,'The email address needs to be unique among users that are cleared for the procurement webUI.','de.metas.procurement','Bei Nutzern, die für die Mengenmeldung-WebUI freigegeben sind, muss die Mailadresse eindeutig sein.','Y','Y','AD_User_Procurement_Mailaddress','N',TO_TIMESTAMP('2016-03-31 13:27:22','YYYY-MM-DD HH24:MI:SS'),100,'IsMFProcurementUser=''Y'' and IsActive=''Y''')
;

-- 31.03.2016 13:27
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540374 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 31.03.2016 13:27
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5396,540746,540374,0,TO_TIMESTAMP('2016-03-31 13:27:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',10,TO_TIMESTAMP('2016-03-31 13:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Element SET Description='Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann', Name='Mengenmeldung-WebUI', PrintName='Mengenmeldung-WebUI',Updated=TO_TIMESTAMP('2016-03-31 13:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543059
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543059
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsMFProcurementUser', Name='Mengenmeldung-WebUI', Description='Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann', Help=NULL WHERE AD_Element_ID=543059
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsMFProcurementUser', Name='Mengenmeldung-WebUI', Description='Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann', Help=NULL, AD_Element_ID=543059 WHERE UPPER(ColumnName)='ISMFPROCUREMENTUSER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsMFProcurementUser', Name='Mengenmeldung-WebUI', Description='Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann', Help=NULL WHERE AD_Element_ID=543059 AND IsCentrallyMaintained='Y'
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_Field SET Name='Mengenmeldung-WebUI', Description='Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543059) AND IsCentrallyMaintained='Y'
;

-- 31.03.2016 13:28
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Mengenmeldung-WebUI', Name='Mengenmeldung-WebUI' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543059)
;




-- 31.03.2016 14:16
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554318,556856,0,496,0,TO_TIMESTAMP('2016-03-31 14:16:34','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann',0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','N','Mengenmeldung-WebUI',145,135,0,1,1,TO_TIMESTAMP('2016-03-31 14:16:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.03.2016 14:16
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556856 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



COMMIT;


-- 31.03.2016 13:22
-- URL zum Konzept
ALTER TABLE AD_User ADD IsMFProcurementUser CHAR(1) DEFAULT 'N' CHECK (IsMFProcurementUser IN ('Y','N')) NOT NULL
;



-- 31.03.2016 13:27
-- URL zum Konzept
CREATE UNIQUE INDEX AD_User_Procurement_Mailaddress ON AD_User (EMail) WHERE IsMFProcurementUser='Y' and IsActive='Y'
;
