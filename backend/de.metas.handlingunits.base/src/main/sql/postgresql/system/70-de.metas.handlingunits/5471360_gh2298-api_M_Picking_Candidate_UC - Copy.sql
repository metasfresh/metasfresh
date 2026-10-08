-- 2017-09-12T07:25:25.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540404,0,540831,TO_TIMESTAMP('2017-09-12 07:25:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Y','M_Picking_Candidate_UC','N',TO_TIMESTAMP('2017-09-12 07:25:24','YYYY-MM-DD HH24:MI:SS'),100,'M_Picking_Candidate.IsActive=''Y''')
;

-- 2017-09-12T07:25:25.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540404 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-12T07:25:40.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556981,540807,540404,0,TO_TIMESTAMP('2017-09-12 07:25:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2017-09-12 07:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T07:26:12.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556979,540808,540404,0,TO_TIMESTAMP('2017-09-12 07:26:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',20,TO_TIMESTAMP('2017-09-12 07:26:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T07:26:28.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556980,540809,540404,0,TO_TIMESTAMP('2017-09-12 07:26:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',30,TO_TIMESTAMP('2017-09-12 07:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T07:26:33.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Picking_Candidate_UC ON M_Picking_Candidate (M_HU_ID,M_ShipmentSchedule_ID,M_PickingSlot_ID) WHERE M_Picking_Candidate.IsActive='Y'
;

-- 2017-09-12T08:15:07.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557018,540810,540404,0,TO_TIMESTAMP('2017-09-12 08:15:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',40,TO_TIMESTAMP('2017-09-12 08:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T08:15:13.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_picking_candidate_uc
;

-- 2017-09-12T08:15:13.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Picking_Candidate_UC ON M_Picking_Candidate (M_HU_ID,M_ShipmentSchedule_ID,M_PickingSlot_ID,Status) WHERE M_Picking_Candidate.IsActive='Y'
;

-- 2017-09-12T08:15:38.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540810
;

-- 2017-09-12T08:15:41.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_picking_candidate_uc
;

-- 2017-09-12T08:15:41.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Picking_Candidate_UC ON M_Picking_Candidate (M_HU_ID,M_ShipmentSchedule_ID,M_PickingSlot_ID) WHERE M_Picking_Candidate.IsActive='Y'
;
