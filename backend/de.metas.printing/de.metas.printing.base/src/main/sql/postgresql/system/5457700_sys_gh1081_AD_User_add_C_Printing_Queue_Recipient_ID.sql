--
-- minor: update this description
--
-- 06.03.2017 09:16
-- URL zum Konzept
UPDATE AD_Reference SET Description='AD_Users that can actually log on to metasfresh (i.e. AD_Users with IsSystemUser = ''Y'')',Updated=TO_TIMESTAMP('2017-03-06 09:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540401
;

-- 06.03.2017 09:16
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540401
;

--
-- now do the actual stuff
--
-- 06.03.2017 09:16
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556333,542879,0,30,540401,114,'N','C_Printing_Queue_Recipient_ID',TO_TIMESTAMP('2017-03-06 09:16:28','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Druck-Empfänger',0,TO_TIMESTAMP('2017-03-06 09:16:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.03.2017 09:16
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556333 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.03.2017 09:16
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ad_user','ALTER TABLE public.AD_User ADD COLUMN C_Printing_Queue_Recipient_ID NUMERIC(10)')
;

-- 06.03.2017 09:16
-- URL zum Konzept
ALTER TABLE AD_User ADD CONSTRAINT CPrintingQueueRecipient_ADUser FOREIGN KEY (C_Printing_Queue_Recipient_ID) REFERENCES AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 06.03.2017 09:23
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556333,557930,0,496,0,TO_TIMESTAMP('2017-03-06 09:23:28','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsSystemUser@ = ''Y''','de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','N','Druck-Empfänger',280,275,0,1,1,TO_TIMESTAMP('2017-03-06 09:23:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.03.2017 09:23
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557930 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Element SET Description='Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.', Help='Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.',Updated=TO_TIMESTAMP('2017-03-06 09:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542879
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542879
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Printing_Queue_Recipient_ID', Name='Druck-Empfänger', Description='Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.', Help='Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.' WHERE AD_Element_ID=542879
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Printing_Queue_Recipient_ID', Name='Druck-Empfänger', Description='Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.', Help='Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.', AD_Element_ID=542879 WHERE UPPER(ColumnName)='C_PRINTING_QUEUE_RECIPIENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Printing_Queue_Recipient_ID', Name='Druck-Empfänger', Description='Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.', Help='Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.' WHERE AD_Element_ID=542879 AND IsCentrallyMaintained='Y'
;

-- 06.03.2017 09:26
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Empfänger', Description='Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.', Help='Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542879) AND IsCentrallyMaintained='Y'
;

-- 06.03.2017 09:27
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-03-06 09:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557930
;

-- 06.03.2017 10:04
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=305,Updated=TO_TIMESTAMP('2017-03-06 10:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11679
;

-- 06.03.2017 10:04
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=121,Updated=TO_TIMESTAMP('2017-03-06 10:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11679
;

-- 06.03.2017 10:04
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=121, SeqNo=309,Updated=TO_TIMESTAMP('2017-03-06 10:04:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556434
;

-- 06.03.2017 10:05
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=307,Updated=TO_TIMESTAMP('2017-03-06 10:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556434
;

-- 06.03.2017 10:07
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2017-03-06 10:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12640
;

--
-- insert the new field into the AD_User and the C_BPartner window
--
-- 06.03.2017 10:08
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556333,557931,0,118,0,TO_TIMESTAMP('2017-03-06 10:08:31','YYYY-MM-DD HH24:MI:SS'),100,'Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.',0,'@IsSystemUser@ = ''Y''','de.metas.printing','Der eigenentlich druckende Nutzer braucht somit keine Druck-Konfiguration.',0,'Y','Y','Y','Y','N','N','N','N','N','Druck-Empfänger',309,190,0,1,1,TO_TIMESTAMP('2017-03-06 10:08:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.03.2017 10:08
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557931 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

