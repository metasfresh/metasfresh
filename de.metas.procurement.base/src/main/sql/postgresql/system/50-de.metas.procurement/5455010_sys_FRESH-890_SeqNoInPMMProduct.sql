-- 03.01.2017 15:47
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556027,566,0,10,540751,'N','SeqNo',TO_TIMESTAMP('2017-01-03 15:47:19','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM PMM_Product WHERE M_Product_ID=@M_Product_ID@ AND M_HU_PI_Item_Product_ID = @M_HU_PI_Item_Product_ID@ AND (C_BPartner_ID = @C_BPartner_ID@ OR C_BPartner_ID IS NULL)','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.procurement',10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Reihenfolge',0,TO_TIMESTAMP('2017-01-03 15:47:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 03.01.2017 15:47
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556027 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.01.2017 15:48
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556027,557492,0,540731,0,TO_TIMESTAMP('2017-01-03 15:48:21','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'de.metas.procurement','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','Y','N','N','N','N','N','Reihenfolge',130,100,0,1,1,TO_TIMESTAMP('2017-01-03 15:48:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.01.2017 15:48
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557492 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 03.01.2017 16:06
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2017-01-03 16:06:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;

-- 03.01.2017 16:07
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM PMM_Product WHERE M_Product_ID=@M_Product_ID@ AND M_HU_PI_Item_Product_ID = @M_HU_PI_Item_Product_ID@ AND (C_BPartner_ID = @C_BPartner_ID@ OR C_BPartner_ID IS NULL)
',Updated=TO_TIMESTAMP('2017-01-03 16:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;

-- 03.01.2017 16:24
-- URL zum Konzept
UPDATE AD_Index_Column SET AD_Column_ID=556027,Updated=TO_TIMESTAMP('2017-01-03 16:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540751
;

-- 03.01.2017 16:24
-- URL zum Konzept
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(SeqNo,0)',Updated=TO_TIMESTAMP('2017-01-03 16:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540751
;

-- 03.01.2017 16:25
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM PMM_Product WHERE M_Product_ID=@M_Product_ID@ AND M_HU_PI_Item_Product_ID = @M_HU_PI_Item_Product_ID@ AND C_BPartner_ID = @C_BPartner_ID/-1@ 
',Updated=TO_TIMESTAMP('2017-01-03 16:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;

-- 03.01.2017 16:28
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2017-01-03 16:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;

-- 03.01.2017 16:29
-- URL zum Konzept
ALTER TABLE public.PMM_Product ADD SeqNo NUMERIC(10) DEFAULT NULL 
;

DROP INDEX public.pmm_product_uc;-- 03.01.2017 16:45




UPDATE PMM_Product SET SeqNo = 10 where M_AttributeSetInstance_ID IS  NULL;

UPDATE PMM_Product SET SeqNo = 20 where M_AttributeSetInstance_ID IS NOT NULL;


--- !!!!!!!!!!!!!!!!!! IF THERE ARE MORE THAN 2 PMM_PRODUCT ENTRIES FOR THE SAME PRODUCT, M_HU_PI_ITEM_PRODUCT AND PARTNER, PLEASE UPDATE THE SEQNO MANUALLY HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- IF THE UPDATE ABOVE IS NOT DONE, THE INDEX CREATION WILL FAIL




-- URL zum Konzept
CREATE UNIQUE INDEX PMM_Product_UC ON PMM_Product (COALESCE(C_BPartner_ID,0),M_Product_ID,COALESCE(M_HU_PI_Item_Product_ID,0),COALESCE(SeqNo,0)) WHERE IsActive='Y'
;

-- 03.01.2017 16:52
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@SQL=select coalesce(MAX(SeqNo),0)+10 AS DefaultValue from PMM_Product where M_Product_ID=@M_Product_ID@ and M_HU_PI_Item_Product_ID = @M_HU_PI_Item_Product_ID@ and (C_BPartner_ID = @C_BPartner_ID@  or C_BPartner_ID is null)
',Updated=TO_TIMESTAMP('2017-01-03 16:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;

-- 03.01.2017 17:01
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2017-01-03 17:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556027
;
commit;
-- 03.01.2017 17:01
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_product','SeqNo','NUMERIC(10)',null,'NULL')
;
commit;
