-- 12.10.2015 14:13:31
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542907,0,'IsDisableOrderCheckup',TO_TIMESTAMP('2015-10-12 14:13:30','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden','de.metas.fresh','Y','keine Bestellkontrolle','keine Bestellkontrolle',TO_TIMESTAMP('2015-10-12 14:13:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.10.2015 14:13:31
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542907 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

/*
-- 12.10.2015 14:13:49
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552777,542907,0,20,293,'N','IsDisableOrderCheckup',TO_TIMESTAMP('2015-10-12 14:13:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden','de.metas.fresh',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','keine Bestellkontrolle',0,TO_TIMESTAMP('2015-10-12 14:13:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.10.2015 14:13:49
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552777 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.10.2015 14:13:53
-- URL zum Konzept
ALTER TABLE C_BPartner_Location ADD IsDisableOrderCheckup CHAR(1) DEFAULT 'N' CHECK (IsDisableOrderCheckup IN ('Y','N')) NOT NULL
;

-- 12.10.2015 14:17:03
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552777
;

-- 12.10.2015 14:17:03
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=552777
;

ALTER TABLE C_BPartner_Location DROP COLUMN IsDisableOrderCheckup;
*/

-- 12.10.2015 14:18:16
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552778,542907,0,20,291,'N','IsDisableOrderCheckup',TO_TIMESTAMP('2015-10-12 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden','de.metas.fresh',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','keine Bestellkontrolle',0,TO_TIMESTAMP('2015-10-12 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.10.2015 14:18:16
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552778 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.10.2015 14:18:50
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552778,556360,0,223,0,TO_TIMESTAMP('2015-10-12 14:18:50','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden',0,'de.metas.fresh',0,'Y','Y','Y','Y','N','N','N','N','Y','keine Bestellkontrolle',185,175,0,TO_TIMESTAMP('2015-10-12 14:18:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.10.2015 14:18:50
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556360 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 12.10.2015 14:19:22
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsCustomer@=''Y''',Updated=TO_TIMESTAMP('2015-10-12 14:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556360
;

COMMIT;

-- 12.10.2015 14:18:19
-- URL zum Konzept
ALTER TABLE C_BPartner ADD IsDisableOrderCheckup CHAR(1) DEFAULT 'N' CHECK (IsDisableOrderCheckup IN ('Y','N')) NOT NULL
;
