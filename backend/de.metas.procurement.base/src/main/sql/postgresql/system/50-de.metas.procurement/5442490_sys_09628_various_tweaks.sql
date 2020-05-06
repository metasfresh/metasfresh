
-- 11.03.2016 16:54
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Lager für Bestellung',Updated=TO_TIMESTAMP('2016-03-11 16:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556769
;

-- 11.03.2016 16:54
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556769
;

-- 11.03.2016 17:19
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-03-11 17:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540751
;



-- 13.03.2016 20:55
-- URL zum Konzept
UPDATE AD_JAXRS_Endpoint SET EntityType='de.metas.procurement',Updated=TO_TIMESTAMP('2016-03-13 20:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JAXRS_Endpoint_ID=540009
;



-- 15.03.2016 08:56
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554159
;

-- 15.03.2016 08:56
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554146
;


-- 15.03.2016 08:56
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554140
;

-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2016-03-15 08:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554134
;


-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE PMM_QtyReport_Event SET IsActive='Y' WHERE IsActive IS NULL
;

-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554147
;


-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554149
;


-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554148
;


-- 15.03.2016 08:57
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554139
;

-- 15.03.2016 08:58
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554150
;

-- 15.03.2016 08:58
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 08:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554141
;


-- 15.03.2016 09:02
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554227,543037,0,30,540747,'N','PMM_Product_ID',TO_TIMESTAMP('2016-03-15 09:02:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferprodukt',0,TO_TIMESTAMP('2016-03-15 09:02:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 15.03.2016 09:02
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554227 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 15.03.2016 09:02
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 09:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554138
;

-- 15.03.2016 09:08
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543038,0,'Product_UUID',TO_TIMESTAMP('2016-03-15 09:08:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt UUID','Produkt UUID',TO_TIMESTAMP('2016-03-15 09:08:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 09:08
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543038 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 15.03.2016 09:08
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:08:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543038 AND AD_Language='de_CH'
;

-- 15.03.2016 09:08
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:08:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product UUID',PrintName='Product UUID' WHERE AD_Element_ID=543038 AND AD_Language='en_US'
;

-- 15.03.2016 09:08
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543039,0,'Partner_UUID',TO_TIMESTAMP('2016-03-15 09:08:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Partner UUID','Partner UUID',TO_TIMESTAMP('2016-03-15 09:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 09:08
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543039 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 15.03.2016 09:08
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:08:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543039 AND AD_Language='de_CH'
;

-- 15.03.2016 09:08
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:08:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543039 AND AD_Language='en_US'
;

-- 15.03.2016 09:10
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554228,543038,0,10,540747,'N','Product_UUID',TO_TIMESTAMP('2016-03-15 09:10:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produkt UUID',0,TO_TIMESTAMP('2016-03-15 09:10:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 15.03.2016 09:10
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554228 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 15.03.2016 09:10
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554229,543039,0,10,540747,'N','Partner_UUID',TO_TIMESTAMP('2016-03-15 09:10:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Partner UUID',0,TO_TIMESTAMP('2016-03-15 09:10:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 15.03.2016 09:10
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554229 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 15.03.2016 09:11
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554228,556770,0,540727,0,TO_TIMESTAMP('2016-03-15 09:11:43','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','N','Produkt UUID',170,170,0,1,1,TO_TIMESTAMP('2016-03-15 09:11:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 09:11
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556770 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.03.2016 09:13
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554229,556771,0,540727,0,TO_TIMESTAMP('2016-03-15 09:13:19','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','N','Partner UUID',180,180,0,1,1,TO_TIMESTAMP('2016-03-15 09:13:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 09:13
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556771 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556670
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556671
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556771
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556770
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556674
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556675
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556685
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556681
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556676
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556680
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556682
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556683
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556684
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556694
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556677
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556678
;

-- 15.03.2016 09:15
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2016-03-15 09:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556671
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556771
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556673
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556770
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556674
;

-- 15.03.2016 09:16
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556685
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2016-03-15 09:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556681
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2016-03-15 09:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2016-03-15 09:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556675
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2016-03-15 09:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556685
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 15.03.2016 09:17
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:17:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556681
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556675
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556685
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556676
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556680
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556683
;

-- 15.03.2016 09:18
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-03-15 09:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556694
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Element SET Name='Bestellkandidat', PrintName='Bestellkandidat',Updated=TO_TIMESTAMP('2016-03-15 09:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543018
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543018
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PMM_PurchaseCandidate_ID', Name='Bestellkandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=543018
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_PurchaseCandidate_ID', Name='Bestellkandidat', Description=NULL, Help=NULL, AD_Element_ID=543018 WHERE UPPER(ColumnName)='PMM_PURCHASECANDIDATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_PurchaseCandidate_ID', Name='Bestellkandidat', Description=NULL, Help=NULL WHERE AD_Element_ID=543018 AND IsCentrallyMaintained='Y'
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Bestellkandidat', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543018) AND IsCentrallyMaintained='Y'
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Bestellkandidat', Name='Bestellkandidat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543018)
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:19:38','YYYY-MM-DD HH24:MI:SS'),Name='PurchaseCandidatePurchase order candidate' WHERE AD_Element_ID=543018 AND AD_Language='de_CH'
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:19:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Bestellkandidat',PrintName='Bestellkandidat' WHERE AD_Element_ID=543018 AND AD_Language='de_CH'
;

-- 15.03.2016 09:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2016-03-15 09:19:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543018 AND AD_Language='en_US'
;

-- 15.03.2016 09:20
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554222,556772,0,540727,0,TO_TIMESTAMP('2016-03-15 09:20:23','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten',0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','N','Fehler',185,190,0,1,1,TO_TIMESTAMP('2016-03-15 09:20:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 09:20
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556772 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.03.2016 09:20
-- URL zum Konzept
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2016-03-15 09:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;

-- 15.03.2016 09:20
-- URL zum Konzept
UPDATE AD_Field SET SpanY=2,Updated=TO_TIMESTAMP('2016-03-15 09:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;

-- 15.03.2016 09:21
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=5, SeqNoGrid=5,Updated=TO_TIMESTAMP('2016-03-15 09:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556672
;

-- 15.03.2016 09:21
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2016-03-15 09:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556672
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556677
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556694
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556684
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556683
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556682
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556674
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556670
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556672
;

-- 15.03.2016 09:22
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-03-15 09:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556669
;



-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-03-15 11:44:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540745
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-03-15 11:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540746
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-03-15 11:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540747
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table SET Name='Bestellposition',Updated=TO_TIMESTAMP('2016-03-15 11:44:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540746
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540746
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table SET Name='Bestellkandidat',Updated=TO_TIMESTAMP('2016-03-15 11:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540745
;

-- 15.03.2016 11:44
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540745
;

-- 15.03.2016 11:45
-- URL zum Konzept
UPDATE AD_Tab SET Name='Bestellposition',Updated=TO_TIMESTAMP('2016-03-15 11:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540726
;

-- 15.03.2016 11:45
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540726
;

-- 15.03.2016 11:45
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-15 11:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554154
;





-- 15.03.2016 12:37
-- URL zum Konzept
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2016-03-15 12:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540286
;

-- 15.03.2016 12:38
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=8,Updated=TO_TIMESTAMP('2016-03-15 12:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556671
;

-- 15.03.2016 12:38
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554227,556775,0,540727,0,TO_TIMESTAMP('2016-03-15 12:38:56','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','Y','Lieferprodukt',20,200,0,1,1,TO_TIMESTAMP('2016-03-15 12:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.03.2016 12:38
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556775 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556672
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556671
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556670
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556775
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556771
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556673
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556770
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556674
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556681
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556675
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556685
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556676
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556680
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556682
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556683
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556684
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556694
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556677
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556678
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556772
;

-- 15.03.2016 13:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2016-03-15 13:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;


COMMIT;


-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','DatePromised','TIMESTAMP WITHOUT TIME ZONE',null,'NULL')
;

-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','DatePromised',null,'NULL',null)
;


-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','IsActive','CHAR(1)',null,'Y')
;

-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_HU_PI_Item_Product_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_HU_PI_Item_Product_ID',null,'NULL',null)
;


-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PriceList_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PriceList_ID',null,'NULL',null)
;


-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PricingSystem_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 08:57
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','M_PricingSystem_ID',null,'NULL',null)
;

-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_UOM_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_UOM_ID',null,'NULL',null)
;


-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_Currency_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 08:56
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_Currency_ID',null,'NULL',null)
;

-- 15.03.2016 08:58
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','Price','NUMERIC',null,'NULL')
;

-- 15.03.2016 08:58
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','Price',null,'NULL',null)
;

-- 15.03.2016 08:58
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','QtyPromised','NUMERIC',null,'NULL')
;

-- 15.03.2016 08:58
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','QtyPromised',null,'NULL',null)
;


-- 15.03.2016 09:02
-- URL zum Konzept
ALTER TABLE PMM_QtyReport_Event ADD PMM_Product_ID NUMERIC(10) DEFAULT NULL 
;


-- 15.03.2016 09:02
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_BPartner_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 09:02
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','C_BPartner_ID',null,'NULL',null)
;


-- 15.03.2016 09:06
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','PMM_Product_ID','NUMERIC(10)',null,'NULL')
;


-- 15.03.2016 09:10
-- URL zum Konzept
ALTER TABLE PMM_QtyReport_Event ADD Product_UUID VARCHAR(60) DEFAULT NULL 
;


-- 15.03.2016 09:10
-- URL zum Konzept
ALTER TABLE PMM_QtyReport_Event ADD Partner_UUID VARCHAR(60) DEFAULT NULL 
;

-- 15.03.2016 11:45
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_purchasecandidate','M_PriceList_ID','NUMERIC(10)',null,'NULL')
;

-- 15.03.2016 11:45
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_purchasecandidate','M_PriceList_ID',null,'NULL',null)
;
