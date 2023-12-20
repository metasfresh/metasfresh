-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T20:50:31.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572375,713823,0,290,0,TO_TIMESTAMP('2023-04-06 21:50:30','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe ID',0,340,0,1,1,TO_TIMESTAMP('2023-04-06 21:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-06T20:50:31.966Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-06T20:50:32.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-04-06T20:50:32.213Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713823
;

-- 2023-04-06T20:50:32.329Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713823)
;

-- 2023-04-06T20:51:11.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582231) 
;

-- 2023-04-06T20:51:11.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713823
;

-- 2023-04-06T20:51:11.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713823)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T20:53:16.849Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,713823,0,290,540226,616643,'F',TO_TIMESTAMP('2023-04-06 21:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe ID',50,0,0,TO_TIMESTAMP('2023-04-06 21:53:15','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:04:08.565Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2023-04-06 22:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572375
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:07:02.324Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572375,713824,0,263,0,TO_TIMESTAMP('2023-04-06 23:06:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe ID',0,530,0,1,1,TO_TIMESTAMP('2023-04-06 23:06:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-06T22:07:02.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-06T22:07:02.827Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-04-06T22:07:02.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713824
;

-- 2023-04-06T22:07:03.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713824)
;


-- 2023-04-06T22:08:06.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582231) 
;

-- 2023-04-06T22:08:07.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713824
;

-- 2023-04-06T22:08:07.193Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713824)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:11:37.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713824,0,263,540027,616644,'F',TO_TIMESTAMP('2023-04-06 23:11:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe ID',32,0,0,TO_TIMESTAMP('2023-04-06 23:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:12:26.180Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2023-04-06 23:12:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713824
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-07T10:47:14.775Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541214, SeqNo=100,Updated=TO_TIMESTAMP('2023-04-07 11:47:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616644
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-07T10:47:32.104Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-04-07 11:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616644
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-07T10:48:41.917Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540218, SeqNo=350,Updated=TO_TIMESTAMP('2023-04-07 11:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616643
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2023-04-07T10:49:04.126Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-04-07 11:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616643
;