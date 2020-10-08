-- 2020-10-08T07:55:56.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='DONT_EXPORT', Value='DE',Updated=TO_TIMESTAMP('2020-10-08 10:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542168
;

-- 2020-10-08T07:56:06.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='PENDING', Value='P',Updated=TO_TIMESTAMP('2020-10-08 10:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542167
;

-- 2020-10-08T08:11:38.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='PENDING',Updated=TO_TIMESTAMP('2020-10-08 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542167
;

-- 2020-10-08T08:11:46.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='DONT_EXPORT',Updated=TO_TIMESTAMP('2020-10-08 11:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542168
;

-- 2020-10-08T08:44:22.677Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='DONT_EXPORT',Updated=TO_TIMESTAMP('2020-10-08 11:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542168
;

-- 2020-10-08T08:44:28.100Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='PENDING',Updated=TO_TIMESTAMP('2020-10-08 11:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542167
;







-- 2020-10-08T10:39:34.519Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571143,622542,0,53054,0,TO_TIMESTAMP('2020-10-08 13:39:33','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Export Status',590,580,0,1,1,TO_TIMESTAMP('2020-10-08 13:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-08T10:39:34.841Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-08T10:39:34.958Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577791) 
;

-- 2020-10-08T10:39:35.011Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622542
;

-- 2020-10-08T10:39:35.044Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(622542)
;


-- 2020-10-08T10:32:00.597Z
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2020-10-08 13:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53027
;

-- 2020-10-08T10:32:45.576Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2020-10-08 13:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571143
;

-- 2020-10-08T10:32:45.900Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2020-10-08 13:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53683
;

-- 2020-10-08T10:32:46.179Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2020-10-08 13:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

-- 2020-10-08T10:34:13.883Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2020-10-08 13:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571008
;

-- 2020-10-08T10:34:14.167Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2020-10-08 13:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549513
;

-- 2020-10-08T10:34:14.435Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2020-10-08 13:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549481
;

-- 2020-10-08T10:34:40.119Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2020-10-08 13:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569354
;

-- 2020-10-08T10:34:40.378Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2020-10-08 13:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569739
;

-- 2020-10-08T10:34:40.677Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-10-08 13:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500224
;

-- 2020-10-08T10:34:40.971Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-10-08 13:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500223
;

-- 2020-10-08T10:34:41.262Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-10-08 13:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541676
;

-- 2020-10-08T10:34:41.522Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2020-10-08 13:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547287
;

-- 2020-10-08T10:34:41.785Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2020-10-08 13:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551760
;

-- 2020-10-08T10:34:42.047Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2020-10-08 13:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547304
;

-- 2020-10-08T10:34:42.319Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2020-10-08 13:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552464
;

-- 2020-10-08T10:34:42.615Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2020-10-08 13:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500231
;

-- 2020-10-08T10:34:42.877Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2020-10-08 13:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545450
;

-- 2020-10-08T10:34:43.158Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2020-10-08 13:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540358
;

-- 2020-10-08T10:34:43.429Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2020-10-08 13:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570910
;

-- 2020-10-08T10:34:43.685Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=140,Updated=TO_TIMESTAMP('2020-10-08 13:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552463
;

-- 2020-10-08T10:34:43.937Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=150,Updated=TO_TIMESTAMP('2020-10-08 13:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551759
;

-- 2020-10-08T10:34:44.190Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=160,Updated=TO_TIMESTAMP('2020-10-08 13:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500222
;











-- 2020-10-08T13:07:07.777Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571144,622543,0,53054,0,TO_TIMESTAMP('2020-10-08 16:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann',0,'D',0,'Y','Y','Y','N','N','N','N','N','Exportierbar ab',600,590,0,1,1,TO_TIMESTAMP('2020-10-08 16:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-08T13:07:07.861Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=622543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-08T13:07:07.902Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577792) 
;

-- 2020-10-08T13:07:07.940Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=622543
;

-- 2020-10-08T13:07:07.973Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(622543)
;

-- 2020-10-08T13:07:43.692Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622542,0,53054,540183,573548,'F',TO_TIMESTAMP('2020-10-08 16:07:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Export Status',220,0,0,TO_TIMESTAMP('2020-10-08 16:07:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-08T13:07:58.785Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622543,0,53054,540183,573549,'F',TO_TIMESTAMP('2020-10-08 16:07:58','YYYY-MM-DD HH24:MI:SS'),100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann','Y','N','N','Y','N','N','N',0,'Exportierbar ab',230,0,0,TO_TIMESTAMP('2020-10-08 16:07:58','YYYY-MM-DD HH24:MI:SS'),100)
;





-- 2020-10-08T14:30:12.084Z
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='',Updated=TO_TIMESTAMP('2020-10-08 17:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540614
;


