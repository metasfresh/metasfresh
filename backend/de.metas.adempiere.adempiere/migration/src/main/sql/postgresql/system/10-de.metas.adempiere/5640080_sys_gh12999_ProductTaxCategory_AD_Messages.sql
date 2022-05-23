-- 2022-05-19T17:01:12.900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545125,0,TO_TIMESTAMP('2022-05-19 20:01:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product tax category cannot be removed as there are products depending on it. Product search-keys: {0}','E',TO_TIMESTAMP('2022-05-19 20:01:11','YYYY-MM-DD HH24:MI:SS'),100,'ForbidRemovingProductTaxCategory')
;

-- 2022-05-19T17:01:12.916Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545125 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-19T17:01:21.953Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht entfernt werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545125
;

-- 2022-05-19T17:01:23.988Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht entfernt werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545125
;

-- 2022-05-19T17:01:27.685Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht entfernt werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545125
;

-- 2022-05-19T17:04:34.466Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545126,0,TO_TIMESTAMP('2022-05-19 20:04:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product tax category cannot be changed as there are products depending on it. Product search-keys: {0}','E',TO_TIMESTAMP('2022-05-19 20:04:34','YYYY-MM-DD HH24:MI:SS'),100,'ForbidChangingProductTaxCategory')
;

-- 2022-05-19T17:04:34.469Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545126 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-19T17:04:40.021Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht geändert werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:04:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545126
;

-- 2022-05-19T17:04:41.897Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht geändert werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545126
;

-- 2022-05-19T17:04:45.733Z
UPDATE AD_Message_Trl SET MsgText='Die Produktsteuerkategorie kann nicht geändert werden, da es Produkte gibt, die von ihr abhängen. Siehe unten: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545126
;

-- 2022-05-19T17:09:39.177Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545127,0,TO_TIMESTAMP('2022-05-19 20:09:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Selected Product Price does not have a Tax Category !','E',TO_TIMESTAMP('2022-05-19 20:09:39','YYYY-MM-DD HH24:MI:SS'),100,'MissingTaxCategoryForProductPrice')
;

-- 2022-05-19T17:09:39.180Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545127 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-19T17:09:52.404Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Produktpreis hat keine Steuerkategorie!',Updated=TO_TIMESTAMP('2022-05-19 20:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545127
;

-- 2022-05-19T17:09:54.244Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Produktpreis hat keine Steuerkategorie!',Updated=TO_TIMESTAMP('2022-05-19 20:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545127
;

-- 2022-05-19T17:09:57.019Z
UPDATE AD_Message_Trl SET MsgText='Der ausgewählte Produktpreis hat keine Steuerkategorie!',Updated=TO_TIMESTAMP('2022-05-19 20:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545127
;

-- 2022-05-19T17:53:12.960Z
UPDATE AD_Message SET MsgText='Der ausgewählte Produktpreis hat keine Steuerkategorie!',Updated=TO_TIMESTAMP('2022-05-19 20:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545127
;

-- 2022-05-19T17:53:49.173Z
UPDATE AD_Message SET MsgText='Die Produktsteuerkategorie kann nicht entfernt werden, da es Produkte gibt, die von ihr abhängen. Produkt-Suchschlüssel: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545125
;

-- 2022-05-19T17:54:08.350Z
UPDATE AD_Message SET MsgText='Die Produktsteuerkategorie kann nicht geändert werden, da es Produkte gibt, die von ihr abhängen. Produkt-Suchschlüssel: {0}',Updated=TO_TIMESTAMP('2022-05-19 20:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545126
;