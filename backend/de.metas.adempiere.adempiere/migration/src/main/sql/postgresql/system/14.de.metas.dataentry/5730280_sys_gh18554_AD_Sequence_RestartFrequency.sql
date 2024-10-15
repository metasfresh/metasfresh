-- UI Element: Nummernfolgen -> Reihenfolge.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2024-07-26T15:14:36.760Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614571
;

-- 2024-07-26T15:14:36.773Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710053
;

-- Field: Nummernfolgen -> Reihenfolge -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Nummernfolge jedes Monat neu beginnen
-- Column: AD_Sequence.StartNewMonth
-- 2024-07-26T15:14:36.782Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710053
;

-- 2024-07-26T15:14:36.784Z
DELETE FROM AD_Field WHERE AD_Field_ID=710053
;

-- 2024-07-26T15:14:36.786Z
/* DDL */ SELECT public.db_alter_table('AD_Sequence','ALTER TABLE AD_Sequence DROP COLUMN IF EXISTS StartNewMonth')
;

-- Column: AD_Sequence.StartNewMonth
-- Column: AD_Sequence.StartNewMonth
-- 2024-07-26T15:14:36.816Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585422
;

-- 2024-07-26T15:14:36.818Z
DELETE FROM AD_Column WHERE AD_Column_ID=585422
;

-- UI Element: Nummernfolgen -> Reihenfolge.Jedes Jahr neu beginnen
-- Column: AD_Sequence.StartNewYear
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 20 -> flags.Jedes Jahr neu beginnen
-- Column: AD_Sequence.StartNewYear
-- 2024-07-26T15:14:45.368Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544140
;

-- 2024-07-26T15:14:45.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=335
;

-- Field: Nummernfolgen -> Reihenfolge -> Nummernfolge jedes Jahr neu beginnen
-- Column: AD_Sequence.StartNewYear
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Nummernfolge jedes Jahr neu beginnen
-- Column: AD_Sequence.StartNewYear
-- 2024-07-26T15:14:45.376Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=335
;

-- 2024-07-26T15:14:45.379Z
DELETE FROM AD_Field WHERE AD_Field_ID=335
;

-- 2024-07-26T15:14:45.380Z
/* DDL */ SELECT public.db_alter_table('AD_Sequence','ALTER TABLE AD_Sequence DROP COLUMN IF EXISTS StartNewYear')
;

-- Column: AD_Sequence.StartNewYear
-- Column: AD_Sequence.StartNewYear
-- 2024-07-26T15:14:45.395Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=225
;

-- 2024-07-26T15:14:45.397Z
DELETE FROM AD_Column WHERE AD_Column_ID=225
;

-- Field: Nummernfolgen -> Reihenfolge -> Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- 2024-07-29T08:50:30.549Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588874,729146,0,146,TO_TIMESTAMP('2024-07-29 11:50:30','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Häufigkeit des Neustarts',TO_TIMESTAMP('2024-07-29 11:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-29T08:50:30.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-29T08:50:30.612Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583198) 
;

-- 2024-07-29T08:50:30.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729146
;

-- 2024-07-29T08:50:30.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729146)
;

-- UI Element: Nummernfolgen -> Reihenfolge.Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- UI Element: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> main -> 10 -> default.Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- 2024-07-29T08:51:26.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729146,0,146,625035,540411,'F',TO_TIMESTAMP('2024-07-29 11:51:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Häufigkeit des Neustarts',40,0,0,TO_TIMESTAMP('2024-07-29 11:51:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Nummernfolgen -> Reihenfolge -> Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Häufigkeit des Neustarts
-- Column: AD_Sequence.RestartFrequency
-- 2024-07-29T09:27:50.766Z
UPDATE AD_Field SET DisplayLogic='@IsAutoSequence@=Y',Updated=TO_TIMESTAMP('2024-07-29 12:27:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729146
;

-- Field: Nummernfolgen -> Reihenfolge -> Date Column
-- Column: AD_Sequence.DateColumn
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Date Column
-- Column: AD_Sequence.DateColumn
-- 2024-07-29T10:38:43.514Z
UPDATE AD_Field SET DisplayLogic='@IsAutoSequence@=Y & @IsTableID@=N & @RestartFrequency@!=NULL',Updated=TO_TIMESTAMP('2024-07-29 13:38:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54357
;

-- Field: Nummernfolgen -> Reihenfolge -> Date Column
-- Column: AD_Sequence.DateColumn
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Date Column
-- Column: AD_Sequence.DateColumn
-- 2024-07-29T10:47:46.981Z
UPDATE AD_Field SET DisplayLogic='@IsAutoSequence@=Y & @IsTableID@=N & (@RestartFrequency@=''Y'' | @RestartFrequency@=''M'' | @RestartFrequency@=''D'')',Updated=TO_TIMESTAMP('2024-07-29 13:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54357
;

-- Field: Nummernfolgen -> Reihenfolge -> Start No
-- Column: AD_Sequence.StartNo
-- Field: Nummernfolgen(112,D) -> Reihenfolge(146,D) -> Start No
-- Column: AD_Sequence.StartNo
-- 2024-07-29T10:49:22.166Z
UPDATE AD_Field SET DisplayLogic='@IsAutoSequence@=Y & @IsTableID@=N & (@RestartFrequency@=''Y'' | @RestartFrequency@=''M'' | @RestartFrequency@=''D'')',Updated=TO_TIMESTAMP('2024-07-29 13:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1555
;

-- Column: AD_Sequence_No.CalendarDay
-- Column: AD_Sequence_No.CalendarDay
-- 2024-07-29T12:43:44.177Z
UPDATE AD_Column SET DefaultValue='1', IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-07-29 15:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588873
;

-- 2024-07-29T12:43:58.558Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarDay','VARCHAR(2)',null,'1')
;

-- 2024-07-29T12:43:58.576Z
UPDATE AD_Sequence_No SET CalendarDay='1' WHERE CalendarDay IS NULL
;

-- 2024-07-29T12:43:58.577Z
INSERT INTO t_alter_column values('ad_sequence_no','CalendarDay',null,'NOT NULL',null)
;

-- include CalendarMonth, CalendarDay in primary key
ALTER TABLE AD_Sequence_no
    DROP CONSTRAINT ad_sequence_no_pkey,
    ADD CONSTRAINT ad_sequence_no_pkey PRIMARY KEY (AD_Sequence_ID, CalendarYear, CalendarMonth, CalendarDay)
;

-- Reference Item: AD_SequenceRestart Frequency  -> D_Day
-- 2024-07-31T09:27:43.541Z
UPDATE AD_Ref_List_Trl SET Name='Day',Updated=TO_TIMESTAMP('2024-07-31 12:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543711
;

-- Reference Item: AD_SequenceRestart Frequency  -> M_Month
-- 2024-07-31T09:27:56.022Z
UPDATE AD_Ref_List_Trl SET Name='Month',Updated=TO_TIMESTAMP('2024-07-31 12:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543710
;

-- Reference Item: AD_SequenceRestart Frequency  -> Y_Year
-- 2024-07-31T09:28:06.625Z
UPDATE AD_Ref_List_Trl SET Name='Year',Updated=TO_TIMESTAMP('2024-07-31 12:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543709
;
