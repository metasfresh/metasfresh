-- 2018-04-10T07:24:48.800
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543950,0,TO_TIMESTAMP('2018-04-10 07:24:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Lieferschein Verarbeitet','Lieferschein Verarbeitet',TO_TIMESTAMP('2018-04-10 07:24:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-10T07:24:48.813
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543950 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-04-10T07:25:15.614
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=543950, Description=NULL, Help=NULL, Name='Lieferschein Verarbeitet',Updated=TO_TIMESTAMP('2018-04-10 07:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554657
;

-- 2018-04-10T07:25:15.663
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543950) 
;

-- 2018-04-10T07:25:33.184
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-10 07:25:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment processed',PrintName='Shipment processed' WHERE AD_Element_ID=543950 AND AD_Language='en_US'
;

-- 2018-04-10T07:25:33.187
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543950,'en_US') 
;

-- 2018-04-10T07:25:49.554
-- URL zum Konzept
UPDATE AD_Element SET Name='Lieferschein verarbeitet', PrintName='Lieferschein verarbeitet ',Updated=TO_TIMESTAMP('2018-04-10 07:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543950
;

-- 2018-04-10T07:25:49.556
-- URL zum Konzept
UPDATE AD_Field SET Name='Lieferschein verarbeitet', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543950) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543950)
;

-- 2018-04-10T07:25:49.576
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferschein verarbeitet ', Name='Lieferschein verarbeitet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543950)
;

