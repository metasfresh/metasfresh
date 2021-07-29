-- 2021-06-17T14:41:47.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555442,TO_TIMESTAMP('2021-06-17 16:41:47','YYYY-MM-DD HH24:MI:SS'),100,54000,100,1,'Y','N','Y','Y','DocumentNo_M_Product_AlbertaTherapy','N',1000000,TO_TIMESTAMP('2021-06-17 16:41:47','YYYY-MM-DD HH24:MI:SS'),100)
;

update AD_Sequence set IsTableID='N', ad_client_id=1000000 where AD_Sequence_ID=555442;


