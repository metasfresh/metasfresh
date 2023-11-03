-- 2023-07-26T09:34:36.318470500Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545299,0,TO_TIMESTAMP('2023-07-26 12:34:36.203','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Der Produktionsauftrag ist noch kein modularer Auftrag. Bitte Belegart bei Bedarf entsprechend anpassen.','E',TO_TIMESTAMP('2023-07-26 12:34:36.203','YYYY-MM-DD HH24:MI:SS.US'),100,'NotModularOrder')
;

-- 2023-07-26T09:34:36.331470400Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545299 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:34:46.791082300Z
UPDATE AD_Message_Trl SET MsgText='The manufacturing order is not yet a modular order. Please adjust document type accordingly if required.',Updated=TO_TIMESTAMP('2023-07-26 12:34:46.79','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545299
;

-- 2023-07-26T09:46:02.119400300Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545300,0,TO_TIMESTAMP('2023-07-26 12:46:02.007','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Für einen modularen Auftrag ist ein modularer Vertrag erforderlich. Bitte entsprechend angeben.','E',TO_TIMESTAMP('2023-07-26 12:46:02.007','YYYY-MM-DD HH24:MI:SS.US'),100,'NoContractModularOrder')
;

-- 2023-07-26T09:46:02.127548400Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545300 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:46:24.273401100Z
UPDATE AD_Message_Trl SET MsgText='A modular order requires a modular contract. Please specify accordingly.',Updated=TO_TIMESTAMP('2023-07-26 12:46:24.272','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545300
;

-- 2023-07-26T09:47:40.964777100Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545301,0,TO_TIMESTAMP('2023-07-26 12:47:40.847','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Der Vertrag für den modularen Auftrag muss ein modularer Vertrag sein. Bitte entsprechend angeben.','E',TO_TIMESTAMP('2023-07-26 12:47:40.847','YYYY-MM-DD HH24:MI:SS.US'),100,'NotModularContract')
;

-- 2023-07-26T09:47:40.965776700Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545301 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:47:57.784122100Z
UPDATE AD_Message_Trl SET MsgText='The contract for the modular order needs to be a modular contract. Please specify accordingly.',Updated=TO_TIMESTAMP('2023-07-26 12:47:57.783','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545301
;

-- 2023-07-26T09:48:37.162572700Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545302,0,TO_TIMESTAMP('2023-07-26 12:48:37.047','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Der Geschäftspartner des Lagers muss mit dem des angegebenen Vertrags übereinstimmen.','E',TO_TIMESTAMP('2023-07-26 12:48:37.047','YYYY-MM-DD HH24:MI:SS.US'),100,'ModularContractDifferentBPartners')
;

-- 2023-07-26T09:48:37.164980900Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545302 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:48:53.991154500Z
UPDATE AD_Message_Trl SET MsgText='The business partner of the warehouse must match the one of the specified contract.',Updated=TO_TIMESTAMP('2023-07-26 12:48:53.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545302
;

-- 2023-07-26T09:49:58.339334200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545303,0,TO_TIMESTAMP('2023-07-26 12:49:58.231','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Pro modularen Auftrag kann nur ein Produkt ausgegeben werden.','E',TO_TIMESTAMP('2023-07-26 12:49:58.231','YYYY-MM-DD HH24:MI:SS.US'),100,'OneProductEligibleToBeIssued')
;

-- 2023-07-26T09:49:58.340334200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545303 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:50:14.336419900Z
UPDATE AD_Message_Trl SET MsgText='Only one product can be issued per modular order.',Updated=TO_TIMESTAMP('2023-07-26 12:50:14.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545303
;

-- 2023-07-26T09:50:43.883679700Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545304,0,TO_TIMESTAMP('2023-07-26 12:50:43.752','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Das auszugebende Produkt muss mit dem des angegebenen Vertrags übereinstimmen.','E',TO_TIMESTAMP('2023-07-26 12:50:43.752','YYYY-MM-DD HH24:MI:SS.US'),100,'ManufacturingModularContractDifferentProducts')
;

-- 2023-07-26T09:50:43.886130Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545304 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T09:50:59.814372500Z
UPDATE AD_Message_Trl SET MsgText='The product to be issued must match that of the specified contract.',Updated=TO_TIMESTAMP('2023-07-26 12:50:59.814','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545304
;

-- 2023-07-26T10:01:14.342757600Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545305,0,TO_TIMESTAMP('2023-07-26 13:01:14.193','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Dieser Beleg kann nicht reaktiviert/storniert/annulliert werden, da er bereits mit anderen Vorgängen verbunden ist.','E',TO_TIMESTAMP('2023-07-26 13:01:14.193','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.modular.interceptor.PP_Order.CannotReactivateVoid')
;

-- 2023-07-26T10:01:14.346955200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545305 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T10:01:32.815140600Z
UPDATE AD_Message_Trl SET MsgText='Cannot reactivate/reverse/void this document because it is already associated with other transactions.',Updated=TO_TIMESTAMP('2023-07-26 13:01:32.814','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545305
;

-- 2023-07-26T10:55:38.286126800Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545306,0,TO_TIMESTAMP('2023-07-26 13:55:38.154','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Dieser Beleg kann nicht annulliert werden, da er bereits mit anderen Vorgängen verbunden ist.','E',TO_TIMESTAMP('2023-07-26 13:55:38.154','YYYY-MM-DD HH24:MI:SS.US'),100,'CannotVoidDueToTransactions')
;

-- 2023-07-26T10:55:38.295677500Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545306 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T10:55:53.634670300Z
UPDATE AD_Message_Trl SET MsgText='Cannot void this document because it is already associated with other transactions.',Updated=TO_TIMESTAMP('2023-07-26 13:55:53.632','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545306
;

-- 2023-07-26T11:02:46.425029100Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545307,0,TO_TIMESTAMP('2023-07-26 14:02:46.289','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Dieser Beleg kann nicht reaktiviert werden, da er bereits mit anderen Vorgängen verbunden ist.','E',TO_TIMESTAMP('2023-07-26 14:02:46.289','YYYY-MM-DD HH24:MI:SS.US'),100,'CannotReactivateDueToTransactions')
;

-- 2023-07-26T11:02:46.433032100Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545307 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-26T11:04:30.755127300Z
UPDATE AD_Message_Trl SET MsgText='Cannot reactivate this document because it is already associated with other transactions.',Updated=TO_TIMESTAMP('2023-07-26 14:04:30.754','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545307
;
