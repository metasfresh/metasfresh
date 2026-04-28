-- Field: Picking Job -> Picking Job Line -> Catch Einheit
-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Catch Einheit
-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- 2023-10-26T08:25:28.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587605,721724,0,544862,TO_TIMESTAMP('2023-10-26 11:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Catch Einheit',TO_TIMESTAMP('2023-10-26 11:25:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-26T08:25:28.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721724 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-26T08:25:28.904Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2023-10-26T08:25:28.917Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721724
;

-- 2023-10-26T08:25:28.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721724)
;

-- Tab: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits)
-- UI Section: main
-- 2023-10-26T08:27:11.716Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544862,545861,TO_TIMESTAMP('2023-10-26 11:27:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-10-26 11:27:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-26T08:27:11.718Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545861 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2023-10-26T08:27:15.936Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547149,545861,TO_TIMESTAMP('2023-10-26 11:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-26 11:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2023-10-26T08:27:18.357Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547150,545861,TO_TIMESTAMP('2023-10-26 11:27:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-10-26 11:27:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10
-- UI Element Group: main
-- 2023-10-26T08:27:54.185Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547149,551272,TO_TIMESTAMP('2023-10-26 11:27:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-10-26 11:27:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits)
-- UI Section: main
-- 2023-10-26T08:28:21.565Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544861,545862,TO_TIMESTAMP('2023-10-26 11:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-10-26 11:28:21','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-26T08:28:21.566Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545862 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2023-10-26T08:28:25.549Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547151,545862,TO_TIMESTAMP('2023-10-26 11:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-26 11:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2023-10-26T08:28:26.930Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547152,545862,TO_TIMESTAMP('2023-10-26 11:28:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-10-26 11:28:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: main
-- 2023-10-26T08:28:33.554Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547151,551273,TO_TIMESTAMP('2023-10-26 11:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-10-26 11:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Picking Job
-- Column: M_Picking_Job.M_Picking_Job_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Picking Job
-- Column: M_Picking_Job.M_Picking_Job_ID
-- 2023-10-26T08:28:52.769Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669033,0,544861,551273,621168,'F',TO_TIMESTAMP('2023-10-26 11:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Picking Job',10,0,0,TO_TIMESTAMP('2023-10-26 11:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Auftrag
-- Column: M_Picking_Job.C_Order_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Auftrag
-- Column: M_Picking_Job.C_Order_ID
-- 2023-10-26T08:28:59.229Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669034,0,544861,551273,621169,'F',TO_TIMESTAMP('2023-10-26 11:28:59','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',20,0,0,TO_TIMESTAMP('2023-10-26 11:28:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Geschäftspartner
-- Column: M_Picking_Job.C_BPartner_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Geschäftspartner
-- Column: M_Picking_Job.C_BPartner_ID
-- 2023-10-26T08:29:33.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669035,0,544861,551273,621170,'F',TO_TIMESTAMP('2023-10-26 11:29:33','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner',30,0,0,TO_TIMESTAMP('2023-10-26 11:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Standort
-- Column: M_Picking_Job.C_BPartner_Location_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Standort
-- Column: M_Picking_Job.C_BPartner_Location_ID
-- 2023-10-26T08:29:40.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669036,0,544861,551273,621171,'F',TO_TIMESTAMP('2023-10-26 11:29:40','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','Standort',40,0,0,TO_TIMESTAMP('2023-10-26 11:29:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Lieferadresse
-- Column: M_Picking_Job.DeliveryToAddress
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Lieferadresse
-- Column: M_Picking_Job.DeliveryToAddress
-- 2023-10-26T08:29:50.225Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669037,0,544861,551273,621172,'F',TO_TIMESTAMP('2023-10-26 11:29:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferadresse',50,0,0,TO_TIMESTAMP('2023-10-26 11:29:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: dates
-- 2023-10-26T08:29:58.724Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547151,551274,TO_TIMESTAMP('2023-10-26 11:29:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2023-10-26 11:29:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: date
-- 2023-10-26T08:30:05.114Z
UPDATE AD_UI_ElementGroup SET Name='date',Updated=TO_TIMESTAMP('2023-10-26 11:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551274
;

-- UI Element: Picking Job -> Picking Job.Bereitstellungsdatum
-- Column: M_Picking_Job.PreparationDate
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> date.Bereitstellungsdatum
-- Column: M_Picking_Job.PreparationDate
-- 2023-10-26T08:30:19.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669038,0,544861,551274,621173,'F',TO_TIMESTAMP('2023-10-26 11:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereitstellungsdatum',10,0,0,TO_TIMESTAMP('2023-10-26 11:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> date.Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-26T08:30:25.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721584,0,544861,551274,621174,'F',TO_TIMESTAMP('2023-10-26 11:30:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdatum',20,0,0,TO_TIMESTAMP('2023-10-26 11:30:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20
-- UI Element Group: settings
-- 2023-10-26T08:30:51.482Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547152,551275,TO_TIMESTAMP('2023-10-26 11:30:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','settings',10,TO_TIMESTAMP('2023-10-26 11:30:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> settings.Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- 2023-10-26T08:31:03.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721716,0,544861,551275,621175,'F',TO_TIMESTAMP('2023-10-26 11:31:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Allow Picking any HU',10,0,0,TO_TIMESTAMP('2023-10-26 11:31:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20
-- UI Element Group: status
-- 2023-10-26T08:31:25.672Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547152,551276,TO_TIMESTAMP('2023-10-26 11:31:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',20,TO_TIMESTAMP('2023-10-26 11:31:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> status.Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- 2023-10-26T08:31:41.468Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669039,0,544861,551276,621176,'F',TO_TIMESTAMP('2023-10-26 11:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mitarbeiter',10,0,0,TO_TIMESTAMP('2023-10-26 11:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> status.Picking Slot
-- Column: M_Picking_Job.M_PickingSlot_ID
-- 2023-10-26T08:31:47.621Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669042,0,544861,551276,621177,'F',TO_TIMESTAMP('2023-10-26 11:31:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Picking Slot',20,0,0,TO_TIMESTAMP('2023-10-26 11:31:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> status.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2023-10-26T08:31:59.992Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669040,0,544861,551276,621178,'F',TO_TIMESTAMP('2023-10-26 11:31:59','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','Belegstatus',30,0,0,TO_TIMESTAMP('2023-10-26 11:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org&client
-- 2023-10-26T08:32:06.864Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547152,551277,TO_TIMESTAMP('2023-10-26 11:32:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',30,TO_TIMESTAMP('2023-10-26 11:32:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Sektion
-- Column: M_Picking_Job.AD_Org_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> org&client.Sektion
-- Column: M_Picking_Job.AD_Org_ID
-- 2023-10-26T08:32:17.973Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669031,0,544861,551277,621179,'F',TO_TIMESTAMP('2023-10-26 11:32:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2023-10-26 11:32:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Mandant
-- Column: M_Picking_Job.AD_Client_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> org&client.Mandant
-- Column: M_Picking_Job.AD_Client_ID
-- 2023-10-26T08:32:24.194Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669030,0,544861,551277,621180,'F',TO_TIMESTAMP('2023-10-26 11:32:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2023-10-26 11:32:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job.Picking Job
-- Column: M_Picking_Job.M_Picking_Job_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Picking Job
-- Column: M_Picking_Job.M_Picking_Job_ID
-- 2023-10-26T08:33:48.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621168
;

-- UI Element: Picking Job -> Picking Job.Auftrag
-- Column: M_Picking_Job.C_Order_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Auftrag
-- Column: M_Picking_Job.C_Order_ID
-- 2023-10-26T08:33:48.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621169
;

-- UI Element: Picking Job -> Picking Job.Geschäftspartner
-- Column: M_Picking_Job.C_BPartner_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Geschäftspartner
-- Column: M_Picking_Job.C_BPartner_ID
-- 2023-10-26T08:33:48.742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621170
;

-- UI Element: Picking Job -> Picking Job.Lieferadresse
-- Column: M_Picking_Job.DeliveryToAddress
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> main.Lieferadresse
-- Column: M_Picking_Job.DeliveryToAddress
-- 2023-10-26T08:33:48.751Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621172
;

-- UI Element: Picking Job -> Picking Job.Bereitstellungsdatum
-- Column: M_Picking_Job.PreparationDate
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> date.Bereitstellungsdatum
-- Column: M_Picking_Job.PreparationDate
-- 2023-10-26T08:33:48.758Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621173
;

-- UI Element: Picking Job -> Picking Job.Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10 -> date.Lieferdatum
-- Column: M_Picking_Job.DeliveryDate
-- 2023-10-26T08:33:48.766Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621174
;

-- UI Element: Picking Job -> Picking Job.Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> status.Mitarbeiter
-- Column: M_Picking_Job.Picking_User_ID
-- 2023-10-26T08:33:48.772Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621176
;

-- UI Element: Picking Job -> Picking Job.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> status.Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2023-10-26T08:33:48.778Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621178
;

-- UI Element: Picking Job -> Picking Job.Sektion
-- Column: M_Picking_Job.AD_Org_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 20 -> org&client.Sektion
-- Column: M_Picking_Job.AD_Org_ID
-- 2023-10-26T08:33:48.784Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-26 11:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621179
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10
-- UI Element Group: product&qty
-- 2023-10-26T08:34:25.905Z
UPDATE AD_UI_ElementGroup SET Name='product&qty',Updated=TO_TIMESTAMP('2023-10-26 11:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551272
;

-- UI Element: Picking Job -> Picking Job Line.Produkt
-- Column: M_Picking_Job_Line.M_Product_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Produkt
-- Column: M_Picking_Job_Line.M_Product_ID
-- 2023-10-26T08:34:42.748Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,669048,0,544862,551272,621181,'F',TO_TIMESTAMP('2023-10-26 11:34:42','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2023-10-26 11:34:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-26T08:34:58.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721718,0,544862,551272,621182,'F',TO_TIMESTAMP('2023-10-26 11:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',20,0,0,TO_TIMESTAMP('2023-10-26 11:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-26T08:35:06.249Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721717,0,544862,551272,621183,'F',TO_TIMESTAMP('2023-10-26 11:35:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Qty To Pick',30,0,0,TO_TIMESTAMP('2023-10-26 11:35:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Catch Einheit
-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Catch Einheit
-- Column: M_Picking_Job_Line.Catch_UOM_ID
-- 2023-10-26T08:35:17.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721724,0,544862,551272,621184,'F',TO_TIMESTAMP('2023-10-26 11:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.','Y','N','Y','N','N','Catch Einheit',40,0,0,TO_TIMESTAMP('2023-10-26 11:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10
-- UI Element Group: product&qty
-- 2023-10-26T08:35:24.841Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2023-10-26 11:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551272
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: main
-- 2023-10-26T08:35:35.438Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2023-10-26 11:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551273
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20
-- UI Element Group: source document
-- 2023-10-26T08:36:01.095Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547150,551278,TO_TIMESTAMP('2023-10-26 11:36:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','source document',10,TO_TIMESTAMP('2023-10-26 11:36:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20
-- UI Element Group: referenced documents
-- 2023-10-26T08:36:10.659Z
UPDATE AD_UI_ElementGroup SET Name='referenced documents',Updated=TO_TIMESTAMP('2023-10-26 11:36:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551278
;

-- UI Element: Picking Job -> Picking Job Line.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-26T08:36:24.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721720,0,544862,551278,621185,'F',TO_TIMESTAMP('2023-10-26 11:36:24','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2023-10-26 11:36:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-26T08:36:30.497Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721721,0,544862,551278,621186,'F',TO_TIMESTAMP('2023-10-26 11:36:30','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','Auftragsposition',20,0,0,TO_TIMESTAMP('2023-10-26 11:36:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2023-10-26T08:36:36.230Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721719,0,544862,551278,621187,'F',TO_TIMESTAMP('2023-10-26 11:36:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdisposition',30,0,0,TO_TIMESTAMP('2023-10-26 11:36:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Picking Job -> Picking Job Line.Produkt
-- Column: M_Picking_Job_Line.M_Product_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Produkt
-- Column: M_Picking_Job_Line.M_Product_ID
-- 2023-10-26T08:37:12.331Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621181
;

-- UI Element: Picking Job -> Picking Job Line.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-26T08:37:12.336Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621182
;

-- UI Element: Picking Job -> Picking Job Line.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> product&qty.Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-26T08:37:12.342Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621183
;

-- UI Element: Picking Job -> Picking Job Line.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-26T08:37:12.348Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621185
;

-- UI Element: Picking Job -> Picking Job Line.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-26T08:37:12.354Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621186
;

-- UI Element: Picking Job -> Picking Job Line.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- UI Element: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 20 -> referenced documents.Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2023-10-26T08:37:12.361Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-26 11:37:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621187
;

