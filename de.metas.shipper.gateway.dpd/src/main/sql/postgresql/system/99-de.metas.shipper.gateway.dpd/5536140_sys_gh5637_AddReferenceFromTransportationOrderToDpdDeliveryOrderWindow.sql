-- 2019-11-12T13:27:23.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,Description,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-12 15:27:23','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-12 15:27:23','YYYY-MM-DD HH24:MI:SS'),100,541074,'T','Dpd Store  Order -> Transportation Order',0,'Find a Dpd Store Order by its Transportation Order ID','D')
;

-- 2019-11-12T13:27:23.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541074 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-12T13:28:11.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541074,569434,'M_ShipperTransportation_ID=@M_ShipperTransportation_ID/-1@',0,'Y',TO_TIMESTAMP('2019-11-12 15:28:11','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-12 15:28:11','YYYY-MM-DD HH24:MI:SS'),'N',540752,100,541432,0,'D')
;

-- 2019-11-12T13:28:40.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-11-12 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2019-11-12 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,540231,541074,'N','N',541069,'Transportation Order -> DPD Delivery Order',0,'D')
;

