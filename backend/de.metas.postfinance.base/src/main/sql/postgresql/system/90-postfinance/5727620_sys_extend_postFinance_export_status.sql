-- Run mode: SWING_CLIENT

-- Reference: PostFinance_ExportStatus
-- Value: E
-- ValueName: Error
-- 2024-07-02T06:14:05.775Z
UPDATE AD_Ref_List SET Name='Datenfehler',Updated=TO_TIMESTAMP('2024-07-02 08:14:05.775','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543650
;

-- 2024-07-02T06:14:05.782Z
UPDATE AD_Ref_List_Trl trl SET Name='Datenfehler' WHERE AD_Ref_List_ID=543650 AND AD_Language='de_DE'
;

-- Reference Item: PostFinance_ExportStatus -> E_Error
-- 2024-07-02T06:14:22.366Z
UPDATE AD_Ref_List_Trl SET Name='Datenfehler',Updated=TO_TIMESTAMP('2024-07-02 08:14:22.366','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543650
;

-- Reference Item: PostFinance_ExportStatus -> E_Error
-- 2024-07-02T06:14:29.949Z
UPDATE AD_Ref_List_Trl SET Name='Data Error',Updated=TO_TIMESTAMP('2024-07-02 08:14:29.949','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543650
;

-- Reference: PostFinance_ExportStatus
-- Value: D
-- ValueName: DontSend
-- 2024-07-02T06:17:59.888Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541860,543697,TO_TIMESTAMP('2024-07-02 08:17:59.756','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Soll nicht gesendet werden',TO_TIMESTAMP('2024-07-02 08:17:59.756','YYYY-MM-DD HH24:MI:SS.US'),100,'D','DontSend')
;

-- 2024-07-02T06:17:59.893Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543697 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PostFinance_ExportStatus -> D_DontSend
-- 2024-07-02T06:18:07.547Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 08:18:07.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543697
;

-- Reference Item: PostFinance_ExportStatus -> D_DontSend
-- 2024-07-02T06:18:08.847Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 08:18:08.847','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543697
;

-- Reference Item: PostFinance_ExportStatus -> D_DontSend
-- 2024-07-02T06:19:02.200Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Don''t send',Updated=TO_TIMESTAMP('2024-07-02 08:19:02.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543697
;

-- Reference: PostFinance_ExportStatus
-- Value: T
-- ValueName: TransmissionError
-- 2024-07-02T06:19:50.223Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541860,543698,TO_TIMESTAMP('2024-07-02 08:19:50.083','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Übertragungsfehler',TO_TIMESTAMP('2024-07-02 08:19:50.083','YYYY-MM-DD HH24:MI:SS.US'),100,'T','TransmissionError')
;

-- 2024-07-02T06:19:50.226Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543698 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PostFinance_ExportStatus -> T_TransmissionError
-- 2024-07-02T06:19:56.691Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 08:19:56.691','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543698
;

-- Reference Item: PostFinance_ExportStatus -> T_TransmissionError
-- 2024-07-02T06:19:57.877Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 08:19:57.877','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543698
;

-- Reference Item: PostFinance_ExportStatus -> T_TransmissionError
-- 2024-07-02T06:20:10.508Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Transmission Error',Updated=TO_TIMESTAMP('2024-07-02 08:20:10.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543698
;

