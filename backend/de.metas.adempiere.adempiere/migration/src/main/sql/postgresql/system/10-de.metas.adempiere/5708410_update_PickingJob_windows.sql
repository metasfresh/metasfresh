-- Field: Picking Job -> Picking Job -> Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- 2023-10-23T16:56:41.703Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587599,721716,0,544861,TO_TIMESTAMP('2023-10-23 19:56:41','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Allow Picking any HU',TO_TIMESTAMP('2023-10-23 19:56:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T16:56:41.705Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T16:56:41.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582776) 
;

-- 2023-10-23T16:56:41.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721716
;

-- 2023-10-23T16:56:41.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721716)
;

-- Field: Picking Job -> Picking Job -> Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Allow Picking any HU
-- Column: M_Picking_Job.IsAllowPickingAnyHU
-- 2023-10-23T16:57:44.280Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2023-10-23 19:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721716
;

-- Field: Picking Job -> Picking Job -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job(544861,de.metas.handlingunits) -> Belegstatus
-- Column: M_Picking_Job.DocStatus
-- 2023-10-23T16:57:44.288Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2023-10-23 19:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669040
;

-- Field: Picking Job -> Picking Job Line -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-23T17:01:00.030Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587596,721717,0,544862,TO_TIMESTAMP('2023-10-23 20:00:59','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Qty To Pick',TO_TIMESTAMP('2023-10-23 20:00:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:01:00.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:01:00.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580046) 
;

-- 2023-10-23T17:01:00.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721717
;

-- 2023-10-23T17:01:00.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721717)
;

-- Field: Picking Job -> Picking Job Line -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-23T17:01:00.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587597,721718,0,544862,TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'de.metas.handlingunits','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:01:00.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:01:00.176Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-10-23T17:01:00.272Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721718
;

-- 2023-10-23T17:01:00.274Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721718)
;

-- Field: Picking Job -> Picking Job Line -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2023-10-23T17:01:00.407Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587598,721719,0,544862,TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Lieferdisposition',TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:01:00.408Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:01:00.410Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2023-10-23T17:01:00.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721719
;

-- 2023-10-23T17:01:00.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721719)
;

-- Field: Picking Job -> Picking Job Line -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-23T17:01:00.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587600,721720,0,544862,TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'de.metas.handlingunits','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:01:00.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721720 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:01:00.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-10-23T17:01:00.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721720
;

-- 2023-10-23T17:01:00.587Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721720)
;

-- Field: Picking Job -> Picking Job Line -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-23T17:01:00.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587601,721721,0,544862,TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'de.metas.handlingunits','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2023-10-23 20:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:01:00.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:01:00.712Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2023-10-23T17:01:00.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721721
;

-- 2023-10-23T17:01:00.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721721)
;

-- Field: Picking Job -> Picking Job Line -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-23T17:01:41.553Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-10-23 20:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721718
;

-- Field: Picking Job -> Picking Job Line -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-23T17:01:41.559Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2023-10-23 20:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721717
;

-- Field: Picking Job -> Picking Job Line -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-23T17:01:41.567Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2023-10-23 20:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721720
;

-- Field: Picking Job -> Picking Job Line -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-23T17:01:41.574Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2023-10-23 20:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721721
;

-- Field: Picking Job -> Picking Job Line -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2023-10-23T17:01:41.581Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2023-10-23 20:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721719
;

-- Field: Picking Job -> Picking Job Line -> Mandant
-- Column: M_Picking_Job_Line.AD_Client_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Mandant
-- Column: M_Picking_Job_Line.AD_Client_ID
-- 2023-10-23T17:02:02.389Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669043
;

-- Field: Picking Job -> Picking Job Line -> Sektion
-- Column: M_Picking_Job_Line.AD_Org_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Sektion
-- Column: M_Picking_Job_Line.AD_Org_ID
-- 2023-10-23T17:02:02.396Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669044
;

-- Field: Picking Job -> Picking Job Line -> Aktiv
-- Column: M_Picking_Job_Line.IsActive
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Aktiv
-- Column: M_Picking_Job_Line.IsActive
-- 2023-10-23T17:02:02.401Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669045
;

-- Field: Picking Job -> Picking Job Line -> Picking Job Line
-- Column: M_Picking_Job_Line.M_Picking_Job_Line_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Picking Job Line
-- Column: M_Picking_Job_Line.M_Picking_Job_Line_ID
-- 2023-10-23T17:02:02.408Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669046
;

-- Field: Picking Job -> Picking Job Line -> Picking Job
-- Column: M_Picking_Job_Line.M_Picking_Job_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Picking Job
-- Column: M_Picking_Job_Line.M_Picking_Job_ID
-- 2023-10-23T17:02:02.413Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669047
;

-- Field: Picking Job -> Picking Job Line -> Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Verarbeitet
-- Column: M_Picking_Job_Line.Processed
-- 2023-10-23T17:02:02.418Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669049
;

-- Field: Picking Job -> Picking Job Line -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Qty To Pick
-- Column: M_Picking_Job_Line.QtyToPick
-- 2023-10-23T17:02:02.424Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721717
;

-- Field: Picking Job -> Picking Job Line -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Maßeinheit
-- Column: M_Picking_Job_Line.C_UOM_ID
-- 2023-10-23T17:02:02.429Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721718
;

-- Field: Picking Job -> Picking Job Line -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Lieferdisposition
-- Column: M_Picking_Job_Line.M_ShipmentSchedule_ID
-- 2023-10-23T17:02:02.434Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721719
;

-- Field: Picking Job -> Picking Job Line -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftrag
-- Column: M_Picking_Job_Line.C_Order_ID
-- 2023-10-23T17:02:02.439Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721720
;

-- Field: Picking Job -> Picking Job Line -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- Field: Picking Job(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Auftragsposition
-- Column: M_Picking_Job_Line.C_OrderLine_ID
-- 2023-10-23T17:02:02.444Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-23 20:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721721
;

-- Field: Picking Job Step -> Picking Job Step -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Job Step(544863,de.metas.handlingunits) -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- 2023-10-23T17:02:43.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587603,721722,0,544863,TO_TIMESTAMP('2023-10-23 20:02:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Is Dynamic',TO_TIMESTAMP('2023-10-23 20:02:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-23T17:02:43.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-23T17:02:43.497Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542448) 
;

-- 2023-10-23T17:02:43.501Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721722
;

-- 2023-10-23T17:02:43.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721722)
;

-- Field: Picking Job Step -> Picking Job Step -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Job Step(544863,de.metas.handlingunits) -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- 2023-10-23T17:02:54.447Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2023-10-23 20:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721722
;

-- Field: Picking Job Step -> Picking Job Step -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- Field: Picking Job Step(541332,de.metas.handlingunits) -> Picking Job Step(544863,de.metas.handlingunits) -> Is Dynamic
-- Column: M_Picking_Job_Step.IsDynamic
-- 2023-10-23T17:03:10.010Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-10-23 20:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721722
;

